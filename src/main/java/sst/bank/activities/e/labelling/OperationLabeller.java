package sst.bank.activities.e.labelling;

import lombok.extern.log4j.Log4j2;
import sst.bank.activities.BankActivity;
import sst.bank.activities.e.labelling.labels.*;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class OperationLabeller implements BankActivity {
    private List<LabelActivity> categories = Arrays.asList(
	    new DefaultLabel(),
	    new WithoutRuleLabel(),
	    new SalaireLabel(),
	    new MapCounterpartyToLabel(),
	    new MapDetailToLabel());

    @Override
    public void run() {
	categories.stream().forEach(this::runCategoriser);
    }

    private void runCategoriser(LabelActivity catAct) {
	log.info(catAct.getClass());
	catAct.run();
    }
}
