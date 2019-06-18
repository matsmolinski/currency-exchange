package groupofvisionaires;

import org.junit.Test;
import static org.junit.Assert.*;

public class ExchangeRateTest {

    @Test
    public void testConstructorAndGet() {
        ExchangeRate instance = new ExchangeRate(0.989, FeeType.FIXED, 0.2);
        assertEquals(0.989, instance.getRate(), 0.001);
        assertEquals(0.2, instance.getValueOfFee(), 0.001);
        assertEquals(FeeType.FIXED, instance.getTypeOfFee());
    }

    @Test
    public void testSetAndGetRate() {
        ExchangeRate instance = new ExchangeRate(0.989, FeeType.FIXED, 0.2);
        instance.setRate(0);
        assertEquals(0, instance.getRate(), 0.001);
    }

    @Test
    public void testGetAndGetTypeOfFee() {
        ExchangeRate instance = new ExchangeRate(0.989, FeeType.FIXED, 0.2);
        instance.setTypeOfFee(FeeType.PERCENTAGE);
        assertEquals(FeeType.PERCENTAGE, instance.getTypeOfFee());
    }

    @Test
    public void testSetAndGetValueOfFee() {
        ExchangeRate instance = new ExchangeRate(0.989, FeeType.FIXED, 0.2);
        instance.setValueOfFee(0);
        assertEquals(0, instance.getValueOfFee(), 0.001);
    }

}
