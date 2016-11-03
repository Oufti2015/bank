package sst.bank.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Beneficiary {
    private String id;
    private String name;
    private List<String> counterparties = new ArrayList<>();
    private List<String> phoneNumbers = new ArrayList<>();
    private String address;

    public Beneficiary(String id) {
	super();
	this.id = id;
	this.name = id;
	this.counterparties.add(id);
    }
}
