package in.gov.abdm.uhi.EUABookingService.repository;

import in.gov.abdm.uhi.EUABookingService.Entity.UserAbhaAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAbhaAddressRepository extends JpaRepository<UserAbhaAddress, Long> {

}
