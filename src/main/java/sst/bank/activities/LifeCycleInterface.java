package sst.bank.activities;

import lombok.extern.log4j.Log4j2;
import sst.bank.activities.lifecycle.*;

@Log4j2
public class LifeCycleInterface {

    public static void runCompleteLifeCyle() {
	run(new CompleteLifeCycle());
    }

    public static void runReadOnlyLifeCyle() {
	run(new ReadOnlyLifeCycle());
    }

    public static void saveCategories() {
	run(new SaveCategoriesLifeCycle());
    }

    public static void saveBeneficiaries() {
	run(new SaveBeneficiariesLifeCycle());
    }

    public static void loadLifeCyle() {
	run(new LoadLifeCycle());
    }

    public static void runPrintLifeCyle() {
	run(new PrintLifeCycle());
    }

    private LifeCycleInterface() {
    }

    private static void run(LifeCycle lifecycle) {
	String lifeCycleName = lifecycle.getClass().getSimpleName();
	log.info("Starting " + lifeCycleName + "...");
	lifecycle.run();
	log.info("...finishing " + lifeCycleName + "...");
    }
}
