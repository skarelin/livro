package com.dictionary.server.dictionary.yandex.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class YandexTranslation {
    private String englishOriginal;
    private String russianTranslation;
}
