package ec.mil.model.dao.entidades;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the aca_prerequisito_curso database table.
 * 
 */
@Entity
@Table(name="aca_prerequisito_curso")
@NamedQuery(name="AcaPrerequisitoCurso.findAll", query="SELECT a FROM AcaPrerequisitoCurso a")
public class AcaPrerequisitoCurso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACA_PREREQUISITO_CURSO_ID_GENERATOR", sequenceName="SEQ_ACA_PREREQUISITO_CURSO", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACA_PREREQUISITO_CURSO_ID_GENERATOR")
	private long id;

	//bi-directional many-to-one association to AcaCurso
	@ManyToOne
	@JoinColumn(name="id_curso")
	private AcaCurso acaCurso;

	//bi-directional many-to-one association to AcaPlanificacionCurso
	@ManyToOne
	@JoinColumn(name="id_planificacion")
	private AcaPlanificacionCurso acaPlanificacionCurso;

	public AcaPrerequisitoCurso() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public AcaCurso getAcaCurso() {
		return this.acaCurso;
	}

	public void setAcaCurso(AcaCurso acaCurso) {
		this.acaCurso = acaCurso;
	}

	public AcaPlanificacionCurso getAcaPlanificacionCurso() {
		return this.acaPlanificacionCurso;
	}

	public void setAcaPlanificacionCurso(AcaPlanificacionCurso acaPlanificacionCurso) {
		this.acaPlanificacionCurso = acaPlanificacionCurso;
	}

}