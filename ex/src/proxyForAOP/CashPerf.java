package ex.src.proxyForAOP;

public class CashPerf implements Payment {
    
    Payment cash = new Cash();

    // 성능 검사
    @Override
    public void pay(int amount) {
        long in = System.currentTimeMillis();

        cash.pay(amount);

        long out = System.currentTimeMillis();
        System.out.println(out - in);
    }
}
