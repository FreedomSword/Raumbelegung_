package SWT2.repository;

import SWT2.model.Gebaeude;
import SWT2.model.Sensortyp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensortypRepository extends JpaRepository<Sensortyp, Integer> {


}
