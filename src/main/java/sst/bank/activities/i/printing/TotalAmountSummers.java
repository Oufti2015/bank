package sst.bank.activities.i.printing;

public class TotalAmountSummers {
    public enum SummerType {
        TOTAL(0), BUDGET(1), DIFF(2);
        int indice;

        SummerType(int indice) {
            this.indice = indice;
        }

        public int getIndice() {
            return indice;
        }
    }

    private final AmountSummer[] summers = new AmountSummer[SummerType.values().length];

    public TotalAmountSummers() {
        for (int i = 0; i < summers.length; i++) {
            summers[i] = new AmountSummer();
        }
    }

    public void add(SummerType type, double amount) {
        summers[type.getIndice()].add(amount);
    }

    public double get(SummerType type) {
        return summers[type.getIndice()].getResult();
    }
}
