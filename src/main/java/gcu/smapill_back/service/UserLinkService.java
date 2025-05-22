package gcu.smapill_back.service;

import gcu.smapill_back.apiPayload.code.status.ErrorStatus;
import gcu.smapill_back.apiPayload.exception.handler.UserHandler;
import gcu.smapill_back.apiPayload.exception.handler.UserLinkHandler;
import gcu.smapill_back.config.jwt.JwtUtil;
import gcu.smapill_back.converter.UserConverter;
import gcu.smapill_back.converter.UserLinkConverter;
import gcu.smapill_back.domain.User;
import gcu.smapill_back.domain.mapping.UserLink;
import gcu.smapill_back.repository.UserLinkRepository;
import gcu.smapill_back.repository.UserRepository;
import gcu.smapill_back.web.dto.UserLinkRequestDTO;
import gcu.smapill_back.web.dto.UserLinkResponseDTO;
import gcu.smapill_back.web.dto.UserRequestDTO;
import gcu.smapill_back.web.dto.UserResponseDTO;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserLinkService {

    private final UserRepository userRepository;
    private final UserLinkRepository userLinkRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserLink joinUserLink(Long id, UserLinkRequestDTO.UserLinkJoinDTO request) {
        LocalDate today = LocalDate.now();

        User protector = userRepository.findById(id)
                .orElseThrow(() -> new UserHandler(ErrorStatus.NO_USER_EXIST));

        User dependent = userRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new UserHandler(ErrorStatus.NO_USER_EXIST));

        if (!passwordEncoder.matches(request.getPassword(), dependent.getPassword())) {
            throw new UserHandler(ErrorStatus.PASSWORD_NOT_MATCH);
        }

        UserLink newUserLink = UserLinkConverter.toUserLinkResultDTO(dependent, protector);
        return userLinkRepository.save(newUserLink);
    }

    public UserResponseDTO.LoginJwtTokenDTO loginUser(UserRequestDTO.UserLoginDTO loginDTO) {
        String userId = loginDTO.getUserId();

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.NO_USER_EXIST));

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new UserHandler(ErrorStatus.PASSWORD_NOT_MATCH);
        }

        String accessToken = JwtUtil.createAccessToken(user.getId(), user.getUserId());

        return UserResponseDTO.LoginJwtTokenDTO.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .build();
    }

    public List<UserResponseDTO.UserProtectorDetailDTO> getProtectorDetail(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserHandler(ErrorStatus.NO_USER_EXIST));

        //자신이 피보호자인 UserLink를 조회
        List<UserLink> protectorList = userLinkRepository.findAllByDependentId(id);

        return protectorList.stream()
                .map(userLink -> UserConverter.toProtectorDetail(userLink.getProtector()))
                .collect(Collectors.toList());
    }

    public List<UserResponseDTO.UserDependentDetailDTO> getDependentDetail(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserHandler(ErrorStatus.NO_USER_EXIST));

        //자신이 보호자인 UserLink를 조회
        List<UserLink> dependentList = userLinkRepository.findAllByProtectorId(id);

        return dependentList.stream()
                .map(userLink -> UserConverter.toDependentDetail(userLink.getDependent()))
                .collect(Collectors.toList());
    }
}
