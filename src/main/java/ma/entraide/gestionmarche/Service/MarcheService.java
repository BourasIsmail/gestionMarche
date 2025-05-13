package ma.entraide.gestionmarche.Service;

import ma.entraide.gestionmarche.Entity.Decompte;
import ma.entraide.gestionmarche.Entity.Marche;
import ma.entraide.gestionmarche.Entity.Rubrique;
import ma.entraide.gestionmarche.DTO.MarcheDTO;
import ma.entraide.gestionmarche.Repository.MarcheRepository;
import ma.entraide.gestionmarche.Repository.RubriqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MarcheService {

    @Autowired
    private MarcheRepository marcheRepository;

    @Autowired
    private RubriqueRepository rubriqueRepository;

    @Transactional
    public Marche createMarcheWithDecomptes(MarcheDTO marcheDTO) {
        // Create new Marche
        Marche marche = new Marche();

        // Set basic properties
        marche.setTypeBudget(marcheDTO.getTypeBudget());
        marche.setObjet(marcheDTO.getObjet());
        marche.setModePassation(marcheDTO.getModePassation());
        marche.setPrestataire(marcheDTO.getPrestataire());
        marche.setReference(marcheDTO.getReference());
        marche.setMontantMarche(marcheDTO.getMontantMarche());
        marche.setDateApprobation(marcheDTO.getDateApprobation());
        marche.setDateOrdreServiceCommencement(marcheDTO.getDateOrdreServiceCommencement());
        marche.setDatePvReceptionProvisoire(marcheDTO.getDatePvReceptionProvisoire());
        marche.setDateBureauOrdre(marcheDTO.getDateBureauOrdre());

        // Set Rubrique if provided
        if (marcheDTO.getRubriqueId() != null) {
            Rubrique rubrique = rubriqueRepository.findById(marcheDTO.getRubriqueId())
                    .orElseThrow(() -> new RuntimeException("Rubrique not found with id: " + marcheDTO.getRubriqueId()));
            marche.setRubrique(rubrique);
        }

        // Add Decomptes if provided
        if (marcheDTO.getDecomptes() != null && !marcheDTO.getDecomptes().isEmpty()) {
            marcheDTO.getDecomptes().forEach(decompteDTO -> {
                Decompte decompte = new Decompte();
                decompte.setnFacture(decompteDTO.getnFacture());
                decompte.setnDecompte(decompteDTO.getnDecompte());
                decompte.setDateExecution(decompteDTO.getDateExecution());
                decompte.setMontantDecompte(decompteDTO.getMontantDecompte());

                marche.addDecompte(decompte);
            });
        }

        // Save and return the Marche with its Decomptes
        return marcheRepository.save(marche);
    }

    @Transactional
    public Marche updateMarche(Long id, MarcheDTO marcheDTO) {
        // Find existing Marche
        Marche existingMarche = marcheRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Marche not found with id: " + id));

        // Update basic properties
        existingMarche.setTypeBudget(marcheDTO.getTypeBudget());
        existingMarche.setObjet(marcheDTO.getObjet());
        existingMarche.setModePassation(marcheDTO.getModePassation());
        existingMarche.setPrestataire(marcheDTO.getPrestataire());
        existingMarche.setReference(marcheDTO.getReference());
        existingMarche.setMontantMarche(marcheDTO.getMontantMarche());
        existingMarche.setDateApprobation(marcheDTO.getDateApprobation());
        existingMarche.setDateOrdreServiceCommencement(marcheDTO.getDateOrdreServiceCommencement());
        existingMarche.setDatePvReceptionProvisoire(marcheDTO.getDatePvReceptionProvisoire());
        existingMarche.setDateBureauOrdre(marcheDTO.getDateBureauOrdre());

        // Update Rubrique if provided
        if (marcheDTO.getRubriqueId() != null) {
            Rubrique rubrique = rubriqueRepository.findById(marcheDTO.getRubriqueId())
                    .orElseThrow(() -> new RuntimeException("Rubrique not found with id: " + marcheDTO.getRubriqueId()));
            existingMarche.setRubrique(rubrique);
        }

        // Handle Decomptes updates
        if (marcheDTO.getDecomptes() != null) {
            // Clear existing decomptes to avoid duplicates
            existingMarche.getDecomptes().clear();

            // Add updated decomptes
            marcheDTO.getDecomptes().forEach(decompteDTO -> {
                Decompte decompte = new Decompte();

                // If decompte has an ID, it's an existing one
                if (decompteDTO.getId() != null) {
                    decompte.setId(decompteDTO.getId());
                }

                decompte.setnFacture(decompteDTO.getnFacture());
                decompte.setnDecompte(decompteDTO.getnDecompte());
                decompte.setDateExecution(decompteDTO.getDateExecution());
                decompte.setMontantDecompte(decompteDTO.getMontantDecompte());

                existingMarche.addDecompte(decompte);
            });
        }

        // Save and return the updated Marche
        return marcheRepository.save(existingMarche);
    }

    @Transactional
    public void deleteMarche(Long id) {
        // Check if Marche exists
        if (!marcheRepository.existsById(id)) {
            throw new RuntimeException("Marche not found with id: " + id);
        }

        // Delete Marche (cascade will handle Decomptes)
        marcheRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<MarcheDTO> getAllMarches() {
        return marcheRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<MarcheDTO> getAllMarchesPaginated(Pageable pageable) {
        return marcheRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    @Transactional(readOnly = true)
    public MarcheDTO getMarcheById(Long id) {
        Marche marche = marcheRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Marche not found with id: " + id));

        return convertToDTO(marche);
    }

    // Method to convert Marche to MarcheDTO for response
    public MarcheDTO convertToDTO(Marche marche) {
        MarcheDTO marcheDTO = new MarcheDTO();
        marcheDTO.setId(marche.getId());
        marcheDTO.setTypeBudget(marche.getTypeBudget());
        marcheDTO.setObjet(marche.getObjet());
        marcheDTO.setModePassation(marche.getModePassation());
        marcheDTO.setPrestataire(marche.getPrestataire());
        marcheDTO.setReference(marche.getReference());
        marcheDTO.setMontantMarche(marche.getMontantMarche());
        marcheDTO.setDateApprobation(marche.getDateApprobation());
        marcheDTO.setDateOrdreServiceCommencement(marche.getDateOrdreServiceCommencement());
        marcheDTO.setDatePvReceptionProvisoire(marche.getDatePvReceptionProvisoire());
        marcheDTO.setDateBureauOrdre(marche.getDateBureauOrdre());

        if (marche.getRubrique() != null) {
            marcheDTO.setRubriqueId(marche.getRubrique().getId());
        }

        if (marche.getDecomptes() != null) {
            marcheDTO.setDecomptes(marche.getDecomptes().stream()
                    .map(decompte -> {
                        MarcheDTO.DecompteDTO decompteDTO = new MarcheDTO.DecompteDTO();
                        decompteDTO.setId(decompte.getId());
                        decompteDTO.setnFacture(decompte.getnFacture());
                        decompteDTO.setnDecompte(decompte.getnDecompte());
                        decompteDTO.setDateExecution(decompte.getDateExecution());
                        decompteDTO.setMontantDecompte(decompte.getMontantDecompte());
                        return decompteDTO;
                    })
                    .collect(Collectors.toList()));
        }

        return marcheDTO;
    }
}