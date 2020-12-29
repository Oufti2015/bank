package sst.bank.model.container;

import sst.bank.model.Operation;

import java.util.List;

public interface OperationsContainerInterface {

    List<Operation> operations();

    void add(Operation operation);

    void addAll(List<Operation> list);

    Operation get(String id);

    Operation get(Integer id);
}
