package constantes;

public class Mesa {
    private int id_mesa;
    private int capacidade;
    private String localizacao;
    private StatusMesa status;

    public Mesa() {
        this.status = StatusMesa.DISPONIVEL;
    }

    public Mesa(int capacidade, String localizacao, StatusMesa status) {
        this.capacidade = capacidade;
        this.localizacao = localizacao;
        this.status = status;
    }

    public enum StatusMesa {
        DISPONIVEL("disponivel"),
        OCUPADA("ocupada"),
        MANUTENCAO("manutencao");

        private final String valor;

        StatusMesa(String valor) {
            this.valor = valor;
        }

        public String getValor() {
            return valor;
        }
    }

    @Override
    public String toString() {
        return "Mesa{" +
                "idMesa=" + id_mesa +
                ", capacidade=" + capacidade +
                ", localizacao='" + localizacao + '\'' +
                ", status=" + status +
                '}';
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
    public StatusMesa getStatus() {
        return status;
    }
    public void setStatus(StatusMesa status) {
        this.status = status;
    }
}
