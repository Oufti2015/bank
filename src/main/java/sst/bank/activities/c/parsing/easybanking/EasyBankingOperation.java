package sst.bank.activities.c.parsing.easybanking;

import lombok.Getter;
import lombok.Setter;
import sst.common.file.parser.Parser;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EasyBankingOperation {
    @Getter
    private String fortisId;
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
    private String account;

    public DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Parser(position = 0)
    public void setFortisId(String fortisId) {
        this.fortisId = fortisId;
    }

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
        String replaceAll = amount;
        if (amount.contains(",")) {
            replaceAll = replaceAll.replace("\\.", "").replace(",", "\\.");
        }
        setAmount(new BigDecimal(replaceAll));
    }

    @Parser(position = 4)
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Parser(position = 5)
    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Parser(position = 6)
    public void setAccount(String account) {
        this.account = account;
    }
}
