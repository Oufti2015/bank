package sst.bank.config;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import sst.bank.model.Category;
import sst.bank.model.container.BankContainer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

public class InvertedCategoryProperties extends AbstractInvertedProperties {
    private final Map<String, Category> mapping = new HashMap<>();

    private InvertedCategoryProperties() {
    }

    public static InvertedCategoryProperties load(File inputFile) throws IOException {
        Properties props = new Properties();
        InvertedCategoryProperties inverted = new InvertedCategoryProperties();
        try (FileInputStream inStream = new FileInputStream(inputFile)) {
            props.load(inStream);
        }
        for (Category category : BankContainer.me().getCategories()) {
            List<String> counterparties = getCounterparties(props, category.getName());
            if (counterparties != null) {
                counterparties.forEach(c -> inverted.mappingPut(c, category));
            }
        }

        return inverted;
    }

    public static InvertedCategoryProperties load(String inputFile) throws IOException {
        return load(new File(inputFile));
    }

    public void save(File outputFile) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Files.copy(outputFile.toPath(), new File(outputFile.getAbsolutePath() + "." + sdf.format(new Date())).toPath());

        Properties props = properties();

        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            props.store(outputStream, "Mapping between Category and Counterparties");
        }
    }

    private Properties properties() {
        Multimap<String, String> map = ArrayListMultimap.create();

        for (Map.Entry<String, Category> category : this.mapping.entrySet()) {
            map.put(category.getValue().getName(), category.getKey());
        }

        Properties result = new Properties();
        for (String category : map.keySet()) {
            result.setProperty(category, String.join(",", map.get(category)));
        }
        return result;
    }

    public void save(String outputFile) throws IOException {
        save(new File(outputFile));
    }

    public Category map(String counterparty) {
        return mapping.get(counterparty);
    }

    public Set<String> keySet() {
        return mapping.keySet();
    }

    public void mappingPut(String counterparty, Category category) {
        mapping.put(counterparty, category);
    }
}
