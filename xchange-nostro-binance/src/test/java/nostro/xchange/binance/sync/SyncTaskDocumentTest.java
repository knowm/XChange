package nostro.xchange.binance.sync;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SyncTaskDocumentTest {
    @Test
    public void documentSerialization() {
        String s = SyncTaskDocument.write(new SyncTaskDocument(517, 18181818));
        SyncTaskDocument document = SyncTaskDocument.read(s);
        
        assertThat(document.getOrderId()).isEqualTo(517);
        assertThat(document.getTradeId()).isEqualTo(18181818);

    }
}