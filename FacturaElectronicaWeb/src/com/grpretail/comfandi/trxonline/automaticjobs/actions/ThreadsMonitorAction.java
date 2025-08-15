/*
 ** Proyecto:
 *
 * Copyriht (C) 2003-2004 Asic S.A. Bogotá, Colombia.
 * * All rights Reserved.
 *
 * $Id: ThreadsMonitorAction.java,v 1.1 2024/07/13 00:09:06 Alfonso Exp $
 *
 */
package com.grpretail.comfandi.trxonline.automaticjobs.actions;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.grpretail.actions.BaseAction;
import com.grpretail.comfandi.monitor.trx.ComfandiPosTransaction;
import com.grpretail.comfandi.trxonline.automaticjobs.forms.ThreadsMonitorForm;
import com.grpretail.comfandi.trxonline.automaticjobs.processes.AutomaticJobsProcessManager;




/**
 * Clase encargada de ejecutar el bean para el RequestHelper
 * y retornar la página siguiente, creando antes el array que
 * contiene el listado que se va a desplegar
 *
 * @author $Author: Alfonso $
 * @version $Revision: 1.1 $
 */
public class ThreadsMonitorAction extends BaseAction {
	//private static PendingCommandsExecutor PCE_1;
	public ThreadsMonitorAction() {
		super();
	}

	/**
	 * JDOC Method Def
	 *
	 * @param mapping DOCUMENT ME!
	 * @param form DOCUMENT ME!
	 * @param request DOCUMENT ME!
	 * @param response DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws Exception DOCUMENT ME!
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		ActionErrors errors = new ActionErrors();
		ActionForward forward = new ActionForward();
        
		String next_ = "pending_commands_task_monitor.success";
		String resp_ = (String)request.getAttribute("mensaje");
		String accion_ =((String)request.getParameter("accion"));

		
		ThreadsMonitorForm form_ = (ThreadsMonitorForm)form;
		 try {
			 //	crea la sesion del usuario
			HttpSession session_ = request.getSession(false);
			ServletContext sc_ = getServlet().getServletContext();
			String company_ = (String)sc_.getAttribute("empresa");
					
			// Si se presiona un botón
			if(form_.isReiniciar()){
				
				
				int botonPulsado = Integer.parseInt(request.getParameter("pBtnTask"));
				String empresa_ = ComfandiPosTransaction.getBusinessLogicParam("empresa").getValue();
				
				switch(botonPulsado) {
					//Cada case para reiniciar la tarea correspondiente
					case 1:
//						if ( sc_.getAttribute("empresa") == null ){					
////							ApplicationConfigFacadeAdapter adapter_ = new ApplicationConfigFacadeAdapter();
////							String valueEmpresa_ = adapter_.get("empresa");
//							sc_.setAttribute("empresa",empresa_);	
//						}
//						ElectronicBillingExecutor PCE_1;
//						PCE_1 = new ElectronicBillingExecutor((String)sc_.getAttribute("empresa"),2);
//						PCE_1.setCurrentCompany((String)sc_.getAttribute("empresa"));
//						ApplicationInitServlet.setPCE_1(PCE_1);
						//CACS: Quitar para versión final de Bono Recompra
//						AutomaticJobsProcessManager.getElectronicBillingService();
						// CACS: Creación de los 4 hilos que van a manejar toda la comunicación con Expressmed
								// CACS: Hilo 1
						/*
						if(AutomaticJobsProcessManager.getTheadNumber()>0){
							//for(int i=0;i<AutomaticJobsProcessManager.getTheadNumber();i++){
								if(AutomaticJobsProcessManager.getElectronicBillingService01(i) != null && AutomaticJobsProcessManager.getElectronicBillingService01(i).isActive())
									AutomaticJobsProcessManager.stopElectronicBillingService01();
								else
									AutomaticJobsProcessManager.startElectronicBillingService01();
								
										// CACS: Hilo 2
								if(AutomaticJobsProcessManager.getElectronicBillingService02() != null && AutomaticJobsProcessManager.getElectronicBillingService02().isActive())
									AutomaticJobsProcessManager.stopElectronicBillingService02();
								else
									AutomaticJobsProcessManager.startElectronicBillingService02();						
		
										// CACS: Hilo 3
								if(AutomaticJobsProcessManager.getElectronicBillingService03() != null && AutomaticJobsProcessManager.getElectronicBillingService03().isActive())
									AutomaticJobsProcessManager.stopElectronicBillingService03();
								else
									AutomaticJobsProcessManager.startElectronicBillingService03();
							//}	
							
	//						AutomaticJobsProcessManager.getServicio().start();
						}
						*/	
						break;
					
					case 2:
//						if ( sc_.getAttribute("empresa") == null ){					
////							ApplicationConfigFacadeAdapter adapter_ = new ApplicationConfigFacadeAdapter();
////							String valueEmpresa_ = adapter_.get("empresa");
//							sc_.setAttribute("empresa",empresa_);	
//						}
//						ElectronicBillingExecutor PCE_2;
//						PCE_2 = new ElectronicBillingExecutor((String)sc_.getAttribute("empresa"),3);
//						PCE_2.setCurrentCompany((String)sc_.getAttribute("empresa"));
//						ApplicationInitServlet.setPCE_2(PCE_2);
//						//CACS: Quitar para versión final de Bono Recompra
						AutomaticJobsProcessManager.startOnlineIventoryService();
//						ApplicationInitServlet.getPCE_2().start();
						break;
//					
//					case 3:
//						if ( sc_.getAttribute("empresa") == null ){					
////							ApplicationConfigFacadeAdapter adapter_ = new ApplicationConfigFacadeAdapter();
////							String valueEmpresa_ = adapter_.get("empresa");
//							sc_.setAttribute("empresa",empresa_);	
//						}
//						ElectronicBillingExecutor PCE_3;
//						PCE_3 = new ElectronicBillingExecutor((String)sc_.getAttribute("empresa"),4);
//						PCE_3.setCurrentCompany((String)sc_.getAttribute("empresa"));
//						ApplicationInitServlet.setPCE_3(PCE_3);
//						//CACS: Quitar para versión final de Bono Recompra
//						ApplicationInitServlet.getPCE_3().start();
//						break;	 	
//					
//					case 4:
//						if ( sc_.getAttribute("empresa") == null ){					
////							ApplicationConfigFacadeAdapter adapter_ = new ApplicationConfigFacadeAdapter();
////							String valueEmpresa_ = adapter_.get("empresa");
//							sc_.setAttribute("empresa",empresa_);	
//						}
//						ElectronicBillingExecutor PCE_4;
//						PCE_4 = new ElectronicBillingExecutor((String)sc_.getAttribute("empresa"),5);
//						PCE_4.setCurrentCompany((String)sc_.getAttribute("empresa"));
//						ApplicationInitServlet.setPCE_4(PCE_4);
//						// CACS: Quitar para versión final de Bono Recompra
//						ApplicationInitServlet.getPCE_4().start();
//						break; 	
				}				
			}
			
			
			
			
			ThreadsMonitorForm Fform_ = traerEstadoTareas(form_);
			
			//Se desactivan o activan botones
			//if (form_.getEstadoTarea1() != null /*&& form_.getEstadoTarea1().equals("ACTIVO")*/){
				//request.setAttribute("btnTarea1","true");
			//}else{
				if (form_.getEstadoTarea1() == null)
					request.setAttribute("btnTarea1","false");
			//}	
			
			if (form_.getEstadoTarea2() != null &&form_.getEstadoTarea2().equals("ACTIVO")){
				request.setAttribute("btnTarea2","false");
			}else{
				if (form_.getEstadoTarea2() == null)
					request.setAttribute("btnTarea2","false");	
			}
			request.setAttribute("btnTarea3","false");
			request.setAttribute("btnTarea4","false");
//			if (form_.getEstadoTarea3().equals("ACTIVO")){
//				request.setAttribute("btnTarea3","false");
//			}
//			if (form_.getEstadoTarea4().equals("ACTIVO")){
//				request.setAttribute("btnTarea4","false");
//			}
						
			request.setAttribute("ThreadsMonitorForm",Fform_);
			if (resp_!=null) {
				request.setAttribute("mensaje",resp_);
			}else{
				request.setAttribute("mensaje","");
			}
			
		 } catch (Exception e) {
			 e.printStackTrace();
			 next_ = errorProcess(this,request, errors, e);
		 }        

		return redirect(mapping, request, errors, next_);
	}
	
	/**
	 * Se traen atributos de las tareas
	 */
	private ThreadsMonitorForm traerEstadoTareas(ThreadsMonitorForm form_) {
		// TODO Auto-generated method stub
		
//		form_.setEstadoTarea1(ApplicationInitServlet.getPCE_1().isActive()?"ACTIVO":"DETENIDO");
		/*
		if(AutomaticJobsProcessManager.getElectronicBillingService01() != null){
			form_.setEstadoTarea1(AutomaticJobsProcessManager.getElectronicBillingService01().isActive()?"ACTIVO":"DETENIDO");
			java.util.Date electronicDate_ = AutomaticJobsProcessManager.getElectronicBillingService01().getLastTaskDateTime();
			form_.setDetalleTarea1(AutomaticJobsProcessManager.getElectronicBillingService01().getLastTaskDetail());
			form_.setResultadoEjecucion1(AutomaticJobsProcessManager.getElectronicBillingService01().getLastTaskResult());
			form_.setFechaEjecucion1(""+electronicDate_.getDate()+"/"+(electronicDate_.getMonth()+1)+"/"+(electronicDate_.getYear()+1900)+" "+electronicDate_.getHours()+":"+electronicDate_.getMinutes()+":"+electronicDate_.getSeconds());
		}else
			form_.setEstadoTarea1(null);
		*/
		if(AutomaticJobsProcessManager.getOnlineInventoryService() != null){
			form_.setEstadoTarea2(AutomaticJobsProcessManager.getOnlineInventoryService().isActive()?"ACTIVO":"DETENIDO");
			java.util.Date onlineInventoriDate_ = AutomaticJobsProcessManager.getOnlineInventoryService().getLastTaskDateTime();
			form_.setFechaEjecucion2(""+onlineInventoriDate_.getDate()+"/"+(onlineInventoriDate_.getMonth()+1)+"/"+(onlineInventoriDate_.getYear()+1900)+" "+onlineInventoriDate_.getHours()+":"+onlineInventoriDate_.getMinutes()+":"+onlineInventoriDate_.getSeconds());
			form_.setDetalleTarea2(AutomaticJobsProcessManager.getOnlineInventoryService().getLastTaskDetail());
			form_.setResultadoEjecucion2(AutomaticJobsProcessManager.getOnlineInventoryService().getLastTaskResult());			
		}else
			form_.setEstadoTarea2(null);
		form_.setEstadoTarea3(null);
		form_.setEstadoTarea4(null);
//		form_.setEstadoTarea3(ApplicationInitServlet.getPCE_3().isActive()?"ACTIVO":"DETENIDO");
//		form_.setEstadoTarea4(ApplicationInitServlet.getPCE_4().isActive()?"ACTIVO":"DETENIDO");
				
//		form_.setFechaEjecucion1(""+ApplicationInitServlet.getPCE_1().getLastTaskDateTime().getDate()+"/"+(ApplicationInitServlet.getPCE_1().getLastTaskDateTime().getMonth()+1)+"/"+(ApplicationInitServlet.getPCE_1().getLastTaskDateTime().getYear()+1900)+" "+ApplicationInitServlet.getPCE_1().getLastTaskDateTime().getHours()+":"+ApplicationInitServlet.getPCE_1().getLastTaskDateTime().getMinutes()+":"+ApplicationInitServlet.getPCE_1().getLastTaskDateTime().getSeconds());
		
		

//		form_.setFechaEjecucion2(""+ApplicationInitServlet.getPCE_2().getLastTaskDateTime().getDate()+"/"+(ApplicationInitServlet.getPCE_2().getLastTaskDateTime().getMonth()+1)+"/"+(ApplicationInitServlet.getPCE_2().getLastTaskDateTime().getYear()+1900)+" "+ApplicationInitServlet.getPCE_2().getLastTaskDateTime().getHours()+":"+ApplicationInitServlet.getPCE_2().getLastTaskDateTime().getMinutes()+":"+ApplicationInitServlet.getPCE_2().getLastTaskDateTime().getSeconds());
//		form_.setFechaEjecucion3(""+ApplicationInitServlet.getPCE_3().getLastTaskDateTime().getDate()+"/"+(ApplicationInitServlet.getPCE_3().getLastTaskDateTime().getMonth()+1)+"/"+(ApplicationInitServlet.getPCE_3().getLastTaskDateTime().getYear()+1900)+" "+ApplicationInitServlet.getPCE_3().getLastTaskDateTime().getHours()+":"+ApplicationInitServlet.getPCE_3().getLastTaskDateTime().getMinutes()+":"+ApplicationInitServlet.getPCE_3().getLastTaskDateTime().getSeconds());
//		form_.setFechaEjecucion4(""+ApplicationInitServlet.getPCE_4().getLastTaskDateTime().getDate()+"/"+(ApplicationInitServlet.getPCE_4().getLastTaskDateTime().getMonth()+1)+"/"+(ApplicationInitServlet.getPCE_4().getLastTaskDateTime().getYear()+1900)+" "+ApplicationInitServlet.getPCE_4().getLastTaskDateTime().getHours()+":"+ApplicationInitServlet.getPCE_4().getLastTaskDateTime().getMinutes()+":"+ApplicationInitServlet.getPCE_4().getLastTaskDateTime().getSeconds());
		
//		form_.setDetalleTarea1(ApplicationInitServlet.getPCE_1().getLastTaskDetail());
		
		
//		form_.setDetalleTarea2(ApplicationInitServlet.getPCE_2().getLastTaskDetail());
//		form_.setDetalleTarea3(ApplicationInitServlet.getPCE_3().getLastTaskDetail());
//		form_.setDetalleTarea4(ApplicationInitServlet.getPCE_4().getLastTaskDetail());
		
//		form_.setResultadoEjecucion1(ApplicationInitServlet.getPCE_1().getLastTaskResult());
		

//		form_.setResultadoEjecucion2(ApplicationInitServlet.getPCE_2().getLastTaskResult());
//		form_.setResultadoEjecucion3(ApplicationInitServlet.getPCE_3().getLastTaskResult());
//		form_.setResultadoEjecucion4(ApplicationInitServlet.getPCE_4().getLastTaskResult());
		
		
		return form_;
		
		
	}

}
