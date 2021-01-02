package sst.bank.activities.d.categorising.categories;

import sst.bank.config.BankConfiguration;
import sst.bank.config.InvertedCategoryProperties;
import sst.bank.model.container.BankContainer;

import java.math.BigDecimal;

public class SalaireCategory implements CategoryActivity {

    private static InvertedCategoryProperties counterparties = null;

    @Override
    public void run() {
	counterparties = BankConfiguration.me().getPositifCounterpartiesMapping();
	BankContainer.me().operationsContainer().operations().stream()
		.filter(o -> o.getCategory().isDefaultCategory())
		.filter(o -> o.getAmount().compareTo(BigDecimal.ZERO) > 0)
		.filter(o -> counterparties.map(o.getCounterparty()) != null)
		.forEach(o -> o.setCategory(counterparties.map(o.getCounterparty())));
    }
}
