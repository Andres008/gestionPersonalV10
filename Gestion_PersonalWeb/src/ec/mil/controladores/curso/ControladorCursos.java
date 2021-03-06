/**
 * 
 */
package ec.mil.controladores.curso;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.event.RowEditEvent;

import ec.mil.controladores.session.BeanLogin;
import ec.mil.model.dao.entidades.AcaCurso;
import ec.mil.model.dao.entidades.AcaEstadoInscripcion;
import ec.mil.model.dao.entidades.AcaInscripcionPersona;
import ec.mil.model.dao.entidades.AcaInstitucionEducativa;
import ec.mil.model.dao.entidades.AcaPersonasCurso;
import ec.mil.model.dao.entidades.AcaPlanificacionCurso;
import ec.mil.model.dao.entidades.AcaPrerequisitoCurso;
import ec.mil.model.dao.entidades.AcaPrerequisitoGrado;
import ec.mil.model.dao.entidades.AcaTipoCurso;
import ec.mil.model.dao.entidades.AcaTipoTitulo;
import ec.mil.model.dao.entidades.AcaTitulo;
import ec.mil.model.dao.entidades.GesGrado;
import ec.mil.model.dao.entidades.GesPersona;
import ec.mil.model.dao.entidades.GesReparto;
import ec.mil.model.modulos.ModelUtil.JSFUtil;
import ec.mil.model.modulos.ModelUtil.ModelUtil;
import ec.mil.model.modulos.cursos.ManagerCurso;
import ec.mil.model.modulos.gestioPersonal.ManagerGestionPersonal;
import ec.mil.model.modulos.log.ManagerLog;

@ManagedBean
@SessionScoped
public class ControladorCursos {

	@EJB
	ManagerCurso managerCurso;
	@EJB
	ManagerLog managerLog;
	private AcaTipoCurso objAcaTipoCurso;
	private List<AcaTipoCurso> lstAcaTipoCurso;
	private AcaCurso objAcaCurso;
	private List<AcaCurso> lstAcaCurso;
	private AcaTipoTitulo objAcaTipoTitulo;
	private List<AcaTipoTitulo> lstAcaTipoTitulo;
	private AcaInstitucionEducativa objAcaInstitucionEducativa;
	private AcaTitulo objAcaTitulo;
	private List<AcaTitulo> lstAcaTitulo;
	private List<AcaInstitucionEducativa> lstAcaInstitucionEducativa;
	private List<AcaPlanificacionCurso> lstAcaPlanificacionCurso;
	private AcaPlanificacionCurso objAcaPlanificacionCurso;
	private AcaInscripcionPersona objAcaInscripcionPersona;

	@ManagedProperty(value = "#{beanLogin}")
	private BeanLogin beanLogin;

	public ControladorCursos() {
		// TODO Auto-generated constructor stub
	}

	public void inicializarCursoDisponible() {
		try {
			beanLogin.verificarCredencial();
			lstAcaPlanificacionCurso = managerCurso.buscarCursoDisponible();
			lstAcaPlanificacionCurso = verificarPrerequisitoGrado(lstAcaPlanificacionCurso);
			lstAcaPlanificacionCurso = verificarPrerequisitoCursos(lstAcaPlanificacionCurso);
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			e.printStackTrace();
		}
	}

	public List<AcaPlanificacionCurso> verificarPrerequisitoGrado(List<AcaPlanificacionCurso> lstPlanifCur)
			throws Exception {
		List<AcaPlanificacionCurso> lstAuxPlaniCurso = new ArrayList<AcaPlanificacionCurso>();
		boolean grado;
		GesPersona persona = managerCurso.buscarPersonaByCedula(beanLogin.getCredencial().getIdUsuario());
		for (AcaPlanificacionCurso acaPlanificacionCurso : lstPlanifCur) {
			grado = false;
			if (acaPlanificacionCurso.getAcaPrerequisitoGrados().isEmpty())
				grado = true;
			else {
				for (AcaPrerequisitoGrado prerequisitoGrado : acaPlanificacionCurso.getAcaPrerequisitoGrados()) {
					if (prerequisitoGrado.getGesGrado().getId() == persona.getGesGrado().getId())
						grado = true;
				}
			}
			if (grado)
				lstAuxPlaniCurso.add(acaPlanificacionCurso);
		}
		return lstAuxPlaniCurso;
	}

	public void ingresarAceptacionInscripcion(AcaInscripcionPersona inscripcion) {
		inscripcion.getAcaEstadoInscripcion().setId(3);
		try {
			managerCurso.actualizarInscripcion(inscripcion);
			inicializarAcaPlanificacionCurso();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			e.printStackTrace();
		}

	}

	public void ingresarAprobacionCurso(AcaInscripcionPersona inscripcion) {
		inscripcion.getAcaEstadoInscripcion().setId(4);
		try {
			managerCurso.actualizarInscripcion(inscripcion);
			AcaPersonasCurso objCursoPersona = new AcaPersonasCurso();
			objCursoPersona.setGesPersona(inscripcion.getGesPersona());
			objCursoPersona.setAcaCurso(inscripcion.getAcaPlanificacionCurso().getAcaCurso());
			objCursoPersona.setFechaInicial(inscripcion.getAcaPlanificacionCurso().getFechaInicioCurso());
			objCursoPersona.setFechaFinal(inscripcion.getAcaPlanificacionCurso().getFechaFinal());
			objCursoPersona.setFechaFinal(new Date());
			managerCurso.ingresarCursoPersona(objCursoPersona);
			inicializarAcaPlanificacionFinalizados();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			e.printStackTrace();
		}

	}

	public void ingresarReprobacionCurso(AcaInscripcionPersona inscripcion) {
		inscripcion.getAcaEstadoInscripcion().setId(5);
		try {
			managerCurso.actualizarInscripcion(inscripcion);
			inicializarAcaPlanificacionFinalizados();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			e.printStackTrace();
		}

	}

	public void regresarAceptacionInscripcion(AcaInscripcionPersona inscripcion) {
		inscripcion.getAcaEstadoInscripcion().setId(1);
		inscripcion.setObservacion("");
		try {
			managerCurso.actualizarInscripcion(inscripcion);
			inicializarAcaPlanificacionCurso();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			e.printStackTrace();
		}

	}

	public void cargarInscripcion(AcaInscripcionPersona inscripcion) {
		System.out.println("Llega");
		objAcaInscripcionPersona = inscripcion;
		System.out.println("Pasa.");
	}

	public void ingresarNegacionInscripcion() {
		objAcaInscripcionPersona.getAcaEstadoInscripcion().setId(2);
		try {
			if (objAcaInscripcionPersona.getObservacion().isEmpty()) {
				inicializarAcaPlanificacionCurso();
				throw new Exception("Debe ingresar la observación de la negación de la inscripción.");
			}
			managerCurso.actualizarInscripcion(objAcaInscripcionPersona);
			inicializarAcaPlanificacionCurso();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			e.printStackTrace();
		}

	}

	public void ingresarInscripcionCurso(AcaPlanificacionCurso planificacionCurso) {
		System.out.println("Llega Inscripcion");
		try {
			AcaInscripcionPersona inscripcion = new AcaInscripcionPersona();
			inscripcion.setAcaPlanificacionCurso(planificacionCurso);
			inscripcion.setAcaEstadoInscripcion(new AcaEstadoInscripcion());
			inscripcion.getAcaEstadoInscripcion().setId(1);
			inscripcion.setGesPersona(managerCurso.buscarPersonaByCedula(beanLogin.getCredencial().getIdUsuario()));
			managerCurso.ingresarInscripcion(inscripcion);
			managerLog.generarLogUsabilidad(beanLogin.getCredencial(), this.getClass(), "ingresarInscripcionCurso",
					"Se inscribio: " + inscripcion.getId());
			JSFUtil.crearMensajeINFO("Atención", "Inscripcion Realizada.");
			inicializarCursoDisponible();
			System.out.println("Inscripcion Realizada");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			e.printStackTrace();
		}
	}

	public List<AcaPlanificacionCurso> verificarPrerequisitoCursos(List<AcaPlanificacionCurso> lstPlanifCur)
			throws Exception {
		List<AcaPlanificacionCurso> lstAuxPlaniCurso = new ArrayList<AcaPlanificacionCurso>();
		boolean curso, cursoPersona;
		GesPersona persona = managerCurso.buscarPersonaByCedula(beanLogin.getCredencial().getIdUsuario());
		/* for de los cursos planificacos */
		for (AcaPlanificacionCurso acaPlanificacionCurso : lstPlanifCur) {
			curso = true;
			/* for de los prerequisitos cursos para el curso planificado */
			for (AcaPrerequisitoCurso prerequisitoCurso : acaPlanificacionCurso.getAcaPrerequisitoCursos()) {
				cursoPersona = true;
				/* For para recorrer los cursos ue tiene la persona */
				for (AcaPersonasCurso acaPersonaCurso : persona.getAcaPersonasCursos()) {
					/* Si */
					if (acaPersonaCurso.getAcaCurso().getId() == prerequisitoCurso.getAcaCurso().getId())
						cursoPersona = false;
				}
				if (cursoPersona)
					curso = false;
			}
			if (curso)
				lstAuxPlaniCurso.add(acaPlanificacionCurso);
		}
		return lstAuxPlaniCurso;
	}

	public void inicializarTipoTitulo() {
		try {
			beanLogin.verificarCredencial();
			objAcaTipoTitulo = new AcaTipoTitulo();
			lstAcaTipoTitulo = managerCurso.findAllTipoTitulo();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
		}
	}

	public void inicializarTitulo() {
		try {
			beanLogin.verificarCredencial();
			objAcaTitulo = new AcaTitulo();
			objAcaTitulo.setAcaTipoTitulo(new AcaTipoTitulo());
			objAcaTitulo.setAcaInstitucionEducativa(new AcaInstitucionEducativa());
			lstAcaTitulo = managerCurso.findAllTitulo();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
		}

	}

	public void inicializarAcaCurso() {
		try {
			beanLogin.verificarCredencial();
			objAcaCurso = new AcaCurso();
			objAcaCurso.setAcaTipoCurso(new AcaTipoCurso());
			lstAcaCurso = managerCurso.findAllCurso();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
		}
	}

	public List<SelectItem> SICursos() {
		List<SelectItem> siLstCursos = new ArrayList<SelectItem>();
		;
		try {
			for (AcaCurso acaCurso : managerCurso.findAllCurso()) {
				SelectItem siCurso = new SelectItem();
				siCurso.setLabel(acaCurso.getNombre());
				siCurso.setValue(acaCurso.getId());
				siLstCursos.add(siCurso);
			}
			return siLstCursos;
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", "Atención, no se pudo buscar lista de cursos.");
			e.printStackTrace();
			return null;
		}
	}

	public void inicializarAcaPlanificacionCurso() {
		try {
			beanLogin.verificarCredencial();
			objAcaInscripcionPersona = new AcaInscripcionPersona();
			objAcaPlanificacionCurso = new AcaPlanificacionCurso();
			objAcaPlanificacionCurso.setAcaCurso(new AcaCurso());
			objAcaPlanificacionCurso.setGesReparto(new GesReparto());
			objAcaPlanificacionCurso.setAcaPrerequisitoCursos(new ArrayList<AcaPrerequisitoCurso>());
			objAcaPlanificacionCurso.setAcaPrerequisitoGrados(new ArrayList<AcaPrerequisitoGrado>());
			lstAcaPlanificacionCurso = managerCurso.buscarTodasPlanificacion();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			e.printStackTrace();
		}
	}

	public void inicializarAcaPlanificacionCursoAceptados() {
		try {
			beanLogin.verificarCredencial();
			lstAcaPlanificacionCurso = managerCurso.buscarTodasPlanificacion();
			lstAcaPlanificacionCurso.forEach(planificados -> {
				planificados.setAcaInscripcionPersonas(planificados.getAcaInscripcionPersonas().stream()
						.filter(estado -> estado.getAcaEstadoInscripcion().getId() == 3).collect(Collectors.toList()));
			});
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			e.printStackTrace();
		}
	}

	public void inicializarAcaPlanificacionFinalizados() {
		objAcaInscripcionPersona = new AcaInscripcionPersona();
		objAcaPlanificacionCurso = new AcaPlanificacionCurso();
		objAcaPlanificacionCurso.setAcaCurso(new AcaCurso());
		objAcaPlanificacionCurso.setGesReparto(new GesReparto());
		objAcaPlanificacionCurso.setAcaPrerequisitoCursos(new ArrayList<AcaPrerequisitoCurso>());
		objAcaPlanificacionCurso.setAcaPrerequisitoGrados(new ArrayList<AcaPrerequisitoGrado>());
		try {
			beanLogin.verificarCredencial();
			lstAcaPlanificacionCurso = managerCurso.buscarPlanificacionFinalizados();
			for (AcaPlanificacionCurso acaCurso : lstAcaPlanificacionCurso) {
				acaCurso.setAcaInscripcionPersonas(acaCurso.getAcaInscripcionPersonas().stream()
						.filter(inscripcion -> inscripcion.getAcaEstadoInscripcion().getId() > 2)
						.collect(Collectors.toList()));
			}
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			e.printStackTrace();
		}
	}

	public void inicializarInstituciones() {
		try {
			beanLogin.verificarCredencial();
			objAcaInstitucionEducativa = new AcaInstitucionEducativa();
			lstAcaInstitucionEducativa = managerCurso.findAllInstituciones();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			e.printStackTrace();
		}
	}

	public List<SelectItem> tipoTituloSI() {
		List<SelectItem> tipoTituloSI = new ArrayList<SelectItem>();
		inicializarTipoTitulo();
		lstAcaTipoTitulo.stream().filter(x -> x.getEstado().equals("A")).forEach(tipoTitulo -> {
			SelectItem siTipoTitulo = new SelectItem();
			siTipoTitulo.setValue(tipoTitulo.getId());
			siTipoTitulo.setLabel(tipoTitulo.getNombre());
			tipoTituloSI.add(siTipoTitulo);
		});
		return tipoTituloSI;
	}

	public List<SelectItem> institucionesEducativasSI() {
		List<SelectItem> institucionSI = new ArrayList<SelectItem>();
		inicializarInstituciones();
		lstAcaInstitucionEducativa.stream().filter(x -> x.getEstado().equals("A")).forEach(institucion -> {
			SelectItem siTipoTitulo = new SelectItem();
			siTipoTitulo.setValue(institucion.getId());
			siTipoTitulo.setLabel(institucion.getNombre());
			institucionSI.add(siTipoTitulo);
		});
		return institucionSI;
	}

	public List<SelectItem> siTipoCursoActivo() {
		List<SelectItem> lstSiTipoCurso = new ArrayList<SelectItem>();
		try {
			List<AcaTipoCurso> lstTipoCursoAux = managerCurso.findTipoCursoActivo();
			for (AcaTipoCurso acaTipoCurso : lstTipoCursoAux) {
				SelectItem siAcaCurso = new SelectItem(acaTipoCurso.getId(), acaTipoCurso.getNombre());
				lstSiTipoCurso.add(siAcaCurso);
			}
			return lstSiTipoCurso;
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			e.printStackTrace();
			return null;
		}

	}

	public void cargarCursoSeleccion(AcaCurso acaCursoSel) {
		objAcaPlanificacionCurso.setAcaCurso(acaCursoSel);
	}

	public void cargarCursoPrerequisito(AcaCurso acaCursoSel) {
		AcaPrerequisitoCurso prerequisitoCurso = new AcaPrerequisitoCurso();
		prerequisitoCurso.setAcaCurso(acaCursoSel);
		prerequisitoCurso.setAcaPlanificacionCurso(objAcaPlanificacionCurso);
		objAcaPlanificacionCurso.getAcaPrerequisitoCursos().add(prerequisitoCurso);
	}

	public void cargarGradoPrerequisito(GesGrado acaGradoSel) {
		AcaPrerequisitoGrado prerequisitoGrado = new AcaPrerequisitoGrado();
		prerequisitoGrado.setGesGrado(acaGradoSel);
		prerequisitoGrado.setAcaPlanificacionCurso(objAcaPlanificacionCurso);
		objAcaPlanificacionCurso.getAcaPrerequisitoGrados().add(prerequisitoGrado);
	}

	public void eliminarCursoPrerequisito(AcaPrerequisitoCurso prerequisitoCurso) {
		try {
			if (objAcaPlanificacionCurso.getId() > 0) {
				managerCurso.eliminarCursoPrere(prerequisitoCurso);
				objAcaPlanificacionCurso = managerCurso
						.buscarPlanificacionById(prerequisitoCurso.getAcaPlanificacionCurso().getId());
			} else
				objAcaPlanificacionCurso.getAcaPrerequisitoCursos().remove(prerequisitoCurso);
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			e.printStackTrace();
		}

	}

	public void eliminarGradoPrerequisito(AcaPrerequisitoGrado prerequisitoCurso) {
		try {
			if (objAcaPlanificacionCurso.getId() > 0) {
				managerCurso.eliminarGradoPrere(prerequisitoCurso);
				objAcaPlanificacionCurso = managerCurso
						.buscarPlanificacionById(prerequisitoCurso.getAcaPlanificacionCurso().getId());
			} else
				objAcaPlanificacionCurso.getAcaPrerequisitoGrados().remove(prerequisitoCurso);
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			e.printStackTrace();
		}

	}

	public void inicializarTipoCurso() {
		try {
			beanLogin.verificarCredencial();
			objAcaTipoCurso = new AcaTipoCurso();
			lstAcaTipoCurso = managerCurso.findAllTipoCurso();
		} catch (Exception e) {
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "inicializarTipoCurso",
					e.getMessage());
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
		}
	}

	public void inactivarTipoCurso(AcaTipoCurso objAcatipoCursoAux) {
		objAcatipoCursoAux.setFechaFinal(new Date());
		objAcatipoCursoAux.setEstado("I");
		try {
			managerCurso.actualizarTipoCurso(objAcatipoCursoAux);
			managerLog.generarLogUsabilidad(beanLogin.getCredencial(), this.getClass(), "inactivarCurso",
					"Se inactivo curso id: " + objAcatipoCursoAux.getId());
			JSFUtil.crearMensajeINFO("Atención", "Se inactivo Correctamente.");
			inicializarTipoCurso();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "inactivarCurso",
					e.getMessage());
			e.printStackTrace();
		}
	}

	public void inactivarTipoTitulo(AcaTipoTitulo objAcatipoTituloAux) {
		objAcatipoTituloAux.setFechaFinal(new Date());
		objAcatipoTituloAux.setEstado("I");
		try {
			managerCurso.actualizarTipoTitulo(objAcatipoTituloAux);
			managerLog.generarLogUsabilidad(beanLogin.getCredencial(), this.getClass(), "inactivarTipoTitulo",
					"Se inactivo titulo id: " + objAcatipoTituloAux.getId());
			JSFUtil.crearMensajeINFO("Atención", "Se inactivo Correctamente.");
			inicializarTipoCurso();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "inactivarTipoTitulo",
					e.getMessage());
			e.printStackTrace();
		}
	}

	public void inactivarCurso(AcaCurso objAcaCursoAux) {
		objAcaCursoAux.setFechaFinal(new Date());
		objAcaCursoAux.setEstado("I");
		try {
			managerCurso.actualizarCurso(objAcaCursoAux);
			managerLog.generarLogUsabilidad(beanLogin.getCredencial(), this.getClass(), "inactivarCurso",
					"Se inactivo curso id: " + objAcaCursoAux.getId());
			JSFUtil.crearMensajeINFO("Atención", "Se inactivo Correctamente.");
			inicializarAcaCurso();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "inactivarCurso",
					e.getMessage());
			e.printStackTrace();
		}
	}

	public void onRowEditTipoCurso(RowEditEvent<AcaTipoCurso> event) {
		try {
			managerCurso.actualizarTipoCurso(event.getObject());
			managerLog.generarLogUsabilidad(beanLogin.getCredencial(), this.getClass(), "onRowEditTipoCurso",
					"Se actualizó tipo curso id: " + event.getObject().getId());
			JSFUtil.crearMensajeINFO("Atención", "Actualización Correcta.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "onRowEditTipoCurso",
					e.getMessage());
			e.printStackTrace();
		}

	}

	public void onRowEditTipoTitulo(RowEditEvent<AcaTipoTitulo> event) {
		try {
			managerCurso.actualizarTipoTitulo(event.getObject());
			managerLog.generarLogUsabilidad(beanLogin.getCredencial(), this.getClass(), "onRowEditTipoTitulo",
					"Se actualizó tipo titulo id: " + event.getObject().getId());
			JSFUtil.crearMensajeINFO("Atención", "Actualización Correcta.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "onRowEditTipoTitulo",
					e.getMessage());
			e.printStackTrace();
		}

	}

	public void onRowEditInstituto(RowEditEvent<AcaInstitucionEducativa> event) {
		try {
			managerCurso.actualizarInstituto(event.getObject());
			managerLog.generarLogUsabilidad(beanLogin.getCredencial(), this.getClass(), "onRowEditInstituto",
					"Se actualizó institución id: " + event.getObject().getId());
			JSFUtil.crearMensajeINFO("Atención", "Actualización Correcta.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "onRowEditInstituto",
					e.getMessage());
			e.printStackTrace();
		}

	}

	public void onRowCancel(RowEditEvent<Object> event) {
		JSFUtil.crearMensajeINFO("Atención", "Edición cancelada.");
	}

	public void onRowEditCurso(RowEditEvent<AcaCurso> event) {
		try {
			AcaCurso objCurso = event.getObject();
			objCurso.setEstado("A");
			managerCurso.actualizarCurso(objCurso);
			managerLog.generarLogUsabilidad(beanLogin.getCredencial(), this.getClass(), "onRowEditCurso",
					"Se actualizó curso id: " + event.getObject().getId());
			JSFUtil.crearMensajeINFO("Atención", "Actualización Correcta.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "onRowEditCurso",
					e.getMessage());
			e.printStackTrace();
		}

	}

	public void ingresarTipoTitulo() {
		try {
			objAcaTipoTitulo.setFechaInicial(new Date());
			objAcaTipoTitulo.setEstado("A");
			objAcaTipoTitulo.setNombre(objAcaTipoTitulo.getNombre().toUpperCase());
			objAcaTipoTitulo.setDescripcion(objAcaTipoTitulo.getDescripcion().toUpperCase());
			managerCurso.ingresarTipoTitulo(objAcaTipoTitulo);
			JSFUtil.crearMensajeINFO("Atención", "Ingreso correcto.");
			managerLog.generarLogUsabilidad(beanLogin.getCredencial(), this.getClass(), "ingresarTipoTitulo",
					"Ingreso tipo de titulo id: " + objAcaTipoTitulo.getNombre());
			inicializarTipoTitulo();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Atención", e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "ingresarTipoTitulo",
					e.getMessage());
			e.printStackTrace();
		}
	}

	public void ingresarTitulo() {
		try {
			objAcaTitulo.setFechaInicial(new Date());
			objAcaTitulo.setEstado("A");
			objAcaTitulo.setTitulo(objAcaTitulo.getTitulo().toUpperCase());
			managerCurso.ingresarTitulo(objAcaTitulo);
			JSFUtil.crearMensajeINFO("Atención", "Ingreso correcto.");
			managerLog.generarLogUsabilidad(beanLogin.getCredencial(), this.getClass(), "ingresarTitulo",
					"Ingreso  titulo id: " + objAcaTitulo.getTitulo());
			inicializarTitulo();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Atención", e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "ingresarTitulo",
					e.getMessage());
			e.printStackTrace();
		}
	}

	public void ingresarInstitucionEducativa() {
		try {
			objAcaInstitucionEducativa.setFechaInicial(new Date());
			objAcaInstitucionEducativa.setEstado("A");
			objAcaInstitucionEducativa.setNombre(objAcaInstitucionEducativa.getNombre().toUpperCase());
			objAcaInstitucionEducativa.setDescripcion(objAcaInstitucionEducativa.getDescripcion().toUpperCase());
			managerCurso.ingresarInstitucion(objAcaInstitucionEducativa);
			JSFUtil.crearMensajeINFO("Atención", "Ingreso correcto.");
			managerLog.generarLogUsabilidad(beanLogin.getCredencial(), this.getClass(), "ingresarInstitucionEducativa",
					"Ingreso institución id: " + objAcaInstitucionEducativa.getNombre());
			inicializarInstituciones();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Atención", e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(),
					"ingresarInstitucionEducativa", e.getMessage());
			e.printStackTrace();
		}
	}

	public void ingresarTipoCurso() {
		try {
			objAcaTipoCurso.setFechaInicial(new Date());
			objAcaTipoCurso.setEstado("A");
			objAcaTipoCurso.setNombre(objAcaTipoCurso.getNombre().toUpperCase());
			objAcaTipoCurso.setDescripcion(objAcaTipoCurso.getDescripcion().toUpperCase());
			managerCurso.ingresarTipoCurso(objAcaTipoCurso);
			JSFUtil.crearMensajeINFO("Atención", "Ingreso correcto.");
			managerLog.generarLogUsabilidad(beanLogin.getCredencial(), this.getClass(), "ingresarTipoCurso",
					"Ingreso tipo de curso id: " + objAcaTipoCurso.getNombre());

			inicializarTipoCurso();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Atención", e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "ingresarTipoCurso",
					e.getMessage());
			e.printStackTrace();
		}
	}

	public void ingresarCurso() {
		try {
			objAcaCurso.setFechaInicial(new Date());
			objAcaCurso.setEstado("A");
			objAcaCurso.setNombre(objAcaCurso.getNombre().toUpperCase());
			objAcaCurso.setDescripcion(objAcaCurso.getDescripcion().toUpperCase());
			managerCurso.ingresarCurso(objAcaCurso);
			JSFUtil.crearMensajeINFO("Atención", "Ingreso correcto.");
			managerLog.generarLogUsabilidad(beanLogin.getCredencial(), this.getClass(), "ingresarCurso",
					"Ingreso tipo de curso id: " + objAcaCurso.getNombre());
			inicializarAcaCurso();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Atención", e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "ingresarCurso",
					e.getMessage());
			e.printStackTrace();
		}
	}

	public void ingresarPlanificarCurso() {
		try {
			if (objAcaPlanificacionCurso.getId() > 0)
				managerCurso.actualizarPlanificacionCurso(objAcaPlanificacionCurso);
			else
				managerCurso.ingresarPlanificacionCurso(objAcaPlanificacionCurso);
			JSFUtil.crearMensajeINFO("Atención", "Ingreso correcto.");
			managerLog.generarLogUsabilidad(beanLogin.getCredencial(), this.getClass(), "ingresarPlanificarCurso",
					"Ingreso tipo de curso id: " + objAcaPlanificacionCurso.getId());
			inicializarAcaPlanificacionCurso();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Atención", e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "ingresarPlanificarCurso",
					e.getMessage());
			e.printStackTrace();
		}
	}

	public void cargarPlanificacionSeleccionada(AcaPlanificacionCurso objAcaPlanificacionCursoSel) {
		objAcaPlanificacionCurso = objAcaPlanificacionCursoSel;
	}

	/***
	 * 
	 * Metodos Accesores y modificadores
	 */

	public AcaTipoCurso getObjAcaTipoCurso() {
		return objAcaTipoCurso;
	}

	public void setObjAcaTipoCurso(AcaTipoCurso objAcaTipoCurso) {
		this.objAcaTipoCurso = objAcaTipoCurso;
	}

	public List<AcaTipoCurso> getLstAcaTipoCurso() {
		return lstAcaTipoCurso;
	}

	public void setLstAcaTipoCurso(List<AcaTipoCurso> lstAcaTipoCurso) {
		this.lstAcaTipoCurso = lstAcaTipoCurso;
	}

	public BeanLogin getBeanLogin() {
		return beanLogin;
	}

	public void setBeanLogin(BeanLogin beanLogin) {
		this.beanLogin = beanLogin;
	}

	public AcaCurso getObjAcaCurso() {
		return objAcaCurso;
	}

	public void setObjAcaCurso(AcaCurso objAcaCurso) {
		this.objAcaCurso = objAcaCurso;
	}

	public List<AcaCurso> getLstAcaCurso() {
		return lstAcaCurso;
	}

	public void setLstAcaCurso(List<AcaCurso> lstAcaCurso) {
		this.lstAcaCurso = lstAcaCurso;
	}

	public AcaTipoTitulo getObjAcaTipoTitulo() {
		return objAcaTipoTitulo;
	}

	public void setObjAcaTipoTitulo(AcaTipoTitulo objAcaTipoTitulo) {
		this.objAcaTipoTitulo = objAcaTipoTitulo;
	}

	public List<AcaTipoTitulo> getLstAcaTipoTitulo() {
		return lstAcaTipoTitulo;
	}

	public void setLstAcaTipoTitulo(List<AcaTipoTitulo> lstAcaTipoTitulo) {
		this.lstAcaTipoTitulo = lstAcaTipoTitulo;
	}

	public AcaInstitucionEducativa getObjAcaInstitucionEducativa() {
		return objAcaInstitucionEducativa;
	}

	public void setObjAcaInstitucionEducativa(AcaInstitucionEducativa objAcaInstitucionEducativa) {
		this.objAcaInstitucionEducativa = objAcaInstitucionEducativa;
	}

	public List<AcaInstitucionEducativa> getLstAcaInstitucionEducativa() {
		return lstAcaInstitucionEducativa;
	}

	public void setLstAcaInstitucionEducativa(List<AcaInstitucionEducativa> lstAcaInstitucionEducativa) {
		this.lstAcaInstitucionEducativa = lstAcaInstitucionEducativa;
	}

	public AcaTitulo getObjAcaTitulo() {
		return objAcaTitulo;
	}

	public void setObjAcaTitulo(AcaTitulo objAcaTitulo) {
		this.objAcaTitulo = objAcaTitulo;
	}

	public List<AcaTitulo> getLstAcaTitulo() {
		return lstAcaTitulo;
	}

	public void setLstAcaTitulo(List<AcaTitulo> lstAcaTitulo) {
		this.lstAcaTitulo = lstAcaTitulo;
	}

	public List<AcaPlanificacionCurso> getLstAcaPlanificacionCurso() {
		return lstAcaPlanificacionCurso;
	}

	public void setLstAcaPlanificacionCurso(List<AcaPlanificacionCurso> lstAcaPlanificacionCurso) {
		this.lstAcaPlanificacionCurso = lstAcaPlanificacionCurso;
	}

	public AcaPlanificacionCurso getObjAcaPlanificacionCurso() {
		return objAcaPlanificacionCurso;
	}

	public void setObjAcaPlanificacionCurso(AcaPlanificacionCurso objAcaPlanificacionCurso) {
		this.objAcaPlanificacionCurso = objAcaPlanificacionCurso;
	}

	public AcaInscripcionPersona getObjAcaInscripcionPersona() {
		return objAcaInscripcionPersona;
	}

	public void setObjAcaInscripcionPersona(AcaInscripcionPersona objAcaInscripcionPersona) {
		this.objAcaInscripcionPersona = objAcaInscripcionPersona;
	}

}
