package groupofvisionaires;

import org.junit.Test;
import static org.junit.Assert.*;

public class CurrencyMatrixTest {

    @Test
    public void testGetAndSetCurrency() {
        int currencyID = 0;
        Currency currency = new Currency("Zloty", "PLN");
        CurrencyMatrix instance = new CurrencyMatrix(3);
        instance.setCurrency(currencyID, currency);
        Currency expResult = currency;
        Currency result = instance.getCurrency(currencyID);
        assertEquals(expResult, result);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testSetCurrencyOutOfBounds() {
        int currencyID = 3;
        Currency currency = null;
        CurrencyMatrix instance = new CurrencyMatrix(2);
        instance.setCurrency(currencyID, currency);
    }

    @Test
    public void testSetAndGetExchangeRate() {
        int currencyIDIn = 0;
        int currencyIDOut = 0;
        ExchangeRate rate = new ExchangeRate(0, FeeType.FIXED);
        CurrencyMatrix instance = new CurrencyMatrix(3);
        instance.setExchangeRate(currencyIDIn, currencyIDOut, rate);
        ExchangeRate expResult = rate;
        ExchangeRate result = instance.getExchangeRate(currencyIDIn, currencyIDOut);
        assertEquals(expResult, result);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testSetExchangeRateOutOfBounds() {
        int currencyIDIn = 3;
        int currencyIDOut = 0;
        ExchangeRate exchangeRate = null;
        CurrencyMatrix instance = new CurrencyMatrix(2);
        instance.setExchangeRate(currencyIDIn, currencyIDOut, exchangeRate);
    }

    @Test
    public void testGetN() {
        CurrencyMatrix instance = new CurrencyMatrix(4);
        int expResult = 4;
        int result = instance.getN();
        assertEquals(expResult, result);
    }

}
