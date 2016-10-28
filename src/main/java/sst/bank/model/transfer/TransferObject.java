package sst.bank.model.transfer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class TransferObject implements Serializable {

    private Integer lastId;
    private List<OperationTO> operations = new ArrayList<>();
    private String creationDate;
}
