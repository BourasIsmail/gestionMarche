package ma.entraide.gestionmarche.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Decompte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nFacture;

    private long nDecompte;

    private String dateExecution;

    private double montantDecompte;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marche_id")
    @JsonBackReference
    private Marche marche;

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

    public Marche getMarche() {
        return marche;
    }

    public void setMarche(Marche marche) {
        this.marche = marche;
    }
}