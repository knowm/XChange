package org.knowm.xchange.okcoin.service.streaming;

import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.ssl.SslContext;

public class WebSocketBase {
  private static final Logger log = LoggerFactory.getLogger(WebSocketBase.class);

  private WebSocketService service = null;
  private Timer timerTask = null;
  private MonitorTask monitor = null;
  private EventLoopGroup group = null;
  private Bootstrap bootstrap = null;
  private Channel channel = null;
  private String url = null;
  private ChannelFuture future = null;
  private boolean isAlive = false;
  private int siteFlag = 0;
  private Set<String> subscribedChannels = new HashSet<String>();

  public WebSocketBase(String url, WebSocketService service) {
    this.url = url;
    this.service = service;
  }

  public void start() {
    if (url == null) {
      log.info("WebSocketClient start error  url can not be null");
      return;
    }
    if (service == null) {
      log.info("WebSocketClient start error  WebSocketService can not be null");
      return;
    }

    monitor = new MonitorTask(this);
    this.connect();

    timerTask = new Timer();
    timerTask.schedule(monitor, 1000, 3000);
  }

  public void setStatus(boolean flag) {
    this.isAlive = flag;
  }

  public void addChannel(String channel) {
    if (channel == null) {
      return;
    }
    String dataMsg = "{'event':'addChannel','channel':'" + channel + "'}";
    this.sendMessage(dataMsg);
    subscribedChannels.add(channel);
  }

  public void removeChannel(String channel) {
    if (channel == null) {
      return;
    }
    String dataMsg = "{'event':'removeChannel','channel':'" + channel + "'}";
    this.sendMessage(dataMsg);
    subscribedChannels.remove(channel);
  }

  /**
   * 期货取消订单
   * 
   * @param apiKey
   * @param secretKey
   * @param symbol
   * @param orderId
   * @param contractType
   */
  public void cancleFutureOrder(String apiKey, String secretKey, String symbol, long orderId, String contractType) {
    log.debug("apiKey=" + apiKey + ", secretKey=" + secretKey + ", symbol=" + symbol + ", orderId=" + orderId + ", contractType=" + contractType);
    Map<String, String> preMap = new HashMap<String, String>();
    preMap.put("api_key", apiKey);
    preMap.put("symbol", symbol);
    preMap.put("order_id", String.valueOf(orderId));
    preMap.put("contract_type", contractType);
    String preStr = MD5Util.createLinkString(preMap);
    preStr = preStr + "&secret_key=" + secretKey;
    String signStr = MD5Util.getMD5String(preStr);
    preMap.put("sign", signStr);
    String params = MD5Util.getParams(preMap);
    StringBuilder tradeStr = new StringBuilder("{'event': 'addChannel','channel': 'ok_futuresusd_cancel_order','parameters': ").append(params)
        .append("}");
    this.sendMessage(tradeStr.toString());
  }

  /**
   * 取消现货交易
   * 
   * @param apiKey
   * @param secretKey
   * @param symbol
   * @param orderId
   */
  public void cancelOrder(String apiKey, String secretKey, String symbol, Long orderId) {
    log.debug("apiKey=" + apiKey + ", secretKey=" + secretKey + ", symbol=" + symbol + ", orderId=" + orderId);
    Map<String, String> preMap = new HashMap<String, String>();
    preMap.put("api_key", apiKey);
    preMap.put("symbol", symbol);
    preMap.put("order_id", orderId.toString());
    String preStr = MD5Util.createLinkString(preMap);
    StringBuilder preBuilder = new StringBuilder(preStr);
    preBuilder.append("&secret_key=").append(secretKey);
    String signStr = MD5Util.getMD5String(preBuilder.toString());
    preMap.put("sign", signStr);
    String params = MD5Util.getParams(preMap);
    String channel = "ok_spotcny_cancel_order";
    if (siteFlag == 1) {
      channel = "ok_spotusd_cancel_order";
    }
    StringBuilder tradeStr = new StringBuilder("{'event':'addChannel', 'channel':'" + channel + "', 'parameters':").append(params).append("}");
    this.sendMessage(tradeStr.toString());
  }

  /**
   * 期货交易数据
   * 
   * @param apiKey
   * @param secretKey
   */
  public void futureRealtrades(String apiKey, String secretKey) {
    log.debug("apiKey=" + apiKey + ", secretKey=" + secretKey);
    StringBuilder preStr = new StringBuilder("api_key=");
    preStr.append(apiKey).append("&secret_key=").append(secretKey);
    String signStr = MD5Util.getMD5String(preStr.toString());
    StringBuilder tradeStr = new StringBuilder("{'event':'addChannel','channel':'ok_usd_future_realtrades','parameters':{'api_key':'").append(apiKey)
        .append("','sign':'").append(signStr).append("'}}");
    log.info(tradeStr.toString());
    this.sendMessage(tradeStr.toString());
  }

  /**
   * 期货下单
   * 
   * @param apiKey
   * @param secretKey
   * @param symbol
   * @param contractType
   * @param price
   * @param amount
   * @param type
   * @param matchPrice
   * @param leverRate
   */
  public void futureTrade(String apiKey, String secretKey, String symbol, String contractType, double price, int amount, int type, double matchPrice,
      int leverRate) {
    log.debug("apiKey=" + apiKey + ", secretKey=" + secretKey + ", symbol=" + symbol + ", contractType=" + contractType + ", price=" + price
        + ", amount=" + amount + ", type=" + type + ", matchPrice=" + matchPrice + ", leverRate=" + leverRate);
    Map<String, String> preMap = new HashMap<String, String>();
    // 待签名字符串
    preMap.put("api_key", apiKey);
    preMap.put("symbol", symbol);
    preMap.put("contract_type", contractType);
    preMap.put("price", String.valueOf(price));
    preMap.put("amount", String.valueOf(amount));
    preMap.put("type", String.valueOf(type));
    preMap.put("match_price", String.valueOf(matchPrice));
    preMap.put("lever_rate", String.valueOf(leverRate));
    String preStr = MD5Util.createLinkString(preMap);
    preStr = preStr + "&secret_key=" + secretKey;
    // 签名
    String signStr = MD5Util.getMD5String(preStr);
    // 参数
    preMap.put("sign", signStr);
    String params = MD5Util.getParams(preMap);
    // 交易json
    StringBuilder tradeStr = new StringBuilder("{'event': 'addChannel','channel':'ok_futuresusd_trade','parameters':").append(params).append("}");
    log.info(tradeStr.toString());
    this.sendMessage(tradeStr.toString());
  }

  /**
   * 取得user_info
   */
  public void getUserInfo(String apiKey, String secretKey) {
    log.debug("apiKey=" + apiKey + ", secretKey=" + secretKey);
    StringBuilder preStr = new StringBuilder("api_key=");
    preStr.append(apiKey).append("&secret_key=").append(secretKey);
    String signStr = MD5Util.getMD5String(preStr.toString());
    String channel = "ok_spotcny_userinfo";
    if (siteFlag == 1) {
      channel = "ok_spotusd_userinfo";
    }
    StringBuilder tradeStr = new StringBuilder("{'event':'addChannel','channel':'").append(channel).append("','parameters':{'api_key':'")
        .append(apiKey).append("','sign':'").append(signStr).append("'}}");
    log.info(tradeStr.toString());
    this.sendMessage(tradeStr.toString());
  }

  /**
   * 现货交易数据
   * 
   * @param apiKey
   * @param secretKey
   */
  public void realTrades(String apiKey, String secretKey) {
    log.debug("apiKey=" + apiKey + ", secretKey=" + secretKey);
    StringBuilder preStr = new StringBuilder("api_key=");
    preStr.append(apiKey).append("&secret_key=").append(secretKey);
    String signStr = MD5Util.getMD5String(preStr.toString());
    String channel = "ok_cny_realtrades";
    if (siteFlag == 1) {
      channel = "ok_usd_realtrades";
    }
    StringBuilder tradeStr = new StringBuilder("{'event':'addChannel','channel':'" + channel + "','parameters':{'api_key':'").append(apiKey)
        .append("','sign':'").append(signStr).append("'}}");
    log.info(tradeStr.toString());
    this.sendMessage(tradeStr.toString());
  }

  /**
   * 现货交易下单
   * 
   * @param apiKey
   * @param symbol
   * @param secretKey
   * @param price
   * @param amount
   * @param type
   */
  public void spotTrade(String apiKey, String secretKey, String symbol, double price, double amount, String type) {
    Map<String, String> signPreMap = new HashMap<String, String>();
    signPreMap.put("api_key", apiKey);
    signPreMap.put("symbol", symbol);
    signPreMap.put("price", String.valueOf(price));
    signPreMap.put("amount", String.valueOf(amount));
    signPreMap.put("type", type);
    String preStr = MD5Util.createLinkString(signPreMap);
    StringBuilder preBuilder = new StringBuilder(preStr);
    preBuilder.append("&secret_key=").append(secretKey);
    String signStr = MD5Util.getMD5String(preBuilder.toString());
    String channel = "ok_spotcny_trade";
    if (siteFlag == 1) {
      channel = "ok_spotusd_trade";
    }
    StringBuilder tradeStr = new StringBuilder("{'event':'addChannel','channel':'" + channel + "','parameters':");
    signPreMap.put("sign", signStr);
    String params = MD5Util.getParams(signPreMap);
    tradeStr.append(params).append("}");
    log.info(tradeStr.toString());
    this.sendMessage(tradeStr.toString());
  }

  private void connect() {
    try {
      final URI uri = new URI(url);

      if (uri.getHost().contains("com")) {
        siteFlag = 1;
      }

      group = new NioEventLoopGroup(1);
      bootstrap = new Bootstrap();
      final SslContext sslCtx = SslContext.newClientContext();
      final WebSocketClientHandler handler = new WebSocketClientHandler(
          WebSocketClientHandshakerFactory.newHandshaker(uri, WebSocketVersion.V13, null, false, new DefaultHttpHeaders(), Integer.MAX_VALUE),
          service, monitor);

      bootstrap.group(group).option(ChannelOption.TCP_NODELAY, true).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
        protected void initChannel(SocketChannel ch) {
          ChannelPipeline p = ch.pipeline();
          if (sslCtx != null) {
            p.addLast(sslCtx.newHandler(ch.alloc(), uri.getHost(), uri.getPort()));
          }
          p.addLast(new HttpClientCodec(), new HttpObjectAggregator(8192), handler);
        }
      });

      future = bootstrap.connect(uri.getHost(), uri.getPort());
      future.addListener(new ChannelFutureListener() {
        public void operationComplete(final ChannelFuture future) throws Exception {
        }
      });
      channel = future.sync().channel();
      handler.handshakeFuture().sync();
      this.setStatus(true);

    } catch (Exception e) {
      log.info("WebSocketClient start error ", e);
      group.shutdownGracefully();
      this.setStatus(false);
    }
  }

  private void sendMessage(String message) {
    if (!isAlive) {
      log.info("WebSocket is not Alive addChannel error");
    }
    channel.writeAndFlush(new TextWebSocketFrame(message));
  }

  public void sendPing() {
    String dataMsg = "{'event':'ping'}";
    this.sendMessage(dataMsg);
  }

  public void reConnect() {
    try {
      this.group.shutdownGracefully();
      this.group = null;
      this.connect();

      if (future.isSuccess()) {
        this.setStatus(true);
        this.sendPing();
        Iterator<String> it = subscribedChannels.iterator();
        while (it.hasNext()) {
          String channel = it.next();
          this.addChannel(channel);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}