package in.gov.abdm.eua.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Payments extends JpaRepository<in.gov.abdm.eua.service.model.Payments, Long> {
}
