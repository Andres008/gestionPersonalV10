package ec.mil.model.modulos.gestioPersonal;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import ec.mil.model.dao.entidades.AcaPersonasCurso;
import ec.mil.model.dao.entidades.AcaTituloPersona;
import ec.mil.model.dao.entidades.AutMenu;
import ec.mil.model.dao.entidades.AutPerfile;
import ec.mil.model.dao.entidades.AutRolPerfil;
import ec.mil.model.dao.entidades.AutRole;
import ec.mil.model.dao.entidades.GesDependencia;
import ec.mil.model.dao.entidades.GesDependenciaPersona;
import ec.mil.model.dao.entidades.GesEstadoCivil;
import ec.mil.model.dao.entidades.GesEstimulo;
import ec.mil.model.dao.entidades.GesEstimuloPersona;
import ec.mil.model.dao.entidades.GesGrado;
import ec.mil.model.dao.entidades.GesGradosPersona;
import ec.mil.model.dao.entidades.GesPersona;
import ec.mil.model.dao.entidades.GesPromocion;
import ec.mil.model.dao.entidades.GesRegione;
import ec.mil.model.dao.entidades.GesReparto;
import ec.mil.model.dao.entidades.GesTipoEstimulo;
import ec.mil.model.dao.entidades.GesTipoGrado;
import ec.mil.model.dao.entidades.GesTipoSangre;
import ec.mil.model.dao.manager.ManagerDAOGestionPersonal;

/**
 * Session Bean implementation class ManagerGestionPersonal
 */
@Stateless(mappedName = "managerGestionPersonal")
@LocalBean
public class ManagerGestionPersonal {

	@EJB
	private ManagerDAOGestionPersonal managerDAOGestionPersonal;

	/**
	 * Default constructor.
	 */
	public ManagerGestionPersonal() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public List<GesTipoEstimulo> buscarTipoEstimulo() throws Exception {
		List<GesTipoEstimulo> lstTipoEstimulo;
		try {
			lstTipoEstimulo = managerDAOGestionPersonal.findAll(GesTipoEstimulo.class, null);
		} catch (Exception e) {
			throw new Exception("Error al buscar lista Tipo estimulo.");
		}
		return lstTipoEstimulo;
	}

	@SuppressWarnings("unchecked")
	public List<GesPersona> buscarPersonas() throws Exception {
		try {
			return managerDAOGestionPersonal.findAll(GesPersona.class, "o.apellido ASC, o.nombre ASC ");
		} catch (Exception e) {
			throw new Exception("Error al buscar personas");
		}
	}

	@SuppressWarnings("unchecked")
	public List<GesEstadoCivil> buscarEstadoCivilActivo() throws Exception {
		try {
			return managerDAOGestionPersonal.findWhere(GesEstadoCivil.class, "o.estado='A'", "o.nombre ASC");
		} catch (Exception e) {
			throw new Exception("Error al buscar estado Civil");
		}
	}

	@SuppressWarnings("unchecked")
	public List<GesGrado> buscarGradoActivo() throws Exception {
		try {
			return managerDAOGestionPersonal.findWhere(GesGrado.class, "o.estado='A'",
					"o.gesTipoGrado.id ASC, o.orden ASC");
		} catch (Exception e) {
			throw new Exception("Error al buscar Grados.");
		}
	}

	@SuppressWarnings("unchecked")
	public List<GesTipoSangre> buscarTipoSangreActivo() throws Exception {
		try {
			return managerDAOGestionPersonal.findWhere(GesTipoSangre.class, "o.estado='A'", "o.nombre ASC");
		} catch (Exception e) {
			throw new Exception("Error al buscar tipos de sangre");
		}
	}

	public void ingresarPersona(GesPersona objGesPersona) throws Exception {
		try {
			managerDAOGestionPersonal.insertar(objGesPersona);
		} catch (Exception e) {
			throw new Exception("Error al ingresar persona. " + objGesPersona.getCedula());
		}

	}

	public GesPersona buscarPersonaByCedulaActivo(String cedula) throws Exception {
		try {
			SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
			GesPersona v_persona = (GesPersona) managerDAOGestionPersonal.findById(GesPersona.class, cedula);
			if (v_persona == null)
				throw new Exception("Atención, persona no existe.");
			if (v_persona.getFechaBaja() != null)
				throw new Exception(
						"Personal en servicio pasivo desde " + formatoFecha.format(v_persona.getFechaBaja()));
			// dummy Listado Cursos Persona
			v_persona.getAcaPersonasCursos().forEach(curso -> {
				curso.getAcaCurso().getAcaTipoCurso().getDescripcion();
			});
			// dummy Listado Estimulo Persona
			v_persona.getGesEstimuloPersonas().forEach(estimulo -> {

				estimulo.getGesEstimulo().getDescripcion();

			});
			// dummy Listado Pases Persona
			v_persona.getGesDependenciaPersonas().forEach(estimulo -> {

				estimulo.getGesDependencia().getDescripcion();

			});
			return v_persona;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public GesPersona buscarPersonaByCedula(String cedula) throws Exception {
		try {
			SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
			GesPersona v_persona = (GesPersona) managerDAOGestionPersonal.findById(GesPersona.class, cedula);
			if (v_persona == null)
				throw new Exception("Atención, persona no existe.");
			// dummy Listado Cursos Persona
			v_persona.getAcaPersonasCursos().forEach(curso -> {
				curso.getAcaCurso().getAcaTipoCurso().getDescripcion();
			});
			// dummy Listado Estimulo Persona
			v_persona.getGesEstimuloPersonas().forEach(estimulo -> {

				estimulo.getGesEstimulo().getDescripcion();

			});
			// dummy Listado Pases Persona
			v_persona.getGesDependenciaPersonas().forEach(estimulo -> {

				estimulo.getGesDependencia().getDescripcion();

			});
			//dummy Litado Grados
			v_persona.getGesGradosPersonas().forEach(grados->{
				grados.getGesGrado().getGesTipoGrado().getDescripcion();
			});
			return v_persona;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public List<AcaPersonasCurso> buscarTodoPersonaCurso() throws Exception {
		try {
			return managerDAOGestionPersonal.findAll(AcaPersonasCurso.class, "o.acaCurso.nombre ASC");
		} catch (Exception e) {
			throw new Exception("Error al buscar Curso Persona.");
		}
	}

	@SuppressWarnings("unchecked")
	public List<AcaTituloPersona> findAllAcaTituloPersona() throws Exception {
		try {
			return managerDAOGestionPersonal.findAll(AcaTituloPersona.class, "o.gesPersona.apellido ASC");
		} catch (Exception e) {
			throw new Exception("Error al buscar Titulos Persona");
		}
	}

	@SuppressWarnings("unchecked")
	public List<AutRole> findAllRol() throws Exception {
		try {
			return managerDAOGestionPersonal.findAll(AutRole.class, "o.estado ASC, o.nombre ASC");
		} catch (Exception e) {
			throw new Exception("Error al buscar roles. " + e.getMessage());
		}
	}

	public void ingresarRol(AutRole objAutRole) throws Exception {
		try {
			managerDAOGestionPersonal.insertar(objAutRole);
		} catch (Exception e) {
			throw new Exception("Error al guardar Rol. " + e.getMessage());
		}

	}

	public void actualizarAutRol(AutRole rol) throws Exception {
		try {
			managerDAOGestionPersonal.actualizar(rol);
		} catch (Exception e) {
			throw new Exception("Error al actualizar rol. " + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public List<AutPerfile> findAllPerfil() throws Exception {
		try {
			List<AutPerfile> lstAutPerfile = managerDAOGestionPersonal.findAll(AutPerfile.class,
					" o.autMenu.nombre ASC,o.estado ASC, o.nombre ASC");
			lstAutPerfile.forEach(perfil -> {
				perfil.getAutRolPerfils().forEach(rol -> {
					rol.getAutRole().getId();
				});
			});
			return lstAutPerfile;
		} catch (Exception e) {
			throw new Exception("Error al buscar Perfiles");
		}

	}

	@SuppressWarnings("unchecked")
	public List<AutPerfile> findAllPerfilActivos() throws Exception {
		try {
			List<AutPerfile> lstAutPerfile = managerDAOGestionPersonal.findWhere(AutPerfile.class, "o.estado='A'",
					" o.autMenu.nombre ASC,o.estado ASC, o.nombre ASC");
			lstAutPerfile.forEach(perfil -> {
				perfil.getAutRolPerfils().forEach(rol -> {
					rol.getAutRole().getId();
				});
			});
			return lstAutPerfile;
		} catch (Exception e) {
			throw new Exception("Error al buscar Perfiles");
		}

	}

	public void ingresarPerfil(AutPerfile objAutPerfile) throws Exception {
		try {
			managerDAOGestionPersonal.insertar(objAutPerfile);
		} catch (Exception e) {
			throw new Exception("Error al insertar perfil. " + e.getMessage());
		}

	}

	public void ingresarMenu(AutMenu objAutMenu) throws Exception {
		try {
			managerDAOGestionPersonal.actualizar(objAutMenu);
		} catch (Exception e) {
			throw new Exception("Error al ingresar Menú. " + e.getMessage());
		}

	}

	public void actualizarAutPerfil(AutPerfile perfil) throws Exception {
		try {
			managerDAOGestionPersonal.actualizar(perfil);
		} catch (Exception e) {
			throw new Exception("Error al actualizar ");
		}

	}

	public void ingresarRolPerfil(AutRolPerfil objRolPerfil) throws Exception {
		managerDAOGestionPersonal.insertar(objRolPerfil);

	}

	public void actualizarRolPerfil(AutRolPerfil rolPerfil) throws Exception {
		try {
			managerDAOGestionPersonal.actualizar(rolPerfil);
		} catch (Exception e) {
			throw new Exception("Error al actualizar Rol perfil. " + e.getMessage());
		}

	}

	@SuppressWarnings("unchecked")
	public List<GesDependencia> buscarAllGesDependencia() throws Exception {
		return managerDAOGestionPersonal.findAll(GesDependencia.class, "o.nombre ASC");
	}

	public void ingresarDependencia(GesDependencia objGesDependencia) throws Exception {
		try {
			managerDAOGestionPersonal.insertar(objGesDependencia);
		} catch (Exception e) {
			throw new Exception("Error al insertar Dependencia. " + e.getMessage());
		}

	}

	public void ingresarRegion(GesRegione objGesRegione) throws Exception {
		try {
			managerDAOGestionPersonal.insertar(objGesRegione);
		} catch (Exception e) {
			throw new Exception("Error al insertar Region. " + e.getMessage());
		}

	}

	public List<GesRegione> buscarRegionesActivas() throws Exception {
		return managerDAOGestionPersonal.findWhere(GesRegione.class, "o.estado='A'", "o.nombre ASC");
	}

	public void ingresarReparto(GesReparto objGesReparto) throws Exception {
		managerDAOGestionPersonal.insertar(objGesReparto);

	}

	public List<GesReparto> buscarRepartoActivo() throws Exception {
		// TODO Auto-generated method stub
		return managerDAOGestionPersonal.findWhere(GesReparto.class, "o.estado='A'", "o.nombre ASC");
	}

	public void actualizarPersona(GesPersona objGesPersona) throws Exception {
		try {
			managerDAOGestionPersonal.actualizar(objGesPersona);
		} catch (Exception e) {
			throw new Exception("Error al actualizar persona: " + objGesPersona.getCedula());
		}

	}

	@SuppressWarnings("unchecked")
	public List<GesTipoGrado> buscarTodosTipoGrado() throws Exception {
		return managerDAOGestionPersonal.findAll(GesTipoGrado.class, "o.id ASC");
	}

	@SuppressWarnings("unchecked")
	public List<GesPromocion> buscarPromocionByGrado(GesTipoGrado gesTipoGrado) throws Exception {
		return managerDAOGestionPersonal.findWhere(GesPromocion.class, "o.gesTipoGrado.id=" + gesTipoGrado.getId(),
				"o.promocion ASC");
	}

	@SuppressWarnings("unchecked")
	public List<GesDependenciaPersona> findAllGesDependenciaPersonal() throws Exception {
		try {
			return managerDAOGestionPersonal.findAll(GesDependenciaPersona.class, "o.gesDependencia.id ASC");
		} catch (Exception e) {
			throw new Exception("Error al buscar GesDependenciaPersona. " + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public List<GesDependencia> findDependenciasActivo() throws Exception {
		try {
			return managerDAOGestionPersonal.findWhere(GesDependencia.class, "o.estado='A'", "o.nombre");
		} catch (Exception e) {
			throw new Exception("Error al buscar dependencias activas. " + e.getMessage());
		}
	}

	public void ingresarDependenciaPersona(GesDependenciaPersona objGesDependenciaPersona) throws Exception {
		try {
			managerDAOGestionPersonal.insertar(objGesDependenciaPersona);
		} catch (Exception e) {
			throw new Exception("Error al ingresar pase persona. " + e.getMessage());
		}

	}

	@SuppressWarnings("unchecked")
	public List<GesDependenciaPersona> buscarDependenciaPersonaActiva(String cedula) throws Exception {
		try {
			return managerDAOGestionPersonal.findWhere(GesDependenciaPersona.class,
					"o.gesPersona.cedula ='" + cedula + "' and o.estado ='A'", null);
		} catch (Exception e) {
			throw new Exception("Error al buscar Pase activo.");
		}
	}

	public void actualizarGesDependenciaPersona(GesDependenciaPersona pase) throws Exception {
		try {
			managerDAOGestionPersonal.actualizar(pase);
		} catch (Exception e) {
			throw new Exception("Error al actualizar pase.");
		}

	}

	public void ingresarGesTipoEstimulo(GesTipoEstimulo objGesTipoEstimulo) throws Exception {
		try {
			managerDAOGestionPersonal.insertar(objGesTipoEstimulo);
		} catch (Exception e) {
			throw new Exception("Error al ingresar Tipo Estimulo. " + e.getMessage());
		}

	}

	@SuppressWarnings("unchecked")
	public List<GesTipoEstimulo> buscarTodoGesTipoEstimulo() throws Exception {
		try {
			return managerDAOGestionPersonal.findAll(GesTipoEstimulo.class, "o.nombre ASC");
		} catch (Exception e) {
			throw new Exception("Error al buscar GesTipoEstimulo. " + e.getMessage());
		}
	}

	public void actualizarTipoEstimulo(GesTipoEstimulo object) throws Exception {
		try {
			managerDAOGestionPersonal.actualizar(object);
		} catch (Exception e) {
			throw new Exception("Error al actualizar Tipo Estimulo." + e.getMessage());
		}

	}

	@SuppressWarnings("unchecked")
	public List<GesEstimulo> buscarAllEstimulos() throws Exception {
		try {
			return managerDAOGestionPersonal.findAll(GesEstimulo.class, "o.nombre");
		} catch (Exception e) {
			throw new Exception("Error al buscar GesEstimulo. " + e.getMessage());
		}
	}

	public List<GesTipoEstimulo> buscarTipoEstimuloActivo() throws Exception {
		try {
			return managerDAOGestionPersonal.findWhere(GesTipoEstimulo.class, "o.estado='A'", "o.nombre ASC");
		} catch (Exception e) {
			throw new Exception("Error al buscar Tipo Estimulo Activo. " + e.getMessage());
		}
	}

	public void ingresarGesEstimulo(GesEstimulo objGesEstimulo) throws Exception {
		try {
			managerDAOGestionPersonal.insertar(objGesEstimulo);
		} catch (Exception e) {
			throw new Exception("Error al insertar Estimulo. " + e.getMessage());
		}

	}

	@SuppressWarnings("unchecked")
	public List<GesEstimuloPersona> buscarAllEstimuloPersona() throws Exception {
		try {
			return managerDAOGestionPersonal.findAll(GesEstimuloPersona.class, "o.id ASC");
		} catch (Exception e) {
			throw new Exception("Error al nuscar Estimulo Persona. " + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public List<GesEstimulo> buscarAllEstimulosActivo() throws Exception {
		try {
			return managerDAOGestionPersonal.findWhere(GesEstimulo.class, "o.estado='A'", "o.nombre ASC");
		} catch (Exception e) {
			throw new Exception("Error al buscar Estimulos. " + e.getMessage());
		}
	}

	public void ingresarEstimuloPersona(GesEstimuloPersona objGesEstimuloPersona) throws Exception {
		try {
			managerDAOGestionPersonal.insertar(objGesEstimuloPersona);
		} catch (Exception e) {
			throw new Exception("Error al ingresar Estimulo Persona. " + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public List<GesGrado> buscarAllGrado() throws Exception {
		return managerDAOGestionPersonal.findAll(GesGrado.class, "o.gesTipoGrado.id ASC, o.orden DESC");
	}

	@SuppressWarnings("unchecked")
	public List<GesTipoGrado> buscarTipoGradoActivo() throws Exception {
		return managerDAOGestionPersonal.findWhere(GesTipoGrado.class, "o.estado='A'", "o.id ASC");
	}

	public void ingresarGrado(GesGrado objGesGrado) throws Exception {
		managerDAOGestionPersonal.insertar(objGesGrado);

	}

	@SuppressWarnings("unchecked")
	public List<GesGradosPersona> buscarAllGradoPersona() throws Exception {
		return managerDAOGestionPersonal.findAll(GesGradosPersona.class,
				"o.gesGrado.gesTipoGrado.id ASC, o.gesGrado.orden DESC");
	}

	@SuppressWarnings("unchecked")
	public List<GesGrado> buscarGradoByTipoOrden(GesGrado gesGrado) throws Exception {
		return  managerDAOGestionPersonal.findWhere(GesGrado.class, "o.gesTipoGrado.id="+gesGrado.getGesTipoGrado().getId()+" and o.orden>"+gesGrado.getOrden(), "o.gesTipoGrado.id ASC, o.orden DESC") ;
	}

	@SuppressWarnings("unchecked")
	public List<GesGradosPersona> buscarGradoPersonaActivo(String cedula) throws Exception {
		return managerDAOGestionPersonal.findWhere(GesGradosPersona.class, "o.gesPersona.cedula='"+cedula+"' and o.estado='A'", null);
	}

	public void actualizarGradoPersona(GesGradosPersona gesGradosPersona) throws Exception {
		managerDAOGestionPersonal.actualizar(gesGradosPersona);
		
	}

	public void ingresarGradoPersona(GesGradosPersona objGesGradosPersona) throws Exception {
		managerDAOGestionPersonal.insertar(objGesGradosPersona);
		
	}

}
