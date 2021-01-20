package ec.mil.controladores.estimulo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import org.primefaces.event.RowEditEvent;

import ec.mil.controladores.session.BeanLogin;
import ec.mil.model.dao.entidades.GesEstimulo;
import ec.mil.model.dao.entidades.GesTipoEstimulo;
import ec.mil.model.dao.entidades.GesTipoGrado;
import ec.mil.model.modulos.ModelUtil.JSFUtil;
import ec.mil.model.modulos.gestioPersonal.ManagerGestionPersonal;
import ec.mil.model.modulos.log.ManagerLog;

@ManagedBean
@SessionScoped
public class ControladorEstimulo {

	@EJB
	ManagerGestionPersonal managerGesPersonal;
	@EJB
	ManagerLog managerLog;
	@ManagedProperty(value = "#{beanLogin}")
	private BeanLogin beanLogin;

	private GesTipoEstimulo objGesTipoEstimulo;
	private List<GesTipoEstimulo> lstGesTipoEstimulo;

	private GesEstimulo objGesEstimulo;
	private List<GesEstimulo> lstGesEstimulo;

	public ControladorEstimulo() {

	}

	public void inicializarGesEstimulo() {
		objGesEstimulo = new GesEstimulo();
		objGesEstimulo.setGesTipoEstimulo(new GesTipoEstimulo());
		try {
			lstGesEstimulo = managerGesPersonal.buscarAllEstimulos();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			e.printStackTrace();
		}
	}

	public List<SelectItem> siTipoEstimulos() {
		try {
			List<SelectItem> siTipEstimulo = new ArrayList<SelectItem>();
			for (GesTipoEstimulo tipoEstimulo : managerGesPersonal.buscarTipoEstimuloActivo()) {
				SelectItem siTiEst = new SelectItem();
				siTiEst.setLabel(tipoEstimulo.getNombre());
				siTiEst.setValue(tipoEstimulo.getId());
				siTipEstimulo.add(siTiEst);
			}
			return siTipEstimulo;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<SelectItem> siTipoGrado() {
		try {
			List<SelectItem> siTipoGrado = new ArrayList<SelectItem>();
			for (GesTipoGrado gesTipoGrado : managerGesPersonal.buscarTipoGradoActivo()) {
				SelectItem siTGrado = new SelectItem();
				siTGrado.setLabel(gesTipoGrado.getNombre());
				siTGrado.setValue(gesTipoGrado.getId());
				siTipoGrado.add(siTGrado);
			}
			return siTipoGrado;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public void inicializarGesTipoEstimulo() {
		objGesTipoEstimulo = new GesTipoEstimulo();
		try {
			lstGesTipoEstimulo = managerGesPersonal.buscarTodoGesTipoEstimulo();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			e.printStackTrace();
		}
	}

	public void ingresarGesTipoEstimulo() {
		try {
			objGesTipoEstimulo.setEstado("A");
			objGesTipoEstimulo.setFechaInicial(new Date());
			managerGesPersonal.ingresarGesTipoEstimulo(objGesTipoEstimulo);
			inicializarGesTipoEstimulo();
			JSFUtil.crearMensajeINFO("Atención", "Tipo Estímulo ingresado.");
			managerLog.generarLogUsabilidad(beanLogin.getCredencial(), this.getClass(), "ingresarGesTipoEstimulo",
					"Ingreso tipo Estimulo id: " + objGesTipoEstimulo.getNombre());
		} catch (Exception e) {
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "ingresarGesTipoEstimulo",
					e.getMessage());
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			e.printStackTrace();
		}
	}

	public void ingresarGesEstimulo() {
		try {
			objGesEstimulo.setEstado("A");
			objGesEstimulo.setFechaInicial(new Date());
			managerGesPersonal.ingresarGesEstimulo(objGesEstimulo);
			inicializarGesEstimulo();
			JSFUtil.crearMensajeINFO("Atención", "Tipo Estímulo ingresado.");
			managerLog.generarLogUsabilidad(beanLogin.getCredencial(), this.getClass(), "ingresarGesEstimulo",
					"Ingreso Estimulo id: " + objGesEstimulo.getNombre());
		} catch (Exception e) {
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "ingresarGesEstimulo",
					e.getMessage());
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			e.printStackTrace();
		}
	}
	

	public void onRowEditGesTipoEstimulo(RowEditEvent<GesTipoEstimulo> event) {
		try {
			managerGesPersonal.actualizarTipoEstimulo(event.getObject());
			managerLog.generarLogUsabilidad(beanLogin.getCredencial(), this.getClass(), "onRowEditGesTipoEstimulo",
					"Se actualizó tipo estimulo id: " + event.getObject().getId());
			JSFUtil.crearMensajeINFO("Atención", "Actualización Correcta.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "onRowEditGesTipoEstimulo",
					e.getMessage());
			e.printStackTrace();
		}

	}

	public GesTipoEstimulo getObjGesTipoEstimulo() {
		return objGesTipoEstimulo;
	}

	public void setObjGesTipoEstimulo(GesTipoEstimulo objGesTipoEstimulo) {
		this.objGesTipoEstimulo = objGesTipoEstimulo;
	}

	public List<GesTipoEstimulo> getLstGesTipoEstimulo() {
		return lstGesTipoEstimulo;
	}

	public void setLstGesTipoEstimulo(List<GesTipoEstimulo> lstGesTipoEstimulo) {
		this.lstGesTipoEstimulo = lstGesTipoEstimulo;
	}

	public BeanLogin getBeanLogin() {
		return beanLogin;
	}

	public void setBeanLogin(BeanLogin beanLogin) {
		this.beanLogin = beanLogin;
	}

	public GesEstimulo getObjGesEstimulo() {
		return objGesEstimulo;
	}

	public void setObjGesEstimulo(GesEstimulo objGesEstimulo) {
		this.objGesEstimulo = objGesEstimulo;
	}

	public List<GesEstimulo> getLstGesEstimulo() {
		return lstGesEstimulo;
	}

	public void setLstGesEstimulo(List<GesEstimulo> lstGesEstimulo) {
		this.lstGesEstimulo = lstGesEstimulo;
	}

}
