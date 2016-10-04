package sst.bank.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import sst.bank.model.Category;
import sst.bank.model.container.BankContainer;

public class InvertedProperties {
    private Map<String, Category> mapping = new HashMap<>();

    private InvertedProperties() {

    }

    public static InvertedProperties load(String inputFile) throws IOException {
	Properties props = new Properties();
	InvertedProperties inverted = new InvertedProperties();
	ClassLoader classLoader = inverted.getClass().getClassLoader();
	props.load(new FileInputStream(new File(classLoader.getResource(inputFile).getFile())));
	// System.out.println("******** props = " + props);
	for (Category category : BankContainer.me().getCategories()) {
	    // System.out.println("Category=" + category);
	    List<String> counterparties = getCounterparties(props, category.getName());
	    if (counterparties != null) {
		counterparties.stream().forEach(c -> inverted.mapping_put(c, category));
	    }
	}

	return inverted;
    }

    public Category map(String counterparty) {
	return mapping.get(counterparty);
    }

    public Set<String> keySet() {
	return mapping.keySet();
    }

    private static List<String> getCounterparties(Properties props, String category) {
	String counterpartyString = props.getProperty(category);
	return (counterpartyString != null) ? new ArrayList<String>(Arrays.asList(counterpartyString.split(",")))
		: null;
    }

    private void mapping_put(String counterparty, Category category) {
	mapping.put(counterparty, category);
    }

}
