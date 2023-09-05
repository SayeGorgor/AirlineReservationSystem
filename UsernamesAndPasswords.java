package com.example.airlinereservationsystem;

import java.util.HashMap;

public class UsernamesAndPasswords {
    HashMap<String, String> loginInfo = new HashMap<String, String>();
    UsernamesAndPasswords() {
        loginInfo.put("sgorgor02", "password123");
    }

    protected HashMap<String, String> getLoginInfo() {
        return loginInfo;
    }
}
