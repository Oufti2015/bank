package sst.bank.activities.b.loading;

import sst.bank.activities.BankActivity;
import sst.bank.config.BankConfiguration;
import sst.bank.model.Beneficiary;
import sst.bank.model.container.BankContainer;

public class BeneficiaryCreator implements BankActivity {

    @Override
    public void run() {
	// BankContainer.me().beneficiaries().removeAll(BankContainer.me().beneficiaries());
	BankConfiguration.me().getCounterpartiesMapping().keySet()
		.stream()
		.filter(s -> null == BankContainer.me().getBeneficiary(s))
		.forEach(s -> createCounterpartyBeneficiary(s));
	BankConfiguration.me().getDetailsMapping().keySet()
		.stream()
		.filter(s -> null == BankContainer.me().getBeneficiary(s))
		.forEach(s -> createDetailBeneficiary(s));
    }

    private void createCounterpartyBeneficiary(String id) {
	BankContainer.me().addBeneficiary(new Beneficiary(id).counterparty(id));
    }

    private void createDetailBeneficiary(String id) {
	BankContainer.me().addBeneficiary(new Beneficiary(id).detail(id));
    }
}
