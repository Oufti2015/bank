package sst.bank.activities.e.labelling;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

import lombok.extern.log4j.Log4j;
import sst.bank.activities.BankActivity;
import sst.bank.config.BankUtils;
import sst.bank.model.Category;
import sst.bank.model.Operation;
import sst.bank.model.Operation.OperationType;
import sst.bank.model.container.BankContainer;

@Log4j
public class OperationsDefaulter implements BankActivity {

    @Override
    public void run() {
	Category cat = BankContainer.me().category("VISA");
	if (cat != null) {

	    BankContainer.me().operations()
		    .stream()
		    .filter(o -> cat.equals(o.getCategory()))
		    .filter(o -> o.getAmount().compareTo(BigDecimal.ZERO) != 0)
		    .forEach(o -> adjustAmount(o));
	}
    }

    private void adjustAmount(Operation visaOperation) {
	if (visaOperation.getAmount().compareTo(BigDecimal.ZERO) != 0) {
	    LocalDate execDate = visaOperation.getExecutionDate();
	    final Month month = Month.from(execDate).minus(1);
	    Double visaSum = BankContainer.me().operations()
		    .stream()
		    .filter(o -> Month.from(o.getExecutionDate()).equals(month)
			    && Year.from(o.getExecutionDate()).equals(Year.from(execDate)))
		    .filter(o -> OperationType.VISA.equals(o.getOperationType()))
		    .mapToDouble(o -> o.getAmount().doubleValue())
		    .sum();

	    if (visaSum != 0.0) {
		visaOperation.setAmount(BigDecimal.ZERO);
		visaOperation.setCounterparty(BankUtils.format(visaSum));
	    }
	    log.info(" --> " + execDate + " VISA amount" + visaOperation.getAmount() + " visaSum " + visaSum);
	}
	return;
    }

}
