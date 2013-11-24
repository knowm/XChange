package com.xeiam.xchange.examples.mtgox.v2;

import java.beans.PropertyVetoException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.speech.AudioException;
import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.EngineStateError;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import javax.speech.synthesis.Voice;

import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitstamp.BitstampExchange;
import com.xeiam.xchange.btce.BTCEExchange;
import com.xeiam.xchange.bter.BTERExchange;
import com.xeiam.xchange.bter.CryptoTradeExchange;
import com.xeiam.xchange.campbx.CampBXExchange;
import com.xeiam.xchange.cryptsy.CryptsyExchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.mtgox.v2.MtGoxExchange;
import com.xeiam.xchange.service.polling.PollingMarketDataService;
import com.xeiam.xchange.vircurex.VircurexExchange;

/**
 * https://github.com/adv0r/mtgox-apiv2-java
 * 
 * @author adv0r <leg@lize.it> MIT License (see LICENSE.md) Run examples at your
 *         own risk. Only partially implemented and untested. Consider donations @
 *         1N7XxSvek1xVnWEBFGa5sHn1NhtDdMhkA7
 */
public class UsageExample {

	public static void printMarket(String anExchangeName, String currency1, String currency2) {
		Exchange exchange = ExchangeFactory.INSTANCE.createExchange(anExchangeName);

		// Interested in the public polling market data feed (no authentication)
		PollingMarketDataService marketDataService = exchange.getPollingMarketDataService();

		// Get the latest order book data for BTC/CAD
		OrderBook orderBook = marketDataService.getFullOrderBook(currency1, currency2);

		Collections.sort(orderBook.getAsks(), new Comparator<LimitOrder>() {
			@Override
			public int compare(LimitOrder o1, LimitOrder o2) {
				return o1.getLimitPrice().compareTo(o2.getLimitPrice().toBigMoney());
			}
		});
		Collections.sort(orderBook.getBids(), new Comparator<LimitOrder>() {
			@Override
			public int compare(LimitOrder o1, LimitOrder o2) {
				return o2.getLimitPrice().compareTo(o1.getLimitPrice().toBigMoney());
			}
		});

		System.out.println(anExchangeName + " Bid=" + orderBook.getBids().get(0).getLimitPrice() + " Ask=" + orderBook.getAsks().get(0).getLimitPrice());

	}

	public static LimitOrder getHighestBid(OrderBook orderBook) {

		Collections.sort(orderBook.getBids(), new Comparator<LimitOrder>() {
			@Override
			public int compare(LimitOrder o1, LimitOrder o2) {
				return o2.getLimitPrice().compareTo(o1.getLimitPrice().toBigMoney());
			}
		});

		return orderBook.getBids().get(0);
	}

	public static LimitOrder getLowestAsk(OrderBook orderBook) {

		Collections.sort(orderBook.getAsks(), new Comparator<LimitOrder>() {
			@Override
			public int compare(LimitOrder o1, LimitOrder o2) {
				return o1.getLimitPrice().compareTo(o2.getLimitPrice().toBigMoney());
			}
		});

		return orderBook.getAsks().get(0);
	}

	public static class Earning implements Serializable {
		public float opportunityEarning;
		public String transactableCurrency;
		public String tradableIdentifier;

	}

	public static class Opportunity {
		ExchangeSpecification askExchange;
		ExchangeSpecification bidExchange;
		LimitOrder bid;
		LimitOrder ask;
		float size;
		float opportunityEarnings;
		CurrencyPair currencyPair;

		public CurrencyPair getCurrencyPair() {
			return currencyPair;
		}

		public void setCurrencyPair(CurrencyPair currencyPair) {
			this.currencyPair = currencyPair;
		}

		public float getEarnings() {
			return opportunityEarnings;
		}

		public void setEarnings(float earnings) {
			this.opportunityEarnings = earnings;
		}

		public Opportunity(ExchangeSpecification anAskExchange, LimitOrder anAskOrder, ExchangeSpecification aBidExchange, LimitOrder aBidOrder, float aSize) {
			askExchange = anAskExchange;
			bidExchange = aBidExchange;
			ask = anAskOrder;
			bid = aBidOrder;
			size = aSize;
		}

		public ExchangeSpecification getAskExchange() {
			return askExchange;
		}

		public void setAskExchange(ExchangeSpecification askExchange) {
			this.askExchange = askExchange;
		}

		public ExchangeSpecification getBidExchange() {
			return bidExchange;
		}

		public void setBidExchange(ExchangeSpecification bidExchange) {
			this.bidExchange = bidExchange;
		}

		public LimitOrder getBid() {
			return bid;
		}

		public void setBid(LimitOrder bid) {
			this.bid = bid;
		}

		public LimitOrder getAsk() {
			return ask;
		}

		public void setAsk(LimitOrder ask) {
			this.ask = ask;
		}

		public float getSize() {
			return size;
		}

		public void setSize(float size) {
			this.size = size;
		}

	}

	public static List<Opportunity> findOpportunities(List<ExchangeSpecification> exchanges, CurrencyPair aPair) {
		Map<List<LimitOrder>, ExchangeSpecification> asks = new HashMap<List<LimitOrder>, ExchangeSpecification>();
		Map<List<LimitOrder>, ExchangeSpecification> bids = new HashMap<List<LimitOrder>, ExchangeSpecification>();
		for (ExchangeSpecification exchange : exchanges) {
			Exchange ex = ExchangeFactory.INSTANCE.createExchange(exchange);
			if (!ex.isSupportedCurrencyPair(aPair)) {
				continue;
			}
			try{
				PollingMarketDataService service = ex.getPollingMarketDataService();
			
			OrderBook book = service.getFullOrderBook(aPair.baseCurrency, aPair.counterCurrency);
			asks.put(book.getAsks(), exchange);
			bids.put(book.getBids(), exchange);
			}catch(Exception e){
				System.out.println(exchange.getExchangeName() + " isn't working. Skipping.");
				e.printStackTrace();
			}
		}
		List<Opportunity> opportunities = new ArrayList<Opportunity>();
		for (Map.Entry<List<LimitOrder>, ExchangeSpecification> askList : asks.entrySet()) {
			for (LimitOrder ask : askList.getKey()) {
				outer: for (Map.Entry<List<LimitOrder>, ExchangeSpecification> bidList : bids.entrySet()) {
					for (LimitOrder bid : bidList.getKey()) {
						if (askList.getValue().equals(bidList.getValue())) {
							continue outer;
						}
						if (ask.getLimitPrice().isLessThan(bid.getLimitPrice())) {
							float difference = bid.getLimitPrice().getAmount().floatValue() - ask.getLimitPrice().getAmount().floatValue();
							// BigMoney average =
							// bid.getLimitPrice().toBigMoney().plus(ask.getLimitPrice().toBigMoney()).dividedBy(2l,
							// RoundingMode.HALF_EVEN);
							float percent = (difference / Math.max(bid.getLimitPrice().getAmount().floatValue(), ask.getLimitPrice().getAmount().floatValue())) * 100;
							if (percent >= 1.3 && ask.getTradableAmount().floatValue() >= 1 && bid.getTradableAmount().floatValue() >= 1) {
								System.out.println("Opportunity found!\n" + bid.getTradableIdentifier() + "->" + bid.getTransactionCurrency() + " Bid: " + bid.getLimitPrice().getAmount().toPlainString() + " @ " + bidList.getValue().getExchangeName() + "(" + bid.getTradableAmount().floatValue()
										+ "), Ask: " + ask.getLimitPrice().getAmount().toPlainString() + " @ " + askList.getValue().getExchangeName() + "(" + ask.getTradableAmount().floatValue() + ") (" + percent + "% gain)");
								Opportunity opportunity = new Opportunity(askList.getValue(), ask, bidList.getValue(), bid, difference);
								opportunity.setCurrencyPair(aPair);
								opportunities.add(opportunity);

							}
						}
					}
				}
			}
		}
		return opportunities;
	}

	public static class OrderCanceler {
		Exchange exchange;
		String orderId;

		public OrderCanceler(Exchange anExchange, String anOrderId) {
			exchange = anExchange;
			orderId = anOrderId;
		}

		public void cancelOrder() {
			exchange.getPollingTradeService().cancelOrder(orderId);
		}
	}

	static SynthesizerModeDesc desc;
	static Synthesizer synthesizer;
	static Voice voice;

	static public void init(String voiceName) throws EngineException, AudioException, EngineStateError, PropertyVetoException {
		if (desc == null) {
			System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
			desc = new SynthesizerModeDesc(Locale.US);
			Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
			synthesizer = Central.createSynthesizer(desc);
			synthesizer.allocate();
			synthesizer.resume();
			SynthesizerModeDesc smd = (SynthesizerModeDesc) synthesizer.getEngineModeDesc();
			Voice[] voices = smd.getVoices();
			for (int i = 0; i < voices.length; i++) {
				if (voices[i].getName().equals(voiceName)) {
					voice = voices[i];
					break;
				}
			}
			synthesizer.getSynthesizerProperties().setVoice(voice);
		}
	}

	public static void terminate() throws EngineException, EngineStateError {
		synthesizer.deallocate();
	}

	public static void doSpeak(String speakText) throws EngineException, AudioException, IllegalArgumentException, InterruptedException {
		synthesizer.speakPlainText(speakText, null);
		synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
	}

	public static List<Earning> earnings = new ArrayList<Earning>();

	public static class Finder implements Runnable {
		@Override
		public void run() {
			ExchangeSpecification campBx = new ExchangeSpecification(CampBXExchange.class.getCanonicalName());
			campBx.setSslUri("https://campbx.com");
			campBx.setHost("campbx.com");
			campBx.setPort(80);
			campBx.setExchangeName("CampBX");
			campBx.setExchangeDescription("CampBX is a Bitcoin exchange registered in the USA.");
			campBx.setUserName("Kevlar");
			campBx.setPassword("Zabbas4242!");

			ExchangeSpecification bitstamp = new ExchangeSpecification(BitstampExchange.class.getCanonicalName());
			bitstamp.setSslUri("https://www.bitstamp.net");
			bitstamp.setHost("www.bitstamp.net");
			bitstamp.setPort(80);
			bitstamp.setExchangeName("Bitstamp");
			bitstamp.setExchangeDescription("Bitstamp is a Bitcoin exchange registered in Slovenia.");
			bitstamp.setApiKey("67954");
			bitstamp.setSecretKey("sc7zddO/1sCs");
			bitstamp.setUserName("67954");
			bitstamp.setPassword("sc7zddO/1sCs");

			ExchangeSpecification mtGox = new ExchangeSpecification(MtGoxExchange.class.getCanonicalName());
			mtGox.setSslUri("https://data.mtgox.com");

			mtGox.setPlainTextUri("http://data.mtgox.com");
			mtGox.setHost("mtgox.com");
			mtGox.setPort(80);
			mtGox.setExchangeName("MtGox");
			mtGox.setExchangeDescription("MtGox is a Bitcoin exchange registered in Japan.");
			mtGox.setUserName("Kevlar");
			mtGox.setPassword("Zabbas4242!");
			mtGox.setApiKey("e1d65f5b-a9c4-4f48-948e-1fa990e865aa");
			mtGox.setSecretKey("GQFQW1UQdklR0Rb+ErDlImo+u7e+GxH2neGUPZzcDCBVeh+tx2UjVgF0mn6tld9XQHkxO8/ER27YuMjn7n7aCQ==");

			ExchangeSpecification btce = new ExchangeSpecification(BTCEExchange.class);
			btce.setSecretKey("27c968ff9713d4a81cd089687844f78be42a9c0e94da72d3d855024f4590cef0");
			btce.setApiKey("RATE50ZQ-MPF3WGST-EME77O1P-S0DXKYD3-P8GW0LJN");

			ExchangeSpecification bter = new ExchangeSpecification(BTERExchange.class);
			bter.setApiKey("6B8C5A04-2FBF-4643-AE67-616D0066A412");
			bter.setSecretKey("63d5c7892f0831d8d04a9af8ddaefeb498ec0822edcf8b47073689f3783d2ee8");

			ExchangeSpecification vircurex = new ExchangeSpecification(VircurexExchange.class);
			ExchangeSpecification cryptsy = new ExchangeSpecification(CryptsyExchange.class);
			cryptsy.setApiKey("1948367b66763024000812b257c1c5907e1e36fb");
			cryptsy.setSecretKey("9c5baae0e58978fd7daa317ce2418980aae3ee0ed2dc623dbd78dfdd5ef319ff78b0f80a5a1a9178");

			ExchangeSpecification cryptoTrade = new ExchangeSpecification(CryptoTradeExchange.class);
			cryptoTrade.setUserName("Kevlar");
			cryptoTrade.setApiKey("E6A56334-59A7-94E3-7764-7EF5A635E89E");
			cryptoTrade.setSecretKey("bde9555ba99316671455d196aaadf4b54a2ab9d5");

			// findPairs(Arrays.asList(BTERExchange.class.getName(),
			// BTCEExchange.class.getName()), "FTC", "BTC");
			// findPairs(Arrays.asList(BTERExchange.class.getName(),
			// BTCEExchange.class.getName()), "LTC", "BTC");

			// opportunities.addAll(findOpportunities(Arrays.asList(campBx,
			// bitstamp, mtGox), "BTC", "USD"));

			//System.out.println(ExchangeFactory.INSTANCE.createExchange(cryptsy).getPollingAccountService().getAccountInfo());
			class OppPlace {
				final List<ExchangeSpecification> exchanges;
				final CurrencyPair pair;

				public List<ExchangeSpecification> getExchanges() {
					return exchanges;
				}

				public CurrencyPair getPair() {
					return pair;
				}

				public OppPlace(List<ExchangeSpecification> someExchanges, CurrencyPair aPair) {
					exchanges = someExchanges;
					pair = aPair;
				}
			}

			List<ExchangeSpecification> exchanges = Arrays.asList(btce, bter, cryptsy, cryptoTrade);
			List<CurrencyPair> currencyPairs = new ArrayList<CurrencyPair>();
			//currencyPairs.add(new CurrencyPair("BTC", "USD"));
			currencyPairs.add(new CurrencyPair("FTC", "BTC"));
			// currencyPairs.add(new CurrencyPair("FTC", "LTC"));
			currencyPairs.add(new CurrencyPair("LTC", "BTC"));
			currencyPairs.add(new CurrencyPair("PPC", "BTC"));
			// currencyPairs.add(new CurrencyPair("CNC", "BTC"));
			currencyPairs.add(new CurrencyPair("TRC", "BTC"));
			currencyPairs.add(new CurrencyPair("FRC", "BTC"));
			currencyPairs.add(new CurrencyPair("YAC", "BTC"));
			currencyPairs.add(new CurrencyPair("WDC", "BTC"));
			currencyPairs.add(new CurrencyPair("BQB", "BTC"));
			currencyPairs.add(new CurrencyPair("DGC", "BTC"));
			currencyPairs.add(new CurrencyPair("BOT", "BTC"));
			currencyPairs.add(new CurrencyPair("SBC", "BTC"));
			currencyPairs.add(new CurrencyPair("NBL", "BTC"));
			currencyPairs.add(new CurrencyPair("DGC", "BTC"));

			List<OppPlace> places = new ArrayList<OppPlace>();
			for (CurrencyPair currencyPair : currencyPairs) {
				places.add(new OppPlace(exchanges, currencyPair));
			}

			for (OppPlace place : places) {
				try {

					List<Opportunity> opportunities = findOpportunities(place.getExchanges(), place.getPair());

					Collections.sort(opportunities, new Comparator<Opportunity>() {
						@Override
						public int compare(Opportunity o1, Opportunity o2) {
							return new Float(o2.getSize()).compareTo(o1.getSize());
						}
					});
					for (Opportunity opp : opportunities) {

						Exchange placeToBuy = ExchangeFactory.INSTANCE.createExchange(opp.getAskExchange());
						Exchange placeToSell = ExchangeFactory.INSTANCE.createExchange(opp.getBidExchange());

						if (placeToBuy.getPollingAccountService() == null || placeToSell.getPollingAccountService() == null) {
							System.out.println("We can't check at " + (placeToBuy.getPollingAccountService() == null ? placeToBuy.getExchangeSpecification().getExchangeName() : placeToSell.getExchangeSpecification().getExchangeName()) + " so skipping.");
							continue;
						}
						BigMoney buyingBalence = placeToBuy.getPollingAccountService().getAccountInfo().getBalance(CurrencyUnit.of(opp.getAsk().getTransactionCurrency()));
						BigMoney sellingBalance = placeToSell.getPollingAccountService().getAccountInfo().getBalance(CurrencyUnit.of(opp.getAsk().getTradableIdentifier()));

						BigDecimal amountToTrade = new BigDecimal(Math.min(Math.min(buyingBalence.getAmount().floatValue() / opp.getAsk().getLimitPrice().getAmount().floatValue(), sellingBalance.getAmount().floatValue()),
								Math.min(opp.getAsk().getTradableAmount().floatValue(), opp.getBid().getTradableAmount().floatValue())));

						amountToTrade = new BigDecimal((int) amountToTrade.floatValue());
						if (amountToTrade.floatValue() < 1) {

							if (buyingBalence.getAmount().floatValue() < .1) {
								float biggestPlace = 0;
								ExchangeSpecification bestExchangeSpecification = null;
								for (ExchangeSpecification info : exchanges) {
									try{
									Exchange ex = ExchangeFactory.INSTANCE.createExchange(info);
									if (ex.isSupportedCurrencyPair(opp.getCurrencyPair())) {
										float bal;
										if ((bal = ex.getPollingAccountService().getAccountInfo().getBalance(CurrencyUnit.of(opp.getAsk().getTransactionCurrency())).getAmount().floatValue()) > 0.1) {
											biggestPlace = Math.max(biggestPlace, bal);
											if (biggestPlace == bal) {
												bestExchangeSpecification = info;
											}
										}
									}
									}catch(Exception e){}
								}
								if (bestExchangeSpecification != null && biggestPlace > 0) {
									System.out.println("You should send some " + opp.getAsk().getTransactionCurrency() + " from " + bestExchangeSpecification.getExchangeName() + " to " + opp.getAskExchange().getExchangeName() + ".");
									doSpeak("You should send some " + opp.getAsk().getTransactionCurrency() + " from " + bestExchangeSpecification.getExchangeName() + " to " + opp.getAskExchange().getExchangeName() + ".");
								}
								// Go through our accounts, whichiever is
								// highest, tell us to send from here.
							}
							if (sellingBalance.getAmount().floatValue() < 1) {
								float biggestPlace = 0;
								ExchangeSpecification bestExchangeSpecification = null;

								for (ExchangeSpecification info : exchanges) {
									try{
									Exchange ex = ExchangeFactory.INSTANCE.createExchange(info);
									if (ex.isSupportedCurrencyPair(opp.getCurrencyPair())) {
										float bal;
										if ((bal = ex.getPollingAccountService().getAccountInfo().getBalance(CurrencyUnit.of(opp.getAsk().getTradableIdentifier())).getAmount().floatValue()) > 0.1 && bal >= 1) {
											biggestPlace = Math.max(biggestPlace, bal);
											if (biggestPlace == bal) {
												bestExchangeSpecification = info;
											}

										}
									}
									}catch(Exception e){
									
									}
								}
								if (bestExchangeSpecification != null && biggestPlace > 0) {
									System.out.println("You should send some " + opp.getAsk().getTradableIdentifier() + " from " + bestExchangeSpecification.getExchangeName() + " to " + opp.getBidExchange().getExchangeName() + ".");
									doSpeak("You should send some " + opp.getAsk().getTradableIdentifier() + " from " + bestExchangeSpecification.getExchangeName() + " to " + opp.getBidExchange().getExchangeName() + ".");
								}
							}
							continue;
						}

						placeToBuy.getPollingTradeService().placeLimitOrder(new LimitOrder(OrderType.BID, amountToTrade, opp.getAsk().getTradableIdentifier(), opp.getAsk().getTransactionCurrency(), opp.getAsk().getLimitPrice()));
						placeToSell.getPollingTradeService().placeLimitOrder(new LimitOrder(OrderType.ASK, amountToTrade, opp.getBid().getTradableIdentifier(), opp.getBid().getTransactionCurrency(), opp.getBid().getLimitPrice()));
						float profit = amountToTrade.floatValue() * (opp.getBid().getLimitPrice().getAmount().floatValue() - opp.getAsk().getLimitPrice().getAmount().floatValue());
						opp.setEarnings(profit);
						System.out.println("We bought " + amountToTrade + " " + opp.getAsk().getTradableIdentifier() + " @ " + placeToBuy.getExchangeSpecification().getExchangeName() + " for " + opp.getAsk().getLimitPrice().toString() + " each, and sold them for "
								+ opp.getBid().getLimitPrice().toString() + " @ " + placeToSell.getExchangeSpecification().getExchangeName() + " and in doing so made " + profit + " " + opp.getAsk().getTransactionCurrency() + ".");

						Earning earning = new Earning();
						earning.opportunityEarning = profit;
						earning.tradableIdentifier = opp.ask.getTradableIdentifier();
						earning.transactableCurrency = opp.ask.getTransactionCurrency();
						earnings.add(earning);

						Map<String, Float> eachEarning = new HashMap<String, Float>();
						for (Earning earnedOpp : earnings) {

							String oppType = earnedOpp.tradableIdentifier + "->" + earnedOpp.transactableCurrency;
							if (eachEarning.get(oppType) == null) {
								eachEarning.put(oppType, earnedOpp.opportunityEarning);
							} else {
								eachEarning.put(oppType, eachEarning.get(oppType) + earnedOpp.opportunityEarning);
							}

						}
						float total = 0;
						for (Map.Entry<String, Float> earns : eachEarning.entrySet()) {
							System.out.println(earns.getKey() + " -> +" + earns.getValue().floatValue() + " BTC.");
							total += earns.getValue().floatValue();
						}
						System.out.println("Total: " + total);
						doSpeak("Our total earnings so far is " + total + " BTC.");
						try {
							// use buffering
							File dest = new File("earnings.ser");
							if (dest.exists()) {
								dest.delete();
							}
							OutputStream file = new FileOutputStream(dest, false);
							OutputStream buffer = new BufferedOutputStream(file);
							ObjectOutput output = new ObjectOutputStream(buffer);
							try {
								output.writeObject(earnings);
								buffer.flush();
							} finally {
								output.close();
							}
						} catch (IOException ex) {
							ex.printStackTrace();
						}

						break;
					}
				} catch (Exception e) {
					System.out.println("Couldn't check our account balance, so skipping the opportunity.");
					e.printStackTrace();
				}
			}

		}
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws InterruptedException, EngineException, AudioException, EngineStateError, PropertyVetoException, KeyManagementException, NoSuchAlgorithmException {
		init("kevin16");
		initSSL(); // Setup the SSL certificate to interact with mtgox over
					// secure http.

		File dest = new File("earnings.ser");
		if (dest.exists()) {
			InputStream file;
			try {
				file = new FileInputStream(dest);
				InputStream buffer = new BufferedInputStream(file);
				ObjectInput input;
				input = new ObjectInputStream(buffer);
				// deserialize the List
				earnings = (List<Earning>) input.readObject();
				input.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		while (true) {
			new Finder().run();
			// Thread.sleep(1000);
		}
	}

	public static void initSSL() throws KeyManagementException, NoSuchAlgorithmException {

		// SSL Certificates trustStore ----------------------------------------
		// Set the SSL certificate for mtgox - Read up on Java Trust store.
		// System.setProperty("javax.net.ssl.trustStore", "trader.jks");
		// System.setProperty("javax.net.ssl.trustStorePassword", "zabbas"); //
		// I

		@SuppressWarnings("deprecation")
		class MyManager implements X509TrustManager {

			public boolean isHostTrusted(X509Certificate[] chain) {
				return true;
			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

			}

		}

		@SuppressWarnings("deprecation")
		TrustManager[] managers = new TrustManager[] { new MyManager() };
		final SSLContext sslContext = SSLContext.getInstance("SSL");
		sslContext.init(null, managers, new SecureRandom());

		HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		});
		// encripted
		// the
		// jks
		// file
		// using
		// this
		// pwd
		// System.setProperty("javax.net.debug","ssl"); //Uncomment for
		// debugging SSL errors

	}

}
