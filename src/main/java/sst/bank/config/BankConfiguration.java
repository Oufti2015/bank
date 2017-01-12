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
    public static final String EURO_CHAR = "\u20ac";

    private static final String INPUT_DIR = "INPUT_DIR";
    private static final String OUTPUT_DIR = "OUTPUT_DIR";
    public final Properties properties = new Properties();
    public static final String PATH = "src" + File.separator + "main" + File.separator + "resources";

    public static final String COUNTERPARTY_PROPERTIES = "counterparty.properties";
    public static final String POSITIF_COUNTERPARTY_PROPERTIES = "positifcounterparty.properties";
    public static final String DETAIL_PROPERTIES = "detail.properties";
    public static final String ID_PROPERTIES = "id.properties";

    public static final String COUNTERPARTY_LABEL_PROPERTIES = "counterparty.label.properties";
    public static final String POSITIF_COUNTERPARTY_LABEL_PROPERTIES = "positifcounterparty.label.properties";
    public static final String DETAIL_LABEL_PROPERTIES = "detail.label.properties";
    public static final String ID_LABEL_PROPERTIES = "id.label.properties";

    public static final String CATEGORIES_PROPERTIES = PATH + File.separator + "categories.properties";
    private static final String CATEGORIES_JSON = "categories.json";
    private static final String OPERATIONS_JSON = "operations.json";
    private static final String LABELS_JSON = "labels.json";
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

	    initCategories(pathname);

	    initLabels(pathname);
	} catch (IOException e) {
	    log.fatal("Cannot read property file", e);
	    OuftiBank.eventBus.post(e);
	}
    }

    /**
     * @param pathname
     * @throws IOException
     */
    private void initCategories(String pathname) throws IOException {
	File inputFile = new File(pathname + COUNTERPARTY_PROPERTIES);
	counterpartiesMapping = InvertedCategoryProperties.load(inputFile);
	Assert.assertTrue(COUNTERPARTY_PROPERTIES, !counterpartiesMapping.keySet().isEmpty());

	inputFile = new File(pathname + DETAIL_PROPERTIES);
	detailsMapping = InvertedCategoryProperties.load(inputFile);
	Assert.assertTrue(DETAIL_PROPERTIES, !detailsMapping.keySet().isEmpty());

	inputFile = new File(pathname + POSITIF_COUNTERPARTY_PROPERTIES);
	positifCounterpartiesMapping = InvertedCategoryProperties.load(inputFile);
	Assert.assertTrue(POSITIF_COUNTERPARTY_PROPERTIES, !positifCounterpartiesMapping.keySet().isEmpty());

	inputFile = new File(pathname + ID_PROPERTIES);
	idMapping = InvertedCategoryProperties.load(inputFile);
	Assert.assertTrue(ID_PROPERTIES, !idMapping.keySet().isEmpty());
    }

    /**
     * @param pathname
     * @throws IOException
     */
    private void initLabels(String pathname) throws IOException {
	File inputFile = new File(pathname + COUNTERPARTY_LABEL_PROPERTIES);
	counterpartiesLabelsMapping = InvertedLabelProperties.load(inputFile);

	inputFile = new File(pathname + DETAIL_LABEL_PROPERTIES);
	detailsLabelsMapping = InvertedLabelProperties.load(inputFile);

	inputFile = new File(pathname + POSITIF_COUNTERPARTY_LABEL_PROPERTIES);
	positifCounterpartiesLabelsMapping = InvertedLabelProperties.load(inputFile);

	inputFile = new File(pathname + ID_LABEL_PROPERTIES);
	idLabelsMapping = InvertedLabelProperties.load(inputFile);
    }

    public static BankConfiguration me() {
	return me;
    }

    @Getter
    private InvertedCategoryProperties counterpartiesMapping = null;
    @Getter
    private InvertedCategoryProperties detailsMapping = null;
    @Getter
    private InvertedCategoryProperties positifCounterpartiesMapping = null;
    @Getter
    private InvertedCategoryProperties idMapping = null;

    @Getter
    private InvertedLabelProperties counterpartiesLabelsMapping = null;
    @Getter
    private InvertedLabelProperties detailsLabelsMapping = null;
    @Getter
    private InvertedLabelProperties positifCounterpartiesLabelsMapping = null;
    @Getter
    private InvertedLabelProperties idLabelsMapping = null;

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

    public String getLabelsJson() {
	return getInputDir() + File.separator + LABELS_JSON;
    }
}
