package sst.bank.config;

import org.apache.commons.configuration2.ex.ConfigurationException;
import sst.bank.model.Category;
import sst.bank.model.container.BankContainer;
import sst.inverted.properties.InvertedProperties;

import java.io.File;

public class InvertedCategoryProperties extends InvertedProperties {

    public InvertedCategoryProperties(File inputFile) throws ConfigurationException {
        super(inputFile.getAbsolutePath());
    }

    public Category mapCategory(String value) {
        return BankContainer.me().category(super.map(value));
    }

    public void putMapping(String counterparty, Category category) throws ConfigurationException {
        super.putMapping(counterparty, category.getName());
    }
}
