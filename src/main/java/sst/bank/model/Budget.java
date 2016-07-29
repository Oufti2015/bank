package sst.bank.model;

import java.math.BigDecimal;
import java.math.MathContext;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import sst.bank.model.container.BankContainer.CategoryName;

@ToString
public class Budget {
    public enum BudgetFrequencyType {
	YEARLY, MONTHLY
    }

    public enum BudgetType {
	SAVING, SPENDING
    }

    @Getter
    @Setter
    private CategoryName category;
    @Getter
    @Setter
    private BigDecimal amount;
    @Getter
    @Setter
    private BudgetFrequencyType budgetFrequencyType;
    @Getter
    @Setter
    private BudgetType budgetType;

    public Budget(CategoryName category, BigDecimal amount, BudgetFrequencyType budgetFrequencyType) {
	super();
	this.category = category;
	this.amount = amount;
	this.budgetFrequencyType = budgetFrequencyType;
	this.budgetType = BudgetType.SPENDING;
    }

    public Budget(CategoryName category, BigDecimal amount, BudgetFrequencyType budgetFrequencyType,
	    BudgetType budgetType) {
	super();
	this.category = category;
	this.amount = amount;
	this.budgetFrequencyType = budgetFrequencyType;
	this.budgetType = budgetType;
    }

    public BigDecimal yearlyAmount() {
	return (BudgetFrequencyType.YEARLY.equals(budgetFrequencyType) ? amount
		: amount.multiply(BigDecimal.valueOf(12)));
    }

    public BigDecimal monthlyAmount() {
	return (BudgetFrequencyType.MONTHLY.equals(budgetFrequencyType) ? amount
		: amount.divide(BigDecimal.valueOf(12), MathContext.DECIMAL32));
    }
}
