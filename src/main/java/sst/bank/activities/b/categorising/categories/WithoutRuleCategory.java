package sst.bank.activities.b.categorising.categories;

import sst.bank.config.BankConfiguration;
import sst.bank.config.InvertedProperties;
import sst.bank.model.container.BankContainer;

public class WithoutRuleCategory implements CategoryActivity {

    private static InvertedProperties ids = BankConfiguration.me().getIdMapping();

    @Override
    public void process() {
	BankContainer.me().operations().stream()
		.filter(o -> ids.map(o.getId()) != null)
		.forEach(o -> o.setCategory(BankContainer.me().category(ids.map(o.getId()))));
    }
}
