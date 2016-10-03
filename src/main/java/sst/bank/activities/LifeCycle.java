package sst.bank.activities;

import java.util.Arrays;
import java.util.List;

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
	System.out.println("Starting " + activity.getClass().getSimpleName() + "...");
	activity.run();
	System.out.println(activity.getClass().getSimpleName() + " finished.");
    }
}
