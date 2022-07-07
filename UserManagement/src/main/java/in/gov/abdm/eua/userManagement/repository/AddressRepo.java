package in.gov.abdm.eua.userManagement.repository;

import in.gov.abdm.eua.userManagement.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo extends JpaRepository<Address, Long> {
}
