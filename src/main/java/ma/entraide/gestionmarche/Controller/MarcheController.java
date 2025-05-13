package ma.entraide.gestionmarche.Controller;

import ma.entraide.gestionmarche.Entity.Marche;
import ma.entraide.gestionmarche.DTO.MarcheDTO;
import ma.entraide.gestionmarche.Service.MarcheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marches")
public class MarcheController {

    @Autowired
    private MarcheService marcheService;

    @PostMapping
    public ResponseEntity<MarcheDTO> createMarcheWithDecomptes(@RequestBody MarcheDTO marcheDTO) {
        Marche savedMarche = marcheService.createMarcheWithDecomptes(marcheDTO);
        return new ResponseEntity<>(marcheService.convertToDTO(savedMarche), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MarcheDTO> updateMarche(@PathVariable Long id, @RequestBody MarcheDTO marcheDTO) {
        try {
            Marche updatedMarche = marcheService.updateMarche(id, marcheDTO);
            return new ResponseEntity<>(marcheService.convertToDTO(updatedMarche), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMarche(@PathVariable Long id) {
        try {
            marcheService.deleteMarche(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<MarcheDTO>> getAllMarches() {
        List<MarcheDTO> marches = marcheService.getAllMarches();
        return new ResponseEntity<>(marches, HttpStatus.OK);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<MarcheDTO>> getAllMarchesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ?
                Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        Page<MarcheDTO> marches = marcheService.getAllMarchesPaginated(pageable);

        return new ResponseEntity<>(marches, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarcheDTO> getMarcheById(@PathVariable Long id) {
        try {
            MarcheDTO marche = marcheService.getMarcheById(id);
            return new ResponseEntity<>(marche, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}