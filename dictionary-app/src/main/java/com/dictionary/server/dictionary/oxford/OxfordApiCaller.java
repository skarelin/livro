package com.dictionary.server.dictionary.oxford;

import com.dictionary.server.library.exception.ExternalRepoException;
import com.dictionary.server.library.exception.WordIsNotFoundInExternalRepoException;
import com.dictionary.server.library.model.oxford.Lemma;
import com.dictionary.server.library.model.oxford.entry.OxfordApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
class OxfordApiCaller {

    @Value("${oxford_app_id}")
    private String appId;

    @Value("${oxford_app_key}")
    private String appKey;

    private static final String GET_DEFINITION_URL = "https://od-api.oxforddictionaries.com:443/api/v2/entries/";
    private static final String GET_DEFINITION_BY_LEMMA_URL = "https://od-api.oxforddictionaries.com/api/v2/lemmas/en/";
    private static final String DICTIONARY_LANG = "en-gb";
    private static final String FIELDS = "definitions,examples,variantForms";

    ResponseEntity<OxfordApiResponse> getDefinition(String word) {
        final String URL = GET_DEFINITION_URL + DICTIONARY_LANG + "/" + word.toLowerCase() + "?" + "fields=" + FIELDS + "&strictMatch=" + "?strictMatch=false";

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = getHttpEntity();

        ResponseEntity<OxfordApiResponse> response;
        try {
            response = restTemplate.exchange(
                    URL, HttpMethod.GET, entity, OxfordApiResponse.class);
        } catch(HttpClientErrorException ex) {
            if(ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new WordIsNotFoundInExternalRepoException("Word was not found!");
            } else {
                log.error("Unexpected problem with publiclibrary repo! Ex: " + ex);
                throw new ExternalRepoException("Unexpected problem with publiclibrary repo. Please contact with admin.");
            }
        }
        return response;
    }

    String getLemma(String word) {
        final String URL = GET_DEFINITION_BY_LEMMA_URL + word;

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = getHttpEntity();

        ResponseEntity<Lemma> response;
        try {
            response = restTemplate.exchange(
                    URL, HttpMethod.GET, entity, Lemma.class);
        } catch(HttpClientErrorException ex) {
            if(ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new WordIsNotFoundInExternalRepoException("Word was not found!");
            } else {
                log.error("Unexpected problem with publiclibrary repo! Ex: " + ex);
                throw new ExternalRepoException("Unexpected problem with publiclibrary repo. Please contact with admin.");
            }
        }
        return response.getBody().getResults().get(0).getLexicalEntries().get(0).getInflectionOf().get(0).getText();
    }

    private HttpEntity getHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("app_id", appId);
        headers.set("app_key", appKey);

        return new HttpEntity(headers);
    }
}
