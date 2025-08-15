/*
 * Creado el 08-feb-08
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generaci�n de c�digo&gt;C�digo y comentarios
 */
package com.grpretail.comfandi.trxonline.automaticjobs.facturaelectronica.dbaccess;

import java.util.Hashtable;
import java.util.List;

import org.ramm.jwaf.dbutil.DBUtil;
import org.ramm.jwaf.sql.ConnectionFailedException;
import org.ramm.jwaf.sql.DBAccessException;

import com.asic.ac.utils.SqlUtils;
import com.asic.ac.utils.StringFormatter;
import com.asic.author.manager.request.PosRequestsHandler;
import com.grpretail.comfandi.business.utils.BusinessLogicParam;
import com.grpretail.facturaelectronica.comfandi.business.utils.vos.CustomerInfoVo;
import com.grpretail.facturaelectronica.comfandi.business.utils.vos.ItemDescriptionVo;
import com.grpretail.facturaelectronica.comfandi.business.utils.vos.NumeroRegistros;
import com.grpretail.trxonline.automaticjobs.facturaelectronica.utils.BusinessLogicParameters;

/**
 * @author Usuario
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generaci�n de c�digo&gt;C�digo y comentarios
 */
public class FacturaElectronicaDBHelper {
	
	protected static Hashtable aHashBusinessLogicParams;
	public static final String BUSINESSSETTINGS_FILENAME	= "BusinessParams";
	
	
	public static List getItemsDescriptions(String pItemsWhere)throws ConnectionFailedException, DBAccessException, Exception, Throwable{
			List answer_=null;
			//RegistersVo numReg_=new RegistersVo();
			//numReg_.setNumberReg(0); 
			String select_ =
					BusinessLogicParam.DB_COLUM_DI_PLU+" plu,"+
					BusinessLogicParam.DB_COLUM_DI_DESCRIPTION+" description";
			String from_ =
				BusinessLogicParam.DB_TABLA_DESCRIPCION_ITEM;
			String where_= BusinessLogicParam.DB_COLUM_DI_COMPANI+"=1 AND " +
							BusinessLogicParam.DB_COLUM_DI_PLU+" IN ("+pItemsWhere+")";
			System.out.println("Sentencia->"+select_+from_+where_);		
			//List res_;
			int reintentos_=1;
			boolean pending_=true;
			while( reintentos_<=3){
				try {
					answer_ = DBUtil.select(select_, from_, where_, ItemDescriptionVo.class);
					return answer_;
					/*
					Iterator iterator_=res_.iterator();
					numReg_=(ItemDescriptionVo)iterator_.next();
					return numReg_.getNumberReg();*/
					//pending_=false;
				} catch (ConnectionFailedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					reintentos_++;
					if (reintentos_>3)
						throw e;
				} catch (DBAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					reintentos_++;
					if (reintentos_>3)
						throw e;				
				}catch (Exception e){
					e.printStackTrace();
					reintentos_++;
					if (reintentos_>3)
						throw e;				
				}catch (Throwable t){
					t.printStackTrace();
					reintentos_++;
					if (reintentos_>3)
						throw t;				
				}
			}
			throw new Exception("Error searching items descrptions in DB");
	}

	public static List getItemsDescriptions2(String pItemsWhere)throws ConnectionFailedException, DBAccessException, Exception, Throwable{
		List answer_=null;
		//RegistersVo numReg_=new RegistersVo();
		//numReg_.setNumberReg(0); 
		String select_ =
				BusinessLogicParam.DB_COLUM_DI2_PLU+" plu,"+
				BusinessLogicParam.DB_COLUM_DI2_DESCRIPTION+" description";
		String from_ =
			BusinessLogicParam.DB_TABLA_DESCRIPCION_ITEM_2;
		String where_= BusinessLogicParam.DB_COLUM_DI2_COMPANI+"=1 AND " +
						BusinessLogicParam.DB_COLUM_DI2_PLU+" IN ("+pItemsWhere+")";
		System.out.println("Sentencia->"+select_+from_+where_);		
		//List res_;
		int reintentos_=1;
		boolean pending_=true;
		while( reintentos_<=3){
			try {
				answer_ = DBUtil.select(select_, from_, where_, ItemDescriptionVo.class, BusinessLogicParameters.ID_DATABASE_ITEMS);
				return answer_;
				/*
				Iterator iterator_=res_.iterator();
				numReg_=(ItemDescriptionVo)iterator_.next();
				return numReg_.getNumberReg();*/
				//pending_=false;
			} catch (ConnectionFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				reintentos_++;
				if (reintentos_>3)
					throw e;
			} catch (DBAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				reintentos_++;
				if (reintentos_>3)
					throw e;				
			}catch (Exception e){
				e.printStackTrace();
				reintentos_++;
				if (reintentos_>3)
					throw e;				
			}catch (Throwable t){
				t.printStackTrace();
				reintentos_++;
				if (reintentos_>3)
					throw t;				
			}
		}
		throw new Exception("Error searching items descrptions in DB");
	}
	
	public static List getAgreementItemsDescriptions(String pItemsWhere)throws ConnectionFailedException, DBAccessException, Exception, Throwable{
		List answer_=null;
		//RegistersVo numReg_=new RegistersVo();
		//numReg_.setNumberReg(0); 
		String select_ =
				BusinessLogicParam.DB_COLUM_ITEM_AGREEMET_PLU+" plu,"+
				BusinessLogicParam.DB_COLUM_ITEM_AGREEMET_DESCRIPTION+" description";
		String from_ =
			BusinessLogicParam.DB_TABLA_DESCRIPCION_ITEM_AGREEMENT;
		String where_= //BusinessLogicParam.DB_COLUM_DI2_COMPANI+"=1 AND " +
						BusinessLogicParam.DB_COLUM_ITEM_AGREEMET_PLU+" IN ("+pItemsWhere+")";
		System.out.println("Sentencia->"+select_+from_+where_);		
		//List res_;
		int reintentos_=1;
		boolean pending_=true;
		while( reintentos_<=3){
			try {
				answer_ = DBUtil.select(select_, from_, where_, ItemDescriptionVo.class, BusinessLogicParameters.ID_DATABASE_ITEMS);
				return answer_;
				//pending_=false;
			} catch (ConnectionFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				reintentos_++;
				if (reintentos_>3)
					throw e;
			} catch (DBAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				reintentos_++;
				if (reintentos_>3)
					throw e;				
			}catch (Exception e){
				e.printStackTrace();
				reintentos_++;
				if (reintentos_>3)
					throw e;				
			}catch (Throwable t){
				t.printStackTrace();
				reintentos_++;
				if (reintentos_>3)
					throw t;				
			}
		}
		throw new Exception("Error searching items descrptions in DB");
	}

	public static CustomerInfoVo getCustomerInfo(String pCustomerId)throws ConnectionFailedException, DBAccessException, Exception, Throwable{
		CustomerInfoVo answer_=null;
		//RegistersVo numReg_=new RegistersVo();
		//numReg_.setNumberReg(0); 
		String select_ =
			//BusinessLogicParam.DB_COLUM_CUS_CUSTOMER_ID+" plu,"+        
			BusinessLogicParam.DB_COLUM_CUS_CUSTOMER_DOC_TYPE+" customerDocType,"+		
			BusinessLogicParam.DB_COLUM_CUS_CUSTOMER_NAMES+" names,"+     
			BusinessLogicParam.DB_COLUM_CUS_CUSTOMER_LAST_NAMES+" lastNames,"+
			BusinessLogicParam.DB_COLUM_CUS_CUSTOMER_ADDRESS+" address,"+
			BusinessLogicParam.DB_COLUM_CUS_CUSTOMER_EMAIL+" email,"+	   
			BusinessLogicParam.DB_COLUM_CUS_CUSTOMER_CELLPHONE+" phoneNumber"; 
		String from_ =
			BusinessLogicParam.DB_TABLA_CUSTOMERS;
		String where_= BusinessLogicParam.DB_COLUM_CUS_COMPANY+"=1 AND " +
						BusinessLogicParam.DB_COLUM_CUS_CUSTOMER_ID+" = "+pCustomerId;
		System.out.println("Sentencia->"+select_+from_+where_);		
		List res_;
		int reintentos_=1;
		boolean pending_=true;
		while( reintentos_<=3){
			try {
				res_ = DBUtil.select(select_, from_, where_, CustomerInfoVo.class, BusinessLogicParameters.ID_DATABASE_VECINO_FIEL);
				if(res_!= null && res_.size()>0)
					answer_ = (CustomerInfoVo)res_.get(0);
				return answer_;
			} catch (ConnectionFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				reintentos_++;
				if (reintentos_>3)
					throw e;
			} catch (DBAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				reintentos_++;
				if (reintentos_>3)
					throw e;				
			}catch (Exception e){
				e.printStackTrace();
				reintentos_++;
				if (reintentos_>3)
					throw e;				
			}catch (Throwable t){
				t.printStackTrace();
				reintentos_++;
				if (reintentos_>3)
					throw t;				
			}
		}
		throw new Exception("Error searching items descrptions in DB");
	}	

	public static boolean insertWSTransactionAnswer(String pCustomerId, String pStore, String pTerminal/*, String pOperator*/,String pTrx, String pDate,
								String pTime, String pTransactionId, String pCUFE, boolean pRetryFlag, long pTotalTrxAmt, long pTotalTaxesAmt, String pStringSMA, String pFiscalNumber){
		// Insertar en registro de la tabla llegadas_del_POS
		try{

			String sentencia_ = "INSERT INTO " + BusinessLogicParam.DB_TABLA_TRX_FAC_ELEC_CARVAJAL + " (" + 
				//BusinessLogicParam.DB_COLUM_TFD_ID + "," + 
				BusinessLogicParam.DB_COLUM_TFC_ID_CLIENTE	+ "," + 
				BusinessLogicParam.DB_COLUM_TFC_TIENDA	 + "," + 
				BusinessLogicParam.DB_COLUM_TFC_NUM_TERMINAL + "," + 	
				//BusinessLogicParam.DB_COLUM_TFD_OPERADOR + "," + 	
				BusinessLogicParam.DB_COLUM_TFC_NUM_FACTURA + "," +
				BusinessLogicParam.DB_COLUM_TFC_FECHA + "," +
				BusinessLogicParam.DB_COLUM_TFC_HORA + "," +
				
				//BusinessLogicParam.DB_COLUM_TFC_FECH_HORA_TRX_DB + "," + 	
				BusinessLogicParam.DB_COLUM_TFC_WS_TRX_ID_CONT 		+ "," + 		
//				BusinessLogicParam.DB_COLUM_TFC_WS_TRX_CUFE	 		+ "," + //APSM 20 marzo 2025, se retira por que està quedando el mismo cufe en line y contingencia
				BusinessLogicParam.DB_COLUM_TFC_WS_TRX_CUFE_CONT	 + "," +
				BusinessLogicParam.DB_COLUM_TFC_WS_TRX_TRX_TOTAL	 + "," +
		 
				BusinessLogicParam.DB_COLUM_TFC_WS_TRX_TAXES_TOTAL	 + "," +
				BusinessLogicParam.DB_COLUM_TFC_FLAG_REENVIO	 + "," +
				BusinessLogicParam.DB_COLUM_TFC_ID_TRANSACCION_EM	 + "," +
				BusinessLogicParam.DB_TFC_FISC_NUMBER_CONT +
			") VALUES (" + 
					//"SELECT MAX("," +//"SQ_LLEGADAS_DE_POS.NEXTVAL," +
					pCustomerId+"," +					 
					"'" + pStore.trim() + "'," +
					"'" + pTerminal.trim() + "'," +
					//"'" + StringFormatter.align(pOperator.trim(),10,'0',0,10) + "'," +
					"'" + pTrx + "'," +
					"'" + pDate + "'," +
					"'" + pTime + "'," +
					"'" + pTransactionId + "'," +
//					"'" + pCUFE + "'," + //APSM 20 marzo 2025, se retira por que està quedando el mismo cufe en line y contingencia
					"'" + pCUFE + "'," +
					"'" + pTotalTrxAmt + "'," +
					"'" + pTotalTaxesAmt + "',";
				if(pRetryFlag)
					sentencia_ += "'1',";
				else
					sentencia_ += "'0',";
				sentencia_ += "'" + pStringSMA + "',";
				sentencia_ += "'" + pFiscalNumber + "'";
				sentencia_ +=")";
				//long pTotalTrxAmount, String 
			int reintentos_=0;	
			boolean pending_=true;
			while(pending_ && reintentos_<3){
				System.out.println("Corrregido");
				pending_ = !SqlUtils.insertaRegistro(
					sentencia_,
					PosRequestsHandler.getCnxClassName(""),
					PosRequestsHandler.getCnxUrl(""),
					PosRequestsHandler.getCnxUserID(""),
					PosRequestsHandler.getCnxPassword("")
				);
				if(pending_ && pRetryFlag)
					reintentos_ = 3;
				reintentos_++;
			}
			if (pending_){
				return false;
				/*
				List res_;
				String select_=" count(*) num_reg ";
				String from_=BusinessLogicParam.DB_TABLA_TRX_FAC_ELEC_CARVAJAL;
				String where_ = 				
								BusinessLogicParam.DB_COLUM_TFC_TIENDA+"='" + pStore.trim() + "' AND " + 
								BusinessLogicParam.DB_COLUM_TFC_NUM_TERMINAL + "='" + StringFormatter.align(pTerminal.trim(),6,'0',0,6) + "' AND " + 	
								//BusinessLogicParam.DB_COLUM_TFN_OPERADOR + "='" + pStore.trim() + "' AND " + 	
								BusinessLogicParam.DB_COLUM_TFC_NUM_FACTURA + "='" + StringFormatter.align(pTrx,6,'0',0,6) + "' AND " + 		
								BusinessLogicParam.DB_COLUM_TFC_FECHA + "='" + pDate.trim() + "' AND " +
								BusinessLogicParam.DB_COLUM_TFC_HORA + "='" + pTime.trim() + "'";
				res_ = DBUtil.select(select_, from_, where_, NumeroRegistros.class);
				int size_=((NumeroRegistros)res_.iterator().next()).getNum_reg();
				if (size_>0)
					return true;				
				return false;
				*/
			}
			return true;		
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}catch(Throwable t){			
			t.printStackTrace();
			return false;
		}
		//return true;
	}
	
	public static boolean insertWSTransactionErrorAnswer(String pStore, String pTerminal,String pTrx, String pDateTime, String pResponseMsg, String pErrorCode, String pIUT, String pFiscalNumber){
		// Insertar en registro de la tabla llegadas_del_POS
		try{
			if(pResponseMsg == null)
				pResponseMsg = "";
			if(pResponseMsg.length()>500)
				pResponseMsg = pResponseMsg.substring(0, 500);
			/*
			int msgPartLenght_ = 1200;
			int partsNumber_ = pRequestMsg.length()/msgPartLenght_;
			if((partsNumber_ * msgPartLenght_)<pRequestMsg.length())
				partsNumber_++;
			int lowLimit_ = 0;
			int topLimit_ = msgPartLenght_;
			if(topLimit_ > pRequestMsg.length())
				topLimit_ = pRequestMsg.length();
			*/				
			//int currPartNumber_ = 1;
			//for(int i=0;i<partsNumber_;i++){
				//String strMsgPartToInsert_ = pRequestMsg.substring(lowLimit_, topLimit_);
				String sentencia_ = "INSERT INTO " + BusinessLogicParam.DB_TABLA_TRX_FAC_ELECT_TRXS_RECHAZADAS + " (" + 
					BusinessLogicParam.DB_COLUM_TFR_TIENDA	 + "," + 
					BusinessLogicParam.DB_COLUM_TFR_NUM_TERMINAL + "," + 	
					BusinessLogicParam.DB_COLUM_TFR_NUM_FACTURA + "," + 		
					BusinessLogicParam.DB_COLUM_TFR_FECHA_TRX + "," + 	
					BusinessLogicParam.DB_COLUM_TFR_HORA_TRX + "," +
					//BusinessLogicParam.DB_COLUM_TFR_REQ_PART_NUM	 + "," +
					//BusinessLogicParam.DB_COLUM_TFR_REQ_PART_DATA	 + "," +
					BusinessLogicParam.DB_COLUM_TFR_ANSWER_MSG	 + "," +
					BusinessLogicParam.DB_COLUM_TFR_ERROR_CODE	 + "," +
					BusinessLogicParam.DB_COLUM_TFR_ID_TRANSACCION_EM	 + "," +
					BusinessLogicParam.DB_COLUM_TFR_FISC_NUMBER	 +
				") VALUES (" + 
						pStore.trim() + "," +
						pTerminal.trim()+ "," +
						pTrx + "," +
						"'" + BusinessLogicParam.PAR_CENTURY+pDateTime.trim().substring(0, 2)+"-"+pDateTime.trim().substring(2, 4)+"-"+pDateTime.trim().substring(4, 6) + "'," +
						"'" + pDateTime.trim().substring(pDateTime.trim().length()-4, pDateTime.trim().length()) + "'," +
						//currPartNumber_ + "," +
						//"'" + strMsgPartToInsert_ + "'," +
						"'" + pResponseMsg + "',"+
						"'" + pErrorCode + "',"+
						"'" + pIUT + "',"+
						"'" + pFiscalNumber + "'"+
					")";
					//long pTotalTrxAmount, String 
				int reintentos_=0;	
				boolean pending_=true;
				while(pending_ && reintentos_<3){
					pending_ = !SqlUtils.insertaRegistro(
						sentencia_,
						PosRequestsHandler.getCnxClassName(""),
						PosRequestsHandler.getCnxUrl(""),
						PosRequestsHandler.getCnxUserID(""),
						PosRequestsHandler.getCnxPassword("")
					);
					//if(pending_ & pRetryFlag)
						//reintentos_ = 3;
					reintentos_++;
				}
				/*
				lowLimit_ += msgPartLenght_;
				topLimit_ += msgPartLenght_;
				if(topLimit_ > pRequestMsg.length())
					topLimit_ = pRequestMsg.length();
				currPartNumber_++;
				*/
			//}
			
			return true;		
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}catch(Throwable t){			
			t.printStackTrace();
			return false;
		}
		//return true;
	}	
	
	/*
	public static int getNumBonusForEvent(String pCustomerId, String pEventId) throws DBAccessException, CustomerNotFoundException, Exception, Throwable{
		boolean answer_=false;
		RegistersVo numReg_=new RegistersVo();
		numReg_.setNumberReg(0); 
		String select_ =
				"COUNT(*) numberReg ";
		String from_ =
			BusinessLogicParam.DB_TABLA_BONO_ELECTRO;
		String where_= BusinessLogicParameters.DB_TABLA_BONO_RECOMPRA+"."+BusinessLogicParameters.DB_COLUM_BRC_ID_CLIENTE+"="+pCustomerId + " AND " +
					   BusinessLogicParameters.DB_TABLA_BONO_RECOMPRA+"."+BusinessLogicParameters.DB_COLUM_BRC_EVENT_ID + "=" + pEventId + " AND " +
						BusinessLogicParameters.DB_TABLA_BONO_RECOMPRA+"."+BusinessLogicParameters.DB_COLUM_BRC_ESTADO + "<>'" +BusinessLogicParameters.BONO_RECOMPRA_ESTADO_INACTIVO+"'";
		System.out.println("Sentencia->"+select_+from_+where_);		
		List res_;
		int reintentos_=1;
		boolean pending_=true;
		while( reintentos_<=3){
			try {
				res_ = DBUtil.select(select_, from_, where_, RegistersVo.class);
				Iterator iterator_=res_.iterator();
				numReg_=(RegistersVo)iterator_.next();
				return numReg_.getNumberReg();
				//pending_=false;
			} catch (ConnectionFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				reintentos_++;
				if (reintentos_>3)
					throw e;
			} catch (DBAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				reintentos_++;
				if (reintentos_>3)
					throw e;				
			}catch (Exception e){
				e.printStackTrace();
				reintentos_++;
				if (reintentos_>3)
					throw e;				
			}catch (Throwable t){
				t.printStackTrace();
				reintentos_++;
				if (reintentos_>3)
					throw t;				
			}
		}
		throw new Exception("Error searching bonus en DB");
	}

	public static int getBonus(String pBonusNumber, String pEventId) throws DBAccessException, ConnectionFailedException, Exception, Throwable{
		boolean answer_=false;
		RegistersVo numReg_=new RegistersVo();
		numReg_.setNumberReg(0); 
		String select_ =
				"COUNT(*) numberReg ";
		String from_ =
			BusinessLogicParameters.DB_TABLA_BONO_RECOMPRA;
		String where_= BusinessLogicParameters.DB_TABLA_BONO_RECOMPRA+"."+BusinessLogicParameters.DB_COLUM_BRC_BONUS_NUM+"='"+pBonusNumber + "' AND " +
					   BusinessLogicParameters.DB_TABLA_BONO_RECOMPRA+"."+BusinessLogicParameters.DB_COLUM_BRC_EVENT_ID + "=" + pEventId;
		System.out.println("Sentencia->"+select_+from_+where_);		
		List res_;
		int reintentos_=1;
		boolean pending_=true;
		while( reintentos_<=2){
			try {
				res_ = DBUtil.select(select_, from_, where_, RegistersVo.class);
				Iterator iterator_=res_.iterator();
				numReg_=(RegistersVo)iterator_.next();
				return numReg_.getNumberReg();
				//pending_=false;
			} catch (ConnectionFailedException e) {
				e.printStackTrace();
				reintentos_++;
				if (reintentos_>2)
					throw e;
			} catch (DBAccessException e) {
				e.printStackTrace();
				reintentos_++;
				if (reintentos_>2)
					throw e;				
			}catch (Exception e){
				e.printStackTrace();
				reintentos_++;
				if (reintentos_>2)
					throw e;				
			}catch (Throwable t){
				t.printStackTrace();
				reintentos_++;
				if (reintentos_>2)
					throw t;				
			}
		}
		throw new Exception("Error searching bonus en DB");
	}
	
	public static BonusRegisterVo validateBonus(String pBonusNumber) throws DBAccessException, ConnectionFailedException, Exception, Throwable{
		BonusRegisterVo answer_ = null;
		//RegistersVo numReg_=new RegistersVo();
		//numReg_.setNumberReg(0); 
		String select_ =
				BusinessLogicParameters.DB_TABLA_BONO_RECOMPRA+"."+BusinessLogicParameters.DB_COLUM_BRC_ID_CLIENTE+" customerId," +
				BusinessLogicParameters.DB_TABLA_BONO_RECOMPRA+"."+BusinessLogicParameters.DB_COLUM_BRC_BONUS_VALUE+" bonusValue," +
				BusinessLogicParameters.DB_TABLA_BONO_RECOMPRA+"."+BusinessLogicParameters.DB_COLUM_BRC_ESTADO+" status," +
				BusinessLogicParameters.DB_TABLA_BONO_RECOMPRA+"."+BusinessLogicParameters.DB_COLUM_START_USE_DATE+" startUseDate," +
				BusinessLogicParameters.DB_TABLA_BONO_RECOMPRA+"."+BusinessLogicParameters.DB_COLUM_EXPIRATION_DATE+" expirationDate";
		String from_ =
			BusinessLogicParameters.DB_TABLA_BONO_RECOMPRA;
		String where_= BusinessLogicParameters.DB_TABLA_BONO_RECOMPRA+"."+BusinessLogicParameters.DB_COLUM_BRC_BONUS_NUM+"='"+pBonusNumber + "'"; // AND " +
					   //BusinessLogicParameters.DB_TABLA_BONO_RECOMPRA+"."+BusinessLogicParameters.DB_COLUM_BRC_ID_CLIENTE + "=" + pCustomerId;
		System.out.println("Sentencia->"+select_+from_+where_);		
		List res_;
		int reintentos_=1;
		boolean pending_=true;
		while( reintentos_<=2){
			try {
				res_ = DBUtil.select(select_, from_, where_, BonusRegisterVo.class);
				Iterator iterator_=res_.iterator();
				answer_=(BonusRegisterVo)iterator_.next();
				return answer_;
				//pending_=false;
			} catch (ConnectionFailedException e) {
				e.printStackTrace();
				reintentos_++;
				if (reintentos_>2)
					throw e;
			} catch (DBAccessException e) {
				e.printStackTrace();
				reintentos_++;
				if (reintentos_>2)
					throw e;				
			}catch (Exception e){
				e.printStackTrace();
				reintentos_++;
				if (reintentos_>2)
					throw e;				
			}catch (Throwable t){
				t.printStackTrace();
				reintentos_++;
				if (reintentos_>2)
					throw t;				
			}
		}
		throw new Exception("Error searching bonus en DB");
	}
	
	public static List getCustomerTypes(String pIdCustomer) throws DBAccessException, CustomerNotFoundException{
		CustomerTypesVo answer_=null;
		RegistersVo numReg_=new RegistersVo();
		numReg_.setNumberReg(0);
		String select_ = BusinessLogicParameters.DB_COLUM_TCL_CODIGO + " code";
		String from_ =
				BusinessLogicParameters.DB_TABLA_CLIENTE_X_TIPO_CLIENTE + "," + BusinessLogicParameters.DB_TABLA_TIPO_CLIENTE;//+","+BusinessLogicParameters.DB_TABLA_CLIENTE;
		String where_= BusinessLogicParameters.DB_COLUM_TCL_ID_TIPO_CLIENTE+"="+BusinessLogicParameters.DB_COLUM_CTC_ID_TIPO_CLIENTE+" AND "+
					   BusinessLogicParameters.DB_COLUM_CTC_ID_CLIENTE+"="+pIdCustomer;
		System.out.println("Sentencia->"+select_+from_+where_);		
		List res_;
		res_ = DBUtil.select(select_, from_, where_, CustomerTypesVo.class);
		return res_;
	}	

	public static BonusRegisterVo getCustomerBonus(String pIdCustomer, String pNumBono) throws DBAccessException, BonusNotFoundException{
		BonusRegisterVo answer_=null;
		//RegistersVo numReg_=new RegistersVo();
		//numReg_.setNumberReg(0);
		String select_ = BusinessLogicParameters.DB_COLUM_LBL_VALOR + " value2, "+
						 "("+BusinessLogicParameters.DB_COLUM_LBL_VALOR+"-"+BusinessLogicParameters.DB_COLUM_LBL_VALOR_USADO+")" + " balance, "+
						 BusinessLogicParameters.DB_COLUM_LBL_ESTADO + " status";						 
		String from_ = 	BusinessLogicParameters.DB_TABLA_BONOS_LEALTAD;//+","+BusinessLogicParameters.DB_TABLA_CLIENTE;
		String where_ = BusinessLogicParameters.DB_COLUM_LBL_ID_CLIENTE + "=" + pIdCustomer + " AND " +
						BusinessLogicParameters.DB_COLUM_LBL_NUMERO + "=" + pNumBono;
		System.out.println("Sentencia->"+select_+from_+where_);		
		List res_;
		res_ = DBUtil.select(select_, from_, where_, BonusRegisterVo.class);
		if (res_!=null && res_.size()==0){
			throw new BonusNotFoundException("Bono no encontrado en la db");
		}
		answer_=(BonusRegisterVo)res_.get(0);
		return answer_;
	}

	public static boolean updateBonus(String pIdCustomer, String pNumBono, String pUsedAmt, String pStore, String pTerminal, String pTrx, String pDateTime, String pOperator) throws DBAccessException, BonusNotFoundException{
		boolean answer_=true;
		String sentence_ =  " UPDATE " + BusinessLogicParameters.DB_TABLA_BONOS_LEALTAD + 
							" SET " + BusinessLogicParameters.DB_COLUM_LBL_ESTADO + "='" + getBusinessLogicParam(BusinessLogicParameters.ID_USED_BONOS_STATUS).getValue()+"',"+
									BusinessLogicParameters.DB_COLUM_LBL_VALOR_USADO + "=" + pUsedAmt+","+
									BusinessLogicParameters.DB_COLUM_LBL_FECHA_USO + "=" + BusinessLogicParameters.PAR_CURRENT_TIMESTAMP_FUNTION_NAME + ", " +
									BusinessLogicParameters.DB_COLUM_LBL_STORE+ "='" + pStore+"', "+			
									BusinessLogicParameters.DB_COLUM_LBL_TERMINAL + "='" + pTerminal +"', "+
									BusinessLogicParameters.DB_COLUM_LBL_TRX + "='" + pTrx +"', "+
									BusinessLogicParameters.DB_COLUM_LBL_POS_FECHA_HORA	 + "=TIMESTAMP('20" + pDateTime + "'), "+
									BusinessLogicParameters.DB_COLUM_LBL_OPERATOR + "='" + pOperator +"'";

		String filter_ =	BusinessLogicParameters.DB_COLUM_LBL_ID_CLIENTE+"="+pIdCustomer+" AND "+
							BusinessLogicParameters.DB_COLUM_LBL_NUMERO+"="+pNumBono;

		int dbUpdateAnswer_ = SqlUtils.actualizaTabla(
						sentence_ +" WHERE "+filter_,
						PosRequestsHandler.getCnxClassName(""),
						PosRequestsHandler.getCnxUrl(""),
						PosRequestsHandler.getCnxUserID(""),
						PosRequestsHandler.getCnxPassword("")
						);
		if(dbUpdateAnswer_>0){ // Registro fue actualizado satisfactoriamente o ya hab�a sido actualizado
			System.out.println("Bono actualizado satisfactoriamente");
		}else if (dbUpdateAnswer_==0){
			System.out.println("Bono no encontrado para actualizaci�n en la DB");
			throw new BonusNotFoundException("Bono no encontrado para actualizaci�n en la DB");
		}else{ //  Hubo problemas con la base de datos
			System.out.println("Error actualizando registro de puntos");
			throw new DBAccessException("Error actulizando bono en la DB");
		}
		return answer_;
	}

	public static boolean existBonusForCustomer(int pYear, int pMoth, String pCustomerId) throws DBAccessException{
		boolean answer_=false;
		CustomerVo custReg_;
		//numReg_.setNumberReg(0);

		String select_ = BusinessLogicParam.DB_COLUM_BEL_ANO + " id," + 
						 BusinessLogicParam.DB_COLUM_CL_ID_TIPOCLIENTE + " typeId," + 
						 BusinessLogicParameters.DB_COLUM_CL_NOMBRES + " firstName," + 
						 BusinessLogicParameters.DB_COLUM_CL_APELLIDOS + " lastName";
		String from_ =
						BusinessLogicParam.DB_TABLA_CLIENTE;
		String where_ = BusinessLogicParameters.DB_COLUM_CL_CEDULA+"="+pNumDoc+"";
		System.out.println("Sentencia->"+select_+from_+where_);		
		List res_;
		//int reintentos_=1;
		//boolean pending_=true;
		//while(pending_ && reintentos_<=pNumRep){
			//try {
				res_ = DBUtil.select(select_, from_, where_, CustomerVo.class);
				Iterator iterator_=res_.iterator();
				if (iterator_.hasNext()){
					custReg_=(CustomerVo)iterator_.next();
					pCustomer.setId(custReg_.getId());
					pCustomer.setTypeId(custReg_.getTypeId());
					System.out.println("custReg_.getFirstName()->"+custReg_.getFirstName());
					pCustomer.setFirstName(custReg_.getFirstName());
					System.out.println("custReg_.getLastName()->"+custReg_.getLastName());
					pCustomer.setLastName(custReg_.getLastName());
					return true;
				}else
					return false;
				
				//pending_=false;
//			} catch (ConnectionFailedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (DBAccessException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}catch (Exception e){
//				e.printStackTrace();
//			}catch (Throwable t){
//				t.printStackTrace();
//			}finally{
//				reintentos_++;
//			}
		//}
		//if (reintentos_>3)
			//throw new DBAccessException("No se pudo consulta en la DB");
		//return numReg_.getNumberReg()>0;
	}	
	
	public static ParameterVo loadParameters() throws DBAccessException{
		//boolean answer_=false;
		ParameterVo param_=null;
		//numReg_.setNumberReg(0);
		String select_ = "*";
		String from_ = BusinessLogicParameters.DB_TABLA_LOYALTY_PARAMETERS;
		String where_ = BusinessLogicParameters.DB_COLUM_LY_VENCIMIENTO+" is not null";
		System.out.println("Sentencia->"+select_+from_+where_);		
		List res_;
		//int reintentos_=1;
		//boolean pending_=true;
		//while(pending_ && reintentos_<=pNumRep){
			//try {
				res_ = DBUtil.select(select_, from_, where_, ParameterVo.class);
				Iterator iterator_=res_.iterator();
				if (iterator_.hasNext()){
					param_=(ParameterVo)iterator_.next();
					//answer_ = true;
				}else
					throw new DBAccessException("No se pudo cargar parametros del m�dulo de lealtad");
		//}
		//if (reintentos_>3)
		//	throw new DBAccessException("No se pudo cargar parametros del m�dulo de lealtad");
		return param_;
	}	
*/	
	/*
	public static boolean existBonusForCustomer(int pYear,int pMonth, String pCustomerId) throws DBAccessException{
		boolean answer_=false;
		ItemDescriptionVo numReg_=new ItemDescriptionVo();
		numReg_.setNumberReg(0);

		String select_ = "count(*) numberReg";
		String from_ =
				BusinessLogicParam.DB_TABLA_BONO_ELECTRO;
		String where_=	BusinessLogicParam.DB_COLUM_BEL_ANO+"="+pYear+" AND " +
						BusinessLogicParam.DB_COLUM_BEL_MES+"="+pMonth+" AND " +
						BusinessLogicParam.DB_COLUM_BEL_ID_CLIENTE+"="+pCustomerId+"";
		System.out.println("Sentencia->"+select_+from_+where_);		
		List res_;
		int reintentos_=1;
		boolean pending_=true;
		while(pending_ && reintentos_<=3){
			try {
				res_ = DBUtil.select(select_, from_, where_, ItemDescriptionVo.class);
				Iterator iterator_=res_.iterator();
				numReg_=(ItemDescriptionVo)iterator_.next();
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
		if (reintentos_>3)
			throw new DBAccessException("No se pudo consulta en la DB");
		return numReg_.getNumberReg()>0;
	}
	*/
	
	/*
	public static boolean updateRedemptionCustomerPoints(TransaccionVO pTrxVo) throws DBAccessException{
		// Insertar en registro de la tabla llegadas_del_POS
		//try{
				
			//String tipoVariedad = pPayment.getTipoVariedad()!=null?pPayment.getTipoVariedad().trim():"";
			String sign_ = "+";
			//if (pOption==2) // acumulacion puntos
			//	sign_ = "-";
			String date_ = pTrxVo.getFecha_hora_trx_pos()!=null?pTrxVo.getFecha_hora_trx_pos().trim():"0";
			date_ = date_.length()>=12?date_.substring(0,10):Relleno.carga(date_,10,'0',0,10);
			String store_ = pTrxVo.getTienda()!=null?pTrxVo.getTienda().trim():"0";
			store_ = store_.length()>3?store_.substring(store_.length()-3,store_.length()):Relleno.carga(store_,3,'0',0,3);
			String terminal_ = pTrxVo.getTerminal()!=null?pTrxVo.getTerminal().trim():"0";
			terminal_ = terminal_.length()>3?terminal_.substring(terminal_.length()-3,terminal_.length()):Relleno.carga(terminal_,3,'0',0,3);
			String trx_ = pTrxVo.getNum_factura()!=null?pTrxVo.getNum_factura().trim():"0";
			trx_ = trx_.length()>3?trx_.substring(trx_.length()-3,trx_.length()):Relleno.carga(trx_,3,'0',0,3);
			String strTrxKey_ = date_ + store_ + terminal_ + trx_;
			
			String sentence_ =  " UPDATE " + BusinessLogicParameters.DB_TABLA_PUNTOS_CLIENTE + 
								" SET " + BusinessLogicParameters.DB_COLUM_LPC_TOTAL_REDEM_POINTS + "=" + BusinessLogicParameters.DB_COLUM_LPC_TOTAL_REDEM_POINTS + sign_ + pTrxVo.getPuntos_redimidos_trx()+","+
										BusinessLogicParameters.DB_COLUM_LPC_TOTAL_POINTS + "=" + BusinessLogicParameters.DB_COLUM_LPC_TOTAL_POINTS + "+" +pTrxVo.getPuntos_acum_trx()+","+
										BusinessLogicParameters.DB_COLUM_LPC_LAST_TRX_REDEM_POINTS + "=" + pTrxVo.getPuntos_redimidos_trx()+","+
										BusinessLogicParameters.DB_COLUM_LPC_TRX_KEY_1 + "='" +strTrxKey_+"',"+
										BusinessLogicParameters.DB_COLUM_LPC_TRX_KEY_2 + "=" + BusinessLogicParameters.DB_COLUM_LPC_TRX_KEY_1+","+
										BusinessLogicParameters.DB_COLUM_LPC_TRX_KEY_3 + "=" + BusinessLogicParameters.DB_COLUM_LPC_TRX_KEY_2+","+
										BusinessLogicParameters.DB_COLUM_LPC_TRX_KEY_4 + "=" + BusinessLogicParameters.DB_COLUM_LPC_TRX_KEY_3+","+
										BusinessLogicParameters.DB_COLUM_LPC_TRX_KEY_5 + "=" + BusinessLogicParameters.DB_COLUM_LPC_TRX_KEY_4;
										  //BusinessLogicParameters.DB_COLUM_LPC_PERIOD_POINTS + "=" + BusinessLogicParameters.DB_COLUM_LPC_PERIOD_POINTS + sign_ + pTrxVo.getPuntos_periodo();
									  
			String filter_ =	BusinessLogicParameters.DB_COLUM_LPC_ID_CLIENTE+"="+pTrxVo.getNumero_documento()+" AND "+
								BusinessLogicParameters.DB_COLUM_LPC_COMPANY_ID+"=1 AND " +
								BusinessLogicParameters.DB_COLUM_LPC_TRX_KEY_1 + "<>'" + strTrxKey_+"' AND "+
								BusinessLogicParameters.DB_COLUM_LPC_TRX_KEY_2 + "<>'" + strTrxKey_+"' AND "+
								BusinessLogicParameters.DB_COLUM_LPC_TRX_KEY_3 + "<>'" + strTrxKey_+"' AND "+
								BusinessLogicParameters.DB_COLUM_LPC_TRX_KEY_4 + "<>'" + strTrxKey_+"' AND "+
								BusinessLogicParameters.DB_COLUM_LPC_TRX_KEY_5 + "<>'" + strTrxKey_+"'";								
				//BusinessLogicParameters.DB_COLUM_PCL_PERMANENT_POINTS + "," + 
				//BusinessLogicParameters.DB_COLUM_PCL_PERIOD_POINTS	+ 
			//int reintentos_=0;	
			//boolean pending_=true;
			//while(pending_ && reintentos_<=3){
				
				int dbUpdateAnswer_ = SqlUtils.actualizaTabla(
								sentence_ +" WHERE "+filter_,
								PosRequestsHandler.getCnxClassName(""),
								PosRequestsHandler.getCnxUrl(""),
								PosRequestsHandler.getCnxUserID(""),
								PosRequestsHandler.getCnxPassword("")
								);
				if(dbUpdateAnswer_>=0){ // Registro fue actualizado satisfactoriamente o ya hab�a sido actualizado
						if (!insertCustomerPointsTrxRegister(pTrxVo))
							throw new DBAccessException("Error insertando trx de lealtad");										
				}else{ //  Hubo problemas con la base de datos
					System.out.println("Error actualizando registro de puntos");
					throw new DBAccessException("Error actualizando registro de puntos");
				}				
				
//				reintentos_++;
			//}		
//			if (pending_)
//				return false;
			return true;		
	
//		}catch(Exception e){
//			
//			return false;
//		}catch(Throwable t){			
//			t.printStackTrace();
//			return false;
//		}
		//return true;
	}	*/	

	/*
	public static boolean insertCustomerBonus(int pYear, int pMonth, String pCustomerId, String pChain, String pStore, String pTerminal, 
			                                           String pOperator, String pTicketNumber, String pPOSDateTime, String pBonusValue) throws DBAccessException{
		// Insertar en registro de la tabla llegadas_del_POS
		try{
			//String tipoVariedad = pPayment.getTipoVariedad()!=null?pPayment.getTipoVariedad().trim():""; 
			String sentencia_ = "INSERT INTO " + BusinessLogicParam.DB_TABLA_BONO_ELECTRO + " (" + 
			BusinessLogicParam.DB_COLUM_BEL_ANO + "," +
			BusinessLogicParam.DB_COLUM_BEL_MES + "," + 
			BusinessLogicParam.DB_COLUM_BEL_ID_CLIENTE + "," +
			BusinessLogicParam.DB_COLUM_BEL_VALOR_BONO + "," +
			BusinessLogicParam.DB_COLUM_BEL_CADENA + "," +
			BusinessLogicParam.DB_COLUM_BEL_TIENDA + "," + 
			BusinessLogicParam.DB_COLUM_BEL_NUM_TERMINAL + "," +
			BusinessLogicParam.DB_COLUM_BEL_OPERADOR + "," +
			BusinessLogicParam.DB_COLUM_BEL_NUM_FACTURA + "," + 
			BusinessLogicParam.DB_COLUM_BEL_FECHA_HORA_TRX_POS	+
			") VALUES (" + 
					pYear +"," + 
					pMonth +"," +
					pCustomerId + "," +
					pBonusValue + "," +
					"'" + pChain + "'," +
					"'" + pStore + "'," +
					"'" + pTerminal + "'," +					

					"'" + pOperator + "'," +
					"'" + pTicketNumber + "'," +
					//"'" + ComfandiPosTransaction.getBusinessLogicParam(BusinessLogicParam.ID_PAR_CENTURY).getValue().trim() + pPOSDateTime + "'" +					
					"'" + pPOSDateTime + "'" +
			")";
			int reintentos_=0;	
			boolean pending_=true;
			while(pending_ && reintentos_<=3){
				pending_ = !SqlUtils.insertaRegistro(
					sentencia_,
					PosRequestsHandler.getCnxClassName(""),
					PosRequestsHandler.getCnxUrl(""),
					PosRequestsHandler.getCnxUserID(""),
					PosRequestsHandler.getCnxPassword("")
				);
				
			reintentos_++;
			}		
			if (pending_)
				return false;
			return true;		
		}catch(Exception e){
			
			return false;
		}catch(Throwable t){			
			t.printStackTrace();
			return false;
		}
		//return true;
	}
	*/
	
	
	/*
	public static boolean activateBonus(String pBonusNumber, String pEventId) throws DBAccessException{
		// Insertar en registro de la tabla llegadas_del_POS
		try{
			String sentence_ =  " UPDATE " + BusinessLogicParameters.DB_TABLA_BONO_RECOMPRA + 
								" SET " + BusinessLogicParameters.DB_COLUM_BRC_ESTADO + "='" + BusinessLogicParameters.BONO_RECOMPRA_ESTADO_ACTIVO+"'";
								
			String filter_ = BusinessLogicParameters.DB_TABLA_BONO_RECOMPRA+"."+BusinessLogicParameters.DB_COLUM_BRC_BONUS_NUM+"='"+pBonusNumber + "' AND " +
							 BusinessLogicParameters.DB_TABLA_BONO_RECOMPRA+"."+BusinessLogicParameters.DB_COLUM_BRC_EVENT_ID + "=" + pEventId + " AND " +
							 BusinessLogicParameters.DB_TABLA_BONO_RECOMPRA+"."+BusinessLogicParameters.DB_COLUM_BRC_ESTADO + "='" +BusinessLogicParameters.BONO_RECOMPRA_ESTADO_INACTIVO+"'";
			int reintentos_=0;	
			boolean pending_=true;
			while(pending_ && reintentos_<=3){
				
				int dbUpdateAnswer_ = SqlUtils.actualizaTabla(
								sentence_ +" WHERE "+filter_,
								PosRequestsHandler.getCnxClassName(""),
								PosRequestsHandler.getCnxUrl(""),
								PosRequestsHandler.getCnxUserID(""),
								PosRequestsHandler.getCnxPassword("")
								);
				if(dbUpdateAnswer_>=0){ // El bono fue activado satisfactoriamente
						pending_=false;
																	
				}else{ //  Hubo problemas con la base de datos
					System.out.println("Error actualizando estado de bono recompra");
					throw new DBAccessException("Error actualizando estado de bono recompra");
				}				
				
				reintentos_++;
			}		
			if (pending_)
				return false;
			return true;		
	
		}catch(Exception e){
			return false;
		}catch(Throwable t){			
			t.printStackTrace();
			return false;
		}
	}
	
	public static boolean updateStatusBonus(String pBonusNumber, String pStatus) throws DBAccessException{
		// Insertar en registro de la tabla llegadas_del_POS
		try{
			String sentence_ =  " UPDATE " + BusinessLogicParameters.DB_TABLA_BONO_RECOMPRA + 
								" SET " + BusinessLogicParameters.DB_COLUM_BRC_ESTADO + "='" + pStatus + "'";
								
			String filter_ = BusinessLogicParameters.DB_TABLA_BONO_RECOMPRA+"."+BusinessLogicParameters.DB_COLUM_BRC_BONUS_NUM+"='"+pBonusNumber + "'";
			int reintentos_=0;	
			boolean pending_=true;
			while(pending_ && reintentos_<=3){
				
				int dbUpdateAnswer_ = SqlUtils.actualizaTabla(
								sentence_ +" WHERE "+filter_,
								PosRequestsHandler.getCnxClassName(""),
								PosRequestsHandler.getCnxUrl(""),
								PosRequestsHandler.getCnxUserID(""),
								PosRequestsHandler.getCnxPassword("")
								);
				if(dbUpdateAnswer_>=0){ // El bono fue activado satisfactoriamente
					pending_=false;
				}else{ //  Hubo problemas con la base de datos
					System.out.println("Error actualizando estado de bono recompra");
					throw new DBAccessException("Error actualizando estado de bono recompra");
				}				
				reintentos_++;
			}		
			if (pending_)
				return false;
			return true;		
	
		}catch(Exception e){
			return false;
		}catch(Throwable t){			
			t.printStackTrace();
			return false;
		}
	}
		
	public static boolean blockBonus(String pBonusNumber, String pStore, String pTerminal, String pTrxNumber, String pTrxDateTime, 
											String pOperator) throws DBAccessException{
		// Insertar en registro de la tabla llegadas_del_POS
		try{
			String sentence_ =  " UPDATE " + BusinessLogicParameters.DB_TABLA_BONO_RECOMPRA + 
								" SET " + BusinessLogicParameters.DB_COLUM_BRC_ESTADO + "='" + BusinessLogicParameters.BONO_RECOMPRA_ESTADO_BLOQUEADO + "', " +
								BusinessLogicParameters.DB_COLUM_BRC_USE_STORE_ID + "='" + pStore + "', "+
								BusinessLogicParameters.DB_COLUM_BRC_USE_TERMINAL_ID + "='" + pTerminal + "', "+
								BusinessLogicParameters.DB_COLUM_BRC_USE_TRANSACTION_ID + "='" + pTrxNumber + "', "+
								BusinessLogicParameters.DB_COLUM_BRC_USE_TRANSACTION_DATE + "=TIMESTAMP('"+getBusinessLogicParam(BusinessLogicParam.ID_PAR_CENTURY).getValue()+pTrxDateTime+"'), "+
								BusinessLogicParameters.DB_COLUM_BRC_USE_OPERATOR_ID + "='" + pOperator + "'";								
								
			String filter_ = BusinessLogicParameters.DB_TABLA_BONO_RECOMPRA+"."+BusinessLogicParameters.DB_COLUM_BRC_BONUS_NUM+"='"+pBonusNumber + "' AND " +
							 BusinessLogicParameters.DB_COLUM_BRC_ESTADO + "='" + BusinessLogicParameters.BONO_RECOMPRA_ESTADO_ACTIVO + "'";
			int reintentos_=0;	
			boolean pending_=true;
			while(pending_ && reintentos_<=3){
				
				int dbUpdateAnswer_ = SqlUtils.actualizaTabla(
								sentence_ +" WHERE "+filter_,
								PosRequestsHandler.getCnxClassName(""),
								PosRequestsHandler.getCnxUrl(""),
								PosRequestsHandler.getCnxUserID(""),
								PosRequestsHandler.getCnxPassword("")
								);
				if(dbUpdateAnswer_>=0){ // El bono fue activado satisfactoriamente
					pending_=false;
				}else{ //  Hubo problemas con la base de datos
					System.out.println("Error actualizando estado de bono recompra");
					throw new DBAccessException("Error actualizando estado de bono recompra");
				}				
				reintentos_++;
			}		
			if (pending_)
				return false;
			return true;		
	
		}catch(Exception e){
			return false;
		}catch(Throwable t){			
			t.printStackTrace();
			return false;
		}
	}	

	public static boolean confirmBonusPayment(String pBonusNumber, String pStore, String pTerminal, String pTrxNumber, String pTrxDateTime) throws DBAccessException{
		// Insertar en registro de la tabla llegadas_del_POS
		try{
			String sentence_ =  " UPDATE " + BusinessLogicParameters.DB_TABLA_BONO_RECOMPRA + 
								" SET " + BusinessLogicParameters.DB_COLUM_BRC_ESTADO + "='" + BusinessLogicParameters.BONO_RECOMPRA_ESTADO_USADO_TOTAL + "'";
									
								
			String filter_ = BusinessLogicParameters.DB_TABLA_BONO_RECOMPRA+"."+BusinessLogicParameters.DB_COLUM_BRC_BONUS_NUM+"='"+pBonusNumber + "' AND " +
							 BusinessLogicParameters.DB_COLUM_BRC_USE_STORE_ID + "='" + pStore + "' AND " +
							 BusinessLogicParameters.DB_COLUM_BRC_USE_TERMINAL_ID + "='" + pTerminal + "' AND " +
							 BusinessLogicParameters.DB_COLUM_BRC_USE_TRANSACTION_ID + "='" + pTrxNumber + "' AND " +
							 BusinessLogicParameters.DB_COLUM_BRC_USE_TRANSACTION_DATE + "=TIMESTAMP('"+getBusinessLogicParam(BusinessLogicParam.ID_PAR_CENTURY).getValue()+pTrxDateTime+"')";
							 //BusinessLogicParameters.DB_COLUM_BRC_USE_OPERATOR_ID + "='" + pOperator + "'";			
			int reintentos_=0;	
			boolean pending_=true;
			while(pending_ && reintentos_<=3){
				
				int dbUpdateAnswer_ = SqlUtils.actualizaTabla(
								sentence_ +" WHERE "+filter_,
								PosRequestsHandler.getCnxClassName(""),
								PosRequestsHandler.getCnxUrl(""),
								PosRequestsHandler.getCnxUserID(""),
								PosRequestsHandler.getCnxPassword("")
								);
				if(dbUpdateAnswer_>0){ // El bono fue activado satisfactoriamente
					pending_=false;
				}else{ //  Hubo problemas con la base de datos
					System.out.println("Error actualizando estado de bono recompra");
					throw new DBAccessException("Error actualizando estado de bono recompra");
				}				
				reintentos_++;
			}		
			if (pending_)
				return false;
			return true;		
	
		}catch(Exception e){
			return false;
		}catch(Throwable t){			
			t.printStackTrace();
			return false;
		}
	}
	
	public static boolean refundReverseBonusPayment(String pBonusNumber, String pStore, String pTerminal, String pTrxNumber, String pTrxDateTime, boolean pIsReverse) throws DBAccessException{
		// Insertar en registro de la tabla llegadas_del_POS
		try{
			String sentence_ =  " UPDATE " + BusinessLogicParameters.DB_TABLA_BONO_RECOMPRA + 
								" SET " + BusinessLogicParameters.DB_COLUM_BRC_ESTADO + "='" + BusinessLogicParameters.BONO_RECOMPRA_ESTADO_ACTIVO + "'";
									
								
			String filter_ = BusinessLogicParameters.DB_TABLA_BONO_RECOMPRA+"."+BusinessLogicParameters.DB_COLUM_BRC_BONUS_NUM+"='"+pBonusNumber + "' AND " +
							 BusinessLogicParameters.DB_COLUM_BRC_USE_STORE_ID + "='" + pStore + "' AND " +
							 BusinessLogicParameters.DB_COLUM_BRC_USE_TERMINAL_ID + "='" + pTerminal + "' AND " +
							 BusinessLogicParameters.DB_COLUM_BRC_USE_TRANSACTION_ID + "='" + pTrxNumber + "' AND " +
							 BusinessLogicParameters.DB_COLUM_BRC_USE_TRANSACTION_DATE + "=TIMESTAMP('"+getBusinessLogicParam(BusinessLogicParam.ID_PAR_CENTURY).getValue()+pTrxDateTime+"') AND " +
							 BusinessLogicParameters.DB_COLUM_BRC_ESTADO + "='" + BusinessLogicParameters.BONO_RECOMPRA_ESTADO_BLOQUEADO + "'";
							 //BusinessLogicParameters.DB_COLUM_BRC_USE_OPERATOR_ID + "='" + pOperator + "'";			
			int reintentos_=0;	
			boolean pending_=true;
			while(pending_ && reintentos_<=3){
				
				int dbUpdateAnswer_ = SqlUtils.actualizaTabla(
								sentence_ +" WHERE "+filter_,
								PosRequestsHandler.getCnxClassName(""),
								PosRequestsHandler.getCnxUrl(""),
								PosRequestsHandler.getCnxUserID(""),
								PosRequestsHandler.getCnxPassword("")
								);
				if(dbUpdateAnswer_>0){ // El bono fue activado satisfactoriamente
					pending_=false;
				}else if (!pIsReverse && dbUpdateAnswer_==0){
					pending_=false;
				}else{ //  Hubo problemas con la base de datos
					System.out.println("Error actualizando estado de bono recompra");
					throw new DBAccessException("Error actualizando estado de bono recompra");
				}				
				reintentos_++;
			}		
			if (pending_)
				return false;
			return true;		
	
		}catch(Exception e){
			return false;
		}catch(Throwable t){			
			t.printStackTrace();
			return false;
		}
	}
	
	public static boolean insertBonus(String pCustomerId, String pBonusNumber, String pBonusValue, String pEventId, String pEventIniDate,
									  String pEventFinDate, String pStore, String pTerminal, String pTrxNumber, String pTrxDateTime, String pOperator) throws BonusAlreadyExistsException{		
		// Insertar en registro de bono recompra
		try{
			String sentencia_ = "INSERT INTO " + BusinessLogicParameters.DB_TABLA_BONO_RECOMPRA + " (" + 
				BusinessLogicParameters.DB_COLUM_BRC_ID_CLIENTE + "," +
				BusinessLogicParameters.DB_COLUM_BRC_BONUS_NUM + "," +
				BusinessLogicParameters.DB_COLUM_BRC_BONUS_VALUE + "," +
				BusinessLogicParameters.DB_COLUM_BRC_EVENT_ID + "," +
				BusinessLogicParameters.DB_COLUM_BRC_EVENT_INI_DATE + "," +
				BusinessLogicParameters.DB_COLUM_BRC_EVENT_FIN_DATE + "," +
				BusinessLogicParameters.DB_COLUM_BRC_STORE_ID + "," +
				BusinessLogicParameters.DB_COLUM_BRC_TERMINAL_ID + "," +
				BusinessLogicParameters.DB_COLUM_BRC_TRANSACTION_ID + "," +
				BusinessLogicParameters.DB_COLUM_BRC_TRANSACTION_DATE + "," +
				BusinessLogicParameters.DB_COLUM_BRC_OPERATOR_ID + "," +
				BusinessLogicParameters.DB_COLUM_BRC_ESTADO +
			") VALUES (" + 
					pCustomerId.trim() + "," +
					"'" + pBonusNumber.trim() + "'," +
					pBonusValue.trim() + "," +
					pEventId.trim() + "," +
					"DATE('"+getBusinessLogicParam(BusinessLogicParam.ID_PAR_CENTURY).getValue() + pEventIniDate.substring(0,2) + "-" +
						  pEventIniDate.substring(2,4) + "-"+pEventIniDate.substring(4,6) +"'), "+
					"DATE('"+getBusinessLogicParam(BusinessLogicParam.ID_PAR_CENTURY).getValue() + pEventFinDate.substring(0,2) + "-" +
						  pEventFinDate.substring(2,4) + "-"+pEventFinDate.substring(4,6) +"'), "+					
					"'" + pStore + "', "+
					"'" + pTerminal + "', "+
					"'" + pTrxNumber + "', "+
					"TIMESTAMP('"+getBusinessLogicParam(BusinessLogicParam.ID_PAR_CENTURY).getValue()+pTrxDateTime+"'), "+
					"'" + pOperator + "',"+
					"'"+BusinessLogicParameters.BONO_RECOMPRA_ESTADO_INACTIVO+"'"+  // I: Inactivo
				")";
			int reintentos_=0;	
			boolean pending_=true;
			while(pending_ && reintentos_<2){
				System.out.println("Sentencia inseci�n->"+sentencia_);
				pending_ = !SqlUtils.insertaRegistro(
					sentencia_,
					PosRequestsHandler.getCnxClassName(""),
					PosRequestsHandler.getCnxUrl(""),
					PosRequestsHandler.getCnxUserID(""),
					PosRequestsHandler.getCnxPassword("")
				);
				if(pending_ && reintentos_==0 && getBonus(pBonusNumber,pEventId)>0)
					throw new BonusAlreadyExistsException("Bono ya hab�a sido insertado");
				reintentos_++;
			}		
			if (pending_)
				return false;
			return true;		
		}catch (BonusAlreadyExistsException e){
			throw e;
		}catch(Exception e){
			return false;
		}catch(Throwable t){			
			t.printStackTrace();
			return false;
		}
		//return true;
	}

	public static boolean insertBonusV2(String pCustomerId, String pBonusNumber, String pBonusValue, String pEventId, String pEventIniDate,
									  String pEventFinDate, String pStore, String pTerminal, String pTrxNumber, String pTrxDateTime, String pOperator,
									  String pBonusTypeId, String pStartUseDate, String pExpirationDate) throws BonusAlreadyExistsException{		
		// Insertar en registro de bono recompra
		try{
			String sentencia_ = "INSERT INTO " + BusinessLogicParameters.DB_TABLA_BONO_RECOMPRA + " (" + 
				BusinessLogicParameters.DB_COLUM_BRC_ID_CLIENTE + "," +
				BusinessLogicParameters.DB_COLUM_BRC_BONUS_NUM + "," +
				BusinessLogicParameters.DB_COLUM_BRC_BONUS_VALUE + "," +
				BusinessLogicParameters.DB_COLUM_BRC_EVENT_ID + "," +
				BusinessLogicParameters.DB_COLUM_BRC_EVENT_INI_DATE + "," +
				BusinessLogicParameters.DB_COLUM_BRC_EVENT_FIN_DATE + "," +
				BusinessLogicParameters.DB_COLUM_BRC_STORE_ID + "," +
				BusinessLogicParameters.DB_COLUM_BRC_TERMINAL_ID + "," +
				BusinessLogicParameters.DB_COLUM_BRC_TRANSACTION_ID + "," +
				BusinessLogicParameters.DB_COLUM_BRC_TRANSACTION_DATE + "," +
				BusinessLogicParameters.DB_COLUM_BRC_OPERATOR_ID + "," +
				BusinessLogicParameters.DB_COLUM_BRC_ESTADO + "," +
				BusinessLogicParameters.DB_COLUM_BRC_ID_TIPO_BONO + "," +
				BusinessLogicParameters.DB_COLUM_START_USE_DATE + "," +
				BusinessLogicParameters.DB_COLUM_EXPIRATION_DATE +
			") VALUES (" + 
					pCustomerId.trim() + "," +
					"'" + pBonusNumber.trim() + "'," +
					pBonusValue.trim() + "," +
					pEventId.trim() + "," +
					"DATE('"+getBusinessLogicParam(BusinessLogicParam.ID_PAR_CENTURY).getValue() + pEventIniDate.substring(0,2) + "-" +
						  pEventIniDate.substring(2,4) + "-"+pEventIniDate.substring(4,6) +"'), "+
					"DATE('"+getBusinessLogicParam(BusinessLogicParam.ID_PAR_CENTURY).getValue() + pEventFinDate.substring(0,2) + "-" +
						  pEventFinDate.substring(2,4) + "-"+pEventFinDate.substring(4,6) +"'), "+					
					"'" + pStore + "', "+
					"'" + pTerminal + "', "+
					"'" + pTrxNumber + "', "+
					"TIMESTAMP('"+getBusinessLogicParam(BusinessLogicParam.ID_PAR_CENTURY).getValue()+pTrxDateTime+"'), "+
					"'" + pOperator + "',"+
					"'"+BusinessLogicParameters.BONO_RECOMPRA_ESTADO_INACTIVO+"',"+  // I: Inactivo
					pBonusTypeId.trim()+","+  // I: Inactivo
					"DATE('"+pStartUseDate.substring(0,4) + "-" + pStartUseDate.substring(4,6) + "-" + pStartUseDate.substring(6,8) +"'), " +
					"DATE('"+pExpirationDate.substring(0,4) + "-" + pExpirationDate.substring(4,6) + "-" + pExpirationDate.substring(6,8) +"') " +					
			
			")";
			int reintentos_=0;	
			boolean pending_=true;
			while(pending_ && reintentos_<2){
				System.out.println("Sentencia inseci�n->"+sentencia_);
				pending_ = !SqlUtils.insertaRegistro(
					sentencia_,
					PosRequestsHandler.getCnxClassName(""),
					PosRequestsHandler.getCnxUrl(""),
					PosRequestsHandler.getCnxUserID(""),
					PosRequestsHandler.getCnxPassword("")
				);
				if(pending_ && reintentos_==0 && getBonus(pBonusNumber,pEventId)>0)
					throw new BonusAlreadyExistsException("Bono ya hab�a sido insertado");
				reintentos_++;
			}		
			if (pending_)
				return false;
			return true;		
		}catch (BonusAlreadyExistsException e){
			e.printStackTrace();
			throw e;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}catch(Throwable t){			
			t.printStackTrace();
			return false;
		}
		//return true;
	}	

	public static boolean createActivateBonus(String pCustomerId, String pBonusNumber, String pBonusValue, String pEventId, String pEventIniDate,
									  String pEventFinDate, String pStore, String pTerminal, String pTrxNumber, String pTrxDateTime, String pOperator) throws BonusAlreadyExistsException{		
		// Insertar en registro de bono recompra
		try{
			String sentencia_ = "INSERT INTO " + BusinessLogicParameters.DB_TABLA_BONO_RECOMPRA + " (" + 
				BusinessLogicParameters.DB_COLUM_BRC_ID_CLIENTE + "," +
				BusinessLogicParameters.DB_COLUM_BRC_BONUS_NUM + "," +
				BusinessLogicParameters.DB_COLUM_BRC_BONUS_VALUE + "," +
				BusinessLogicParameters.DB_COLUM_BRC_EVENT_ID + "," +
				BusinessLogicParameters.DB_COLUM_BRC_EVENT_INI_DATE + "," +
				BusinessLogicParameters.DB_COLUM_BRC_EVENT_FIN_DATE + "," +
				BusinessLogicParameters.DB_COLUM_BRC_STORE_ID + "," +
				BusinessLogicParameters.DB_COLUM_BRC_TERMINAL_ID + "," +
				BusinessLogicParameters.DB_COLUM_BRC_TRANSACTION_ID + "," +
				BusinessLogicParameters.DB_COLUM_BRC_TRANSACTION_DATE + "," +
				BusinessLogicParameters.DB_COLUM_BRC_OPERATOR_ID + "," +
				BusinessLogicParameters.DB_COLUM_BRC_ESTADO +
			") VALUES (" + 
					pCustomerId.trim() + "," +
					"'" + pBonusNumber.trim() + "'," +
					pBonusValue.trim() + "," +
					pEventId.trim() + "," +
					"DATE('"+getBusinessLogicParam(BusinessLogicParam.ID_PAR_CENTURY).getValue() + pEventIniDate.substring(0,2) + "-" +
						  pEventIniDate.substring(2,4) + "-"+pEventIniDate.substring(4,6) +"'), "+
					"DATE('"+getBusinessLogicParam(BusinessLogicParam.ID_PAR_CENTURY).getValue() + pEventFinDate.substring(0,2) + "-" +
						  pEventFinDate.substring(2,4) + "-"+pEventFinDate.substring(4,6) +"'), "+					
					"'" + pStore + "', "+
					"'" + pTerminal + "', "+
					"'" + pTrxNumber + "', "+
					"TIMESTAMP('"+getBusinessLogicParam(BusinessLogicParam.ID_PAR_CENTURY).getValue()+pTrxDateTime+"'), "+
					"'" + pOperator + "',"+
					"'"+BusinessLogicParameters.BONO_RECOMPRA_ESTADO_ACTIVO+"'"+  // I: Inactivo
				")";
			int reintentos_=0;	
			boolean pending_=true;
			while(pending_ && reintentos_<2){
				System.out.println("Sentencia inseci�n->"+sentencia_);
				pending_ = !SqlUtils.insertaRegistro(
					sentencia_,
					PosRequestsHandler.getCnxClassName(""),
					PosRequestsHandler.getCnxUrl(""),
					PosRequestsHandler.getCnxUserID(""),
					PosRequestsHandler.getCnxPassword("")
				);
				if(pending_ && reintentos_==0 && getBonus(pBonusNumber,pEventId)>0)
					throw new BonusAlreadyExistsException("Bono ya hab�a sido insertado");
				reintentos_++;
			}		
			if (pending_)
				return false;
			return true;		
		}catch (BonusAlreadyExistsException e){
			throw e;
		}catch(Exception e){
			return false;
		}catch(Throwable t){			
			t.printStackTrace();
			return false;
		}
		//return true;
	}
	
	public static boolean createActivateBonusV2(String pCustomerId, String pBonusNumber, String pBonusValue, String pEventId, String pEventIniDate,
									  String pEventFinDate, String pStore, String pTerminal, String pTrxNumber, String pTrxDateTime, String pOperator,
									  String pBonusTypeId, String pStartUseDate, String pExpirationDate) throws BonusAlreadyExistsException{		
		// Insertar en registro de bono recompra
		try{
			String sentencia_ = "INSERT INTO " + BusinessLogicParameters.DB_TABLA_BONO_RECOMPRA + " (" + 
				BusinessLogicParameters.DB_COLUM_BRC_ID_CLIENTE + "," +
				BusinessLogicParameters.DB_COLUM_BRC_BONUS_NUM + "," +
				BusinessLogicParameters.DB_COLUM_BRC_BONUS_VALUE + "," +
				BusinessLogicParameters.DB_COLUM_BRC_EVENT_ID + "," +
				BusinessLogicParameters.DB_COLUM_BRC_EVENT_INI_DATE + "," +
				BusinessLogicParameters.DB_COLUM_BRC_EVENT_FIN_DATE + "," +
				BusinessLogicParameters.DB_COLUM_BRC_STORE_ID + "," +
				BusinessLogicParameters.DB_COLUM_BRC_TERMINAL_ID + "," +
				BusinessLogicParameters.DB_COLUM_BRC_TRANSACTION_ID + "," +
				BusinessLogicParameters.DB_COLUM_BRC_TRANSACTION_DATE + "," +
				BusinessLogicParameters.DB_COLUM_BRC_OPERATOR_ID + "," +
				BusinessLogicParameters.DB_COLUM_BRC_ESTADO + "," +
				BusinessLogicParameters.DB_COLUM_BRC_ID_TIPO_BONO + "," +
				BusinessLogicParameters.DB_COLUM_START_USE_DATE + "," +
				BusinessLogicParameters.DB_COLUM_EXPIRATION_DATE +				
			") VALUES (" + 
					pCustomerId.trim() + "," +
					"'" + pBonusNumber.trim() + "'," +
					pBonusValue.trim() + "," +
					pEventId.trim() + "," +
					"DATE('"+getBusinessLogicParam(BusinessLogicParam.ID_PAR_CENTURY).getValue() + pEventIniDate.substring(0,2) + "-" +
						  pEventIniDate.substring(2,4) + "-"+pEventIniDate.substring(4,6) +"'), "+
					"DATE('"+getBusinessLogicParam(BusinessLogicParam.ID_PAR_CENTURY).getValue() + pEventFinDate.substring(0,2) + "-" +
						  pEventFinDate.substring(2,4) + "-"+pEventFinDate.substring(4,6) +"'), "+					
					"'" + pStore + "', "+
					"'" + pTerminal + "', "+
					"'" + pTrxNumber + "', "+
					"TIMESTAMP('"+getBusinessLogicParam(BusinessLogicParam.ID_PAR_CENTURY).getValue()+pTrxDateTime+"'), "+
					"'" + pOperator + "',"+
					"'"+BusinessLogicParameters.BONO_RECOMPRA_ESTADO_ACTIVO+"',"+  // A: Inactivo
					pBonusTypeId.trim()+","+ 
					"DATE('"+pStartUseDate.substring(0,4) + "-" + pStartUseDate.substring(4,6) + "-" + pStartUseDate.substring(6,8) +"'), " +
					"DATE('"+pExpirationDate.substring(0,4) + "-" + pExpirationDate.substring(4,6) + "-" + pExpirationDate.substring(6,8) +"') " +					
				")";
			int reintentos_=0;	
			boolean pending_=true;
			while(pending_ && reintentos_<2){
				System.out.println("Sentencia inseci�n->"+sentencia_);
				pending_ = !SqlUtils.insertaRegistro(
					sentencia_,
					PosRequestsHandler.getCnxClassName(""),
					PosRequestsHandler.getCnxUrl(""),
					PosRequestsHandler.getCnxUserID(""),
					PosRequestsHandler.getCnxPassword("")
				);
				if(pending_ && reintentos_==0 && getBonus(pBonusNumber,pEventId)>0)
					throw new BonusAlreadyExistsException("Bono ya hab�a sido insertado");
				reintentos_++;
			}		
			if (pending_)
				return false;
			return true;		
		}catch (BonusAlreadyExistsException e){
			throw e;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}catch(Throwable t){			
			t.printStackTrace();
			return false;
		}
		//return true;
	}

	public static BusinessLogicParam getBusinessLogicParam(String pIdParam) {
		try{
			Hashtable hash_ = getHashBusinessLogicParams();
			BusinessLogicParam answerParam_=null;
			if (hash_ != null) {
				answerParam_=(BusinessLogicParam)hash_.get(pIdParam);
			}
			if (answerParam_ == null) {
				loadBusinessParam(pIdParam);
				answerParam_=(BusinessLogicParam)getHashBusinessLogicParams().get(pIdParam);
			}
			if (answerParam_ == null) {	
				return new BusinessLogicParam("PAR_NO_FOUND","XX");
			} else {
				return answerParam_;
			}
		}catch(Exception e){		
			return new BusinessLogicParam("PAR_NO_FOUND","XX");
		}
	}

	public static Hashtable getHashBusinessLogicParams() {

		return aHashBusinessLogicParams;
	}
	
	private static void loadBusinessParam(String pParamId) {
		String paramValue_=null;										   	
		BusinessLogicParam param_=null; 
		try {
			ResourceBundle bundle_ = ResourceLocator.get(BUSINESSSETTINGS_FILENAME);
			paramValue_ = bundle_.getString(pParamId);
			if (aHashBusinessLogicParams==null)
				aHashBusinessLogicParams = new Hashtable();
			param_=new BusinessLogicParam();
			param_.setName(pParamId);
			param_.setValue(paramValue_.trim());		
			aHashBusinessLogicParams.put(pParamId,param_);
			aHashBusinessLogicParams.put(pParamId,param_);
			aHashBusinessLogicParams.put(pParamId,param_);
		}catch (NoSuchElementException ex1){
			System.out.println ("Elemento no definido correctamente en el archivo de parametros de negocio ");
			ex1.printStackTrace();
		}catch (Exception ex)  {
			System.out.println("ERROR: Parameter "+pParamId+" not properly defined in parameters file..." + ex);
		}		
	}	
	
	protected static void updateCustomerTypes(final String pSentencia) {
		Thread hilo_ = new Thread(new Runnable() {
			public void run() {
				boolean result_=SqlUtils.insertaRegistro(
					pSentencia,
					PosRequestsHandler.getCnxClassName(""),
					PosRequestsHandler.getCnxUrl(""),
					PosRequestsHandler.getCnxUserID(""),
					PosRequestsHandler.getCnxPassword("")
				);
				System.out.println("Resultado de la trx->"+result_);
			}
		});
		hilo_.start();
	}
	
	protected static String getCustomerTypesSentente(String pIdCustomer,String pCustTypes){
		String answer_;
		answer_ = "UPDATE " + BusinessLogicParameters.DB_TABLA_PUNTOS_CLIENTE +
				  "	SET " + BusinessLogicParameters.DB_COLUM_LPC_TIPOS_CLIENTE+"='"+pCustTypes +"'" +
				  " WHERE " + BusinessLogicParameters.DB_COLUM_LPC_COMPANY_ID+"=1"+
				  " AND "+ BusinessLogicParameters.DB_COLUM_LPC_ID_CLIENTE+"="+pIdCustomer;
				System.out.println("Sentencia->"+answer_);
		return answer_;					
			
	}*/
	
}
