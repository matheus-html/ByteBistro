package model;

import java.sql.Timestamp;

public class Reserva {
    private int id_reserva;
    private int id_cliente;
    private int id_mesa;
    private Timestamp data_hora;
    private int num_pessoas;

    public Reserva(int id_cliente, int id_mesa, Timestamp data_hora, int num_pessoas) {
        this.id_cliente = id_cliente;
        this.id_mesa = id_mesa;
        this.data_hora = data_hora;
        this.num_pessoas = num_pessoas;
    }

    public Reserva(int id_reserva, int id_cliente, int id_mesa, Timestamp data_hora, int num_pessoas) {
        this.id_reserva = id_reserva;
        this.id_cliente = id_cliente;
        this.id_mesa = id_mesa;
        this.data_hora = data_hora;
        this.num_pessoas = num_pessoas;
    }

    public int getId_reserva() {
        return id_reserva;
    }
    public void setId_reserva(int id_reserva) {
        this.id_reserva = id_reserva;
    }
    public int getId_cliente() {
        return id_cliente;
    }
    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }
    public int getId_mesa() {
        return id_mesa;
    }
    public void setId_mesa(int id_mesa) {
        this.id_mesa = id_mesa;
    }
    public Timestamp getData_hora() {
        return data_hora;
    }
    public void setData_hora(Timestamp data_hora) {
        this.data_hora = data_hora;
    }
    public int getNum_pessoas() {
        return num_pessoas;
    }
    public void setNum_pessoas(int num_pessoas) {
        this.num_pessoas = num_pessoas;
    }
}
