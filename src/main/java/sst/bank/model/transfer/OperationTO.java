package sst.bank.model.transfer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.Data;
import sst.bank.model.Category;
import sst.bank.model.Operation;
import sst.bank.model.Operation.OperationType;

@Data
public class OperationTO implements Serializable {
    private Integer bankId;
    private String fortisId;
    private String executionDate;
    private String valueDate;
    private BigDecimal amount;
    private String currency;
    private String counterparty;
    private String detail;
    private String account;
    private Category category;
    private OperationType operationType;

    public OperationTO() {
	super();
    }

    public Operation toOperation() {
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	Operation operation = new Operation();
	operation.setAccount(account);
	operation.setAmount(amount);
	operation.setBankId(bankId);
	operation.setCategory(category);
	operation.setCounterparty(counterparty);
	operation.setCurrency(currency);
	operation.setDetail(detail);
	operation.setExecutionDate(LocalDate.parse(executionDate, dtf));
	operation.setFortisId(fortisId);
	operation.setValueDate(LocalDate.parse(valueDate, dtf));
	if (operationType != null) {
	    operation.setOperationType(operationType);
	}

	return operation;
    }

    public static OperationTO fromOperation(Operation operation) {
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	OperationTO to = new OperationTO();
	to.account = operation.getAccount();
	to.amount = operation.getAmount();
	to.bankId = operation.getBankId();
	to.category = operation.getCategory();
	to.counterparty = operation.getCounterparty();
	to.currency = operation.getCurrency();
	to.detail = operation.getDetail();
	to.executionDate = dtf.format(operation.getExecutionDate());
	to.fortisId = operation.getFortisId();
	to.valueDate = dtf.format(operation.getValueDate());
	to.operationType = operation.getOperationType();

	return to;
    }
}
