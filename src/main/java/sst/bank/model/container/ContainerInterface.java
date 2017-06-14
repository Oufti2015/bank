package sst.bank.model.container;

import java.time.LocalDate;
import java.time.Year;
import java.util.Collection;
import java.util.List;

import sst.bank.model.BankSummary;
import sst.bank.model.Beneficiary;
import sst.bank.model.Category;
import sst.bank.model.OperationLabel;

public interface ContainerInterface {

    public Integer newId();

    public Integer lastId();

    public void initLastId(Integer id);

    public String creationDate();

    public void initCreationDate(String creationDate);

    /* Operation */

    public OperationsContainerInterface operationsContainer();

    /* Project */

    public ProjectsContainerInterface projectsContainer();

    /* Category */
    public Category category(String categoryName);

    public Collection<Category> getCategories();

    public void setCategories(List<Category> categories);

    /* Beneficiary */
    public Collection<Beneficiary> beneficiaries();

    public Beneficiary getBeneficiaryByCounterpartyDetails(String id);

    Beneficiary getBeneficiary(String id);

    public void addBeneficiary(Beneficiary beneficiary);

    public void addAllBeneficiaries(Collection<Beneficiary> list);

    /* BankSummary */
    public void addMonth(BankSummary summary);

    public void addYear(BankSummary summary);

    public void addOperationsByCategory(BankSummary summary);

    public BankSummary getBankSummaryByCategory(Category category);

    public List<BankSummary> operationsByCategory();

    public List<BankSummary> operationsByYear();

    public List<BankSummary> operationsByMonth();

    public BankSummary yearlySummary(Year year, LocalDate endDate);

    /* OperationLabel */

    public Collection<OperationLabel> getLabels();

    public OperationLabel label(String labelName);

    public void setLabels(List<OperationLabel> labels);
}
