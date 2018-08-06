package com.etrade.eeo.resource;


import com.etrade.eeo.controller.AccountService;
import com.etrade.eeo.model.Account;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by rayyar on 3/9/18.
 */
public class AccountResource extends ResourceSupport {


    private final Account account;


    public Account getAccount() {
        return account;
    }


    public AccountResource(final Account account) {
        this.account = account;
        final Long id = account.getAccountId();

        System.out.println( "Account Resource ID = " + id );

        add(linkTo(AccountService.class).withRel("account"));
        add(linkTo(AccountService.class).slash(id).withSelfRel());
    }

}
