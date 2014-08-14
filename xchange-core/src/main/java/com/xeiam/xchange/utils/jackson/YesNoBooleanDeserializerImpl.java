package com.xeiam.xchange.utils.jackson;

/**
 * @author Matija Mazi
 */
public class YesNoBooleanDeserializerImpl extends BooleanDeserializer {

  protected YesNoBooleanDeserializerImpl() {

    super("Yes", "No");
  }
}
