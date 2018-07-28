package cucumber;

import java.util.ArrayList;
import java.util.List;

public class Menu {

	public int fries=1;
	public int pizza=3;
	public int coke=2;
	
	public List<Integer> createOrder(String string, String string2) {
	
		List<Integer> order= new ArrayList();
		order.add(fries);
		order.add(coke);
		order.add(pizza);
		
		return order;
	}
}
