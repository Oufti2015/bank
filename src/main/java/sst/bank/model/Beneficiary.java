package sst.bank.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Beneficiary implements Comparable<Beneficiary>, Serializable {
    private String id;
    private String name;
    private List<String> counterparties = new ArrayList<>();
    private List<String> details = new ArrayList<>();
    private List<String> phoneNumbers = new ArrayList<>();
    private String address;

    public Beneficiary() {
    }

    public Beneficiary(String id) {
	super();
	this.id = id;
	this.name = id;
    }

    public Beneficiary counterparty(String counterparty) {
	counterparties.add(counterparty);
	details.remove(counterparty);
	return this;
    }

    public Beneficiary detail(String detail) {
	counterparties.remove(detail);
	details.add(detail);
	return this;
    }

    @Override
    public String toString() {
	return name;
    }

    @Override
    public int compareTo(Beneficiary o) {
	return name.compareTo(o.getName());
    }
}
