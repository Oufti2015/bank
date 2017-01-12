package sst.bank.activities.lifecycle;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import lombok.extern.log4j.Log4j;
import sst.bank.activities.ActivityPhase;
import sst.bank.activities.BankActivity;

@Log4j
public abstract class LifeCycle {

    public enum Phase {
	CONFIG("Configuration"),
	LOADING("Loading"),
	PARSING("Parsing"),
	CATEGORISING("Categorising"),
	LABELLING("Labelling"),
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
	log.info("+----------------------------------------------------------------------------------------------+");
	log.info("| Phase " + phase.getPhase().name);
	log.info("+----------------------------------------------------------------------------------------------+");
	phase.getActivities().stream().forEach(a -> startActivity(a));
    }

    private void startActivity(BankActivity activity) {
	log.info("+----------------------------------------------------------------------------------------------+");
	log.info(">>>>> Starting " + activity.getClass().getSimpleName() + "...");
	Instant start = Instant.now();
	activity.run();
	Instant stop = Instant.now();
	log.info("<<<<< " + activity.getClass().getSimpleName() + " finished in "
		+ ChronoUnit.MILLIS.between(start, stop) + " ms.");
    }
}
