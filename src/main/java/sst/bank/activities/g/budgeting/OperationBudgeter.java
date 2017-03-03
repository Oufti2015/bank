package sst.bank.activities.g.budgeting;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j;
import sst.bank.activities.BankActivity;
import sst.bank.config.BankUtils;
import sst.bank.main.OuftiBank;
import sst.bank.model.Budget;
import sst.bank.model.Budget.BudgetFrequencyType;
import sst.bank.model.Budget.BudgetType;
import sst.bank.model.Category;
import sst.bank.model.container.BankContainer;

@Log4j
public class OperationBudgeter implements BankActivity {

    @Override
    public void run() {
	Optional<Category> optcat = BankContainer.me().getCategories().stream()
		.filter(c -> c.getBudget().getBudgetType().equals(BudgetType.SAVING)).findFirst();
	Budget spendingBudget = null;
	if (optcat.isPresent()) {
	    spendingBudget = optcat.get().getBudget();
	} else {
	    log.fatal("Cannot found EPARGNE");
	    OuftiBank.eventBus.post(new Exception("Cannot found EPARGNE"));
	}

	Double averageSalary = averageSalary();
	log.info("Average salary = " + averageSalary);

	List<Budget> budgets = BankContainer.me().getCategories()
		.stream()
		.map(c -> c.getBudget())
		.collect(Collectors.toList());

	budgets.stream()
		.filter(b -> BudgetType.SALARY.equals(b.getBudgetType()))
		.forEach(b -> {
		    BigDecimal salaryBudget = BigDecimal.valueOf(averageSalary);
		    b.setAmount(salaryBudget);
		    b.setControlledAmount(salaryBudget);
		});

	BigDecimal spendingControlledAmountBudget = calculateSpendingControlledAmountBudget(budgets);
	BigDecimal subtractAmount = spendingBudget.monthlyAmount().subtract(spendingControlledAmountBudget,
		MathContext.DECIMAL64);
	spendingBudget.setControlledAmount(subtractAmount);

	setControlledAmountOnYearlyCategories(budgets);

	printDebugInfo(budgets);
    }

    private Double averageSalary() {
	OptionalDouble averageOption = BankContainer.me().operationsContainer().operations()
		.stream()
		.filter(o -> LocalDate.of(2016, Month.AUGUST, 15).compareTo(o.getExecutionDate()) < 0)
		.filter(o -> o.getCategory().getName().equals("SALAIRE"))
		.filter(o -> !Month.DECEMBER.equals(Month.from(o.getExecutionDate()))) // 13rd
		.filter(o -> !Month.FEBRUARY.equals(Month.from(o.getExecutionDate()))) // Bonus
		.mapToDouble(o -> o.getAmount().doubleValue())
		.average();
	return (averageOption.isPresent()) ? averageOption.getAsDouble() : 0.0;
    }

    private void printDebugInfo(List<Budget> budgets) {
	log.debug("------------------------------------------------------------------------------");

	budgets.stream().forEach(b -> log.debug(b.toString()));

	log.debug("------------------------------------------------------------------------------");
	log.debug(
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
	log.debug("------------------------------------------------------------------------------");
    }

    private BigDecimal calculateSpendingControlledAmountBudget(List<Budget> budgets) {
	double yearlyAmount = budgets
		.stream()
		.filter(b -> BudgetFrequencyType.YEARLY.equals(b.getBudgetFrequencyType()))
		.mapToDouble(b -> b.monthlyAmount().doubleValue())
		.sum();

	return BigDecimal.valueOf(yearlyAmount);
    }

    private void setControlledAmountOnYearlyCategories(List<Budget> budgets) {
	budgets
		.stream()
		.filter(b -> BudgetFrequencyType.YEARLY.equals(b.getBudgetFrequencyType()))
		.forEach(b -> b.setControlledAmount(b.monthlyAmount()));
    }
}
