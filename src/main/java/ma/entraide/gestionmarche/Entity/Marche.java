package ma.entraide.gestionmarche.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Marche {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "rubrique")
    private Rubrique rubrique;

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

    @OneToMany(mappedBy = "marche", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Decompte> decomptes = new ArrayList<>();

    // Helper method to add decompte and maintain relationship
    public void addDecompte(Decompte decompte) {
        decomptes.add(decompte);
        decompte.setMarche(this);
    }

    // Helper method to remove decompte and maintain relationship
    public void removeDecompte(Decompte decompte) {
        decomptes.remove(decompte);
        decompte.setMarche(null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Rubrique getRubrique() {
        return rubrique;
    }

    public void setRubrique(Rubrique rubrique) {
        this.rubrique = rubrique;
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

    public List<Decompte> getDecomptes() {
        return decomptes;
    }

    public void setDecomptes(List<Decompte> decomptes) {
        this.decomptes = decomptes;
    }
}