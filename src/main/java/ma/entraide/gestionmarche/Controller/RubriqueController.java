package ma.entraide.gestionmarche.Controller;

import ma.entraide.gestionmarche.Entity.Rubrique;
import ma.entraide.gestionmarche.Repository.RubriqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rubriques")
public class RubriqueController {

    @Autowired
    private RubriqueRepository rubriqueRepository;

    @GetMapping
    public List<Rubrique> getAllRubriques() {
        return rubriqueRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rubrique> getRubriqueById(@PathVariable Long id) {
        Optional<Rubrique> rubrique = rubriqueRepository.findById(id);
        return rubrique.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/compte/{nCompte}")
    public ResponseEntity<Rubrique> getRubriqueByNCompte(@PathVariable String nCompte) {
        Optional<Rubrique> rubrique = rubriqueRepository.findByNCompte(nCompte);
        return rubrique.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Rubrique> createRubrique(@RequestBody Rubrique rubrique) {
        // Check if nCompte already exists
        if (rubriqueRepository.findByNCompte(rubrique.getnCompte()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Rubrique savedRubrique = rubriqueRepository.save(rubrique);
        return new ResponseEntity<>(savedRubrique, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rubrique> updateRubrique(@PathVariable Long id, @RequestBody Rubrique rubrique) {
        if (!rubriqueRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        // Check if nCompte already exists for another rubrique
        Optional<Rubrique> existingRubrique = rubriqueRepository.findByNCompte(rubrique.getnCompte());
        if (existingRubrique.isPresent() && !existingRubrique.get().getId().equals(id)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        rubrique.setId(id);
        Rubrique updatedRubrique = rubriqueRepository.save(rubrique);
        return ResponseEntity.ok(updatedRubrique);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRubrique(@PathVariable Long id) {
        if (!rubriqueRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        rubriqueRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}