package gui;

import tools.MaConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;

public class StatController implements Initializable {

    @FXML
    private PieChart stat;

    private Statement st;
    private ResultSet rs;
    private Connection cnx;
    ObservableList<PieChart.Data> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cnx = MaConnection.getInstance().getCnx();
        stat();
    }

    private void stat() {
        try {
            String query = "SELECT SUM(budget), date FROM budget GROUP BY date ASC;";
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                LocalDate date = rs.getDate("date").toLocalDate();
                Double budget = rs.getDouble("SUM(budget)");
                data.add(new PieChart.Data(date.toString(), budget));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        stat.setTitle("**Statistiques des Budgets Par Date**");
        stat.setLegendSide(Side.LEFT);
        stat.setData(data);
    }
}
