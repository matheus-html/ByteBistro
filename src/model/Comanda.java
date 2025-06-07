package model;

public class Comanda {
    private int id_comanda;
    private int id_item;
    private int id_mesa;
    private int qnt_item;

    public Comanda(int id_item, int id_mesa, int qnt_item) {
        this.id_item = id_item;
        this.id_mesa = id_mesa;
        this.qnt_item = qnt_item;
    }

    public Comanda(int id_comanda, int id_item, int id_mesa, int qnt_item) {
        this.id_comanda = id_comanda;
        this.id_item = id_item;
        this.id_mesa = id_mesa;
        this.qnt_item = qnt_item;
    }

    public int getId_comanda() {
        return id_comanda;
    }

    public void setId_comanda(int id_comanda) {
        this.id_comanda = id_comanda;
    }

    public int getId_item() {
        return id_item;
    }

    public void setId_item(int id_item) {
        this.id_item = id_item;
    }

    public int getId_mesa() {
        return id_mesa;
    }

    public void setId_mesa(int id_mesa) {
        this.id_mesa = id_mesa;
    }

    public int getQnt_item() {
        return qnt_item;
    }

    public void setQnt_item(int qnt_item) {
        this.qnt_item = qnt_item;
    }
}
