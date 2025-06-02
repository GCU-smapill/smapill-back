package gcu.smapill_back.web.controller;

import gcu.smapill_back.apiPayload.exception.ApiResponse;
import gcu.smapill_back.config.auth.UserDetail;
import gcu.smapill_back.config.jwt.JwtUtil;
import gcu.smapill_back.converter.UserConverter;
import gcu.smapill_back.domain.User;
import gcu.smapill_back.service.UserService;
import gcu.smapill_back.web.dto.UserRequestDTO;
import gcu.smapill_back.web.dto.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "user", description = "유저 API")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    @Operation(summary = "회원가입 API", description = "회원가입 API")
    public ApiResponse<UserResponseDTO.UserJoinResultDTO> joinUser(@Valid @RequestBody UserRequestDTO.UserJoinDTO request) throws Exception {
        User user = userService.joinUser(request);
        return ApiResponse.onSuccess(UserConverter.toJoinResultDTO(user));
    }

    @PostMapping("/login")
    @Operation(summary = "로그인 API", description = "로그인 API")
    public ApiResponse<UserResponseDTO.LoginJwtTokenDTO> login(@Valid @RequestBody UserRequestDTO.UserLoginDTO request) {
        UserResponseDTO.LoginJwtTokenDTO token = userService.loginUser(request);
        return ApiResponse.onSuccess(token);
    }

    @PostMapping("/signup/pwd")
    @Operation(summary = "비밀번호 확인 API", description = "비밀번호 확인 API")
    @Parameters({
            @Parameter(name = "password", description = "비밀번호"),
            @Parameter(name = "checkPassword", description = "확인용 비밀번호")
    })
    public ApiResponse<UserResponseDTO> verifyPassword(@RequestParam("password") String password,
                                                       @RequestParam("checkPassword") String verifyPassword) throws Exception {
        userService.checkPassword(password, verifyPassword);
        return ApiResponse.onSuccess(null);
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃 API", description = "로그아웃 API")
    public ApiResponse<UserResponseDTO> logout(@RequestHeader("Authorization") String accessToken) {
        userService.logoutUser(accessToken.substring(7));
        return ApiResponse.onSuccess(null);
    }

    @PostMapping("/delete")
    @Operation(summary = "회원탈퇴 API", description = "회원탈퇴 API")
    public ApiResponse<UserResponseDTO> deleteMember(@AuthenticationPrincipal UserDetail userDetail, HttpServletRequest request) {
        userService.withdrawer(userDetail.getUser().getId());
        userService.logoutUser(jwtUtil.resolveToken(request));
        return ApiResponse.onSuccess(null);
    }

    @GetMapping("/")
    @Operation(summary = "회원정보조회 API", description = "회원정보조회 API")
    public ApiResponse<UserResponseDTO.UserDetailResultDTO> getMemberDetail(@AuthenticationPrincipal UserDetail userDetail) {
        String userId = userDetail.getUser().getUserId();
        return ApiResponse.onSuccess(userService.getUserDetail(userId));
    }

    @PatchMapping("/mode")
    @Operation(summary = "유저모드수정 API", description = "유저모드수정 API")
    public ApiResponse<UserResponseDTO.UserUpdateResultDTO> updateUserMode(@AuthenticationPrincipal UserDetail userDetail, @RequestParam String mode) {
        User user = userDetail.getUser();
        return ApiResponse.onSuccess(userService.updateMode(user.getId(), mode));
    }
}
