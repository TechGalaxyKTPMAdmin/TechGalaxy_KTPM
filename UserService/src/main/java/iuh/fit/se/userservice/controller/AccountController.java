package iuh.fit.se.userservice.controller;

import iuh.fit.se.userservice.dto.request.AccountUpdateRequest;
import iuh.fit.se.userservice.dto.response.AccountResponse;
import iuh.fit.se.userservice.dto.response.AccountUpdateResponse;
import iuh.fit.se.userservice.dto.response.DataResponse;
import iuh.fit.se.userservice.entities.Account;
import iuh.fit.se.userservice.entities.Role;
import iuh.fit.se.userservice.exception.AppException;
import iuh.fit.se.userservice.exception.ErrorCode;
import iuh.fit.se.userservice.mapper.AccountMapper;
import iuh.fit.se.userservice.mapper.RoleMapper;
import iuh.fit.se.userservice.repository.RoleRepository;
import iuh.fit.se.userservice.service.RoleService;
import iuh.fit.se.userservice.service.impl.AccountServiceImpl;
import iuh.fit.se.userservice.service.impl.CustomerServiceImpl;
import iuh.fit.se.userservice.service.impl.SystemUserServiceImpl;
import iuh.fit.se.userservice.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
//    private final EmailServiceImpl emailService;
    private final AccountServiceImpl accountService;
    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;

    private final RoleMapper roleMapper;

    private final CustomerServiceImpl customerService;

    private final SystemUserServiceImpl systemUserService;

    private final SecurityUtil securityUtil;
    private final RoleRepository roleRepository;

    private final AccountMapper accountMapper;


    @Value("${jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenExpiration;


    @Autowired
    public AccountController(AuthenticationManagerBuilder authenticationManagerBuilder,
                             AccountServiceImpl accountService,
                             PasswordEncoder passwordEncoder,
                             SecurityUtil securityUtil,
                             RoleService roleService,
                             CustomerServiceImpl customerService,
                             RoleMapper roleMapper,
                             SystemUserServiceImpl systemUserService,
                             RoleRepository roleRepository,
                             AccountMapper accountMapper) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
        this.securityUtil = securityUtil;
        this.roleService = roleService;
        this.customerService = customerService;
        this.roleMapper = roleMapper;
        this.systemUserService = systemUserService;
        this.roleRepository = roleRepository;
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
        Account account = accountService.getAccountById(accountRequest.getId()).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOTFOUND));
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(DataResponse.<AccountUpdateResponse>builder()
                            .status(404)
                            .message("Account not found")
                            .build());
        }
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
        Account account = accountService.getAccountById(accountRequest.getId()).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOTFOUND));
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(DataResponse.<AccountUpdateResponse>builder()
                            .status(404)
                            .message("Account not found")
                            .build());
        }
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
        Account account = accountService.getAccountById(id).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOTFOUND));
        AccountResponse accountResponse = accountMapper.toAccountResponseToClient(account);
        accountResponse.setRolesIds(account.getRoles().stream().map(Role::getId).toList());
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(DataResponse.<AccountResponse>builder()
                            .status(404)
                            .message("Account not found")
                            .build());
        }
        return ResponseEntity.ok(DataResponse.<AccountResponse>builder()
                .status(200)
                .message("Account retrieved successfully")
                .data(Collections.singletonList(accountResponse))
                .build());
    }


    @GetMapping("/email/{email}")
    public ResponseEntity<DataResponse<AccountResponse>> getAccountByEmail(@PathVariable String email) {
        Account account = accountService.getAccountByEmail(email).orElseThrow(()-> new AppException(ErrorCode.ACCOUNT_NOTFOUND));
        AccountResponse accountResponse = accountMapper.toAccountResponseToClient(account);
        accountResponse.setRolesIds(account.getRoles().stream().map(Role::getId).toList());
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(DataResponse.<AccountResponse>builder()
                            .status(404)
                            .message("Account not found")
                            .build());
        }
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
