package sst.bank.activities.i.printing.budget;

import sst.bank.components.AmountCellInfo;
import sst.bank.model.Category;
import sst.common.html.table.builders.CellInfo;
import sst.common.html.table.builders.IntoTableConverter;

public class UtilisationIntoTableConverter implements IntoTableConverter {

    private final Category category;
    private final Double[] utilisations;

    public UtilisationIntoTableConverter(Category category, Double[] utilisations) {
        this.category = category;
        this.utilisations = utilisations;
    }

    @Override
    public CellInfo[] convert() {
        CellInfo[] cellInfo = new CellInfo[utilisations.length + 2];
        int i = 0;
        cellInfo[i++] = new CellInfo(category.getLabel(), category.getStyle());
        cellInfo[i++] = new AmountCellInfo(category.getBudget().yearlyControlledAmount(12));

        for (Double utilisation : utilisations) {
            cellInfo[i++] = new AmountCellInfo(utilisation);
        }
        return cellInfo;
    }
}
