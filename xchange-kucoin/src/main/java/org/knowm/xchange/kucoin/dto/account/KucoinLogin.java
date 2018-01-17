
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
    "ip",
    "context",
    "time"
})
public class KucoinLogin {

    @JsonProperty("ip")
    private String ip;
    @JsonProperty("context")
    private Object context;
    @JsonProperty("time")
    private Long time;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public KucoinLogin() {
    }

    /**
     * 
     * @param ip
     * @param context
     * @param time
     */
    public KucoinLogin(String ip, Object context, Long time) {
        super();
        this.ip = ip;
        this.context = context;
        this.time = time;
    }

    /**
     * 
     * @return
     *     The ip
     */
    @JsonProperty("ip")
    public String getIp() {
        return ip;
    }

    /**
     * 
     * @param ip
     *     The ip
     */
    @JsonProperty("ip")
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 
     * @return
     *     The context
     */
    @JsonProperty("context")
    public Object getContext() {
        return context;
    }

    /**
     * 
     * @param context
     *     The context
     */
    @JsonProperty("context")
    public void setContext(Object context) {
        this.context = context;
    }

    /**
     * 
     * @return
     *     The time
     */
    @JsonProperty("time")
    public Long getTime() {
        return time;
    }

    /**
     * 
     * @param time
     *     The time
     */
    @JsonProperty("time")
    public void setTime(Long time) {
        this.time = time;
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
