package sst.bank.activities.d.categorising.categories;

import sst.bank.model.container.BankContainer;

public class DefaultCategory implements CategoryActivity {

    @Override
    public void run() {
	BankContainer.me().operations().stream()
		// .filter(o -> o.getCategory() == null)
		.forEach(o -> o.setCategory(BankContainer.me().category("DEFAULT")));
    }
}