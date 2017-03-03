package sst.bank.activities.c.parsing.pcBanking;

import sst.bank.model.Operation;
import sst.bank.model.container.BankContainer;
import sst.common.file.loader.interfaces.RecordFormatter;
import sst.common.file.loader.interfaces.RecordSelector;

public class PCBankingParser implements RecordFormatter, RecordSelector {

    @Override
    public boolean select(String record) {
	if (record != null) {
	    String[] array = record.split(";", -2);
	    return array.length == 9 && !record.contains("ANNEE + REFERENCE");
	}
	return false;
    }

    @Override
    public void format(Object recordParsed) {
	PCBankingOperation pcBoperation = (PCBankingOperation) recordParsed;

	Operation op = new Operation();
	op.setAccount(pcBoperation.getAccount());
	op.setAmount(pcBoperation.getAmount());
	op.setCounterparty(pcBoperation.getCounterparty());
	op.setCurrency(pcBoperation.getCurrency());
	op.setDetail(pcBoperation.getDetail());
	op.setExecutionDate(pcBoperation.getExecutionDate());
	op.setFortisId(pcBoperation.getFortisId());
	op.setValueDate(pcBoperation.getValueDate());

	if (!BankContainer.me().operationsContainer().operations().contains(op)) {
	    op.setBankId(BankContainer.me().newId());
	    BankContainer.me().operationsContainer().operations().add(op);
	}
    }

}
