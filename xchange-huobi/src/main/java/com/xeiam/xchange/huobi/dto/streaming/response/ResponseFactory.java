package com.xeiam.xchange.huobi.dto.streaming.response;

import java.util.HashMap;
import java.util.Map;

import com.xeiam.xchange.huobi.dto.streaming.response.historydata.ReqKLineResponse;
import com.xeiam.xchange.huobi.dto.streaming.response.historydata.ReqMarketDepthResponse;
import com.xeiam.xchange.huobi.dto.streaming.response.historydata.ReqMarketDepthTopResponse;
import com.xeiam.xchange.huobi.dto.streaming.response.historydata.ReqMarketDetailResponse;
import com.xeiam.xchange.huobi.dto.streaming.response.historydata.ReqTimeLineResponse;
import com.xeiam.xchange.huobi.dto.streaming.response.historydata.ReqTradeDetailTopResponse;
import com.xeiam.xchange.huobi.dto.streaming.response.marketdata.LastKLine;
import com.xeiam.xchange.huobi.dto.streaming.response.marketdata.LastTimeLine;
import com.xeiam.xchange.huobi.dto.streaming.response.marketdata.MarketDepthDiff;
import com.xeiam.xchange.huobi.dto.streaming.response.marketdata.MarketDepthTopDiff;
import com.xeiam.xchange.huobi.dto.streaming.response.marketdata.MarketDetail;
import com.xeiam.xchange.huobi.dto.streaming.response.marketdata.MarketOverview;
import com.xeiam.xchange.huobi.dto.streaming.response.marketdata.TradeDetail;
import com.xeiam.xchange.huobi.dto.streaming.response.payload.Payload;
import com.xeiam.xchange.huobi.dto.streaming.response.service.ReqMsgSubscribeResponse;
import com.xeiam.xchange.huobi.dto.streaming.response.service.ReqMsgUnsubscribeResponse;
import com.xeiam.xchange.huobi.dto.streaming.response.service.ReqSymbolDetailResponse;
import com.xeiam.xchange.huobi.dto.streaming.response.service.ReqSymbolListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class ResponseFactory {

	private static class ResponseFactoryHolder {
		private static final ResponseFactory INSTANCE = new ResponseFactory();
	}

	public static ResponseFactory getInstance() {
		return ResponseFactoryHolder.INSTANCE;
	}

	private final Logger log = LoggerFactory.getLogger(ResponseFactory.class);

	private final Map<String, Class<? extends Response<? extends Payload>>> responseTypes;
	private final Gson gson;

	private ResponseFactory() {
		responseTypes = getResponseTypes();
		gson = new Gson();
	}

	private Map<String, Class<? extends Response<? extends Payload>>> getResponseTypes() {
		Map<String, Class<? extends Response<? extends Payload>>> types = new HashMap();

		// Service API
		types.put("reqSymbolList", ReqSymbolListResponse.class);
		types.put("reqSymbolDetail", ReqSymbolDetailResponse.class);
		types.put("reqMsgSubscribe", ReqMsgSubscribeResponse.class);
		types.put("reqMsgUnsubscribe", ReqMsgUnsubscribeResponse.class);

		// Market data API
		types.put("lastKLine", LastKLine.class);
		types.put("lastTimeLine", LastTimeLine.class);
		types.put("marketDepthDiff", MarketDepthDiff.class);
		types.put("marketDepthTopDiff", MarketDepthTopDiff.class);
		types.put("marketDetail", MarketDetail.class);
		types.put("marketOverview", MarketOverview.class);
		types.put("tradeDetail", TradeDetail.class);

		// History data API
		types.put("reqTimeLine", ReqTimeLineResponse.class);
		types.put("reqKLine", ReqKLineResponse.class);
		types.put("reqMarketDepthTop", ReqMarketDepthTopResponse.class);
		types.put("reqMarketDepth", ReqMarketDepthResponse.class);
		types.put("reqTradeDetailTop", ReqTradeDetailTopResponse.class);
		types.put("reqMarketDetail", ReqMarketDetailResponse.class);

		return types;
	}

	public Response<? extends Payload> fromJson(String event, JsonElement... args) {
		final Response<? extends Payload> response;

		if(event.equals("request")){
			response = fromJson(args);
                }
                else if(event.equals("message")){
			response = fromJson(args);
                }
                else{
                        response = null;
			log.warn("Unknow event of args length is not 1. event: {}, args: {}.", event, args);
		}

		return response;
	}

	public Response<? extends Payload> fromJson(JsonElement... args) {
		final Response<? extends Payload> response;
		JsonElement jsonElement = args[0];
		if (jsonElement.isJsonObject()) {
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			response = fromJson(jsonObject);
		} else {
			log.warn("jsonElement is not a json object.");
			response = null;
		}
		return response;
	}

	public Response<? extends Payload> fromJson(JsonObject jsonObject) {
		String msgType = jsonObject.get("msgType").getAsString();
		log.debug("msgType: {}", msgType);

		Class<? extends Response<? extends Payload>> responseClass = responseTypes.get(msgType);

		if (responseClass == null) {
			responseClass = ErrorResponse.class;
		}

		final Response<? extends Payload> response = gson.fromJson(jsonObject, responseClass);

		return response;
	}

}
