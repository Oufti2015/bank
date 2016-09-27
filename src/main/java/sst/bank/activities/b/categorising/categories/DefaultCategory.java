package sst.bank.activities.b.categorising.categories;

import sst.bank.model.container.BankContainer;

public class DefaultCategory implements CategoryActivity {

    @Override
    public void process() {
	BankContainer.me().operations().stream()
		.forEach(o -> o.setCategory(BankContainer.me().category("DEFAULT")));
    }

}
