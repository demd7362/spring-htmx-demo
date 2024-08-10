package com.api.sample.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

@Getter
@Setter
public class SubwayResponse {
    private int currentCount;
    private int matchCount;
    private int page;
    private int perPage;
    private int totalCount;

    private List<Data> data;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface Order {
        int value();
    }
    @Getter
    @Setter
    public static class Data {
        @JsonProperty("00시00분")
        @Order(1)
        private String zeroOClock;

        @JsonProperty("6시00분")
        private String sixOClock;

        @JsonProperty("7시00분")
        private String sevenOClock;

        @JsonProperty("8시00분")
        private String eightOClock;

        @JsonProperty("9시00분")
        private String nineOClock;

        @JsonProperty("10시00분")
        private String tenOClock;

        @JsonProperty("11시00분")
        private String elevenOClock;

        @JsonProperty("12시00분")
        private String twelveOClock;

        @JsonProperty("13시00분")
        private String thirteenOClock;

        @JsonProperty("14시00분")
        private String fourteenOClock;

        @JsonProperty("15시00분")
        private String fifteenOClock;

        @JsonProperty("16시00분")
        private String sixteenOClock;

        @JsonProperty("17시00분")
        private String seventeenOClock;

        @JsonProperty("18시00분")
        private String eighteenOClock;

        @JsonProperty("19시00분")
        private String nineteenOClock;

        @JsonProperty("20시00분")
        private String twentyOClock;

        @JsonProperty("21시00분")
        private String twentyOneOClock;

        @JsonProperty("22시00분")
        private String twentyTwoOClock;

        @JsonProperty("23시00분")
        private String twentyThreeOClock;

        @JsonProperty("00시30분")
        private String zeroThirty;


        @JsonProperty("5시30분")
        private String fiveThirty;

        @JsonProperty("6시30분")
        private String sixThirty;

        @JsonProperty("7시30분")
        private String sevenThirty;

        @JsonProperty("8시30분")
        private String eightThirty;

        @JsonProperty("9시30분")
        private String nineThirty;

        @JsonProperty("10시30분")
        private String tenThirty;

        @JsonProperty("11시30분")
        private String elevenThirty;

        @JsonProperty("12시30분")
        private String twelveThirty;

        @JsonProperty("13시30분")
        private String thirteenThirty;

        @JsonProperty("14시30분")
        private String fourteenThirty;

        @JsonProperty("15시30분")
        private String fifteenThirty;

        @JsonProperty("16시30분")
        private String sixteenThirty;

        @JsonProperty("17시30분")
        private String seventeenThirty;

        @JsonProperty("18시30분")
        private String eighteenThirty;

        @JsonProperty("19시30분")
        private String nineteenThirty;

        @JsonProperty("20시30분")
        private String twentyThirty;

        @JsonProperty("21시30분")
        private String twentyOneThirty;

        @JsonProperty("22시30분")
        private String twentyTwoThirty;

        @JsonProperty("23시30분")
        private String twentyThreeThirty;

        @JsonProperty("상하구분")
        private String direction;
        @JsonProperty("역번호")
        private int stationNumber;
        @JsonProperty("연번")
        private int serialNumber;
        @JsonProperty("요일구분")
        private String dayOfWeek;
        @JsonProperty("출발역")
        private String departureStation;
        @JsonProperty("호선")
        private String line;
    }
}
