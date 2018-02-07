
package org.knowm.xchange.kucoin.dto.trading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    "SELL",
    "BUY"
})
public class KucoinActiveOrders {

    @JsonProperty("SELL")
    private List<KucoinActiveOrder> sell = new ArrayList<KucoinActiveOrder>();
    @JsonProperty("BUY")
    private List<KucoinActiveOrder> buy = new ArrayList<KucoinActiveOrder>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public KucoinActiveOrders() {
    }

    /**
     * 
     * @param sell
     * @param buy
     */
    public KucoinActiveOrders(List<KucoinActiveOrder> sell, List<KucoinActiveOrder> buy) {
        super();
        this.sell = sell;
        this.buy = buy;
    }

    /**
     * 
     * @return
     *     The sELL
     */
    @JsonProperty("SELL")
    public List<KucoinActiveOrder> getSell() {
        return sell;
    }

    /**
     * 
     * @param sell
     *     The SELL
     */
    @JsonProperty("SELL")
    public void setSELL(List<KucoinActiveOrder> sell) {
        this.sell = sell;
    }

    /**
     * 
     * @return
     *     The bUY
     */
    @JsonProperty("BUY")
    public List<KucoinActiveOrder> getBuy() {
        return buy;
    }

    /**
     * 
     * @param buy
     *     The BUY
     */
    @JsonProperty("BUY")
    public void setBuy(List<KucoinActiveOrder> buy) {
        this.buy = buy;
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
