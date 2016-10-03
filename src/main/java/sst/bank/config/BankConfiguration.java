package sst.bank.config;

import java.io.File;
import java.io.IOException;

import lombok.Getter;
import lombok.Setter;

public class BankConfiguration {
    private static final String COUNTERPARTY_PROPERTIES = "counterparty.properties";
    private static final String POSITIF_COUNTERPARTY_PROPERTIES = "positifcounterparty.properties";
    private static final String DETAIL_PROPERTIES = "detail.properties";
    private static final String ID_PROPERTIES = "id.properties";

    public static final String CATEGORIES_PROPERTIES = "src" + File.separator + "main" + File.separator + "resources"
	    + File.separator + "categories.properties";
    public static final String CATEGORIES_JSON = "src" + File.separator + "main" + File.separator + "resources"
	    + File.separator + "categories.json";
    public static final String OPERATIONS_JSON = "data" + File.separator + "operations.json";
    public static final String OPERATIONS_TXT = "data" + File.separator + "operations.txt";

    private static BankConfiguration me;
    static {
	me = new BankConfiguration();
    }

    private BankConfiguration() {
	try {
	    counterpartiesMapping = InvertedProperties.load(COUNTERPARTY_PROPERTIES);
	    detailsMapping = InvertedProperties.load(DETAIL_PROPERTIES);
	    positifCounterpartiesMapping = InvertedProperties.load(POSITIF_COUNTERPARTY_PROPERTIES);
	    idMapping = InvertedProperties.load(ID_PROPERTIES);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public static BankConfiguration me() {
	return me;
    }

    @Getter
    private InvertedProperties counterpartiesMapping = null;
    @Getter
    private InvertedProperties detailsMapping = null;
    @Getter
    private InvertedProperties positifCounterpartiesMapping = null;
    @Getter
    private InvertedProperties idMapping = null;
    @Setter
    @Getter
    private String inputFileName;
    @Setter
    @Getter
    private String outputFileDir;
}
