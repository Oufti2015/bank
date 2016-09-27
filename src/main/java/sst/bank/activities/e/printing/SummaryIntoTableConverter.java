package sst.bank.activities.e.printing;

import java.math.BigDecimal;

import sst.bank.activities.e.printing.TotalAmountSummers.SummerType;
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

    private TotalAmountSummers summers;

    public SummaryIntoTableConverter(TotalAmountSummers summers, Category category, BigDecimal amount, Budget budget,
	    int monthQuantity) {
	super();
	this.category = category;
	this.amount = amount;
	this.budget = budget;
	this.monthQuantity = monthQuantity;
	this.summers = summers;
    }

    @Override
    public CellInfo[] convert() {
	CellInfo[] cells = new CellInfo[headers.length];
	cells[0] = new CellInfo(category.getLabel(), category.getStyle());
	cells[1] = new AmountCellInfo(amount);
	if (1 == monthQuantity) {
	    BigDecimal budgetMonthlyControlledAmount = budget.monthlyControlledAmount();
	    cells[2] = new AmountCellInfo(budgetMonthlyControlledAmount);
	    summers.add(SummerType.BUDGET, budgetMonthlyControlledAmount.doubleValue());
	    BigDecimal subtractDiff = amount.subtract(budgetMonthlyControlledAmount);
	    if (category.isNegatif()) {
		cells[3] = new PositifAmountCellInfo(subtractDiff);
	    } else {
		cells[3] = new NegatifAmountCellInfo(subtractDiff);
	    }
	    summers.add(SummerType.DIFF, subtractDiff.doubleValue());
	} else {
	    BigDecimal budgetYearlyControlledAmount = budget.yearlyControlledAmount(monthQuantity);
	    cells[2] = new AmountCellInfo(budgetYearlyControlledAmount);
	    summers.add(SummerType.BUDGET, budgetYearlyControlledAmount.doubleValue());
	    BigDecimal subtractDiff = amount.subtract(budgetYearlyControlledAmount);
	    cells[3] = new AmountCellInfo(subtractDiff);
	    summers.add(SummerType.DIFF, subtractDiff.doubleValue());
	}
	return cells;
    }
}
