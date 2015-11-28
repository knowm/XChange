package com.xeiam.xchange.currency;

import java.util.*;

/**
 * A Currency class roughly modeled after {@link java.util.Currency}.
 * </p>
 * Each object retains the code it was acquired with -- so {@link #getInstance}("BTC").{@link #getCurrencyCode}() will always be "BTC",
 * even though the proposed ISO 4217 symbol is "XBT"
 */
public class Currency implements Comparable <Currency> {

  private static final Map<String, Currency> currencies = new HashMap<String, Currency>();

  /**
   * Global currency codes
   */
  // TODO: Load from json resource
  public static final Currency AED = createCurrency("AED", "United Arab Emirates Dirham", null, null);
  public static final Currency AFN = createCurrency("AFN", "Afghan Afghani", null, null);
  public static final Currency ALL = createCurrency("ALL", "Albanian Lek", null, null);
  public static final Currency AMD = createCurrency("AMD", "Armenian Dram", null, null);
  public static final Currency ANC = createCurrency("ANC", "Anoncoin", null, null);
  public static final Currency ANG = createCurrency("ANG", "Netherlands Antillean Guilder", null, null);
  public static final Currency AOA = createCurrency("AOA", "Angolan Kwanza", null, null);
  public static final Currency ARS = createCurrency("ARS", "Argentine Peso", null, null);
  public static final Currency AUD = createCurrency("AUD", "Australian Dollar", null, null);
  public static final Currency AUR = createCurrency("AUR", "Auroracoin", null, null);
  public static final Currency AWG = createCurrency("AWG", "Aruban Florin", null, null);
  public static final Currency AZN = createCurrency("AZN", "Azerbaijani Manat", null, null);
  public static final Currency BAM = createCurrency("BAM", "Bosnia-Herzegovina Convertible Mark", null, null);
  public static final Currency BBD = createCurrency("BBD", "Barbadian Dollar", null, null);
  public static final Currency BC = createCurrency("BC", "BlackCoin", null, null, "BLK");
  public static final Currency BLK = getInstance("BLK");
  public static final Currency BDT = createCurrency("BDT", "Bangladeshi Taka", null, null);
  public static final Currency BGC = createCurrency("BGC", "Aten 'Black Gold' Coin", null, null);
  public static final Currency BGN = createCurrency("BGN", "Bulgarian Lev", null, null);
  public static final Currency BHD = createCurrency("BHD", "Bahraini Dinar", null, null);
  public static final Currency BIF = createCurrency("BIF", "Burundian Franc", null, null);
  public static final Currency BMD = createCurrency("BMD", "Bermudan Dollar", null, null);
  public static final Currency BND = createCurrency("BND", "Brunei Dollar", null, null);
  public static final Currency BOB = createCurrency("BOB", "Bolivian Boliviano", null, null);
  public static final Currency BRL = createCurrency("BRL", "Brazilian Real", null, null);
  public static final Currency BSD = createCurrency("BSD", "Bahamian Dollar", null, null);
  public static final Currency BTC = createCurrency("BTC", "Bitcoin", null, null, "XBT");
  public static final Currency XBT = getInstance("XBT");
  public static final Currency BTN = createCurrency("BTN", "Bhutanese Ngultrum", null, null);
  public static final Currency BWP = createCurrency("BWP", "Botswanan Pula", null, null);
  public static final Currency BYR = createCurrency("BYR", "Belarusian Ruble", null, null);
  public static final Currency BZD = createCurrency("BZD", "Belize Dollar", null, null);
  public static final Currency CAD = createCurrency("CAD", "Canadian Dollar", null, null);
  public static final Currency CDF = createCurrency("CDF", "Congolese Franc", null, null);
  public static final Currency CHF = createCurrency("CHF", "Swiss Franc", null, null);
  public static final Currency CLF = createCurrency("CLF", "Chilean Unit of Account (UF)", null, null);
  public static final Currency CLP = createCurrency("CLP", "Chilean Peso", null, null);
  public static final Currency CNC = createCurrency("CNC", "Chinacoin", null, null);
  public static final Currency CNY = createCurrency("CNY", "Chinese Yuan", null, null);
  public static final Currency COP = createCurrency("COP", "Colombian Peso", null, null);
  public static final Currency CRC = createCurrency("CRC", "Costa Rican Colón", null, null);
  public static final Currency CUP = createCurrency("CUP", "Cuban Peso", null, null);
  public static final Currency CVE = createCurrency("CVE", "Cape Verdean Escudo", null, null);
  public static final Currency CZK = createCurrency("CZK", "Czech Republic Koruna", null, null);
  public static final Currency DGB = createCurrency("DGB", "DigiByte", null, null);
  public static final Currency DJF = createCurrency("DJF", "Djiboutian Franc", null, null);
  public static final Currency DKK = createCurrency("DKK", "Danish Krone", null, null);
  public static final Currency DOGE = createCurrency("DOGE", "Dogecoin", null, null, "XDC");
  public static final Currency XDC = getInstance("XDC");
  public static final Currency DOP = createCurrency("DOP", "Dominican Peso", null, null);
  public static final Currency DGC = createCurrency("DGC", "Digitalcoin", null, null);
  public static final Currency DVC = createCurrency("DVC", "Devcoin", null, null);
  public static final Currency DRK = createCurrency("DRK", "Darkcoin", null, null);
  public static final Currency DZD = createCurrency("DZD", "Algerian Dinar", null, null);
  public static final Currency EEK = createCurrency("EEK", "Estonian Kroon", null, null);
  public static final Currency EGD = createCurrency("EGD", "egoldcoin", null, null);
  public static final Currency EGP = createCurrency("EGP", "Egyptian Pound", null, null);
  public static final Currency ETB = createCurrency("ETB", "Ethiopian Birr", null, null);
  public static final Currency ETH = createCurrency("ETH", "Ether", null, null);
  public static final Currency EUR = createCurrency("EUR", "Euro", null, null);
  public static final Currency FJD = createCurrency("FJD", "Fijian Dollar", null, null);
  public static final Currency FKP = createCurrency("FKP", "Falkland Islands Pound", null, null);
  public static final Currency FTC = createCurrency("FTC", "Feathercoin", null, null);
  public static final Currency GBP = createCurrency("GBP", "British Pound Sterling", null, null);
  public static final Currency GEL = createCurrency("GEL", "Georgian Lari", null, null);
  public static final Currency GHS = createCurrency("GHS", "Ghanaian Cedi", null, null);
  public static final Currency GHs = createCurrency("GHS", "Gigahashes per second", null, null);
  public static final Currency GIP = createCurrency("GIP", "Gibraltar Pound", null, null);
  public static final Currency GMD = createCurrency("GMD", "Gambian Dalasi", null, null);
  public static final Currency GNF = createCurrency("GNF", "Guinean Franc", null, null);
  public static final Currency GTQ = createCurrency("GTQ", "Guatemalan Quetzal", null, null);
  public static final Currency GYD = createCurrency("GYD", "Guyanaese Dollar", null, null);
  public static final Currency HKD = createCurrency("HKD", "Hong Kong Dollar", null, null);
  public static final Currency HNL = createCurrency("HNL", "Honduran Lempira", null, null);
  public static final Currency HRK = createCurrency("HRK", "Croatian Kuna", null, null);
  public static final Currency HTG = createCurrency("HTG", "Haitian Gourde", null, null);
  public static final Currency HUF = createCurrency("HUF", "Hungarian Forint", null, null);
  public static final Currency IDR = createCurrency("IDR", "Indonesian Rupiah", null, null);
  public static final Currency ILS = createCurrency("ILS", "Israeli New Sheqel", null, null);
  public static final Currency INR = createCurrency("INR", "Indian Rupee", null, null);
  public static final Currency IQD = createCurrency("IQD", "Iraqi Dinar", null, null);
  public static final Currency IRR = createCurrency("IRR", "Iranian Rial", null, null);
  public static final Currency ISK = createCurrency("ISK", "Icelandic Króna", null, null);
  public static final Currency IXC = createCurrency("IXC", "iXcoin", null, null);
  public static final Currency JEP = createCurrency("JEP", "Jersey Pound", null, null);
  public static final Currency JMD = createCurrency("JMD", "Jamaican Dollar", null, null);
  public static final Currency JOD = createCurrency("JOD", "Jordanian Dinar", null, null);
  public static final Currency JPY = createCurrency("JPY", "Japanese Yen", null, null);
  public static final Currency KES = createCurrency("KES", "Kenyan Shilling", null, null);
  public static final Currency KGS = createCurrency("KGS", "Kyrgystani Som", null, null);
  public static final Currency KHR = createCurrency("KHR", "Cambodian Riel", null, null);
  public static final Currency KMF = createCurrency("KMF", "Comorian Franc", null, null);
  public static final Currency KPW = createCurrency("KPW", "North Korean Won", null, null);
  public static final Currency KRW = createCurrency("KRW", "South Korean Won", null, null);
  public static final Currency KWD = createCurrency("KWD", "Kuwaiti Dinar", null, null);
  public static final Currency KYD = createCurrency("KYD", "Cayman Islands Dollar", null, null);
  public static final Currency KZT = createCurrency("KZT", "Kazakhstani Tenge", null, null);
  public static final Currency LAK = createCurrency("LAK", "Laotian Kip", null, null);
  public static final Currency LBP = createCurrency("LBP", "Lebanese Pound", null, null);
  public static final Currency LKR = createCurrency("LKR", "Sri Lankan Rupee", null, null);
  public static final Currency LRD = createCurrency("LRD", "Liberian Dollar", null, null);
  public static final Currency LSL = createCurrency("LSL", "Lesotho Loti", null, null);
  public static final Currency LTC = createCurrency("LTC", "Litecoin", null, null);
  public static final Currency LTL = createCurrency("LTL", "Lithuanian Litas", null, null);
  public static final Currency LVL = createCurrency("LVL", "Latvian Lats", null, null);
  public static final Currency LYD = createCurrency("LYD", "Libyan Dinar", null, null);
  public static final Currency MAD = createCurrency("MAD", "Moroccan Dirham", null, null);
  public static final Currency MDL = createCurrency("MDL", "Moldovan Leu", null, null);
  public static final Currency MEC = createCurrency("MEC", "MegaCoin", null, null);
  public static final Currency MGA = createCurrency("MGA", "Malagasy Ariary", null, null);
  public static final Currency MKD = createCurrency("MKD", "Macedonian Denar", null, null);
  public static final Currency MMK = createCurrency("MMK", "Myanma Kyat", null, null);
  public static final Currency MNT = createCurrency("MNT", "Mongolian Tugrik", null, null);
  public static final Currency MOP = createCurrency("MOP", "Macanese Pataca", null, null);
  public static final Currency MRO = createCurrency("MRO", "Mauritanian Ouguiya", null, null);
  public static final Currency MSC = createCurrency("MSC", "Mason Coin", null, null);
  public static final Currency MUR = createCurrency("MUR", "Mauritian Rupee", null, null);
  public static final Currency MVR = createCurrency("MVR", "Maldivian Rufiyaa", null, null);
  public static final Currency MWK = createCurrency("MWK", "Malawian Kwacha", null, null);
  public static final Currency MXN = createCurrency("MXN", "Mexican Peso", null, null);
  public static final Currency MYR = createCurrency("MYR", "Malaysian Ringgit", null, null);
  public static final Currency MZN = createCurrency("MZN", "Mozambican Metical", null, null);
  public static final Currency NAD = createCurrency("NAD", "Namibian Dollar", null, null);
  public static final Currency NGN = createCurrency("NGN", "Nigerian Naira", null, null);
  public static final Currency NIO = createCurrency("NIO", "Nicaraguan Córdoba", null, null);
  public static final Currency NMC = createCurrency("NMC", "Namecoin", null, null);
  public static final Currency NOK = createCurrency("NOK", "Norwegian Krone", null, null);
  public static final Currency NPR = createCurrency("NPR", "Nepalese Rupee", null, null);
  public static final Currency NVC = createCurrency("NVC", "Novacoin", null, null);
  public static final Currency NXT = createCurrency("NXT", "Nextcoin", null, null);
  public static final Currency NZD = createCurrency("NZD", "New Zealand Dollar", null, null);
  public static final Currency OMR = createCurrency("OMR", "Omani Rial", null, null);
  public static final Currency PAB = createCurrency("PAB", "Panamanian Balboa", null, null);
  public static final Currency PEN = createCurrency("PEN", "Peruvian Nuevo Sol", null, null);
  public static final Currency PGK = createCurrency("PGK", "Papua New Guinean Kina", null, null);
  public static final Currency PHP = createCurrency("PHP", "Philippine Peso", null, null);
  public static final Currency PKR = createCurrency("PKR", "Pakistani Rupee", null, null);
  public static final Currency PLN = createCurrency("PLN", "Polish Zloty", null, null);
  public static final Currency POT = createCurrency("POT", "PotCoin", null, null);
  public static final Currency PPC = createCurrency("PPC", "Peercoin", null, null);
  public static final Currency PYG = createCurrency("PYG", "Paraguayan Guarani", null, null);
  public static final Currency QAR = createCurrency("QAR", "Qatari Rial", null, null);
  public static final Currency QRK = createCurrency("QRK", "QuarkCoin", null, null);
  public static final Currency RON = createCurrency("RON", "Romanian Leu", null, null);
  public static final Currency RSD = createCurrency("RSD", "Serbian Dinar", null, null);
  public static final Currency RUB = createCurrency("RUB", "Russian Ruble", null, null);
  public static final Currency RUR = createCurrency("RUR", "Old Russian Ruble", null, null);
  public static final Currency RWF = createCurrency("RWF", "Rwandan Franc", null, null);
  public static final Currency SAR = createCurrency("SAR", "Saudi Riyal", null, null);
  public static final Currency SBC = createCurrency("SBC", "Stablecoin", null, null);
  public static final Currency SBD = createCurrency("SBD", "Solomon Islands Dollar", null, null);
  public static final Currency SCR = createCurrency("SCR", "Seychellois Rupee", null, null);
  public static final Currency SDG = createCurrency("SDG", "Sudanese Pound", null, null);
  public static final Currency SEK = createCurrency("SEK", "Swedish Krona", null, null);
  public static final Currency SGD = createCurrency("SGD", "Singapore Dollar", null, null);
  public static final Currency SHP = createCurrency("SHP", "Saint Helena Pound", null, null);
  public static final Currency SLL = createCurrency("SLL", "Sierra Leonean Leone", null, null);
  public static final Currency SOS = createCurrency("SOS", "Somali Shilling", null, null);
  public static final Currency SRD = createCurrency("SRD", "Surinamese Dollar", null, null);
  public static final Currency START = createCurrency("START", "startcoin", null, null);
  public static final Currency STD = createCurrency("STD", "São Tomé and Príncipe Dobra", null, null);
  public static final Currency STR = createCurrency("STR", "Stellar", null, null);
  public static final Currency SVC = createCurrency("SVC", "Salvadoran Colón", null, null);
  public static final Currency SYP = createCurrency("SYP", "Syrian Pound", null, null);
  public static final Currency SZL = createCurrency("SZL", "Swazi Lilangeni", null, null);
  public static final Currency THB = createCurrency("THB", "Thai Baht", null, null);
  public static final Currency TJS = createCurrency("TJS", "Tajikistani Somoni", null, null);
  public static final Currency TMT = createCurrency("TMT", "Turkmenistani Manat", null, null);
  public static final Currency TND = createCurrency("TND", "Tunisian Dinar", null, null);
  public static final Currency TOP = createCurrency("TOP", "Tongan Paʻanga", null, null);
  public static final Currency TRC = createCurrency("TRC", "Terracoin", null, null);
  public static final Currency TRY = createCurrency("TRY", "Turkish Lira", null, null);
  public static final Currency TTD = createCurrency("TTD", "Trinidad and Tobago Dollar", null, null);
  public static final Currency TWD = createCurrency("TWD", "New Taiwan Dollar", null, null);
  public static final Currency TZS = createCurrency("TZS", "Tanzanian Shilling", null, null);
  public static final Currency UAH = createCurrency("UAH", "Ukrainian Hryvnia", null, null);
  public static final Currency UGX = createCurrency("UGX", "Ugandan Shilling", null, null);
  public static final Currency USD = createCurrency("USD", "United States Dollar", null, null);
  public static final Currency USDE = createCurrency("USDE", "Unitary Status Dollar eCoin", null, null);
  public static final Currency UTC = createCurrency("UTC", "Ultracoin", null, null);
  public static final Currency UYU = createCurrency("UYU", "Uruguayan Peso", null, null);
  public static final Currency UZS = createCurrency("UZS", "Uzbekistan Som", null, null);
  public static final Currency VEF = createCurrency("VEF", "Venezuelan Bolívar", null, null);
  public static final Currency VEN = createCurrency("VEN", "Hub Culture's Ven", null, null, "XVN");
  public static final Currency XVN = getInstance("XVN");
  public static final Currency VND = createCurrency("VND", "Vietnamese Dong", null, null);
  public static final Currency VUV = createCurrency("VUV", "Vanuatu Vatu", null, null);
  public static final Currency WDC = createCurrency("WDC", "WorldCoin", null, null);
  public static final Currency WST = createCurrency("WST", "Samoan Tala", null, null);
  public static final Currency XAF = createCurrency("XAF", "CFA Franc BEAC", null, null);
  public static final Currency XCD = createCurrency("XCD", "East Caribbean Dollar", null, null);
  public static final Currency XDR = createCurrency("XDR", "Special Drawing Rights", null, null);
  public static final Currency XMR = createCurrency("XMR", "Monero", null, null);
  public static final Currency XOF = createCurrency("XOF", "CFA Franc BCEAO", null, null);
  public static final Currency XPF = createCurrency("XPF", "CFP Franc", null, null);
  public static final Currency XPM = createCurrency("XPM", "Primecoin", null, null);
  public static final Currency XRP = createCurrency("XRP", "Ripple", null, null);
  public static final Currency YBC = createCurrency("YBC", "YbCoin", null, null);
  public static final Currency YER = createCurrency("YER", "Yemeni Rial", null, null);
  public static final Currency ZAR = createCurrency("ZAR", "South African Rand", null, null);
  public static final Currency ZMK = createCurrency("ZMK", "Zambian Kwacha", null, null);
  public static final Currency ZWL = createCurrency("ZWL", "Zimbabwean Dollar", null, null);


  /**
   * Gets the set of available currencies.
   */
  public static SortedSet<Currency> getAvailableCurrencies() {

    return new TreeSet<Currency>(currencies.values());
  }


  private final String code;
  private final CurrencyAttributes attributes;

  /**
   * Returns a Currency instance for the given currency code.
   */
  public static Currency getInstance(String currencyCode) {

    Currency currency = getInstanceNoCreate(currencyCode);

    if (currency == null) {
      return createCurrency(currencyCode, null, null, null);
    } else {
      return currency;
    }
  }

  /**
   * Returns the Currency instance for the given currency code only if one already exists.
   */
  public static Currency getInstanceNoCreate(String currencyCode) {

    return currencies.get(currencyCode);
  }

  /**
   * Public constructor. Links to an existing currency.
   */
  public Currency(String code) {

    this.code = code;
    this.attributes = getInstance(code).attributes;
  }

  /**
   * Gets the default number of fraction digits used with this currency.
   */
  public int getDefaultFractionDigits() {

    return attributes.scale;
  }

  /**
   * Gets the currency code originally used to acquire this object.
   */
  public String getCurrencyCode() {

    return code;
  }

  /**
   * Gets the equivalent object with the passed code.
   * <p/>
   * This is useful in case some currencies share codes, such that {@link #getInstance(String)} may return the wrong currency.
   *
   * @param code The code the returned object will evaluate to
   * @return A Currency representing the same currency but having the passed currency code
   * @throws IllegalArgumentException if the passed code is not listed for this currency
   */
  public Currency getCodeCurrency(String code) {

    if (code.equals(this.code))
      return this;
    
    Currency currency = getInstance(code);
    if (currency.equals(this))
      return currency;

    if (!attributes.codes.contains(code))
      throw new IllegalArgumentException("Code not listed for this currency");

    return new Currency(code, attributes);
  }

  /**
   * Gets the equivalent object with an ISO 4217 code, or if none a code which looks ISO compatible (starts with an X),
   * or the constructed currency code if neither exist.
   */
  public Currency getIso4217Currency() {

    if (attributes.isoCode == null)
      return this;

    // The logic for setting isoCode is in CurrencyAttributes

    return getCodeCurrency(attributes.isoCode);
  }

  /**
   * Gets the equivalent object that was created with the "commonly used" code.
   */
  public Currency getCommonlyUsedCurrency() {

    return getCodeCurrency(attributes.commonCode);
  }

  /**
   * Gets the set of all currency codes associated with this currency.
   */
  public Set<String> getCurrencyCodes() {

    return attributes.codes;
  }

  /**
   * Gets the unit symbol of this currency.
   */
  public String getSymbol() {

    return attributes.symbol;
  }

  /**
   * Gets the name that is suitable for displaying this currency.
   */
  public String getDisplayName() {

    return attributes.name;
  }


  /**
   * Factory
   *
   * @param commonCode commonly used code for this currency: "BTC"
   * @param name Name of the currency: "Bitcoin"
   * @param symbol Symbol for the currency: new unicode "\u20BF" or bhat "฿"
   * @param scale Number of digits past the decimal point for this currency
   * @param alternativeCodes Alternative codes for the currency: "XBT"
   */
  private static Currency createCurrency(String commonCode, String name, String symbol, Integer scale, String... alternativeCodes) {

    CurrencyAttributes attributes = new CurrencyAttributes(commonCode, name, symbol, scale, alternativeCodes);

    Currency currency = new Currency(commonCode, attributes);

    for(String code : attributes.codes) {
      if (commonCode.equals(code)) {
        // common code will always be part of the currencies map

        currencies.put(code, currency);

      } else if (!currencies.containsKey(code)){
        // alternative codes will never overwrite common codes

        currencies.put(code, new Currency(code, attributes));
      }
    }

    return currency;
  }

  private Currency(String alternativeCode, CurrencyAttributes attributes) {

    this.code = alternativeCode;
    this.attributes = attributes;
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

    return attributes == other.attributes;
  }

  @Override
  public int compareTo(Currency o) {

    if (attributes == o.attributes)
      return 0;

    int comparison = code.compareTo(o.code);
    if (comparison == 0)
      comparison = getDisplayName().compareTo(o.getDisplayName());
    if (comparison == 0)
      comparison = hashCode() - o.hashCode();
    return comparison;
  }

  private static class CurrencyAttributes {

    public final Set<String> codes;
    public final String isoCode;
    public final String commonCode;
    public final String name;
    public final String symbol;
    public final int scale;

    public CurrencyAttributes(String commonCode, String name, String symbol, Integer scale, String... alternativeCodes) {

      if (alternativeCodes.length > 0) {
        this.codes = new TreeSet<String>(Arrays.asList(alternativeCodes));
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
          } catch(IllegalArgumentException e) { }
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

      if (symbol != null) {
        this.symbol = symbol;
      } else if (javaCurrency != null) {
        this.symbol = javaCurrency.getSymbol();
      } else {
        this.symbol = commonCode;
      }

      if (scale == null && javaCurrency != null) {
        scale = javaCurrency.getDefaultFractionDigits();
      }
      if (scale != null && scale != -1) {
        this.scale = scale;
      } else {
        this.scale = 8;
      }
    }
  }
}
