package sst.bank.model.container;

import java.util.List;

import sst.bank.model.Operation;

public interface OperationsContainerInterface {

    public List<Operation> operations();

    public void addOperation(Operation operation);

    public void addOperations(List<Operation> list);
}
