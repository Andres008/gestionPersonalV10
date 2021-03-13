package ec.mil.model.dao.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the v_reporte_consolidado database table.
 * 
 */
@Entity
@Table(name="v_reporte_consolidado")
@NamedQuery(name="VReporteConsolidado.findAll", query="SELECT v FROM VReporteConsolidado v")
public class VReporteConsolidado implements Serializable {
	private static final long serialVersionUID = 1L;

	private String cedula;

	private String dependencia;

	private String disciplina;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_final")
	private Date fechaFinal;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_inicial")
	private Date fechaInicial;

	private String grado;

	@Id
	private Long id;

	@Column(name="nombre_curso")
	private String nombreCurso;

	private String nombres;

	@Column(name="orden_grado")
	private BigDecimal ordenGrado;

	private String reparto;

	@Column(name="tipo_disciplina")
	private String tipoDisciplina;

	@Column(name="tipo_grado")
	private BigDecimal tipoGrado;

	@Column(name="tipo_titulo")
	private String tipoTitulo;

	private String titulo;

	public VReporteConsolidado() {
	}

	public String getCedula() {
		return this.cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getDependencia() {
		return this.dependencia;
	}

	public void setDependencia(String dependencia) {
		this.dependencia = dependencia;
	}

	public String getDisciplina() {
		return this.disciplina;
	}

	public void setDisciplina(String disciplina) {
		this.disciplina = disciplina;
	}

	public Date getFechaFinal() {
		return this.fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public Date getFechaInicial() {
		return this.fechaInicial;
	}

	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public String getGrado() {
		return this.grado;
	}

	public void setGrado(String grado) {
		this.grado = grado;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreCurso() {
		return this.nombreCurso;
	}

	public void setNombreCurso(String nombreCurso) {
		this.nombreCurso = nombreCurso;
	}

	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public BigDecimal getOrdenGrado() {
		return this.ordenGrado;
	}

	public void setOrdenGrado(BigDecimal ordenGrado) {
		this.ordenGrado = ordenGrado;
	}

	public String getReparto() {
		return this.reparto;
	}

	public void setReparto(String reparto) {
		this.reparto = reparto;
	}

	public String getTipoDisciplina() {
		return this.tipoDisciplina;
	}

	public void setTipoDisciplina(String tipoDisciplina) {
		this.tipoDisciplina = tipoDisciplina;
	}

	public BigDecimal getTipoGrado() {
		return this.tipoGrado;
	}

	public void setTipoGrado(BigDecimal tipoGrado) {
		this.tipoGrado = tipoGrado;
	}

	public String getTipoTitulo() {
		return this.tipoTitulo;
	}

	public void setTipoTitulo(String tipoTitulo) {
		this.tipoTitulo = tipoTitulo;
	}

	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

}