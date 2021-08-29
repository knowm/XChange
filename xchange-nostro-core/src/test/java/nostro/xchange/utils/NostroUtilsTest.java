package nostro.xchange.utils;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NostroUtilsTest {

    @Test
    public void randomUUID() {
        assertThat(NostroUtils.randomUUID().length()).isEqualTo(32);
        NostroUtils.randomUUID().chars()
                .forEach(i -> assertThat(Character.digit((char) i, 16) >= 0).isTrue());
    }

}