package sst.bank.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class OperationLabel implements Comparable<OperationLabel>, Serializable {
    private String id;
    private String name;

    @Override
    public int compareTo(OperationLabel label) {
        return id.compareTo(label.id);
    }
}
