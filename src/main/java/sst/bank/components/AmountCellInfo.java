package sst.bank.components;

import sst.bank.config.BankUtils;
import sst.common.html.table.builders.CellInfo;

import java.math.BigDecimal;

public class AmountCellInfo extends CellInfo {

    public AmountCellInfo(BigDecimal amount) {
        super(BankUtils.format(amount), "amount");
    }

    public AmountCellInfo(double amount) {
        super(BankUtils.format(amount), "amount");
    }
}
