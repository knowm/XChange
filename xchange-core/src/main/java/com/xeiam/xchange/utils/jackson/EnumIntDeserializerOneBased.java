package com.xeiam.xchange.utils.jackson;

/**
 * @author Matija Mazi <br/>
 *         1-based enum deserializer. This wil deserialize 1 as the first enum constant, 2 as the second etc.
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
