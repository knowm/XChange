package org.knowm.xchange.idex.annotations;

public @interface ApiOperation {
  String value();

  String notes();

  String[] tags();
}
