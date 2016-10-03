package sst.bank.activities;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import sst.bank.activities.a.loading.OperationsLoader;
import sst.bank.activities.b.parsing.OperationFiller;
import sst.bank.activities.b.parsing.OperationsParser;
import sst.bank.activities.c.categorising.OperationCategoriser;
import sst.bank.activities.d.grouping.OperationsGrouper;
import sst.bank.activities.e.budgeting.OperationBudgeter;
import sst.bank.activities.f.printing.bycategory.OperationsPrinterByCategory;
import sst.bank.activities.f.printing.bydate.OperationsPrinterByDate;
import sst.bank.activities.g.saving.OperationsSaver;

public class LifeCycle {
    private static Logger logger = Logger.getLogger(LifeCycle.class);

    private static List<BankActivity> activities = Arrays.asList(
	    new OperationsLoader(),
	    new OperationsParser(),
	    new OperationFiller(),
	    new OperationCategoriser(),
	    new OperationsGrouper(),
	    new OperationBudgeter(),
	    new OperationsPrinterByDate(),
	    new OperationsPrinterByCategory(),
	    new OperationsSaver());

    public LifeCycle() {
    }

    public void run() {
	activities.stream().forEach(a -> startActivity(a));
    }

    private void startActivity(BankActivity activity) {
	logger.info(">>>>> Starting " + activity.getClass().getSimpleName() + "...");
	Instant start = Instant.now();
	activity.run();
	Instant stop = Instant.now();
	logger.info("<<<<< " + activity.getClass().getSimpleName() + " finished in "
		+ ChronoUnit.MILLIS.between(start, stop) + " ms.");
    }
}
