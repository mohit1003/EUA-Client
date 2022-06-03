package in.gov.abdm.uhi.EUABookingService.repository;

import in.gov.abdm.uhi.EUABookingService.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserReposotory extends JpaRepository<User, Long>{
	
	List<User> findByHealthIdNumber(String abha_id);

}
