package com.dictionary.server.dictionary.oxford;

import com.dictionary.server.library.exception.WordIsNotFoundInExternalRepoException;
import com.dictionary.server.library.model.oxford.entry.OxfordApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class OxfordDictionaryFacadeImpl implements OxfordDictionaryFacade {

    private OxfordApiCaller oxfordApiCaller;

    @Autowired
    OxfordDictionaryFacadeImpl(OxfordApiCaller oxfordApiCaller) {
        this.oxfordApiCaller = oxfordApiCaller;
    }

    @Override
    public ResponseEntity<OxfordApiResponse> getWordDefinition(String word) {
        try {
            return oxfordApiCaller.getDefinition(word);
        } catch (WordIsNotFoundInExternalRepoException e) {
            String lemma = oxfordApiCaller.getLemma(word);
            return oxfordApiCaller.getDefinition(lemma);
        }
    }
}
