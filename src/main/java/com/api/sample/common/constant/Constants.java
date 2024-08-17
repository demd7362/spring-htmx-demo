package com.api.sample.common.constant;


public class Constants {
    public static class Redis {
        public static final String RATE_LIMITER_KEY = "$rate_limit";
    }

    public static class ROUTE {
        public static final String LOGIN = "/login";
        public static final String JOIN = "/join";
        public static final String INDEX = "/";
        public static final String LOGOUT = "/logout";
    }
}
