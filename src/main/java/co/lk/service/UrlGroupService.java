package co.lk.service;

import co.lk.domain.UrlGroup;
import co.lk.repository.UrlGroupRepository;
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
 * Service Implementation for managing UrlGroup.
 */
@Service
@Transactional
public class UrlGroupService {

    private final Logger log = LoggerFactory.getLogger(UrlGroupService.class);
    
    @Inject
    private UrlGroupRepository urlGroupRepository;
    
    /**
     * Save a urlGroup.
     * @return the persisted entity
     */
    public UrlGroup save(UrlGroup urlGroup) {
        log.debug("Request to save UrlGroup : {}", urlGroup);
        UrlGroup result = urlGroupRepository.save(urlGroup);
        return result;
    }

    /**
     *  get all the urlGroups.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<UrlGroup> findAll(Pageable pageable) {
        log.debug("Request to get all UrlGroups");
        Page<UrlGroup> result = urlGroupRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one urlGroup by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public UrlGroup findOne(Long id) {
        log.debug("Request to get UrlGroup : {}", id);
        UrlGroup urlGroup = urlGroupRepository.findOne(id);
        return urlGroup;
    }

    /**
     *  delete the  urlGroup by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete UrlGroup : {}", id);
        urlGroupRepository.delete(id);
    }
}
