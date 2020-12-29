package sst.bank.activities.b.loading;

import sst.bank.activities.BankActivity;
import sst.bank.config.BankConfiguration;
import sst.bank.model.Beneficiary;
import sst.bank.model.container.BankContainer;

public class BeneficiaryCreator implements BankActivity {

    @Override
    public void run() {
        BankConfiguration.me().getCounterpartiesMapping().keySet()
                .stream()
                .filter(s -> null == BankContainer.me().getBeneficiaryByCounterpartyDetails(s))
                .forEach(this::createCounterpartyBeneficiary);
        BankConfiguration.me().getDetailsMapping().keySet()
                .stream()
                .filter(s -> null == BankContainer.me().getBeneficiaryByCounterpartyDetails(s))
                .forEach(this::createDetailBeneficiary);
    }

    private void createCounterpartyBeneficiary(String id) {
        BankContainer.me().addBeneficiary(new Beneficiary(id).counterparty(id));
    }

    private void createDetailBeneficiary(String id) {
        BankContainer.me().addBeneficiary(new Beneficiary(id).detail(id));
    }
}
