package info.bitrich.xchangestream.coinmate.dto.auth;

import com.pusher.client.util.ConnectionFactory;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import org.knowm.xchange.coinmate.CoinmateException;

public class CoinmateUrlEncodedConnectionFactory extends ConnectionFactory {

  private final PusherAuthParamsObject pusherAuthParamsObject;

  public CoinmateUrlEncodedConnectionFactory(PusherAuthParamsObject pusherAuthParamsObject) {
    this.pusherAuthParamsObject = pusherAuthParamsObject;
  }

  public String getCharset() {
    return "UTF-8";
  }

  public String getContentType() {
    return "application/x-www-form-urlencoded";
  }

  public String getBody() {
    StringBuilder urlParameters = new StringBuilder();

    try {
      Map<String, String> mQueryStringParameters = pusherAuthParamsObject.getParams();
      urlParameters
          .append("channel_name=")
          .append(URLEncoder.encode(getChannelName(), getCharset()));
      urlParameters.append("&socket_id=").append(URLEncoder.encode(getSocketId(), getCharset()));
      Iterator var2 = mQueryStringParameters.keySet().iterator();

      while (var2.hasNext()) {
        String parameterName = (String) var2.next();
        urlParameters.append("&").append(parameterName).append("=");
        urlParameters.append(
            URLEncoder.encode((String) mQueryStringParameters.get(parameterName), getCharset()));
      }
    } catch (IOException e) {
      throw new CoinmateException(e.getMessage());
    }

    return urlParameters.toString();
  }
}
