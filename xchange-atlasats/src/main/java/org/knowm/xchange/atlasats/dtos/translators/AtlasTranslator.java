package org.knowm.xchange.atlasats.dtos.translators;

public interface AtlasTranslator<SourceObject, TranslatedObject> {

  public TranslatedObject translate(SourceObject sourceObject);

}
