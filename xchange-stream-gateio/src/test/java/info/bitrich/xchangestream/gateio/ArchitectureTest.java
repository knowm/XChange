package info.bitrich.xchangestream.gateio;


import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import java.time.Instant;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class ArchitectureTest {

  @Test
  void use_clock_for_now_timestamps() {
    JavaClasses classes = new ClassFileImporter()
        .importPackagesOf(GateioStreamingExchange.class);

    ArchRule rule = noClasses()
        .should().callMethod(LocalDateTime.class, "now")
        .orShould().callMethod(Instant.class, "now")
        .because("we want to use variant with clock instead");

    rule.check(classes);
  }
}