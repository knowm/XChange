package nostro.xchange.utils;

import java.util.Set;
import java.util.TreeSet;

public class AccountDocument {

    private AccountSubscriptions subscriptions = new AccountSubscriptions();

    public AccountDocument() {
    }

    public AccountSubscriptions getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(AccountSubscriptions subscriptions) {
        this.subscriptions = subscriptions;
    }

    public static class AccountSubscriptions {

        private TreeSet<String> balances = new TreeSet<>();
        private TreeSet<String> orders = new TreeSet<>();

        public AccountSubscriptions() {
        }

        public Set<String> getBalances() {
            return balances;
        }

        public void setBalances(TreeSet<String> balances) {
            this.balances = balances;
        }

        public Set<String> getOrders() {
            return orders;
        }

        public void setOrders(TreeSet<String> orders) {
            this.orders = orders;
        }
    }
}
