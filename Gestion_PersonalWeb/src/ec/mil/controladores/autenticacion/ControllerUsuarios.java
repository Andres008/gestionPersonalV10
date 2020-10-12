/**
 * 
 */
package ec.mil.controladores.autenticacion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import ec.mil.controladores.session.BeanLogin;
import ec.mil.model.dao.entidades.AutRole;
import ec.mil.model.dao.entidades.AutUsuario;
import ec.mil.model.dao.entidades.GesPersona;
import ec.mil.model.modulos.ModelUtil.JSFUtil;
import ec.mil.model.modulos.ModelUtil.ModelUtil;
import ec.mil.model.modulos.autUsuarios.ManagerUsuarios;
import ec.mil.model.modulos.gestioPersonal.ManagerGestionPersonal;
import ec.mil.model.modulos.log.ManagerLog;

@ManagedBean
@SessionScoped
public class ControllerUsuarios  {

	private AutUsuario objAutUsuario;
	private List<AutUsuario> lstAutUsuario;
	@ManagedProperty(value = "#{beanLogin}")
	private BeanLogin beanLogin;
	@EJB
	private ManagerUsuarios managerUsuarios;
	@EJB
	private ManagerLog managerLog;
	@EJB
	private ManagerGestionPersonal managerGestionPersonal;
	private Boolean busqueda;

	/**
	 * 
	 */
	public ControllerUsuarios() {
		// TODO Auto-generated constructor stub
	}

	public void inicializarUsuario() {
		try {
			objAutUsuario = new AutUsuario();
			objAutUsuario.setGesPersona(new GesPersona());
			objAutUsuario.setAutRole(new AutRole());
			lstAutUsuario = managerUsuarios.buscarTodosUsuarios();
			busqueda = false;
			System.out.println(beanLogin);
		} catch (Exception e) {
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "inicializarUsuario",
					e.getMessage());
			e.printStackTrace();
		}
	}

	public void restablercerContrasenia(AutUsuario usuario) {
		try {
			usuario.setClave(ModelUtil.md5(usuario.getCedula()));
			usuario.setPrimerInicio("SI");
			managerUsuarios.actualizarUsuario(usuario);
			JSFUtil.crearMensajeINFO("Atenci�n", "Restablecimiento de contrase�a correcto");
			// managerLog.generarLogUsabilidad(beanLogin.getCredencial(), this.getClass(),
			// "restablercerContrasenia", "Se actualiz� contrase�a. "+usuario.getCedula());
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(),
			 "restablercerContrasenia", e.getMessage());
			e.printStackTrace();
		}
	}

	public List<SelectItem> SIroles() {
		try {
			List<AutRole> lstRol = managerUsuarios.findRoleActivo();
			List<SelectItem> siRol = new ArrayList<SelectItem>();
			for (AutRole autRole : lstRol) {
				SelectItem rol = new SelectItem();
				rol.setLabel(autRole.getNombre());
				rol.setValue(autRole.getId());
				siRol.add(rol);
			}
			return siRol;
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			return null;
		}
	}

	public void buscarPersona() {
		try {
			objAutUsuario.setGesPersona(managerGestionPersonal.buscarPersonaByCedula(objAutUsuario.getCedula()));
			busqueda = true;
		} catch (Exception e) {
			inicializarUsuario();
			JSFUtil.crearMensajeERROR("Atenci�n", e.getMessage());
			/*
			 * managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(),
			 * "inicializarUsuario", e.getMessage());
			 */
			e.printStackTrace();
		}
	}

	public void ingresarUsuario() {
		try {
			objAutUsuario.setClave(ModelUtil.md5(objAutUsuario.getCedula()));
			objAutUsuario.setFechaCreacion(new Date());
			objAutUsuario.setPrimerInicio("SI");
			objAutUsuario.setEstado("A");
			managerUsuarios.ingresarUsuario(objAutUsuario);
			JSFUtil.crearMensajeINFO("Atenci�n", "Usuario creado correctamente");
			inicializarUsuario();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Atenci�n", e.getMessage());
			e.printStackTrace();
		}
	}

	/***
	 * Metodos accesores y modificadores
	 */
	public AutUsuario getObjAutUsuario() {
		return objAutUsuario;
	}

	public void setObjAutUsuario(AutUsuario objAutUsuario) {
		this.objAutUsuario = objAutUsuario;
	}

	public List<AutUsuario> getLstAutUsuario() {
		return lstAutUsuario;
	}

	public void setLstAutUsuario(List<AutUsuario> lstAutUsuario) {
		this.lstAutUsuario = lstAutUsuario;
	}

	public Boolean getBusqueda() {
		return busqueda;
	}

	public void setBusqueda(Boolean busqueda) {
		this.busqueda = busqueda;
	}

}
