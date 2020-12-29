package sst.bank.model.container;

import sst.bank.model.Project;

import java.util.List;

public interface ProjectsContainerInterface {

    List<Project> projects();

    void add(Project operation);

    void addAll(List<Project> list);
}
