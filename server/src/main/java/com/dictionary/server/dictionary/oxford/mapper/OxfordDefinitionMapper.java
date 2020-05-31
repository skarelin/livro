package com.dictionary.server.dictionary.oxford.mapper;

import com.dictionary.server.dictionary.oxford.model.MongoOxfordDefinition;
import com.dictionary.server.library.model.oxford.DictionaryEntry;
import com.dictionary.server.library.model.oxford.entry.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class OxfordDefinitionMapper {

    public static MongoOxfordDefinition toMongoDefinition(OxfordApiResponse oxfordApiResponse) {
        List<Result> oxfordResults = oxfordApiResponse.getResults();
        List<DictionaryEntry> dictionaryEntries = new ArrayList<>();

        for (Result oxfordResult : oxfordResults) { // TODO. Разделение на verb itd
            List<LexicalEntry> oxfordLexicalEntries = oxfordResult.getLexicalEntries();
            for (LexicalEntry oxfordLexicalEntry : oxfordLexicalEntries) {
                List<Entry> oxfordEntries = oxfordLexicalEntry.getEntries();
                List<DictionaryEntry> dictionaryEntriesResult = new ArrayList<>();
                for (Entry oxfordEntry : oxfordEntries) {
                    List<Sense> oxfordSenses = oxfordEntry.getSenses();
                    for (Sense oxfordSense : oxfordSenses) {
                        List<String> oxfordDefinitions = oxfordSense.getDefinitions();
                        List<Example> oxfordExamples = oxfordSense.getExamples();

                        DictionaryEntry dictionaryEntry = new DictionaryEntry();

                        List<String> dictionaryDefinitions = getDictionaryDefinitions(oxfordDefinitions);
                        List<String> dictionaryExamples = getDictionaryExamples(oxfordExamples);

                        dictionaryEntry.setDefinitions(dictionaryDefinitions);
                        dictionaryEntry.setExamples(dictionaryExamples);
                        dictionaryEntriesResult.add(dictionaryEntry);
                    }
                }
                dictionaryEntries.addAll(dictionaryEntriesResult);
            }
        }
        return MongoOxfordDefinition.builder().senses(dictionaryEntries).build();
    }

    private static List<String> getDictionaryExamples(List<Example> oxfordExamples) {
        if (oxfordExamples != null) {
            return oxfordExamples.stream().filter(Objects::nonNull).map(Example::getText).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }


    private static List<String> getDictionaryDefinitions(List<String> oxfordDefinitions) {
        if (oxfordDefinitions != null) {
            return new ArrayList<>(oxfordDefinitions);
        }
        return Collections.emptyList();
    }
}
