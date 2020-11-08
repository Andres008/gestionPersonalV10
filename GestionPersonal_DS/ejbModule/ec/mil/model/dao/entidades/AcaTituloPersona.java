package ec.mil.model.dao.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the aca_titulo_persona database table.
 * 
 */
@Entity
@Table(name="aca_titulo_persona")
@NamedQuery(name="AcaTituloPersona.findAll", query="SELECT a FROM AcaTituloPersona a")
public class AcaTituloPersona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACA_TITULO_PERSONA_ID_GENERATOR", sequenceName="SEQ_ACA_TITULO_PERSONA", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACA_TITULO_PERSONA_ID_GENERATOR")
	private long id;

	@Column(name="codigo_registro")
	private String codigoRegistro;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_refrendacion")
	private Date fechaRefrendacion;

	//bi-directional many-to-one association to AcaTitulo
	@ManyToOne
	@JoinColumn(name="id_titulo")
	private AcaTitulo acaTitulo;

	//bi-directional many-to-one association to GesPersona
	@ManyToOne
	@JoinColumn(name="cedula")
	private GesPersona gesPersona;

	public AcaTituloPersona() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCodigoRegistro() {
		return this.codigoRegistro;
	}

	public void setCodigoRegistro(String codigoRegistro) {
		this.codigoRegistro = codigoRegistro;
	}

	public Date getFechaRefrendacion() {
		return this.fechaRefrendacion;
	}

	public void setFechaRefrendacion(Date fechaRefrendacion) {
		this.fechaRefrendacion = fechaRefrendacion;
	}

	public AcaTitulo getAcaTitulo() {
		return this.acaTitulo;
	}

	public void setAcaTitulo(AcaTitulo acaTitulo) {
		this.acaTitulo = acaTitulo;
	}

	public GesPersona getGesPersona() {
		return this.gesPersona;
	}

	public void setGesPersona(GesPersona gesPersona) {
		this.gesPersona = gesPersona;
	}

}