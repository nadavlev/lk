package co.lk.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.lk.domain.ShortLink;
import co.lk.service.ShortLinkService;
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
 * REST controller for managing ShortLink.
 */
@RestController
@RequestMapping("/api")
public class ShortLinkResource {

    private final Logger log = LoggerFactory.getLogger(ShortLinkResource.class);
        
    @Inject
    private ShortLinkService shortLinkService;
    
    /**
     * POST  /shortLinks -> Create a new shortLink.
     */
    @RequestMapping(value = "/shortLinks",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ShortLink> createShortLink(@Valid @RequestBody ShortLink shortLink) throws URISyntaxException {
        log.debug("REST request to save ShortLink : {}", shortLink);
        if (shortLink.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("shortLink", "idexists", "A new shortLink cannot already have an ID")).body(null);
        }
        ShortLink result = shortLinkService.save(shortLink);
        return ResponseEntity.created(new URI("/api/shortLinks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("shortLink", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shortLinks -> Updates an existing shortLink.
     */
    @RequestMapping(value = "/shortLinks",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ShortLink> updateShortLink(@Valid @RequestBody ShortLink shortLink) throws URISyntaxException {
        log.debug("REST request to update ShortLink : {}", shortLink);
        if (shortLink.getId() == null) {
            return createShortLink(shortLink);
        }
        ShortLink result = shortLinkService.save(shortLink);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("shortLink", shortLink.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shortLinks -> get all the shortLinks.
     */
    @RequestMapping(value = "/shortLinks",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ShortLink>> getAllShortLinks(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ShortLinks");
        Page<ShortLink> page = shortLinkService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/shortLinks");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /shortLinks/:id -> get the "id" shortLink.
     */
    @RequestMapping(value = "/shortLinks/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ShortLink> getShortLink(@PathVariable Long id) {
        log.debug("REST request to get ShortLink : {}", id);
        ShortLink shortLink = shortLinkService.findOne(id);
        return Optional.ofNullable(shortLink)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /shortLinks/:id -> delete the "id" shortLink.
     */
    @RequestMapping(value = "/shortLinks/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteShortLink(@PathVariable Long id) {
        log.debug("REST request to delete ShortLink : {}", id);
        shortLinkService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("shortLink", id.toString())).build();
    }
}
