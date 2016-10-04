package sst.bank.activities.d.categorising;

import java.util.Arrays;
import java.util.List;

import sst.bank.activities.BankActivity;
import sst.bank.activities.d.categorising.categories.CategoryActivity;
import sst.bank.activities.d.categorising.categories.DefaultCategory;
import sst.bank.activities.d.categorising.categories.MapCounterpartyToCategory;
import sst.bank.activities.d.categorising.categories.MapDetailToCategory;
import sst.bank.activities.d.categorising.categories.SalaireCategory;
import sst.bank.activities.d.categorising.categories.WithoutRuleCategory;

public class OperationCategoriser implements BankActivity {

    private List<CategoryActivity> categories = Arrays.asList(
	    new DefaultCategory(),
	    new WithoutRuleCategory(),
	    new SalaireCategory(),
	    new MapCounterpartyToCategory(),
	    new MapDetailToCategory());

    @Override
    public void run() {
	categories.stream().forEach(c -> c.process());
    }
}
