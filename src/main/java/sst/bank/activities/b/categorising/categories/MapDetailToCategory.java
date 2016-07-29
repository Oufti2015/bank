package sst.bank.activities.b.categorising.categories;

import java.util.HashMap;

import sst.bank.model.container.BankContainer;
import sst.bank.model.container.BankContainer.CategoryName;

public class MapDetailToCategory implements CategoryActivity {

    private static HashMap<String, CategoryName> mapping = new HashMap<>();

    static {
	mapping.put("LAURENT MURIEL", CategoryName.DOG);
	mapping.put("CFL PETANG", CategoryName.MOVE);
    }

    @Override
    public void process() {
	for (String key : mapping.keySet()) {
	    BankContainer.me().operations().stream()
		    .filter(o -> BankContainer.me().category(CategoryName.UNKNOWN).equals(o.getCategory()))
		    .filter(o -> o.getDetail().contains(key))
		    // .forEach(o -> System.out.println(o.getId() + "/" +
		    // o.getDetail()));
		    .forEach(o -> o.setCategory(BankContainer.me().category(mapping.get(key))));
	}
    }

}
