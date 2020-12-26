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

import ec.mil.controladores.session.BeanLogin;
import ec.mil.model.dao.entidades.AcaCurso;
import ec.mil.model.dao.entidades.AcaPersonasCurso;
import ec.mil.model.dao.entidades.AcaTitulo;
import ec.mil.model.dao.entidades.AcaTituloPersona;
import ec.mil.model.dao.entidades.GesEstadoCivil;
import ec.mil.model.dao.entidades.GesGrado;
import ec.mil.model.dao.entidades.GesPersona;
import ec.mil.model.dao.entidades.GesTipoSangre;
import ec.mil.model.modulos.ModelUtil.JSFUtil;
import ec.mil.model.modulos.ModelUtil.ModelUtil;
import ec.mil.model.modulos.cursos.ManagerCurso;
import ec.mil.model.modulos.gestioPersonal.ManagerGestionPersonal;
import ec.mil.model.modulos.log.ManagerLog;


@ManagedBean
@SessionScoped
public class ControladorPersonal{

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
	private boolean busqueda;
	

	/**
	 * 
	 */
	public ControladorPersonal() {
		// TODO Auto-generated constructor stub
	}
	
	public void inicializarTituloPersona()
	{
		objAcaTituloPersona = new AcaTituloPersona();
		objAcaTituloPersona.setAcaTitulo(new AcaTitulo());
		objAcaTituloPersona.setGesPersona(new GesPersona());
		busqueda =false;
		try {
			lstAcaTituloPersona = managerGestionPersonal.findAllAcaTituloPersona();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
		}
	}
	
	public void inicializarAcaPersonasCurso()
	{
		
		try {
			lstAcaPersonasCurso = managerGestionPersonal.buscarTodoPersonaCurso();
			objAcaPersonasCurso= new AcaPersonasCurso();
			objAcaPersonasCurso.setGesPersona(new GesPersona());
			objAcaPersonasCurso.setAcaCurso(new AcaCurso());
			busqueda =false;
		} catch (Exception e) {
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "inicializarAcaPersonasCurso", e.getMessage());
			e.printStackTrace();
		}
		
	}

	public void inicializarPersona() {
		objGesPersona = new GesPersona();
		objGesPersona.setGesEstadoCivil(new GesEstadoCivil());
		objGesPersona.setGesGrado(new GesGrado());
		objGesPersona.setGesTipoSangre(new GesTipoSangre());
		try {
			lstGesPersona = managerGestionPersonal.buscarPersonas();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "inicializarPersona",
					e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void cargarCursoSeleccion( AcaCurso acaCursoSel) {
		objAcaPersonasCurso.setAcaCurso(acaCursoSel);
	}
	
	public void cargarTituloSeleccion( AcaTitulo acaTituloSel) {
		objAcaTituloPersona.setAcaTitulo(acaTituloSel);
	}
	
	public void buscarPersona() {
		try {
			String cedula = objAcaPersonasCurso.getGesPersona().getCedula();
			inicializarAcaPersonasCurso();
			objAcaPersonasCurso.setGesPersona(managerGestionPersonal.buscarPersonaByCedula(cedula));
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
			objAcaTituloPersona.setGesPersona(managerGestionPersonal.buscarPersonaByCedula(cedula));
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
	
	public void ingresarCursoPersona()
	{
		objAcaPersonasCurso.setFechaInicial(new Date());
		try {
			managerGestionCurso.ingresarCursoPersona(objAcaPersonasCurso);
			JSFUtil.crearMensajeINFO("Atención", "Se ingresó correctamente.");
			inicializarAcaPersonasCurso();
			managerLog.generarLogGeneral(beanLogin.getCredencial(), this.getClass(), "ingresarCursoPersona", "Se ingreso curso persona: "+objAcaPersonasCurso.getIdPersonasCursos());
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			e.printStackTrace();
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "ingresarCursoPersona", e.getMessage());
		}
	}
	
	public void ingresarTituloPersona()
	{
		try {
			managerGestionCurso.ingresarTituloPersona(objAcaTituloPersona);
			JSFUtil.crearMensajeINFO("Atención", "Se ingresó correctamente.");
			inicializarTituloPersona();
			managerLog.generarLogGeneral(beanLogin.getCredencial(), this.getClass(), "ingresarTituloPersona", "Se ingreso titulo persona: "+getObjAcaTituloPersona().getId());
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			e.printStackTrace();
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "ingresarTituloPersona", e.getMessage());
		}
	}
	
	public List<AcaCurso> getListAcaCursoActivo()
	{
		try {
			return managerGestionCurso.findCursoActivo();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public List<AcaTitulo> getListAcaTituloActivo()
	{
		try {
			return managerGestionCurso.findTituloActivo();
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

	public void ingresarPersona() {
		try {
			objGesPersona.setApellido(ModelUtil.cambiarMayusculas( objGesPersona.getApellido()));
			objGesPersona.setNombre(ModelUtil.cambiarMayusculas(objGesPersona.getNombre()));
			objGesPersona.setCorreo(ModelUtil.cambiarMinusculas(objGesPersona.getCorreo()));
			managerGestionPersonal.ingresarPersona(objGesPersona);
			/*managerLog.generarLogUsabilidad(beanLogin.getCredencial(), this.getClass(), "ingresarPersona",
					"Se ingreso persona id " + objGesPersona.getCedula());*/
			JSFUtil.crearMensajeINFO("Mensaje", "Ingreso Correcto.");
			inicializarPersona();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "ingresarPersona",
					e.getMessage());
			e.printStackTrace();
		}
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

}
