package sst.bank.activities.b.categorising.categories;

import java.util.HashMap;
import java.util.Map;

import sst.bank.model.container.BankContainer;
import sst.bank.model.container.BankContainer.CategoryName;

public class WithoutRuleCategory implements CategoryActivity {

    private static Map<String, CategoryName> mapping = new HashMap<String, CategoryName>();

    static {
	mapping.put("2016-0281", CategoryName.DOG);
    }

    @Override
    public void process() {
	BankContainer.me().operations().stream()
		.filter(o -> mapping.get(o.getId()) != null)
		.forEach(o -> o.setCategory(BankContainer.me().category(mapping.get(o.getId()))));
    }
}
