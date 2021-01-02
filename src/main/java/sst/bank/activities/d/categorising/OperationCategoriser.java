package sst.bank.activities.d.categorising;

import lombok.extern.log4j.Log4j2;
import sst.bank.activities.BankActivity;
import sst.bank.activities.d.categorising.categories.*;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class OperationCategoriser implements BankActivity {
    private List<CategoryActivity> categories = Arrays.asList(
	    new DefaultCategory(),
	    new WithoutRuleCategory(),
	    new SalaireCategory(),
	    new MapCounterpartyToCategory(),
	    new MapDetailToCategory());

    @Override
    public void run() {
	categories.stream().forEach(this::runCategoriser);
    }

    private void runCategoriser(CategoryActivity catAct) {
	log.info(catAct.getClass());
	catAct.run();
    }
}
