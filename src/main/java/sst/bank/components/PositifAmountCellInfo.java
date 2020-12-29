package sst.bank.components;

import sst.bank.config.BankUtils;
import sst.common.html.table.builders.CellInfo;

import java.math.BigDecimal;

public class PositifAmountCellInfo extends CellInfo {

    public PositifAmountCellInfo(BigDecimal amount) {
        super(BankUtils.format(amount));
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            this.setStyle("amountpositif");
        } else {
            this.setStyle("amountnegatif");
        }
    }
}
