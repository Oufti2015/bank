package sst.bank.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import sst.bank.model.container.BankBudget;

public class BankSummary {
    @Getter
    @Setter
    private LocalDate startDate;
    @Getter
    @Setter
    private LocalDate endDate;
    @Getter
    @Setter
    private List<Operation> list = new ArrayList<>();
    @Getter
    @Setter
    private Map<Category, BigDecimal> summary = new HashMap<>();
    @Getter
    @Setter
    private BankBudget budget = BankBudget.me();

    public BankSummary(List<Operation> list) {
	super();
	this.list = list;
	init();
    }

    private void init() {
	computeStartDate();
	computeEndDate();
	this.computeSummary();
    }

    private void computeStartDate() {
	Optional<Operation> findFirst = list.stream().sorted().findFirst();
	if (findFirst.isPresent()) {
	    this.setStartDate(findFirst.get().getValueDate());
	}
    }

    private void computeEndDate() {
	Optional<Operation> findLast = list.stream().sorted(new Comparator<Operation>() {

	    @Override
	    public int compare(Operation o1, Operation o2) {
		return o2.getValueDate().compareTo(o1.getValueDate());
	    }
	}).findFirst();

	if (findLast.isPresent()) {
	    this.setEndDate(findLast.get().getValueDate());
	}
    }

    private void summary(Category category, BigDecimal amount) {
	BigDecimal sum = summary.get(category);
	if (null == sum) {
	    sum = BigDecimal.ZERO;
	}
	summary.put(category, sum.add(amount));
    }

    private void computeSummary() {
	list.stream().filter(o -> null != o.getCategory()).forEach(o -> summary(o.getCategory(), o.getAmount()));
    }
}
