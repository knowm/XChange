/*
 * BitMEX API
 * ## REST API for the BitMEX Trading Platform  [View Changelog](/app/apiChangelog)  ----  #### Getting Started  Base URI: [https://www.bitmex.com/api/v1](/api/v1)  ##### Fetching Data  All REST endpoints are documented below. You can try out any query right from this interface.  Most table queries accept `count`, `start`, and `reverse` params. Set `reverse=true` to get rows newest-first.  Additional documentation regarding filters, timestamps, and authentication is available in [the main API documentation](/app/restAPI).  *All* table data is available via the [Websocket](/app/wsAPI). We highly recommend using the socket if you want to have the quickest possible data without being subject to ratelimits.  ##### Return Types  By default, all data is returned as JSON. Send `?_format=csv` to get CSV data or `?_format=xml` to get XML data.  ##### Trade Data Queries  *This is only a small subset of what is available, to get you started.*  Fill in the parameters and click the `Try it out!` button to try any of these queries.  * [Pricing Data](#!/Quote/Quote_get)  * [Trade Data](#!/Trade/Trade_get)  * [OrderBook Data](#!/OrderBook/OrderBook_getL2)  * [Settlement Data](#!/Settlement/Settlement_get)  * [Exchange Statistics](#!/Stats/Stats_history)  Every function of the BitMEX.com platform is exposed here and documented. Many more functions are available.  ##### Swagger Specification  [⇩ Download Swagger JSON](swagger.json)  ----  ## All API Endpoints  Click to expand a section. 
 *
 * OpenAPI spec version: 1.2.0
 * Contact: support@bitmex.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package org.knowm.xchange.bitmex.service;

import org.knowm.xchange.bitmex.util.ApiException;
import org.knowm.xchange.bitmex.dto.BitmexStats;
import org.knowm.xchange.bitmex.dto.BitmexStatsHistory;
import org.knowm.xchange.bitmex.dto.BitmexStatsUSD;
import org.junit.Test;
import org.junit.Ignore;

import java.util.List;

/**
 * API tests for StatsApi
 */
@Ignore
public class StatsApiTest {

    private final StatsApi api = new StatsApi();

    
    /**
     * Get exchange-wide and per-series turnover and volume statistics.
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void statsGetTest() throws ApiException {
        List<BitmexStats> response = api.statsGet();

        // TODO: test validations
    }
    
    /**
     * Get historical exchange-wide and per-series turnover and volume statistics.
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void statsHistoryTest() throws ApiException {
        List<BitmexStatsHistory> response = api.statsHistory();

        // TODO: test validations
    }
    
    /**
     * Get a summary of exchange statistics in USD.
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void statsHistoryUSDTest() throws ApiException {
        List<BitmexStatsUSD> response = api.statsHistoryUSD();

        // TODO: test validations
    }
    
}
