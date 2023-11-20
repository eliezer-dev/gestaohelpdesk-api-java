package dev.eliezer.superticket.service.impl;

import dev.eliezer.superticket.domain.model.User;
import dev.eliezer.superticket.domain.repository.UserRepository;
import dev.eliezer.superticket.service.UserService;
import dev.eliezer.superticket.service.exception.BusinessException;
import dev.eliezer.superticket.service.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    public User insert(User user) {
        userValidator(user);
        return userRepository.save(user);
    }

    @Override
    public User update(Long id, User user) {
        userValidator(user);
        User userToChange =  userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        userToChange.setCpf(user.getCpf());
        userToChange.setName(user.getName());
        userToChange.setCep(user.getCep());
        userToChange.setState(user.getState());
        userToChange.setCity(user.getCity());
        return userRepository.save(userToChange);
    }

    @Override
    public void delete(Long id) {
        User userToDelete = userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        userRepository.delete(userToDelete);
    }

    private void userValidator(User user){
        //checkIfObjectIsNull
        if (checkIfUserIsEmpty(user)) throw new BusinessException("This user data is empty");

        //checkIfPropertiesIsNull
        if (user.getCpf() == null) throw new BusinessException("cpf is not provided");
        if (user.getName() == null) throw new BusinessException("name is not provided");
        if (user.getCep() == null) throw new BusinessException("cep is not provided");
        if (user.getAddress() == null) throw new BusinessException("address is not provided");
        if (user.getAddressNumber() == null) throw new BusinessException("address number is not provided");
        if (user.getState() == null) throw new BusinessException("state is not provided");
        if (user.getCity() == null) throw new BusinessException("city is not provided");
    }

    public boolean checkIfUserIsEmpty(User user) {
        if (user.getCpf() != null) return false;
        if (user.getName() != null) return false;
        if (user.getCep() != null) return false;
        if (user.getCity() != null) return false;
        if (user.getState() != null) return false;
        if (user.getAddress() != null) return false;
        if (user.getAddressNumber() != null) return false;
        return true;
    }

}
