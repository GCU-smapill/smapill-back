package gcu.smapill_back.service;

import gcu.smapill_back.apiPayload.code.status.ErrorStatus;
import gcu.smapill_back.apiPayload.exception.handler.UserHandler;
import gcu.smapill_back.converter.DeviceConverter;
import gcu.smapill_back.converter.UserConverter;
import gcu.smapill_back.domain.Device;
import gcu.smapill_back.domain.User;
import gcu.smapill_back.repository.DeviceRepository;
import gcu.smapill_back.repository.UserRepository;
import gcu.smapill_back.web.dto.DeviceRequestDTO;
import gcu.smapill_back.web.dto.UserRequestDTO;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class DeviceService {
    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;

    @Transactional
    public Device accessDevice(Long userId, DeviceRequestDTO.DeviceAccessDTO request) {
        LocalDate today = LocalDate.now();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.NO_USER_EXIST));

        Device newDevice = DeviceConverter.toAccessResultDTO(request);
        newDevice.setUser(user);
        return deviceRepository.save(newDevice);
    }
}


