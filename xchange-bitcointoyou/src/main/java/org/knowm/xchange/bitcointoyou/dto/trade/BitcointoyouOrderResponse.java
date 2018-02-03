package org.knowm.xchange.bitcointoyou.dto.trade;

import static com.fasterxml.jackson.annotation.JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * The Order response object that Bitcointoyou API returns.
 *
 * @author Danilo Guimaraes
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"success", "oReturn", "error", "date", "timestamp"})
public class BitcointoyouOrderResponse {

  private final String success;
  @JsonIgnore
  private List<BitcointoyouOrderInfo> oReturn;
  @JsonIgnore
  private String oReturnAsString;
  private final String error;
  private final String date;
  private final String timestamp;
  @JsonIgnore
  private final Map<String, Object> additionalProperties = new HashMap<>();

  @JsonCreator
  public BitcointoyouOrderResponse(@JsonProperty("success") String success,
      @JsonProperty("oReturn") @JsonFormat(with = {ACCEPT_SINGLE_VALUE_AS_ARRAY}) Object oReturn, @JsonProperty("error") String error,
      @JsonProperty("date") String date, @JsonProperty("timestamp")String timestamp) {
    this.success = success;
    this.setOReturn(oReturn);
    this.error = error;
    this.date = date;
    this.timestamp = timestamp;
  }

  /**
   * This complete messy it's because the 'oReturn' JSON field can be either an Object, or a String or an Array of Objects.
   *
   * <p>
   *   Examples:
   * </p>
   *
   * oReturn as a String (representing an exception or error):
   * <pre>
   *   oReturn : 'NO_CONTENT_FOUND'
   * </pre>
   *
   * oReturn as an Object (representing a single order):
   * <pre>
   *   oReturn : { id: 1, asset: 'BTC', price : 15000.00 }
   * </pre>
   *
   * oReturn as an Array of Objects (representing multiple-orders):
   * <pre>
   *   oReturn : [
   *      { id: 1, asset: 'BTC', price : 15000.00 },
   *      { id: 2, asset: 'BTC', price : 15000.00 }
   *   ]
   * </pre>
   *
   *
   * I haven't figured out a better way to do this, like Jackson built-in annotations or something.
   *
   * <p>
   *   Please see {@code BitcointoyouOrderResponseTest}
   * </p>
   *
   * @param oReturn the 'oReturn' JSON field content
   */
  private void setOReturn(Object oReturn) {

    if (oReturn != null) {
      if (oReturn instanceof String) {
        this.oReturnAsString = (String) oReturn;
      } else if (oReturn instanceof List) {
        this.oReturn = new ArrayList<>();

        for (Object obj : (List) oReturn) {
         if (obj instanceof LinkedHashMap) {
           addNewBitcointoyouOrderInfo((Map<String, String>) obj);
         } else if (obj instanceof BitcointoyouOrderInfo) {
           this.oReturn.add((BitcointoyouOrderInfo) obj);
         }
        }
      } else if (oReturn instanceof Map) {
        this.oReturn = new ArrayList<>();
        addNewBitcointoyouOrderInfo((Map<String, String>) oReturn);
      }
    }
  }

  private void addNewBitcointoyouOrderInfo(Map<String, String> params) {
    this.oReturn.add(new BitcointoyouOrderInfo( params));
  }

  public String getSuccess() {

    return success;
  }

  @JsonIgnore
  public List<BitcointoyouOrderInfo> getOrderList() {

    return oReturn;
  }

  @JsonIgnore
  public String getoReturnAsString() {
    return oReturnAsString;
  }

  public String getError() {

    return error;
  }

  public String getDate() {

    return date;
  }

  public String getTimestamp() {

    return timestamp;
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