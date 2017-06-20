package sst.bank.events;

import lombok.Data;
import sst.bank.model.Beneficiary;

@Data
public class BeneficiaryChangeEvent {
    private Beneficiary beneficiary;

    public BeneficiaryChangeEvent(Beneficiary beneficiary) {
        super();
        this.beneficiary = beneficiary;
    }
}
