package co.lk.repository;

import co.lk.domain.LkAccount;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LkAccount entity.
 */
public interface LkAccountRepository extends JpaRepository<LkAccount,Long> {

    @Query("select lkAccount from LkAccount lkAccount where lkAccount.creationUser.login = ?#{principal.username}")
    List<LkAccount> findByCreationUserIsCurrentUser();

    @Query("select lkAccount from LkAccount lkAccount where lkAccount.updateUser.login = ?#{principal.username}")
    List<LkAccount> findByUpdateUserIsCurrentUser();

    @Query("select lkAccount from LkAccount lkAccount where lkAccount.manager.login = ?#{principal.username}")
    List<LkAccount> findByManagerIsCurrentUser();

    @Query("select lkAccount from LkAccount lkAccount where lkAccount.owner.login = ?#{principal.username}")
    List<LkAccount> findByOwnerIsCurrentUser();

}
