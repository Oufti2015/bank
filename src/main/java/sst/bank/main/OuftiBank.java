package sst.bank.main;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import lombok.extern.log4j.Log4j2;
import sst.bank.activities.LifeCycleInterface;
import sst.bank.events.CategoryChangeEvent;
import sst.bank.model.container.BankContainer;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Log4j2
public class OuftiBank {
	public static final String MESSAGE_LINE_STRING = "+----------------------------------------------------------------------------------------------+";
	public static final  EventBus eventBus = new EventBus();

    public static void main(String[] args) {
	Instant start = Instant.now();

	log.info(MESSAGE_LINE_STRING);
	log.info("|----O-U-F-T-I----B-A-N-K----------------------------------------------------------------------|");
	log.info(MESSAGE_LINE_STRING);

	OuftiBank bank = new OuftiBank();
	bank.run();
	Instant stop = Instant.now();

	log.info("Oufti Bank finished in "
		+ ChronoUnit.MILLIS.between(start, stop) + " ms.");
	log.info(MESSAGE_LINE_STRING);
    }

    public OuftiBank() {
	eventBus.register(this);
    }

    private void run() {
	LifeCycleInterface.runPrintLifeCyle();

	log.info("OPERATIONS    : " + BankContainer.me().operationsContainer().operations().size());
	log.info("MONTHS        : " + BankContainer.me().operationsByMonth().size());
	log.info("YEARS         : " + BankContainer.me().operationsByYear().size());
	log.info("CATEGORIES    : " + BankContainer.me().getCategories().size());
	log.info("LABELS        : " + BankContainer.me().getLabels().size());
	log.info("BENEFICIARIES : " + BankContainer.me().beneficiaries().size());
	log.info("PROJECTS      : " + BankContainer.me().projectsContainer().projects().size());
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
