package sst.bank.model;

import lombok.Data;

@Data
public class OperationLabel implements Comparable<OperationLabel> {
    private String id;
    private String name;

    @Override
    public int compareTo(OperationLabel label) {
	return id.compareTo(label.id);
    }
}
