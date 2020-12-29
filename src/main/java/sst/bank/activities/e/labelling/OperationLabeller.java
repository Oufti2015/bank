package sst.bank.activities.e.labelling;

import java.util.Arrays;
import java.util.List;

import lombok.extern.log4j.Log4j;
import sst.bank.activities.BankActivity;
import sst.bank.activities.e.labelling.labels.DefaultLabel;
import sst.bank.activities.e.labelling.labels.LabelActivity;
import sst.bank.activities.e.labelling.labels.MapCounterpartyToLabel;
import sst.bank.activities.e.labelling.labels.MapDetailToLabel;
import sst.bank.activities.e.labelling.labels.SalaireLabel;
import sst.bank.activities.e.labelling.labels.WithoutRuleLabel;

@Log4j
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
