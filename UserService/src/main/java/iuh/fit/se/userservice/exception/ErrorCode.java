package iuh.fit.se.userservice.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    // 9000 - 9999: System error with authorization
    INVALID_KEY(9998, "Invalid key", HttpStatus.UNAUTHORIZED),
    JWT_INVALID(9997, "JWT invalid", HttpStatus.UNAUTHORIZED),
    UNCATEGORIZED_ERROR(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    NO_LOGIN(9996, "No login", HttpStatus.UNAUTHORIZED),
    NOT_IN_REQUEST(9995, "Not in request", HttpStatus.BAD_REQUEST),
    CUSTOMER_NOTMATCH_LOGIN(9994, "Customer not match", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(9993, "Password must be at least 8 characters long and include at least one uppercase letter, one lowercase letter, and one number", HttpStatus.BAD_REQUEST),
    // 8000 - 8999: error with authentication
    AUTHENTICATION_ERROR(8000, "You no access to method", HttpStatus.FORBIDDEN),

    // 1000 - 1999: Business error ( call data from database)
    DATA_DUPLICATE_PRODUCT_DETAIL(1000, "Data duplicate product detail please check color and memories request",
            HttpStatus.BAD_REQUEST),
    PRODUCT_UPDATE_FAILED(1001, "Product update failed", HttpStatus.INTERNAL_SERVER_ERROR),
    PRODUCT_DELETE_FAILED(1002, "Product delete failed", HttpStatus.INTERNAL_SERVER_ERROR),
    MEMORY_NOTFOUND(1003, "Memory not found", HttpStatus.NOT_FOUND),
    COLOR_NOTFOUND(1004, "Color not found", HttpStatus.NOT_FOUND),
    USAGE_CATEGORY_NOTFOUND(1005, "Usage category not found", HttpStatus.NOT_FOUND),
    PRODUCT_NOTFOUND(1006, "Product not found", HttpStatus.NOT_FOUND),
    ACCOUNT_NOTFOUND(1007, "Account not found", HttpStatus.NOT_FOUND),
    CUSTOMER_NOTFOUND(1008, "Customer not found", HttpStatus.NOT_FOUND),
    TRADEMARK_NOTFOUND(1009, "Trademark not found", HttpStatus.NOT_FOUND),
    FEEDBACK_NOTFOUND(1010, "Feedback not found", HttpStatus.NOT_FOUND),
    IMAGE_FEEDBACK_NOTFOUND(1011, "Image Feedback not found", HttpStatus.NOT_FOUND),
    ORDER_NOTFOUND(1012, "Order not found", HttpStatus.NOT_FOUND),
    ORDER_DETAIL_NOTFOUND(1013, "Order detail not found", HttpStatus.NOT_FOUND),
    SYSTEM_USER_NOTFOUND(1014, "System user not found", HttpStatus.NOT_FOUND),
    INSUFFICIENT_PRODUCT_QUANTITY(1015, "Insufficient product quantity", HttpStatus.BAD_REQUEST),
    SYSTEM_USER_NOT_FOUND(1016, "System user not found", HttpStatus.NOT_FOUND),
    PRODUCT_NOT_ENOUGH(1017, "Product not enough", HttpStatus.BAD_REQUEST),
    NOT_UPDATE_ORDER(1018, "Not update order", HttpStatus.BAD_REQUEST),
    DATA_INTEGRITY_VIOLATION_EXCEPTION(1019, "Data integrity violation exception", HttpStatus.BAD_REQUEST),

    // 2000 - 2999: Validation error in request body
    PRODUCT_DISCOUNT_INVALID(2000, "Product discount min value is {min} and max value is {max}",
            HttpStatus.BAD_REQUEST),
    PRODUCT_NAME_INVALID(2002, "Product name min length is {min} and max length is {max}", HttpStatus.BAD_REQUEST),
    USERNAME_NOT_EMPTY(2003, "Username is not empty", HttpStatus.BAD_REQUEST),
    PASSWORD_NOT_EMPTY(2004, "Password is not empty", HttpStatus.BAD_REQUEST),
    EMAIL_NOT_EMPTY(2005, "Email is not empty", HttpStatus.BAD_REQUEST),
    FULL_NAME_NOT_EMPTY(2006, "Full name is not empty", HttpStatus.BAD_REQUEST),
    NAME_NOT_EMPTY(2007, "Name is not empty", HttpStatus.BAD_REQUEST),
    PHONE_NOT_EMPTY(2008, "Phone is not empty", HttpStatus.BAD_REQUEST),
    PHONE_INVALID(2009, "Phone is invalid", HttpStatus.BAD_REQUEST),
    ADDRESS_NOT_EMPTY(2010, "Address is not empty", HttpStatus.BAD_REQUEST),
    ADDRESS_INVALID(2011, "Address is invalid", HttpStatus.BAD_REQUEST),
    SYSTEM_USER_STATUS_NOT_EMPTY(2012, "System user status is not empty", HttpStatus.BAD_REQUEST),
    LEVEL_NOT_EMPTY(2013, "Level is not empty", HttpStatus.BAD_REQUEST),
    GENDER_NOT_EMPTY(2014, "Gender is not empty", HttpStatus.BAD_REQUEST),
    ROLE_NOT_EMPTY(2015, "Role is not empty", HttpStatus.BAD_REQUEST),
    INVALID_PRODUCT_QUANTITY(2016, "Invalid product quantity", HttpStatus.BAD_REQUEST),
    ORDER_SERVICE_UNAVAILABLE(2017, "Order Service is temporarily unavailable. Please try again later.",
            HttpStatus.SERVICE_UNAVAILABLE),

    CUSTOMER_NO_DELETE(2018, "Cannot delete account as the user has placed orders.", HttpStatus.BAD_REQUEST),
    // Mail
    FAILED_SEND_EMAIL(3000, "Failed to send email", HttpStatus.INTERNAL_SERVER_ERROR),
    NOTIFICATION_SERVICE_UNAVAILABLE(2017, "Notification Service is temporarily unavailable. Please try again later.",
            HttpStatus.SERVICE_UNAVAILABLE),
    // 4000 - 4999: Storage and file error
    NO_RESOURCE_FOUND(4010, "No resource found", HttpStatus.NOT_FOUND),
    FILE_EMPTY(4000, "File is empty. Please upload a file.", HttpStatus.BAD_REQUEST),
    INVALID_FILE_EXTENSION(4001, "Invalid file extension. only allows pdf, jpg, jpeg, png, doc, docx",
            HttpStatus.BAD_REQUEST),
    FILE_NOT_FOUND(4002, "File not found", HttpStatus.NOT_FOUND),
    FILE_SIZE_EXCEEDED(4003, "File size exceeded", HttpStatus.PAYLOAD_TOO_LARGE),
    MISSING_FILE(4004, "Missing file", HttpStatus.BAD_REQUEST),
    CREATE_DIRECTORY_FAILED(4005, "Create directory failed", HttpStatus.INTERNAL_SERVER_ERROR);

    int code;
    String message;
    HttpStatus httpStatus;
}
