package iuh.fit.se.productservice.service.impl;

import iuh.fit.se.productservice.dto.request.AttributeRequest;
import iuh.fit.se.productservice.dto.response.AttributeResponse;
import iuh.fit.se.productservice.entities.Attribute;

import iuh.fit.se.productservice.mapper.AttributeMapper;
import iuh.fit.se.productservice.repository.AttributeRepository;
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

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttributeServiceImpl implements AttributeService {

    AttributeMapper attributeMapper;
    AttributeRepository attributeRepository;



    @Override
    public AttributeResponse createAttribute(AttributeRequest attributeRequest) {
        Attribute attribute = attributeMapper.toAttribute(attributeRequest);
        return attributeMapper.toAttributeResponse(attributeRepository.save(attribute));

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
        return false;
    }
}
