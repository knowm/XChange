package si.mazi.bitstampapi;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

/**
 * @author Matija Mazi <br/>
 * @created 12/19/12 7:45 AM
 */
public class BitstampFactory {
    static {
        ResteasyProviderFactory factory = ResteasyProviderFactory.getInstance();
        factory.registerProviderInstance(new JacksonJsonProvider());
        RegisterBuiltin.register(factory);
    }

    public static BitStamp createResteasyEndpoint() {
        ResteasyClient client = new ResteasyClient();
        ResteasyWebTarget target = client.target("https://www.bitstamp.net/");
        return target.proxy(BitStamp.class);
    }
}
