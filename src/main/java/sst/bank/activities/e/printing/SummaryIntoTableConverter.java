package sst.bank.activities.e.printing;

import java.math.BigDecimal;

import sst.bank.config.BankUtils;
import sst.bank.model.Category;
import sst.common.html.table.builders.CellInfo;
import sst.common.html.table.builders.IntoTableConverter;

class SummaryIntoTableConverter implements IntoTableConverter {
    static final String[] headers = { "CATEGORY", "AMOUNT" };

    private Category category;
    private BigDecimal amount;

    SummaryIntoTableConverter(Category category, BigDecimal amount) {
	super();
	this.category = category;
	this.amount = amount;
    }

    @Override
    public CellInfo[] convert() {
	CellInfo[] cells = new CellInfo[2];
	cells[0] = new CellInfo(category.getLabel(), category.getStyle());
	cells[1] = new CellInfo(BankUtils.format(amount), "amount");
	return cells;
    }
}
