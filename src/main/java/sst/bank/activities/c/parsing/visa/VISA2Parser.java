package sst.bank.activities.c.parsing.visa;

import com.google.common.base.Strings;
import sst.bank.model.Operation;
import sst.bank.model.Operation.OperationType;
import sst.bank.model.container.BankContainer;
import sst.common.file.loader.interfaces.RecordFormatter;
import sst.common.file.loader.interfaces.RecordSelector;

public class VISA2Parser implements RecordFormatter, RecordSelector {

    @Override
    public boolean select(String record) {
	String[] array = record.split(";", -2);
	return array.length == 7
		&& !Strings.isNullOrEmpty(array[0])
		&& array[0].length() == 10
		&& !record.contains("cution;Date valeur;Montant;Devise du compte;D")
		&& "Taux de change##".equals(array[5]);
    }

    @Override
    public void format(Object recordParsed) {
	VISA2Operation pcBoperation = (VISA2Operation) recordParsed;

	Operation op = new Operation();
	op.setAmount(pcBoperation.getAmount());
	op.setCurrency(pcBoperation.getCurrency());
	op.setDetail(pcBoperation.getDetail());
	op.setExecutionDate(pcBoperation.getExecutionDate());
	op.setValueDate(pcBoperation.getValueDate());
	op.setOperationType(OperationType.VISA);

	if (!BankContainer.me().operationsContainer().operations().contains(op)) {
	    op.setBankId(BankContainer.me().newId());
	    BankContainer.me().operationsContainer().operations().add(op);
	} else {
	    BankContainer.me().operationsContainer().operations()
		    .stream()
		    .filter(o -> o.equals(op))
		    .forEach(o -> {
			o.setDetail(pcBoperation.getDetail());
			o.setOperationType(OperationType.VISA);
		    });
	}
    }
}
