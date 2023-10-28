package ex.src.proxyForAOP;

public class CreditCard implements Payment {
    
    Payment cash = new Cash();

    @Override
    public void pay(int amount) {
        if(amount > 100) {
            System.out.println(amount + " 신용 카드 결제");
        } else {
            cash.pay(amount);
        }
    }
    
}
