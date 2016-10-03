package sst.bank.activities.c.categorising.categories;

import sst.bank.config.BankConfiguration;
import sst.bank.config.InvertedProperties;
import sst.bank.model.container.BankContainer;

public class WithoutRuleCategory implements CategoryActivity {

    private static InvertedProperties ids = BankConfiguration.me().getIdMapping();

    @Override
    public void process() {
	BankContainer.me().operations().stream()
		.filter(o -> ids.map(o.getFortisId()) != null)
		.forEach(o -> o.setCategory(ids.map(o.getFortisId())));
    }
}
