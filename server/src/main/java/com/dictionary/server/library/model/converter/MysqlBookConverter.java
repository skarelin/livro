package com.dictionary.server.library.model.converter;

import com.dictionary.server.library.model.MysqlBook;
import com.dictionary.server.library.model.dto.MysqlBookDTO;

public class MysqlBookConverter {
    public static MysqlBookDTO toDto(MysqlBook domain) {
        return MysqlBookDTO.builder()
                .id(domain.getId())
                .title(domain.getTitle())
                .filename(domain.getFileName())
                .fileExtension(FileExtensionDeterminator.determineBy(domain.getFileName()))
                .librarySourceType(domain.getLibrarySourceType())
                .build();
    }
}
