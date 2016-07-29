package sst.bank.activities.b.categorising;

import java.util.Arrays;
import java.util.List;

import sst.bank.activities.Activity;
import sst.bank.activities.b.categorising.categories.CategoryActivity;
import sst.bank.activities.b.categorising.categories.MapCounterpartyToCategory;
import sst.bank.activities.b.categorising.categories.MapDetailToCategory;
import sst.bank.activities.b.categorising.categories.SalaireCategory;
import sst.bank.activities.b.categorising.categories.UnknownCategory;
import sst.bank.activities.b.categorising.categories.WithoutRuleCategory;

public class OperationCategoriser implements Activity {

    private List<CategoryActivity> categories = Arrays.asList(
	    new UnknownCategory(),
	    new WithoutRuleCategory(),
	    new SalaireCategory(),
	    new MapCounterpartyToCategory(),
	    new MapDetailToCategory());

    @Override
    public void run() {
	categories.stream().forEach(c -> c.process());
    }
}
