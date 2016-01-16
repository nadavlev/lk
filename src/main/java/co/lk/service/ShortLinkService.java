package co.lk.service;

import co.lk.domain.ShortLink;
import co.lk.repository.ShortLinkRepository;
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
 * Service Implementation for managing ShortLink.
 */
@Service
@Transactional
public class ShortLinkService {

    private final Logger log = LoggerFactory.getLogger(ShortLinkService.class);
    
    @Inject
    private ShortLinkRepository shortLinkRepository;
    
    /**
     * Save a shortLink.
     * @return the persisted entity
     */
    public ShortLink save(ShortLink shortLink) {
        log.debug("Request to save ShortLink : {}", shortLink);
        ShortLink result = shortLinkRepository.save(shortLink);
        return result;
    }

    /**
     *  get all the shortLinks.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<ShortLink> findAll(Pageable pageable) {
        log.debug("Request to get all ShortLinks");
        Page<ShortLink> result = shortLinkRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one shortLink by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ShortLink findOne(Long id) {
        log.debug("Request to get ShortLink : {}", id);
        ShortLink shortLink = shortLinkRepository.findOne(id);
        return shortLink;
    }

    /**
     *  delete the  shortLink by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete ShortLink : {}", id);
        shortLinkRepository.delete(id);
    }
}
