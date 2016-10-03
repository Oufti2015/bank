package sst.bank.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class Operation implements Comparable<Operation> {
    public enum OperationType {
	CASUAL, VISA
    };

    private OperationType operationType = OperationType.CASUAL;
    private Integer bankId;
    private String fortisId;
    private LocalDate executionDate;
    private LocalDate valueDate;
    private BigDecimal amount;
    private String currency;
    private String counterparty;
    private String detail;
    private String account;
    private Category category;

    public Operation() {
    }

    @Override
    public int compareTo(Operation o) {

	if (o.getFortisId() != null && getFortisId() != null) {
	    return getFortisId().compareTo(o.getFortisId());
	}
	int c = getExecutionDate().compareTo(o.getExecutionDate());
	if (c == 0) {
	    return getValueDate().compareTo(o.getValueDate());
	}
	return c;
    }

    @Override
    public boolean equals(Object obj) {
	Operation second = (Operation) obj;

	if (second.getFortisId() != null && getFortisId() != null) {
	    return second.getFortisId().equals(getFortisId());
	}
	return second.getAmount().equals(getAmount())
		&& second.getExecutionDate().equals(getExecutionDate())
		&& second.getValueDate().equals(getValueDate());
    }

    @Override
    public int hashCode() {
	final int PRIME = 59;
	int result = bankId;

	result = (result * PRIME) + (this.fortisId == null ? 43 : this.fortisId.hashCode());
	result = (result * PRIME) + (this.executionDate == null ? 43 : this.executionDate.hashCode());
	result = (result * PRIME) + (this.valueDate == null ? 43 : this.valueDate.hashCode());
	result = (result * PRIME) + (this.amount == null ? 43 : this.amount.hashCode());
	result = (result * PRIME) + (this.currency == null ? 43 : this.currency.hashCode());
	result = (result * PRIME) + (this.counterparty == null ? 43 : this.counterparty.hashCode());
	result = (result * PRIME) + (this.detail == null ? 43 : this.detail.hashCode());
	result = (result * PRIME) + (this.account == null ? 43 : this.account.hashCode());
	result = (result * PRIME) + (this.category == null ? 43 : this.category.hashCode());

	return result;
    }
}
