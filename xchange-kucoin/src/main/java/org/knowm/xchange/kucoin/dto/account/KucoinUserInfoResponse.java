
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
public class KucoinUserInfoResponse {

    @JsonProperty("data")
    private KucoinUserInfo userInfo;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public KucoinUserInfoResponse() {
    }

    /**
     * 
     * @param data
     */
    public KucoinUserInfoResponse(KucoinUserInfo data) {
        super();
        this.userInfo = data;
    }

    /**
     * 
     * @return
     *     The data
     */
    @JsonProperty("data")
    public KucoinUserInfo getUserInfo() {
        return userInfo;
    }

    /**
     * 
     * @param data
     *     The data
     */
    @JsonProperty("data")
    public void setUserInfo(KucoinUserInfo data) {
        this.userInfo = data;
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
