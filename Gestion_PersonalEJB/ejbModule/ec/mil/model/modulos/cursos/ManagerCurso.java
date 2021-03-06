package ec.mil.model.modulos.cursos;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import ec.mil.model.dao.entidades.AcaCurso;
import ec.mil.model.dao.entidades.AcaInscripcionPersona;
import ec.mil.model.dao.entidades.AcaInstitucionEducativa;
import ec.mil.model.dao.entidades.AcaPersonasCurso;
import ec.mil.model.dao.entidades.AcaPlanificacionCurso;
import ec.mil.model.dao.entidades.AcaPrerequisitoCurso;
import ec.mil.model.dao.entidades.AcaPrerequisitoGrado;
import ec.mil.model.dao.entidades.AcaTipoCurso;
import ec.mil.model.dao.entidades.AcaTipoTitulo;
import ec.mil.model.dao.entidades.AcaTitulo;
import ec.mil.model.dao.entidades.AcaTituloPersona;
import ec.mil.model.dao.entidades.GesEstimulo;
import ec.mil.model.dao.entidades.GesEstimuloPersona;
import ec.mil.model.dao.entidades.GesPersona;
import ec.mil.model.dao.manager.ManagerDAOGestionPersonal;

/**
 * Session Bean implementation class ManagerReportes
 */
@Stateless(mappedName = "managerCurso")
@LocalBean
public class ManagerCurso {

	@EJB
	ManagerDAOGestionPersonal managerDAOGestionPersonal;

	/**
	 * Default constructor.
	 */
	public ManagerCurso() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public List<AcaTipoCurso> findAllTipoCurso() throws Exception {
		try {
			return managerDAOGestionPersonal.findAll(AcaTipoCurso.class, "o.estado ASC, o.nombre ASC");
		} catch (Exception e) {
			throw new Exception("Error al buscar tipo curso.");
		}
	}

	public void ingresarTipoCurso(AcaTipoCurso objAcaTipoCurso) throws Exception {
		try {
			managerDAOGestionPersonal.insertar(objAcaTipoCurso);
		} catch (Exception e) {
			throw new Exception("Error al ingresar Tipo de Curso. " + e.getMessage());
		}
	}

	public void actualizarTipoCurso(AcaTipoCurso objAcatipoCursoAux) throws Exception {
		try {
			managerDAOGestionPersonal.actualizar(objAcatipoCursoAux);
		} catch (Exception e) {
			throw new Exception("Error al actuaizar objeto.");
		}

	}

	public void actualizarCurso(AcaCurso objAcatipoCursoAux) throws Exception {
		try {
			managerDAOGestionPersonal.actualizar(objAcatipoCursoAux);
		} catch (Exception e) {
			throw new Exception("Error al actuaizar objeto.");
		}

	}

	@SuppressWarnings("unchecked")
	public List<AcaCurso> findAllCurso() throws Exception {
		try {
			return managerDAOGestionPersonal.findAll(AcaCurso.class, "o.estado ASC, o.nombre ASC");
		} catch (Exception e) {
			throw new Exception("Error al buscar Cursos.");
		}
	}

	@SuppressWarnings("unchecked")
	public List<AcaTipoCurso> findTipoCursoActivo() throws Exception {
		try {
			return managerDAOGestionPersonal.findWhere(AcaTipoCurso.class, "o.estado='A'", "o.nombre ASC");
		} catch (Exception e) {
			throw new Exception("Error al buscar Tipo Curso Activo");
		}
	}

	public void ingresarCurso(AcaCurso objAcaCurso) throws Exception {
		try {
			managerDAOGestionPersonal.insertar(objAcaCurso);
		} catch (Exception e) {
			throw new Exception("Error al Ingresar Curso.");
		}

	}

	@SuppressWarnings("unchecked")
	public List<AcaCurso> findCursoActivo() throws Exception {
		try {
			return managerDAOGestionPersonal.findWhere(AcaCurso.class, "o.estado='A'", "o.nombre");
		} catch (Exception e) {
			throw new Exception("Error al buscar cursos.");
		}
	}

	public void ingresarCursoPersona(AcaPersonasCurso objAcaPersonasCurso) throws Exception {
		try {
			managerDAOGestionPersonal.insertar(objAcaPersonasCurso);
		} catch (Exception e) {
			throw new Exception("Error al ingresar Curso a Persona.");
		}
	}

	@SuppressWarnings("unchecked")
	public List<AcaTipoTitulo> findAllTipoTitulo() throws Exception {
		try {
			return managerDAOGestionPersonal.findAll(AcaTipoTitulo.class, "o.descripcion ASC");
		} catch (Exception e) {
			throw new Exception("Error al buscar AcaTipoTitulo");
		}
	}

	public void ingresarTipoTitulo(AcaTipoTitulo objAcaTipoTitulo) throws Exception {
		try {
			managerDAOGestionPersonal.insertar(objAcaTipoTitulo);
		} catch (Exception e) {
			throw new Exception("Error al ingresar tipo título");
		}

	}

	public void actualizarTipoTitulo(AcaTipoTitulo object) throws Exception {
		try {
			managerDAOGestionPersonal.actualizar(object);
		} catch (Exception e) {
			throw new Exception("Error al actualizar Tipo Titulo");
		}
	}

	@SuppressWarnings("unchecked")
	public List<AcaInstitucionEducativa> findAllInstituciones() throws Exception {
		return managerDAOGestionPersonal.findAll(AcaInstitucionEducativa.class, "o.estado ASC, o.nombre ASC");
	}

	public void ingresarInstitucion(AcaInstitucionEducativa objAcaInstitucionEducativa) throws Exception {
		managerDAOGestionPersonal.insertar(objAcaInstitucionEducativa);
	}

	public void actualizarInstituto(AcaInstitucionEducativa object) throws Exception {
		try {
			managerDAOGestionPersonal.actualizar(object);
		} catch (Exception e) {
			throw new Exception("Error al actualizar Instituto.");
		}

	}

	@SuppressWarnings("unchecked")
	public List<AcaTitulo> findAllTitulo() throws Exception {
		try {
			return managerDAOGestionPersonal.findAll(AcaTitulo.class,
					"o.titulo ASC, o.acaInstitucionEducativa.descripcion ASC ");
		} catch (Exception e) {
			throw new Exception("Error al buscar titulos.");
		}
	}

	public void ingresarTitulo(AcaTitulo objAcaTitulo) throws Exception {
		try {
			managerDAOGestionPersonal.insertar(objAcaTitulo);
		} catch (Exception e) {
			throw new Exception("Error al ingresar titulo. " + e.getMessage());
		}

	}

	@SuppressWarnings("unchecked")
	public List<AcaTitulo> findTituloActivo() throws Exception {
		try {
			return managerDAOGestionPersonal.findWhere(AcaTitulo.class, "o.estado='A'", "o.titulo");
		} catch (Exception e) {
			throw new Exception("Error al buscar titulos activos. " + e.getMessage());
		}
	}

	public void ingresarTituloPersona(AcaTituloPersona objAcaTituloPersona) throws Exception {
		try {
			managerDAOGestionPersonal.insertar(objAcaTituloPersona);
		} catch (Exception e) {
			throw new Exception("Error al ingresar titulo persona. " + e.getMessage());
		}

	}

	public void ingresarPlanificacionCurso(AcaPlanificacionCurso objAcaPlanificacionCurso) throws Exception {
		try {
			managerDAOGestionPersonal.insertar(objAcaPlanificacionCurso);
		} catch (Exception e) {
			throw new Exception("Error al ingresar Planificacion. " + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public List<AcaPlanificacionCurso> buscarTodasPlanificacion() throws Exception {
		try {
			List<AcaPlanificacionCurso> lstPlanificacion = managerDAOGestionPersonal
					.findAll(AcaPlanificacionCurso.class, "o.fechaInicioCurso DESC");
			lstPlanificacion.forEach(planificacion -> {
				planificacion.getAcaPrerequisitoCursos().forEach(curso -> {
					curso.getAcaCurso().getId();
				});
				planificacion.getAcaPrerequisitoGrados().forEach(grado -> {
					grado.getGesGrado().getId();
				});
				planificacion.getAcaInscripcionPersonas().forEach(inscritos -> {
					inscritos.getGesPersona().getCedula();
				});
				;
			});
			return lstPlanificacion;
		} catch (Exception e) {
			throw new Exception("Error al consultar cursos planificados. " + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public List<AcaPlanificacionCurso> buscarPlanificacionFinalizados() throws Exception {
		SimpleDateFormat fd = new SimpleDateFormat("dd-MM-yyyy");
		try {
			List<AcaPlanificacionCurso> lstPlanificacion = managerDAOGestionPersonal.findWhere(
					AcaPlanificacionCurso.class,
					"to_date('" + fd.format(new Date()) + "', 'dd-MM-yyyy') > o.fechaFinal", "o.fechaInicioCurso DESC");
			lstPlanificacion.forEach(planificacion -> {
				planificacion.getAcaPrerequisitoCursos().forEach(curso -> {
					curso.getAcaCurso().getId();
				});
				planificacion.getAcaPrerequisitoGrados().forEach(grado -> {
					grado.getGesGrado().getId();
				});
				planificacion.getAcaInscripcionPersonas().forEach(inscritos -> {
					inscritos.getGesPersona().getCedula();
				});
			});
			return lstPlanificacion;
		} catch (Exception e) {
			throw new Exception("Error al consultar cursos planificados. " + e.getMessage());
		}
	}

	public void actualizarPlanificacionCurso(AcaPlanificacionCurso objAcaPlanificacionCurso) throws Exception {
		try {
			managerDAOGestionPersonal.actualizar(objAcaPlanificacionCurso);
		} catch (Exception e) {
			throw new Exception("Error al actualizar planificacion. " + e.getMessage());
		}

	}

	public void eliminarGradoPrere(AcaPrerequisitoGrado prerequisitoCurso) throws Exception {
		try {
			managerDAOGestionPersonal.eliminar(AcaPrerequisitoGrado.class, prerequisitoCurso.getId());
		} catch (Exception e) {
			throw new Exception("Error al eliminar Prerequisito Grado. " + e.getMessage());
		}

	}

	public AcaPlanificacionCurso buscarPlanificacionById(long id) throws Exception {
		try {
			AcaPlanificacionCurso planificacion = (AcaPlanificacionCurso) managerDAOGestionPersonal
					.findById(AcaPlanificacionCurso.class, id);
			planificacion.getAcaPrerequisitoCursos().forEach(curso -> {
				curso.getAcaCurso().getId();
			});
			planificacion.getAcaPrerequisitoGrados().forEach(grado -> {
				grado.getGesGrado().getId();
			});
			return planificacion;
		} catch (Exception e) {
			throw new Exception("Error al buscar Planificacion. " + e.getMessage());
		}
	}

	public void eliminarCursoPrere(AcaPrerequisitoCurso prerequisitoCurso) throws Exception {
		try {
			managerDAOGestionPersonal.eliminar(AcaPrerequisitoCurso.class, prerequisitoCurso.getId());
		} catch (Exception e) {
			throw new Exception("Error al eliminar Prerequisito Curso. " + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public List<AcaPlanificacionCurso> buscarCursoDisponible() throws Exception {
		try {
			SimpleDateFormat fd = new SimpleDateFormat("dd-MM-yyyy");
			List<AcaPlanificacionCurso> lstPlanificacion = managerDAOGestionPersonal.findWhere(
					AcaPlanificacionCurso.class,
					"to_date('" + fd.format(new Date())
							+ "', 'dd-MM-yyyy') between o.fechaInicioInscripcion and o.fechaFinalInscripcion ",
					"o.fechaInicioCurso DESC");
			lstPlanificacion.forEach(planificacion -> {
				planificacion.getAcaPrerequisitoCursos().forEach(curso -> {
					curso.getAcaCurso().getId();
				});
				planificacion.getAcaPrerequisitoGrados().forEach(grado -> {
					grado.getGesGrado().getId();
				});
				planificacion.getAcaInscripcionPersonas().forEach(inscritos -> {
					inscritos.getGesPersona().getCedula();
				});
				;
			});
			System.out.println("Tamanio lista: "+lstPlanificacion.size());
			return lstPlanificacion;
		} catch (Exception e) {
			throw new Exception("Error al consultar cursos planificados. " + e.getMessage());
		}
	}

	public GesPersona buscarPersonaByCedula(String idUsuario) throws Exception {
		GesPersona persona = (GesPersona) managerDAOGestionPersonal.findById(GesPersona.class, idUsuario);
		if (persona == null)
			throw new Exception("Persona no existe");
		persona.getAcaPersonasCursos().forEach(cursos -> {
			cursos.getAcaCurso().getId();
		});
		return persona;
	}

	public void ingresarInscripcion(AcaInscripcionPersona inscripcion) throws Exception {
		try {
			managerDAOGestionPersonal.insertar(inscripcion);
		} catch (Exception e) {
			throw new Exception("Error al insertar inscripcion. " + e.getMessage());
		}

	}

	public void actualizarInscripcion(AcaInscripcionPersona inscripcion) throws Exception {
		try {
			managerDAOGestionPersonal.actualizar(inscripcion);
		} catch (Exception e) {
			throw new Exception("Error al actualizar inscripción. " + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public AcaCurso buscarCursoNA() throws Exception {
		List<AcaCurso> lstCurso = new ArrayList<AcaCurso>();
		lstCurso = managerDAOGestionPersonal.findWhere(AcaCurso.class, "o.nombre='N/A'", null);
		if (lstCurso.size() == 1)
			return lstCurso.get(0);
		throw new Exception("Parametro de curso N/A no encontrado.");
	}

	public AcaTitulo buscarTituloNA() throws Exception {
		@SuppressWarnings("unchecked")
		List<AcaTitulo> lstAcaTitulo = managerDAOGestionPersonal.findWhere(AcaTitulo.class, "o.titulo = 'N/A'", null);
		if (lstAcaTitulo.size() == 1)
			return lstAcaTitulo.get(0);
		throw new Exception("Parametro de título N/A no encontrado.");
	}

	public GesEstimulo buscarDisciplinaNA() throws Exception {
		@SuppressWarnings("unchecked")
		List<GesEstimulo> lstAcaTitulo = managerDAOGestionPersonal.findWhere(GesEstimulo.class, "o.nombre = 'N/A'",
				null);
		if (lstAcaTitulo.size() == 1)
			return lstAcaTitulo.get(0);
		throw new Exception("Parametro de disciplina N/A no encontrado.");
	}

	public AcaPersonasCurso buscarCursoPersonaNAByPersona(GesPersona gesPersona) throws Exception {
		@SuppressWarnings("unchecked")
		List<AcaPersonasCurso> lstAcaPersonaCur = managerDAOGestionPersonal.findWhere(AcaPersonasCurso.class,
				"o.gesPersona.cedula='" + gesPersona.getCedula() + "' and o.acaCurso.nombre='N/A'", null);
		if (lstAcaPersonaCur.size()==1)
			return lstAcaPersonaCur.get(0);
		return null;
	}

	public void eliminarCursoPersona(AcaPersonasCurso objAcaPersonasCu) throws Exception {
		managerDAOGestionPersonal.eliminar(AcaPersonasCurso.class, objAcaPersonasCu.getIdPersonasCursos());
	}

	public GesEstimuloPersona buscarEstimuloPersonaNA(GesPersona objGesEstimuloPersona2) throws Exception {
		@SuppressWarnings("unchecked")
		List<GesEstimuloPersona> lstGesEstimuloPersona = managerDAOGestionPersonal.findWhere(GesEstimuloPersona.class, 
				"o.gesPersona.cedula='"+objGesEstimuloPersona2.getCedula()+"' and o.gesEstimulo.nombre='N/A'",null);
		if(lstGesEstimuloPersona.size()==1)
			return lstGesEstimuloPersona.get(0);
		return null;
	}

}
