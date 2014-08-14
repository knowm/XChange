package com.xeiam.xchange.examples.coinbase.account.merchant;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.coinbase.dto.merchant.CoinbaseOrder;
import com.xeiam.xchange.coinbase.dto.merchant.CoinbaseOrders;
import com.xeiam.xchange.coinbase.dto.merchant.CoinbaseSubscription;
import com.xeiam.xchange.coinbase.dto.merchant.CoinbaseSubscriptions;
import com.xeiam.xchange.coinbase.service.polling.CoinbaseAccountService;
import com.xeiam.xchange.examples.coinbase.CoinbaseDemoUtils;

/**
 * @author jamespedwards42
 */
public class CoinbaseMerchantDemo {

  public static void main(String[] args) throws IOException {

    Exchange coinbase = CoinbaseDemoUtils.createExchange();
    CoinbaseAccountService accountService = (CoinbaseAccountService) coinbase.getPollingAccountService();

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
