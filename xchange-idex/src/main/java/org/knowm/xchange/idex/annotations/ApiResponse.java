package org.knowm.xchange.idex.annotations;

public @interface ApiResponse {
  int code();

  String message();

  Class response();

  String responseContainer() default "";
}
