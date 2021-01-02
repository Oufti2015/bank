package sst.bank.config;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.configuration2.ex.ConfigurationException;
import sst.bank.main.OuftiBank;
import sst.inverted.properties.InvertedProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

@Log4j2
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
        ClassLoader classLoader = getClass().getClassLoader();
        try (FileInputStream fileInputStream = new FileInputStream(Objects.requireNonNull(classLoader.getResource("oufti.properties")).getFile())) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            log.fatal("Cannot read " + "oufti.properties", e);
            OuftiBank.eventBus.post(e);
        }
    }

    public void init() {
        try {
            initCategories();

            initLabels();
        } catch (IOException | ConfigurationException e) {
            log.fatal("Cannot read property file", e);
            OuftiBank.eventBus.post(e);
        }
    }

    /**
     * @throws IOException
     */
    private void initCategories() throws IOException, ConfigurationException {
        File inputFile = getMappingFile(COUNTERPARTY_PROPERTIES);
        counterpartiesMapping = new InvertedCategoryProperties(inputFile);

        inputFile = getMappingFile(DETAIL_PROPERTIES);
        detailsMapping = new InvertedCategoryProperties(inputFile);

        inputFile = getMappingFile(POSITIF_COUNTERPARTY_PROPERTIES);
        positifCounterpartiesMapping = new InvertedCategoryProperties(inputFile);

        inputFile = getMappingFile(ID_PROPERTIES);
        idMapping = new InvertedCategoryProperties(inputFile);
    }

    public File getMappingFile(String counterpartyProperties) {
        return new File(getInputDir() + File.separator + counterpartyProperties);
    }

    /**
     * @throws IOException
     */
    private void initLabels() throws IOException {
        File inputFile = getMappingFile(COUNTERPARTY_LABEL_PROPERTIES);
        counterpartiesLabelsMapping = InvertedLabelProperties.load(inputFile);

        inputFile = getMappingFile(DETAIL_LABEL_PROPERTIES);
        detailsLabelsMapping = InvertedLabelProperties.load(inputFile);

        inputFile = getMappingFile(POSITIF_COUNTERPARTY_LABEL_PROPERTIES);
        positifCounterpartiesLabelsMapping = InvertedLabelProperties.load(inputFile);

        inputFile = getMappingFile(ID_LABEL_PROPERTIES);
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
