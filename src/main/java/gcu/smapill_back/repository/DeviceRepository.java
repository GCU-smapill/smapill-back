package gcu.smapill_back.repository;

import gcu.smapill_back.domain.Device;
import gcu.smapill_back.domain.mapping.UserLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {
}
