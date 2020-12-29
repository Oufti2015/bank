package sst.bank.config;

import sst.bank.model.Category;
import sst.bank.model.container.BankContainer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

    public Category map(String counterparty) {
        return mapping.get(counterparty);
    }

    public Set<String> keySet() {
        return mapping.keySet();
    }

    private void mappingPut(String counterparty, Category category) {
        mapping.put(counterparty, category);
    }
}
