package co.lk.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.lk.domain.UrlGroup;
import co.lk.service.UrlGroupService;
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
 * REST controller for managing UrlGroup.
 */
@RestController
@RequestMapping("/api")
public class UrlGroupResource {

    private final Logger log = LoggerFactory.getLogger(UrlGroupResource.class);
        
    @Inject
    private UrlGroupService urlGroupService;
    
    /**
     * POST  /urlGroups -> Create a new urlGroup.
     */
    @RequestMapping(value = "/urlGroups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UrlGroup> createUrlGroup(@Valid @RequestBody UrlGroup urlGroup) throws URISyntaxException {
        log.debug("REST request to save UrlGroup : {}", urlGroup);
        if (urlGroup.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("urlGroup", "idexists", "A new urlGroup cannot already have an ID")).body(null);
        }
        UrlGroup result = urlGroupService.save(urlGroup);
        return ResponseEntity.created(new URI("/api/urlGroups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("urlGroup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /urlGroups -> Updates an existing urlGroup.
     */
    @RequestMapping(value = "/urlGroups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UrlGroup> updateUrlGroup(@Valid @RequestBody UrlGroup urlGroup) throws URISyntaxException {
        log.debug("REST request to update UrlGroup : {}", urlGroup);
        if (urlGroup.getId() == null) {
            return createUrlGroup(urlGroup);
        }
        UrlGroup result = urlGroupService.save(urlGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("urlGroup", urlGroup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /urlGroups -> get all the urlGroups.
     */
    @RequestMapping(value = "/urlGroups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<UrlGroup>> getAllUrlGroups(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of UrlGroups");
        Page<UrlGroup> page = urlGroupService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/urlGroups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /urlGroups/:id -> get the "id" urlGroup.
     */
    @RequestMapping(value = "/urlGroups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UrlGroup> getUrlGroup(@PathVariable Long id) {
        log.debug("REST request to get UrlGroup : {}", id);
        UrlGroup urlGroup = urlGroupService.findOne(id);
        return Optional.ofNullable(urlGroup)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /urlGroups/:id -> delete the "id" urlGroup.
     */
    @RequestMapping(value = "/urlGroups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteUrlGroup(@PathVariable Long id) {
        log.debug("REST request to delete UrlGroup : {}", id);
        urlGroupService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("urlGroup", id.toString())).build();
    }
}
