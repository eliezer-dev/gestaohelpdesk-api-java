package dev.eliezer.gestaohelpdesk.providers;

import dev.eliezer.gestaohelpdesk.modules.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class EncryptUserPasswords {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void execute(){
        userRepository.findAll().forEach(user -> {
            var passwordEncoded = passwordEncoder.encode(user.getPassword());
            user.setPassword(passwordEncoded);
            userRepository.save(user);
        });
    }

}
