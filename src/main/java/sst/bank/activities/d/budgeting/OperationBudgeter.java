package sst.bank.activities.d.budgeting;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

import sst.bank.activities.Activity;
import sst.bank.config.BankUtils;
import sst.bank.model.Budget;
import sst.bank.model.Budget.BudgetFrequencyType;
import sst.bank.model.container.BankBudget;
import sst.bank.model.container.BankContainer.CategoryName;

public class OperationBudgeter implements Activity {

    @Override
    public void run() {
	Budget spendingBudget = BankBudget.me().get(CategoryName.EPARGNE);

	List<Budget> budgets = BankBudget.me().budgets();
	double yearlyAmount = budgets
		.stream()
		.filter(b -> BudgetFrequencyType.YEARLY.equals(b.getBudgetFrequencyType()))
		.mapToDouble(b -> b.monthlyAmount().doubleValue())
		.sum();

	spendingBudget.setControlledAmount(spendingBudget.monthlyAmount().subtract(BigDecimal.valueOf(yearlyAmount),
		MathContext.DECIMAL64));

	System.out.println("------------------------------------------------------------------------------");

	budgets.stream().forEach(b -> System.out.println(b.toString()));

	System.out.println("------------------------------------------------------------------------------");
	System.out.println(
		String.format("%-15s : %10s %10s %10s %10s", "Total Budget",
			BankUtils.format(budgets
				.stream()
				.mapToDouble(b -> b.monthlyAmount().doubleValue())
				.sum()),
			BankUtils.format(budgets
				.stream()
				.mapToDouble(b -> b.getControlledAmount().doubleValue())
				.sum()),
			BankUtils.format(budgets
				.stream()
				.mapToDouble(b -> b.yearlyAmount(12).doubleValue())
				.sum()),
			BankUtils.format(budgets
				.stream()
				.mapToDouble(b -> b.yearlyControlledAmount(12).doubleValue())
				.sum())));
	System.out.println("------------------------------------------------------------------------------");
    }
}
