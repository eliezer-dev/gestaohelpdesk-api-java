package dev.eliezer.superticket.service;

import dev.eliezer.superticket.domain.model.User;

public interface UserService {
    Iterable<User> findAll();
    User findById(Long id);

    User insert(User user);

    User update(Long id, User user);

    void delete (Long id);
    
}
