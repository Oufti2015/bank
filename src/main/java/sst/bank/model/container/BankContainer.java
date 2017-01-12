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
import lombok.extern.log4j.Log4j;
import sst.bank.model.BankSummary;
import sst.bank.model.Beneficiary;
import sst.bank.model.Category;
import sst.bank.model.Operation;
import sst.bank.model.OperationLabel;

@Log4j
public class BankContainer {

    private static final String CACHE_CATEGORIES_BY_NAME = "CategoriesByName";
    private static BankContainer me = null;
    static {
	me = new BankContainer(true);
    }

    private BankContainer(boolean usingCache) {
    }

    public static BankContainer me() {
	return me;
    }

    @Getter
    @Setter
    private Integer lastId = 0;
    @Getter
    @Setter
    private String creationDate;
    private List<Operation> operations = new ArrayList<>();
    private List<BankSummary> operationsByMonth = new ArrayList<>();
    private List<BankSummary> operationsByYear = new ArrayList<>();
    private List<BankSummary> operationsByCategory = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();
    private List<OperationLabel> labels = new ArrayList<>();
    private HashMap<String, Category> categoryByName = new HashMap<>();
    private HashMap<String, OperationLabel> labelByName = new HashMap<>();
    private List<Operation> visaOperations = new ArrayList<>();
    private HashMap<String, Beneficiary> benefiaries = new HashMap<>();
    private HashMap<String, Beneficiary> benefiariesByCounterpartyDetails = new HashMap<>();

    public Integer newId() {
	return lastId++;
    }

    public List<Operation> operations() {
	// // 2. Get a cache called "cache1", declared in ehcache.xml
	// Cache cache = cacheManager.getCache("operationsCache");
	// Map<String, V>cache.getAll(cache.getKeys());
	return operations;
    }

    public void addOperations(List<Operation> list) {
	operations.addAll(list);
    }

    public List<BankSummary> operationsByMonth() {
	return operationsByMonth;
    }

    public void addMonth(BankSummary summary) {
	operationsByMonth.add(summary);
    }

    public List<BankSummary> operationsByYear() {
	return operationsByYear;
    }

    public void addYear(BankSummary summary) {
	operationsByYear.add(summary);
    }

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

    public void setCategories(List<Category> categories) {
	this.categories = categories;
	categories.stream().forEach(c -> categoryByName.put(c.getName(), c));
    }

    public void setLabels(List<OperationLabel> labels) {
	this.labels = labels;
	labels.stream().forEach(c -> labelByName.put(c.getName(), c));
    }

    public Category category(String categoryName) {
	return categoryByName.get(categoryName);
    }

    public OperationLabel label(String labelName) {
	return labelByName.get(labelName);
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

    public Collection<OperationLabel> getLabels() {
	return labels;
    }

    public void addVISAOperations(Operation operation) {
	visaOperations.add(operation);
    }

    public void addOperation(Operation operation) {
	operations.add(operation);
    }

    public void addAllBeneficiaries(Collection<Beneficiary> list) {
	list.stream().forEach(b -> addBeneficiary(b));
    }

    public Collection<Beneficiary> beneficiaries() {
	return benefiaries.values();
    }

    public void addBeneficiary(Beneficiary beneficiary) {
	if (checkDuplicate(beneficiary)) {
	    benefiaries.put(beneficiary.getId(), beneficiary);
	    beneficiary.getCounterparties()
		    .stream()
		    .forEach(s -> benefiariesByCounterpartyDetails.put(s, beneficiary));
	    beneficiary.getDetails()
		    .stream()
		    .forEach(s -> benefiariesByCounterpartyDetails.put(s, beneficiary));
	}
    }

    private boolean checkDuplicate(Beneficiary beneficiary) {
	boolean result = true;
	for (String cpty : beneficiary.getCounterparties()) {
	    if (benefiariesByCounterpartyDetails.get(cpty) != null) {
		log.error("Beneficiary <" + beneficiary + "> is duplicate on counterparty <" + cpty + ">");
		result = false;
	    }
	}
	for (String detail : beneficiary.getDetails()) {
	    if (benefiariesByCounterpartyDetails.get(detail) != null) {
		log.error("Beneficiary <" + beneficiary + "> is duplicate on detail <" + detail + ">");
		result = false;
	    }
	}
	return result;
    }

    public Beneficiary getBeneficiaryByCounterpartyDetails(String id) {
	return benefiariesByCounterpartyDetails.get(id);
    }

    public Beneficiary getBeneficiary(String id) {
	return benefiaries.get(id);
    }
}
