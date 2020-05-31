package com.dictionary.server.dictionary.oxford;

import com.dictionary.server.library.model.oxford.entry.OxfordApiResponse;
import org.springframework.http.ResponseEntity;

public interface OxfordDictionaryFacade {
    ResponseEntity<OxfordApiResponse> getWordDefinition(String word);
}
