package deeroot.deeroot_shop.repositories;

import deeroot.deeroot_shop.domain.entities.MusicItem;
import jakarta.annotation.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicItemRepository extends JpaRepository<MusicItem, Long> {

}
