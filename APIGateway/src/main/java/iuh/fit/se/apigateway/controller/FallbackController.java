package iuh.fit.se.apigateway.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

        @RequestMapping("/fallback/user")
        public ResponseEntity<Map<String, Object>> userFallback() {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "User Service is temporarily unavailable. Please try again later.");
                response.put("status", "503");
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
        }

        @RequestMapping("/fallback/product")
        public ResponseEntity<Map<String, Object>> productFallback() {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Product Service is temporarily unavailable. Please try again later.");
                response.put("status", "503");
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
        }

        @RequestMapping("/fallback/order")
        public ResponseEntity<Map<String, Object>> orderFallback() {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Order Service is temporarily unavailable. Please try again later.");
                response.put("status", "503");
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
        }

        @GetMapping(value = "/payment", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Map<String, Object>> paymentFallback() {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Payment Service is temporarily unavailable. Please try again later.");
                response.put("status", "503");
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
        }

        @GetMapping(value = "/inventory", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Map<String, Object>> inventoryFallback() {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Inventory Service is temporarily unavailable. Please try again later.");
                response.put("status", "503");
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
        }

        @GetMapping(value = "/notification", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Map<String, Object>> notificationFallback() {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Notification Service is temporarily unavailable. Please try again later.");
                response.put("status", "503");
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
        }

        @GetMapping(value = "/recommendation", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Map<String, Object>> recommendationFallback() {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Recommendation Service is temporarily unavailable. Please try again later.");
                response.put("status", "503");
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
        }
}