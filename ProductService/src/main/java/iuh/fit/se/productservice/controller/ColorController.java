package iuh.fit.se.productservice.controller;

import iuh.fit.se.productservice.dto.request.ColorRequest;
import iuh.fit.se.productservice.dto.response.ColorResponse;
import iuh.fit.se.productservice.dto.response.DataResponse;
import iuh.fit.se.productservice.service.impl.ColorServiceImpl;
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
@RequestMapping("/colors")
public class ColorController {
    ColorServiceImpl colorServiceImpl;

    @GetMapping
    public ResponseEntity<DataResponse<ColorResponse>> getAllColor() {
        return ResponseEntity.ok(
                DataResponse.<ColorResponse>builder()
                        .data(colorServiceImpl.getAllColors())
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<DataResponse<ColorResponse>> createColor(@RequestBody ColorRequest request) {
        Set<ColorResponse> colorResponses = new HashSet<>();
        colorResponses.add(colorServiceImpl.createColor(request));
        return ResponseEntity.ok(DataResponse.<ColorResponse>builder().data(colorResponses).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataResponse<ColorResponse>> updateColor(@PathVariable String id, @RequestBody ColorRequest request) {
        Set<ColorResponse> colorResponse = new HashSet<>();
        colorResponse.add(colorServiceImpl.updateColor(id, request));
        return ResponseEntity.ok(DataResponse.<ColorResponse>builder().data(colorResponse).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DataResponse<ColorResponse>> deleteColor(@PathVariable String id) {
        Set<ColorResponse> colorResponse = new HashSet<>();
        colorResponse.add(colorServiceImpl.deleteColor(id));
        return ResponseEntity.ok(DataResponse.<ColorResponse>builder().data(colorResponse).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResponse<ColorResponse>> getColorById(String id) {
        return ResponseEntity.ok(DataResponse.<ColorResponse>builder().data(List.of( colorServiceImpl.getColorById(id))).build());
    }

    @GetMapping("/ids")
    public ResponseEntity<DataResponse<ColorResponse>> getColorsByIDs(@RequestParam List<String> ids) {
        return ResponseEntity.ok(DataResponse.<ColorResponse>builder().data(colorServiceImpl.getColorsByIDs(ids)).build());
    }
}
