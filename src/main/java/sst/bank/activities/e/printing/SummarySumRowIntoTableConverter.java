package sst.bank.activities.e.printing;

import java.text.DecimalFormat;

import sst.bank.model.BankSummary;
import sst.common.html.table.builders.CellInfo;
import sst.common.html.table.builders.IntoTableConverter;

class SummarySumRowIntoTableConverter implements IntoTableConverter {

    private BankSummary summary;

    SummarySumRowIntoTableConverter(BankSummary summary) {
	super();
	this.summary = summary;
    }

    @Override
    public CellInfo[] convert() {
	CellInfo[] cells = new CellInfo[2];
	cells[0] = new CellInfo("Total", "date");
	double sum = summary.getSummary().values().stream().mapToDouble(o -> o.doubleValue()).sum();
	DecimalFormat decimalFormat = new DecimalFormat("#.00");
	cells[1] = new CellInfo(decimalFormat.format(sum), "amount");
	return cells;
    }
}
