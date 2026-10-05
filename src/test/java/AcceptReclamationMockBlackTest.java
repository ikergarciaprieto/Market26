import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import dataAccess.DataAccess;
import domain.Erreklamazioa;
import domain.Sale;
import domain.Seller;
import exceptions.MustBeLaterThanTodayException;
import exceptions.ParamNullException;
import exceptions.SaleAlreadyExistException;

public class AcceptReclamationMockBlackTest {
	static DataAccess sut;
	
	protected MockedStatic<Persistence> persistenceMock;

	@Mock
	protected  EntityManagerFactory entityManagerFactory;
	@Mock
	protected  EntityManager db;
	@Mock
    protected  EntityTransaction  et;
	
	private  long errekId;
	private String buyerEmail;
	private String sellerEmail;
	private double salePrice;
	private double hasierakoDirua;
	
	private Seller buyer;
    private Seller seller;
    private Sale sale;
    private Erreklamazioa errek;
	
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
		errekId = 1;
		
		Seller seller = new Seller(sellerEmail, "Iker", "aurrera");
		
		Sale sale = seller.addSale("Ordenagailua", "6Gb-ko memoria", 1, (float) salePrice, new Date(), null);
		sale.setErreklamatuta(true);
		
		Seller erreklamatzenDuena = new Seller(buyerEmail, "Aitzol", "aurrera");
		erreklamatzenDuena.setDiruTotala(hasierakoDirua);
		
		Erreklamazioa errek = new Erreklamazioa(sale, new Date(), "Oso ondo dago", erreklamatzenDuena);
		
		Mockito.when(db.find(Erreklamazioa.class, errekId)).thenReturn(errek);

	}
	
	@After
    public  void tearDown() {
		persistenceMock.close();
    }
	
	@Test
	// b = true, errekId = 1 eta errekId existitzen da Mockito bidez sortutako datu basean
    public void test1() {
        boolean b = true;

        try {
            sut.open();
            sut.acceptReclamation(b, errekId);
            sut.close();

            assertEquals("Onartua", errek.getOnartua());
            assertEquals(hasierakoDirua + salePrice, buyer.getDiruTotala(), 0.01);

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    // b = false, errekId = 1 eta errekId existitzen da Mockito bidez sortutako datu basean
    public void test2() {
        boolean b = false;

        try {
            sut.open();
            sut.acceptReclamation(b, errekId);
            sut.close();

            assertEquals("EzOnartua", errek.getOnartua());
            assertEquals(hasierakoDirua, buyer.getDiruTotala(), 0.01);

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    // b = true eta errekId = 1 positiboa da baina ez da existitzen Mockito bidez sortutako datu basean
    public void test3() {
        boolean b = true;
        long existituGabekoId = 999;

        Mockito.when(db.find(Erreklamazioa.class, existituGabekoId)).thenReturn(null);

        try {
            sut.open();
            sut.acceptReclamation(b, existituGabekoId);
            sut.close();

            assertNull(db.find(Erreklamazioa.class, existituGabekoId));

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    // b = true eta errekId = -1 positiboa baina ez da existitzen Mockito bidez sortutako datu basean
    public void test4() {
        boolean b = true;
        long idNegatiboa = -1;

        Mockito.when(db.find(Erreklamazioa.class, idNegatiboa)).thenReturn(null);

        try {
            sut.open();
            sut.acceptReclamation(b, idNegatiboa);
            sut.close();

            assertNull(db.find(Erreklamazioa.class, idNegatiboa));

        } catch (Exception e) {
            fail();
        }
    }

}
