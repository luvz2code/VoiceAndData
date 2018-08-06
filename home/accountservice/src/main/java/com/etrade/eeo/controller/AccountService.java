package com.etrade.eeo.controller;

import com.etrade.eeo.model.Account;
import com.etrade.eeo.repository.AccountRepository;
import com.etrade.eeo.repository.NotFoundException;
import com.etrade.eeo.resource.AccountResource;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rayyar on 3/9/18.
 */
@RestController
@RequestMapping("/account")
@Api(value="Account Service", description="Operations pertaining to Accounts")
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @GetMapping
    @ApiOperation(value = "Get all accounts",
            notes = "Get all accounts. In future this would acccept filter and pagination parameters.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<Resources<AccountResource>> all() {
        final List<AccountResource> collection =
                accountRepository.findAll().stream().map(AccountResource::new).collect(Collectors.toList());
        final Resources<AccountResource> resources = new Resources<>(collection);
        final String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        resources.add(new Link(uriString, "self"));
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get account by its ID",
            notes = "returns links too.")
    public ResponseEntity<AccountResource> get(@PathVariable final long id) {
        return accountRepository
                .findById(id)
                .map(p -> ResponseEntity.ok(new AccountResource(p)))
                .orElseThrow(() -> new NotFoundException("Account", id));
    }



    @PostMapping
    @ApiOperation(value = "Add new account",
            notes = "returns the new account with links too.")
    public ResponseEntity<AccountResource> post(@RequestBody final Account accountFromRequest) {
        final Account account = accountRepository.save(new Account(accountFromRequest));

        return ResponseEntity.ok( new AccountResource(account));

        /*
        final URI uri =
                MvcUriComponentsBuilder.fromController(getClass())
                        .path("/{id}")
                        .buildAndExpand(account.getAccountId())
                        .toUri();

        return ResponseEntity.created(uri).body(new AccountResource(account));
        */
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update existing account identified by ID",
            notes = "returns the updated account with links too.")
    public ResponseEntity<AccountResource> put(
            @PathVariable("id") final long id, @RequestBody Account accountFromRequest) {

        final Account account = new Account(accountFromRequest, id);
        accountRepository.save(account);

        return ResponseEntity.ok( new AccountResource(account));

        /*
        final AccountResource resource = new AccountResource(account);
        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(uri).body(resource);
        */
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete existing account identified by ID",
            notes = "returns nothing if successful or 'not found' exception if not found.")
    public ResponseEntity<?> delete(@PathVariable("id") final long id) {
        return accountRepository
                .findById(id)
                .map(
                        p -> {
                            accountRepository.deleteById(id);
                            return ResponseEntity.noContent().build();
                        })
                .orElseThrow(() -> new NotFoundException("Account", id));
    }

}
