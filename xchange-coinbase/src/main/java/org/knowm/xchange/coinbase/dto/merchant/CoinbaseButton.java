package org.knowm.xchange.coinbase.dto.merchant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.coinbase.dto.CoinbaseBaseResponse;
import org.knowm.xchange.coinbase.dto.common.CoinbaseRepeat;
import org.knowm.xchange.coinbase.dto.marketdata.CoinbaseMoney;
import org.knowm.xchange.coinbase.dto.serialization.CoinbaseCentsDeserializer;

/** @author jamespedwards42 */
public class CoinbaseButton extends CoinbaseBaseResponse {

  @JsonProperty("button")
  private final CoinbaseButtonInfo button;

  private CoinbaseButton(
      @JsonProperty("button") final CoinbaseButtonInfo button,
      @JsonProperty("success") final boolean success,
      @JsonProperty("errors") final List<String> errors) {

    super(success, errors);
    this.button = button;
  }

  CoinbaseButton(CoinbaseButtonInfo button) {

    super(true);
    this.button = button;
  }

  @JsonIgnore
  public CoinbaseButtonType getType() {

    return button.getType();
  }

  @JsonIgnore
  public String getName() {

    return button.getName();
  }

  @JsonIgnore
  public String getDescription() {

    return button.getDescription();
  }

  @JsonIgnore
  public String getId() {

    return button.getId();
  }

  @JsonIgnore
  public CoinbaseMoney getPrice() {

    return button.getPrice();
  }

  @JsonIgnore
  public String getPriceString() {

    return button.getPriceString();
  }

  @JsonIgnore
  public String getCurrency() {

    return button.getCurrency();
  }

  @JsonIgnore
  public String getCustom() {

    return button.getCustom();
  }

  @JsonIgnore
  public CoinbaseButtonStyle getStyle() {

    return button.getStyle();
  }

  @JsonIgnore
  public String getCode() {

    return button.getCode();
  }

  @JsonIgnore
  public String getText() {

    return button.getText();
  }

  @JsonIgnore
  public CoinbaseRepeat getRepeat() {

    return button.getRepeat();
  }

  @JsonIgnore
  public boolean isCustomSecure() {

    return button.isCustomSecure();
  }

  @JsonIgnore
  public String getCallbackUrl() {

    return button.getCallbackUrl();
  }

  @JsonIgnore
  public String getSuccessUrl() {

    return button.getSuccessUrl();
  }

  @JsonIgnore
  public String getCancelUrl() {

    return button.getCancelUrl();
  }

  @JsonIgnore
  public String getInfoUrl() {

    return button.getInfoUrl();
  }

  @JsonIgnore
  public boolean isAutoReDirect() {

    return button.isAutoReDirect();
  }

  @JsonIgnore
  public boolean isVariablePrice() {

    return button.isVariablePrice();
  }

  @JsonIgnore
  public boolean isChoosePrice() {

    return button.isChoosePrice();
  }

  @JsonIgnore
  public boolean isIncludeAddress() {

    return button.isIncludeAddress();
  }

  @JsonIgnore
  public boolean isIncludeEmail() {

    return button.isIncludeEmail();
  }

  @JsonIgnore
  public List<String> getSuggestedPrices() {

    final List<String> suggestedPrices = new ArrayList<>();
    suggestedPrices.add(button.price1);
    suggestedPrices.add(button.price2);
    suggestedPrices.add(button.price3);
    suggestedPrices.add(button.price4);
    suggestedPrices.add(button.price5);
    return suggestedPrices;
  }

  @JsonIgnore
  public String getPrice1() {

    return button.getPrice1();
  }

  @JsonIgnore
  public String getPrice2() {

    return button.getPrice2();
  }

  @JsonIgnore
  public String getPrice3() {

    return button.getPrice3();
  }

  @JsonIgnore
  public String getPrice4() {

    return button.getPrice4();
  }

  @JsonIgnore
  public String getPrice5() {

    return button.getPrice5();
  }

  @Override
  public String toString() {

    return "CoinbaseButton [button=" + button + "]";
  }

  public static class CoinbaseButtonBuilder {

    private static final int MAX_SUGGESTED_PRICES = 5;
    private final String name;
    private final CoinbaseMoney price;
    private CoinbaseButtonType type;
    private String description;
    private String custom;
    private CoinbaseButtonStyle style;
    private String text;
    private CoinbaseRepeat repeat;
    private boolean customSecure;
    private String callbackUrl;
    private String successUrl;
    private String cancelUrl;
    private String infoUrl;
    private boolean autoDirect;
    private boolean variablePrice;
    private boolean choosePrice;
    private boolean includeAddress;
    private boolean includeEmail;
    private String price1;
    private String price2;
    private String price3;
    private String price4;
    private String price5;

    public CoinbaseButtonBuilder(String name, final String currency, final String priceString) {

      this(name, new CoinbaseMoney(currency, new BigDecimal(priceString)));
    }

    public CoinbaseButtonBuilder(String name, final CoinbaseMoney price) {

      this.name = name;
      this.price = price;
    }

    public CoinbaseButton buildButton() {

      final CoinbaseButtonInfo buttonInfo =
          new CoinbaseButtonInfo(
              name,
              price,
              type,
              description,
              null,
              custom,
              style,
              null,
              text,
              repeat,
              customSecure,
              callbackUrl,
              successUrl,
              cancelUrl,
              infoUrl,
              autoDirect,
              variablePrice,
              choosePrice,
              includeAddress,
              includeEmail,
              price1,
              price2,
              price3,
              price4,
              price5);

      return new CoinbaseButton(buttonInfo);
    }

    public String getName() {

      return name;
    }

    public CoinbaseMoney getPrice() {

      return price;
    }

    public String getPriceString() {

      return price.getAmount().toPlainString();
    }

    public String getCurrency() {

      return price.getCurrency();
    }

    public CoinbaseButtonType getType() {

      return type;
    }

    public CoinbaseButtonBuilder withType(CoinbaseButtonType type) {

      this.type = type;
      return this;
    }

    public String getDescription() {

      return description;
    }

    public CoinbaseButtonBuilder withDescription(String description) {

      this.description = description;
      return this;
    }

    public String getCustom() {

      return custom;
    }

    public CoinbaseButtonBuilder withCustom(String custom) {

      this.custom = custom;
      return this;
    }

    public CoinbaseButtonStyle getStyle() {

      return style;
    }

    public CoinbaseButtonBuilder withStyle(CoinbaseButtonStyle style) {

      this.style = style;
      return this;
    }

    public String getText() {

      return text;
    }

    public CoinbaseButtonBuilder withText(String text) {

      this.text = text;
      return this;
    }

    public CoinbaseRepeat getRepeat() {

      return repeat;
    }

    public CoinbaseButtonBuilder withRepeat(CoinbaseRepeat repeat) {

      this.repeat = repeat;
      return this;
    }

    public boolean isCustomSecure() {

      return customSecure;
    }

    public CoinbaseButtonBuilder withCustomSecure(boolean customSecure) {

      this.customSecure = customSecure;
      return this;
    }

    public String getCallbackUrl() {

      return callbackUrl;
    }

    public CoinbaseButtonBuilder withCallbackUrl(String callbackUrl) {

      this.callbackUrl = callbackUrl;
      return this;
    }

    public String getSuccessUrl() {

      return successUrl;
    }

    public CoinbaseButtonBuilder withSuccessUrl(String successUrl) {

      this.successUrl = successUrl;
      return this;
    }

    public String getCancelUrl() {

      return cancelUrl;
    }

    public CoinbaseButtonBuilder withCancelUrl(String cancelUrl) {

      this.cancelUrl = cancelUrl;
      return this;
    }

    public String getInfoUrl() {

      return infoUrl;
    }

    public CoinbaseButtonBuilder withInfoUrl(String infoUrl) {

      this.infoUrl = infoUrl;
      return this;
    }

    public boolean isAutoDirect() {

      return autoDirect;
    }

    public CoinbaseButtonBuilder withAutoDirect(boolean autoDirect) {

      this.autoDirect = autoDirect;
      return this;
    }

    public boolean isVariablePrice() {

      return variablePrice;
    }

    public CoinbaseButtonBuilder withVariablePrice(boolean variablePrice) {

      this.variablePrice = variablePrice;
      return this;
    }

    public boolean isChoosePrice() {

      return choosePrice;
    }

    public CoinbaseButtonBuilder withChoosePrice(boolean choosePrice) {

      this.choosePrice = choosePrice;
      return this;
    }

    public boolean isIncludeAddress() {

      return includeAddress;
    }

    public CoinbaseButtonBuilder withIncludeAddress(boolean includeAddress) {

      this.includeAddress = includeAddress;
      return this;
    }

    public boolean isIncludeEmail() {

      return includeEmail;
    }

    public CoinbaseButtonBuilder withIncludeEmail(boolean includeEmail) {

      this.includeEmail = includeEmail;
      return this;
    }

    public String getPrice1() {

      return price1;
    }

    public String getPrice2() {

      return price2;
    }

    public String getPrice3() {

      return price3;
    }

    public String getPrice4() {

      return price4;
    }

    public String getPrice5() {

      return price5;
    }

    public CoinbaseButtonBuilder withSuggestedPrices(String... suggestedPrices) {

      if (suggestedPrices.length > MAX_SUGGESTED_PRICES)
        throw new IllegalArgumentException(
            "Only "
                + MAX_SUGGESTED_PRICES
                + " suggested prices are allowed. There was an attempt to add "
                + suggestedPrices.length
                + " prices.");

      switch (suggestedPrices.length) {
        case 5:
          this.price5 = suggestedPrices[4];
        case 4:
          this.price4 = suggestedPrices[3];
        case 3:
          this.price3 = suggestedPrices[2];
        case 2:
          this.price2 = suggestedPrices[1];
        case 1:
          this.price1 = suggestedPrices[0];
      }

      return this;
    }
  }

  static class CoinbaseButtonInfo {

    private final String name;
    private final CoinbaseMoney price;
    private final CoinbaseButtonType type;
    private final String description;
    private final String id;
    private final String custom;
    private final CoinbaseButtonStyle style;
    private final String code;
    private final String text;
    private final CoinbaseRepeat repeat;
    private final boolean customSecure;
    private final String callbackUrl;
    private final String successUrl;
    private final String cancelUrl;
    private final String infoUrl;
    private final boolean autoReDirect;
    private final boolean variablePrice;
    private final boolean choosePrice;
    private final boolean includeAddress;
    private final boolean includeEmail;
    private final String price1;
    private final String price2;
    private final String price3;
    private final String price4;
    private final String price5;

    private CoinbaseButtonInfo(
        @JsonProperty("name") final String name,
        @JsonProperty("price") @JsonDeserialize(using = CoinbaseCentsDeserializer.class)
            final CoinbaseMoney price,
        @JsonProperty("type") final CoinbaseButtonType type,
        @JsonProperty("description") final String description,
        @JsonProperty("id") final String id,
        @JsonProperty("custom") final String custom,
        @JsonProperty("style") final CoinbaseButtonStyle style,
        @JsonProperty("code") final String code,
        @JsonProperty("text") final String text,
        @JsonProperty("repeat") final CoinbaseRepeat repeat,
        @JsonProperty("custom_secure") final boolean customSecure,
        @JsonProperty("callback_url") final String callbackUrl,
        @JsonProperty("success_url") final String successUrl,
        @JsonProperty("cancel_url") final String cancelUrl,
        @JsonProperty("info_url") final String infoUrl,
        @JsonProperty("auto_redirect") final boolean autoDirect,
        @JsonProperty("variable_price") final boolean variablePrice,
        @JsonProperty("choose_price") final boolean choosePrice,
        @JsonProperty("include_address") final boolean includeAddress,
        @JsonProperty("include_email") final boolean includeEmail,
        @JsonProperty("price1") final String price1,
        @JsonProperty("price2") final String price2,
        @JsonProperty("price3") final String price3,
        @JsonProperty("price4") final String price4,
        @JsonProperty("price5") final String price5) {

      this.name = name;
      this.price = price;
      this.type = type;
      this.description = description;
      this.id = id;
      this.custom = custom;
      this.style = style;
      this.code = code;
      this.text = text;
      this.repeat = repeat;
      this.customSecure = customSecure;
      this.callbackUrl = callbackUrl;
      this.successUrl = successUrl;
      this.cancelUrl = cancelUrl;
      this.infoUrl = infoUrl;
      this.autoReDirect = autoDirect;
      this.variablePrice = variablePrice;
      this.choosePrice = choosePrice;
      this.includeAddress = includeAddress;
      this.includeEmail = includeEmail;
      this.price1 = price1;
      this.price2 = price2;
      this.price3 = price3;
      this.price4 = price4;
      this.price5 = price5;
    }

    @JsonProperty("type")
    public CoinbaseButtonType getType() {

      return type;
    }

    @JsonProperty("name")
    public String getName() {

      return name;
    }

    @JsonProperty("description")
    public String getDescription() {

      return description;
    }

    @JsonIgnore
    public String getId() {

      return id;
    }

    @JsonIgnore
    public CoinbaseMoney getPrice() {

      return price;
    }

    @JsonProperty("price_string")
    public String getPriceString() {

      return price.getAmount().toPlainString();
    }

    @JsonProperty("price_currency_iso")
    public String getCurrency() {

      return price.getCurrency();
    }

    @JsonProperty("custom")
    public String getCustom() {

      return custom;
    }

    @JsonProperty("style")
    public CoinbaseButtonStyle getStyle() {

      return style;
    }

    @JsonIgnore
    public String getCode() {

      return code;
    }

    @JsonProperty("text")
    public String getText() {

      return text;
    }

    @JsonProperty("repeat")
    public CoinbaseRepeat getRepeat() {

      return repeat;
    }

    @JsonProperty("custom_secure")
    public boolean isCustomSecure() {

      return customSecure;
    }

    @JsonProperty("callback_url")
    public String getCallbackUrl() {

      return callbackUrl;
    }

    @JsonProperty("success_url")
    public String getSuccessUrl() {

      return successUrl;
    }

    @JsonProperty("cancel_url")
    public String getCancelUrl() {

      return cancelUrl;
    }

    @JsonProperty("info_url")
    public String getInfoUrl() {

      return infoUrl;
    }

    @JsonProperty("auto_direct")
    public boolean isAutoReDirect() {

      return autoReDirect;
    }

    @JsonProperty("variable_price")
    public boolean isVariablePrice() {

      return variablePrice;
    }

    @JsonProperty("choose_price")
    public boolean isChoosePrice() {

      return choosePrice;
    }

    @JsonProperty("include_address")
    public boolean isIncludeAddress() {

      return includeAddress;
    }

    @JsonProperty("include_email")
    public boolean isIncludeEmail() {

      return includeEmail;
    }

    @JsonProperty("price1")
    public String getPrice1() {

      return price1;
    }

    @JsonProperty("price2")
    public String getPrice2() {

      return price2;
    }

    @JsonProperty("price3")
    public String getPrice3() {

      return price3;
    }

    @JsonProperty("price4")
    public String getPrice4() {

      return price4;
    }

    @JsonProperty("price5")
    public String getPrice5() {

      return price5;
    }

    @Override
    public String toString() {

      return "CoinbaseButtonInfo [name="
          + name
          + ", price="
          + price
          + ", type="
          + type
          + ", description="
          + description
          + ", id="
          + id
          + ", custom="
          + custom
          + ", style="
          + style
          + ", code="
          + code
          + ", text="
          + text
          + ", repeat="
          + repeat
          + ", customSecure="
          + customSecure
          + ", callbackUrl="
          + callbackUrl
          + ", successUrl="
          + successUrl
          + ", cancelUrl="
          + cancelUrl
          + ", infoUrl="
          + infoUrl
          + ", autoDirect="
          + autoReDirect
          + ", variablePrice="
          + variablePrice
          + ", choosePrice="
          + choosePrice
          + ", includeAddress="
          + includeAddress
          + ", includeEmail="
          + includeEmail
          + ", price1="
          + price1
          + ", price2="
          + price2
          + ", price3="
          + price3
          + ", price4="
          + price4
          + ", price5="
          + price5
          + "]";
    }
  }
}
