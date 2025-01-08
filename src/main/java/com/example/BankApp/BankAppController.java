package com.example.BankApp;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
public class BankAppController {

    boolean validateAccountNumber(String accountNumber) {
        // this simple method checks that the length should is 16, but there should
        // be other data checks such as to verify that all characters are numeric
        return (accountNumber.length() == 16);
    }

    String getAccountBalance(String accountNumber)  {

        ObjectMapper objectMapper = new ObjectMapper();

        List<AccountInfo> myAccountInfoList = new ArrayList<>();

        try {
            myAccountInfoList = objectMapper.readValue(new File("accountData.json"), new TypeReference<List<AccountInfo>>(){});
        } catch (IOException e) {
            // in production, this would most likely be log.error with details about the failure (file not found, file not readable, etc.)
            System.out.println("Could not read the file: " + e.getMessage());
            // return the "error" so that we can set the appropriate HTTPStatus in calling method.  Do not show the user the specific issue.
            return("Error");
        }

        // Set the default to not finding the account number
        String result = "NotFound";
        for (AccountInfo accountInfo : myAccountInfoList) {
            if (accountNumber.equals(accountInfo.getAccount().getCardNumber())) {
                // convert the balance to a string for display on UI
                result = Double.toString(accountInfo.getAccount().getBalance());
            }
        }
        return result;
    }

    @GetMapping("/accountBalance/{accountId}")
    public ResponseEntity<String> getBalance(@PathVariable String accountId) {
        // Assume that all is going to work
        HttpStatus status = HttpStatus.OK;

        String accountBalance = "";
        if (validateAccountNumber(accountId)) {
            accountBalance = getAccountBalance(accountId);
            if (accountBalance.equals("Error")) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;  // this could be other status's depending on what the specific failure was
            } else if (accountBalance.equals("NotFound")) {
                status = HttpStatus.NOT_FOUND;
            }
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Access-Control-Allow-Origin", "*");
        String body = "{\n" +
                "    \"balance\": \"" + accountBalance + "\"\n" +
                "}";

        return ResponseEntity.status(status).headers(headers).body(body);

    }
}