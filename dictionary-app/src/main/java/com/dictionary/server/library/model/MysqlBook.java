package com.dictionary.server.library.model;

import com.dictionary.server.auth.model.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Table(name = "book")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ApiModel("MysqlBook entity in MySQL table")
public class MysqlBook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ApiModelProperty("Public library's id. Can be null if it's not public book.")
    private Integer publicId;

    @ApiModelProperty("File name")
    private String fileName;

    @NotNull
    @ApiModelProperty("MysqlBook title")
    private String title;

    @NotNull
    @ApiModelProperty("Library source type. Is book in publiclibrary or userlibrary's library?")
    private LibrarySourceType librarySourceType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
