
package org.knowm.xchange.kucoin.dto.account;

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
    "data"
})
public class KucoinDepositAddressResponse {

    @JsonProperty("data")
    private KucoinDepositAddress depositAddress;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public KucoinDepositAddressResponse() {
    }

    /**
     * 
     * @param depositAddress
     */
    public KucoinDepositAddressResponse(KucoinDepositAddress depositAddress) {
        super();
        this.depositAddress = depositAddress;
    }

    /**
     * 
     * @return
     *     The depositAddress
     */
    @JsonProperty("data")
    public KucoinDepositAddress getDepositAddress() {
        return depositAddress;
    }

    /**
     * 
     * @param depositAddress
     *     The depositAddress
     */
    @JsonProperty("data")
    public void setData(KucoinDepositAddress depositAddress) {
        this.depositAddress = depositAddress;
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
