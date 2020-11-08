package ec.mil.model.dao.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the aca_institucion_educativa database table.
 * 
 */
@Entity
@Table(name="aca_institucion_educativa")
@NamedQuery(name="AcaInstitucionEducativa.findAll", query="SELECT a FROM AcaInstitucionEducativa a")
public class AcaInstitucionEducativa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACA_INSTITUCION_EDUCATIVA_ID_GENERATOR", sequenceName="SEQ_ACA_INSTITUCION_EDUCATIVA", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACA_INSTITUCION_EDUCATIVA_ID_GENERATOR")
	private long id;

	private String descripcion;

	private String estado;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_final")
	private Date fechaFinal;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_inicial")
	private Date fechaInicial;

	private String nombre;

	//bi-directional many-to-one association to AcaTitulo
	@OneToMany(mappedBy="acaInstitucionEducativa")
	private List<AcaTitulo> acaTitulos;

	public AcaInstitucionEducativa() {
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

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<AcaTitulo> getAcaTitulos() {
		return this.acaTitulos;
	}

	public void setAcaTitulos(List<AcaTitulo> acaTitulos) {
		this.acaTitulos = acaTitulos;
	}

	public AcaTitulo addAcaTitulo(AcaTitulo acaTitulo) {
		getAcaTitulos().add(acaTitulo);
		acaTitulo.setAcaInstitucionEducativa(this);

		return acaTitulo;
	}

	public AcaTitulo removeAcaTitulo(AcaTitulo acaTitulo) {
		getAcaTitulos().remove(acaTitulo);
		acaTitulo.setAcaInstitucionEducativa(null);

		return acaTitulo;
	}

}