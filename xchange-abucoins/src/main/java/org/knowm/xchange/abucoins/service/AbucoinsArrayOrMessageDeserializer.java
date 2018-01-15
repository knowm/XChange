package org.knowm.xchange.abucoins.service;

import java.io.IOException;
import java.lang.reflect.Array;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * <p>For several of the Abucoins APIs a JSON array is returned.  If there is an error however, the json is
 * a json object not an array.  To handle this case we have this generic JsonDeserializer that can handle an array or
 * a json object being returned.</p>
 * 
 * <h2>Assumptions</h2>
 * <ul>
 * <li>The type <em>T</em> represents a POJO that includes a <em>message</em> field to handle the error case.</li>
 * <li>The type <em>C</em> supplies a constructor that accepts and array of type T.
 * </ul>
 * @author bryant_harris
 *
 * @param <T> The individual array element type.
 * @param <C> The container type that contains an array of T.
 */
public abstract class AbucoinsArrayOrMessageDeserializer<T,C> extends JsonDeserializer<C> {
  Class<T> classT;
  Class<?> TarrayClass;
  Class<C> classC;
  public AbucoinsArrayOrMessageDeserializer(Class<T> classT, Class<C> classC) {
    this.classT = classT;
    this.classC = classC;
                
    Object array = Array.newInstance(classT, 0); 
    TarrayClass = array.getClass();
  }
        
  @Override
  @SuppressWarnings("unchecked")
  public C deserialize(JsonParser jsonParser, DeserializationContext ctx)
      throws IOException, JsonProcessingException {
    try {
      if ( jsonParser.isExpectedStartArrayToken()) {
        T[] array = (T[]) jsonParser.readValueAs(TarrayClass);
        return classC.getConstructor(new Class<?>[] { TarrayClass }).newInstance( new Object[] { array } );
      }
                
      // occurs if there's an error
      T singleInstance = jsonParser.readValueAs(classT);
                
      T[] array = (T[]) Array.newInstance(classT, 1);
      array[0] = singleInstance;
      return classC.getConstructor(new Class<?>[] { TarrayClass }).newInstance( new Object[] { array } );
    }
    catch (IOException e) {
      throw e;
    }
    catch (Exception e) {
      throw new IOException("Failed to dynamically instantiate classes", e);
    }
  }
}
