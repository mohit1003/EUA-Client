package in.gov.abdm.eua.userManagement.repository;

import in.gov.abdm.eua.userManagement.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>{

}
