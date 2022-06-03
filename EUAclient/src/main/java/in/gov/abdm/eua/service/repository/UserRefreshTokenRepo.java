package in.gov.abdm.eua.service.repository;

import in.gov.abdm.eua.service.model.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRefreshTokenRepo extends JpaRepository<UserRefreshToken, Long> {
}
