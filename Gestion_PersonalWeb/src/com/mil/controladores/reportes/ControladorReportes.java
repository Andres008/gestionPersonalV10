/**
 * 
 */
package com.mil.controladores.reportes;

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
import ec.mil.model.dao.entidades.VReporteConsolidado;
import ec.mil.model.modulos.ModelUtil.JSFUtil;
import ec.mil.model.modulos.ModelUtil.ModelUtil;
import ec.mil.model.modulos.autUsuarios.ManagerUsuarios;
import ec.mil.model.modulos.gestioPersonal.ManagerGestionPersonal;
import ec.mil.model.modulos.log.ManagerLog;
import ec.mil.model.modulos.reportes.ManagerReportes;

@ManagedBean
@SessionScoped
public class ControladorReportes {

	
	@ManagedProperty(value = "#{beanLogin}")
	private BeanLogin beanLogin;
	@EJB
	private ManagerReportes managerReportes;
	@EJB
	private ManagerLog managerLog;
	
	private List<VReporteConsolidado> lstConsolidado;
	

	/**
	 * 
	 */
	public ControladorReportes() {
		lstConsolidado= new ArrayList<VReporteConsolidado>();
	}
	
	public void inicializarReporteConsolidado() {
		try {
			lstConsolidado = managerReportes.buscarReporteConsolidado();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public BeanLogin getBeanLogin() {
		return beanLogin;
	}

	public void setBeanLogin(BeanLogin beanLogin) {
		this.beanLogin = beanLogin;
	}

	public List<VReporteConsolidado> getLstConsolidado() {
		return lstConsolidado;
	}

	public void setLstConsolidado(List<VReporteConsolidado> lstConsolidado) {
		this.lstConsolidado = lstConsolidado;
	}

	
}
