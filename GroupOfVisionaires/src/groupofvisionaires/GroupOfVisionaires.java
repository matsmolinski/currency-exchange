package groupofvisionaires;

import filereader.DataReader;
import filereader.ReadDataException;
import java.io.File;

public class GroupOfVisionaires {

    private double amount;
    private Currency inputCurrency;
    private Currency outputCurrency;
    private CurrencyMatrix currencyMatrix;

    public CurrencyMatrix getCurrencyMatrix() {
        return currencyMatrix;
    }

    public void setCurrencyMatrix(CurrencyMatrix currencyMatrix) {
        this.currencyMatrix = currencyMatrix;
    }

    public boolean checkArgs(String[] args) {
        try {
            amount = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            System.err.println("ERROR: Value given as amount of money was not read properly. Verify the second argument and try again.");
            return false;
        }
        if (amount <= 0) {
            System.err.println("ERROR: Value given as amount of money is not positive. Verify the second argument and try again.");
            return false;
        }
        if (args.length == 2) {
            return true;
        }
        boolean isCurrency = false;
        for (int i = 0; i < currencyMatrix.getN(); i++) {
            if (args[2].equals(currencyMatrix.getCurrency(i).getShortName())) {
                inputCurrency = new Currency(currencyMatrix.getCurrency(i).getFullName(), currencyMatrix.getCurrency(i).getShortName());
                isCurrency = true;
                break;
            }
        }
        if (!isCurrency) {
            System.err.println("ERROR: The input currency was not recognised. Verify the third argument and try again.");
            return false;
        }
        if (args.length == 4) {
            isCurrency = false;
            for (int i = 0; i < currencyMatrix.getN(); i++) {
                if (args[3].equals(currencyMatrix.getCurrency(i).getShortName())) {
                    outputCurrency = new Currency(currencyMatrix.getCurrency(i).getFullName(), currencyMatrix.getCurrency(i).getShortName());
                    isCurrency = true;
                    break;
                }
            }
            if (!isCurrency) {
                System.err.println("ERROR: The output currency was not recognised. Verify the fourth argument and try again.");
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {

        GroupOfVisionaires gov = new GroupOfVisionaires();
        if (args.length != 2 && args.length != 3 && args.length != 4) {
            System.err.println("ERROR: program needs 2, 3 or 4 arguments to work properly");
            return;
        }
        File dataFile = new File(args[0]);
        if (!dataFile.exists()) {
            System.err.println("ERROR: file " + dataFile.getName() + " was not found. Make sure the file is located in proper place and try again.");
            return;
        }
        if (!dataFile.getName().toLowerCase().endsWith(".txt")) {
            System.err.println("ERROR: file " + dataFile.getName() + " is not proper .txt file. Check if you have chosen right file and try again.”");
            return;
        }

        try {
            gov.currencyMatrix = DataReader.readData(dataFile);
        } catch (ReadDataException e) {
            System.err.println(e.getMessage());
            return;
        }

        ExchangeSearcher exchangeSearcher = new ExchangeSearcher();
        if (gov.checkArgs(args)) {
            switch (args.length) {
                case 2:
                    String finalResult = "No economic arbitration found";
                    for (int i = 0; i < gov.currencyMatrix.getN(); i++) {
                        String result = exchangeSearcher.SearchForArbitrage(gov.amount, gov.currencyMatrix, gov.currencyMatrix.getCurrency(i));
                        if (!result.equals("No economic arbitration found")) {
                            finalResult = result;
                            break;
                        }
                    }
                    System.out.println(finalResult);
                    break;
                case 3:
                    System.out.println(exchangeSearcher.SearchForArbitrage(gov.amount, gov.currencyMatrix, gov.inputCurrency));
                    break;
                default:
                    System.out.println(exchangeSearcher.SearchForBestExchange(gov.amount, gov.currencyMatrix, gov.inputCurrency, gov.outputCurrency));
                    break;
            }
        }
    }
}
