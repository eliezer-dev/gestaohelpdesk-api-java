package dev.eliezer.gestaohelpdesk.modules.userAvatar.repositories;

import dev.eliezer.gestaohelpdesk.modules.userAvatar.entities.UserPicture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPictureRepository extends JpaRepository<UserPicture, Long> {

}
