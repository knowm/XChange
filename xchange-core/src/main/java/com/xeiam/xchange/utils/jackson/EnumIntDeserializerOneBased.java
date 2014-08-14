package com.xeiam.xchange.utils.jackson;

/**
 * 1-based enum deserializer.
 * <p>
 * This will deserialize 1 as the first enum constant, 2 as the second etc.
 * </p>
 * 
 * @author Matija Mazi
 */
public class EnumIntDeserializerOneBased<E extends Enum<E>> extends EnumIntDeserializer<E> {

  protected EnumIntDeserializerOneBased(Class<E> enumClass) {

    super(enumClass);
  }

  @Override
  protected int getIndexBase() {

    return 1;
  }
}
