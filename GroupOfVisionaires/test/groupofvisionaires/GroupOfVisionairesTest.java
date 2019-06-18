package groupofvisionaires;

import org.junit.Test;
import static org.junit.Assert.*;

public class GroupOfVisionairesTest {

    @Test
    public void testCheckArgsProperFourArgs() {
        String[] args = {"dane.txt", "1000", "PLN", "EUR"};
        GroupOfVisionaires gov = new GroupOfVisionaires();
        CurrencyMatrix cm = new CurrencyMatrix(2);
        Currency zl = new Currency("Zloty", "PLN");
        Currency eu = new Currency("Euro", "EUR");
        cm.setCurrency(0, zl);
        cm.setCurrency(1, eu);
        gov.setCurrencyMatrix(cm);
        boolean result = gov.checkArgs(args);
        assertTrue(result);
    }

    @Test
    public void testCheckArgsProperThreeArgs() {
        String[] args = {"dane.txt", "1000", "PLN"};
        GroupOfVisionaires gov = new GroupOfVisionaires();
        CurrencyMatrix cm = new CurrencyMatrix(2);
        Currency zl = new Currency("Zloty", "PLN");
        Currency eu = new Currency("Euro", "EUR");
        cm.setCurrency(0, zl);
        cm.setCurrency(1, eu);
        gov.setCurrencyMatrix(cm);
        boolean result = gov.checkArgs(args);
        assertTrue(result);
    }
    
    @Test
    public void testCheckArgsProperTwoArgs() {
        String[] args = {"dane.txt", "1000"};
        GroupOfVisionaires gov = new GroupOfVisionaires();
        CurrencyMatrix cm = new CurrencyMatrix(2);
        Currency zl = new Currency("Zloty", "PLN");
        Currency eu = new Currency("Euro", "EUR");
        cm.setCurrency(0, zl);
        cm.setCurrency(1, eu);
        gov.setCurrencyMatrix(cm);
        boolean result = gov.checkArgs(args);
        assertTrue(result);
    }
    
    @Test
    public void testCheckArgsWrongNumberFourArgs() {
        String[] args = {"dane.txt", "10o0", "PLN", "EUR"};
        GroupOfVisionaires gov = new GroupOfVisionaires();
        CurrencyMatrix cm = new CurrencyMatrix(2);
        Currency zl = new Currency("Zloty", "PLN");
        Currency eu = new Currency("Euro", "EUR");
        cm.setCurrency(0, zl);
        cm.setCurrency(1, eu);
        gov.setCurrencyMatrix(cm);
        boolean result = gov.checkArgs(args);
        assertFalse(result);
    }
    
    @Test
    public void testCheckArgsWrongNumberTwoArgs() {
        String[] args = {"dane.txt", "10o0"};
        GroupOfVisionaires gov = new GroupOfVisionaires();
        CurrencyMatrix cm = new CurrencyMatrix(2);
        Currency zl = new Currency("Zloty", "PLN");
        Currency eu = new Currency("Euro", "EUR");
        cm.setCurrency(0, zl);
        cm.setCurrency(1, eu);
        gov.setCurrencyMatrix(cm);
        boolean result = gov.checkArgs(args);
        assertFalse(result);
    }
    
    @Test
    public void testCheckArgsWrongInCurrencyFourArgs() {
        String[] args = {"dane.txt", "10o0", "CZK", "EUR"};
        GroupOfVisionaires gov = new GroupOfVisionaires();
        CurrencyMatrix cm = new CurrencyMatrix(2);
        Currency zl = new Currency("Zloty", "PLN");
        Currency eu = new Currency("Euro", "EUR");
        cm.setCurrency(0, zl);
        cm.setCurrency(1, eu);
        gov.setCurrencyMatrix(cm);
        boolean result = gov.checkArgs(args);
        assertFalse(result);
    }
    
    @Test
    public void testCheckArgsWrongInCurrencyThreeArgs() {
        String[] args = {"dane.txt", "10o0", "CZK"};
        GroupOfVisionaires gov = new GroupOfVisionaires();
        CurrencyMatrix cm = new CurrencyMatrix(2);
        Currency zl = new Currency("Zloty", "PLN");
        Currency eu = new Currency("Euro", "EUR");
        cm.setCurrency(0, zl);
        cm.setCurrency(1, eu);
        gov.setCurrencyMatrix(cm);
        boolean result = gov.checkArgs(args);
        assertFalse(result);
    }
    
    @Test
    public void testCheckArgsWrongOutCurrencyFourArgs() {
        String[] args = {"dane.txt", "10o0", "PLN", "CZK"};
        GroupOfVisionaires gov = new GroupOfVisionaires();
        CurrencyMatrix cm = new CurrencyMatrix(2);
        Currency zl = new Currency("Zloty", "PLN");
        Currency eu = new Currency("Euro", "EUR");
        cm.setCurrency(0, zl);
        cm.setCurrency(1, eu);
        gov.setCurrencyMatrix(cm);
        boolean result = gov.checkArgs(args);
        assertFalse(result);
    }
}
