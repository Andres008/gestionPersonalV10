package ec.mil.model.modulos.reportes;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import ec.mil.model.dao.entidades.VReporteConsolidado;
import ec.mil.model.dao.manager.ManagerDAOGestionPersonal;

/**
 * Session Bean implementation class ManagerReportes
 */
@Stateless(mappedName = "managerCurso")
@LocalBean
public class ManagerReportes {

	@EJB
	ManagerDAOGestionPersonal managerDAOGestionPersonal;

	/**
	 * Default constructor.
	 */
	public ManagerReportes() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public List<VReporteConsolidado> buscarReporteConsolidado() throws Exception {
		return managerDAOGestionPersonal.findAll(VReporteConsolidado.class, "o.promocion ASC, o.antiguedadActual ASC");
	}

	
}
