package co.lk.web.rest;

import co.lk.Application;
import co.lk.domain.ShortLink;
import co.lk.repository.ShortLinkRepository;
import co.lk.service.ShortLinkService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ShortLinkResource REST controller.
 *
 * @see ShortLinkResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ShortLinkResourceIntTest {

    private static final String DEFAULT_NAME = "AAA";
    private static final String UPDATED_NAME = "BBB";
    private static final String DEFAULT_DESTINATION = "AAAA";
    private static final String UPDATED_DESTINATION = "BBBB";

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Integer DEFAULT_STATE = 1;
    private static final Integer UPDATED_STATE = 2;
    private static final String DEFAULT_SHORT_LINK = "AAAAA";
    private static final String UPDATED_SHORT_LINK = "BBBBB";

    private static final Long DEFAULT_TOTAL_CLICKS = 0L;
    private static final Long UPDATED_TOTAL_CLICKS = 1L;

    private static final LocalDate DEFAULT_LAST_CLICK_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_CLICK_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_INDEX_OF_KEYS = "AAAAA";
    private static final String UPDATED_INDEX_OF_KEYS = "BBBBB";

    private static final Long DEFAULT_TIMEOUT = 1L;
    private static final Long UPDATED_TIMEOUT = 2L;

    @Inject
    private ShortLinkRepository shortLinkRepository;

    @Inject
    private ShortLinkService shortLinkService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restShortLinkMockMvc;

    private ShortLink shortLink;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ShortLinkResource shortLinkResource = new ShortLinkResource();
        ReflectionTestUtils.setField(shortLinkResource, "shortLinkService", shortLinkService);
        this.restShortLinkMockMvc = MockMvcBuilders.standaloneSetup(shortLinkResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        shortLink = new ShortLink();
        shortLink.setName(DEFAULT_NAME);
        shortLink.setDestination(DEFAULT_DESTINATION);
        shortLink.setCreationDate(DEFAULT_CREATION_DATE);
        shortLink.setUpdateDate(DEFAULT_UPDATE_DATE);
        shortLink.setDescription(DEFAULT_DESCRIPTION);
        shortLink.setState(DEFAULT_STATE);
        shortLink.setShortLink(DEFAULT_SHORT_LINK);
        shortLink.setTotalClicks(DEFAULT_TOTAL_CLICKS);
        shortLink.setLastClickDate(DEFAULT_LAST_CLICK_DATE);
        shortLink.setIndexOfKeys(DEFAULT_INDEX_OF_KEYS);
        shortLink.setTimeout(DEFAULT_TIMEOUT);
    }

    @Test
    @Transactional
    public void createShortLink() throws Exception {
        int databaseSizeBeforeCreate = shortLinkRepository.findAll().size();

        // Create the ShortLink

        restShortLinkMockMvc.perform(post("/api/shortLinks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(shortLink)))
                .andExpect(status().isCreated());

        // Validate the ShortLink in the database
        List<ShortLink> shortLinks = shortLinkRepository.findAll();
        assertThat(shortLinks).hasSize(databaseSizeBeforeCreate + 1);
        ShortLink testShortLink = shortLinks.get(shortLinks.size() - 1);
        assertThat(testShortLink.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testShortLink.getDestination()).isEqualTo(DEFAULT_DESTINATION);
        assertThat(testShortLink.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testShortLink.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testShortLink.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testShortLink.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testShortLink.getShortLink()).isEqualTo(DEFAULT_SHORT_LINK);
        assertThat(testShortLink.getTotalClicks()).isEqualTo(DEFAULT_TOTAL_CLICKS);
        assertThat(testShortLink.getLastClickDate()).isEqualTo(DEFAULT_LAST_CLICK_DATE);
        assertThat(testShortLink.getIndexOfKeys()).isEqualTo(DEFAULT_INDEX_OF_KEYS);
        assertThat(testShortLink.getTimeout()).isEqualTo(DEFAULT_TIMEOUT);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = shortLinkRepository.findAll().size();
        // set the field null
        shortLink.setName(null);

        // Create the ShortLink, which fails.

        restShortLinkMockMvc.perform(post("/api/shortLinks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(shortLink)))
                .andExpect(status().isBadRequest());

        List<ShortLink> shortLinks = shortLinkRepository.findAll();
        assertThat(shortLinks).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDestinationIsRequired() throws Exception {
        int databaseSizeBeforeTest = shortLinkRepository.findAll().size();
        // set the field null
        shortLink.setDestination(null);

        // Create the ShortLink, which fails.

        restShortLinkMockMvc.perform(post("/api/shortLinks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(shortLink)))
                .andExpect(status().isBadRequest());

        List<ShortLink> shortLinks = shortLinkRepository.findAll();
        assertThat(shortLinks).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = shortLinkRepository.findAll().size();
        // set the field null
        shortLink.setCreationDate(null);

        // Create the ShortLink, which fails.

        restShortLinkMockMvc.perform(post("/api/shortLinks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(shortLink)))
                .andExpect(status().isBadRequest());

        List<ShortLink> shortLinks = shortLinkRepository.findAll();
        assertThat(shortLinks).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = shortLinkRepository.findAll().size();
        // set the field null
        shortLink.setState(null);

        // Create the ShortLink, which fails.

        restShortLinkMockMvc.perform(post("/api/shortLinks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(shortLink)))
                .andExpect(status().isBadRequest());

        List<ShortLink> shortLinks = shortLinkRepository.findAll();
        assertThat(shortLinks).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllShortLinks() throws Exception {
        // Initialize the database
        shortLinkRepository.saveAndFlush(shortLink);

        // Get all the shortLinks
        restShortLinkMockMvc.perform(get("/api/shortLinks?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(shortLink.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].destination").value(hasItem(DEFAULT_DESTINATION.toString())))
                .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
                .andExpect(jsonPath("$.[*].shortLink").value(hasItem(DEFAULT_SHORT_LINK.toString())))
                .andExpect(jsonPath("$.[*].totalClicks").value(hasItem(DEFAULT_TOTAL_CLICKS.intValue())))
                .andExpect(jsonPath("$.[*].lastClickDate").value(hasItem(DEFAULT_LAST_CLICK_DATE.toString())))
                .andExpect(jsonPath("$.[*].indexOfKeys").value(hasItem(DEFAULT_INDEX_OF_KEYS.toString())))
                .andExpect(jsonPath("$.[*].timeout").value(hasItem(DEFAULT_TIMEOUT.intValue())));
    }

    @Test
    @Transactional
    public void getShortLink() throws Exception {
        // Initialize the database
        shortLinkRepository.saveAndFlush(shortLink);

        // Get the shortLink
        restShortLinkMockMvc.perform(get("/api/shortLinks/{id}", shortLink.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(shortLink.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.destination").value(DEFAULT_DESTINATION.toString()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.shortLink").value(DEFAULT_SHORT_LINK.toString()))
            .andExpect(jsonPath("$.totalClicks").value(DEFAULT_TOTAL_CLICKS.intValue()))
            .andExpect(jsonPath("$.lastClickDate").value(DEFAULT_LAST_CLICK_DATE.toString()))
            .andExpect(jsonPath("$.indexOfKeys").value(DEFAULT_INDEX_OF_KEYS.toString()))
            .andExpect(jsonPath("$.timeout").value(DEFAULT_TIMEOUT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingShortLink() throws Exception {
        // Get the shortLink
        restShortLinkMockMvc.perform(get("/api/shortLinks/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShortLink() throws Exception {
        // Initialize the database
        shortLinkRepository.saveAndFlush(shortLink);

		int databaseSizeBeforeUpdate = shortLinkRepository.findAll().size();

        // Update the shortLink
        shortLink.setName(UPDATED_NAME);
        shortLink.setDestination(UPDATED_DESTINATION);
        shortLink.setCreationDate(UPDATED_CREATION_DATE);
        shortLink.setUpdateDate(UPDATED_UPDATE_DATE);
        shortLink.setDescription(UPDATED_DESCRIPTION);
        shortLink.setState(UPDATED_STATE);
        shortLink.setShortLink(UPDATED_SHORT_LINK);
        shortLink.setTotalClicks(UPDATED_TOTAL_CLICKS);
        shortLink.setLastClickDate(UPDATED_LAST_CLICK_DATE);
        shortLink.setIndexOfKeys(UPDATED_INDEX_OF_KEYS);
        shortLink.setTimeout(UPDATED_TIMEOUT);

        restShortLinkMockMvc.perform(put("/api/shortLinks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(shortLink)))
                .andExpect(status().isOk());

        // Validate the ShortLink in the database
        List<ShortLink> shortLinks = shortLinkRepository.findAll();
        assertThat(shortLinks).hasSize(databaseSizeBeforeUpdate);
        ShortLink testShortLink = shortLinks.get(shortLinks.size() - 1);
        assertThat(testShortLink.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testShortLink.getDestination()).isEqualTo(UPDATED_DESTINATION);
        assertThat(testShortLink.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testShortLink.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testShortLink.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testShortLink.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testShortLink.getShortLink()).isEqualTo(UPDATED_SHORT_LINK);
        assertThat(testShortLink.getTotalClicks()).isEqualTo(UPDATED_TOTAL_CLICKS);
        assertThat(testShortLink.getLastClickDate()).isEqualTo(UPDATED_LAST_CLICK_DATE);
        assertThat(testShortLink.getIndexOfKeys()).isEqualTo(UPDATED_INDEX_OF_KEYS);
        assertThat(testShortLink.getTimeout()).isEqualTo(UPDATED_TIMEOUT);
    }

    @Test
    @Transactional
    public void deleteShortLink() throws Exception {
        // Initialize the database
        shortLinkRepository.saveAndFlush(shortLink);

		int databaseSizeBeforeDelete = shortLinkRepository.findAll().size();

        // Get the shortLink
        restShortLinkMockMvc.perform(delete("/api/shortLinks/{id}", shortLink.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ShortLink> shortLinks = shortLinkRepository.findAll();
        assertThat(shortLinks).hasSize(databaseSizeBeforeDelete - 1);
    }
}
