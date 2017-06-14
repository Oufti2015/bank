package sst.bank.model.container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Map<String, Operation> operationsMap = new HashMap<>();

    @Override
    public List<Operation> operations() {
	return operations;
    }

    @Override
    public void add(Operation operation) {
	addToMap(operation);
	operations.add(operation);
    }

    @Override
    public void addAll(List<Operation> list) {
	addToMap(list);
	operations.addAll(list);
    }

    private void addToMap(Operation operation) {
	String id = (operation.getFortisId() != null) ? operation.getFortisId() : operation.getBankId().toString();
	operationsMap.put(id, operation);
    }

    private void addToMap(List<Operation> list) {
	list.forEach(o -> addToMap(o));
    }

    @Override
    public Operation get(String id) {
	return operationsMap.get(id);
    }

    @Override
    public Operation get(Integer id) {
	return operationsMap.get(id.toString());
    }
}
