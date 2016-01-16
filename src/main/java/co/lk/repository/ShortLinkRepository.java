package co.lk.repository;

import co.lk.domain.ShortLink;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ShortLink entity.
 */
public interface ShortLinkRepository extends JpaRepository<ShortLink,Long> {

    @Query("select shortLink from ShortLink shortLink where shortLink.creationUser.login = ?#{principal.username}")
    List<ShortLink> findByCreationUserIsCurrentUser();

    @Query("select shortLink from ShortLink shortLink where shortLink.updateUser.login = ?#{principal.username}")
    List<ShortLink> findByUpdateUserIsCurrentUser();

}
