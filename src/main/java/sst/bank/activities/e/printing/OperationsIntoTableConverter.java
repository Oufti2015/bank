package sst.bank.activities.e.printing;

import sst.bank.components.AmountCellInfo;
import sst.bank.components.DateCellInfo;
import sst.bank.model.Category;
import sst.bank.model.Operation;
import sst.bank.model.container.BankContainer;
import sst.bank.model.container.BankContainer.CategoryName;
import sst.common.html.table.builders.CellInfo;
import sst.common.html.table.builders.IntoTableConverter;

class OperationsIntoTableConverter implements IntoTableConverter {
    static final String[] headers = { "ID", "CATEGORY", "EXEC.DATE", "VALUE DATE", "AMOUNT", "CURR",
	    "COUNTERPARTY", "DETAIL" };

    private Operation operation = null;

    OperationsIntoTableConverter(Operation operation) {
	super();
	this.operation = operation;
    }

    @Override
    public CellInfo[] convert() {
	CellInfo[] cells = new CellInfo[headers.length];
	int i = 0;
	cells[i++] = new CellInfo(operation.getId(), "id");
	Category cat = operation.getCategory();
	if (!BankContainer.me().category(CategoryName.UNKNOWN).equals(cat)) {
	    cells[i++] = new CellInfo(cat.getLabel(), cat.getStyle());
	} else {
	    cells[i++] = new CellInfo("&nbsp;");
	}
	cells[i++] = new DateCellInfo(operation.getExecutionDate());
	cells[i++] = new DateCellInfo(operation.getValueDate());
	cells[i++] = new AmountCellInfo(operation.getAmount());
	cells[i++] = new CellInfo(operation.getCurrency(), "currency");
	cells[i++] = new CellInfo(operation.getCounterparty(), "counterparty");
	cells[i++] = new CellInfo(operation.getDetail(), "detail");
	return cells;
    }

}
