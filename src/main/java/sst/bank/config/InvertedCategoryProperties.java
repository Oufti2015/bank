package sst.bank.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import sst.bank.model.Category;
import sst.bank.model.container.BankContainer;

public class InvertedCategoryProperties extends AbstractInvertedProperties {
    private Map<String, Category> mapping = new HashMap<>();

    private InvertedCategoryProperties() {

    }

    public static InvertedCategoryProperties load(File inputFile) throws IOException {
	Properties props = new Properties();
	InvertedCategoryProperties inverted = new InvertedCategoryProperties();
	props.load(new FileInputStream(inputFile));
	// log.debug("******** props = " + props);
	for (Category category : BankContainer.me().getCategories()) {
	    // log.debug("********************* Category=" + category);
	    List<String> counterparties = getCounterparties(props, category.getName());
	    if (counterparties != null) {
		counterparties.stream().forEach(c -> inverted.mapping_put(c, category));
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

    private void mapping_put(String counterparty, Category category) {
	mapping.put(counterparty, category);
    }

}
