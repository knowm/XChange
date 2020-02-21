package org.knowm.xchange.gatehub.service.polling;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.gatehub.GatehubAuthenticated;
import org.knowm.xchange.gatehub.dto.Account;
import org.knowm.xchange.gatehub.dto.Balance;
import org.knowm.xchange.gatehub.dto.BearerToken;
import org.knowm.xchange.gatehub.dto.GatehubException;
import org.knowm.xchange.gatehub.dto.Network;
import org.knowm.xchange.gatehub.dto.Payment;
import org.knowm.xchange.service.BaseExchangeService;

import si.mazi.rescu.RestProxyFactory;

/**
 * @author ObsessiveOrange
 */
public class GatehubAccountServiceRaw extends BaseExchangeService {

  private final GatehubAuthenticated gatehubAuthenticated;
  private final BearerToken token;
  protected final String walletAddress;
  private final String userUid;
  private final Map<Currency, String> vaults = new HashMap<Currency, String>() {{
    put(Currency.ETH, "dbf81182-93f7-4ae1-b1b8-ba7016d6e546");
    put(new Currency("ETC"), "6b1efef3-f6bc-44fd-86d7-5f30c7cd87a6");
  }};

  public GatehubAccountServiceRaw(Exchange exchange) {
    super(exchange);
    ExchangeSpecification spec = exchange.getExchangeSpecification();
    this.userUid = spec.getUserName();
    this.walletAddress = spec.getApiKey();
    this.token = new BearerToken(spec.getSecretKey());
//    ClientConfig config = new ClientConfig();
//    config.getDefaultParamsMap().put(HeaderParam.class, Params.of("Origin", "https://wallet.gatehub.net", "Referer", "https://wallet.gatehub.net/"));
    this.gatehubAuthenticated = RestProxyFactory.createProxy(GatehubAuthenticated.class, "https://api.gatehub.net/"/*, config*/);
  }

  public List<Balance> getGatehubBalances() throws IOException, ExchangeException {
    return gatehubAuthenticated.getBalances(this.token, this.walletAddress);
  }

  public Payment pay(BigDecimal amount, Currency currency, String address, Network network) throws IOException {
    String receivingAccountUid = this.getAccount(network, address).getUuid();
//    gatehubAuthenticated.authorizePayment("accept, authorization, content-type", "POST", "https://wallet.gatehub.net", "https://wallet.gatehub.net/");
    return gatehubAuthenticated.pay(token, Payment.request(walletAddress, amount, vaults.get(currency), network, receivingAccountUid));
  }

  public Account getAccount(Network network, String address) throws IOException, GatehubException {
    return gatehubAuthenticated.getAccount(token, userUid, Account.createQuery(network, address));
  }
}
