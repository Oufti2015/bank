package sst.bank.activities.lifecycle;

import lombok.extern.log4j.Log4j;
import sst.bank.activities.ActivityPhase;
import sst.bank.activities.BankActivity;
import sst.bank.main.OuftiBank;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Log4j
public abstract class LifeCycle {

    public enum Phase {
        CONFIG("Configuration"),
        LOADING("Loading"),
        PARSING("Parsing"),
        CATEGORISING("Categorising"),
        LABELLING("Labelling"),
        PROJECTS("Projects"),
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

    public void run() {
        List<ActivityPhase> phases = createLifeCycle();
        phases.forEach(this::startPhase);
    }

    protected abstract List<ActivityPhase> createLifeCycle();

    private void startPhase(ActivityPhase phase) {
        log.info(OuftiBank.MESSAGE_LINE_STRING);
        log.info("| Phase " + phase.getPhase().name);
        log.info(OuftiBank.MESSAGE_LINE_STRING);
        phase.getActivities().forEach(this::startActivity);
    }

    private void startActivity(BankActivity activity) {
        log.info(OuftiBank.MESSAGE_LINE_STRING);
        log.info(">>>>> Starting " + activity.getClass().getSimpleName() + "...");
        Instant start = Instant.now();
        activity.run();
        Instant stop = Instant.now();
        log.info("<<<<< " + activity.getClass().getSimpleName() + " finished in "
                + ChronoUnit.MILLIS.between(start, stop) + " ms.");
    }
}
