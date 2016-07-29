package sst.bank.activities.e.printing;

import java.util.List;

import sst.bank.config.BankUtils;
import sst.bank.model.Operation;
import sst.common.html.HTML;
import sst.common.html.table.builders.CellInfo;
import sst.common.html.table.builders.IntoTableConverter;

class SumRowIntoTableConverter implements IntoTableConverter {

    private List<Operation> list;

    SumRowIntoTableConverter(List<Operation> list) {
	super();
	this.list = list;
    }

    @Override
    public CellInfo[] convert() {
	CellInfo[] cells = new CellInfo[4];
	cells[0] = new CellInfo("Total", "date", 4);
	double sum = list.stream().mapToDouble(o -> o.getAmount().doubleValue()).sum();
	cells[1] = new CellInfo(BankUtils.format(sum), "amount");
	cells[2] = new CellInfo("EUR", "currency");
	cells[3] = new CellInfo(HTML.EMPTY_STR, 2);
	return cells;
    }
}
