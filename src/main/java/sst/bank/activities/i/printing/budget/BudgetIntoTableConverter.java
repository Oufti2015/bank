package sst.bank.activities.i.printing.budget;

import sst.bank.components.AmountCellInfo;
import sst.bank.model.Budget;
import sst.bank.model.Category;
import sst.common.html.table.builders.CellInfo;
import sst.common.html.table.builders.IntoTableConverter;

public class BudgetIntoTableConverter implements IntoTableConverter {
    public static final String[] headers = {"CATEGORY", "AMOUNT", "AMOUNT", "AMOUNT", "AMOUNT", "TYPE",
            "TYPE"};

    private final Category category;

    public BudgetIntoTableConverter(Category category) {
        this.category = category;
    }

    @Override
    public CellInfo[] convert() {
        CellInfo[] cellInfos = new CellInfo[headers.length];

        int i = 0;
        cellInfos[i++] = new CellInfo(category.getLabel(), category.getStyle());
        cellInfos[i++] = new AmountCellInfo(category.getBudget().monthlyAmount());
        cellInfos[i++] = new AmountCellInfo(category.getBudget().monthlyControlledAmount());
        cellInfos[i++] = new AmountCellInfo(category.getBudget().yearlyAmount(Budget.MONTHS_COUNT));
        cellInfos[i++] = new AmountCellInfo(category.getBudget().yearlyControlledAmount(Budget.MONTHS_COUNT));
        cellInfos[i++] = new CellInfo(category.getBudget().getBudgetFrequencyType().name());
        cellInfos[i++] = new CellInfo(category.getBudget().getBudgetType().name());

        return cellInfos;
    }

}
