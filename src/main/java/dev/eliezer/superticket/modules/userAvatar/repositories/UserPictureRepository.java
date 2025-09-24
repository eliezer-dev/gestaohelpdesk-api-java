package dev.eliezer.superticket.modules.userAvatar.repositories;

import dev.eliezer.superticket.modules.userAvatar.entities.UserPicture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPictureRepository extends JpaRepository<UserPicture, Long> {

}
