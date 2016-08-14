package sst.bank.activities.e.printing;

import java.math.BigDecimal;

import sst.bank.config.BankUtils;
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
	cells[1] = new CellInfo(BankUtils.format(amount), "amount");
	if (1 == monthQuantity) {
	    cells[2] = new CellInfo(BankUtils.format(budget.monthlyControlledAmount()), "amount");
	    cells[3] = new CellInfo(BankUtils.format(amount.subtract(budget.monthlyControlledAmount())), "amount");
	} else {
	    cells[2] = new CellInfo(BankUtils.format(budget.yearlyControlledAmount(monthQuantity)), "amount");
	    cells[3] = new CellInfo(BankUtils.format(amount.subtract(budget.yearlyControlledAmount(monthQuantity))),
		    "amount");
	}
	return cells;
    }
}
