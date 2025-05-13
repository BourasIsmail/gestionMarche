package ma.entraide.gestionmarche.Controller;

import ma.entraide.gestionmarche.DTO.DecompteWithMarcheDTO;
import ma.entraide.gestionmarche.Service.DecompteService;
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
@RequestMapping("/api/decomptes")
public class DecompteController {

    @Autowired
    private DecompteService decompteService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDecompte(@PathVariable Long id) {
        try {
            decompteService.deleteDecompte(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<DecompteWithMarcheDTO>> getAllDecomptes() {
        List<DecompteWithMarcheDTO> decomptes = decompteService.getAllDecomptes();
        return new ResponseEntity<>(decomptes, HttpStatus.OK);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<DecompteWithMarcheDTO>> getAllDecomptesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ?
                Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        Page<DecompteWithMarcheDTO> decomptes = decompteService.getAllDecomptesPaginated(pageable);

        return new ResponseEntity<>(decomptes, HttpStatus.OK);
    }

    @GetMapping("/marche/{marcheId}")
    public ResponseEntity<List<DecompteWithMarcheDTO>> getDecomptesByMarcheId(@PathVariable Long marcheId) {
        try {
            List<DecompteWithMarcheDTO> decomptes = decompteService.getDecomptesByMarcheId(marcheId);
            return new ResponseEntity<>(decomptes, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}