package filereader;

import groupofvisionaires.Currency;
import groupofvisionaires.CurrencyMatrix;
import groupofvisionaires.ExchangeRate;
import groupofvisionaires.FeeType;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DataReader {

    private enum ReaderState {
        START, CURRENCIES, RATES
    }

    private static Currency parseCurrency(String line, int lineCounter) throws ParseLineException {
        int lineIndex = 0;
        while (lineIndex + 1 < line.length() && line.charAt(lineIndex) >= '0' && line.charAt(lineIndex) <= '9') {
            lineIndex++;
        }
        try {
            Integer.parseInt(line.substring(0, lineIndex));
        } catch (NumberFormatException ex) {
            throw new ParseLineException("Warning: Data file contains invalid ID in " + lineCounter + " line. It will be ignored by program.");
        }
        String sCurrency = "";
        while (lineIndex + 1 < line.length() && (line.charAt(++lineIndex) >= 'A' && line.charAt(lineIndex) <= 'Z' || line.charAt(lineIndex) >= 'a' && line.charAt(lineIndex) <= 'z')) {
            sCurrency += line.charAt(lineIndex);
        }
        if (sCurrency.length() != 3) {
            throw new ParseLineException("Warning: Data file contains invalid currency shortcut in " + lineCounter + " line. It will be ignored by program.");
        }
        Currency currency = new Currency(line.substring(++lineIndex), sCurrency);
        if (currency.getFullName().equals("")) {
            throw new ParseLineException("Warning: Data file contains invalid currency name in " + lineCounter + " line. It will be ignored by program.");
        }
        return currency;
    }

    private static void parseRate(String line, int lineCounter, CurrencyMatrix currencyMatrix) throws ParseLineException {
        int lineIndex = 0;
        while (lineIndex + 1 < line.length() && line.charAt(lineIndex) >= '0' && line.charAt(lineIndex) <= '9') {
            lineIndex++;
        }
        try {
            Integer.parseInt(line.substring(0, lineIndex));
        } catch (NumberFormatException ex) {
            throw new ParseLineException("Warning: Data file contains invalid ID in " + lineCounter + " line. It will be ignored by program.");
        }
        ExchangeRate exchangeRate;
        String currencyIn = "";
        while (lineIndex + 1 < line.length() && (line.charAt(++lineIndex) >= 'A' && line.charAt(lineIndex) <= 'Z' || line.charAt(lineIndex) >= 'a' && line.charAt(lineIndex) <= 'z')) {
            currencyIn += line.charAt(lineIndex);
        }
        if (currencyIn.length() != 3) {
            System.out.println(currencyIn);
            throw new ParseLineException("Warning: Data file contains invalid currency shortcut in " + lineCounter + " line. It will be ignored by program.");
        }
        String currencyOut = "";
        while (lineIndex + 1 < line.length() && (line.charAt(++lineIndex) >= 'A' && line.charAt(lineIndex) <= 'Z' || line.charAt(lineIndex) >= 'a' && line.charAt(lineIndex) <= 'z')) {
            currencyOut += line.charAt(lineIndex);
        }
        if (currencyOut.length() != 3) {
            throw new ParseLineException("Warning: Data file contains invalid currency shortcut in " + lineCounter + " line. It will be ignored by program.");
        }
        String sValue = "";
        while (lineIndex + 1 < line.length() && line.charAt(++lineIndex) != ' ') {
            sValue += line.charAt(lineIndex);
        }
        try {
            double dValue = Double.parseDouble(sValue);
            String sFeeType = "";
            while (lineIndex + 1 < line.length() && line.charAt(++lineIndex) != ' ') {
                sFeeType += line.charAt(lineIndex);
            }
            if (sFeeType.equals("PROC")) {
                exchangeRate = new ExchangeRate(dValue, FeeType.PERCENTAGE);
            } else if (sFeeType.equals("STALA") || sFeeType.equals("STAŁA")) {
                exchangeRate = new ExchangeRate(dValue, FeeType.FIXED);
            } else {
                throw new ParseLineException("Warning: Data file contains invalid fee type in " + lineCounter + " line. It will be ignored by program.");
            }
            sValue = line.substring(lineIndex);
            dValue = Double.parseDouble(sValue);
            if ((dValue < 0 || dValue > 1) && sFeeType.equals("PROC")) {
                throw new ParseLineException("Warning: Data file contains invalid fee value in " + lineCounter + " line. It will be ignored by program.");
            }
            exchangeRate.setValueOfFee(dValue);
        } catch (NumberFormatException ex) {
            throw new ParseLineException("Warning: Data file contains invalid value in " + lineCounter + " line. It will be ignored by program.");
        }
        int IDIn = -1, IDOut = -1;
        for (int i = 0; i < currencyMatrix.getN(); i++) {
            if (currencyMatrix.getCurrency(i).getShortName().equals(currencyIn)) {
                IDIn = i;
            }
            if (currencyMatrix.getCurrency(i).getShortName().equals(currencyOut)) {
                IDOut = i;
            }
        }
        if (IDIn == -1 || IDOut == -1) {
            throw new ParseLineException("Warning: Data file contains unrecognised currency shortcut in " + lineCounter + " line. It will be ignored by program.");
        }
        if (IDIn == IDOut) {
            throw new ParseLineException("Warning: Data file contains rate with input == output in " + lineCounter + " line. It will be ignored by program.");
        }
        if (currencyMatrix.getExchangeRate(IDIn, IDOut) != null) {
            throw new ParseLineException("Warning: Data file contains repeating rate in " + lineCounter + " line. It will be ignored by program.");
        }
        currencyMatrix.setExchangeRate(IDIn, IDOut, exchangeRate);
    }

    public static CurrencyMatrix readData(File dataFile) throws ReadDataException {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(dataFile), "Cp1250"));
            int lineCounter = 0;
            ReaderState state = ReaderState.START;
            List<Currency> currencies = new ArrayList<>();
            CurrencyMatrix currencyMatrix = null;
            String line = br.readLine();
            OUTER:
            do {
                lineCounter++;
                switch (state) {
                    case START:
                        if (line != null && line.charAt(0) == '#') {
                            state = ReaderState.CURRENCIES;
                        } else {
                            throw new ReadDataException("ERROR: Data file starts in unrecognizable way. Make sure that you have attached proper file and try again");
                        }
                        break;
                    case CURRENCIES:
                        if (line.length() > 0 && line.charAt(0) == '#') {
                            state = ReaderState.RATES;
                            currencyMatrix = new CurrencyMatrix(currencies.size());
                            for (int i = 0; i < currencies.size(); i++) {
                                currencyMatrix.setCurrency(i, currencies.get(i));
                            }
                        } else {
                            try {
                                currencies.add(parseCurrency(line, lineCounter));
                            } catch (ParseLineException ex) {
                                System.err.println(ex.getMessage());
                            }
                        }
                        break;
                    case RATES:
                        if (line.length() > 0 && line.charAt(0) == '#') {
                            System.err.println("Warning: Data file contains more than 2 separating lines. Everything below the third will be ignored.");
                            break OUTER;
                        } else {
                            try {
                                parseRate(line, lineCounter, currencyMatrix);
                            } catch (ParseLineException ex) {
                                System.err.println(ex.getMessage());
                            }
                        }
                        break;
                    default:
                        break;
                }
                line = br.readLine();
            } while (line != null);
            if (state != ReaderState.RATES) {
                throw new ReadDataException("ERROR: Data file does not contain enough separating lines. Make sure that you have attached proper file and try again");
            }
            return currencyMatrix;
        } catch (FileNotFoundException ex) {
            throw new ReadDataException("ERROR: file " + dataFile.getName() + " was not found. Make sure the file is located in proper place and try again.");
        } catch (IOException ex) {
            throw new ReadDataException("ERROR: Cannot get a lines from file " + dataFile.getName() + ". Make sure the file is in proper format and try again.");
        }
    }

}
