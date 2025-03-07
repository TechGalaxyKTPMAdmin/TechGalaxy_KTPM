package iuh.fit.se.userservice.mapper;

import iuh.fit.se.userservice.dto.request.AccountUpdateRequest;
import iuh.fit.se.userservice.dto.response.AccountResponse;
import iuh.fit.se.userservice.dto.response.AccountUpdateResponse;
import iuh.fit.se.userservice.entities.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountUpdateResponse toAccountResponse(Account account);

    Account toAccount(AccountUpdateRequest accountRequest);

    AccountResponse toAccountResponseToClient(Account account);

}
