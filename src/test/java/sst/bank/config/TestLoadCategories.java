package sst.bank.config;

import org.junit.Assert;
import org.junit.Test;

import sst.bank.activities.BankActivity;
import sst.bank.activities.a.config.CategoriesLoader;
import sst.bank.model.container.BankContainer;

public class TestLoadCategories {

    @Test
    public void test() {
	BankActivity loader = new CategoriesLoader();
	loader.run();
	Assert.assertTrue("Categories not loaded !", !BankContainer.me().getCategories().isEmpty());
    }
}
