package sst.bank.main;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import lombok.extern.log4j.Log4j;
import sst.bank.activities.LifeCycleInterface;
import sst.bank.config.BankConfiguration;
import sst.bank.model.container.BankContainer;

@Log4j
public class OuftiBank { // NO_UCD (unused code)

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

    private void init() {
	BankConfiguration.me().setInputFileName("data/COURANT.CSV");
	BankConfiguration.me().setOutputFileDir("output");
    }

    private void run() {
	LifeCycleInterface.runCompleteLifeCyle();

	log.info("OPERATIONS : " + BankContainer.me().operations().size());
	log.info("MONTHS     : " + BankContainer.me().operationsByMonth().size());
	log.info("YEARS      : " + BankContainer.me().operationsByYear().size());
    }
}
