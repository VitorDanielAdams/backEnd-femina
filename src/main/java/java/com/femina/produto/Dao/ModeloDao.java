package Dao;

import Model.ModelosDosProdutos;

import java.com.femina.produto.Factory.ConectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModeloDao {

    private Connection connection;

    public ModeloDao(){
        this.connection = new ConectionFactory().getConection();
    }

    public void criarTabelaModelo() {
        String sql = "CREATE TABLE IF NOT EXISTS modelo ("+
        "id_modelo INT PRIMARY KEY AUTO_INCREMENT,"+
        "nome VARCHAR(50) NOT NULL"+
        ");";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.execute();
            stmt.close();

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void cadastrarModelo(ModelosDosProdutos modelo){
        String sql = "INSERT INTO modelo (nome) VALUES (?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, modelo.getNomeTipo());
            stmt.execute();

            ResultSet resultSet = stmt.getGeneratedKeys();

            while (resultSet.next()){
                modelo.setId(resultSet.getInt(1));
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<ModelosDosProdutos> listarModelos(){
        String sql = "SELECT * FROM modelo";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            List<ModelosDosProdutos> listaModelos = new ArrayList<>();

            while (resultSet.next()){
                ModelosDosProdutos modelo = new ModelosDosProdutos();

                modelo.setId(resultSet.getInt("id_modelo"));
                modelo.setNomeTipo(resultSet.getString("nome"));

                listaModelos.add(modelo);
            }

            return listaModelos;

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public ModelosDosProdutos selecionaModeloById(int id){
        String sql = "SELECT * FROM modelo WHERE id_modelo = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()){
                ModelosDosProdutos modelo = new ModelosDosProdutos();

                modelo.setId(resultSet.getInt("id_modelo"));
                modelo.setNomeTipo(resultSet.getString("nome"));

                return modelo;
            }

        } catch (SQLException e){
            throw new RuntimeException(e);
        }

        return null;
    }

    public void deletarModelo(ModelosDosProdutos modelo){
        String sql = "DELETE FROM modelo WHERE id_modelo = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, modelo.getId());

            stmt.execute();

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}