package co.lk.web.rest;

import co.lk.Application;
import co.lk.domain.UrlGroup;
import co.lk.repository.UrlGroupRepository;
import co.lk.service.UrlGroupService;

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
 * Test class for the UrlGroupResource REST controller.
 *
 * @see UrlGroupResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class UrlGroupResourceIntTest {

    private static final String DEFAULT_NAME = "AAA";
    private static final String UPDATED_NAME = "BBB";

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Integer DEFAULT_STATE = 1;
    private static final Integer UPDATED_STATE = 2;
    private static final String DEFAULT_MAIN_ADDRESS = "AAAA";
    private static final String UPDATED_MAIN_ADDRESS = "BBBB";

    @Inject
    private UrlGroupRepository urlGroupRepository;

    @Inject
    private UrlGroupService urlGroupService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restUrlGroupMockMvc;

    private UrlGroup urlGroup;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UrlGroupResource urlGroupResource = new UrlGroupResource();
        ReflectionTestUtils.setField(urlGroupResource, "urlGroupService", urlGroupService);
        this.restUrlGroupMockMvc = MockMvcBuilders.standaloneSetup(urlGroupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        urlGroup = new UrlGroup();
        urlGroup.setName(DEFAULT_NAME);
        urlGroup.setCreationDate(DEFAULT_CREATION_DATE);
        urlGroup.setUpdateDate(DEFAULT_UPDATE_DATE);
        urlGroup.setDescription(DEFAULT_DESCRIPTION);
        urlGroup.setState(DEFAULT_STATE);
        urlGroup.setMainAddress(DEFAULT_MAIN_ADDRESS);
    }

    @Test
    @Transactional
    public void createUrlGroup() throws Exception {
        int databaseSizeBeforeCreate = urlGroupRepository.findAll().size();

        // Create the UrlGroup

        restUrlGroupMockMvc.perform(post("/api/urlGroups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(urlGroup)))
                .andExpect(status().isCreated());

        // Validate the UrlGroup in the database
        List<UrlGroup> urlGroups = urlGroupRepository.findAll();
        assertThat(urlGroups).hasSize(databaseSizeBeforeCreate + 1);
        UrlGroup testUrlGroup = urlGroups.get(urlGroups.size() - 1);
        assertThat(testUrlGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUrlGroup.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testUrlGroup.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testUrlGroup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testUrlGroup.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testUrlGroup.getMainAddress()).isEqualTo(DEFAULT_MAIN_ADDRESS);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = urlGroupRepository.findAll().size();
        // set the field null
        urlGroup.setName(null);

        // Create the UrlGroup, which fails.

        restUrlGroupMockMvc.perform(post("/api/urlGroups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(urlGroup)))
                .andExpect(status().isBadRequest());

        List<UrlGroup> urlGroups = urlGroupRepository.findAll();
        assertThat(urlGroups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = urlGroupRepository.findAll().size();
        // set the field null
        urlGroup.setCreationDate(null);

        // Create the UrlGroup, which fails.

        restUrlGroupMockMvc.perform(post("/api/urlGroups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(urlGroup)))
                .andExpect(status().isBadRequest());

        List<UrlGroup> urlGroups = urlGroupRepository.findAll();
        assertThat(urlGroups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = urlGroupRepository.findAll().size();
        // set the field null
        urlGroup.setState(null);

        // Create the UrlGroup, which fails.

        restUrlGroupMockMvc.perform(post("/api/urlGroups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(urlGroup)))
                .andExpect(status().isBadRequest());

        List<UrlGroup> urlGroups = urlGroupRepository.findAll();
        assertThat(urlGroups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMainAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = urlGroupRepository.findAll().size();
        // set the field null
        urlGroup.setMainAddress(null);

        // Create the UrlGroup, which fails.

        restUrlGroupMockMvc.perform(post("/api/urlGroups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(urlGroup)))
                .andExpect(status().isBadRequest());

        List<UrlGroup> urlGroups = urlGroupRepository.findAll();
        assertThat(urlGroups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUrlGroups() throws Exception {
        // Initialize the database
        urlGroupRepository.saveAndFlush(urlGroup);

        // Get all the urlGroups
        restUrlGroupMockMvc.perform(get("/api/urlGroups?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(urlGroup.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
                .andExpect(jsonPath("$.[*].mainAddress").value(hasItem(DEFAULT_MAIN_ADDRESS.toString())));
    }

    @Test
    @Transactional
    public void getUrlGroup() throws Exception {
        // Initialize the database
        urlGroupRepository.saveAndFlush(urlGroup);

        // Get the urlGroup
        restUrlGroupMockMvc.perform(get("/api/urlGroups/{id}", urlGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(urlGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.mainAddress").value(DEFAULT_MAIN_ADDRESS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUrlGroup() throws Exception {
        // Get the urlGroup
        restUrlGroupMockMvc.perform(get("/api/urlGroups/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUrlGroup() throws Exception {
        // Initialize the database
        urlGroupRepository.saveAndFlush(urlGroup);

		int databaseSizeBeforeUpdate = urlGroupRepository.findAll().size();

        // Update the urlGroup
        urlGroup.setName(UPDATED_NAME);
        urlGroup.setCreationDate(UPDATED_CREATION_DATE);
        urlGroup.setUpdateDate(UPDATED_UPDATE_DATE);
        urlGroup.setDescription(UPDATED_DESCRIPTION);
        urlGroup.setState(UPDATED_STATE);
        urlGroup.setMainAddress(UPDATED_MAIN_ADDRESS);

        restUrlGroupMockMvc.perform(put("/api/urlGroups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(urlGroup)))
                .andExpect(status().isOk());

        // Validate the UrlGroup in the database
        List<UrlGroup> urlGroups = urlGroupRepository.findAll();
        assertThat(urlGroups).hasSize(databaseSizeBeforeUpdate);
        UrlGroup testUrlGroup = urlGroups.get(urlGroups.size() - 1);
        assertThat(testUrlGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUrlGroup.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testUrlGroup.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testUrlGroup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testUrlGroup.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testUrlGroup.getMainAddress()).isEqualTo(UPDATED_MAIN_ADDRESS);
    }

    @Test
    @Transactional
    public void deleteUrlGroup() throws Exception {
        // Initialize the database
        urlGroupRepository.saveAndFlush(urlGroup);

		int databaseSizeBeforeDelete = urlGroupRepository.findAll().size();

        // Get the urlGroup
        restUrlGroupMockMvc.perform(delete("/api/urlGroups/{id}", urlGroup.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<UrlGroup> urlGroups = urlGroupRepository.findAll();
        assertThat(urlGroups).hasSize(databaseSizeBeforeDelete - 1);
    }
}
