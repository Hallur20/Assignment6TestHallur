
import java.util.ArrayList;
import java.util.List;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyObject;

@RunWith(MockitoJUnitRunner.class)
public class OrderTests {

    @Mock
    Customer c1, c2;

    @Mock
    Employee e;

    @Test
    public void bla() {
        ArrayList<Order> orders = new ArrayList<>(); //list for pizza orders
        Order margeritaOrder = new Order(1, "Margerita", "cheese and tomato", 50); //option 1
        Order pepperoniOrder = new Order(2, "Pepperoni", "cheese, tomato and pepperoni", 62); //option 2
        when(c1.makeOrder()).thenReturn(margeritaOrder); //when makeOrder is called with c1, return a margerita order...
        when(c2.makeOrder()).thenReturn(pepperoniOrder); //when makeOrder is called with c2, return a pepperoni order...

        orders.add(c1.makeOrder()); //calling makeOrder with c1, and adds the pizza to the order list...
        orders.add(c2.makeOrder()); //calling makeOrder with c2, and adds the pizza to the order list...

        Mockito.doAnswer((customer) -> {
            if (customer.getArgument(0).equals(c1)) { //if employee wants to remove order from c1, remove it from list...
                System.out.println("removing margerita order..."); 
                orders.remove(margeritaOrder);
            }
            if (customer.getArgument(0).equals(c2)) { //if customer wants to remove order from c2, remove it from list...
                System.out.println("removing pepperoni order...");
                orders.remove(pepperoniOrder);
            }
            return null;
        }).when(e).deleteOrder(anyObject());
        
        verify(c1).makeOrder(); //verify c1 has made an order
        verify(c2).makeOrder(); //verify c2 has made an order
        
        Assert.assertThat(orders.size(), CoreMatchers.equalTo(2)); //assert that there are 2 pizzas in the list
        e.deleteOrder(c1); //delete the order from cusomter 1...
        Assert.assertThat(orders.size(), CoreMatchers.equalTo(1)); //assert that there is 1 pizza in the list
    }
}
