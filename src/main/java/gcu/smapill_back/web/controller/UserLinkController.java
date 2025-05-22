package gcu.smapill_back.web.controller;

import gcu.smapill_back.apiPayload.exception.ApiResponse;
import gcu.smapill_back.config.auth.UserDetail;
import gcu.smapill_back.converter.UserConverter;
import gcu.smapill_back.converter.UserLinkConverter;
import gcu.smapill_back.domain.User;
import gcu.smapill_back.domain.mapping.UserLink;
import gcu.smapill_back.service.UserLinkService;
import gcu.smapill_back.web.dto.UserLinkRequestDTO;
import gcu.smapill_back.web.dto.UserLinkResponseDTO;
import gcu.smapill_back.web.dto.UserRequestDTO;
import gcu.smapill_back.web.dto.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/userLink")
public class UserLinkController {
    private final UserLinkService userLinkService;

    @PostMapping("/")
    @Operation(summary = "사용자 연동 API", description = "사용자 연동 API")
    public ApiResponse<UserLinkResponseDTO.UserLinkJoinResultDTO> joinUserLink(@AuthenticationPrincipal UserDetail userDetail, @RequestBody @Valid UserLinkRequestDTO.UserLinkJoinDTO request) throws Exception {
        UserLink userLink = userLinkService.joinUserLink(userDetail.getUser().getId(), request);
        return ApiResponse.onSuccess(UserLinkConverter.toJoinResultDTO(userLink));
    }

    @GetMapping("/protector")
    @Operation(summary = "보호자 조회 API", description = "보호자 조회 API")
    public ApiResponse<List<UserResponseDTO.UserProtectorDetailDTO>> getProtectorDetail(@AuthenticationPrincipal UserDetail userDetail) {
        return ApiResponse.onSuccess(userLinkService.getProtectorDetail(userDetail.getUser().getId()));
    }

    @GetMapping("/dependent")
    @Operation(summary = "피보호자 조회 API", description = "피보호자 조회 API")
    public ApiResponse<List<UserResponseDTO.UserDependentDetailDTO>> getDependentDetail(@AuthenticationPrincipal UserDetail userDetail) {
        return ApiResponse.onSuccess(userLinkService.getDependentDetail(userDetail.getUser().getId()));
    }
}
