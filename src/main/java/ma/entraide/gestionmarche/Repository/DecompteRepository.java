package ma.entraide.gestionmarche.Repository;

import ma.entraide.gestionmarche.Entity.Decompte;
import ma.entraide.gestionmarche.Entity.Marche;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DecompteRepository extends JpaRepository<Decompte, Long> {
    // Find all Decomptes for a specific Marche
    List<Decompte> findByMarche(Marche marche);

    // Find all Decomptes for a specific Marche with pagination
    Page<Decompte> findByMarche(Marche marche, Pageable pageable);

    // Find Decomptes by invoice number
    List<Decompte> findByNFactureContaining(String nFacture);

    // Find Decomptes by decompte number
    List<Decompte> findByNDecompte(long nDecompte);

    // Find Decomptes with amount greater than a specified value
    List<Decompte> findByMontantDecompteGreaterThan(double montant);

    // Find Decomptes with amount less than a specified value
    List<Decompte> findByMontantDecompteLessThan(double montant);
}