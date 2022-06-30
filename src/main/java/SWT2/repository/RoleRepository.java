package SWT2.repository;

import SWT2.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("SELECT r from Role r WHERE r.name = :name")
    public Role findByName(@Param("name") String name);
}
