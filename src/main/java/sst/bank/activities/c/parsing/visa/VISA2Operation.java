package sst.bank.activities.c.parsing.visa;

import lombok.Getter;
import lombok.Setter;
import sst.common.file.parser.Parser;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class VISA2Operation {
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
    private String detail;
    @Getter
    private String tauxDeChange;
    @Getter
    private String coutAssocie;

    public DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Parser(position = 0)
    public void setExecutionDateString(String executionDate) {
	setExecutionDate(LocalDate.parse(executionDate, dtf));
    }

    @Parser(position = 1)
    public void setValueDateString(String valueDate) {
	setValueDate(LocalDate.parse(valueDate, dtf));
    }

    public String getExecutioinDateString() {
	return dtf.format(executionDate);
    }

    public String getValueDateString() {
	return dtf.format(valueDate);
    }

    @Parser(position = 2)
    public void setAmountString(String amount) {
	String replaceAll = amount;
	if (amount.contains(",")) {
	    replaceAll = replaceAll.replaceAll("\\.", "").replaceAll(",", "\\.");
	}
	setAmount(new BigDecimal(replaceAll));
    }

    @Parser(position = 3)
    public void setCurrency(String currency) {
	this.currency = currency;
    }

    @Parser(position = 4)
    public void setDetail(String detail) {
	this.detail = detail;
    }

    @Parser(position = 5)
    public void setTauxDeChange(String tauxDeChange) {
	this.tauxDeChange = tauxDeChange;
    }

    @Parser(position = 6)
    public void setCoutAssocie(String coutAssocie) {
	this.coutAssocie = coutAssocie;
    }

}
