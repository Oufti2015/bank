package sst.bank.events;

import lombok.Data;
import sst.bank.model.Project;

@Data
public class ProjectChangeEvent {
    private Project project;

    public ProjectChangeEvent(Project project) {
        super();
        this.project = project;
    }
}
