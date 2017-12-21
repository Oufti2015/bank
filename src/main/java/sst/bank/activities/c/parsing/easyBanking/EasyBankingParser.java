package sst.bank.activities.c.parsing.easyBanking;

import com.google.common.base.Strings;

import sst.bank.model.Operation;
import sst.bank.model.container.BankContainer;
import sst.common.file.loader.interfaces.RecordFormatter;
import sst.common.file.loader.interfaces.RecordSelector;

public class EasyBankingParser implements RecordFormatter, RecordSelector {

    @Override
    public boolean select(String record) {
	String[] array = record.split(";", -2);
	return array.length == 8
		&& !Strings.isNullOrEmpty(array[0])
		&& array[0].length() == 9
		&& !record.contains("cution;Date valeur;Montant;Devise du compte;D")
		&& !Strings.isNullOrEmpty(array[6]);
    }

    @Override
    public void format(Object recordParsed) {
	EasyBankingOperation op = (EasyBankingOperation) recordParsed;

	Operation operation = new Operation();
	operation.setFortisId(op.getFortisId());
	operation.setAmount(op.getAmount());
	operation.setCurrency(op.getCurrency());
	operation.setDetail(op.getDetail());
	operation.setExecutionDate(op.getExecutionDate());
	operation.setValueDate(op.getValueDate());

	if (operation.getAmount() == null || operation.getExecutionDate() == null || operation.getValueDate() == null) {
	    System.exit(-1);
	}

	if (!BankContainer.me().operationsContainer().operations().contains(operation)) {
	    operation.setBankId(BankContainer.me().newId());
	    BankContainer.me().operationsContainer().operations().add(operation);
	} else {
	    BankContainer.me().operationsContainer().operations()
		    .stream()
		    .filter(o -> o.equals(operation))
		    .forEach(o -> {
			System.out.println("Operation updated ! Amount : old : " + o.getAmount() + " / new : "
				+ operation.getAmount());
			o.setFortisId(operation.getFortisId());
			o.setAmount(operation.getAmount());
			System.out.println("Operation updated : " + o);
		    });
	}
    }
}
