package org.knowm.xchange.currency;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * A Currency class roughly modeled after {@link java.util.Currency}. Each object retains the code
 * it was acquired with -- so {@link #getInstance}("BTC").{@link #getCurrencyCode}() will always be
 * "BTC", even though the proposed ISO 4217 code is "XBT"
 */
public class Currency implements Comparable<Currency>, Serializable {

  private static final long serialVersionUID = -7340731832345284129L;
  private static final Map<String, Currency> currencies = new HashMap<>();

  /** Global currency codes */
  // TODO: Load from json resource
  public static final Currency AED = createCurrency("AED", "United Arab Emirates Dirham", null);

  public static final Currency AFN = createCurrency("AFN", "Afghan Afghani", null);
  public static final Currency ALL = createCurrency("ALL", "Albanian Lek", null);
  public static final Currency AMD = createCurrency("AMD", "Armenian Dram", null);
  public static final Currency ANC = createCurrency("ANC", "Anoncoin", null);
  public static final Currency ANG = createCurrency("ANG", "Netherlands Antillean Guilder", null);
  public static final Currency AOA = createCurrency("AOA", "Angolan Kwanza", null);
  public static final Currency ARN = createCurrency("ARN", "Aeron", null);
  public static final Currency ARS = createCurrency("ARS", "Argentine Peso", null);
  public static final Currency ATOM = createCurrency("ATOM", "Cosmos", null);
  public static final Currency AUD = createCurrency("AUD", "Australian Dollar", null);
  public static final Currency AUR = createCurrency("AUR", "Auroracoin", null);
  public static final Currency AVT = createCurrency("AVT", "Aventus", null);
  public static final Currency AWG = createCurrency("AWG", "Aruban Florin", null);
  public static final Currency AZN = createCurrency("AZN", "Azerbaijani Manat", null);
  public static final Currency BAM =
      createCurrency("BAM", "Bosnia-Herzegovina Convertible Mark", null);
  public static final Currency BAT = createCurrency("BAT", "Basic Attention Token", null);
  public static final Currency BBD = createCurrency("BBD", "Barbadian Dollar", null);
  public static final Currency BC = createCurrency("BC", "BlackCoin", null, "BLK");
  public static final Currency BCC = createCurrency("BCC", "BitConnect", null);
  public static final Currency BCH = createCurrency("BCH", "BitcoinCash", null);
  public static final Currency BCA = createCurrency("BCA", "BitcoinAtom", null);
  public static final Currency BLK = getInstance("BLK");
  public static final Currency BDT = createCurrency("BDT", "Bangladeshi Taka", null);
  public static final Currency BGC = createCurrency("BGC", "Aten 'Black Gold' Coin", null);
  public static final Currency BGN = createCurrency("BGN", "Bulgarian Lev", null);
  public static final Currency BHD = createCurrency("BHD", "Bahraini Dinar", null);
  public static final Currency BIF = createCurrency("BIF", "Burundian Franc", null);
  public static final Currency BMD = createCurrency("BMD", "Bermudan Dollar", null);
  public static final Currency BND = createCurrency("BND", "Brunei Dollar", null);
  public static final Currency BOB = createCurrency("BOB", "Bolivian Boliviano", null);
  public static final Currency BRL = createCurrency("BRL", "Brazilian Real", "R$");
  public static final Currency BSD = createCurrency("BSD", "Bahamian Dollar", null);
  public static final Currency BTC = createCurrency("BTC", "Bitcoin", null, "XBT");
  public static final Currency BTG = createCurrency("BTG", "Bitcoin Gold", null);
  public static final Currency XBT = getInstance("XBT");
  public static final Currency BTN = createCurrency("BTN", "Bhutanese Ngultrum", null);
  public static final Currency BWP = createCurrency("BWP", "Botswanan Pula", null);
  public static final Currency BYR = createCurrency("BYR", "Belarusian Ruble", null);
  public static final Currency BZD = createCurrency("BZD", "Belize Dollar", null);
  public static final Currency CAD = createCurrency("CAD", "Canadian Dollar", null);
  public static final Currency CDF = createCurrency("CDF", "Congolese Franc", null);
  public static final Currency CHF = createCurrency("CHF", "Swiss Franc", null);
  public static final Currency CLF = createCurrency("CLF", "Chilean Unit of Account (UF)", null);
  public static final Currency CLP = createCurrency("CLP", "Chilean Peso", null);
  public static final Currency CNC = createCurrency("CNC", "Chinacoin", null);
  public static final Currency CNY = createCurrency("CNY", "Chinese Yuan", null);
  public static final Currency COP = createCurrency("COP", "Colombian Peso", null);
  public static final Currency CRC = createCurrency("CRC", "Costa Rican Colón", null);
  public static final Currency CUP = createCurrency("CUP", "Cuban Peso", null);
  public static final Currency CVE = createCurrency("CVE", "Cape Verdean Escudo", null);
  public static final Currency CZK = createCurrency("CZK", "Czech Republic Koruna", null);
  public static final Currency DASH = createCurrency("DASH", "Dash", null);
  public static final Currency DCR = createCurrency("DCR", "Decred", null);
  public static final Currency DGB = createCurrency("DGB", "DigiByte", null);
  public static final Currency DJF = createCurrency("DJF", "Djiboutian Franc", null);
  public static final Currency DKK = createCurrency("DKK", "Danish Krone", null);
  public static final Currency DOGE = createCurrency("DOGE", "Dogecoin", null, "XDC", "XDG");
  public static final Currency XDC = getInstance("XDC");
  public static final Currency XDG = getInstance("XDG");
  public static final Currency DOP = createCurrency("DOP", "Dominican Peso", null);
  public static final Currency DGC = createCurrency("DGC", "Digitalcoin", null);
  public static final Currency DVC = createCurrency("DVC", "Devcoin", null);
  public static final Currency DRK = createCurrency("DRK", "Darkcoin", null);
  public static final Currency DZD = createCurrency("DZD", "Algerian Dinar", null);
  public static final Currency EDO = createCurrency("EDO", "Eidoo", null);
  public static final Currency EEK = createCurrency("EEK", "Estonian Kroon", null);
  public static final Currency EGD = createCurrency("EGD", "egoldcoin", null);
  public static final Currency EGP = createCurrency("EGP", "Egyptian Pound", null);
  public static final Currency EOS = createCurrency("EOS", "EOS", null);
  public static final Currency ETB = createCurrency("ETB", "Ethiopian Birr", null);
  public static final Currency ETC = createCurrency("ETC", "Ether Classic", null);
  public static final Currency ETH = createCurrency("ETH", "Ether", null);
  public static final Currency EUR = createCurrency("EUR", "Euro", null);
  public static final Currency FJD = createCurrency("FJD", "Fijian Dollar", null);
  public static final Currency _1ST = createCurrency("1ST", "First Blood", null);
  public static final Currency FKP = createCurrency("FKP", "Falkland Islands Pound", null);
  public static final Currency FTC = createCurrency("FTC", "Feathercoin", null);
  public static final Currency GBP = createCurrency("GBP", "British Pound Sterling", null);
  public static final Currency GEL = createCurrency("GEL", "Georgian Lari", null);
  public static final Currency GHS = createCurrency("GHS", "Ghanaian Cedi", null);
  public static final Currency GHs = createCurrency("GHS", "Gigahashes per second", null);
  public static final Currency GIP = createCurrency("GIP", "Gibraltar Pound", null);
  public static final Currency GMD = createCurrency("GMD", "Gambian Dalasi", null);
  public static final Currency GNF = createCurrency("GNF", "Guinean Franc", null);
  public static final Currency GNO = createCurrency("GNO", "Gnosis", null);
  public static final Currency GNT = createCurrency("GNT", "Golem", null);
  public static final Currency GTQ = createCurrency("GTQ", "Guatemalan Quetzal", null);
  public static final Currency GVT = createCurrency("GVT", "Genesis Vision", null);
  public static final Currency GYD = createCurrency("GYD", "Guyanaese Dollar", null);
  public static final Currency HKD = createCurrency("HKD", "Hong Kong Dollar", null);
  public static final Currency HVN = createCurrency("HVN", "Hive", null);
  public static final Currency HNL = createCurrency("HNL", "Honduran Lempira", null);
  public static final Currency HRK = createCurrency("HRK", "Croatian Kuna", null);
  public static final Currency HTG = createCurrency("HTG", "Haitian Gourde", null);
  public static final Currency HUF = createCurrency("HUF", "Hungarian Forint", null);
  public static final Currency ICN = createCurrency("ICN", "Iconomi", null);
  public static final Currency IDR = createCurrency("IDR", "Indonesian Rupiah", null);
  public static final Currency ILS = createCurrency("ILS", "Israeli New Sheqel", null);
  public static final Currency INR = createCurrency("INR", "Indian Rupee", null);
  public static final Currency IOC = createCurrency("IOC", "I/OCoin", null);
  public static final Currency IOT = createCurrency("IOT", "IOTA", null);
  public static final Currency IQD = createCurrency("IQD", "Iraqi Dinar", null);
  public static final Currency IRR = createCurrency("IRR", "Iranian Rial", null);
  public static final Currency ISK = createCurrency("ISK", "Icelandic Króna", null);
  public static final Currency IXC = createCurrency("IXC", "iXcoin", null);
  public static final Currency JEP = createCurrency("JEP", "Jersey Pound", null);
  public static final Currency JMD = createCurrency("JMD", "Jamaican Dollar", null);
  public static final Currency JOD = createCurrency("JOD", "Jordanian Dinar", null);
  public static final Currency JPY = createCurrency("JPY", "Japanese Yen", null);
  public static final Currency KES = createCurrency("KES", "Kenyan Shilling", null);
  public static final Currency KGS = createCurrency("KGS", "Kyrgystani Som", null);
  public static final Currency KHR = createCurrency("KHR", "Cambodian Riel", null);
  public static final Currency KICK = createCurrency("KICK", "KickCoin", null);
  public static final Currency KMF = createCurrency("KMF", "Comorian Franc", null);
  public static final Currency KPW = createCurrency("KPW", "North Korean Won", null);
  public static final Currency KRW = createCurrency("KRW", "South Korean Won", null);
  public static final Currency KWD = createCurrency("KWD", "Kuwaiti Dinar", null);
  public static final Currency KYD = createCurrency("KYD", "Cayman Islands Dollar", null);
  public static final Currency KZT = createCurrency("KZT", "Kazakhstani Tenge", null);
  public static final Currency LAK = createCurrency("LAK", "Laotian Kip", null);
  public static final Currency LBP = createCurrency("LBP", "Lebanese Pound", null);
  public static final Currency LSK = createCurrency("LSK", "Lisk", null);
  public static final Currency LKR = createCurrency("LKR", "Sri Lankan Rupee", null);
  public static final Currency LRD = createCurrency("LRD", "Liberian Dollar", null);
  public static final Currency LSL = createCurrency("LSL", "Lesotho Loti", null);
  public static final Currency LTC = createCurrency("LTC", "Litecoin", null, "XLT");
  public static final Currency XLT = getInstance("XLT");
  public static final Currency LTL = createCurrency("LTL", "Lithuanian Litas", null);
  public static final Currency LVL = createCurrency("LVL", "Latvian Lats", null);
  public static final Currency LYD = createCurrency("LYD", "Libyan Dinar", null);
  public static final Currency MAD = createCurrency("MAD", "Moroccan Dirham", null);
  public static final Currency MDL = createCurrency("MDL", "Moldovan Leu", null);
  public static final Currency MEC = createCurrency("MEC", "MegaCoin", null);
  public static final Currency MGA = createCurrency("MGA", "Malagasy Ariary", null);
  public static final Currency MKD = createCurrency("MKD", "Macedonian Denar", null);
  public static final Currency MLN = createCurrency("MLN", "Melonport", null);
  public static final Currency MMK = createCurrency("MMK", "Myanma Kyat", null);
  public static final Currency MNT = createCurrency("MNT", "Mongolian Tugrik", null);
  public static final Currency MOP = createCurrency("MOP", "Macanese Pataca", null);
  public static final Currency MRO = createCurrency("MRO", "Mauritanian Ouguiya", null);
  public static final Currency MSC = createCurrency("MSC", "Mason Coin", null);
  public static final Currency MUR = createCurrency("MUR", "Mauritian Rupee", null);
  public static final Currency MVR = createCurrency("MVR", "Maldivian Rufiyaa", null);
  public static final Currency MWK = createCurrency("MWK", "Malawian Kwacha", null);
  public static final Currency MXN = createCurrency("MXN", "Mexican Peso", null);
  public static final Currency MYR = createCurrency("MYR", "Malaysian Ringgit", null);
  public static final Currency MZN = createCurrency("MZN", "Mozambican Metical", null);
  public static final Currency NAD = createCurrency("NAD", "Namibian Dollar", null);
  public static final Currency NOBS = createCurrency("NOBS", "No BS Crypto", null);
  public static final Currency NEO = createCurrency("NEO", "NEO", null);
  public static final Currency NGN = createCurrency("NGN", "Nigerian Naira", null);
  public static final Currency NIO = createCurrency("NIO", "Nicaraguan Córdoba", null);
  public static final Currency NMC = createCurrency("NMC", "Namecoin", null);
  public static final Currency NOK = createCurrency("NOK", "Norwegian Krone", null);
  public static final Currency NPR = createCurrency("NPR", "Nepalese Rupee", null);
  public static final Currency NVC = createCurrency("NVC", "Novacoin", null);
  public static final Currency NXT = createCurrency("NXT", "Nextcoin", null);
  public static final Currency NZD = createCurrency("NZD", "New Zealand Dollar", null);
  public static final Currency OMG = createCurrency("OMG", "OmiseGO", null);
  public static final Currency OMR = createCurrency("OMR", "Omani Rial", null);
  public static final Currency PAB = createCurrency("PAB", "Panamanian Balboa", null);
  public static final Currency PEN = createCurrency("PEN", "Peruvian Nuevo Sol", null);
  public static final Currency PGK = createCurrency("PGK", "Papua New Guinean Kina", null);
  public static final Currency PHP = createCurrency("PHP", "Philippine Peso", null);
  public static final Currency PKR = createCurrency("PKR", "Pakistani Rupee", null);
  public static final Currency PLN = createCurrency("PLN", "Polish Zloty", null);
  public static final Currency POT = createCurrency("POT", "PotCoin", null);
  public static final Currency PPC = createCurrency("PPC", "Peercoin", null);
  public static final Currency PYG = createCurrency("PYG", "Paraguayan Guarani", null);
  public static final Currency QAR = createCurrency("QAR", "Qatari Rial", null);
  public static final Currency QRK = createCurrency("QRK", "QuarkCoin", null);
  public static final Currency QTUM = createCurrency("QTUM", "Qtum", null);
  public static final Currency REP = createCurrency("REP", "Augur", null);
  public static final Currency RON = createCurrency("RON", "Romanian Leu", null);
  public static final Currency RSD = createCurrency("RSD", "Serbian Dinar", null);
  public static final Currency RUB = createCurrency("RUB", "Russian Ruble", null);
  public static final Currency RUR = createCurrency("RUR", "Old Russian Ruble", null);
  public static final Currency RWF = createCurrency("RWF", "Rwandan Franc", null);
  public static final Currency SAR = createCurrency("SAR", "Saudi Riyal", null);
  public static final Currency SBC = createCurrency("SBC", "Stablecoin", null);
  public static final Currency SBD = createCurrency("SBD", "Solomon Islands Dollar", null);
  public static final Currency SC = createCurrency("SC", "Siacoin", null);
  public static final Currency SCR = createCurrency("SCR", "Seychellois Rupee", null);
  public static final Currency SDG = createCurrency("SDG", "Sudanese Pound", null);
  public static final Currency SEK = createCurrency("SEK", "Swedish Krona", null);
  public static final Currency SGD = createCurrency("SGD", "Singapore Dollar", null);
  public static final Currency SHP = createCurrency("SHP", "Saint Helena Pound", null);
  public static final Currency SLL = createCurrency("SLL", "Sierra Leonean Leone", null);
  public static final Currency SMART = createCurrency("SMART", "SmartCash", null);
  public static final Currency SOS = createCurrency("SOS", "Somali Shilling", null);
  public static final Currency SRD = createCurrency("SRD", "Surinamese Dollar", null);
  public static final Currency START = createCurrency("START", "startcoin", null);
  public static final Currency STEEM = createCurrency("STEEM", "Steem", null);
  public static final Currency STD = createCurrency("STD", "São Tomé and Príncipe Dobra", null);
  public static final Currency STR = createCurrency("STR", "Stellar", null);
  public static final Currency STRAT = createCurrency("STRAT", "Stratis", null);
  public static final Currency SVC = createCurrency("SVC", "Salvadoran Colón", null);
  public static final Currency SYP = createCurrency("SYP", "Syrian Pound", null);
  public static final Currency SZL = createCurrency("SZL", "Swazi Lilangeni", null);
  public static final Currency THB = createCurrency("THB", "Thai Baht", null);
  public static final Currency TJS = createCurrency("TJS", "Tajikistani Somoni", null);
  public static final Currency TMT = createCurrency("TMT", "Turkmenistani Manat", null);
  public static final Currency TND = createCurrency("TND", "Tunisian Dinar", null);
  public static final Currency TOP = createCurrency("TOP", "Tongan Paʻanga", null);
  public static final Currency TRC = createCurrency("TRC", "Terracoin", null);
  public static final Currency TRY = createCurrency("TRY", "Turkish Lira", null);
  public static final Currency TTD = createCurrency("TTD", "Trinidad and Tobago Dollar", null);
  public static final Currency TWD = createCurrency("TWD", "New Taiwan Dollar", null);
  public static final Currency TZS = createCurrency("TZS", "Tanzanian Shilling", null);
  public static final Currency UAH = createCurrency("UAH", "Ukrainian Hryvnia", null);
  public static final Currency UGX = createCurrency("UGX", "Ugandan Shilling", null);
  public static final Currency USD = createCurrency("USD", "United States Dollar", null);
  public static final Currency USDC = createCurrency("USDC", "USD Coin", null, "UDC");
  public static final Currency USDT = createCurrency("USDT", "Tether USD Anchor", null);
  public static final Currency USDE = createCurrency("USDE", "Unitary Status Dollar eCoin", null);
  public static final Currency UTC = createCurrency("UTC", "Ultracoin", null);
  public static final Currency UYU = createCurrency("UYU", "Uruguayan Peso", null);
  public static final Currency UZS = createCurrency("UZS", "Uzbekistan Som", null);
  public static final Currency VEF = createCurrency("VEF", "Venezuelan Bolívar", null);
  public static final Currency VET = createCurrency("VET", "Hub Culture's Vet", null, "VEN");
  public static final Currency VEN = createCurrency("VEN", "Hub Culture's Ven", null, "XVN");
  public static final Currency XTZ = createCurrency("XTZ", "Tezos", null);
  public static final Currency XVN = getInstance("XVN");
  public static final Currency VIB = createCurrency("VIB", "Viberate", null);
  public static final Currency VND = createCurrency("VND", "Vietnamese Dong", null);
  public static final Currency VUV = createCurrency("VUV", "Vanuatu Vatu", null);
  public static final Currency WDC = createCurrency("WDC", "WorldCoin", null);
  public static final Currency WST = createCurrency("WST", "Samoan Tala", null);
  public static final Currency XAF = createCurrency("XAF", "CFA Franc BEAC", null);
  public static final Currency XAS = createCurrency("XAS", "Asch", null);
  public static final Currency XAUR = createCurrency("XAUR", "Xaurum", null);
  public static final Currency XCD = createCurrency("XCD", "East Caribbean Dollar", null);
  public static final Currency XDR = createCurrency("XDR", "Special Drawing Rights", null);
  public static final Currency XEM = createCurrency("XEM", "NEM", null);
  public static final Currency XLM = createCurrency("XLM", "Stellar Lumen", null);
  public static final Currency XMR = createCurrency("XMR", "Monero", null);
  public static final Currency XRB = createCurrency("XRB", "Rai Blocks", null);
  public static final Currency XOF = createCurrency("XOF", "CFA Franc BCEAO", null);
  public static final Currency XPF = createCurrency("XPF", "CFP Franc", null);
  public static final Currency XPM = createCurrency("XPM", "Primecoin", null);
  public static final Currency XRP = createCurrency("XRP", "Ripple", null);
  public static final Currency YBC = createCurrency("YBC", "YbCoin", null);
  public static final Currency YER = createCurrency("YER", "Yemeni Rial", null);
  public static final Currency ZAR = createCurrency("ZAR", "South African Rand", null);
  public static final Currency ZEC = createCurrency("ZEC", "Zcash", null);
  public static final Currency ZEN = createCurrency("ZEN", "ZenCash", null);
  public static final Currency ZMK = createCurrency("ZMK", "Zambian Kwacha", null);
  public static final Currency ZRC = createCurrency("ZRC", "ziftrCOIN", null);
  public static final Currency ZWL = createCurrency("ZWL", "Zimbabwean Dollar", null);

  // Bitmex futures settlement dates
  public static final Currency H18 = createCurrency("H18", "March 30th", null);
  public static final Currency M18 = createCurrency("M18", "June 29th", null);
  public static final Currency U18 = createCurrency("U18", "September 28th", null);
  public static final Currency Z18 = createCurrency("Z18", "December 28th", null);
  public static final Currency H19 = createCurrency("H19", "March 29th", null);
  public static final Currency M19 = createCurrency("M19", "June 28th", null);

  // Cryptos
  public static final Currency BNK = createCurrency("BNK", "Bankera Coin", null);
  public static final Currency BNB = createCurrency("BNB", "Binance Coin", null);
  public static final Currency QSP = createCurrency("QSP", "Quantstamp", null);
  public static final Currency IOTA = createCurrency("IOTA", "Iota", null);
  public static final Currency YOYO = createCurrency("YOYO", "Yoyow", null);
  public static final Currency BTS = createCurrency("BTS", "Bitshare", null);
  public static final Currency ICX = createCurrency("ICX", "Icon", null);
  public static final Currency MCO = createCurrency("MCO", "Monaco", null);
  public static final Currency CND = createCurrency("CND", "Cindicator", null);
  public static final Currency XVG = createCurrency("XVG", "Verge", null);
  public static final Currency POE = createCurrency("POE", "Po.et", null);
  public static final Currency TRX = createCurrency("TRX", "Tron", null);
  public static final Currency ADA = createCurrency("ADA", "Cardano", null);
  public static final Currency FUN = createCurrency("FUN", "FunFair", null);
  public static final Currency HSR = createCurrency("HSR", "Hshare", null);
  public static final Currency LEND = createCurrency("LEND", "ETHLend", null);
  public static final Currency ELF = createCurrency("ELF", "aelf", null);
  public static final Currency STORJ = createCurrency("STORJ", "Storj", null);
  public static final Currency MOD = createCurrency("MOD", "Modum", null);

  private final String code;

  private final CurrencyAttributes attributes;

  /** Public constructor. Links to an existing currency. */
  public Currency(String code) {

    this.code = code;
    this.attributes = getInstance(code).attributes;
  }

  private Currency(String alternativeCode, CurrencyAttributes attributes) {

    this.code = alternativeCode;
    this.attributes = attributes;
  }

  /** Gets the set of available currencies. */
  public static SortedSet<Currency> getAvailableCurrencies() {

    return new TreeSet<>(currencies.values());
  }

  /** Gets the set of available currency codes. */
  public static SortedSet<String> getAvailableCurrencyCodes() {

    return new TreeSet<>(currencies.keySet());
  }

  /** Returns a Currency instance for the given currency code. */
  @JsonCreator
  public static Currency getInstance(String currencyCode) {

    Currency currency = getInstanceNoCreate(currencyCode.toUpperCase());

    if (currency == null) {
      return createCurrency(currencyCode.toUpperCase(), null, null);
    } else {
      return currency;
    }
  }

  /** Returns the Currency instance for the given currency code only if one already exists. */
  public static Currency getInstanceNoCreate(String currencyCode) {

    return currencies.get(currencyCode.toUpperCase());
  }

  /**
   * Factory
   *
   * @param commonCode commonly used code for this currency: "BTC"
   * @param name Name of the currency: "Bitcoin"
   * @param unicode Unicode symbol for the currency: "\u20BF" or "฿"
   * @param alternativeCodes Alternative codes for the currency: "XBT"
   */
  private static Currency createCurrency(
      String commonCode, String name, String unicode, String... alternativeCodes) {

    CurrencyAttributes attributes =
        new CurrencyAttributes(commonCode, name, unicode, alternativeCodes);

    Currency currency = new Currency(commonCode, attributes);

    for (String code : attributes.codes) {
      if (commonCode.equals(code)) {
        // common code will always be part of the currencies map

        currencies.put(code, currency);

      } else if (!currencies.containsKey(code)) {
        // alternative codes will never overwrite common codes

        currencies.put(code, new Currency(code, attributes));
      }
    }

    return currency;
  }

  /** Gets the currency code originally used to acquire this object. */
  @JsonValue
  public String getCurrencyCode() {

    return code;
  }

  /**
   * Gets the equivalent object with the passed code.
   *
   * <p>This is useful in case some currencies share codes, such that {@link #getInstance(String)}
   * may return the wrong currency.
   *
   * @param code The code the returned object will evaluate to
   * @return A Currency representing the same currency but having the passed currency code
   * @throws IllegalArgumentException if the passed code is not listed for this currency
   */
  public Currency getCodeCurrency(String code) {

    if (code.equals(this.code)) return this;

    Currency currency = getInstance(code);
    if (currency.equals(this)) return currency;

    if (!attributes.codes.contains(code))
      throw new IllegalArgumentException("Code not listed for this currency");

    return new Currency(code, attributes);
  }

  /**
   * Gets the equivalent object with an ISO 4217 code, or if none a code which looks ISO compatible
   * (starts with an X), or the constructed currency code if neither exist.
   */
  public Currency getIso4217Currency() {

    if (attributes.isoCode == null) return this;

    // The logic for setting isoCode is in CurrencyAttributes

    return getCodeCurrency(attributes.isoCode);
  }

  /** Gets the equivalent object that was created with the "commonly used" code. */
  public Currency getCommonlyUsedCurrency() {

    return getCodeCurrency(attributes.commonCode);
  }

  /** Gets the set of all currency codes associated with this currency. */
  public Set<String> getCurrencyCodes() {

    return attributes.codes;
  }

  /** Gets the unicode symbol of this currency. */
  public String getSymbol() {

    return attributes.unicode;
  }

  /** Gets the name that is suitable for displaying this currency. */
  public String getDisplayName() {

    return attributes.name;
  }

  @Override
  public String toString() {

    return code;
  }

  @Override
  public int hashCode() {

    return attributes.hashCode();
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Currency other = (Currency) obj;

    return attributes.equals(other.attributes);
  }

  @Override
  public int compareTo(Currency o) {

    if (attributes.equals(o.attributes)) return 0;

    int comparison = code.compareTo(o.code);
    if (comparison == 0) comparison = getDisplayName().compareTo(o.getDisplayName());
    if (comparison == 0) comparison = hashCode() - o.hashCode();
    return comparison;
  }

  private static class CurrencyAttributes implements Serializable {

    private static final long serialVersionUID = -5575649542242146958L;

    public final Set<String> codes;
    public final String isoCode;
    public final String commonCode;
    public final String name;
    public final String unicode;

    public CurrencyAttributes(
        String commonCode, String name, String unicode, String... alternativeCodes) {

      if (alternativeCodes.length > 0) {
        this.codes = new TreeSet<>(Arrays.asList(alternativeCodes));
        this.codes.add(commonCode);
      } else {
        this.codes = Collections.singleton(commonCode);
      }

      String possibleIsoProposalCryptoCode = null;

      java.util.Currency javaCurrency = null;
      for (String code : this.codes) {
        if (javaCurrency == null) {
          try {
            javaCurrency = java.util.Currency.getInstance(code);
          } catch (IllegalArgumentException e) {
          }
        }
        if (code.startsWith("X")) {
          possibleIsoProposalCryptoCode = code;
        }
      }

      if (javaCurrency != null) {
        this.isoCode = javaCurrency.getCurrencyCode();
      } else {
        this.isoCode = possibleIsoProposalCryptoCode;
      }

      this.commonCode = commonCode;

      if (name != null) {
        this.name = name;
      } else if (javaCurrency != null) {
        this.name = javaCurrency.getDisplayName();
      } else {
        this.name = commonCode;
      }

      if (unicode != null) {
        this.unicode = unicode;
      } else if (javaCurrency != null) {
        this.unicode = javaCurrency.getSymbol();
      } else {
        this.unicode = commonCode;
      }
    }

    @Override
    public int hashCode() {
      return commonCode.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      CurrencyAttributes other = (CurrencyAttributes) obj;
      if (commonCode == null) {
        if (other.commonCode != null) return false;
      } else if (!commonCode.equals(other.commonCode)) return false;
      return true;
    }
  }
}
