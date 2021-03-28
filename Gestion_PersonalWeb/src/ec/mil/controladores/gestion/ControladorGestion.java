package ec.mil.controladores.gestion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import ec.mil.controladores.session.BeanLogin;
import ec.mil.model.dao.entidades.GesDependencia;
import ec.mil.model.dao.entidades.GesGrado;
import ec.mil.model.dao.entidades.GesRegione;
import ec.mil.model.dao.entidades.GesReparto;
import ec.mil.model.dao.entidades.GesTipoGrado;
import ec.mil.model.modulos.ModelUtil.JSFUtil;
import ec.mil.model.modulos.gestioPersonal.ManagerGestionPersonal;
import ec.mil.model.modulos.log.ManagerLog;

@SessionScoped
@ManagedBean
public class ControladorGestion {
	private GesDependencia objGesDependencia;
	private List<GesDependencia> lstGesDependencia;
	private GesRegione objGesRegione;
	private GesReparto objGesReparto;
	private GesGrado objGesGrado;
	private List<GesGrado> lstGesGrado;

	@EJB
	private ManagerGestionPersonal managerGestionPersonal;

	@EJB
	private ManagerLog managerLog;

	@ManagedProperty(value = "#{beanLogin}")
	private BeanLogin beanLogin;

	public ControladorGestion() {

	}

	public void inicailizarDependencias() {
		try {
			beanLogin.verificarCredencial();
			inicializarRegion();
			inicializarReparto();
			objGesDependencia = new GesDependencia();
			objGesDependencia.setGesRegione(new GesRegione());
			objGesDependencia.setGesReparto(new GesReparto());
			lstGesDependencia = managerGestionPersonal.buscarAllGesDependencia();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Atención", "Error al buscar Dependencias. " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void inicializarRegion() {
		objGesRegione = new GesRegione();
	}

	public void inicializarGrado() {
		try {
			beanLogin.verificarCredencial();
			objGesGrado = new GesGrado();
			objGesGrado.setGesTipoGrado(new GesTipoGrado());
			lstGesGrado = managerGestionPersonal.buscarAllGrado();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", "Error al inicializar Grado.");
			e.printStackTrace();
		}
	}

	public void inicializarReparto() {
		objGesReparto = new GesReparto();
	}

	public void ingresarRegion() {
		objGesRegione.setNombre(objGesRegione.getNombre().toUpperCase());
		objGesRegione.setDescripcion(objGesRegione.getDescripcion().toUpperCase());
		objGesRegione.setFechaInicial(new Date());
		objGesRegione.setEstado("A");
		try {
			managerGestionPersonal.ingresarRegion(objGesRegione);
			managerLog.generarLogGeneral(beanLogin.getCredencial(), this.getClass(), "ingresarRegion",
					"Se ingreso nueva Region. " + objGesRegione.getId());
			JSFUtil.crearMensajeINFO("Atención", "Región Ingresada Correctamente.");
			inicializarRegion();
		} catch (Exception e) {
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "ingresarDependencia",
					e.getMessage());
			JSFUtil.crearMensajeERROR("Atención", e.getMessage());
			e.printStackTrace();
		}

	}

	public void ingresarReparto() {
		objGesReparto.setNombre(objGesReparto.getNombre().toUpperCase());
		objGesReparto.setDescripcion(objGesReparto.getDescripcion().toUpperCase());
		objGesReparto.setFechaInicial(new Date());
		objGesReparto.setEstado("A");
		try {
			managerGestionPersonal.ingresarReparto(objGesReparto);
			managerLog.generarLogGeneral(beanLogin.getCredencial(), this.getClass(), "ingresarRegion",
					"Se ingreso nueva Region. " + objGesReparto.getId());
			JSFUtil.crearMensajeINFO("Atención", "Reparto Ingresado Correctamente.");
			inicializarReparto();
		} catch (Exception e) {
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "ingresarDependencia",
					e.getMessage());
			JSFUtil.crearMensajeERROR("Atención", e.getMessage());
			e.printStackTrace();
		}

	}

	public List<SelectItem> siRegionesActivas() {
		List<SelectItem> lstRegiones = new ArrayList<SelectItem>();
		try {
			managerGestionPersonal.buscarRegionesActivas().forEach(region -> {
				SelectItem regionSi = new SelectItem();
				regionSi.setLabel(region.getNombre());
				regionSi.setValue(region.getId());
				lstRegiones.add(regionSi);
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lstRegiones;
	}

	public List<SelectItem> siRepartosActivo() {
		List<SelectItem> lstReparto = new ArrayList<SelectItem>();
		try {
			managerGestionPersonal.buscarRepartoActivo().forEach(reparto -> {
				SelectItem regionSi = new SelectItem();
				regionSi.setLabel(reparto.getNombre());
				regionSi.setValue(reparto.getId());
				lstReparto.add(regionSi);
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lstReparto;
	}

	public void ingresarGrado() {
		objGesGrado.setEstado("A");
		objGesGrado.setFechaInicial(new Date());
		objGesGrado.setGrado(objGesGrado.getGrado().toUpperCase());
		objGesGrado.setDescripcion(objGesGrado.getDescripcion().toUpperCase());
		try {
			managerGestionPersonal.ingresarGrado(objGesGrado);
			managerLog.generarLogGeneral(beanLogin.getCredencial(), this.getClass(), "ingresarGrado",
					"Se ingreso nuevo grado.. " + objGesGrado.getId());
			inicializarGrado();
			JSFUtil.crearMensajeINFO("Atención", "Dependencia Ingresada Correctamente.");
		} catch (Exception e) {
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "ingresarGrado",
					e.getMessage());
			JSFUtil.crearMensajeERROR("Atención", e.getMessage());
			e.printStackTrace();
		}
	}

	public void ingresarDependencia() {
		objGesDependencia.setNombre(objGesDependencia.getNombre().toUpperCase());
		objGesDependencia.setDescripcion(objGesDependencia.getDescripcion().toUpperCase());
		objGesDependencia.setFechaInicial(new Date());
		objGesDependencia.setEstado("A");
		try {
			managerGestionPersonal.ingresarDependencia(objGesDependencia);
			managerLog.generarLogGeneral(beanLogin.getCredencial(), this.getClass(), "ingresarDependencia",
					"Se ingreso nueva dependencia. " + objGesDependencia.getId());
			inicailizarDependencias();
			JSFUtil.crearMensajeINFO("Atención", "Dependencia Ingresada Correctamente.");
		} catch (Exception e) {
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "ingresarDependencia",
					e.getMessage());
			JSFUtil.crearMensajeERROR("Atención", e.getMessage());
			e.printStackTrace();
		}

	}

	public GesDependencia getObjGesDependencia() {
		return objGesDependencia;
	}

	public void setObjGesDependencia(GesDependencia objGesDependencia) {
		this.objGesDependencia = objGesDependencia;
	}

	public List<GesDependencia> getLstGesDependencia() {
		return lstGesDependencia;
	}

	public void setLstGesDependencia(List<GesDependencia> lstGesDependencia) {
		this.lstGesDependencia = lstGesDependencia;
	}

	public BeanLogin getBeanLogin() {
		return beanLogin;
	}

	public void setBeanLogin(BeanLogin beanLogin) {
		this.beanLogin = beanLogin;
	}

	public GesRegione getObjGesRegione() {
		return objGesRegione;
	}

	public void setObjGesRegione(GesRegione objGesRegione) {
		this.objGesRegione = objGesRegione;
	}

	public GesReparto getObjGesReparto() {
		return objGesReparto;
	}

	public void setObjGesReparto(GesReparto objGesReparto) {
		this.objGesReparto = objGesReparto;
	}

	public GesGrado getObjGesGrado() {
		return objGesGrado;
	}

	public void setObjGesGrado(GesGrado objGesGrado) {
		this.objGesGrado = objGesGrado;
	}

	public List<GesGrado> getLstGesGrado() {
		return lstGesGrado;
	}

	public void setLstGesGrado(List<GesGrado> lstGesGrado) {
		this.lstGesGrado = lstGesGrado;
	}

}
