package sst.bank.model.container;

import java.util.List;

import sst.bank.model.Project;

public interface ProjectsContainerInterface {

    public List<Project> projects();

    public void add(Project operation);

    public void addAll(List<Project> list);
}
