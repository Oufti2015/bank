package sst.bank.config;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import sst.bank.activities.BankActivity;
import sst.bank.activities.a.config.CategoriesLoader;
import sst.bank.model.container.BankContainer;

public class TestInvertedProperties {

    @Before
    public void init() {
	BankActivity loader = new CategoriesLoader();
	loader.run();
	Assert.assertTrue("Categories not loaded !", !BankContainer.me().getCategories().isEmpty());
    }

    @Test
    public void test() {
	try {
	    InvertedProperties detailsMapping = InvertedProperties.load(BankConfiguration.DETAIL_PROPERTIES);
	    Assert.assertTrue("Not loaded", !detailsMapping.keySet().isEmpty());
	} catch (IOException e) {
	    fail("" + e.getMessage() + " : " + e);
	}
    }
}
