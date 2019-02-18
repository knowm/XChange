package org.knowm.xchange.examples.kucoin.account;

public class KucoinAccountDemo {

  // Currently not supported. See https://github.com/knowm/XChange/issues/2914
//  public static void main(String[] args) throws IOException {
//
//    Exchange exchange = KucoinExamplesUtils.getExchange();
//
//    AccountService accountService = exchange.getAccountService();
//
//    generic(accountService);
//    raw((KucoinAccountServiceRaw) accountService);
//  }
//
//  private static void generic(AccountService accountService) throws IOException {
//
//    System.out.println("----------GENERIC---------");
//
//    Map<Currency, Balance> balances = accountService.getAccountInfo().getWallet().getBalances();
//    System.out.println(balances);
//
//    System.out.println(accountService.requestDepositAddress(Currency.BTC));
//  }
//
//  private static void raw(KucoinAccountServiceRaw accountService) throws IOException {
//
//    System.out.println("------------RAW-----------");
//
//    KucoinSimpleResponse<KucoinCoinBalances> responseBalances =
//        accountService.getKucoinBalances(20, 1);
//    System.out.println(responseBalances.getData());
//
//    KucoinResponse<KucoinDepositAddress> responseAddress =
//        accountService.getKucoinDepositAddress(Currency.BTC);
//    System.out.println(responseAddress.getData().getAddress());
//  }
}
