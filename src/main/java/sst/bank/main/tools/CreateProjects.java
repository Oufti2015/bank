package sst.bank.main.tools;

import sst.bank.activities.LifeCycleInterface;
import sst.bank.activities.j.saving.OperationsSaver;
import sst.bank.model.Project;
import sst.bank.model.container.BankContainer;

import java.time.LocalDate;
import java.time.Month;

public class CreateProjects {

    public static void main(String[] args) {
        LifeCycleInterface.loadLifeCyle();

        createProject("Vacances 2017", LocalDate.of(2017, Month.AUGUST, 5), LocalDate.of(2017, Month.AUGUST, 21));
        createProject("Voyage à Amsterdam", LocalDate.of(2017, Month.JUNE, 23), LocalDate.of(2017, Month.JUNE, 25));
        createProject("Voyage à Montpellier", LocalDate.of(2017, Month.MARCH, 31), LocalDate.of(2017, Month.APRIL, 5));
        createProject("Voyage à Barcelone", LocalDate.of(2017, Month.JANUARY, 25),
                LocalDate.of(2017, Month.JANUARY, 30));
        createProject("Séminaire Ivona", LocalDate.of(2017, Month.JANUARY, 3), LocalDate.of(2017, Month.JANUARY, 4));
        createProject("Vacances 2016", LocalDate.of(2016, Month.AUGUST, 7), LocalDate.of(2016, Month.AUGUST, 21));

        new OperationsSaver().run();
    }

    private static Project createProject(String name, LocalDate startDate, LocalDate endDate) {
        Project project = new Project();
        project.setId(BankContainer.me().newId().toString());
        project.setName(name);
        project.setStartDate(startDate);
        project.setEndDate(endDate);

        BankContainer.me().projectsContainer().add(project);

        return project;
    }

}
