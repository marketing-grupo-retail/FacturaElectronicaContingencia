package com.grpretail.comfandi.procesosautomaticos.facturaelectronica.db;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ramm.jwaf.dbutil.DBUtil;
import org.ramm.jwaf.sql.ConnectionFailedException;
import org.ramm.jwaf.sql.DBAccessException;

import com.asic.ac.utils.SqlUtils;
import com.asic.author.manager.request.PosRequestsHandler;
import com.grpretail.comfandi.business.utils.BusinessLogicParam;
import com.grpretail.comfandi.procesosautomaticos.facturaelectronica.vos.DiscountRegInDBVO;
import com.grpretail.comfandi.procesosautomaticos.facturaelectronica.vos.ItemEntryRegInDBVO;
import com.grpretail.comfandi.procesosautomaticos.facturaelectronica.vos.TenderRegInDBVO;
import com.grpretail.comfandi.procesosautomaticos.facturaelectronica.vos.TrxonlineTransactionInDBVO;
import com.grpretail.comfandi.procesosautomaticos.facturaelectronica.vos.TrxonlineUserDataInDBVO;
import com.grpretail.comfandi.trxonline.automaticjobs.onlineinventory.vos.RegistersVo;
import com.grpretail.facturaelectronica.comfandi.business.utils.vos.AlmacenVo;
import com.grpretail.facturaelectronica.comfandi.mensajeria.vos.ServiceOrderTenderVo;
import com.grpretail.trxonline.automaticjobs.facturaelectronica.utils.BusinessLogicParameters;
import com.grpretail.trxonline.automaticjobs.onlineinventory.comfandi.job.OnlineInventoryFileGeneration;

public class TrxonlineDatabaseHelper {

	public static boolean updateWSElectronicBillingTrx(String pStore, String pTerminal,String pTrx, String pDate, String pTime, String pTransactionId, String pCUFE, String pFiscalNumber){
		//DB_TABLE_TRX_ONLINE_HEADER
		int counter_ = 0;
		boolean exit_ = false;
		while(counter_<3 && !exit_){
			try{
				//String dateTime_ = "'20"+pDateTime.substring(0, 2)+"-"+pDateTime.substring(2, 4)+"-"+pDateTime.substring(4, 6)+" "+ pDateTime.substring(6,8)+":"+pDateTime.substring(8,10)+":00.0'";
				String sentence_ =  " UPDATE " + BusinessLogicParam.DB_TABLA_TRX_FAC_ELEC_CARVAJAL + 
									" SET " + 	BusinessLogicParam.DB_COLUM_TFC_WS_TRX_CUFE_CONT + " = '" + pCUFE +"', " +									
												BusinessLogicParam.DB_COLUM_TFC_WS_TRX_ID_CONT + " = '" + pTransactionId +"', " +
												BusinessLogicParam.DB_TFC_FISC_NUMBER_CONT + " = '" + pFiscalNumber +"'";
				String filter_ =	BusinessLogicParam.DB_COLUM_TFC_TIENDA + " = "+pStore + " AND " +
									BusinessLogicParam.DB_COLUM_TFC_NUM_TERMINAL + " = "+pTerminal + " AND " +
									BusinessLogicParam.DB_COLUM_TFC_NUM_FACTURA + " = "+pTrx + " AND " +
									BusinessLogicParam.DB_COLUM_TFC_FECHA + " = '"+pDate+"' AND " +
									BusinessLogicParam.DB_COLUM_TFC_HORA + " = '"+pTime+"'";
									
				int dbUpdateAnswer_ = SqlUtils.actualizaTabla(
					sentence_ +" WHERE "+filter_,
					PosRequestsHandler.getCnxClassName(""),
					PosRequestsHandler.getCnxUrl(""),
					PosRequestsHandler.getCnxUserID(""),
					PosRequestsHandler.getCnxPassword("")	
					);
				if(dbUpdateAnswer_>0)
					exit_ = true;
				//return false;
			}catch (Exception e) {
				e.printStackTrace();
				//return false;
			}catch (Throwable t) {
				t.printStackTrace();
				//return false;
			}finally{
				counter_++;
			}
		}
		if(exit_)
			return true;
		return false;
	}
	
	public static boolean updateNaturalCustomerElectronicBillingTrx(String pStore, String pTerminal,String pTrx, String pDateTime, String pStatusToSet){
		//DB_TABLE_TRX_ONLINE_HEADER
		int counter_ = 0;
		boolean exit_ = false;
		while(counter_<3 && !exit_){
			try{
				String dateTime_ = "'20"+pDateTime.substring(0, 2)+"-"+pDateTime.substring(2, 4)+"-"+pDateTime.substring(4, 6)+" "+ pDateTime.substring(6,8)+":"+pDateTime.substring(8,10)+":00.0'";
				String sentence_ =  " UPDATE " + BusinessLogicParameters.DB_TABLE_TRX_ONLINE_HEADER + 
									" SET " + BusinessLogicParameters.DB_COLUM_TOL_HEADER_ELECT_BILLING_STATUS + " = '" + pStatusToSet +"'";									
				String filter_ =	BusinessLogicParameters.DB_COLUM_TOL_STORE + " = "+pStore + " AND " +
									BusinessLogicParameters.DB_COLUM_TOL_TERMINAL + " = "+pTerminal + " AND " +
									BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER + " = "+pTrx + " AND " +
									BusinessLogicParameters.DB_COLUM_TOL_DATE_TIME + " = TIMESTAMP("+dateTime_+")";
									
				int dbUpdateAnswer_ = SqlUtils.actualizaTabla(
					sentence_ +" WHERE "+filter_,
					PosRequestsHandler.getCnxClassName(""),
					PosRequestsHandler.getCnxUrl(""),
					PosRequestsHandler.getCnxUserID(""),
					PosRequestsHandler.getCnxPassword("")	
					);
				if(dbUpdateAnswer_>0)
					exit_ = true;
				//return false;
			}catch (Exception e) {
				e.printStackTrace();
				//return false;
			}catch (Throwable t) {
				t.printStackTrace();
				//return false;
			}finally{
				counter_++;
			}
		}
		if(exit_)
			return true;
		return false;
	}
	
	public static boolean updateRejectedElectronicBillingTrxRegister(String pStore, String pTerminal,String pTrx, String pDateTime, String pStatusToSet){
		//DB_TABLE_TRX_ONLINE_HEADER
		int counter_ = 0;
		boolean exit_ = false;
		while(counter_<3 && !exit_){
			try{
				String date_ = "20"+pDateTime.substring(0, 2)+"-"+pDateTime.substring(2, 4)+"-"+pDateTime.substring(4, 6);
				String time_ = pDateTime.substring(6,10); //+pDateTime.substring(8,10);
				String sentence_ =  " UPDATE " + BusinessLogicParam.DB_TABLA_FAC_ELEC_REJECT_TRXS_CARVAJAL + 
									" SET " + BusinessLogicParam.DB_COLUM_FERTC_REGISTER_STATUS + " = '" + pStatusToSet +"'";									
				String filter_ =	BusinessLogicParam.DB_COLUM_FERTC_STORE + " = "+pStore + " AND " +
									BusinessLogicParam.DB_COLUM_FERTC_TERMINAL + " = "+pTerminal + " AND " +
									BusinessLogicParam.DB_COLUM_FERTC_TRX_NUMBER + " = "+pTrx + " AND " +
									BusinessLogicParam.DB_COLUM_FERTC_DATE + " = '"+date_+"' AND " +
									BusinessLogicParam.DB_COLUM_FERTC_TIME + " = '"+time_+"'";
									
				int dbUpdateAnswer_ = SqlUtils.actualizaTabla(
					sentence_ +" WHERE "+filter_,
					PosRequestsHandler.getCnxClassName(""),
					PosRequestsHandler.getCnxUrl(""),
					PosRequestsHandler.getCnxUserID(""),
					PosRequestsHandler.getCnxPassword("")	
					);
				if(dbUpdateAnswer_>0)
					exit_ = true;
				//return false;
			}catch (Exception e) {
				e.printStackTrace();
				//return false;
			}catch (Throwable t) {
				t.printStackTrace();
				//return false;
			}finally{
				counter_++;
			}
		}
		if(exit_)
			return true;
		return false;
	}	
	
	// CACS: Retorna una lista de objetos DiscountRegInDBVO que corresponden a los descuentos en la trx.
	public static List getTransactionDiscounts(TrxonlineTransactionInDBVO pTrxToFindData)throws ConnectionFailedException, DBAccessException{
		System.out.println("Desde getTransactionDiscounts");
		String select_ =
			
			" b."+BusinessLogicParameters.DB_COLUM_TOL_STORE+" almacen,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL+" terminal,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER+" numTrx,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_DATE_TIME+" fechaHora,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_DISCOUNT_TYPE+" discountType,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_DISCOUNT_AMOUNT+" discountAmount";
		String from_ =
				BusinessLogicParameters.DB_TABLE_TRX_ONLINE_DISCOUNT+" b";
			
		String where_ =
			" b."+BusinessLogicParameters.DB_COLUM_TOL_STORE + "=" + pTrxToFindData.getAlmacen() + " AND " +  
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL + "=" + pTrxToFindData.getTerminal() + " AND " + 
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER + "=" + pTrxToFindData.getNumTrx() + " AND " + 
			" b."+BusinessLogicParameters.DB_COLUM_TOL_DATE_TIME + "='" + pTrxToFindData.getFechaHora() + "'"+
			" ORDER BY b.OFFSETX ASC ";
		List lstDiscountsInfo_ =
			DBUtil.select(
				select_,						
				from_,
				where_,		
				DiscountRegInDBVO.class);
		return lstDiscountsInfo_;
	}
	
	// CACS: Retorna una lista de objetos TenderRegInDBVO que corresponden a los medios de pago en la trx.
	public static List getTransactionTenders(TrxonlineTransactionInDBVO pTrxToFindData)throws ConnectionFailedException, DBAccessException{
		System.out.println("Desde getTransactionTenders");
		String select_ =
			
			" b."+BusinessLogicParameters.DB_COLUM_TOL_STORE+" almacen,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL+" terminal,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER+" numTrx,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_DATE_TIME+" fechaHora,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TENDER_ID+" tenderId,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TENDER_TYPE+" type,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TENDER_AMOUNT+" tenderAmount";
		String from_ =
				BusinessLogicParameters.DB_TABLE_TRX_ONLINE_TENDER+" b";
			
		String where_ =
			" b."+BusinessLogicParameters.DB_COLUM_TOL_STORE + "=" + pTrxToFindData.getAlmacen() + " AND " +  
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL + "=" + pTrxToFindData.getTerminal() + " AND " + 
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER + "=" + pTrxToFindData.getNumTrx() + " AND " + 
			" b."+BusinessLogicParameters.DB_COLUM_TOL_DATE_TIME + "='" + pTrxToFindData.getFechaHora() + "'";
		List lstTendersInfo_ = 
							DBUtil.select(
								select_,						
								from_,
								where_,		
								TenderRegInDBVO.class);	
		return lstTendersInfo_;
	}

	//CACS: Retorna una lista de objetos DayWithoutVATTransactionInDBVO que corresponden a la informaci�n adicional de los �tems o productos en la trx.
	/*
	public static List getTransactionItemsAdditionalInfo(TrxonlineTransactionInDBVO pTrxToFindData)throws ConnectionFailedException, DBAccessException{
		System.out.println("Desde getTransactionItemsAdditionalInfo");
		String select_ =
			
			" a."+BusinessLogicParameters.DB_COLUM_TOL_STORE+" almacen,"+
			" a."+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL+" terminal,"+
			" a."+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER+" numTrx,"+
			" a."+BusinessLogicParameters.DB_COLUM_TOL_DATE_TIME+" fechaHora,"+
			" a."+BusinessLogicParameters.DB_COLUM_TOL_OPERATOR+" operator,"+
			
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA2+" data2,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA3+" data3,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA4+" data4,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA5+" data5,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA6+" data6,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA7+" data7,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA8+" data8,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA9+" data9,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA10+" data10,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA11+" data11";
									
			String from_ =
						//BusinessLogicParameters.DB_TABLE_SERVICE_PAYMENT_TRX;
						BusinessLogicParameters.DB_TABLE_TRX_ONLINE_HEADER +" a," +
						BusinessLogicParameters.DB_TABLE_TRX_ONLINE_USER_DATA+" b" ;
						
			String where_ =
			" a."+BusinessLogicParameters.DB_COLUM_TOL_STORE + "= b." + BusinessLogicParameters.DB_COLUM_TOL_STORE + " AND " +  
			" a."+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL + "= b." + BusinessLogicParameters.DB_COLUM_TOL_TERMINAL + " AND " + 
			" a."+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER + "= b." + BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER + " AND " + 
			" a."+BusinessLogicParameters.DB_COLUM_TOL_DATE_TIME + "= b." + BusinessLogicParameters.DB_COLUM_TOL_DATE_TIME + " AND " +
			
			" b."+BusinessLogicParameters.DB_COLUM_TOL_STORE + "=" + pTrxToFindData.getAlmacen() + " AND " +  
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL + "=" + pTrxToFindData.getTerminal() + " AND " + 
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER + "=" + pTrxToFindData.getNumTrx() + " AND " + 
			" b."+BusinessLogicParameters.DB_COLUM_TOL_DATE_TIME + "='" + pTrxToFindData.getFechaHora() + "' AND " +			
			
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA1+"='"+BusinessLogicParameters.USER_DATA_ITEM_ADDITIONAL_INFO+"'";

						
			
			
			List lstItemsAdditionalInfo_ = 
				DBUtil.select(
					select_,						
					from_,
					where_,		
					TrxonlineTransactionInDBVO.class);	
			return lstItemsAdditionalInfo_;
	}
	*/
	//CACS: Retorna una lista de objetos DayWithoutVATTransactionInDBVO que corresponden a la informaci�n adicional de los �tems o productos en la trx.
	/*
	public static List getTransactionItemsAdditionalInfo2(TrxonlineTransactionInDBVO pTrxToFindData)throws ConnectionFailedException, DBAccessException{
		System.out.println("Desde getTransactionItemsAdditionalInfo2");
		String select_ =
			
			" b."+BusinessLogicParameters.DB_COLUM_TOL_STORE+" almacen,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL+" terminal,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER+" numTrx,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_DATE_TIME+" fechaHora,"+
			//" a."+BusinessLogicParameters.DB_COLUM_TOL_OPERATOR+" operator,"+
			
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA2+" data2,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA3+" data3,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA4+" data4,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA5+" data5,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA6+" data6,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA7+" data7,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA8+" data8,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA9+" data9,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA10+" data10,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA11+" data11";
									
			String from_ =
						//BusinessLogicParameters.DB_TABLE_SERVICE_PAYMENT_TRX;
						//BusinessLogicParameters.DB_TABLE_TRX_ONLINE_HEADER +" a," +
						BusinessLogicParameters.DB_TABLE_TRX_ONLINE_USER_DATA+" b" ;
						
			String where_ =
			" b."+BusinessLogicParameters.DB_COLUM_TOL_STORE + "=" + pTrxToFindData.getAlmacen() + " AND " +  
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL + "=" + pTrxToFindData.getTerminal() + " AND " + 
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER + "=" + pTrxToFindData.getNumTrx() + " AND " + 
			//" b."+BusinessLogicParameters.DB_COLUM_TOL_DATE_TIME + "='" + pTrxToFindData.getFechaHora() + "' AND " +
			" b."+BusinessLogicParameters.DB_COLUM_TOL_DAY + "='" + pTrxToFindData.getFecha() + "' AND " +
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TIME + "='" + pTrxToFindData.getHora() + "' AND " +			
			
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA1+"='"+BusinessLogicParameters.USER_DATA_ITEM_ADDITIONAL_INFO+"'";

			System.out.println("SELECT "+select_+" FROM "+from_+" WHERE "+where_);			
			
			
			List lstItemsAdditionalInfo_ = 
				DBUtil.select(
					select_,						
					from_,
					where_,		
					TrxonlineTransactionInDBVO.class);	
			return lstItemsAdditionalInfo_;
	}
	*/	
	
	// CACS: Retorna una lista de objetos ItemEntryRegInDBVO que corresponden a las entradas de �tems o productos en la trx.
	public static List getTransactionItemEntries(TrxonlineTransactionInDBVO pTrxToFindData)throws ConnectionFailedException, DBAccessException{
		System.out.println("Desde getTransactionItemEntries");
		String select_ =
			
			" b."+BusinessLogicParameters.DB_COLUM_TOL_STORE+" almacen,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL+" terminal,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER+" numTrx,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_DATE_TIME+" fechaHora,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_ITM_ENTRY_ITEMCODE+" itemCode,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_ITM_ENTRY_DEPARTMENT2+" departme,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_ITM_ENTRY_QTYORWGT+" qtyOrWgt,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_ITM_ENTRY_XPRICE+" xprice,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_ITM_ENTRY_INDICAT31+" indicat31,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_ITM_ENTRY_INDICAT17+" indicat17,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_ITM_ENTRY_INDICAT211+" indicat211,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_ITM_ENTRY_EXEMP_FLAG+" exemptionFlag,"+			
			" b."+BusinessLogicParameters.DB_COLUM_TOL_ITM_ENTRY_VAT_RATE+" VATRate,"+				
			" b."+BusinessLogicParameters.DB_COLUM_TOL_ITM_ENTRY_CONSUM_TAX_RATE+" consumptionTaxRate,"+	
			" b."+BusinessLogicParameters.DB_COLUM_TOL_ITM_ENTRY_MEASURE_UNIT+" measureUnit,"+		
			" b."+BusinessLogicParameters.DB_COLUM_TOL_ITM_ENTRY_DISCOUNT_VALUE+" discount,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_ITM_ENTRY_SALEPRICE+" salePrice,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_ITM_ICUI_TAX_RATE+" ICUIRate";

		
			/*
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA5+" data5,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA6+" data6,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA7+" data7,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA1+" data8";
			*/						
		String from_ =
				BusinessLogicParameters.DB_TABLE_TRX_ONLINE_ITEM_ENTRY+" b";
			
		String where_ =
			" b."+BusinessLogicParameters.DB_COLUM_TOL_STORE + "=" + pTrxToFindData.getAlmacen() + " AND " +  
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL + "=" + pTrxToFindData.getTerminal() + " AND " + 
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER + "=" + pTrxToFindData.getNumTrx() + " AND " + 
			" b."+BusinessLogicParameters.DB_COLUM_TOL_DATE_TIME + "='" + pTrxToFindData.getFechaHora() + "'";

		List lstItemEntriesInfo_ = 
		DBUtil.select(
			select_,						
			from_,
			where_,		
			ItemEntryRegInDBVO.class);	
		
		return lstItemEntriesInfo_;
		
	}
	
	// CACS: Retorna una lista de objetos DayWithoutVATTransactionInDBVO con user datas que tienen la informaci�n del cliente en la trx. Data1 = '20040608'
	public static List getCustomerInfoUserData(TrxonlineTransactionInDBVO pTrxToFindData)throws ConnectionFailedException, DBAccessException{
		System.out.println("Desde getCustomerInfoUserData");
		String select_ =
			
			" b."+BusinessLogicParameters.DB_COLUM_TOL_STORE+" almacen,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL+" terminal,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER+" numTrx,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_DATE_TIME+" fechaHora,"+
			
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA2+" data2,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA3+" data3,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA4+" data4,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA5+" data5,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA6+" data6,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA7+" data7,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA10+" data10,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA1+" data8";
									
		String from_ =
				BusinessLogicParameters.DB_TABLE_TRX_ONLINE_USER_DATA+" b";
			
		String where_ =
			" b."+BusinessLogicParameters.DB_COLUM_TOL_STORE + "=" + pTrxToFindData.getAlmacen() + " AND " +  
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL + "=" + pTrxToFindData.getTerminal() + " AND " + 
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER + "=" + pTrxToFindData.getNumTrx() + " AND " + 
			" b."+BusinessLogicParameters.DB_COLUM_TOL_DAY + "='" + pTrxToFindData.getFecha() + "' AND " +
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TIME + "='" + pTrxToFindData.getHora() + "' AND " + 
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA1+"='"+BusinessLogicParameters.USER_DATA_CUSTOMER_INFO+"'"; //G' AND "+
		
		List lstCustomerInfo_;
		//try {
			lstCustomerInfo_ = DBUtil.select(
				select_,						
				from_,
				where_,		
				TrxonlineTransactionInDBVO.class);
		/*} catch (ConnectionFailedException e) {
			// TODO Bloque catch generado autom�ticamente
			e.printStackTrace();
		} catch (DBAccessException e) {
			// TODO Bloque catch generado autom�ticamente
			e.printStackTrace();
		}*/	
		System.out.println("lstCustomerInfo_.size()"+lstCustomerInfo_.size());
		return lstCustomerInfo_;
	}
	
	
	// CACS: Retorna una lista de objetos DayWithoutVATTransactionInDBVO con user datas que tienen la informaci�n del cliente en la trx. Data1 = '20040608'
	public static List getHeaderAdditionalInfo(TrxonlineTransactionInDBVO pTrxToFindData)throws ConnectionFailedException, DBAccessException{
		System.out.println("Desde getHeaderAdditionalInfo");
		String select_ =
			/*
			" b."+BusinessLogicParameters.DB_COLUM_TOL_STORE+" almacen,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL+" terminal,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER+" numTrx,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_DATE_TIME+" fechaHora,"+
			*/
			" b."+BusinessLogicParameters.DB_COLUM_TOL_HEADER_GROSS_POS+" grossPos," +
			" b."+BusinessLogicParameters.DB_COLUM_TOL_HEADER_GROSS_NEG+" grossNeg," +
			" b."+BusinessLogicParameters.DB_COLUM_TOL_HEADER_TYPE+" tipo";
									
		String from_ =
				BusinessLogicParameters.DB_TABLE_TRX_ONLINE_HEADER+" b";
			
		String where_ =
			" b."+BusinessLogicParameters.DB_COLUM_TOL_STORE + "=" + pTrxToFindData.getAlmacen() + " AND " +  
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL + "=" + pTrxToFindData.getTerminal() + " AND " + 
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER + "=" + pTrxToFindData.getNumTrx() + " AND " + 
			" b."+BusinessLogicParameters.DB_COLUM_TOL_DAY + "='" + pTrxToFindData.getFecha() + "' AND " +
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TIME + "='" + pTrxToFindData.getHora() + "'";
			System.out.println("SELECT "+select_+" FROM "+from_+" WHERE "+where_);
		List lstHeaderAdditionalInfo_;
		//try {
			lstHeaderAdditionalInfo_ = DBUtil.select(
				select_,						
				from_,
				where_,		
				TrxonlineTransactionInDBVO.class);
		/*} catch (ConnectionFailedException e) {
			// TODO Bloque catch generado autom�ticamente
			e.printStackTrace();
		} catch (DBAccessException e) {
			// TODO Bloque catch generado autom�ticamente
			e.printStackTrace();
		}*/	
		System.out.println("lstHeaderAdditionalInfo_.size()"+lstHeaderAdditionalInfo_.size());
		return lstHeaderAdditionalInfo_;
	}	

	// CACS: Retorna una lista de objetos TrxonlineTransactionInDBVO con user datas que tienen la informaci�n del cliente en la trx. Data1 = '20040608'
	public static List getCustomerInfoUserData2(TrxonlineTransactionInDBVO pTrxToFindData)throws ConnectionFailedException, DBAccessException{
		System.out.println("Desde getCustomerInfoUserData2");
		String select_ =
			
			" b."+BusinessLogicParameters.DB_COLUM_TOL_STORE+" almacen,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL+" terminal,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER+" numTrx,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_DATE_TIME+" fechaHora,"+
			
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA2+" data2,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA3+" data3,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA4+" data4,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA5+" data5,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA6+" data6,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA7+" data7,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA10+" data10,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA1+" data8";
									
		String from_ =
				BusinessLogicParameters.DB_TABLE_TRX_ONLINE_USER_DATA+" b";
			
		String where_ =
			" b."+BusinessLogicParameters.DB_COLUM_TOL_STORE + "=" + pTrxToFindData.getAlmacen() + " AND " +  
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL + "=" + pTrxToFindData.getTerminal() + " AND " + 
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER + "=" + pTrxToFindData.getNumTrx() + " AND " + 
			//" b."+BusinessLogicParameters.DB_COLUM_TOL_DATE_TIME + "='" + pTrxToFindData.getFechaHora() + "' AND " +
			" b."+BusinessLogicParameters.DB_COLUM_TOL_DAY + "='" + pTrxToFindData.getFecha() + "' AND " +
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TIME + "='" + pTrxToFindData.getHora() + "' AND " +
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA1+"='"+BusinessLogicParameters.USER_DATA_CUSTOMER_INFO+"'"; //G' AND "+
			System.out.println("SELECT "+select_+" FROM "+from_+" WHERE "+where_);
		List lstCustomerInfo_;
		//try {
			lstCustomerInfo_ = DBUtil.select(
				select_,						
				from_,
				where_,		
				TrxonlineTransactionInDBVO.class);
		/*} catch (ConnectionFailedException e) {
			// TODO Bloque catch generado autom�ticamente
			e.printStackTrace();
		} catch (DBAccessException e) {
			// TODO Bloque catch generado autom�ticamente
			e.printStackTrace();
		}*/	
		System.out.println("lstCustomerInfo_.size()"+lstCustomerInfo_.size());
		return lstCustomerInfo_;
	}
	
	// CACS: Retorna una lista de objetos TrxonlineTransactionInDBVO con user datas que tienen la informaci�n fiscal de la factura. Data1 = '20040608'
	public static List getTrxFiscalInformation(TrxonlineTransactionInDBVO pTrxToFindData)throws ConnectionFailedException, DBAccessException{
		System.out.println("Desde getTrxFiscalInformation");
		String select_ =
			
			" b."+BusinessLogicParameters.DB_COLUM_TOL_STORE+" almacen,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL+" terminal,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER+" numTrx,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_DATE_TIME+" fechaHora,"+
			
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA2+" data2,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA3+" data3,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA4+" data4,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA5+" data5,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA6+" data6,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA7+" data7,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA8+" data8,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA9+" data9,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA10+" data10";
									
		String from_ =
				BusinessLogicParameters.DB_TABLE_TRX_ONLINE_USER_DATA+" b";
			
		String where_ =
			" b."+BusinessLogicParameters.DB_COLUM_TOL_STORE + "=" + pTrxToFindData.getAlmacen() + " AND " +  
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL + "=" + pTrxToFindData.getTerminal() + " AND " + 
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER + "=" + pTrxToFindData.getNumTrx() + " AND " + 
			//" b."+BusinessLogicParameters.DB_COLUM_TOL_DATE_TIME + "='" + pTrxToFindData.getFechaHora() + "' AND " +
			" b."+BusinessLogicParameters.DB_COLUM_TOL_DAY + "='" + pTrxToFindData.getFecha() + "' AND " +
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TIME + "='" + pTrxToFindData.getHora() + "' AND " +
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA1+"='"+BusinessLogicParameters.USER_DATA_TRX_FISCAL_INFO+"'"; //G' AND "+
			System.out.println("SELECT "+select_+" FROM "+from_+" WHERE "+where_);
		List lstCustomerInfo_;
		//try {
			lstCustomerInfo_ = DBUtil.select(
				select_,						
				from_,
				where_,		
				TrxonlineTransactionInDBVO.class);
		/*} catch (ConnectionFailedException e) {
			// TODO Bloque catch generado autom�ticamente
			e.printStackTrace();
		} catch (DBAccessException e) {
			// TODO Bloque catch generado autom�ticamente
			e.printStackTrace();
		}*/	
		System.out.println("lstCustomerInfo_.size()"+lstCustomerInfo_.size());
		return lstCustomerInfo_;
	}	

	// CACS: Retorna una lista de objetos TrxonlineTransactionInDBVO con user datas que tienen la informaci�n fiscal de la factura. Data1 = '20040608'
	public static List getTrxUserDatasInformation(TrxonlineTransactionInDBVO pTrxToFindData)throws ConnectionFailedException, DBAccessException{
		System.out.println("Desde getTrxFiscalInformation");
		String select_ =
			
			" b."+BusinessLogicParameters.DB_COLUM_TOL_STORE+" almacen,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL+" terminal,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER+" numTrx,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_DATE_TIME+" fechaHora,"+
			
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA1+" data1,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA2+" data2,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA3+" data3,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA4+" data4,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA5+" data5,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA6+" data6,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA7+" data7,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA8+" data8,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA9+" data9,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA10+" data10";
									
		String from_ =
				BusinessLogicParameters.DB_TABLE_TRX_ONLINE_USER_DATA+" b";
			
		String where_ =
			" b."+BusinessLogicParameters.DB_COLUM_TOL_STORE + "=" + pTrxToFindData.getAlmacen() + " AND " +  
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL + "=" + pTrxToFindData.getTerminal() + " AND " + 
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER + "=" + pTrxToFindData.getNumTrx() + " AND " + 
			//" b."+BusinessLogicParameters.DB_COLUM_TOL_DATE_TIME + "='" + pTrxToFindData.getFechaHora() + "' AND " +
			" b."+BusinessLogicParameters.DB_COLUM_TOL_DAY + "='" + pTrxToFindData.getFecha() + "' AND " +
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TIME + "='" + pTrxToFindData.getHora() + "' "; //AND " +
			//" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA1+"='"+BusinessLogicParameters.USER_DATA_TRX_FISCAL_INFO+"'"; //G' AND "+
			System.out.println("SELECT "+select_+" FROM "+from_+" WHERE "+where_);
		List lstCustomerInfo_;
			lstCustomerInfo_ = DBUtil.select(
				select_,						
				from_,
				where_,		
				TrxonlineTransactionInDBVO.class);
		System.out.println("lstCustomerInfo_.size()"+lstCustomerInfo_.size());
		return lstCustomerInfo_;
	}	
	
	public static boolean isThereATrxWithThisStatus(TrxonlineTransactionInDBVO pTrxToFindData, String pStatus, String pStatus2){
		System.out.println("Desde hasTrxThisStatus");
		boolean answer_=false;
		RegistersVo numReg_=new RegistersVo();
		numReg_.setNumberReg(0); 
		String select_ =
				"COUNT(*) numberReg ";
		String from_ =
				BusinessLogicParameters.DB_TABLE_TRX_ONLINE_HEADER + " b ";
		String where_ =
			" b."+BusinessLogicParameters.DB_COLUM_TOL_STORE + "=" + pTrxToFindData.getAlmacen() + " AND " +  
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL + "=" + pTrxToFindData.getTerminal() + " AND " + 
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER + "=" + pTrxToFindData.getNumTrx() + " AND " + 
			" b."+BusinessLogicParameters.DB_COLUM_TOL_DAY + "='" + pTrxToFindData.getFecha() + "' AND " +
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TIME + "='" + pTrxToFindData.getHora() + "' AND (" +	
			" b."+BusinessLogicParameters.DB_COLUM_TOL_HEADER_ELECT_BILLING_STATUS + "='" + pStatus + "' OR" +
			" b."+BusinessLogicParameters.DB_COLUM_TOL_HEADER_ELECT_BILLING_STATUS + "='" + pStatus2 + "')";
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
	
	// CACS: Retorna una lista de objetos DayWithoutVATTransactionInDBVO con user datas que tienen la informaci�n del cliente en la trx. Data1 = '20040608'
	public static List getSmallBillInfoUserData(TrxonlineTransactionInDBVO pTrxToFindData)throws ConnectionFailedException, DBAccessException{
		System.out.println("Desde getSmallBillInfoUserData");
		String select_ =
			/*
			" b."+BusinessLogicParameters.DB_COLUM_TOL_STORE+" almacen,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL+" terminal,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER+" numTrx,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_DATE_TIME+" fechaHora,"+
			*/
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA2+" data2";
			/*
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA3+" data3,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA4+" data4,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA5+" data5,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA6+" data6,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA7+" data7,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA10+" data10,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA1+" data8";
			*/						
		String from_ =
				BusinessLogicParameters.DB_TABLE_TRX_ONLINE_USER_DATA+" b";
			
		String where_ =
			" b."+BusinessLogicParameters.DB_COLUM_TOL_STORE + "=" + pTrxToFindData.getAlmacen() + " AND " +  
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL + "=" + pTrxToFindData.getTerminal() + " AND " + 
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER + "=" + pTrxToFindData.getNumTrx() + " AND " + 
			//" b."+BusinessLogicParameters.DB_COLUM_TOL_DATE_TIME + "='" + pTrxToFindData.getFechaHora() + "' AND " +
			" b."+BusinessLogicParameters.DB_COLUM_TOL_DAY + "='" + pTrxToFindData.getFecha() + "' AND " +
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TIME + "='" + pTrxToFindData.getHora() + "' AND " +
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA1+"='"+BusinessLogicParameters.USER_DATA_SMALL_BILL_ADDITIONAL_INFO+"'"; //G' AND "+
			System.out.println("SELECT "+select_+" FROM "+from_+" WHERE "+where_);
		List lstCustomerInfo_;
		//try {
			lstCustomerInfo_ = DBUtil.select(
				select_,						
				from_,
				where_,		
				TrxonlineTransactionInDBVO.class);
		/*} catch (ConnectionFailedException e) {
			// TODO Bloque catch generado autom�ticamente
			e.printStackTrace();
		} catch (DBAccessException e) {
			// TODO Bloque catch generado autom�ticamente
			e.printStackTrace();
		}*/	
		System.out.println("lstCustomerInfo_.size()"+lstCustomerInfo_.size());
		return lstCustomerInfo_;
	}	
	
	/*
	public static List getTaxesByItemUserDatas(TrxonlineUserDataInDBVO pTrxToFindData)throws ConnectionFailedException, DBAccessException{
		System.out.println("Desde getCustomerInfoUserData");
		String select_ =
			
			" b."+BusinessLogicParameters.DB_COLUM_TOL_STORE+" almacen,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL+" terminal,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER+" numTrx,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_DATE_TIME+" fechaHora,"+
			
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA2+" data2,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA3+" data3,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA4+" data4,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA5+" data5,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA6+" data6,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA7+" data7,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA8+" data8,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA9+" data9,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA10+" data10,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA11+" data11,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_OFFSETX+" offsetx";
									
		String from_ =
				BusinessLogicParameters.DB_TABLE_TRX_ONLINE_USER_DATA+" b";
			
		String where_ =
			" b."+BusinessLogicParameters.DB_COLUM_TOL_STORE + "=" + pTrxToFindData.getAlmacen() + " AND " +  
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL + "=" + pTrxToFindData.getTerminal() + " AND " + 
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER + "=" + pTrxToFindData.getNumTrx() + " AND " + 
			" b."+BusinessLogicParameters.DB_COLUM_TOL_DATE_TIME + "='" + pTrxToFindData.getFechaHora() + "' AND " + 
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA1+"='"+BusinessLogicParameters.USER_DATA_TAXES_INFO+"' AND " +
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA2+"='" + pTrxToFindData.getData2() + "' AND " +
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_OFFSETX+"<"+pTrxToFindData.getOffsetx() +
			" ORDER BY b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_OFFSETX+" DESC ";
		
		List lstCustomerInfo_;
		//try {
			lstCustomerInfo_ = DBUtil.select(
				select_,						
				from_,
				where_,		
				TrxonlineUserDataInDBVO.class);
		//} catch (ConnectionFailedException e) {
			// TODO Bloque catch generado autom�ticamente
			//e.printStackTrace();
		//} catch (DBAccessException e) {
			// TODO Bloque catch generado autom�ticamente
			//e.printStackTrace();
		//}
		System.out.println("lstCustomerInfo_.size()"+lstCustomerInfo_.size());
		return lstCustomerInfo_;
	}*/	

	public static List getTaxesByItemUserDatas()throws ConnectionFailedException, DBAccessException{
		System.out.println("Desde getTaxesByItemUserDatas");
		String select_ =
			
			" b."+BusinessLogicParameters.DB_COLUM_TOL_STORE+" almacen,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL+" terminal,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER+" numTrx,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_DATE_TIME+" fechaHora,"+
			
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA2+" data2,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA3+" data3,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA4+" data4,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA5+" data5,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA6+" data6,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA7+" data7,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA8+" data8,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA9+" data9,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA10+" data10,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA11+" data11,"+
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_OFFSETX+" offsetx";
									
		String from_ =
					BusinessLogicParameters.DB_TABLE_TRX_ONLINE_HEADER+" a,"+BusinessLogicParameters.DB_TABLE_TRX_ONLINE_USER_DATA+" b";
			
		/*String where_ =
			" b."+BusinessLogicParameters.DB_COLUM_TOL_STORE + "=" + pTrxToFindData.getAlmacen() + " AND " +  
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL + "=" + pTrxToFindData.getTerminal() + " AND " + 
			" b."+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER + "=" + pTrxToFindData.getNumTrx() + " AND " + 
			" b."+BusinessLogicParameters.DB_COLUM_TOL_DATE_TIME + "='" + pTrxToFindData.getFechaHora() + "' AND " + 
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA1+"='"+BusinessLogicParameters.USER_DATA_TAXES_INFO+"' AND " +
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA2+"='" + pTrxToFindData.getData2() + "' AND " +
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_OFFSETX+"<"+pTrxToFindData.getOffsetx() +
			" ORDER BY b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_OFFSETX+" DESC ";*/

		String where_ =
			" a."+BusinessLogicParameters.DB_COLUM_TOL_STORE + "=" +" b."+BusinessLogicParameters.DB_COLUM_TOL_STORE + " AND " +  
			" a."+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL + "=" + " b."+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL + " AND " + 
			" a."+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER + "=" + " b."+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER + " AND " + 
			" a."+BusinessLogicParameters.DB_COLUM_TOL_DATE_TIME + "=" + " b."+BusinessLogicParameters.DB_COLUM_TOL_DATE_TIME + " AND " +
			" a."+BusinessLogicParameters.DB_COLUM_TOL_HEADER_ONLINE_INVENTORY_STATUS+"='"+OnlineInventoryFileGeneration.REGS_STATUS_PROCESSING+"' AND " +
			" b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_DATA1+"='"+BusinessLogicParameters.USER_DATA_TAXES_INFO + "'" +
			" ORDER BY b."+BusinessLogicParameters.DB_COLUM_TOL_STORE+" ASC,"+"b."+BusinessLogicParameters.DB_COLUM_TOL_TERMINAL+
			" ASC,b."+BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER+" ASC,"+"b."+BusinessLogicParameters.DB_COLUM_TOL_DATE_TIME+
			" ASC,b."+BusinessLogicParameters.DB_COLUM_TOL_USR_DATA_OFFSETX+" ASC ";
		
		
		List lstCustomerInfo_;
		//try {
			lstCustomerInfo_ = DBUtil.select(
				select_,						
				from_,
				where_,		
				TrxonlineUserDataInDBVO.class);
		/*} catch (ConnectionFailedException e) {
			// TODO Bloque catch generado autom�ticamente
			e.printStackTrace();
		} catch (DBAccessException e) {
			// TODO Bloque catch generado autom�ticamente
			e.printStackTrace();
		}*/	
		System.out.println("No Items lista de user datas->"+lstCustomerInfo_.size());
		return lstCustomerInfo_;
	}
	
	public static boolean changeElectronicBillingTrxStatus(String pStore, String pTerminal,String pTrx, String pDateTime, String pNewStatus){
		//DB_TABLE_TRX_ONLINE_HEADER
		int counter_ = 0;
		boolean exit_ = false;
		while(counter_<3 && !exit_){
			try{
				String dateTime_ = "'20"+pDateTime.substring(0, 2)+"-"+pDateTime.substring(2, 4)+"-"+pDateTime.substring(4, 6)+" "+ pDateTime.substring(6,8)+":"+pDateTime.substring(8,10)+":00.0'";
				String sentence_ =  " UPDATE " + BusinessLogicParameters.DB_TABLE_TRX_ONLINE_HEADER + 
									" SET " + BusinessLogicParameters.DB_COLUM_TOL_HEADER_ELECT_BILLING_STATUS + " = '"+pNewStatus+"'";									
				String filter_ =	BusinessLogicParameters.DB_COLUM_TOL_STORE + " = "+pStore + " AND " +
									BusinessLogicParameters.DB_COLUM_TOL_TERMINAL + " = "+pTerminal + " AND " +
									BusinessLogicParameters.DB_COLUM_TOL_TRX_NUMBER + " = "+pTrx + " AND " +
									BusinessLogicParameters.DB_COLUM_TOL_DATE_TIME + " = TIMESTAMP("+dateTime_+")";
									
				int dbUpdateAnswer_ = SqlUtils.actualizaTabla(
					sentence_ +" WHERE "+filter_,
					PosRequestsHandler.getCnxClassName(""),
					PosRequestsHandler.getCnxUrl(""),
					PosRequestsHandler.getCnxUserID(""),
					PosRequestsHandler.getCnxPassword("")	
					);
				if(dbUpdateAnswer_>0)
					exit_ = true;
				//return false;
			}catch (Exception e) {
				e.printStackTrace();
				//return false;
			}catch (Throwable t) {
				t.printStackTrace();
				//return false;
			}finally{
				counter_++;
			}
		}
		if(exit_){
			// CACS: Se inserta el error en la tabla
			return true;
		}
			
		return false;
	}
	
	/*
	public static AlmacenVo getAlmacenInfo(Long pIdAlmacen) throws ConnectionFailedException, DBAccessException {
		System.out.println("Desde getHeaderAdditionalInfo");
		String select_ =

			" b."+"codcco"+" idAlmacen," +
			" b."+"dsceco"+" nombre," +
			" b."+"socdir"+" direccion," +
			" b."+"socmun"+" nombreMunicipio," +
			" b."+"socnac"+" idMunicipio," +//no está actualizado
			" b."+"socpos"+" codPostal," +//no está actualizado
			" b."+"socpro"+" idDepartamento," +
			" d."+"DESPRO"+" nombreDpto" ;


	//TODO Falta idMunicipio, codigo postal
	
		String from_ =
				BusinessLogicParameters.DB_TABLE_STORE+" b, "+BusinessLogicParameters.DB_TABLE_DEPARTAMENTO+" d";
			
		String where_ =
			" b."+"codcco" + "=" + pIdAlmacen +" and d.codpro= b.socpro" ;
			System.out.println("SELECT "+select_+" FROM "+from_+" WHERE "+where_);
		List almacenInfo_;
		//try {
			almacenInfo_ = DBUtil.select(
				select_,						
				from_,
				where_,		
				AlmacenVo.class,
				BusinessLogicParameters.ID_DATABASE_PARAMETROS
				);
		System.out.println("almacenInfo_.size()"+almacenInfo_.size());
		AlmacenVo vo_ = null;
		if (!almacenInfo_.isEmpty()) {
			vo_ = (AlmacenVo)almacenInfo_.get(0); 
		}
		return vo_;
	}
	*/
	
	public static AlmacenVo getAlmacenInfo(Long pIdAlmacen) throws ConnectionFailedException, DBAccessException {
		System.out.println("Desde getHeaderAdditionalInfo");
		String select_ =
			" b."+"codcco"+" idAlmacen," +
			" b."+"dsceco"+" nombre," +
			" b."+"socdir"+" direccion," +
			" b."+"socmun"+" nombreMunicipio," +
			" p."+"IDMUNICIPIO"+" idMunicipio," +
			" p."+"CODPOSTAL"+" codPostal," +
			" b."+"socpro"+" idDepartamento," +
			" d."+"DESPRO"+" nombreDpto" ;

		String from_ =
				BusinessLogicParameters.DB_TABLE_STORE+" b, "
				+BusinessLogicParameters.DB_TABLE_DEPARTAMENTO+" d, "
				+BusinessLogicParameters.DB_TABLE_CODCIUDAD_POSTAL+" p";

		String where_ =
			" b."+"codcco" + "=" + pIdAlmacen +" and d.codpro = b.socpro and b.codcco = p.CODCCO" ;
			System.out.println("SELECT "+select_+" FROM "+from_+" WHERE "+where_);

		List almacenInfo_;
		almacenInfo_ = DBUtil.select(
			select_,						
			from_,
			where_,		
			AlmacenVo.class,
			BusinessLogicParameters.ID_DATABASE_PARAMETROS);

		System.out.println("almacenInfo_.size()"+almacenInfo_.size());
		AlmacenVo vo_ = null;
		if (!almacenInfo_.isEmpty()) {
			vo_ = (AlmacenVo)almacenInfo_.get(0); 
		}
		return vo_;
	}
	
	public static Map<Integer, ServiceOrderTenderVo> getMDPInfo() throws ConnectionFailedException, DBAccessException {
		System.out.println("Desde getMDPInfo");
		Map mapReturn_ = new HashMap<Integer, ServiceOrderTenderVo>();
		String select_ =
			" b."+"ID_MDP_POS"+" tenderId," +
			" b."+"DESCRIP_POS"+" tenderDescription,"+
			" b."+"COD_MDP_DIAN"+" codMDPDian," +
			" b."+"MDP_DIAN"+" mdpDian";
		String from_ =
				BusinessLogicParameters.DB_TABLE_MDP+" b";
			
		String where_ = "";
//			" b."+"ID_MDP_POS" + "=" + pIdMDPPos  ;
			System.out.println("SELECT "+select_+" FROM "+from_+" WHERE "+where_);
		List mdpInfo_;
			mdpInfo_ = DBUtil.select(
				select_,						
				from_,
				where_,		
				ServiceOrderTenderVo.class,
				BusinessLogicParameters.ID_DATABASE_PARAMETROS);
		System.out.println("mdpInfo_.size()"+mdpInfo_.size());
		for (Iterator iterator_ = mdpInfo_.iterator(); iterator_.hasNext();) {
			ServiceOrderTenderVo tenderVo_ = (ServiceOrderTenderVo) iterator_.next();
			mapReturn_.put(new Integer(tenderVo_.getTenderId()), tenderVo_);
		}
		ServiceOrderTenderVo vo_ = null;
		if (!mdpInfo_.isEmpty()) {
			vo_ = (ServiceOrderTenderVo)mdpInfo_.get(0); 
		}
		return mapReturn_;
	}
	
}
