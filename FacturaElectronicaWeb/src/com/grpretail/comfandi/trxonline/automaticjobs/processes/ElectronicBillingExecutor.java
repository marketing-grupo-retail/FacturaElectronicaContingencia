/*
 * Creado el 23/08/2004
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generaci?n de c?digo&gt;C?digo y comentarios
 */
package com.grpretail.comfandi.trxonline.automaticjobs.processes;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.ramm.jwaf.dbutil.DBUtil;
import org.ramm.jwaf.locator.FailedLocatingPropertiesFileException;
import org.ramm.jwaf.locator.ResourceLocator;
import org.ramm.jwaf.sql.ConnectionFailedException;
import org.ramm.jwaf.sql.DBAccessException;

import com.asic.objetos.Respuesta;
import com.general.utils.StringFormatter;
//import com.grpretail.pagoservicios.comfandi.business.utils.ParametrosWas;
import com.grpretail.comfandi.business.utils.BusinessLogicParam;
import com.grpretail.comfandi.business.utils.DatabaseFieldsUtils;
import com.grpretail.comfandi.business.utils.StringUtils;
import com.grpretail.comfandi.fe.businesslogic.vo.FEJSONMessageVo;
import com.grpretail.comfandi.monitor.trx.ComfandiPosTransaction;
import com.grpretail.comfandi.procesosautomaticos.facturaelectronica.db.TrxonlineDatabaseHelper;
import com.grpretail.comfandi.procesosautomaticos.facturaelectronica.exceptions.CustomerTrxNotFoundException;
import com.grpretail.comfandi.procesosautomaticos.facturaelectronica.exceptions.DIANTenderConfigurationNotFoundException;
import com.grpretail.comfandi.procesosautomaticos.facturaelectronica.exceptions.DataInconsistencyInTotalsException;
import com.grpretail.comfandi.procesosautomaticos.facturaelectronica.exceptions.FiscalInformationNotFoundException;
import com.grpretail.comfandi.procesosautomaticos.facturaelectronica.exceptions.ParameterNotFoundException;
import com.grpretail.comfandi.procesosautomaticos.facturaelectronica.exceptions.StoreNotFoundException;
//import com.grpretail.comfandi.procesosautomaticos.facturaelectronica.model.ServiceOrderItemVo;
//import com.grpretail.comfandi.procesosautomaticos.facturaelectronica.model.ServiceOrderTenderVo;
//import com.grpretail.comfandi.procesosautomaticos.facturaelectronica.model.ServiceOrderVo;
import com.grpretail.comfandi.procesosautomaticos.facturaelectronica.vos.DiscountRegInDBVO;
import com.grpretail.comfandi.procesosautomaticos.facturaelectronica.vos.ItemEntryRegInDBVO;
import com.grpretail.comfandi.procesosautomaticos.facturaelectronica.vos.Period;
import com.grpretail.comfandi.procesosautomaticos.facturaelectronica.vos.TenderRegInDBVO;
import com.grpretail.comfandi.procesosautomaticos.facturaelectronica.vos.Time;
import com.grpretail.comfandi.procesosautomaticos.facturaelectronica.vos.TrxonlineTransactionInDBVO;
import com.grpretail.comfandi.trxonline.automaticjobs.facturaelectronica.dbaccess.FacturaElectronicaDBHelper;
import com.grpretail.facturaelectronica.comfandi.business.utils.vos.AlmacenVo;
import com.grpretail.facturaelectronica.comfandi.business.utils.vos.AuthenticationAnswer;
import com.grpretail.facturaelectronica.comfandi.business.utils.vos.CustomerInfoVo;
import com.grpretail.facturaelectronica.comfandi.business.utils.vos.ItemDescriptionVo;
import com.grpretail.facturaelectronica.comfandi.business.utils.vos.SendDocumentAnswer;
import com.grpretail.facturaelectronica.comfandi.dian.utils.CarvajalProcessUtils;
import com.grpretail.facturaelectronica.comfandi.exception.ItemDataException;
import com.grpretail.facturaelectronica.comfandi.mensajeria.JSONMessagesCarvajalBuilder;
import com.grpretail.facturaelectronica.comfandi.mensajeria.vos.JSONMessageVo;
import com.grpretail.facturaelectronica.comfandi.mensajeria.vos.ServiceOrderItemVo;
import com.grpretail.facturaelectronica.comfandi.mensajeria.vos.ServiceOrderTenderVo;
import com.grpretail.facturaelectronica.comfandi.mensajeria.vos.ServiceOrderVo;
import com.grpretail.trxonline.automaticjobs.facturaelectronica.utils.BusinessLogicParameters;
import com.grpretail.utils.ArrayUtils;
import com.grpretail.ws.JSONServicesFacturaElectronica;

/**
 * @author ACadena
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generaci?n de c?digo&gt;C?digo y comentarios
 */
public class ElectronicBillingExecutor extends Thread{
	private boolean isSSL = true; //Boolean.parseBoolean(conexion.getString("isSSL"));
	//private static String BATCH_FILENAME="C:\\Archivos";
	//private static String BATCH_INPUTDIRECTORY;
	//private static String BATCH_OUTPUTDIRECTORY;
	//private static String BATCH_SEPARATOR;
	//private static String BATCH_TIME_EXE_TOTAL_GEN_FILE;
	public final static boolean MODULE_IN_DEBUG = false;
	//public final static String ID_ELECTRONIC_BILLING_TYPE = "01"; // Facturación en línea
	public final static String ID_ELECTRONIC_BILLING_TYPE = "03"; // Facturación en contingencia
	private Map theVATTableHash = null;
	private Map theVATBasesTableHash = null;
	
	private static String BATCH_TIME_EXE_PARTIAL_GEN_FILE;
	private static final int theTheadsNumber = 4;
	//private static int BATCH_TIMEOUT;
	private boolean terminar=false;
	private String status="I";
	public static final String PROCCESS_PARAMS_FILENAME	= "ElectronicBillingParams";
	private ArrayList arrPartialTimes_;
	private Hashtable theProcessedPeriodHashTable;
	public static final int PERIODS_NUMBER_MAX = 20;
	public static final int STORE_RANGE_01 = 520;
	//public static final int STORE_RANGE_01 = 0;
	public static final int STORE_RANGE_02 = 613;
	//public static final int STORE_RANGE_02 = 1000;
	public static final int STORE_RANGE_03 = 799;
	//public static final int STORE_RANGE_03 = 100;
	//public static final int STORE_RANGE_03 = 1000;
	public static final String REG_STATUS_TO_SEND_WITHOUT_CONEX_CARVAJAL 	= "2"; // Completamente en contingencia. No hubo conexión con Carvajal
	public static final String REG_STATUS_TO_SEND_WITH_CONEX_CARVAJAL 		= "3"; // Contingencia pero o con conexión Carvajal o error inesperado incluyendo timeout
	
	
	//public static final String REG_STATUS_ACTIVE 	= "2"; // Estado de registro a enviar
	public static final String REG_STATUS_SENDED 		= "4"; // Estado de registro Enviado después de tener estado = 2
	public static final String REG_STATUS_SENDED_2 		= "5"; // Estado de registro Enviado después de tener estado = 3
	public static final String REG_STATUS_PENDING 		= "6"; // Estado de registro Pendiente después de tener estado = 2
	public static final String REG_STATUS_PENDING_2		= "7"; // Estado de registro Pendiente después de tener estado = 3
	public static final String REG_STATUS_TO_REVIEW 	= "8"; // Estado de registro Pendiente después de tener estado = 2
	public static final String REG_STATUS_TO_REVIEW_2	= "9"; // Estado de registro Pendiente después de tener estado = 3	
	
	public static final String ERROR_STATUS = "ERROR_STATUS";
	
	//public static final int STORE_RANGE_04 = 799;
	protected static String theCurrentServiceToken = null;

	private static ResourceBundle bundle_;
	private int theThreadsArrayIndexNumber; // Idenficador del ?ndice (fila) dentro del arreglo de hilos bidimensional. Se usa para darle una parametrizaci?n diferente.
	private int theNewThreadsArrayIndexNumber; // Idenficador del ?ndice (fila) dentro del arreglo de hilos bidimensional. Se usa para moverse entre hilos cuando no hay trabajos pendientes.	
	private int theThreadArraySubindexNumber; // Idenficador del sub?ndice (columna) dentro del arreglo de hilos bidimensional. Se usa para darle una parametrizaci?n diferente.
	
	//private int theThreadNumber=0;    // Id del hilo que se est? ejecutando. Llega como par?metro cuando se instancia esta clase. 
	private String theLastTaskResult; // Resulta obtenido ejecutando la ?ltima tarea
	private java.util.Date theLastTaskDateTime; // Fecha y hora de la ?ltima tarea ejecutada o de cuando se inici? la tarea
	private String theLastTaskDetail; // Detalle de ?ltima tarea ejecutada
	//APS: Valor de la empresa:
	//String empresa = null;	
	private static String currentCompany="";
	private static Hashtable theTrxsInProccessThread00;
	private static Hashtable theTrxsInProccessThread01;
	private static Hashtable theTrxsInProccessThread02;
	private static Hashtable theTrxsInProccessThread03;

	private static Hashtable theTrxsProccessedThread00;
	private static Hashtable theTrxsProccessedThread01;
	private static Hashtable theTrxsProccessedThread02;
	private static Hashtable theTrxsProccessedThread03;	
		
	static Map<Long, AlmacenVo> mapAlmacen = new HashMap<Long, AlmacenVo>();  // Datos de almacenes en memoria
	static Map<Integer, ServiceOrderTenderVo> mapMDP = new HashMap<Integer, ServiceOrderTenderVo>(); // Datos de medios de pago para tenerlos en memoria
	
	/*		
	public ElectronicBillingExecutor(String pEmpresa, int pThreadNumber){		
		empresa = pEmpresa;
		theThreadNumber=pThreadNumber;
		theLastTaskResult="";
		theLastTaskDateTime=new java.util.Date();
		theLastTaskDetail="";
		try{	
			bundle_ = ResourceLocator.get(PROCCESS_PARAMS_FILENAME);
		}catch (FailedLocatingPropertiesFileException ex) {
			System.out.println("excepcion en constructor->");
			System.out.println(ex);
		}
		try{ 	
			BATCH_INPUTDIRECTORY=bundle_.getString("CON_INPUT_DIRECTORY");
			BATCH_OUTPUTDIRECTORY=bundle_.getString("CON_OUTPUT_DIRECTORY");
			BATCH_SEPARATOR=bundle_.getString("CON_SEPARATOR");
			BATCH_TIMEOUT=new Integer(bundle_.getString("CON_TIMEOUT")).intValue();
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
	}*/
	public ElectronicBillingExecutor(int pTheadIndexNumber, int pTheadSubindexNumber){
		//empresa = pEmpresa;
		//theThreadNumber=pThreadNumber;
		theThreadsArrayIndexNumber = pTheadIndexNumber; // Fila del arreglo bidireccional
		theNewThreadsArrayIndexNumber = pTheadIndexNumber; // Fila del arreglo bidireccional Original (Se usa para cambiar el comportamiento del hilo)
		theThreadArraySubindexNumber = pTheadSubindexNumber; // Columna del arreglo bidireccional
		theLastTaskResult="";
		theLastTaskDateTime=new java.util.Date();
		theLastTaskDetail="";
		try{	
			bundle_ = ResourceLocator.get(PROCCESS_PARAMS_FILENAME);
		}catch (FailedLocatingPropertiesFileException ex) {
			System.out.println("excepcion en constructor->");
			System.out.println(ex);
		}
		try{ 	
			//BATCH_INPUTDIRECTORY=bundle_.getString("CON_INPUT_DIRECTORY");
			//BATCH_OUTPUTDIRECTORY=bundle_.getString("CON_OUTPUT_DIRECTORY");
			//BATCH_SEPARATOR=bundle_.getString("CON_SEPARATOR");
			//BATCH_TIMEOUT=new Integer(bundle_.getString("CON_TIMEOUT")).intValue();
			//BATCH_TIME_EXE_TOTAL_GEN_FILE=bundle_.getString("TIME_EXECUTE_TOTAL_GEN_FILE");
			BATCH_TIME_EXE_PARTIAL_GEN_FILE=bundle_.getString("TIME_EXECUTE_PARTIAL_GEN_FILE");
			fileGenParFileTimes(BATCH_TIME_EXE_PARTIAL_GEN_FILE);
			//System.out.println("bundle_.getString->"+bundle_.getString("CON_FILES_OUT_EXTENSION"));
			//System.out.println("bundle_.getString->"+bundle_.getString("CON_OUTPUT_DIRECTORY"));
			//System.out.println("bundle_.getString->"+bundle_.getString("CON_INPUT_DIRECTORY"));
			// APSM: Trae informacion de todos los tender
			mapMDP = TrxonlineDatabaseHelper.getMDPInfo();			
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
		System.out.println("Iniciando proceso factura electrOnica desde Trxonline");	
		while(!terminar){
			try{
				setStatus("W");// trabajando				
				System.out.println("Estado: Procesando facturas electr�nica desde Trxonline. Versi�n Feb 01 de 2024. Hilo:"+theThreadsArrayIndexNumber+" Subhilo:"+theThreadArraySubindexNumber);
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
				
				Period nextPeriod_=retrieveNextPeriod(curTime_);
				if (nextPeriod_ != null && !getProcessedPeriodInHashtable(nextPeriod_, curTime_ )){
					for (int i=0;i<2;i++){
						if(!terminar && ProccessPendingTransactions()/*Conciliacion.genPartialFile(curTime_, nextPeriod_)*/){
							insertProcessedPeriod(nextPeriod_, curTime_);
							i=2;	
						}	
					}
				}	
			
				if(!terminar){
					try {
						//System.out.println("Tiempo a dormir->"+(sleepTime_+2)*60000);
						System.out.println("Estado: Durmiendo. Hilo:"+theThreadsArrayIndexNumber+" Subhilo:"+theThreadArraySubindexNumber);
						setStatus("S"); // durmiendo
						//sleep(300000); // Duerme durante 5 minutos esperando para verificar si hay una nueva hora programada para ejecutar el trabajo.
						sleep(120000); // Duerme durante 30 segundos esperando la creaci�n del pr�ximo archivo.
						setStatus("A"); // despierto
						System.out.println("Estado: Despierto. Hilo:"+theThreadsArrayIndexNumber+" Subhilo:"+theThreadArraySubindexNumber);
						Date currDate_ = new Date();
						//ElectronicBillingExecutor currThread_ = AutomaticJobsProcessManager.getElectronicBillingService(theThreadNumber);
						//AutomaticJobsProcessManager.getElectronicBillingService(theThreadNumber).setLastTaskDateTime(currDate_);
						setLastTaskDateTime(currDate_);
					} catch (InterruptedException e){
						e.printStackTrace();
						System.out.println("Hilo interrumpido. Hilo:"+theThreadsArrayIndexNumber+" Subhilo:"+theThreadArraySubindexNumber);
						setTerminar(true);
					}
				}
				firstTime_=false;
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("Exception en el bloque principal del hilo Simple. Hilo:"+theThreadsArrayIndexNumber+" Subhilo:"+theThreadArraySubindexNumber);
			}catch (Throwable t){
				t.printStackTrace();
				System.out.println("Throwable en el bloque principal del hilo Simple. Hilo:"+theThreadsArrayIndexNumber+" Subhilo:"+theThreadArraySubindexNumber);
			}					
		} // fin while			
		setStatus("I");
		System.out.println("Terminando proceso Conciliacion. Hilo:"+theThreadsArrayIndexNumber+" Subhilo:"+theThreadArraySubindexNumber);
	}
	// CACS: Este m�todo retorna la �ltima hora a la que se debi� ejecutar el proceso.
	// Es decir, que funciona con el tiempo cumplido.
	
	private String getFrom(){
		String from_ =	BusinessLogicParameters.DB_TABLE_TRX_ONLINE_HEADER +" a";
		return from_;
	}
	
	private String getThread04From(){
		//String from_ =	BusinessLogicParameters.DB_TABLE_TRX_ONLINE_HEADER +" a, TRX_FECO_TRXS_RECHAZA_CARVAJAL b";
		String from_ =	BusinessLogicParameters.DB_TABLE_TRX_ONLINE_HEADER +" a";
		return from_;
	}	
	
	private String getWhere(){
		String where_ = "";
		//if(theThreadsArrayIndexNumber==0){
		if(theNewThreadsArrayIndexNumber==0){
			where_ = 
				"(a."+BusinessLogicParameters.DB_COLUM_TOL_HEADER_ELECT_BILLING_STATUS+" = '"+REG_STATUS_TO_SEND_WITHOUT_CONEX_CARVAJAL+"' OR " +
				"a."+BusinessLogicParameters.DB_COLUM_TOL_HEADER_ELECT_BILLING_STATUS+" = '"+REG_STATUS_TO_SEND_WITH_CONEX_CARVAJAL+"') "+
				" AND a."+BusinessLogicParameters.DB_COLUM_TOL_STORE+" <= "+STORE_RANGE_01; //+" AND " +
				//" a.STORE = 520 AND a.TERMINAL = 1 AND a.TRANSNUM = 254 AND a.DAY ='2024-06-07' AND a.TIME = '1514'";
				//" AND a.STORE = 841 AND a.TERMINAL = 001 AND a.TRANSNUM=0147 AND a.DAY='2024-05-23' AND a.TIME='1343'";		
				//"a.STORE = 781 AND a.TERMINAL = 1 AND a.TRANSNUM = 18 AND a.DAY = '2024-05-22' AND a.TIME = '1258'";		
				//"a.STORE = 781 AND a.TERMINAL = 1 AND a.TRANSNUM = 17 AND a.DAY = '2024-05-22' AND a.TIME = '1257'";
				//"a.STORE = 781 AND a.TERMINAL = 1 AND a.TRANSNUM = 14 AND a.DAY = '2024-05-22' AND a.TIME = '1254'";
				//"a.STORE = 573 AND a.TERMINAL = 1 AND a.TRANSNUM = 17 AND a.DAY = '2024-05-24' AND a.TIME = '1031'";		
				//"a.STORE = 535 AND a.TERMINAL = 9 AND a.TRANSNUM = 37 AND a.DAY = '2024-05-23' AND a.TIME='1000'";		
				//"a.STORE = 802 AND a.TERMINAL=6 AND a.TRANSNUM=24 AND a.DAY = '2024-05-16' AND a.TIME='1001'"; 		
				//"a.STORE = 719 AND a.TERMINAL=1 AND a.TRANSNUM=100 AND a.DAY='2024-05-10' AND a.TIME='1142'";
				//"a.STORE = 573 AND a.TERMINAL=1 AND a.TRANSNUM=79 AND a.DAY='2024-05-15' AND a.TIME='1510'";
				//"a.STORE = 515 AND a.TERMINAL = 9 AND TRANSNUM = 11 AND a.DAY = '2024-05-01' AND a.TIME =  '1013'";
				//"a.STORE = 908 AND a.TERMINAL = 001 AND TRANSNUM = 0010 AND a.DAY = '2024-05-01' AND a.TIME =  '1420'";
				//where_ += " AND a.DAY = '2024-05-30' AND STORE NOT IN (841)"; // Fecha más atrasada
				//where_ += " AND STORE IN (841)"; // Fecha más atrasada
			//where_ +=" AND STORE = 520 "; //AND a.DAY = '2024-05-30'"; // AND STORE NOT IN (641,647,648,623,563)"; // Fecha más atrasada		
				//"a."+BusinessLogicParameters.DB_COLUM_TOL_HEADER_ELECT_BILLING_STATUS+" = '"+REG_STATUS_ACTIVE+"' ";
				//"AND a.STORE > 515 "; // AND a.TERMINAL = 003 AND a.TRANSNUM = 0180 AND a.DAY = '2024-03-27' AND a.TIME = '1724'"; // 27 Productos mdp
				//"AND a.STORE = 563 AND a.TERMINAL = 003 AND a.TRANSNUM = 0180 AND a.DAY = '2024-03-27' AND a.TIME = '1724'"; // 27 Productos mdp
				//"AND a.STORE = 905 AND a.TERMINAL = 001 AND a.TRANSNUM = 0151 AND a.DAY = '2024-03-27' AND a.TIME = '1450'"; // 22 Productos
				
				// Esa trx ya subi�, tiene su propio JSON
				//"AND a.STORE = 809 AND a.TERMINAL = 001 AND a.TRANSNUM = 52 AND a.DAY = '2024-03-22' AND a.TIME = '1333'";
				//"AND a.STORE = 647 AND a.TERMINAL =1 AND a.TRANSNUM = 82 AND a.DAY = '2024-02-15' AND a.TIME = '1755'"; // IVA: 19 ICUI: 15
				//" a.STORE = 512 AND a.TERMINAL =43 AND a.TRANSNUM = 9 AND a.DAY = '2024-04-13' AND a.TIME = '1438'"; // Trx DESA
				//"AND a.STORE = 512 AND a.TERMINAL =43 AND a.TRANSNUM = 18 AND a.DAY = '2024-04-15' AND a.TIME = '1309'"; // Ningún impuesto y un solo producto			 
				//" AND a.STORE = 998 AND a.TERMINAL =002 AND a.TRANSNUM = 0005 AND a.DAY = '2024-04-26' AND a.TIME = '1348'"; // Ningún impuesto y un solo producto
				//STORE =  AND TERMINAL =  AND TRANSNUM =  AND DAY = '2024-04-26' AND TIME = ''

				//"a."+BusinessLogicParameters.DB_COLUM_TOL_STORE+" <= "+STORE_RANGE_01;
			if(theThreadArraySubindexNumber == 0)
				where_ += " ORDER BY "+BusinessLogicParameters.DB_COLUM_TOL_INSERT_DATE_TIME+ " ASC ";
			else if(theThreadArraySubindexNumber == 1)
				where_ += " ORDER BY "+BusinessLogicParameters.DB_COLUM_TOL_INSERT_DATE_TIME+ " DESC ";
			else if(theThreadArraySubindexNumber == 2)
				where_ += " ORDER BY "+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER+ " ASC ";
			else if(theThreadArraySubindexNumber == 3)
				where_ += " ORDER BY "+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER+ " DESC ";
			else
				where_ += " ORDER BY "+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL+ " ASC ";
		//}else if(theThreadsArrayIndexNumber==1){ 	
		}else if(theNewThreadsArrayIndexNumber==1){	
			where_ = 
				"(a."+BusinessLogicParameters.DB_COLUM_TOL_HEADER_ELECT_BILLING_STATUS+" = '"+REG_STATUS_TO_SEND_WITHOUT_CONEX_CARVAJAL+"' OR " +
				"a."+BusinessLogicParameters.DB_COLUM_TOL_HEADER_ELECT_BILLING_STATUS+" = '"+REG_STATUS_TO_SEND_WITH_CONEX_CARVAJAL+"')  AND " +
				"a."+BusinessLogicParameters.DB_COLUM_TOL_STORE+" > "+STORE_RANGE_01+" AND " +
				"a."+BusinessLogicParameters.DB_COLUM_TOL_STORE+" <= "+STORE_RANGE_02;
			//where_ +=	" AND a.DAY > '2024-05-01'"; // Fecha más atrasada
			if(theThreadArraySubindexNumber == 0)
				where_ += " ORDER BY "+BusinessLogicParameters.DB_COLUM_TOL_INSERT_DATE_TIME+ " ASC ";
			else if(theThreadArraySubindexNumber == 1)
				where_ += " ORDER BY "+BusinessLogicParameters.DB_COLUM_TOL_INSERT_DATE_TIME+ " DESC ";
			else if(theThreadArraySubindexNumber == 2)
				where_ += " ORDER BY "+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER+ " ASC ";
			else if(theThreadArraySubindexNumber == 3)
				where_ += " ORDER BY "+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER+ " DESC ";
			else
				where_ += " ORDER BY "+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL+ " ASC ";			
		//}else if(theThreadsArrayIndexNumber==2){ 	
		}else if(theNewThreadsArrayIndexNumber==2){	
			where_ = 
				"(a."+BusinessLogicParameters.DB_COLUM_TOL_HEADER_ELECT_BILLING_STATUS+" = '"+REG_STATUS_TO_SEND_WITHOUT_CONEX_CARVAJAL+"' OR " +
				"a."+BusinessLogicParameters.DB_COLUM_TOL_HEADER_ELECT_BILLING_STATUS+" = '"+REG_STATUS_TO_SEND_WITH_CONEX_CARVAJAL+"')  AND " +
				//"a."+BusinessLogicParameters.DB_COLUM_TOL_STORE+" > "+STORE_RANGE_02+" AND " +
				"a."+BusinessLogicParameters.DB_COLUM_TOL_STORE+" > "+STORE_RANGE_02+" AND " +
				"a."+BusinessLogicParameters.DB_COLUM_TOL_STORE+" <= "+STORE_RANGE_03;
			//where_ +=	" AND a.DAY > '2024-05-01' AND STORE NOT IN (623)"; // Fecha más atrasada
			if(theThreadArraySubindexNumber == 0)
				where_ += " ORDER BY "+BusinessLogicParameters.DB_COLUM_TOL_INSERT_DATE_TIME+ " ASC ";
			else if(theThreadArraySubindexNumber == 1)
				where_ += " ORDER BY "+BusinessLogicParameters.DB_COLUM_TOL_INSERT_DATE_TIME+ " DESC ";
			else if(theThreadArraySubindexNumber == 2)
				where_ += " ORDER BY "+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER+ " ASC ";
			else if(theThreadArraySubindexNumber == 3)
				where_ += " ORDER BY "+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER+ " DESC ";
			else
				where_ += " ORDER BY "+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL+ " ASC ";
		//}else if(theThreadsArrayIndexNumber==3){ 	
		}else if(theNewThreadsArrayIndexNumber==3){	
			where_ = 
				"(a."+BusinessLogicParameters.DB_COLUM_TOL_HEADER_ELECT_BILLING_STATUS+" = '"+REG_STATUS_TO_SEND_WITHOUT_CONEX_CARVAJAL+"' OR " +
				"a."+BusinessLogicParameters.DB_COLUM_TOL_HEADER_ELECT_BILLING_STATUS+" = '"+REG_STATUS_TO_SEND_WITH_CONEX_CARVAJAL+"')  AND " +
				//"a."+BusinessLogicParameters.DB_COLUM_TOL_STORE+" > "+STORE_RANGE_03;
				"a."+BusinessLogicParameters.DB_COLUM_TOL_STORE+" > "+STORE_RANGE_03;		
			//where_ +=	" AND a.DAY > '2024-05-01'"; // AND STORE NOT IN (623)"; // Fecha más atrasada
			if(theThreadArraySubindexNumber == 0)
				where_ += " ORDER BY "+BusinessLogicParameters.DB_COLUM_TOL_INSERT_DATE_TIME+ " ASC ";
			else if(theThreadArraySubindexNumber == 1)
				where_ += " ORDER BY "+BusinessLogicParameters.DB_COLUM_TOL_INSERT_DATE_TIME+ " DESC ";
			else if(theThreadArraySubindexNumber == 2)
				where_ += " ORDER BY "+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER+ " ASC ";
			else if(theThreadArraySubindexNumber == 3)
				where_ += " ORDER BY "+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER+ " DESC ";
			else
				where_ += " ORDER BY "+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL+ " ASC ";
		}else if(theNewThreadsArrayIndexNumber==4){
			//where_ = "STORE = 535	AND TERMINAL = 6 AND TRANSNUM=14 AND DAY='2024-04-30' AND DATA1= '65' AND TIME=	'0720'";
			//where_ = "STORE = 515 AND TERMINAL = 7 AND TRANSNUM = 35 AND DAY = '2024-05-08' AND TIME = '1232'";
			//where_ = "STORE = 515 AND TERMINAL = 1 AND TRANSNUM = 67 AND DAY = '2024-05-15' AND  TIME = '1013'";
			//where_ = "STORE = 800 AND TERMINAL = 1 AND TRANSNUM = 12 AND DAY = '2024-12-03' AND  TIME = '1233'";
			//where_ = "STORE = 560 AND TERMINAL = 3 AND TRANSNUM = 55 AND DAY = '2024-12-03' AND  TIME = '0958'";
//			where_ = "STORE = 560 AND TERMINAL = 3 AND TRANSNUM = 87 AND DAY = '2024-12-03' AND  TIME = '1221'";
//			where_ += 	" a.STORE= 998 and a.TERMINAL=2 and a.TRANSNUM in( 12, 8, 4, 6, 13, 7 )and a.DAY = '2025-03-14' "; //apsm 12/03/2025 pruebas notacredito 
			where_ = "(a.ELECT_BILL_STATUS = '2' OR a.ELECT_BILL_STATUS = '3') ";
			//TODO QUITAR PRODUCCION
//			where_ += 	" and a.STORE= 620 and a.TERMINAL=1 and a.TRANSNUM = 58 and a.DAY = '2025-02-05' and a.time = '1613'  ";
			//where_ += 	" and a.STORE= 620 and a.TERMINAL=1 and a.TRANSNUM = 25 and a.DAY = '2025-03-17' and a.time = '1119'  ";//620	1	25	2025-03-17	1119
//			where_ += 	" and a.STORE= 620 and a.TERMINAL=1 and a.TRANSNUM = 52 and a.DAY = '2025-02-05' and a.time = '1530'  ";//620	1	52	2025-02-05	1530 
//			where_ += 	" and a.STORE= 620 and a.TERMINAL=1 and a.TRANSNUM = 72 and a.DAY = '2025-02-05' and a.time = '1723'  ";//620	1	72	2025-02-05	1723
//			where_ += 	" and a.STORE= 998 and a.TERMINAL=2 and a.TRANSNUM = 5 and a.DAY = '2025-06-26' and a.time = '0927' ";
//			where_ += 	" and a.STORE= 620 and a.TERMINAL=1 and a.TRANSNUM = 6 and a.DAY = '2025-03-06' and a.time = '0914' "; //apsm 12/03/2025 pruebas notacredito
//			where_ += 	" and a.STORE= 620 and a.TERMINAL=1 and a.TRANSNUM = 22 and a.DAY = '2025-03-08' and a.time = '1218' "; //apsm 12/03/2025 pruebas notacredito
			//where_ += 	" a.STORE= 560 and a.TERMINAL=1 and a.TRANSNUM = 52 and a.DAY = '2025-03-01' and a.time = '0314' "; //apsm 12/03/2025 pruebas notacredito560	1	52	2025-03-01	0314
			where_ += 	" and a.STORE= 520 and a.TERMINAL=1 and a.TRANSNUM = 12 and a.DAY = '2025-06-17' and a.TIME = '1151' ";//apsm 18/07/2025

			//			where_ += 	" and a.DAY = '2025-03-25' "; //apsm 25/03/2025 pruebas texto tributario
			//where_ = "STORE = 797 AND TERMINAL = 1 AND TRANSNUM = 49 AND DAY = '2024-11-27' AND  TIME = '1217'";
			
			/*
			SELECT * FROM TPOS_HEADER a, TRX_FECO_TRXS_RECHAZA_CARVAJAL b WHERE 
			a.STORE = TFR_STORE AND 
			a.TERMINAL = b. TFR_TERMINAL AND 
			a.TRANSNUM = b.TFR_TRANSNUM AND 
			a. DAY = b.TFR_DAY AND 
			a.TIME = b.TFR_TIME AND 
			TFR_ERROR_DATA LIKE 'CLIENTE NO ENCONTRADO EN DB. No IDENTIFICACION%'
			*/
			//where_ = "(a.ELECT_BILL_STATUS = '2' OR a.ELECT_BILL_STATUS = '3')  AND a.DAY = '2024-11-12'"; 
			//where_ = "(a.ELECT_BILL_STATUS = '2' OR a.ELECT_BILL_STATUS = '3') ";
			//where_ = "STORE = 901 AND TERMINAL = 1 AND TRANSNUM = 15 AND DAY = '2024-09-13' AND TIME = '0839'";
			//where_ = "STORE = 512 AND TERMINAL = 2 AND TRANSNUM = 75 AND DAY = '2024-09-30' AND TIME = '1737'";
			//where_ = "STORE = 802 AND TERMINAL = 4 AND TRANSNUM = 102 AND DAY = '2024-10-21' AND TIME = '1243'";
			 
			 //AND  b.TFC_TERMINAL=2 AND  b.TFC_NUM_FACTURA=75 AND  b.TFC_FECHA='2024-09-30' AND  b.TFC_HORA='1737 '
			//where_ = 
					//"a.STORE = b.TFR_STORE AND a.TERMINAL = b.TFR_TERMINAL AND a.TRANSNUM = b.TFR_TRANSNUM AND a.DAY = b.TFR_DAY AND a.TIME = b.TFR_TIME AND\r\n"
					//+ "TFR_ERROR_CODE = 'EDI001' AND TFR_ERROR_DATA = 'INCONSISTENCIA TOTALES TENDERS - TRX TOTAL. DIRERENCIA:9' AND TFR_DAY >= '2024-03-29' AND TFR_DAY <= '2024-06-21' AND TFR_STATUS IS NULL ORDER BY TFR_DAY DESC ";
				/*
				//where_ +=	" AND a.DAY > '2024-05-01'"; // AND STORE NOT IN (623)"; // Fecha más atrasada
				if(theThreadArraySubindexNumber == 0)
					where_ += " ORDER BY "+BusinessLogicParameters.DB_COLUM_TOL_INSERT_DATE_TIME+ " ASC ";
				else if(theThreadArraySubindexNumber == 1)
					where_ += " ORDER BY "+BusinessLogicParameters.DB_COLUM_TOL_INSERT_DATE_TIME+ " DESC ";
				else if(theThreadArraySubindexNumber == 2)
					where_ += " ORDER BY "+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER+ " ASC ";
				else if(theThreadArraySubindexNumber == 3)
					where_ += " ORDER BY "+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER+ " DESC ";
				else
					where_ += " ORDER BY "+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL+ " ASC ";
				*/	
			}
		where_ += " FETCH FIRST "+BusinessLogicParameters.MAX_REGS_TO_RETRIEVE_FROM_DB+" ROWS ONLY";
		return where_;
	}

	private String getSelect(){
		String select_ = "";
		
		//if(theThreadNumber==1){
			select_ = 
				" a."+BusinessLogicParameters.DB_COLUM_TOL_STORE+" almacen,"+
				" a."+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL+" terminal,"+
				" a."+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER+" numTrx,"+
				" a."+BusinessLogicParameters.DB_COLUM_TOL_DAY+" fecha,"+
				" a."+BusinessLogicParameters.DB_COLUM_TOL_TIME+" hora,"+
				" a."+BusinessLogicParameters.DB_COLUM_TOL_DATE_TIME+" fechaHora,"+
				" a."+BusinessLogicParameters.DB_COLUM_TOL_HEADER_ELECT_BILLING_STATUS+" electBillStatus";
		/*	
		}else if(theThreadNumber==2){ 	
			select_ = 
				" a."+BusinessLogicParameters.DB_COLUM_TOL_STORE+" almacen,"+
				" a."+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL+" terminal,"+
				" a."+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER+" numTrx,"+
				" a."+BusinessLogicParameters.DB_COLUM_TOL_DAY+" fecha,"+
				" a."+BusinessLogicParameters.DB_COLUM_TOL_TIME+" hora,"+
				" a."+BusinessLogicParameters.DB_COLUM_TOL_DATE_TIME+" fechaHora";
		}else if(theThreadNumber==3){ 	
			select_ = 
				" a."+BusinessLogicParameters.DB_COLUM_TOL_STORE+" almacen,"+
				" a."+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL+" terminal,"+
				" a."+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER+" numTrx,"+
				" a."+BusinessLogicParameters.DB_COLUM_TOL_DAY+" fecha,"+
				" a."+BusinessLogicParameters.DB_COLUM_TOL_TIME+" hora,"+
				" a."+BusinessLogicParameters.DB_COLUM_TOL_DATE_TIME+" fechaHora";
		}else if(theThreadNumber==4){ 	
			select_ = 
				" a."+BusinessLogicParameters.DB_COLUM_TOL_STORE+" almacen,"+
				" a."+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL+" terminal,"+
				" a."+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER+" numTrx,"+
				" a."+BusinessLogicParameters.DB_COLUM_TOL_DAY+" fecha,"+
				" a."+BusinessLogicParameters.DB_COLUM_TOL_TIME+" hora,"+
				" a."+BusinessLogicParameters.DB_COLUM_TOL_DATE_TIME+" fechaHora";
		}*/
		return select_;
	}
	
	// Revisa s� la trx se deber�a ser enviada o no. true: se debe enviar, false: no se debe enviar  
	private boolean reviewTrx(TrxonlineTransactionInDBVO pTrxToFindData){
		boolean answer_ = false;
		if(theNewThreadsArrayIndexNumber == theThreadsArrayIndexNumber && theThreadArraySubindexNumber == 0){ 
			System.out.println("Es el hilo original. Se debe enviar");
			answer_ = true;
		}else{
			String statusToReview_ = REG_STATUS_TO_SEND_WITHOUT_CONEX_CARVAJAL;
			String statusToReview2_ = REG_STATUS_TO_SEND_WITH_CONEX_CARVAJAL;
			if(TrxonlineDatabaseHelper.isThereATrxWithThisStatus(pTrxToFindData,statusToReview_,statusToReview2_)){
				System.out.println("Trx tiene el estado "+statusToReview_+" or "+statusToReview2_+" en la DB");
				answer_ = true;
			}else
				System.out.println("Trx NO tiene el estado "+statusToReview_+" ni el estado "+statusToReview2_);
		}
		return answer_;
	}
	
	private boolean ProccessPendingTransactions(){
		System.out.println("Desde ProccessPendingTransactions. Hilo:"+theThreadsArrayIndexNumber+" Subhilo:"+theThreadArraySubindexNumber);
		boolean answer_ = true;
		theNewThreadsArrayIndexNumber = theThreadsArrayIndexNumber; // Se asigna el original para que comience siempre con su propio hilo.
		// CACS: Traemos las trxs que est�n pendientes de procesar
		String select_ = getSelect();
		String from_ = "";
		if(theNewThreadsArrayIndexNumber==4){
			from_ = getThread04From();
		}else
			from_ = getFrom();
		ServiceOrderVo order_ = null; 

		//where_ = " a.TRANSNUM = 45 AND " + where_;
			String trxInProccessingId_ = null;	
		try {
			boolean thereAreRegsInDB_ = false;
			boolean exit_ = false;
			//long iterations_ = 0;
			do{
				String where_ = getWhere();
				//BusinessLogicParameters.DB_TABLE_TRX_ONLINE_USER_DATA+" b" ;
				System.out.println("Recuperando trx a enviar->"+where_);				
				List lstTransactions_ =
						DBUtil.select(
							select_,						
							from_,
							where_,		
							TrxonlineTransactionInDBVO.class);
				System.out.println("No trx recuperadas:"+lstTransactions_.size()+". Hilo:"+theThreadsArrayIndexNumber+" Subhilo:"+theThreadArraySubindexNumber);
				System.out.println("where:"+where_+". Hilo:"+theThreadsArrayIndexNumber+" Subhilo:"+theThreadArraySubindexNumber+" Nuevo nilo:"+theNewThreadsArrayIndexNumber);
				if(lstTransactions_.size()>0){
					thereAreRegsInDB_ = lstTransactions_.size()==BusinessLogicParameters.MAX_REGS_TO_RETRIEVE_FROM_DB;
					Iterator itTransactions_=lstTransactions_.iterator();
					if (itTransactions_.hasNext()){ // 
						TrxonlineTransactionInDBVO trx_=null;
						//while(itTransactions_.hasNext() && !exit_ && AutomaticJobsProcessManager.getElectronicBillingService().isActive()){
						while(itTransactions_.hasNext() && !exit_ && isActive()){
							try{
								trx_=(TrxonlineTransactionInDBVO)itTransactions_.next();
								//TrxsInProccessingThread00
								trxInProccessingId_ = StringFormatter.align(trx_.getAlmacen()+"",4,'0',0,4) + StringFormatter.align(trx_.getTerminal()+"",4,'0',0,4) + 
													  StringFormatter.align(trx_.getNumTrx()+"",6,'0',0,6) + getTLogDateTime2(trx_.getFechaHora());
								//pOrder.getStore()+StringFormatter.align(pOrder.getTerminal(),3,'0',0,3)+StringFormatter.align(pOrder.getTransaction(),4,'0',0,4)+pOrder.getDateTime();
								System.out.println("trxInProccessingId_->"+trxInProccessingId_);
								if(!isTrxsInProccessing(trxInProccessingId_) && !isTrxsProccessed(trxInProccessingId_) && reviewTrx(trx_)){
									order_ = getServiceOrderVo(trx_);//TODO XYZ
									for(int i=0;i<order_.getTendersList().size();i++){
										ServiceOrderTenderVo tendVo_ = (ServiceOrderTenderVo)order_.getTendersList().get(i);
										if(!tendVo_.isVoided()) {
											System.out.println("Id tender->"+i+"->"+tendVo_.getTenderId());
											ServiceOrderTenderVo nuevoTenderVo_ = mapMDP.get(tendVo_.getTenderId());
											if(nuevoTenderVo_ != null) {
												tendVo_.setCodMDPDian(nuevoTenderVo_.getCodMDPDian());
												tendVo_.setTenderDescription(nuevoTenderVo_.getTenderDescription());
												tendVo_.setMdpDian(nuevoTenderVo_.getMdpDian());
											}else
												throw new DIANTenderConfigurationNotFoundException("CONFIGURACION DIAN PARA ENVIO TENDER NO ENCONTRADA.TENDER ID:"+tendVo_.getTenderId());
										}
									}
									if (order_ != null){
										System.out.println("Ya se tiene la orden");
										order_.removeVoidItems();
										order_.groupTenders();
										if(sendOrderToWebService(order_, trx_.getElectBillStatus())){
											System.out.println("Orden enviada satisfactoriamente a WS");
											//removeTrxsInProccessing(trxInProccessingId_);
											insertTrxProccessed(trxInProccessingId_);
										}else{
											System.out.println("Orden NO pudo ser enviada satisfactoriamente a WS");
											answer_ = false;
										}	
									}
								}else
									System.out.println("Trx "+trxInProccessingId_+" estA siendo procesada por otro hilo.");
							}catch(CustomerTrxNotFoundException e){
								System.out.println("Comfandi Customer User Data not found.");
								System.out.println(trx_.getAlmacen());
								System.out.println(trx_.getTerminal());
								System.out.println(trx_.getNumTrx());
								System.out.println(trx_.getFechaHora());
								String dateTime_ = getTLogDateTime(trx_.getFechaHora());
								String strTrxId_ = trx_.getAlmacen()+StringFormatter.align(trx_.getTerminal()+"",3,'0',0,3)+StringFormatter.align(trx_.getNumTrx()+"",4,'0',0,4)+dateTime_;
								if(TrxonlineDatabaseHelper.changeElectronicBillingTrxStatus(trx_.getAlmacen()+"", trx_.getTerminal()+"",trx_.getNumTrx()+"",dateTime_,getToReviewStatus(   trx_.getElectBillStatus()   )   )  ){
									System.out.println("Insertando registro en tabla de errores");
									// insertWSTransactionErrorAnswer(trx_,dateTime_, "TRX NO ENVIADA A EXPRESSMED. NO TIENE USER DATA DE CLIENTE", strTrxId_);
									String continFiscalNum_ = "";
									if(order_ != null && order_.getFiscalPrefix()!=null && order_.getFiscalBillNumber()!=null)
										continFiscalNum_ = order_.getFiscalPrefix().trim() + order_.getFiscalBillNumber().trim();
									insertWSTransactionErrorAnswer(trx_, dateTime_, e.getMessage(), CarvajalProcessUtils.ID_EDC_001, strTrxId_, continFiscalNum_);
									// public static boolean insertWSTransactionErrorAnswer(TrxonlineTransactionInDBVO pTrxToInsert, String pDateTime, String pErrorMsg, String pErrorCode, String pIUT){
								}else
									System.out.println("Error actualizando trx con error a estado 'P:Pending'");
							}catch(FiscalInformationNotFoundException e){
								System.out.println("Comfandi fiscal User Data not found.");
								System.out.println(trx_.getAlmacen());
								System.out.println(trx_.getTerminal());
								System.out.println(trx_.getNumTrx());
								System.out.println(trx_.getFechaHora());
								String dateTime_ = getTLogDateTime(trx_.getFechaHora());
								String strTrxId_ = trx_.getAlmacen()+StringFormatter.align(trx_.getTerminal()+"",3,'0',0,3)+StringFormatter.align(trx_.getNumTrx()+"",4,'0',0,4)+dateTime_;
								if(TrxonlineDatabaseHelper.changeElectronicBillingTrxStatus(trx_.getAlmacen()+"", trx_.getTerminal()+"",trx_.getNumTrx()+"",dateTime_,getToReviewStatus(   trx_.getElectBillStatus()   ))){
									System.out.println("Insertando registro en tabla de errores");
									String continFiscalNum_ = "";
									if(order_ != null && order_.getFiscalPrefix()!=null && order_.getFiscalBillNumber()!=null)
										continFiscalNum_ = order_.getFiscalPrefix().trim() + order_.getFiscalBillNumber().trim();									
									insertWSTransactionErrorAnswer(trx_, dateTime_, CarvajalProcessUtils.ID_EDF_MSG_001, CarvajalProcessUtils.ID_EDF_001, strTrxId_, continFiscalNum_);
									// FacturaElectronicaDBHelper.insertWSTransactionErrorAnswer(trx_.getAlmacen()+"", trx_.getTerminal()+"",trx_.getNumTrx()+"",dateTime_, "TRX NO ENVIADA A EXPRESSMED. NO TIENE USER DATA FISCAL", CarvajalProcessUtils.ID_PRO_01, strTrxId_);
								}else
									System.out.println("Error actualizando trx con error a estado 'P:Pending'");
							}catch(ItemDataException e){
								System.out.println("Error en datos de item.");
								System.out.println(trx_.getAlmacen());
								System.out.println(trx_.getTerminal());
								System.out.println(trx_.getNumTrx());
								System.out.println(trx_.getFechaHora());
								String dateTime_ = getTLogDateTime(trx_.getFechaHora());
								String strTrxId_ = trx_.getAlmacen()+StringFormatter.align(trx_.getTerminal()+"",3,'0',0,3)+StringFormatter.align(trx_.getNumTrx()+"",4,'0',0,4)+dateTime_;
								if(TrxonlineDatabaseHelper.changeElectronicBillingTrxStatus(trx_.getAlmacen()+"", trx_.getTerminal()+"",trx_.getNumTrx()+"",dateTime_,getToReviewStatus(   trx_.getElectBillStatus()   ))){
									System.out.println("Insertando registro en tabla de errores");
									String continFiscalNum_ = "";
									if(order_ != null && order_.getFiscalPrefix()!=null && order_.getFiscalBillNumber()!=null)
										continFiscalNum_ = order_.getFiscalPrefix().trim() + order_.getFiscalBillNumber().trim();									
									insertWSTransactionErrorAnswer(trx_, dateTime_, e.getMessage(), CarvajalProcessUtils.ID_EDI_001, strTrxId_, continFiscalNum_);
									// FacturaElectronicaDBHelper.insertWSTransactionErrorAnswer(trx_.getAlmacen()+"", trx_.getTerminal()+"",trx_.getNumTrx()+"",dateTime_, "TRX NO ENVIADA A EXPRESSMED. NO TIENE USER DATA FISCAL", CarvajalProcessUtils.ID_PRO_01, strTrxId_);
								}else
									System.out.println("Error actualizando trx con error a estado 'P:Pending'");
							}catch(StoreNotFoundException e){
								System.out.println("Error en datos del centro de costo");
								System.out.println(trx_.getAlmacen());
								System.out.println(trx_.getTerminal());
								System.out.println(trx_.getNumTrx());
								System.out.println(trx_.getFechaHora());
								String dateTime_ = getTLogDateTime(trx_.getFechaHora());
								String strTrxId_ = trx_.getAlmacen()+StringFormatter.align(trx_.getTerminal()+"",3,'0',0,3)+StringFormatter.align(trx_.getNumTrx()+"",4,'0',0,4)+dateTime_;
								if(TrxonlineDatabaseHelper.changeElectronicBillingTrxStatus(trx_.getAlmacen()+"", trx_.getTerminal()+"",trx_.getNumTrx()+"",dateTime_,getToReviewStatus(   trx_.getElectBillStatus()   ))){
									System.out.println("Insertando registro en tabla de errores");
									String continFiscalNum_ = "";
									if(order_ != null && order_.getFiscalPrefix()!=null && order_.getFiscalBillNumber()!=null)
										continFiscalNum_ = order_.getFiscalPrefix().trim() + order_.getFiscalBillNumber().trim();							
									insertWSTransactionErrorAnswer(trx_, dateTime_, e.getMessage(), CarvajalProcessUtils.ID_EDI_001, strTrxId_, continFiscalNum_);
									// FacturaElectronicaDBHelper.insertWSTransactionErrorAnswer(trx_.getAlmacen()+"", trx_.getTerminal()+"",trx_.getNumTrx()+"",dateTime_, "TRX NO ENVIADA A EXPRESSMED. NO TIENE USER DATA FISCAL", CarvajalProcessUtils.ID_PRO_01, strTrxId_);
								}else
									System.out.println("Error actualizando trx con error a estado 'P:Pending'");
							}catch(DataInconsistencyInTotalsException e){
								System.out.println("Error en totales de trx");
								System.out.println(trx_.getAlmacen());
								System.out.println(trx_.getTerminal());
								System.out.println(trx_.getNumTrx());
								System.out.println(trx_.getFechaHora());
								String dateTime_ = getTLogDateTime(trx_.getFechaHora());
								String strTrxId_ = trx_.getAlmacen()+StringFormatter.align(trx_.getTerminal()+"",3,'0',0,3)+StringFormatter.align(trx_.getNumTrx()+"",4,'0',0,4)+dateTime_;
								if(TrxonlineDatabaseHelper.changeElectronicBillingTrxStatus(trx_.getAlmacen()+"", trx_.getTerminal()+"",trx_.getNumTrx()+"",dateTime_, getToReviewStatus(   trx_.getElectBillStatus()   ))){
									System.out.println("Insertando registro en tabla de errores");
									String continFiscalNum_ = "";
									if(order_ != null && order_.getFiscalPrefix()!=null && order_.getFiscalBillNumber()!=null)
										continFiscalNum_ = order_.getFiscalPrefix().trim() + order_.getFiscalBillNumber().trim();										
									insertWSTransactionErrorAnswer(trx_, dateTime_, e.getMessage(), CarvajalProcessUtils.ID_EDI_001, strTrxId_, continFiscalNum_);
									// FacturaElectronicaDBHelper.insertWSTransactionErrorAnswer(trx_.getAlmacen()+"", trx_.getTerminal()+"",trx_.getNumTrx()+"",dateTime_, "TRX NO ENVIADA A EXPRESSMED. NO TIENE USER DATA FISCAL", CarvajalProcessUtils.ID_PRO_01, strTrxId_);
								}else
									System.out.println("Error actualizando trx con error a estado 'P:Pending'");
							}catch(DIANTenderConfigurationNotFoundException e) {
								System.out.println("ConfiguraciOn de tender para envio a la DIAN no encontrada en la tabla FE_MDPPOSDIAN");
								System.out.println(trx_.getAlmacen());
								System.out.println(trx_.getTerminal());
								System.out.println(trx_.getNumTrx());
								System.out.println(trx_.getFechaHora());
								String dateTime_ = getTLogDateTime(trx_.getFechaHora());
								String strTrxId_ = trx_.getAlmacen()+StringFormatter.align(trx_.getTerminal()+"",3,'0',0,3)+StringFormatter.align(trx_.getNumTrx()+"",4,'0',0,4)+dateTime_;
								if(TrxonlineDatabaseHelper.changeElectronicBillingTrxStatus(trx_.getAlmacen()+"", trx_.getTerminal()+"",trx_.getNumTrx()+"",dateTime_, getToReviewStatus(   trx_.getElectBillStatus()   ))){
									System.out.println("Insertando registro en tabla de errores");
									String continFiscalNum_ = "";
									if(order_ != null && order_.getFiscalPrefix()!=null && order_.getFiscalBillNumber()!=null)
										continFiscalNum_ = order_.getFiscalPrefix().trim() + order_.getFiscalBillNumber().trim();										
									insertWSTransactionErrorAnswer(trx_, dateTime_, e.getMessage(), CarvajalProcessUtils.ID_EDT_001, strTrxId_, continFiscalNum_);
									// FacturaElectronicaDBHelper.insertWSTransactionErrorAnswer(trx_.getAlmacen()+"", trx_.getTerminal()+"",trx_.getNumTrx()+"",dateTime_, "TRX NO ENVIADA A EXPRESSMED. NO TIENE USER DATA FISCAL", CarvajalProcessUtils.ID_PRO_01, strTrxId_);
								}else
									System.out.println("Error actualizando trx con error a estado 'P:Pending'");								
							}
							
							removeTrxsInProccessing(trxInProccessingId_);
							Date currDate_ = new Date();
							//AutomaticJobsProcessManager.getElectronicBillingService().setLastTaskDateTime(currDate_);							
							setLastTaskDateTime(currDate_);
						}
						if(theNewThreadsArrayIndexNumber != theThreadsArrayIndexNumber){
							System.out.println("Ya hizo una iteraciOn en otro hilo. Reestableciendo nUmero de hilo...");
							theNewThreadsArrayIndexNumber = theThreadsArrayIndexNumber;
						}
					}else{
						//if(!AutomaticJobsProcessManager.getElectronicBillingService().isActive()){
						if(!isActive()){
							System.out.println("Se recibi� mandato para detener tarea de procesamiento de factura electrOnica");
						}else
							System.out.println("Se procesaron todos los registros del bloque");
					}				
				}else{
					System.out.println("No hay registros para procesar");
					theNewThreadsArrayIndexNumber = getNewThreadsArrayIndexNumber();
					if(theNewThreadsArrayIndexNumber == theThreadsArrayIndexNumber){
						System.out.println("Ya diO toda la vuelta. Se debe salir del while");
						exit_ = true;
					}else{
						System.out.println("Se cambia de hilo para validar otro rango de tareas. Nuevo hilo "+theNewThreadsArrayIndexNumber);
						thereAreRegsInDB_ = true; // Se coloca en true para que continUe con el otro hilo
					}
					
				}								
				Date currDate_ = new Date();
				//AutomaticJobsProcessManager.getElectronicBillingService().setLastTaskDateTime(currDate_);
				setLastTaskDateTime(currDate_);
				/*
				try{
					System.out.println("Durmiendo 20 segundos ...");
					Thread.sleep(20000);
				}catch(Exception e){}
				*/
				
			//}while(thereAreRegsInDB_ && !exit_ && AutomaticJobsProcessManager.getElectronicBillingService().isActive());
				//iterations_++;
			}while(thereAreRegsInDB_ && !exit_ && isActive());
			System.out.println("Saliendo del while ...");
		}catch (ConnectionFailedException e) {
			// TODO Bloque catch generado automáticamente
			answer_ = false;
			e.printStackTrace();
		} catch (DBAccessException e) {
			// TODO Bloque catch generado automáticamente
			answer_ = false;
			e.printStackTrace();
		} finally{
			if(trxInProccessingId_!=null)
				removeTrxsInProccessing(trxInProccessingId_);
		}
		return answer_;
	}

	public int getNewThreadsArrayIndexNumber(){
		int answer_ = theNewThreadsArrayIndexNumber + 1;
		if(answer_>=theTheadsNumber)
			answer_ = 0;
		return answer_;
	}
	//TrxsInProccessingThread00
	//synchronized
	protected void removeTrxsInProccessing(String pTrxId){
		System.out.println("Removiendo trx->"+pTrxId);
		try{
			if(theNewThreadsArrayIndexNumber == 0){
				if(theTrxsInProccessThread00==null)
					theTrxsInProccessThread00=new Hashtable();		
				theTrxsInProccessThread00.remove(pTrxId);
			}else if(theNewThreadsArrayIndexNumber == 1){
				if(theTrxsInProccessThread01==null)
					theTrxsInProccessThread01=new Hashtable();		
				theTrxsInProccessThread01.remove(pTrxId);
			}else if(theNewThreadsArrayIndexNumber == 2){
				if(theTrxsInProccessThread02==null)
					theTrxsInProccessThread02=new Hashtable();		
				theTrxsInProccessThread02.remove(pTrxId);			
			}else if(theNewThreadsArrayIndexNumber == 3){
				if(theTrxsInProccessThread03==null)
					theTrxsInProccessThread03=new Hashtable();		
				theTrxsInProccessThread03.remove(pTrxId);			
			}
		}catch(Exception e){
			e.printStackTrace();
		}catch(Throwable t){
			t.printStackTrace();
		}
	}

	protected boolean isTrxsInProccessing(String pTrxId){
		//if(theThreadsArrayIndexNumber == 0){
		if(theNewThreadsArrayIndexNumber == 0){	
			if(theTrxsInProccessThread00==null){
				theTrxsInProccessThread00=new Hashtable();
				insertTrxInProccess(pTrxId);
				return false;
			}
			System.out.println("theTrxsInProccessThread00.size()->"+theTrxsInProccessThread00.size());
			if(theTrxsInProccessThread00.size()>100){
				theTrxsInProccessThread00.clear();
			}
			System.out.println("No trx en proceso->"+theTrxsInProccessThread00.size());
			if(theTrxsInProccessThread00.get(pTrxId)==null){
				insertTrxInProccess(pTrxId);
				return false;
			}else{
				Date dt_ = (Date)theTrxsInProccessThread00.get(pTrxId);
				Date currDt_ = new Date();
				if ((currDt_.getTime()-dt_.getTime())>3600000 || (currDt_.getTime()-dt_.getTime())<0)
					theTrxsInProccessThread00.remove(pTrxId);
			}
			return true;
		//}else if(theThreadsArrayIndexNumber == 1){
		}else if(theNewThreadsArrayIndexNumber == 1){	
			if(theTrxsInProccessThread01==null){
				theTrxsInProccessThread01=new Hashtable();
				insertTrxInProccess(pTrxId);
				return false;
			}
			System.out.println("theTrxsInProccessThread01.size()->"+theTrxsInProccessThread01.size());
			if(theTrxsInProccessThread01.size()>100){
				theTrxsInProccessThread01.clear();
			}
			if(theTrxsInProccessThread01.get(pTrxId)==null){
				insertTrxInProccess(pTrxId);
				return false;
			}else{
				Date dt_ = (Date)theTrxsInProccessThread01.get(pTrxId);
				Date currDt_ = new Date();
				if ((currDt_.getTime()-dt_.getTime())>3600000 || (currDt_.getTime()-dt_.getTime())<0)
					theTrxsInProccessThread01.remove(pTrxId);
			}
			return true;			
		//}else if(theThreadsArrayIndexNumber == 2){
		}else if(theNewThreadsArrayIndexNumber == 2){	
			if(theTrxsInProccessThread02==null){
				theTrxsInProccessThread02=new Hashtable();
				insertTrxInProccess(pTrxId);
				return false;
			}
			System.out.println("theTrxsInProccessThread02.size()->"+theTrxsInProccessThread02.size());
			if(theTrxsInProccessThread02.size()>100){
				theTrxsInProccessThread02.clear();
			}
			if(theTrxsInProccessThread02.get(pTrxId)==null){
				insertTrxInProccess(pTrxId);
				return false;
			}else{
				Date dt_ = (Date)theTrxsInProccessThread02.get(pTrxId);
				Date currDt_ = new Date();
				if ((currDt_.getTime()-dt_.getTime())>3600000 || (currDt_.getTime()-dt_.getTime())<0)
					theTrxsInProccessThread02.remove(pTrxId);
			}
			return true;			
		//}else if(theThreadsArrayIndexNumber == 3){
		}else if(theNewThreadsArrayIndexNumber == 3){	
			if(theTrxsInProccessThread03==null){
				theTrxsInProccessThread03=new Hashtable();
				insertTrxInProccess(pTrxId);
				return false;
			}
			System.out.println("theTrxsInProccessThread03.size()->"+theTrxsInProccessThread03.size());
			if(theTrxsInProccessThread03.size()>100){
				theTrxsInProccessThread03.clear();
			}
			if(theTrxsInProccessThread03.get(pTrxId)==null){
				insertTrxInProccess(pTrxId);
				return false;
			}else{
				Date dt_ = (Date)theTrxsInProccessThread03.get(pTrxId);
				Date currDt_ = new Date();
				if ((currDt_.getTime()-dt_.getTime())>3600000 || (currDt_.getTime()-dt_.getTime())<0)
					theTrxsInProccessThread03.remove(pTrxId);
			}
			return true;			
		}else if(theNewThreadsArrayIndexNumber == 4){	
				return false;
		}
		return false;
	}
	
	protected boolean isTrxsProccessed(String pTrxId){
		//if(theThreadsArrayIndexNumber == 0){
		if(theNewThreadsArrayIndexNumber == 0){	
			if(theTrxsProccessedThread00==null){
				theTrxsProccessedThread00=new Hashtable();
				//insertTrxInProccess(pTrxId);
				return false;
			}
			System.out.println("theTrxsProccessedThread00.size()->"+theTrxsProccessedThread00.size());
			if(theTrxsProccessedThread00.size()>200){
				theTrxsProccessedThread00.clear();
			}
			System.out.println("No trx en proceso->"+theTrxsProccessedThread00.size());
			if(theTrxsProccessedThread00.get(pTrxId)==null){
				//insertTrxInProccess(pTrxId);
				return false;
			}else{
				Date dt_ = (Date)theTrxsProccessedThread00.get(pTrxId);
				Date currDt_ = new Date();
				if ((currDt_.getTime()-dt_.getTime())>3600000 || (currDt_.getTime()-dt_.getTime())<0)
					theTrxsProccessedThread00.remove(pTrxId);
			}
			System.out.println("Trx ya procesada 0");
			return true;
		//}else if(theThreadsArrayIndexNumber == 1){
		}else if(theNewThreadsArrayIndexNumber == 1){	
			if(theTrxsProccessedThread01==null){
				theTrxsProccessedThread01=new Hashtable();
				//insertTrxInProccess(pTrxId);
				return false;
			}
			System.out.println("theTrxsProccessedThread01.size()->"+theTrxsProccessedThread01.size());
			if(theTrxsProccessedThread01.size()>200){
				theTrxsProccessedThread01.clear();
			}
			if(theTrxsProccessedThread01.get(pTrxId)==null){
				//insertTrxInProccess(pTrxId);
				return false;
			}else{
				Date dt_ = (Date)theTrxsProccessedThread01.get(pTrxId);
				Date currDt_ = new Date();
				if ((currDt_.getTime()-dt_.getTime())>3600000 || (currDt_.getTime()-dt_.getTime())<0)
					theTrxsProccessedThread01.remove(pTrxId);
			}
			System.out.println("Trx ya procesada 1");
			return true;			
		//}else if(theThreadsArrayIndexNumber == 2){
		}else if(theNewThreadsArrayIndexNumber == 2){	
			if(theTrxsProccessedThread02==null){
				theTrxsProccessedThread02=new Hashtable();
				//insertTrxInProccess(pTrxId);
				return false;
			}
			System.out.println("theTrxsProccessedThread02.size()->"+theTrxsProccessedThread02.size());
			if(theTrxsProccessedThread02.size()>200){
				theTrxsProccessedThread02.clear();
			}
			if(theTrxsProccessedThread02.get(pTrxId)==null){
				//insertTrxInProccess(pTrxId);
				return false;
			}else{
				Date dt_ = (Date)theTrxsProccessedThread02.get(pTrxId);
				Date currDt_ = new Date();
				if ((currDt_.getTime()-dt_.getTime())>3600000 || (currDt_.getTime()-dt_.getTime())<0)
					theTrxsProccessedThread02.remove(pTrxId);
			}
			System.out.println("Trx ya procesada 2");
			return true;			
		//}else if(theThreadsArrayIndexNumber == 3){
		}else if(theNewThreadsArrayIndexNumber == 3){	
			if(theTrxsProccessedThread03==null){
				theTrxsProccessedThread03=new Hashtable();
				//insertTrxInProccess(pTrxId);
				return false;
			}
			System.out.println("theTrxsProccessedThread03.size()->"+theTrxsProccessedThread03.size());
			if(theTrxsProccessedThread03.size()>200){
				theTrxsProccessedThread03.clear();
			}
			if(theTrxsProccessedThread03.get(pTrxId)==null){
				//insertTrxInProccess(pTrxId);
				return false;
			}else{
				Date dt_ = (Date)theTrxsProccessedThread03.get(pTrxId);
				Date currDt_ = new Date();
				if ((currDt_.getTime()-dt_.getTime())>3600000 || (currDt_.getTime()-dt_.getTime())<0)
					theTrxsProccessedThread03.remove(pTrxId);
			}
			System.out.println("Trx ya procesada 3");
			return true;			
		}else if(theNewThreadsArrayIndexNumber == 4){	
			return false;
		}
		return false;
	}	
	
	protected synchronized void insertTrxInProccess(String pTrxId){
		System.out.println("Adicionando trx->"+pTrxId);
		if(theNewThreadsArrayIndexNumber == 0){
			if(theTrxsInProccessThread00==null)
				theTrxsInProccessThread00=new Hashtable();		
			theTrxsInProccessThread00.put(pTrxId,new Date());
		}else if(theNewThreadsArrayIndexNumber == 1){
			if(theTrxsInProccessThread01==null)
				theTrxsInProccessThread01=new Hashtable();		
			theTrxsInProccessThread01.put(pTrxId,new Date());			
		}else if(theNewThreadsArrayIndexNumber == 2){
			if(theTrxsInProccessThread02==null)
				theTrxsInProccessThread02=new Hashtable();		
			theTrxsInProccessThread02.put(pTrxId,new Date());			
		}else if(theNewThreadsArrayIndexNumber == 3){
			if(theTrxsInProccessThread03==null)
				theTrxsInProccessThread03=new Hashtable();		
			theTrxsInProccessThread03.put(pTrxId,new Date());			
		}
	}	
	
	protected synchronized void insertTrxProccessed(String pTrxId){
		System.out.println("Adicionando trx->"+pTrxId);
		if(theNewThreadsArrayIndexNumber == 0){
			if(theTrxsProccessedThread00==null)
				theTrxsProccessedThread00=new Hashtable();		
			theTrxsProccessedThread00.put(pTrxId,new Date());
		}else if(theNewThreadsArrayIndexNumber == 1){
			if(theTrxsProccessedThread01==null)
				theTrxsProccessedThread01=new Hashtable();		
			theTrxsProccessedThread01.put(pTrxId,new Date());			
		}else if(theNewThreadsArrayIndexNumber == 2){
			if(theTrxsProccessedThread02==null)
				theTrxsProccessedThread02=new Hashtable();		
			theTrxsProccessedThread02.put(pTrxId,new Date());			
		}else if(theNewThreadsArrayIndexNumber == 3){
			if(theTrxsProccessedThread03==null)
				theTrxsProccessedThread03=new Hashtable();		
			theTrxsProccessedThread03.put(pTrxId,new Date());			
		}
	}	
	
	public boolean sendOrderToWebService(ServiceOrderVo pOrder, String pTrxElecBillStatus) throws ItemDataException, DataInconsistencyInTotalsException, CustomerTrxNotFoundException{
		//PuenteFEResponse pagoResponse_ = new PuenteFEResponse();
		int statusCode_ = 0;
		boolean answer_ = false;
		CloseableHttpClient httpclient_ = null;
		HttpPost post_ = null; // new HttpPost(pUrl);
		
		System.out.println("Desde sendOrderToWebService. Hilo:"+theThreadsArrayIndexNumber+" Subhilo:"+theThreadArraySubindexNumber);
		try{
	        
			String format="xml";
			String url_ = "";//ComfandiPosTransaction.getBusinessLogicParam(BusinessLogicParameters.ID_WS_AUTHENTICATION_URL).getValue();
			System.out.println(url_);
			boolean thereIsATokenFlag_ = false;
			//String WSPlatform_ = ComfandiPosTransaction.getBusinessLogicParam(BusinessLogicParameters.WS_AUTHENTICATION_PLATFORM).getValue().trim();
			//theCurrentServiceToken = "";
			//StringBuffer xmlString_ = getJSONMessage(pOrder);
			

			if (theCurrentServiceToken == null){
				url_ = ComfandiPosTransaction.getBusinessLogicParam(BusinessLogicParameters.ID_WS_AUTHENTICATION_URL).getValue();
				System.out.println("url autenticaciON:"+url_);
				AuthenticationAnswer authAnsw_ = getAutheticationAnswer(url_/*, WSPlatform_*/);
				if (authAnsw_ != null && authAnsw_.isSuccessful()){
					System.out.println("Token obtenido desde web service->"+authAnsw_.getToken());
					theCurrentServiceToken = authAnsw_.getToken();
					thereIsATokenFlag_ = true;
					// CACS: Se debe enviar la transacci�n al servicio con el token obtenido
				    	
					//setRespuestaElement(paymentAnswer_.getPaymentId(), 0);
					//setRespuestaServidor(getRespuestaServidor(UserServerAnswer.ID_ORDER_ALREADY_PAYED));
				}else{
					System.out.println("Error obteniendo token");
				}
			}else{
				System.out.println("Ya hay un token obtenido anteriormente");
				thereIsATokenFlag_ = true;							
			}
			
			//thereIsATokenFlag_ = true;
			if (thereIsATokenFlag_){
				httpclient_ = doConexion();
			    String strTrxId_ = pOrder.getStore()+StringFormatter.align(pOrder.getTerminal(),3,'0',0,3)+StringFormatter.align(pOrder.getTransaction(),4,'0',0,4)+pOrder.getDateTime();
			    
			    //try {
			    	String environment_ = ComfandiPosTransaction.getBusinessLogicParam(BusinessLogicParam.ID_CARVAJAL_ENVIRONMENT).getValue();
				    JSONMessageVo JSONMsgVo_ = getJSONMessage(pOrder, strTrxId_, environment_); 

				    long totalTrxValue_ = JSONMsgVo_.getTrxTotalAmount().longValue();//pOrder.getTotalTrxValue();
				    long totalTrxValueInTenders_ = 0;
					for(int i=0;i<pOrder.getTendersList().size();i++){
						ServiceOrderTenderVo tendVo_ = (ServiceOrderTenderVo)pOrder.getTendersList().get(i);
						if(!tendVo_.isVoided()) {
							System.out.println("Monto tender->"+tendVo_.getTenderAmount());
							totalTrxValueInTenders_ += tendVo_.getTenderAmount();
						}
					}
					if(totalTrxValue_ == totalTrxValueInTenders_) {
						System.out.println("Suma tenders cuadra con monto total trx.");
					}else{	
						System.out.println("Suma tenders NO cuadra con monto total trx.");
						long diff_ = totalTrxValue_ - totalTrxValueInTenders_;
						if (diff_<0)
							diff_ = diff_ * (-1);
						if(diff_>20)
							throw new DataInconsistencyInTotalsException("INCONSISTENCIA TOTALES TENDERS - TRX TOTAL. DIRERENCIA:"+diff_);
						else
							System.out.println("Se deja pasar la diferencia");
					}				    
				    
					StringBuffer xmlString_ = new StringBuffer(JSONMsgVo_.getJSONMessageHeader()+JSONMsgVo_.getJSONMessageBody()+JSONMsgVo_.getJSONMessageFooter());
	
					String urlParam_ = BusinessLogicParam.ID_SEND_DOCUMENT_URL+"_"+theThreadsArrayIndexNumber;
					//                                                               theThreadsArrayIndexNumber
					url_ = ComfandiPosTransaction.getBusinessLogicParam(urlParam_).getValue();
					System.out.println("URL para el hilo "+theThreadsArrayIndexNumber+"->"+url_);				
					
					//System.out.println("xmlString_->"+xmlString_);
					System.out.println("Trama Items a enviar->"+xmlString_.toString());
		        	HttpEntity requestEntity2 =  new  StringEntity(xmlString_.toString(), "UTF-8");
		        	post_ = new HttpPost(url_);
		        	post_.setEntity(requestEntity2);
	
	//			    post = new HttpPost(url);
				    
	//			    post_.setHeader("Content-Type", "application/json");
	//			    post_.setHeader("Origin", "https://comfandi.com.co");
	//			    post_.setHeader("Accept-Language", "es-co");
	//			    post_.setHeader("Platform", WSPlatform_);
		        	post_.setHeader(
			                "Content-Type", "application/json; Charset=UTF-8\"");
		        	post_.setHeader(
			                "Authorization", "Bearer "+theCurrentServiceToken);
				    System.out.println("Token de autorizaciOn->"+theCurrentServiceToken);
	
	//		        HttpEntity entity = new ByteArrayEntity(xmlString_.toString().getBytes("UTF-8"));
	//		        post_.setEntity(entity);
		
				    HttpResponse response = httpclient_.execute(post_);
	
		
				    System.out.println("\nSending 'POST' request to URL : " + theCurrentServiceToken);
				    System.out.println("Post parameters : " + post_.getEntity());
				    System.out.println("Response Code : " + 
				                                response.getStatusLine().getStatusCode());
		
				    long responseCode_ = -1;
				    if (response != null && response.getStatusLine() != null){
				    	responseCode_ = response.getStatusLine().getStatusCode();
				    	if (response.getEntity() != null && response.getEntity().getContent() != null){	
						    BufferedReader rd = new BufferedReader(
				                    new InputStreamReader(response.getEntity().getContent()));
		
						    StringBuffer result_ = new StringBuffer();
						    String line_ = "";
						    while ((line_ = rd.readLine()) != null) {
						        result_.append(line_);
						    }
						    System.out.println(result_.toString());
						    if (responseCode_ == 200){ // Remisi�n creada satisfactoriamente
						    	System.out.println("Respuesta satisfactoria del WS");
	
								//setRespuestaServidor(getRespuestaServidor(UserServerAnswer.ID_ORDER_CREATION_SUCCESSFULLY));
								//setCodRespuesta(ID_APPL_SUCCESS_CODE);
								//setCodControl(ID_TRX_SUCCESS_CODE);
	
								SendDocumentAnswer sendDocAnswer_ = JSONServicesFacturaElectronica.getSendDocumentAnswer(result_.toString());
								String date_ = 	getServiceDate(pOrder.getDateTime());
								String time_ = 	pOrder.getDateTime().substring(6);	
								
								if(sendDocAnswer_.getGovernmentResponseCode() != null && sendDocAnswer_.getGovernmentResponseCode().equals("00")) {
									String continFiscalNum_ = "";
									if(pOrder != null && pOrder.getFiscalPrefix()!=null && pOrder.getFiscalBillNumber()!=null)
										continFiscalNum_ = pOrder.getFiscalPrefix().trim() + pOrder.getFiscalBillNumber().trim();
									if(insertWSTransactionAnswer( JSONMsgVo_, pOrder.getCustomerId(), pOrder.getStore() ,pOrder.getTerminal(),pOrder.getTransaction(),date_, time_, sendDocAnswer_.getTransactionId(),sendDocAnswer_.getCUFE(),false, strTrxId_, continFiscalNum_)){
										System.out.println("Registro de control insertado satisfactoriamente. Se hace la actualizaciOn del registro en Tronline");
										String statusSended_ = "";
										if(theThreadsArrayIndexNumber == 4) {
											statusSended_ = getSendedStatus(pTrxElecBillStatus); 
											if(statusSended_.equals(ERROR_STATUS)) {
												System.out.println("Error en estado a colocar");
											}else {
												//if(TrxonlineDatabaseHelper.updateRejectedElectronicBillingTrxRegister(pOrder.getStore(), pOrder.getTerminal(),pOrder.getTransaction(),pOrder.getDateTime(),statusSended_)){
													System.out.println("Regitro actualizado en tabla de rechazos");
													if(TrxonlineDatabaseHelper.updateNaturalCustomerElectronicBillingTrx(pOrder.getStore(), pOrder.getTerminal(),pOrder.getTransaction(),pOrder.getDateTime(),statusSended_)) {
														System.out.println("Regitro actualizado en tabla de transacciones");
													}else
														System.out.println("Error actualizando en tabla de transacciones");
												//}else
													//System.out.println("Error actualizando en tabla de transacciones");
											}
										}else {
											statusSended_ = getSendedStatus(pTrxElecBillStatus); 
											TrxonlineDatabaseHelper.updateNaturalCustomerElectronicBillingTrx(pOrder.getStore(), pOrder.getTerminal(),pOrder.getTransaction(),pOrder.getDateTime(),statusSended_);
										}
										answer_ = true;	
									}else{
										if(TrxonlineDatabaseHelper.updateWSElectronicBillingTrx(pOrder.getStore() ,pOrder.getTerminal(),pOrder.getTransaction(),date_, time_, sendDocAnswer_.getTransactionId(),sendDocAnswer_.getCUFE(), continFiscalNum_)) {
											String statusSended_ = "";
											statusSended_ = getSendedStatus(pTrxElecBillStatus); 
											TrxonlineDatabaseHelper.updateNaturalCustomerElectronicBillingTrx(pOrder.getStore(), pOrder.getTerminal(),pOrder.getTransaction(),pOrder.getDateTime(),statusSended_);											
										}else
											System.out.println("Error insertando registro de control. No se hace actualizaciOn");
									}	
								}else if(sendDocAnswer_.getGovernmentResponseCode() != null && sendDocAnswer_.getGovernmentResponseCode().equals("01") && 
									//sendDocAnswer_.getGovernmentResponseDescription().indexOf(CarvajalProcessUtils.ID_BILL_ALREADY_SENDED_MSG)>=0) {
									result_.toString().indexOf(CarvajalProcessUtils.ID_BILL_ALREADY_SENDED_MSG)>=0) {
									System.out.println("Mensaje Rta:"+result_.toString());
									String continFiscalNum_ = "";
									if(pOrder != null && pOrder.getFiscalPrefix()!=null && pOrder.getFiscalBillNumber()!=null)
										continFiscalNum_ = pOrder.getFiscalPrefix().trim() + pOrder.getFiscalBillNumber().trim();	
									String errorMessage_ = CarvajalProcessUtils.ID_BILL_ALREADY_SENDED_MSG;
									//if(sendDocAnswer_.getGovernmentResponseDescription() != null)
										//errorMessage_ = sendDocAnswer_.getGovernmentResponseDescription();
									
									if(insertWSTransactionErrorAnswer(pOrder, errorMessage_, CarvajalProcessUtils.ID_CARV_R90, strTrxId_, continFiscalNum_)){
										System.out.println("Registro de control insertado satisfactoriamente. Se hace la actualizaciOn del registro en Tronline");
										String statusSended_ = "";
										statusSended_ = getErrorStatus(pTrxElecBillStatus); 
										TrxonlineDatabaseHelper.updateNaturalCustomerElectronicBillingTrx(pOrder.getStore(), pOrder.getTerminal(),pOrder.getTransaction(),pOrder.getDateTime(),statusSended_);
										answer_ = true;	
									}else
										System.out.println("Error insertando registro de control. No se hace actulizaciOn");									
								}else if(sendDocAnswer_.getGovernmentResponseCode() != null && sendDocAnswer_.getGovernmentResponseCode().equals("01") && 
										result_.toString().indexOf(CarvajalProcessUtils.ID_TAXES_PROBLEM_MSG)>=0) {
										System.out.println("Mensaje Rta:"+result_.toString());
										String continFiscalNum_ = "";
										if(pOrder != null && pOrder.getFiscalPrefix()!=null && pOrder.getFiscalBillNumber()!=null)
											continFiscalNum_ = pOrder.getFiscalPrefix().trim() + pOrder.getFiscalBillNumber().trim();	
										String errorMessage_ = CarvajalProcessUtils.ID_TAXES_PROBLEM_MSG;
										//if(sendDocAnswer_.getGovernmentResponseDescription() != null)
											//errorMessage_ = sendDocAnswer_.getGovernmentResponseDescription();
										
										if(insertWSTransactionErrorAnswer(pOrder, errorMessage_, CarvajalProcessUtils.ID_CARV_RTX01, strTrxId_, continFiscalNum_)){
											System.out.println("Registro de control insertado satisfactoriamente. Se hace la actualizaciOn del registro en Tronline");
											String statusSended_ = "";
											statusSended_ = getErrorStatus(pTrxElecBillStatus); 
											TrxonlineDatabaseHelper.updateNaturalCustomerElectronicBillingTrx(pOrder.getStore(), pOrder.getTerminal(),pOrder.getTransaction(),pOrder.getDateTime(),statusSended_);
											answer_ = true;	
										}else
											System.out.println("Error insertando registro de control. No se hace actulizaciOn");									
								}else if(sendDocAnswer_.getGovernmentResponseCode() != null && sendDocAnswer_.getGovernmentResponseCode().equals("01") && 
										result_.toString().indexOf(CarvajalProcessUtils.ID_CHECK_DIGIT_MSG)>=0) {
										System.out.println("Mensaje Rta:"+result_.toString());
										String continFiscalNum_ = "";
										if(pOrder != null && pOrder.getFiscalPrefix()!=null && pOrder.getFiscalBillNumber()!=null)
											continFiscalNum_ = pOrder.getFiscalPrefix().trim() + pOrder.getFiscalBillNumber().trim();	
										String errorMessage_ = CarvajalProcessUtils.ID_CHECK_DIGIT_MSG;
										//if(sendDocAnswer_.getGovernmentResponseDescription() != null)
											//errorMessage_ = sendDocAnswer_.getGovernmentResponseDescription();
										if(insertWSTransactionErrorAnswer(pOrder, errorMessage_, CarvajalProcessUtils.ID_CARV_RAD01, strTrxId_, continFiscalNum_)){
											System.out.println("Registro de control insertado satisfactoriamente. Se hace la actualizaciOn del registro en Tronline");
											String statusSended_ = "";
											statusSended_ = getErrorStatus(pTrxElecBillStatus); 
											TrxonlineDatabaseHelper.updateNaturalCustomerElectronicBillingTrx(pOrder.getStore(), pOrder.getTerminal(),pOrder.getTransaction(),pOrder.getDateTime(),statusSended_);
											answer_ = true;	
										}else
											System.out.println("Error insertando registro de control. No se hace actulizaciOn");									
								}else{
									System.out.println("Hay que manejar el error");
								}
																				//			String pCustomerId,String pStore,    String pTerminal,    String pOperator,            String pTrx,    String pDateTime,                  String pWSTrxSerial, boolean pRetryFlag											
						    }else if (responseCode_ == 400){ // Puede ser que la remisi�n ya creada anteriormente
								System.out.println("Hay una respuesta en envio de documento");
								//String pymtRef_ = getPaymentReferenceId(result.toString());
								SendDocumentAnswer sendDocAnswer_ = JSONServicesFacturaElectronica.getSendDocumentAnswer(result_.toString());
								if (sendDocAnswer_ != null){
									 if (sendDocAnswer_.getCode() != null && sendDocAnswer_.getCode().equals(JSONServicesFacturaElectronica.ID_COD_BILL_ALREADY_EXISTS) &&
										 sendDocAnswer_.getMessage().equals(JSONServicesFacturaElectronica.ID_MSG_BILL_ALREADY_EXISTS)	 ){ // Factura duplicada
										System.out.println("Factura creada anteriormente. Se retorna una rta satisfactoria para que no sea enviada de nuevo");
										String date_ = 	getServiceDate(pOrder.getDateTime());
										String time_ = 	pOrder.getDateTime().substring(6);
										String continFiscalNum_ = "";
										if(pOrder != null && pOrder.getFiscalPrefix()!=null && pOrder.getFiscalBillNumber()!=null)
											continFiscalNum_ = pOrder.getFiscalPrefix().trim() + pOrder.getFiscalBillNumber().trim();										
										if(insertWSTransactionAnswer( JSONMsgVo_, pOrder.getCustomerId(), pOrder.getStore() ,pOrder.getTerminal(),pOrder.getTransaction(),date_, time_, sendDocAnswer_.getTransactionId(),sendDocAnswer_.getCUFE(),false,strTrxId_, continFiscalNum_)){									
											System.out.println("Registro de control insertado satisfactoriamente. Se hace la actulizaciOn del registro en Tronline");
											String statusSended_ = "";
											/*
											if(pTrxElecBillStatus.equals(REG_STATUS_TO_SEND_WITHOUT_CONEX_CARVAJAL))
												statusSended_ = REG_STATUS_SENDED;
											else
												statusSended_ = REG_STATUS_SENDED_2;
											*/
											if(theThreadsArrayIndexNumber == 4) {
												statusSended_ = getSendedStatus(pTrxElecBillStatus); 
												if(statusSended_.equals(ERROR_STATUS)) {
													System.out.println("Error en estado a colocar");
												}else {
													//if(TrxonlineDatabaseHelper.updateRejectedElectronicBillingTrxRegister(pOrder.getStore(), pOrder.getTerminal(),pOrder.getTransaction(),pOrder.getDateTime(),statusSended_)){
														System.out.println("Regitro actualizado en tabla de rechazos");
														if(TrxonlineDatabaseHelper.updateNaturalCustomerElectronicBillingTrx(pOrder.getStore(), pOrder.getTerminal(),pOrder.getTransaction(),pOrder.getDateTime(),statusSended_)) {
															System.out.println("Regitro actualizado en tabla de transacciones");
														}else
															System.out.println("Error actualizando en tabla de transacciones");
													//}else
														//System.out.println("Error actualizando en tabla de transacciones");
												}
											}else {
												statusSended_ = getSendedStatus(pTrxElecBillStatus); 
												TrxonlineDatabaseHelper.updateNaturalCustomerElectronicBillingTrx(pOrder.getStore(), pOrder.getTerminal(),pOrder.getTransaction(),pOrder.getDateTime(),statusSended_);
											}	
										}else {
											String statusSended_ = "";
											if(TrxonlineDatabaseHelper.updateWSElectronicBillingTrx(pOrder.getStore() ,pOrder.getTerminal(),pOrder.getTransaction(),date_, time_, sendDocAnswer_.getTransactionId(),sendDocAnswer_.getCUFE(), continFiscalNum_)) {
												if(theThreadsArrayIndexNumber == 4) {
													statusSended_ = getSendedStatus(pTrxElecBillStatus); 
													if(statusSended_.equals(ERROR_STATUS)) {
														System.out.println("Error en estado a colocar");
													}else {

														//if(TrxonlineDatabaseHelper.updateRejectedElectronicBillingTrxRegister(pOrder.getStore(), pOrder.getTerminal(),pOrder.getTransaction(),pOrder.getDateTime(),statusSended_)){
															System.out.println("Regitro actualizado en tabla de rechazos");
															if(TrxonlineDatabaseHelper.updateNaturalCustomerElectronicBillingTrx(pOrder.getStore(), pOrder.getTerminal(),pOrder.getTransaction(),pOrder.getDateTime(),statusSended_)) {
																System.out.println("Regitro actualizado en tabla de transacciones");
															}else
																System.out.println("Error actualizando en tabla de transacciones");
														//}else
															//System.out.println("Error actualizando en tabla de transacciones");														
														
													}
												}else {
													statusSended_ = getSendedStatus(pTrxElecBillStatus); 
													TrxonlineDatabaseHelper.updateNaturalCustomerElectronicBillingTrx(pOrder.getStore(), pOrder.getTerminal(),pOrder.getTransaction(),pOrder.getDateTime(),statusSended_);
												}
											}else	
												System.out.println("Error insertando registro de control. No se hace actulizaciOn");
										}	
										answer_ = true;
									 }else if(sendDocAnswer_.getCode() != null && sendDocAnswer_.getCode().equals(JSONServicesFacturaElectronica.ID_COD_STRUCTURE_DOC_ERROR) && 
										sendDocAnswer_.getMessage() != null && sendDocAnswer_.getMessage().startsWith(JSONServicesFacturaElectronica.MSG_STRUCTURE_DOCUMENT_ERROR)){
										System.out.println("Mensaje Rta:"+result_.toString());
										String continFiscalNum_ = "";
										if(pOrder != null && pOrder.getFiscalPrefix()!=null && pOrder.getFiscalBillNumber()!=null)
											continFiscalNum_ = pOrder.getFiscalPrefix().trim() + pOrder.getFiscalBillNumber().trim();	
										String errorMessage_ = CarvajalProcessUtils.ID_STRUCTURE_DOCUMENT_ERROR_MSG;
												//if(sendDocAnswer_.getGovernmentResponseDescription() != null)
													//errorMessage_ = sendDocAnswer_.getGovernmentResponseDescription();
												
										if(insertWSTransactionErrorAnswer(pOrder, errorMessage_, CarvajalProcessUtils.ID_CARV_DS01, strTrxId_, continFiscalNum_)){
													System.out.println("Registro de control insertado satisfactoriamente. Se hace la actualizaciOn del registro en Tronline");
													String statusSended_ = "";
													statusSended_ = getErrorStatus(pTrxElecBillStatus); 
													TrxonlineDatabaseHelper.updateNaturalCustomerElectronicBillingTrx(pOrder.getStore(), pOrder.getTerminal(),pOrder.getTransaction(),pOrder.getDateTime(),statusSended_);
													answer_ = true;	
										}else
											System.out.println("Error insertando registro de control. No se hace actulizaciOn");									
									}else if(sendDocAnswer_.getMessage() != null && sendDocAnswer_.getMessage().startsWith(JSONServicesFacturaElectronica.MSG_NO_ITEMS_IN_REMISSION)){
										 System.out.println("Es una devoluciOn en caja, solo trae negativos. Enviando a pendientes ...");
										 if(TrxonlineDatabaseHelper.changeElectronicBillingTrxStatus(pOrder.getStore(), pOrder.getTerminal(),pOrder.getTransaction(),pOrder.getDateTime(),getToReviewStatus(pTrxElecBillStatus   ))){
											 //FacturaElectronicaDBHelper.insertWSTransactionErrorAnswer(pOrder.getStore(), pOrder.getTerminal(),pOrder.getTransaction(),pOrder.getDateTime(), result_.toString(),strTrxId_);
											String continFiscalNum_ = "";
											if(pOrder != null && pOrder.getFiscalPrefix()!=null && pOrder.getFiscalBillNumber()!=null)
												continFiscalNum_ = pOrder.getFiscalPrefix().trim() + pOrder.getFiscalBillNumber().trim();		
											 insertWSTransactionErrorAnswer(pOrder, result_.toString(), CarvajalProcessUtils.ID_CARV_001, strTrxId_, continFiscalNum_);
										}else
											System.out.println("Error insertando registro de control. No se hace actulizaciOn");											 
									 }else if(result_.toString().indexOf(CarvajalProcessUtils.ID_RESOLUTION_ERROR_MSG)>=0) {
										 System.out.println("Error en la resoluciOn fiscal de la terminal. Enviando a pendientes ...");
										 if(TrxonlineDatabaseHelper.changeElectronicBillingTrxStatus(pOrder.getStore(), pOrder.getTerminal(),pOrder.getTransaction(),pOrder.getDateTime(),getToReviewStatus(pTrxElecBillStatus   ))){
											 //FacturaElectronicaDBHelper.insertWSTransactionErrorAnswer(pOrder.getStore(), pOrder.getTerminal(),pOrder.getTransaction(),pOrder.getDateTime(), result_.toString(),strTrxId_);
											String continFiscalNum_ = "";
											if(pOrder != null && pOrder.getFiscalPrefix()!=null && pOrder.getFiscalBillNumber()!=null)
												continFiscalNum_ = pOrder.getFiscalPrefix().trim() + pOrder.getFiscalBillNumber().trim();		
											 insertWSTransactionErrorAnswer(pOrder, CarvajalProcessUtils.ID_RESOLUTION_ERROR_MSG, CarvajalProcessUtils.ID_CARV_FR01, strTrxId_, continFiscalNum_);
										}else
											System.out.println("Error insertando registro de control. No se hace actulizaciOn");										 
									 }else if(result_.toString().indexOf(CarvajalProcessUtils.ID_RESOLUTION_ERROR_MSG_002)>=0) {
										 System.out.println("Error en la resoluciOn fiscal de la terminal 002. Enviando a pendientes ...");
										 if(TrxonlineDatabaseHelper.changeElectronicBillingTrxStatus(pOrder.getStore(), pOrder.getTerminal(),pOrder.getTransaction(),pOrder.getDateTime(),getToReviewStatus(pTrxElecBillStatus   ))){
											 //FacturaElectronicaDBHelper.insertWSTransactionErrorAnswer(pOrder.getStore(), pOrder.getTerminal(),pOrder.getTransaction(),pOrder.getDateTime(), result_.toString(),strTrxId_);
											String continFiscalNum_ = "";
											if(pOrder != null && pOrder.getFiscalPrefix()!=null && pOrder.getFiscalBillNumber()!=null)
												continFiscalNum_ = pOrder.getFiscalPrefix().trim() + pOrder.getFiscalBillNumber().trim();		
											 insertWSTransactionErrorAnswer(pOrder, CarvajalProcessUtils.ID_RESOLUTION_ERROR_MSG_002, CarvajalProcessUtils.ID_CARV_FR02, strTrxId_, continFiscalNum_);
										}else
											System.out.println("Error insertando registro de control. No se hace actulizaciOn");										 
									 }else{
										if(result_ != null && result_.toString() != null && !result_.toString().trim().equals("")) {
											if(result_.toString().indexOf(JSONServicesFacturaElectronica.ID_COD_STRUCTURE_DOC_ERROR)>=0
												&& result_.toString().indexOf(CarvajalProcessUtils.ID_RESOLUTION_ERROR_MSG_003)>=0) {
													 System.out.println("Error en la resoluciOn fiscal de la terminal 003. Enviando a pendientes ...");
													 if(TrxonlineDatabaseHelper.changeElectronicBillingTrxStatus(pOrder.getStore(), pOrder.getTerminal(),pOrder.getTransaction(),pOrder.getDateTime(),getToReviewStatus(pTrxElecBillStatus   ))){
														 //FacturaElectronicaDBHelper.insertWSTransactionErrorAnswer(pOrder.getStore(), pOrder.getTerminal(),pOrder.getTransaction(),pOrder.getDateTime(), result_.toString(),strTrxId_);
														String continFiscalNum_ = "";
														if(pOrder != null && pOrder.getFiscalPrefix()!=null && pOrder.getFiscalBillNumber()!=null)
															continFiscalNum_ = pOrder.getFiscalPrefix().trim() + pOrder.getFiscalBillNumber().trim();		
														 insertWSTransactionErrorAnswer(pOrder, CarvajalProcessUtils.ID_RESOLUTION_ERROR_MSG_0031, CarvajalProcessUtils.ID_CARV_FR03, strTrxId_, continFiscalNum_);
													}else
														System.out.println("Error insertando registro de control. No se hace actulizaciOn");										 
											}else
												System.out.println("Respuesta desconocida");
										}else										 
											System.out.println("Respuesta desconocida.");
										/* 
										if(sendDocAnswer_.getStatus().equals(JSONServicesFacturaElectronica.MSG_REMISSION_ERRORS_OCCURRED)){
											System.out.println("Hay errores de validaci�n en recepciOn del documento. Enviando a pendientes ...");
											if(TrxonlineDatabaseHelper.sendElectronicBillingTrxToPendingStatus(pOrder.getStore(), pOrder.getTerminal(),pOrder.getTransaction(),pOrder.getDateTime(),getPendigStatus(   trx_.getElectBillStatus()   )))
												FacturaElectronicaDBHelper.insertWSTransactionErrorAnswer(pOrder.getStore(), pOrder.getTerminal(),pOrder.getTransaction(),pOrder.getDateTime(), xmlString_.toString(), result_.toString(),strTrxId_);
										}else if (sendDocAnswer_.getCodMessage().equals(JSONServicesFacturaElectronica.ID_MSG_UNIT_NOT_VALID_IN_REMISSION)){ // Remisi�n enviada anteriormente
											System.out.println("Precio unitario enviado para un producto NO v�lido");
											if(TrxonlineDatabaseHelper.sendElectronicBillingTrxToPendingStatus(pOrder.getStore(), pOrder.getTerminal(),pOrder.getTransaction(),pOrder.getDateTime(),getPendigStatus(   trx_.getElectBillStatus()   )))
												FacturaElectronicaDBHelper.insertWSTransactionErrorAnswer(pOrder.getStore(), pOrder.getTerminal(),pOrder.getTransaction(),pOrder.getDateTime(), xmlString_.toString(), result_.toString(),strTrxId_);										
										}else{									 
											System.out.println("Remisi�n NO fue creada anteriormente.");
										}	
										*/
										//setRespuestaServidor(getRespuestaServidor(UserServerAnswer.ID_CREATING_ORDER_ERROR));
										//setCodRespuesta(ID_APPL_ERROR_CODE);
										//setCodControl(ID_TRX_SUCCESS_CODE);
										
									 }
								}else{
									System.out.println("sendDocAnswer_ is null");
									
									//setRespuestaServidor(getRespuestaServidor(UserServerAnswer.ID_INVALID_WEB_SERVICE_ANSWER));
									//setCodRespuesta(ID_APPL_ERROR_CODE);
									//setCodControl(ID_TRX_SUCCESS_CODE);
												
								}
	
								
					    	}else if (responseCode_ == 401){ // Unauthorized
					    		System.out.println("Respuesta 401 Unauthorized");
	
								//setRespuestaServidor(getRespuestaServidor(UserServerAnswer.ID_WEB_SERVICE_AUTHORIZATION_ERROR));
								//setCodRespuesta(ID_APPL_ERROR_CODE);
								//setCodControl(ID_TRX_SUCCESS_CODE);
		
								theCurrentServiceToken = null;
					    	}else{
					    		System.out.println("Respuesta indefinida desde WS");
								//setRespuestaServidor(getRespuestaServidor(UserServerAnswer.ID_WEB_SERVICE_UNKNOWN_ANSWER_CODE));
								//setCodRespuesta(ID_APPL_ERROR_CODE);
								//setCodControl(ID_TRX_SUCCESS_CODE);
					    	}
				    	}else{
				    		System.out.println("No se recupero string de rta desde web service");
							//setRespuestaServidor(getRespuestaServidor(UserServerAnswer.ID_INVALID_WEB_SERVICE_ANSWER));
							//setCodRespuesta(ID_APPL_ERROR_CODE);
							//setCodControl(ID_TRX_SUCCESS_CODE);
				    	}
				    		
				    }else{
				    	System.out.println("No hay cOdigo de respuesta");
						//setRespuestaServidor(getRespuestaServidor(UserServerAnswer.ID_NO_ANSWER_RETURNED));
						//setCodRespuesta(ID_APPL_ERROR_CODE);
						//setCodControl(ID_TRX_SUCCESS_CODE);
				    }
				/*}catch(ItemDataException e) {
					System.out.println("Error en datos de Items en la trx");
					// Meter código paga manejar la excepción y marcar la trx para que no se quede dando vueltas
					throw e;
				}*/
			}else
				System.out.println("No se obtuvo un token para la transacciOn");

			    
		}  /*catch (UnsupportedEncodingException e) {
			// TODO Bloque catch generado autom�ticamente
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Bloque catch generado autom�ticamente
			e.printStackTrace();
		} catch (ConnectionFailedException e) {
			// TODO Bloque catch generado autom�ticamente
			e.printStackTrace();
		}*/catch(ItemDataException e) {
			System.out.println("Error en datos de Items en la trx");
			e.printStackTrace();
			// Meter código paga manejar la excepción y marcar la trx para que no se quede dando vueltas
			throw e;
		}catch(DataInconsistencyInTotalsException e) {
			System.out.println("Error en totales de trx");
			e.printStackTrace();
			// Meter código paga manejar la excepción y marcar la trx para que no se quede dando vueltas
			throw e;
		}catch(CustomerTrxNotFoundException e) {
			System.out.println("Error en cliente de la trx");
			e.printStackTrace();
			throw e;
		}catch (DBAccessException e) {
			// TODO Bloque catch generado autom�ticamente
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Bloque catch generado autom�ticamente
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Bloque catch generado autom�ticamente
			e.printStackTrace();
		} finally{
			
			try{
				httpclient_.close();
			}catch(Exception e){
				System.out.println("Exception cerrando client");
			}
			try{
				post_.releaseConnection();
			}catch(Exception e){
				System.out.println("Exception liberando post");
			}
		}
		return answer_;
	}
	
	private JSONMessageVo getJSONMessage(ServiceOrderVo pOrder, String pStrTrxId, String pEnvironment)throws ConnectionFailedException, DBAccessException, /*CustomerNotFoundException,*/ Exception, Throwable{
		JSONMessageVo JSONMsgVo_ = new JSONMessageVo();
		StringBuffer answer_ = new StringBuffer("");
		setItemDescriptionsToOrder(pOrder);
		setCustomerInfoToOrder(pOrder);
		//setTendersAdditionalInformation();//TODO INFORmACION NOT1,2,3
		Date serverDate_ = new Date();
		if(MODULE_IN_DEBUG){
			System.out.println("XML transacciOn->"+getDebugXMLMessage(pOrder));
			
		}
		
		
					// CACS: BODY DEL MENSAJE JSON 
		JSONMessagesCarvajalBuilder JSONMessageBuil_ = new JSONMessagesCarvajalBuilder();
		String bagsItemCode_ = ComfandiPosTransaction.getBusinessLogicParam(BusinessLogicParam.ID_BAGS_ITEM_CODE).getValue().trim();
		if(bagsItemCode_.equals("")) {
			throw new ParameterNotFoundException("Bags item code not found");
		}
		String bagsItemValue_ = ComfandiPosTransaction.getBusinessLogicParam(BusinessLogicParam.ID_BAGS_ITEM_VALUE).getValue().trim();
		BigDecimal BDBagsItemValue_ = null;
		try {
			BDBagsItemValue_ = new BigDecimal(bagsItemValue_);
		}catch(Exception e) {
			throw new ParameterNotFoundException("Bags item value not found");
		}
		 
		JSONMsgVo_ = JSONMessageBuil_.getInvoiceJSONMessage(pOrder, bagsItemCode_, BDBagsItemValue_);
		String JSONMessage_ = JSONMsgVo_.getJSONMessageBody();  
		if(MODULE_IN_DEBUG)
			System.out.println("JSON transacciOn->"+JSONMessage_);
		System.out.println(pOrder.getDateTime());//2403271724
					// CACS: FIN BODY DEL MENSAJE JSON
		
		//SimpleDateFormat sdf_ = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		//Date date_=sdf_.parse(getServiceDateTime(pOrder.getDateTime()));//2020-11-16T13:09:17.878Z
		
		//sdf_.applyPattern("yyyy-MM-dd");
		//String fecha_=  sdf_.format(new Date());//sdf_.format(date_);
		//sdf_.applyPattern("HH:mm:ss");
		//String timeZone_ = "-05:00";
		//String hora_= sdf_.format(date_)+timeZone_;
		
				// CACS: HEADER DEL MENSAJE JSON
		AlmacenVo almacenVo_ = mapAlmacen.get(Long.valueOf(pOrder.getStore().trim()));
		//AlmacenVo almacenVo_ = (AlmacenVo) mapAlmacen.get(pTrxToFindData.getAlmacen());
		if (almacenVo_==null) {
			almacenVo_ = TrxonlineDatabaseHelper.getAlmacenInfo(Long.valueOf(pOrder.getStore().trim()));
			mapAlmacen.put(Long.valueOf(pOrder.getStore().trim()), almacenVo_);
			if (almacenVo_==null)
				throw new StoreNotFoundException("NO SE ENCONTRO INFORMACION ALMACEN PARA CUFE");
		}		
		
		
		FEJSONMessageVo jsonVo_ = new FEJSONMessageVo(pOrder, almacenVo_, getServiceDateTime(pOrder.getDateTime())) ;
		String header_ = jsonVo_.getHeader(ID_ELECTRONIC_BILLING_TYPE, pEnvironment);
//		String x = getHeader(pOrder, fecha_, hora_, almacenVo_);
		String customer_ =  jsonVo_.getCustomer();
		JSONMsgVo_.setJSONMessageHeader(header_ +customer_);	
		JSONMessage_ = header_ +customer_+ JSONMessage_;
				// CACS: FIN HEADER DEL MENSAJE JSON
		
		
				// CACS: FOOTER DEL MENSAJE JSON
		String footer_ = jsonVo_.getFooter(pStrTrxId, JSONMsgVo_.getTrxTotalAmount());
		JSONMsgVo_.setJSONMessageFooter(footer_);
				// CACS: FIN FOOTER DEL MENSAJE JSON
		
		
		JSONMessage_ = JSONMessage_+footer_;
		answer_ = new StringBuffer(JSONMessage_);
		if(MODULE_IN_DEBUG)
			System.out.println("JSON transacciOn completo->"+answer_);
		return JSONMsgVo_;	
	}
	

	private AuthenticationAnswer getAutheticationAnswer(String pURL/*, String pPlatform*/) throws UnsupportedEncodingException, IOException{
		AuthenticationAnswer answer_ = null;
		/*
        SSLContext sslcontext =null;
		try {
			sslcontext = SSLContexts.custom()
					.loadTrustMaterial(new File(getBusinessLogicParam(BusinessLogicParam.ROUTE_FILE_CERTIFICATE).getValue()), "changeit".toCharArray(),
			                new TrustSelfSignedStrategy())
			        .build();
		} catch (KeyManagementException e1) {
			// TODO Bloque catch generado autom�ticamente
			e1.printStackTrace();
		} catch (NoSuchAlgorithmException e1) {
			// TODO Bloque catch generado autom�ticamente
			e1.printStackTrace();
		} catch (KeyStoreException e1) {
			// TODO Bloque catch generado autom�ticamente
			e1.printStackTrace();
		} catch (CertificateException e1) {
			// TODO Bloque catch generado autom�ticamente
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Bloque catch generado autom�ticamente
			e1.printStackTrace();
		}
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] {"TLSv1.2" },
//	                new String[] { "SSL" },
                null,
                SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        CloseableHttpClient client = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();*/
		
        //CloseableHttpClient client = HttpClientBuilder.create().useSystemProperties().build();
        CloseableHttpClient client = doConexion();    
		//String format="xml";
		//String accessToken_ = getBusinessLogicParam(BusinessLogicParam.ID_ACCESS_TOKEN).getValue();
		//String url = "https://comfandi.expressmed.co/administrativo/sos/utilidades/facturacion_electronica/api/v1/stage/identity/";
		System.out.println(pURL);
	    HttpPost post = new HttpPost(pURL);
	    

	    //post.setHeader("Accept-Language", "es-co");
	    //post.setHeader("Platform", "51240cc9-2822-470a-9d08-4be6409d5586");
	    //post.setHeader("Platform", "2e69d552-185c-43a9-8bf4-ce8a6b893277");
	    //post.setHeader("Platform", pPlatform);
	    //post.setHeader("Origin", "https://comfandi.com.co");
	    
	    //String externalRef_ = getTienda()+getNumTerminal()+getNumFactura()+getFechaHoraInicioTrx();
	    String WSUser_ = ComfandiPosTransaction.getBusinessLogicParam(BusinessLogicParam.WS_AUTHENTICATION_USER).getValue().trim();
	    String WSPassword_ = ComfandiPosTransaction.getBusinessLogicParam(BusinessLogicParam.WS_AUTHENTICATION_PASSWORD).getValue().trim();
	    //String WSPlatform_ = ComfandiPosTransaction.getBusinessLogicParam(BusinessLogicParam.WS_AUTHENTICATION_PLATFORM).getValue().trim();
//	    BASE64Encoder b64enc_ = new BASE64Encoder();
	    StringBuffer xmlString = new StringBuffer("");
//	    String encriptedPassword_ = b64enc_.encode(WSPassword_.getBytes());
		xmlString.append("{");
		//xmlString.append("\"Email\" : \"int_pos_1@expressmed.co\",");
		xmlString.append("\"user\" : \""+WSUser_+"\",");
//		xmlString.append("\"Password\" : \""+encriptedPassword_+"\""); // test-pos-20*_
		xmlString.append("\"password\" : \""+WSPassword_+"\""); // test-pos-20*_
		xmlString.append("}");	
		
		System.out.println("Trama Items a enviar->"+xmlString.toString());
        
		HttpEntity entity = new StringEntity(xmlString.toString());
        post.setEntity(entity);
	    
        post.setHeader("Content-Type", "application/json");

    	//HttpEntity requestEntity2 =  new  StringEntity(body);
    	//post_.setEntity(requestEntity2);
	    
	    HttpResponse response = client.execute(post);

	    System.out.println("\nSending 'POST' request to URL : " + pURL);
	    System.out.println("Post parameters : " + post.getEntity());
	    System.out.println("Response Code : " + 
	                                response.getStatusLine().getStatusCode());
	    long responseCode_ = -1;
	    if (response != null && response.getStatusLine() != null){
	    	responseCode_ = response.getStatusLine().getStatusCode();
	    }else
	    	System.out.println("No hay cOdigo de respuesta");
	    BufferedReader rd = new BufferedReader(
	                    new InputStreamReader(response.getEntity().getContent()));

	    StringBuffer result = new StringBuffer();
	    String line = "";
	    while ((line = rd.readLine()) != null) {
	        result.append(line);
	    }
	    System.out.println(result.toString());
		if (responseCode_ == 200){ // La respuesta es satisfactoria en la creaci�n de la orden
			System.out.println("La respuesta es satisfactoria en obtenci�n del token");
			//String pymtRef_ = getPaymentReferenceId(result.toString());
			answer_ = JSONServicesFacturaElectronica.getAuthenticationAnswer(result.toString());
		    	

		}else{
			System.out.println("Error creando orden en WS");
			/*
			setRespuestaServidor(getRespuestaServidor(UserServerAnswer.ID_CREATING_ORDER_ERROR));
			setCodRespuesta(ID_APPL_SUCCESS_CODE);
			setCodControl(ID_TRX_SUCCESS_CODE);
			*/	
		}
		return answer_;	
	}	
	
	private CloseableHttpClient doConexion() /*throws PuentePagoException*/ {
		//conexion ssl
		CloseableHttpClient httpclient = HttpClients.createDefault();
		if (isSSL) {
			// Trust own CA and all self-signed certs
	        SSLContext sslcontext =null;
			try {
				
				sslcontext = SSLContexts.custom().build();
			} catch (KeyManagementException e1) {
				// TODO Bloque catch generado autom?ticamente
				e1.printStackTrace();
				//throw new PuentePagoException(e1.getMessage());
			} catch (NoSuchAlgorithmException e1) {
				// TODO Bloque catch generado autom?ticamente
				e1.printStackTrace();
				//throw new PuentePagoException(e1.getMessage());
			}

	        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext);
	        httpclient = HttpClients.custom()
	                .setSSLSocketFactory(sslsf)
	                .build();
		}
		return httpclient;
	}	
	
	public ServiceOrderVo getServiceOrderVo(TrxonlineTransactionInDBVO pTrxToFindData) throws CustomerTrxNotFoundException,FiscalInformationNotFoundException, StoreNotFoundException{
		ServiceOrderVo answer_ = null;
		try{

			// CACS: Se itera sobre las transacciones pero hay que ir a traer los prouductos.
			System.out.println(pTrxToFindData.getAlmacen());
			System.out.println(pTrxToFindData.getTerminal());
			System.out.println(pTrxToFindData.getNumTrx());
			System.out.println(pTrxToFindData.getFechaHora());
			// CACS: Se busca el id del cliente
			List lstHeaderAdditionalInfo_ = TrxonlineDatabaseHelper.getHeaderAdditionalInfo(pTrxToFindData);
			TrxonlineTransactionInDBVO trxVo_ = (TrxonlineTransactionInDBVO)lstHeaderAdditionalInfo_.get(0);
			long totalTrxAmount_ = trxVo_.getGrosspos()-trxVo_.getGrossneg();
			
			// APSM: Trae informacion del almacen
			AlmacenVo almacenVo_ = (AlmacenVo) mapAlmacen.get(pTrxToFindData.getAlmacen());
			if (almacenVo_==null) {
				almacenVo_ = TrxonlineDatabaseHelper.getAlmacenInfo(pTrxToFindData.getAlmacen());
				mapAlmacen.put(pTrxToFindData.getAlmacen(), almacenVo_);
				if (almacenVo_==null)
					throw new StoreNotFoundException("NO SE ENCONTRO INFORMACION ALMACEN PARA CUFE");
			}
			
			// CACS:Se valida s� es minifactura
			boolean isSmallBillTrx_ = false;
			List lstSmallBillAdditionalInfo_ = null;
			String smallBillCodeOrNumber_ = "";
			String trxType_ = trxVo_.getTipo();
			if(trxType_ != null && !trxType_.equals("") && !trxType_.equals("0")){ // Hay alg�n flag prendido
				int iType_ = DatabaseFieldsUtils.getNumericValue(trxType_);
				if(iType_>0 && iType_ % 2 == 1){ // Est� prendido el flag de minifactura para Factura Electr�nica
					isSmallBillTrx_ = true;
					lstSmallBillAdditionalInfo_ = TrxonlineDatabaseHelper.getSmallBillInfoUserData(pTrxToFindData);
					if(lstSmallBillAdditionalInfo_!= null && lstSmallBillAdditionalInfo_.size()>0){
						TrxonlineTransactionInDBVO smallBillInfo_ = (TrxonlineTransactionInDBVO)lstSmallBillAdditionalInfo_.get(lstSmallBillAdditionalInfo_.size()-1);
						smallBillCodeOrNumber_ = smallBillInfo_.getData2();
						smallBillCodeOrNumber_ = StringUtils.removeZeros(smallBillCodeOrNumber_);
						System.out.println("COdigo o nUmero de mini factura->"+smallBillCodeOrNumber_);
					}
				}else
					System.out.println("Hay un flag prendido pero no es para minifactura factura electrOnica->"+iType_);
			}
			if(!isSmallBillTrx_ || (isSmallBillTrx_ && lstSmallBillAdditionalInfo_!= null && lstSmallBillAdditionalInfo_.size()>0)){
				//CACS: Es una minifactura y tiene el user data con la info adicional
				System.out.println("No es una minifactura o es una minifactura y tiene el user data con la info adicional");
				
				TrxonlineTransactionInDBVO fiscalInfo_ = null;
				List lstTrxUserDatasInfo_ = TrxonlineDatabaseHelper.getTrxUserDatasInformation(pTrxToFindData);
				if(lstTrxUserDatasInfo_.size()>0){
					for(int i=0;i<lstTrxUserDatasInfo_.size();i++) {
						TrxonlineTransactionInDBVO currUserDataInfo_ = (TrxonlineTransactionInDBVO)lstTrxUserDatasInfo_.get(i);
						if(currUserDataInfo_.getData1().equals(BusinessLogicParameters.USER_DATA_TRX_FISCAL_INFO)) {
							fiscalInfo_ = currUserDataInfo_;
							i = lstTrxUserDatasInfo_.size();
						}	
					}	
				}	
				//List lstTrxFiscalInfo_ = TrxonlineDatabaseHelper.getTrxFiscalInformation(pTrxToFindData);
				//System.out.println("lstTrxFiscalInfo_.size()"+lstTrxFiscalInfo_.size());
				if(fiscalInfo_ != null) {
				//if(lstTrxFiscalInfo_.size()>0){				
					//TrxonlineTransactionInDBVO fiscalInfo_ = (TrxonlineTransactionInDBVO)lstTrxFiscalInfo_.get(lstTrxFiscalInfo_.size()-1);
					String fiscalFactType_ = fiscalInfo_.getData2(); // 02: Contingencia sin conex carvajal, 03: Contingencia con conex
					String fiscalPrefix_ = fiscalInfo_.getData3().trim(); // Prefijo
					String fiscalBillNumber_ = fiscalInfo_.getData4(); // Número factura fiscal
					String resolFirstNumber_ = fiscalInfo_.getData5(); // Número inicial resolución
					String resolLastNumber_ = fiscalInfo_.getData6(); // Número final resolución
					String resolNumber_ = fiscalInfo_.getData7(); // Número de resolución
					String resolIniDate_ = fiscalInfo_.getData8(); // Fecha inicial resolución
					String resolEndDate_ = fiscalInfo_.getData9(); // Fecha final resolución
					answer_ = new ServiceOrderVo();
					answer_.setFiscalFactType(fiscalFactType_);
					answer_.setFiscalPrefix(fiscalPrefix_);
					answer_.setFiscalBillNumber(StringUtils.removeZeros(fiscalBillNumber_));
					answer_.setFiscalResolFirstNumber(StringUtils.removeZeros(resolFirstNumber_));
					answer_.setFiscalResolLastNumber(StringUtils.removeZeros(resolLastNumber_));
					answer_.setFiscalResolNumber(StringUtils.removeZeros(resolNumber_));
					answer_.setFiscalResolIniDate(getServiceDate(resolIniDate_));
					answer_.setFiscalResolEndDate(getServiceDate(resolEndDate_));
	
					TrxonlineTransactionInDBVO customerInfo_ = null;
					if(lstTrxUserDatasInfo_.size()>0){
						for(int i=0;i<lstTrxUserDatasInfo_.size();i++) {
							TrxonlineTransactionInDBVO currUserDataInfo_ = (TrxonlineTransactionInDBVO)lstTrxUserDatasInfo_.get(i);
							if(currUserDataInfo_.getData1().equals(BusinessLogicParameters.USER_DATA_CUSTOMER_INFO)) {
								customerInfo_ = currUserDataInfo_;
								i = lstTrxUserDatasInfo_.size();
							}	
						}	
					}					
					
					//List lstCustomerInfo_ = TrxonlineDatabaseHelper.getCustomerInfoUserData2(pTrxToFindData);
					//System.out.println("lstCustomerInfo_.size()"+lstCustomerInfo_.size());
					//if(lstCustomerInfo_.size()>0){
					if(customerInfo_ != null) {
						//TrxonlineTransactionInDBVO customerInfo_ = (TrxonlineTransactionInDBVO)lstCustomerInfo_.get(lstCustomerInfo_.size()-1);
						String cusIdentificationId_ = customerInfo_.getData2();
						cusIdentificationId_ = StringUtils.removeZeros(cusIdentificationId_);				
						System.out.println("IdentificaciOn cliente->"+cusIdentificationId_);
		
						String cusIdentificationTypeId_ = customerInfo_.getData10();
						System.out.println("Id tipo de IdentificaciOn cliente->"+cusIdentificationTypeId_);				
						// CACS: S� existe el ID de cliente se buscan los productos
		
						List lstItemEntriesInfo_ = TrxonlineDatabaseHelper.getTransactionItemEntries(pTrxToFindData);
					
						if(lstItemEntriesInfo_.size()>0){
							System.out.println("ItemEntries en la trx->"+lstItemEntriesInfo_.size());
							//List lstItemsAdditionalInfo_ = TrxonlineDatabaseHelper.getTransactionItemsAdditionalInfo2(pTrxToFindData);
							//if(lstItemsAdditionalInfo_.size()>0){
								List lstTendersInfo_ = TrxonlineDatabaseHelper.getTransactionTenders(pTrxToFindData);
								if(lstTendersInfo_.size()>0){
									System.out.println("Tenders en la trx->"+lstTendersInfo_.size());
									//List lstDiscountsInfo_ = TrxonlineDatabaseHelper.getTransactionDiscounts(pTrxToFindData);
									answer_.setStore(pTrxToFindData.getAlmacen()+"");
									answer_.setTerminal(pTrxToFindData.getTerminal()+"");
									answer_.setTransaction(pTrxToFindData.getNumTrx()+"");
									String dateTime_ = getTLogDateTime(pTrxToFindData.getFechaHora());
									answer_.setDateTime(dateTime_);
									answer_.setTotalTrxValue(totalTrxAmount_);
									//answer_.setOperator(pTrxToFindData.getOperator()+"");
									answer_.setCustomerId(cusIdentificationId_);
									answer_.setCustomerUDIdType(cusIdentificationTypeId_); // Id del tipo de identificaci�n del cliente usado en el POS
									answer_.setSmallBillFlag(isSmallBillTrx_);
									if(isSmallBillTrx_)
										answer_.setSmallBillCodeOrNumber(smallBillCodeOrNumber_);
									/*
									if(lstDiscountsInfo_.size()>0){
										System.out.println("Discounts en la trx->"+lstDiscountsInfo_.size());
										for(int i=0;i<lstDiscountsInfo_.size();i++){
											DiscountRegInDBVO currDiscount_ = (DiscountRegInDBVO)lstDiscountsInfo_.get(i);
											if(currDiscount_.getDiscountType()==4){ // Es una anulaci�n de descuento
												answer_.setDiscount(0);
											}else{ // Es una adici�n de descuento
												long discAmt_ = currDiscount_.getDiscountAmount();
												answer_.setDiscount(discAmt_);
											}	
										}							
									}else
										System.out.println("Trx no tiene discounts.");	
									*/							
									long orderTotalAmt_ = 0;
									// CACS: Recorriendo lista de items entries
									for(int i=0;i<lstItemEntriesInfo_.size();i++){
										long currEntryAmount_ = 0;
										long currItemsCount_ = 0;
										ItemEntryRegInDBVO currItmEntry_ = (ItemEntryRegInDBVO)lstItemEntriesInfo_.get(i);
										//ItemEntryString itmStr_ = (ItemEntryString)string_;
										
										/*
										System.out.println("******* ITEM **********");
										System.out.println("Item code->"+currItmEntry_.getItemCode());
										System.out.println("Secci�n->"+currItmEntry_.getDepartme());
										System.out.println("Precio entrada->"+currItmEntry_.getXprice());
										System.out.println("Indicat 31->"+currItmEntry_.getIndicat31()); // {8: Anulaci�n 2:Devoluci�n}
										System.out.println("Indicat 211->"+currItmEntry_.getIndicat211()); // {1: Precio es negativo}
										//System.out.println("Indicat 16->"+currItmEntry_.getINDICAT16()); //
										System.out.println("Indicat 17->"+currItmEntry_.getIndicat17()); // 1: Es pesable  0: No pesable
										System.out.println("Sale Price->"+currItmEntry_.getSalePrice()); // 1: Es pesable  0: No pesable
										*/
										
										int countSign_ = 1;
										if (currItmEntry_.getIndicat31().equals("2") || currItmEntry_.getIndicat31().equals("8")) //{8: Anulaci�n 2:Devoluci�n} 
											countSign_ = -1;
										int amtSign_ = 1;
										if (currItmEntry_.getIndicat211()  .equals("1")) // {1: Precio es negativo}
											amtSign_ = -1;
										try{
											currEntryAmount_ = currItmEntry_.getXprice() * countSign_;
											if (amtSign_ == -1 && currEntryAmount_>0)
												currEntryAmount_ = currEntryAmount_ * amtSign_;
											orderTotalAmt_ += currEntryAmount_;
										}catch(Exception e){
											System.out.println("Exception transformando XPRICE. No deberia pasar");
										}
				
										if (currItmEntry_.getQtyOrWgt() > 0){
											System.out.println("Cantidad->"+currItmEntry_.getQtyOrWgt());
											currItemsCount_ = currItmEntry_.getQtyOrWgt()  * countSign_;
											//itemsNumber_ += currItemsCount_;
										}else{
											System.out.println("Cantidad x defecto->1");
											if (countSign_ == 1)
												currItemsCount_++;
											else
												currItemsCount_--;
											//itemsNumber_ ++;
										}
										long VATTaxRate_ = currItmEntry_.getVATRate();
										String ExemptionFlag_ = currItmEntry_.getExemptionFlag();
										long consumptionTaxRate_ = currItmEntry_.getConsumptionTaxRate();
										String measureUnit_ = currItmEntry_.getMeasureUnit();
										long discount_ = currItmEntry_.getDiscount();
										
										System.out.println("***********************");
										ServiceOrderItemVo itmVo_ = new ServiceOrderItemVo();
										answer_.setItemValues(currItmEntry_.getItemCode()+"", currItmEntry_.getDepartme()+"", currItemsCount_, currEntryAmount_, currItmEntry_.getIndicat17().equals("1"), VATTaxRate_, ExemptionFlag_, consumptionTaxRate_, measureUnit_, discount_,currItmEntry_.getSalePrice(),currItmEntry_.getICUIRate());
										//answer_.setUserDatasItemValues(plu_, unitaryPrice_, VATTaxRate_,excemptExcludedFlag_,consumTaxRate_, measureUnit,sectionDepartment_, discountValue_, movSign_);
										//answer_.setItemValues(String pItemCode, String pDeparment, long pCount);
										//itemsList_.add(;)
									}
									/*
									for(int i=0;i<lstItemsAdditionalInfo_.size();i++){
										TrxonlineTransactionInDBVO currItmAddInfReg_ = (TrxonlineTransactionInDBVO)lstItemsAdditionalInfo_.get(i);
										String plu_ = StringFormatter.removeCharsOnLeft(currItmAddInfReg_.getData2(),'0');
										System.out.println("***** User data item en orden de servicio *****");
										//System.out.println("Data1->"+userDataStr_.getDATA(1));
										//String plu_ = StringFormatter.removeCharsOnLeft(currItmAddInfReg_.getData2(),'0');
										System.out.println("PLU->"+currItmAddInfReg_.getData2());
		
										String unitaryPrice_ = currItmAddInfReg_.getData3();
										System.out.println("Precio unitario Item->"+unitaryPrice_);
										String quantity_ = currItmAddInfReg_.getData4();
										System.out.println("quantity_->"+quantity_);
										
										String VATTaxRate_ = currItmAddInfReg_.getData5();
										System.out.println("tasa IVA_->"+VATTaxRate_);
										
										String excemptExcludedFlag_ = currItmAddInfReg_.getData6();
										System.out.println("exemptExcludedFlag_->"+excemptExcludedFlag_);
										
										String consumTaxRate_ = currItmAddInfReg_.getData7();
										System.out.println("Tasa impo-consumo_->"+consumTaxRate_);
										
										String measureUnit = currItmAddInfReg_.getData8();
										System.out.println("measureUnit->"+measureUnit);
										
										//String unitaryPriceWeight_ = userDataStr_.getDATA(9);
										//System.out.println("unitaryPriceWeight_->"+unitaryPriceWeight_);						
										//String description_ = userDataStr_.getDATA(9);
										//System.out.println("description_->"+description_);
										
										String sectionDepartment_ = currItmAddInfReg_.getData9();
										sectionDepartment_ = StringFormatter.removeCharsOnLeft(sectionDepartment_,'0');
										sectionDepartment_ = StringFormatter.align(sectionDepartment_,3,'0',0,3);
										
										
										System.out.println("secciOn-departamento->"+sectionDepartment_);
										
										String discountValue_ = currItmAddInfReg_.getData10();
										System.out.println("Valor descuento->"+discountValue_);
										
										String movSign_ = currItmAddInfReg_.getData11();
										System.out.println("Signo movimiento->"+movSign_);
										answer_.setUserDatasItemValues(plu_, unitaryPrice_, VATTaxRate_,excemptExcludedFlag_,consumTaxRate_, measureUnit,sectionDepartment_, discountValue_, movSign_);								
										
									}
									*/
									for(int i=0;i<lstTendersInfo_.size();i++){
										TenderRegInDBVO currTender_ = (TenderRegInDBVO)lstTendersInfo_.get(i);
										ServiceOrderTenderVo tendToAdd_ = new ServiceOrderTenderVo();
										tendToAdd_.setTenderId(currTender_.getTenderId());
										tendToAdd_.setTenderAmount(currTender_.getTenderAmount()  );
										answer_.addTender(tendToAdd_, currTender_.getType()+"");
									}
								}else
									System.out.println("Trx no tiene tenders. No deberIa pasar");
							//}else
								//System.out.println("Trx no tiene user datas de informaciOn adicional. No deberIa pasar");	
						}else
							System.out.println("Trx no tiene Items. No deberIa pasar");
						
					}else{
						System.out.println("No hay info de cliente");
						throw new CustomerTrxNotFoundException("There is not customer User Data");
					}
				}else{
					System.out.println("No hay info fiscal");
					throw new FiscalInformationNotFoundException("There is not fiscal User Data");
				}					
			}else
				System.out.println("Dice que es una minifactura pero no tiene el user data. No deberIa pasar.");
	
				
		} catch (ConnectionFailedException e) {
			// TODO Bloque catch generado autom�ticamente
			e.printStackTrace();
		} catch (DBAccessException e) {
			// TODO Bloque catch generado autom�ticamente
			e.printStackTrace();
		}
		return answer_;

	}		
	
	private Period retrieveNextPeriod(Date pCurTime){
		Iterator it_=arrPartialTimes_.iterator();
		Period periodToReturn_ = null; //=new Time();
		//Time firstTime_=null;
		// Time toma el tiempo actual que lleg� en la varibale pCurTime
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
		// La respuesta obtenida es satisfactoria o la funci�n enviada en la trx no existe.
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
			System.out.println("Per�odo ya fue procesado. Encontrado en hashtable");
			return true;
		}	
	}
	
	private String getPeriodKey(Period pPeriod, java.util.Date pDate ){
		return  (pDate.getYear()+1900)+ // A�o Pago
				StringFormatter.align(""+(pDate.getMonth()+1),2,'0',0,2)+ // Mes								
				StringFormatter.align(""+(pDate.getDate()),2,'0',0,2)+ //D'ia
				pPeriod.getNumber() ; // Consecutivo del d�a.
	}
	
	private synchronized void insertProcessedPeriod(Period pPeriod, Date pDate ){
		String periodKey_ = getPeriodKey(pPeriod, pDate);
		if (theProcessedPeriodHashTable == null)
			theProcessedPeriodHashTable = new Hashtable();

		System.out.println("Tama�o de la tabla de perIodos procesados->"+theProcessedPeriodHashTable.size());
		if (theProcessedPeriodHashTable.size()>PERIODS_NUMBER_MAX) // No hay m�s de 200 periodos guardados en la hashtable
			theProcessedPeriodHashTable.clear();
		
		theProcessedPeriodHashTable.put(periodKey_,new Date());
	}	
	
	private String getTLogDateTime(Timestamp pDateTime){
		String answer_ = "";
		answer_ += ((pDateTime.getYear()+1900)+"").substring(2);
		answer_ += StringFormatter.align(((pDateTime.getMonth()+1)+""),2,'0',0,2);
		answer_ += StringFormatter.align(((pDateTime.getDate())+""),2,'0',0,2);
		answer_ += StringFormatter.align(((pDateTime.getHours())+""),2,'0',0,2);
		answer_ += StringFormatter.align(((pDateTime.getMinutes())+""),2,'0',0,2);
		return answer_;
	}

	private String getTLogDateTime2(Timestamp pDateTime){
		String answer_ = "";
		answer_ += ((pDateTime.getYear()+1900)+"").substring(2);
		answer_ += StringFormatter.align(((pDateTime.getMonth()+1)+""),2,'0',0,2);
		answer_ += StringFormatter.align(((pDateTime.getDate())+""),2,'0',0,2);
		answer_ += StringFormatter.align(((pDateTime.getHours())+""),2,'0',0,2);
		answer_ += StringFormatter.align(((pDateTime.getMinutes())+""),2,'0',0,2);
		answer_ += StringFormatter.align(((pDateTime.getSeconds())+""),2,'0',0,2);
		return answer_;
	}	

	private String getServiceDate(String pDateTime){
		//2020-11-16T13:09:17.878Z
		String answer_ = "";
		if (pDateTime != null && pDateTime.trim().length()>=6){
			answer_ += ComfandiPosTransaction.CURRENT_CENTURY + pDateTime.substring(0, 2)+"-"; // A�o
			answer_ += pDateTime.substring(2, 4)+"-"; // Mes
			answer_ += pDateTime.substring(4, 6); // D�a
		}
		return answer_;
	}	
	
	private String getServiceDateTime(String pDateTime){
		//2020-11-16T13:09:17.878Z
		String answer_ = "";
		if (pDateTime != null && pDateTime.trim().length()>=10){
			answer_ += ComfandiPosTransaction.CURRENT_CENTURY + pDateTime.substring(0, 2)+"-"; // Año
			answer_ += pDateTime.substring(2, 4)+"-"; // Mes
			answer_ += pDateTime.substring(4, 6)+"T"; // Día
			answer_ += pDateTime.substring(6, 8)+":"; // hora
			answer_ += pDateTime.substring(8, 10)+":"; // minuto
			//answer_ += pDateTime.substring(10, 12)+".000Z"; // segundo
			answer_ += "00.000Z"; // segundo
		}
		return answer_;
	}
	
	private String getServiceDateTime(Date pDateTime){
		//2020-11-16T13:09:17.878Z
		String answer_ = "";
		long dateTime_ = pDateTime.getTime();
		long miliseconds_  = dateTime_ % 1000;
		if (pDateTime != null){
			answer_ += 1900 + pDateTime.getYear()+"-"; // A�o
			answer_ += StringFormatter.align(((pDateTime.getMonth()+1)+""),2,'0',0,2)+"-"; // Mes
			answer_ += StringFormatter.align((pDateTime.getDate()+""),2,'0',0,2)+"T"; // D�a
			answer_ += StringFormatter.align((pDateTime.getHours()+""),2,'0',0,2)+":"; // hora
			answer_ += StringFormatter.align((pDateTime.getMinutes()+""),2,'0',0,2)+":"; // minuto
			//answer_ += pDateTime.substring(10, 12)+".000Z"; // segundo
			answer_ += StringFormatter.align((pDateTime.getSeconds()+""),2,'0',0,2)+"."; // segundo
			answer_ += StringFormatter.align((miliseconds_+""),3,'0',0,3)+"Z"; // mili segundo
		}
		return answer_;
	}

	public boolean insertWSTransactionAnswer(final JSONMessageVo pJSONMgVo, final String pCustomerId, final String pStore, final String pTerminal/*, final String pOperator*/,final String pTrx, final String pDate, final String pTime,  final String pTransactionId, final String pCUFE, final boolean pRetryFlag, final String pStringSMA, String pFiscalNumber){
		                                            //JSONMsgV0_,              pOrder.getCustomerId(),    Order_.getStore() ,pOrder.getTerminal(),                     pOrder.getTransaction(),pOrder.getDateTime(),              sendDocAnswer_.getTransactionId(),sendDocAnswer_.getCUFE(),false
		/*
		Thread hilo_ = new Thread(new Runnable() {
			public void run() {
				try {*/
					return FacturaElectronicaDBHelper.insertWSTransactionAnswer(pCustomerId, pStore, pTerminal/*, pOperator*/, pTrx, pDate, pTime, pTransactionId, pCUFE, pRetryFlag, pJSONMgVo.getTrxTotalAmount().longValue(), pJSONMgVo.getTrxTotalTaxesAmount().longValue(),pStringSMA,pFiscalNumber);
				/*} catch (DBAccessException e) {
					// TODO Bloque catch generado autom�ticamente
					e.printStackTrace();
				}
			}
		});
		hilo_.start();*/
	}	
	
	/*
	public boolean insertWSTransactionErrorAnswer(final JSONMessageVo pJSONMgVo, final String pCustomerId, final String pStore, final String pTerminal,final String pTrx, final String pDate, final String pTime,  final String pTransactionId, final String pCUFE, final boolean pRetryFlag, final String pStringSMA){
        //JSONMsgV0_,              pOrder.getCustomerId(),    Order_.getStore() ,pOrder.getTerminal(),                     pOrder.getTransaction(),pOrder.getDateTime(),              sendDocAnswer_.getTransactionId(),sendDocAnswer_.getCUFE(),false
				return FacturaElectronicaDBHelper.insertWSTransactionErrorAnswer(pCustomerId, pStore, pTerminal, pTrx, pDate, pTime, pTransactionId, pCUFE, pRetryFlag, pJSONMgVo.getTrxTotalAmount().longValue(), pJSONMgVo.getTrxTotalTaxesAmount().longValue(),pStringSMA);
	}
	*/


	private void setItemDescriptionsToOrder(ServiceOrderVo pOrder)throws ConnectionFailedException, DBAccessException, Exception, Throwable{
		System.out.println("Desde setItemDescriptionsToOrder");
		int itmsCounter_ = 0;
		String strWhere_ = "";
		for(int i=0;i<pOrder.getItemsList().size();i++){
			ServiceOrderItemVo itmVo_ = (ServiceOrderItemVo)pOrder.getItemsList().get(i);
			itmsCounter_++;
			if(itmsCounter_ == 1)
				strWhere_+=itmVo_.getPlu();		
			else{ 
				strWhere_+=","+itmVo_.getPlu();
				if(itmsCounter_ == 10){
					//List itmsDescsLst_ = FacturaElectronicaDBHelper.getItemsDescriptions(strWhere_);
					List itmsDescsLst_ = FacturaElectronicaDBHelper.getItemsDescriptions2(strWhere_);
					if(itmsDescsLst_!= null && itmsDescsLst_.size()>0){
						for(int j=0;j<=i;j++){
							ServiceOrderItemVo itmVo2_ = (ServiceOrderItemVo)pOrder.getItemsList().get(j);
							for(int k=0;k<itmsDescsLst_.size();k++){
								ItemDescriptionVo itmDesc_ = (ItemDescriptionVo)itmsDescsLst_.get(k);
								if(itmVo2_.getPlu().equals(itmDesc_.getPlu()+"")){
									// Es el �tem en cuesti�n. Se coloca la descrci�n
									itmVo2_.setDescription(itmDesc_.getDescription());
									k=itmsDescsLst_.size();
								}	
							}	
						}
					}	
					itmsCounter_=0;
					strWhere_ = "";
				}	
			}
		}
		if (!strWhere_.equals("")){ // Hay productos para obtener la descripci�n
			//List itmsDescsLst_ = FacturaElectronicaDBHelper.getItemsDescriptions(strWhere_);
			List itmsDescsLst_ = FacturaElectronicaDBHelper.getItemsDescriptions2(strWhere_);
			if(itmsDescsLst_!= null && itmsDescsLst_.size()>0){
				for(int j=0;j<pOrder.getItemsList().size();j++){
					ServiceOrderItemVo itmVo2_ = (ServiceOrderItemVo)pOrder.getItemsList().get(j);
					for(int k=0;k<itmsDescsLst_.size();k++){
						ItemDescriptionVo itmDesc_ = (ItemDescriptionVo)itmsDescsLst_.get(k);
						if(itmVo2_.getPlu().equals(itmDesc_.getPlu()+"")){
							// Es el �tem en cuesti�n. Se coloca la descrci�n
							itmVo2_.setDescription(itmDesc_.getDescription());
							k=itmsDescsLst_.size();
						}	
					}	
				}
			}			
		}
		itmsCounter_ = 0;
		strWhere_ = "";
		for(int i=0;i<pOrder.getItemsList().size();i++){
			ServiceOrderItemVo itmVo_ = (ServiceOrderItemVo)pOrder.getItemsList().get(i);
			if(itmVo_.getDescription() != null && itmVo_.getDescription().equals(ServiceOrderItemVo.ID_TEXT_PRODUCT_WITHOUT_DESCRIPTION)){
				System.out.println("Producto sin descripciOn->"+itmVo_.getPlu());
				itmsCounter_++;
				if(itmsCounter_ == 1)
					strWhere_+=itmVo_.getPlu();		
				else{ 
					strWhere_+=","+itmVo_.getPlu();
					if(itmsCounter_ == 10){
						List itmsDescsLst_ = FacturaElectronicaDBHelper.getAgreementItemsDescriptions(strWhere_);
						if(itmsDescsLst_!= null && itmsDescsLst_.size()>0){
							for(int j=0;j<=i;j++){
								ServiceOrderItemVo itmVo2_ = (ServiceOrderItemVo)pOrder.getItemsList().get(j);
								for(int k=0;k<itmsDescsLst_.size();k++){
									ItemDescriptionVo itmDesc_ = (ItemDescriptionVo)itmsDescsLst_.get(k);
									if(itmVo2_.getPlu().equals(itmDesc_.getPlu()+"")){
										// Es el �tem en cuesti�n. Se coloca la descrci�n
										itmVo2_.setDescription(itmDesc_.getDescription());
										k=itmsDescsLst_.size();
									}	
								}	
							}
						}	
						itmsCounter_=0;
						strWhere_ = "";
					}	
				}
			}	
		}
		if (!strWhere_.equals("")){ // Hay productos para obtener la descripci�n
			//List itmsDescsLst_ = FacturaElectronicaDBHelper.getItemsDescriptions(strWhere_);
			List itmsDescsLst_ = FacturaElectronicaDBHelper.getAgreementItemsDescriptions(strWhere_);
			if(itmsDescsLst_!= null && itmsDescsLst_.size()>0){
				for(int j=0;j<pOrder.getItemsList().size();j++){
					ServiceOrderItemVo itmVo2_ = (ServiceOrderItemVo)pOrder.getItemsList().get(j);
					for(int k=0;k<itmsDescsLst_.size();k++){
						ItemDescriptionVo itmDesc_ = (ItemDescriptionVo)itmsDescsLst_.get(k);
						if(itmVo2_.getPlu().equals(itmDesc_.getPlu()+"")){
							// Es el �tem en cuesti�n. Se coloca la descrci�n
							itmVo2_.setDescription(itmDesc_.getDescription());
							k=itmsDescsLst_.size();
						}	
					}	
				}
			}			
		}
		
	}	

	private void setCustomerInfoToOrder(ServiceOrderVo pOrder)throws ConnectionFailedException, DBAccessException, Exception, Throwable{
		System.out.println("Desde setCustomerInfoToOrder");
		/*
		int itmsCounter_ = 0;
		String strWhere_ = "";
		for(int i=0;i<pOrder.getItemsList().size();i++){
			ServiceOrderItemVo itmVo_ = (ServiceOrderItemVo)pOrder.getItemsList().get(i);
			itmsCounter_++;
			if(itmsCounter_ == 1)
				strWhere_+=itmVo_.getPlu();		
			else{ 
				strWhere_+=","+itmVo_.getPlu();
				if(itmsCounter_ == 10){*/
					CustomerInfoVo custInfo_ = FacturaElectronicaDBHelper.getCustomerInfo(pOrder.getCustomerId());
					if(custInfo_ != null && custInfo_.getCustomerDocType() == 5){ // Persona natural y jur�dica. El POS decidi� 
						System.out.println("Es una persona natural y jurIdica. custInfo_. Se usa el que llegO del POS");
						long documentoTypeId_ = 0;
						try{
							System.out.println("pOrder.getCustomerUDIdType()->"+pOrder.getCustomerUDIdType());
							documentoTypeId_ = Long.parseLong(pOrder.getCustomerUDIdType());
						}catch(Exception e){
							documentoTypeId_ = 1;
						}
						pOrder.setCustomerIdType(documentoTypeId_);
					}else{
						if(custInfo_ != null)
							System.out.println("custInfo_.getCustomerDocType()->"+custInfo_.getCustomerDocType());
						else {
							System.out.println("custInfo es null");
							throw new CustomerTrxNotFoundException("CLIENTE NO ENCONTRADO EN DB. No IDENTIFICACION:"+pOrder.getCustomerId());
						}	
						pOrder.setCustomerIdType(custInfo_.getCustomerDocType());						
					}
						
						

					pOrder.setCustomerName(custInfo_.getNames());
					pOrder.setCustomerLastName(custInfo_.getLastNames());
					pOrder.setAddress(custInfo_.getAddress());
					pOrder.setEmail(custInfo_.getEmail());
					pOrder.setPhoneNumber(custInfo_.getPhoneNumber());
					/*
					if(itmsDescsLst_!= null && itmsDescsLst_.size()>0){
						for(int j=0;j<i;j++){
							ServiceOrderItemVo itmVo2_ = (ServiceOrderItemVo)pOrder.getItemsList().get(j);
							for(int k=0;k<itmsDescsLst_.size();k++){
								ItemDescriptionVo itmDesc_ = (ItemDescriptionVo)itmsDescsLst_.get(k);
								if(itmVo2_.getPlu().equals(itmDesc_.getPlu()+"")){
									// Es el �tem en cuesti�n. Se coloca la descrci�n
									itmVo2_.setDescription(itmDesc_.getDescription());
									k=itmsDescsLst_.size();
								}	
							}	
						}
					}	
					itmsCounter_=0;
					strWhere_ = "";
				}	
			}
		}
		if (!strWhere_.equals("")){ // Hay productos para obtener la descripci�n
			List itmsDescsLst_ = FacturaElectronicaDBHelper.getItemsDescriptions(strWhere_);
			if(itmsDescsLst_!= null && itmsDescsLst_.size()>0){
				for(int j=0;j<pOrder.getItemsList().size();j++){
					ServiceOrderItemVo itmVo2_ = (ServiceOrderItemVo)pOrder.getItemsList().get(j);
					for(int k=0;k<itmsDescsLst_.size();k++){
						ItemDescriptionVo itmDesc_ = (ItemDescriptionVo)itmsDescsLst_.get(k);
						if(itmVo2_.getPlu().equals(itmDesc_.getPlu()+"")){
							// Es el �tem en cuesti�n. Se coloca la descrci�n
							itmVo2_.setDescription(itmDesc_.getDescription());
							k=itmsDescsLst_.size();
						}	
					}	
				}
			}			
		}
		*/
	}

	private String getJSONMessage(ServiceOrderVo pOrder){
		String strAnswer_ = "";
		try{
			StringBuffer answer_ = new StringBuffer("");
			
			answer_.append(		"			\"DRF\": {\n");
			/*
			answer_.append(		"				\"DRF_1\": 18760000001,\n");
			answer_.append(		"				\"DRF_2\": \"2019-01-19\",\n");
			answer_.append(		"				\"DRF_3\": \"2030-01-19\",\n");
			answer_.append(		"				\"DRF_4\": \"SETT\",\n");
			answer_.append(		"				\"DRF_5\": 1,\n");
			answer_.append(		"				\"DRF_6\": 5000000\n");
			*/
			
			answer_.append(		"				\"DRF_1\": "+pOrder.getFiscalResolNumber()+",\n");
			answer_.append(		"				\"DRF_2\": \""+pOrder.getFiscalResolIniDate()+"\",\n");
			answer_.append(		"				\"DRF_3\": \""+pOrder.getFiscalResolEndDate()+"\",\n");
			answer_.append(		"				\"DRF_4\": \""+pOrder.getFiscalPrefix()+"\",\n");
			answer_.append(		"				\"DRF_5\": "+pOrder.getFiscalResolFirstNumber()+",\n");
			answer_.append(		"				\"DRF_6\": "+pOrder.getFiscalResolLastNumber()+"\n");			
			
			answer_.append(		"			},\n");
			
			answer_.append(		"			\"MEP\": [\n"); 		
			for(int i=0;i<pOrder.getTendersList().size();i++){
				ServiceOrderTenderVo tndVo_ = (ServiceOrderTenderVo)pOrder.getTendersList().get(i);
				if (!tndVo_.isVoided()){
					if(i>0)
						answer_.append(		",\n");
					answer_.append(		"				{\n");
					answer_.append(		"					\"MEP_1\": \""+CarvajalProcessUtils.getDIANTenderCode(tndVo_.getTenderId())+"\",\n");
					answer_.append(		"					\"MEP_2\": "+CarvajalProcessUtils.getDIANTenderMethod(tndVo_.getTenderId())+",\n");
					answer_.append(		"					\"MEP_3\": \""+getServiceDate(pOrder.getDateTime())+"\"\n");
					answer_.append(		"				}");
				}
			}
			answer_.append(		"\n");
			answer_.append(		"			],\n");
			
			System.out.println("Tenders->"+answer_.toString());
			
			//answer_.append(		"  <ISH />\n");
			
			/*
			BigDecimal BDVAT0_ = new BigDecimal(0);
			BigDecimal BDVAT5_ = new BigDecimal(0);
			BigDecimal BDVAT19_ = new BigDecimal(0);
			*/
			BigDecimal BDICUITax_ = new BigDecimal(0);;
			
			theVATTableHash = null;
			theVATBasesTableHash = null;			
			
			/*
			BigDecimal BDBaseVAT0_ = new BigDecimal(0);
			BigDecimal BDBaseVAT5_ = new BigDecimal(0);
			BigDecimal BDBaseVAT19_ = new BigDecimal(0);
			*/
			BigDecimal BDBaseICUITax_ = new BigDecimal(0);
			BigDecimal BDBaseICUITaxImponible_ = new BigDecimal(0);
			
			answer_.append(		"			\"ITE\": [\n");			
			for(int i=0;i<pOrder.getItemsList().size();i++){
				ServiceOrderItemVo itmVo_ = (ServiceOrderItemVo)pOrder.getItemsList().get(i);
				
				if (itmVo_.getMeasureUnit() != null){
					if(i>0)
						answer_.append(		",\n");
					answer_.append(		"				{\n");
					//answer_.append(		"  <ITE>\n");

					answer_.append(		"					\"ITE_1\": "+(i+1)+",\n");
					//answer_.append(		"					""ITE_2": "false",\n");
					BigDecimal  weighableCount_ = new BigDecimal(0);
					if(itmVo_.getMeasureUnit().equals(CarvajalProcessUtils.COMF_WEIGH_CODE)){
						weighableCount_ = new BigDecimal(itmVo_.getCount());
						weighableCount_ = weighableCount_.divide(new BigDecimal(1000),3, RoundingMode.HALF_UP);
						answer_.append(		"					\"ITE_3\": "+weighableCount_.setScale(2, BigDecimal.ROUND_HALF_UP).toString()+",\n");
					}else
						answer_.append(		"					\"ITE_3\": "+itmVo_.getCount()+".00,\n");
					
					answer_.append(		"					\"ITE_4\": \""+CarvajalProcessUtils.getDianUnitCode(itmVo_.getMeasureUnit())+"\",\n");
					float VATTaxRate_ = 0;
					float ICUITaxRate_ = 0;
					//if(itmVo_.getVATTaxRate()>0){
						VATTaxRate_ = itmVo_.getVATTaxRate();
						ICUITaxRate_ = itmVo_.getICUITaxRate();
					//	VATTaxRate = (VATTaxRate/100) + 1;
					//}
					long unitValue_ = itmVo_.getUnitValue();	
					long unitValueWithouDiscount_ = itmVo_.getUnitValue();
					BigDecimal discountBaseValue_ = new BigDecimal(0);
					BigDecimal BDDiscount_ = new BigDecimal(0);
					if(itmVo_.getDiscountValue()>0){
						BDDiscount_ = new BigDecimal(itmVo_.getDiscountValue());
						if(itmVo_.getMeasureUnit().equals("KLG"))
							BDDiscount_ = BDDiscount_.divide(weighableCount_);
						else
							BDDiscount_ = BDDiscount_.divide(new BigDecimal(itmVo_.getCount()));
						//unitValue_ = unitValue_ - itmVo_.getDiscountValue();
						unitValueWithouDiscount_ = unitValueWithouDiscount_ - BDDiscount_.longValue();
						if(itmVo_.getMeasureUnit().equals("KLG"))
							discountBaseValue_ = CarvajalProcessUtils.getBaseValue((itmVo_.getDiscountValue()/weighableCount_.longValue()),VATTaxRate_);
						else
							discountBaseValue_ = CarvajalProcessUtils.getBaseValue((itmVo_.getDiscountValue()/itmVo_.getCount()),VATTaxRate_);
						
					}
					BigDecimal baseTotal_ = new BigDecimal(0);
					if(itmVo_.getMeasureUnit().equals("KLG"))
						baseTotal_ = CarvajalProcessUtils.getBaseValue(unitValueWithouDiscount_,(VATTaxRate_+ICUITaxRate_),5).multiply(weighableCount_);
					else
						baseTotal_ = CarvajalProcessUtils.getBaseValue(unitValueWithouDiscount_,(VATTaxRate_+ICUITaxRate_),5).multiply(new BigDecimal(itmVo_.getCount()));
					baseTotal_ = baseTotal_.setScale(2, BigDecimal.ROUND_HALF_UP);
					
					BigDecimal entryTotalWithouDiscount_ = new BigDecimal(unitValueWithouDiscount_);
					BigDecimal entryTotal_ = new BigDecimal(unitValue_);
					if(itmVo_.getMeasureUnit().equals("KLG")){
						entryTotalWithouDiscount_ = entryTotalWithouDiscount_.multiply(weighableCount_);
						entryTotal_ = entryTotal_.multiply(weighableCount_);
					}else{
						entryTotalWithouDiscount_ = entryTotalWithouDiscount_.multiply(new BigDecimal(itmVo_.getCount())); //DIANUtils.getBaseValue(unitValue_,VATTaxRate,5).multiply(new BigDecimal(itmVo_.getCount()));
						entryTotal_ = entryTotal_.multiply(new BigDecimal(itmVo_.getCount())); //DIANUtils.getBaseValue(unitValue_,VATTaxRate,5).multiply(new BigDecimal(itmVo_.getCount()));
					}	
						//unitaryBaseTotal_ = DIANUtils.getBaseValue(unitValue_,VATTaxRate,5);
					entryTotalWithouDiscount_ = entryTotalWithouDiscount_.setScale(2, BigDecimal.ROUND_DOWN);
					
					BigDecimal entryTotalBase_ = new BigDecimal(unitValueWithouDiscount_);
					BigDecimal BDUnitValue_ = new BigDecimal(unitValueWithouDiscount_);
					BDUnitValue_ = BDUnitValue_.setScale(0, BigDecimal.ROUND_DOWN);
					if(itmVo_.getMeasureUnit().equals("KLG"))
						//entryTotalBase_ =  DIANUtils.getBaseValue(  (BDUnitValue_.multiply(weighableCount_                  )).longValue(),  VATTaxRate,5);
						entryTotalBase_ =  CarvajalProcessUtils.getBaseValue(  BDUnitValue_.longValue(),  (VATTaxRate_+ICUITaxRate_),5);
					else
						//entryTotalBase_ = DIANUtils.getBaseValue(   (BDUnitValue_.multiply(new BigDecimal(itmVo_.getCount()))).longValue(),  VATTaxRate,5); //DIANUtils.getBaseValue(unitValue_,VATTaxRate,5).multiply(new BigDecimal(itmVo_.getCount()));
						entryTotalBase_ = CarvajalProcessUtils.getBaseValue(   BDUnitValue_.longValue(),  (VATTaxRate_+ICUITaxRate_),5); //DIANUtils.getBaseValue(unitValue_,VATTaxRate,5).multiply(new BigDecimal(itmVo_.getCount()));
						//unitaryBaseTotal_ = DIANUtils.getBaseValue(unitValue_,VATTaxRate,5);
					entryTotalBase_ = entryTotalBase_.setScale(2, BigDecimal.ROUND_HALF_UP);				
					
					answer_.append(		"					\"ITE_5\": "+baseTotal_.toString()+",\n"); // Columna 'Valor Total' Cantidad x Precio unitario - descuentos
					
					
					answer_.append(		"					\"ITE_6\": \"COP\",\n");
					
					BigDecimal base_ = CarvajalProcessUtils.getBaseValue(unitValueWithouDiscount_,VATTaxRate_);
					//answer_.append(		"    <ITE_7>"+base_.toString()+"</ITE_7>\n"); // Precio x unidad Parece ITE_5 + Base Descuento
					
					//answer_.append(		"    <ITE_7>"+baseTotal_.add(discountBaseValue_)+"</ITE_7>\n"); // Precio x unidad Parece ITE_5 + Base Descuento
					answer_.append(		"					\"ITE_7\": "+entryTotalBase_.add(discountBaseValue_)+",\n"); // Precio x unidad Parece ITE_5 + Base Descuento
					
					
					
					answer_.append(		"					\"ITE_8\": \"COP\",\n");
					//answer_.append(		"					""ITE_10": "+DIANUtils.removeSpecialCharacters(itmVo_.getDescription().trim())+"</ITE_10>\n");
					answer_.append(		"					\"ITE_11\": \""+CarvajalProcessUtils.removeSpecialCharacters(itmVo_.getDescription().trim())+"\",\n");
					//answer_.append(		"					""ITE_14 />\n");
					answer_.append(		"					\"ITE_21\": "+entryTotalWithouDiscount_.toString()+",\n"); // Valor total unitario - descuento
					answer_.append(		"					\"ITE_22\": \"COP\",\n");
					//answer_.append(		"					""ITE_24": "COP</ITE_24>\n");
					if(itmVo_.getMeasureUnit().equals(CarvajalProcessUtils.COMF_WEIGH_CODE))
						answer_.append(		"					\"ITE_27\": "+weighableCount_.setScale(2, BigDecimal.ROUND_HALF_UP).toString()+",\n");
					else
						answer_.append(		"					\"ITE_27\": "+itmVo_.getCount()+".00,\n");
					
					answer_.append(		"					\"ITE_28\": \""+CarvajalProcessUtils.getDianUnitCode(itmVo_.getMeasureUnit())+"\",\n");
				    
					answer_.append(		"					\"IAE\": {\n");
					answer_.append(		"						\"IAE_1\": "+itmVo_.getPlu()+",\n"); // PLU
					answer_.append(		"						\"IAE_2\": 999\n"); // Stándar adoptado x por el contribuyente
					answer_.append(		"					}");
					
					if(itmVo_.getDiscountValue()>0){
						double dDiscountPercent_ = new Double(itmVo_.getDiscountValue()).doubleValue() * 100 / new Double(itmVo_.getUnitValue()).doubleValue();
						BigDecimal DiscountPercent_ = new BigDecimal(dDiscountPercent_);
						if(itmVo_.getMeasureUnit().equals("KLG"))
							DiscountPercent_ = DiscountPercent_.divide(weighableCount_, 2, RoundingMode.HALF_UP);
						else
							DiscountPercent_ = DiscountPercent_.divide(new BigDecimal(itmVo_.getCount()), 2, RoundingMode.HALF_UP);
						DiscountPercent_ = DiscountPercent_.setScale(0, BigDecimal.ROUND_HALF_UP);
						/*
						if(itmVo_.getMeasureUnit().equals("KLG"))
							DiscountPercent_ = DiscountPercent_.divide(weighableCount_, 2, RoundingMode.DOWN);
						else
							DiscountPercent_ = DiscountPercent_.divide(new BigDecimal(itmVo_.getCount()), 2, RoundingMode.DOWN);
						*/
						//DiscountPercent_ = DiscountPercent_.setScale(0, BigDecimal.ROUND_HALF_UP);					
						
						/*** Descuento *****/
						answer_.append(		",\n");
						answer_.append(		"					\"IDE\": [\n");
						answer_.append(		"						{\n");
						
						answer_.append(		"							\"IDE_1\": \"false\",\n");
						answer_.append(		"							\"IDE_2\": "+itmVo_.getDiscountValue()+".00,\n"); // Descuento
						answer_.append(		"							\"IDE_3\": \"COP\",\n");
						answer_.append(		"							\"IDE_6\": "+DiscountPercent_+".00,\n"); // % de descuento
						//answer_.append(		"      <IDE_7>"+itmVo_.getUnitValue()+".00</IDE_7>\n"); // Valor unitario
						//answer_.append(		"      <IDE_7>"+entryTotalWithouDiscount_.subtract(BDDiscount_)+".00</IDE_7>\n"); // Valor total entrada
						answer_.append(		"							\"IDE_7\": "+entryTotal_.setScale(2, BigDecimal.ROUND_DOWN).toString()+",\n"); // Valor total entrada
						answer_.append(		"							\"IDE_8\": \"COP\",\n");
						answer_.append(		"							\"IDE_10\": 1\n"); // Parece el secuencial de un grupo(Empieza x uno)
						
						answer_.append(		"						}\n");
					    answer_.append(		"					]");
					}    
					
					
					//BigDecimal totalTaxValue_ = DIANUtils.getTaxValue(entryTotalWithouDiscount_.longValue(),VATTaxRate_);
					BigDecimal totalVATTaxValue_ = CarvajalProcessUtils.getTaxValueFromBaseValue(baseTotal_,VATTaxRate_);
					
						//new BigDecimal(itmVo_.get);
					/*
					BigDecimal BDUnitaryTaxValue_ = (BDUnitValue_.subtract(DIANUtils.getBaseValue(unitValueWithouDiscount_,VATTaxRate_,3)));
					
					if(itmVo_.getMeasureUnit().equals("KLG"))
						BDUnitaryTaxValue_ = BDUnitaryTaxValue_.divide(weighableCount_, 2, RoundingMode.DOWN);
					else
						BDUnitaryTaxValue_ = BDUnitaryTaxValue_.divide(new BigDecimal(itmVo_.getCount()), 2, RoundingMode.DOWN);
					*/	
					
					//.setScale(2, BigDecimal.ROUND_DOWN)
					String strVATXML_ = "";
					
					//if(totalTaxValue_.longValue()>0){
						//strVATXML_ += 		"    <TII>\n";
						strVATXML_ += 		",\n";
						strVATXML_ += 		"					\"TII\": [\n";
						strVATXML_ += 		"						{\n";						
						strVATXML_ += 		"							\"TII_1\": "+totalVATTaxValue_.setScale(2, BigDecimal.ROUND_HALF_UP).toString()+",\n";
						strVATXML_ += 		"							\"TII_2\": \"COP\",\n";
						strVATXML_ += 		"							\"TII_3\": \"false\",\n";
						
						strVATXML_ += 		"							\"IIM\": [\n";
						strVATXML_ += 		"								{\n";						
						strVATXML_ += 		"									\"IIM_1\": \"01\",\n"; // IVA 
						//BigDecimal BDUnitValue_ = new BigDecimal(unitValue_);
						//strVATXML_ += 		"        <IIM_2>"+DIANUtils.getTaxValue(itmVo_.getUnitValue(),VATTaxRate).toString()+"</IIM_2>\n");
						//strVATXML_ += 		"        <IIM_2>"+(BDUnitValue_.subtract(DIANUtils.getBaseValue(unitValueWithouDiscount_,VATTaxRate_,3)).setScale(2, BigDecimal.ROUND_DOWN)).toString()+"</IIM_2>\n";  // Valor impuesto
						//strVATXML_ += 		"        <IIM_2>"+DIANUtils.getTaxValue(unitValueWithouDiscount_,VATTaxRate_).toString()+"</IIM_2>\n";
						//if(itmVo_.getVATTaxRate() == 19)
							//BDVAT19_ = BDVAT19_.add(DIANUtils.getTaxValue(entryTotalWithouDiscount_.longValue(),VATTaxRate_));
							//addVATValue(itmVo_.getVATTaxRate(), DIANUtils.getTaxValue(entryTotalWithouDiscount_.longValue(),VATTaxRate_));
							addVATValue(itmVo_.getVATTaxRate(), totalVATTaxValue_.setScale(2, BigDecimal.ROUND_HALF_UP));							
						/*else if(itmVo_.getVATTaxRate() == 5)
							BDVAT5_ = BDVAT5_.add(DIANUtils.getTaxValue(entryTotalWithouDiscount_.longValue(),VATTaxRate_));
						else if(itmVo_.getVATTaxRate() == 0)
							BDVAT0_ = BDVAT0_.add(DIANUtils.getTaxValue(entryTotalWithouDiscount_.longValue(),VATTaxRate_));
						*/
						
						//strVATXML_ += 		"									\"IIM_2\": "+DIANUtils.getTaxValue(entryTotalWithouDiscount_.longValue(),VATTaxRate_).toString()+",\n"; // Valor Impuesto
							strVATXML_ += 		"									\"IIM_2\": "+totalVATTaxValue_.setScale(2, BigDecimal.ROUND_HALF_UP).toString()+",\n"; // Valor Impuesto IVA	
						
						
						strVATXML_ += 		"									\"IIM_3\": \"COP\",\n";
						
						/*
						if(itmVo_.getVATTaxRate() == 19)
							BDBaseVAT19_ = BDBaseVAT19_.add(baseTotal_);
						else if(itmVo_.getVATTaxRate() == 5)
							BDBaseVAT5_ = BDBaseVAT5_.add(baseTotal_);
						else if(itmVo_.getVATTaxRate() == 0)
							BDBaseVAT0_ = BDBaseVAT0_.add(baseTotal_);					
						*/
						addVATBaseValue(itmVo_.getVATTaxRate(), baseTotal_);
						
						strVATXML_ += 		"									\"IIM_4\": "+baseTotal_.toString()+",\n"; // Valor Base
						strVATXML_ += 		"									\"IIM_5\": \"COP\",\n";
						

						
						strVATXML_ += 		"									\"IIM_6\": \""+itmVo_.getVATTaxRate()+".00\"\n";
						strVATXML_ += 		"								}\n";
						strVATXML_ += 		"							]\n";
						strVATXML_ += 		"						}";
						//strVATXML_ += 		"    </TII>\n";		

					//}
					boolean closeTaxArrayTag_ = false;	
					//if(totalTaxValue_.longValue()>0){
					if(totalVATTaxValue_.longValue()>0){
						answer_.append(strVATXML_);
						closeTaxArrayTag_ = true;
					}	
					//else
						//answer_.append("\n");
					
					//BigDecimal totalICUITaxValue_ = DIANUtils.getTaxValue(unitValueWithouDiscount_,ICUITaxRate_);
					BigDecimal totalICUITaxValue_ = CarvajalProcessUtils.getTaxValueFromBaseValue(baseTotal_,ICUITaxRate_);
					
					//new BigDecimal(itmVo_.get);
					if(totalICUITaxValue_.longValue()>0){
						//if(totalTaxValue_.longValue() == 0)
						if(totalVATTaxValue_.longValue() == 0)	
							answer_.append(strVATXML_);
						answer_.append( 	",\n");
						if(!closeTaxArrayTag_){
							strVATXML_ += 		"					\"TII\": [\n";
							closeTaxArrayTag_ = true;
						}	
						//answer_.append(		"					\"TII\": [\n");
						answer_.append(		"						{\n");						
						answer_.append(		"							\"TII_1\": "+totalICUITaxValue_.setScale(2, BigDecimal.ROUND_HALF_UP).toString()+",\n");
						//answer_.append(		"      <TII_1>"+totalICUITaxValue_.setScale(2, BigDecimal.ROUND_HALF_UP).toString()+"</TII_1>\n");
						answer_.append(		"							\"TII_2\": \"COP\",\n");
						answer_.append(		"							\"TII_3\": \"false\",\n");

						answer_.append(		"							\"IIM\": [\n");
						answer_.append(		"								{\n");						
						answer_.append(		"									\"IIM_1\": \"35\",\n"); // ICUI 
						//BigDecimal BDUnitValue_ = new BigDecimal(unitValue_);
						//answer_.append(		"        <IIM_2>"+DIANUtils.getTaxValue(itmVo_.getUnitValue(),VATTaxRate).toString()+"</IIM_2>\n");
						
						/*
						BigDecimal baseICUITotal_ = new BigDecimal(0);
						if(itmVo_.getMeasureUnit().equals("KLG"))
							baseICUITotal_ = DIANUtils.getBaseValue(unitValueWithouDiscount_,ICUITaxRate_,5).multiply(weighableCount_);
						else
							baseICUITotal_ = DIANUtils.getBaseValue(unitValueWithouDiscount_,ICUITaxRate_,5).multiply(new BigDecimal(itmVo_.getCount()));
						baseICUITotal_ = baseICUITotal_.setScale(2, BigDecimal.ROUND_DOWN);
						*/
						//answer_.append(		"									\"IIM_2\": "+(BDUnitValue_.subtract(DIANUtils.getBaseValue(unitValueWithouDiscount_,ICUITaxRate_,3)).setScale(2, BigDecimal.ROUND_DOWN)).toString()+",\n");  // Valor impuesto
						answer_.append(			"									\"IIM_2\": "+totalICUITaxValue_.setScale(2, BigDecimal.ROUND_HALF_UP).toString()+",\n");  // Valor impuesto
						
						
						answer_.append(		"									\"IIM_3\": \"COP\",\n");
						//answer_.append(		"									\"IIM_4\": "+baseICUITotal_.toString()+",\n"); // Base
						answer_.append(		"									\"IIM_4\": "+baseTotal_.toString()+",\n"); // Base tiene en cuenta VAT + ICUI
						
						answer_.append(		"									\"IIM_5\": \"COP\",\n");
						answer_.append(		"									\"IIM_6\": \""+itmVo_.getICUITaxRate()+".00\",\n");
						answer_.append(		"									\"IIM_11\": \"ICUI\"\n");
						answer_.append(		"								}\n");
						answer_.append(		"							]\n");
						answer_.append(		"						}");
						//answer_.append(		"					]\n");
						
						//answer_.append(		"							]\n");
						//answer_.append(		"    <TII>\n");
						//BDICUITax_ = BDICUITax_.add(BDUnitValue_.subtract(DIANUtils.getBaseValue(unitValueWithouDiscount_,ICUITaxRate_,3)).setScale(2, BigDecimal.ROUND_DOWN));
						
						BDICUITax_ = BDICUITax_.add(totalICUITaxValue_.setScale(2, BigDecimal.ROUND_HALF_UP));
						//BDBaseICUITax_ = BDBaseICUITax_.add(baseICUITotal_);
						BDBaseICUITax_ = BDBaseICUITax_.add(baseTotal_);
						if(totalVATTaxValue_.longValue() == 0) // La tasa de IVA es 0 (Cero). Sólo se suma cuando el IVA escero, sino es cero la base queda dentro del IVA
							BDBaseICUITaxImponible_ = BDBaseICUITaxImponible_.add(baseTotal_);						
						
					}
					answer_.append(		"\n");	
					if(closeTaxArrayTag_)
						answer_.append( 	"					]\n");
					

					answer_.append(		"				}");
					
					
					
					
				}
		
				
			}
			answer_.append(		"\n");			
			answer_.append(		"			],\n");

			
			/********************  IVAS ******************/
			//System.out.println("Total IVA 19_->"+BDVAT19_.toString());
			System.out.println("Total IVA 19_->"+getVATValue(19).toString());
			//System.out.println("Total Base IVA 19_->"+BDBaseVAT19_.toString());
			System.out.println("Total Base IVA 19_->"+getVATBaseValue(19).toString());
			
			//System.out.println("Total IVA 5_->"+BDVAT5_.toString());
			System.out.println("Total IVA 5_->"+getVATValue(5).toString());
			//System.out.println("Total Base IVA 5_->"+BDBaseVAT5_.toString());
			System.out.println("Total Base IVA 5_->"+getVATBaseValue(5).toString());

			//System.out.println("Total IVA 0_->"+BDVAT0_.toString());
			System.out.println("Total IVA 0_->"+getVATValue(0).toString());
			//System.out.println("Total Base IVA 0_->"+BDBaseVAT0_.toString());		
			System.out.println("Total Base IVA 0_->"+getVATBaseValue(0).toString());

			Set keys_ = theVATTableHash.keySet();
			Iterator iterator_ = keys_.iterator();
			int VATTAXArray_[] = new int[keys_.size()];
			int i=0;
			while(iterator_.hasNext()){
				VATTAXArray_[i] = ((Long)iterator_.next()).intValue();
				i++;	
			}
			
			System.out.println(VATTAXArray_.length);
			
			VATTAXArray_ = ArrayUtils.orderArray(VATTAXArray_, ArrayUtils.DESCENDING);
			
			System.out.println("Array Ordenado");
			
			BigDecimal BDTotalVATValue_ = new BigDecimal(0);
			//BDTotalVATValue_ = BDTotalVATValue_.add(BDVAT19_);
			//BDTotalVATValue_ = BDTotalVATValue_.add(BDVAT5_);
			/*
			BDTotalVATValue_ = BDTotalVATValue_.add(getVATValue(19));
			BDTotalVATValue_ = BDTotalVATValue_.add(getVATValue(5));
			*/
	
			for(int j=0;j<VATTAXArray_.length;j++){
				//VATTAXArray_[i] = ((Long)iterator_.next()).intValue();
				//if(VATTAXArray_[j]>0)
					BDTotalVATValue_ = BDTotalVATValue_.add(getVATValue(VATTAXArray_[j]));
			}			
			
			BigDecimal BDTotalVATBaseValue_ = new BigDecimal(0);
			//BDTotalVATBaseValue_ = BDTotalVATBaseValue_.add(BDBaseVAT19_);
			//BDTotalVATBaseValue_ = BDTotalVATBaseValue_.add(BDBaseVAT5_);
			//BDTotalVATBaseValue_ = BDTotalVATBaseValue_.add(BDBaseVAT0_);
			/*
			BDTotalVATBaseValue_ = BDTotalVATBaseValue_.add(getVATBaseValue(19));
			BDTotalVATBaseValue_ = BDTotalVATBaseValue_.add(getVATBaseValue(5));
			BDTotalVATBaseValue_ = BDTotalVATBaseValue_.add(getVATBaseValue(0));
			*/
			for(int j=0;j<VATTAXArray_.length;j++){
				//VATTAXArray_[i] = ((Long)iterator_.next()).intValue();
				//if(VATTAXArray_[j]>0)
					BDTotalVATBaseValue_ = BDTotalVATBaseValue_.add(getVATBaseValue(VATTAXArray_[j]));
			}
			
			
			System.out.println("Total IVA_->"+BDTotalVATValue_.toString());
			System.out.println("Total Base IVA->"+BDTotalVATBaseValue_.toString());
			
			String strVATTotalValuesXML_ = "";
			strVATTotalValuesXML_ += 			"			\"TIM\": [\n";
			strVATTotalValuesXML_ += 			"				{\n";
			strVATTotalValuesXML_ += 			"					\"TIM_1\": \"false\",\n";
			strVATTotalValuesXML_ += 			"					\"TIM_2\": "+BDTotalVATValue_.setScale(2, BigDecimal.ROUND_HALF_UP).toString()+",\n"; // Valor Total del impuuesto
			strVATTotalValuesXML_ += 			"					\"TIM_3\": \"COP\",\n";
			boolean pendigColon_ = false;
			//Set<Integer> keys_ = theVATTableHash.keySet();
			
			for(int j=0;j<VATTAXArray_.length;j++){
				BigDecimal currVATValue_ = getVATValue(VATTAXArray_[j]);
				if(getVATValue(VATTAXArray_[j]).longValue() > 0){
					/*
					strVATTotalValuesXML_ += 		"    <IMP>\n";
					strVATTotalValuesXML_ += 		"      <IMP_1>01</IMP_1>\n";
					strVATTotalValuesXML_ += 		"      <IMP_2>"+getVATBaseValue(VATTAXArray_[j]).toString()+"</IMP_2>\n"; // Valor Base impuesto
					strVATTotalValuesXML_ += 		"      <IMP_3>COP</IMP_3>\n";
					strVATTotalValuesXML_ += 		"      <IMP_4>"+getVATValue(VATTAXArray_[j])+"</IMP_4>\n"; // Valor impuesto
					strVATTotalValuesXML_ += 		"      <IMP_5>COP</IMP_5>\n";
					strVATTotalValuesXML_ += 		"      <IMP_6>"+VATTAXArray_[j]+".00</IMP_6>\n";
					strVATTotalValuesXML_ += 		"    </IMP>\n";
					*/
					
					if(pendigColon_)
						strVATTotalValuesXML_ += 		",\n";
					else{
						if(j>0)
							strVATTotalValuesXML_ += 		"\n";
						else // Es igual a cero
							strVATTotalValuesXML_ +="					\"IMP\": [\n";
						pendigColon_ = true;
					}					
					strVATTotalValuesXML_ += 		"						{\n";
					strVATTotalValuesXML_ += 		"							\"IMP_1\": \"01\",\n";
					//strVATTotalValuesXML_ += 		"							\"IMP_2\": "+BDBaseVAT19_.setScale(2, BigDecimal.ROUND_HALF_UP).toString()+",\n"; // Valor Base impuesto
					strVATTotalValuesXML_ += 		"							\"IMP_2\": "+getVATBaseValue(VATTAXArray_[j]).toString()+",\n"; // Valor Base impuesto
					strVATTotalValuesXML_ += 		"							\"IMP_3\": \"COP\",\n";
					//strVATTotalValuesXML_ += 		"							\"IMP_4\": "+BDVAT19_.setScale(2, BigDecimal.ROUND_HALF_UP).toString()+",\n"; // Valor impuesto
					strVATTotalValuesXML_ += 		"							\"IMP_4\": "+getVATValue(VATTAXArray_[j]).toString()+",\n"; // Valor impuesto
					strVATTotalValuesXML_ += 		"							\"IMP_5\": \"COP\",\n";
					strVATTotalValuesXML_ += 		"							\"IMP_6\": \""+VATTAXArray_[j]+".00\"\n";
					strVATTotalValuesXML_ += 		"						}";
					pendigColon_ = true;					
				}				
			}
			
			/*
			if(BDVAT19_.longValue() > 0){
				strVATTotalValuesXML_ += 		"					\"IMP\": [\n";
				strVATTotalValuesXML_ += 		"						{\n";
				strVATTotalValuesXML_ += 		"							\"IMP_1\": \"01\",\n";
				strVATTotalValuesXML_ += 		"							\"IMP_2\": "+BDBaseVAT19_.setScale(2, BigDecimal.ROUND_HALF_UP).toString()+",\n"; // Valor Base impuesto
				strVATTotalValuesXML_ += 		"							\"IMP_3\": \"COP\",\n";
				strVATTotalValuesXML_ += 		"							\"IMP_4\": "+BDVAT19_.setScale(2, BigDecimal.ROUND_HALF_UP).toString()+",\n"; // Valor impuesto
				strVATTotalValuesXML_ += 		"							\"IMP_5\": \"COP\",\n";
				strVATTotalValuesXML_ += 		"							\"IMP_6\": \"19.00\"\n";
				strVATTotalValuesXML_ += 		"						}";
				pendigColon_ = true;
			}	

			if(BDVAT5_.longValue() > 0){
				if(pendigColon_)
					strVATTotalValuesXML_ += 		",\n";
				else{
					strVATTotalValuesXML_ += 		"\n";
					pendigColon_ = true;
				}	
				//strVATTotalValuesXML_ += 		"					\"IMP>\n";
				strVATTotalValuesXML_ += 		"						{\n";				
				strVATTotalValuesXML_ += 		"							\"IMP_1\": \"01\",\n";
				strVATTotalValuesXML_ += 		"							\"IMP_2\": "+BDBaseVAT5_.toString()+",\n"; // Valor Base impuesto
				strVATTotalValuesXML_ += 		"							\"IMP_3\": \"COP\",\n";
				strVATTotalValuesXML_ += 		"							\"IMP_4\": "+BDVAT5_+",\n"; // Valor impuesto
				strVATTotalValuesXML_ += 		"							\"IMP_5\": \"COP\",\n";
				strVATTotalValuesXML_ += 		"							\"IMP_6\": \"5.00\"\n";
				strVATTotalValuesXML_ += 		"						}";
			}
			*/
			
			
			//if(BDBaseICUITax_.longValue() > 0){
			if(BDBaseICUITaxImponible_.longValue() > 0){		
				if(pendigColon_)
					strVATTotalValuesXML_ += 		",\n";
				else{
					strVATTotalValuesXML_ += 		"\n";
					pendigColon_ = true;
				}
				//strVATTotalValuesXML_ += 		"    <IMP>\n";
				strVATTotalValuesXML_ += 		"						{\n";
				strVATTotalValuesXML_ += 		"							\"IMP_1\": \"01\",\n";
				//strVATTotalValuesXML_ += 		"							\"IMP_2>"+BDBaseVAT0_.toString()+"</IMP_2>\n"; // Valor Base impuesto
				strVATTotalValuesXML_ += 		"							\"IMP_2\": "+BDBaseICUITax_.toString()+",\n"; // Valor Base impuesto
				strVATTotalValuesXML_ += 		"							\"IMP_3\": \"COP\",\n";
				//strVATTotalValuesXML_ += 		"							\"IMP_4\": "+BDVAT0_+",\n"; // Valor impuesto
				strVATTotalValuesXML_ += 		"							\"IMP_4\": "+getVATValue(0)+",\n"; // Valor impuesto
				strVATTotalValuesXML_ += 		"							\"IMP_5\": \"COP\",\n";
				strVATTotalValuesXML_ += 		"							\"IMP_6\": \"0.00\"\n";
				//strVATTotalValuesXML_ += 		"    </IMP>\n";
				strVATTotalValuesXML_ += 		"						}\n";
				
			}else
				strVATTotalValuesXML_ += 		"\n";
		
			strVATTotalValuesXML_ += 			"					]\n";
			strVATTotalValuesXML_ += 			"				}";
			
			if(BDICUITax_.longValue() > 0){
				if(pendigColon_)
					strVATTotalValuesXML_ += 		",\n";
				else{
					strVATTotalValuesXML_ += 		"\n";
					pendigColon_ = true;
				}				
				//strVATTotalValuesXML_ += 		"			\"TIM\": [\n";
				strVATTotalValuesXML_ += 		"				{\n";
				strVATTotalValuesXML_ += 		"					\"TIM_1\": \"false\",\n";
				strVATTotalValuesXML_ += 		"					\"TIM_2\": "+BDICUITax_.toString()+",\n"; // Valor Total del impuuesto
				strVATTotalValuesXML_ += 		"					\"TIM_3\": \"COP\",\n";
				strVATTotalValuesXML_ += 		"					\"IMP\": [\n";
				strVATTotalValuesXML_ += 		"						{\n";
				strVATTotalValuesXML_ += 		"							\"IMP_1\": \"35\",\n";
				strVATTotalValuesXML_ += 		"							\"IMP_2\": "+BDBaseICUITax_.toString()+",\n"; // Valor Base impuesto
				strVATTotalValuesXML_ += 		"							\"IMP_3\": \"COP\",\n";
				strVATTotalValuesXML_ += 		"							\"IMP_4\": "+BDICUITax_.toString()+",\n"; // Valor impuesto
				strVATTotalValuesXML_ += 		"							\"IMP_5\": \"COP\",\n";
				strVATTotalValuesXML_ += 		"							\"IMP_6\": \"15.00\",\n";
				strVATTotalValuesXML_ += 		"							\"IMP_11\": \"ICUI\"\n";
				strVATTotalValuesXML_ += 		"						}\n";				
				strVATTotalValuesXML_ += 		"					]\n";
				strVATTotalValuesXML_ += 		"				}";				
				//strVATTotalValuesXML_ += 		"			],\n";
			}
			if(pendigColon_)
				strVATTotalValuesXML_ += 		"\n";

			strVATTotalValuesXML_ += 			"			],\n";
			
			/********************  TOTALES ******************/
			BigDecimal BDTotalVATBaseValueWithICUI_ = new BigDecimal(0); // CACS: No tiene la base de cero(0) y se le adiciona la DB del ICUI
			/*
			BDTotalVATBaseValueWithICUI_ = BDTotalVATBaseValueWithICUI_.add(BDBaseVAT19_);
			BDTotalVATBaseValueWithICUI_ = BDTotalVATBaseValueWithICUI_.add(BDBaseVAT5_);
			*/
			
			/*
			BDTotalVATBaseValueWithICUI_ = BDTotalVATBaseValueWithICUI_.add(getVATBaseValue(19));
			BDTotalVATBaseValueWithICUI_ = BDTotalVATBaseValueWithICUI_.add(getVATBaseValue(5));
			BDTotalVATBaseValueWithICUI_ = BDTotalVATBaseValueWithICUI_.add(BDBaseICUITax_);
			*/
			
			for(int j=0;j<VATTAXArray_.length;j++){
				//VATTAXArray_[i] = ((Long)iterator_.next()).intValue();
				if(VATTAXArray_[j]>0)
					BDTotalVATBaseValueWithICUI_ = BDTotalVATBaseValueWithICUI_.add(getVATBaseValue(VATTAXArray_[j]));
			}
			//BDTotalVATBaseValueWithICUI_ = BDTotalVATBaseValueWithICUI_.add(BDBaseICUITax_);
			BDTotalVATBaseValueWithICUI_ = BDTotalVATBaseValueWithICUI_.add(BDBaseICUITaxImponible_);
			
			
			BigDecimal BDTotalTrxValue_ = new BigDecimal(0);
			BDTotalTrxValue_ = BDTotalTrxValue_.add(BDTotalVATBaseValue_);
			/*
			BDTotalTrxValue_ = BDTotalTrxValue_.add(BDVAT19_);
			BDTotalTrxValue_ = BDTotalTrxValue_.add(BDVAT5_);
			*/
			
			/*
			BDTotalTrxValue_ = BDTotalTrxValue_.add(getVATValue(19));
			BDTotalTrxValue_ = BDTotalTrxValue_.add(getVATValue(5));
			*/
			for(int j=0;j<VATTAXArray_.length;j++){
				//VATTAXArray_[i] = ((Long)iterator_.next()).intValue();
				if(VATTAXArray_[j]>0)
					BDTotalTrxValue_ = BDTotalTrxValue_.add(getVATValue(VATTAXArray_[j]));
			}			
			BDTotalTrxValue_ = BDTotalTrxValue_.add(BDICUITax_);

			
			
			
			String strTrxTotalValuesXML_ = "";
			strTrxTotalValuesXML_ += 		"			\"TOT\": {\n";
			strTrxTotalValuesXML_ +=		"				\"TOT_1\": "+BDTotalVATBaseValue_.setScale(2, BigDecimal.ROUND_HALF_UP).toString()+",\n"; // Total de las bases
			strTrxTotalValuesXML_ +=		"				\"TOT_2\": \"COP\",\n";
			strTrxTotalValuesXML_ +=		"				\"TOT_3\": "+BDTotalVATBaseValueWithICUI_.setScale(2, BigDecimal.ROUND_HALF_UP).toString()+",\n";
			strTrxTotalValuesXML_ +=		"				\"TOT_4\": \"COP\",\n";
			strTrxTotalValuesXML_ +=		"				\"TOT_5\": "+BDTotalTrxValue_.setScale(2, BigDecimal.ROUND_HALF_UP).toString()+",\n"; //'VALOR TOTAL'
			strTrxTotalValuesXML_ +=		"				\"TOT_6\": \"COP\",\n";
			strTrxTotalValuesXML_ +=		"				\"TOT_7\": "+BDTotalTrxValue_.setScale(2, BigDecimal.ROUND_HALF_UP).toString()+",\n"; //'VALOR TOTAL'
			strTrxTotalValuesXML_ +=		"				\"TOT_8\": \"COP\"\n";
			strTrxTotalValuesXML_ +=		"			},\n";		
			System.out.println("XML a enviar->"+strTrxTotalValuesXML_);
			//System.out.println("XML a enviar->"+strTrxTotalValuesXML_+strVATTotalValuesXML_+answer_.toString());
			strAnswer_ = strTrxTotalValuesXML_+strVATTotalValuesXML_+answer_.toString();
		}catch(Exception e){
			e.printStackTrace();
		}catch(Throwable t){
			t.printStackTrace();
		}	
		return strAnswer_;
	}
	
	private String getDebugXMLMessage(ServiceOrderVo pOrder){
		String strAnswer_ = "";
		try{
			StringBuffer answer_ = new StringBuffer("");
			
			/*** Medios de Pago *****/ 
			for(int i=0;i<pOrder.getTendersList().size();i++){
				ServiceOrderTenderVo tndVo_ = (ServiceOrderTenderVo)pOrder.getTendersList().get(i);
				if (!tndVo_.isVoided()){
					answer_.append(		"  <MEP>\n");
					//if(i>0)
						//answer_.append(		",");
					answer_.append(		"    <MEP_1>"+CarvajalProcessUtils.getDIANTenderCode(tndVo_.getTenderId())+"</MEP_1>\n");
					answer_.append(		"    <MEP_2>"+CarvajalProcessUtils.getDIANTenderMethod(tndVo_.getTenderId())+"</MEP_2>\n");
					answer_.append(		"    <MEP_3>"+getServiceDate(pOrder.getDateTime())+"</MEP_3>\n");
					answer_.append(		"  </MEP>\n");
				}
			}
			/*** Fin Medios de Pago *****/
			
			answer_.append(		"  <ISH />\n");

			
			/*
			BigDecimal BDVAT0_ = new BigDecimal(0);
			BigDecimal BDVAT5_ = new BigDecimal(0);
			BigDecimal BDVAT19_ = new BigDecimal(0);
			*/
			BigDecimal BDICUITax_ = new BigDecimal(0);;
			
			theVATTableHash = null;
			theVATBasesTableHash = null;
			
			/*
			BigDecimal BDBaseVAT0_ = new BigDecimal(0);
			BigDecimal BDBaseVAT5_ = new BigDecimal(0);
			BigDecimal BDBaseVAT19_ = new BigDecimal(0);
			*/
			BigDecimal BDBaseICUITax_ = new BigDecimal(0);
			
			/*** Informaci�n de Items *****/
			
			for(int i=0;i<pOrder.getItemsList().size();i++){
				ServiceOrderItemVo itmVo_ = (ServiceOrderItemVo)pOrder.getItemsList().get(i);
				
				if (itmVo_.getMeasureUnit() != null){
		

					//if(i>0)
					answer_.append(		"  <ITE>\n");

					answer_.append(		"    <ITE_1>"+(i+1)+"</ITE_1>\n");
					answer_.append(		"    <ITE_2>false</ITE_2>\n");
					//answer_.append(		"    <ITE_3>"+1.00+"</ITE_3>\n");
					BigDecimal  weighableCount_ = new BigDecimal(0);
					if(itmVo_.getMeasureUnit().equals(CarvajalProcessUtils.COMF_WEIGH_CODE)){
						weighableCount_ = new BigDecimal(itmVo_.getCount());
						weighableCount_ = weighableCount_.divide(new BigDecimal(1000),3, RoundingMode.DOWN);
						answer_.append(		"    <ITE_3>"+weighableCount_.setScale(2, BigDecimal.ROUND_DOWN).toString()+"</ITE_3>\n"); // Pesable
					}else
						answer_.append(		"    <ITE_3>"+itmVo_.getCount()+".00</ITE_3>\n"); // X Unidad
					
					answer_.append(		"    <ITE_4>"+CarvajalProcessUtils.getDianUnitCode(itmVo_.getMeasureUnit())+"</ITE_4>\n");
					float VATTaxRate_ = 0;
					float ICUITaxRate_ = 0;
					//if(itmVo_.getVATTaxRate()>0){
						VATTaxRate_ = itmVo_.getVATTaxRate();
						ICUITaxRate_ = itmVo_.getICUITaxRate();
					//	VATTaxRate = (VATTaxRate/100) + 1;
					//}
					long unitValue_ = itmVo_.getUnitValue();	
					long unitValueWithouDiscount_ = itmVo_.getUnitValue();
					BigDecimal discountBaseValue_ = new BigDecimal(0);
					BigDecimal BDDiscount_ = new BigDecimal(0);
					if(itmVo_.getDiscountValue()>0){
						BDDiscount_ = new BigDecimal(itmVo_.getDiscountValue());
						if(itmVo_.getMeasureUnit().equals("KLG"))
							BDDiscount_ = BDDiscount_.divide(weighableCount_);
						else
							BDDiscount_ = BDDiscount_.divide(new BigDecimal(itmVo_.getCount()));
						//unitValue_ = unitValue_ - itmVo_.getDiscountValue();
						unitValueWithouDiscount_ = unitValueWithouDiscount_ - BDDiscount_.longValue();
						if(itmVo_.getMeasureUnit().equals("KLG"))
							discountBaseValue_ = CarvajalProcessUtils.getBaseValue((itmVo_.getDiscountValue()/weighableCount_.longValue()),VATTaxRate_);
						else
							discountBaseValue_ = CarvajalProcessUtils.getBaseValue((itmVo_.getDiscountValue()/itmVo_.getCount()),VATTaxRate_);
						
					}
					BigDecimal baseTotal_ = new BigDecimal(0);
					if(itmVo_.getMeasureUnit().equals("KLG"))
						baseTotal_ = CarvajalProcessUtils.getBaseValue(unitValueWithouDiscount_,(VATTaxRate_+ICUITaxRate_),5).multiply(weighableCount_);
					else
						baseTotal_ = CarvajalProcessUtils.getBaseValue(unitValueWithouDiscount_,(VATTaxRate_+ICUITaxRate_),5).multiply(new BigDecimal(itmVo_.getCount()));
					baseTotal_ = baseTotal_.setScale(2, BigDecimal.ROUND_DOWN);
					
					BigDecimal entryTotalWithouDiscount_ = new BigDecimal(unitValueWithouDiscount_);
					BigDecimal entryTotal_ = new BigDecimal(unitValue_);
					if(itmVo_.getMeasureUnit().equals("KLG")){
						entryTotalWithouDiscount_ = entryTotalWithouDiscount_.multiply(weighableCount_);
						entryTotal_ = entryTotal_.multiply(weighableCount_);
					}else{
						entryTotalWithouDiscount_ = entryTotalWithouDiscount_.multiply(new BigDecimal(itmVo_.getCount())); //DIANUtils.getBaseValue(unitValue_,VATTaxRate,5).multiply(new BigDecimal(itmVo_.getCount()));
						entryTotal_ = entryTotal_.multiply(new BigDecimal(itmVo_.getCount())); //DIANUtils.getBaseValue(unitValue_,VATTaxRate,5).multiply(new BigDecimal(itmVo_.getCount()));
					}	
						//unitaryBaseTotal_ = DIANUtils.getBaseValue(unitValue_,VATTaxRate,5);
					entryTotalWithouDiscount_ = entryTotalWithouDiscount_.setScale(2, BigDecimal.ROUND_DOWN);
					
					BigDecimal entryTotalBase_ = new BigDecimal(unitValueWithouDiscount_);
					BigDecimal BDUnitValue_ = new BigDecimal(unitValueWithouDiscount_);
					BDUnitValue_ = BDUnitValue_.setScale(0, BigDecimal.ROUND_DOWN);
					if(itmVo_.getMeasureUnit().equals("KLG"))
						//entryTotalBase_ =  DIANUtils.getBaseValue(  (BDUnitValue_.multiply(weighableCount_                  )).longValue(),  VATTaxRate,5);
						entryTotalBase_ =  CarvajalProcessUtils.getBaseValue(  BDUnitValue_.longValue(),  (VATTaxRate_+ICUITaxRate_),5);
					else
						//entryTotalBase_ = DIANUtils.getBaseValue(   (BDUnitValue_.multiply(new BigDecimal(itmVo_.getCount()))).longValue(),  VATTaxRate,5); //DIANUtils.getBaseValue(unitValue_,VATTaxRate,5).multiply(new BigDecimal(itmVo_.getCount()));
						entryTotalBase_ = CarvajalProcessUtils.getBaseValue(   BDUnitValue_.longValue(),  (VATTaxRate_+ICUITaxRate_),5); //DIANUtils.getBaseValue(unitValue_,VATTaxRate,5).multiply(new BigDecimal(itmVo_.getCount()));
						//unitaryBaseTotal_ = DIANUtils.getBaseValue(unitValue_,VATTaxRate,5);
					entryTotalBase_ = entryTotalBase_.setScale(2, BigDecimal.ROUND_DOWN);				
					
					answer_.append(		"    <ITE_5>"+baseTotal_.setScale(2, BigDecimal.ROUND_HALF_UP).toString()+"</ITE_5>\n"); // Columna 'Valor Total' Cantidad x Precio unitario - descuentos
					
					
					answer_.append(		"    <ITE_6>COP</ITE_6>\n");
					
					BigDecimal base_ = CarvajalProcessUtils.getBaseValue(unitValueWithouDiscount_,VATTaxRate_);
					//answer_.append(		"    <ITE_7>"+base_.toString()+"</ITE_7>\n"); // Precio x unidad Parece ITE_5 + Base Descuento
					
					//answer_.append(		"    <ITE_7>"+baseTotal_.add(discountBaseValue_)+"</ITE_7>\n"); // Precio x unidad Parece ITE_5 + Base Descuento
					answer_.append(		"    <ITE_7>"+entryTotalBase_.add(discountBaseValue_).setScale(2, BigDecimal.ROUND_HALF_UP).toString()+"</ITE_7>\n"); // Precio x unidad Parece ITE_5 + Base Descuento
					
					
					
					answer_.append(		"    <ITE_8>COP</ITE_8>\n");
					answer_.append(		"    <ITE_10>"+CarvajalProcessUtils.removeSpecialCharacters(itmVo_.getDescription().trim())+"</ITE_10>\n");
					answer_.append(		"    <ITE_11>"+CarvajalProcessUtils.removeSpecialCharacters(itmVo_.getDescription().trim())+"</ITE_11>\n");
					answer_.append(		"    <ITE_14 />\n");
					answer_.append(		"    <ITE_21>"+entryTotalWithouDiscount_.setScale(2, BigDecimal.ROUND_HALF_UP).toString()+"</ITE_21>\n"); // Valor total unitario - descuento
					answer_.append(		"    <ITE_22>COP</ITE_22>\n");
					answer_.append(		"    <ITE_24>COP</ITE_24>\n");
					if(itmVo_.getMeasureUnit().equals(CarvajalProcessUtils.COMF_WEIGH_CODE))
						answer_.append(		"    <ITE_27>"+weighableCount_.setScale(2, BigDecimal.ROUND_DOWN).toString()+"</ITE_27>\n");
					else
						answer_.append(		"    <ITE_27>"+itmVo_.getCount()+".00</ITE_27>\n");
					
					answer_.append(		"    <ITE_28>"+CarvajalProcessUtils.getDianUnitCode(itmVo_.getMeasureUnit())+"</ITE_28>\n");
				    
					answer_.append(		"    <IAE>\n");
					answer_.append(		"      <IAE_1>"+itmVo_.getPlu()+"</IAE_1>\n");
					answer_.append(		"      <IAE_2>999</IAE_2>\n"); // St�ndar adoptado x por el contribuyente
					answer_.append(		"    </IAE>\n");
					
					if(itmVo_.getDiscountValue()>0){
						double dDiscountPercent_ = itmVo_.getDiscountValue() * 100 / itmVo_.getUnitValue();
						BigDecimal DiscountPercent_ = new BigDecimal(dDiscountPercent_);
						if(itmVo_.getMeasureUnit().equals("KLG"))
							DiscountPercent_ = DiscountPercent_.divide(weighableCount_, 2, RoundingMode.DOWN);
						else
							DiscountPercent_ = DiscountPercent_.divide(new BigDecimal(itmVo_.getCount()), 2, RoundingMode.DOWN);
						DiscountPercent_ = DiscountPercent_.setScale(0, BigDecimal.ROUND_HALF_UP);
						/*
						if(itmVo_.getMeasureUnit().equals("KLG"))
							DiscountPercent_ = DiscountPercent_.divide(weighableCount_, 2, RoundingMode.DOWN);
						else
							DiscountPercent_ = DiscountPercent_.divide(new BigDecimal(itmVo_.getCount()), 2, RoundingMode.DOWN);
						*/
						DiscountPercent_ = DiscountPercent_.setScale(0, BigDecimal.ROUND_HALF_UP);					
						
						/*** Descuento *****/
						answer_.append(		"    <IDE>\n");
						answer_.append(		"      <IDE_1>false</IDE_1>\n");
						answer_.append(		"      <IDE_2>"+itmVo_.getDiscountValue()+".00</IDE_2>\n"); // Descuento
						answer_.append(		"      <IDE_3>COP</IDE_3>\n");
						answer_.append(		"      <IDE_6>"+DiscountPercent_+".00</IDE_6>\n"); // % de descuento
						//answer_.append(		"      <IDE_7>"+itmVo_.getUnitValue()+".00</IDE_7>\n"); // Valor unitario
						//answer_.append(		"      <IDE_7>"+entryTotalWithouDiscount_.subtract(BDDiscount_)+".00</IDE_7>\n"); // Valor total entrada
						answer_.append(		"      <IDE_7>"+entryTotal_.setScale(2, BigDecimal.ROUND_DOWN).toString()+"</IDE_7>\n"); // Valor total entrada
						answer_.append(		"      <IDE_8>COP</IDE_8>\n");
						answer_.append(		"      <IDE_10>1</IDE_10>\n"); // Parece el secuencial de un grupo(Empieza x uno)
					    answer_.append(		"    </IDE>\n");
					}    
					
					
					BigDecimal totalTaxValue_ = CarvajalProcessUtils.getTaxValue(entryTotalWithouDiscount_.longValue(),VATTaxRate_);
						//new BigDecimal(itmVo_.get);
					/*
					BigDecimal BDUnitaryTaxValue_ = (BDUnitValue_.subtract(DIANUtils.getBaseValue(unitValueWithouDiscount_,VATTaxRate_,3)));
					
					if(itmVo_.getMeasureUnit().equals("KLG"))
						BDUnitaryTaxValue_ = BDUnitaryTaxValue_.divide(weighableCount_, 2, RoundingMode.DOWN);
					else
						BDUnitaryTaxValue_ = BDUnitaryTaxValue_.divide(new BigDecimal(itmVo_.getCount()), 2, RoundingMode.DOWN);
					*/	
					
					//.setScale(2, BigDecimal.ROUND_DOWN)
					String strVATXML_ = "";
					
					//if(totalTaxValue_.longValue()>0){
						strVATXML_ += 		"    <TII>\n";
						strVATXML_ += 		"      <TII_1>"+totalTaxValue_.toString()+"</TII_1>\n";
						strVATXML_ += 		"      <TII_2>COP</TII_2>\n";
						strVATXML_ += 		"      <TII_3>false</TII_3>\n";
						strVATXML_ += 		"      <IIM>\n";
						strVATXML_ += 		"        <IIM_1>01</IIM_1>\n"; // IVA 
						//BigDecimal BDUnitValue_ = new BigDecimal(unitValue_);
						//strVATXML_ += 		"        <IIM_2>"+DIANUtils.getTaxValue(itmVo_.getUnitValue(),VATTaxRate).toString()+"</IIM_2>\n");
						//strVATXML_ += 		"        <IIM_2>"+(BDUnitValue_.subtract(DIANUtils.getBaseValue(unitValueWithouDiscount_,VATTaxRate_,3)).setScale(2, BigDecimal.ROUND_DOWN)).toString()+"</IIM_2>\n";  // Valor impuesto
						//strVATXML_ += 		"        <IIM_2>"+DIANUtils.getTaxValue(unitValueWithouDiscount_,VATTaxRate_).toString()+"</IIM_2>\n";
						if(itmVo_.getVATTaxRate() == 19){
							//BDVAT19_ = BDVAT19_.add(DIANUtils.getTaxValue(entryTotalWithouDiscount_.longValue(),VATTaxRate_));
							addVATValue(itmVo_.getVATTaxRate(), CarvajalProcessUtils.getTaxValue(entryTotalWithouDiscount_.longValue(),VATTaxRate_));
						}else if(itmVo_.getVATTaxRate() == 5){
							//BDVAT5_ = BDVAT5_.add(DIANUtils.getTaxValue(entryTotalWithouDiscount_.longValue(),VATTaxRate_));
							addVATValue(itmVo_.getVATTaxRate(), CarvajalProcessUtils.getTaxValue(entryTotalWithouDiscount_.longValue(),VATTaxRate_));
						}else if(itmVo_.getVATTaxRate() == 0){
							//BDVAT0_ = BDVAT0_.add(DIANUtils.getTaxValue(entryTotalWithouDiscount_.longValue(),VATTaxRate_));
							addVATValue(itmVo_.getVATTaxRate(), CarvajalProcessUtils.getTaxValue(entryTotalWithouDiscount_.longValue(),VATTaxRate_));
						}	
						
						strVATXML_ += 		"        <IIM_2>"+CarvajalProcessUtils.getTaxValue(entryTotalWithouDiscount_.longValue(),VATTaxRate_).toString()+"</IIM_2>\n"; // Valor Impuesto
						
						
						strVATXML_ += 		"        <IIM_3>COP</IIM_3>\n";
						
						/*
						if(itmVo_.getVATTaxRate() == 19)
							BDBaseVAT19_ = BDBaseVAT19_.add(baseTotal_);
						else if(itmVo_.getVATTaxRate() == 5)
							BDBaseVAT5_ = BDBaseVAT5_.add(baseTotal_);
						else if(itmVo_.getVATTaxRate() == 0)
							BDBaseVAT0_ = BDBaseVAT0_.add(baseTotal_);
						*/
						addVATBaseValue(itmVo_.getVATTaxRate(), baseTotal_);
						
						strVATXML_ += 		"        <IIM_4>"+baseTotal_.toString()+"</IIM_4>\n"; // Valor Base
						strVATXML_ += 		"        <IIM_5>COP</IIM_5>\n";
						

						
						strVATXML_ += 		"        <IIM_6>"+itmVo_.getVATTaxRate()+".00</IIM_6>\n";
						strVATXML_ += 		"      </IIM>\n";
						strVATXML_ += 		"    </TII>\n";		
						//answer_.append(		"    <TII>\n");
					//}
					if(totalTaxValue_.longValue()>0)
						answer_.append(strVATXML_);
					
					BigDecimal totalICUITaxValue_ = CarvajalProcessUtils.getTaxValue(unitValueWithouDiscount_,ICUITaxRate_);
					//new BigDecimal(itmVo_.get);
					if(totalICUITaxValue_.longValue()>0){
						if(totalTaxValue_.longValue() == 0)
							answer_.append(strVATXML_);
						answer_.append(		"    <TII>\n");
						answer_.append(		"      <TII_1>"+totalICUITaxValue_.toString()+"</TII_1>\n");
						answer_.append(		"      <TII_2>COP</TII_2>\n");
						answer_.append(		"      <TII_3>false</TII_3>\n");
						answer_.append(		"      <IIM>\n");
						answer_.append(		"        <IIM_1>35</IIM_1>\n"); // ICUI 
						//BigDecimal BDUnitValue_ = new BigDecimal(unitValue_);
						//answer_.append(		"        <IIM_2>"+DIANUtils.getTaxValue(itmVo_.getUnitValue(),VATTaxRate).toString()+"</IIM_2>\n");
						
						BigDecimal baseICUITotal_ = new BigDecimal(0);
						if(itmVo_.getMeasureUnit().equals("KLG"))
							baseICUITotal_ = CarvajalProcessUtils.getBaseValue(unitValueWithouDiscount_,ICUITaxRate_,5).multiply(weighableCount_);
						else
							baseICUITotal_ = CarvajalProcessUtils.getBaseValue(unitValueWithouDiscount_,ICUITaxRate_,5).multiply(new BigDecimal(itmVo_.getCount()));
						baseICUITotal_ = baseICUITotal_.setScale(2, BigDecimal.ROUND_DOWN);
						
						answer_.append(		"        <IIM_2>"+(BDUnitValue_.subtract(CarvajalProcessUtils.getBaseValue(unitValueWithouDiscount_,ICUITaxRate_,3)).setScale(2, BigDecimal.ROUND_DOWN)).toString()+"</IIM_2>\n");  // Valor impuesto
						
						
						answer_.append(		"        <IIM_3>COP</IIM_3>\n");
						answer_.append(		"        <IIM_4>"+baseICUITotal_.toString()+"</IIM_4>\n"); // Base
						answer_.append(		"        <IIM_5>COP</IIM_5>\n");
						answer_.append(		"        <IIM_6>"+itmVo_.getICUITaxRate()+".00</IIM_6>\n");
						answer_.append(		"        <IIM_11>ICUI</IIM_11>\n");
						answer_.append(		"      </IIM>\n");
						answer_.append(		"    </TII>\n");		
						//answer_.append(		"    <TII>\n");
						BDICUITax_ = BDICUITax_.add(BDUnitValue_.subtract(CarvajalProcessUtils.getBaseValue(unitValueWithouDiscount_,ICUITaxRate_,3)).setScale(2, BigDecimal.ROUND_DOWN));
						BDBaseICUITax_ = BDBaseICUITax_.add(baseICUITotal_);
					}				
					
					answer_.append(		"  </ITE>\n");
					
				}
				
				
			}
			
			/*** Fin - Informaci�n de Itmes *****/
			
			/********************  IVAS ******************/
			//System.out.println("Total IVA 19_->"+BDVAT19_.toString());
			System.out.println("Total IVA 19_->"+getVATValue(19).toString());
			//System.out.println("Total Base IVA 19_->"+BDBaseVAT19_.toString());
			System.out.println("Total Base IVA 19_->"+getVATBaseValue(19).toString());
			
			//System.out.println("Total IVA 5_->"+BDVAT5_.toString());
			System.out.println("Total IVA 5_->"+getVATValue(5).toString());
			//System.out.println("Total Base IVA 5_->"+BDBaseVAT5_.toString());
			System.out.println("Total Base IVA 5_->"+getVATBaseValue(5).toString());

			//System.out.println("Total IVA 0_->"+BDVAT0_.toString());
			System.out.println("Total IVA 0_->"+getVATValue(0).toString());
			//System.out.println("Total Base IVA 0_->"+BDBaseVAT0_.toString());		
			System.out.println("Total Base IVA 0_->"+getVATBaseValue(0).toString());
			
			BigDecimal BDTotalVATValue_ = new BigDecimal(0);
			BDTotalVATValue_ = BDTotalVATValue_.add(getVATValue(19));
			BDTotalVATValue_ = BDTotalVATValue_.add(getVATValue(5));
			
			BigDecimal BDTotalVATBaseValue_ = new BigDecimal(0);
			BDTotalVATBaseValue_ = BDTotalVATBaseValue_.add(getVATBaseValue(19));
			BDTotalVATBaseValue_ = BDTotalVATBaseValue_.add(getVATBaseValue(5));
			BDTotalVATBaseValue_ = BDTotalVATBaseValue_.add(getVATBaseValue(0));

			
			
			System.out.println("Total IVA_->"+BDTotalVATValue_.toString());
			System.out.println("Total Base IVA->"+BDTotalVATBaseValue_.toString());
			
			String strVATTotalValuesXML_ = "";
			strVATTotalValuesXML_ += 		"  <TIM>\n";
			strVATTotalValuesXML_ += 		"    <TIM_1>false</TIM_1>\n";
			strVATTotalValuesXML_ += 		"    <TIM_2>"+BDTotalVATValue_.toString()+"</TIM_2>\n"; // Valor Total del impuuesto
			strVATTotalValuesXML_ += 		"    <TIM_3>COP</TIM_3>\n";
			//Set<Integer> keys_ = theVATTableHash.keySet();
			Set keys_ = theVATTableHash.keySet();
			Iterator iterator_ = keys_.iterator();
			//for(int i=0; i<set_.size();i++){
			int VATTAXArray_[] = new int[keys_.size()];
			int i=0;
			while(iterator_.hasNext()){
			//for (int key : keys_) {	
				//System.out.println(iterator_.next());
				VATTAXArray_[i] = ((Long)iterator_.next()).intValue();
				i++;	
			}
			
			System.out.println(VATTAXArray_.length);
			
			VATTAXArray_ = ArrayUtils.orderArray(VATTAXArray_, ArrayUtils.DESCENDING);
			
			System.out.println("Array Ordenado");
			
			for(int j=0;j<VATTAXArray_.length;j++){
				BigDecimal currVATValue_ = getVATValue(VATTAXArray_[j]);
				if(getVATValue(VATTAXArray_[j]).longValue() > 0){
					strVATTotalValuesXML_ += 		"    <IMP>\n";
					strVATTotalValuesXML_ += 		"      <IMP_1>01</IMP_1>\n";
					strVATTotalValuesXML_ += 		"      <IMP_2>"+getVATBaseValue(VATTAXArray_[j]).toString()+"</IMP_2>\n"; // Valor Base impuesto
					strVATTotalValuesXML_ += 		"      <IMP_3>COP</IMP_3>\n";
					strVATTotalValuesXML_ += 		"      <IMP_4>"+getVATValue(VATTAXArray_[j])+"</IMP_4>\n"; // Valor impuesto
					strVATTotalValuesXML_ += 		"      <IMP_5>COP</IMP_5>\n";
					strVATTotalValuesXML_ += 		"      <IMP_6>"+VATTAXArray_[j]+".00</IMP_6>\n";
					strVATTotalValuesXML_ += 		"    </IMP>\n";
				}				
			}
			
			//if(BDVAT19_.longValue() > 0){
			/*
			if(getVATValue(19).longValue() > 0){
				strVATTotalValuesXML_ += 		"    <IMP>\n";
				strVATTotalValuesXML_ += 		"      <IMP_1>01</IMP_1>\n";
				strVATTotalValuesXML_ += 		"      <IMP_2>"+getVATBaseValue(19).toString()+"</IMP_2>\n"; // Valor Base impuesto
				strVATTotalValuesXML_ += 		"      <IMP_3>COP</IMP_3>\n";
				strVATTotalValuesXML_ += 		"      <IMP_4>"+getVATValue(19)+"</IMP_4>\n"; // Valor impuesto
				strVATTotalValuesXML_ += 		"      <IMP_5>COP</IMP_5>\n";
				strVATTotalValuesXML_ += 		"      <IMP_6>19.00</IMP_6>\n";
				strVATTotalValuesXML_ += 		"    </IMP>\n";
			}	

			//if(BDVAT5_.longValue() > 0){
			if(getVATValue(5).longValue() > 0){
				strVATTotalValuesXML_ += 		"    <IMP>\n";
				strVATTotalValuesXML_ += 		"      <IMP_1>01</IMP_1>\n";
				strVATTotalValuesXML_ += 		"      <IMP_2>"+getVATBaseValue(5).toString()+"</IMP_2>\n"; // Valor Base impuesto
				strVATTotalValuesXML_ += 		"      <IMP_3>COP</IMP_3>\n";
				strVATTotalValuesXML_ += 		"      <IMP_4>"+getVATValue(5)+"</IMP_4>\n"; // Valor impuesto
				strVATTotalValuesXML_ += 		"      <IMP_5>COP</IMP_5>\n";
				strVATTotalValuesXML_ += 		"      <IMP_6>5.00</IMP_6>\n";
				strVATTotalValuesXML_ += 		"    </IMP>\n";
			}
			*/

			
			
			if(BDBaseICUITax_.longValue() > 0){
				strVATTotalValuesXML_ += 		"    <IMP>\n";
				strVATTotalValuesXML_ += 		"      <IMP_1>01</IMP_1>\n";
				//strVATTotalValuesXML_ += 		"      <IMP_2>"+BDBaseVAT0_.toString()+"</IMP_2>\n"; // Valor Base impuesto
				strVATTotalValuesXML_ += 		"      <IMP_2>"+BDBaseICUITax_.toString()+"</IMP_2>\n"; // Valor Base impuesto
				strVATTotalValuesXML_ += 		"      <IMP_3>COP</IMP_3>\n";
				strVATTotalValuesXML_ += 		"      <IMP_4>"+getVATValue(0)+"</IMP_4>\n"; // Valor impuesto
				strVATTotalValuesXML_ += 		"      <IMP_5>COP</IMP_5>\n";
				strVATTotalValuesXML_ += 		"      <IMP_6>0.00</IMP_6>\n";
				strVATTotalValuesXML_ += 		"    </IMP>\n";		
				strVATTotalValuesXML_ += 		"  </TIM>\n";
			}
			
			if(BDICUITax_.longValue() > 0){
				strVATTotalValuesXML_ += 		"  <TIM>\n";
				strVATTotalValuesXML_ += 		"    <TIM_1>false</TIM_1>\n";
				strVATTotalValuesXML_ += 		"    <TIM_2>"+BDICUITax_.toString()+"</TIM_2>\n"; // Valor Total del impuuesto
				strVATTotalValuesXML_ += 		"    <TIM_3>COP</TIM_3>\n";
				strVATTotalValuesXML_ += 		"    <IMP>\n";
				strVATTotalValuesXML_ += 		"      <IMP_1>35</IMP_1>\n";
				strVATTotalValuesXML_ += 		"      <IMP_2>"+BDBaseICUITax_.toString()+"</IMP_2>\n"; // Valor Base impuesto
				strVATTotalValuesXML_ += 		"      <IMP_3>COP</IMP_3>\n";
				strVATTotalValuesXML_ += 		"      <IMP_4>"+BDICUITax_.toString()+"</IMP_4>\n"; // Valor impuesto
				strVATTotalValuesXML_ += 		"      <IMP_5>COP</IMP_5>\n";
				strVATTotalValuesXML_ += 		"      <IMP_6>15.00</IMP_6>\n";
				strVATTotalValuesXML_ += 		"      <IMP_11>ICUI</IMP_11>\n";
				strVATTotalValuesXML_ += 		"    </IMP>\n";	
				strVATTotalValuesXML_ += 		"  </TIM>\n";
			}	

			/******************** FIN IVAS ******************/
			
			/********************  TOTALES ******************/
			BigDecimal BDTotalVATBaseValueWithICUI_ = new BigDecimal(0); // CACS: No tiene la base de cero(0) y se le adiciona la DB del ICUI
			BDTotalVATBaseValueWithICUI_ = BDTotalVATBaseValueWithICUI_.add(getVATBaseValue(19));
			BDTotalVATBaseValueWithICUI_ = BDTotalVATBaseValueWithICUI_.add(getVATBaseValue(5));
			BDTotalVATBaseValueWithICUI_ = BDTotalVATBaseValueWithICUI_.add(BDBaseICUITax_);
			
			BigDecimal BDTotalTrxValue_ = new BigDecimal(0);
			BDTotalTrxValue_ = BDTotalTrxValue_.add(BDTotalVATBaseValue_);
			BDTotalTrxValue_ = BDTotalTrxValue_.add(getVATValue(19));
			BDTotalTrxValue_ = BDTotalTrxValue_.add(getVATValue(5));
			BDTotalTrxValue_ = BDTotalTrxValue_.add(BDICUITax_);
			
			String strTrxTotalValuesXML_ = "";
			strTrxTotalValuesXML_ += 		"  <TOT>\n";
			strTrxTotalValuesXML_ +=		"    <TOT_1>"+BDTotalVATBaseValue_.toString()+"</TOT_1>\n"; // Total de las bases
			strTrxTotalValuesXML_ +=		"    <TOT_2>COP</TOT_2>\n";
			strTrxTotalValuesXML_ +=		"    <TOT_3>"+BDTotalVATBaseValueWithICUI_.toString()+"</TOT_3>\n";
			strTrxTotalValuesXML_ +=		"    <TOT_4>COP</TOT_4>\n";
			strTrxTotalValuesXML_ +=		"    <TOT_5>"+BDTotalTrxValue_+"</TOT_5>\n"; //'VALOR TOTAL'
			strTrxTotalValuesXML_ +=		"    <TOT_6>COP</TOT_6>\n";
			strTrxTotalValuesXML_ +=		"    <TOT_7>"+BDTotalTrxValue_+"</TOT_7>\n"; //'VALOR TOTAL'
			strTrxTotalValuesXML_ +=		"    <TOT_8>COP</TOT_8>\n";
			strTrxTotalValuesXML_ +=		"  </TOT>\n";		
			System.out.println("XML a enviar->"+strTrxTotalValuesXML_);
			
			/********************  FIN - TOTALES ******************/
			
			//System.out.println("XML a enviar->"+strTrxTotalValuesXML_+strVATTotalValuesXML_+answer_.toString());
			strAnswer_ = strTrxTotalValuesXML_+strVATTotalValuesXML_+answer_.toString();
		}catch(Exception e){
			e.printStackTrace();
		}catch(Throwable t){
			t.printStackTrace();
		}	
		return strAnswer_;
	}
	
	public void addVATValue(long pVATTaxRate, BigDecimal pTaxValue){
		if(theVATTableHash == null){
			theVATTableHash = new HashMap();
		}	
		if(theVATTableHash.get(pVATTaxRate) != null){
			BigDecimal tmpBDVAT_ = (BigDecimal)theVATTableHash.get(pVATTaxRate);
			tmpBDVAT_ = tmpBDVAT_.add(pTaxValue);
			theVATTableHash.put(pVATTaxRate, tmpBDVAT_);
		}else{
			theVATTableHash.put(pVATTaxRate, pTaxValue);
		}
	}
	
	public BigDecimal getVATValue(long pVATTaxRate){
		BigDecimal answer_ = new BigDecimal(0);
		if(theVATTableHash != null){
			if(theVATTableHash.get(pVATTaxRate) != null){
				answer_ = (BigDecimal)theVATTableHash.get(pVATTaxRate);
			}
		}	
		return answer_;
	}
	

	public void addVATBaseValue(long pVATTaxRate, BigDecimal pTaxBaseValue){
		if(theVATBasesTableHash == null){
			theVATBasesTableHash = new HashMap();
		}	
		if(theVATBasesTableHash.get(pVATTaxRate) != null){
			BigDecimal tmpBDVATBase_ = (BigDecimal)theVATBasesTableHash.get(pVATTaxRate);
			tmpBDVATBase_ = tmpBDVATBase_.add(pTaxBaseValue);
			theVATBasesTableHash.put(pVATTaxRate, tmpBDVATBase_);
		}else{
			theVATBasesTableHash.put(pVATTaxRate, pTaxBaseValue);
		}
	}
	
	public BigDecimal getVATBaseValue(long pVATTaxRate){
		BigDecimal answer_ = new BigDecimal(0);
		if(theVATBasesTableHash != null){
			if(theVATBasesTableHash.get(pVATTaxRate) != null){
				answer_ = (BigDecimal)theVATBasesTableHash.get(pVATTaxRate);
			}
		}	
		return answer_;
	}		
	
	public static String getCurrentCompany() {
		return currentCompany;
	}
	public static void setCurrentCompany(String currentCompany) {
		ElectronicBillingExecutor.currentCompany = currentCompany;
	}	
	
	/**
	 * @return
	 */
	public boolean isActive() {
		System.out.println("Terminar desde isActive->"+terminar);
		long currTime_ = (new java.util.Date()).getTime();
		long diffTime_ = (currTime_ - theLastTaskDateTime.getTime());
		System.out.println("diffTime:"+diffTime_);
		// La tarea se bloque� o est� detenida
		if (diffTime_>7200000 || diffTime_<0){// 2horas * 60min * 60seg *1000mseg = 7`200.000 
			terminar = true; //CACS: se prende este flag que se detenga s� a�n est� haciendo algo.
		}
			
		return !terminar;
	}
	
	public static boolean insertWSTransactionErrorAnswer(TrxonlineTransactionInDBVO pTrxToInsert, String pDateTime, String pErrorMsg, String pErrorCode, String pIUT, String pFiscalNumber){
		return FacturaElectronicaDBHelper.insertWSTransactionErrorAnswer(pTrxToInsert.getAlmacen()+"", pTrxToInsert.getTerminal()+"",pTrxToInsert.getNumTrx()+"",pDateTime, pErrorMsg, pErrorCode, pIUT, pFiscalNumber);
	}
	 //FacturaElectronicaDBHelper.insertWSTransactionErrorAnswer(pOrder.getStore(), pOrder.getTerminal(),pOrder.getTransaction(),pOrder.getDateTime(), xmlString_.toString(), result_.toString(),strTrxId_); 
	 //insertWSTransactionErrorAnswer(trx_, dateTime_, CarvajalProcessUtils.ID_PRO_MSG_001, CarvajalProcessUtils.ID_PRO_001, strTrxId_);
	 public static boolean insertWSTransactionErrorAnswer(ServiceOrderVo pOrder, String pErrorMsg, String pErrorCode, String pIUT, String pFiscalNumber){
			return FacturaElectronicaDBHelper.insertWSTransactionErrorAnswer(pOrder.getStore(), pOrder.getTerminal(),pOrder.getTransaction(),pOrder.getDateTime(), pErrorMsg, pErrorCode, pIUT, pFiscalNumber);
	}
	
	public String getToReviewStatus(String pCurrentRegisterStatus) {
		String statusPendig_ = "";
		if(pCurrentRegisterStatus.equals(REG_STATUS_TO_SEND_WITHOUT_CONEX_CARVAJAL))
			statusPendig_ = REG_STATUS_TO_REVIEW;
		else
			statusPendig_ = REG_STATUS_TO_REVIEW_2;
		return statusPendig_;
	}
	
	public String getSendedStatus(String pCurrentRegisterStatus) {
		String statusPendig_ = "";
		if(pCurrentRegisterStatus.equals(REG_STATUS_TO_SEND_WITHOUT_CONEX_CARVAJAL))
			statusPendig_ = REG_STATUS_SENDED;
		else if(pCurrentRegisterStatus.equals(REG_STATUS_TO_SEND_WITH_CONEX_CARVAJAL))
			statusPendig_ = REG_STATUS_SENDED_2;
		else
			statusPendig_ = pCurrentRegisterStatus;
		return statusPendig_;
	}
	
/*	public String getThred04SendedStatus(String pCurrentRegisterStatus) {
		String statusSended_ = "";
		if(pCurrentRegisterStatus.equals(REG_STATUS_PENDING))
			statusSended_ = REG_STATUS_SENDED;
		else if(pCurrentRegisterStatus.equals(REG_STATUS_PENDING_2))
			statusSended_ = REG_STATUS_SENDED_2;
		else if(pCurrentRegisterStatus.equals(REG_STATUS_TO_REVIEW))
			statusSended_ = REG_STATUS_SENDED;
		else if(pCurrentRegisterStatus.equals(REG_STATUS_TO_REVIEW_2))
			statusSended_ = REG_STATUS_SENDED_2;	
		else 
			statusSended_ = ERROR_STATUS; 
		return statusSended_;
	}*/

	public String getErrorStatus(String pCurrentRegisterStatus) {
		String statusPendig_ = "";
		if(pCurrentRegisterStatus.equals(REG_STATUS_TO_SEND_WITHOUT_CONEX_CARVAJAL))
			statusPendig_ = REG_STATUS_PENDING;
		else if (pCurrentRegisterStatus.equals(REG_STATUS_TO_SEND_WITH_CONEX_CARVAJAL))
			statusPendig_ = REG_STATUS_PENDING_2;
		else
			statusPendig_ = pCurrentRegisterStatus; 
		return statusPendig_;
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
	
	/*
	private CloseableHttpClient doConexion() throws PuentePagoException {
		//conexion ssl
		CloseableHttpClient httpclient = HttpClients.createDefault();
		if (isSSL) {
			// Trust own CA and all self-signed certs
	        SSLContext sslcontext =null;
			try {
				
				sslcontext = SSLContexts.custom().build();
//				        .loadTrustMaterial(new File(rutaCertificado), "changeit".toCharArray(),
//				                new TrustSelfSignedStrategy())
//				        .build();
				

				
			} catch (KeyManagementException e1) {
				// TODO Bloque catch generado autom�ticamente
				e1.printStackTrace();
				throw new PuentePagoException(e1.getMessage());
			} catch (NoSuchAlgorithmException e1) {
				// TODO Bloque catch generado autom�ticamente
				e1.printStackTrace();
				throw new PuentePagoException(e1.getMessage());
			}
//			catch (KeyStoreException e1) {
//				// TODO Bloque catch generado autom�ticamente
//				e1.printStackTrace();
//				throw new PuentePagoException(e1.getMessage());
//			} catch (CertificateException e1) {
//				// TODO Bloque catch generado autom�ticamente
//				e1.printStackTrace();
//				throw new PuentePagoException(e1.getMessage());
//			} catch (IOException e1) {
//				// TODO Bloque catch generado autom�ticamente
//				e1.printStackTrace();
//				throw new PuentePagoException(e1.getMessage());
//			}
	        // Allow TLSv1.2 protocol only
//	        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
//	                sslcontext,
//	                new String[] {"TLSv1.2" },
////	                new String[] { "SSL" },
//	                null,
//	                SSLConnectionSocketFactory.getDefaultHostnameVerifier());
	        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext);
	        httpclient = HttpClients.custom()
	                .setSSLSocketFactory(sslsf)
	                .build();
		}
		return httpclient;
	}
	*/

	byte[] replaceNegativeValues(byte[] pBytes) {
		for(int i=0;i<pBytes.length;i++) {
			if(pBytes[i]<0) {
				System.out.println(pBytes[i]);
				//pBytes[i] = 32;
			}
		}
		return pBytes;
	}
}	
