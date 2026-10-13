package testPaketea;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import dataAccess.DataAccess;
import testOperations.TestDataAccess;

public class ChatIrekiBDBlackTest {

	static DataAccess sut = new DataAccess();

	static TestDataAccess testDA = new TestDataAccess();

	private String user1Mail;
	private String user1Name;

	private String user2Mail;
	private String user2Name;

	@Before
	public void defaultValues() {
		user1Mail = "user1@ehu.eus";
		user1Name = "user1";

		user2Mail = "user2@ehu.eus";
		user2Name = "user2";
	}

	@Test
	public void test1() {
		// Both mails on BD (no chat between them) -> return true and open new chat
		testDA.open();
		testDA.createSeller(user1Mail, user1Name);
		testDA.createSeller(user2Mail, user2Name);
		testDA.close();

		try {
			sut.open();
			boolean result = sut.chatIreki(user1Mail, user2Mail);
			int size = sut.chatakLortu(user1Mail).size();
			sut.close();

			assertTrue(result);
			assertEquals(1, size);
		} finally {
			testDA.open();
			testDA.removeChat(user1Mail, user2Mail);
			testDA.removeSeller(user1Mail);
			testDA.removeSeller(user2Mail);
			testDA.close();
		}
	}

	@Test
	public void test2() {
		// Both mails on BD (chat between them) -> return false
		testDA.open();
		testDA.createSeller(user1Mail, user1Name);
		testDA.createSeller(user2Mail, user2Name);
		testDA.addChat(user1Mail, user2Mail);
		testDA.close();

		try {
			sut.open();
			boolean result = sut.chatIreki(user1Mail, user2Mail);
			int size = sut.chatakLortu(user1Mail).size();
			sut.close();

			assertFalse(result);
			assertEquals(1, size);
		} finally {
			testDA.open();
			testDA.removeChat(user1Mail, user2Mail);
			testDA.removeSeller(user1Mail);
			testDA.removeSeller(user2Mail);
			testDA.close();
		}
	}

	@Test
	public void test3() {
		// user1@ehu.eus is not on BD
		testDA.open();
		testDA.createSeller(user2Mail, user2Name);
		testDA.close();

		try {
			sut.open();
			boolean result = sut.chatIreki(user1Mail, user2Mail);
			boolean isEmpty = sut.chatakLortu(user2Mail).isEmpty();
			sut.close();

			assertFalse(result);
			assertTrue(isEmpty);
		} finally {
			testDA.open();
			testDA.removeSeller(user2Mail);
			testDA.close();
		}
	}

	@Test
	public void test4() {
		// user2@ehu.eus is not on BD
		testDA.open();
		testDA.createSeller(user1Mail, user1Name);
		testDA.close();

		try {
			sut.open();
			boolean result = sut.chatIreki(user1Mail, user2Mail);
			boolean isEmpty = sut.chatakLortu(user1Mail).isEmpty();
			sut.close();

			assertFalse(result);
			assertTrue(isEmpty);
		} finally {
			testDA.open();
			testDA.removeSeller(user1Mail);
			testDA.close();
		}
	}
}