package sst.bank.activities.b.parsing.easyBanking;

import sst.bank.model.Operation;
import sst.bank.model.container.BankContainer;
import sst.common.file.loader.interfaces.RecordFormatter;
import sst.common.file.loader.interfaces.RecordSelector;

public class EasyBankingParser implements RecordFormatter, RecordSelector {

    @Override
    public boolean select(String record) {
	String[] array = record.split(";", -2);
	return array.length == 6 && !record.contains("cution;Date valeur;Montant;Devise du compte;D");
    }

    @Override
    public void format(Object recordParsed) {
	EasyBankingOperation op = (EasyBankingOperation) recordParsed;

	Operation operation = new Operation();
	operation.setAmount(op.getAmount());
	operation.setCurrency(op.getCurrency());
	operation.setDetail(op.getDetail());
	operation.setExecutionDate(op.getExecutionDate());
	operation.setValueDate(op.getValueDate());

	if (!BankContainer.me().operations().contains(operation)) {
	    operation.setBankId(BankContainer.me().newId());
	    BankContainer.me().operations().add(operation);
	}
    }
}
