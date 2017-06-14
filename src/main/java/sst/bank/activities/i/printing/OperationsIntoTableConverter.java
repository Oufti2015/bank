package sst.bank.activities.i.printing;

import java.util.List;

import sst.bank.components.AmountCellInfo;
import sst.bank.components.CategoryCellInfo;
import sst.bank.components.DateCellInfo;
import sst.bank.model.Category;
import sst.bank.model.Operation;
import sst.bank.model.OperationLabel;
import sst.common.html.table.builders.CellInfo;
import sst.common.html.table.builders.IntoTableConverter;

public class OperationsIntoTableConverter implements IntoTableConverter {
    public static final String[] headers = { "ID", "CATEGORY", "LABELS", "EXEC.DATE", "VALUE DATE", "AMOUNT", "CURR",
	    "COUNTERPARTY", "DETAIL" };

    private Operation operation = null;

    public OperationsIntoTableConverter(Operation operation) {
	super();
	this.operation = operation;
    }

    @Override
    public CellInfo[] convert() {
	CellInfo[] cells = new CellInfo[headers.length];
	int i = 0;
	cells[i++] = new CellInfo(
		(operation.getFortisId() != null) ? operation.getFortisId() : operation.getBankId().toString(), "id");
	Category cat = operation.getCategory();
	if (!cat.isDefaultCategory()) {
	    cells[i++] = new CategoryCellInfo(cat);
	} else {
	    cells[i++] = new CellInfo("&nbsp;");
	}
	List<OperationLabel> labels = operation.getLabels();
	if (labels.isEmpty()) {
	    cells[i++] = new CellInfo("&nbsp;");
	} else {
	    String cell = "";
	    int j = 0;
	    for (OperationLabel label : labels) {
		j++;
		cell += label.getName();
		if (j < labels.size()) {
		    cell += "<BR>";
		}
	    }
	    cells[i++] = new CellInfo(cell);
	}
	cells[i++] = new DateCellInfo(operation.getExecutionDate());
	cells[i++] = new DateCellInfo(operation.getValueDate());
	cells[i++] = new AmountCellInfo(operation.getAmount());
	cells[i++] = new CellInfo(operation.getCurrency(), "currency");
	cells[i++] = new CellInfo(operation.getCounterparty() != null ? operation.getCounterparty() : "&nbsp;",
		"counterparty");
	cells[i++] = new CellInfo(operation.getDetail(), "details");
	return cells;
    }

}
