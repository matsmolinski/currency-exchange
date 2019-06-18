package groupofvisionaires;

public class ExchangeRate {

    private double rate;
    private FeeType typeOfFee;
    private double valueOfFee;

    public ExchangeRate(double rate, FeeType typeOfFee, double valueOfFee) {
        this.rate = rate;
        this.typeOfFee = typeOfFee;
        this.valueOfFee = valueOfFee;
    }

    public ExchangeRate(double rate, FeeType typeOfFee) {
        this.rate = rate;
        this.typeOfFee = typeOfFee;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public FeeType getTypeOfFee() {
        return typeOfFee;
    }

    public void setTypeOfFee(FeeType typeOfFee) {
        this.typeOfFee = typeOfFee;
    }

    public double getValueOfFee() {
        return valueOfFee;
    }

    public void setValueOfFee(double valueOfFee) {
        this.valueOfFee = valueOfFee;
    }

}
