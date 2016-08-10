package sst.bank.activities.b.categorising.categories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import sst.bank.config.BankConfiguration;
import sst.bank.model.container.BankContainer;
import sst.bank.model.container.BankContainer.CategoryName;

public class MapCounterpartyToCategory implements CategoryActivity {

    private static Map<String, CategoryName> mapping = new HashMap<String, CategoryName>();
    private static Multimap<CategoryName, String> myMultimap = ArrayListMultimap.create();

    static {
	for (CategoryName category : CategoryName.values()) {
	    List<String> counterparties = BankConfiguration.me().getCounterparties(category);
	    if (counterparties != null) {
		counterparties.stream().forEach(c -> mapping_put(c, category));
	    }
	}
    }

    @Override
    public void process() {
	// myMultimap.keySet().stream().forEach(c -> System.out.println("" + c +
	// "=" + myMultimap.get(c)));
	BankContainer.me().operations().stream()
		.filter(o -> mapping.get(o.getCounterparty()) != null)
		.forEach(o -> o.setCategory(BankContainer.me().category(mapping.get(o.getCounterparty()))));
    }

    private static void mapping_put(String counterparty, CategoryName category) {
	mapping.put(counterparty, category);
	myMultimap.put(category, counterparty);
    }
}
