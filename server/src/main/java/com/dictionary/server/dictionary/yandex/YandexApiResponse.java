
package com.dictionary.server.dictionary.yandex;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "code",
        "text",
        "lang"
})
public class YandexApiResponse {

    @JsonProperty("code")
    private Integer code;

    @JsonProperty("text")
    private List<String> text;

    @JsonProperty("lang")
    private String lang;


    @JsonProperty("code")
    public Integer getId() {
        return code;
    }

    @JsonProperty("id")
    public void setId(Integer code) {
        this.code = code;
    }

    @JsonProperty("text")
    public List<String> getText() {
        return text;
    }

    @JsonProperty("text")
    public void setText(List<String> text) {
        this.text = text;
    }

    @JsonProperty("lang")
    public String getLang() {
        return lang;
    }

    @JsonProperty("lang")
    public void setLang(String lang) {
        this.lang = lang;
    }
}
