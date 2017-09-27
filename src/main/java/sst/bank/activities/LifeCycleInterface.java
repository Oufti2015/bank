package sst.bank.activities;

import lombok.extern.log4j.Log4j;
import sst.bank.activities.lifecycle.CompleteLifeCycle;
import sst.bank.activities.lifecycle.LifeCycle;
import sst.bank.activities.lifecycle.LoadLifeCycle;
import sst.bank.activities.lifecycle.PrintLifeCycle;
import sst.bank.activities.lifecycle.ReadOnlyLifeCycle;
import sst.bank.activities.lifecycle.SaveBeneficiariesLifeCycle;
import sst.bank.activities.lifecycle.SaveCategoriesLifeCycle;

@Log4j
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

    private static void run(LifeCycle lifecycle) {
	String lifeCycleName = lifecycle.getClass().getSimpleName();
	log.info("Starting " + lifeCycleName + "...");
	lifecycle.run();
	log.info("...finishing " + lifeCycleName + "...");
    }
}
