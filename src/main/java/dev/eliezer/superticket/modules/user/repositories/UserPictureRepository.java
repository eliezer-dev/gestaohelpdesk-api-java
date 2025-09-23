package dev.eliezer.superticket.modules.user.repositories;

import dev.eliezer.superticket.modules.user.entities.UserPicture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPictureRepository extends JpaRepository<UserPicture, Long> {

}
