package com.etrade.eeo.repository;

import com.etrade.eeo.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rayyar on 3/9/18.
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
}
