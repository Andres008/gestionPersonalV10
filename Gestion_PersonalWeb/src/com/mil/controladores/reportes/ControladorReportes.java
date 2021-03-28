/**
 * 
 */
package com.mil.controladores.reportes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import org.primefaces.event.UnselectEvent;

import ec.mil.controladores.session.BeanLogin;
import ec.mil.model.dao.entidades.AcaCurso;
import ec.mil.model.dao.entidades.AcaTipoTitulo;
import ec.mil.model.dao.entidades.AutMenu;
import ec.mil.model.dao.entidades.AutPerfile;
import ec.mil.model.dao.entidades.AutRolPerfil;
import ec.mil.model.dao.entidades.AutRole;
import ec.mil.model.dao.entidades.AutUsuario;
import ec.mil.model.dao.entidades.GesGrado;
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

	private List<VReporteConsolidado> lstConsolidado, lstConsolidadoResult;

	private List<String> lstGradosSeleccionados, lstCursosSeleccionados, lstTipoTituloSeleccionados;
	
	private boolean busquedaAvanzadaR;

	/**
	 * 
	 */
	public ControladorReportes() {
		lstConsolidado = new ArrayList<VReporteConsolidado>();
		lstGradosSeleccionados = new ArrayList<String>();
		lstCursosSeleccionados = new ArrayList<String>();
		lstTipoTituloSeleccionados = new ArrayList<String>();
	}

	public void inicializarReporteConsolidado() {
		busquedaAvanzadaR=true;
		try {
			beanLogin.verificarCredencial();
			lstConsolidado = managerReportes.buscarReporteConsolidado();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public BeanLogin getBeanLogin() {
		return beanLogin;
	}

	public void onItemUnselectGrado(UnselectEvent<String> event) {
		lstGradosSeleccionados.remove(event.getObject());
	}

	public void onItemUnselectCurso(UnselectEvent<String> event) {
		lstCursosSeleccionados.remove(event.getObject());
	}

	public void onItemUnselectTipoTitulo(UnselectEvent<String> event) {
		lstTipoTituloSeleccionados.remove(event.getObject());
	}

	public void busquedaAvanzada() {

		if (lstGradosSeleccionados.isEmpty() && lstCursosSeleccionados.isEmpty()&&lstTipoTituloSeleccionados.isEmpty())
			inicializarReporteConsolidado();
		else {
			inicializarReporteConsolidado();
			busquedaAvanzadaR=false;
			lstConsolidadoResult = new ArrayList<VReporteConsolidado>();

			/*
			 * Filtro de grados
			 */
			if (!lstGradosSeleccionados.isEmpty()) {
				lstGradosSeleccionados.forEach(grados -> {
					List<VReporteConsolidado> lstResultado = new ArrayList<VReporteConsolidado>();

					lstResultado = lstConsolidado.stream().filter(grad -> grad.getGrado().equals(grados))
							.collect(Collectors.toList());
					lstResultado.forEach(result -> {
						lstConsolidadoResult.add(result);
					});
				});
				lstConsolidado = new ArrayList<VReporteConsolidado>();
				lstConsolidadoResult.forEach(resul -> {
					lstConsolidado.add(resul);
				});
				lstConsolidadoResult = new ArrayList<VReporteConsolidado>();
			}

			if (!lstCursosSeleccionados.isEmpty()) {
				/*
				 * Filtro de Cursos
				 */
				lstCursosSeleccionados.forEach(cursos -> {
					List<VReporteConsolidado> lstResultado = new ArrayList<VReporteConsolidado>();
					lstResultado = lstConsolidado.stream().filter(grad -> grad.getNombreCurso().equals(cursos))
							.collect(Collectors.toList());
					lstResultado.forEach(result -> {
						lstConsolidadoResult.add(result);
					});
				});
				
				lstConsolidado = new ArrayList<VReporteConsolidado>();
				lstConsolidadoResult.forEach(resul -> {
					lstConsolidado.add(resul);
				});
				lstConsolidadoResult = new ArrayList<VReporteConsolidado>();
			}
			
			if (!lstTipoTituloSeleccionados.isEmpty()) {
				/*
				 * Filtro de Cursos
				 */
				lstTipoTituloSeleccionados.forEach(cursos -> {
					List<VReporteConsolidado> lstResultado = new ArrayList<VReporteConsolidado>();
					lstResultado = lstConsolidado.stream().filter(grad -> grad.getTipoTitulo().equals(cursos))
							.collect(Collectors.toList());
					lstResultado.forEach(result -> {
						lstConsolidadoResult.add(result);
					});
				});
				lstConsolidado = lstConsolidadoResult;
			}
			lstConsolidado = lstConsolidado.stream().filter(conso-> conso.getFechaFinal()==null).collect(Collectors.toList());
			eliminarDatosRepetidos(lstConsolidado);
		}

	}

	private void eliminarDatosRepetidos(List<VReporteConsolidado> lstConsolidado2) {
		List<VReporteConsolidado> resultadoSinRepe= new ArrayList<VReporteConsolidado>();
		for (VReporteConsolidado vReporteConsolidado : lstConsolidado2) {
			int cont=0;
			for (VReporteConsolidado vReporteConsolidado2 : resultadoSinRepe) 
			{
				if ( vReporteConsolidado.getCedula().equals(vReporteConsolidado2.getCedula()))
					cont=1;
			}
			if(cont==0)
				resultadoSinRepe.add(vReporteConsolidado);
		}
		lstConsolidado=resultadoSinRepe;
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

	public List<String> getLstGradosSeleccionados() {
		return lstGradosSeleccionados;
	}

	public void setLstGradosSeleccionados(List<String> lstGradosSeleccionados) {
		this.lstGradosSeleccionados = lstGradosSeleccionados;
	}

	public List<String> getLstCursosSeleccionados() {
		return lstCursosSeleccionados;
	}

	public void setLstCursosSeleccionados(List<String> lstCursosSeleccionados) {
		this.lstCursosSeleccionados = lstCursosSeleccionados;
	}

	public List<String> getLstTipoTituloSeleccionados() {
		return lstTipoTituloSeleccionados;
	}

	public void setLstTipoTituloSeleccionados(List<String> lstTipoTituloSeleccionados) {
		this.lstTipoTituloSeleccionados = lstTipoTituloSeleccionados;
	}

	public boolean isBusquedaAvanzadaR() {
		return busquedaAvanzadaR;
	}

	public void setBusquedaAvanzadaR(boolean busquedaAvanzada) {
		this.busquedaAvanzadaR = busquedaAvanzada;
	}

}
