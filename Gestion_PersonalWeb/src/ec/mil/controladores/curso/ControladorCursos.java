/**
 * 
 */
package ec.mil.controladores.curso;

import java.io.Serializable;
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
import ec.mil.model.dao.entidades.AcaCurso;
import ec.mil.model.dao.entidades.AcaTipoCurso;
import ec.mil.model.modulos.ModelUtil.JSFUtil;
import ec.mil.model.modulos.cursos.ManagerCurso;
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
	@ManagedProperty(value = "#{beanLogin}")
	private BeanLogin beanLogin;

	public ControladorCursos() {
		// TODO Auto-generated constructor stub
	}

	public void inicializarAcaCurso() {
		try {
			objAcaCurso = new AcaCurso();
			objAcaCurso.setAcaTipoCurso(new AcaTipoCurso());
			lstAcaCurso = managerCurso.findAllCurso();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
		}
	}

	public List<SelectItem> siTipoCursoActivo() {
		List<SelectItem> lstSiTipoCurso = new ArrayList<SelectItem>();
		try {
			List<AcaTipoCurso> lstTipoCursoAux = managerCurso.findCursoActivo();
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

	public void inicializarTipoCurso() {
		try {
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
					"Se actualizò tipo curso id: " + event.getObject().getId());
			JSFUtil.crearMensajeINFO("Atención", "Actualización Correcta.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "onRowEditTipoCurso",
					e.getMessage());
			e.printStackTrace();
		}

	}

	public void onRowEditCurso(RowEditEvent<AcaCurso> event) {
		try {
			managerCurso.actualizarCurso(event.getObject());
			managerLog.generarLogUsabilidad(beanLogin.getCredencial(), this.getClass(), "onRowEditCurso",
					"Se actualizò curso id: " + event.getObject().getId());
			JSFUtil.crearMensajeINFO("Atención", "Actualización Correcta.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "onRowEditCurso",
					e.getMessage());
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

}
