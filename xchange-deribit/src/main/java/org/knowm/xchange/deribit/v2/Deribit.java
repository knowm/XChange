package org.knowm.xchange.deribit.v2;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.deribit.v2.dto.DeribitException;
import org.knowm.xchange.deribit.v2.dto.DeribitResponse;
import org.knowm.xchange.deribit.v2.dto.GrantType;
import org.knowm.xchange.deribit.v2.dto.Kind;
import org.knowm.xchange.deribit.v2.dto.account.DeribitAuthentication;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitCurrency;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitInstrument;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitOrderBook;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitSummary;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitTicker;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitTrades;

@Path("/api/v2/public")
@Produces(MediaType.APPLICATION_JSON)
public interface Deribit {

  /**
   * @param currency required, the currency symbol
   * @param kind optional, Instrument kind, if not provided instruments of all kinds are considered
   * @param expired optional, set to true to show expired instruments instead of active ones
   * @return
   * @throws DeribitException
   * @throws IOException
   */
  @GET
  @Path("get_instruments")
  DeribitResponse<List<DeribitInstrument>> getInstruments(
      @QueryParam("currency") String currency,
      @QueryParam("kind") Kind kind,
      @QueryParam("expired") Boolean expired)
      throws DeribitException, IOException;

  /**
   * Retrieves available trading instruments. This method can be used to see which instruments are
   * available for trading, or which instruments have existed historically.
   *
   * @return
   * @throws DeribitException
   * @throws IOException
   */
  @GET
  @Path("get_currencies")
  DeribitResponse<List<DeribitCurrency>> getCurrencies() throws DeribitException, IOException;

  /**
   * Retrieves the order book, along with other market values for a given instrument.
   *
   * @param instrumentName required, the instrument name for which to retrieve the order book, see
   *     {@link #getInstruments(String, Kind, Boolean) getInstruments} to obtain instrument names.
   * @param depth optional, the number of entries to return for bids and asks. max: 10000, default:
   *     20
   * @return
   * @throws DeribitException
   * @throws IOException
   */
  @GET
  @Path("get_order_book")
  DeribitResponse<DeribitOrderBook> getOrderBook(
      @QueryParam("instrument_name") String instrumentName, @QueryParam("depth") Integer depth)
      throws DeribitException, IOException;

  /**
   * Provides information about historical volatility for given cryptocurrency.
   *
   * @param currency required, the currency name for which to retrieve the historical volatility,the
   *     value can be BTC,ETH or USDT.
   * @return
   * @throws DeribitException
   * @throws IOException
   */
  @GET
  @Path("get_historical_volatility")
  DeribitResponse<List<List<BigDecimal>>> getHistoricalVolatility(
      @QueryParam("currency") String currency) throws DeribitException, IOException;

  /**
   * Retrieve the latest trades that have occurred for a specific instrument.
   *
   * @param instrumentName required, Instrument name
   * @param startSeq optional, The sequence number of the first trade to be returned
   * @param endSeq optional, The sequence number of the last trade to be returned
   * @param count optional, Number of requested items, default: 10
   * @param includeOld optional, Include trades older than 7 days, default: false
   * @param sorting optional, (asc, desc, default) Direction of results sorting (default value means
   *     no sorting, results will be returned in order in which they left the database)
   * @return
   * @throws DeribitException
   * @throws IOException
   */
  @GET
  @Path("get_last_trades_by_instrument")
  DeribitResponse<DeribitTrades> getLastTradesByInstrument(
      @QueryParam("instrument_name") String instrumentName,
      @QueryParam("start_seq") Integer startSeq,
      @QueryParam("end_seq") Integer endSeq,
      @QueryParam("count") Integer count,
      @QueryParam("include_old") Boolean includeOld,
      @QueryParam("sorting") String sorting)
      throws DeribitException, IOException;

  /**
   * Retrieves the summary information such as open interest, 24h volume, etc. for a specific
   * instrument.
   *
   * @param instrumentName required, Instrument name
   * @return
   * @throws DeribitException
   * @throws IOException
   */
  @GET
  @Path("get_book_summary_by_instrument")
  DeribitResponse<List<DeribitSummary>> getSummaryByInstrument(
      @QueryParam("instrument_name") String instrumentName) throws DeribitException, IOException;

  /**
   * Get ticker for an instrument.
   *
   * @param instrumentName required, Instrument name
   * @return
   * @throws DeribitException
   * @throws IOException
   */
  @GET
  @Path("ticker")
  DeribitResponse<DeribitTicker> getTicker(@QueryParam("instrument_name") String instrumentName)
      throws DeribitException, IOException;

  /**
   * Retrieve an Oauth access token, to be used for authentication of 'private' requests.
   *
   * @param grantType Method of authentication
   * @param username Required for grant type 'password'
   * @param password Required for grant type 'password'
   * @param clientId Required for grant type 'client_credentials' and 'client_signature'
   * @param clientSecret Required for grant type 'client_credentials'
   * @param refreshToken Required for grant type 'refresh_token'
   * @param timestamp Required for grant type 'client_signature', provides time when request has
   *     been generated
   * @param signature Required for grant type 'client_signature'; it's a cryptographic signature
   *     calculated over provided fields using user secret key. The signature should be calculated
   *     as an HMAC (Hash-based Message Authentication Code) with SHA256 hash algorithm
   * @param nonce Optional for grant type 'client_signature'; delivers user generated initialization
   *     vector for the server token
   * @param state Will be passed back in the response
   * @param scope Describes type of the access for assigned token, possible values: connection,
   *     session, session:name, trade:[read, read_write, none], wallet:[read, read_write, none],
   *     account:[read, read_write, none], expires:NUMBER, ip:ADDR. NOTICE: Depending on choosing an
   *     authentication method (grant type) some scopes could be narrowed by the server. e.g. when
   *     grant_type = client_credentials and scope = wallet:read_write it's modified by the server
   *     as scope = wallet:read
   * @return
   * @throws DeribitException
   * @throws IOException
   */
  @GET
  @Path("auth")
  DeribitResponse<DeribitAuthentication> auth(
      @QueryParam("grant_type") GrantType grantType,
      @QueryParam("username") String username,
      @QueryParam("password") String password,
      @QueryParam("client_id") String clientId,
      @QueryParam("client_secret") String clientSecret,
      @QueryParam("refresh_token") String refreshToken,
      @QueryParam("timestamp") String timestamp,
      @QueryParam("signature") String signature,
      @QueryParam("nonce") String nonce,
      @QueryParam("state") String state,
      @QueryParam("scope") String scope)
      throws DeribitException, IOException;
}
