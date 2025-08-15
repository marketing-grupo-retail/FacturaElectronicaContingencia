package Test;

public class Ordenamiento {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Apéndice de método generado automáticamente
		int VATRates_[] = new int[10];
		/*
		VATRates_[0] = 8;
		VATRates_[1] = 10;
		VATRates_[2] = 3;
		VATRates_[3] = 6;
		VATRates_[4] = 1;
		VATRates_[5] = 7;
		VATRates_[6] = 9;
		VATRates_[7] = 4;
		VATRates_[8] = 2;
		VATRates_[9] = 5;
		*/
		/*
		VATRates_[0] = 10;
		VATRates_[1] = 9;
		VATRates_[2] = 8;
		VATRates_[3] = 7;
		VATRates_[4] = 6;
		VATRates_[5] = 5;
		VATRates_[6] = 4;
		VATRates_[7] = 3;
		VATRates_[8] = 2;
		VATRates_[9] = 1;
		*/		
		/*	
		VATRates_[0] = 1;
		VATRates_[1] = 2;
		VATRates_[2] = 3;
		VATRates_[3] = 4;
		VATRates_[4] = 5;
		VATRates_[5] = 6;
		VATRates_[6] = 7;
		VATRates_[7] = 8;
		VATRates_[8] = 9;
		VATRates_[9] = 10;		
		*/
		VATRates_[0] = 6;
		VATRates_[1] = 7;
		VATRates_[2] = 8;
		VATRates_[3] = 9;
		VATRates_[4] = 10;
		VATRates_[5] = 1;
		VATRates_[6] = 2;
		VATRates_[7] = 3;
		VATRates_[8] = 4;
		VATRates_[9] = 5;		
		ordenar(VATRates_);
	}
	
	public static void ordenar(int[] VATRates_){
		for(int i=0;i<VATRates_.length;i++){
			for(int j=i+1; j<VATRates_.length;j++){
				System.out.println("VATRates_["+i+"]: "+VATRates_[i]+" - "+" VATRates_["+j+"]: "+VATRates_[j] );
				if(VATRates_[i]>VATRates_[j]){
					int iTemp_= VATRates_[j];
					VATRates_[j] = VATRates_[i];
					VATRates_[i] = iTemp_;
				}
			}
		}
		System.out.println("Salió");
	}
	

}
