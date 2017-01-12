package sst.bank.activities.h.printing;

public class AmountSummer {

    private double amount = 0.0;
    
    public void add(double addition) {
	amount += addition;
    }
    
    public double getResult() {
	return amount;
    }
}
