package info.bitrich.xchangestream.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Foat Akhmadeev
 * 08/06/2018
 */
public class PropsLoader {
    private static final Logger LOG = LoggerFactory.getLogger(PropsLoader.class);

    public static LocalExchangeConfig loadKeys(String fileName, String originName) throws IOException {
        try (FileInputStream input = new FileInputStream(fileName)) {
            Properties properties = new Properties();
            properties.load(input);
            return new LocalExchangeConfig(properties.getProperty("api.key"), properties.getProperty("secret.key"));
        } catch (FileNotFoundException e) {
            LOG.error("Please create {} file from {}", fileName, originName);
            throw e;
        }
    }
}
