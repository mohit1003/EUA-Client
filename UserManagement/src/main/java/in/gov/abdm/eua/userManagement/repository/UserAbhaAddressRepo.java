package in.gov.abdm.eua.userManagement.repository;

import in.gov.abdm.eua.userManagement.model.User;
import in.gov.abdm.eua.userManagement.model.UserAbhaAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAbhaAddressRepo extends JpaRepository<UserAbhaAddress, Long> {
    List<UserAbhaAddress> findByPhrAddress(String phrAddress);

    User findByUserUserId(String toString);

    UserAbhaAddress findByUserHealthIdNumber(String healthid);
}
