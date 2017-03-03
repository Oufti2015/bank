package sst.bank.model.container;

import java.util.ArrayList;
import java.util.List;

import sst.bank.model.Operation;

public class OperationsContainer implements OperationsContainerInterface {

    private static OperationsContainerInterface me = null;
    static {
	me = new OperationsContainer();
    }

    private OperationsContainer() {
    }

    public static OperationsContainerInterface me() {
	return me;
    }

    private List<Operation> operations = new ArrayList<>();

    @Override
    public List<Operation> operations() {
	return operations;
    }

    @Override
    public void addOperation(Operation operation) {
	operations.add(operation);
    }

    @Override
    public void addOperations(List<Operation> list) {
	operations.addAll(list);
    }
}
