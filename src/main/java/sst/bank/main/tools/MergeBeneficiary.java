package sst.bank.main.tools;

import lombok.extern.log4j.Log4j;
import sst.bank.activities.LifeCycleInterface;
import sst.bank.model.Beneficiary;
import sst.bank.model.container.BankContainer;

@Log4j
public class MergeBeneficiary {

    public static void main(String[] args) {
	new MergeBeneficiary().run();
    }

    private void run() {
	LifeCycleInterface.loadLifeCyle();

	String[] idFrom = { "BNPPARIBAS" };
	String idInto = "BNPPARIBASFORTIS";
	for (int i = 0; i < idFrom.length; i++) {
	    if (merge(idFrom[i], idInto)) {
		LifeCycleInterface.saveBeneficiaries();
		log.info("Beneficiary merged <" + idFrom[i] + "> into <" + idInto + ">.");
	    }

	}
    }

    private boolean merge(String idFrom, String idInto) {
	Beneficiary from = BankContainer.me().getBeneficiary(idFrom);
	Beneficiary into = BankContainer.me().getBeneficiary(idInto);

	if (into != null && from != null) {
	    into.getCounterparties().addAll(from.getCounterparties());
	    into.getDetails().addAll(from.getDetails());

	    BankContainer.me().beneficiaries().remove(from);
	    return true;
	}

	if (into == null) {
	    log.error("Cannot find beneficiary INTO <" + idInto + ">");
	}
	if (from == null) {
	    log.error("Cannot find beneficiary FROM <" + idFrom + ">");
	}
	return false;
    }
}
