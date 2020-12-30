package sst.bank.config;

import org.junit.Test;
import sst.bank.activities.lifecycle.LifeCycle;
import sst.bank.activities.lifecycle.ReadOnlyLifeCycle;
import sst.common.skv.Bucket;
import sst.common.skv.Context;
import sst.common.skv.KeyValue;
import sst.common.skv.SimpleKeyValue;
import sst.textfile.OutputTextFile;
import sst.textfile.OutputTextFileImpl;

import java.io.File;
import java.util.Set;

public class TestPropertiesInTextFile {

    @Test
    public void testIPropToSKV() {

        LifeCycle lc = new ReadOnlyLifeCycle();
        lc.run();

        SimpleKeyValue skv = new KeyValue();

        Context context = skv.context("bank-prod");

        Bucket bucket = skv.bucket(context);

        extractProperties("category-counterparties", bucket, BankConfiguration.me().getCounterpartiesMapping());
        extractProperties("category-details", bucket, BankConfiguration.me().getDetailsMapping());
        extractLabelsProperties("labels-details", bucket, BankConfiguration.me().getDetailsLabelsMapping());
        extractLabelsProperties("labels-counterparties", bucket,
                BankConfiguration.me().getCounterpartiesLabelsMapping());

        try (OutputTextFile output = new OutputTextFileImpl(new File("mappings.txt"))) {
            output.sort(true);
            output.serialize(skv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void extractLabelsProperties(String name, Bucket bucket, InvertedLabelProperties labels) {
        Set<String> keys = labels.keySet();

        System.out.println("[extractLabelsProperties] keys  = " + keys.size());
        Bucket detBucket = bucket.newBucket(name);

        for (String key : keys) {
            detBucket.newEntry(key, labels.map(key).getName());
        }
    }

    private void extractProperties(String name, Bucket bucket, InvertedCategoryProperties details) {
        Set<String> keys = details.keySet();

        Bucket detBucket = bucket.newBucket(name);

        for (String key : keys) {
            detBucket.newEntry(key, details.map(key).getName());
        }
    }
}
