package sst.bank.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.junit.Assert;

import lombok.Getter;
import lombok.extern.log4j.Log4j;
import sst.bank.main.OuftiBank;

@Log4j
public class BankConfiguration {
    private static final String INPUT_DIR = "INPUT_DIR";
    private static final String OUTPUT_DIR = "OUTPUT_DIR";
    public final Properties properties = new Properties();
    public static final String PATH = "src" + File.separator + "main" + File.separator + "resources";
    public static final String COUNTERPARTY_PROPERTIES = "counterparty.properties";
    public static final String POSITIF_COUNTERPARTY_PROPERTIES = "positifcounterparty.properties";
    public static final String DETAIL_PROPERTIES = "detail.properties";
    public static final String ID_PROPERTIES = "id.properties";

    public static final String CATEGORIES_PROPERTIES = PATH + File.separator + "categories.properties";
    private static final String CATEGORIES_JSON = "categories.json";
    private static final String OPERATIONS_JSON = "operations.json";
    public static final String OPERATIONS_TXT = "data" + File.separator + "operations.txt";

    private static BankConfiguration me;
    static {
	me = new BankConfiguration();
    }

    private BankConfiguration() {
	try {
	    ClassLoader classLoader = getClass().getClassLoader();
	    properties.load(new FileInputStream(new File(classLoader.getResource("oufti.properties").getFile())));
	} catch (IOException e) {
	    log.fatal("Cannot read " + "oufti.properties", e);
	    OuftiBank.eventBus.post(e);
	}
    }

    public void init() {
	try {

	    String pathname = getInputDir() + File.separator;
	    File inputFile = new File(pathname + COUNTERPARTY_PROPERTIES);
	    counterpartiesMapping = InvertedProperties.load(inputFile);
	    Assert.assertTrue(COUNTERPARTY_PROPERTIES, !counterpartiesMapping.keySet().isEmpty());

	    inputFile = new File(pathname + DETAIL_PROPERTIES);
	    detailsMapping = InvertedProperties.load(inputFile);
	    Assert.assertTrue(DETAIL_PROPERTIES, !detailsMapping.keySet().isEmpty());
	    Assert.assertNotNull(DETAIL_PROPERTIES, detailsMapping.map("LAURENT MURIEL"));

	    inputFile = new File(pathname + POSITIF_COUNTERPARTY_PROPERTIES);
	    positifCounterpartiesMapping = InvertedProperties.load(inputFile);
	    Assert.assertTrue(POSITIF_COUNTERPARTY_PROPERTIES, !positifCounterpartiesMapping.keySet().isEmpty());

	    inputFile = new File(pathname + ID_PROPERTIES);
	    idMapping = InvertedProperties.load(inputFile);
	    Assert.assertTrue(ID_PROPERTIES, !idMapping.keySet().isEmpty());
	} catch (IOException e) {
	    log.fatal("Cannot read property file", e);
	    OuftiBank.eventBus.post(e);
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

    public String getInputDir() {
	return properties.getProperty(INPUT_DIR);
    }

    public String getOutputDir() {
	return properties.getProperty(OUTPUT_DIR);
    }

    public String getOperationsJson() {
	return getInputDir() + File.separator + OPERATIONS_JSON;
    }

    public String getCategoriesJson() {
	return getInputDir() + File.separator + CATEGORIES_JSON;
    }
}
