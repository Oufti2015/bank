package sst.bank.config;

import org.junit.Test;
import sst.bank.activities.a.config.CategoriesLoader;

import java.io.File;
import java.io.IOException;

public class TestInvertedPropertiesLoadAndSave {

    @Test
    public void testIPropToSKV() {
        try {
            new CategoriesLoader().run();

            final InvertedCategoryProperties inverted = InvertedCategoryProperties.load("/Users/steph/Documents/bank/input/counterparty.properties");

            System.out.println("Loaded " + inverted.keySet().size());

            final File tempFile = File.createTempFile("Test", ".properties");
            inverted.save(tempFile);

            System.out.println("Temp File = [" + tempFile.getAbsolutePath() + "] length = <" + tempFile.length() + ">");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
