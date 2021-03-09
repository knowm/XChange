package org.knowm.xchange.lgo.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collections;
import java.util.List;

public final class LgoProducts {

  private final List<LgoProduct> products;

  public LgoProducts(@JsonProperty("products") List<LgoProduct> products) {
    this.products = Collections.unmodifiableList(products);
  }

  public List<LgoProduct> getProducts() {
    return products;
  }
}
