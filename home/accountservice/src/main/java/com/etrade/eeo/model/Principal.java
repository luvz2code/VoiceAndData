package com.etrade.eeo.model;

import java.util.List;

/**
 * Created by rayyar on 3/16/18.
 */
public class Principal {
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String userId;

    public List getEntitlements() {
        return entitlements;
    }

    public void setEntitlements(List entitlements) {
        this.entitlements = entitlements;
    }

    private List entitlements;


    public boolean isEntitled( String featureName ) {
        return this.entitlements.contains(featureName);
    }
}
