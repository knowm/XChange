/*
 * BitMEX API
 * ## REST API for the BitMEX Trading Platform  [View Changelog](/app/apiChangelog)  ----  #### Getting Started  Base URI: [https://www.bitmex.com/api/v1](/api/v1)  ##### Fetching Data  All REST endpoints are documented below. You can try out any query right from this interface.  Most table queries accept `count`, `start`, and `reverse` params. Set `reverse=true` to get rows newest-first.  Additional documentation regarding filters, timestamps, and authentication is available in [the main API documentation](/app/restAPI).  *All* table data is available via the [Websocket](/app/wsAPI). We highly recommend using the socket if you want to have the quickest possible data without being subject to ratelimits.  ##### Return Types  By default, all data is returned as JSON. Send `?_format=csv` to get CSV data or `?_format=xml` to get XML data.  ##### Trade Data Queries  *This is only a small subset of what is available, to get you started.*  Fill in the parameters and click the `Try it out!` button to try any of these queries.  * [Pricing Data](#!/Quote/Quote_get)  * [Trade Data](#!/Trade/Trade_get)  * [OrderBook Data](#!/OrderBook/OrderBook_getL2)  * [Settlement Data](#!/Settlement/Settlement_get)  * [Exchange Statistics](#!/Stats/Stats_history)  Every function of the BitMEX.com platform is exposed here and documented. Many more functions are available.  ##### Swagger Specification  [⇩ Download Swagger JSON](swagger.json)  ----  ## All API Endpoints  Click to expand a section. 
 *
 * OpenAPI spec version: 1.2.0
 * Contact: support@bitmex.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package org.knowm.xchange.bitmex.dto;

import java.util.Objects;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * Account Notifications
 */
@ApiModel(description = "Account Notifications")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-02-06T17:10:23.689Z")
public class BitmexNotification {
  @SerializedName("id")
  private BigDecimal id = null;

  @SerializedName("date")
  private OffsetDateTime date = null;

  @SerializedName("title")
  private String title = null;

  @SerializedName("body")
  private String body = null;

  @SerializedName("ttl")
  private BigDecimal ttl = null;

  /**
   * Gets or Sets type
   */
  @JsonAdapter(TypeEnum.Adapter.class)
  public enum TypeEnum {
    SUCCESS("success"),
    
    ERROR("error"),
    
    INFO("info");

    private String value;

    TypeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static TypeEnum fromValue(String text) {
      for (TypeEnum b : TypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<TypeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final TypeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public TypeEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return TypeEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("type")
  private TypeEnum type = null;

  @SerializedName("closable")
  private Boolean closable = true;

  @SerializedName("persist")
  private Boolean persist = true;

  @SerializedName("waitForVisibility")
  private Boolean waitForVisibility = true;

  @SerializedName("sound")
  private String sound = null;

  public BitmexNotification id(BigDecimal id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(value = "")
  public BigDecimal getId() {
    return id;
  }

  public void setId(BigDecimal id) {
    this.id = id;
  }

  public BitmexNotification date(OffsetDateTime date) {
    this.date = date;
    return this;
  }

   /**
   * Get date
   * @return date
  **/
  @ApiModelProperty(required = true, value = "")
  public OffsetDateTime getDate() {
    return date;
  }

  public void setDate(OffsetDateTime date) {
    this.date = date;
  }

  public BitmexNotification title(String title) {
    this.title = title;
    return this;
  }

   /**
   * Get title
   * @return title
  **/
  @ApiModelProperty(required = true, value = "")
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public BitmexNotification body(String body) {
    this.body = body;
    return this;
  }

   /**
   * Get body
   * @return body
  **/
  @ApiModelProperty(required = true, value = "")
  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public BitmexNotification ttl(BigDecimal ttl) {
    this.ttl = ttl;
    return this;
  }

   /**
   * Get ttl
   * @return ttl
  **/
  @ApiModelProperty(required = true, value = "")
  public BigDecimal getTtl() {
    return ttl;
  }

  public void setTtl(BigDecimal ttl) {
    this.ttl = ttl;
  }

  public BitmexNotification type(TypeEnum type) {
    this.type = type;
    return this;
  }

   /**
   * Get type
   * @return type
  **/
  @ApiModelProperty(value = "")
  public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  public BitmexNotification closable(Boolean closable) {
    this.closable = closable;
    return this;
  }

   /**
   * Get closable
   * @return closable
  **/
  @ApiModelProperty(value = "")
  public Boolean isClosable() {
    return closable;
  }

  public void setClosable(Boolean closable) {
    this.closable = closable;
  }

  public BitmexNotification persist(Boolean persist) {
    this.persist = persist;
    return this;
  }

   /**
   * Get persist
   * @return persist
  **/
  @ApiModelProperty(value = "")
  public Boolean isPersist() {
    return persist;
  }

  public void setPersist(Boolean persist) {
    this.persist = persist;
  }

  public BitmexNotification waitForVisibility(Boolean waitForVisibility) {
    this.waitForVisibility = waitForVisibility;
    return this;
  }

   /**
   * Get waitForVisibility
   * @return waitForVisibility
  **/
  @ApiModelProperty(value = "")
  public Boolean isWaitForVisibility() {
    return waitForVisibility;
  }

  public void setWaitForVisibility(Boolean waitForVisibility) {
    this.waitForVisibility = waitForVisibility;
  }

  public BitmexNotification sound(String sound) {
    this.sound = sound;
    return this;
  }

   /**
   * Get sound
   * @return sound
  **/
  @ApiModelProperty(value = "")
  public String getSound() {
    return sound;
  }

  public void setSound(String sound) {
    this.sound = sound;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BitmexNotification notification = (BitmexNotification) o;
    return Objects.equals(this.id, notification.id) &&
        Objects.equals(this.date, notification.date) &&
        Objects.equals(this.title, notification.title) &&
        Objects.equals(this.body, notification.body) &&
        Objects.equals(this.ttl, notification.ttl) &&
        Objects.equals(this.type, notification.type) &&
        Objects.equals(this.closable, notification.closable) &&
        Objects.equals(this.persist, notification.persist) &&
        Objects.equals(this.waitForVisibility, notification.waitForVisibility) &&
        Objects.equals(this.sound, notification.sound);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, date, title, body, ttl, type, closable, persist, waitForVisibility, sound);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BitmexNotification {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    body: ").append(toIndentedString(body)).append("\n");
    sb.append("    ttl: ").append(toIndentedString(ttl)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    closable: ").append(toIndentedString(closable)).append("\n");
    sb.append("    persist: ").append(toIndentedString(persist)).append("\n");
    sb.append("    waitForVisibility: ").append(toIndentedString(waitForVisibility)).append("\n");
    sb.append("    sound: ").append(toIndentedString(sound)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

