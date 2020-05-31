package com.dictionary.server.auth.model;


import com.dictionary.server.library.model.MysqlBook;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Table(name = "userEntity")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String uid;
    @NotNull
    private String username;
    //@NotNull
    private String email;

    @NotNull
    private Boolean premium;

    private Integer demoLimit;


    @ElementCollection
    private List<String> dictionary;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<MysqlBook> mysqlBooks;
}
