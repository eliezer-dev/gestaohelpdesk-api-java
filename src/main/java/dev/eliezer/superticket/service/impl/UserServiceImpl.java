package dev.eliezer.superticket.service.impl;

import dev.eliezer.superticket.domain.model.User;
import dev.eliezer.superticket.domain.repository.UserRepository;
import dev.eliezer.superticket.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public Iterable<User> findAll() {
        System.out.println(userRepository.findAll());
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public User insert(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(Long id, User user) {
        User userToChange =  userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        userToChange.setCpf(user.getCpf());
        userToChange.setName(user.getName());
        userToChange.setCep(user.getCep());
        userToChange.setState(user.getState());
        userToChange.setCity(user.getCity());
        return userRepository.save(userToChange);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
    
}
