package sst.bank.model;

import lombok.Getter;
import lombok.Setter;
import sst.bank.config.BankUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;

public class Budget implements Serializable {
    public static final int MONTHS_COUNT = 12;

    public enum BudgetFrequencyType {
        YEARLY, MONTHLY
    }

    public enum BudgetType {
        SALARY, SAVING, SPENDING
    }

    @Getter
    @Setter
    private String category;
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

    public Budget() {
    }

    public Budget(String category, BigDecimal amount, BudgetFrequencyType budgetFrequencyType) {
        super();
        this.category = category;
        this.amount = amount;
        this.budgetFrequencyType = budgetFrequencyType;
        this.budgetType = BudgetType.SPENDING;
        this.controlledAmount = monthlyAmount();
    }

    public Budget(String category, BigDecimal amount, BudgetFrequencyType budgetFrequencyType,
                  BudgetType budgetType) {
        super();
        this.category = category;
        this.amount = amount;
        this.budgetFrequencyType = budgetFrequencyType;
        this.budgetType = budgetType;
        this.controlledAmount = monthlyAmount();
    }

    public BigDecimal yearlyAmount(int monthsCount) {
        return (BudgetFrequencyType.YEARLY.equals(budgetFrequencyType) ? amount
                : amount.multiply(BigDecimal.valueOf(monthsCount)));
    }

    public BigDecimal yearlyControlledAmount(int monthsCount) {
        return (BudgetFrequencyType.YEARLY.equals(budgetFrequencyType)
                ? controlledAmount.multiply(BigDecimal.valueOf(MONTHS_COUNT))
                : controlledAmount.multiply(BigDecimal.valueOf(monthsCount)));
    }

    public BigDecimal monthlyAmount() {
        return (BudgetFrequencyType.MONTHLY.equals(budgetFrequencyType) ? amount
                : amount.divide(BigDecimal.valueOf(MONTHS_COUNT), MathContext.DECIMAL32));
    }

    public BigDecimal monthlyControlledAmount() {
        return controlledAmount;
    }

    @Override
    public String toString() {
        return String.format("%-15s : %10s %10s %10s %10s %-7s %-8s",
                category,
                BankUtils.format(monthlyAmount()),
                BankUtils.format(monthlyControlledAmount()),
                BankUtils.format(yearlyAmount(MONTHS_COUNT)),
                BankUtils.format(yearlyControlledAmount(MONTHS_COUNT)),
                getBudgetFrequencyType(),
                getBudgetType());
    }
}
