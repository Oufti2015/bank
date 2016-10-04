package sst.bank.config;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import sst.bank.activities.BankActivity;
import sst.bank.activities.a.config.CategoriesLoader;
import sst.bank.activities.a.config.Configurator;
import sst.bank.model.container.BankContainer;

public class TestConfigurator {

    @Before
    public void init() {
	BankActivity loader = new CategoriesLoader();
	loader.run();
	Assert.assertTrue("Categories not loaded !", !BankContainer.me().getCategories().isEmpty());
    }

    @Test
    public void test() {
	BankActivity activity = new Configurator();
	activity.run();

	Assert.assertTrue(BankConfiguration.COUNTERPARTY_PROPERTIES,
		!BankConfiguration.me().getCounterpartiesMapping().keySet().isEmpty());

	Assert.assertTrue(BankConfiguration.DETAIL_PROPERTIES,
		!BankConfiguration.me().getDetailsMapping().keySet().isEmpty());

	Assert.assertTrue(BankConfiguration.POSITIF_COUNTERPARTY_PROPERTIES,
		!BankConfiguration.me().getPositifCounterpartiesMapping().keySet().isEmpty());

	Assert.assertTrue(BankConfiguration.ID_PROPERTIES, !BankConfiguration.me().getIdMapping().keySet().isEmpty());

    }

}
