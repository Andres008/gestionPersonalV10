package ec.mil.controladores.gestion;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import ec.mil.model.modulos.ModelUtil.JSFUtil;
import ec.mil.model.modulos.gestioPersonal.ManagerGestionPersonal;

@SessionScoped
@ManagedBean
public class ControladorGestion  {

	@EJB
	private ManagerGestionPersonal managerGestionPersonal;
	
	public ControladorGestion() {
	}
	
	public void holaMundo()
	{
		try {
			System.out.println(managerGestionPersonal.buscarTipoEstimulo().size());
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Atención", e.getMessage());
			e.printStackTrace();
		}
	}

}
