package sst.bank.components;

import sst.common.html.table.builders.CellInfo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateCellInfo extends CellInfo {

    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public DateCellInfo(LocalDate date) {
        super(date.format(fmt), "Date");
    }
}
