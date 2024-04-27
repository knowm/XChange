package dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class BybitPublicOrder {

  private final String price;
  private final String size;

  @JsonCreator
  public BybitPublicOrder(String[] data) {
    this.price = data[0];
    this.size = data[1];
  }
}

