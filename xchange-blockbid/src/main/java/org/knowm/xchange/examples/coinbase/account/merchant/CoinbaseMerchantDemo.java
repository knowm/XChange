package org.knowm.xchange.examples.coinbase.account.merchant;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbase.dto.merchant.CoinbaseOrder;
import org.knowm.xchange.coinbase.dto.merchant.CoinbaseOrders;
import org.knowm.xchange.coinbase.dto.merchant.CoinbaseSubscription;
import org.knowm.xchange.coinbase.dto.merchant.CoinbaseSubscriptions;
import org.knowm.xchange.coinbase.service.CoinbaseAccountService;
import org.knowm.xchange.examples.coinbase.CoinbaseDemoUtils;

/** @author jamespedwards42 */
public class CoinbaseMerchantDemo {

  public static void main(String[] args) throws IOException {

    Exchange coinbase = CoinbaseDemoUtils.createExchange();
    CoinbaseAccountService accountService = (CoinbaseAccountService) coinbase.getAccountService();

    CoinbaseSubscriptions subscriptions = accountService.getCoinbaseSubscriptions();
    System.out.println(subscriptions);

    List<CoinbaseSubscription> subscriptionsList = subscriptions.getSubscriptions();
    if (!subscriptionsList.isEmpty()) {
      CoinbaseSubscription subscription = subscriptionsList.get(0);
      subscription = accountService.getCoinbaseSubscription(subscription.getId());
      System.out.println(subscription);
    }

    CoinbaseOrders orders = accountService.getCoinbaseOrders();
    System.out.println(orders);

    if (!orders.getOrders().isEmpty()) {
      CoinbaseOrder order = accountService.getCoinbaseOrder(orders.getOrders().get(0).getId());
      System.out.println(order);
    }
  }
}
