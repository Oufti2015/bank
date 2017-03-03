package sst.bank.main;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import lombok.extern.log4j.Log4j;
import sst.bank.activities.LifeCycleInterface;
import sst.bank.events.CategoryChangeEvent;
import sst.bank.model.container.BankContainer;

@Log4j
public class OuftiBank { // NO_UCD (unused code)
    public static EventBus eventBus = new EventBus();

    public static void main(String[] args) {
	Instant start = Instant.now();

	log.info("+----------------------------------------------------------------------------------------------+");
	log.info("|----O-U-F-T-I----B-A-N-K----------------------------------------------------------------------|");
	log.info("+----------------------------------------------------------------------------------------------+");

	OuftiBank bank = new OuftiBank();
	bank.init();
	bank.run();
	Instant stop = Instant.now();

	log.info("Oufti Bank finished in "
		+ ChronoUnit.MILLIS.between(start, stop) + " ms.");
	log.info("+----------------------------------------------------------------------------------------------+");
    }

    public OuftiBank() {
	eventBus.register(this);
    }

    private void init() {
	// eventBus.post(new Exception("Test"));
    }

    private void run() {
	LifeCycleInterface.runCompleteLifeCyle();

	log.info("OPERATIONS    : " + BankContainer.me().operationsContainer().operations().size());
	log.info("MONTHS        : " + BankContainer.me().operationsByMonth().size());
	log.info("YEARS         : " + BankContainer.me().operationsByYear().size());
	log.info("CATEGORIES    : " + BankContainer.me().getCategories().size());
	log.info("LABELS        : " + BankContainer.me().getLabels().size());
	log.info("BENEFICIARIES : " + BankContainer.me().beneficiaries().size());
    }

    @Subscribe
    public void handleEvent(CategoryChangeEvent e) {
	log.info("" + e.getCategory() + " has changed.");
	LifeCycleInterface.saveCategories();
	LifeCycleInterface.runCompleteLifeCyle();
    }

    @Subscribe
    public void deadEvents(DeadEvent e) {
	log.info("Event " + e.getEvent() + " not subscribed...");
    }

    @Subscribe
    public void fatalError(Exception e) {
	log.fatal("FATAL error detected", e);
	System.exit(-1);
    }
}
