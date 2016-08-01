package sst.bank.model;

import java.math.BigDecimal;
import java.math.MathContext;

import lombok.Getter;
import lombok.Setter;
import sst.bank.config.BankUtils;
import sst.bank.model.container.BankContainer.CategoryName;

public class Budget {
    public enum BudgetFrequencyType {
	YEARLY, MONTHLY
    }

    public enum BudgetType {
	SALARY, SAVING, SPENDING
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
    @Getter
    @Setter
    private BigDecimal controlledAmount;

    public Budget(CategoryName category, BigDecimal amount, BudgetFrequencyType budgetFrequencyType) {
	super();
	this.category = category;
	this.amount = amount;
	this.budgetFrequencyType = budgetFrequencyType;
	this.budgetType = BudgetType.SPENDING;
	this.controlledAmount = monthlyAmount();
    }

    public Budget(CategoryName category, BigDecimal amount, BudgetFrequencyType budgetFrequencyType,
	    BudgetType budgetType) {
	super();
	this.category = category;
	this.amount = amount;
	this.budgetFrequencyType = budgetFrequencyType;
	this.budgetType = budgetType;
	this.controlledAmount = monthlyAmount();
    }

    public BigDecimal yearlyAmount() {
	return (BudgetFrequencyType.YEARLY.equals(budgetFrequencyType) ? amount
		: amount.multiply(BigDecimal.valueOf(12)));
    }

    public BigDecimal yearlyControlledAmount() {
	return controlledAmount.multiply(BigDecimal.valueOf(12));
    }

    public BigDecimal monthlyAmount() {
	return (BudgetFrequencyType.MONTHLY.equals(budgetFrequencyType) ? amount
		: amount.divide(BigDecimal.valueOf(12), MathContext.DECIMAL32));
    }

    public BigDecimal monthlyControlledAmount() {
	return controlledAmount;
    }

    @Override
    public String toString() {
	return String.format("%-15s : %10s %10s %10s %10s %7s %8s",
		category,
		BankUtils.format(monthlyAmount()),
		BankUtils.format(monthlyControlledAmount()),
		BankUtils.format(yearlyAmount()),
		BankUtils.format(yearlyControlledAmount()),
		getBudgetFrequencyType(),
		getBudgetType());
    }
}
