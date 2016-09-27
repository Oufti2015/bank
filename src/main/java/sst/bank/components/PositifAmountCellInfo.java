package sst.bank.components;

import java.math.BigDecimal;

import sst.bank.config.BankUtils;
import sst.common.html.table.builders.CellInfo;

public class PositifAmountCellInfo extends CellInfo {

    public PositifAmountCellInfo(BigDecimal amount) {
	super(BankUtils.format(amount));
	if (amount.compareTo(BigDecimal.ZERO) < 0) {
	    this.setStyle("amountpositif");
	} else {
	    this.setStyle("amountnegatif");
	}
    }

    public PositifAmountCellInfo(double amount) {
	super(BankUtils.format(amount));
	if (amount < 0) {
	    this.setStyle("amountpositif");
	} else {
	    this.setStyle("amountnegatif");
	}
    }
}
