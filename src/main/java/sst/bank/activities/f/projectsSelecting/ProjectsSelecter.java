package sst.bank.activities.f.projectsSelecting;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j;
import sst.bank.activities.BankActivity;
import sst.bank.model.Category;
import sst.bank.model.Operation;
import sst.bank.model.Project;
import sst.bank.model.container.BankContainer;

@Log4j
public class ProjectsSelecter implements BankActivity {
    List<Category> unselectedCategories = new ArrayList<>();

    @Override
    public void run() {
	createCategoriesList();

	List<Project> projects = BankContainer.me().projectsContainer().projects();
	List<Operation> operations = BankContainer.me().operationsContainer().operations();

	for (Project project : projects) {
	    project.getOperations().clear();
	    List<Operation> subSet = operations.stream()
		    .filter(o -> o.getValueDate().compareTo(project.getStartDate()) >= 0)
		    .filter(o -> o.getValueDate().compareTo(project.getEndDate()) <= 0)
		    .filter(o -> o.getCategory() == null || !unselectedCategories.contains(o.getCategory()))
		    .collect(Collectors.toList());
	    project.getOperations().addAll(subSet);

	    log.info("Project : " + project + " (" + subSet.size() + " operations.)");
	}
    }

    private void createCategoriesList() {
	if (unselectedCategories.isEmpty()) {
	    unselectedCategories.add(BankContainer.me().category("BANK"));
	    unselectedCategories.add(BankContainer.me().category("EPARGNE"));
	    unselectedCategories.add(BankContainer.me().category("HOUSE"));
	    unselectedCategories.add(BankContainer.me().category("ASSURANCES"));
	    unselectedCategories.add(BankContainer.me().category("SALAIRE"));
	    unselectedCategories.add(BankContainer.me().category("MOVE"));
	    unselectedCategories.add(BankContainer.me().category("SPORT"));
	    unselectedCategories.add(BankContainer.me().category("TAX"));
	}
    }

}