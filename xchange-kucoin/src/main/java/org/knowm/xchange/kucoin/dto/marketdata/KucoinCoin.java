
package org.knowm.xchange.kucoin.dto.marketdata;

import java.math.BigDecimal;
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
    "withdrawMinFee",
    "withdrawMinAmount",
    "withdrawFeeRate",
    "confirmationCount",
    "withdrawRemark",
    "infoUrl",
    "name",
    "tradePrecision",
    "depositRemark",
    "enableWithdraw",
    "enableDeposit",
    "coin"
})
public class KucoinCoin {

    @JsonProperty("withdrawMinFee")
    private BigDecimal withdrawMinFee;
    @JsonProperty("withdrawMinAmount")
    private BigDecimal withdrawMinAmount;
    @JsonProperty("withdrawFeeRate")
    private BigDecimal withdrawFeeRate;
    @JsonProperty("confirmationCount")
    private Integer confirmationCount;
    @JsonProperty("withdrawRemark")
    private String withdrawRemark;
    @JsonProperty("infoUrl")
    private Object infoUrl;
    @JsonProperty("name")
    private String name;
    @JsonProperty("tradePrecision")
    private Integer tradePrecision;
    @JsonProperty("depositRemark")
    private String depositRemark;
    @JsonProperty("enableWithdraw")
    private Boolean enableWithdraw;
    @JsonProperty("enableDeposit")
    private Boolean enableDeposit;
    @JsonProperty("coin")
    private String coin;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public KucoinCoin() {
    }

    /**
     * 
     * @param withdrawMinFee
     * @param withdrawMinAmount
     * @param withdrawFeeRate
     * @param confirmationCount
     * @param withdrawRemark
     * @param infoUrl
     * @param name
     * @param tradePrecision
     * @param depositRemark
     * @param enableWithdraw
     * @param enableDeposit
     * @param coin
     */
    public KucoinCoin(BigDecimal withdrawMinFee, BigDecimal withdrawMinAmount, BigDecimal withdrawFeeRate, Integer confirmationCount, String withdrawRemark, Object infoUrl, String name, Integer tradePrecision, String depositRemark, Boolean enableWithdraw, Boolean enableDeposit, String coin) {
        super();
        this.withdrawMinFee = withdrawMinFee;
        this.withdrawMinAmount = withdrawMinAmount;
        this.withdrawFeeRate = withdrawFeeRate;
        this.confirmationCount = confirmationCount;
        this.withdrawRemark = withdrawRemark;
        this.infoUrl = infoUrl;
        this.name = name;
        this.tradePrecision = tradePrecision;
        this.depositRemark = depositRemark;
        this.enableWithdraw = enableWithdraw;
        this.enableDeposit = enableDeposit;
        this.coin = coin;
    }

    /**
     * 
     * @return
     *     The withdrawMinFee
     */
    @JsonProperty("withdrawMinFee")
    public BigDecimal getWithdrawMinFee() {
        return withdrawMinFee;
    }

    /**
     * 
     * @param withdrawMinFee
     *     The withdrawMinFee
     */
    @JsonProperty("withdrawMinFee")
    public void setWithdrawMinFee(BigDecimal withdrawMinFee) {
        this.withdrawMinFee = withdrawMinFee;
    }

    /**
     * 
     * @return
     *     The withdrawMinAmount
     */
    @JsonProperty("withdrawMinAmount")
    public BigDecimal getWithdrawMinAmount() {
        return withdrawMinAmount;
    }

    /**
     * 
     * @param withdrawMinAmount
     *     The withdrawMinAmount
     */
    @JsonProperty("withdrawMinAmount")
    public void setWithdrawMinAmount(BigDecimal withdrawMinAmount) {
        this.withdrawMinAmount = withdrawMinAmount;
    }

    /**
     * 
     * @return
     *     The withdrawFeeRate
     */
    @JsonProperty("withdrawFeeRate")
    public BigDecimal getWithdrawFeeRate() {
        return withdrawFeeRate;
    }

    /**
     * 
     * @param withdrawFeeRate
     *     The withdrawFeeRate
     */
    @JsonProperty("withdrawFeeRate")
    public void setWithdrawFeeRate(BigDecimal withdrawFeeRate) {
        this.withdrawFeeRate = withdrawFeeRate;
    }

    /**
     * 
     * @return
     *     The confirmationCount
     */
    @JsonProperty("confirmationCount")
    public Integer getConfirmationCount() {
        return confirmationCount;
    }

    /**
     * 
     * @param confirmationCount
     *     The confirmationCount
     */
    @JsonProperty("confirmationCount")
    public void setConfirmationCount(Integer confirmationCount) {
        this.confirmationCount = confirmationCount;
    }

    /**
     * 
     * @return
     *     The withdrawRemark
     */
    @JsonProperty("withdrawRemark")
    public String getWithdrawRemark() {
        return withdrawRemark;
    }

    /**
     * 
     * @param withdrawRemark
     *     The withdrawRemark
     */
    @JsonProperty("withdrawRemark")
    public void setWithdrawRemark(String withdrawRemark) {
        this.withdrawRemark = withdrawRemark;
    }

    /**
     * 
     * @return
     *     The infoUrl
     */
    @JsonProperty("infoUrl")
    public Object getInfoUrl() {
        return infoUrl;
    }

    /**
     * 
     * @param infoUrl
     *     The infoUrl
     */
    @JsonProperty("infoUrl")
    public void setInfoUrl(Object infoUrl) {
        this.infoUrl = infoUrl;
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
     *     The tradePrecision
     */
    @JsonProperty("tradePrecision")
    public Integer getTradePrecision() {
        return tradePrecision;
    }

    /**
     * 
     * @param tradePrecision
     *     The tradePrecision
     */
    @JsonProperty("tradePrecision")
    public void setTradePrecision(Integer tradePrecision) {
        this.tradePrecision = tradePrecision;
    }

    /**
     * 
     * @return
     *     The depositRemark
     */
    @JsonProperty("depositRemark")
    public String getDepositRemark() {
        return depositRemark;
    }

    /**
     * 
     * @param depositRemark
     *     The depositRemark
     */
    @JsonProperty("depositRemark")
    public void setDepositRemark(String depositRemark) {
        this.depositRemark = depositRemark;
    }

    /**
     * 
     * @return
     *     The enableWithdraw
     */
    @JsonProperty("enableWithdraw")
    public Boolean getEnableWithdraw() {
        return enableWithdraw;
    }

    /**
     * 
     * @param enableWithdraw
     *     The enableWithdraw
     */
    @JsonProperty("enableWithdraw")
    public void setEnableWithdraw(Boolean enableWithdraw) {
        this.enableWithdraw = enableWithdraw;
    }

    /**
     * 
     * @return
     *     The enableDeposit
     */
    @JsonProperty("enableDeposit")
    public Boolean getEnableDeposit() {
        return enableDeposit;
    }

    /**
     * 
     * @param enableDeposit
     *     The enableDeposit
     */
    @JsonProperty("enableDeposit")
    public void setEnableDeposit(Boolean enableDeposit) {
        this.enableDeposit = enableDeposit;
    }

    /**
     * 
     * @return
     *     The coin
     */
    @JsonProperty("coin")
    public String getCoin() {
        return coin;
    }

    /**
     * 
     * @param coin
     *     The coin
     */
    @JsonProperty("coin")
    public void setCoin(String coin) {
        this.coin = coin;
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
