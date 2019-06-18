package filereader;

import groupofvisionaires.CurrencyMatrix;
import groupofvisionaires.FeeType;
import java.io.File;
import org.junit.Test;
import static org.junit.Assert.*;

public class DataReaderTest {

    @Test
    public void testReadDataGood() throws ReadDataException {
        File dataFile = new File("data1.txt");
        CurrencyMatrix result = DataReader.readData(dataFile);
        assertEquals(2, result.getN());
        assertEquals("EUR", result.getCurrency(0).getShortName());
        assertEquals("PLN", result.getCurrency(1).getShortName());
        assertEquals("Euro", result.getCurrency(0).getFullName());
        assertEquals("Złoty", result.getCurrency(1).getFullName());
        assertNotNull(result.getExchangeRate(0, 1));
        assertEquals(0.8889, result.getExchangeRate(0, 1).getRate(), 1e-8);
        assertEquals(FeeType.PERCENTAGE, result.getExchangeRate(0, 1).getTypeOfFee());
        assertEquals(0.0001, result.getExchangeRate(0, 1).getValueOfFee(), 1e-8);
    }

    @Test(expected = ReadDataException.class)
    public void testReadDataNoSeparations() throws ReadDataException {
        File dataFile = new File("data2.txt");
        CurrencyMatrix result = DataReader.readData(dataFile);
    }

    @Test(expected = ReadDataException.class)
    public void testReadDataOneSeparation() throws ReadDataException {
        File dataFile = new File("data3.txt");
        CurrencyMatrix result = DataReader.readData(dataFile);
    }
    
    @Test(expected = ReadDataException.class)
    public void testReadDataEmptyFile() throws ReadDataException {
        File dataFile = new File("emptyFile.txt");
        CurrencyMatrix result = DataReader.readData(dataFile);
    }
    
    @Test
    public void testReadDataWrongCurrency() throws ReadDataException {
        File dataFile = new File("data4.txt");
        CurrencyMatrix result = DataReader.readData(dataFile);
        assertEquals(1, result.getN());
        assertEquals("EUR", result.getCurrency(0).getShortName());
    }
    
    @Test
    public void testReadDataDoubledRate() throws ReadDataException {
        File dataFile = new File("data5.txt");
        CurrencyMatrix result = DataReader.readData(dataFile);
        assertEquals(2, result.getN());
        assertEquals("EUR", result.getCurrency(0).getShortName());
        assertEquals("PLN", result.getCurrency(1).getShortName());
        assertEquals("Euro", result.getCurrency(0).getFullName());
        assertEquals("Złoty", result.getCurrency(1).getFullName());
        assertNotNull(result.getExchangeRate(0, 1));
        assertEquals(0.8889, result.getExchangeRate(0, 1).getRate(), 1e-8);
        assertEquals(FeeType.PERCENTAGE, result.getExchangeRate(0, 1).getTypeOfFee());
        assertEquals(0.0001, result.getExchangeRate(0, 1).getValueOfFee(), 1e-8);
    }
    
    @Test
    public void testReadDataDoubledCurrency() throws ReadDataException {
        File dataFile = new File("data6.txt");
        CurrencyMatrix result = DataReader.readData(dataFile);
        assertEquals(2, result.getN());
        assertEquals("EUR", result.getCurrency(0).getShortName());
        assertEquals("PLN", result.getCurrency(1).getShortName());
        assertEquals("Euro", result.getCurrency(0).getFullName());
        assertEquals("Złoty", result.getCurrency(1).getFullName());
        assertNull(result.getExchangeRate(0, 1));
    }
    
    @Test
    public void testReadDataWrongValue() throws ReadDataException {
        File dataFile = new File("data7.txt");
        CurrencyMatrix result = DataReader.readData(dataFile);
        assertEquals(2, result.getN());
        assertEquals("EUR", result.getCurrency(0).getShortName());
        assertEquals("PLN", result.getCurrency(1).getShortName());
        assertEquals("Euro", result.getCurrency(0).getFullName());
        assertEquals("Złoty", result.getCurrency(1).getFullName());
        assertNull(result.getExchangeRate(0, 1));
    }
    
    @Test
    public void testReadDataEmptyLines() throws ReadDataException {
        File dataFile = new File("data8.txt");
        CurrencyMatrix result = DataReader.readData(dataFile);
        assertEquals(2, result.getN());
        assertEquals("EUR", result.getCurrency(0).getShortName());
        assertEquals("PLN", result.getCurrency(1).getShortName());
        assertEquals("Euro", result.getCurrency(0).getFullName());
        assertEquals("Złoty", result.getCurrency(1).getFullName());
        assertNotNull(result.getExchangeRate(0, 1));
        assertEquals(0.8889, result.getExchangeRate(0, 1).getRate(), 1e-8);
        assertEquals(FeeType.PERCENTAGE, result.getExchangeRate(0, 1).getTypeOfFee());
        assertEquals(0.0001, result.getExchangeRate(0, 1).getValueOfFee(), 1e-8);
    }
}
