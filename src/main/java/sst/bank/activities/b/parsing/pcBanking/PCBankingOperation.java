package sst.bank.activities.b.parsing.pcBanking;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;
import sst.bank.config.LocalDateDeserializer;
import sst.bank.config.LocalDateSerializer;
import sst.common.file.parser.Parser;

public class PCBankingOperation {
    @Getter
    private String fortisId;
    @Getter
    @Setter
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate executionDate;
    @Getter
    @Setter
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
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

    public PCBankingOperation() {
    }

    @Parser(position = 0)
    public void setFortisId(String fortisId) {
	this.fortisId = fortisId;
    }

    public DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Parser(position = 1)
    public void setExecutionDateString(String executionDate) {
	setExecutionDate(LocalDate.parse(executionDate, dtf));
    }

    @Parser(position = 2)
    public void setValueDateString(String valueDate) {
	setValueDate(LocalDate.parse(valueDate, dtf));
    }

    public String getExecutioinDateString() {
	return dtf.format(executionDate);
    }

    public String getValueDateString() {
	return dtf.format(valueDate);
    }

    @Parser(position = 3)
    public void setAmountString(String amount) {
	setAmount(new BigDecimal(amount.replaceAll(",", ".")));
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
}
