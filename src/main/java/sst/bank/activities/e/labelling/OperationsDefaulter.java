package sst.bank.activities.e.labelling;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.Year;

import sst.bank.activities.BankActivity;
import sst.bank.config.BankUtils;
import sst.bank.model.Operation;
import sst.bank.model.Operation.OperationType;
import sst.bank.model.OperationLabel;
import sst.bank.model.container.BankContainer;

public class OperationsDefaulter implements BankActivity {

    @Override
    public void run() {
	OperationLabel label = BankContainer.me().label("Visa");
	if (label != null) {

	    BankContainer.me().operationsContainer().operations()
		    .stream()
		    .filter(o -> o.getLabels().contains(label))
		    .filter(o -> o.getAmount().compareTo(BigDecimal.ZERO) != 0)
		    .forEach(o -> adjustAmount(o));
	}
    }

    private void adjustAmount(Operation visaOperation) {
	if (visaOperation.getAmount().compareTo(BigDecimal.ZERO) != 0) {
	    LocalDate execDate = visaOperation.getExecutionDate();
	    final Month month = Month.from(execDate).minus(1);
	    Year year = (Month.DECEMBER.equals(month)) ? Year.from(execDate).minus(Period.ofYears(1))
		    : Year.from(execDate);
	    Double visaSum = BankContainer.me().operationsContainer().operations()
		    .stream()
		    .filter(o -> Month.from(o.getExecutionDate()).equals(month)
			    && Year.from(o.getExecutionDate()).equals(year))
		    .filter(o -> OperationType.VISA.equals(o.getOperationType()))
		    .mapToDouble(o -> o.getAmount().doubleValue())
		    .sum();

	    if (visaSum != 0.0) {
		visaOperation.setAmount(BigDecimal.ZERO);
		visaOperation.setCounterparty(BankUtils.format(visaSum));
	    }
	}
	return;
    }

}
