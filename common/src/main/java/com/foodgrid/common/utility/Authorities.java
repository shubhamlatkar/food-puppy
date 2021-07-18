package com.foodgrid.common.utility;

public enum Authorities {


        USER_READ("user:read"),

        USER_WRITE("user:write"),

        RESTAURANT_READ("restaurant:read"),

        RESTAURANT_WRITE("restaurant:write"),

        SERVICE_WRITE("service:write"),

        SERVICE_READ("service:read");

        private final String value;

        Authorities(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }



}
