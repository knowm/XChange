
package org.knowm.xchange.bitcoinde.trade;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * 
 * @author kaiserfr
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "current",
    "last"
})
public class BitcoindePage {

    @JsonProperty("current")
    private Integer current;
    @JsonProperty("last")
    private Integer last;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public BitcoindePage() {
    }

    /**
     * 
     * @param last
     * @param current
     */
    public BitcoindePage(Integer current, Integer last) {
        super();
        this.current = current;
        this.last = last;
    }

    @JsonProperty("current")
    public Integer getCurrent() {
        return current;
    }

    @JsonProperty("current")
    public void setCurrent(Integer current) {
        this.current = current;
    }

    @JsonProperty("last")
    public Integer getLast() {
        return last;
    }

    @JsonProperty("last")
    public void setLast(Integer last) {
        this.last = last;
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
