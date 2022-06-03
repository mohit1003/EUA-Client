package in.gov.abdm.eua.userManagement.repository;

import in.gov.abdm.eua.userManagement.model.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRefreshTokenRepo extends JpaRepository<in.gov.abdm.eua.userManagement.model.UserRefreshToken, Long> {
    UserRefreshToken getByUserHealthIdNumber(String healthIdNo);
}
