package co.lk.repository;

import co.lk.domain.UrlGroup;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UrlGroup entity.
 */
public interface UrlGroupRepository extends JpaRepository<UrlGroup,Long> {

    @Query("select urlGroup from UrlGroup urlGroup where urlGroup.creationUser.login = ?#{principal.username}")
    List<UrlGroup> findByCreationUserIsCurrentUser();

    @Query("select urlGroup from UrlGroup urlGroup where urlGroup.updateUser.login = ?#{principal.username}")
    List<UrlGroup> findByUpdateUserIsCurrentUser();

}
