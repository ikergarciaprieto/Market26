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

public class KarrituraEramanMockWhiteTest {
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
	private double hasierakoDirua;

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
		hasierakoDirua = 1000;
		saleNum = 1;

		buyer = new Seller(buyerEmail, "Kim", "aurrera");
		buyer2 = new Seller(buyerEmail + ".eus", "James", "aurrera");
		seller = new Seller(sellerEmail, "Chris", "aurrera");
		seller2 = new Seller(sellerEmail + ".eus", "Larry", "aurrera");
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
	// Jarraitutako bidea: 1 - try1(F) - IF1(F) - IF2(F) - 5 - 6 - END
	// Gertatzen da user.getErosketaAnitza() != null eta sale.getSeller() ==
	// erosAnitz.getSeller() direnean
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
	// Jarraitutako bidea: 1 - try1(F) - IF1(F) - IF2(T) - EXC - 6 - END
	// Gertatzen da user.getErosketaAnitza() != null eta sale.getSeller() !=
	// erosAnitz.getSeller() direnean
	// Aldaketarik ez
	public void test2() {
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

	@Test
	// Jarraitutako bidea: 1 - try1(F) - IF1(T) - 10 - 5 - 6 - END
	// Gertatzen da user.getErosketaAnitza() == null denean
	// Erosketa anitza sortu user-ari, erosketa horri salenum duen sale-a gehitu,
	// sale hori anitza egin, erosketa anitzari sale horren prezioa gehitu
	public void test3() {
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
}