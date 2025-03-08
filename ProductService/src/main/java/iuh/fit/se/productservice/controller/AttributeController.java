package iuh.fit.se.productservice.controller;

import iuh.fit.se.productservice.dto.response.AttributeResponse;
import iuh.fit.se.productservice.dto.response.DataResponse;
import iuh.fit.se.productservice.service.impl.AttributeServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/attributes")

public class AttributeController {

    AttributeServiceImpl attributeServiceImpl;

    @GetMapping
    public ResponseEntity<DataResponse<AttributeResponse>> getAllAttribute() {
        return ResponseEntity.ok(DataResponse.<AttributeResponse>builder().data(attributeServiceImpl.getAllAttribute()).build());
    }


}
