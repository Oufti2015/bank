package sst.bank.main;

import sst.bank.activities.LifeCycle;
import sst.bank.config.BankConfiguration;
import sst.bank.model.container.BankContainer;

public class OuftiBank { // NO_UCD (unused code)

    public static void main(String[] args) {
	OuftiBank bank = new OuftiBank();
	bank.init();
	bank.run();
    }

    private void init() {
	BankConfiguration.me().setInputFileName("data/COURANT.CSV");
	BankConfiguration.me().setOutputFileDir("output");
    }

    private void run() {
	LifeCycle lifecycle = new LifeCycle();
	lifecycle.run();

	System.out.println(BankContainer.me().operations().size() + " operations read.");
	System.out.println(BankContainer.me().operationsByMonth().size() + " months sections created.");

	// BankContainer.me().operations().stream().filter(o -> (o.getCategory()
	// != null))
	// .forEach(o -> System.out.println(o.toString()));
	// ;
	// BankContainer.me().operations().stream().forEach(o ->
	// System.out.println(o.toString()));
    }
}
