package com.dictionary.server.dictionary.yandex;

import com.dictionary.server.library.exception.ExternalRepoException;
import com.dictionary.server.library.exception.WordIsNotFoundInExternalRepoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
class YandexApiCaller {


    // TODO: Temporary solution. Move to application.properties.
    private static final String APP_KEY = "YANDEX_API_KEY";

    private static final String GET_DEFINITION_URL = "https://translate.yandex.net/api/v1.5/tr.json/translate?lang=en-ru&";

    String getTranslation(String text) {
        final String URL = GET_DEFINITION_URL + "key=" + APP_KEY + "&text=" + text;

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = getHttpEntity();

        ResponseEntity<YandexApiResponse> response;

        try {
            response = restTemplate.exchange(
                    URL, HttpMethod.POST, entity, YandexApiResponse.class);
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new WordIsNotFoundInExternalRepoException("[Yandex]: Word was not found!");
            } else {
                log.error("[Yandex]: Unexpected problem with public library repo! Ex: " + ex);
                throw new ExternalRepoException("[Yandex]: Unexpected problem with publiclibrary repo. Please contact with admin.");
            }
        }
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody().getText().get(0);
        } else {
            log.error("Yandex: probably problem with an API key. Verify it.");
            throw new WordIsNotFoundInExternalRepoException("[Yandex]: probably problem with a API key.");
        }
    }

    private HttpEntity getHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        //headers.set("Accept", "application/json");
        //headers.set("Content-Type", "application/x-www-form-urlencoded");
        return new HttpEntity(headers);
    }
}
