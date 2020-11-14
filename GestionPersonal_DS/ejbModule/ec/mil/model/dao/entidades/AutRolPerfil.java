package ec.mil.model.dao.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the aut_rol_perfil database table.
 * 
 */
@Entity
@Table(name="aut_rol_perfil")
@NamedQuery(name="AutRolPerfil.findAll", query="SELECT a FROM AutRolPerfil a")
public class AutRolPerfil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="AUT_ROL_PERFIL_ID_GENERATOR", sequenceName="SEQ_AUT_ROL_PERFIL", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="AUT_ROL_PERFIL_ID_GENERATOR")
	private long id;

	//bi-directional many-to-one association to AutPerfile
	@ManyToOne
	@JoinColumn(name="id_perfil")
	private AutPerfile autPerfile;

	//bi-directional many-to-one association to AutRole
	@ManyToOne
	@JoinColumn(name="id_rol")
	private AutRole autRole;
	
	private String estado;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_final")
	private Date fechaFinal;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_inicial")
	private Date fechaInicial;

	public AutRolPerfil() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public AutPerfile getAutPerfile() {
		return this.autPerfile;
	}

	public void setAutPerfile(AutPerfile autPerfile) {
		this.autPerfile = autPerfile;
	}

	public AutRole getAutRole() {
		return this.autRole;
	}

	public void setAutRole(AutRole autRole) {
		this.autRole = autRole;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public Date getFechaInicial() {
		return fechaInicial;
	}

	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

}