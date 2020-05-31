package com.dictionary.server.auth;

import com.dictionary.server.auth.exception.UserException;
import com.dictionary.server.auth.model.User;
import com.dictionary.server.library.model.MysqlBook;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

public interface UserFacade {

    List<MysqlBook> getMysqlBooks(String username);
    void setMysqlBooks(String username, List<MysqlBook> mysqlBooks);

    List<String> getDictionary(String username);
    void setDictionary(String username, List<String> words);

    Integer getDemoLimit(String username);
    void setDemoLimit(String username, Integer limit);

    boolean isPremium(String username);

    User getByUsername(String username);


    static String getUsernameByPrincipal(Principal principal) {
        String username = principal.getName();
        if (Objects.isNull(username)) {
            throw new UserException("[UserFacade]: Cannot get username by auth token (principal). Username is null. Principal: " + principal.toString());
        }
        return username;
    }
}
