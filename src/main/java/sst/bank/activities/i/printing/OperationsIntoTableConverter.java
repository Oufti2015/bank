package sst.bank.activities.i.printing;

import lombok.Getter;
import sst.bank.components.AmountCellInfo;
import sst.bank.components.CategoryCellInfo;
import sst.bank.components.DateCellInfo;
import sst.bank.model.Category;
import sst.bank.model.Operation;
import sst.bank.model.OperationLabel;
import sst.common.html.table.builders.CellInfo;
import sst.common.html.table.builders.IntoTableConverter;

import java.util.List;

public class OperationsIntoTableConverter implements IntoTableConverter {
	@Getter
    private static final String[] headers = {"ID", "CATEGORY", "LABELS", "EXEC.DATE", "VALUE DATE", "AMOUNT", "CURR",
            "COUNTERPARTY", "DETAIL"};
    public static final String NBSP = "&nbsp;";

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
            cells[i++] = new CellInfo(NBSP);
        }
        List<OperationLabel> labels = operation.getLabels();
        if (labels.isEmpty()) {
            cells[i++] = new CellInfo(NBSP);
        } else {
            int j = 0;
            StringBuilder stringBuilder = new StringBuilder();
            for (OperationLabel label : labels) {
                j++;
                stringBuilder.append(label.getName());
                if (j < labels.size()) {
                    stringBuilder.append("<BR>");
                }
            }
            cells[i++] = new CellInfo(stringBuilder.toString());
        }
        cells[i++] = new DateCellInfo(operation.getExecutionDate());
        cells[i++] = new DateCellInfo(operation.getValueDate());
        cells[i++] = new AmountCellInfo(operation.getAmount());
        cells[i++] = new CellInfo(operation.getCurrency(), "currency");
        cells[i++] = new CellInfo(operation.getCounterparty() != null ? operation.getCounterparty() : NBSP,
                "counterparty");
        cells[i] = new CellInfo(operation.getDetail(), "details");
        return cells;
    }

}
