package ec.mil.model.dao.entidades;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the aca_prerequisito_grado database table.
 * 
 */
@Entity
@Table(name="aca_prerequisito_grado")
@NamedQuery(name="AcaPrerequisitoGrado.findAll", query="SELECT a FROM AcaPrerequisitoGrado a")
public class AcaPrerequisitoGrado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACA_PREREQUISITO_GRADO_ID_GENERATOR", sequenceName="SEQ_ACA_PREREQUISITO_GRADO", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACA_PREREQUISITO_GRADO_ID_GENERATOR")
	private long id;

	//bi-directional many-to-one association to AcaPlanificacionCurso
	@ManyToOne
	@JoinColumn(name="id_planificacion")
	private AcaPlanificacionCurso acaPlanificacionCurso;

	//bi-directional many-to-one association to GesGrado
	@ManyToOne
	@JoinColumn(name="id_grado")
	private GesGrado gesGrado;

	public AcaPrerequisitoGrado() {
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

	public GesGrado getGesGrado() {
		return this.gesGrado;
	}

	public void setGesGrado(GesGrado gesGrado) {
		this.gesGrado = gesGrado;
	}

}