package sst.bank.activities.d.categorising.categories;

import java.math.BigDecimal;

import sst.bank.config.BankConfiguration;
import sst.bank.config.InvertedProperties;
import sst.bank.model.container.BankContainer;

public class SalaireCategory implements CategoryActivity {

    private static InvertedProperties counterparties = null;

    @Override
    public void run() {
	counterparties = BankConfiguration.me().getPositifCounterpartiesMapping();
	BankContainer.me().operations().stream()
		.filter(o -> o.getCategory().isDefaultCategory())
		.filter(o -> o.getAmount().compareTo(BigDecimal.ZERO) > 0)
		.filter(o -> counterparties.map(o.getCounterparty()) != null)
		.forEach(o -> o.setCategory(counterparties.map(o.getCounterparty())));
    }
}