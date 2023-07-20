package org.web3j.crypto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.web3j.abi.TypeEncoder;
import org.web3j.abi.datatypes.AbiTypes;
import org.web3j.abi.datatypes.Type;
import static org.web3j.crypto.Hash.sha3;
import static org.web3j.crypto.Hash.sha3String;
import org.web3j.utils.Numeric;

public class FixedStructuredDataEncoder extends org.web3j.crypto.StructuredDataEncoder {
  public FixedStructuredDataEncoder(String jsonMessageInString) throws IOException, RuntimeException {
    super(jsonMessageInString);
  }

  @Override
  public List<Integer> getArrayDimensionsFromDeclaration(String declaration) {
    // All arrays are dynamic, bug in web3j does not detect these properly
    return List.of(-1);
  }

  // Bug in web3j does not encode primitive arrays properly (tries to cast value to Map of fields)
  public byte[] encodeData(String primaryType, HashMap<String, Object> data)
      throws RuntimeException {
    HashMap<String, List<StructuredData.Entry>> types = jsonMessageObject.getTypes();

    List<String> encTypes = new ArrayList<>();
    List<Object> encValues = new ArrayList<>();

    // Add typehash
    encTypes.add("bytes32");
    encValues.add(typeHash(primaryType));

    // Add field contents
    for (StructuredData.Entry field : types.get(primaryType)) {
      Object value = data.get(field.getName());

      if (field.getType().equals("string")) {
        encTypes.add("bytes32");
        byte[] hashedValue = Numeric.hexStringToByteArray(sha3String((String) value));
        encValues.add(hashedValue);
      } else if (field.getType().equals("bytes")) {
        encTypes.add(("bytes32"));
        encValues.add(sha3(Numeric.hexStringToByteArray((String) value)));
      } else if (types.containsKey(field.getType())) {
        // User Defined Type
        byte[] hashedValue =
            sha3(encodeData(field.getType(), (HashMap<String, Object>) value));
        encTypes.add("bytes32");
        encValues.add(hashedValue);
      } else if (bytesTypePattern.matcher(field.getType()).find()) {
        encTypes.add(field.getType());
        encValues.add(Numeric.hexStringToByteArray((String) value));
      } else if (arrayTypePattern.matcher(field.getType()).find()) {
        String baseTypeName = field.getType().substring(0, field.getType().indexOf('['));
        List<Integer> expectedDimensions =
            getArrayDimensionsFromDeclaration(field.getType());
        // This function will itself give out errors in case
        // that the data is not a proper array
        List<Integer> dataDimensions = getArrayDimensionsFromData(value);

        final String format =
            String.format(
                "Array Data %s has dimensions %s, "
                    + "but expected dimensions are %s",
                value.toString(),
                dataDimensions.toString(),
                expectedDimensions.toString());
        if (expectedDimensions.size() != dataDimensions.size()) {
          // Ex: Expected a 3d array, but got only a 2d array
          throw new RuntimeException(format);
        }
        for (int i = 0; i < expectedDimensions.size(); i++) {
          if (expectedDimensions.get(i) == -1) {
            // Skip empty or dynamically declared dimensions
            continue;
          }
          if (!expectedDimensions.get(i).equals(dataDimensions.get(i))) {
            throw new RuntimeException(format);
          }
        }

        List<Object> arrayItems = flattenMultidimensionalArray(value);

        List<String> arrayTypes = arrayItems.stream().map((x) -> baseTypeName).collect(Collectors.toList());
        arrayItems = arrayItems.stream().map((x) -> {
          if (x instanceof String) {
            return Numeric.hexStringToByteArray((String) x);
          } else {
            return x;
          }
        }).collect(Collectors.toList());

        byte[] encodedItems = encodeTypesAndValues(arrayTypes, arrayItems);
        byte[] hashedValue = sha3(encodedItems);
        encTypes.add("bytes32");
        encValues.add(hashedValue);
      } else {
        encTypes.add(field.getType());
        encValues.add(value);
      }
    }

    return encodeTypesAndValues(encTypes, encValues);
  }

  private static byte[] encodeTypesAndValues(List<String> encTypes, List<Object> encValues) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    for (int i = 0; i < encTypes.size(); i++) {
      Class<Type> typeClazz = (Class<Type>) AbiTypes.getType(encTypes.get(i));

      boolean atleastOneConstructorExistsForGivenParametersType = false;
      // Using the Reflection API to get the types of the parameters
      Constructor[] constructors = typeClazz.getConstructors();
      for (Constructor constructor : constructors) {
        // Check which constructor matches
        try {
          Class[] parameterTypes = constructor.getParameterTypes();
          byte[] temp =
              Numeric.hexStringToByteArray(
                  TypeEncoder.encode(
                      typeClazz
                          .getDeclaredConstructor(parameterTypes)
                          .newInstance(encValues.get(i))));
          baos.write(temp, 0, temp.length);
          atleastOneConstructorExistsForGivenParametersType = true;
          break;
        } catch (IllegalArgumentException
                 | NoSuchMethodException
                 | InstantiationException
                 | IllegalAccessException
                 | InvocationTargetException ignored) {
        }
      }

      if (!atleastOneConstructorExistsForGivenParametersType) {
        throw new RuntimeException(
            String.format(
                "Received an invalid argument for which no constructor"
                    + " exists for the ABI Class %s",
                typeClazz.getSimpleName()));
      }
    }
    byte[] result = baos.toByteArray();
    return result;
  }
}
