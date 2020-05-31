
package com.dictionary.server.library.model.oxford.entry;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "entry_id",
    "sense_id"
})
public class ThesaurusLink_ {

    @JsonProperty("entry_id")
    private String entryId;
    @JsonProperty("sense_id")
    private String senseId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("entry_id")
    public String getEntryId() {
        return entryId;
    }

    @JsonProperty("entry_id")
    public void setEntryId(String entryId) {
        this.entryId = entryId;
    }

    @JsonProperty("sense_id")
    public String getSenseId() {
        return senseId;
    }

    @JsonProperty("sense_id")
    public void setSenseId(String senseId) {
        this.senseId = senseId;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
