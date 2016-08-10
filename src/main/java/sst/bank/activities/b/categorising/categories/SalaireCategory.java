package sst.bank.activities.b.categorising.categories;

import java.math.BigDecimal;

import sst.bank.config.BankConfiguration;
import sst.bank.config.InvertedProperties;
import sst.bank.model.container.BankContainer;
import sst.bank.model.container.BankContainer.CategoryName;

public class SalaireCategory implements CategoryActivity {

    private static InvertedProperties counterparties = BankConfiguration.me().getPositifCounterpartiesMapping();

    @Override
    public void process() {
	BankContainer.me().operations().stream()
		.filter(o -> (BankContainer.me().category(CategoryName.UNKNOWN).equals(o.getCategory())))
		.filter(o -> o.getAmount().compareTo(BigDecimal.ZERO) > 0)
		.filter(o -> counterparties.map(o.getCounterparty()) != null)
		.forEach(o -> o.setCategory(BankContainer.me().category(counterparties.map(o.getCounterparty()))));
    }
}
