package in.gov.abdm.eua.service.repository;

import in.gov.abdm.eua.service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
