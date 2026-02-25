package souk.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import souk.demo.model.AdModel;

@Repository
public interface AdRepository extends JpaRepository<AdModel, Long> {
    // You can add custom queries later, e.g. findByCategory, findByLocation
}
