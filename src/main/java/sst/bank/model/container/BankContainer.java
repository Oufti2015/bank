package sst.bank.model.container;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import sst.bank.model.BankSummary;
import sst.bank.model.Category;
import sst.bank.model.Operation;

public class BankContainer {

    private static BankContainer me = null;
    static {
	me = new BankContainer();
    }

    private BankContainer() {
    }

    public static BankContainer me() {
	return me;
    }

    @Getter
    @Setter
    private Integer lastId = 0;

    public Integer newId() {
	return lastId++;
    }

    private List<Operation> operations = new ArrayList<>();

    public List<Operation> operations() {
	return operations;
    }

    public void addOperations(List<Operation> list) {
	operations.addAll(list);
    }

    private List<BankSummary> operationsByMonth = new ArrayList<>();

    public List<BankSummary> operationsByMonth() {
	return operationsByMonth;
    }

    public void addMonth(BankSummary summary) {
	operationsByMonth.add(summary);
    }

    private List<BankSummary> operationsByYear = new ArrayList<>();

    public List<BankSummary> operationsByYear() {
	return operationsByYear;
    }

    public void addYear(BankSummary summary) {
	operationsByYear.add(summary);
    }

    private List<BankSummary> operationsByCategory = new ArrayList<>();

    public List<BankSummary> operationsByCategory() {
	return operationsByCategory.stream().sorted().collect(Collectors.toList());
    }

    public void addOperationsByCategory(BankSummary summary) {
	operationsByCategory.add(summary);
    }

    public BankSummary getBankSummaryByCategory(Category category) {
	Optional<BankSummary> result = operationsByCategory
		.stream()
		.filter(o -> o.getCategory().equals(category))
		.findFirst();
	if (result.isPresent()) {
	    return result.get();
	}
	return null;
    }

    private List<Category> categories = new ArrayList<>();
    private HashMap<String, Category> categoryByName = new HashMap<>();

    public void setCategories(List<Category> categories) {
	this.categories = categories;
	categories.stream().forEach(c -> categoryByName.put(c.getName(), c));
    }

    public Category category(String categoryName) {
	return categoryByName.get(categoryName);
    }

    public BankSummary yearlySummary(Year year, LocalDate endDate) {
	List<Operation> result = operations.stream()
		.filter(o -> year.equals(Year.from(o.getValueDate())))
		.filter(o -> endDate.compareTo(o.getValueDate()) >= 0)
		.collect(Collectors.toList());

	return new BankSummary(result);
    }

    public Collection<Category> getCategories() {
	return categories;
    }

    private List<Operation> visaOperations = new ArrayList<>();

    public void addVISAOperations(Operation operation) {
	visaOperations.add(operation);
    }

    public void addOperation(Operation operation) {
	operations.add(operation);
    }
}
