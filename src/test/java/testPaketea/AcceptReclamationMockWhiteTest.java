package testPaketea;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
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

import dataAccess.DataAccess;
import domain.Erreklamazioa;
import domain.Sale;
import domain.Seller;

public class AcceptReclamationMockWhiteTest {
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
		
		seller = new Seller(sellerEmail, "Iker", "aurrera");
		
		sale = seller.addSale("Ordenagailua", "6Gb-ko memoria", 1, (float) salePrice, new Date(), null);
		sale.setErreklamatuta(true);
		
		buyer = new Seller(buyerEmail, "Aitzol", "aurrera");
		buyer.setDiruTotala(hasierakoDirua);
		
		errek = new Erreklamazioa(sale, new Date(), "Oso ondo dago", buyer);
		
		Mockito.when(db.find(Erreklamazioa.class, errekId)).thenReturn(errek);

	}
	
	@After
    public  void tearDown() {
		persistenceMock.close();
    }
	
	/*@Test
	//Jarraitutako bidea: try1(F) - 3 - IF1(T) - IF2(T) - 7 - 9 - END
    public void test1() {
        boolean b = true;

        try {
            sut.open();
            sut.acceptReclamation(b, errekId);
            sut.close();

            assertEquals("Aceptada", errek.getOnartua());
            assertEquals(hasierakoDirua + salePrice, buyer.getDiruTotala(), 0.01);
            Mockito.verify(et).commit();

        } catch (Exception e) {
        	fail();
        }
    }

    @Test
  //Jarraitutako bidea: try1(F) - 3 - IF1(T) - IF2(F) - 8 - 9 - END
    public void test2() {
        boolean b = false;

        try {
            sut.open();
            sut.acceptReclamation(b, errekId);
            sut.close();

            assertEquals("Denegada", errek.getOnartua());
            assertFalse(sale.isErreklamatuta());
            Mockito.verify(et).commit();

        } catch (Exception e) {
        	fail();
        
        }
    }

    @Test
  //Jarraitutako bidea: try1(T) - EXC - END
    public void test3() {
        boolean b = true;
        Erreklamazioa errekSaleNull = new Erreklamazioa(null, new Date(), "Defectuoso", buyer);

        Mockito.when(db.find(Erreklamazioa.class, errekId)).thenReturn(errekSaleNull);
        Mockito.when(et.isActive()).thenReturn(true);

        try {
            sut.open();
            sut.acceptReclamation(b, errekId);
            sut.close();
            
            fail();

        } catch (Exception e) {
            Mockito.verify(et).rollback();
        }
    }

    @Test
  //Jarraitutako bidea: try1(F) - 3 - IF1(F) - 5 - END
    public void test4() {
        boolean b = true;
        long idNegatiboa = 999;

        Mockito.when(db.find(Erreklamazioa.class, idNegatiboa)).thenReturn(null);

        try {
            sut.open();
            sut.acceptReclamation(b, idNegatiboa);
            sut.close();
            
            assertNull(db.find(Erreklamazioa.class, idNegatiboa));
            
            Mockito.verify(et).rollback();

            

        } catch (Exception e) {
            fail();
        }
    }*/

}
