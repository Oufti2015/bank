package sst.bank.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public abstract class AbstractInvertedProperties {
    protected static List<String> getCounterparties(Properties props, String category) {
        String counterpartyString = props.getProperty(category);
        return (counterpartyString != null) ? new ArrayList<>(Arrays.asList(counterpartyString.split(","))) : null;
    }
}
