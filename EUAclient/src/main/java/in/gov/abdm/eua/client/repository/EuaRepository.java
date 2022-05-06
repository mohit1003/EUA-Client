package in.gov.abdm.eua.client.repository;

import in.gov.abdm.eua.client.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EuaRepository extends JpaRepository<Message, Long> {

    List<Optional<Message>> findByMessageIdAndDhpQueryType(String messageId, String DhpQueryType);

     List<Optional<Message>> findByMessageId(String messageId);
}
