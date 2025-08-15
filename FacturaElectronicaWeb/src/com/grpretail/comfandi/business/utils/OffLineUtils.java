package com.grpretail.comfandi.business.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ramm.jwaf.dbutil.DBUtil;
import org.ramm.jwaf.sql.ConnectionFailedException;
import org.ramm.jwaf.sql.DBAccessException;

import com.grpretail.comfandi.procesosautomaticos.facturaelectronica.vos.ItemEntryRegInDBVO;
import com.grpretail.comfandi.procesosautomaticos.facturaelectronica.vos.TransactionInDBVO;
import com.grpretail.comfandi.trxonline.automaticjobs.onlineinventory.vos.RegistersVo;
import com.grpretail.trxonline.automaticjobs.facturaelectronica.utils.BusinessLogicParameters;

public class OffLineUtils {
	
	public static List getOnlineInventoryTrxs(){
		ArrayList answer_ = new ArrayList();
		for(long i=0;i<10;i++){
			TransactionInDBVO vo_ = new TransactionInDBVO();
			vo_.setAlmacen(535L);
			vo_.setTerminal(1L);
			vo_.setNumTrx(i);
			answer_.add(vo_);
		}
		return answer_;
	}
	
	public static List getOnlineInventoryItemsEntries(){
		ArrayList answer_ = new ArrayList();
		for(long i=0;i<10;i++){
			ItemEntryRegInDBVO vo_ = new ItemEntryRegInDBVO();
			vo_.setItemCode(i);
			answer_.add(vo_);
		}
		return answer_;
	}
	
	public static boolean getPeriod(java.util.Date pProcessDate, int pFileConsecutive)throws Exception{
		return false;
	}
	
	public static int getRegistersInProcess(){
		return 0;
	}
	
	public static int getRegistersWasGenerated(){
		return 0;
	}	
	
	public static int getRegistersNumberToProcess(String pWhere){
		boolean answer_=false;
		RegistersVo numReg_=new RegistersVo();
		numReg_.setNumberReg(0); 
		String select_ =
				"COUNT(*) numberReg ";
		String from_ =
				BusinessLogicParameters.DB_TABLE_TRX_ONLINE_HEADER;
		String where_= pWhere;//BusinessLogicParameters.DB_COLUM_SP_SUPPLYER_EAN+"="+BATCH_EAN_SIMPLE +" AND " + // EAN de Simple  	
					   //BusinessLogicParameters.DB_COLUM_TOL_HEADER_ONLINE_INVENTORY_STATUS+"='"+REGS_STATUS_PROCESSING+"'";		
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
				//return -1;
			} catch (DBAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//return -1;
			}catch (Exception e){
				e.printStackTrace();
			}catch (Throwable t){
				t.printStackTrace();
				//return -1;
			}finally{
				//reintentos_++;
			}
		//}
		int return_ = 20000;	
		return return_;
	}

}
