package org.knowm.xchange.bibox.dto.account;

import java.math.BigDecimal;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "totalBalance",
    "balance",
    "freeze",
    "id",
    "symbol",
    "icon_url",
    "describe_url",
    "name",
    "enable_withdraw",
    "enable_deposit",
    "confirm_count",
    "BTCValue",
    "CNYValue",
    "USDValue"
})
public class BiboxCoin {

    @JsonProperty("totalBalance")
    private BigDecimal totalBalance;
    @JsonProperty("balance")
    private BigDecimal balance;
    @JsonProperty("freeze")
    private BigDecimal freeze;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("icon_url")
    private URI iconUrl;
    @JsonProperty("describe_url")
    private String describeUrl;
    @JsonProperty("name")
    private String name;
    @JsonProperty("enable_withdraw")
    private boolean enableWithdraw;
    @JsonProperty("enable_deposit")
    private boolean enableDeposit;
    @JsonProperty("confirm_count")
    private Integer confirmCount;
    @JsonProperty("BTCValue")
    private BigDecimal btcValue;
    @JsonProperty("CNYValue")
    private BigDecimal cnyValue;
    @JsonProperty("USDValue")
    private BigDecimal usdValue;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The totalBalance
     */
    @JsonProperty("totalBalance")
    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    /**
     * 
     * @param totalBalance
     *     The totalBalance
     */
    @JsonProperty("totalBalance")
    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
    }

    /**
     * 
     * @return
     *     The balance
     */
    @JsonProperty("balance")
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * 
     * @param balance
     *     The balance
     */
    @JsonProperty("balance")
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * 
     * @return
     *     The freeze
     */
    @JsonProperty("freeze")
    public BigDecimal getFreeze() {
        return freeze;
    }

    /**
     * 
     * @param freeze
     *     The freeze
     */
    @JsonProperty("freeze")
    public void setFreeze(BigDecimal freeze) {
        this.freeze = freeze;
    }

    /**
     * 
     * @return
     *     The id
     */
    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The symbol
     */
    @JsonProperty("symbol")
    public String getSymbol() {
        return symbol;
    }

    /**
     * 
     * @param symbol
     *     The symbol
     */
    @JsonProperty("symbol")
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * 
     * @return
     *     The iconUrl
     */
    @JsonProperty("icon_url")
    public URI getIconUrl() {
        return iconUrl;
    }

    /**
     * 
     * @param iconUrl
     *     The icon_url
     */
    @JsonProperty("icon_url")
    public void setIconUrl(URI iconUrl) {
        this.iconUrl = iconUrl;
    }

    /**
     * 
     * @return
     *     The describeUrl
     */
    @JsonProperty("describe_url")
    public String getDescribeUrl() {
        return describeUrl;
    }

    /**
     * 
     * @param describeUrl
     *     The describe_url
     */
    @JsonProperty("describe_url")
    public void setDescribeUrl(String describeUrl) {
        this.describeUrl = describeUrl;
    }

    /**
     * 
     * @return
     *     The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The enableWithdraw
     */
    @JsonProperty("enable_withdraw")
    public boolean getEnableWithdraw() {
        return enableWithdraw;
    }

    /**
     * 
     * @param enableWithdraw
     *     The enable_withdraw
     */
    @JsonProperty("enable_withdraw")
    public void setEnableWithdraw(boolean enableWithdraw) {
        this.enableWithdraw = enableWithdraw;
    }

    /**
     * 
     * @return
     *     The enableDeposit
     */
    @JsonProperty("enable_deposit")
    public boolean getEnableDeposit() {
        return enableDeposit;
    }

    /**
     * 
     * @param enableDeposit
     *     The enable_deposit
     */
    @JsonProperty("enable_deposit")
    public void setEnableDeposit(boolean enableDeposit) {
        this.enableDeposit = enableDeposit;
    }

    /**
     * 
     * @return
     *     The confirmCount
     */
    @JsonProperty("confirm_count")
    public Integer getConfirmCount() {
        return confirmCount;
    }

    /**
     * 
     * @param confirmCount
     *     The confirm_count
     */
    @JsonProperty("confirm_count")
    public void setConfirmCount(Integer confirmCount) {
        this.confirmCount = confirmCount;
    }

    /**
     * 
     * @return
     *     The btcValue
     */
    @JsonProperty("BTCValue")
    public BigDecimal getBtcValue() {
        return btcValue;
    }

    /**
     * 
     * @param btcValue
     *     The BTCValue
     */
    @JsonProperty("BTCValue")
    public void setBtcValue(BigDecimal bTCValue) {
        this.btcValue = bTCValue;
    }

    /**
     * 
     * @return
     *     The cnyValue
     */
    @JsonProperty("CNYValue")
    public BigDecimal getCnyValue() {
        return cnyValue;
    }

    /**
     * 
     * @param cnyValue
     *     The CNYValue
     */
    @JsonProperty("CNYValue")
    public void setCnyValue(BigDecimal cNYValue) {
        this.cnyValue = cNYValue;
    }

    /**
     * 
     * @return
     *     The usdValue
     */
    @JsonProperty("USDValue")
    public BigDecimal getUsdValue() {
        return usdValue;
    }

    /**
     * 
     * @param usdValue
     *     The USDValue
     */
    @JsonProperty("USDValue")
    public void setUsdValue(BigDecimal usdValue) {
        this.usdValue = usdValue;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
