package com.coinbase.exchange.api.marketdata;

import com.coinbase.exchange.api.exchange.CoinbaseExchange;
import org.springframework.core.ParameterizedTypeReference;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 * Created by robevansuk on 07/02/2017.
 */
public class MarketDataService {

    final CoinbaseExchange exchange;

    public MarketDataService(final CoinbaseExchange exchange) {
        this.exchange = exchange;
    }

    public static final String PRODUCT_ENDPOINT = "/products";

    public MarketData getMarketDataOrderBook(String productId, int level) {
        String marketDataEndpoint = PRODUCT_ENDPOINT + "/" + productId + "/book";
        if (level != 1)
            marketDataEndpoint += "?level=" + level;
        return exchange.get(marketDataEndpoint, new ParameterizedTypeReference<MarketData>() {
        });
    }

    public List<Trade> getTrades(String productId) {
        String tradesEndpoint = PRODUCT_ENDPOINT + "/" + productId + "/trades";
        return exchange.getAsList(tradesEndpoint, new ParameterizedTypeReference<Trade[]>() {
        });
    }


    /**
     * [
     *     [ time, low, high, open, close, volume ],
     *     [ 1415398768, 0.32, 4.2, 0.35, 4.2, 12.3 ],
     * @param productId
     * @param granularity
     * @return
     */
    public List<String[]> getHistory( String productId, int granularity)
    {
        /**
         * The granularity field must be one of the following values: {60, 300, 900, 3600, 21600, 86400}.
         * Otherwise, your request will be rejected.
         * These values correspond to timeslices representing one minute, five minutes, fifteen minutes, one hour, six hours, and one day, respectively
         */
        String marketDataEndpoint = MarketDataService.PRODUCT_ENDPOINT + "/" + productId + "/candles?granularity="+granularity;
        return exchange.getAsList(marketDataEndpoint, new ParameterizedTypeReference<String[][]>() {
        });
    }

    class Quote {

        Instant time;
        BigDecimal low;
        BigDecimal open;
        BigDecimal close;
        BigDecimal high;
        BigDecimal volume;
    }
}
