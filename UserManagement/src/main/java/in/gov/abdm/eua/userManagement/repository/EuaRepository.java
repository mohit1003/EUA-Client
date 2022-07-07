<<<<<<< HEAD
<<<<<<< HEAD:UserManagement/src/main/java/in/gov/abdm/eua/userManagement/repository/EuaRepository.java
package in.gov.abdm.eua.userManagement.repository;

import in.gov.abdm.eua.userManagement.model.Message;
=======
package in.gov.abdm.eua.service.repository;

import in.gov.abdm.eua.service.model.Message;
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a:EUAclient/src/main/java/in/gov/abdm/eua/service/repository/EuaRepository.java
=======
package in.gov.abdm.eua.userManagement.repository;

import in.gov.abdm.eua.userManagement.model.Message;
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EuaRepository extends JpaRepository<Message, Long> {

    List<Optional<Message>> findByMessageIdAndDhpQueryType(String messageId, String DhpQueryType);

    List<Optional<Message>> findByMessageId(String messageId);
}
