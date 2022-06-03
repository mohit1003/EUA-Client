package in.gov.abdm.uhi.EUABookingService.repository;

import in.gov.abdm.uhi.EUABookingService.Entity.UserDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {

}
