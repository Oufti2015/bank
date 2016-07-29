package sst.bank.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import sst.bank.model.container.BankContainer;
import sst.bank.model.container.BankContainer.CategoryName;
import sst.common.file.parser.Parser;

@ToString(exclude = "dtf")
public class Operation implements Comparable<Operation> {
    @Getter
    private String id;
    @Getter
    @Setter
    private LocalDate executionDate;
    @Getter
    @Setter
    private LocalDate valueDate;
    @Getter
    @Setter
    private BigDecimal amount;
    @Getter
    private String currency;
    @Getter
    private String counterparty;
    @Getter
    private String detail;
    @Getter
    private String account;
    @Getter
    @Setter
    private Category category;

    public Operation() {
	BankContainer.me().category(CategoryName.UNKNOWN);
    }

    @Parser(position = 0)
    public void setId(String id) {
	this.id = id;
    }

    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Parser(position = 1)
    public void setExecutionDateString(String executionDate) {
	setExecutionDate(LocalDate.parse(executionDate, dtf));
    }

    @Parser(position = 2)
    public void setValueDateString(String valueDate) {
	setValueDate(LocalDate.parse(valueDate, dtf));
    }

    @Parser(position = 3)
    public void setAmountString(String amount) {
	setAmount(new BigDecimal(amount.replaceAll(",", ".")));
	// // Create a DecimalFormat that fits your requirements
	// DecimalFormatSymbols symbols = new DecimalFormatSymbols();
	// symbols.setGroupingSeparator('.');
	// symbols.setDecimalSeparator(',');
	// String pattern = "+#,##0.0#";
	// DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
	// decimalFormat.setParseBigDecimal(true);
	//
	// try {
	// setAmount((BigDecimal) decimalFormat.parse(amount));
	// } catch (ParseException e) {
	// e.printStackTrace();
	// setAmount(null);
	// }
    }

    @Parser(position = 4)
    public void setCurrency(String currency) {
	this.currency = currency;
    }

    @Parser(position = 5)
    public void setCounterparty(String counterparty) {
	this.counterparty = counterparty;
    }

    @Parser(position = 6)
    public void setDetail(String detail) {
	this.detail = detail;
    }

    @Parser(position = 7)
    public void setAccount(String account) {
	this.account = account;
    }

    @Override
    public int compareTo(Operation o) {
	return getId().compareTo(o.getId());
    }
}
