package org.knowm.xchange.exx.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.exx.EXXAuthenticated;
import org.knowm.xchange.exx.dto.trade.EXXCancelOrder;
import org.knowm.xchange.exx.dto.trade.EXXOrder;
import org.knowm.xchange.exx.dto.trade.EXXPlaceOrder;
import org.knowm.xchange.exx.utils.CommonUtil;

public class EXXTradeServiceRaw extends EXXBaseService {
  private final EXXAuthenticated exxAuthenticated;
  private final String apiKey;
  private final String secretKey;

  public EXXTradeServiceRaw(Exchange exchange) {
    super(exchange);
    this.exxAuthenticated =
        ExchangeRestProxyBuilder.forInterface(
                EXXAuthenticated.class, exchange.getExchangeSpecification())
            .build();

    this.apiKey = super.apiKey;
    this.secretKey = super.secretKey;
  }

  /**
   * @param order
   * @return
   * @throws IOException
   */
  public EXXPlaceOrder placeExxOrder(
      BigDecimal amount, String currency, BigDecimal price, String type) throws IOException {

    Long nonce = System.currentTimeMillis();
    String params =
        "accesskey="
            + this.apiKey
            + "&amount="
            + amount
            + "&currency="
            + currency
            + "&nonce="
            + nonce
            + "&price="
            + price
            + "&type="
            + type;

    String signature = CommonUtil.HmacSHA512(params, this.secretKey);

    return exxAuthenticated.placeLimitOrder(
        this.apiKey, amount, currency, nonce, price, type, signature);
  }

  /**
   * @return boolean
   * @throws IOException
   */
  public boolean cancelExxOrder(String orderId, String currency) throws IOException {
    Long nonce = System.currentTimeMillis();
    String params =
        "accesskey=" + this.apiKey + "&currency=" + currency + "&id=" + orderId + "&nonce=" + nonce;

    String signature = CommonUtil.HmacSHA512(params, this.secretKey);
    EXXCancelOrder exxCancelOrder =
        exxAuthenticated.cancelOrder(this.apiKey, currency, orderId, nonce, signature);

    if (exxCancelOrder.getCode() == 100) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * @return Object
   * @throws IOException
   */
  public List<EXXOrder> getExxOpenOrders(String currency, int pageIndex, String type)
      throws IOException {
    Long nonce = System.currentTimeMillis();
    String params =
        "accesskey="
            + this.apiKey
            + "&currency="
            + currency
            + "&nonce="
            + nonce
            + "&pageIndex="
            + pageIndex
            + "&type="
            + type;

    String signature = CommonUtil.HmacSHA512(params, this.secretKey);

    return exxAuthenticated.getOpenOrders(this.apiKey, currency, nonce, pageIndex, type, signature);
  }
}
