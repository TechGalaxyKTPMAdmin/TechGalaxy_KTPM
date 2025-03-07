package iuh.fit.se.userservice.controller;

import iuh.fit.se.userservice.dto.request.*;
import iuh.fit.se.userservice.dto.response.*;
import iuh.fit.se.userservice.entities.Account;
import iuh.fit.se.userservice.entities.Role;
import iuh.fit.se.userservice.exception.AppException;
import iuh.fit.se.userservice.exception.ErrorCode;
import iuh.fit.se.userservice.mapper.AccountMapper;
import iuh.fit.se.userservice.service.impl.AccountServiceImpl;
import iuh.fit.se.userservice.service.impl.AuthenticationServiceImpl;
import iuh.fit.se.userservice.service.impl.RegistrationServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class AccountController {

    private final AccountServiceImpl accountService;
    private final PasswordEncoder passwordEncoder;
    private final AccountMapper accountMapper;

    @Autowired
    public AccountController(
            AccountServiceImpl accountService,
            PasswordEncoder passwordEncoder,
            AccountMapper accountMapper) {
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
        this.accountMapper = accountMapper;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DataResponse<Void>> deleteAccount(@PathVariable String id) {
        accountService.deleteAccountById(id);
        return ResponseEntity.ok(DataResponse.<Void>builder()
                .status(200)
                .message("Account deleted successfully")
                .build());
    }

    @PutMapping
    public ResponseEntity<DataResponse<AccountUpdateResponse>> updateAccount(@RequestBody AccountUpdateRequest accountRequest) {
        Account account = accountService.getAccountById(accountRequest.getId())
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOTFOUND));

        String hashPass = passwordEncoder.encode(accountRequest.getPassword());
        accountRequest.setPassword(hashPass);

        AccountUpdateResponse updatedAccount = accountService.updateAccount(accountRequest);
        updatedAccount.setPassword(null);
        updatedAccount.setId(null);

        return ResponseEntity.ok(DataResponse.<AccountUpdateResponse>builder()
                .status(200)
                .message("Account updated successfully")
                .data(Collections.singletonList(updatedAccount))
                .build());
    }

    @PutMapping("/update-account-without-password")
    public ResponseEntity<DataResponse<AccountUpdateResponse>> updateAccountWithoutPassword(@RequestBody AccountUpdateRequest accountRequest) {
        Account account = accountService.getAccountById(accountRequest.getId())
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOTFOUND));

        String hashPass = passwordEncoder.encode(accountRequest.getPassword());
        accountRequest.setPassword(hashPass);

        AccountUpdateResponse updatedAccount = accountService.updateAccountWithoutPassword(accountRequest);
        updatedAccount.setPassword(null);
        updatedAccount.setId(null);

        return ResponseEntity.ok(DataResponse.<AccountUpdateResponse>builder()
                .status(200)
                .message("Account updated successfully")
                .data(Collections.singletonList(updatedAccount))
                .build());
    }

    @GetMapping
    public ResponseEntity<DataResponse<AccountResponse>> getAllSystemUserAccounts() {
        List<AccountResponse> accounts = accountService.findAllSystemUserAccounts();
        return ResponseEntity.ok(DataResponse.<AccountResponse>builder()
                .status(200)
                .message("Accounts retrieved successfully")
                .data(accounts)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResponse<AccountResponse>> getAccountById(@PathVariable String id) {
        Account account = accountService.getAccountById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOTFOUND));

        AccountResponse accountResponse = accountMapper.toAccountResponseToClient(account);
        accountResponse.setRolesIds(account.getRoles().stream().map(Role::getId).toList());

        return ResponseEntity.ok(DataResponse.<AccountResponse>builder()
                .status(200)
                .message("Account retrieved successfully")
                .data(Collections.singletonList(accountResponse))
                .build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<DataResponse<AccountResponse>> getAccountByEmail(@PathVariable String email) {
        Account account = accountService.getAccountByEmail(email)
                .orElseThrow(()-> new AppException(ErrorCode.ACCOUNT_NOTFOUND));

        AccountResponse accountResponse = accountMapper.toAccountResponseToClient(account);
        accountResponse.setRolesIds(account.getRoles().stream().map(Role::getId).toList());

        return ResponseEntity.ok(DataResponse.<AccountResponse>builder()
                .status(200)
                .message("Account retrieved successfully")
                .data(Collections.singletonList(accountResponse))
                .build());
    }

    @GetMapping("/exists-by-email/{email}")
    public boolean existsByEmail(@PathVariable String email) {
        return accountService.existsByEmail(email);
    }
}