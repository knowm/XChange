
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
    "last",
    "current"
})
public class KucoinLoginRecord {

    @JsonProperty("last")
    private KucoinLogin last;
    @JsonProperty("current")
    private KucoinLogin current;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public KucoinLoginRecord() {
    }

    /**
     * 
     * @param current
     * @param last
     */
    public KucoinLoginRecord(KucoinLogin last, KucoinLogin current) {
        super();
        this.last = last;
        this.current = current;
    }

    /**
     * 
     * @return
     *     The last
     */
    @JsonProperty("last")
    public KucoinLogin getLast() {
        return last;
    }

    /**
     * 
     * @param last
     *     The last
     */
    @JsonProperty("last")
    public void setLast(KucoinLogin last) {
        this.last = last;
    }

    /**
     * 
     * @return
     *     The current
     */
    @JsonProperty("current")
    public KucoinLogin getCurrent() {
        return current;
    }

    /**
     * 
     * @param current
     *     The current
     */
    @JsonProperty("current")
    public void setCurrent(KucoinLogin current) {
        this.current = current;
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
