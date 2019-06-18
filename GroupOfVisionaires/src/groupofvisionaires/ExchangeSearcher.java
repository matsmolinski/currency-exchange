package groupofvisionaires;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ExchangeSearcher {

    public String SearchForArbitrage(double amount, CurrencyMatrix cm, Currency currency) {
        Integer currIndex = null;
        double bestFinalAmount = amount;
        List<Integer> bestExchange = null;
        for (int i = 0; i < cm.getN(); i++) {
            if (cm.getCurrency(i).getShortName().equals(currency.getShortName())) {
                currIndex = i;
                break;
            }
        }
        LinkedList<List<Integer>> queue = new LinkedList<>();
        List<Integer> currentList = new ArrayList<>();
        currentList.add(currIndex);
        queue.add(currentList);

        while (!queue.isEmpty()) {
            currentList = queue.poll();
            Integer currentCurrIndex = currentList.get(currentList.size() - 1);
            if (Objects.equals(currentCurrIndex, currIndex) && currentList.size() > 1) {
                double exchangedAmount = amount;
                for (int i = 1; i < currentList.size(); i++) {
                    ExchangeRate rate = cm.getExchangeRate(currentList.get(i - 1), currentList.get(i));
                    if (rate.getTypeOfFee() == FeeType.FIXED) {
                        exchangedAmount = exchangedAmount * rate.getRate() - rate.getValueOfFee();
                    } else {
                        exchangedAmount = exchangedAmount * rate.getRate();
                        exchangedAmount -= exchangedAmount * rate.getValueOfFee();
                    }
                }
                if (exchangedAmount > bestFinalAmount) {
                    bestFinalAmount = exchangedAmount;
                    bestExchange = currentList;
                }
            } else {
                for (int i = 0; i < cm.getN(); i++) {
                    if (cm.getExchangeRate(currentCurrIndex, i) != null) {
                        boolean isVisited = false;
                        for (Integer index : currentList) {
                            if (index == i && !Objects.equals(index, currIndex)) {
                                isVisited = true;
                            }
                        }
                        if (!isVisited) {
                            List<Integer> newList = new ArrayList<>();
                            newList.addAll(currentList);
                            newList.add(i);
                            queue.add(newList);
                        }
                    }
                }
            }
        }

        if (bestExchange == null) {
            return "No economic arbitration found";
        } else {
            StringBuilder answer = new StringBuilder("");
            answer.append(amount).append(" ").append(currency.getShortName()).append(" -> ");
            for (int i = 1; i < bestExchange.size(); i++) {
                answer.append(cm.getCurrency(bestExchange.get(i)).getShortName()).append(" -> ");
            }
            bestFinalAmount *= 100;
            bestFinalAmount = Math.round(bestFinalAmount);
            bestFinalAmount /= 100;
            answer.append(bestFinalAmount).append(" ").append(currency.getShortName());
            return answer.toString();
        }
    }

    public String SearchForBestExchange(double amount, CurrencyMatrix cm, Currency inputCurrency, Currency outputCurrency) {
        Integer inputCurrIndex = null, outputCurrIndex = null;
        double bestFinalAmount = 0;
        List<Integer> bestExchange = null;
        for (int i = 0; i < cm.getN(); i++) {
            if (cm.getCurrency(i).getShortName().equals(inputCurrency.getShortName())) {
                inputCurrIndex = i;
                break;
            }
        }
        for (int i = 0; i < cm.getN(); i++) {
            if (cm.getCurrency(i).getShortName().equals(outputCurrency.getShortName())) {
                outputCurrIndex = i;
                break;
            }
        }
        LinkedList<List<Integer>> queue = new LinkedList<>();
        List<Integer> currentList = new ArrayList<>();
        currentList.add(inputCurrIndex);
        queue.add(currentList);

        while (!queue.isEmpty()) {
            currentList = queue.poll();
            Integer currentCurrIndex = currentList.get(currentList.size() - 1);
            if (Objects.equals(currentCurrIndex, outputCurrIndex)) {
                double exchangedAmount = amount;
                for (int i = 1; i < currentList.size(); i++) {
                    ExchangeRate rate = cm.getExchangeRate(currentList.get(i - 1), currentList.get(i));
                    if (rate.getTypeOfFee() == FeeType.FIXED) {
                        exchangedAmount = exchangedAmount * rate.getRate() - rate.getValueOfFee();
                    } else {
                        exchangedAmount = exchangedAmount * rate.getRate();
                        exchangedAmount -= exchangedAmount * rate.getValueOfFee();
                    }
                }
                if (exchangedAmount > bestFinalAmount) {
                    bestFinalAmount = exchangedAmount;
                    bestExchange = currentList;
                }
            } else {
                for (int i = 0; i < cm.getN(); i++) {
                    if (cm.getExchangeRate(currentCurrIndex, i) != null) {
                        boolean isVisited = false;
                        for (Integer index : currentList) {
                            if (index == i) {
                                isVisited = true;
                            }
                        }
                        if (!isVisited) {
                            List<Integer> newList = new ArrayList<>();
                            newList.addAll(currentList);
                            newList.add(i);
                            queue.add(newList);
                        }
                    }
                }
            }
        }

        if (bestExchange == null) {
            return "Exchange is not possible";
        } else {
            StringBuilder answer = new StringBuilder("");
            answer.append(amount).append(" ").append(inputCurrency.getShortName()).append(" -> ");
            for (int i = 1; i < bestExchange.size(); i++) {
                answer.append(cm.getCurrency(bestExchange.get(i)).getShortName()).append(" -> ");
            }
            bestFinalAmount *= 100;
            bestFinalAmount = Math.round(bestFinalAmount);
            bestFinalAmount /= 100;
            answer.append(bestFinalAmount).append(" ").append(outputCurrency.getShortName());
            return answer.toString();
        }
    }
}
