package sst.bank.config;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
public class BankConfiguration {
    public static final String PATH = "src" + File.separator + "main" + File.separator + "resources";
    public static final String COUNTERPARTY_PROPERTIES = "counterparty.properties";
    public static final String POSITIF_COUNTERPARTY_PROPERTIES = "positifcounterparty.properties";
    public static final String DETAIL_PROPERTIES = "detail.properties";
    public static final String ID_PROPERTIES = "id.properties";

    public static final String CATEGORIES_PROPERTIES = PATH + File.separator + "categories.properties";
    public static final String CATEGORIES_JSON = PATH + File.separator + "categories.json";
    public static final String OPERATIONS_JSON = "../bank/data/operations.json";
    public static final String OPERATIONS_TXT = "data" + File.separator + "operations.txt";

    private static BankConfiguration me;
    static {
	me = new BankConfiguration();
    }

    private BankConfiguration() {
    }

    public void init() {
	try {
	    counterpartiesMapping = InvertedProperties.load(COUNTERPARTY_PROPERTIES);
	    Assert.assertTrue(COUNTERPARTY_PROPERTIES, !counterpartiesMapping.keySet().isEmpty());

	    detailsMapping = InvertedProperties.load(DETAIL_PROPERTIES);
	    Assert.assertTrue(DETAIL_PROPERTIES, !detailsMapping.keySet().isEmpty());
	    Assert.assertNotNull(DETAIL_PROPERTIES, detailsMapping.map("LAURENT MURIEL"));

	    positifCounterpartiesMapping = InvertedProperties.load(POSITIF_COUNTERPARTY_PROPERTIES);
	    Assert.assertTrue(POSITIF_COUNTERPARTY_PROPERTIES, !positifCounterpartiesMapping.keySet().isEmpty());

	    idMapping = InvertedProperties.load(ID_PROPERTIES);
	    Assert.assertTrue(ID_PROPERTIES, !idMapping.keySet().isEmpty());
	} catch (IOException e) {
	    log.error("Cannot read property file", e);
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
