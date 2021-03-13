/**
 * 
 */
package ec.mil.controladores.gestionPersonal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import ec.mil.controladores.session.BeanLogin;
import ec.mil.model.dao.entidades.AcaCurso;
import ec.mil.model.dao.entidades.AcaPersonasCurso;
import ec.mil.model.dao.entidades.AcaTitulo;
import ec.mil.model.dao.entidades.AcaTituloPersona;
import ec.mil.model.dao.entidades.GesDependencia;
import ec.mil.model.dao.entidades.GesDependenciaPersona;
import ec.mil.model.dao.entidades.GesEstadoCivil;
import ec.mil.model.dao.entidades.GesEstimulo;
import ec.mil.model.dao.entidades.GesEstimuloPersona;
import ec.mil.model.dao.entidades.GesGrado;
import ec.mil.model.dao.entidades.GesGradosPersona;
import ec.mil.model.dao.entidades.GesPersona;
import ec.mil.model.dao.entidades.GesPromocion;
import ec.mil.model.dao.entidades.GesReparto;
import ec.mil.model.dao.entidades.GesTipoEstimulo;
import ec.mil.model.dao.entidades.GesTipoGrado;
import ec.mil.model.dao.entidades.GesTipoSangre;
import ec.mil.model.modulos.ModelUtil.JSFUtil;
import ec.mil.model.modulos.ModelUtil.ModelUtil;
import ec.mil.model.modulos.cursos.ManagerCurso;
import ec.mil.model.modulos.gestioPersonal.ManagerGestionPersonal;
import ec.mil.model.modulos.log.ManagerLog;

@ManagedBean
@SessionScoped
public class ControladorPersonal {

	@EJB
	private ManagerGestionPersonal managerGestionPersonal;
	@EJB
	private ManagerCurso managerGestionCurso;
	@EJB
	private ManagerLog managerLog;
	@ManagedProperty(value = "#{beanLogin}")
	private BeanLogin beanLogin;
	private GesPersona objGesPersona;
	private List<GesPersona> lstGesPersona;
	private AcaPersonasCurso objAcaPersonasCurso;
	private List<AcaPersonasCurso> lstAcaPersonasCurso;
	private AcaTituloPersona objAcaTituloPersona;
	private List<AcaTituloPersona> lstAcaTituloPersona;
	private boolean busqueda, editar;
	private GesDependenciaPersona objGesDependenciaPersona;
	private List<GesDependenciaPersona> lstGesDependenciaPersona;
	private GesEstimuloPersona objGesEstimuloPersona;
	private List<GesEstimuloPersona> lstGesEstimuloPersona;
	private GesGradosPersona objGesGradosPersona;
	private List<GesGradosPersona> lstGesGradosPersona;

	/**
	 * 
	 */
	public ControladorPersonal() {

	}

	public void inicializarTituloPersona() {
		objAcaTituloPersona = new AcaTituloPersona();
		objAcaTituloPersona.setAcaTitulo(new AcaTitulo());
		objAcaTituloPersona.setGesPersona(new GesPersona());
		busqueda = false;
		editar = false;
		try {
			lstAcaTituloPersona = managerGestionPersonal.findAllAcaTituloPersona();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
		}
	}

	public void inicializarRepartoPersona() {
		try {
			objGesDependenciaPersona = new GesDependenciaPersona();
			objGesDependenciaPersona.setGesPersona(new GesPersona());
			objGesDependenciaPersona.setGesDependencia(new GesDependencia());
			busqueda = false;
			editar = false;
			lstGesDependenciaPersona = managerGestionPersonal.findAllGesDependenciaPersonal();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			e.printStackTrace();
		}
	}

	public void inicializarEstimuloPersona() {
		objGesEstimuloPersona = new GesEstimuloPersona();
		objGesEstimuloPersona.setGesPersona(new GesPersona());
		objGesEstimuloPersona.setGesEstimulo(new GesEstimulo());
		busqueda = false;
		try {
			lstGesEstimuloPersona = managerGestionPersonal.buscarAllEstimuloPersona();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			e.printStackTrace();
		}

	}

	public void inicializarGradoPersona() {
		objGesGradosPersona = new GesGradosPersona();
		objGesGradosPersona.setGesPersona(new GesPersona());
		objGesGradosPersona.setGesGrado(new GesGrado());
		busqueda = false;
		try {
			lstGesGradosPersona = managerGestionPersonal.buscarAllGradoPersona();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			e.printStackTrace();
		}
	}

	public String fechaNula(Date fecha) {
		if (fecha == null)
			return "a";
		return "";
	}

	public void cargarPersona(GesPersona objPersonaAux) {
		objGesPersona = objPersonaAux;
		editar = true;
	}

	public void registrarBaja() {
		ingresarPersona();
	}

	public void inicializarAcaPersonasCurso() {

		try {
			lstAcaPersonasCurso = managerGestionPersonal.buscarTodoPersonaCurso();
			objAcaPersonasCurso = new AcaPersonasCurso();
			objAcaPersonasCurso.setGesPersona(new GesPersona());
			objAcaPersonasCurso.setAcaCurso(new AcaCurso());
			busqueda = false;
		} catch (Exception e) {
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "inicializarAcaPersonasCurso",
					e.getMessage());
			e.printStackTrace();
		}

	}

	public void inicializarPersona() {
		objGesPersona = new GesPersona();
		objGesPersona.setGesEstadoCivil(new GesEstadoCivil());
		objGesPersona.setGesGrado(new GesGrado());
		objGesPersona.setGesTipoSangre(new GesTipoSangre());
		objGesPersona.setGesPromocion(new GesPromocion());
		try {
			lstGesPersona = managerGestionPersonal.buscarPersonas();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "inicializarPersona",
					e.getMessage());
			e.printStackTrace();
		}
	}

	public void inicializarAP7() {
		try {
			objGesPersona = managerGestionPersonal.buscarPersonaByCedula(beanLogin.getCredencial().getIdUsuario());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void cargarCursoSeleccion(AcaCurso acaCursoSel) {
		objAcaPersonasCurso.setAcaCurso(acaCursoSel);
	}

	public void cargarTituloSeleccion(AcaTitulo acaTituloSel) {
		objAcaTituloPersona.setAcaTitulo(acaTituloSel);
	}

	public void cargarDependenciaSeleccion(GesDependencia gesDependenciaSel) {
		objGesDependenciaPersona.setGesDependencia(gesDependenciaSel);
	}

	public void cargarEstimuloSeleccion(GesEstimulo gesEstimuloSel) {
		objGesEstimuloPersona.setGesEstimulo(gesEstimuloSel);
	}

	public void cargaGradoSeleccion(GesGrado gesGradoSel) {
		objGesGradosPersona.setGesGrado(gesGradoSel);
	}

	public void buscarPersona() {
		try {
			String cedula = objAcaPersonasCurso.getGesPersona().getCedula();
			inicializarAcaPersonasCurso();
			objAcaPersonasCurso.setGesPersona(managerGestionPersonal.buscarPersonaByCedulaActivo(cedula));
			busqueda = true;
		} catch (Exception e) {
			inicializarAcaPersonasCurso();
			JSFUtil.crearMensajeERROR("Atención", e.getMessage());
			/*
			 * managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(),
			 * "inicializarUsuario", e.getMessage());
			 */
			e.printStackTrace();
		}
	}

	public void buscarPersonaAP7() {
		try {
			String cedula = objGesPersona.getCedula();
			inicializarAcaPersonasCurso();
			objGesPersona = managerGestionPersonal.buscarPersonaByCedula(cedula);
			busqueda = true;
		} catch (Exception e) {
			inicializarAcaPersonasCurso();
			JSFUtil.crearMensajeERROR("Atención", e.getMessage());
			/*
			 * managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(),
			 * "inicializarUsuario", e.getMessage());
			 */
			e.printStackTrace();
		}
	}

	public void buscarPersonaTitulo() {
		try {
			String cedula = objAcaTituloPersona.getGesPersona().getCedula();
			inicializarTituloPersona();
			objAcaTituloPersona.setGesPersona(managerGestionPersonal.buscarPersonaByCedulaActivo(cedula));
			busqueda = true;
		} catch (Exception e) {
			inicializarTituloPersona();
			JSFUtil.crearMensajeERROR("Atención", e.getMessage());
			/*
			 * managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(),
			 * "inicializarUsuario", e.getMessage());
			 */
			e.printStackTrace();
		}
	}

	public void buscarPersonaDependencia() {
		try {
			String cedula = objGesDependenciaPersona.getGesPersona().getCedula();
			inicializarTituloPersona();
			objGesDependenciaPersona.setGesPersona(managerGestionPersonal.buscarPersonaByCedulaActivo(cedula));
			busqueda = true;
		} catch (Exception e) {
			inicializarTituloPersona();
			JSFUtil.crearMensajeERROR("Atención", e.getMessage());
			/*
			 * managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(),
			 * "inicializarUsuario", e.getMessage());
			 */
			e.printStackTrace();
		}
	}

	public void buscarPersonaEstimulo() {
		try {
			String cedula = objGesEstimuloPersona.getGesPersona().getCedula();
			inicializarTituloPersona();
			objGesEstimuloPersona.setGesPersona(managerGestionPersonal.buscarPersonaByCedulaActivo(cedula));
			busqueda = true;
		} catch (Exception e) {
			inicializarEstimuloPersona();
			JSFUtil.crearMensajeERROR("Atención", e.getMessage());
			/*
			 * managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(),
			 * "inicializarUsuario", e.getMessage());
			 */
			e.printStackTrace();
		}
	}

	public void buscarPersonaGrado() {
		try {
			String cedula = objGesGradosPersona.getGesPersona().getCedula();
			inicializarTituloPersona();
			objGesGradosPersona.setGesPersona(managerGestionPersonal.buscarPersonaByCedulaActivo(cedula));
			busqueda = true;
		} catch (Exception e) {
			inicializarEstimuloPersona();
			JSFUtil.crearMensajeERROR("Atención", e.getMessage());
			/*
			 * managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(),
			 * "inicializarUsuario", e.getMessage());
			 */
			e.printStackTrace();
		}
	}

	public void ingresarCursoPersona() {
		objAcaPersonasCurso.setFechaRegistro(new Date());
		try {
			managerGestionCurso.ingresarCursoPersona(objAcaPersonasCurso);
			AcaPersonasCurso objAcaPersonasCu = eliminarCursoNA(objAcaPersonasCurso.getGesPersona());
			if (objAcaPersonasCu != null)
				managerGestionCurso.eliminarCursoPersona(objAcaPersonasCu);
			JSFUtil.crearMensajeINFO("Atención", "Se ingresó correctamente.");
			inicializarAcaPersonasCurso();
			managerLog.generarLogGeneral(beanLogin.getCredencial(), this.getClass(), "ingresarCursoPersona",
					"Se ingreso curso persona: " + objAcaPersonasCurso.getIdPersonasCursos());
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			e.printStackTrace();
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "ingresarCursoPersona",
					e.getMessage());
		}
	}

	private AcaPersonasCurso eliminarCursoNA(GesPersona gesPersona) throws Exception {
		return managerGestionCurso.buscarCursoPersonaNAByPersona(gesPersona);

	}

	public void ingresarTituloPersona() {
		try {
			managerGestionCurso.ingresarTituloPersona(objAcaTituloPersona);
			AcaTituloPersona objAcaTituloPers =eliminarTituloNA(objAcaTituloPersona);
			if (objAcaTituloPers != null)
				managerGestionPersonal.eliminarTituloNA(objAcaTituloPers);
			JSFUtil.crearMensajeINFO("Atención", "Se ingresó correctamente.");
			inicializarTituloPersona();
			managerLog.generarLogGeneral(beanLogin.getCredencial(), this.getClass(), "ingresarTituloPersona",
					"Se ingreso titulo persona: " + getObjAcaTituloPersona().getId());
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			e.printStackTrace();
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "ingresarTituloPersona",
					e.getMessage());
		}
	}

	private AcaTituloPersona eliminarTituloNA(AcaTituloPersona objAcaTituloPersona2) throws Exception {
		return managerGestionPersonal.buscarTituloNA(objAcaTituloPersona2.getGesPersona());
		
	}

	public void ingresarDependenciaPersona() {
		try {
			for (GesDependenciaPersona pase : managerGestionPersonal
					.buscarDependenciaPersonaActiva(objGesDependenciaPersona.getGesPersona().getCedula())) {
				pase.setFechaFinal(ModelUtil.getSumarDias(objGesDependenciaPersona.getFechaInicial(), -1));
				managerGestionPersonal.actualizarGesDependenciaPersona(pase);
			}
			objGesDependenciaPersona.setEstado("A");
			managerGestionPersonal.ingresarDependenciaPersona(objGesDependenciaPersona);
			JSFUtil.crearMensajeINFO("Atención", "Se ingresó correctamente.");
			inicializarRepartoPersona();
			managerLog.generarLogGeneral(beanLogin.getCredencial(), this.getClass(), "ingresarDependenciaPersona",
					"Se ingreso titulo persona: " + objGesDependenciaPersona.getId());
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "ingresarDependenciaPersona",
					e.getMessage());
			e.printStackTrace();
		}
	}

	public void ingresarGradoPersona() {
		try {
			for (GesGradosPersona gesGradosPersona : managerGestionPersonal
					.buscarGradoPersonaActivo(objGesGradosPersona.getGesPersona().getCedula())) {
				gesGradosPersona.setFechaFinal(ModelUtil.getSumarDias(objGesGradosPersona.getFechaInicial(), -1));
				gesGradosPersona.setEstado("I");
				managerGestionPersonal.actualizarGradoPersona(gesGradosPersona);
			}
			objGesGradosPersona.setEstado("A");
			managerGestionPersonal.ingresarGradoPersona(objGesGradosPersona);
			objGesGradosPersona.getGesPersona().setGesGrado(objGesGradosPersona.getGesGrado());
			managerGestionPersonal.actualizarPersona(objGesGradosPersona.getGesPersona());
			inicializarGradoPersona();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", "Error al ingresar Grado Persona. " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void ingresarEstimuloPersona() {
		try {
			objGesEstimuloPersona.setFechaRegistro(new Date());
			managerGestionPersonal.ingresarEstimuloPersona(objGesEstimuloPersona);
			GesEstimuloPersona objGesEstimuloPers = eliminarEstimuloNA(objGesEstimuloPersona);
			if (objGesEstimuloPers!=null) {
				managerGestionPersonal.eliminarEstimuloNA(objGesEstimuloPers);
			}
			JSFUtil.crearMensajeINFO("Atención", "Se ingresó correctamente.");
			inicializarEstimuloPersona();
			managerLog.generarLogGeneral(beanLogin.getCredencial(), this.getClass(), "objGesEstimuloPersona",
					"Se ingreso titulo persona: " + objGesEstimuloPersona.getId());
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "objGesEstimuloPersona",
					e.getMessage());
			e.printStackTrace();
		}
	}

	private GesEstimuloPersona eliminarEstimuloNA(GesEstimuloPersona objGesEstimuloPersona2) throws Exception {
		return managerGestionCurso.buscarEstimuloPersonaNA(objGesEstimuloPersona2.getGesPersona());
	}

	public List<AcaCurso> getListAcaCursoActivo() {
		try {
			return managerGestionCurso.findCursoActivo();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public List<AcaTitulo> getListAcaTituloActivo() {
		try {
			return managerGestionCurso.findTituloActivo();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public List<GesDependencia> getListGesDependencia() {
		try {
			return managerGestionPersonal.findDependenciasActivo();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public List<GesEstimulo> getListGesEstimulo() {
		try {
			return managerGestionPersonal.buscarAllEstimulosActivo();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public List<GesGrado> getListGesGrado() {
		if (objGesGradosPersona.getGesPersona().getGesGrado() != null) {
			try {
				System.out.println("Grado..........");
				System.out.println(objGesGradosPersona.getGesPersona().getGesGrado().getGrado());
				return managerGestionPersonal.buscarGradoByTipoOrden(objGesGradosPersona.getGesPersona().getGesGrado());
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR("Error", e.getMessage());
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	public List<GesGrado> getListTodosGesGrado() {
		try {
			return managerGestionPersonal.buscarGradoActivo();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public List<SelectItem> SIestadoCivil() {
		List<SelectItem> lstSiEstadoCilvil = new ArrayList<SelectItem>();
		try {
			List<GesEstadoCivil> lstEstadoCivil = managerGestionPersonal.buscarEstadoCivilActivo();
			for (GesEstadoCivil gesEstadoCivil : lstEstadoCivil) {
				SelectItem siEstCivil = new SelectItem();
				siEstCivil.setLabel(gesEstadoCivil.getNombre());
				siEstCivil.setValue(gesEstadoCivil.getId());
				lstSiEstadoCilvil.add(siEstCivil);
			}
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "SIestadoCivil",
					e.getMessage());
			e.printStackTrace();
		}
		return lstSiEstadoCilvil;
	}

	public List<SelectItem> SIgrados() {
		List<SelectItem> lstSiGrados = new ArrayList<SelectItem>();
		try {
			List<GesGrado> lstGrados = managerGestionPersonal.buscarGradoActivo();
			for (GesGrado gesEstadoCivil : lstGrados) {
				SelectItem siEstCivil = new SelectItem();
				siEstCivil.setLabel(gesEstadoCivil.getGrado());
				siEstCivil.setValue(gesEstadoCivil.getId());
				lstSiGrados.add(siEstCivil);
			}
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "SIgrados", e.getMessage());
			e.printStackTrace();
		}
		return lstSiGrados;
	}

	public List<SelectItem> SIgradosNombre() {
		List<SelectItem> lstSiGrados = new ArrayList<SelectItem>();
		try {
			List<GesGrado> lstGrados = managerGestionPersonal.buscarGradoActivo();
			for (GesGrado gesEstadoCivil : lstGrados) {
				SelectItem siEstCivil = new SelectItem();
				siEstCivil.setLabel(gesEstadoCivil.getGrado());
				siEstCivil.setValue(gesEstadoCivil.getGrado());
				lstSiGrados.add(siEstCivil);
			}
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "SIgrados", e.getMessage());
			e.printStackTrace();
		}
		return lstSiGrados;
	}

	public List<SelectItem> SITipoDisNombre() {
		List<SelectItem> lstSiTipoDisciplina = new ArrayList<SelectItem>();
		try {
			List<GesTipoEstimulo> lstGrados = managerGestionPersonal.buscarAllTipoDisciplina();
			for (GesTipoEstimulo gesTipoEsti : lstGrados) {
				SelectItem siEstCivil = new SelectItem();
				siEstCivil.setLabel(gesTipoEsti.getNombre());
				siEstCivil.setValue(gesTipoEsti.getNombre());
				lstSiTipoDisciplina.add(siEstCivil);
			}
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "SIgrados", e.getMessage());
			e.printStackTrace();
		}
		return lstSiTipoDisciplina;
	}

	public List<SelectItem> SICursosNombre() {
		List<SelectItem> lstSiCurso = new ArrayList<SelectItem>();
		try {
			List<AcaCurso> lstGrados = managerGestionCurso.findAllCurso();
			for (AcaCurso gesTipoEsti : lstGrados) {
				SelectItem siCursoLst = new SelectItem();
				siCursoLst.setLabel(gesTipoEsti.getNombre());
				siCursoLst.setValue(gesTipoEsti.getNombre());
				lstSiCurso.add(siCursoLst);
			}
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "SIgrados", e.getMessage());
			e.printStackTrace();
		}
		return lstSiCurso;
	}

	public List<SelectItem> SITituloNombre() {
		List<SelectItem> lstSiCurso = new ArrayList<SelectItem>();
		try {
			List<AcaTitulo> lstCurso = managerGestionCurso.findAllTitulo();
			for (AcaTitulo acaCurso : lstCurso) {
				SelectItem siCursoLst = new SelectItem();
				siCursoLst.setLabel(acaCurso.getTitulo());
				siCursoLst.setValue(acaCurso.getTitulo());
				lstSiCurso.add(siCursoLst);
			}
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "SIgrados", e.getMessage());
			e.printStackTrace();
		}
		return lstSiCurso;
	}

	public List<SelectItem> SIRepartoNombre() {
		List<SelectItem> lstSiCurso = new ArrayList<SelectItem>();
		try {
			List<GesReparto> lstCurso = managerGestionPersonal.buscarRepartoActivo();
			for (GesReparto acaCurso : lstCurso) {
				SelectItem siCursoLst = new SelectItem();
				siCursoLst.setLabel(acaCurso.getNombre());
				siCursoLst.setValue(acaCurso.getNombre());
				lstSiCurso.add(siCursoLst);
			}
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "SIgrados", e.getMessage());
			e.printStackTrace();
		}
		return lstSiCurso;
	}

	public List<SelectItem> SIPromocion() {
		List<SelectItem> lstSiPromocion = new ArrayList<SelectItem>();
		try {
			List<GesTipoGrado> lstTipoGrado = managerGestionPersonal.buscarTodosTipoGrado();
			for (GesTipoGrado gesTipoGrado : lstTipoGrado) {
				List<GesPromocion> lstPromocion = managerGestionPersonal.buscarPromocionByGrado(gesTipoGrado);
				SelectItemGroup g1 = new SelectItemGroup(gesTipoGrado.getNombre());
				int i = 0;
				SelectItem auxSelec[] = new SelectItem[lstPromocion.size()];
				for (GesPromocion gesPromocion : lstPromocion) {
					SelectItem selecIt = new SelectItem();
					selecIt.setLabel(gesPromocion.getPromocion() + "-" + gesPromocion.getNombre());
					selecIt.setValue(gesPromocion.getId());
					auxSelec[i] = selecIt;
					i++;
				}
				g1.setSelectItems(auxSelec);
				lstSiPromocion.add(g1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lstSiPromocion;
	}

	public List<SelectItem> SITipoSangre() {
		List<SelectItem> lstSiTipoSangre = new ArrayList<SelectItem>();
		try {
			List<GesTipoSangre> lstTipoSangre = managerGestionPersonal.buscarTipoSangreActivo();
			for (GesTipoSangre gesEstadoCivil : lstTipoSangre) {
				SelectItem siTipSangre = new SelectItem();
				siTipSangre.setLabel(gesEstadoCivil.getNombre());
				siTipSangre.setValue(gesEstadoCivil.getId());
				lstSiTipoSangre.add(siTipSangre);
			}
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "SITipoSangre",
					e.getMessage());
			e.printStackTrace();
		}
		return lstSiTipoSangre;
	}

	public String validarTipoServicio(Date fechaBaja) {
		if (fechaBaja == null)
			return "Activo";
		return "Pasivo";
	}

	public void ingresarPersona() {
		try {
			ModelUtil.esSoloLetras(objGesPersona.getApellido());
			ModelUtil.esSoloLetras(objGesPersona.getNombre());
			objGesPersona.setApellido(ModelUtil.cambiarMayusculas(objGesPersona.getApellido()));
			objGesPersona.setNombre(ModelUtil.cambiarMayusculas(objGesPersona.getNombre()));
			objGesPersona.setCorreo(ModelUtil.cambiarMinusculas(objGesPersona.getCorreo()));
			if (editar)
				managerGestionPersonal.actualizarPersona(objGesPersona);
			else {

				valoresInicialesPersona(objGesPersona);

				managerGestionPersonal.ingresarPersona(objGesPersona);
			}
			managerLog.generarLogUsabilidad(beanLogin.getCredencial(), this.getClass(), "ingresarPersona",
					"Se ingreso persona id " + objGesPersona.getCedula());
			JSFUtil.crearMensajeINFO("Mensaje", "Ingreso Correcto.");
			inicializarPersona();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "ingresarPersona",
					e.getMessage());
			e.printStackTrace();
		}
	}

	private void valoresInicialesPersona(GesPersona objGesPersona2) throws Exception {
		/**
		 * Agregamos Curso
		 */
		objGesPersona2.setAcaPersonasCursos(new ArrayList<AcaPersonasCurso>());
		AcaPersonasCurso objAcaPersonasCurso = new AcaPersonasCurso();
		objAcaPersonasCurso.setGesPersona(objGesPersona2);
		objAcaPersonasCurso.setAcaCurso(managerGestionCurso.buscarCursoNA());
		objGesPersona2.addAcaPersonasCurso(objAcaPersonasCurso);
		/**
		 * Agregamos Titulo N/A
		 * 
		 */
		objGesPersona2.setAcaTituloPersonas(new ArrayList<AcaTituloPersona>());
		AcaTituloPersona objTituPersona = new AcaTituloPersona();
		objTituPersona.setGesPersona(objGesPersona2);
		objTituPersona.setAcaTitulo(managerGestionCurso.buscarTituloNA());
		objGesPersona2.getAcaTituloPersonas().add(objTituPersona);
		/**
		 * Agregamos Disciplina N/A
		 * 
		 */
		objGesPersona2.setGesEstimuloPersonas(new ArrayList<GesEstimuloPersona>());
		GesEstimuloPersona objEstimulo = new GesEstimuloPersona();
		objEstimulo.setGesPersona(objGesPersona2);
		objEstimulo.setGesEstimulo(managerGestionCurso.buscarDisciplinaNA());
		objGesPersona2.getGesEstimuloPersonas().add(objEstimulo);
	}

	/*
	 * 
	 * Metodos Accesores y modificadores
	 * 
	 */
	public GesPersona getObjGesPersona() {
		return objGesPersona;
	}

	public void setObjGesPersona(GesPersona objGesPersona) {
		this.objGesPersona = objGesPersona;
	}

	public List<GesPersona> getLstGesPersona() {
		return lstGesPersona;
	}

	public void setLstGesPersona(List<GesPersona> lstGesPersona) {
		this.lstGesPersona = lstGesPersona;
	}

	public BeanLogin getBeanLogin() {
		return beanLogin;
	}

	public void setBeanLogin(BeanLogin beanLogin) {
		this.beanLogin = beanLogin;
	}

	public AcaPersonasCurso getObjAcaPersonasCurso() {
		return objAcaPersonasCurso;
	}

	public void setObjAcaPersonasCurso(AcaPersonasCurso objAcaPersonasCurso) {
		this.objAcaPersonasCurso = objAcaPersonasCurso;
	}

	public List<AcaPersonasCurso> getLstAcaPersonasCurso() {
		return lstAcaPersonasCurso;
	}

	public void setLstAcaPersonasCurso(List<AcaPersonasCurso> lstAcaPersonasCurso) {
		this.lstAcaPersonasCurso = lstAcaPersonasCurso;
	}

	public boolean isBusqueda() {
		return busqueda;
	}

	public void setBusqueda(boolean busqueda) {
		this.busqueda = busqueda;
	}

	public AcaTituloPersona getObjAcaTituloPersona() {
		return objAcaTituloPersona;
	}

	public void setObjAcaTituloPersona(AcaTituloPersona objAcaTituloPersona) {
		this.objAcaTituloPersona = objAcaTituloPersona;
	}

	public List<AcaTituloPersona> getLstAcaTituloPersona() {
		return lstAcaTituloPersona;
	}

	public void setLstAcaTituloPersona(List<AcaTituloPersona> lstAcaTituloPersona) {
		this.lstAcaTituloPersona = lstAcaTituloPersona;
	}

	public GesDependenciaPersona getObjGesDependenciaPersona() {
		return objGesDependenciaPersona;
	}

	public void setObjGesDependenciaPersona(GesDependenciaPersona objGesDependenciaPersona) {
		this.objGesDependenciaPersona = objGesDependenciaPersona;
	}

	public List<GesDependenciaPersona> getLstGesDependenciaPersona() {
		return lstGesDependenciaPersona;
	}

	public void setLstGesDependenciaPersona(List<GesDependenciaPersona> lstGesDependenciaPersona) {
		this.lstGesDependenciaPersona = lstGesDependenciaPersona;
	}

	public GesEstimuloPersona getObjGesEstimuloPersona() {
		return objGesEstimuloPersona;
	}

	public void setObjGesEstimuloPersona(GesEstimuloPersona objGesEstimuloPersona) {
		this.objGesEstimuloPersona = objGesEstimuloPersona;
	}

	public List<GesEstimuloPersona> getLstGesEstimuloPersona() {
		return lstGesEstimuloPersona;
	}

	public void setLstGesEstimuloPersona(List<GesEstimuloPersona> lstGesEstimuloPersona) {
		this.lstGesEstimuloPersona = lstGesEstimuloPersona;
	}

	public GesGradosPersona getObjGesGradosPersona() {
		return objGesGradosPersona;
	}

	public void setObjGesGradosPersona(GesGradosPersona objGesGradosPersona) {
		this.objGesGradosPersona = objGesGradosPersona;
	}

	public List<GesGradosPersona> getLstGesGradosPersona() {
		return lstGesGradosPersona;
	}

	public void setLstGesGradosPersona(List<GesGradosPersona> lstGesGradosPersona) {
		this.lstGesGradosPersona = lstGesGradosPersona;
	}

}
