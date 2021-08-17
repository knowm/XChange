package org.knowm.xchange.test.coinsuper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinsuper.CoinsuperExchange;
import org.knowm.xchange.coinsuper.dto.CoinsuperResponse;
import org.knowm.xchange.coinsuper.dto.account.CoinsuperUserAssetInfo;
import org.knowm.xchange.coinsuper.service.CoinsuperAccountServiceRaw;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.service.account.AccountService;

public class AccountServiceIntegration {

  public static void main(String[] args) {
    try {
      // getAssetInfoRaw();
      getAssetInfo();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void getAssetInfoRaw() throws IOException {
    String apiKey = "00af0b38-11fb-4aab-bf19-45edd44a4adc";
    String secretKey = "fa3f0510-155f-4567-a3b3-3f386080efa3";

    Exchange coinsuper = ExchangeFactory.INSTANCE.createExchange(CoinsuperExchange.class);

    ExchangeSpecification exchangeSpecification = coinsuper.getExchangeSpecification();
    exchangeSpecification.setApiKey(apiKey);
    exchangeSpecification.setSecretKey(secretKey);
    coinsuper.applySpecification(exchangeSpecification);

    AccountService accountService = coinsuper.getAccountService();
    try {
      raw((CoinsuperAccountServiceRaw) accountService);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void raw(CoinsuperAccountServiceRaw accountService) throws IOException {
    Map<String, String> parameters = new HashMap<String, String>();

    CoinsuperResponse<CoinsuperUserAssetInfo> coinsuperResponse = accountService.getUserAssetInfo();

    System.out.println("-------------coinsuperResponse.getData().getResult()--------");

    System.out.println(
        "BTC:" + coinsuperResponse.getData().getResult().getAsset().getBTC().getTotal());
    System.out.println(
        "BTC:" + coinsuperResponse.getData().getResult().getAsset().getBTC().getAvailable());
    System.out.println(
        "ETH:" + coinsuperResponse.getData().getResult().getAsset().getETH().getAvailable());
  }

  private static void getAssetInfo() throws IOException {
    String apiKey = "00af0b38-11fb-4aab-bf19-45edd44a4adc";
    String secretKey = "fa3f0510-155f-4567-a3b3-3f386080efa3";

    Exchange coinsuper = ExchangeFactory.INSTANCE.createExchange(CoinsuperExchange.class);

    ExchangeSpecification exchangeSpecification = coinsuper.getExchangeSpecification();
    exchangeSpecification.setApiKey(apiKey);
    exchangeSpecification.setSecretKey(secretKey);
    coinsuper.applySpecification(exchangeSpecification);

    AccountService accountService = coinsuper.getAccountService();

    try {
      AccountInfo accountInfo = accountService.getAccountInfo();
      System.out.println(accountInfo);
      System.out.println(accountInfo.getWallets());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
