package sst.bank.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import sst.bank.model.OperationLabel;
import sst.bank.model.container.BankContainer;

public class InvertedLabelProperties extends AbstractInvertedProperties {
    private Map<String, OperationLabel> mapping = new HashMap<>();

    private InvertedLabelProperties() {

    }

    public static InvertedLabelProperties load(File inputFile) throws IOException {
	Properties props = new Properties();
	InvertedLabelProperties inverted = new InvertedLabelProperties();
	props.load(new FileInputStream(inputFile));
	// log.debug("******** props = " + props);
	for (OperationLabel label : BankContainer.me().getLabels()) {
	    // log.debug("********************* Category=" + category);
	    List<String> counterparties = getCounterparties(props, label.getId());
	    if (counterparties != null) {
		counterparties.stream().forEach(c -> inverted.mapping_put(c, label));
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

    private void mapping_put(String counterparty, OperationLabel category) {
	mapping.put(counterparty, category);
    }

}
