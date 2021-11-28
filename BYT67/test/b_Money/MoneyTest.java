package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	@Test
	public void testGetAmount() {
		assertEquals("200",Double.valueOf(200),SEK200.getAmount());
		assertEquals("SEK",Double.valueOf(100),SEK100.getAmount());
		assertEquals("0",Double.valueOf(0),SEK0.getAmount());
		assertEquals("-100",Double.valueOf(-100),SEKn100.getAmount());

		assertEquals("20",Double.valueOf(20),EUR20.getAmount());
		assertEquals("10",Double.valueOf(10),EUR10.getAmount());
		assertEquals("0",Double.valueOf(0),EUR0.getAmount());		
	}

	@Test
	public void testGetCurrency() {
		assertEquals("200",SEK,SEK200.getCurrency());
		assertEquals("SEK",SEK,SEK100.getCurrency());
		assertEquals("0",SEK,SEK0.getCurrency());
		assertEquals("-100",SEK,SEKn100.getCurrency());
		
		assertEquals("20",EUR,EUR20.getCurrency());
		assertEquals("EUR",EUR,EUR10.getCurrency());
		assertEquals("0",EUR,EUR0.getCurrency());		
	}

	@Test
	public void testToString() {
		assertEquals( "200.0 SEK", "200.0 SEK",SEK200.toString());
		assertEquals( "100.0 SEK", "100.0 SEK",SEK100.toString());
		assertEquals(   "0.0 SEK",   "0.0 SEK",SEK0.toString());
		assertEquals("-100.0 SEK","-100.0 SEK",SEKn100.toString());
		
		assertEquals("20.0 EUR","20.0 EUR",EUR20.toString());
		assertEquals("10.0 EUR","10.0 EUR",EUR10.toString());
		assertEquals( "0.0 EUR", "0.0 EUR",EUR0.toString());
	}

	@Test
	public void testGlobalValue() {
		assertEquals( "200*0,15", Double.valueOf(20000*0.15),SEK200.universalValue());
		assertEquals( "100*0,15", Double.valueOf(10000*0.15),SEK100.universalValue());
		assertEquals(   "0*0,15", Double.valueOf(0*0.15)  ,SEK0.universalValue());
		assertEquals("-100*0,15", Double.valueOf(-10000*0.15),SEKn100.universalValue());
		
		assertEquals("20*1,5",Double.valueOf(2000*1.5),EUR20.universalValue());
		assertEquals("10*1,5",Double.valueOf(1000*1.5),EUR10.universalValue());
		assertEquals(" 0*1,5", Double.valueOf(000*1.5),EUR0.universalValue());
	}

	@Test
	public void testEqualsMoney() {
		assertTrue("0 EUR == 0 SEK", EUR0.equals(SEK0));
		assertTrue("10 EUR == 100 SEK", EUR10.equals(SEK100));
		assertFalse("10 EUR == 200 SEK", EUR10.equals(SEK200));
	}

	@Test
	public void testAdd() {
		assertEquals("0 EUR + 0 SEK",Double.valueOf(0),EUR0.add(SEK0).getAmount());
		assertEquals("0 EUR + 0 SEK",EUR,EUR0.add(SEK0).getCurrency());
		
		assertEquals("10EUR + 100SEK",Double.valueOf(20),EUR10.add(SEK100).getAmount());
		assertEquals("10EUR + 100SEK",EUR,EUR10.add(SEK100).getCurrency());
		
		assertEquals("200SEK + 20EUR",Double.valueOf(400),SEK200.add(EUR20).getAmount());
		assertEquals("200SEK + 20EUR",SEK,SEK200.add(EUR20).getCurrency());	
		
		assertEquals("-100SEK + 0EUR",Double.valueOf(-100),SEKn100.add(EUR0).getAmount());
		assertEquals("-100SEK + 0EUR",SEK,SEKn100.add(EUR0).getCurrency());	
		
		assertEquals("0EUR + -100SEK",Double.valueOf(-10),EUR0.add(SEKn100).getAmount());
		assertEquals("0EUR + -100SEK",EUR,EUR0.add(SEKn100).getCurrency());	
		
		assertEquals("0SEK + -100SEK",Double.valueOf(-100),SEK0.add(SEKn100).getAmount());
		assertEquals("0EUR + -100SEK",SEK,SEK0.add(SEKn100).getCurrency());
	}
	
	@Test
	public void testSub() {
		assertEquals("0 EUR - 0 SEK",Double.valueOf(0),EUR0.sub(SEK0).getAmount());
		assertEquals("0 EUR - 0 SEK",EUR,EUR0.sub(SEK0).getCurrency());
		
		assertEquals("10EUR - 100SEK",Double.valueOf(0),EUR10.sub(SEK100).getAmount());
		assertEquals("10EUR - 100SEK",EUR,EUR10.sub(SEK100).getCurrency());
		
		assertEquals("200SEK - 20EUR",Double.valueOf(0),SEK200.sub(EUR20).getAmount());
		assertEquals("200SEK - 20EUR",SEK,SEK200.sub(EUR20).getCurrency());

		assertEquals("-100SEK - 0EUR",Double.valueOf(-100),SEKn100.sub(EUR0).getAmount());
		assertEquals("-100SEK - 0EUR",SEK,SEKn100.sub(EUR0).getCurrency());	
		
		assertEquals("0EUR - -100SEK",Double.valueOf(10),EUR0.sub(SEKn100).getAmount());
		assertEquals("0EUR - -100SEK",EUR,EUR0.sub(SEKn100).getCurrency());	
	}

	@Test
	public void testIsZero() {
		assertTrue("EUR0 == 0",EUR0.isZero());
		assertTrue("SEK0 == 0",SEK0.isZero());
		assertFalse("SEK100 == 0", SEK100.isZero());
		assertFalse("SEKn100 == 0", SEKn100.isZero());
	}

	@Test
	public void testNegate() {
		assertEquals("negat EUR0",Double.valueOf(0),EUR0.negate().getAmount());
		assertEquals("negat EUR0",EUR,EUR0.negate().getCurrency());
		
		assertEquals("negat SEK100",Double.valueOf(-100),SEK100.negate().getAmount());
		assertEquals("negat SEK100",SEK,SEK100.negate().getCurrency());
		
		assertEquals("negat SEKn100",Double.valueOf(100),SEKn100.negate().getAmount());
		assertEquals("negat SEKn100",SEK,SEKn100.negate().getCurrency());
	}

	@Test
	public void testCompareTo() {
		assertEquals("SEK200 > EUR0",1,SEK200.compareTo(EUR0));
		assertEquals("EUR0 == SEK0",0,EUR0.compareTo(SEK0));
		assertEquals("SEKn100 < EUR10",-1,SEKn100.compareTo(EUR10));
	}
}
