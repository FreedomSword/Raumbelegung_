package SWT2.repository;

import SWT2.model.Sensortype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SensortypeRepository extends JpaRepository<Sensortype, Integer> {


}
