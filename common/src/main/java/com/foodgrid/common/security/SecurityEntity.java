package com.foodgrid.common.security;

public class SecurityEntity {
    private String security;

    public SecurityEntity(String security) {
        this.security = security;
    }

    public SecurityEntity() {
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    @Override
    public String toString() {
        return "SecurityEntity{" +
                "security='" + security + '\'' +
                '}';
    }
}
