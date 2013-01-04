package com.xeiam.xchange.mtgox.v1.service.account;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.mtgox.v1.MtGoxAdapters;
import com.xeiam.xchange.mtgox.v1.MtGoxUtils;
import com.xeiam.xchange.mtgox.v1.dto.account.MtGoxAccountInfo;
import com.xeiam.xchange.mtgox.v1.dto.account.MtGoxBitcoinDepositAddress;
import com.xeiam.xchange.service.BasePollingExchangeService;
import com.xeiam.xchange.service.account.polling.PollingAccountService;
import com.xeiam.xchange.utils.Assert;
import com.xeiam.xchange.utils.CryptoUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;

/**
 * <p>
 * XChange service to provide the following to {@link com.xeiam.xchange.Exchange}:
 * </p>
 * <ul>
 * <li>MtGox specific methods to handle account-related operations</li>
 * </ul>
 */
public class MtGoxPollingAccountService extends BasePollingExchangeService implements PollingAccountService {

  /**
   * Configured from the super class reading of the exchange specification
   */
  private final String apiBaseURI;

  public MtGoxPollingAccountService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);

    Assert.notNull(exchangeSpecification.getUri(), "Exchange specification URI cannot be null");
    Assert.notNull(exchangeSpecification.getVersion(), "Exchange specification version cannot be null");
    this.apiBaseURI = String.format("%s/api/%s/", exchangeSpecification.getUri(), exchangeSpecification.getVersion());
  }

  @Override
  public AccountInfo getAccountInfo() {

    // Build request
    String url = apiBaseURI + "/generic/private/info?raw";
    String postBody = "nonce=" + CryptoUtils.getNumericalNonce();

    // Request data
    MtGoxAccountInfo mtGoxAccountInfo = httpTemplate.postForJsonObject(url, MtGoxAccountInfo.class, postBody, mapper,
        MtGoxUtils.getMtGoxAuthenticationHeaderKeyValues(postBody, exchangeSpecification.getApiKey(), exchangeSpecification.getSecretKey()));

    // Adapt to XChange DTOs
    AccountInfo accountInfo = new AccountInfo();
    accountInfo.setUsername(mtGoxAccountInfo.getLogin());
    accountInfo.setWallets(MtGoxAdapters.adaptWallets(mtGoxAccountInfo.getWallets()));

    return accountInfo;
  }

  @Override
  public String withdrawFunds(BigDecimal amount, String address) {

    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public String requestBitcoinDepositAddress(String description, String notificationUrl) {

    try {

      // Build request
      String url = apiBaseURI + "generic/bitcoin/address?raw";
      String postBody = "nonce=" + CryptoUtils.getNumericalNonce();
      if (description != null) {
        postBody += "&description=" + URLEncoder.encode(description, "UTF-8");
      }
      if (notificationUrl != null) {
        postBody += "&ipn=" + URLEncoder.encode(notificationUrl, "UTF-8");
      }

      // Request data
      MtGoxBitcoinDepositAddress mtGoxBitcoinDepositAddress = httpTemplate.postForJsonObject(url, MtGoxBitcoinDepositAddress.class, postBody, mapper,
          MtGoxUtils.getMtGoxAuthenticationHeaderKeyValues(postBody, exchangeSpecification.getApiKey(), exchangeSpecification.getSecretKey()));

      return mtGoxBitcoinDepositAddress.getAddres();

    } catch (UnsupportedEncodingException e) {
      throw new ExchangeException("Problem generating HTTP request  (Unsupported Encoding)", e);
    }
  }
}
