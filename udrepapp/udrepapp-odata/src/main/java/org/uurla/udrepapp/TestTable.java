package org.uurla.udrepapp;

public class TestTable {
long id;
String Nome;
String Type;
double Days;



public TestTable(long id, String nome, String type, double days) {
	super();
	this.id = id;
	Nome = nome;
	Type = type;
	Days = days;
}
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public String getNome() {
	return Nome;
}
public void setNome(String nome) {
	Nome = nome;
}
public String getType() {
	return Type;
}
public void setType(String type) {
	Type = type;
}
public double getDays() {
	return Days;
}
public void setDays(double days) {
	Days = days;
}

}
