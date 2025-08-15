package Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.grpretail.facturaelectronica.comfandi.dian.utils.CarvajalProcessUtils;

public class Test5 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Ap�ndice de m�todo generado autom�ticamente
		/*
		BigDecimal totalValue_ = new BigDecimal(5100);
		BigDecimal IVARate_ = new BigDecimal(1.19);
		BigDecimal base_ = new BigDecimal(0);
		base_ = totalValue_.divide(IVARate_, 2, RoundingMode.DOWN);
		System.out.println("base1_->"+base_);
		base_ = base_.multiply(new BigDecimal(10));
		*/
		float VATTaxRate_ = 0;
		//if(itmVo_.getVATTaxRate()>0){
			VATTaxRate_ = 19;
			//VATTaxRate_ = (VATTaxRate_/100) + 1;
		//}	
		System.out.println("VATTaxRate_->"+VATTaxRate_);
		System.out.println("base2_->"+getBaseValue(5100,VATTaxRate_));
		System.out.println("base2_->"+getTaxValue(5100,VATTaxRate_));
		//System.out.println("base2_->"+getBaseValue(5100,1.19f));
		BigDecimal value_ = new BigDecimal(814.289);
		value_ = value_.setScale(2, BigDecimal.ROUND_DOWN);
		System.out.println("value_->"+value_);
		String description_ = "*ACEITE OLIOS*420ML*";
		System.out.println("Description->"+CarvajalProcessUtils.removeSpecialCharacters(description_));

	}
	
	public static String getBaseValue(long pTotalValue, float pTaxRate){
		//float multiplier_ = (pTaxRate/100) + 1;
		BigDecimal multiplier_ = new BigDecimal((pTaxRate/100) + 1);
		BigDecimal totalValue_ = new BigDecimal(pTotalValue);
		BigDecimal IVARate_ = new BigDecimal(pTaxRate);
		BigDecimal base_ = new BigDecimal(0);
		base_ = totalValue_.divide(multiplier_, 2, RoundingMode.DOWN);
		//System.out.println("base1_->"+base_);
		//base_ = base_.multiply(new BigDecimal(10));
		//System.out.println("base2_->"+base_);
		return base_.toString();
	}
	
	public static String getTaxValue(long pTotalValue, float pTaxRate){
		//float divider_ = (pTaxRate/100) + 1;
		BigDecimal divider_ = new BigDecimal((pTaxRate/100) + 1);
		BigDecimal totalValue_ = new BigDecimal(pTotalValue);
		BigDecimal IVARate_ = new BigDecimal(pTaxRate/100);
		BigDecimal taxValue_ = new BigDecimal(0);
		taxValue_ = totalValue_.multiply(IVARate_);
		taxValue_ = taxValue_.divide(divider_, 2, RoundingMode.DOWN);
		//System.out.println("base1_->"+base_);
		//base_ = base_.multiply(new BigDecimal(10));
		//System.out.println("base2_->"+base_);
		return taxValue_.toString();
	}

}
