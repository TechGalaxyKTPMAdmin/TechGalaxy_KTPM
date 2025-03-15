package iuh.fit.se.userservice.mapper;

import iuh.fit.se.userservice.dto.request.CustomerRequest;
import iuh.fit.se.userservice.dto.response.CustomerResponse;
import iuh.fit.se.userservice.dto.response.CustomerResponseV2;
import iuh.fit.se.userservice.entities.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

//    @Mapping(target = "account.roles", ignore = true)
    CustomerResponse toCustomerResponse(Customer customer);

    CustomerRequest toCustomerRequest(Customer customer);

    Customer toCustomerFromResponse(CustomerResponse customerResponse);

    CustomerResponseV2 toCustomerResponseV2(Customer customer);

    Customer toCustomerFromRequest(CustomerRequest customerRequest);
}
