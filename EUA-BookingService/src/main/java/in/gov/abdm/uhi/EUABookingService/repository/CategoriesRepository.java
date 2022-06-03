package in.gov.abdm.uhi.EUABookingService.repository;

import in.gov.abdm.uhi.EUABookingService.Entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long>{
	
	List<Categories> findByCategoryId(Long categoryid);

}
