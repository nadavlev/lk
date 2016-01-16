package co.lk.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.lk.domain.LkAccount;
import co.lk.service.LkAccountService;
import co.lk.web.rest.util.HeaderUtil;
import co.lk.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing LkAccount.
 */
@RestController
@RequestMapping("/api")
public class LkAccountResource {

    private final Logger log = LoggerFactory.getLogger(LkAccountResource.class);
        
    @Inject
    private LkAccountService lkAccountService;
    
    /**
     * POST  /lkAccounts -> Create a new lkAccount.
     */
    @RequestMapping(value = "/lkAccounts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LkAccount> createLkAccount(@Valid @RequestBody LkAccount lkAccount) throws URISyntaxException {
        log.debug("REST request to save LkAccount : {}", lkAccount);
        if (lkAccount.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("lkAccount", "idexists", "A new lkAccount cannot already have an ID")).body(null);
        }
        LkAccount result = lkAccountService.save(lkAccount);
        return ResponseEntity.created(new URI("/api/lkAccounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("lkAccount", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lkAccounts -> Updates an existing lkAccount.
     */
    @RequestMapping(value = "/lkAccounts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LkAccount> updateLkAccount(@Valid @RequestBody LkAccount lkAccount) throws URISyntaxException {
        log.debug("REST request to update LkAccount : {}", lkAccount);
        if (lkAccount.getId() == null) {
            return createLkAccount(lkAccount);
        }
        LkAccount result = lkAccountService.save(lkAccount);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("lkAccount", lkAccount.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lkAccounts -> get all the lkAccounts.
     */
    @RequestMapping(value = "/lkAccounts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<LkAccount>> getAllLkAccounts(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of LkAccounts");
        Page<LkAccount> page = lkAccountService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/lkAccounts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /lkAccounts/:id -> get the "id" lkAccount.
     */
    @RequestMapping(value = "/lkAccounts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LkAccount> getLkAccount(@PathVariable Long id) {
        log.debug("REST request to get LkAccount : {}", id);
        LkAccount lkAccount = lkAccountService.findOne(id);
        return Optional.ofNullable(lkAccount)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /lkAccounts/:id -> delete the "id" lkAccount.
     */
    @RequestMapping(value = "/lkAccounts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLkAccount(@PathVariable Long id) {
        log.debug("REST request to delete LkAccount : {}", id);
        lkAccountService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("lkAccount", id.toString())).build();
    }
}
