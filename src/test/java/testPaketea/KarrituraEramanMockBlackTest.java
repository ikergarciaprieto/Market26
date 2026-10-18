package testPaketea;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import dataAccess.CreateSaleData;
import dataAccess.DataAccess;
import domain.ErosketaAnitza;
import domain.Erreklamazioa;
import domain.Sale;
import domain.Seller;

public class KarrituraEramanMockBlackTest {
	static DataAccess sut;

	protected MockedStatic<Persistence> persistenceMock;

	@Mock
	protected EntityManagerFactory entityManagerFactory;
	@Mock
	protected EntityManager db;
	@Mock
	protected EntityTransaction et;

	private String buyerEmail;
	private String sellerEmail;
	private int saleNum;
	private double salePrice;

	private Seller buyer;
	private Seller buyer2;
	private Seller seller;
	private Seller seller2;
	private Sale sale;
	private Sale sale2;
	private ErosketaAnitza ea;

	@Before
	public void init() {
		MockitoAnnotations.openMocks(this);
		persistenceMock = Mockito.mockStatic(Persistence.class);
		persistenceMock.when(() -> Persistence.createEntityManagerFactory(Mockito.any()))
				.thenReturn(entityManagerFactory);

		Mockito.doReturn(db).when(entityManagerFactory).createEntityManager();
		Mockito.doReturn(et).when(db).getTransaction();
		sut = new DataAccess(db);

		buyerEmail = "buyerTest@ehu.eus";
		sellerEmail = "sellerTest@ehu.eus";
		salePrice = 10;
		saleNum = 1;

		seller = new Seller(sellerEmail, "Chris", "aurrera");
		seller2 = new Seller(sellerEmail + ".eus", "Larry", "aurrera");

		buyer = new Seller(buyerEmail, "Kim", "aurrera");
		buyer2 = new Seller(buyerEmail + ".eus", "James", "aurrera");

		sale2 = seller2.addSale(
				new CreateSaleData("Maila2", "Marroia", 1, (float) salePrice, new Date(), seller2.getEmail(), null));
		sale2.setSaleNumber(saleNum + 2);

		sale = seller.addSale(
				new CreateSaleData("Maila2", "Marroia", 1, (float) salePrice, new Date(), seller.getEmail(), null));
		sale.setSaleNumber(saleNum);

		Sale sale3 = seller.addSale(
				new CreateSaleData("Maila", "Marroia", 1, (float) salePrice, new Date(), seller.getEmail(), null));
		sale3.setSaleNumber(saleNum + 1);

		ErosketaAnitza ea = buyer.createErosketaAnitza(sale3);
		ea.addSales(sale3);
		sale3.setAnitza(ea);
		ea.gehituPrezioa(sale3.getPrice());

		Mockito.when(db.find(Seller.class, buyerEmail)).thenReturn(buyer);
		Mockito.when(db.find(Seller.class, buyerEmail + ".eus")).thenReturn(buyer2);

		Mockito.when(db.find(Sale.class, saleNum)).thenReturn(sale);
		Mockito.when(db.find(Sale.class, saleNum + 2)).thenReturn(sale2);

	}

	@After
	public void tearDown() {
		persistenceMock.close();
	}

	@Test
	// user erosketa anitza du eta honen seller sale-aren berdina da
	// Erosketa anitzari salenum duen sale-a gehitu, sale hori anitza egin, erosketa
	// anitzari sale horren prezioa gehitu
	public void test1() {
		try {
			sut.open();
			boolean em = sut.karrituraEraman(buyerEmail, saleNum);
			sut.close();

			assertNotNull(sale.getAnitza());
			assertTrue(em);

			Mockito.verify(et).commit();

		} catch (Exception e) {
			fail();
		}
	}

	@Test
	// user ez du erosketa anitza
	// Erosketa anitza sortu user-ari, erosketa horri salenum duen sale-a gehitu,
	// sale hori anitza egin, erosketa anitzari sale horren prezioa gehitu
	public void test2() {
		try {

			sut.open();
			boolean em = sut.karrituraEraman(buyerEmail + ".eus", saleNum);
			sut.close();

			assertNotNull(sale.getAnitza());
			assertTrue(em);

			Mockito.verify(et).commit();

		} catch (Exception e) {
			fail();

		}
	}

	@Test
	// user erosketa anitza du baina honen seller ez da sale-aren berdina
	// Aldaketarik ez
	public void test3() {
		try {
			sut.open();
			boolean em = sut.karrituraEraman(buyerEmail, saleNum + 2);
			sut.close();

			assertNull(sale.getAnitza());
			assertTrue(!em);

			Mockito.verify(et).commit();

		} catch (Exception e) {
			fail();

		}
	}
}