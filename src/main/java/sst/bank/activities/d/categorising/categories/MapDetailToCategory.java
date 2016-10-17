package sst.bank.activities.d.categorising.categories;

import sst.bank.config.BankConfiguration;
import sst.bank.config.InvertedProperties;
import sst.bank.model.Category;
import sst.bank.model.Operation;
import sst.bank.model.container.BankContainer;

public class MapDetailToCategory implements CategoryActivity {

    private static InvertedProperties details = null;

    @Override
    public void run() {
	details = BankConfiguration.me().getDetailsMapping();
	for (String key : details.keySet()) {
	    // System.out.println(" ------> Key=" + key + " - " +
	    // details.map(key));
	    BankContainer.me().operations().stream()
		    .filter(o -> o.getCategory().isDefaultCategory())
		    .filter(o -> o.getDetail().contains(key))
		    .forEach(o -> setCategory(o, key, details.map(key)));
	}
	// BankContainer.me().operations().stream()
	// .filter(o -> o.getCategory().isDefaultCategory())
	// .filter(o -> o.getDetail().contains("TOTAL 3007 RODANGE"))
	// .forEach(o -> System.out.println(o));
    }

    private void setCategory(Operation o, String key, Category map) {
	// System.out.println("Set " + map.getName() + " on " + o.getDetail());
	o.setCategory(map);
	if (o.getCounterparty() == null) {
	    o.setCounterparty(key);
	}
    }
}
