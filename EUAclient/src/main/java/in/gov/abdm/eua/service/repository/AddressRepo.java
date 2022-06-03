package in.gov.abdm.eua.service.repository;

import in.gov.abdm.eua.service.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo extends JpaRepository<Address, Long> {
}
