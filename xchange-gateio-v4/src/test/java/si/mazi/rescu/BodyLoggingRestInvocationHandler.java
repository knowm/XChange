package si.mazi.rescu;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

/**
 * Invocation handler that logs the raw textual response body
 */
@Slf4j
public class BodyLoggingRestInvocationHandler extends RestInvocationHandler {

    BodyLoggingRestInvocationHandler(Class<?> restInterface, String url, ClientConfig config) {
        super(restInterface, url, config);
    }

    @Override
    protected Object mapInvocationResult(InvocationResult invocationResult, RestMethodMetadata methodMetadata) throws IOException {
        // log the body
        log.info(invocationResult.getHttpBody());

        // do the normal processing
        return super.mapInvocationResult(invocationResult, methodMetadata);
    }
}
