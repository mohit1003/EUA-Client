package in.gov.abdm.uhi.EUABookingService.repository;

import in.gov.abdm.uhi.EUABookingService.Entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>{

}
