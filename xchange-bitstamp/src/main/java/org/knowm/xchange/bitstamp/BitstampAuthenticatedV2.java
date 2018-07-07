package org.knowm.xchange.bitstamp;

import java.io.IOException;
import java.math.BigDecimal;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.bitstamp.dto.BitstampException;
import org.knowm.xchange.bitstamp.dto.BitstampTransferBalanceResponse;
import org.knowm.xchange.bitstamp.dto.account.BitstampWithdrawal;
import org.knowm.xchange.bitstamp.dto.account.WithdrawalRequest;
import org.knowm.xchange.bitstamp.dto.trade.BitstampOrder;
import org.knowm.xchange.bitstamp.dto.trade.BitstampUserTransaction;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("api/v2")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface BitstampAuthenticatedV2 {

  @POST
  @Path("open_orders/{pair}/")
  BitstampOrder[] getOpenOrders(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @PathParam("pair") BitstampV2.Pair pair)
      throws BitstampException, IOException;

  @POST
  @Path("{side}/market/{pair}/")
  BitstampOrder placeMarketOrder(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @PathParam("side") Side side,
      @PathParam("pair") BitstampV2.Pair pair,
      @FormParam("amount") BigDecimal amount)
      throws BitstampException, IOException;

  @POST
  @Path("{side}/{pair}/")
  BitstampOrder placeOrder(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @PathParam("side") Side side,
      @PathParam("pair") BitstampV2.Pair pair,
      @FormParam("amount") BigDecimal amount,
      @FormParam("price") BigDecimal price)
      throws BitstampException, IOException;

  @POST
  @Path("user_transactions/")
  BitstampUserTransaction[] getUserTransactions(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("limit") Long numberOfTransactions,
      @FormParam("offset") Long offset,
      @FormParam("sort") String sort)
      throws BitstampException, IOException;

  @POST
  @Path("user_transactions/{pair}/")
  BitstampUserTransaction[] getUserTransactions(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @PathParam("pair") BitstampV2.Pair pair,
      @FormParam("limit") Long numberOfTransactions,
      @FormParam("offset") Long offset,
      @FormParam("sort") String sort)
      throws BitstampException, IOException;

  @POST
  @Path("xrp_withdrawal/")
  BitstampWithdrawal xrpWithdrawal(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String rippleAddress,
      @FormParam("destination_tag") String destinationTag)
      throws BitstampException, IOException;

  @POST
  @Path("ltc_withdrawal/")
  BitstampWithdrawal withdrawLitecoin(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws BitstampException, IOException;

  @POST
  @Path("bch_withdrawal/")
  BitstampWithdrawal bchWithdrawal(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws BitstampException, IOException;

  @POST
  @Path("eth_withdrawal/")
  BitstampWithdrawal withdrawEther(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws BitstampException, IOException;

  @POST
  @Path("transfer-to-main/")
  BitstampTransferBalanceResponse transferSubAccountBalanceToMain(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("amount") BigDecimal amount,
      @FormParam("currency") String currency,
      @FormParam("subAccount") String subAccount)
      throws BitstampException, IOException;

  @POST
  @Path("withdrawal-requests/")
  WithdrawalRequest[] getWithdrawalRequests(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("timedelta") Long timeDelta)
      throws BitstampException, IOException;

  @POST
  @Path("withdrawal/open/")
  BitstampWithdrawal bankWithdrawal(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("amount") BigDecimal amount,
      @FormParam("account_currency") AccountCurrency accountCurrency,
      @FormParam("name") String name,
      @FormParam("iban") String IBAN,
      @FormParam("bic") String BIK,
      @FormParam("address") String address,
      @FormParam("postal_code") String postalCode,
      @FormParam("city") String city,
      @FormParam("country") String countryAlpha2,
      @FormParam("type") BankWithdrawalType type,
      @FormParam("bank_name") String bankName,
      @FormParam("bank_address") String bankAddress,
      @FormParam("bank_postal_code") String bankPostalCode,
      @FormParam("bank_city") String bankCity,
      @FormParam("bank_country") String bankCountryAlpha2,
      @FormParam("currency") Currency currency)
      throws BitstampException, IOException;

  enum Side {
    buy,
    sell
  }

  enum AccountCurrency {
    USD,
    EUR
  }

  enum BankWithdrawalType {
    sepa,
    international
  }

  enum Country {
    Afghanistan("AF"),
    Åland_Islands("AX"),
    Albania("AL"),
    Algeria("DZ"),
    American_Samoa("AS"),
    Andorra("AD"),
    Angola("AO"),
    Anguilla("AI"),
    Antarctica("AQ"),
    Antigua_and_Barbuda("AG"),
    Argentina("AR"),
    Armenia("AM"),
    Aruba("AW"),
    Australia("AU"),
    Austria("AT"),
    Azerbaijan("AZ"),
    Bahamas("BS"),
    Bahrain("BH"),
    Bangladesh("BD"),
    Barbados("BB"),
    Belarus("BY"),
    Belgium("BE"),
    Belize("BZ"),
    Benin("BJ"),
    Bermuda("BM"),
    Bhutan("BT"),
    Bolivia("BO"),
    Bosnia_and_Herzegovina("BA"),
    Botswana("BW"),
    Bouvet_Island("BV"),
    Brazil("BR"),
    Brunei_Darussalam("BN"),
    Bulgaria("BG"),
    Burkina_Faso("BF"),
    Burundi("BI"),
    Cabo_Verde("CV"),
    Cambodia("KH"),
    Cameroon("CM"),
    Canada("CA"),
    Cayman_Islands("KY"),
    Central_African_Republic("CF"),
    Chad("TD"),
    Chile("CL"),
    China("CN"),
    Christmas_Island("CX"),
    Cocos_Islands("CC"),
    Colombia("CO"),
    Comoros("KM"),
    Cook_Islands("CK"),
    Costa_Rica("CR"),
    Cote_dIvoire("CI"),
    Croatia("HR"),
    Cuba("CU"),
    Curacao("CW"),
    Cyprus("CY"),
    Czechia("CZ"),
    Denmark("DK"),
    Djibouti("DJ"),
    Dominica("DM"),
    Dominican_Republic("DO"),
    Ecuador("EC"),
    Egypt("EG"),
    El_Salvador("SV"),
    Equatorial_Guinea("GQ"),
    Eritrea("ER"),
    Estonia("EE"),
    Ethiopia("ET"),
    Falkland_Islands("FK"),
    Faroe_Islands("FO"),
    Fiji("FJ"),
    Finland("FI"),
    France("FR"),
    French_Guiana("GF"),
    French_Polynesia("PF"),
    French_Southern_Territories("TF"),
    Gabon("GA"),
    Gambia("GM"),
    Georgia("GE"),
    Germany("DE"),
    Ghana("GH"),
    Gibraltar("GI"),
    Greece("GR"),
    Greenland("GL"),
    Grenada("GD"),
    Guadeloupe("GP"),
    Guam("GU"),
    Guatemala("GT"),
    Guernsey("GG"),
    Guinea("GN"),
    Guinea_Bissau("GW"),
    Guyana("GY"),
    Haiti("HT"),
    Holy_See("VA"),
    Honduras("HN"),
    Hong_Kong("HK"),
    Hungary("HU"),
    Iceland("IS"),
    India("IN"),
    Indonesia("ID"),
    Iran("IR"),
    Iraq("IQ"),
    Ireland("IE"),
    Isle_of_Man("IM"),
    Israel("IL"),
    Italy("IT"),
    Jamaica("JM"),
    Japan("JP"),
    Jersey("JE"),
    Jordan("JO"),
    Kazakhstan("KZ"),
    Kenya("KE"),
    Kiribati("KI"),
    Korea_South("KP"),
    Kuwait("KW"),
    Kyrgyzstan("KG"),
    Lao("LA"),
    Latvia("LV"),
    Lebanon("LB"),
    Lesotho("LS"),
    Liberia("LR"),
    Libya("LY"),
    Liechtenstein("LI"),
    Lithuania("LT"),
    Luxembourg("LU"),
    Macao("MO"),
    Macedonia("MK"),
    Malaysia("MY"),
    Maldives("MV"),
    Mali("ML"),
    Malta("MT"),
    Marshall_Islands("MH"),
    Martinique("MQ"),
    Mauritania("MR"),
    Mauritius("MU"),
    Mayotte("YT"),
    Mexico("MX"),
    Micronesia("FM"),
    Moldova("MD"),
    Monaco("MC"),
    Mongolia("MN"),
    Montenegro("ME"),
    Montserrat("MS"),
    Morocco("MA"),
    Mozambique("MZ"),
    Myanmar("MM"),
    Namibia("NA"),
    Nauru("NR"),
    Nepal("NP"),
    Netherlands("NL"),
    New_Caledonia("NC"),
    New_Zealand("NZ"),
    Nicaragua("NI"),
    Niger("NE"),
    Nigeria("NG"),
    Niue("NU"),
    Norfolk_Island("NF"),
    Northern_Mariana_Islands("MP"),
    Norway("NO"),
    Oman("OM"),
    Pakistan("PK"),
    Palau("PW"),
    Palestine("PS"),
    Panama("PA"),
    Papua_New_Guinea("PG"),
    Paraguay("PY"),
    Peru("PE"),
    Philippines("PH"),
    Pitcairn("PN"),
    Poland("PL"),
    Portugal("PT"),
    Puerto_Rico("PR"),
    Qatar("QA"),
    Reunion("RE"),
    Romania("RO"),
    Russian_Federation("RU"),
    Rwanda("RW"),
    Saint_Lucia("LC"),
    Samoa("WS"),
    San_Marino("SM"),
    Saudi_Arabia("SA"),
    Senegal("SN"),
    Serbia("RS"),
    Seychelles("SC"),
    Sierra_Leone("SL"),
    Singapore("SG"),
    Slovakia("SK"),
    Slovenia("SI"),
    Solomon_Islands("SB"),
    Somalia("SO"),
    South_Africa("ZA"),
    South_Sudan("SS"),
    Spain("ES"),
    Sri_Lanka("LK"),
    Sudan("SD"),
    Suriname("SR"),
    Swaziland("SZ"),
    Sweden("SE"),
    Switzerland("CH"),
    Syria("SY"),
    Taiwan("TW"),
    Tajikistan("TJ"),
    Tanzania("TZ"),
    Thailand("TH"),
    Timor_Leste("TL"),
    Togo("TG"),
    Tokelau("TK"),
    Tonga("TO"),
    Trinidad_and_Tobago("TT"),
    Tunisia("TN"),
    Turkey("TR"),
    Turkmenistan("TM"),
    Tuvalu("TV"),
    Uganda("UG"),
    Ukraine("UA"),
    United_Arab_Emirates("AE"),
    England("GB"),
    USA("US"),
    Uruguay("UY"),
    Uzbekistan("UZ"),
    Vanuatu("VU"),
    Venezuela("VE"),
    Viet_Nam("VN"),
    Virgin_Islands_British("VG"),
    Virgin_Islands_USA("VI"),
    Wallis_and_Futuna("WF"),
    Western_Sahara("EH"),
    Yemen("YE"),
    Zambia("ZM"),
    Zimbabwe("ZW");

    Country(String alpha2) {
      this.alpha2 = alpha2;
    }

    public String alpha2;
  }

  enum Currency {
    AED,
    AFN,
    ALL,
    AMD,
    ANG,
    AOA,
    ARS,
    AUD,
    AWG,
    AZN,
    BAM,
    BBD,
    BDT,
    BGN,
    BHD,
    BIF,
    BMD,
    BND,
    BOB,
    BOV,
    BRL,
    BSD,
    BTN,
    BWP,
    BYN,
    BZD,
    CAD,
    CDF,
    CHE,
    CHF,
    CHW,
    CLF,
    CLP,
    CNY,
    COP,
    COU,
    CRC,
    CUC,
    CUP,
    CVE,
    CZK,
    DJF,
    DKK,
    DOP,
    DZD,
    EGP,
    ERN,
    ETB,
    EUR,
    FJD,
    FKP,
    GBP,
    GEL,
    GHS,
    GIP,
    GMD,
    GNF,
    GTQ,
    GYD,
    HKD,
    HNL,
    HRK,
    HTG,
    HUF,
    IDR,
    ILS,
    INR,
    IQD,
    IRR,
    ISK,
    JMD,
    JOD,
    JPY,
    KES,
    KGS,
    KHR,
    KMF,
    KPW,
    KRW,
    KWD,
    KYD,
    KZT,
    LAK,
    LBP,
    LKR,
    LRD,
    LSL,
    LYD,
    MAD,
    MDL,
    MGA,
    MKD,
    MMK,
    MNT,
    MOP,
    MUR,
    MVR,
    MWK,
    MXN,
    MXV,
    MYR,
    MZN,
    NAD,
    NGN,
    NIO,
    NOK,
    NPR,
    NZD,
    OMR,
    PAB,
    PEN,
    PGK,
    PHP,
    PKR,
    PLN,
    PYG,
    QAR,
    RON,
    RSD,
    RUB,
    RWF,
    SAR,
    SBD,
    SCR,
    SDG,
    SEK,
    SGD,
    SHP,
    SLL,
    SOS,
    SRD,
    SSP,
    SVC,
    SYP,
    SZL,
    THB,
    TJS,
    TMT,
    TND,
    TOP,
    TRY,
    TTD,
    TWD,
    TZS,
    UAH,
    UGX,
    USD,
    USN,
    UYI,
    UYU,
    UZS,
    VEF,
    VND,
    VUV,
    WST,
    XAF,
    XAG,
    XAU,
    XBA,
    XBB,
    XBC,
    XBD,
    XCD,
    XDR,
    XOF,
    XPD,
    XPF,
    XPT,
    XSU,
    XTS,
    XUA,
    XXX,
    YER,
    ZAR,
    ZMW,
    ZWL
  }
}
