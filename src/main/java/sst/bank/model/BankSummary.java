package sst.bank.model;

import lombok.Getter;
import lombok.Setter;
import sst.bank.model.Operation.OperationType;
import sst.bank.model.container.BankContainer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.*;

public class BankSummary implements Comparable<BankSummary> {
    @Getter
    @Setter
    private LocalDate startDate;
    @Getter
    @Setter
    private LocalDate endDate;
    @Getter
    @Setter
    private List<Operation> list = new ArrayList<>();

    @Override
    public String toString() {
	if (monthQuantity() == 1) {
	    return Month.from(startDate).getDisplayName(TextStyle.FULL, Locale.FRENCH)
		    + " "
		    + Year.from(startDate);
	} else if (monthQuantity() == 12) {
	    return Year.from(startDate).toString();
	} else {
	    return "De "
		    + Month.from(startDate).getDisplayName(TextStyle.FULL, Locale.FRENCH)
		    + " "
		    + Year.from(startDate)
		    + " \u00e0 "
		    + Month.from(endDate).getDisplayName(TextStyle.FULL, Locale.FRENCH)
		    + " "
		    + Year.from(endDate);
	}
    }

    public class Summary {
	public Category category;
	public BigDecimal amount;

	public Summary(Category category, BigDecimal amount) {
	    super();
	    this.category = category;
	    this.amount = amount;
	}
    }

    @Getter
    @Setter
    private Map<Category, Summary> summary = new HashMap<>();
    @Getter
    @Setter
    private Category category;

    public BankSummary(List<Operation> list) {
	super();
	this.list = list;
	BankContainer.me().getCategories().stream().forEach(c -> summary.put(c, new Summary(c, BigDecimal.ZERO)));
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
	if (category.isVisa() && operationsContainVISA()) {
	    return;
	}
	Summary sum = summary.get(category);
	if (null == sum) {
	    sum = new Summary(category, BigDecimal.ZERO);
	    summary.put(category, sum);
	}
	sum.amount = sum.amount.add(amount);
    }

    private boolean operationsContainVISA() {
	long op = list.stream().filter(o -> o.getOperationType().equals(OperationType.VISA)).count();
	return op > 0;
    }

    private void computeSummary() {
	list.stream()
		.filter(o -> null != o.getCategory())
		.forEach(o -> summary(o.getCategory(), o.getAmount()));
    }

    public int monthQuantity() {
	return (int) list.stream().map(o -> Year.from(o.getValueDate()) + "/" + Month.from(o.getValueDate())).distinct()
		.count();
    }

    public int operationsCount() {
	return list.size();
    }

    @Override
    public int compareTo(BankSummary bankSummary) {
	int result = getStartDate().compareTo(bankSummary.getStartDate());
	if (result == 0 && getCategory() != null) {
	    result = getCategory().compareTo(bankSummary.getCategory());
	}
	return result;
    }

    public String getId() {
	return Month.from(getStartDate()) + " " + Year.from(getStartDate());
    }
}
