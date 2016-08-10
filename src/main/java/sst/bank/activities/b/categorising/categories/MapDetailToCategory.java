package sst.bank.activities.b.categorising.categories;

import sst.bank.config.BankConfiguration;
import sst.bank.config.InvertedProperties;
import sst.bank.model.container.BankContainer;
import sst.bank.model.container.BankContainer.CategoryName;

public class MapDetailToCategory implements CategoryActivity {

    private static InvertedProperties details = BankConfiguration.me().getDetailsMapping();

    @Override
    public void process() {
	for (String key : details.keySet()) {
	    BankContainer.me().operations().stream()
		    .filter(o -> BankContainer.me().category(CategoryName.UNKNOWN).equals(o.getCategory()))
		    .filter(o -> o.getDetail().contains(key))
		    .forEach(o -> o.setCategory(BankContainer.me().category(details.map(key))));
	}
    }

}
