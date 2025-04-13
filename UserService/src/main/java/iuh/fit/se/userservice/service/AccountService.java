package iuh.fit.se.userservice.service;

import iuh.fit.se.userservice.dto.request.AccountUpdateRequest;
import iuh.fit.se.userservice.dto.response.AccountResponse;
import iuh.fit.se.userservice.dto.response.AccountUpdateResponse;
import iuh.fit.se.userservice.entities.Account;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    /**
     * Tạo mới một tài khoản.
     *
     * @param account đối tượng Account cần tạo
     * @return Account đã được lưu trong cơ sở dữ liệu
     */
    Account createAccount(Account account);

    /**
     * Lấy thông tin tài khoản theo ID.
     *
     * @param id ID của tài khoản
     * @return Optional<Account> chứa thông tin tài khoản nếu tìm thấy, hoặc rỗng nếu không tìm thấy
     * author: Vũ Nguyễn Minh Đức
     */
    Optional<Account> getAccountById(String id);

    /**
     * Cập nhật thông tin tài khoản.
     *
     * @param account đối tượng Account đã chỉnh sửa
     * @return Account đã được cập nhật
     * author: Vũ Nguyễn Minh Đức
     */
    AccountUpdateResponse updateAccount(AccountUpdateRequest account);

    AccountUpdateResponse updateAccountWithoutPassword(AccountUpdateRequest account);

    /**
     * Xóa tài khoản theo ID.
     *
     * @param id ID của tài khoản cần xóa
     *           author: Vũ Nguyễn Minh Đức
     */
    boolean deleteAccountById(String id);

    /**
     * Lấy danh sách tất cả tài khoản.
     *
     * @return List<AccountResponse> danh sách tất cả tài khoản của system user
     * author: Vũ Nguyễn Minh Đức
     */
    List<AccountResponse> findAllSystemUserAccounts();

    /**
     * Tìm kiếm tài khoản dựa trên các tiêu chí lọc.
     *
     * @param spec tiêu chí tìm kiếm (Specification)
     * @return List<Account> danh sách tài khoản thỏa mãn tiêu chí
     * author: Vũ Nguyễn Minh Đức
     */
    List<Account> findAccountsByCriteria(Specification<Account> spec);

    /**
     * Lấy thông tin tài khoản dựa trên email.
     *
     * @param email email của tài khoản
     * @return Optional<Account> chứa thông tin tài khoản nếu tìm thấy, hoặc rỗng nếu không tìm thấy
     * author: Vũ Nguyễn Minh Đức
     */
    Optional<Account> getAccountByEmail(String email);

    /**
     * Đặt lại mật khẩu cho tài khoản.
     *
     * @param id          ID của tài khoản
     * @param newPassword mật khẩu mới
     *                    author: Vũ Nguyễn Minh Đức
     */
    void resetPassword(String id, String newPassword);

    /**
     * Xác thực tài khoản dựa trên email và mật khẩu.
     *
     * @param email    email của tài khoản
     * @param password mật khẩu của tài khoản
     * @return true nếu tài khoản hợp lệ, ngược lại là false
     * author: Vũ Nguyễn Minh Đức
     */
    boolean validateAccount(String email, String password);


    /**
     * update refreshToken cho tài khoản
     *
     * @param token refreshToken mới
     * @param email email của tài khoản
     *              author: Vũ Nguyễn Minh Đức
     */
    public void updateToken(String token, String email);

    /**
     * Lấy thông tin tài khoản dựa trên email và refreshToken.
     *
     * @param email email của tài khoản
     * @param token refreshToken của tài khoản
     * @return Account chứa thông tin tài khoản nếu tìm thấy, hoặc null nếu không tìm thấy
     * author: Vũ Nguyễn Minh Đức
     */
    public Account getAcountByRefreshTokenAndEmail(String token, String email);


    /**
     * Kiểm tra sự tồn tại của email trong cơ sở dữ liệu.
     *
     * @param email email cần kiểm tra
     * @return true nếu email đã tồn tại, ngược lại là false
     * author: Vũ Nguyễn Minh Đức
     */
    public boolean existsByEmail(String email);
    }
