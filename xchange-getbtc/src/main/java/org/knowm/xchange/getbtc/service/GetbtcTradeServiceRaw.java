package org.knowm.xchange.getbtc.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.getbtc.GetbtcAuthenticated;
import org.knowm.xchange.getbtc.dto.trade.GetbtcCancelOrder;
import org.knowm.xchange.getbtc.dto.trade.GetbtcOpenOrders;
import org.knowm.xchange.getbtc.dto.trade.GetbtcOrder;
import org.knowm.xchange.getbtc.dto.trade.GetbtcPlaceOrder;
import org.knowm.xchange.getbtc.utils.CommonUtil;
import org.knowm.xchange.getbtc.utils.RestSignUtil;

import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.SynchronizedValueFactory;
/**
 * kevinobamatheus@gmail.com
 * @author kevingates
 *
 */
public class GetbtcTradeServiceRaw extends GetbtcBaseService {
  private final GetbtcAuthenticated exxAuthenticated;
  private String apiKey;
  private String secretKey;
  private SynchronizedValueFactory<Long> nonceFactory;

  public GetbtcTradeServiceRaw(Exchange exchange) {
    super(exchange);
    this.exxAuthenticated =
        RestProxyFactory.createProxy(
        		GetbtcAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());

    this.apiKey = super.apiKey;
    this.secretKey = super.secretKey;
    this.nonceFactory = exchange.getNonceFactory();
  }

  /**
   * @param order
   * @return
   * @throws IOException
   */
  public GetbtcPlaceOrder placeGetbtcOrder(BigDecimal amount, String currency, BigDecimal price,String type) throws IOException {
	  	  
	  Map params = new HashMap();
	  params.put("api_key", this.apiKey);
	  params.put("nonce", System. currentTimeMillis());
      	   
      try {
		   params.put("signature", RestSignUtil.getHmacSHA256(params, this.secretKey));
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {			
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	  	 	  
    return exxAuthenticated.placeLimitOrder(params);

  }

  /**
   * @return boolean
   * @throws IOException
   */
  public boolean cancelGetbtcOrder(String orderId,String currency) throws IOException {    
	  Map params = new HashMap();
	  params.put("api_key", this.apiKey);
	  params.put("nonce", System. currentTimeMillis());
      	   
      try {
		   params.put("signature", RestSignUtil.getHmacSHA256(params, this.secretKey));
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {			
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	  GetbtcCancelOrder exxCancelOrder = exxAuthenticated.cancelOrder(params);
	  
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
  public GetbtcOpenOrders getGetbtcOpenOrders(String currency) throws IOException {
	  Map params = new HashMap();
	  params.put("api_key", this.apiKey);
	  params.put("nonce", System. currentTimeMillis());
      	   
      try {
		   params.put("signature", RestSignUtil.getHmacSHA256(params, this.secretKey));
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {			
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	  
    return exxAuthenticated.getOpenOrders(params);
  }
}
