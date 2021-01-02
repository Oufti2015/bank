package sst.bank.config;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import sst.bank.model.OperationLabel;
import sst.bank.model.container.BankContainer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

public class InvertedLabelProperties extends AbstractInvertedProperties {
    private final Map<String, OperationLabel> mapping = new HashMap<>();

    private InvertedLabelProperties() {
    }

    public static InvertedLabelProperties load(File inputFile) throws IOException {
        Properties props = new Properties();
        InvertedLabelProperties inverted = new InvertedLabelProperties();
        try (FileInputStream inStream = new FileInputStream(inputFile)) {
            props.load(inStream);
        }
        for (OperationLabel label : BankContainer.me().getLabels()) {
            List<String> counterparties = getCounterparties(props, label.getId());
            if (counterparties != null) {
                counterparties.stream().forEach(c -> inverted.mappingPut(c, label));
            }
        }

        return inverted;
    }

    public static InvertedLabelProperties load(String inputFile) throws IOException {
        return load(new File(inputFile));
    }

    public void save(File outputFile) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Files.copy(outputFile.toPath(), new File(outputFile.getAbsolutePath() + "." + sdf.format(new Date())).toPath());

        Properties props = properties();

        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            props.store(outputStream, "Mapping between Labels and Counterparties");
        }
    }

    private Properties properties() {
        Multimap<String, String> map = ArrayListMultimap.create();

        for (Map.Entry<String, OperationLabel> category : this.mapping.entrySet()) {
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

    public OperationLabel map(String counterparty) {
        return mapping.get(counterparty);
    }

    public Set<String> keySet() {
        return mapping.keySet();
    }

    private void mappingPut(String counterparty, OperationLabel category) {
        mapping.put(counterparty, category);
    }

}
