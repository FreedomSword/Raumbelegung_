package SWT2.repository;

import SWT2.model.Gebaeude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GebaeudeRepository extends JpaRepository<Gebaeude, Long> {


}
