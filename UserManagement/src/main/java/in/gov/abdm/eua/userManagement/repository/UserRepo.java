package in.gov.abdm.eua.userManagement.repository;

import in.gov.abdm.eua.userManagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByHealthIdNumber(String healthIdNumber);
}
