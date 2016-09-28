package sst.bank.activities.b.categorising;

import java.util.Arrays;
import java.util.List;

import sst.bank.activities.BankActivity;
import sst.bank.activities.b.categorising.categories.CategoryActivity;
import sst.bank.activities.b.categorising.categories.MapCounterpartyToCategory;
import sst.bank.activities.b.categorising.categories.MapDetailToCategory;
import sst.bank.activities.b.categorising.categories.SalaireCategory;
import sst.bank.activities.b.categorising.categories.DefaultCategory;
import sst.bank.activities.b.categorising.categories.WithoutRuleCategory;

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
