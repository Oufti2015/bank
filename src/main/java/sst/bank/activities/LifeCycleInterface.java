package sst.bank.activities;

import lombok.extern.log4j.Log4j;
import sst.bank.activities.lifecycle.CompleteLifeCycle;
import sst.bank.activities.lifecycle.LifeCycle;
import sst.bank.activities.lifecycle.ReadOnlyLifeCycle;

@Log4j
public class LifeCycleInterface {

    public static void runCompleteLifeCyle() {
	log.info("Starting Complete LifeCycle...");
	LifeCycle lifecycle = new CompleteLifeCycle();
	lifecycle.run();
	log.info("...finishing Complete LifeCycle...");
    }

    public static void runReadOnlyLifeCyle() {
	log.info("Starting Read Only LifeCycle...");
	LifeCycle lifecycle = new ReadOnlyLifeCycle();
	lifecycle.run();
	log.info("...finishing Read Only LifeCycle...");
    }
}
