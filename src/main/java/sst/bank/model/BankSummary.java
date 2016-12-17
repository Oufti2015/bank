package sst.bank.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import sst.bank.model.Operation.OperationType;
import sst.bank.model.container.BankContainer;

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
		    + " à "
		    + Month.from(endDate).getDisplayName(TextStyle.FULL, Locale.FRENCH)
		    + " "
		    + Year.from(endDate);
	}
    }

    @Getter
    @Setter
    private Map<Category, BigDecimal> summary = new HashMap<>();
    @Getter
    @Setter
    private Category category;

    public BankSummary(List<Operation> list) {
	super();
	this.list = list;
	BankContainer.me().getCategories().stream().forEach(c -> summary.put(c, BigDecimal.ZERO));
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
	BigDecimal sum = summary.get(category);
	if (null == sum) {
	    sum = BigDecimal.ZERO;
	}
	summary.put(category, sum.add(amount));
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
