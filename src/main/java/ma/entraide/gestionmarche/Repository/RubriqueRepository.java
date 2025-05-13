package ma.entraide.gestionmarche.Repository;

import ma.entraide.gestionmarche.Entity.Rubrique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RubriqueRepository extends JpaRepository<Rubrique, Long> {
    Optional<Rubrique> findByNCompte(String nCompte);
}
