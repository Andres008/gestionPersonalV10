package ec.mil.model.dao.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ges_promocion database table.
 * 
 */
@Entity
@Table(name="ges_promocion")
@NamedQuery(name="GesPromocion.findAll", query="SELECT g FROM GesPromocion g")
public class GesPromocion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GES_PROMOCION_ID_GENERATOR", sequenceName="SEQ_GES_PROMOCION")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GES_PROMOCION_ID_GENERATOR")
	private long id;

	private BigDecimal antiguedad;

	private String descripcion;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_final")
	private Date fechaFinal;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_inicio")
	private Date fechaInicio;

	private String nombre;

	private BigDecimal promocion;

	//bi-directional many-to-one association to GesPersona
	@OneToMany(mappedBy="gesPromocion")
	private List<GesPersona> gesPersonas;

	//bi-directional many-to-one association to GesTipoGrado
	@ManyToOne
	@JoinColumn(name="id_grado")
	private GesTipoGrado gesTipoGrado;

	public GesPromocion() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getAntiguedad() {
		return this.antiguedad;
	}

	public void setAntiguedad(BigDecimal antiguedad) {
		this.antiguedad = antiguedad;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaFinal() {
		return this.fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getPromocion() {
		return this.promocion;
	}

	public void setPromocion(BigDecimal promocion) {
		this.promocion = promocion;
	}

	public List<GesPersona> getGesPersonas() {
		return this.gesPersonas;
	}

	public void setGesPersonas(List<GesPersona> gesPersonas) {
		this.gesPersonas = gesPersonas;
	}

	public GesPersona addGesPersona(GesPersona gesPersona) {
		getGesPersonas().add(gesPersona);
		gesPersona.setGesPromocion(this);

		return gesPersona;
	}

	public GesPersona removeGesPersona(GesPersona gesPersona) {
		getGesPersonas().remove(gesPersona);
		gesPersona.setGesPromocion(null);

		return gesPersona;
	}

	public GesTipoGrado getGesTipoGrado() {
		return this.gesTipoGrado;
	}

	public void setGesTipoGrado(GesTipoGrado gesTipoGrado) {
		this.gesTipoGrado = gesTipoGrado;
	}

}