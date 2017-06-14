package sst.bank.activities.i.printing;

public class AmountSummer {

    private double amount = 0.0;
    
    public void add(double addition) {
	amount += addition;
    }
    
    public double getResult() {
	return amount;
    }
}
