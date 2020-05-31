
package com.dictionary.server.library.model.oxford.entry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "etymologies",
    "senses",
    "notes",
    "grammaticalFeatures"
})
public class Entry {

    @JsonProperty("etymologies")
    private List<String> etymologies = null;
    @JsonProperty("senses")
    private List<Sense> senses = null;
    @JsonProperty("notes")
    private List<Note____> notes = null;
    @JsonProperty("grammaticalFeatures")
    private List<GrammaticalFeature> grammaticalFeatures = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("etymologies")
    public List<String> getEtymologies() {
        return etymologies;
    }

    @JsonProperty("etymologies")
    public void setEtymologies(List<String> etymologies) {
        this.etymologies = etymologies;
    }

    @JsonProperty("senses")
    public List<Sense> getSenses() {
        return senses;
    }

    @JsonProperty("senses")
    public void setSenses(List<Sense> senses) {
        this.senses = senses;
    }

    @JsonProperty("notes")
    public List<Note____> getNotes() {
        return notes;
    }

    @JsonProperty("notes")
    public void setNotes(List<Note____> notes) {
        this.notes = notes;
    }

    @JsonProperty("grammaticalFeatures")
    public List<GrammaticalFeature> getGrammaticalFeatures() {
        return grammaticalFeatures;
    }

    @JsonProperty("grammaticalFeatures")
    public void setGrammaticalFeatures(List<GrammaticalFeature> grammaticalFeatures) {
        this.grammaticalFeatures = grammaticalFeatures;
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
