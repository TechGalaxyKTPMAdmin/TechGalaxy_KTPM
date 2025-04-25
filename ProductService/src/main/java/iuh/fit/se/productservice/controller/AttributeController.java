package iuh.fit.se.productservice.controller;

import iuh.fit.se.productservice.dto.request.AttributeRequest;
import iuh.fit.se.productservice.dto.request.AttributeValueRequest;
import iuh.fit.se.productservice.dto.request.AttributeValueUpdateRequest;
import iuh.fit.se.productservice.dto.response.AttributeResponse;
import iuh.fit.se.productservice.dto.response.DataResponse;
import iuh.fit.se.productservice.dto.response.ValueResponse;
import iuh.fit.se.productservice.service.impl.AttributeServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/attributes")

public class AttributeController {

    AttributeServiceImpl attributeServiceImpl;

    @GetMapping
    public ResponseEntity<DataResponse<AttributeResponse>> getAllAttribute() {
        return ResponseEntity
                .ok(DataResponse.<AttributeResponse>builder().data(attributeServiceImpl.getAllAttribute()).build());
    }

    @GetMapping("/attributes/{id}")
    public ResponseEntity<DataResponse<AttributeResponse>> getAttributeById(@PathVariable String id) {
        Set<AttributeResponse> attributeRespone = new HashSet<>();
        attributeRespone.add(attributeServiceImpl.getAttributeById(id));
        return ResponseEntity.ok(DataResponse.<AttributeResponse>builder().data(attributeRespone).build());
    }

    @PostMapping
    public ResponseEntity<DataResponse<AttributeResponse>> creatAttribute(@RequestBody AttributeRequest request) {
        Set<AttributeResponse> attributeRespone = new HashSet<>();
        attributeRespone.add(attributeServiceImpl.createAttribute(request));
        return ResponseEntity.ok(DataResponse.<AttributeResponse>builder().data(attributeRespone).build());
    }

    @PutMapping("/attributes/{id}")
    public ResponseEntity<DataResponse<AttributeResponse>> updateAttribute(@PathVariable String id,
            @RequestBody AttributeRequest request) {
        Set<AttributeResponse> attributeRespone = new HashSet<>();
        attributeRespone.add(attributeServiceImpl.updateAttribute(id, request));
        return ResponseEntity.ok(DataResponse.<AttributeResponse>builder().data(attributeRespone).build());
    }

    @DeleteMapping("/attributes/{id}")
    public ResponseEntity<DataResponse<AttributeResponse>> deleteAttribute(@PathVariable String id) {
        Set<AttributeResponse> attributeRespone = new HashSet<>();
        attributeRespone.add(attributeServiceImpl.deleteAttribute(id));
        return ResponseEntity.ok(DataResponse.<AttributeResponse>builder().data(attributeRespone).build());
    }

    @DeleteMapping("/value/{id}")
    public ResponseEntity<DataResponse<Boolean>> deleteValue(@PathVariable String id) {
        attributeServiceImpl.deleteValue(id);
        return ResponseEntity.ok(DataResponse.<Boolean>builder().message("Ok").build());
    }

    @GetMapping("/value/{id}")
    public ResponseEntity<DataResponse<ValueResponse>> getValueById(@PathVariable String id) {
        System.out.println("getValueById ");
        ValueResponse valueResponse = attributeServiceImpl.getValueById(id);
        return ResponseEntity.ok(DataResponse.<ValueResponse>builder().data(List.of(valueResponse)).build());
    }

    @GetMapping("/atributevalue/{name}")
    public ResponseEntity<DataResponse<ValueResponse>> getValueByNameAtri(@PathVariable String name) {
        List<ValueResponse> valueResponses = attributeServiceImpl.getValueByNameAtri(name);
        return ResponseEntity.ok(DataResponse.<ValueResponse>builder().data(valueResponses).build());
    }

    @GetMapping("/attributeByVariantId/{variantId}")
    public ResponseEntity<DataResponse<ValueResponse>> getAttributeByVariantId(@PathVariable String variantId) {
        List<ValueResponse> valueResponses = attributeServiceImpl.getAttributeByVariantId(variantId);
        return ResponseEntity.ok(DataResponse.<ValueResponse>builder().data(valueResponses).build());
    }

    @PostMapping("/productvariant/{provariant}")
    public ResponseEntity<DataResponse<Boolean>> createAttributeValueVariant(@PathVariable String provariant,
            @RequestBody List<AttributeValueRequest> request) {
        attributeServiceImpl.createValueProductVariant(provariant, request);
        return ResponseEntity.ok(DataResponse.<Boolean>builder().message("Ok").build());
    }

    @PutMapping("/productvariant/{provariant}")
    public ResponseEntity<DataResponse<ValueResponse>> updateAttributeValueVariant(@PathVariable String provariant,
            @RequestBody AttributeValueUpdateRequest request) {
        ValueResponse valueResponse = attributeServiceImpl.updateValueProductVariant(provariant, request);
        return ResponseEntity.ok(DataResponse.<ValueResponse>builder().data(List.of(valueResponse)).build());
    }
}
