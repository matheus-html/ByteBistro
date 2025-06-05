package constantes;

public class Cliente {
    private int id_cliente;
    private String nomeCliente;
    private String cpf;
    private String telefone;
    private String email;


    public Cliente(String nomeCliente, String cpf, String telefone, String email) {
        this.nomeCliente = nomeCliente;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
    }

    public Cliente(int id_cliente, String nomeCliente, String cpf, String telefone, String email) {
        this.id_cliente = id_cliente;
        this.nomeCliente = nomeCliente;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
    }

    public int getId() {
        return id_cliente;
    }

    public void setId(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
