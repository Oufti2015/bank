package sst.bank.activities.b.categorising.categories;

import java.math.BigDecimal;

import sst.bank.model.container.BankContainer;
import sst.bank.model.container.BankContainer.CategoryName;

public class SalaireCategory implements CategoryActivity {

    @Override
    public void process() {
	BankContainer.me().operations().stream()
		.filter(o -> (BankContainer.me().category(CategoryName.UNKNOWN).equals(o.getCategory())))
		.filter(o -> o.getAmount().compareTo(BigDecimal.ZERO) > 0)
		.filter(o -> o.getDetail().contains("LU100029197924208100"))
		.forEach(o -> o.setCategory(BankContainer.me().category(CategoryName.SALAIRE)));
    }
}
