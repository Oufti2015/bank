package sst.bank.config;

import sst.bank.model.OperationLabel;
import sst.bank.model.container.BankContainer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
