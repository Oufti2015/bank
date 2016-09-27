package sst.bank.activities.e.printing;

import java.math.BigDecimal;

import sst.bank.components.AmountCellInfo;
import sst.bank.components.NegatifAmountCellInfo;
import sst.bank.components.PositifAmountCellInfo;
import sst.bank.model.Budget;
import sst.bank.model.Category;
import sst.common.html.table.builders.CellInfo;
import sst.common.html.table.builders.IntoTableConverter;

class SummaryIntoTableConverter implements IntoTableConverter {
    static final String[] headers = { "CATEGORY", "AMOUNT", "BUDGET", "DIFF." };

    private Category category;
    private BigDecimal amount;
    private Budget budget;
    private int monthQuantity;

    public SummaryIntoTableConverter(Category category, BigDecimal amount, Budget budget, int monthQuantity) {
	super();
	this.category = category;
	this.amount = amount;
	this.budget = budget;
	this.monthQuantity = monthQuantity;
    }

    @Override
    public CellInfo[] convert() {
	CellInfo[] cells = new CellInfo[headers.length];
	cells[0] = new CellInfo(category.getLabel(), category.getStyle());
	cells[1] = new AmountCellInfo(amount);
	if (1 == monthQuantity) {
	    cells[2] = new AmountCellInfo(budget.monthlyControlledAmount());
	    if (category.isNegatif()) {
		cells[3] = new PositifAmountCellInfo(amount.subtract(budget.monthlyControlledAmount()));
	    } else {
		cells[3] = new NegatifAmountCellInfo(amount.subtract(budget.monthlyControlledAmount()));
	    }
	} else {
	    cells[2] = new AmountCellInfo(budget.yearlyControlledAmount(monthQuantity));
	    cells[3] = new AmountCellInfo(amount.subtract(budget.yearlyControlledAmount(monthQuantity)));
	}
	return cells;
    }
}
