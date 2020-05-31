
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
    "definitions",
    "examples",
    "id",
    "shortDefinitions",
    "subsenses",
    "thesaurusLinks",
    "domains",
    "notes",
    "variantForms",
    "crossReferenceMarkers",
    "crossReferences",
    "regions"
})
public class Sense {

    @JsonProperty("definitions")
    private List<String> definitions = null;
    @JsonProperty("examples")
    private List<Example> examples = null;
    @JsonProperty("id")
    private String id;
    @JsonProperty("shortDefinitions")
    private List<String> shortDefinitions = null;
    @JsonProperty("subsenses")
    private List<Subsense> subsenses = null;
    @JsonProperty("thesaurusLinks")
    private List<ThesaurusLink_> thesaurusLinks = null;
    @JsonProperty("domains")
    private List<Domain_> domains = null;
    @JsonProperty("notes")
    private List<Note___> notes = null;
    @JsonProperty("variantForms")
    private List<VariantForm> variantForms = null;
    @JsonProperty("crossReferenceMarkers")
    private List<String> crossReferenceMarkers = null;
    @JsonProperty("crossReferences")
    private List<CrossReference> crossReferences = null;
    @JsonProperty("regions")
    private List<Region_> regions = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("definitions")
    public List<String> getDefinitions() {
        return definitions;
    }

    @JsonProperty("definitions")
    public void setDefinitions(List<String> definitions) {
        this.definitions = definitions;
    }

    @JsonProperty("examples")
    public List<Example> getExamples() {
        return examples;
    }

    @JsonProperty("examples")
    public void setExamples(List<Example> examples) {
        this.examples = examples;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("shortDefinitions")
    public List<String> getShortDefinitions() {
        return shortDefinitions;
    }

    @JsonProperty("shortDefinitions")
    public void setShortDefinitions(List<String> shortDefinitions) {
        this.shortDefinitions = shortDefinitions;
    }

    @JsonProperty("subsenses")
    public List<Subsense> getSubsenses() {
        return subsenses;
    }

    @JsonProperty("subsenses")
    public void setSubsenses(List<Subsense> subsenses) {
        this.subsenses = subsenses;
    }

    @JsonProperty("thesaurusLinks")
    public List<ThesaurusLink_> getThesaurusLinks() {
        return thesaurusLinks;
    }

    @JsonProperty("thesaurusLinks")
    public void setThesaurusLinks(List<ThesaurusLink_> thesaurusLinks) {
        this.thesaurusLinks = thesaurusLinks;
    }

    @JsonProperty("domains")
    public List<Domain_> getDomains() {
        return domains;
    }

    @JsonProperty("domains")
    public void setDomains(List<Domain_> domains) {
        this.domains = domains;
    }

    @JsonProperty("notes")
    public List<Note___> getNotes() {
        return notes;
    }

    @JsonProperty("notes")
    public void setNotes(List<Note___> notes) {
        this.notes = notes;
    }

    @JsonProperty("variantForms")
    public List<VariantForm> getVariantForms() {
        return variantForms;
    }

    @JsonProperty("variantForms")
    public void setVariantForms(List<VariantForm> variantForms) {
        this.variantForms = variantForms;
    }

    @JsonProperty("crossReferenceMarkers")
    public List<String> getCrossReferenceMarkers() {
        return crossReferenceMarkers;
    }

    @JsonProperty("crossReferenceMarkers")
    public void setCrossReferenceMarkers(List<String> crossReferenceMarkers) {
        this.crossReferenceMarkers = crossReferenceMarkers;
    }

    @JsonProperty("crossReferences")
    public List<CrossReference> getCrossReferences() {
        return crossReferences;
    }

    @JsonProperty("crossReferences")
    public void setCrossReferences(List<CrossReference> crossReferences) {
        this.crossReferences = crossReferences;
    }

    @JsonProperty("regions")
    public List<Region_> getRegions() {
        return regions;
    }

    @JsonProperty("regions")
    public void setRegions(List<Region_> regions) {
        this.regions = regions;
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
