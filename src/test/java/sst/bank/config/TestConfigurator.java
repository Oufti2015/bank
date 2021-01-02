package sst.bank.config;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sst.bank.activities.BankActivity;
import sst.bank.activities.LifeCycleInterface;
import sst.bank.activities.a.config.CategoriesLoader;
import sst.bank.activities.a.config.Configurator;
import sst.bank.model.container.BankContainer;

import java.io.IOException;

import static org.junit.Assert.fail;

public class TestConfigurator {

    @Before
    public void init() {
        BankActivity loader = new CategoriesLoader();
        loader.run();
        Assert.assertTrue("Categories not loaded !", !BankContainer.me().getCategories().isEmpty());
    }

    @Test
    public void testConfigurator() {
        BankActivity activity = new Configurator();
        activity.run();

        Assert.assertTrue(BankConfiguration.COUNTERPARTY_PROPERTIES,
                !BankConfiguration.me().getCounterpartiesMapping().keys().isEmpty());

        Assert.assertTrue(BankConfiguration.DETAIL_PROPERTIES,
                !BankConfiguration.me().getDetailsMapping().keys().isEmpty());

        Assert.assertTrue(BankConfiguration.POSITIF_COUNTERPARTY_PROPERTIES,
                !BankConfiguration.me().getPositifCounterpartiesMapping().keys().isEmpty());

        Assert.assertTrue(BankConfiguration.ID_PROPERTIES, !BankConfiguration.me().getIdMapping().keys().isEmpty());

    }

    @Test
    public void testInvertedProperties() {
   /*     try {
            InvertedCategoryProperties detailsMapping = new InvertedCategoryProperties(BankConfiguration.DETAIL_PROPERTIES);
            Assert.assertTrue("Not loaded", !detailsMapping.size() == 0);
        } catch (IOException e) {
            fail("" + e.getMessage() + " : " + e);
        }*/
    }

    @Test
    public void testReadOnlyLifeCycle() {
        LifeCycleInterface.runReadOnlyLifeCyle();
        Assert.assertFalse(BankContainer.me().getCategories().isEmpty());
    }

}
