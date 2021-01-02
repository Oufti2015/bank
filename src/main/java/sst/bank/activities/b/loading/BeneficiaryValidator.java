package sst.bank.activities.b.loading;

import lombok.extern.log4j.Log4j2;
import sst.bank.activities.BankActivity;
import sst.bank.model.Beneficiary;
import sst.bank.model.container.BankContainer;

@Log4j2
public class BeneficiaryValidator implements BankActivity {

    @Override
    public void run() {
        BankContainer.me().beneficiaries()
                .stream()
                .filter(this::hasDuplicate)
                .forEach(b -> log.warn("Duplicate beneficiary " + b));
    }

    private boolean hasDuplicate(Beneficiary beneficiary) {
        Beneficiary found = BankContainer.me().getBeneficiaryByCounterpartyDetails(beneficiary.getId());
        return beneficiary != found;
    }
}
