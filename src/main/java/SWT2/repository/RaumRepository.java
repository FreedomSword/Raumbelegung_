package SWT2.repository;

import SWT2.model.Raum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaumRepository extends JpaRepository<Raum, Integer> {


}

