package ec.mil.model.dao.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the aca_titulo database table.
 * 
 */
@Entity
@Table(name="aca_titulo")
@NamedQuery(name="AcaTitulo.findAll", query="SELECT a FROM AcaTitulo a")
public class AcaTitulo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACA_TITULO_ID_GENERATOR", sequenceName="SEQ_ACA_TITULO", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACA_TITULO_ID_GENERATOR")
	private long id;

	private String estado;
	
	private String titulo;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_final")
	private Date fechaFinal;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_inicial")
	private Date fechaInicial;

	//bi-directional many-to-one association to AcaInstitucionEducativa
	@ManyToOne
	@JoinColumn(name="id_institucion")
	private AcaInstitucionEducativa acaInstitucionEducativa;

	//bi-directional many-to-one association to AcaTipoTitulo
	@ManyToOne
	@JoinColumn(name="id_tipo_titulo")
	private AcaTipoTitulo acaTipoTitulo;

	//bi-directional many-to-one association to AcaTituloPersona
	@OneToMany(mappedBy="acaTitulo")
	private List<AcaTituloPersona> acaTituloPersonas;

	public AcaTitulo() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
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

	public AcaInstitucionEducativa getAcaInstitucionEducativa() {
		return this.acaInstitucionEducativa;
	}

	public void setAcaInstitucionEducativa(AcaInstitucionEducativa acaInstitucionEducativa) {
		this.acaInstitucionEducativa = acaInstitucionEducativa;
	}

	public AcaTipoTitulo getAcaTipoTitulo() {
		return this.acaTipoTitulo;
	}

	public void setAcaTipoTitulo(AcaTipoTitulo acaTipoTitulo) {
		this.acaTipoTitulo = acaTipoTitulo;
	}

	public List<AcaTituloPersona> getAcaTituloPersonas() {
		return this.acaTituloPersonas;
	}

	public void setAcaTituloPersonas(List<AcaTituloPersona> acaTituloPersonas) {
		this.acaTituloPersonas = acaTituloPersonas;
	}

	public AcaTituloPersona addAcaTituloPersona(AcaTituloPersona acaTituloPersona) {
		getAcaTituloPersonas().add(acaTituloPersona);
		acaTituloPersona.setAcaTitulo(this);

		return acaTituloPersona;
	}

	public AcaTituloPersona removeAcaTituloPersona(AcaTituloPersona acaTituloPersona) {
		getAcaTituloPersonas().remove(acaTituloPersona);
		acaTituloPersona.setAcaTitulo(null);

		return acaTituloPersona;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

}