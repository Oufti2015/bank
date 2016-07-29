package sst.bank.config;

import lombok.Getter;
import lombok.Setter;

public class BankConfiguration {
    private static BankConfiguration me;
    static {
	me = new BankConfiguration();
    }

    private BankConfiguration() {
    }

    public static BankConfiguration me() {
	return me;
    }

    @Setter
    @Getter
    private String inputFileName;
    @Setter
    @Getter
    private String outputFileDir;
}
