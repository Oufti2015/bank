package sst.bank.activities;

import java.util.Arrays;
import java.util.List;

import sst.bank.activities.a.parsing.OperationsParser;
import sst.bank.activities.b.categorising.OperationCategoriser;
import sst.bank.activities.c.grouping.OperationsGrouper;
import sst.bank.activities.d.budgeting.OperationBudgeter;
import sst.bank.activities.e.printing.OperationsPrinter;

public class LifeCycle {
    private static List<Activity> activities = Arrays.asList(
	    new OperationsParser(),
	    new OperationCategoriser(),
	    new OperationsGrouper(),
	    new OperationBudgeter(),
	    new OperationsPrinter());

    public LifeCycle() {
    }

    public void run() {
	activities.stream().forEach(a -> startActivity(a));
    }

    private void startActivity(Activity activity) {
	System.out.println("Starting " + activity.getClass().getSimpleName() + "...");
	activity.run();
	System.out.println(activity.getClass().getSimpleName() + " finished.");
    }
}
