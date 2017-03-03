package sst.bank.model.container;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j;
import sst.bank.model.BankSummary;
import sst.bank.model.Beneficiary;
import sst.bank.model.Category;
import sst.bank.model.Operation;
import sst.bank.model.OperationLabel;

@Log4j
public class BankContainer implements ContainerInterface {

    private static ContainerInterface me = null;
    static {
	me = new BankContainer(true);
    }

    private BankContainer(boolean usingCache) {
    }

    public static ContainerInterface me() {
	return me;
    }

    private Integer lastId = 0;
    private String creationDate;
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

    @Override
    public Integer newId() {
	return lastId++;
    }

    @Override
    public Integer lastId() {
	return lastId;
    }

    @Override
    public void initLastId(Integer id) {
	this.lastId = id;
    }

    @Override
    public void initCreationDate(String creationDate) {
	this.creationDate = creationDate;

    }

    @Override
    public List<BankSummary> operationsByMonth() {
	return operationsByMonth;
    }

    @Override
    public void addMonth(BankSummary summary) {
	operationsByMonth.add(summary);
    }

    @Override
    public List<BankSummary> operationsByYear() {
	return operationsByYear;
    }

    @Override
    public void addYear(BankSummary summary) {
	operationsByYear.add(summary);
    }

    @Override
    public void addOperationsByCategory(BankSummary summary) {
	operationsByCategory.add(summary);
    }

    @Override
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

    @Override
    public void setCategories(List<Category> categories) {
	this.categories = categories;
	categories.stream().forEach(c -> categoryByName.put(c.getName(), c));
    }

    @Override
    public void setLabels(List<OperationLabel> labels) {
	this.labels = labels;
	labels.stream().forEach(c -> labelByName.put(c.getName(), c));
    }

    @Override
    public Category category(String categoryName) {
	return categoryByName.get(categoryName);
    }

    @Override
    public OperationLabel label(String labelName) {
	return labelByName.get(labelName);
    }

    @Override
    public BankSummary yearlySummary(Year year, LocalDate endDate) {
	List<Operation> result = operationsContainer().operations().stream()
		.filter(o -> year.equals(Year.from(o.getValueDate())))
		.filter(o -> endDate.compareTo(o.getValueDate()) >= 0)
		.collect(Collectors.toList());

	return new BankSummary(result);
    }

    @Override
    public Collection<Category> getCategories() {
	return categories;
    }

    @Override
    public Collection<OperationLabel> getLabels() {
	return labels;
    }

    public void addVISAOperations(Operation operation) {
	visaOperations.add(operation);
    }

    @Override
    public void addAllBeneficiaries(Collection<Beneficiary> list) {
	list.stream().forEach(b -> addBeneficiary(b));
    }

    @Override
    public Collection<Beneficiary> beneficiaries() {
	return benefiaries.values();
    }

    @Override
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

    @Override
    public Beneficiary getBeneficiaryByCounterpartyDetails(String id) {
	return benefiariesByCounterpartyDetails.get(id);
    }

    @Override
    public Beneficiary getBeneficiary(String id) {
	return benefiaries.get(id);
    }

    @Override
    public String creationDate() {
	return creationDate;
    }

    @Override
    public List<BankSummary> operationsByCategory() {
	return operationsByCategory.stream().sorted().collect(Collectors.toList());
    }

    @Override
    public OperationsContainerInterface operationsContainer() {
	return OperationsContainer.me();
    }
}
