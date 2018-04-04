package org.knowm.xchange.coinbase.service;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbase.dto.trade.CoinbaseTransfer;
import org.knowm.xchange.coinbase.dto.trade.CoinbaseTransfers;

/** @author jamespedwards42 */
class CoinbaseTradeServiceRaw extends CoinbaseBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  protected CoinbaseTradeServiceRaw(Exchange exchange) {

    super(exchange);
  }

  /**
   * Authenticated resource that lets you purchase Bitcoin using the primary bank account that is
   * linked to your account. (You must link and verify your bank account through the website before
   * this API call will work). The underlying optional parameter agree_btc_amount_varies is set to
   * false. Use {@link #buyAndAgreeBTCAmountVaries} to have it set to true.
   *
   * @param quantity The quantity of Bitcoin you would like to buy.
   * @return The {@code CoinbaseTransfer} representing the buy.
   * @throws IOException
   * @see <a
   *     href="https://coinbase.com/api/doc/1.0/buys/create.html">coinbase.com/api/doc/1.0/buys/create.html</a>
   */
  public CoinbaseTransfer buy(BigDecimal quantity) throws IOException {

    final CoinbaseTransfer buyTransfer =
        coinbase.buy(
            quantity.toPlainString(),
            false,
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            exchange.getNonceFactory());
    return handleResponse(buyTransfer);
  }

  /**
   * Authenticated resource that lets you purchase Bitcoin using the primary bank account that is
   * linked to your account. (You must link and verify your bank account through the website before
   * this API call will work). The underlying optional parameter agree_btc_amount_varies is set to
   * true. Use {@link #buyAndAgreeBTCAmountVaries} to have it set to false.
   *
   * @param quantity The quantity of Bitcoin you would like to buy.
   * @return A {@code CoinbaseTransfer} representing the buy.
   * @throws IOException
   * @see <a
   *     href="https://coinbase.com/api/doc/1.0/buys/create.html">coinbase.com/api/doc/1.0/buys/create.html</a>
   */
  public CoinbaseTransfer buyAndAgreeBTCAmountVaries(BigDecimal quantity) throws IOException {

    final CoinbaseTransfer buyTransfer =
        coinbase.buy(
            quantity.toPlainString(),
            true,
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            exchange.getNonceFactory());
    return handleResponse(buyTransfer);
  }

  /**
   * Authenticated resource that lets you convert Bitcoin in your account to USD by crediting your
   * primary bank account on Coinbase. (You must link and verify your bank account through the
   * website before this API call will work).
   *
   * @param quantity The quantity of Bitcoin you would like to sell.
   * @return A {@code CoinbaseTransfer} representing the sell.
   * @throws IOException
   * @see <a
   *     href="https://coinbase.com/api/doc/1.0/sells/create.html">coinbase.com/api/doc/1.0/sells/create.html</a>
   */
  public CoinbaseTransfer sell(BigDecimal quantity) throws IOException {

    final CoinbaseTransfer sellTransfer =
        coinbase.sell(
            quantity.toPlainString(),
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            exchange.getNonceFactory());
    return handleResponse(sellTransfer);
  }

  /**
   * Authenticated resource which returns the user’s Bitcoin purchases and sells. Sorted in
   * descending order by creation date. This is a paged resource and will return the first page by
   * default.
   *
   * @return
   * @throws IOException
   * @see <a
   *     href="https://coinbase.com/api/doc/1.0/transfers/index.html">coinbase.com/api/doc/1.0/transfers/index.html</a>
   */
  public CoinbaseTransfers getCoinbaseTransfers() throws IOException {

    return getCoinbaseTransfers(null, null);
  }

  /**
   * Authenticated resource which returns the user’s Bitcoin purchases and sells. Sorted in
   * descending order by creation date.
   *
   * @param page Optional parameter to request a desired page of results. Will return page 1 if the
   *     supplied page is null or less than 1.
   * @param limit Optional parameter to limit the maximum number of results to return. Will return
   *     up to 25 results by default if null or less than 1.
   * @return
   * @throws IOException
   * @see <a
   *     href="https://coinbase.com/api/doc/1.0/transfers/index.html">coinbase.com/api/doc/1.0/transfers/index.html</a>
   */
  public CoinbaseTransfers getCoinbaseTransfers(Integer page, final Integer limit)
      throws IOException {

    final CoinbaseTransfers transfers =
        coinbase.getTransfers(
            page,
            limit,
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            exchange.getNonceFactory());
    return transfers;
  }
}
