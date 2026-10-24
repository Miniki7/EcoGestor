package api.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "indicadores_ambientais")
public class IndicadorAmbiental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "consumo_agua", nullable = false, precision = 10, scale = 2)
    private BigDecimal consumoAgua;

    @Column(name = "consumo_energia", nullable = false, precision = 10, scale = 2)
    private BigDecimal consumoEnergia;

    @Column(name = "residuos_gerados", nullable = false, precision = 10, scale = 2)
    private BigDecimal residuosGerados;

    @Column(name = "residuos_reciclados", nullable = false, precision = 10, scale = 2)
    private BigDecimal residuosReciclados;

    @Column(name = "mes_referencia", nullable = false)
    private LocalDate mesReferencia;

    @Column(columnDefinition = "text")
    private String observacao;

    @Column(name = "data_cadastro", updatable = false)
    private LocalDateTime dataCadastro;

    protected IndicadorAmbiental() {
    }

    public IndicadorAmbiental(Usuario usuario, BigDecimal consumoAgua, BigDecimal consumoEnergia,
                              BigDecimal residuosGerados, BigDecimal residuosReciclados,
                              LocalDate mesReferencia, String observacao) {
        this.usuario = usuario;
        this.consumoAgua = consumoAgua;
        this.consumoEnergia = consumoEnergia;
        this.residuosGerados = residuosGerados;
        this.residuosReciclados = residuosReciclados;
        this.mesReferencia = mesReferencia;
        this.observacao = observacao;
    }

    @PrePersist
    private void definirDataCadastro() {
        if (dataCadastro == null) {
            dataCadastro = LocalDateTime.now();
        }
    }

    public Integer getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getConsumoAgua() {
        return consumoAgua;
    }

    public void setConsumoAgua(BigDecimal consumoAgua) {
        this.consumoAgua = consumoAgua;
    }

    public BigDecimal getConsumoEnergia() {
        return consumoEnergia;
    }

    public void setConsumoEnergia(BigDecimal consumoEnergia) {
        this.consumoEnergia = consumoEnergia;
    }

    public BigDecimal getResiduosGerados() {
        return residuosGerados;
    }

    public void setResiduosGerados(BigDecimal residuosGerados) {
        this.residuosGerados = residuosGerados;
    }

    public BigDecimal getResiduosReciclados() {
        return residuosReciclados;
    }

    public void setResiduosReciclados(BigDecimal residuosReciclados) {
        this.residuosReciclados = residuosReciclados;
    }

    public LocalDate getMesReferencia() {
        return mesReferencia;
    }

    public void setMesReferencia(LocalDate mesReferencia) {
        this.mesReferencia = mesReferencia;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }
}