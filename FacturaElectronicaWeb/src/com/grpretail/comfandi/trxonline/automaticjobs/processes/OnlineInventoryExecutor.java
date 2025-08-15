/*
 * Creado el 23/08/2004
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
package com.grpretail.comfandi.trxonline.automaticjobs.processes;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import org.ramm.jwaf.dbutil.DBUtil;
import org.ramm.jwaf.locator.FailedLocatingPropertiesFileException;
import org.ramm.jwaf.locator.ResourceLocator;
import org.ramm.jwaf.sql.ConnectionFailedException;
import org.ramm.jwaf.sql.DBAccessException;

import com.asic.ac.utils.StringFormatter;
import com.asic.objetos.Respuesta;
//import com.grpretail.pagoservicios.comfandi.business.utils.ParametrosWas;
import com.grpretail.comfandi.procesosautomaticos.facturaelectronica.vos.Period;
import com.grpretail.comfandi.procesosautomaticos.facturaelectronica.vos.Time;
import com.grpretail.trxonline.automaticjobs.onlineinventory.comfandi.job.OnlineInventoryFileGeneration;
//import com.grpretail.pagoservicios.comfandi.model.RegistersVo;
/**
 * @author ACadena
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
public class OnlineInventoryExecutor extends Thread{
	//private static String BATCH_FILENAME="C:\\Archivos";
	/*
	private static String BATCH_INPUTDIRECTORY;
	private static String BATCH_OUTPUTDIRECTORY;
	private static String BATCH_SEPARATOR;
	*/
	//private static String BATCH_TIME_EXE_TOTAL_GEN_FILE;
	private static String BATCH_TIME_EXE_PARTIAL_GEN_FILE;
	//private static int BATCH_TIMEOUT;
	private boolean terminar=false;
	private String status="I";
	//public static final String DBSETTINGS_FILENAME	= "OtrosParametros";
	public static final String PROCCESS_PARAMS_FILENAME	= "OnlineInventoryParams";
	private ArrayList arrPartialTimes_;
	private Hashtable theProcessedPeriodHashTable;
	public static final int PERIODS_NUMBER_MAX = 20;
	
	private String theLastTaskResult; // Resulta obtenido ejecutando la última tarea
	private java.util.Date theLastTaskDateTime; // Fecha y hora de la última tarea ejecutada o de cuando se inició la tarea
	private String theLastTaskDetail; // Detalle de última tarea ejecutada

	private static ResourceBundle bundle_;
			
	public OnlineInventoryExecutor(){	
		
		theLastTaskResult="";
		theLastTaskDateTime=new java.util.Date();
		theLastTaskDetail="";
		//setDaemon(true);
		try{	
			bundle_ = ResourceLocator.get(PROCCESS_PARAMS_FILENAME);
		}catch (FailedLocatingPropertiesFileException ex) {
			System.out.println("excepcion en constructor->");
			System.out.println(ex);
		}
		try{ 
			/*
			BATCH_INPUTDIRECTORY=bundle_.getString("CON_INPUT_DIRECTORY");
			BATCH_OUTPUTDIRECTORY=bundle_.getString("CON_OUTPUT_DIRECTORY");
			BATCH_SEPARATOR=bundle_.getString("CON_SEPARATOR");
			BATCH_TIMEOUT=new Integer(bundle_.getString("CON_TIMEOUT")).intValue();
			*/
			//BATCH_TIME_EXE_TOTAL_GEN_FILE=bundle_.getString("TIME_EXECUTE_TOTAL_GEN_FILE");
			BATCH_TIME_EXE_PARTIAL_GEN_FILE=bundle_.getString("TIME_EXECUTE_PARTIAL_GEN_FILE");
			fileGenParFileTimes(BATCH_TIME_EXE_PARTIAL_GEN_FILE);
			//System.out.println("bundle_.getString->"+bundle_.getString("CON_FILES_OUT_EXTENSION"));
			//System.out.println("bundle_.getString->"+bundle_.getString("CON_OUTPUT_DIRECTORY"));
			//System.out.println("bundle_.getString->"+bundle_.getString("CON_INPUT_DIRECTORY"));
		}catch(Exception e){
			//System.out.println("exception en el getString->");
			e.printStackTrace();
		}
	}
	private void fileGenParFileTimes(String pTimes){
		arrPartialTimes_=new ArrayList();
		String tmp_ =null;
		for (StringTokenizer t1 = new StringTokenizer(pTimes,",");t1.hasMoreTokens();){
			Time tm_=new Time();
			tmp_ = t1.nextToken();
			tm_.setHour(Integer.parseInt(tmp_.substring(0,tmp_.indexOf(":"))));
			tm_.setMinute(Integer.parseInt(tmp_.substring(tmp_.indexOf(":")+1)));
			arrPartialTimes_.add(tm_);
		}
		System.out.println(arrPartialTimes_.size());			
	}
	public void run(){
		Respuesta currAnswer_=null;
		setTerminar(false);
		boolean firstTime_=true;
		boolean pendingConcil_=false;
		System.out.println("Iniciando proceso generaciOn archivos Inventarios en LInea");	
		while(!terminar){
			try{
				setStatus("W");// trabajando				
				//System.out.println("Estado: Generando Archivos. Versión Oct 20 de 2022.");
				System.out.println("Estado: Generando Archivos. Versión Dic 12 de 2022.");
				//Calendar calendar = Calendar.getInstance();
				Calendar calendar = new GregorianCalendar();
				//Calendar calendar = Calendar.getInstance();
	
				long remainTime_=0;
				boolean pendinteDia;
				//if ((procTime_.getTime()-curTime_.getTime())<0){
				//	remainTime_=((procTime_.getTime() + 86400000) - curTime_.getTime())/60000;
				//}else
				Date curTime_ = new Date();	
				Date procTime_ = new Date();
				long sleepTime_=5;//BATCH_TIMEOUT;
				
				/*
				int hora_= new Integer(BATCH_TIME_EXE_TOTAL_GEN_FILE.substring(0,BATCH_TIME_EXE_TOTAL_GEN_FILE.indexOf(":"))).intValue();
				int minuto_= new Integer(BATCH_TIME_EXE_TOTAL_GEN_FILE.substring(BATCH_TIME_EXE_TOTAL_GEN_FILE.indexOf(":")+1)).intValue();
				procTime_.setHours(hora_);
				procTime_.setMinutes(minuto_);
				procTime_.setSeconds(0);
				
				*/
				/*if (isPendingDayFile(calendar)||pendingConcil_){ // No se ha procesado el archivo del d'ia.
					remainTime_=(procTime_.getTime() - curTime_.getTime())/60000;				
					if(remainTime_<=0){ // Ya se pas'o la hora en que se deb'ia procesar. Se debe procesar inmediatamente.
						int i;
						pendingConcil_=true;
						for (i=0;i<5;i++){
							if(Conciliacion.genTotalFile()){
								i=5;
								pendingConcil_=false;
								sleepTime_=5;
							}
						}
					}else{
						//if (!firstTime_){
							for (int i=0;i<5;i++){
								if(Conciliacion.genPartialFile())
									i=5;			
							}
						//}
					}
				}else{*/
					//if (!firstTime_){
				
				
						Period nextPeriod_=retrieveNextPeriod(curTime_);
						if (nextPeriod_ != null && !getProcessedPeriodInHashtable(nextPeriod_, curTime_ )){
							for (int i=0;i<2;i++){
								OnlineInventoryFileGeneration currJob_ = new OnlineInventoryFileGeneration();
								if(currJob_.genOnlineInvetoryFile(curTime_, nextPeriod_)){
									insertProcessedPeriod(nextPeriod_, curTime_);
									i=2;	
								}	
							}
						}	
						
						
					//}
				//}
				
				/*		
				if (!pendingConcil_){
					long sleepTimeDayly_=0;
					if ((procTime_.getTime()-curTime_.getTime())<=0){
						sleepTimeDayly_=((procTime_.getTime() + 86400000) - curTime_.getTime())/60000;
					}else
						sleepTimeDayly_=(procTime_.getTime() - curTime_.getTime())/60000;					
					System.out.println(sleepTimeDayly_);
					nextTime_=retrieveNextTime(curTime_);
					procTime_.setHours(nextTime_.getHour());
					procTime_.setMinutes(nextTime_.getMinute());
					if ((procTime_.getTime()-curTime_.getTime())<0){
						System.out.println(procTime_.getTime());
						System.out.println(curTime_.getTime());
						if (((procTime_.getTime()-curTime_.getTime())*-1)>5000)// 5 segundos de margen
							sleepTime_=((procTime_.getTime() + 86400000) - curTime_.getTime())/60000;
						else	
							sleepTime_=0;//((procTime_.getTime() + 86400000) - curTime_.getTime())/60000;
					}else
						sleepTime_=(procTime_.getTime() - curTime_.getTime())/60000;
					if (sleepTime_>sleepTimeDayly_)
						sleepTime_=sleepTimeDayly_;
				}
				*/	
				
				if(!terminar){
					try {
						//System.out.println("Tiempo a dormir->"+(sleepTime_+2)*60000);
						System.out.println("Estado: Durmiendo");
						setStatus("S"); // durmiendo
						sleep(300000); // Duerme durante 5 minutos esperando la creación del próximo archivo.
						//sleep(30000); // Duerme durante 30 segundos esperando la creación del próximo archivo.
						setStatus("A"); // despierto
						System.out.println("Estado: Despierto");
						
					} catch (InterruptedException e){
						e.printStackTrace();
						System.out.println("Hilo interrumpido");
						setTerminar(true);
					}
				}
				firstTime_=false;
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("Exception en el bloque principal del hilo Simple");
			}catch (Throwable t){
				t.printStackTrace();
				System.out.println("Throwable en el bloque principal del hilo Simple");
			}					
		} // fin while			
		setStatus("I");
		System.out.println("Proceso Inventarios Online terminado.");
	}
	// CACS: Este método retorna la última hora a la que se debió ejecutar el proceso.
	// Es decir, que funciona con el tiempo cumplido.
	
	private Period retrieveNextPeriod(Date pCurTime){
		Iterator it_=arrPartialTimes_.iterator();
		Period periodToReturn_ = null; //=new Time();
		//Time firstTime_=null;
		// Time toma el tiempo actual que llegó en la varibale pCurTime
		Time time_ = new Time();
		time_.setHour(pCurTime.getHours());
		time_.setMinute(pCurTime.getMinutes());
		Time curTime_;
		int periodNumber_ = 0;
		while (it_.hasNext()){
			curTime_=(Time)it_.next();
			periodNumber_++;
			/*
			if (firstTime_==null){
				firstTime_=new Time();
				firstTime_.setHour(curTime_.getHour());
				firstTime_.setMinute(curTime_.getMinute());				
			}
			*/
			if (curTime_.getHour()<time_.getHour()){
				if (periodToReturn_ == null)
					periodToReturn_ = new Period();
				periodToReturn_.getTime().setHour(curTime_.getHour());
				periodToReturn_.getTime().setMinute(curTime_.getMinute());
				periodToReturn_.setNumber(periodNumber_);
			}else if (curTime_.getHour()==time_.getHour()){
				if (curTime_.getMinute()<time_.getMinute()){
					if (periodToReturn_ == null)
						periodToReturn_ = new Period();
					periodToReturn_.getTime().setHour(curTime_.getHour());
					periodToReturn_.getTime().setMinute(curTime_.getMinute());
					periodToReturn_.setNumber(periodNumber_);
				}
			}
		}		
		return periodToReturn_;
	}
	/*private boolean isPendingDayFile(Calendar pCurCalendar){
		boolean answer_=true;
		return answer_;
	}*/	
	
	/*
	private boolean isPendingDayFile(Calendar pCurCalendar){
		boolean answer_=false;
		RegistersVo numReg_=new RegistersVo();
		numReg_.setNumber(0); 
		List res_;
		int reintentos_=1;
		boolean pending_=true;
		while(pending_ && reintentos_<=3){
			try {
				String select_ =
						"COUNT(*) number ";
				String from_ =
						ParametrosWas.DB_TABLE_DAYLY_CONCIL;
				String where_=ParametrosWas.DB_TABLE_DAYLY_CONCIL + "." + ParametrosWas.DB_COLUM_DC_CONCIL_DATE + "=" +
							"DATE('"+ //YYYY-MM-DD
							pCurCalendar.get(Calendar.YEAR)+"-"+
							StringFormatter.align(""+( pCurCalendar.get(Calendar.MONTH)+1),2,'0',0,2)+"-"+
							StringFormatter.align(""+ pCurCalendar.get(Calendar.DAY_OF_MONTH),2,'0',0,2)+
							"') ";							
					
				res_ = DBUtil.select(select_, from_, where_, RegistersVo.class);
				Iterator iterator_=res_.iterator();
				numReg_=(RegistersVo)iterator_.next();
				pending_=false;
			} catch (ConnectionFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DBAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (Exception e){
				e.printStackTrace();
			}catch (Throwable t){
				t.printStackTrace();
			}finally{
				reintentos_++;
			}
		}
		return (numReg_.getNumber()==0);
	}
	*/
	
	public static boolean isRightAnswer(Respuesta pAnswer)
	{
		//String control_ = ((String)pAnswer).substring(PosTransaction.ANCHO_RESPUESTA_GENERICA - 4);
		//String trxCode_ = control_.substring(0, 1);
		//String ctrCode_ = control_.substring(1, 2);
		String trxCode_ = pAnswer.getCodRespuesta();
		String ctrCode_ = pAnswer.getCodRtaTrans();	
		System.out.println("Trx code: " + trxCode_);
		System.out.println("Ctr code: " + ctrCode_);		
		// La respuesta obtenida es satisfactoria o la función enviada en la trx no existe.
		//return (trxCode_.equals(PosTransactionInterface.COD_COMANDO_COMPLETO) && ctrCode_.equals(PosTransactionInterface.CTR_OK))||(trxCode_.equals(PosTransactionInterface.COD_COMANDO_COMPLETO) && ctrCode_.equals(PosTransactionInterface.CTR_FUNCION_NO_EXISTE));
		return (trxCode_.equals("2")                                          && ctrCode_.equals("0"))                           ||(trxCode_.equals("2")                                          && ctrCode_.equals("G"));
		
	}


	
	public void setTerminar(boolean tr){
		terminar=tr;
	}
	
	public void setStatus(String st){
		status=st;
	}
	
	public String getStatus(){
		return status;
	}
		
	public String getLabelStatus(){
		if(status=="I")
			return "Inactivo";
		else if (status=="S")
			return "Durmiendo";
		else if (status=="A")
			return "Despierto";
		else if (status=="W")
			return "Procesando";			
		else
			return "Desconocido";
	}

	private boolean getProcessedPeriodInHashtable(Period pPeriod, Date pDate ){
		String periodKey_ = getPeriodKey(pPeriod, pDate);
		if (theProcessedPeriodHashTable == null)
			theProcessedPeriodHashTable = new Hashtable();

		if(theProcessedPeriodHashTable.get(periodKey_)==null)
			return false;
		else{
			System.out.println("Período ya fue procesado. Encontrado en hashtable");
			return true;
		}	
	}
	
	private String getPeriodKey(Period pPeriod, java.util.Date pDate ){
		return  (pDate.getYear()+1900)+ // Año Pago
				StringFormatter.align(""+(pDate.getMonth()+1),2,'0',0,2)+ // Mes								
				StringFormatter.align(""+(pDate.getDate()),2,'0',0,2)+ //D'ia
				pPeriod.getNumber() ; // Consecutivo del día.
	}
	
	private synchronized void insertProcessedPeriod(Period pPeriod, Date pDate ){
		String periodKey_ = getPeriodKey(pPeriod, pDate);
		if (theProcessedPeriodHashTable == null)
			theProcessedPeriodHashTable = new Hashtable();

		System.out.println("Tamaño de la tabla de archivos enviados->"+theProcessedPeriodHashTable.size());
		if (theProcessedPeriodHashTable.size()>PERIODS_NUMBER_MAX) // No hay más de 200 periodos guardados en la hashtable
			theProcessedPeriodHashTable.clear();
		
		theProcessedPeriodHashTable.put(periodKey_,new Date());
	}	
	
	/**
	 * @return
	 */
	public boolean isActive() {
		long currTime_ = (new java.util.Date()).getTime();
		long diffTime_ = (currTime_ - theLastTaskDateTime.getTime());
		System.out.println("diffTime:"+diffTime_);
		// La tarea se bloqueó o está detenida
		if (diffTime_>7200000 || diffTime_<0){// 2horas * 60min * 60seg *1000mseg = 7`200.000 
			terminar = true; //CACS: se prende este flag que se detenga sí aún está haciendo algo.
		}
			
		return !terminar;
	}
	public java.util.Date getLastTaskDateTime() {
		return theLastTaskDateTime;
	}
	public void setLastTaskDateTime(java.util.Date theLastTaskDateTime) {
		this.theLastTaskDateTime = theLastTaskDateTime;
	}
	public String getLastTaskResult() {
		return theLastTaskResult;
	}
	public void setLastTaskResult(String theLastTaskResult) {
		this.theLastTaskResult = theLastTaskResult;
	}
	public String getLastTaskDetail() {
		return theLastTaskDetail;
	}
	public void setLastTaskDetail(String theLastTaskDetail) {
		this.theLastTaskDetail = theLastTaskDetail;
	}
	
}	
