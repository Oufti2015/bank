package sst.bank.config;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public abstract class BankUtils {

    private final static NumberFormat formatting = new DecimalFormat("0.00");

    public static String format(BigDecimal amount) {
	return format(amount.doubleValue());
    }

    public static String format(double amount) {
	return formatting.format(amount);
    }
}
