package sst.bank.main.tools;

import lombok.extern.log4j.Log4j2;
import sst.bank.activities.LifeCycleInterface;
import sst.bank.model.Beneficiary;
import sst.bank.model.container.BankContainer;

@Log4j2
public class MergeBeneficiary {

    public static void main(String[] args) {
        new MergeBeneficiary().run();
    }

    private void run() {
        LifeCycleInterface.loadLifeCyle();

        String[] idFrom = {"BNPPARIBAS"};
        String idInto = "BNPPARIBASFORTIS";
        for (String s : idFrom) {
            if (merge(s, idInto)) {
                LifeCycleInterface.saveBeneficiaries();
                log.info("Beneficiary merged <" + s + "> into <" + idInto + ">.");
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
