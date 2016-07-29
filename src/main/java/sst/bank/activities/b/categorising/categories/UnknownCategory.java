package sst.bank.activities.b.categorising.categories;

import sst.bank.model.container.BankContainer;
import sst.bank.model.container.BankContainer.CategoryName;

public class UnknownCategory implements CategoryActivity {

    @Override
    public void process() {
	BankContainer.me().operations().stream()
		.forEach(o -> o.setCategory(BankContainer.me().category(CategoryName.UNKNOWN)));
    }

}
