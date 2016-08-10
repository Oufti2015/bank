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

import sst.bank.model.container.BankContainer.CategoryName;

public class InvertedProperties {
    private Map<String, CategoryName> mapping = new HashMap<String, CategoryName>();

    private InvertedProperties() {

    }

    public static InvertedProperties load(String inputFile) throws IOException {
	Properties props = new Properties();
	InvertedProperties inverted = new InvertedProperties();
	ClassLoader classLoader = inverted.getClass().getClassLoader();
	props.load(new FileInputStream(new File(classLoader.getResource(inputFile).getFile())));
	System.out.println("******** props = " + props);
	for (CategoryName category : CategoryName.values()) {
	    List<String> counterparties = getCounterparties(props, category);
	    if (counterparties != null) {
		counterparties.stream().forEach(c -> inverted.mapping_put(c, category));
	    }
	}

	return inverted;
    }

    public CategoryName map(String counterparty) {
	return mapping.get(counterparty);
    }

    public Set<String> keySet() {
	return mapping.keySet();
    }

    private static List<String> getCounterparties(Properties props, CategoryName category) {
	String counterpartyString = props.getProperty(category.name());
	return (counterpartyString != null) ? new ArrayList<String>(Arrays.asList(counterpartyString.split(",")))
		: null;
    }

    private void mapping_put(String counterparty, CategoryName category) {
	mapping.put(counterparty, category);
    }

}
