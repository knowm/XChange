package org.knowm.xchange.coinsetter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import com.google.gson.Gson;

public final class GsonHelper {

  private static final Gson gson = new Gson();

  private GsonHelper() {

  }

  public static <T> T fromJson(URL src, Class<T> classOfT) throws IOException {

    InputStream inputStream = null;
    try {
      inputStream = src.openStream();
      Reader reader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
      return gson.fromJson(reader, classOfT);
    } finally {
      if (inputStream != null) {
        inputStream.close();
      }
    }
  }

}
