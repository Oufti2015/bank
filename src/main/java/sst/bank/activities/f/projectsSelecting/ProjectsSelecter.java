package sst.bank.activities.f.projectsSelecting;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import sst.bank.activities.BankActivity;
import sst.bank.model.Category;
import sst.bank.model.Operation;
import sst.bank.model.Project;
import sst.bank.model.container.BankContainer;

public class ProjectsSelecter implements BankActivity {

    @Override
    public void run() {
	List<Category> unselectedCategories = new ArrayList<>();
	unselectedCategories.add(BankContainer.me().category("BANK"));

	List<Project> projects = BankContainer.me().projectsContainer().projects();
	List<Operation> operations = BankContainer.me().operationsContainer().operations();

	for (Project project : projects) {
	    List<Operation> subSet = operations.stream()
		    .filter(o -> o.getValueDate().compareTo(project.getStartDate()) >= 0)
		    .filter(o -> o.getValueDate().compareTo(project.getEndDate()) <= 0)
		    .filter(o -> o.getCategory() == null || !unselectedCategories.contains(o.getCategory()))
		    .collect(Collectors.toList());
	    project.getOperations().addAll(subSet);

	    System.out.println("Project : " + project + " (" + subSet.size() + " operations.)");
	}
    }

}
