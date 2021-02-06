package ec.mil.model.dao.entidades;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the aca_inscripcion_persona database table.
 * 
 */
@Entity
@Table(name = "aca_inscripcion_persona")
@NamedQuery(name = "AcaInscripcionPersona.findAll", query = "SELECT a FROM AcaInscripcionPersona a")
public class AcaInscripcionPersona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "ACA_INSCRIPCION_PERSONA_ID_GENERATOR", sequenceName = "SEQ_ACA_INSCRIPCION_PERSONA", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACA_INSCRIPCION_PERSONA_ID_GENERATOR")
	private long id;
	
	private String observacion;

	// bi-directional many-to-one association to AcaPlanificacionCurso
	@ManyToOne
	@JoinColumn(name = "id_planificacion")
	private AcaPlanificacionCurso acaPlanificacionCurso;

	// bi-directional many-to-one association to GesPersona
	@ManyToOne
	@JoinColumn(name = "cedula")
	private GesPersona gesPersona;

	// bi-directional many-to-one association to AcaEstadoInscripcion
	@ManyToOne
	@JoinColumn(name = "id_estado_mat")
	private AcaEstadoInscripcion acaEstadoInscripcion;

	public AcaInscripcionPersona() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public AcaPlanificacionCurso getAcaPlanificacionCurso() {
		return this.acaPlanificacionCurso;
	}

	public void setAcaPlanificacionCurso(AcaPlanificacionCurso acaPlanificacionCurso) {
		this.acaPlanificacionCurso = acaPlanificacionCurso;
	}

	public GesPersona getGesPersona() {
		return this.gesPersona;
	}

	public void setGesPersona(GesPersona gesPersona) {
		this.gesPersona = gesPersona;
	}

	public AcaEstadoInscripcion getAcaEstadoInscripcion() {
		return acaEstadoInscripcion;
	}

	public void setAcaEstadoInscripcion(AcaEstadoInscripcion acaEstadoInscripcion) {
		this.acaEstadoInscripcion = acaEstadoInscripcion;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

}