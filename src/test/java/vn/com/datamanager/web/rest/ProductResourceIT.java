package vn.com.datamanager.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import vn.com.datamanager.IntegrationTest;
import vn.com.datamanager.domain.Product;
import vn.com.datamanager.repository.ProductRepository;
import vn.com.datamanager.service.criteria.ProductCriteria;

/**
 * Integration tests for the {@link ProductResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductResourceIT {

    private static final String DEFAULT_PRODUCT_ID = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_FAMILY_ID = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_FAMILY_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_PRICE_ID = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_PRICE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_FAMILY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_FAMILY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_FAMILY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_FAMILY_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/products";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductMockMvc;

    private Product product;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createEntity(EntityManager em) {
        Product product = new Product()
            .productId(DEFAULT_PRODUCT_ID)
            .productCode(DEFAULT_PRODUCT_CODE)
            .productFamilyId(DEFAULT_PRODUCT_FAMILY_ID)
            .productPriceId(DEFAULT_PRODUCT_PRICE_ID)
            .productName(DEFAULT_PRODUCT_NAME)
            .productFamilyCode(DEFAULT_PRODUCT_FAMILY_CODE)
            .productFamilyName(DEFAULT_PRODUCT_FAMILY_NAME);
        return product;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createUpdatedEntity(EntityManager em) {
        Product product = new Product()
            .productId(UPDATED_PRODUCT_ID)
            .productCode(UPDATED_PRODUCT_CODE)
            .productFamilyId(UPDATED_PRODUCT_FAMILY_ID)
            .productPriceId(UPDATED_PRODUCT_PRICE_ID)
            .productName(UPDATED_PRODUCT_NAME)
            .productFamilyCode(UPDATED_PRODUCT_FAMILY_CODE)
            .productFamilyName(UPDATED_PRODUCT_FAMILY_NAME);
        return product;
    }

    @BeforeEach
    public void initTest() {
        product = createEntity(em);
    }

    @Test
    @Transactional
    void createProduct() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();
        // Create the Product
        restProductMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isCreated());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate + 1);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testProduct.getProductCode()).isEqualTo(DEFAULT_PRODUCT_CODE);
        assertThat(testProduct.getProductFamilyId()).isEqualTo(DEFAULT_PRODUCT_FAMILY_ID);
        assertThat(testProduct.getProductPriceId()).isEqualTo(DEFAULT_PRODUCT_PRICE_ID);
        assertThat(testProduct.getProductName()).isEqualTo(DEFAULT_PRODUCT_NAME);
        assertThat(testProduct.getProductFamilyCode()).isEqualTo(DEFAULT_PRODUCT_FAMILY_CODE);
        assertThat(testProduct.getProductFamilyName()).isEqualTo(DEFAULT_PRODUCT_FAMILY_NAME);
    }

    @Test
    @Transactional
    void createProductWithExistingId() throws Exception {
        // Create the Product with an existing ID
        product.setId(1L);

        int databaseSizeBeforeCreate = productRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProducts() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList
        restProductMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID)))
            .andExpect(jsonPath("$.[*].productCode").value(hasItem(DEFAULT_PRODUCT_CODE)))
            .andExpect(jsonPath("$.[*].productFamilyId").value(hasItem(DEFAULT_PRODUCT_FAMILY_ID)))
            .andExpect(jsonPath("$.[*].productPriceId").value(hasItem(DEFAULT_PRODUCT_PRICE_ID)))
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME)))
            .andExpect(jsonPath("$.[*].productFamilyCode").value(hasItem(DEFAULT_PRODUCT_FAMILY_CODE)))
            .andExpect(jsonPath("$.[*].productFamilyName").value(hasItem(DEFAULT_PRODUCT_FAMILY_NAME)));
    }

    @Test
    @Transactional
    void getProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get the product
        restProductMockMvc
            .perform(get(ENTITY_API_URL_ID, product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(product.getId().intValue()))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID))
            .andExpect(jsonPath("$.productCode").value(DEFAULT_PRODUCT_CODE))
            .andExpect(jsonPath("$.productFamilyId").value(DEFAULT_PRODUCT_FAMILY_ID))
            .andExpect(jsonPath("$.productPriceId").value(DEFAULT_PRODUCT_PRICE_ID))
            .andExpect(jsonPath("$.productName").value(DEFAULT_PRODUCT_NAME))
            .andExpect(jsonPath("$.productFamilyCode").value(DEFAULT_PRODUCT_FAMILY_CODE))
            .andExpect(jsonPath("$.productFamilyName").value(DEFAULT_PRODUCT_FAMILY_NAME));
    }

    @Test
    @Transactional
    void getProductsByIdFiltering() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        Long id = product.getId();

        defaultProductShouldBeFound("id.equals=" + id);
        defaultProductShouldNotBeFound("id.notEquals=" + id);

        defaultProductShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductShouldNotBeFound("id.greaterThan=" + id);

        defaultProductShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProductsByProductIdIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productId equals to DEFAULT_PRODUCT_ID
        defaultProductShouldBeFound("productId.equals=" + DEFAULT_PRODUCT_ID);

        // Get all the productList where productId equals to UPDATED_PRODUCT_ID
        defaultProductShouldNotBeFound("productId.equals=" + UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    void getAllProductsByProductIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productId not equals to DEFAULT_PRODUCT_ID
        defaultProductShouldNotBeFound("productId.notEquals=" + DEFAULT_PRODUCT_ID);

        // Get all the productList where productId not equals to UPDATED_PRODUCT_ID
        defaultProductShouldBeFound("productId.notEquals=" + UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    void getAllProductsByProductIdIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productId in DEFAULT_PRODUCT_ID or UPDATED_PRODUCT_ID
        defaultProductShouldBeFound("productId.in=" + DEFAULT_PRODUCT_ID + "," + UPDATED_PRODUCT_ID);

        // Get all the productList where productId equals to UPDATED_PRODUCT_ID
        defaultProductShouldNotBeFound("productId.in=" + UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    void getAllProductsByProductIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productId is not null
        defaultProductShouldBeFound("productId.specified=true");

        // Get all the productList where productId is null
        defaultProductShouldNotBeFound("productId.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByProductIdContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productId contains DEFAULT_PRODUCT_ID
        defaultProductShouldBeFound("productId.contains=" + DEFAULT_PRODUCT_ID);

        // Get all the productList where productId contains UPDATED_PRODUCT_ID
        defaultProductShouldNotBeFound("productId.contains=" + UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    void getAllProductsByProductIdNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productId does not contain DEFAULT_PRODUCT_ID
        defaultProductShouldNotBeFound("productId.doesNotContain=" + DEFAULT_PRODUCT_ID);

        // Get all the productList where productId does not contain UPDATED_PRODUCT_ID
        defaultProductShouldBeFound("productId.doesNotContain=" + UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    void getAllProductsByProductCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productCode equals to DEFAULT_PRODUCT_CODE
        defaultProductShouldBeFound("productCode.equals=" + DEFAULT_PRODUCT_CODE);

        // Get all the productList where productCode equals to UPDATED_PRODUCT_CODE
        defaultProductShouldNotBeFound("productCode.equals=" + UPDATED_PRODUCT_CODE);
    }

    @Test
    @Transactional
    void getAllProductsByProductCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productCode not equals to DEFAULT_PRODUCT_CODE
        defaultProductShouldNotBeFound("productCode.notEquals=" + DEFAULT_PRODUCT_CODE);

        // Get all the productList where productCode not equals to UPDATED_PRODUCT_CODE
        defaultProductShouldBeFound("productCode.notEquals=" + UPDATED_PRODUCT_CODE);
    }

    @Test
    @Transactional
    void getAllProductsByProductCodeIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productCode in DEFAULT_PRODUCT_CODE or UPDATED_PRODUCT_CODE
        defaultProductShouldBeFound("productCode.in=" + DEFAULT_PRODUCT_CODE + "," + UPDATED_PRODUCT_CODE);

        // Get all the productList where productCode equals to UPDATED_PRODUCT_CODE
        defaultProductShouldNotBeFound("productCode.in=" + UPDATED_PRODUCT_CODE);
    }

    @Test
    @Transactional
    void getAllProductsByProductCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productCode is not null
        defaultProductShouldBeFound("productCode.specified=true");

        // Get all the productList where productCode is null
        defaultProductShouldNotBeFound("productCode.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByProductCodeContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productCode contains DEFAULT_PRODUCT_CODE
        defaultProductShouldBeFound("productCode.contains=" + DEFAULT_PRODUCT_CODE);

        // Get all the productList where productCode contains UPDATED_PRODUCT_CODE
        defaultProductShouldNotBeFound("productCode.contains=" + UPDATED_PRODUCT_CODE);
    }

    @Test
    @Transactional
    void getAllProductsByProductCodeNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productCode does not contain DEFAULT_PRODUCT_CODE
        defaultProductShouldNotBeFound("productCode.doesNotContain=" + DEFAULT_PRODUCT_CODE);

        // Get all the productList where productCode does not contain UPDATED_PRODUCT_CODE
        defaultProductShouldBeFound("productCode.doesNotContain=" + UPDATED_PRODUCT_CODE);
    }

    @Test
    @Transactional
    void getAllProductsByProductFamilyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productFamilyId equals to DEFAULT_PRODUCT_FAMILY_ID
        defaultProductShouldBeFound("productFamilyId.equals=" + DEFAULT_PRODUCT_FAMILY_ID);

        // Get all the productList where productFamilyId equals to UPDATED_PRODUCT_FAMILY_ID
        defaultProductShouldNotBeFound("productFamilyId.equals=" + UPDATED_PRODUCT_FAMILY_ID);
    }

    @Test
    @Transactional
    void getAllProductsByProductFamilyIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productFamilyId not equals to DEFAULT_PRODUCT_FAMILY_ID
        defaultProductShouldNotBeFound("productFamilyId.notEquals=" + DEFAULT_PRODUCT_FAMILY_ID);

        // Get all the productList where productFamilyId not equals to UPDATED_PRODUCT_FAMILY_ID
        defaultProductShouldBeFound("productFamilyId.notEquals=" + UPDATED_PRODUCT_FAMILY_ID);
    }

    @Test
    @Transactional
    void getAllProductsByProductFamilyIdIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productFamilyId in DEFAULT_PRODUCT_FAMILY_ID or UPDATED_PRODUCT_FAMILY_ID
        defaultProductShouldBeFound("productFamilyId.in=" + DEFAULT_PRODUCT_FAMILY_ID + "," + UPDATED_PRODUCT_FAMILY_ID);

        // Get all the productList where productFamilyId equals to UPDATED_PRODUCT_FAMILY_ID
        defaultProductShouldNotBeFound("productFamilyId.in=" + UPDATED_PRODUCT_FAMILY_ID);
    }

    @Test
    @Transactional
    void getAllProductsByProductFamilyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productFamilyId is not null
        defaultProductShouldBeFound("productFamilyId.specified=true");

        // Get all the productList where productFamilyId is null
        defaultProductShouldNotBeFound("productFamilyId.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByProductFamilyIdContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productFamilyId contains DEFAULT_PRODUCT_FAMILY_ID
        defaultProductShouldBeFound("productFamilyId.contains=" + DEFAULT_PRODUCT_FAMILY_ID);

        // Get all the productList where productFamilyId contains UPDATED_PRODUCT_FAMILY_ID
        defaultProductShouldNotBeFound("productFamilyId.contains=" + UPDATED_PRODUCT_FAMILY_ID);
    }

    @Test
    @Transactional
    void getAllProductsByProductFamilyIdNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productFamilyId does not contain DEFAULT_PRODUCT_FAMILY_ID
        defaultProductShouldNotBeFound("productFamilyId.doesNotContain=" + DEFAULT_PRODUCT_FAMILY_ID);

        // Get all the productList where productFamilyId does not contain UPDATED_PRODUCT_FAMILY_ID
        defaultProductShouldBeFound("productFamilyId.doesNotContain=" + UPDATED_PRODUCT_FAMILY_ID);
    }

    @Test
    @Transactional
    void getAllProductsByProductPriceIdIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productPriceId equals to DEFAULT_PRODUCT_PRICE_ID
        defaultProductShouldBeFound("productPriceId.equals=" + DEFAULT_PRODUCT_PRICE_ID);

        // Get all the productList where productPriceId equals to UPDATED_PRODUCT_PRICE_ID
        defaultProductShouldNotBeFound("productPriceId.equals=" + UPDATED_PRODUCT_PRICE_ID);
    }

    @Test
    @Transactional
    void getAllProductsByProductPriceIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productPriceId not equals to DEFAULT_PRODUCT_PRICE_ID
        defaultProductShouldNotBeFound("productPriceId.notEquals=" + DEFAULT_PRODUCT_PRICE_ID);

        // Get all the productList where productPriceId not equals to UPDATED_PRODUCT_PRICE_ID
        defaultProductShouldBeFound("productPriceId.notEquals=" + UPDATED_PRODUCT_PRICE_ID);
    }

    @Test
    @Transactional
    void getAllProductsByProductPriceIdIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productPriceId in DEFAULT_PRODUCT_PRICE_ID or UPDATED_PRODUCT_PRICE_ID
        defaultProductShouldBeFound("productPriceId.in=" + DEFAULT_PRODUCT_PRICE_ID + "," + UPDATED_PRODUCT_PRICE_ID);

        // Get all the productList where productPriceId equals to UPDATED_PRODUCT_PRICE_ID
        defaultProductShouldNotBeFound("productPriceId.in=" + UPDATED_PRODUCT_PRICE_ID);
    }

    @Test
    @Transactional
    void getAllProductsByProductPriceIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productPriceId is not null
        defaultProductShouldBeFound("productPriceId.specified=true");

        // Get all the productList where productPriceId is null
        defaultProductShouldNotBeFound("productPriceId.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByProductPriceIdContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productPriceId contains DEFAULT_PRODUCT_PRICE_ID
        defaultProductShouldBeFound("productPriceId.contains=" + DEFAULT_PRODUCT_PRICE_ID);

        // Get all the productList where productPriceId contains UPDATED_PRODUCT_PRICE_ID
        defaultProductShouldNotBeFound("productPriceId.contains=" + UPDATED_PRODUCT_PRICE_ID);
    }

    @Test
    @Transactional
    void getAllProductsByProductPriceIdNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productPriceId does not contain DEFAULT_PRODUCT_PRICE_ID
        defaultProductShouldNotBeFound("productPriceId.doesNotContain=" + DEFAULT_PRODUCT_PRICE_ID);

        // Get all the productList where productPriceId does not contain UPDATED_PRODUCT_PRICE_ID
        defaultProductShouldBeFound("productPriceId.doesNotContain=" + UPDATED_PRODUCT_PRICE_ID);
    }

    @Test
    @Transactional
    void getAllProductsByProductNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productName equals to DEFAULT_PRODUCT_NAME
        defaultProductShouldBeFound("productName.equals=" + DEFAULT_PRODUCT_NAME);

        // Get all the productList where productName equals to UPDATED_PRODUCT_NAME
        defaultProductShouldNotBeFound("productName.equals=" + UPDATED_PRODUCT_NAME);
    }

    @Test
    @Transactional
    void getAllProductsByProductNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productName not equals to DEFAULT_PRODUCT_NAME
        defaultProductShouldNotBeFound("productName.notEquals=" + DEFAULT_PRODUCT_NAME);

        // Get all the productList where productName not equals to UPDATED_PRODUCT_NAME
        defaultProductShouldBeFound("productName.notEquals=" + UPDATED_PRODUCT_NAME);
    }

    @Test
    @Transactional
    void getAllProductsByProductNameIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productName in DEFAULT_PRODUCT_NAME or UPDATED_PRODUCT_NAME
        defaultProductShouldBeFound("productName.in=" + DEFAULT_PRODUCT_NAME + "," + UPDATED_PRODUCT_NAME);

        // Get all the productList where productName equals to UPDATED_PRODUCT_NAME
        defaultProductShouldNotBeFound("productName.in=" + UPDATED_PRODUCT_NAME);
    }

    @Test
    @Transactional
    void getAllProductsByProductNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productName is not null
        defaultProductShouldBeFound("productName.specified=true");

        // Get all the productList where productName is null
        defaultProductShouldNotBeFound("productName.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByProductNameContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productName contains DEFAULT_PRODUCT_NAME
        defaultProductShouldBeFound("productName.contains=" + DEFAULT_PRODUCT_NAME);

        // Get all the productList where productName contains UPDATED_PRODUCT_NAME
        defaultProductShouldNotBeFound("productName.contains=" + UPDATED_PRODUCT_NAME);
    }

    @Test
    @Transactional
    void getAllProductsByProductNameNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productName does not contain DEFAULT_PRODUCT_NAME
        defaultProductShouldNotBeFound("productName.doesNotContain=" + DEFAULT_PRODUCT_NAME);

        // Get all the productList where productName does not contain UPDATED_PRODUCT_NAME
        defaultProductShouldBeFound("productName.doesNotContain=" + UPDATED_PRODUCT_NAME);
    }

    @Test
    @Transactional
    void getAllProductsByProductFamilyCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productFamilyCode equals to DEFAULT_PRODUCT_FAMILY_CODE
        defaultProductShouldBeFound("productFamilyCode.equals=" + DEFAULT_PRODUCT_FAMILY_CODE);

        // Get all the productList where productFamilyCode equals to UPDATED_PRODUCT_FAMILY_CODE
        defaultProductShouldNotBeFound("productFamilyCode.equals=" + UPDATED_PRODUCT_FAMILY_CODE);
    }

    @Test
    @Transactional
    void getAllProductsByProductFamilyCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productFamilyCode not equals to DEFAULT_PRODUCT_FAMILY_CODE
        defaultProductShouldNotBeFound("productFamilyCode.notEquals=" + DEFAULT_PRODUCT_FAMILY_CODE);

        // Get all the productList where productFamilyCode not equals to UPDATED_PRODUCT_FAMILY_CODE
        defaultProductShouldBeFound("productFamilyCode.notEquals=" + UPDATED_PRODUCT_FAMILY_CODE);
    }

    @Test
    @Transactional
    void getAllProductsByProductFamilyCodeIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productFamilyCode in DEFAULT_PRODUCT_FAMILY_CODE or UPDATED_PRODUCT_FAMILY_CODE
        defaultProductShouldBeFound("productFamilyCode.in=" + DEFAULT_PRODUCT_FAMILY_CODE + "," + UPDATED_PRODUCT_FAMILY_CODE);

        // Get all the productList where productFamilyCode equals to UPDATED_PRODUCT_FAMILY_CODE
        defaultProductShouldNotBeFound("productFamilyCode.in=" + UPDATED_PRODUCT_FAMILY_CODE);
    }

    @Test
    @Transactional
    void getAllProductsByProductFamilyCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productFamilyCode is not null
        defaultProductShouldBeFound("productFamilyCode.specified=true");

        // Get all the productList where productFamilyCode is null
        defaultProductShouldNotBeFound("productFamilyCode.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByProductFamilyCodeContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productFamilyCode contains DEFAULT_PRODUCT_FAMILY_CODE
        defaultProductShouldBeFound("productFamilyCode.contains=" + DEFAULT_PRODUCT_FAMILY_CODE);

        // Get all the productList where productFamilyCode contains UPDATED_PRODUCT_FAMILY_CODE
        defaultProductShouldNotBeFound("productFamilyCode.contains=" + UPDATED_PRODUCT_FAMILY_CODE);
    }

    @Test
    @Transactional
    void getAllProductsByProductFamilyCodeNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productFamilyCode does not contain DEFAULT_PRODUCT_FAMILY_CODE
        defaultProductShouldNotBeFound("productFamilyCode.doesNotContain=" + DEFAULT_PRODUCT_FAMILY_CODE);

        // Get all the productList where productFamilyCode does not contain UPDATED_PRODUCT_FAMILY_CODE
        defaultProductShouldBeFound("productFamilyCode.doesNotContain=" + UPDATED_PRODUCT_FAMILY_CODE);
    }

    @Test
    @Transactional
    void getAllProductsByProductFamilyNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productFamilyName equals to DEFAULT_PRODUCT_FAMILY_NAME
        defaultProductShouldBeFound("productFamilyName.equals=" + DEFAULT_PRODUCT_FAMILY_NAME);

        // Get all the productList where productFamilyName equals to UPDATED_PRODUCT_FAMILY_NAME
        defaultProductShouldNotBeFound("productFamilyName.equals=" + UPDATED_PRODUCT_FAMILY_NAME);
    }

    @Test
    @Transactional
    void getAllProductsByProductFamilyNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productFamilyName not equals to DEFAULT_PRODUCT_FAMILY_NAME
        defaultProductShouldNotBeFound("productFamilyName.notEquals=" + DEFAULT_PRODUCT_FAMILY_NAME);

        // Get all the productList where productFamilyName not equals to UPDATED_PRODUCT_FAMILY_NAME
        defaultProductShouldBeFound("productFamilyName.notEquals=" + UPDATED_PRODUCT_FAMILY_NAME);
    }

    @Test
    @Transactional
    void getAllProductsByProductFamilyNameIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productFamilyName in DEFAULT_PRODUCT_FAMILY_NAME or UPDATED_PRODUCT_FAMILY_NAME
        defaultProductShouldBeFound("productFamilyName.in=" + DEFAULT_PRODUCT_FAMILY_NAME + "," + UPDATED_PRODUCT_FAMILY_NAME);

        // Get all the productList where productFamilyName equals to UPDATED_PRODUCT_FAMILY_NAME
        defaultProductShouldNotBeFound("productFamilyName.in=" + UPDATED_PRODUCT_FAMILY_NAME);
    }

    @Test
    @Transactional
    void getAllProductsByProductFamilyNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productFamilyName is not null
        defaultProductShouldBeFound("productFamilyName.specified=true");

        // Get all the productList where productFamilyName is null
        defaultProductShouldNotBeFound("productFamilyName.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByProductFamilyNameContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productFamilyName contains DEFAULT_PRODUCT_FAMILY_NAME
        defaultProductShouldBeFound("productFamilyName.contains=" + DEFAULT_PRODUCT_FAMILY_NAME);

        // Get all the productList where productFamilyName contains UPDATED_PRODUCT_FAMILY_NAME
        defaultProductShouldNotBeFound("productFamilyName.contains=" + UPDATED_PRODUCT_FAMILY_NAME);
    }

    @Test
    @Transactional
    void getAllProductsByProductFamilyNameNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productFamilyName does not contain DEFAULT_PRODUCT_FAMILY_NAME
        defaultProductShouldNotBeFound("productFamilyName.doesNotContain=" + DEFAULT_PRODUCT_FAMILY_NAME);

        // Get all the productList where productFamilyName does not contain UPDATED_PRODUCT_FAMILY_NAME
        defaultProductShouldBeFound("productFamilyName.doesNotContain=" + UPDATED_PRODUCT_FAMILY_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductShouldBeFound(String filter) throws Exception {
        restProductMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID)))
            .andExpect(jsonPath("$.[*].productCode").value(hasItem(DEFAULT_PRODUCT_CODE)))
            .andExpect(jsonPath("$.[*].productFamilyId").value(hasItem(DEFAULT_PRODUCT_FAMILY_ID)))
            .andExpect(jsonPath("$.[*].productPriceId").value(hasItem(DEFAULT_PRODUCT_PRICE_ID)))
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME)))
            .andExpect(jsonPath("$.[*].productFamilyCode").value(hasItem(DEFAULT_PRODUCT_FAMILY_CODE)))
            .andExpect(jsonPath("$.[*].productFamilyName").value(hasItem(DEFAULT_PRODUCT_FAMILY_NAME)));

        // Check, that the count call also returns 1
        restProductMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductShouldNotBeFound(String filter) throws Exception {
        restProductMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProduct() throws Exception {
        // Get the product
        restProductMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product
        Product updatedProduct = productRepository.findById(product.getId()).get();
        // Disconnect from session so that the updates on updatedProduct are not directly saved in db
        em.detach(updatedProduct);
        updatedProduct
            .productId(UPDATED_PRODUCT_ID)
            .productCode(UPDATED_PRODUCT_CODE)
            .productFamilyId(UPDATED_PRODUCT_FAMILY_ID)
            .productPriceId(UPDATED_PRODUCT_PRICE_ID)
            .productName(UPDATED_PRODUCT_NAME)
            .productFamilyCode(UPDATED_PRODUCT_FAMILY_CODE)
            .productFamilyName(UPDATED_PRODUCT_FAMILY_NAME);

        restProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProduct.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProduct))
            )
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testProduct.getProductCode()).isEqualTo(UPDATED_PRODUCT_CODE);
        assertThat(testProduct.getProductFamilyId()).isEqualTo(UPDATED_PRODUCT_FAMILY_ID);
        assertThat(testProduct.getProductPriceId()).isEqualTo(UPDATED_PRODUCT_PRICE_ID);
        assertThat(testProduct.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testProduct.getProductFamilyCode()).isEqualTo(UPDATED_PRODUCT_FAMILY_CODE);
        assertThat(testProduct.getProductFamilyName()).isEqualTo(UPDATED_PRODUCT_FAMILY_NAME);
    }

    @Test
    @Transactional
    void putNonExistingProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, product.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(product))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(product))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductWithPatch() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product using partial update
        Product partialUpdatedProduct = new Product();
        partialUpdatedProduct.setId(product.getId());

        partialUpdatedProduct.productName(UPDATED_PRODUCT_NAME).productFamilyCode(UPDATED_PRODUCT_FAMILY_CODE);

        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProduct.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProduct))
            )
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testProduct.getProductCode()).isEqualTo(DEFAULT_PRODUCT_CODE);
        assertThat(testProduct.getProductFamilyId()).isEqualTo(DEFAULT_PRODUCT_FAMILY_ID);
        assertThat(testProduct.getProductPriceId()).isEqualTo(DEFAULT_PRODUCT_PRICE_ID);
        assertThat(testProduct.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testProduct.getProductFamilyCode()).isEqualTo(UPDATED_PRODUCT_FAMILY_CODE);
        assertThat(testProduct.getProductFamilyName()).isEqualTo(DEFAULT_PRODUCT_FAMILY_NAME);
    }

    @Test
    @Transactional
    void fullUpdateProductWithPatch() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product using partial update
        Product partialUpdatedProduct = new Product();
        partialUpdatedProduct.setId(product.getId());

        partialUpdatedProduct
            .productId(UPDATED_PRODUCT_ID)
            .productCode(UPDATED_PRODUCT_CODE)
            .productFamilyId(UPDATED_PRODUCT_FAMILY_ID)
            .productPriceId(UPDATED_PRODUCT_PRICE_ID)
            .productName(UPDATED_PRODUCT_NAME)
            .productFamilyCode(UPDATED_PRODUCT_FAMILY_CODE)
            .productFamilyName(UPDATED_PRODUCT_FAMILY_NAME);

        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProduct.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProduct))
            )
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testProduct.getProductCode()).isEqualTo(UPDATED_PRODUCT_CODE);
        assertThat(testProduct.getProductFamilyId()).isEqualTo(UPDATED_PRODUCT_FAMILY_ID);
        assertThat(testProduct.getProductPriceId()).isEqualTo(UPDATED_PRODUCT_PRICE_ID);
        assertThat(testProduct.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testProduct.getProductFamilyCode()).isEqualTo(UPDATED_PRODUCT_FAMILY_CODE);
        assertThat(testProduct.getProductFamilyName()).isEqualTo(UPDATED_PRODUCT_FAMILY_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, product.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(product))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(product))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeDelete = productRepository.findAll().size();

        // Delete the product
        restProductMockMvc
            .perform(delete(ENTITY_API_URL_ID, product.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
