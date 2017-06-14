package sst.bank.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = "operations")
public class Project implements Serializable {
    private String id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;

    private Set<Operation> operations = new TreeSet<>();
}