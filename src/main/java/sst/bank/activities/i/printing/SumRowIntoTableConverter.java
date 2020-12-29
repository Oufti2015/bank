package sst.bank.activities.i.printing;

import sst.bank.components.AmountCellInfo;
import sst.bank.model.Operation;
import sst.common.html.HTML;
import sst.common.html.table.builders.CellInfo;
import sst.common.html.table.builders.IntoTableConverter;

import java.util.List;

public class SumRowIntoTableConverter implements IntoTableConverter {

    private final List<Operation> list;

    public SumRowIntoTableConverter(List<Operation> list) {
        super();
        this.list = list;
    }

    @Override
    public CellInfo[] convert() {
        CellInfo[] cells = new CellInfo[4];
        cells[0] = new CellInfo("Total", "date", 4);
        double sum = list.stream().mapToDouble(o -> o.getAmount().doubleValue()).sum();
        cells[1] = new AmountCellInfo(sum);
        cells[2] = new CellInfo("EUR", "currency");
        cells[3] = new CellInfo(HTML.EMPTY_STR, 2);
        return cells;
    }
}
