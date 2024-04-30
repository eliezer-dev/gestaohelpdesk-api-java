package dev.eliezer.superticket.domain.repository;

import dev.eliezer.superticket.domain.model.UserPicture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPictureRepository extends JpaRepository<UserPicture, Long> {

}
