package ma.entraide.gestionmarche.Repository;

import ma.entraide.gestionmarche.Entity.Marche;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarcheRepository extends JpaRepository<Marche, Long> {
}