/*
 * Created on 21/09/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.grpretail.comfandi.trxonline.automaticjobs.processes;

/**
 * @author ACadena
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
import java.io.FilenameFilter;

public class AutomaticJobsProcessManager {
		private static final int theSubTheadsNumber = 2;
		//private static ConciliatorExecutor executor;
		// Se van a manejar 4 hilos para que cada uno apunte a un EndPointDiferente
		private static ElectronicBillingExecutor[] theElecBillingExecutorThead00 = null;
		private static ElectronicBillingExecutor[] theElecBillingExecutorThead01 = null;
		private static ElectronicBillingExecutor[] theElecBillingExecutorThead02 = null;
		private static ElectronicBillingExecutor[] theElecBillingExecutorThead03 = null;
		
		private static ElectronicBillingExecutor[] theElecBillingExecutorThead04 = null; // CACS: Usado para revisión de trasnacciones
		
		private static OnlineInventoryExecutor invOnlineExecutor;
		
		/*static{
			if(executor==null){
				executor=new PendingCommandsExecutor();
				executor.start();
			}
		}*/		
		
		/*
		public void procesarTransacciones(){
			System.out.println("Executing pending commands...");
			if(elecBillingExecutor==null){
				//executor=new ConciliatorExecutor();
				elecBillingExecutor=new ElectronicBillingExecutor();
			}
			elecBillingExecutor.start();
		}
		*/
	
		public static ElectronicBillingExecutor getElectronicBillingService00(int pTheadIndex){
			if(theSubTheadsNumber>pTheadIndex)
				return theElecBillingExecutorThead00[pTheadIndex];
			return null;
		}

		public static ElectronicBillingExecutor getElectronicBillingService01(int pTheadIndex){
			return theElecBillingExecutorThead01[pTheadIndex];		
		}

		public static ElectronicBillingExecutor getElectronicBillingService02(int pTheadIndex){
			return theElecBillingExecutorThead02[pTheadIndex];	
		}

		public static ElectronicBillingExecutor getElectronicBillingService03(int pTheadIndex){
			return theElecBillingExecutorThead03[pTheadIndex];	
		}
		
		/*
		public static ElectronicBillingExecutor getElectronicBillingService(int pThreadNumber, int pTheadIndex){
			if(pThreadNumber == 1){
				return theElecBillingExecutorThead01;
			}else if(pThreadNumber == 2){
				return theElecBillingExecutorThead02;
			}else if(pThreadNumber == 3){
				return theElecBillingExecutorThead03;
			}else if(pThreadNumber == 4){
				return theElecBillingExecutorThead04;
			}else
				return null;
		}
		*/
		
		public static void startElectronicBillingService(){
			startElectronicBillingService04();//TODO Pruebas
			//TODO Produccion
			/*startElectronicBillingService00();
			startElectronicBillingService01();
			startElectronicBillingService02();
			startElectronicBillingService03();*/
			
		}

		public static void startElectronicBillingService00(){
			// CACS: Hilo de facturaci�n electr�nica
			if(theSubTheadsNumber>0){
				if (theElecBillingExecutorThead00!=null){
					for(int i=0;i<theSubTheadsNumber;i++){					
						if(theElecBillingExecutorThead00[i].getStatus().equals("I")){ // Inactivo
							if(!theElecBillingExecutorThead00[i].isAlive()){
								theElecBillingExecutorThead00[i]=new ElectronicBillingExecutor(0,i);
							}
							theElecBillingExecutorThead00[i].start();
						}
					}	
				}else{
					theElecBillingExecutorThead00 = new ElectronicBillingExecutor[theSubTheadsNumber];
					for(int i=0;i<theSubTheadsNumber;i++){
						theElecBillingExecutorThead00[i]=new ElectronicBillingExecutor(0,i);
						theElecBillingExecutorThead00[i].start();
					}	
				}
			}else
				System.out.println("theTheadNumber menor igual a cero");
		}
		
		public static void startElectronicBillingService01(){
			// CACS: Hilo de facturaci�n electr�nica
			if(theSubTheadsNumber>0){
				if (theElecBillingExecutorThead01!=null){
					for(int i=0;i<theSubTheadsNumber;i++){
						if(theElecBillingExecutorThead01[i].getStatus().equals("I")){ // Inactivo
							if(!theElecBillingExecutorThead01[i].isAlive()){
								theElecBillingExecutorThead01[i]=new ElectronicBillingExecutor(1,i);
							}
							theElecBillingExecutorThead01[i].start();
						}
					}	
				}else{
					theElecBillingExecutorThead01 = new ElectronicBillingExecutor[theSubTheadsNumber];
					for(int i=0;i<theSubTheadsNumber;i++){					
						theElecBillingExecutorThead01[i]=new ElectronicBillingExecutor(1,i);
						theElecBillingExecutorThead01[i].start();
					}	
				}
			}else
				System.out.println("theTheadNumber menor igual a cero");				
		}
		
		public static void startElectronicBillingService02(){
			// CACS: Hilo de facturaci�n electr�nica
			if(theSubTheadsNumber>0){
				if (theElecBillingExecutorThead02!=null){
					for(int i=0;i<theSubTheadsNumber;i++){
						if(theElecBillingExecutorThead02[i].getStatus().equals("I")){ // Inactivo
							if(!theElecBillingExecutorThead02[i].isAlive()){
								theElecBillingExecutorThead02[i]=new ElectronicBillingExecutor(2,i);
							}
							theElecBillingExecutorThead02[i].start();
						}
					}	
				}else{
					theElecBillingExecutorThead02 = new ElectronicBillingExecutor[theSubTheadsNumber];
					for(int i=0;i<theSubTheadsNumber;i++){
						theElecBillingExecutorThead02[i]=new ElectronicBillingExecutor(2,i);
						theElecBillingExecutorThead02[i].start();
					}	
				}
			}else
				System.out.println("theTheadNumber menor igual a cero");				
		}
		
		public static void startElectronicBillingService03(){
			// CACS: Hilo de facturaci�n electr�nica
			if(theSubTheadsNumber>0){
				if (theElecBillingExecutorThead03!=null){
					for(int i=0;i<theSubTheadsNumber;i++){
						if(theElecBillingExecutorThead03[i].getStatus().equals("I")){ // Inactivo
							if(!theElecBillingExecutorThead03[i].isAlive()){
								theElecBillingExecutorThead03[i]=new ElectronicBillingExecutor(3,i);
							}
							theElecBillingExecutorThead03[i].start();
						}
					}	
				}else{
					theElecBillingExecutorThead03 = new ElectronicBillingExecutor[theSubTheadsNumber];
					for(int i=0;i<theSubTheadsNumber;i++){					
						theElecBillingExecutorThead03[i]=new ElectronicBillingExecutor(3,i);
						theElecBillingExecutorThead03[i].start();
					}	
				}
			}else
				System.out.println("theTheadNumber menor igual a cero");				
		}

		public static void startElectronicBillingService04(){
			// CACS: Hilo de facturaci�n electr�nica
			if(theSubTheadsNumber>0){
				if (theElecBillingExecutorThead04!=null){
					for(int i=0;i<theSubTheadsNumber;i++){
						if(theElecBillingExecutorThead04[i].getStatus().equals("I")){ // Inactivo
							if(!theElecBillingExecutorThead04[i].isAlive()){
								theElecBillingExecutorThead04[i]=new ElectronicBillingExecutor(4,i);
							}
							theElecBillingExecutorThead04[i].start();
						}
					}	
				}else{
					theElecBillingExecutorThead04 = new ElectronicBillingExecutor[theSubTheadsNumber];
					for(int i=0;i<theSubTheadsNumber;i++){					
						theElecBillingExecutorThead04[i]=new ElectronicBillingExecutor(4,i);
						theElecBillingExecutorThead04[i].start();
					}	
				}
			}else
				System.out.println("theTheadNumber menor igual a cero");				
		}		

		public static void stopElectronicBillingService(){
			if (theElecBillingExecutorThead00!=null){
				for(int i=0;i<theSubTheadsNumber;i++){
					if (theElecBillingExecutorThead00[i].getStatus().equals("S")){ // Sleeping:durmiendo
						System.out.println("Interrumpiendo hilo1["+i+"]");
						theElecBillingExecutorThead00[i].interrupt();
					}
					theElecBillingExecutorThead00[i].setTerminar(true);
				}
			}	
			if (theElecBillingExecutorThead01!=null){
				for(int i=0;i<theSubTheadsNumber;i++){	
					if (theElecBillingExecutorThead01[i].getStatus().equals("S")){ // Sleeping:durmiendo
						System.out.println("Interrumpiendo hilo2["+i+"]");
						theElecBillingExecutorThead01[i].interrupt();
					}
					theElecBillingExecutorThead01[i].setTerminar(true);
				}
			}
			if (theElecBillingExecutorThead02!=null){
				for(int i=0;i<theSubTheadsNumber;i++){				
					if (theElecBillingExecutorThead02[i].getStatus().equals("S")){ // Sleeping:durmiendo
						System.out.println("Interrumpiendo hilo3["+i+"]");
						theElecBillingExecutorThead02[i].interrupt();
					}
					theElecBillingExecutorThead02[i].setTerminar(true);
				}
			}
			if (theElecBillingExecutorThead03!=null){
				for(int i=0;i<theSubTheadsNumber;i++){			
					if (theElecBillingExecutorThead03[i].getStatus().equals("S")){ // Sleeping:durmiendo
						System.out.println("Interrumpiendo hilo4["+i+"]");
						theElecBillingExecutorThead03[i].interrupt();
					}
					theElecBillingExecutorThead03[i].setTerminar(true);
				}	
			}
		}
		
		public static void stopElectronicBillingService01(){
			if (theElecBillingExecutorThead01!=null){
				for(int i=0;i<theSubTheadsNumber;i++){
					if (theElecBillingExecutorThead01[i].getStatus().equals("S")){ // Sleeping:durmiendo
						System.out.println("Interrumpiendo hilo2["+i+"]");
						theElecBillingExecutorThead01[i].interrupt();
					}
					theElecBillingExecutorThead01[i].setTerminar(true);
				}	
			}
		}
		
		public static void stopElectronicBillingService02(){
			if (theElecBillingExecutorThead02!=null){
				for(int i=0;i<theSubTheadsNumber;i++){
					if (theElecBillingExecutorThead02[i].getStatus().equals("S")){ // Sleeping:durmiendo
						System.out.println("Interrumpiendo hilo3["+i+"]");
						theElecBillingExecutorThead02[i].interrupt();
					}
					theElecBillingExecutorThead02[i].setTerminar(true);
				}	
			}
		}
		
		public static void stopElectronicBillingService03(){
			if (theElecBillingExecutorThead03!=null){
				for(int i=0;i<theSubTheadsNumber;i++){
					if (theElecBillingExecutorThead03[i].getStatus().equals("S")){ // Sleeping:durmiendo
						System.out.println("Interrumpiendo hilo4["+i+"]");
						theElecBillingExecutorThead03[i].interrupt();
					}
					theElecBillingExecutorThead03[i].setTerminar(true);
				}	
			}
		}		
		
		public static OnlineInventoryExecutor getOnlineInventoryService(){
			return invOnlineExecutor;		
		}		
	
		public static void startOnlineIventoryService(){
			// CACS: Hilo de facturaci�n electr�nica
			/*
			if (elecBillingExecutor!=null){
				if(elecBillingExecutor.getStatus().equals("I")){ // Inactivo
					if(!elecBillingExecutor.isAlive()){
						elecBillingExecutor=new ElectronicBillingExecutor();
					}
					elecBillingExecutor.start();
				}
			}else{
				elecBillingExecutor=new ElectronicBillingExecutor();
				elecBillingExecutor.start();
			}
			*/
			
			// CACS: Hilo de inventario en l�nea
			if (invOnlineExecutor!=null){
				if(invOnlineExecutor.getStatus().equals("I")){ // Inactivo
					if(!invOnlineExecutor.isAlive()){
						invOnlineExecutor=new OnlineInventoryExecutor();
					}
					invOnlineExecutor.start();
				}
			}else{
				invOnlineExecutor=new OnlineInventoryExecutor();
				invOnlineExecutor.start();
			}
		}

		public static void stopOnlineIventoryService(){
			/*
			if (elecBillingExecutor!=null){
				if (elecBillingExecutor.getStatus().equals("S")){ // Sleeping:durmiendo
					System.out.println("Interrumpiendo hilo");
					elecBillingExecutor.interrupt();
				}
				elecBillingExecutor.setTerminar(true);
			}
			*/
			if (invOnlineExecutor!=null){
				if (invOnlineExecutor.getStatus().equals("S")){ // Sleeping:durmiendo
					System.out.println("Interrumpiendo hilo");
					invOnlineExecutor.interrupt();
				}
				invOnlineExecutor.setTerminar(true);
			}			
		}
		
		public static int getTheadNumber(){
			return theSubTheadsNumber;
		}
	
	}


