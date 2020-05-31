
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
    "thesaurusLinks",
    "domains",
    "regions",
    "notes",
    "registers"
})
public class Subsense {

    @JsonProperty("definitions")
    private List<String> definitions = null;
    @JsonProperty("examples")
    private List<Example_> examples = null;
    @JsonProperty("id")
    private String id;
    @JsonProperty("shortDefinitions")
    private List<String> shortDefinitions = null;
    @JsonProperty("thesaurusLinks")
    private List<ThesaurusLink> thesaurusLinks = null;
    @JsonProperty("domains")
    private List<Domain> domains = null;
    @JsonProperty("regions")
    private List<Region> regions = null;
    @JsonProperty("notes")
    private List<Note__> notes = null;
    @JsonProperty("registers")
    private List<Register> registers = null;
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
    public List<Example_> getExamples() {
        return examples;
    }

    @JsonProperty("examples")
    public void setExamples(List<Example_> examples) {
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

    @JsonProperty("thesaurusLinks")
    public List<ThesaurusLink> getThesaurusLinks() {
        return thesaurusLinks;
    }

    @JsonProperty("thesaurusLinks")
    public void setThesaurusLinks(List<ThesaurusLink> thesaurusLinks) {
        this.thesaurusLinks = thesaurusLinks;
    }

    @JsonProperty("domains")
    public List<Domain> getDomains() {
        return domains;
    }

    @JsonProperty("domains")
    public void setDomains(List<Domain> domains) {
        this.domains = domains;
    }

    @JsonProperty("regions")
    public List<Region> getRegions() {
        return regions;
    }

    @JsonProperty("regions")
    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }

    @JsonProperty("notes")
    public List<Note__> getNotes() {
        return notes;
    }

    @JsonProperty("notes")
    public void setNotes(List<Note__> notes) {
        this.notes = notes;
    }

    @JsonProperty("registers")
    public List<Register> getRegisters() {
        return registers;
    }

    @JsonProperty("registers")
    public void setRegisters(List<Register> registers) {
        this.registers = registers;
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
