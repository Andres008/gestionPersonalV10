package ec.mil.model.dao.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the aca_planificacion_curso database table.
 * 
 */
@Entity
@Table(name="aca_planificacion_curso")
@NamedQuery(name="AcaPlanificacionCurso.findAll", query="SELECT a FROM AcaPlanificacionCurso a")
public class AcaPlanificacionCurso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACA_PLANIFICACION_CURSO_ID_GENERATOR", sequenceName="SEQ_ACA_PLANIFICACION_CURSO", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACA_PLANIFICACION_CURSO_ID_GENERATOR")
	private long id;

	private BigDecimal cupo;
	
	private String observacion;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_final")
	private Date fechaFinal;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_final_inscripcion")
	private Date fechaFinalInscripcion;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_inicio_curso")
	private Date fechaInicioCurso;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_inicio_inscripcion")
	private Date fechaInicioInscripcion;

	//bi-directional many-to-one association to AcaInscripcionPersona
	@OneToMany(mappedBy="acaPlanificacionCurso")
	private List<AcaInscripcionPersona> acaInscripcionPersonas;

	//bi-directional many-to-one association to AcaCurso
	@ManyToOne
	@JoinColumn(name="id_curso")
	private AcaCurso acaCurso;

	//bi-directional many-to-one association to GesReparto
	@ManyToOne
	@JoinColumn(name="id_reparto")
	private GesReparto gesReparto;

	//bi-directional many-to-one association to AcaPrerequisitoCurso
	@OneToMany(mappedBy="acaPlanificacionCurso", cascade = CascadeType.ALL)
	private List<AcaPrerequisitoCurso> acaPrerequisitoCursos;

	//bi-directional many-to-one association to AcaPrerequisitoGrado
	@OneToMany(mappedBy="acaPlanificacionCurso", cascade = CascadeType.ALL)
	private List<AcaPrerequisitoGrado> acaPrerequisitoGrados;

	public AcaPlanificacionCurso() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getCupo() {
		return this.cupo;
	}

	public void setCupo(BigDecimal cupo) {
		this.cupo = cupo;
	}

	public Date getFechaFinal() {
		return this.fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public Date getFechaFinalInscripcion() {
		return this.fechaFinalInscripcion;
	}

	public void setFechaFinalInscripcion(Date fechaFinalInscripcion) {
		this.fechaFinalInscripcion = fechaFinalInscripcion;
	}

	public Date getFechaInicioCurso() {
		return this.fechaInicioCurso;
	}

	public void setFechaInicioCurso(Date fechaInicioCurso) {
		this.fechaInicioCurso = fechaInicioCurso;
	}

	public Date getFechaInicioInscripcion() {
		return this.fechaInicioInscripcion;
	}

	public void setFechaInicioInscripcion(Date fechaInicioInscripcion) {
		this.fechaInicioInscripcion = fechaInicioInscripcion;
	}

	public List<AcaInscripcionPersona> getAcaInscripcionPersonas() {
		return this.acaInscripcionPersonas;
	}

	public void setAcaInscripcionPersonas(List<AcaInscripcionPersona> acaInscripcionPersonas) {
		this.acaInscripcionPersonas = acaInscripcionPersonas;
	}

	public AcaInscripcionPersona addAcaInscripcionPersona(AcaInscripcionPersona acaInscripcionPersona) {
		getAcaInscripcionPersonas().add(acaInscripcionPersona);
		acaInscripcionPersona.setAcaPlanificacionCurso(this);

		return acaInscripcionPersona;
	}

	public AcaInscripcionPersona removeAcaInscripcionPersona(AcaInscripcionPersona acaInscripcionPersona) {
		getAcaInscripcionPersonas().remove(acaInscripcionPersona);
		acaInscripcionPersona.setAcaPlanificacionCurso(null);

		return acaInscripcionPersona;
	}

	public AcaCurso getAcaCurso() {
		return this.acaCurso;
	}

	public void setAcaCurso(AcaCurso acaCurso) {
		this.acaCurso = acaCurso;
	}

	public GesReparto getGesReparto() {
		return this.gesReparto;
	}

	public void setGesReparto(GesReparto gesReparto) {
		this.gesReparto = gesReparto;
	}

	public List<AcaPrerequisitoCurso> getAcaPrerequisitoCursos() {
		return this.acaPrerequisitoCursos;
	}

	public void setAcaPrerequisitoCursos(List<AcaPrerequisitoCurso> acaPrerequisitoCursos) {
		this.acaPrerequisitoCursos = acaPrerequisitoCursos;
	}

	public AcaPrerequisitoCurso addAcaPrerequisitoCurso(AcaPrerequisitoCurso acaPrerequisitoCurso) {
		getAcaPrerequisitoCursos().add(acaPrerequisitoCurso);
		acaPrerequisitoCurso.setAcaPlanificacionCurso(this);

		return acaPrerequisitoCurso;
	}

	public AcaPrerequisitoCurso removeAcaPrerequisitoCurso(AcaPrerequisitoCurso acaPrerequisitoCurso) {
		getAcaPrerequisitoCursos().remove(acaPrerequisitoCurso);
		acaPrerequisitoCurso.setAcaPlanificacionCurso(null);

		return acaPrerequisitoCurso;
	}

	public List<AcaPrerequisitoGrado> getAcaPrerequisitoGrados() {
		return this.acaPrerequisitoGrados;
	}

	public void setAcaPrerequisitoGrados(List<AcaPrerequisitoGrado> acaPrerequisitoGrados) {
		this.acaPrerequisitoGrados = acaPrerequisitoGrados;
	}

	public AcaPrerequisitoGrado addAcaPrerequisitoGrado(AcaPrerequisitoGrado acaPrerequisitoGrado) {
		getAcaPrerequisitoGrados().add(acaPrerequisitoGrado);
		acaPrerequisitoGrado.setAcaPlanificacionCurso(this);

		return acaPrerequisitoGrado;
	}

	public AcaPrerequisitoGrado removeAcaPrerequisitoGrado(AcaPrerequisitoGrado acaPrerequisitoGrado) {
		getAcaPrerequisitoGrados().remove(acaPrerequisitoGrado);
		acaPrerequisitoGrado.setAcaPlanificacionCurso(null);

		return acaPrerequisitoGrado;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

}