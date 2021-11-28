package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
	}
	
	@Test
	public void testAddRemoveTimedPayment() {
		testAccount.addTimedPayment("test", 1, 1, new Money(200,SEK), SweBank, "Alice");
		testAccount.tick();
		assertEquals(Double.valueOf(99998.0),testAccount.getBalance().getAmount());
		testAccount.tick();
		assertEquals(Double.valueOf(99996.0),testAccount.getBalance().getAmount());
		testAccount.removeTimedPayment("test");
		testAccount.tick();
		testAccount.tick();
		testAccount.tick();
		assertEquals(Double.valueOf(99996.0),testAccount.getBalance().getAmount());
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		testAccount.addTimedPayment("test", 1, 1, new Money(200,SEK), SweBank, "Alice");
		testAccount.tick();
		assertEquals(Double.valueOf(99998.0),testAccount.getBalance().getAmount());
		testAccount.tick();
		assertEquals(Double.valueOf(99996.0),testAccount.getBalance().getAmount());
	}

	@Test
	public void testAddWithdraw() {
		testAccount.deposit(new Money(200,SEK));
		assertEquals(Double.valueOf(100002.0),testAccount.getBalance().getAmount());
		testAccount.withdraw(new Money(200,SEK));
		assertEquals(Double.valueOf(100000.0),testAccount.getBalance().getAmount());
	}
	
	@Test
	public void testGetBalance() {
		assertEquals(Double.valueOf(100000.0),testAccount.getBalance().getAmount());
	}
}
