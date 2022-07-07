package in.gov.abdm.eua.userManagement.repository;

import in.gov.abdm.eua.userManagement.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{

}
