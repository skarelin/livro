package com.dictionary.server.library.model.oxford;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DictionaryEntry {
    private List<String> definitions;
    private List<String> examples;
}
