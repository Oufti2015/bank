package sst.bank.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import lombok.Data;

@Data
public class Project implements Serializable, Comparable<Project> {
    private String id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;

    private Set<Operation> operations = new TreeSet<>();

    @Override
    public int compareTo(Project o) {
	int i = o.startDate.compareTo(startDate);
	if (i == 0)
	    return id.compareTo(o.id);
	return i;
    }

    @Override
    public String toString() {
	return name + " (" + Month.from(startDate).getDisplayName(TextStyle.FULL, Locale.FRENCH) + " "
		+ Year.from(startDate) + ")";
    }
}