package in.gov.abdm.eua.userManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Orders extends JpaRepository<in.gov.abdm.eua.userManagement.model.Orders, Long> {
}
