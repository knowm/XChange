package info.bitrich.xchangestream.coinmate;

import info.bitrich.xchangestream.coinmate.CoinmateStreamingService;
import info.bitrich.xchangestream.coinmate.dto.auth.AuthParams;

public class CoinmateStreamingServiceFactory {

  private AuthParams authParams;
  private String baseUrl;

  public CoinmateStreamingServiceFactory(String baseUrl, AuthParams authParams) {
    this.baseUrl = baseUrl;
    this.authParams = authParams;
  }

  public CoinmateStreamingService createAndConnect(String endpoint, boolean needsAuth) {
    String url = baseUrl + "/" + endpoint;
    if (needsAuth && authParams != null) {
      url += "?" + authParams.toParams();
    }
    CoinmateStreamingService service = new CoinmateStreamingService(url);
    // block until connected, because of nonce conflicts when connecting to multiple channels
    service.connect().blockingAwait();
    return service;
  }

}
