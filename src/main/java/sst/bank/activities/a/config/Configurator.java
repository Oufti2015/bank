package sst.bank.activities.a.config;

import sst.bank.activities.BankActivity;
import sst.bank.config.BankConfiguration;

public class Configurator implements BankActivity {

    @Override
    public void run() {
	BankConfiguration.me().init();
    }
}
