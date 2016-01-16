package co.lk.web.rest;

import co.lk.Application;
import co.lk.domain.LkAccount;
import co.lk.repository.LkAccountRepository;
import co.lk.service.LkAccountService;

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
 * Test class for the LkAccountResource REST controller.
 *
 * @see LkAccountResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class LkAccountResourceIntTest {

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

    private static final Boolean DEFAULT_IS_DEFAULT = false;
    private static final Boolean UPDATED_IS_DEFAULT = true;

    @Inject
    private LkAccountRepository lkAccountRepository;

    @Inject
    private LkAccountService lkAccountService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restLkAccountMockMvc;

    private LkAccount lkAccount;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LkAccountResource lkAccountResource = new LkAccountResource();
        ReflectionTestUtils.setField(lkAccountResource, "lkAccountService", lkAccountService);
        this.restLkAccountMockMvc = MockMvcBuilders.standaloneSetup(lkAccountResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        lkAccount = new LkAccount();
        lkAccount.setName(DEFAULT_NAME);
        lkAccount.setCreationDate(DEFAULT_CREATION_DATE);
        lkAccount.setUpdateDate(DEFAULT_UPDATE_DATE);
        lkAccount.setDescription(DEFAULT_DESCRIPTION);
        lkAccount.setState(DEFAULT_STATE);
        lkAccount.setIsDefault(DEFAULT_IS_DEFAULT);
    }

    @Test
    @Transactional
    public void createLkAccount() throws Exception {
        int databaseSizeBeforeCreate = lkAccountRepository.findAll().size();

        // Create the LkAccount

        restLkAccountMockMvc.perform(post("/api/lkAccounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lkAccount)))
                .andExpect(status().isCreated());

        // Validate the LkAccount in the database
        List<LkAccount> lkAccounts = lkAccountRepository.findAll();
        assertThat(lkAccounts).hasSize(databaseSizeBeforeCreate + 1);
        LkAccount testLkAccount = lkAccounts.get(lkAccounts.size() - 1);
        assertThat(testLkAccount.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLkAccount.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testLkAccount.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testLkAccount.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testLkAccount.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testLkAccount.getIsDefault()).isEqualTo(DEFAULT_IS_DEFAULT);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = lkAccountRepository.findAll().size();
        // set the field null
        lkAccount.setName(null);

        // Create the LkAccount, which fails.

        restLkAccountMockMvc.perform(post("/api/lkAccounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lkAccount)))
                .andExpect(status().isBadRequest());

        List<LkAccount> lkAccounts = lkAccountRepository.findAll();
        assertThat(lkAccounts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = lkAccountRepository.findAll().size();
        // set the field null
        lkAccount.setCreationDate(null);

        // Create the LkAccount, which fails.

        restLkAccountMockMvc.perform(post("/api/lkAccounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lkAccount)))
                .andExpect(status().isBadRequest());

        List<LkAccount> lkAccounts = lkAccountRepository.findAll();
        assertThat(lkAccounts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = lkAccountRepository.findAll().size();
        // set the field null
        lkAccount.setState(null);

        // Create the LkAccount, which fails.

        restLkAccountMockMvc.perform(post("/api/lkAccounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lkAccount)))
                .andExpect(status().isBadRequest());

        List<LkAccount> lkAccounts = lkAccountRepository.findAll();
        assertThat(lkAccounts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLkAccounts() throws Exception {
        // Initialize the database
        lkAccountRepository.saveAndFlush(lkAccount);

        // Get all the lkAccounts
        restLkAccountMockMvc.perform(get("/api/lkAccounts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(lkAccount.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
                .andExpect(jsonPath("$.[*].isDefault").value(hasItem(DEFAULT_IS_DEFAULT.booleanValue())));
    }

    @Test
    @Transactional
    public void getLkAccount() throws Exception {
        // Initialize the database
        lkAccountRepository.saveAndFlush(lkAccount);

        // Get the lkAccount
        restLkAccountMockMvc.perform(get("/api/lkAccounts/{id}", lkAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(lkAccount.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.isDefault").value(DEFAULT_IS_DEFAULT.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLkAccount() throws Exception {
        // Get the lkAccount
        restLkAccountMockMvc.perform(get("/api/lkAccounts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLkAccount() throws Exception {
        // Initialize the database
        lkAccountRepository.saveAndFlush(lkAccount);

		int databaseSizeBeforeUpdate = lkAccountRepository.findAll().size();

        // Update the lkAccount
        lkAccount.setName(UPDATED_NAME);
        lkAccount.setCreationDate(UPDATED_CREATION_DATE);
        lkAccount.setUpdateDate(UPDATED_UPDATE_DATE);
        lkAccount.setDescription(UPDATED_DESCRIPTION);
        lkAccount.setState(UPDATED_STATE);
        lkAccount.setIsDefault(UPDATED_IS_DEFAULT);

        restLkAccountMockMvc.perform(put("/api/lkAccounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lkAccount)))
                .andExpect(status().isOk());

        // Validate the LkAccount in the database
        List<LkAccount> lkAccounts = lkAccountRepository.findAll();
        assertThat(lkAccounts).hasSize(databaseSizeBeforeUpdate);
        LkAccount testLkAccount = lkAccounts.get(lkAccounts.size() - 1);
        assertThat(testLkAccount.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLkAccount.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testLkAccount.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testLkAccount.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLkAccount.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testLkAccount.getIsDefault()).isEqualTo(UPDATED_IS_DEFAULT);
    }

    @Test
    @Transactional
    public void deleteLkAccount() throws Exception {
        // Initialize the database
        lkAccountRepository.saveAndFlush(lkAccount);

		int databaseSizeBeforeDelete = lkAccountRepository.findAll().size();

        // Get the lkAccount
        restLkAccountMockMvc.perform(delete("/api/lkAccounts/{id}", lkAccount.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<LkAccount> lkAccounts = lkAccountRepository.findAll();
        assertThat(lkAccounts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
