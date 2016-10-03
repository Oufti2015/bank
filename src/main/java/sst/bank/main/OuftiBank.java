package sst.bank.main;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.apache.log4j.Logger;

import sst.bank.activities.LifeCycle;
import sst.bank.config.BankConfiguration;
import sst.bank.model.container.BankContainer;

public class OuftiBank { // NO_UCD (unused code)
    private static Logger logger = Logger.getLogger(OuftiBank.class);

    public static void main(String[] args) {
	Instant start = Instant.now();

	logger.info("+----------------------------------------------------------------------------------------------+");
	logger.info("|----O-U-F-T-I----B-A-N-K----------------------------------------------------------------------|");
	logger.info("+----------------------------------------------------------------------------------------------+");

	OuftiBank bank = new OuftiBank();
	bank.init();
	bank.run();
	Instant stop = Instant.now();

	logger.info("Oufti Bank finished in "
		+ ChronoUnit.MILLIS.between(start, stop) + " ms.");
	logger.info("+----------------------------------------------------------------------------------------------+");
    }

    private void init() {
	BankConfiguration.me().setInputFileName("data/COURANT.CSV");
	BankConfiguration.me().setOutputFileDir("output");
    }

    private void run() {
	LifeCycle lifecycle = new LifeCycle();
	lifecycle.run();

	logger.info(BankContainer.me().operations().size() + " operations read.");
	logger.info(BankContainer.me().operationsByMonth().size() + " month(s) sections created.");
	logger.info(BankContainer.me().operationsByYear().size() + " year(s) sections created.");
    }
}
