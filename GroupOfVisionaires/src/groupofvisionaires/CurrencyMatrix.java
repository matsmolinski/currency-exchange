package groupofvisionaires;

public class CurrencyMatrix {

    private final int n;
    private final Currency[] currencies;
    private final ExchangeRate[][] matrix;

    public CurrencyMatrix(int n) {
        this.n = n;
        currencies = new Currency[n];
        matrix = new ExchangeRate[n][];
        for (int i = 0; i < n; i++) {
            matrix[i] = new ExchangeRate[n];
            for (int j = 0; j < n; j++) {
                matrix[i][j] = null;
            }
        }
    }

    public Currency getCurrency(int currencyID) {
        return currencies[currencyID];
    }

    public void setCurrency(int currencyID, Currency currency) {
        this.currencies[currencyID] = currency;
    }

    public ExchangeRate getExchangeRate(int currencyIDIn, int currencyIDOut) {
        return matrix[currencyIDIn][currencyIDOut];
    }

    public void setExchangeRate(int currencyIDIn, int currencyIDOut, ExchangeRate exchangeRate) {
        this.matrix[currencyIDIn][currencyIDOut] = exchangeRate;
    }

    public int getN() {
        return n;
    }

}
