/*
 * Created on 09-nov-07
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.grpretail.trxonline.automaticjobs.onlineinventory.comfandi.job;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import org.ramm.jwaf.dbutil.DBUtil;
import org.ramm.jwaf.sql.ConnectionFailedException;
import org.ramm.jwaf.sql.DBAccessException;

import com.asic.ac.utils.SqlUtils;
import com.asic.author.manager.request.PosRequestsHandler;
import com.asic.utils.locator.FailedLocatingPropertiesFileException;
import com.asic.utils.locator.ResourceLocator;
import com.general.utils.StringFormatter;
import com.grpretail.comfandi.business.utils.OffLineUtils;
import com.grpretail.comfandi.procesosautomaticos.facturaelectronica.db.TrxonlineDatabaseHelper;
import com.grpretail.comfandi.procesosautomaticos.facturaelectronica.vos.ItemEntryRegInDBVO;
//import com.grpretail.comfandi.procesosautomaticos.facturaelectronica.vos.ItemEntryXStore;
import com.grpretail.comfandi.procesosautomaticos.facturaelectronica.vos.Period;
import com.grpretail.comfandi.procesosautomaticos.facturaelectronica.vos.StoreXItemEntry;
import com.grpretail.comfandi.procesosautomaticos.facturaelectronica.vos.TransactionInDBVO;
import com.grpretail.comfandi.procesosautomaticos.facturaelectronica.vos.TrxonlineTransactionInDBVO;
import com.grpretail.comfandi.procesosautomaticos.facturaelectronica.vos.TrxonlineUserDataInDBVO;
import com.grpretail.comfandi.trxonline.automaticjobs.onlineinventory.vos.BusinessLogicParam;
import com.grpretail.comfandi.trxonline.automaticjobs.onlineinventory.vos.RegistersVo;
import com.grpretail.trxonline.automaticjobs.facturaelectronica.utils.BusinessLogicParameters;
import com.jscape.ftcl.FTCL;

/**
 * @author Usuario
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class OnlineInventoryFileGeneration{
	//private static String BATCH_FILENAME="C:\\Archivos";
	private static String BATCH_INPUT_DIRECTORY;
	private static String BATCH_OUTPUT_DIRECTORY;
	private static String BATCH_UPLOAD_DIRECTORY;
	private static String BATCH_UPLOAD_SERVER_IP;
	private static String BATCH_UPLOAD_SERVER_PORT;
	private static String BATCH_UPLOAD_SERVER_USER;
	private static String BATCH_UPLOAD_SERVER_PASSWORD;
	private static String BATCH_SEPARATOR;
	private static int BATCH_TIMEOUT;
	private static String BATCH_PREFIX_PARTIAL_FILE;
	private static String BATCH_NIT_COMFANDI;
	private static String BATCH_FINAL_REG_MESSAGE;
	//private static String BATCH_EAN_SIMPLE;
	//private static int MAX_REGS_TO_RETRIEVE_FROM_DB = 5;
	private static int MAX_REGS_TO_RETRIEVE_FROM_DB = 500;
	private static int MIN_REGS_TO_PROCCESS = 9000;
	
	// CACS: Estados de los registros en la tabla
	private static String REGS_STATUS_BLANK		 = "";  // Sin estado
	public static String REGS_STATUS_PROCESSING = "P"; // Procesando
	private static String REGS_STATUS_GENERATED  = "G"; // Se gener� el archivo
	private static String REGS_STATUS_SENDED     = "E"; // Se envi� en un archivo a Simple
	private static String REGS_STATUS_OLD    	 = "O"; // Registros viejos de antes de iniciar el m�dulo
	private static String REGS_STATUS_TO_SEND  	 = "A"; // Registros viejos de antes de iniciar el m�dulo
	
	//private static String BATCH_PREFIX_TOTAL_FILE;
	private static String BATCH_EXTENSION_FILE;
	private boolean terminar=false;
	private String status="I";
	public static final String PROCCESS_PARAMS_FILENAME	= "OnlineInventoryParams";
	protected static Hashtable hsTenders;
	protected static Hashtable hsStores;
	private Hashtable theHSStores; // Tabla hash donde se van agrupando las cantides vendidas para cada �tem o producto

	private static ResourceBundle bundle_;
			
	//ConciliatorExecutor(){
	static{		
		//setDaemon(true);
		try{	
			bundle_ = ResourceLocator.get(PROCCESS_PARAMS_FILENAME);
		}catch (FailedLocatingPropertiesFileException ex) {
			System.out.println("excepcion en constructor->");
			System.out.println(ex);
		}
		try{ 	
			BATCH_INPUT_DIRECTORY=bundle_.getString("CON_INPUT_DIRECTORY");
			BATCH_OUTPUT_DIRECTORY=bundle_.getString("CON_OUTPUT_DIRECTORY");
			BATCH_UPLOAD_DIRECTORY=bundle_.getString("UPLOAD_DIRECTORY");
			BATCH_UPLOAD_SERVER_IP=bundle_.getString("UPLOAD_SERVER_IP");
			BATCH_UPLOAD_SERVER_PORT=bundle_.getString("UPLOAD_SERVER_PORT");
			BATCH_UPLOAD_SERVER_USER=bundle_.getString("UPLOAD_SERVER_USER");
			BATCH_UPLOAD_SERVER_PASSWORD=bundle_.getString("UPLOAD_SERVER_PASSWORD");
			BATCH_SEPARATOR=bundle_.getString("CON_SEPARATOR");
			BATCH_TIMEOUT=new Integer(bundle_.getString("CON_TIMEOUT")).intValue();
			BATCH_PREFIX_PARTIAL_FILE=bundle_.getString("PREFIX_PARTIAL_FILE");
			BATCH_EXTENSION_FILE=bundle_.getString("EXTENSION_FILE");
			BATCH_NIT_COMFANDI=bundle_.getString("NIT_COMFANDI");
			BATCH_FINAL_REG_MESSAGE=bundle_.getString("FINAL_REG_MESSAGE");
			//BATCH_NIT_SIMPLE=bundle_.getString("NIT_SIMPLE");
			//BATCH_EAN_SIMPLE=bundle_.getString("EAN_SIMPLE");
			//BATCH_PREFIX_TOTAL_FILE=bundle_.getString("PREFIX_TOTAL_FILE");
			//System.out.println("bundle_.getString->"+bundle_.getString("CON_FILES_OUT_EXTENSION"));
			//System.out.println("bundle_.getString->"+bundle_.getString("CON_OUTPUT_DIRECTORY"));
			//System.out.println("bundle_.getString->"+bundle_.getString("CON_INPUT_DIRECTORY"));
		}catch(Exception e){
			//System.out.println("exception en el getString->");
			e.printStackTrace();
		}
			

	}

	/*
	public static void loadTenders(){
		try{
			List lst_;
			hsTenders = new Hashtable();
			String select_ =
							ParametrosWas.DB_COLUM_T_TENDER_TV+" code_sma,"+
							ParametrosWas.DB_COLUM_T_TENDER_CODE+" code_db";

			String from_ =
							ParametrosWas.DB_TABLE_TENDERS;
			String where_ = ParametrosWas.DB_COLUM_T_TENDER_TV+" is not null";						

			System.out.println("Setencia a ejectutar->"+select_+from_+where_);
			lst_ =
					DBUtil.select(
						select_,						
						from_,
						where_,		
						TenderVo.class);
			System.out.println("No. Reg->"+lst_.size());
			Iterator it_=lst_.iterator();
			//TrxPagoServiciosDBVO trx_=null;
			boolean exit_=false;
			TenderVo tender_;
			while(it_.hasNext()){
				tender_=(TenderVo)it_.next();
				hsTenders.put(new Long(tender_.getCode_sma()),new Long(tender_.getCode_db()));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void loadStores(){
		try{
			List lst_;
			hsStores = new Hashtable();
			String select_ =
							ParametrosWas.DB_COLUM_ST_STORE_COMF+" code_ssma,"+
							ParametrosWas.DB_COLUM_ST_STORE_EMCALI+" code_semc";

			String from_ =
							ParametrosWas.DB_TABLE_STORES;
			String where_ = ParametrosWas.DB_COLUM_ST_STORE_COMF+" is not null";						

			System.out.println("Setencia a ejectutar->"+select_+from_+where_);
			lst_ =
					DBUtil.select(
						select_,						
						from_,
						where_,		
						StoreVo.class);
			System.out.println("No. Reg->"+lst_.size());
			Iterator it_=lst_.iterator();
			//TrxPagoServiciosDBVO trx_=null;
			boolean exit_=false;
			StoreVo store_;
			while(it_.hasNext()){
				store_=(StoreVo)it_.next();
				hsStores.put(new Long(store_.getCode_ssma()),new Long(store_.getCode_semc()));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	*/
	public static Long getTender(Long pTendId) {

		if ((Long)hsTenders.get(pTendId)!=null)
			return (Long)hsTenders.get(pTendId);
		return new Long(1);		
					
	}	

	public static Long getStore(Long pStoreId) {

		if ((Long)hsStores.get(pStoreId)!=null)
			return (Long)hsStores.get(pStoreId);
		return new Long(1);		
					
	}
	
	// Generaci'on del archivo parcial
	public boolean genOnlineInvetoryFile(java.util.Date pProcessDate, Period pPeriodToProcess /*int pFileConsecutive*/)
	//public static void procesarLogNP()
	 {
		 List lstTransactions_;
		 FileOutputStream os_ = null;
		 BufferedWriter bw_=null;
		 //GeneralFileInt tLogFile=null;
		 PrintStream aSysLogStream=null;
		 Hashtable hs_=new Hashtable();
		 boolean log_=false;
		 boolean sendTrx_=false;
		 boolean pending_=true;
		 boolean firstTrx_=true;
		 String fechaHoraInicial_=null;
		 String fechaHoraFinal_=null;
		 Calendar  calFechaHoraProceso_ =Calendar.getInstance();
		 //Calendar calFechaHoraFinal_ = new GregorianCalendar();		
		 int numLineas_=0;
		 //loadTenders();
		 //loadStores();
		 try {
			if (!getPeriod(pProcessDate, pPeriodToProcess.getNumber())){
			//if (!OffLineUtils.getPeriod(pProcessDate, pPeriodToProcess.getNumber())){
				String fileName_=BATCH_PREFIX_PARTIAL_FILE + //"_"+BATCH_NIT_COMFANDI+"_"+BATCH_NIT_SIMPLE + "_"+
								//calFechaHoraProceso_.get(Calendar.YEAR)+ // A�o Pago
								StringFormatter.align(""+(calFechaHoraProceso_.get(Calendar.MONTH)+1),2,'0',0,2)+ // Mes								
								StringFormatter.align(""+(calFechaHoraProceso_.get(Calendar.DAY_OF_MONTH)),2,'0',0,2)+ //D'ia
								StringFormatter.align(pPeriodToProcess.getNumber()+"",2,'0',0,2) +
								"."+BATCH_EXTENSION_FILE;
				boolean readyToStart_= false;
				int lastRegisters_ = -1;
				int retries_ = 0;
				int registersInProcess = 0;
				while(!readyToStart_){ // Cuando retries_ llega a 6 es porque ya pas� media hora y no hay cambios en el n�mero de
					                                  // registros bloqueados. 
					registersInProcess = getRegistersInProcess();
					//OffLineUtils.getRegistersInProcess();
					if (registersInProcess == 0){
						System.out.println("No hay registros con estado 'Procesando'. No parece haber un proceso en ejecuci�n.");
						readyToStart_ = true;
						retries_ = 0;
					}else{
						if (lastRegisters_ == registersInProcess){
							retries_ ++;
							// Se est� revisando cada 5 minutos y si los registros bloqueados siguen siendo los mismos es muy probable que no haya
							// ning�n proceso en ejecuci�n. Si ese es el escenario usamos la varibles retries_ para sacarlo y continuar con el proceso.
							if (retries_>=5)
								readyToStart_ = true;
						}else // Si el n�mero de registros bloqueados cambia quiere decir que hay un proceso corriendo y reiniciamos retries_
							retries_ = 0;
						lastRegisters_ = registersInProcess;
						try{
							//Thread.sleep(300000); // 5 minutos.
							Thread.sleep(1000); // 5 minutos.
						}catch(Exception e){}					
					}
				}
				if (readyToStart_ && retries_>0){ // Est� listo para procesar pero a�n hay registros en procesamiento.
					if (cleanProcessingRegs(registersInProcess)>0) // Los registros que a�n est�n en estado de procesamiento no se les pudo limpiar el estado '' (blanco)
						readyToStart_ = false; // Por lo tanto no se puede iniciar el proceso.
				}
				if (readyToStart_){ // Pas� las validaciones de los registros en estado de procesamientos ('P).
					                // Vamos a revisar si hay registros con estado 'G' si eso pasa no se puede continar el proceso
					readyToStart_ = false;
					lastRegisters_ = -1;
					retries_ = 0;
					while(!readyToStart_ && retries_<5){ // Cuando retries_ llega a 6 es porque ya pas� media hora y no hay cambios en el n�mero de
		                // registros bloqueados. 
						int registersWasSended_ = getRegistersWasGenerated();
						//int registersWasSended_ = OffLineUtils.getRegistersWasGenerated();
						if (registersWasSended_ == 0){
							System.out.println("No hay registros con estado 'Generado'. No parece haber un proceso en ejecuci�n.");
							readyToStart_ = true;
							retries_ = 0;
						}else{
							if (lastRegisters_ == registersWasSended_){
								retries_ ++;
								// Se est� revisando cada 5 minutos por si los los registros generados hacen parte de un proceso en ejecuci�n.
								//if (retries_>=5)
									//readyToStart_ = true;
							}else // Si el n�mero de registros generados cambia quiere decir que hay un proceso corriendo y reiniciamos retries_
								retries_ = 0;
							lastRegisters_ = registersWasSended_;
							try{
								//Thread.sleep(300000); // 5 minutos.
								Thread.sleep(1000); // 5 minutos.
							}catch(Exception e){}					
						}
					}
					if (!readyToStart_){
						System.out.println("Hay registros que se encuentran en estado 'G' por m�s de media hora. Esto bloquea el proceso. Se deber�a enviar correo.");
					}
				}	
				if (readyToStart_){
					boolean thereAreRegsInDB_ = false;
					boolean exit_ = false;
					boolean firstLine_ = true;
					int totalItemEntriesRegs_ = 0;
					long totalAmout_ = 0;
					String pwdate_ = "";					
					do{
						int dbCounter_ = 0;
						//long periodTime_ = 2*365*24*60*60;//*1000; // Iniciamos con dos a�os
						long periodTime_ = 24*60*60;//*1000; // Un d�a
						String where2_ = " TABLA_GPR";
						int trxNumToProccess_ = -1;
						int lastTrxNumToProccess_ = -1;
						boolean firtsTime_ = true;
						while(!exit_ && dbCounter_<5){ // Vamos a buscar el n�mero adecuando de transacciones que vamos a procesar 
							// No pueden ser inferior al promedio x hora 3000 ni mayor al doble 6000.
							System.out.println(periodTime_);
							if(periodTime_ < 0)
								periodTime_ = periodTime_ * (-1);
							java.util.Date firstDate_ = new java.util.Date();
							System.out.println(firstDate_.getTime());
							firstDate_.setTime(firstDate_.getTime()-(periodTime_*1000));
							System.out.println(firstDate_);
							where2_ =
								" a."+BusinessLogicParameters.DB_COLUM_TOL_HEADER_TRX_TYPE+"=0 AND " + // Solo se toman las trxs de venta(tipo cero)
								" a."+BusinessLogicParameters.DB_COLUM_TOL_INSERT_DATE_TIME+"<= TIMESTAMP('"+(pProcessDate.getYear()+1900)+"-"+StringFormatter.align(""+(pProcessDate.getMonth()+1),2,'0',0,2)+"-"+StringFormatter.align(""+pProcessDate.getDate(),2,'0',0,2)+" "+StringFormatter.align(pPeriodToProcess.getTime().getHour()+"",2,'0',0,2)+":"+StringFormatter.align(pPeriodToProcess.getTime().getMinute()+"",2,'0',0,2)+":00.00') AND "+
								" a."+BusinessLogicParameters.DB_COLUM_TOL_INSERT_DATE_TIME+">= TIMESTAMP('"+(firstDate_.getYear()+1900)+"-"+StringFormatter.align(""+(firstDate_.getMonth()+1),2,'0',0,2)+"-"+StringFormatter.align(""+firstDate_.getDate(),2,'0',0,2)+" "+StringFormatter.align(firstDate_.getHours()+"",2,'0',0,2)+":"+StringFormatter.align(firstDate_.getMinutes()+"",2,'0',0,2)+":00.00') AND "+
								
								" a."+BusinessLogicParameters.DB_COLUM_TOL_HEADER_ONLINE_INVENTORY_STATUS+"='" + REGS_STATUS_TO_SEND+"'";
								//" AND DAY > '2023-11-27'";
								//" FETCH FIRST "+BusinessLogicParameters.MAX_REGS_TO_RETRIEVE_FROM_DB+" ROWS ONLY";
								//" (a."+BusinessLogicParameters.DB_COLUM_TOL_HEADER_ONLINE_INVENTORY_STATUS+" is null OR (" +
								//" a."+BusinessLogicParameters.DB_COLUM_TOL_HEADER_ONLINE_INVENTORY_STATUS+"<>'"+REGS_STATUS_SENDED+"' AND a."+BusinessLogicParameters.DB_COLUM_TOL_HEADER_ONLINE_INVENTORY_STATUS+"<>'"+REGS_STATUS_GENERATED+"' AND "+
								//" a."+BusinessLogicParameters.DB_COLUM_TOL_HEADER_ONLINE_INVENTORY_STATUS+"<>'"+REGS_STATUS_PROCESSING+"' AND " +
								//" a."+BusinessLogicParameters.DB_COLUM_TOL_HEADER_ONLINE_INVENTORY_STATUS+"<>'"+REGS_STATUS_OLD+"')) ";
								//" ORDER BY a."+BusinessLogicParameters.DB_COLUM_TOL_STORE+" ASC ";
							
							//where_ += " AND (a.STORE = 535 OR a.STORE = 515) AND DAY = '2022-07-18' ";
							//where_ += " AND DAY = '2022-07-18' ";
							//where_ += " AND a.STORE = 514 AND DAY = '2022-07-18' ";
							lastTrxNumToProccess_ = trxNumToProccess_; 
							trxNumToProccess_ = getRegistersNumberToProcess(where2_);
							System.out.println("trxNumToProccess_->"+trxNumToProccess_);
							//int trxNumToProccess_ = OffLineUtils.getRegistersNumberToProcess(where_);
							if(firtsTime_){
								System.out.println("Es la primera vez");
								if(trxNumToProccess_ != -1){
									if(trxNumToProccess_<MIN_REGS_TO_PROCCESS){
										periodTime_ = 2*365*24*60*60;//*1000; // Iniciamos nuevamente con dos a�os
										trxNumToProccess_ = MIN_REGS_TO_PROCCESS + 1;
									}
									firtsTime_ = false;
								}else
									System.out.println("trxNumToProccess_ = -1");
							}
							
							if(trxNumToProccess_ != -1 && trxNumToProccess_<MIN_REGS_TO_PROCCESS){
							//if(trxNumToProccess_ != -1 && trxNumToProccess_<2000){
								exit_ = true;
							}else{
								if(trxNumToProccess_ == -1) // Error en DB
									dbCounter_++;
								else{
									if(periodTime_<=(7*24*60*60)){ // 7 d�as
										System.out.println("Ajustando periodTime_->"+periodTime_);
										periodTime_ = periodTime_ - (2*60*60);
										/*
										if(lastTrxNumToProccess_ != -1 && trxNumToProccess_ != lastTrxNumToProccess_){
											System.out.println("Bajando el perIodo para tomar mAs trxs");
											periodTime_ = 2*60*60; // 2 horas
										}else{
											System.out.println("No cambiO el lastTrxNumToProccess_");
											periodTime_ = periodTime_/2;
										}
										*/
									}else
										periodTime_ = periodTime_/2;
								}	
							}	

						}
						if(exit_){ // Encontr� el n�mero adecuado de registros a procesar
							if(trxNumToProccess_ > 0){
								exit_ = false;
								int updatedRegsNumber_ = updateStatusToProccessingTransactions(where2_);								
								if(trxNumToProccess_ == updatedRegsNumber_){ // El n�mero de trxs que se quieren procesar corresponde al n�mero de  registros actualizados. Es lo que se esperaba
									List lstItemEntries_ = getTransactionItemEntries();
									/*
									String select_ =
										" a."+BusinessLogicParameters.DB_COLUM_TOL_STORE+" almacen,"+
										" a."+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL+" terminal,"+
										" a."+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER+" numTrx,"+
										" a."+BusinessLogicParameters.DB_COLUM_TOL_DATE_TIME+" fechaHora";
									String from_ =
													//BusinessLogicParameters.DB_TABLE_SERVICE_PAYMENT_TRX;
													BusinessLogicParameters.DB_TABLE_TRX_ONLINE_HEADER +" a";*/
									/*String where_ =
										" a."+BusinessLogicParameters.DB_COLUM_TOL_HEADER_TRX_TYPE+"=0 AND " + // Solo se toman las trxs de venta(tipo cero)
										" a."+BusinessLogicParameters.DB_COLUM_TOL_DATE_TIME+"<= TIMESTAMP('"+(pProcessDate.getYear()+1900)+"-"+StringFormatter.align(""+(pProcessDate.getMonth()+1),2,'0',0,2)+"-"+StringFormatter.align(""+pProcessDate.getDate(),2,'0',0,2)+" "+StringFormatter.align(pPeriodToProcess.getTime().getHour()+"",2,'0',0,2)+":"+StringFormatter.align(pPeriodToProcess.getTime().getMinute()+"",2,'0',0,2)+":00.00') AND "+
										" (a."+BusinessLogicParameters.DB_COLUM_TOL_HEADER_ONLINE_INVENTORY_STATUS+" is null OR (" +
										" a."+BusinessLogicParameters.DB_COLUM_TOL_HEADER_ONLINE_INVENTORY_STATUS+"<>'"+REGS_STATUS_SENDED+"' AND a."+BusinessLogicParameters.DB_COLUM_TOL_HEADER_ONLINE_INVENTORY_STATUS+"<>'"+REGS_STATUS_GENERATED+"' AND "+
										" a."+BusinessLogicParameters.DB_COLUM_TOL_HEADER_ONLINE_INVENTORY_STATUS+"<>'"+REGS_STATUS_PROCESSING+"' AND " +
										" a."+BusinessLogicParameters.DB_COLUM_TOL_HEADER_ONLINE_INVENTORY_STATUS+"<>'"+REGS_STATUS_OLD+"'))" +
				
										" FETCH FIRST "+MAX_REGS_TO_RETRIEVE_FROM_DB+" ROWS ONLY";*/					
								
										// CACS: Posibles estados {E: Enviado, P: Procesado, '': No procesado}
									/*
									System.out.println("Setencia a ejectutar->"+select_+from_+where_);
									
									lstTransactions_ =
											DBUtil.select(
												select_,						
												from_,
												where_,		
												TrxonlineTransactionInDBVO.class);*/						
									//OffLineUtils.getOnlineInventoryTrxs();
									System.out.println("No. item entries a procesar->"+lstItemEntries_.size());
									thereAreRegsInDB_ = lstItemEntries_.size()==MAX_REGS_TO_RETRIEVE_FROM_DB;
									String lineToInsert_="";
									Iterator itItemEntries_=lstItemEntries_.iterator();
									TrxonlineTransactionInDBVO trx_=null;
									//boolean exit_=false;
									//List taxesList_ = new ArrayList();
									if (itItemEntries_.hasNext()){ // Se crea el archivo solo si hay registros para insertar en 'el.
										totalItemEntriesRegs_ = lstItemEntries_.size();
										if (firstLine_){ // El archivo solo se crea cuando se va a insertar la primera l�nea.						
											os_= new FileOutputStream(BATCH_INPUT_DIRECTORY+BATCH_SEPARATOR+fileName_);
											bw_=new BufferedWriter(new OutputStreamWriter(os_));
										}
										//taxesList_ = TrxonlineDatabaseHelper.getTaxesByItemUserDatas();
									}else{
										if (totalItemEntriesRegs_ == 0){ // No hay ni un solo registro para procesar
											System.out.println("No hay registros para procesar");
											insertWorkRegister(pProcessDate, pPeriodToProcess.getNumber(), trxNumToProccess_, totalItemEntriesRegs_);
											exit_=true;
										}	
									}
									// Campo para guardar la fecha, hora y periodo en que se ejecut� el proceso
									java.util.Date curTime_ = new java.util.Date();
									System.out.println(curTime_.getYear()+1900);
									System.out.println(curTime_.getMonth()+1);
									System.out.println(curTime_.getDate());
									System.out.println(curTime_.getHours());
									System.out.println(curTime_.getMinutes());
									System.out.println(curTime_.getSeconds());
									while(itItemEntries_.hasNext()&&!exit_){
										// CACS: Se itera sobre las transacciones pero hay que ir a traer los prouductos.
										ItemEntryRegInDBVO currItemEntry_ = (ItemEntryRegInDBVO)itItemEntries_.next();
										TrxonlineUserDataInDBVO trxVO_ = new TrxonlineUserDataInDBVO();
										trxVO_.setAlmacen(currItemEntry_.getAlmacen());
										trxVO_.setTerminal(currItemEntry_.getTerminal());
										trxVO_.setNumTrx(currItemEntry_.getNumTrx());
										trxVO_.setFechaHora(currItemEntry_.getFechaHora());
										trxVO_.setOffsetx(currItemEntry_.getOffsetx());
										String strItemCode_ = currItemEntry_.getItemCode()+"";
										if(strItemCode_!=null && strItemCode_.length()%2==1)
											strItemCode_ = "0"+strItemCode_;
										trxVO_.setData2(strItemCode_);
										//List taxesList_ = TrxonlineDatabaseHelper.getTaxesByItemUserDatas(trxVO_);
										long vatValue_ = 0;
										long consumValue_ = 0;
										/*if(taxesList_.size()>0){ // Existen user datas de impuesto para los items.
											TrxonlineUserDataInDBVO trxUserDataVo_ = getTaxUserData(trxVO_, taxesList_);
											if(trxUserDataVo_ != null){*/
										
												int VATRate_ = 0;
												try{
													//VATRate_  = Integer.parseInt(trxUserDataVo_.getData6());	
													VATRate_  = new Long(currItemEntry_.getVATRate()).intValue();
												}catch(Exception e){
													e.printStackTrace();
												}
												if(VATRate_>0){
													try{
														//vatValue_ = Long.parseLong(trxUserDataVo_.getData10());
														vatValue_ = currItemEntry_.getTaxValue();
														//if(trxUserDataVo_.getData10()!=null && trxUserDataVo_.getData11().equals("01")){// Es anulaci�n
														if (currItemEntry_.getIndicat31().equals("2") || currItemEntry_.getIndicat31().equals("8")){ //{8: Anulaci�n 2:Devoluci�n} 
															System.out.println("Es una anulaciOn de la entrada. Valor del IVA negativo");
															vatValue_ = vatValue_ * (-1);
														}else
															System.out.println("No es una anulaciOn");
													}catch(Exception e){
														e.printStackTrace();
													}
												}else{
													System.out.println("Tarifa IVA cero");
													int consumTaxRate_ = 0;
													try{
														//consumTaxRate_  = Integer.parseInt(trxUserDataVo_.getData7());	
														consumTaxRate_  = new Long(currItemEntry_.getConsumptionTaxRate()).intValue();
													}catch(Exception e){
														e.printStackTrace();
													}
													if(consumTaxRate_>0){
														try{
															//consumValue_ = Long.parseLong(trxUserDataVo_.getData10());
															consumValue_ = currItemEntry_.getTaxValue();
															//if(trxUserDataVo_.getData10()!=null && trxUserDataVo_.getData11().equals("01")){// Es anulaci�n
															if (currItemEntry_.getIndicat31().equals("2") || currItemEntry_.getIndicat31().equals("8")){ //{8: Anulaci�n 2:Devoluci�n}
																System.out.println("Es una anulaciOn de la entrada. Valor del IVA negativo");
																consumValue_ = consumValue_ * (-1);
															}else
																System.out.println("No es una anulaciOn");
														}catch(Exception e){
															e.printStackTrace();
														}
													}else
														System.out.println("Tarifa impoconsumo cero");
												}
											//}else
												//System.out.println("No hay string de taxes. No deberIa pasar");	
										//}else
											//System.out.println("No hay string de taxes. No deberIa pasar");
											
										currItemEntry_.setVATValue(vatValue_);
										currItemEntry_.setConsumptionTaxValue(consumValue_);
										
										/*
										System.out.println("Item code->"+currItemEntry_.getItemCode());
										System.out.println("Item code->"+currItemEntry_.getQtyOrWgt());
										System.out.println("Indicat 17->"+currItemEntry_.getIndicat17());
										System.out.println("Indicat 211->"+currItemEntry_.getIndicat211());
										System.out.println("Indicat 31->"+currItemEntry_.getIndicat31());
										System.out.println("Indicat 46->"+currItemEntry_.getIndicat46());
										*/
										addItemToList(currItemEntry_);
										
										/*
										if (firstLine_){ // CACS Se inserta el encabezado.
											
											pwdate_ = 								
											StringFormatter.align(""+(curTime_.getYear()+1900),4,'0',0,4)+ // Fecha actual - A�o
											StringFormatter.align(""+(curTime_.getMonth()+1),2,'0',0,2)+ // Fecha actual - Mes
											StringFormatter.align(""+(curTime_.getDate()),2,'0',0,2)+ // Fecha actual - D�a
											StringFormatter.align(""+(curTime_.getHours()),2,'0',0,2)+ // Fecha actual - Hora
											StringFormatter.align(""+(curTime_.getMinutes()),2,'0',0,2); // Fecha actual - Minuto
											
											String period_ = pPeriodToProcess.getNumber()+"";
											if (pPeriodToProcess.getNumber()<0 || pPeriodToProcess.getNumber()>99)
												period_ = "99";
											pwdate_ += StringFormatter.align(period_,2,'0',0,2); // Per�odo
											
											//StringFormatter.align(BATCH_NIT_COMFANDI,10,'0',0,10)+ // A�o Pago
											lineToInsert_ += "4";
											lineToInsert_ += StringFormatter.align(""+(curTime_.getYear()+1900),4,'0',0,4); // Fecha actual - A�o
											lineToInsert_ += StringFormatter.align(""+(curTime_.getMonth()+1),2,'0',0,2); // Fecha actual - Mes
											lineToInsert_ += StringFormatter.align(""+(curTime_.getDate()),2,'0',0,2); // Fecha actual - D�a
											
											lineToInsert_ += "0000000000";
											
											lineToInsert_ += StringFormatter.align("",21,' ',0,21);
	
											bw_.write(lineToInsert_);
											bw_.newLine();
											bw_.flush();
											firstLine_ = false;
										}*/
										/*
										trx_=(TransactionInDBVO)it_.next();
										totalAmout_ += trx_.getMonto().longValue();				
										String fechaHoraTrx_=((trx_.getFecha()!=null && trx_.getHora() != null)?
										""+(trx_.getFecha().getYear()+1900)+""+
										StringFormatter.align((trx_.getFecha().getMonth()+1)+"",2,'0',0,2)+
										StringFormatter.align(trx_.getFecha().getDate()+"",2,'0',0,2)+
										StringFormatter.align(""+trx_.getHora().getHours()+"",2,'0',0,2)+
										StringFormatter.align(trx_.getHora().getMinutes()+"",2,'0',0,2)+
										StringFormatter.align(trx_.getHora().getSeconds()+"",2,'0',0,2):"00000000000000");
										lineToInsert_=	"02"+//Tipo de registro: fijo '02'  
														StringFormatter.align(""+trx_.getNumReferencia(),10,'0',0,10)+ // No referencia
														
														StringFormatter.align(fechaHoraTrx_.substring(0,4),4,'0',0,4)+ // A�o Pago
														StringFormatter.align(fechaHoraTrx_.substring(4,6),2,'0',0,2)+ // Mes Pago
														StringFormatter.align(fechaHoraTrx_.substring(6,8),2,'0',0,2)+ // D�a Pago
														
														StringFormatter.align(fechaHoraTrx_.substring(8),6,'0',0,6)+ // Hora
														
														StringFormatter.align(""+(trx_.getMonto().longValue()),10,'0',0,10)+ // Valor del recaudo
														
														StringFormatter.align(""+trx_.getAlmacen(),6,'0',0,6)+ // Almac�n
														StringFormatter.align(""+trx_.getTerminal(),6,'0',0,6)+ // Terminal
														StringFormatter.align(""+trx_.getNumTrx(),10,'0',0,10)+ // Transacci�n
														StringFormatter.align("",42,' ',0,42)+ // Relleno
														StringFormatter.align(trx_.getPeriodo(),6,'0',0,6); // Periodo
														//StringFormatter.align("",36,' ',0,36); // Relleno
										bw_.write(lineToInsert_);
										bw_.newLine();
										bw_.flush();					
										if (!marcarRegistro(trx_, fechaHoraTrx_, pwdate_))
											return false;
										*/
									}
									System.out.println("Saliendo ... Registros en tabla hash->"+theHSStores.size());
									//theHSItems.elements();
									long totalRegsInFile_ = 0;
									for (Enumeration enum1_ = theHSStores.elements() ; enum1_.hasMoreElements() ;) {
										/*	
											java.util.Date curTime_ = new java.util.Date();
											System.out.println(curTime_.getYear()+1900);
											System.out.println(curTime_.getMonth()+1);
											System.out.println(curTime_.getDate());
											System.out.println(curTime_.getHours());
											System.out.println(curTime_.getMinutes());
											System.out.println(curTime_.getSeconds());
										*/
										StoreXItemEntry currObj1_ = (StoreXItemEntry)enum1_.nextElement();
										
										//if (firstLine_){ // CACS Se inserta el encabezado.
											
											pwdate_ = 								
											StringFormatter.align(""+(curTime_.getYear()+1900),4,'0',0,4)+ // Fecha actual - A�o
											StringFormatter.align(""+(curTime_.getMonth()+1),2,'0',0,2)+ // Fecha actual - Mes
											StringFormatter.align(""+(curTime_.getDate()),2,'0',0,2)+ // Fecha actual - D�a
											StringFormatter.align(""+(curTime_.getHours()),2,'0',0,2)+ // Fecha actual - Hora
											StringFormatter.align(""+(curTime_.getMinutes()),2,'0',0,2); // Fecha actual - Minuto
											
											String period_ = pPeriodToProcess.getNumber()+"";
											if (pPeriodToProcess.getNumber()<0 || pPeriodToProcess.getNumber()>99)
												period_ = "99";
											pwdate_ += StringFormatter.align(period_,2,'0',0,2); // Per�odo
											
											/*
											lineToInsert_=	"I999"+//Tipo de registro: fijo '01'  
											lineToInsert_ += StringFormatter.align(""+(curTime_.getMonth()+1),2,'0',0,2); // Fecha actual - Mes
											lineToInsert_ += StringFormatter.align(""+(curTime_.getDate()),2,'0',0,2); // Fecha actual - D�a // Fecha actual - Mes
											*/
											
											//StringFormatter.align(BATCH_NIT_COMFANDI,10,'0',0,10)+ // A�o Pago
											lineToInsert_ = "4000";
											lineToInsert_ += StringFormatter.align(""+(curTime_.getYear()+1900),4,'0',0,4); // Fecha actual - A�o
											lineToInsert_ += StringFormatter.align(""+(curTime_.getMonth()+1),2,'0',0,2); // Fecha actual - Mes
											lineToInsert_ += StringFormatter.align(""+(curTime_.getDate()),2,'0',0,2); // Fecha actual - D�a
											
											//lineToInsert_ += "0000000000";
											lineToInsert_ += StringFormatter.align(""+currObj1_.getStore(),10,'0',0,10);
											
											lineToInsert_ += StringFormatter.align("",21,' ',0,21);
											/*
											StringFormatter.align(""+(curTime_.getHours()),2,'0',0,2)+ // Fecha actual - Hora
											StringFormatter.align(""+(curTime_.getMinutes()),2,'0',0,2)+ // Fecha actual - Minuto
											StringFormatter.align(""+(curTime_.getSeconds()),2,'0',0,2)+ // Fecha actual - Segundo					
											StringFormatter.align("",74,' ',0,74); // Relleno
											*/
											bw_.write(lineToInsert_);
											bw_.newLine();
											bw_.flush();
											firstLine_ = false;
										//}									
										
										
										//if(currObj1_.getItemCode()==114)
											//System.out.println("Este es Item que buscamos");
										Hashtable currItemsHS_ = currObj1_.getHSItems();
										for (Enumeration enum2_ = currItemsHS_.elements() ; enum2_.hasMoreElements() ;) {
											ItemEntryRegInDBVO currItem_ = (ItemEntryRegInDBVO)enum2_.nextElement();
	
											System.out.println("Item Code: "+currItem_.getItemCode()+" Cantidad:"+currItem_.getQtyOrWgt()+" Total vendido:"+currItem_.getXprice());
											lineToInsert_ = "1";
											//lineToInsert_ += StringFormatter.align(""+currItem_.getItemCode(),12,'0',0,12);
											lineToInsert_ += StringFormatter.align(""+currItem_.getItemCode(),7,'0',0,7);
											//lineToInsert_ += StringFormatter.align(""+currItem_.getItemCode(),7,'0',0,7);
											lineToInsert_ += StringFormatter.align(""+(curTime_.getMonth()+1),2,'0',0,2);
											lineToInsert_ += StringFormatter.align(""+(curTime_.getDate()),2,'0',0,2);
											long soldValue_ = currItem_.getXprice();
											long quantity_ = currItem_.getQtyOrWgt();
											if(soldValue_ != 0 || quantity_ != 0){
												String sign_ = "+";
												if(quantity_<0){
													sign_ = "-";
													quantity_ = quantity_ * (-1);
												}//else
													//System.out.println("Cantidad negativa");
												if(soldValue_<0)
													soldValue_ = soldValue_ * (-1);
												// CACS: Los pesables se multiplican x 1000 por alguna raz�n
												if(currItem_.getIndicat17().equals("0")) // No pesable
													quantity_ = quantity_ * 1000;
												
												lineToInsert_ += StringFormatter.align(""+quantity_,9,'0',0,9);
												lineToInsert_ += sign_;
												lineToInsert_ += StringFormatter.align(""+soldValue_,9,'0',0,9);
												//String impoConsumo_ = "0";
												long impoConsumo_ = currItem_.getConsumptionTaxValue();
												if(impoConsumo_<0){
													impoConsumo_ = impoConsumo_ * (-1);
												}												
												lineToInsert_ += StringFormatter.align(impoConsumo_+"",7,'0',0,7);
												//String VATValue_ = "0";
												long VATValue_ = currItem_.getVATValue();
												if(VATValue_<0){
													VATValue_ = VATValue_ * (-1);
												}
												
												lineToInsert_ += StringFormatter.align(VATValue_+"",7,'0',0,7);
												//lineToInsert_ += StringFormatter.align(currItem_.getAlmacen()+"",4,'0',0,4);
												/*
												String id_ = ((String)row_.elementAt(0)).trim();
												BusinessLogicParam param_ = new BusinessLogicParam(
													id_,
													(String)row_.elementAt(1)
												);
												aHashBusinessLogicParams.put(id_,param_);
												*/
												bw_.write(lineToInsert_);
												bw_.newLine();
												bw_.flush();
												totalRegsInFile_++;
											}else
												System.out.println("Valores en cero. LInea no fue insertada");
										}
										//System.out.println("Saliendo de la lista interna");
	
										// CACS: Se inserta el registro de trailer.
										lineToInsert_ = StringFormatter.align("",43,'9',0,43);// 43 9s
	
										bw_.write(lineToInsert_);
										bw_.newLine();
										bw_.flush();									
										
										lineToInsert_=	BATCH_FINAL_REG_MESSAGE+//TOTAL #REG GRABADOS   
										//StringFormatter.align(""+theHSItems.size(),9,'0',0,9)+ // N�mero total de registros
										StringFormatter.align(""+totalRegsInFile_,9,'0',0,9)+ // N�mero total de registros
										StringFormatter.align("0",14,'0',0,14); // Relleno
										
										bw_.write(lineToInsert_);
										bw_.newLine();
										bw_.flush();
						
										totalRegsInFile_ = 0;
										
									}
									os_.close();
									bw_.close();//Se cierra el archivo
									if (!exit_ && !thereAreRegsInDB_){ // El archivo se gener� con los datos satisfactoriamente.
										/*
										// CACS: Se inserta el registro de trailer.
										lineToInsert_ = StringFormatter.align("",43,'9',0,43);// 43 9s
	
										bw_.write(lineToInsert_);
										bw_.newLine();
										bw_.flush();									
										
										lineToInsert_=	BATCH_FINAL_REG_MESSAGE+//TOTAL #REG GRABADOS   
										//StringFormatter.align(""+theHSItems.size(),9,'0',0,9)+ // N�mero total de registros
										StringFormatter.align(""+totalRegsInFile_,9,'0',0,9)+ // N�mero total de registros
										StringFormatter.align("0",14,'0',0,14); // Relleno
										
										bw_.write(lineToInsert_);
										bw_.newLine();
										bw_.flush();
						
										os_.close();
										bw_.close();//Se cierra el archivo
										*/
										//int test_ = 999'999.999;
										//if (marcarRegistrosGeneradosInItemEntriesTable(totalItemEntriesRegs_, pwdate_)>0){ //Si los registros se marcaron como generados, el siguiente paso es enviar el archivo.
										System.out.println("Fecha hora de proceso incluyendo perIodo->"+pwdate_);
										if (marcarRegistrosGenerados(trxNumToProccess_, pwdate_)>0){ //Si los registros se marcaron como generados, el siguiente paso es enviar el archivo.
											//if (enviarArchivo(fileName_)){
												enviarArchivo(fileName_);
												System.out.println("El archivo fue enviado. Se procede a marcar los registros como enviados");
												int counter_ = 0;
												boolean confirmationIsOk_ = false;
												// CACS: Si los registros no se confirman bloqueamos el proceso hasta que lo haga bien
												// o se quede bloqueado para evitar errores por envio de datos duplicados.
												// en caso de detenci�n de la tarea habr�a que revisar manualmente qu� pasa con los registros
												// que quedaron en estado G.
												while(counter_<1440 && !confirmationIsOk_){ // Se espera hasta 24 horas para poder hacer esta tarea
													int result_ = confirmarRegistros(trxNumToProccess_);  
													if (result_>=0){ // Pas� la confirmaci�n en la DB.
														if (result_>0){ // Los registros actualizados son los correctos.
															confirmationIsOk_ = true;
															//counter_ = 0;
														}/*else{ // Los registros actualizados no fueron los correctos
															//return false;
															counter_ ++;
															try{
																Thread.sleep(60000);
															}catch(Exception e){}
														}*/	
													}else{
														counter_++;
														try{
															Thread.sleep(60000);
														}catch(Exception e){}										
													}	
												}
												if (confirmationIsOk_){
													System.out.println("Todo el proceso fue satisfactorio");
													insertWorkRegister(pProcessDate, pPeriodToProcess.getNumber(), trxNumToProccess_, totalItemEntriesRegs_);
													System.out.println("Todo el proceso termin� satisfactoriamente.");
													try{
														File file_ = new File(BATCH_INPUT_DIRECTORY+BATCH_SEPARATOR+fileName_);
														int counter2_=0;
														Boolean exit2_=false;
														while(counter2_<10 && !exit2_){
															try{
																if (file_.renameTo(new File(BATCH_OUTPUT_DIRECTORY+BATCH_SEPARATOR+fileName_))){
																	exit2_=true;
																}else{
																	counter2_++;
																	Thread.sleep(5000);
																}
															}catch(Exception e){
																e.printStackTrace();
																Thread.sleep(5000);
															}catch (Throwable t){
																t.printStackTrace();
																Thread.sleep(5000);
															}
														}
														if (counter2_>=10) // Salio del bucle por contador y no porque moviera el archivo 
															               // al directorio de salida o de archivos ya enviados
															System.out.println("Archivo no pudo ser movido al directorio de salida.");	
													}catch(Exception  e){e.printStackTrace();}catch(Throwable t){t.printStackTrace();}	
													
													/*if (counter_>=10){// El archivo no se pudo mover hay que reversar los registros.
														if(!reversarRegistros(calFechaHoraProceso_)){
															System.out.println("Error fatal se marcaron registros en la base de datos que no fueron colocados correctamente en el archivo");
																
														}	
													}//else*/
													return true; // Se retorna true porque el archivo fue enviado y los registros quedaron con el estado 'E',
																// no imporata si el archivo no fue movido satisfactoriamente.
												}else // Los registros no pudieron ser confirmados.
													exit_ = true;
											//}else{ // CACS: Si el envio del archivo fall� debemos desmarcar los registros que se marcaron con anticipaci�n
												// CACS: S� los registros no se pueden desmarcar, bloqueamos el proceso hasta que lo haga bien
												// o se quede bloqueado para evitar errores por envio de datos duplicados.
												// en caso de detenci�n de la tarea habr�a que revisar manualmente qu� pasa con los registros
												// que quedaron en estado G.
												
												// CACS: 14 de Marzo de 2016. Se quit� para poder enviar el archivo manualmente y no desmarcar
												// los registros y as� poder pasarlos a estado E de forma manual.
												
												/*
												int counter_ = 0;
												boolean desmarcarOk_ = false;
												while (counter_<1440 && !desmarcarOk_){
													if (!(desmarcarRegistrosGenerados(totalRegs_)>0)){ // Ponemos a dormir el proceso para hacer un reintento.
														counter_++;
														try{
															Thread.sleep(60000);
														}catch(Exception e){}
													}else
														desmarcarOk_ = true;	
												}
												*/	
											//}
											
												
										}else
											System.out.println("Error marcando registros como generados");
									
										//}else
											//System.out.println("Error marcando registros como generados en tabla item entries");
										
									}else{
										if (exit_) // CACS: Se aborta el proceso
											return true;
										else
											System.out.println("Pueden haber m�s registros en la DB para procesar");
									}
								}else{
									System.out.println("NUmero incorrecto de registros actualizados. Puede haber otro proceso corriendo en paralelo.");
									System.out.println("Se detiene proceso");
								}
							
							}else{
								System.out.println("No hay registros para procesar");
								insertWorkRegister(pProcessDate, pPeriodToProcess.getNumber(), trxNumToProccess_, totalItemEntriesRegs_);
								return true;
							}
						}else
							System.out.println("No enctrontrO el nUmero apropiado de registros a procesar. No deberIa pasar");
						
					}while(thereAreRegsInDB_ && !exit_);
					System.out.println("Saliendo ...");
					// System.out.println("Numero total de cargos PES->"+counter_);
					// System.out.println("Numero total de reintegros PES->"+counter2_);
				}else{
					System.out.println("No pas� las validaciones para la generaci�n del archivo");
				}
			}else{
				System.out.println("Per�odo en cuesti�n ya fue procesado");
				return true;
			}	
		 }catch (Exception e){
			 System.out.println("Excepci�n encontrada en el proceso:" + e);
			 e.printStackTrace();	
		 } catch (Throwable t){
			 t.printStackTrace();	
		 } finally{
			 if (bw_!=null){
				 try {
					 bw_.close();
				 } catch (IOException e1) {
					 // TODO Auto-generated catch block
					 e1.printStackTrace();
					 System.exit(1);
				 }
			 }
			if (os_!=null){
				try {
					os_.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					System.exit(1);
				}
			}			 
			
			 System.out.println("Terminando...");					
		 }
		 System.out.println("Saliendo....");
		 return false;

	 }	

	public TrxonlineUserDataInDBVO getTaxUserData(TrxonlineUserDataInDBVO pTrxToFindData, List pTaxesList){
		System.out.println("Desde getTaxUserData(List pTaxesList)");
		System.out.println("trxUserDataVo_.getAlmacen()->"+pTrxToFindData.getAlmacen());
		System.out.println("trxUserDataVo_.getTerminal()->"+pTrxToFindData.getTerminal());
		System.out.println("trxUserDataVo_.getNumTrx()->"+pTrxToFindData.getNumTrx());
		System.out.println("trxUserDataVo_.getFechaHora()->"+pTrxToFindData.getFechaHora());		
		System.out.println("pTrxToFindData.getOffsetx()->"+pTrxToFindData.getOffsetx());
		System.out.println("COdigo Item->"+pTrxToFindData.getData2());
		
		TrxonlineUserDataInDBVO answer_ = null;
		for(int i=0;i<pTaxesList.size();i++){
			TrxonlineUserDataInDBVO trxUserDataVo_ = (TrxonlineUserDataInDBVO)pTaxesList.get(i);
			if( trxUserDataVo_.getAlmacen().longValue() == pTrxToFindData.getAlmacen().longValue() && 
				trxUserDataVo_.getTerminal().longValue() == pTrxToFindData.getTerminal().longValue() &&
				trxUserDataVo_.getNumTrx().longValue() == pTrxToFindData.getNumTrx().longValue()	&&
				trxUserDataVo_.getFechaHora().toString().equals(pTrxToFindData.getFechaHora().toString()) &&
				trxUserDataVo_.getData2().equals(pTrxToFindData.getData2())
			  ){      
				System.out.println("Es la trx que se necesita");
				System.out.println("trxUserDataVo_.getAlmacen()->"+trxUserDataVo_.getAlmacen());
				System.out.println("trxUserDataVo_.getTerminal()->"+trxUserDataVo_.getTerminal());
				System.out.println("trxUserDataVo_.getNumTrx()->"+trxUserDataVo_.getNumTrx());
				System.out.println("trxUserDataVo_.getFechaHora()->"+trxUserDataVo_.getFechaHora());
				System.out.println("trxUserDataVo_.getOffsetx()->"+trxUserDataVo_.getOffsetx());
				System.out.println("COdigo Item->"+trxUserDataVo_.getData2());
				if(trxUserDataVo_.getOffsetx()< pTrxToFindData.getOffsetx()){
					System.out.println("Reemplazando la respuesta ...");
					answer_ = trxUserDataVo_;
				}	
			}else{
				/*
				System.out.println("No es al trx que se necesita");
				System.out.println("trxUserDataVo_.getAlmacen()->"+trxUserDataVo_.getAlmacen());
				System.out.println("trxUserDataVo_.getTerminal()->"+trxUserDataVo_.getTerminal());
				System.out.println("trxUserDataVo_.getNumTrx()->"+trxUserDataVo_.getNumTrx());
				System.out.println("trxUserDataVo_.getFechaHora()->"+trxUserDataVo_.getFechaHora());
				System.out.println("trxUserDataVo_.getOffsetx()->"+trxUserDataVo_.getOffsetx());
				*/
				//if(answer_ == null)
					//System.out.println("Se mantiene el barrido");
				//else{
				if(answer_ != null){
					System.out.println("Ya tiene un user data. Se puede sacar- i->"+i);
					i = pTaxesList.size();
				}	
			}
		}
		return answer_;
			/*
			if(pTaxesList)
			int VATRate_ = 0;
			try{
				VATRate_  = Integer.parseInt(trxUserDataVo_.getData6());	
			}catch(Exception e){
				e.printStackTrace();
			}
			if(VATRate_>0){
				try{
					vatValue_ = Long.parseLong(trxUserDataVo_.getData10());
					if(trxUserDataVo_.getData10()!=null && trxUserDataVo_.getData10().equals("01")){// Es anulaci�n
						System.out.println("Es una anulaciOn de la entrada. Valor del IVA negativo");
						vatValue_ = vatValue_ * (-1);
					}else
						System.out.println("No es una anulaciOn");
				}catch(Exception e){
					e.printStackTrace();
				}
			}else{
				System.out.println("Tarifa IVA cero");
				int consumTaxRate_ = 0;
				try{
					consumTaxRate_  = Integer.parseInt(trxUserDataVo_.getData7());	
				}catch(Exception e){
					e.printStackTrace();
				}
				if(consumTaxRate_>0){
					try{
						consumValue_ = Long.parseLong(trxUserDataVo_.getData10());
						if(trxUserDataVo_.getData10()!=null && trxUserDataVo_.getData10().equals("01")){// Es anulaci�n
							System.out.println("Es una anulaciOn de la entrada. Valor del IVA negativo");
							consumValue_ = consumValue_ * (-1);
						}else
							System.out.println("No es una anulaciOn");
					}catch(Exception e){
						e.printStackTrace();
					}
				}else
					System.out.println("Tarifa impoconsumo cero");
			}			
		}
		*/
	}
	
	/*
	// Generaci'on del archivo diario.
	public static boolean genTotalFile()
	//public static void procesarLogNP()
	 {
		 List lst_;
		 FileOutputStream os_ = null;
		 BufferedWriter bw_=null;
		 //GeneralFileInt tLogFile=null;
		 PrintStream aSysLogStream=null;
		 Hashtable hs_=new Hashtable();
		 boolean log_=false;
		 boolean sendTrx_=false;
		 boolean pending_=true;
		 boolean firstTrx_=true;
		 String fechaHoraInicial_=null;
		 String fechaHoraFinal_=null;
		 Calendar  calFechaHoraProceso_ =Calendar.getInstance();
		 //Calendar calFechaHoraFinal_ = new GregorianCalendar();
		 loadStores();		
		 int numLineas_=0;
		 try {
			String fileName_=BATCH_PREFIX_TOTAL_FILE+calFechaHoraProceso_.get(Calendar.YEAR)+ // A�o Pago
							StringFormatter.align(""+(calFechaHoraProceso_.get(Calendar.MONTH)+1),2,'0',0,2)+ // Mes								
							StringFormatter.align(""+(calFechaHoraProceso_.get(Calendar.DAY_OF_MONTH)),2,'0',0,2)+ //D'ia
							StringFormatter.align(""+(calFechaHoraProceso_.get(Calendar.HOUR_OF_DAY)),2,'0',0,2)+ // Hora							
							StringFormatter.align(""+(calFechaHoraProceso_.get(Calendar.MINUTE)),2,'0',0,2)+ //Minuto
							StringFormatter.align(""+(calFechaHoraProceso_.get(Calendar.SECOND)),2,'0',0,2)+ //Segundo
							"."+BATCH_EXTENSION_FILE;
			
			String select_ =

			//select codcco almacen, count(*), sum(MONTOR) numalmacenes  from emcalref where estado='W' group by CODCCO
							ParametrosWas.DB_COLUM_SP_STORE+" almacen,"+
							"COUNT(*) numpagos,"+
							"SUM("+ParametrosWas.DB_COLUM_SP_BILL_AMOUNT+") montopagos"
							//ParametrosWas.DB_COLUM_SP_BILL_AMOUNT+" monto,"+
							//ParametrosWas.DB_COLUM_SP_TRX_TIMESTAMP+" fechaHoraPago,"+
							//ParametrosWas.DB_COLUM_SP_TENDER_TYPE+" tenderType";
							;
			String from_ =
							ParametrosWas.DB_TABLE_SERVICE_PAYMENT_TRX;
			String where_ =
							ParametrosWas.DB_COLUM_SP_STATUS+"='W' GROUP BY "+ParametrosWas.DB_COLUM_SP_STORE;						
			System.out.println("Setencia a ejectutar->"+select_+from_+where_);
			lst_ =
					DBUtil.select(
						select_,						
						from_,
						where_,		
						TotalPagos.class);
			System.out.println("No. Reg->"+lst_.size());
			String lineToInsert_="";
			Iterator it_=lst_.iterator();
			TotalPagos reg_=null;
			boolean exit_=false;
			if (it_.hasNext()){ // Se crea el archivo solo si hay registros para insertar en 'el.
				os_= new FileOutputStream(BATCH_INPUTDIRECTORY+BATCH_SEPARATOR+fileName_);
				bw_=new BufferedWriter(new OutputStreamWriter(os_));
			}else
				exit_=true;
			while(it_.hasNext()&&!exit_){
				reg_=(TotalPagos)it_.next();
				String previousDayDate_ = DateFormatter.getDate("-1");
				lineToInsert_=	"CO"+//Tipo de trx: Conciliaci�n
								"1500"+ //Banco
								StringFormatter.align(""+getStore(reg_.getAlmacen()),6,'0',0,6)+
								StringFormatter.align(""+reg_.getMontopagos()+"00",13,'0',0,13)+ //Valor informado: Para los archivos parciales va en 0
								StringFormatter.align(""+reg_.getNumpagos(),6,'0',0,6)+ //N�mero de cupones: Para los archivos parciales va en 0
								StringFormatter.align("0",10,'0',0,10)+ //Num cup'on o referencia.
								//StringFormatter.align(""+(reg_.getMontopagos().longValue()*100L),13,'0',0,13)+
								StringFormatter.align("0",13,'0',0,13)+
								//calFechaHoraProceso_.get(Calendar.YEAR)+ // A�o Pago
								previousDayDate_.substring(0,4)+ // A�o Pago
								//StringFormatter.align(""+(calFechaHoraProceso_.get(Calendar.MONTH)+1),2,'0',0,2)+ // Mes
								previousDayDate_.substring(4,6)+ // Mes Pago								
								//StringFormatter.align(""+(calFechaHoraProceso_.get(Calendar.DAY_OF_MONTH)),2,'0',0,2)+ //D'ia
								previousDayDate_.substring(6,8)+ // D�a Pago
								//calFechaHoraProceso_.get(Calendar.YEAR)+ // A�o Pago
								previousDayDate_.substring(0,4)+ // A�o Pago
								//StringFormatter.align(""+(calFechaHoraProceso_.get(Calendar.MONTH)+1),2,'0',0,2)+ // Mes
								previousDayDate_.substring(4,6)+ // Mes Pago							
								//StringFormatter.align(""+(calFechaHoraProceso_.get(Calendar.DAY_OF_MONTH)),2,'0',0,2)+ //D'ia
								previousDayDate_.substring(6,8)+ // D�a Pago
								StringFormatter.align("",6,' ',0,6)+ // Hora
								"00"+ // Medio de Pago
								"N"+ // Flag de procesamiento
								StringFormatter.align("0",4,'0',0,4)+ // C�digo de error
								StringFormatter.align("",37,' ',0,37); // Descripci�n Error						
				bw_.write(lineToInsert_);
				bw_.newLine();
				bw_.flush();					
			}
			if (!exit_){ // El archivo se gener� con los datos satisfactoriamente.
				os_.close();
				bw_.close();//Se cierra el archivo
				if ( confirmarRegistrosTotales(calFechaHoraProceso_)){ //Si los registros se pueden confirmar se debe mover el archivo a la carpeta de salida.
					if (registrarTrx(calFechaHoraProceso_)){
						System.out.println("Los registros de totales fueron confirmados. Se puede enviar el archivo al directorio de salida para que sea enviado v'ia FTP");
						File file_ = new File(BATCH_INPUTDIRECTORY+BATCH_SEPARATOR+fileName_);
						int counter_=0;
						//exit_=false;
						while(counter_<10 && !exit_){
							try{
								if (file_.renameTo(new File(BATCH_OUTPUTDIRECTORY+BATCH_SEPARATOR+fileName_))){
									exit_=true;
								}else{
									counter_++;
									Thread.sleep(5000);
								}
							}catch(Exception e){
								e.printStackTrace();
								Thread.sleep(5000);
							}catch (Throwable t){
								t.printStackTrace();
								Thread.sleep(5000);
							}
						}
						if (counter_>=10){// Hay que reversar los registros.
							if(!reversarRegistrosTotales(calFechaHoraProceso_)| !borrarTrx(calFechaHoraProceso_)){// 
								//int counter2_=0;
								//while(counter2_==10){
								//	if(file_.delete())
								//		counter2_=10;
								//	else
								//		counter2_++;										
								//}
								//if (counter2_==10)
									System.out.println("Error fatal se marcaron registros en la base de datos que no fueron colocados correctamente en el archivo");
								
							}	
						}else{
							//registrarTrx(calFechaHoraProceso_);
							return true;
						}	
					}else{ // No se pudo insertar el reg en el DB
						if(!reversarRegistrosTotales(calFechaHoraProceso_)&& borrarTrx(calFechaHoraProceso_)){// Se reversan los registro confirmados para intentar una conciliaci'on nuevamente.
							System.out.println("Quedaron registros confirmados que no deber�an quedar as�");
						}	
						
					}
				}					
			}else{ // No hab�an datos para generar archivos.
				registrarTrx(calFechaHoraProceso_);
				return true;
			}
				
			
			// System.out.println("Numero total de cargos PES->"+counter_);
			// System.out.println("Numero total de reintegros PES->"+counter2_);
		 }catch (Exception e){
			 System.out.println("Excepci�n encontrada en el proceso:" + e);
			 e.printStackTrace();	
		 } catch (Throwable t){
			 t.printStackTrace();	
		 } finally{
			 if (bw_!=null){
				 try {
					 bw_.close();
				 } catch (IOException e1) {
					 // TODO Auto-generated catch block
					 e1.printStackTrace();
					 System.exit(1);
				 }
			 }
			if (os_!=null){
				try {
					os_.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					System.exit(1);
				}
			}			 
			
			 System.out.println("Terminando...");					
		 }
		 System.out.println("Saliendo....");
		 return false;

	 }
	*/
	

	public static boolean reversarRegistros(Calendar pCalFechaHoraProceso){
		/*
		try{
			boolean pending_=true;
			int counter_=0;
			while(counter_<3){
					String sentence_=
					" UPDATE " +
							ParametrosWas.DB_TABLE_SERVICE_PAYMENT_TRX +
					" SET "+
							ParametrosWas.DB_TABLE_SERVICE_PAYMENT_TRX + "." + ParametrosWas.DB_COLUM_BP_REG_STATUS + "=''";							
					String filtro_ = 
									ParametrosWas.DB_TABLE_SERVICE_PAYMENT_TRX + "." + ParametrosWas.DB_COLUM_BP_REG_STATUS + "='" +
									ComfandiPosTransaction.getBusinessLogicParam(BusinessLogicParam.ID_WORKED_REGISTER_CODE).getValue()+"' AND " +
									ParametrosWas.DB_TABLE_SERVICE_PAYMENT_TRX + "." + ParametrosWas.DB_COLUM_BP_P_WORK_DATE + "=" +
									"TIMESTAMP('"+
									pCalFechaHoraProceso.get(Calendar.YEAR)+
									StringFormatter.align(""+(pCalFechaHoraProceso.get(Calendar.MONTH)+1),2,'0',0,2)+
									StringFormatter.align(""+pCalFechaHoraProceso.get(Calendar.DAY_OF_MONTH),2,'0',0,2)+
									StringFormatter.align(""+pCalFechaHoraProceso.get(Calendar.HOUR_OF_DAY),2,'0',0,2)+
									StringFormatter.align(""+pCalFechaHoraProceso.get(Calendar.MINUTE),2,'0',0,2)+
									StringFormatter.align(""+pCalFechaHoraProceso.get(Calendar.SECOND),2,'0',0,2)+
									//pCalFechaHoraProceso.get(Calendar.MILLISECOND)+
									"') "									;
					System.out.println("Sentencia->"+sentence_ +" WHERE "+filtro_);										
					int dbAnswer_ = SqlUtils.actualizaTabla(
									sentence_ +" WHERE "+filtro_,
									PosRequestsHandler.getCnxClassName(ParametrosWas.ID_DATABASE_MILENIUM),
									PosRequestsHandler.getCnxUrl(ParametrosWas.ID_DATABASE_MILENIUM),
									PosRequestsHandler.getCnxUserID(ParametrosWas.ID_DATABASE_MILENIUM),
									PosRequestsHandler.getCnxPassword(ParametrosWas.ID_DATABASE_MILENIUM)
									);
					if(dbAnswer_==-1 || dbAnswer_==0){ // Hubo problemas con la base de datos o el registro no existe.
						System.out.println("Hubo problemas con la base de datos o el registro no existe. Num Reg->"+dbAnswer_);						
					}else{ // El pago fue confirmado satisfactoriamente.
						filtro_=ParametrosWas.DB_TABLE_SERVICE_PAYMENT_TRX + "." + ParametrosWas.DB_COLUM_BP_REG_STATUS + "='' AND "+
						ParametrosWas.DB_TABLE_SERVICE_PAYMENT_TRX + "." + ParametrosWas.DB_COLUM_BP_P_WORK_DATE + "=" +
						"TIMESTAMP('"+
						pCalFechaHoraProceso.get(Calendar.YEAR)+
						StringFormatter.align(""+(pCalFechaHoraProceso.get(Calendar.MONTH)+1),2,'0',0,2)+
						StringFormatter.align(""+pCalFechaHoraProceso.get(Calendar.DAY_OF_MONTH),2,'0',0,2)+
						StringFormatter.align(""+pCalFechaHoraProceso.get(Calendar.HOUR_OF_DAY),2,'0',0,2)+
						StringFormatter.align(""+pCalFechaHoraProceso.get(Calendar.MINUTE),2,'0',0,2)+
						StringFormatter.align(""+pCalFechaHoraProceso.get(Calendar.SECOND),2,'0',0,2)+
						"') ";
						if (quedoTrx(filtro_)){
							return true;					
						}
					}
					counter_++;
			}			
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}*/
		return false;
	}

	/*
	public static boolean reversarRegistrosTotales(Calendar pCalFechaHoraProceso){
		try{
			boolean pending_=true;
			int counter_=0;
			while(counter_<10){
					String sentence_=
					" UPDATE " +
							ParametrosWas.DB_TABLE_SERVICE_PAYMENT_TRX +
					" SET "+
							ParametrosWas.DB_TABLE_SERVICE_PAYMENT_TRX + "." + ParametrosWas.DB_COLUM_BP_REG_STATUS + "='"+
							ComfandiPosTransaction.getBusinessLogicParam(BusinessLogicParam.ID_WORKED_REGISTER_CODE).getValue()+"'";							
					String filtro_ = 
									ParametrosWas.DB_TABLE_SERVICE_PAYMENT_TRX + "." + ParametrosWas.DB_COLUM_BP_REG_STATUS + "='" +
									ComfandiPosTransaction.getBusinessLogicParam(BusinessLogicParam.ID_SENDED_REGISTER_CODE).getValue()+"' AND " +
									ParametrosWas.DB_TABLE_SERVICE_PAYMENT_TRX + "." + ParametrosWas.DB_COLUM_BP_T_WORK_DATE + "=" +
									"TIMESTAMP('"+
									pCalFechaHoraProceso.get(Calendar.YEAR)+
									StringFormatter.align(""+(pCalFechaHoraProceso.get(Calendar.MONTH)+1),2,'0',0,2)+
									StringFormatter.align(""+pCalFechaHoraProceso.get(Calendar.DAY_OF_MONTH),2,'0',0,2)+
									StringFormatter.align(""+pCalFechaHoraProceso.get(Calendar.HOUR_OF_DAY),2,'0',0,2)+
									StringFormatter.align(""+pCalFechaHoraProceso.get(Calendar.MINUTE),2,'0',0,2)+
									StringFormatter.align(""+pCalFechaHoraProceso.get(Calendar.SECOND),2,'0',0,2)+
									//pCalFechaHoraProceso.get(Calendar.MILLISECOND)+
									"') "									;
					System.out.println("Sentencia->"+sentence_ +" WHERE "+filtro_);										
					int dbAnswer_ = SqlUtils.actualizaTabla(
									sentence_ +" WHERE "+filtro_,
									PosRequestsHandler.getCnxClassName(ParametrosWas.ID_DATABASE_MILENIUM),
									PosRequestsHandler.getCnxUrl(ParametrosWas.ID_DATABASE_MILENIUM),
									PosRequestsHandler.getCnxUserID(ParametrosWas.ID_DATABASE_MILENIUM),
									PosRequestsHandler.getCnxPassword(ParametrosWas.ID_DATABASE_MILENIUM)
									);
					if(dbAnswer_==-1 || dbAnswer_==0){ // Hubo problemas con la base de datos o el registro no existe.
						System.out.println("Hubo problemas con la base de datos o el registro no existe. Num Reg->"+dbAnswer_);
						Thread.sleep(60000);						
					}else{ // El pago fue confirmado satisfactoriamente.
						filtro_=ParametrosWas.DB_TABLE_SERVICE_PAYMENT_TRX + "." + ParametrosWas.DB_COLUM_BP_REG_STATUS + "='" +
						ComfandiPosTransaction.getBusinessLogicParam(BusinessLogicParam.ID_WORKED_REGISTER_CODE).getValue()+"' AND " +
						ParametrosWas.DB_TABLE_SERVICE_PAYMENT_TRX + "." + ParametrosWas.DB_COLUM_BP_T_WORK_DATE + "=" +
						"TIMESTAMP('"+
						pCalFechaHoraProceso.get(Calendar.YEAR)+
						StringFormatter.align(""+(pCalFechaHoraProceso.get(Calendar.MONTH)+1),2,'0',0,2)+
						StringFormatter.align(""+pCalFechaHoraProceso.get(Calendar.DAY_OF_MONTH),2,'0',0,2)+
						StringFormatter.align(""+pCalFechaHoraProceso.get(Calendar.HOUR_OF_DAY),2,'0',0,2)+
						StringFormatter.align(""+pCalFechaHoraProceso.get(Calendar.MINUTE),2,'0',0,2)+
						StringFormatter.align(""+pCalFechaHoraProceso.get(Calendar.SECOND),2,'0',0,2)+
						"') ";
						if (quedoTrx(filtro_)){
							return true;					
						}
					}
					counter_++;
			}			
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return false;
	}
	*/

	public static boolean enviarArchivo(String pFileName){
	  // create new FTCL instance
		
		boolean connectedFlag_ = false; // Flag que indica si la conexi�n fue exitosa.
		boolean remotePathFlag_ = false; // Flag que indica si se pudo situar en la ruta correcta para subir los archivos.
		boolean localPathFlag_ = false; // Flag que indica si se pudo situar en la ruta local desde donde se van a subir los archivos.
		boolean uploadFileFlag_ = false; // Flag que indica si el archivo subi� correctamente al servidor
		int counter_=0;
		boolean exit_= false;
		while (counter_<60 && !uploadFileFlag_){ // Dura 4320 minutos, aproximadamente 3 d�as. Si al cabo de 3 d�as logra enviar el archivo aborta. 
			counter_++;
			System.out.println("Envio de archivo No."+counter_);
			FTCL ftcl = new FTCL();
			// CACS: Conexi�n
			try{
				StringBuffer buffer = new StringBuffer();
				// 200.74.147.88
				//190.25.224.90
		   		//buffer.append("set hostname \"ftp.myserver.com\"\r\n");
		   		//buffer.append("set hostname \"190.25.224.90\"\r\n");
				buffer.append("set hostname \""+BATCH_UPLOAD_SERVER_IP+"\"\r\n");
				
		   		//buffer.append("set port \"1010\"\r\n");
		   		//buffer.append("set port 1010\r\n");
				buffer.append("set port "+BATCH_UPLOAD_SERVER_PORT+"\r\n");
				// fredy-guerrero
				//buffer.append("set username \"fredy.guerrero\"\r\n");
				buffer.append("set username \""+BATCH_UPLOAD_SERVER_USER+"\"\r\n");
				//Control03*
				//buffer.append("set password \"Control03*\"\r\n");
				buffer.append("set password \""+BATCH_UPLOAD_SERVER_PASSWORD+"\"\r\n");
				//buffer.append("set protocol \"sftp\"\r\n");
				buffer.append("set protocol \"ftp\"\r\n");
				//set protocol "sftp"
				buffer.append("connect\r\n");
				//buffer.append("dir \r\n");
				//buffer.append("cd \"Pruebas Comfandi Recaudo\"\r\n");
				//buffer.append("dir \r\n");
				//buffer.append("cd Pruebas Comfandi Recaudo\r\n");
				// Ruta de conexi�n de pruebas de Comfandi
				// Pruebas Comfandi Recaudo
			   //buffer.append("dir\r\n");
			   //buffer.append("disconnect\r\n");
				byte[] data = buffer.toString().getBytes();
				ftcl.execute(data);
				connectedFlag_ = true;  
			}catch (Exception e){
				System.out.println("Excepci�n conectando a servidor sFTP");
				e.printStackTrace();
			}
			
			// CACS: Situandose en el directorio de subida del servidor	
			if (connectedFlag_){ // Si la conexi�n fue correcta se procede a situarse en la ruta adecuada.

				try {
					StringBuffer buffer = new StringBuffer();
					//buffer.append("connect\r\n");
					//buffer.append("disconnect\r\n");
					//buffer.append("exec \"quote site namefmt 1\"");
					//buffer.append("exec \"namefmt 1\"");
					//uffer.append("quote site namefmt 1");
					buffer.append("cd \"/QSYS.LIB/QGPL.LIB\"\r\n");
					byte[] data_ = buffer.toString().getBytes();
					ftcl.execute(data_);
					remotePathFlag_ = true;
				}catch(Exception e){
					System.out.println("Excepci�n colocando quote site namefmt a 1");
					e.printStackTrace();
				}				
				
				//String path_ = "Pruebas Comfandi Recaudo";
				String path_ = BATCH_UPLOAD_DIRECTORY;
				try {
					StringBuffer buffer = new StringBuffer();
					//buffer.append("connect\r\n");
					//buffer.append("disconnect\r\n");
					buffer.append("cd \""+ path_ +"\"\r\n");
					buffer.append("dir \r\n");
					byte[] data_ = buffer.toString().getBytes();
					ftcl.execute(data_);
					remotePathFlag_ = true;
				}catch(Exception e){
					System.out.println("Excepci�n situ�ndose en la ruta:"+path_);
					e.printStackTrace();
				}
			}
	
			// CACS: Situandose en el directorio local donde est� el archivo a subir	
			if (remotePathFlag_){ // Si se logro situar satisfactoriamente el directorio de subida de archivos en el servidor sFTP.
				//String path_ = "C:/temp/comfandi/outputfiles";
				String path_ = BATCH_INPUT_DIRECTORY;
				try {
					StringBuffer buffer = new StringBuffer();
					//buffer.append("connect\r\n");
					//buffer.append("disconnect\r\n");
					buffer.append("lcd \""+ path_ +"\"\r\n");
					byte[] data_ = buffer.toString().getBytes();
					ftcl.execute(data_);
					localPathFlag_ = true;
				}catch(Exception e){
					System.out.println("Excepci�n situ�ndose en la ruta:"+path_);
					e.printStackTrace();
				}
			}
			
			
			if (localPathFlag_){ // CACS Si se pudo situar correctament en la ruta se procede a subir los archivos.
				System.out.println("Subiendo los archivos ...");
				//String command_ = "mput \".*\\.txt\"\r\n";
				String command_ = "mput \""+pFileName+"\"\r\n";
				System.out.println("Aplicando el comando:"+command_);
				try {
					StringBuffer buffer = new StringBuffer();
					//buffer.append("connect\r\n");
					//buffer.append("disconnect\r\n");
					//buffer.append("lcd \""+ path_ +"\"\r\n");
					buffer.append(command_);
					byte[] data_ = buffer.toString().getBytes();
					ftcl.execute(data_);
					uploadFileFlag_ = true;
				}catch(Exception e){
					System.out.println("Excepci�n ejecutando el comando:"+command_);
					e.printStackTrace();
				}	 
			}
			
			if (connectedFlag_){ // Si se logro hacer la conexi�n, se debe cerrar.
				try{
					StringBuffer buffer = new StringBuffer();
					buffer.append("disconnect\r\n");
					byte[] data_ = buffer.toString().getBytes();
					ftcl.execute(data_);
				}catch (Exception e){
					System.out.println("Excepci�n cerrando conexi�n");
					e.printStackTrace();
				}		
			}
			try{
				Thread.sleep(60000);
			}catch(Exception e){
				
			}
		}
		return uploadFileFlag_;
	}
	
	public static int desmarcarRegistrosGenerados(int pRegsToUpdate){
		int updatedRegs_ = 0;
		try{
			boolean pending_=true;
			int counter_=0;
			while(counter_<3){
					String sentence_=
					" UPDATE " +
						BusinessLogicParameters.DB_TABLE_TRX_ONLINE_HEADER +
					" SET "+
							BusinessLogicParameters.DB_TABLE_TRX_ONLINE_HEADER + "." + BusinessLogicParameters.DB_COLUM_TOL_HEADER_ONLINE_INVENTORY_STATUS + "='" +
							REGS_STATUS_BLANK+"'"; /* +
							ParametrosWas.DB_TABLE_SERVICE_PAYMENT_TRX + "." + BusinessLogicParameters.DB_COLUM_BP_P_WORK_DATE + "=" +
							"TIMESTAMP('"+
							pCalFechaHoraProceso.get(Calendar.YEAR)+
							StringFormatter.align(""+(pCalFechaHoraProceso.get(Calendar.MONTH)+1),2,'0',0,2)+
							StringFormatter.align(""+pCalFechaHoraProceso.get(Calendar.DAY_OF_MONTH),2,'0',0,2)+
							StringFormatter.align(""+pCalFechaHoraProceso.get(Calendar.HOUR_OF_DAY),2,'0',0,2)+
							StringFormatter.align(""+pCalFechaHoraProceso.get(Calendar.MINUTE),2,'0',0,2)+
							StringFormatter.align(""+pCalFechaHoraProceso.get(Calendar.SECOND),2,'0',0,2)+
							//pCalFechaHoraProceso.get(Calendar.MILLISECOND)+
							"') ";*/	
					String filtro_ = 
									//BusinessLogicParameters.DB_COLUM_SP_SUPPLYER_EAN+"="+BATCH_EAN_SIMPLE +" AND " + // EAN de Simple  
									BusinessLogicParameters.DB_TABLE_TRX_ONLINE_HEADER + "." + BusinessLogicParameters.DB_COLUM_TOL_HEADER_ONLINE_INVENTORY_STATUS + "='" +
									REGS_STATUS_GENERATED+"' ";
					System.out.println("Sentencia->"+sentence_ +" WHERE "+filtro_);										
					updatedRegs_ = SqlUtils.actualizaTabla(
									sentence_ +" WHERE "+filtro_,
									PosRequestsHandler.getCnxClassName(BusinessLogicParameters.ID_DATABASE_TRXONLINE),
									PosRequestsHandler.getCnxUrl(BusinessLogicParameters.ID_DATABASE_TRXONLINE),
									PosRequestsHandler.getCnxUserID(BusinessLogicParameters.ID_DATABASE_TRXONLINE),
									PosRequestsHandler.getCnxPassword(BusinessLogicParameters.ID_DATABASE_TRXONLINE)
									);
					if (pRegsToUpdate != updatedRegs_){
						System.out.println("Registros marcados como generados no coinciden con los que se metieron en archivo");
						return -1;
					}
						
					if(updatedRegs_==-1 || updatedRegs_==0){ // Hubo problemas con la base de datos o el registro no existe.
						System.out.println("Hubo problemas con la base de datos o el registro no existe. Num Reg->"+updatedRegs_);						
					}else{ // El pago fue confirmado satisfactoriamente.
						filtro_=
						//BusinessLogicParameters.DB_COLUM_SP_SUPPLYER_EAN+"="+BATCH_EAN_SIMPLE +" AND " + // EAN de Simple  
						BusinessLogicParameters.DB_TABLE_TRX_ONLINE_HEADER + "." + BusinessLogicParameters.DB_COLUM_TOL_HEADER_ONLINE_INVENTORY_STATUS + "='" +
						REGS_STATUS_BLANK+"' ";
						if (!quedoTrx(filtro_)){
							return -1;					
						}else
							counter_=3;
					}
					counter_++;
			}			
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}
		return updatedRegs_;
	}	
	
	/*
	public static int marcarRegistrosGeneradosInItemEntriesTable(int pRegsToUpdate, String pDateTimePeriod){
		int updatedRegs_ = 0;
		try{
			boolean pending_=true;
			int counter_=0;
			while(counter_<3){
					String sentence_=
					" UPDATE " +
						BusinessLogicParameters.DB_TABLE_TRX_ONLINE_ITEM_ENTRY +
					" SET "+
							
							BusinessLogicParameters.DB_TABLE_TRX_ONLINE_ITEM_ENTRY + "." + BusinessLogicParameters.DB_COLUM_TOL_ITM_ENTRY_ONLINE_INVENTORY_WORKED_DATE + "='" +
							REGS_STATUS_GENERATED+"'"+"', "+
							BusinessLogicParameters.DB_TABLE_TRX_ONLINE_ITEM_ENTRY + "." + BusinessLogicParameters.DB_COLUM_TOL_ITM_ENTRY_ONLINE_INVENTORY_WORKED_DATE + "=" +
							"TIMESTAMP('"+pDateTimePeriod.substring(0,4)+pDateTimePeriod.substring(4,6)+pDateTimePeriod.substring(6,8)+
							pDateTimePeriod.substring(8,10)+pDateTimePeriod.substring(10,12)+pDateTimePeriod.substring(12,14)+"') ";

					String filtro_ = 
									//BusinessLogicParameters.DB_COLUM_SP_SUPPLYER_EAN+"="+BATCH_EAN_SIMPLE +" AND " + // EAN de Simple  
									BusinessLogicParameters.DB_TABLE_TRX_ONLINE_HEADER + "." + BusinessLogicParameters.DB_COLUM_TOL_HEADER_ONLINE_INVENTORY_STATUS + "='" +
									REGS_STATUS_PROCESSING+"' ";
					System.out.println("Sentencia->"+sentence_ +" WHERE "+filtro_);										
					updatedRegs_ = SqlUtils.actualizaTabla(
									sentence_ +" WHERE "+filtro_,
									PosRequestsHandler.getCnxClassName(BusinessLogicParameters.ID_DATABASE_TRXONLINE),
									PosRequestsHandler.getCnxUrl(BusinessLogicParameters.ID_DATABASE_TRXONLINE),
									PosRequestsHandler.getCnxUserID(BusinessLogicParameters.ID_DATABASE_TRXONLINE),
									PosRequestsHandler.getCnxPassword(BusinessLogicParameters.ID_DATABASE_TRXONLINE)
									);
					if (pRegsToUpdate != updatedRegs_){
						System.out.println("Registros marcados como generados no coinciden con los que se metieron en archivo");
						return -1;
					}
						
					if(updatedRegs_==-1 || updatedRegs_==0){ // Hubo problemas con la base de datos o el registro no existe.
						System.out.println("Hubo problemas con la base de datos o el registro no existe. Num Reg->"+updatedRegs_);						
					}else{ // El pago fue confirmado satisfactoriamente.
						filtro_=									
						//BusinessLogicParameters.DB_COLUM_SP_SUPPLYER_EAN+"="+BATCH_EAN_SIMPLE +" AND " + // EAN de Simple  
						BusinessLogicParameters.DB_TABLE_TRX_ONLINE_HEADER + "." + BusinessLogicParameters.DB_COLUM_TOL_HEADER_ONLINE_INVENTORY_STATUS + "='" +
						REGS_STATUS_GENERATED+"' ";
						if (!quedoTrx(filtro_)){
							return -1;					
						}else
							counter_=3;
					}
					counter_++;
			}			
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}
		return updatedRegs_;
	}
	*/
	
	public static int marcarRegistrosGenerados(int pRegsToUpdate, String pDateTimePeriod){
		int updatedRegs_ = 0;
		try{
			boolean pending_=true;
			int counter_=0;
			while(counter_<3){
					String sentence_=
					" UPDATE " +
						BusinessLogicParameters.DB_TABLE_TRX_ONLINE_HEADER +
					" SET "+
							BusinessLogicParameters.DB_TABLE_TRX_ONLINE_HEADER + "." + BusinessLogicParameters.DB_COLUM_TOL_HEADER_ONLINE_INVENTORY_STATUS + "='" +
							REGS_STATUS_GENERATED+"'," + 
							BusinessLogicParameters.DB_TABLE_TRX_ONLINE_HEADER + "." + BusinessLogicParameters.DB_COLUM_TOL_HEADER_ONLINE_INVENTORY_WORKED_DATE + "=" +
							"TIMESTAMP('"+pDateTimePeriod.substring(0,4)+pDateTimePeriod.substring(4,6)+pDateTimePeriod.substring(6,8)+
							pDateTimePeriod.substring(8,10)+pDateTimePeriod.substring(10,12)+pDateTimePeriod.substring(12,14)+"') ";
							/*
							ParametrosWas.DB_TABLE_SERVICE_PAYMENT_TRX + "." + BusinessLogicParameters.DB_COLUM_BP_P_WORK_DATE + "=" +
							"TIMESTAMP('"+
							pCalFechaHoraProceso.get(Calendar.YEAR)+
							StringFormatter.align(""+(pCalFechaHoraProceso.get(Calendar.MONTH)+1),2,'0',0,2)+
							StringFormatter.align(""+pCalFechaHoraProceso.get(Calendar.DAY_OF_MONTH),2,'0',0,2)+
							StringFormatter.align(""+pCalFechaHoraProceso.get(Calendar.HOUR_OF_DAY),2,'0',0,2)+
							StringFormatter.align(""+pCalFechaHoraProceso.get(Calendar.MINUTE),2,'0',0,2)+
							StringFormatter.align(""+pCalFechaHoraProceso.get(Calendar.SECOND),2,'0',0,2)+
							//pCalFechaHoraProceso.get(Calendar.MILLISECOND)+
							"') ";*/	
					String filtro_ = 
									//BusinessLogicParameters.DB_COLUM_SP_SUPPLYER_EAN+"="+BATCH_EAN_SIMPLE +" AND " + // EAN de Simple  
									BusinessLogicParameters.DB_TABLE_TRX_ONLINE_HEADER + "." + BusinessLogicParameters.DB_COLUM_TOL_HEADER_ONLINE_INVENTORY_STATUS + "='" +
									REGS_STATUS_PROCESSING+"' ";
					System.out.println("Sentencia->"+sentence_ +" WHERE "+filtro_);										
					updatedRegs_ = SqlUtils.actualizaTabla(
									sentence_ +" WHERE "+filtro_,
									PosRequestsHandler.getCnxClassName(BusinessLogicParameters.ID_DATABASE_TRXONLINE),
									PosRequestsHandler.getCnxUrl(BusinessLogicParameters.ID_DATABASE_TRXONLINE),
									PosRequestsHandler.getCnxUserID(BusinessLogicParameters.ID_DATABASE_TRXONLINE),
									PosRequestsHandler.getCnxPassword(BusinessLogicParameters.ID_DATABASE_TRXONLINE)
									);
					if (pRegsToUpdate != updatedRegs_){
						System.out.println("Registros marcados como generados no coinciden con los que se metieron en archivo");
						return -1;
					}
						
					if(updatedRegs_==-1 || updatedRegs_==0){ // Hubo problemas con la base de datos o el registro no existe.
						System.out.println("Hubo problemas con la base de datos o el registro no existe. Num Reg->"+updatedRegs_);						
					}else{ // El pago fue confirmado satisfactoriamente.
						filtro_=									
						//BusinessLogicParameters.DB_COLUM_SP_SUPPLYER_EAN+"="+BATCH_EAN_SIMPLE +" AND " + // EAN de Simple  
						BusinessLogicParameters.DB_TABLE_TRX_ONLINE_HEADER + "." + BusinessLogicParameters.DB_COLUM_TOL_HEADER_ONLINE_INVENTORY_STATUS + "='" +
						REGS_STATUS_GENERATED+"' ";
						if (!quedoTrx(filtro_)){
							return -1;					
						}else
							counter_=3;
					}
					counter_++;
			}			
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}
		return updatedRegs_;
	}

	public static int confirmarRegistros(int pRegsToUpdate){
		int updatedRegs_ = 0;
		try{
			boolean pending_=true;
			int counter_=0;
			while(counter_<3){
					String sentence_=
					" UPDATE " +
						BusinessLogicParameters.DB_TABLE_TRX_ONLINE_HEADER +
					" SET "+
							BusinessLogicParameters.DB_TABLE_TRX_ONLINE_HEADER + "." + BusinessLogicParameters.DB_COLUM_TOL_HEADER_ONLINE_INVENTORY_STATUS + "='" +
							REGS_STATUS_SENDED+"'"; /* +
							ParametrosWas.DB_TABLE_SERVICE_PAYMENT_TRX + "." + BusinessLogicParameters.DB_COLUM_BP_P_WORK_DATE + "=" +
							"TIMESTAMP('"+
							pCalFechaHoraProceso.get(Calendar.YEAR)+
							StringFormatter.align(""+(pCalFechaHoraProceso.get(Calendar.MONTH)+1),2,'0',0,2)+
							StringFormatter.align(""+pCalFechaHoraProceso.get(Calendar.DAY_OF_MONTH),2,'0',0,2)+
							StringFormatter.align(""+pCalFechaHoraProceso.get(Calendar.HOUR_OF_DAY),2,'0',0,2)+
							StringFormatter.align(""+pCalFechaHoraProceso.get(Calendar.MINUTE),2,'0',0,2)+
							StringFormatter.align(""+pCalFechaHoraProceso.get(Calendar.SECOND),2,'0',0,2)+
							//pCalFechaHoraProceso.get(Calendar.MILLISECOND)+
							"') ";*/	
					String filtro_ = 
									//BusinessLogicParameters.DB_COLUM_SP_SUPPLYER_EAN+"="+BATCH_EAN_SIMPLE +" AND " + // EAN de Simple  
									BusinessLogicParameters.DB_TABLE_TRX_ONLINE_HEADER + "." + BusinessLogicParameters.DB_COLUM_TOL_HEADER_ONLINE_INVENTORY_STATUS + "='" +
									REGS_STATUS_GENERATED+"' ";
					System.out.println("Sentencia->"+sentence_ +" WHERE "+filtro_);										
					updatedRegs_ = SqlUtils.actualizaTabla(
									sentence_ +" WHERE "+filtro_,
									PosRequestsHandler.getCnxClassName(BusinessLogicParameters.ID_DATABASE_TRXONLINE),
									PosRequestsHandler.getCnxUrl(BusinessLogicParameters.ID_DATABASE_TRXONLINE),
									PosRequestsHandler.getCnxUserID(BusinessLogicParameters.ID_DATABASE_TRXONLINE),
									PosRequestsHandler.getCnxPassword(BusinessLogicParameters.ID_DATABASE_TRXONLINE)
									);
					if (pRegsToUpdate != updatedRegs_){
						System.out.println("Registros marcados como enviandos no coinciden con los que se metieron en archivo. Esto no deber�a pasar y no es corregible");
						return -1;
					}
						
					if(updatedRegs_==-1 || updatedRegs_==0){ // Hubo problemas con la base de datos o el registro no existe.
						System.out.println("Hubo problemas con la base de datos o el registro no existe. Num Reg->"+updatedRegs_);						
					}else{ // El pago fue confirmado satisfactoriamente.
						filtro_=									
						//BusinessLogicParameters.DB_COLUM_SP_SUPPLYER_EAN+"="+BATCH_EAN_SIMPLE +" AND " + // EAN de Simple  
						BusinessLogicParameters.DB_TABLE_TRX_ONLINE_HEADER + "." + BusinessLogicParameters.DB_COLUM_TOL_HEADER_ONLINE_INVENTORY_STATUS + "='" +
						REGS_STATUS_SENDED+"' ";
						if (!quedoTrx(filtro_)){
							return -1;					
						}else
							counter_=3;
					}
					counter_++;
			}			
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}
		return updatedRegs_;
	}

	public static int cleanProcessingRegs(int pRegsToUpdate){
		int updatedRegs_ = 0;
		try{
			boolean pending_=true;
			int counter_=0;
			while(counter_<3){
					String sentence_=
					" UPDATE " +
						BusinessLogicParameters.DB_TABLE_TRX_ONLINE_HEADER +
					" SET "+
							BusinessLogicParameters.DB_TABLE_TRX_ONLINE_HEADER + "." + BusinessLogicParameters.DB_COLUM_TOL_HEADER_ONLINE_INVENTORY_STATUS + "='" +
							REGS_STATUS_BLANK+"'"; /* +
							ParametrosWas.DB_TABLE_SERVICE_PAYMENT_TRX + "." + BusinessLogicParameters.DB_COLUM_BP_P_WORK_DATE + "=" +
							"TIMESTAMP('"+
							pCalFechaHoraProceso.get(Calendar.YEAR)+
							StringFormatter.align(""+(pCalFechaHoraProceso.get(Calendar.MONTH)+1),2,'0',0,2)+
							StringFormatter.align(""+pCalFechaHoraProceso.get(Calendar.DAY_OF_MONTH),2,'0',0,2)+
							StringFormatter.align(""+pCalFechaHoraProceso.get(Calendar.HOUR_OF_DAY),2,'0',0,2)+
							StringFormatter.align(""+pCalFechaHoraProceso.get(Calendar.MINUTE),2,'0',0,2)+
							StringFormatter.align(""+pCalFechaHoraProceso.get(Calendar.SECOND),2,'0',0,2)+
							//pCalFechaHoraProceso.get(Calendar.MILLISECOND)+
							"') ";*/	
					String filtro_ = 
									//BusinessLogicParameters.DB_COLUM_SP_SUPPLYER_EAN+"="+BATCH_EAN_SIMPLE +" AND " + // EAN de Simple  
									BusinessLogicParameters.DB_TABLE_TRX_ONLINE_HEADER + "." + BusinessLogicParameters.DB_COLUM_TOL_HEADER_ONLINE_INVENTORY_STATUS + "='" +
									REGS_STATUS_PROCESSING+"' ";
					System.out.println("Sentencia->"+sentence_ +" WHERE "+filtro_);										
					updatedRegs_ = SqlUtils.actualizaTabla(
									sentence_ +" WHERE "+filtro_,
									PosRequestsHandler.getCnxClassName(BusinessLogicParameters.ID_DATABASE_TRXONLINE),
									PosRequestsHandler.getCnxUrl(BusinessLogicParameters.ID_DATABASE_TRXONLINE),
									PosRequestsHandler.getCnxUserID(BusinessLogicParameters.ID_DATABASE_TRXONLINE),
									PosRequestsHandler.getCnxPassword(BusinessLogicParameters.ID_DATABASE_TRXONLINE)
									);
					if (pRegsToUpdate != updatedRegs_){
						System.out.println("Registros marcados como generados no coinciden con los que se metieron en archivo");
						return -1;
					}
						
					if(updatedRegs_==-1 || updatedRegs_==0){ // Hubo problemas con la base de datos o el registro no existe.
						System.out.println("Hubo problemas con la base de datos o el registro no existe. Num Reg->"+updatedRegs_);						
					}else{ // El pago fue confirmado satisfactoriamente.
						filtro_=									
						//BusinessLogicParameters.DB_COLUM_SP_SUPPLYER_EAN+"="+BATCH_EAN_SIMPLE +" AND " + // EAN de Simple  
						BusinessLogicParameters.DB_TABLE_TRX_ONLINE_HEADER + "." + BusinessLogicParameters.DB_COLUM_TOL_HEADER_ONLINE_INVENTORY_STATUS + "='" +
						REGS_STATUS_GENERATED+"' ";
						if (!quedoTrx(filtro_)){
							return -1;					
						}else
							counter_=3;
					}
					counter_++;
			}			
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}
		return updatedRegs_;
	}	

	/*
	public static boolean confirmarRegistrosTotales(Calendar pCalFechaHoraProceso){
		try{
			boolean pending_=true;
			int counter_=0;
			while(counter_<3){
					String sentence_=
					" UPDATE " +
							ParametrosWas.DB_TABLE_SERVICE_PAYMENT_TRX +
					" SET "+
							ParametrosWas.DB_TABLE_SERVICE_PAYMENT_TRX + "." + ParametrosWas.DB_COLUM_BP_REG_STATUS + "='" +
							ComfandiPosTransaction.getBusinessLogicParam(BusinessLogicParam.ID_SENDED_REGISTER_CODE).getValue()+"',"+
							ParametrosWas.DB_TABLE_SERVICE_PAYMENT_TRX + "." + ParametrosWas.DB_COLUM_BP_T_WORK_DATE + "=" +
							"TIMESTAMP('"+
							pCalFechaHoraProceso.get(Calendar.YEAR)+
							StringFormatter.align(""+(pCalFechaHoraProceso.get(Calendar.MONTH)+1),2,'0',0,2)+
							StringFormatter.align(""+pCalFechaHoraProceso.get(Calendar.DAY_OF_MONTH),2,'0',0,2)+
							StringFormatter.align(""+pCalFechaHoraProceso.get(Calendar.HOUR_OF_DAY),2,'0',0,2)+
							StringFormatter.align(""+pCalFechaHoraProceso.get(Calendar.MINUTE),2,'0',0,2)+
							StringFormatter.align(""+pCalFechaHoraProceso.get(Calendar.SECOND),2,'0',0,2)+
							//pCalFechaHoraProceso.get(Calendar.MILLISECOND)+
							"') ";							
					String filtro_ = 
									ParametrosWas.DB_TABLE_SERVICE_PAYMENT_TRX + "." + ParametrosWas.DB_COLUM_BP_REG_STATUS + "='" +
									ComfandiPosTransaction.getBusinessLogicParam(BusinessLogicParam.ID_WORKED_REGISTER_CODE).getValue()+"' ";
					System.out.println("Sentencia->"+sentence_ +" WHERE "+filtro_);										
					int dbAnswer_ = SqlUtils.actualizaTabla(
									sentence_ +" WHERE "+filtro_,
									PosRequestsHandler.getCnxClassName(ParametrosWas.ID_DATABASE_MILENIUM),
									PosRequestsHandler.getCnxUrl(ParametrosWas.ID_DATABASE_MILENIUM),
									PosRequestsHandler.getCnxUserID(ParametrosWas.ID_DATABASE_MILENIUM),
									PosRequestsHandler.getCnxPassword(ParametrosWas.ID_DATABASE_MILENIUM)
									);
					if(dbAnswer_==-1 || dbAnswer_==0){ // Hubo problemas con la base de datos o el registro no existe.
						System.out.println("Hubo problemas con la base de datos o el registro no existe. Num Reg->"+dbAnswer_);						
					}else{ // El pago fue confirmado satisfactoriamente.
						filtro_=ParametrosWas.DB_TABLE_SERVICE_PAYMENT_TRX + "." + ParametrosWas.DB_COLUM_BP_REG_STATUS + "='" +
						ComfandiPosTransaction.getBusinessLogicParam(BusinessLogicParam.ID_SENDED_REGISTER_CODE).getValue()+"' AND "+
						ParametrosWas.DB_TABLE_SERVICE_PAYMENT_TRX + "." + ParametrosWas.DB_COLUM_BP_T_WORK_DATE + "=" +
						"TIMESTAMP('"+
						pCalFechaHoraProceso.get(Calendar.YEAR)+
						StringFormatter.align(""+(pCalFechaHoraProceso.get(Calendar.MONTH)+1),2,'0',0,2)+
						StringFormatter.align(""+pCalFechaHoraProceso.get(Calendar.DAY_OF_MONTH),2,'0',0,2)+
						StringFormatter.align(""+pCalFechaHoraProceso.get(Calendar.HOUR_OF_DAY),2,'0',0,2)+
						StringFormatter.align(""+pCalFechaHoraProceso.get(Calendar.MINUTE),2,'0',0,2)+
						StringFormatter.align(""+pCalFechaHoraProceso.get(Calendar.SECOND),2,'0',0,2)+
						//pCalFechaHoraProceso.get(Calendar.MILLISECOND)+
						"') ";
						if (quedoTrx(filtro_)){
							return true;					
						}else
							counter_=3;
					}
					counter_++;
			}			
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return false;
	}	
*/
	
	public static boolean insertWorkRegister(java.util.Date pProcessDate, int pConsecutive, int headerUpdatedRegs, int itemEntryUpdatedRegs){
		try{
			boolean pending_=true;
			int counter_=0;
			while(counter_<10){
					String sentence_ = "INSERT INTO " + BusinessLogicParameters.DB_TABLE_TOL_OLI_SEND_FILE + " (" +
						BusinessLogicParameters.DB_COLUM_TOLOLI_SF_PERIOD + "," + 
						BusinessLogicParameters.DB_COLUM_TOLOLI_SF_DATE + "," +
						BusinessLogicParameters.DB_COLUM_TOLOLI_SF_INSERT_DATE + "," +
						BusinessLogicParameters.DB_COLUM_TOLOLI_SF_HEADER_UPDATED_REGS + "," +
						BusinessLogicParameters.DB_COLUM_TOLOLI_SF_ITMENTRY_UPDATED_REGS +
					") VALUES (" +
						pConsecutive + ","+
						"DATE('"+
						(pProcessDate.getYear()+1900)+"-"+ 
						StringFormatter.align(""+(pProcessDate.getMonth()+1),2,'0',0,2)+"-"+
						StringFormatter.align(""+pProcessDate.getDate(),2,'0',0,2)+"'),"+ // format: yyyy-mm-dd
						"CURRENT TIMESTAMP,"+
						headerUpdatedRegs + "," +
						itemEntryUpdatedRegs +
					")";				
					boolean result_=SqlUtils.insertaRegistro(
						sentence_,
						PosRequestsHandler.getCnxClassName(BusinessLogicParameters.ID_DATABASE_TRXONLINE),
						PosRequestsHandler.getCnxUrl(BusinessLogicParameters.ID_DATABASE_TRXONLINE),
						PosRequestsHandler.getCnxUserID(BusinessLogicParameters.ID_DATABASE_TRXONLINE),
						PosRequestsHandler.getCnxPassword(BusinessLogicParameters.ID_DATABASE_TRXONLINE)
					);
					if(!result_){ // Hubo problemas con la base de datos o el registro no existe.
						System.out.println("Hubo problemas con la base de datos o el registro no existe. Num Reg->"+result_);						
					}else{ // El pago fue confirmado satisfactoriamente.
						String filtro_ = BusinessLogicParameters.DB_TABLE_TOL_OLI_SEND_FILE + "." + BusinessLogicParameters.DB_COLUM_TOLOLI_SF_PERIOD + "=" + 
						pConsecutive + " AND " +BusinessLogicParameters.DB_TABLE_TOL_OLI_SEND_FILE + "." + BusinessLogicParameters.DB_COLUM_TOLOLI_SF_DATE + "=" +
						"DATE('"+
						(pProcessDate.getYear()+1900)+"-"+
						StringFormatter.align(""+(pProcessDate.getMonth()+1),2,'0',0,2)+"-"+
						StringFormatter.align(""+pProcessDate.getDate(),2,'0',0,2)+"')"; // format: yyyy-mm-dd
						if (quedoRegConciliation(filtro_)){
							return true;					
						}else
							counter_=3;
					}
					counter_++;
					try{
						Thread.sleep(60000);
					}catch (Exception e){}	
			}			
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return false;
	}
	/*
	public static boolean borrarTrx(Calendar pCalFechaHoraProceso){
		try{
			boolean pending_=true;
			int counter_=0;
			while(counter_<10){
					String sentence_ = "DELETE FROM " + ParametrosWas.DB_TABLE_DAYLY_CONCIL; 
					String filtro_ = ParametrosWas.DB_COLUM_DC_CONCIL_DATE+"=DATE('"+
									pCalFechaHoraProceso.get(Calendar.YEAR)+"-"+
									StringFormatter.align(""+(pCalFechaHoraProceso.get(Calendar.MONTH)+1),2,'0',0,2)+"-"+
									StringFormatter.align(""+pCalFechaHoraProceso.get(Calendar.DAY_OF_MONTH),2,'0',0,2)+ // format: yyyy-mm-dd
					"')";				
					int result_=SqlUtils.actualizaTabla(
						sentence_+" WHERE " + filtro_,
						PosRequestsHandler.getCnxClassName(ParametrosWas.ID_DATABASE_TITAN),
						PosRequestsHandler.getCnxUrl(ParametrosWas.ID_DATABASE_TITAN),
						PosRequestsHandler.getCnxUserID(ParametrosWas.ID_DATABASE_TITAN),
						PosRequestsHandler.getCnxPassword(ParametrosWas.ID_DATABASE_TITAN)
					);
					if(result_==-1){ // Hubo problemas con la base de datos o el registro no existe.
						System.out.println("Hubo problemas con la base de datos o el registro no existe. Num Reg->"+result_);
						Thread.sleep(60000);						
					}else{ // El pago fue confirmado satisfactoriamente.
						return true;					
					}
					counter_++;
			}			
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return false;
	}
	*/
	
/*	protected static void registrarTrx(final String pSentencia) {
		Thread hilo_ = new Thread(new Runnable() {
			public void run() {
				boolean result_=SqlUtils.insertaRegistro(
					pSentencia,
					PosRequestsHandler.getCnxClassName(ParametrosWas.ID_DATABASE_TITAN),
					PosRequestsHandler.getCnxUrl(ParametrosWas.ID_DATABASE_TITAN),
					PosRequestsHandler.getCnxUserID(ParametrosWas.ID_DATABASE_TITAN),
					PosRequestsHandler.getCnxPassword(ParametrosWas.ID_DATABASE_TITAN)
				);
				System.out.println("Resultado de la trx->"+result_);
				

					
			}
		});
		
		hilo_.start();
	}*/

	/*
	protected static String getInsertionSentence(Calendar pCalFechaHoraProceso){
			String sentence_ = "INSERT INTO " + ParametrosWas.DB_TABLE_DAYLY_CONCIL + " (" +
							//ParametrosWas.DB_COLUM_PEC_ID + "," +
							ParametrosWas.DB_COLUM_DC_CONCIL_DATE + 
					") VALUES (" + 
							"DATE('"+
								pCalFechaHoraProceso.get(Calendar.YEAR)+"-"+
								StringFormatter.align(""+(pCalFechaHoraProceso.get(Calendar.MONTH)+1),2,'0',0,2)+"-"+
								StringFormatter.align(""+pCalFechaHoraProceso.get(Calendar.DAY_OF_MONTH),2,'0',0,2)+"')"+ // format: yyyy-mm-dd
					")";			
				System.out.println("Sentencia para insertar en tabla log->"+sentence_);		
				return sentence_;
	}
	*/	
	
	/*
	public static boolean marcarRegistro(TransactionInDBVO pTrx, String pFechaHoraTrx, String pDateTimePeriod){
				
		try{
			boolean pending_=true;
			int counter_=0;
			while(counter_<3){
					String sentence_=
					" UPDATE " +
						BusinessLogicParameters.DB_TABLE_TRX_ONLINE_HEADER +
					" SET "+
						BusinessLogicParameters.DB_TABLE_TRX_ONLINE_HEADER + "." + BusinessLogicParameters.DB_COLUM_TOL_HEADER_ONLINE_INVENTORY_STATUS + "='" +
							REGS_STATUS_PROCESSING+"', "+
						BusinessLogicParameters.DB_TABLE_TRX_ONLINE_HEADER + "." + BusinessLogicParameters.DB_COLUM_BP_P_WORK_DATE + "=" +
						"TIMESTAMP('"+pDateTimePeriod.substring(0,4)+pDateTimePeriod.substring(4,6)+pDateTimePeriod.substring(6,8)+
						pDateTimePeriod.substring(8,10)+pDateTimePeriod.substring(10,12)+pDateTimePeriod.substring(12,14)+"') ";
					String filtroBase_ = 
						//BusinessLogicParameters.DB_COLUM_SP_REFERENCE_NUMBER+"="+pTrx.getNumReferencia() +" AND " +
						//BusinessLogicParameters.DB_COLUM_SP_SUPPLYER_EAN+"="+BATCH_EAN_SIMPLE +" AND " + // format: yyyy-mm-dd
						BusinessLogicParameters.DB_COLUM_TOL_STORE+"="+pTrx.getAlmacen() +" AND " + // almac�n
						BusinessLogicParameters.DB_COLUM_TOL_TERMINAL+"="+pTrx.getTerminal() +" AND " + // Caja
						BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER+"="+pTrx.getNumTrx() +" AND " + // Transacci�n
						//BusinessLogicParameters.DB_COLUM_SP_PAYMENT_DATE +"="+"DATE('"+pFechaHoraTrx.substring(0,4)+"-"+pFechaHoraTrx.substring(4,6)+"-"+pFechaHoraTrx.substring(6,8)+"')"; //AND "+ // Fecha
						//"DATE("+BusinessLogicParameters.DB_COLUM_SP_TRX_TIMESTAMP +")="+"DATE('"+pFechaHoraTrx.substring(0,4)+"-"+pFechaHoraTrx.substring(4,6)+"-"+pFechaHoraTrx.substring(6,8)+"')"; //AND "+ // Fecha
						BusinessLogicParameters.DB_COLUM_TOL_DATE_TIME +"="+"DATE('"+pFechaHoraTrx.substring(0,4)+"-"+pFechaHoraTrx.substring(4,6)+"-"+pFechaHoraTrx.substring(6,8)+"') AND " + //AND "+ // Fecha
						BusinessLogicParameters.DB_COLUM_TOL_DATE_TIME +"="+"TIME('"+pFechaHoraTrx.substring(8,10)+":"+pFechaHoraTrx.substring(10,12)+":"+pFechaHoraTrx.substring(12,14)+"')"; //AND "+ // Fecha
					String filtro_ = filtroBase_ + " AND " + BusinessLogicParameters.DB_COLUM_TOL_HEADER_ONLINE_INVENTORY_STATUS+"='"+REGS_STATUS_BLANK+"'"; // Transacci�n
				//pFechaHoraPago.substring(0,4)+"/"+StringFormatter.align(pFechaHoraPago.substring(4,6),2,'0',0,2)+"/"+StringFormatter.align(pFechaHoraPago.substring(6,8),2,'0',0,2)+"')"; // format: yyyy-mm-dd
							;
							System.out.println("Sentencia->"+sentence_ +" WHERE "+filtro_);										
					int dbAnswer_ = SqlUtils.actualizaTabla(
									sentence_ +" WHERE "+filtro_,
									PosRequestsHandler.getCnxClassName(BusinessLogicParameters.ID_DATABASE_TRXONLINE),
									PosRequestsHandler.getCnxUrl(BusinessLogicParameters.ID_DATABASE_TRXONLINE),
									PosRequestsHandler.getCnxUserID(BusinessLogicParameters.ID_DATABASE_TRXONLINE),
									PosRequestsHandler.getCnxPassword(BusinessLogicParameters.ID_DATABASE_TRXONLINE)
									);
					if(dbAnswer_==-1 || dbAnswer_==0){ // Hubo problemas con la base de datos o el registro no existe.
						System.out.println("Hubo problemas con la base de datos o el registro no existe. Num Reg->"+dbAnswer_);						
					}else{ // El pago fue confirmado satisfactoriamente.
						filtroBase_ += " AND " + BusinessLogicParameters.DB_TABLE_TRX_ONLINE_HEADER + "." + BusinessLogicParameters.DB_COLUM_TOL_HEADER_ONLINE_INVENTORY_STATUS + "='" +
						REGS_STATUS_PROCESSING+"' ";
						if (quedoTrx(filtroBase_)){
							return true;					
						}
					}
					counter_++;
			}			
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return false;
	}*/

	public static boolean quedoTrx(String pFilter){
		boolean answer_=false;
		RegistersVo numReg_=new RegistersVo();
		numReg_.setNumberReg(0); 
		String select_ =
				"COUNT(*) numberReg ";
		String from_ =
				BusinessLogicParameters.DB_TABLE_TRX_ONLINE_HEADER;
		String where_=	pFilter;		
		List res_;
		int reintentos_=1;
		boolean pending_=true;
		while(pending_ && reintentos_<=3){
			try {
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
		return (numReg_.getNumberReg()>0);
	}

	public static int getRegistersWasGenerated(){
		boolean answer_=false;
		RegistersVo numReg_=new RegistersVo();
		numReg_.setNumberReg(0); 
		String select_ =
				"COUNT(*) numberReg ";
		String from_ =
				BusinessLogicParameters.DB_TABLE_TRX_ONLINE_HEADER;
		String where_= //BusinessLogicParameters.DB_COLUM_SP_SUPPLYER_EAN+"="+BATCH_EAN_SIMPLE +" AND " + // EAN de Simple  
					   BusinessLogicParameters.DB_COLUM_TOL_HEADER_ONLINE_INVENTORY_STATUS+"='"+REGS_STATUS_GENERATED+"'";		
		List res_;
		int reintentos_=1;
		//boolean pending_=true;
		//while(pending_ && reintentos_<=3){
			try {
				System.out.println("Select "+select_+" from "+from_+" where "+where_);
				res_ = DBUtil.select(select_, from_, where_, RegistersVo.class);
				Iterator iterator_=res_.iterator();
				numReg_=(RegistersVo)iterator_.next();
				//pending_=false;
			} catch (ConnectionFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return -1;
			} catch (DBAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return -1;
			}catch (Exception e){
				e.printStackTrace();
			}catch (Throwable t){
				t.printStackTrace();
				return -1;
			}finally{
				//reintentos_++;
			}
		//}
		return numReg_.getNumberReg();
	}
	
	public static int getRegistersInProcess(){
		boolean answer_=false;
		RegistersVo numReg_=new RegistersVo();
		numReg_.setNumberReg(0); 
		String select_ =
				"COUNT(*) numberReg ";
		String from_ =
				BusinessLogicParameters.DB_TABLE_TRX_ONLINE_HEADER;
		String where_= //BusinessLogicParameters.DB_COLUM_SP_SUPPLYER_EAN+"="+BATCH_EAN_SIMPLE +" AND " + // EAN de Simple  	
					   BusinessLogicParameters.DB_COLUM_TOL_HEADER_ONLINE_INVENTORY_STATUS+"='"+REGS_STATUS_PROCESSING+"'";		
		List res_;
		int reintentos_=1;
		//boolean pending_=true;
		//while(pending_ && reintentos_<=3){
			try {
				System.out.println("Select "+select_+" from "+from_+" where "+where_);
				res_ = DBUtil.select(select_, from_, where_, RegistersVo.class);
				Iterator iterator_=res_.iterator();
				numReg_=(RegistersVo)iterator_.next();
				//pending_=false;
			} catch (ConnectionFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return -1;
			} catch (DBAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return -1;
			}catch (Exception e){
				e.printStackTrace();
			}catch (Throwable t){
				t.printStackTrace();
				return -1;
			}finally{
				//reintentos_++;
			}
		//}
		return numReg_.getNumberReg();
	}

	public static int updateStatusToProccessingTransactions(String pWhere){ //throws DBAccessException, BonusNotFoundException{
		//boolean answer_=true;
		int answer_ = -1;
		int counter_ = 0;
		boolean exit_ = false;
		while(!exit_ && counter_<5){
			try{
				String sentence_ =  " UPDATE " + BusinessLogicParameters.DB_TABLE_TRX_ONLINE_HEADER + " a " + 
									" SET " + BusinessLogicParameters.DB_COLUM_TOL_HEADER_ONLINE_INVENTORY_STATUS + "='" + REGS_STATUS_PROCESSING+"'";
				
		
				String filter_ = pWhere;
		
				answer_ = SqlUtils.actualizaTabla(
								sentence_ +" WHERE "+filter_,
								PosRequestsHandler.getCnxClassName(""),
								PosRequestsHandler.getCnxUrl(""),
								PosRequestsHandler.getCnxUserID(""),
								PosRequestsHandler.getCnxPassword("")
								);
				if(answer_>0){ // Registro fue actualizado satisfactoriamente o ya hab�a sido actualizado
					System.out.println("Registros inventarios en lInea actualizados satisfactoriamente");
				}/*else if (dbUpdateAnswer_==0){
					System.out.println("Bono no encontrado para actualizaci�n en la DB");
					//throw new BonusNotFoundException("Bono no encontrado para actualizaci�n en la DB");
				}*/else{ //  Hubo problemas con la base de datos
					System.out.println("Error actualizando registro de puntos");
					//throw new DBAccessException("Error actulizando bono en la DB");
				}
				exit_ = true;
			}/*catch(DBAccessException e){
				e.printStackTrace();
			}*/catch(Exception e){
				e.printStackTrace();
			}catch(Throwable t){
				t.printStackTrace();
			}finally{
				counter_++;
			}
		}	
		return answer_;
	}
	
	public static int getRegistersNumberToProcess(String pWhere) throws DBAccessException{
		boolean answer_=false;
		RegistersVo numReg_=new RegistersVo();
		numReg_.setNumberReg(0); 
		String select_ =
				"COUNT(*) numberReg ";
		String from_ =
				BusinessLogicParameters.DB_TABLE_TRX_ONLINE_HEADER + " a ";
		String where_= pWhere;//BusinessLogicParameters.DB_COLUM_TOL_HEADER_ONLINE_INVENTORY_STATUS+"='"+REGS_STATUS_PROCESSING+"'";
		//where_ += " ORDER BY FECHAHORA ASC";
		List res_;
		int reintentos_=1;
		//boolean pending_=true;
		//while(pending_ && reintentos_<=3){
			try {
				System.out.println("Select "+select_+" from "+from_+" where "+where_);
				res_ = DBUtil.select(select_, from_, where_, RegistersVo.class);
				Iterator iterator_=res_.iterator();
				numReg_=(RegistersVo)iterator_.next();
				//pending_=false;
			} catch (ConnectionFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return -1;
			} catch (DBAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return -1;
			}catch (Exception e){
				e.printStackTrace();
			}catch (Throwable t){
				t.printStackTrace();
				return -1;
			}finally{
				//reintentos_++;
			}
		//}
		return numReg_.getNumberReg();
	}	
	
	public static boolean getPeriod(java.util.Date pProcessDate, int pFileConsecutive)throws Exception{
		boolean answer_=false;
		RegistersVo numReg_=new RegistersVo();
		numReg_.setNumberReg(0); 
		String select_ =
				"COUNT(*) numberReg ";
		String from_ =
				BusinessLogicParameters.DB_TABLE_TOL_OLI_SEND_FILE;
		String where_=  BusinessLogicParameters.DB_TABLE_TOL_OLI_SEND_FILE + "." + BusinessLogicParameters.DB_COLUM_TOLOLI_SF_PERIOD + "=" + 
						pFileConsecutive + " AND " +BusinessLogicParameters.DB_TABLE_TOL_OLI_SEND_FILE + "." + BusinessLogicParameters.DB_COLUM_TOLOLI_SF_DATE + "=" +
						"DATE('"+
						(pProcessDate.getYear()+1900)+"-"+
						StringFormatter.align(""+(pProcessDate.getMonth()+1),2,'0',0,2)+"-"+
						StringFormatter.align(""+pProcessDate.getDate(),2,'0',0,2)+"')";		
		List res_;
		int reintentos_=1;
		boolean pending_=true;
		while(pending_ && reintentos_<=3){
			try {
				System.out.println("Select "+select_+" from "+from_+" where "+where_);
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
		if (pending_)
			throw new Exception ("Error obteniendo periodo en DB");
		return (numReg_.getNumberReg()>0);
	}

	public static boolean quedoRegConciliation(String pFilter){
		boolean answer_=false;
		RegistersVo numReg_=new RegistersVo();
		numReg_.setNumberReg(0); 
		String select_ =
				"COUNT(*) numberReg ";
		String from_ =
				BusinessLogicParameters.DB_TABLE_TOL_OLI_SEND_FILE;
		String where_=	pFilter;		
		List res_;
		int reintentos_=1;
		boolean pending_=true;
		while(pending_ && reintentos_<=3){
			try {
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
		return (numReg_.getNumberReg()>0);
	}
	
	private void addItemToList(ItemEntryRegInDBVO pItmEntryVo){
		if(theHSStores == null)
			theHSStores = new Hashtable();

			//long itmQuantity_ = pItmEntryVo.getQtyOrWgt();
		if(pItmEntryVo.getItemCode()==114)
			System.out.println("Este es Item que buscamos");
		
		if(pItmEntryVo != null){
			long currEntryAmount_ = 0;
			long currItemsCount_ = 0;			
			System.out.println("******* ITEM **********");
			System.out.println("Item code->"+pItmEntryVo.getItemCode());
			System.out.println("Secci�n->"+pItmEntryVo.getDepartme());
			System.out.println("Precio entrada->"+pItmEntryVo.getXprice());
			System.out.println("Indicat 31->"+pItmEntryVo.getIndicat31()); // {8: Anulaci�n 2:Devoluci�n}
			System.out.println("Indicat 211->"+pItmEntryVo.getIndicat211()); // {1: Precio es negativo}
			//System.out.println("Indicat 16->"+pItmEntryVo.getINDICAT16()); //
			System.out.println("Indicat 17->"+pItmEntryVo.getIndicat17()); // 1: Es pesable  0: No pesable
			int countSign_ = 1;
			if (pItmEntryVo.getIndicat31().equals("2") || pItmEntryVo.getIndicat31().equals("8")){ //{8: Anulaci�n 2:Devoluci�n} 
				//itmQuantity_ = itmQuantity_ * (-1);
				countSign_ = -1;
			}	
			int amtSign_ = 1;
			if (pItmEntryVo.getIndicat211().equals("1")) // {1: Precio es negativo}
				amtSign_ = -1;
			try{
				currEntryAmount_ = pItmEntryVo.getXprice() * countSign_;
				if (amtSign_ == -1 && currEntryAmount_>0)
					currEntryAmount_ = currEntryAmount_ * amtSign_;
				//orderTotalAmt_ += currEntryAmount_;
			}catch(Exception e){
				System.out.println("Exception transformando XPRICE. No deberia pasar");
			}

			if (pItmEntryVo.getQtyOrWgt() > 0){
				System.out.println("Cantidad->"+pItmEntryVo.getQtyOrWgt());
				currItemsCount_ = pItmEntryVo.getQtyOrWgt()  * countSign_;
				//itemsNumber_ += currItemsCount_;
			}else{
				System.out.println("Cantidad x defecto->1");
				if (countSign_ == 1)
					currItemsCount_++;
				else
					currItemsCount_--;
				//itemsNumber_ ++;
			}
			StoreXItemEntry currItmXStore_ = (StoreXItemEntry)theHSStores.get(pItmEntryVo.getAlmacen()+"");
			if(currItmXStore_ == null){ // No existe un registro para el almac�n
				ItemEntryRegInDBVO currItmEntry_ = new ItemEntryRegInDBVO();
				currItmEntry_.setItemCode(pItmEntryVo.getItemCode());
				currItmEntry_.setQtyOrWgt(currItemsCount_);
				currItmEntry_.setXprice(currEntryAmount_);
				currItmEntry_.setIndicat17(pItmEntryVo.getIndicat17()); // Flag de pesable
				currItmEntry_.setAlmacen(pItmEntryVo.getAlmacen());
				currItmEntry_.setVATValue(pItmEntryVo.getVATValue());
				currItmEntry_.setConsumptionTaxValue(pItmEntryVo.getConsumptionTaxValue());
				currItmXStore_ = new StoreXItemEntry(); // Tiene C�digo �tem y lista de los almacenes donde se vendi�
				//currItmXStore_.setAlmacen(pItmEntryVo.getAlmacen());
				currItmXStore_.setStore(pItmEntryVo.getAlmacen());
				Hashtable HSItems_ = new Hashtable();
				HSItems_.put(pItmEntryVo.getItemCode()+"", currItmEntry_);
				currItmXStore_.setHSItems(HSItems_);
				theHSStores.put(pItmEntryVo.getAlmacen()+"",currItmXStore_);
				
			}else{ // Existe un registro para el almac�n
				if(pItmEntryVo.getItemCode() == 268980)
					System.out.println("Es la bolsa");
				else
					System.out.println("NO es la bolsa");
				//ItemEntryRegInDBVO currItmEntry_ = (ItemEntryRegInDBVO)currItmXStore_.getHSStores().get(pItmEntryVo.getAlmacen()+"");
				ItemEntryRegInDBVO currItmEntry_ = (ItemEntryRegInDBVO)currItmXStore_.getHSItems().get(pItmEntryVo.getItemCode()+"");
				if(currItmEntry_ != null){ // Existe un registro para el �tem dentro del almac�n
					currItmEntry_.setQtyOrWgt(currItmEntry_.getQtyOrWgt()+currItemsCount_);
					currItmEntry_.setXprice(currItmEntry_.getXprice()+currEntryAmount_);			
					long entryVATValue_ = pItmEntryVo.getVATValue();
					long entryConsumpValue_ = pItmEntryVo.getConsumptionTaxValue();
					currItmEntry_.setVATValue(currItmEntry_.getVATValue()+entryVATValue_);
					currItmEntry_.setConsumptionTaxValue(currItmEntry_.getConsumptionTaxValue()+entryConsumpValue_);
				}else{
					currItmEntry_ = new ItemEntryRegInDBVO();
					currItmEntry_.setItemCode(pItmEntryVo.getItemCode());
					currItmEntry_.setQtyOrWgt(currItemsCount_);
					currItmEntry_.setXprice(currEntryAmount_);
					currItmEntry_.setIndicat17(pItmEntryVo.getIndicat17()); // Flag de pesable
					currItmEntry_.setAlmacen(pItmEntryVo.getAlmacen());
					currItmEntry_.setVATValue(pItmEntryVo.getVATValue());
					currItmEntry_.setConsumptionTaxValue(pItmEntryVo.getConsumptionTaxValue());
					//Hashtable HSStores_ = new Hashtable();
					//HSStores_.put(pItmEntryVo.getAlmacen()+"", currItmEntry_);
					//currItmXStore_.setHSStores(HSStores_);
					//theHSItems.put(pItmEntryVo.getItemCode()+"",currItmXStore_);
					currItmXStore_.getHSItems().put(pItmEntryVo.getItemCode()+"", currItmEntry_);
				}
				if(currItmEntry_.getQtyOrWgt()>2 && currItmEntry_.getItemCode() != 268980 )
					System.out.println("Cantidad mayor a 2");

			}	
		}else{
			System.out.println("Objeto item entry de estrada es nulo. No deberIa pasar");
		}
		
	}
	
	public static List getTransactionItemEntries()throws ConnectionFailedException, DBAccessException{
		System.out.println("Desde getTransactionItemEntries");
		String select_ =
			
			" b."+BusinessLogicParameters.DB_COLUM_TOL_STORE+" almacen,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL+" terminal,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER+" numTrx,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_DATE_TIME+" fechaHora,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_ITM_ENTRY_ITEMCODE+" itemCode,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_ITM_ENTRY_DEPARTMENT+" departme,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_ITM_ENTRY_QTYORWGT+" qtyOrWgt,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_ITM_ENTRY_XPRICE+" xprice," +
			" b."+BusinessLogicParameters.DB_COLUM_TOL_ITM_ENTRY_VAT_RATE+" VATRate," +
			" b."+BusinessLogicParameters.DB_COLUM_TOL_ITM_ENTRY_TAX_VALUE+" taxValue," +
			" b."+BusinessLogicParameters.DB_COLUM_TOL_ITM_ENTRY_CONSUM_TAX_RATE+" consumptionTaxRate," +
			
			/*
				" a."+BusinessLogicParameters.DB_COLUM_TOL_ITM_ENTRY_ITEMCODE+" itemcode,"+
				" a."+BusinessLogicParameters.DB_COLUM_TOL_STORE+" almacen,"+
				" a."+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL+" terminal,"+
				" a."+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER+" numTrx,"+
				" a."+BusinessLogicParameters.DB_COLUM_TOL_DATE_TIME+" fechaHora" +
				" a."+BusinessLogicParameters.DB_COLUM_TOL_ITM_ENTRY_ITEMCODE+" itemCode" +
				" a."+BusinessLogicParameters.DB_COLUM_TOL_ITM_ENTRY_QTYORWGT+" quantityOrWeight" +
				" a."+BusinessLogicParameters.DB_COLUM_TOL_ITM_ENTRY_INDICAT31+" indicat31"+
			*/	
				" b."+BusinessLogicParameters.DB_COLUM_TOL_ITM_ENTRY_INDICAT31+" indicat31,"+
				" b."+BusinessLogicParameters.DB_COLUM_TOL_ITM_ENTRY_INDICAT211+" indicat211,"+
				" b."+BusinessLogicParameters.DB_COLUM_TOL_ITM_ENTRY_INDICAT17+" indicat17,"+
				" b."+BusinessLogicParameters.DB_COLUM_TOL_ITM_ENTRY_OFFSETX+" offsetx";
			
			//	" a."+BusinessLogicParameters.DB_COLUM_TOL_ITM_ENTRY_INDICAT46+" indicat46";

		
		
		String from_ =
					BusinessLogicParameters.DB_TABLE_TRX_ONLINE_HEADER+" a,"+
					BusinessLogicParameters.DB_TABLE_TRX_ONLINE_ITEM_ENTRY+" b";
			
		String where_ =
			" a."+BusinessLogicParameters.DB_COLUM_TOL_STORE + "=" +" b."+BusinessLogicParameters.DB_COLUM_TOL_STORE + " AND " +  
			" a."+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL + "=" + " b."+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL + " AND " + 
			" a."+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER + "=" + " b."+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER + " AND " + 
			" a."+BusinessLogicParameters.DB_COLUM_TOL_DATE_TIME + "=" + " b."+BusinessLogicParameters.DB_COLUM_TOL_DATE_TIME + " AND " +
			" a."+BusinessLogicParameters.DB_COLUM_TOL_HEADER_ONLINE_INVENTORY_STATUS+"='"+REGS_STATUS_PROCESSING+"'";
		
		System.out.println("Select "+ select_ +" from "+ from_ + " where " + where_);
		List lstItemEntriesInfo_ = 
		DBUtil.select(
			select_,						
			from_,
			where_,		
			ItemEntryRegInDBVO.class);	
		return lstItemEntriesInfo_;
	}

}
