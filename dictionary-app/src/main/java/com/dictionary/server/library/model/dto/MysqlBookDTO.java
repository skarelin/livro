package com.dictionary.server.library.model.dto;

import com.dictionary.server.library.model.LibrarySourceType;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@Builder
@ToString
public class MysqlBookDTO {
    private UUID id;
    private String title;
    private String filename;
    private String fileExtension;
    private LibrarySourceType librarySourceType;
}
