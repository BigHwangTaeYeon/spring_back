package ex.src.proxyForAOP;

public class App {
    public static void main(String[] args) throws Exception {
        Payment cashPerf = new CashPerf();
        Store store = new Store(cashPerf);
        store.buySomething(100);
    }
}
