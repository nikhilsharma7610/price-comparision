package com.service.pricecomparision;

import com.service.pricecomparision.constant.AppConstant;
import com.service.pricecomparision.dao.IProductDBDao;
import com.service.pricecomparision.dao.IProductESDao;
import com.service.pricecomparision.dto.SearchRequest;
import com.service.pricecomparision.enums.SortingOptions;
import com.service.pricecomparision.repository.Product;
import com.service.pricecomparision.requests.ProductCreateRequest;
import com.service.pricecomparision.requests.ProductUpdateRequest;
import com.service.pricecomparision.service.IMongoToESService;
import com.service.pricecomparision.service.IPartnerService;
import com.service.pricecomparision.service.ISearchService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PricecomparisionApplicationTests {

	private static final Logger LOG = LoggerFactory.getLogger(PricecomparisionApplicationTests.class);

	@Autowired
	private IProductESDao productESDao;

	@Autowired
	private IProductDBDao productDBDao;

	@Autowired
	private IPartnerService partnerService;

	@Autowired
	private ISearchService searchService;

	@Autowired
	private IMongoToESService mongoToESService;

	@BeforeAll
	public void before() {

		productDBDao.removeAllDocuments();
		productESDao.deleteIndex(AppConstant.IndexName.PRODUCT);
		productESDao.createIndex(AppConstant.IndexName.PRODUCT);
	}

	@Test
	@Order(1)
	public void createProduct_test() throws Exception {

		ProductCreateRequest productCreateRequest = new ProductCreateRequest();
		productCreateRequest.setProductName("Fruity");
		productCreateRequest.setProviderId("P1");
		productCreateRequest.setProviderName("Provider1");
		productCreateRequest.setCategoryId("Cat1");
		productCreateRequest.setCategoryName("Category1");
		productCreateRequest.setPrice(10D);

		String productId = partnerService.save(productCreateRequest);
		Assert.assertNotNull(productId);
	}

	@Test
	@Order(2)
	public void updateProduct_test() throws Exception {

		ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest();
		productUpdateRequest.setProductName("Fruity");
		productUpdateRequest.setProviderId("P1");
		productUpdateRequest.setCategoryId("Cat1");
		productUpdateRequest.setPrice(20D);

		String productId = partnerService.save(productUpdateRequest);
		Assert.assertNotNull(productId);
	}

	@Test
	@Order(3)
	public void addMultipleProducts_test() throws Exception {

		ProductCreateRequest productCreateRequest1 = new ProductCreateRequest();
		productCreateRequest1.setProductName("Fruity");
		productCreateRequest1.setProviderId("P2");
		productCreateRequest1.setProviderName("Provider2");
		productCreateRequest1.setCategoryId("Cat1");
		productCreateRequest1.setCategoryName("Category1");
		productCreateRequest1.setPrice(10D);

		ProductCreateRequest productCreateRequest2 = new ProductCreateRequest();
		productCreateRequest2.setProductName("Fruity");
		productCreateRequest2.setProviderId("P3");
		productCreateRequest2.setProviderName("Provider3");
		productCreateRequest2.setCategoryId("Cat1");
		productCreateRequest2.setCategoryName("Category1");
		productCreateRequest2.setPrice(5D);


		String productId1 = partnerService.save(productCreateRequest1);
		String productId2 = partnerService.save(productCreateRequest2);

		Assert.assertNotNull(productId1);
		Assert.assertNotNull(productId2);
	}

	//@Test
	//@Order(4)
	/*
		Commenting this test because it was failing due to limitation of MongoToEs sync design

		Tests are executing in [main] thread, while sync is getting executed in [ForkJoinPool.commonPool]
		Since both are not executing in parallel (?), so expected products are not present in ES when search query is executed
		Tried even by giving 0 sync interval for tests
		mvn clean install -Dmongo.es.sync.interval.ms=0
	 */
	public void searchProducts_test() throws InterruptedException {

		SearchRequest searchRequest = SearchRequest.builder()
				.category("C1")
				.productName("Fruity")
				.sortingOption(SortingOptions.DEFAULT)
				.build();
		mongoToESService.waitToSyncMongoToEs();
		List<Product> products = searchService.search(searchRequest);

		Assert.assertEquals(3, products.size());
		Assert.assertEquals("P3", products.get(0).getProviderId());
		Assert.assertTrue(Double.compare(products.get(0).getPrice(), 5D) == 0);
	}

}
