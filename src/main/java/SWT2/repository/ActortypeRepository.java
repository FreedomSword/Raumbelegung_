package SWT2.repository;


import SWT2.model.Actor;
import SWT2.model.Actortype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActortypeRepository extends JpaRepository<Actortype, Integer> {

    @Query("Select a From Actortype a WHERE a.name = ?1")
    Actortype findByName(String name);
}

