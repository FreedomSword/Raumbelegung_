package SWT2.repository;

import SWT2.model.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Integer> {



    //GetUserbyUsername

}
