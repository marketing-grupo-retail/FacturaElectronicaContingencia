package com.grpretail.comfandi.procesosautomaticos.facturaelectronica.vos;

public class Period {
	Time time;
	int number;
	public Period(){
		time = new Time();
		number = 0;
	}
	public Time getTime() {
		return time;
	}
	public void setTime(Time time) {
		this.time = time;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}

}
