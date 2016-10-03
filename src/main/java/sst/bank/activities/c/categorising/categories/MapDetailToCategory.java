package sst.bank.activities.c.categorising.categories;

import sst.bank.config.BankConfiguration;
import sst.bank.config.InvertedProperties;
import sst.bank.model.container.BankContainer;

public class MapDetailToCategory implements CategoryActivity {

    private static InvertedProperties details = BankConfiguration.me().getDetailsMapping();

    @Override
    public void process() {
	for (String key : details.keySet()) {
	    BankContainer.me().operations().stream()
		    .filter(o -> o.getCategory().isDefaultCategory())
		    .filter(o -> o.getDetail().contains(key))
		    .forEach(o -> o.setCategory(details.map(key)));
	}
    }

}
