package co.lk.service;

import co.lk.domain.LkAccount;
import co.lk.repository.LkAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing LkAccount.
 */
@Service
@Transactional
public class LkAccountService {

    private final Logger log = LoggerFactory.getLogger(LkAccountService.class);
    
    @Inject
    private LkAccountRepository lkAccountRepository;
    
    /**
     * Save a lkAccount.
     * @return the persisted entity
     */
    public LkAccount save(LkAccount lkAccount) {
        log.debug("Request to save LkAccount : {}", lkAccount);
        LkAccount result = lkAccountRepository.save(lkAccount);
        return result;
    }

    /**
     *  get all the lkAccounts.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<LkAccount> findAll(Pageable pageable) {
        log.debug("Request to get all LkAccounts");
        Page<LkAccount> result = lkAccountRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one lkAccount by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public LkAccount findOne(Long id) {
        log.debug("Request to get LkAccount : {}", id);
        LkAccount lkAccount = lkAccountRepository.findOne(id);
        return lkAccount;
    }

    /**
     *  delete the  lkAccount by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete LkAccount : {}", id);
        lkAccountRepository.delete(id);
    }
}
