package iuh.fit.se.userservice.service.impl;

import iuh.fit.se.userservice.dto.request.AccountUpdateRequest;
import iuh.fit.se.userservice.dto.response.AccountResponse;
import iuh.fit.se.userservice.dto.response.AccountUpdateResponse;
import iuh.fit.se.userservice.entities.Account;
import iuh.fit.se.userservice.entities.Role;
import iuh.fit.se.userservice.entities.SystemUser;
import iuh.fit.se.userservice.mapper.AccountMapper;
import iuh.fit.se.userservice.repository.AccountRepository;
import iuh.fit.se.userservice.repository.RoleRepository;
import iuh.fit.se.userservice.repository.SystemUserRepository;
import iuh.fit.se.userservice.service.AccountService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final SystemUserRepository systemUserRepository;
    private final PasswordEncoder passwordEncoder;

    private final AccountMapper accountMapper;
    public final RoleRepository roleRepository;

    @Override
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Optional<Account> getAccountById(String id) {
        return accountRepository.findById(id);
    }

    @Override
    public AccountUpdateResponse updateAccount(AccountUpdateRequest account) {
        Account existingAccount = accountRepository.findById(account.getId())
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));

        if (account.getEmail() != null && !account.getEmail().isEmpty()) {
            if (existingAccount.getEmail().equals(account.getEmail())) {

            } else if (accountRepository.existsByEmail(account.getEmail())) {
                throw new IllegalArgumentException("Email already exists");
            }
        }
        existingAccount.setEmail(account.getEmail());
        existingAccount.setPassword(account.getPassword());

        List<String> rolesIds = existingAccount.getRoles().stream().map(Role::getId).toList();
        rolesIds.forEach(roleId -> {
            System.out.println(roleId);
        });
        if (account.getRolesIds() != null) {
            List<Role> newRoles = roleRepository.findAllById(account.getRolesIds());
            newRoles.forEach(role -> {
                System.out.println(role.getName());
            });
            existingAccount.getRoles().clear();
            existingAccount.getRoles().addAll(newRoles);
        }

        Account updatedAccount = accountRepository.save(existingAccount);
        return accountMapper.toAccountResponse(updatedAccount);
    }

    @Override
    public AccountUpdateResponse updateAccountWithoutPassword(AccountUpdateRequest account) {
        Account existingAccount = accountRepository.findById(account.getId())
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));

        if (account.getEmail() != null && !account.getEmail().isEmpty()) {
            if (existingAccount.getEmail().equals(account.getEmail())) {

            } else if (accountRepository.existsByEmail(account.getEmail())) {
                throw new IllegalArgumentException("Email already exists");
            }
        }
        existingAccount.setEmail(account.getEmail());

        List<String> rolesIds = existingAccount.getRoles().stream().map(Role::getId).toList();
        rolesIds.forEach(roleId -> {
            System.out.println(roleId);
        });
        if (account.getRolesIds() != null) {
            List<Role> newRoles = roleRepository.findAllById(account.getRolesIds());
            newRoles.forEach(role -> {
                System.out.println(role.getName());
            });
            existingAccount.getRoles().clear();
            existingAccount.getRoles().addAll(newRoles);
        }

        Account updatedAccount = accountRepository.save(existingAccount);
        return accountMapper.toAccountResponse(updatedAccount);
    }

    @Override
    public boolean deleteAccountById(String id) {
        if (!accountRepository.existsById(id)) {
            return false;
        }
        if (accountRepository.existsById(id)) {
            Account account = accountRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Account not found"));
            account.getRoles().clear();
            accountRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<AccountResponse> findAllSystemUserAccounts() {
        List<AccountResponse> list = accountRepository.findAllSystemUserAccounts().stream().map(account -> {
            AccountResponse response = new AccountResponse();
            SystemUser systemUser = systemUserRepository.findSystemUserByEmail(account.getEmail());
            response.setId(account.getId());
            response.setEmail(account.getEmail());
            response.setPassword(account.getPassword());
            response.setRolesIds(account.getRoles().stream().map(Role::getId).toList());
            response.setPhone(systemUser.getPhone());
            response.setName(systemUser.getName());
            return response;
        }).toList();
        return list;
    }

    @Override
    public List<Account> findAccountsByCriteria(Specification<Account> spec) {
        return accountRepository.findAll(spec);
    }

    @Override
    public Optional<Account> getAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public void resetPassword(String id, String newPassword) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));
        account.setPassword(newPassword);
        accountRepository.save(account);
    }

    @Override
    public boolean validateAccount(String email, String password) {
        Optional<Account> accountOpt = accountRepository.findByEmail(email);
        return accountOpt.map(account -> passwordEncoder.matches(password, account.getPassword())).orElse(false);
    }

    @Override
    public void updateToken(String token, String email) {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));
        account.setRefreshToken(token);
        accountRepository.save(account);
    }

    @Override
    public Account getAcountByRefreshTokenAndEmail(String token, String email) {
        return accountRepository.findByRefreshTokenAndEmail(token, email)
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));
    }

    @Override
    public boolean existsByEmail(String email) {
        return accountRepository.existsByEmail(email);
    }

}
