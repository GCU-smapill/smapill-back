package gcu.smapill_back.service;

import gcu.smapill_back.apiPayload.code.status.ErrorStatus;
import gcu.smapill_back.apiPayload.exception.handler.UserHandler;
import gcu.smapill_back.apiPayload.exception.handler.UserLinkHandler;
import gcu.smapill_back.converter.UserConverter;
import gcu.smapill_back.converter.UserLinkConverter;
import gcu.smapill_back.domain.User;
import gcu.smapill_back.domain.mapping.UserLink;
import gcu.smapill_back.repository.UserLinkRepository;
import gcu.smapill_back.repository.UserRepository;
import gcu.smapill_back.web.dto.UserLinkResponseDTO;
import gcu.smapill_back.web.dto.UserRequestDTO;
import gcu.smapill_back.web.dto.UserResponseDTO;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.RequiredArgsConstructor;
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

    @Transactional
    public UserLink joinUserLink(Long dependentId, Long protectorId) {
        LocalDate today = LocalDate.now();

        User dependent = userRepository.findById(dependentId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.NO_USER_EXIST));

        User protector = userRepository.findById(protectorId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.NO_USER_EXIST));

        UserLink newUserLink = UserLinkConverter.toUserLinkResultDTO(dependent, protector);
        return userLinkRepository.save(newUserLink);
    }

    public List<Long> getProtectorDetail(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.NO_USER_EXIST));

        List<UserLink> protectorList = userLinkRepository.findAllByProtectorId(userId);

        return protectorList.stream()
                .map(userLink -> userLink.getProtector().getId()) // protector 객체에서 id 꺼냄
                .collect(Collectors.toList());
    }

    public List<Long> getDependentDetail(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.NO_USER_EXIST));

        List<UserLink> dependentList = userLinkRepository.findAllByDependentId(userId);

        return dependentList.stream()
                .map(userLink -> userLink.getDependent().getId()) // dependent 객체에서 id 꺼냄
                .collect(Collectors.toList());
    }
}
