package sst.bank.components;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import sst.common.html.table.builders.CellInfo;

public class DateCellInfo extends CellInfo {

    private static DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public DateCellInfo(LocalDate date) {
	super(date.format(fmt), "Date");
    }
}
