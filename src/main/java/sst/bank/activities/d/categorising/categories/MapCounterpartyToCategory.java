package sst.bank.activities.d.categorising.categories;

import sst.bank.config.BankConfiguration;
import sst.bank.config.InvertedProperties;
import sst.bank.model.container.BankContainer;

public class MapCounterpartyToCategory implements CategoryActivity {

    private static InvertedProperties counterparties = null;

    @Override
    public void run() {
	counterparties = BankConfiguration.me().getCounterpartiesMapping();
	BankContainer.me().operations().stream()
		.filter(o -> counterparties.map(o.getCounterparty()) != null)
		.forEach(o -> o.setCategory(counterparties.map(o.getCounterparty())));
    }
}
