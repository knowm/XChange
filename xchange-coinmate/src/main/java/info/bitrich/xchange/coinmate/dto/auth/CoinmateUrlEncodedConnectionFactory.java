package info.bitrich.xchange.coinmate.dto.auth;

import com.pusher.client.util.ConnectionFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

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
        StringBuffer urlParameters = new StringBuffer();

        try {
            Map<String,String> mQueryStringParameters = pusherAuthParamsObject.getParams();
            urlParameters.append("channel_name=").append(URLEncoder.encode(this.getChannelName(), this.getCharset()));
            urlParameters.append("&socket_id=").append(URLEncoder.encode(this.getSocketId(), this.getCharset()));
            Iterator var2 = mQueryStringParameters.keySet().iterator();

            while(var2.hasNext()) {
                String parameterName = (String)var2.next();
                urlParameters.append("&").append(parameterName).append("=");
                urlParameters.append(URLEncoder.encode((String)mQueryStringParameters.get(parameterName), this.getCharset()));
            }
        } catch (UnsupportedEncodingException var4) {
            var4.printStackTrace();
        }

        return urlParameters.toString();
    }
}
