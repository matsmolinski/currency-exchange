package groupofvisionaires;

import org.junit.Test;
import static org.junit.Assert.*;

public class ExchangeSearcherTest {
    
    @Test
    public void testSearchForArbitrageNoArbitrage() {
        CurrencyMatrix cm = new CurrencyMatrix(2);
        Currency currency = new Currency ("Zloty", "PLN");
        cm.setCurrency(0, currency);
        cm.setCurrency(1, new Currency("Euro", "EUR"));
        ExchangeSearcher instance = new ExchangeSearcher();
        String expResult = "No economic arbitration found";
        String result = instance.SearchForArbitrage(1000, cm, currency);
        assertEquals(expResult, result);
    }

    @Test
    public void testSearchForArbitrageSingleArbitrage() {
        CurrencyMatrix cm = new CurrencyMatrix(2);
        Currency currency = new Currency ("Zloty", "PLN");
        cm.setCurrency(0, currency);
        cm.setCurrency(1, new Currency("Euro", "EUR"));
        cm.setExchangeRate(0, 1, new ExchangeRate(1.1, FeeType.FIXED, 0));
        cm.setExchangeRate(1, 0, new ExchangeRate(1.1, FeeType.FIXED, 0));
        ExchangeSearcher instance = new ExchangeSearcher();
        String expResult = "1000.0 PLN -> EUR -> PLN -> 1210.0 PLN";
        String result = instance.SearchForArbitrage(1000, cm, currency);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testSearchForArbitrageSingleBadArbitrage() {
        CurrencyMatrix cm = new CurrencyMatrix(2);
        Currency currency = new Currency ("Zloty", "PLN");
        cm.setCurrency(0, currency);
        cm.setCurrency(1, new Currency("Euro", "EUR"));
        cm.setExchangeRate(0, 1, new ExchangeRate(0.9, FeeType.FIXED, 0));
        cm.setExchangeRate(1, 0, new ExchangeRate(0.9, FeeType.FIXED, 0));
        ExchangeSearcher instance = new ExchangeSearcher();
        String expResult = "No economic arbitration found";
        String result = instance.SearchForArbitrage(1000, cm, currency);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testSearchForForArbitrageTwoArbitrages() {
        CurrencyMatrix cm = new CurrencyMatrix(3);
        Currency zloty = new Currency ("Zloty", "PLN");
        Currency euro = new Currency("Euro", "EUR");
        Currency pound = new Currency("Pound", "GBP");
        cm.setCurrency(0, zloty);
        cm.setCurrency(1, euro);
        cm.setCurrency(2, pound);
        cm.setExchangeRate(0, 1, new ExchangeRate(1.1, FeeType.FIXED, 0));
        cm.setExchangeRate(1, 0, new ExchangeRate(1.1, FeeType.FIXED, 0));
        cm.setExchangeRate(0, 2, new ExchangeRate(1.1, FeeType.FIXED, 0));
        cm.setExchangeRate(2, 0, new ExchangeRate(1.2, FeeType.FIXED, 0));
        ExchangeSearcher instance = new ExchangeSearcher();
        String expResult = "1000.0 PLN -> GBP -> PLN -> 1320.0 PLN";
        String result = instance.SearchForArbitrage(1000, cm, zloty);
        assertEquals(expResult, result);
    }
    

    @Test
    public void testSearchForBestExchangeNoExchange() {
        CurrencyMatrix cm = new CurrencyMatrix(2);
        Currency zloty = new Currency ("Zloty", "PLN");
        Currency euro = new Currency("Euro", "EUR");
        cm.setCurrency(0, zloty);
        cm.setCurrency(1, euro);
        ExchangeSearcher instance = new ExchangeSearcher();
        String expResult = "Exchange is not possible";
        String result = instance.SearchForBestExchange(1000, cm, zloty, euro);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testSearchForBestExchangeOneExchange() {
        CurrencyMatrix cm = new CurrencyMatrix(2);
        Currency zloty = new Currency ("Zloty", "PLN");
        Currency euro = new Currency("Euro", "EUR");
        cm.setCurrency(0, zloty);
        cm.setCurrency(1, euro);
        cm.setExchangeRate(0, 1, new ExchangeRate(0.9, FeeType.FIXED, 0));
        cm.setExchangeRate(1, 0, new ExchangeRate(0.9, FeeType.FIXED, 0));
        ExchangeSearcher instance = new ExchangeSearcher();
        String expResult = "1000.0 PLN -> EUR -> 900.0 EUR";
        String result = instance.SearchForBestExchange(1000, cm, zloty, euro);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testSearchForBestExchangeTwoExchanges() {
        CurrencyMatrix cm = new CurrencyMatrix(3);
        Currency zloty = new Currency ("Zloty", "PLN");
        Currency euro = new Currency("Euro", "EUR");
        Currency pound = new Currency("Pound", "GBP");
        cm.setCurrency(0, zloty);
        cm.setCurrency(1, euro);
        cm.setCurrency(2, pound);
        cm.setExchangeRate(0, 1, new ExchangeRate(1.1, FeeType.FIXED, 0));
        cm.setExchangeRate(1, 2, new ExchangeRate(1.1, FeeType.FIXED, 0));
        cm.setExchangeRate(0, 2, new ExchangeRate(1.1, FeeType.FIXED, 0));
        ExchangeSearcher instance = new ExchangeSearcher();
        String expResult = "1000.0 PLN -> EUR -> GBP -> 1210.0 GBP";
        String result = instance.SearchForBestExchange(1000, cm, zloty, pound);
        assertEquals(expResult, result);
    }
}
