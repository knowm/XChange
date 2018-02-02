
package org.knowm.xchange.kucoin.dto.trading;

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
    "orderOid"
})
public class KucoinOrder {

    @JsonProperty("orderOid")
    private String orderOid;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public KucoinOrder() {
    }

    /**
     * 
     * @param orderOid
     */
    public KucoinOrder(String orderOid) {
        super();
        this.orderOid = orderOid;
    }

    /**
     * 
     * @return
     *     The orderOid
     */
    @JsonProperty("orderOid")
    public String getOrderOid() {
        return orderOid;
    }

    /**
     * 
     * @param orderOid
     *     The orderOid
     */
    @JsonProperty("orderOid")
    public void setOrderOid(String orderOid) {
        this.orderOid = orderOid;
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
