package org.knowm.xchange.upbit.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

/**
 * For several of the Upbit APIs a JSON array is returned. If there is an error however, the json is
 * a json object not an array. To handle this case we have this generic JsonDeserializer that can
 * handle an array or a json object being returned.
 *
 * <h2>Assumptions</h2>
 *
 * <ul>
 *   <li>The type <em>T</em> represents a POJO that includes a <em>message</em> field to handle the
 *       error case.
 *   <li>The type <em>C</em> supplies a constructor that accepts and array of type T.
 * </ul>
 *
 * @param <T> The individual array element type.
 * @param <C> The container type that contains an array of T.
 * @author bryant_harris
 */
public abstract class UpbitArrayOrMessageDeserializer<T, C> extends JsonDeserializer<C> {
  Class<T> classT;
  Class<?> TarrayClass;
  Class<C> classC;
  boolean useCollectionClassForMessage = false;

  public UpbitArrayOrMessageDeserializer(
      Class<T> classT, Class<C> classC, boolean collectionHasMessageFlag) {
    this.classT = classT;
    this.classC = classC;
    useCollectionClassForMessage = collectionHasMessageFlag;

    Object array = Array.newInstance(classT, 0);
    TarrayClass = array.getClass();
  }

  public UpbitArrayOrMessageDeserializer(Class<T> classT, Class<C> classC) {
    this(classT, classC, false);
  }

  @Override
  @SuppressWarnings("unchecked")
  public C deserialize(JsonParser jsonParser, DeserializationContext ctx)
      throws IOException, JsonProcessingException {
    try {
      if (jsonParser.isExpectedStartArrayToken()) {
        T[] array = (T[]) jsonParser.readValueAs(TarrayClass);
        return classC
            .getConstructor(new Class<?>[] {TarrayClass})
            .newInstance(new Object[] {array});
      }

      if (useCollectionClassForMessage) {
        JsonToken jsonToken = jsonParser.nextToken();

        // Some hacky assumptions here that the container
        // class has a public 'message' Field on it that we
        // can set.
        if (JsonToken.FIELD_NAME.equals(jsonToken)) {
          jsonToken = jsonParser.nextToken();
          String message = jsonParser.getValueAsString();
          C containerInstance = classC.newInstance();
          Field f = classC.getField("message");
          f.set(containerInstance, message);

          return containerInstance;
        }

        throw new IOException("Failed to parse");
      } else {
        // occurs if there's an error
        T singleInstance = jsonParser.readValueAs(classT);

        T[] array = (T[]) Array.newInstance(classT, 1);
        array[0] = singleInstance;
        return classC
            .getConstructor(new Class<?>[] {TarrayClass})
            .newInstance(new Object[] {array});
      }
    } catch (IOException e) {
      throw e;
    } catch (Exception e) {
      throw new IOException("Failed to dynamically instantiate classes", e);
    }
  }
}
