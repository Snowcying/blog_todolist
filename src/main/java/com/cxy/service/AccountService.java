package com.cxy.service;

import com.cxy.entity.Account;

public interface AccountService {
    public Account findByUsername(String username);
}
