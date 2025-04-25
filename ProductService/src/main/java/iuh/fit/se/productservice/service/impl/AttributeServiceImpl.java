package iuh.fit.se.productservice.service.impl;

import iuh.fit.se.productservice.dto.request.AttributeRequest;
import iuh.fit.se.productservice.dto.request.AttributeValueRequest;
import iuh.fit.se.productservice.dto.request.AttributeValueUpdateRequest;
import iuh.fit.se.productservice.dto.response.AttributeResponse;
import iuh.fit.se.productservice.dto.response.ValueResponse;
import iuh.fit.se.productservice.entities.Attribute;
import iuh.fit.se.productservice.entities.ProductVariant;
import iuh.fit.se.productservice.entities.Value;
import iuh.fit.se.productservice.mapper.AttributeMapper;
import iuh.fit.se.productservice.mapper.ValueMapper;
import iuh.fit.se.productservice.repository.AttributeRepository;
import iuh.fit.se.productservice.repository.ProductVariantRepository;
import iuh.fit.se.productservice.repository.ValueRepository;
import iuh.fit.se.productservice.service.AttributeService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttributeServiceImpl implements AttributeService {

    AttributeMapper attributeMapper;
    AttributeRepository attributeRepository;
    ValueRepository valueRepository;
    ValueMapper valueMapper;
    ProductVariantRepository productVariantRepository;

    @Override
    public AttributeResponse createAttribute(AttributeRequest attributeRequest) {
        Attribute attribute = attributeMapper.toAttribute(attributeRequest);
        return attributeMapper.toAttributeResponse(attributeRepository.save(attribute));

    }

    @Override
    public boolean createValueProductVariant(String variantId, List<AttributeValueRequest> attributeValueRequest) {
        ProductVariant productVaritant = productVariantRepository.findById(variantId)
                .orElseThrow(() -> new RuntimeException("ProductVariant not found"));
        List<Value> values = new ArrayList<Value>();
        for (AttributeValueRequest att : attributeValueRequest) {
            Attribute attribute = attributeRepository.findById(att.getAttributeId())
                    .orElseThrow(() -> new RuntimeException("Attribute not found"));
            Value value = valueMapper.toValue(att, attribute, productVaritant);
            values.add(value);
        }
        valueRepository.saveAll(values);
        return true;
    }

    @Transactional
    @Override
    public AttributeResponse updateAttribute(String id, AttributeRequest attributeRequest) {
        Attribute attribute = attributeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Attribute not found"));
        attributeMapper.updateAttributeFromRequest(attribute, attributeRequest);
        return attributeMapper.toAttributeResponse(attributeRepository.save(attribute));
    }

    @Override
    public AttributeResponse deleteAttribute(String id) {
        Attribute attribute = attributeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attribute not found"));
        return attributeMapper.toAttributeResponse(attribute);

    }

    @Override
    public AttributeResponse getAttributeById(String id) {
        return attributeRepository.findById(id).map(attributeMapper::toAttributeResponse)
                .orElseThrow(() -> new RuntimeException("Attribute not found"));

    }

    @Override
    public Set<AttributeResponse> getAllAttribute() {
        return attributeRepository.findAll().stream().map(attributeMapper::toAttributeResponse)
                .collect(Collectors.toSet());

    }

    @Override
    public boolean deleteValue(String valueId) {
        Value value = valueRepository.findById(valueId)
                .orElseThrow(() -> new RuntimeException("Value not found"));
        valueRepository.delete(value);
        return true;
    }

    @Override
    public ValueResponse getValueById(String valueId) {
        System.out.println(valueId);
        Value value = valueRepository.findById(valueId)
                .orElseThrow(() -> new RuntimeException("Value not found"));
        System.out.println(value.toString());
        ValueResponse response = valueMapper.toValueResponse(value);
        response.setAttributeId(value.getAttribute().getId());
        response.setAttributeName(value.getAttribute().getName());
        System.out.println(response.toString());

        return response;
    }

    @Override
    public ValueResponse updateValueProductVariant(String variantId,
            AttributeValueUpdateRequest attributeValueRequest) {
        Value value = valueRepository.findById(attributeValueRequest.getId())
                .orElseThrow(() -> new RuntimeException("Value not found"));

        value.setValue(attributeValueRequest.getValue());
        valueRepository.save(value);
        return valueMapper.toValueResponse(value);

    }

    @Override
    public List<ValueResponse> getValueByNameAtri(String name) {
        List<Value> values = valueRepository.findDistinctValuesByNameAndAttributeName(name);
        return values.stream().map(valueMapper::toValueResponse).toList();

    }

    @Override
    public List<ValueResponse> getAttributeByVariantId(String variantId) {
        List<Value> values = valueRepository.findAllByProductVariantId(variantId);
        List<ValueResponse> valueResponse = values.stream()
                .map(value -> valueMapper.toAttributeName(value, value.getAttribute().getName()))
                .collect(Collectors.toList());
        return valueResponse;

    }
}
