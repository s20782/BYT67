package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;
	
	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		SweBank.deposit("Ulrika", new Money(200,SEK));
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() {
		assertEquals("SweBank",SweBank.getName());
		assertEquals("Nordea",Nordea.getName());
		assertEquals("DanskeBank",DanskeBank.getName());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK,SweBank.getCurrency());
		assertEquals(SEK,Nordea.getCurrency());
		assertEquals(DKK,DanskeBank.getCurrency());
	}

	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
		assertThrows(AccountExistsException.class, () -> SweBank.openAccount("Bob"));
		assertThrows(AccountExistsException.class, () -> SweBank.openAccount("Ulrika"));
		assertThrows(AccountExistsException.class, () -> Nordea.openAccount("Bob"));
		Nordea.openAccount("Ulrika");
	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		assertThrows(AccountDoesNotExistException.class, () -> SweBank.deposit("Bor",new Money(200,SEK)));
		SweBank.deposit("Bob",new Money(200,SEK));
	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		assertThrows(AccountDoesNotExistException.class, () -> SweBank.withdraw("Bor",new Money(200,SEK)));
		SweBank.withdraw("Bob",new Money(200,SEK));
	}
	
	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		assertEquals("0",Integer.valueOf(0),SweBank.getBalance("Bob"));
		assertEquals("200",Integer.valueOf(200),SweBank.getBalance("Ulrika"));
	}
	
	@Test
	public void testTransfer() throws AccountDoesNotExistException {
		assertThrows(AccountDoesNotExistException.class, () -> SweBank.transfer("Bor",Nordea,"Bob",new Money(200,SEK)));
		SweBank.transfer("Bob",Nordea,"Bob",new Money(200,SEK));
		assertEquals("0",Integer.valueOf(-200),SweBank.getBalance("Bob"));
		assertEquals("0",Integer.valueOf(200),Nordea.getBalance("Bob"));
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		SweBank.addTimedPayment("Bob", "BobPaymentOne", 5, 5, new Money(200,SEK), Nordea, "Bob");
		SweBank.tick();
		SweBank.tick();
		SweBank.tick();
		SweBank.tick();
		SweBank.tick();
		assertEquals(Integer.valueOf(200),Nordea.getBalance("Bob"));
		SweBank.tick();
		SweBank.removeTimedPayment("Bob","BobPaymentOne");
		SweBank.tick();
		SweBank.tick();
		SweBank.tick();
		SweBank.tick();
		assertEquals(Integer.valueOf(400),Nordea.getBalance("Bob"));
	}
}
