package sst.bank.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import lombok.Getter;
import lombok.Setter;
import sst.bank.model.container.BankContainer.CategoryName;

public class BankConfiguration {
    private static final String COUNTERPARTY_PROPERTIES = "counterparty.properties";

    private static BankConfiguration me;
    static {
	me = new BankConfiguration();
    }

    private BankConfiguration() {
	try {
	    ClassLoader classLoader = getClass().getClassLoader();
	    props.load(new FileInputStream(new File(classLoader.getResource(COUNTERPARTY_PROPERTIES).getFile())));
	    System.out.println("******** props = " + props);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public static BankConfiguration me() {
	return me;
    }

    private Properties props = new Properties();

    @Setter
    @Getter
    private String inputFileName;
    @Setter
    @Getter
    private String outputFileDir;

    public List<String> getCounterparties(CategoryName category) {
	String counterpartyString = props.getProperty(category.name());
	return (counterpartyString != null) ? new ArrayList<String>(Arrays.asList(counterpartyString.split(",")))
		: null;
    }
}
