package deeroot.deeroot_shop.repositories;

import deeroot.deeroot_shop.domain.entities.Role;
import deeroot.deeroot_shop.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String email);
}
