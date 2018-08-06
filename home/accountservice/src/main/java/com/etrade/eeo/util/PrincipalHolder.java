package com.etrade.eeo.util;

import com.etrade.eeo.model.Principal;

/**
 * Created by rayyar on 3/16/18.
 */
public class PrincipalHolder {

    private static PrincipalHolder instance;
    private static final Object lock = new Object();

    public static PrincipalHolder getInstance() {
        if (instance == null) {
            synchronized(lock) {
                if (instance == null) {
                    instance = new PrincipalHolder();
                }
            }

        }
        return instance;
    }

    ThreadLocal<Principal> CURRENT_PRINCIPAL = new ThreadLocal<>();

    public void setPrincipal( Principal p) {
        CURRENT_PRINCIPAL.set(p);
    }

    public Principal getPrincipal() {
        return CURRENT_PRINCIPAL.get();
    }

    public void unsetPrincipal() {
        CURRENT_PRINCIPAL.remove();
    }

}
