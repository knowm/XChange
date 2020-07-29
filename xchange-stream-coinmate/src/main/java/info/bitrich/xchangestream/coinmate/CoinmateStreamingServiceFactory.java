package info.bitrich.xchangestream.coinmate;

import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.coinmate.dto.auth.AuthParams;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class CoinmateStreamingServiceFactory {

  private AuthParams authParams;
  private String baseUrl;
  private ConcurrentMap<String, Observable<JsonNode>> serviceMap;
  private static Lock connectionLock = new ReentrantLock();
  private Scheduler scheduler = Schedulers.single();

  public CoinmateStreamingServiceFactory(String baseUrl, AuthParams authParams) {
    this.baseUrl = baseUrl;
    this.authParams = authParams;
    this.serviceMap = new ConcurrentHashMap<>();
  }

  public Observable<JsonNode> createConnection(String endpoint, boolean needsAuth) {
    return serviceMap.computeIfAbsent(
        baseUrl + "/" + endpoint,
        url -> {
          String authUrl = url;
          // append auth parameters if necessary
          if (needsAuth && authParams != null) {
            authUrl += "?" + authParams.toParams();
          }
          CoinmateStreamingService service = new CoinmateStreamingService(authUrl, endpoint);

          return service
              .connect()
              .andThen(service.subscribeMessages())
              .doOnDispose(() -> service.disconnect().subscribe())
              .share();
        });
  }
}
