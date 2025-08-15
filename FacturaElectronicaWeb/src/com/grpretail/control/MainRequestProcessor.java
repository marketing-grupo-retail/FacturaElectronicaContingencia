/*
 ** Proyecto:
 *
 * Copyright (C) 2003-2004 Asic S.A. Bogotá, Colombia.
 * All rights Reserved.
 *
 * Id:
 *
 */
package com.grpretail.control;

import java.io.IOException;
import java.util.Hashtable;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ActionConfig;
import org.apache.struts.tiles.TilesRequestProcessor;
import org.ramm.jwaf.logger.LogWriter;
import org.ramm.jwaf.logger.WebLogger;

/**
 * @author ramm
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
public class MainRequestProcessor extends TilesRequestProcessor {

	private String authorization;

	private String audit;

	public static Hashtable hashConfigs;

	// monitorea la primera solicitud de action desde un request
	private static boolean TRACE = true;

	/* (no Javadoc)
	 * @see org.apache.struts.action.RequestProcessor#processPreprocess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
//	protected boolean processPreprocess(
//		HttpServletRequest pRequest,
//		HttpServletResponse pResponse) {
//
//		String actionPath_ = getActionPath(pRequest);
//
//		String next_ = null;
//
//		// valida solo cuando es el primer action que se ejecuta
//		// asi se evita la validacion en actions encadenados.
//		if (pRequest.getAttribute("_preprocess") == null) {
//			pRequest.setAttribute("_preprocess", "true");
//
//			// registra el action en el LogWriter de la aplicacion
//			traceAction(pRequest);
//
//			try{
//
//				// validar que el puerto de acceso sea permitido
//				//valideRemoteAccess( pRequest, pResponse);
//
//				// valida que la sesion del usuario este activa
//				valideActiveSession(pRequest, pResponse, actionPath_);
//
//				// valida que el usuario tenga permiso de realizar la operacion deseada
//				valideAccessControl(pRequest, pResponse, actionPath_);
//
//
//			// si el puerto de acceso no esta autorizado
//			}/*catch(InvalidRemoteAccessException e){
//				next_ = "/_errors/invalide_access.jsp";
//
//			// si la sesion es invalida
//			}*/ catch (InvalidSessionException e) {
//				pRequest.setAttribute("invalid_session","true");
//				
//				//redirect(pRequest,pResponse, "/welcome/welcome.jsp");
//				redirect(pRequest,pResponse, "/welcome/redirect.html");
//				return false;
//				
//
//			// si el usuario no tiene permiso para el action deseado
//			} catch (InvalidActionAccessException e) {
//				pRequest.setAttribute("invalid_access","true");
//				redirect(pRequest,pResponse, "/welcome/welcome.jsp");
//
//			}
//
//		}
//		// si algo fallo, redirecciona a la pagina definida por el error
//		if(next_!=null){
//			redirect(pRequest, pResponse, next_);
//			return false;
//		}
//
//
//		return super.processPreprocess(pRequest, pResponse);
//	}


	/** Inserta un registro en el log de las accions realizadas por un usuario.
	 * @param pRequest
	 * @param pResponse
	 * @param pOptionId
	 */
	private void logAcciones(HttpServletRequest pRequest, HttpServletResponse pResponse, String pOptionId) {

//		try {
//
//			USUARIO user_ = (USUARIO)pRequest.getSession().getAttribute("s_usuario");
//			if (user_!=null){
//
//				((AuditoriaBusinessDelegate) ServiceLocator.getService(AuditoriaBusinessDelegate.class))
//						.logAcciones( user_, pOptionId );
//			}
//
//		} catch (ServiceException e) {
//		LogWriter.getLog().log(MainRequestProcessor.class,LogWriter.WARNING,
//			"valideRemoteAccess: "+e.getRootCause().getMessage(),
//			e.getRootCause());
//		}
	}


	private void valideAccessControl(
		HttpServletRequest pRequest,
		HttpServletResponse pResponse,
		String pActionPath) throws InvalidActionAccessException {

		try {

			cargarPerfilesEnAplicacion(pRequest, pResponse);

			ActionMapping map_ = processMapping(pRequest,pResponse,pActionPath);
			String param_ = "null";
			// si el action existe en el sistema
			if(map_ != null){
				param_ = map_.getParameter();
			}
			// si el action tiene parametros
			if(param_ != null && !param_.equals("null")){
				Hashtable actionParams_ = getActionParams(param_);
				String optionId_ = (String)actionParams_.get("id");
				optionId_ = optionId_.trim();
				// si el action tiene id de opcion
				if(optionId_ != null){
					LogWriter.getLog().log(
									this,
									LogWriter.FINEST,
									"Option ID:"+optionId_);
					// valida la opcion contra los permisos que posee el usuario

					// Inserta en el log de aciones
					logAcciones(pRequest, pResponse, optionId_);
					// si no tiene acceso lanza una excepcion
					//throw new InvalidActionAccessException();
				}
			}

		} catch (IOException e1) {
			LogWriter.getLog().log(
				this,
				LogWriter.FINEST,
				"IOException: in processMapping:"+pActionPath);
		}
	}


	private void cargarPerfilesEnAplicacion(
		HttpServletRequest pRequest,
		HttpServletResponse pResponse) {

		ServletContext sc_ = getServletContext();

		ActionConfig[] actCfgs_ =  (ActionConfig[])sc_.getAttribute("sc_actionConfigs");
		ActionMapping[] maps_;

		try {

			if( actCfgs_ != null && actCfgs_.length > 0 ){

				hashConfigs = (Hashtable) sc_.getAttribute("sc_actions");

				// Crea una tabla con las configuraciones
				if ( hashConfigs == null ){

					maps_ = new ActionMapping[actCfgs_.length];
					for ( int i =0; i< actCfgs_.length; i++ ){
						maps_[i] = processMapping( pRequest,pResponse,actCfgs_[i].getPath());
					}

					hashConfigs = new Hashtable();

					for ( int i=0; i< actCfgs_.length; i++){

						//LogWriter.getLog().log(this,LogWriter.FINEST,"actCfgs_["+i+"].getParameter():" + actCfgs_[i].getParameter());
						String parametro = maps_[i].getParameter();

						if ( maps_[i].getParameter()!= null ) {
							int indiceComa_ = maps_[i].getParameter().indexOf(",");
							if ( indiceComa_ == -1 ){
								try{
								
								parametro = parametro.substring(parametro.indexOf("="),parametro.length());

////							parametro = parametro.replace(' ', '');
								parametro = "id"+parametro.trim();
								hashConfigs.put(parametro,maps_[i].getPath());
								}catch (Exception e){
								}
							}
							else{
								parametro = maps_[i].getParameter().substring(indiceComa_, maps_[i].getParameter().length());
								String indice = maps_[i].getParameter().substring(0,indiceComa_);
								// asume que el primer parámetro es el id
								parametro = parametro.substring(parametro.indexOf("="),parametro.length());
								parametro = "id="+parametro.trim();
								hashConfigs.put(parametro,maps_[i].getPath()+"?"+parametro);
							}
						}
					}
				}

				if ( hashConfigs.size() > 0 ){
					sc_.setAttribute("sc_actions", hashConfigs );
				}
			}
		}
		catch (IOException e1) {
			LogWriter.getLog().log(
				this,
				LogWriter.FINEST,
				"IOException: in processPreprocess");
		}
	}

	/**
	 * Retorna los parametros definidos en un action
	 * @param pParameters
	 * @return Hashtable con los parametros en formato llave,valor
	 */
	private Hashtable getActionParams(String pParameters) {
		StringTokenizer tokens_ = new StringTokenizer(pParameters,";");
		Hashtable actionParams_ = new Hashtable();
		while(tokens_.hasMoreElements()){
			String pair_ = (String)tokens_.nextElement();
			String key_ = pair_.substring(0,pair_.indexOf("="));
			String value_ = pair_.substring(pair_.indexOf("=")+1, pair_.length());
			actionParams_.put(key_, value_);
		}
		return actionParams_;
	}

	private String getActionPath(HttpServletRequest pRequest) {
		String actionPath_ = pRequest.getRequestURI();
		// quita el .do
		actionPath_ = actionPath_.substring(0,actionPath_.length()-3);
		// quita el /Project_Web y queda /directorio/action_path
		actionPath_ = actionPath_.substring(0 + pRequest.getContextPath().length(),actionPath_.length());
		return actionPath_;
	}

	/**
	 * Monitorea el acceso al primer action de una solicitud web
	 * @param pRequest
	 */
	private void traceAction(HttpServletRequest pRequest) {

		if(TRACE){
			StringBuffer out_ = new StringBuffer("");
			String uri_ = pRequest.getRequestURI();

			out_.append(
				"Server Name:" + pRequest.getServerName()	+ "<br>"
				+"Server Port:" + pRequest.getServerPort()	+ "<br>"
				+"Request URI:" + pRequest.getRequestURI()	+ "<br>"
				+"Request service:" + uri_.substring(uri_.lastIndexOf("/"), uri_.length()) + "<br>"
				+"Request Path Info:" + pRequest.getPathInfo()				+ "<br>"
				+"Request Context Path:" + pRequest.getContextPath()		+"<br>"
				+"Request Path Translated:" + pRequest.getPathTranslated()	+"<br>");

			LogWriter.getLog().log(
				this,
				LogWriter.FINEST,
				out_.toString());

			WebLogger.logRequestParamenters(this, pRequest);
		}
	}

	/**
	 * @param pRequest
	 * @param pResponse
	 * @param pActionPath
	 */
	private void valideActiveSession(
		HttpServletRequest pRequest,
		HttpServletResponse pResponse,
		String pActionPath) throws InvalidSessionException{

//			valida la sesion
		HttpSession session_ = pRequest.getSession(false);
		Object loginObj_ = session_.getAttribute("s_login");
		// si no es un action de ingreso
		if(!pActionPath.equals("/welcome/login") && !pActionPath.equals("/welcome/new_user")
			&& !pActionPath.equals("/welcome/registry")
			&& !pActionPath.equals("/admin_cliente/test")
			&& !pActionPath.equals("/clientes/crear_modificar_cliente_POS")
			&& !pActionPath.equals("/clientes/crear_clientes_POS")){

			// si no hay usuario en la sesion la invalida y envia a la pagina
			// de login
			if(loginObj_==null || loginObj_.equals("") ){
				throw new InvalidSessionException();
			}
			
			/*//APSM Elimina la validadación de varias sesiones simultáneas para un mismo usuario
			//Verifica que la sesión del usuario sea la misma a la de su último ingreso
			Hashtable ht_ = SessionAdmin.getInstance().getHashUserSession();
			Vector vector_ = (Vector)ht_.get((String)loginObj_);
			if (!((String)vector_.elementAt(1)).equals(session_.getId()))
			{
				pRequest.setAttribute("other_login","true");
//				Inserta en el log de reportes
				  Hashtable pVariables_ = new Hashtable();
				  pVariables_.put("<user>",(String)loginObj_);
				  LogReportsBean.insertLog((String)vector_.elementAt(0), "report.user.ingresoMulti", pVariables_);

				throw new InvalidSessionException();

						}
			*/
				
		}
	}

	/**
	 * Valida si el puerto por el que accede es valido
	 * @param pRequest
	 * @param pResponse
	 */
	private void valideRemoteAccess(HttpServletRequest pRequest,
		HttpServletResponse pResponse) throws InvalidRemoteAccessException{

//		try {
//
//
////			if (!(""+pRequest.getServerPort()).equals("9080")) {
//
//			if (!((ApplicationConfigDelegate) ServiceLocator.getService(ApplicationConfigDelegate.class))
//			.isValidPort(""+pRequest.getServerPort())){
//				throw new InvalidRemoteAccessException();
//			}
//
////			}
//
//
//
//		} catch (ServiceException e) {
//			LogWriter.getLog().log(MainRequestProcessor.class,LogWriter.WARNING,
//				"valideRemoteAccess: "+e.getRootCause().getMessage(),
//				e.getRootCause());
//		}
	}

	private void redirect(
		HttpServletRequest pRequest,
		HttpServletResponse pResponse, String pNext) {

		try {
			RequestDispatcher rd_ =
				getServletContext().getRequestDispatcher(pNext);
			rd_.forward(pRequest, pResponse);
		} catch (ServletException e) {

		} catch (IOException e) {

		}
	}

	/**
	 * @return
	 */
	public String getAudit() {
		return audit;
	}

	/**
	 * @return
	 */
	public String getAuthorization() {
		return authorization;
	}

	/**
	 * @param string
	 */
	public void setAudit(String string) {
		audit = string;
	}

	/**
	 * @param string
	 */
	public void setAuthorization(String string) {
		authorization = string;
	}

}
