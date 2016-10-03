package sst.bank.activities.c.categorising.categories;

import sst.bank.config.BankConfiguration;
import sst.bank.config.InvertedProperties;
import sst.bank.model.container.BankContainer;

public class MapCounterpartyToCategory implements CategoryActivity {

    private static InvertedProperties counterparties = BankConfiguration.me().getCounterpartiesMapping();

    @Override
    public void process() {
	BankContainer.me().operations().stream()
		.filter(o -> counterparties.map(o.getCounterparty()) != null)
		.forEach(o -> o.setCategory(counterparties.map(o.getCounterparty())));
    }
}
