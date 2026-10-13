package testPaketea;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import dataAccess.DataAccess;
import domain.Chat;
import domain.Seller;

public class ChatIrekiMockBlackTest {

	static DataAccess sut;

	protected MockedStatic<Persistence> persistenceMock;

	@Mock
	protected EntityManagerFactory entityManagerFactory;
	@Mock
	protected EntityManager db;
	@Mock
	protected EntityTransaction et;

	private Seller user1;
	private String user1Mail;
	private String user1Name;

	private Seller user2;
	private String user2Mail;
	private String user2Name;

	private List<Chat> chats;
	private Chat chat;

	@Before
	public void init() {
		MockitoAnnotations.openMocks(this);
		persistenceMock = Mockito.mockStatic(Persistence.class);
		persistenceMock.when(() -> Persistence.createEntityManagerFactory(Mockito.any()))
				.thenReturn(entityManagerFactory);

		Mockito.doReturn(db).when(entityManagerFactory).createEntityManager();
		Mockito.doReturn(et).when(db).getTransaction();
		sut = new DataAccess(db);

		user1Mail = "user1@ehu.eus";
		user1Name = "user1";
		user1 = new Seller(user1Mail, user1Name, "aurrera");

		user2Mail = "user2@ehu.eus";
		user2Name = "user2";
		user2 = new Seller(user2Mail, user2Name, "aurrera");

		chats = new ArrayList<Chat>();
		chat = new Chat(user1, user2);
		chats.add(chat);

		Mockito.when(db.find(Seller.class, user1.getEmail())).thenReturn(user1);
		Mockito.when(db.find(Seller.class, user2.getEmail())).thenReturn(user2);
	}

	@After
	public void tearDown() {
		persistenceMock.close();
	}

	@Test
	public void test1() {
		// Both mails on BD (no chat between them) -> return true and open new chat
		@SuppressWarnings("unchecked")
		TypedQuery<Chat> query = Mockito.mock(TypedQuery.class);

		Mockito.when(db.createQuery(anyString(), eq(Chat.class))).thenReturn(query);
		Mockito.when(query.getResultList()).thenReturn(new ArrayList<Chat>());

		boolean result = sut.chatIreki(user1Mail, user2Mail);

		assertTrue(result);
		Mockito.verify(et).begin();
		Mockito.verify(db).persist(Mockito.any(Chat.class));
		Mockito.verify(et).commit();
	}

	@Test
	public void test2() {
		// Both mails on BD (chat between them) -> return false
		@SuppressWarnings("unchecked")
		TypedQuery<Chat> query = Mockito.mock(TypedQuery.class);

		Mockito.when(db.createQuery(anyString(), eq(Chat.class))).thenReturn(query);
		Mockito.when(query.getResultList()).thenReturn(chats);

		boolean result = sut.chatIreki(user1Mail, user2Mail);

		assertFalse(result);
		Mockito.verify(et).begin();
		Mockito.verify(et).commit();
		Mockito.verify(db, Mockito.never()).persist(Mockito.any());
	}

	@Test
	public void test3() {
		// user1@ehu.eus is not on BD
		@SuppressWarnings("unchecked")
		TypedQuery<Chat> query = Mockito.mock(TypedQuery.class);

		Mockito.when(db.find(Seller.class, user1Mail)).thenReturn(null);
		Mockito.when(db.createQuery(anyString(), eq(Chat.class))).thenReturn(query);
		Mockito.when(query.getResultList()).thenReturn(new ArrayList<Chat>());

		boolean result = sut.chatIreki(user1Mail, user2Mail);

		assertFalse(result);
		Mockito.verify(et).begin();
		Mockito.verify(et).commit();
		Mockito.verify(db, Mockito.never()).persist(Mockito.any());
	}

	@Test
	public void test4() {
		// user2@ehu.eus is not on BD
		@SuppressWarnings("unchecked")
		TypedQuery<Chat> query = Mockito.mock(TypedQuery.class);

		Mockito.when(db.find(Seller.class, user2Mail)).thenReturn(null);
		Mockito.when(db.createQuery(anyString(), eq(Chat.class))).thenReturn(query);
		Mockito.when(query.getResultList()).thenReturn(new ArrayList<Chat>());

		boolean result = sut.chatIreki(user1Mail, user2Mail);

		assertFalse(result);
		Mockito.verify(et).begin();
		Mockito.verify(et).commit();
		Mockito.verify(db, Mockito.never()).persist(Mockito.any());
	}
}
