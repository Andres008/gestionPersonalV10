package ec.mil.model.dao.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the aca_estado_inscripcion database table.
 * 
 */
@Entity
@Table(name="aca_estado_inscripcion")
@NamedQuery(name="AcaEstadoInscripcion.findAll", query="SELECT a FROM AcaEstadoInscripcion a")
public class AcaEstadoInscripcion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACA_ESTADO_INSCRIPCION_ID_GENERATOR", sequenceName="SEQ_ACA_ESTADO_INSCRIPCION")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACA_ESTADO_INSCRIPCION_ID_GENERATOR")
	private long id;

	private String descripcion;

	private String estado;

	//bi-directional many-to-one association to AcaInscripcionPersona
	@OneToMany(mappedBy="acaEstadoInscripcion")
	private List<AcaInscripcionPersona> acaInscripcionPersonas;

	public AcaEstadoInscripcion() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List<AcaInscripcionPersona> getAcaInscripcionPersonas() {
		return this.acaInscripcionPersonas;
	}

	public void setAcaInscripcionPersonas(List<AcaInscripcionPersona> acaInscripcionPersonas) {
		this.acaInscripcionPersonas = acaInscripcionPersonas;
	}

	public AcaInscripcionPersona addAcaInscripcionPersona(AcaInscripcionPersona acaInscripcionPersona) {
		getAcaInscripcionPersonas().add(acaInscripcionPersona);
		acaInscripcionPersona.setAcaEstadoInscripcion(this);

		return acaInscripcionPersona;
	}

	public AcaInscripcionPersona removeAcaInscripcionPersona(AcaInscripcionPersona acaInscripcionPersona) {
		getAcaInscripcionPersonas().remove(acaInscripcionPersona);
		acaInscripcionPersona.setAcaEstadoInscripcion(null);

		return acaInscripcionPersona;
	}

}