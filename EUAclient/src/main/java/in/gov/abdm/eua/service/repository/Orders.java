package in.gov.abdm.eua.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Orders extends JpaRepository<in.gov.abdm.eua.service.model.Orders, Long> {
}
