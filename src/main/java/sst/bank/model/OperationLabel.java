package sst.bank.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class OperationLabel implements Comparable<OperationLabel>, Serializable {
    private String id;
    private String name;

    @Override
    public int compareTo(OperationLabel label) {
	return id.compareTo(label.id);
    }
}
