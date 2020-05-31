package com.dictionary.server.auth;

import com.dictionary.server.auth.exception.UserException;
import com.dictionary.server.auth.exception.UserSessionIsNotActive;
import com.dictionary.server.auth.model.User;
import com.dictionary.server.library.model.MysqlBook;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFacadeImpl implements UserFacade {

    private UserRepository userRepository;

    UserFacadeImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<MysqlBook> getMysqlBooks(String username) {
        User user = getByUsername(username);
        return user.getMysqlBooks();
    }

    @Override
    public void setMysqlBooks(String username, List<MysqlBook> mysqlBooks) {
        User user = getByUsername(username);
        user.setMysqlBooks(mysqlBooks);
        userRepository.save(user);
    }

    @Override
    public List<String> getDictionary(String username) {
        User user = getByUsername(username);
        return user.getDictionary();
    }

    @Override
    public void setDictionary(String username, List<String> words) {
        User user = getByUsername(username);
        user.setDictionary(words);
        userRepository.save(user);
    }

    @Override
    public Integer getDemoLimit(String username) {
        User user = getByUsername(username);
        Integer demoLimit = user.getDemoLimit();
        if (demoLimit == null) {
            user.setDemoLimit(0);
            userRepository.save(user);
            return 0;
        }
        return user.getDemoLimit();
    }

    @Override
    public void setDemoLimit(String username, Integer limit) {
        User user = getByUsername(username);
        user.setDemoLimit(limit);
        userRepository.save(user);
    }

    @Override
    public boolean isPremium(String username) {
        return getByUsername(username).getPremium();
    }

    @Override
    @Deprecated //TODO. Should be private.
    public User getByUsername(String username) {
        if (username == null) {
            throw new UserSessionIsNotActive("User session is not active! Refresh bearer token.");
        }
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserException("Username can't be null! Refresh bearer token.");
        }
        return user;
    }
}
