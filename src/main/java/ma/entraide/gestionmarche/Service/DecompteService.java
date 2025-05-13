package ma.entraide.gestionmarche.Service;

import ma.entraide.gestionmarche.Entity.Decompte;
import ma.entraide.gestionmarche.Entity.Marche;
import ma.entraide.gestionmarche.DTO.DecompteWithMarcheDTO;
import ma.entraide.gestionmarche.Repository.DecompteRepository;
import ma.entraide.gestionmarche.Repository.MarcheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DecompteService {

    @Autowired
    private DecompteRepository decompteRepository;

    @Autowired
    private MarcheRepository marcheRepository;

    @Transactional
    public void deleteDecompte(Long id) {
        Decompte decompte = decompteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Decompte not found with id: " + id));

        // Get the parent Marche
        Marche marche = decompte.getMarche();

        if (marche != null) {
            // Remove the Decompte from the Marche's collection
            marche.removeDecompte(decompte);
            // Update the Marche
            marcheRepository.save(marche);
        }

        // Delete the Decompte
        decompteRepository.delete(decompte);
    }

    @Transactional(readOnly = true)
    public List<DecompteWithMarcheDTO> getAllDecomptes() {
        List<Decompte> decomptes = decompteRepository.findAll();
        return decomptes.stream()
                .map(this::convertToDecompteWithMarcheDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<DecompteWithMarcheDTO> getAllDecomptesPaginated(Pageable pageable) {
        Page<Decompte> decomptes = decompteRepository.findAll(pageable);

        List<DecompteWithMarcheDTO> dtoList = decomptes.getContent().stream()
                .map(this::convertToDecompteWithMarcheDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, decomptes.getTotalElements());
    }

    private DecompteWithMarcheDTO convertToDecompteWithMarcheDTO(Decompte decompte) {
        DecompteWithMarcheDTO dto = new DecompteWithMarcheDTO();

        // Set Decompte information
        dto.setId(decompte.getId());
        dto.setnFacture(decompte.getnFacture());
        dto.setnDecompte(decompte.getnDecompte());
        dto.setDateExecution(decompte.getDateExecution());
        dto.setMontantDecompte(decompte.getMontantDecompte());

        // Set Marche information if available
        Marche marche = decompte.getMarche();
        if (marche != null) {
            dto.setMarcheId(marche.getId());
            dto.setMarcheObjet(marche.getObjet());
            dto.setMarcheReference(marche.getReference());
            dto.setMarchePrestataire(marche.getPrestataire());
            dto.setMarcheMontantTotal(marche.getMontantMarche());
            dto.setMarcheTypeBudget(marche.getTypeBudget());

            // Set Rubrique information if available
            if (marche.getRubrique() != null) {
                dto.setRubriqueNom(marche.getRubrique().getRubrique());
                dto.setRubriqueNCompte(marche.getRubrique().getnCompte());
            }
        }

        return dto;
    }

    @Transactional(readOnly = true)
    public List<DecompteWithMarcheDTO> getDecomptesByMarcheId(Long marcheId) {
        Marche marche = marcheRepository.findById(marcheId)
                .orElseThrow(() -> new RuntimeException("Marche not found with id: " + marcheId));

        List<Decompte> decomptes = decompteRepository.findByMarche(marche);
        return decomptes.stream()
                .map(this::convertToDecompteWithMarcheDTO)
                .collect(Collectors.toList());
    }
}