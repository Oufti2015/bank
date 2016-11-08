package sst.bank.model.transfer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import sst.bank.model.Beneficiary;

@Data
public class TransferObject implements Serializable {

    private Integer lastId;
    private List<OperationTO> operations = new ArrayList<>();
    private String creationDate;
    private List<Beneficiary> beneficiaries = new ArrayList<>();
}
