package cucumber;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.List;

import org.testng.annotations.Test;

public class calculatorTest {
	
	@Test
	public void menu() {
		Menu menu=new Menu();
		
		assertNotNull(menu);
	}
	
	
	@Test
	public void priceTest() {
		Menu menu=new Menu();
		assertEquals(menu.fries,1);
		assertEquals(menu.coke,2);
		assertEquals(menu.pizza,3);
	}
	
	@Test
	public void orderTest() {
		Menu menu=new Menu();
		List<Integer> order=menu.createOrder("pizza","fries");
		
		assertNotNull(order);
		
	}
}
