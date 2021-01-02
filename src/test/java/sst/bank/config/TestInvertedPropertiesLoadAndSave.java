package sst.bank.config;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.Assert;
import org.junit.Test;
import sst.bank.activities.a.config.CategoriesLoader;

import java.io.File;
import java.io.IOException;

public class TestInvertedPropertiesLoadAndSave {

    @Test
    public void testIPropToSKV() {
        try {
            new CategoriesLoader().run();

            final InvertedCategoryProperties inverted = new InvertedCategoryProperties(new File("/Users/steph/Documents/bank/input/counterparty.properties"));

            System.out.println("Loaded " + inverted.size());

            final File tempFile = File.createTempFile("Test", ".properties");
            inverted.save();

            System.out.println("Temp File = [" + tempFile.getAbsolutePath() + "] length = <" + tempFile.length() + ">");

            Assert.assertNotEquals(0, inverted.size());
        } catch (IOException | ConfigurationException e) {
            e.printStackTrace();
        }
    }
}
