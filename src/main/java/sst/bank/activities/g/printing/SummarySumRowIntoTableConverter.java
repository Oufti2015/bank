package sst.bank.activities.g.printing;

import sst.bank.activities.g.printing.TotalAmountSummers.SummerType;
import sst.bank.components.AmountCellInfo;
import sst.bank.model.BankSummary;
import sst.common.html.table.builders.CellInfo;
import sst.common.html.table.builders.IntoTableConverter;

public class SummarySumRowIntoTableConverter implements IntoTableConverter {

    private BankSummary summary;
    private TotalAmountSummers tas;

    public SummarySumRowIntoTableConverter(BankSummary summary, TotalAmountSummers tas) {
	super();
	this.summary = summary;
	this.tas = tas;
    }

    @Override
    public CellInfo[] convert() {
	CellInfo[] cells = new CellInfo[4];
	cells[0] = new CellInfo("Total", "date");
	double sum = summary.getSummary().values()
		.stream()
		.mapToDouble(o -> o.amount.doubleValue())
		.sum();
	cells[1] = new AmountCellInfo(sum);
	cells[2] = new AmountCellInfo(tas.get(SummerType.BUDGET));
	cells[3] = new AmountCellInfo(tas.get(SummerType.DIFF));
	return cells;
    }
}
