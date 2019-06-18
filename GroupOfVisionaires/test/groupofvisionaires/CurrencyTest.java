package groupofvisionaires;

import org.junit.Test;
import static org.junit.Assert.*;

public class CurrencyTest {
    
    @Test
    public void testConstructorAndGet() {
        Currency instance = new Currency("Zloty", "PLN");
        String expected1 = "Zloty";
        String result1 = instance.getFullName();
        String expected2 = "PLN";
        String result2 = instance.getShortName();
        assertEquals(expected1, result1);
        assertEquals(expected2, result2);
    }
    
    @Test
    public void testSetAndGetFullName() {
        Currency instance = new Currency("Zloty", "PLN");
        instance.setFullName("Zloty Polski");
        String expected = "Zloty Polski";
        String result = instance.getFullName();
        assertEquals(expected, result);
    }

    @Test
    public void testSetAndGetShortName() {
        Currency instance = new Currency("Zloty", "PLN");
        instance.setShortName("ZLP");
        String expected = "ZLP";
        String result = instance.getShortName();
        assertEquals(expected, result);
    }

    
}
