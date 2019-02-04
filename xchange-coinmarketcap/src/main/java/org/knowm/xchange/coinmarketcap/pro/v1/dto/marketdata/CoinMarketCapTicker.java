package org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class CoinMarketCapTicker {

	private final String symbol;
	private final int circulatingSupply;
	private final String lastUpdated;
	private final int totalSupply;
	private final int cmcRank;
	private final Object platform;
	private final List<String> tags;
	private final String dateAdded;
	private final Map<String, CoinMarketCapQuote> quoteData;
	private final int numMarketPairs;
	private final String name;
	private final int maxSupply;
	private final int id;
	private final String slug;

	public CoinMarketCapTicker(
			@JsonProperty("symbol") String symbol,
			@JsonProperty("circulating_supply") int circulatingSupply,
			@JsonProperty("last_updated") String lastUpdated,
			@JsonProperty("total_supply") int totalSupply,
			@JsonProperty("cmc_rank") int cmcRank,
			@JsonProperty("platform") Object platform,
			@JsonProperty("tags") List<String> tags,
			@JsonProperty("date_added") String dateAdded,
			@JsonProperty("quote")
					@JsonDeserialize(using = CoinMarketCapQuoteDeserializer.class)
					Map<String, CoinMarketCapQuote> quoteData,
			@JsonProperty("num_market_pairs") int numMarketPairs,
			@JsonProperty("name") String name,
			@JsonProperty("max_supply") int maxSupply,
			@JsonProperty("id") int id,
			@JsonProperty("slug") String slug) {
		this.symbol = symbol;
		this.circulatingSupply = circulatingSupply;
		this.lastUpdated = lastUpdated;
		this.totalSupply = totalSupply;
		this.cmcRank = cmcRank;
		this.platform = platform;
		this.tags = tags;
		this.dateAdded = dateAdded;
		this.quoteData = quoteData;
		this.numMarketPairs = numMarketPairs;
		this.name = name;
		this.maxSupply = maxSupply;
		this.id = id;
		this.slug = slug;
	}

	public String getSymbol(){
		return symbol;
	}

	public int getCirculatingSupply(){
		return circulatingSupply;
	}

	public String getLastUpdated(){
		return lastUpdated;
	}

	public int getTotalSupply(){
		return totalSupply;
	}

	public int getCmcRank(){
		return cmcRank;
	}

	public Object getPlatform(){
		return platform;
	}

	public List<String> getTags(){
		return tags;
	}

	public String getDateAdded(){
		return dateAdded;
	}

	public Map<String, CoinMarketCapQuote> getQuote(){
		return quoteData;
	}

	public int getNumMarketPairs(){
		return numMarketPairs;
	}

	public String getName(){
		return name;
	}

	public int getMaxSupply(){
		return maxSupply;
	}

	public int getId(){
		return id;
	}

	public String getSlug(){
		return slug;
	}

	@Override
 	public String toString(){
		return 
			"CoinMarketCapTicker{" +
			"symbol = '" + symbol + '\'' + 
			",circulating_supply = '" + circulatingSupply + '\'' + 
			",last_updated = '" + lastUpdated + '\'' + 
			",total_supply = '" + totalSupply + '\'' + 
			",cmc_rank = '" + cmcRank + '\'' + 
			",platform = '" + platform + '\'' + 
			",tags = '" + tags + '\'' + 
			",date_added = '" + dateAdded + '\'' + 
			",quote = '" + quoteData + '\'' +
			",num_market_pairs = '" + numMarketPairs + '\'' + 
			",name = '" + name + '\'' + 
			",max_supply = '" + maxSupply + '\'' + 
			",id = '" + id + '\'' + 
			",slug = '" + slug + '\'' + 
			"}";
		}

	public static class CoinMarketCapQuoteDeserializer
			extends JsonDeserializer<Map<String, CoinMarketCapQuote>> {

		@Override
		public Map<String, CoinMarketCapQuote> deserialize(JsonParser jp, DeserializationContext ctxt)
				throws IOException {
			JsonNode jsonNode = jp.getCodec().readTree(jp);
			return deserializeFromNode(jsonNode);
		}

		static Map<String, CoinMarketCapQuote> deserializeFromNode(JsonNode jsonNode)
				throws IOException {
			Iterator<Map.Entry<String, JsonNode>> iterator = jsonNode.fields();
			Map<String, CoinMarketCapQuote> quoteData = new HashMap<>();
			ObjectMapper mapper = new ObjectMapper();
			while (iterator.hasNext()) {
				Map.Entry<String, JsonNode> entry = iterator.next();
				CoinMarketCapQuote quote =
						mapper.readValue(entry.getValue().toString(), CoinMarketCapQuote.class);
				quoteData.put(entry.getKey(), quote);
			}
			return quoteData;
		}
	}
}