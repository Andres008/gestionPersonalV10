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
import ec.mil.model.dao.entidades.AutMenu;
import ec.mil.model.dao.entidades.AutPerfile;
import ec.mil.model.dao.entidades.AutRolPerfil;
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
public class ControllerUsuarios {

	private AutUsuario objAutUsuario;
	private List<AutUsuario> lstAutUsuario;
	private AutRole objAutRole;
	private List<AutRole> lstAutRole;
	private AutMenu objAutMenu;
	private List<AutMenu> lstAutMenu;
	@ManagedProperty(value = "#{beanLogin}")
	private BeanLogin beanLogin;
	@EJB
	private ManagerUsuarios managerUsuarios;
	@EJB
	private ManagerLog managerLog;
	@EJB
	private ManagerGestionPersonal managerGestionPersonal;
	private Boolean busqueda;
	private AutPerfile objAutPerfile;
	private List<AutPerfile> lstAutPerfile;

	/**
	 * 
	 */
	public ControllerUsuarios() {
		// TODO Auto-generated constructor stub
	}

	public void inicializarPerfil() {
		objAutRole= new AutRole();
		objAutPerfile = new AutPerfile();
		objAutPerfile.setAutMenu(new AutMenu());
		inicializarMenu();
		try {
			lstAutPerfile = managerGestionPersonal.findAllPerfil();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			e.printStackTrace();
		}
	}

	public void inicializarMenu() {
		objAutMenu = new AutMenu();
	}

	public void inicializarRol() {
		objAutRole = new AutRole();
		try {
			lstAutRole = managerGestionPersonal.findAllRol();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			e.printStackTrace();
		}
	}

	public void cargarPerfil(AutPerfile perfil) {
		objAutPerfile = perfil;
		objAutRole = new AutRole();
	}

	public void ingresarRolPeril() {
		AutRolPerfil objRolPerfil = new AutRolPerfil();
		objRolPerfil.setAutPerfile(objAutPerfile);
		objRolPerfil.setAutRole(objAutRole);
		objRolPerfil.setEstado("A");
		objRolPerfil.setFechaInicial(new Date());
		try {
			managerGestionPersonal.ingresarRolPerfil(objRolPerfil);
			JSFUtil.crearMensajeINFO("Ateción", "Se ingreso correctamente la información.");
			managerLog.generarLogGeneral(beanLogin.getCredencial(), this.getClass(), "ingresarRolPeril",
					"Ingreso coreccto Rol Menu. " + objRolPerfil.getId());
			inicializarPerfil();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Atención", "Error al ingresar Rol Perfil. " + e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "ingresarRolPeril",
					e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	public List<AutRolPerfil> filtrarRolPerfilActios(List<AutRolPerfil> lstAutRolPerfil) {
		List<AutRolPerfil> lstAutRolPerAct = new ArrayList<AutRolPerfil>(); 
		lstAutRolPerfil.forEach(autRolPerfil ->
		{
			if (autRolPerfil.getEstado().equals("A"))
				lstAutRolPerAct.add(autRolPerfil);
		});
		return lstAutRolPerAct;
	}
	
	public void desactivarRolPerfil(AutRolPerfil rolPerfil)
	{
		rolPerfil.setEstado("I");
		rolPerfil.setFechaFinal(new Date());
		try {
			managerGestionPersonal.actualizarRolPerfil(rolPerfil);
			JSFUtil.crearMensajeINFO("Ateción", "Se actualizó correctamente la información.");
			managerLog.generarLogGeneral(beanLogin.getCredencial(), this.getClass(), "desactivarRolPerfil",
					"Actualización correcta Rol Menu. " + rolPerfil.getId());
			inicializarPerfil();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Atención", "Error al actualizar información. " + e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "desactivarRolPerfil",
					e.getMessage());
			inicializarPerfil();
			e.printStackTrace();
		}
	}

	public void desactivarRol(AutRole rol) {
		try {
			rol.setFechaFinal(new Date());
			rol.setEstado("I");
			managerGestionPersonal.actualizarAutRol(rol);
			managerLog.generarLogUsabilidad(beanLogin.getCredencial(), this.getClass(), "desactivarRol",
					"Se actualizó rol " + rol.getId());
			inicializarRol();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "desactivarRol",
					e.getMessage());
			e.printStackTrace();
		}
	}

	public void desactivarPerfil(AutPerfile perfil) {
		try {
			perfil.setFechaFinal(new Date());
			perfil.setEstado("I");
			managerGestionPersonal.actualizarAutPerfil(perfil);
			managerLog.generarLogUsabilidad(beanLogin.getCredencial(), this.getClass(), "desactivarPerfil",
					"Se actualizó perfil " + perfil.getId());
			inicializarRol();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "desactivarPerfil",
					e.getMessage());
			e.printStackTrace();
		}
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
			JSFUtil.crearMensajeINFO("Atención", "Restablecimiento de contraseña correcto");
			managerLog.generarLogUsabilidad(beanLogin.getCredencial(), this.getClass(), "restablercerContrasenia",
					"Se actualiz� contrase�a. " + usuario.getCedula());
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "restablercerContrasenia",
					e.getMessage());
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

	public List<SelectItem> SIMenu() {
		try {
			List<AutMenu> lstMenu = managerUsuarios.findAutMenuActivo();
			List<SelectItem> siPerfil = new ArrayList<SelectItem>();
			for (AutMenu autMenu : lstMenu) {
				SelectItem perfil = new SelectItem();
				perfil.setLabel(autMenu.getNombre());
				perfil.setValue(autMenu.getId());
				siPerfil.add(perfil);
			}
			return siPerfil;
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
			JSFUtil.crearMensajeERROR("Atención", e.getMessage());
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
			JSFUtil.crearMensajeINFO("Atención", "Usuario creado correctamente");
			inicializarUsuario();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Atención", e.getMessage());
			e.printStackTrace();
		}
	}

	public void ingresarRol() {
		objAutRole.setNombre(ModelUtil.cambiarMayusculas(objAutRole.getNombre()));
		objAutRole.setDescripcion(ModelUtil.cambiarMayusculas(objAutRole.getDescripcion()));
		objAutRole.setFechaInicial(new Date());
		objAutRole.setEstado("A");
		try {
			managerGestionPersonal.ingresarRol(objAutRole);
			managerLog.generarLogUsabilidad(beanLogin.getCredencial(), this.getClass(), "ingresarRol",
					"Se ingresó el rol " + objAutRole.getId() + " " + objAutRole.getNombre());
			inicializarRol();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "ingresarRol",
					e.getMessage());
			e.printStackTrace();
		}

	}

	public void ingresarPerfil() {
		objAutPerfile.setNombre(ModelUtil.cambiarMayusculas(objAutPerfile.getNombre()));
		objAutPerfile.setFechaInicial(new Date());
		objAutPerfile.setEstado("A");
		try {
			managerGestionPersonal.ingresarPerfil(objAutPerfile);
			managerLog.generarLogUsabilidad(beanLogin.getCredencial(), this.getClass(), "ingresarPerfil",
					"Se ingresó el rol " + objAutPerfile.getId() + " " + objAutPerfile.getNombre());
			inicializarPerfil();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "ingresarPerfil",
					e.getMessage());
			e.printStackTrace();
			inicializarPerfil();
		}

	}

	public void ingresarMenu() {
		objAutMenu.setNombre(ModelUtil.cambiarMayusculas(objAutMenu.getNombre()));
		objAutMenu.setObservacion(ModelUtil.cambiarMayusculas(objAutMenu.getObservacion()));
		objAutMenu.setFechaInicial(new Date());
		objAutMenu.setEstado("A");
		try {
			managerGestionPersonal.ingresarMenu(objAutMenu);
			managerLog.generarLogUsabilidad(beanLogin.getCredencial(), this.getClass(), "ingresarMenu",
					"Se ingresó el rol " + objAutMenu.getId() + " " + objAutMenu.getNombre());
			inicializarMenu();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "ingresarMenu",
					e.getMessage());
			e.printStackTrace();
			inicializarPerfil();
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

	public BeanLogin getBeanLogin() {
		return beanLogin;
	}

	public void setBeanLogin(BeanLogin beanLogin) {
		this.beanLogin = beanLogin;
	}

	public AutRole getObjAutRole() {
		return objAutRole;
	}

	public void setObjAutRole(AutRole objAutRole) {
		this.objAutRole = objAutRole;
	}

	public List<AutRole> getLstAutRole() {
		return lstAutRole;
	}

	public void setLstAutRole(List<AutRole> lstAutRole) {
		this.lstAutRole = lstAutRole;
	}

	public AutPerfile getObjAutPerfile() {
		return objAutPerfile;
	}

	public void setObjAutPerfile(AutPerfile objAutPerfile) {
		this.objAutPerfile = objAutPerfile;
	}

	public List<AutPerfile> getLstAutPerfile() {
		return lstAutPerfile;
	}

	public void setLstAutPerfile(List<AutPerfile> lstAutPerfile) {
		this.lstAutPerfile = lstAutPerfile;
	}

	public AutMenu getObjAutMenu() {
		return objAutMenu;
	}

	public void setObjAutMenu(AutMenu objAutMenu) {
		this.objAutMenu = objAutMenu;
	}

	public List<AutMenu> getLstAutMenu() {
		return lstAutMenu;
	}

	public void setLstAutMenu(List<AutMenu> lstAutMenu) {
		this.lstAutMenu = lstAutMenu;
	}

}
