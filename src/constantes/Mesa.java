package constantes;

public class Mesa {
    private int id_mesa;
    private int capacidade;
    private String localizacao;
    private String statusMesa;

    public Mesa(int id_mesa, int capacidade, String localizacao, String statusMesa) {
        this.id_mesa = id_mesa;
        this.capacidade = capacidade;
        this.localizacao = localizacao;
        this.statusMesa = statusMesa;
    }

    public int getId_mesa() {
        return id_mesa;
    }
    public void setId_mesa(int id_mesa) {
        this.id_mesa = id_mesa;
    }
    public int getCapacidade() {
        return capacidade;
    }
    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }
    public String getLocalizacao() {
        return localizacao;
    }
    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getStatusMesa() {
        return statusMesa;
    }

    public void setStatusMesa(String statusMesa) {
        this.statusMesa = statusMesa;
    }
}
