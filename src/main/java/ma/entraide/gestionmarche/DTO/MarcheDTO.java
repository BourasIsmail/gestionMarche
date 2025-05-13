package ma.entraide.gestionmarche.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.entraide.gestionmarche.Entity.Decompte;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarcheDTO {
    private Long id;
    private Long rubriqueId;
    private String typeBudget;
    private String objet;
    private String modePassation;
    private String prestataire;
    private String reference;
    private double montantMarche;
    private String dateApprobation;
    private String dateOrdreServiceCommencement;
    private String datePvReceptionProvisoire;
    private String dateBureauOrdre;
    private List<DecompteDTO> decomptes = new ArrayList<>();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DecompteDTO {
        private Long id;
        private String nFacture;
        private long nDecompte;
        private String dateExecution;
        private double montantDecompte;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getnFacture() {
            return nFacture;
        }

        public void setnFacture(String nFacture) {
            this.nFacture = nFacture;
        }

        public long getnDecompte() {
            return nDecompte;
        }

        public void setnDecompte(long nDecompte) {
            this.nDecompte = nDecompte;
        }

        public String getDateExecution() {
            return dateExecution;
        }

        public void setDateExecution(String dateExecution) {
            this.dateExecution = dateExecution;
        }

        public double getMontantDecompte() {
            return montantDecompte;
        }

        public void setMontantDecompte(double montantDecompte) {
            this.montantDecompte = montantDecompte;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRubriqueId() {
        return rubriqueId;
    }

    public void setRubriqueId(Long rubriqueId) {
        this.rubriqueId = rubriqueId;
    }

    public String getTypeBudget() {
        return typeBudget;
    }

    public void setTypeBudget(String typeBudget) {
        this.typeBudget = typeBudget;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public String getModePassation() {
        return modePassation;
    }

    public void setModePassation(String modePassation) {
        this.modePassation = modePassation;
    }

    public String getPrestataire() {
        return prestataire;
    }

    public void setPrestataire(String prestataire) {
        this.prestataire = prestataire;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public double getMontantMarche() {
        return montantMarche;
    }

    public void setMontantMarche(double montantMarche) {
        this.montantMarche = montantMarche;
    }

    public String getDateApprobation() {
        return dateApprobation;
    }

    public void setDateApprobation(String dateApprobation) {
        this.dateApprobation = dateApprobation;
    }

    public String getDateOrdreServiceCommencement() {
        return dateOrdreServiceCommencement;
    }

    public void setDateOrdreServiceCommencement(String dateOrdreServiceCommencement) {
        this.dateOrdreServiceCommencement = dateOrdreServiceCommencement;
    }

    public String getDatePvReceptionProvisoire() {
        return datePvReceptionProvisoire;
    }

    public void setDatePvReceptionProvisoire(String datePvReceptionProvisoire) {
        this.datePvReceptionProvisoire = datePvReceptionProvisoire;
    }

    public String getDateBureauOrdre() {
        return dateBureauOrdre;
    }

    public void setDateBureauOrdre(String dateBureauOrdre) {
        this.dateBureauOrdre = dateBureauOrdre;
    }

    public List<DecompteDTO> getDecomptes() {
        return decomptes;
    }

    public void setDecomptes(List<DecompteDTO> decomptes) {
        this.decomptes = decomptes;
    }
}