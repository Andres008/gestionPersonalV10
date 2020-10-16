package ec.mil.model.modulos.cursos;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import ec.mil.model.dao.entidades.AcaCurso;
import ec.mil.model.dao.entidades.AcaTipoCurso;
import ec.mil.model.dao.manager.ManagerDAOGestionPersonal;

/**
 * Session Bean implementation class ManagerCurso
 */
@Stateless(mappedName = "managerCurso")
@LocalBean
public class ManagerCurso {

	@EJB
	ManagerDAOGestionPersonal managerDAOGestionPersonal; 
    /**
     * Default constructor. 
     */
    public ManagerCurso() {
        // TODO Auto-generated constructor stub
    }
	@SuppressWarnings("unchecked")
	public List<AcaTipoCurso> findAllTipoCurso() throws Exception {
		try {
			return managerDAOGestionPersonal.findAll(AcaTipoCurso.class, "o.estado ASC, o.nombre ASC") ;
		} catch (Exception e) {
			throw new Exception("Error al buscar tipo curso.");
		}
	}
	public void ingresarTipoCurso(AcaTipoCurso objAcaTipoCurso) throws Exception {
		try {
			managerDAOGestionPersonal.insertar(objAcaTipoCurso);
		} catch (Exception e) {
			throw new Exception("Error al ingresar Tipo de Curso. "+e.getMessage());
		}
	}
	public void actualizarTipoCurso(AcaTipoCurso objAcatipoCursoAux) throws Exception {
		try {
			managerDAOGestionPersonal.actualizar(objAcatipoCursoAux);
		} catch (Exception e) {
			throw new Exception("Error al actuaizar objeto.");
		}
		
	}
	
	public void actualizarCurso(AcaCurso objAcatipoCursoAux) throws Exception {
		try {
			managerDAOGestionPersonal.actualizar(objAcatipoCursoAux);
		} catch (Exception e) {
			throw new Exception("Error al actuaizar objeto.");
		}
		
	}
	@SuppressWarnings("unchecked")
	public List<AcaCurso> findAllCurso() throws Exception {
		try {
			return managerDAOGestionPersonal.findAll(AcaCurso.class, "o.estado ASC, o.nombre ASC");
		} catch (Exception e) {
			throw new Exception("Error al buscar Cursos.");
		}
	}
	@SuppressWarnings("unchecked")
	public List<AcaTipoCurso> findCursoActivo() throws Exception {
		try {
			return managerDAOGestionPersonal.findWhere(AcaTipoCurso.class, "o.estado='A'", "o.nombre ASC");
		} catch (Exception e) {
			throw new Exception("Error al buscar Tipo Curso Activo");
		}
	}
	public void ingresarCurso(AcaCurso objAcaCurso) throws Exception {
		try {
			managerDAOGestionPersonal.insertar(objAcaCurso);
		} catch (Exception e) {
			throw new Exception("Error al Ingresar Curso.");
		}
		
	}

}
