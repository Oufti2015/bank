package sst.bank.activities.c.parsing.visa;

import com.google.common.base.Strings;

import sst.bank.model.Operation;
import sst.bank.model.Operation.OperationType;
import sst.bank.model.container.BankContainer;
import sst.common.file.loader.interfaces.RecordFormatter;
import sst.common.file.loader.interfaces.RecordSelector;

public class VISAFirstLineParser implements RecordFormatter, RecordSelector {

    private Operation operation = null;

    @Override
    public boolean select(String record) {
	String[] array = record.split(";", -2);
	return array.length == 7 && !record.contains("cution;Date valeur;Montant;Devise du compte;D")
		&& Strings.isNullOrEmpty(array[6]);
    }

    @Override
    public void format(Object recordParsed) {
	if (recordParsed instanceof VISAFirstLine) {
	    VISAFirstLine visa = (VISAFirstLine) recordParsed;
	    operation = new Operation();
	    operation.setBankId(BankContainer.me().newId());
	    operation.setOperationType(OperationType.VISA);
	    operation.setExecutionDate(visa.getExecutionDate());
	    operation.setValueDate(visa.getValueDate());
	    operation.setAmount(visa.getAmount());
	    operation.setCurrency(visa.getCurrency());
	} else {
	    VISASecondLine visa = (VISASecondLine) recordParsed;
	    operation.setCounterparty("RELEVE VISA");
	    operation.setDetail(visa.getDetail());
	    BankContainer.me().addOperation(operation);
	    operation = null;
	}
    }
}
