package sst.bank.config;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import sst.bank.activities.BankActivity;
import sst.bank.activities.a.config.CategoriesLoader;
import sst.bank.activities.a.config.Configurator;
import sst.bank.activities.a.config.LabelsLoader;
import sst.bank.activities.b.loading.OperationsLoader;
import sst.bank.model.container.BankContainer;

public class TestOperationsLoader {

    @Before
    public void init() {
	new CategoriesLoader().run();
	new LabelsLoader().run();
	new Configurator().run();
    }

    @Test
    public void testOperationsLoader() {
	BankActivity activity = new OperationsLoader();
	activity.run();

	Assert.assertTrue(800 < BankContainer.me().operationsContainer().operations().size());
    }
}
