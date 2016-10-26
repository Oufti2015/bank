package sst.bank.activities;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.apache.log4j.Logger;

public abstract class LifeCycle {
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

    private List<ActivityPhase> phases = null;

    public void run() {
	phases = createLifeCycle();
	phases.stream().forEach(p -> startPhase(p));
    }

    protected abstract List<ActivityPhase> createLifeCycle();

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
