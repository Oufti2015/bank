package sst.bank.model.container;

import sst.bank.model.Project;

import java.util.ArrayList;
import java.util.List;

public class ProjectsContainer implements ProjectsContainerInterface {

    private static ProjectsContainerInterface me;

    static {
        me = new ProjectsContainer();
    }

    private ProjectsContainer() {
    }

    public static ProjectsContainerInterface me() {
        return me;
    }

    List<Project> projects = new ArrayList<>();

    @Override
    public List<Project> projects() {
        return projects;
    }

    @Override
    public void add(Project project) {
        projects.add(project);
    }

    @Override
    public void addAll(List<Project> list) {
        projects.addAll(list);
    }

}
