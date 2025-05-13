package ma.entraide.gestionmarche.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DecompteWithMarcheDTO {
    // Decompte information
    private Long id;
    private String nFacture;
    private long nDecompte;
    private String dateExecution;
    private double montantDecompte;

    // Marche information
    private Long marcheId;
    private String marcheObjet;
    private String marcheReference;
    private String marchePrestataire;
    private double marcheMontantTotal;
    private String marcheTypeBudget;

    // Additional useful information
    private String rubriqueNom;
    private String rubriqueNCompte;

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

    public Long getMarcheId() {
        return marcheId;
    }

    public void setMarcheId(Long marcheId) {
        this.marcheId = marcheId;
    }

    public String getMarcheObjet() {
        return marcheObjet;
    }

    public void setMarcheObjet(String marcheObjet) {
        this.marcheObjet = marcheObjet;
    }

    public String getMarcheReference() {
        return marcheReference;
    }

    public void setMarcheReference(String marcheReference) {
        this.marcheReference = marcheReference;
    }

    public String getMarchePrestataire() {
        return marchePrestataire;
    }

    public void setMarchePrestataire(String marchePrestataire) {
        this.marchePrestataire = marchePrestataire;
    }

    public double getMarcheMontantTotal() {
        return marcheMontantTotal;
    }

    public void setMarcheMontantTotal(double marcheMontantTotal) {
        this.marcheMontantTotal = marcheMontantTotal;
    }

    public String getMarcheTypeBudget() {
        return marcheTypeBudget;
    }

    public void setMarcheTypeBudget(String marcheTypeBudget) {
        this.marcheTypeBudget = marcheTypeBudget;
    }

    public String getRubriqueNom() {
        return rubriqueNom;
    }

    public void setRubriqueNom(String rubriqueNom) {
        this.rubriqueNom = rubriqueNom;
    }

    public String getRubriqueNCompte() {
        return rubriqueNCompte;
    }

    public void setRubriqueNCompte(String rubriqueNCompte) {
        this.rubriqueNCompte = rubriqueNCompte;
    }
}