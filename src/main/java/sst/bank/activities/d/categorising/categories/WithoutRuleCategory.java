package sst.bank.activities.d.categorising.categories;

import sst.bank.config.BankConfiguration;
import sst.bank.config.InvertedCategoryProperties;
import sst.bank.model.container.BankContainer;

public class WithoutRuleCategory implements CategoryActivity {

    private static InvertedCategoryProperties ids = null;

    @Override
    public void run() {
	ids = BankConfiguration.me().getIdMapping();
	BankContainer.me().operationsContainer().operations().stream()
		.filter(o -> o.getFortisId() != null)
		.filter(o -> ids.mapCategory(o.getFortisId()) != null)
		.forEach(o -> o.setCategory(ids.mapCategory(o.getFortisId())));
    }
}
