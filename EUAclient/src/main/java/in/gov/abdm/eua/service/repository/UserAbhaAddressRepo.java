package in.gov.abdm.eua.service.repository;

import in.gov.abdm.eua.service.model.UserAbhaAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAbhaAddressRepo extends JpaRepository<UserAbhaAddress, Long> {
    UserAbhaAddress findByPhrAddress(String phrAddress);
}
