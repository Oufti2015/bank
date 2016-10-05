package sst.bank.activities;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import sst.bank.activities.a.config.CategoriesLoader;
import sst.bank.activities.a.config.Configurator;
import sst.bank.activities.b.loading.OperationsLoader;
import sst.bank.activities.c.parsing.OperationFiller;
import sst.bank.activities.c.parsing.OperationsParser;
import sst.bank.activities.d.categorising.categories.DefaultCategory;
import sst.bank.activities.d.categorising.categories.MapCounterpartyToCategory;
import sst.bank.activities.d.categorising.categories.MapDetailToCategory;
import sst.bank.activities.d.categorising.categories.SalaireCategory;
import sst.bank.activities.d.categorising.categories.WithoutRuleCategory;
import sst.bank.activities.e.grouping.OperationsGrouper;
import sst.bank.activities.f.budgeting.OperationBudgeter;
import sst.bank.activities.g.printing.DefaultCategoriesPrinter;
import sst.bank.activities.g.printing.bycategory.OperationsPrinterByCategory;
import sst.bank.activities.g.printing.bydate.OperationsPrinterByDate;
import sst.bank.activities.h.saving.CategoriesSaver;
import sst.bank.activities.h.saving.OperationsSaver;

public class LifeCycle {
    private static Logger logger = Logger.getLogger(LifeCycle.class);

    public enum Phase {
	CONFIG("Configuration"),
	LOADING("Loading"),
	PARSING("Parsing"),
	CATEGORISING("Categorising"),
	GROUPING("Grouping"),
	BUDGETING("Budgeting"),
	PRINTING("Printing"),
	SAVING("Saving");

	String name;

	Phase(String name) {
	    this.name = name;
	}

	public String getName() {
	    return name;
	}
    }

    private static List<ActivityPhase> phases = Arrays.asList(
	    new ActivityPhase(Phase.CONFIG, new CategoriesLoader(), new Configurator()),
	    new ActivityPhase(Phase.LOADING, new OperationsLoader()),
	    new ActivityPhase(Phase.PARSING, new OperationsParser(), new OperationFiller()),
	    new ActivityPhase(Phase.CATEGORISING,
		    new DefaultCategory(),
		    new WithoutRuleCategory(),
		    new SalaireCategory(),
		    new MapCounterpartyToCategory(),
		    new MapDetailToCategory()),
	    new ActivityPhase(Phase.GROUPING, new OperationsGrouper()),
	    new ActivityPhase(Phase.BUDGETING, new OperationBudgeter()),
	    new ActivityPhase(Phase.PRINTING, new OperationsPrinterByDate(), new OperationsPrinterByCategory(),
		    new DefaultCategoriesPrinter()),
	    new ActivityPhase(Phase.SAVING, new OperationsSaver(), new CategoriesSaver()));

    public LifeCycle() {
    }

    public void run() {
	phases.stream().forEach(p -> startPhase(p));
    }

    private void startPhase(ActivityPhase phase) {
	logger.info("+----------------------------------------------------------------------------------------------+");
	logger.info("| Phase " + phase.getPhase().name);
	logger.info("+----------------------------------------------------------------------------------------------+");
	phase.getActivities().stream().forEach(a -> startActivity(a));
    }

    private void startActivity(BankActivity activity) {
	logger.info("+----------------------------------------------------------------------------------------------+");
	logger.info(">>>>> Starting " + activity.getClass().getSimpleName() + "...");
	Instant start = Instant.now();
	activity.run();
	Instant stop = Instant.now();
	logger.info("<<<<< " + activity.getClass().getSimpleName() + " finished in "
		+ ChronoUnit.MILLIS.between(start, stop) + " ms.");
    }
}
