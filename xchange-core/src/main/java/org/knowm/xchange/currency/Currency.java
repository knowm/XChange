package org.knowm.xchange.currency;

import java.io.Serializable;
import java.util.*;

public interface Currency extends Comparable<Currency>, Serializable {
  Map<String, Currency> currencies = new HashMap<>();
  /** Global currency codes */
  // TODO: Load from json resource
  Currency AED = createCurrency("AED", "United Arab Emirates Dirham", null);

  Currency AFN = createCurrency("AFN", "Afghan Afghani", null);
  Currency ALL = createCurrency("ALL", "Albanian Lek", null);
  Currency AMD = createCurrency("AMD", "Armenian Dram", null);
  Currency ANC = createCurrency("ANC", "Anoncoin", null);
  Currency ANG = createCurrency("ANG", "Netherlands Antillean Guilder", null);
  Currency AOA = createCurrency("AOA", "Angolan Kwanza", null);
  Currency ARN = createCurrency("ARN", "Aeron", null);
  Currency ARS = createCurrency("ARS", "Argentine Peso", null);
  Currency AUD = createCurrency("AUD", "Australian Dollar", null);
  Currency AUR = createCurrency("AUR", "Auroracoin", null);
  Currency AVT = createCurrency("AVT", "Aventus", null);
  Currency AWG = createCurrency("AWG", "Aruban Florin", null);
  Currency AZN = createCurrency("AZN", "Azerbaijani Manat", null);
  Currency BAM = createCurrency("BAM", "Bosnia-Herzegovina Convertible Mark", null);
  Currency BAT = createCurrency("BAT", "Basic Attention Token", null);
  Currency BBD = createCurrency("BBD", "Barbadian Dollar", null);
  Currency BC = createCurrency("BC", "BlackCoin", null, "BLK");
  Currency BCC = createCurrency("BCC", "BitConnect", null);
  Currency BCH = createCurrency("BCH", "BitcoinCash", null);
  Currency BLK = valueOf("BLK");
  Currency BDT = createCurrency("BDT", "Bangladeshi Taka", null);
  Currency BGC = createCurrency("BGC", "Aten 'Black Gold' Coin", null);
  Currency BGN = createCurrency("BGN", "Bulgarian Lev", null);
  Currency BHD = createCurrency("BHD", "Bahraini Dinar", null);
  Currency BIF = createCurrency("BIF", "Burundian Franc", null);
  Currency BMD = createCurrency("BMD", "Bermudan Dollar", null);
  Currency BND = createCurrency("BND", "Brunei Dollar", null);
  Currency BOB = createCurrency("BOB", "Bolivian Boliviano", null);
  Currency BRL = createCurrency("BRL", "Brazilian Real", "R$");
  Currency BSD = createCurrency("BSD", "Bahamian Dollar", null);
  Currency BTC = createCurrency("BTC", "Bitcoin", null, "XBT");
  Currency BTG = createCurrency("BTG", "Bitcoin Gold", null);
  Currency XBT = valueOf("XBT");
  Currency BTN = createCurrency("BTN", "Bhutanese Ngultrum", null);
  Currency BWP = createCurrency("BWP", "Botswanan Pula", null);
  Currency BYR = createCurrency("BYR", "Belarusian Ruble", null);
  Currency BZD = createCurrency("BZD", "Belize Dollar", null);
  Currency CAD = createCurrency("CAD", "Canadian Dollar", null);
  Currency CDF = createCurrency("CDF", "Congolese Franc", null);
  Currency CHF = createCurrency("CHF", "Swiss Franc", null);
  Currency CLF = createCurrency("CLF", "Chilean Unit of Account (UF)", null);
  Currency CLP = createCurrency("CLP", "Chilean Peso", null);
  Currency CNC = createCurrency("CNC", "Chinacoin", null);
  Currency CNY = createCurrency("CNY", "Chinese Yuan", null);
  Currency COP = createCurrency("COP", "Colombian Peso", null);
  Currency CRC = createCurrency("CRC", "Costa Rican Colón", null);
  Currency CUP = createCurrency("CUP", "Cuban Peso", null);
  Currency CVE = createCurrency("CVE", "Cape Verdean Escudo", null);
  Currency CZK = createCurrency("CZK", "Czech Republic Koruna", null);
  Currency DASH = createCurrency("DASH", "Dash", null);
  Currency DCR = createCurrency("DCR", "Decred", null);
  Currency DGB = createCurrency("DGB", "DigiByte", null);
  Currency DJF = createCurrency("DJF", "Djiboutian Franc", null);
  Currency DKK = createCurrency("DKK", "Danish Krone", null);
  Currency DOGE = createCurrency("DOGE", "Dogecoin", null, "XDC", "XDG");
  Currency XDC = valueOf("XDC");
  Currency XDG = valueOf("XDG");
  Currency DOP = createCurrency("DOP", "Dominican Peso", null);
  Currency DGC = createCurrency("DGC", "Digitalcoin", null);
  Currency DVC = createCurrency("DVC", "Devcoin", null);
  Currency DRK = createCurrency("DRK", "Darkcoin", null);
  Currency DZD = createCurrency("DZD", "Algerian Dinar", null);
  Currency EDO = createCurrency("EDO", "Eidoo", null);
  Currency EEK = createCurrency("EEK", "Estonian Kroon", null);
  Currency EGD = createCurrency("EGD", "egoldcoin", null);
  Currency EGP = createCurrency("EGP", "Egyptian Pound", null);
  Currency EOS = createCurrency("EOS", "EOS", null);
  Currency ETB = createCurrency("ETB", "Ethiopian Birr", null);
  Currency ETC = createCurrency("ETC", "Ether Classic", null);
  Currency ETH = createCurrency("ETH", "Ether", null);
  Currency EUR = createCurrency("EUR", "Euro", null);
  Currency FJD = createCurrency("FJD", "Fijian Dollar", null);
  Currency _1ST = createCurrency("1ST", "First Blood", null);
  Currency FKP = createCurrency("FKP", "Falkland Islands Pound", null);
  Currency FTC = createCurrency("FTC", "Feathercoin", null);
  Currency GBP = createCurrency("GBP", "British Pound Sterling", null);
  Currency GEL = createCurrency("GEL", "Georgian Lari", null);
  Currency GHS = createCurrency("GHS", "Ghanaian Cedi", null);
  Currency GHs = createCurrency("GHS", "Gigahashes per second", null);
  Currency GIP = createCurrency("GIP", "Gibraltar Pound", null);
  Currency GMD = createCurrency("GMD", "Gambian Dalasi", null);
  Currency GNF = createCurrency("GNF", "Guinean Franc", null);
  Currency GNO = createCurrency("GNO", "Gnosis", null);
  Currency GNT = createCurrency("GNT", "Golem", null);
  Currency GTQ = createCurrency("GTQ", "Guatemalan Quetzal", null);
  Currency GYD = createCurrency("GYD", "Guyanaese Dollar", null);
  Currency HKD = createCurrency("HKD", "Hong Kong Dollar", null);
  Currency HVN = createCurrency("HVN", "Hive", null);
  Currency HNL = createCurrency("HNL", "Honduran Lempira", null);
  Currency HRK = createCurrency("HRK", "Croatian Kuna", null);
  Currency HTG = createCurrency("HTG", "Haitian Gourde", null);
  Currency HUF = createCurrency("HUF", "Hungarian Forint", null);
  Currency ICN = createCurrency("ICN", "Iconomi", null);
  Currency IDR = createCurrency("IDR", "Indonesian Rupiah", null);
  Currency ILS = createCurrency("ILS", "Israeli New Sheqel", null);
  Currency INR = createCurrency("INR", "Indian Rupee", null);
  Currency IOC = createCurrency("IOC", "I/OCoin", null);
  Currency IOT = createCurrency("IOT", "IOTA", null);
  Currency IQD = createCurrency("IQD", "Iraqi Dinar", null);
  Currency IRR = createCurrency("IRR", "Iranian Rial", null);
  Currency ISK = createCurrency("ISK", "Icelandic Króna", null);
  Currency IXC = createCurrency("IXC", "iXcoin", null);
  Currency JEP = createCurrency("JEP", "Jersey Pound", null);
  Currency JMD = createCurrency("JMD", "Jamaican Dollar", null);
  Currency JOD = createCurrency("JOD", "Jordanian Dinar", null);
  Currency JPY = createCurrency("JPY", "Japanese Yen", null);
  Currency KES = createCurrency("KES", "Kenyan Shilling", null);
  Currency KGS = createCurrency("KGS", "Kyrgystani Som", null);
  Currency KHR = createCurrency("KHR", "Cambodian Riel", null);
  Currency KICK = createCurrency("KICK", "KickCoin", null);
  Currency KMF = createCurrency("KMF", "Comorian Franc", null);
  Currency KPW = createCurrency("KPW", "North Korean Won", null);
  Currency KRW = createCurrency("KRW", "South Korean Won", null);
  Currency KWD = createCurrency("KWD", "Kuwaiti Dinar", null);
  Currency KYD = createCurrency("KYD", "Cayman Islands Dollar", null);
  Currency KZT = createCurrency("KZT", "Kazakhstani Tenge", null);
  Currency LAK = createCurrency("LAK", "Laotian Kip", null);
  Currency LBP = createCurrency("LBP", "Lebanese Pound", null);
  Currency LSK = createCurrency("LSK", "Lisk", null);
  Currency LKR = createCurrency("LKR", "Sri Lankan Rupee", null);
  Currency LRD = createCurrency("LRD", "Liberian Dollar", null);
  Currency LSL = createCurrency("LSL", "Lesotho Loti", null);
  Currency LTC = createCurrency("LTC", "Litecoin", null, "XLT");
  Currency XLT = valueOf("XLT");
  Currency LTL = createCurrency("LTL", "Lithuanian Litas", null);
  Currency LVL = createCurrency("LVL", "Latvian Lats", null);
  Currency LYD = createCurrency("LYD", "Libyan Dinar", null);
  Currency MAD = createCurrency("MAD", "Moroccan Dirham", null);
  Currency MDL = createCurrency("MDL", "Moldovan Leu", null);
  Currency MEC = createCurrency("MEC", "MegaCoin", null);
  Currency MGA = createCurrency("MGA", "Malagasy Ariary", null);
  Currency MKD = createCurrency("MKD", "Macedonian Denar", null);
  Currency MLN = createCurrency("MLN", "Melonport", null);
  Currency MMK = createCurrency("MMK", "Myanma Kyat", null);
  Currency MNT = createCurrency("MNT", "Mongolian Tugrik", null);
  Currency MOP = createCurrency("MOP", "Macanese Pataca", null);
  Currency MRO = createCurrency("MRO", "Mauritanian Ouguiya", null);
  Currency MSC = createCurrency("MSC", "Mason Coin", null);
  Currency MUR = createCurrency("MUR", "Mauritian Rupee", null);
  Currency MVR = createCurrency("MVR", "Maldivian Rufiyaa", null);
  Currency MWK = createCurrency("MWK", "Malawian Kwacha", null);
  Currency MXN = createCurrency("MXN", "Mexican Peso", null);
  Currency MYR = createCurrency("MYR", "Malaysian Ringgit", null);
  Currency MZN = createCurrency("MZN", "Mozambican Metical", null);
  Currency NAD = createCurrency("NAD", "Namibian Dollar", null);
  Currency NEO = createCurrency("NEO", "NEO", null);
  Currency NGN = createCurrency("NGN", "Nigerian Naira", null);
  Currency NIO = createCurrency("NIO", "Nicaraguan Córdoba", null);
  Currency NMC = createCurrency("NMC", "Namecoin", null);
  Currency NOK = createCurrency("NOK", "Norwegian Krone", null);
  Currency NPR = createCurrency("NPR", "Nepalese Rupee", null);
  Currency NVC = createCurrency("NVC", "Novacoin", null);
  Currency NXT = createCurrency("NXT", "Nextcoin", null);
  Currency NZD = createCurrency("NZD", "New Zealand Dollar", null);
  Currency OMG = createCurrency("OMG", "OmiseGO", null);
  Currency OMR = createCurrency("OMR", "Omani Rial", null);
  Currency PAB = createCurrency("PAB", "Panamanian Balboa", null);
  Currency PEN = createCurrency("PEN", "Peruvian Nuevo Sol", null);
  Currency PGK = createCurrency("PGK", "Papua New Guinean Kina", null);
  Currency PHP = createCurrency("PHP", "Philippine Peso", null);
  Currency PKR = createCurrency("PKR", "Pakistani Rupee", null);
  Currency PLN = createCurrency("PLN", "Polish Zloty", null);
  Currency POT = createCurrency("POT", "PotCoin", null);
  Currency PPC = createCurrency("PPC", "Peercoin", null);
  Currency PYG = createCurrency("PYG", "Paraguayan Guarani", null);
  Currency QAR = createCurrency("QAR", "Qatari Rial", null);
  Currency QRK = createCurrency("QRK", "QuarkCoin", null);
  Currency QTUM = createCurrency("QTUM", "Qtum", null);
  Currency REP = createCurrency("REP", "Augur", null);
  Currency RON = createCurrency("RON", "Romanian Leu", null);
  Currency RSD = createCurrency("RSD", "Serbian Dinar", null);
  Currency RUB = createCurrency("RUB", "Russian Ruble", null);
  Currency RUR = createCurrency("RUR", "Old Russian Ruble", null);
  Currency RWF = createCurrency("RWF", "Rwandan Franc", null);
  Currency SAR = createCurrency("SAR", "Saudi Riyal", null);
  Currency SBC = createCurrency("SBC", "Stablecoin", null);
  Currency SBD = createCurrency("SBD", "Solomon Islands Dollar", null);
  Currency SC = createCurrency("SC", "Siacoin", null);
  Currency SCR = createCurrency("SCR", "Seychellois Rupee", null);
  Currency SDG = createCurrency("SDG", "Sudanese Pound", null);
  Currency SEK = createCurrency("SEK", "Swedish Krona", null);
  Currency SGD = createCurrency("SGD", "Singapore Dollar", null);
  Currency SHP = createCurrency("SHP", "Saint Helena Pound", null);
  Currency SLL = createCurrency("SLL", "Sierra Leonean Leone", null);
  Currency SOS = createCurrency("SOS", "Somali Shilling", null);
  Currency SRD = createCurrency("SRD", "Surinamese Dollar", null);
  Currency START = createCurrency("START", "startcoin", null);
  Currency STEEM = createCurrency("STEEM", "Steem", null);
  Currency STD = createCurrency("STD", "São Tomé and Príncipe Dobra", null);
  Currency STR = createCurrency("STR", "Stellar", null);
  Currency STRAT = createCurrency("STRAT", "Stratis", null);
  Currency SVC = createCurrency("SVC", "Salvadoran Colón", null);
  Currency SYP = createCurrency("SYP", "Syrian Pound", null);
  Currency SZL = createCurrency("SZL", "Swazi Lilangeni", null);
  Currency THB = createCurrency("THB", "Thai Baht", null);
  Currency TJS = createCurrency("TJS", "Tajikistani Somoni", null);
  Currency TMT = createCurrency("TMT", "Turkmenistani Manat", null);
  Currency TND = createCurrency("TND", "Tunisian Dinar", null);
  Currency TOP = createCurrency("TOP", "Tongan Paʻanga", null);
  Currency TRC = createCurrency("TRC", "Terracoin", null);
  Currency TRY = createCurrency("TRY", "Turkish Lira", null);
  Currency TTD = createCurrency("TTD", "Trinidad and Tobago Dollar", null);
  Currency TWD = createCurrency("TWD", "New Taiwan Dollar", null);
  Currency TZS = createCurrency("TZS", "Tanzanian Shilling", null);
  Currency UAH = createCurrency("UAH", "Ukrainian Hryvnia", null);
  Currency UGX = createCurrency("UGX", "Ugandan Shilling", null);
  Currency USD = createCurrency("USD", "United States Dollar", null);
  Currency USDT = createCurrency("USDT", "Tether USD Anchor", null);
  Currency USDE = createCurrency("USDE", "Unitary Status Dollar eCoin", null);
  Currency UTC = createCurrency("UTC", "Ultracoin", null);
  Currency UYU = createCurrency("UYU", "Uruguayan Peso", null);
  Currency UZS = createCurrency("UZS", "Uzbekistan Som", null);
  Currency VEF = createCurrency("VEF", "Venezuelan Bolívar", null);
  Currency VEN = createCurrency("VEN", "Hub Culture's Ven", null, "XVN");
  Currency XVN = valueOf("XVN");
  Currency VIB = createCurrency("VIB", "Viberate", null);
  Currency VND = createCurrency("VND", "Vietnamese Dong", null);
  Currency VUV = createCurrency("VUV", "Vanuatu Vatu", null);
  Currency WDC = createCurrency("WDC", "WorldCoin", null);
  Currency WST = createCurrency("WST", "Samoan Tala", null);
  Currency XAF = createCurrency("XAF", "CFA Franc BEAC", null);
  Currency XAS = createCurrency("XAS", "Asch", null);
  Currency XAUR = createCurrency("XAUR", "Xaurum", null);
  Currency XCD = createCurrency("XCD", "East Caribbean Dollar", null);
  Currency XDR = createCurrency("XDR", "Special Drawing Rights", null);
  Currency XEM = createCurrency("XEM", "NEM", null);
  Currency XLM = createCurrency("XLM", "Stellar Lumen", null);
  Currency XMR = createCurrency("XMR", "Monero", null);
  Currency XRB = createCurrency("XRB", "Rai Blocks", null);
  Currency XOF = createCurrency("XOF", "CFA Franc BCEAO", null);
  Currency XPF = createCurrency("XPF", "CFP Franc", null);
  Currency XPM = createCurrency("XPM", "Primecoin", null);
  Currency XRP = createCurrency("XRP", "Ripple", null);
  Currency YBC = createCurrency("YBC", "YbCoin", null);
  Currency YER = createCurrency("YER", "Yemeni Rial", null);
  Currency ZAR = createCurrency("ZAR", "South African Rand", null);
  Currency ZEC = createCurrency("ZEC", "Zcash", null);
  Currency ZMK = createCurrency("ZMK", "Zambian Kwacha", null);
  Currency ZRC = createCurrency("ZRC", "ziftrCOIN", null);
  Currency ZWL = createCurrency("ZWL", "Zimbabwean Dollar", null);

  // Cryptos
  Currency BNB = createCurrency("BNB", "Binance Coin", null);
  Currency QSP = createCurrency("QSP", "Quantstamp", null);
  Currency IOTA = createCurrency("IOTA", "Iota", null);
  Currency YOYO = createCurrency("YOYO", "Yoyow", null);
  Currency BTS = createCurrency("BTS", "Bitshare", null);
  Currency ICX = createCurrency("ICX", "Icon", null);
  Currency MCO = createCurrency("MCO", "Monaco", null);
  Currency CND = createCurrency("CND", "Cindicator", null);
  Currency XVG = createCurrency("XVG", "Verge", null);
  Currency POE = createCurrency("POE", "Po.et", null);
  Currency TRX = createCurrency("TRX", "Tron", null);
  Currency ADA = createCurrency("ADA", "Cardano", null);
  Currency FUN = createCurrency("FUN", "FunFair", null);
  Currency HSR = createCurrency("HSR", "Hshare", null);
  Currency LEND = createCurrency("LEND", "ETHLend", null);
  Currency ELF = createCurrency("ELF", "aelf", null);
  Currency STORJ = createCurrency("STORJ", "Storj", null);
  Currency MOD = createCurrency("MOD", "Modum", null);

  /** Gets the set of available currencies. */
  static SortedSet<Currency> getAvailableCurrencies() {

    return new TreeSet<>(currencies.values());
  }

  /** Gets the set of available currency codes. */
  static SortedSet<String> getAvailableCurrencyCodes() {

    return new TreeSet<>(currencies.keySet());
  }

  /** Returns a Currency instance for the given currency code. */
  static Currency valueOf(String currencyCode) {

    Currency currency = getInstanceNoCreate(currencyCode.toUpperCase());

    if (currency == null) {
      return createCurrency(currencyCode.toUpperCase(), null, null);
    } else {
      return currency;
    }
  }

  /** Returns the Currency instance for the given currency code only if one already exists. */
  static Currency getInstanceNoCreate(String currencyCode) {

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
  static Currency createCurrency(
      String commonCode, String name, String unicode, String... alternativeCodes) {

    CurrencyAttributes attributes =
        new CurrencyAttributes.Builder()
            .setCommonCode(commonCode)
            .setName(name)
            .setUnicode(unicode)
            .setAlternativeCodes(alternativeCodes)
            .createCurrencyAttributes();

    Currency currency = new org.knowm.xchange.currency.impl.Currency(commonCode, attributes);

    for (String code : attributes.getCodes()) {
      if (commonCode.equals(code)) {
        // common code will always be part of the currencies map

        currencies.put(code, currency);

      } else if (!currencies.containsKey(code)) {
        // alternative codes will never overwrite common codes

        currencies.put(code, new org.knowm.xchange.currency.impl.Currency(code, attributes));
      }
    }

    return currency;
  }

  String getCode();

  CurrencyAttributes getAttributes();

  /** Gets the currency code originally used to acquire this object. */
  default String getCurrencyCode() {

    return this.getCode();
  }

  /**
   * Gets the equivalent object with the passed code.
   *
   * <p>This is useful in case some currencies share codes, such that {@link
   * Currency#valueOf(String)} may return the wrong currency.
   *
   * @param code The code the returned object will evaluate to
   * @return A Currency representing the same currency but having the passed currency code
   * @throws IllegalArgumentException if the passed code is not listed for this currency
   */
  default Currency getCodeCurrency(String code) {

    if (code.equals(getCode())) return this;

    Currency currency = valueOf(code);
    if (currency.equals(this)) return currency;

    if (!this.getAttributes().getCodes().contains(code))
      throw new IllegalArgumentException("Code not listed for this currency");

    return new org.knowm.xchange.currency.impl.Currency(code, this.getAttributes());
  }

  /**
   * Gets the equivalent object with an ISO 4217 code, or if none a code which looks ISO compatible
   * (starts with an X), or the constructed currency code if neither exist.
   */
  default Currency getIso4217Currency() {

    if (getAttributes().getIsoCode() == null) return this;

    // The logic for setting isoCode is in CurrencyAttributesImpl

    return this.getCodeCurrency(this.getAttributes().getIsoCode());
  }

  /** Gets the equivalent object that was created with the "commonly used" code. */
  default Currency getCommonlyUsedCurrency() {

    return this.getCodeCurrency(this.getAttributes().getCommonCode());
  }

  /** Gets the set of all currency codes associated with this currency. */
  default Set<String> getCurrencyCodes() {

    return this.getAttributes().getCodes();
  }

  /** Gets the unicode symbol of this currency. */
  default String getSymbol() {

    return this.getAttributes().getUnicode();
  }

  /** Gets the name that is suitable for displaying this currency. */
  default String getDisplayName() {

    return this.getAttributes().getName();
  }

  @Override
  default int compareTo(Currency o) {

    if (this.getAttributes().equals(o.getAttributes())) return 0;

    int comparison = this.getCode().compareTo(o.getCode());
    if (comparison == 0) comparison = this.getDisplayName().compareTo(o.getDisplayName());
    if (comparison == 0) comparison = this.hashCode() - o.hashCode();
    return comparison;
  }
}
