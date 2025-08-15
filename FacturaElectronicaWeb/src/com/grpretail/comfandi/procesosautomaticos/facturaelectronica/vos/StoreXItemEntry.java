// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 08/11/2007 10:26:23
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   TrxVentaPosCVO_Imp.java

package com.grpretail.comfandi.procesosautomaticos.facturaelectronica.vos;

import java.util.Hashtable;

// Referenced classes of package com.sodimac.bonos.bono:
//            TrxVentaPos_Imp, TrxVentaPosCVO

public class StoreXItemEntry {
	//String operador;
	//String fechaHoraTrx;
	Long store;
	//Long almacen;
	Hashtable theHSItems; // Contiene una lista de los almacenes en los que se vendió el producto dato el itemCode

	public StoreXItemEntry(){
		store = 0L;
		//almacen = 0L;
		theHSItems = new Hashtable();
	}

	public Long getStore() {
		return store;
	}
	public void setStore(Long itemCode) {
		this.store = itemCode;
	}
	public Hashtable getHSItems() {
		return theHSItems;
	}
	public void setHSItems(Hashtable stores) {
		theHSItems = stores;
	}
	/*
	public Long getAlmacen() {
		return almacen;
	}
	public void setAlmacen(Long almacen) {
		this.almacen = almacen;
	}
	*/
}