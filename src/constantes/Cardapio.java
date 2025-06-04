package constantes;

public class Cardapio {
    private int id_item;
    private String nome_item;
    private String descricao;
    private double preco;
    private String categoria;

    public Cardapio(int id_item, String nome_item, String descricao, double preco, String categoria) {
        id_item = id_item;
        this.nome_item = nome_item;
        this.descricao = descricao;
        this.preco = preco;
        this.categoria = categoria;
    }

    public int getId_item() {
        return id_item;
    }
    public void setId_item(int id_item) {
        this.id_item = id_item;
    }
    public String getNome_item() {
        return nome_item;
    }
    public void setNome_item(String nome_item) {
        this.nome_item = nome_item;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public double getPreco() {
        return preco;
    }
    public void setPreco(double preco) {
        this.preco = preco;
    }
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
