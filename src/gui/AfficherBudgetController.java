/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Service.BudgetService;
import entity.budget;
import entity.pdf;
import java.io.IOException;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author ihebt
 */
public class AfficherBudgetController implements Initializable {

    budget b = new budget();
    BudgetService bs = new BudgetService();
    @FXML
    private TableColumn<budget, Integer> idcol;
    @FXML
    private TableColumn<budget, Float> budgetCol;
    @FXML
    private TableColumn<budget, Float> primeCol;
    @FXML
    private TableColumn<budget, LocalDate> dateCol;
    @FXML
    private TableColumn<budget, Float> budgetColmateriel;
    @FXML
    private TableColumn<budget, Float> budgetSalaireCol;
    @FXML
    private TableColumn<budget, Float> budgetSerciceCol;
    @FXML
    private TextField id;
    @FXML
    private TextField budget;
    @FXML
    private TextField prime;
    @FXML
    private TextField budgetService;
    @FXML
    private TextField budgetMateriel;
    @FXML
    private TextField budgetSalaire;
    @FXML
    private TextField recherche;
    @FXML
    private Button modifier;
    @FXML
    private Button delete;
    @FXML
    private DatePicker date;
    @FXML
    private Button back;
    @FXML
    private Button PDF;
    @FXML
    private Button Stat;
    @FXML
    private TableView<budget> tableview;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO   idCol.setCellValueFactory(cellData -> {
        idcol.setCellValueFactory(cellData -> {
            return new SimpleIntegerProperty(cellData.getValue().getId()).asObject();
        });

        budgetCol.setCellValueFactory(cellData -> {
            return new SimpleFloatProperty(cellData.getValue().getBudget()).asObject();
        });

        dateCol.setCellValueFactory(cellData -> {
            return new SimpleObjectProperty<LocalDate>(cellData.getValue().getDate());
        });

        primeCol.setCellValueFactory(cellData -> {
            return new SimpleFloatProperty(cellData.getValue().getPrime()).asObject();
        });
        budgetColmateriel.setCellValueFactory(cellData -> {
            return new SimpleFloatProperty(cellData.getValue().getBudget_materiel()).asObject();
        });
        budgetSalaireCol.setCellValueFactory(cellData -> {
            return new SimpleFloatProperty(cellData.getValue().getBudget_salaire()).asObject();
        });
        budgetSerciceCol.setCellValueFactory(cellData -> {
            return new SimpleFloatProperty(cellData.getValue().getBudget_service()).asObject();
        });

        List<budget> budgets = bs.getAll();
        ObservableList<budget> observablePlaylists = FXCollections.observableArrayList(budgets);
        tableview.setItems(observablePlaylists);

    }

    @FXML
    private void modifierBudget(ActionEvent event) {
        LocalDate d = date.getValue();

        if (budget.getText().isEmpty() || date.getValue() == null || prime.getText().isEmpty() || budgetMateriel.getText().isEmpty() || budgetSalaire.getText().isEmpty() || budgetService.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("NOT OK");
            alert.setHeaderText("modification non effectue");
            alert.setContentText("Click Cancel to exit.");
            System.out.println(d);
            alert.showAndWait();
        }
        if (!budget.getText().matches("\\d*\\.?\\d*")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Veuillez entrer un nombre réel (ex: 3.14) pour le budget.");
            alert.showAndWait();
            return;
        }

        if (!budgetMateriel.getText().matches("\\d*\\.?\\d*")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Veuillez entrer un nombre réel (ex: 3.14) pour le budget matériel.");
            alert.showAndWait();
            return;
        }

        if (!budgetSalaire.getText().matches("\\d*\\.?\\d*")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Veuillez entrer un nombre réel (ex: 3.14) pour le budget salaire.");
            alert.showAndWait();
            return;
        }

        if (!budgetService.getText().matches("\\d*\\.?\\d*")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Veuillez entrer un nombre réel (ex: 3.14) pour le budget service.");
            alert.showAndWait();
            return;
        }

        float budgetTotal = parseFloat(budgetMateriel.getText()) + parseFloat(budgetSalaire.getText()) + parseFloat(budgetService.getText());

        if (Math.abs(parseFloat(budget.getText()) - budgetTotal) > 0.001) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("La somme des budgets matériels, salaires et services doit être égale au budget total.");
            alert.showAndWait();
            return;
        } else {

            b.setBudget(parseFloat(budget.getText()));
            b.setDate(d);
            b.setPrime(parseFloat(prime.getText()));
            b.setBudget_materiel(parseFloat(budgetMateriel.getText()));
            b.setBudget_salaire(parseFloat(budgetSalaire.getText()));
            b.setBudget_service(parseFloat(budgetService.getText()));
            b.setId(parseInt(id.getText()));

            bs.modifier_budget(parseFloat(budget.getText()), d, parseFloat(prime.getText()), parseFloat(budgetMateriel.getText()), parseFloat(budgetService.getText()), parseFloat(budgetSalaire.getText()), b);

        }
        ObservableList<budget> budgets = FXCollections.observableList(bs.getAll());
        tableview.setItems(budgets);

    }

    @FXML
    private void deleteBudget(ActionEvent event) {
        b = tableview.getSelectionModel().getSelectedItem();

        if (b == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Cliquez sur un budget du table!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "voulez Vous  vraiment supprimer cette reclamation :" + b.getId() + " ?");
            alert.setHeaderText(null);
            //Getting Buttons
            Optional<ButtonType> result = alert.showAndWait();
            //Testing if the user clicked OK
            if (result.isPresent() && result.get() == ButtonType.OK) {
                bs.supprimer_budget(b);
                //updating user data after closing popup
                ObservableList<budget> budgets = FXCollections.observableList(bs.getAll());
                tableview.setItems(budgets);
            }
        }
    }

    @FXML
    private void backAjout(ActionEvent event) {
        try {
            //navigation
            Parent loader = FXMLLoader.load(getClass().getResource("AjouterBudget.fxml"));
            back.getScene().setRoot(loader);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    
    @FXML
    private void Stat(ActionEvent event) {

       
            try {
                Parent parent = FXMLLoader.load(getClass().getResource("/gui/Stat.fxml"));
                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                //stage.getIcons().add(new Image("/images/logo.png"));
                stage.setScene(scene);
                stage.initStyle(StageStyle.UTILITY);
                stage.show();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
      public void Stat() {
        this.Stat.setVisible(false);
    }

    @FXML
    private void Recherche(KeyEvent event) {
        BudgetService se = new BudgetService();
        String chaine = recherche.getText();
        populateTable(se.chercherVoyage(chaine));
    }

    private void populateTable(ObservableList<budget> branlist) {
        tableview.setItems(branlist);

    }
    
        @FXML
    private void PDF(MouseEvent event) {
        budget bud = tableview.getSelectionModel().getSelectedItem();

        pdf pd=new pdf();
        try{
                    pd.GeneratePdf("Gestion du Budget",bud,bud.getId());

            System.out.println("impression done");
        } catch (Exception ex) {
            Logger.getLogger(BudgetService.class.getName()).log(Level.SEVERE, null, ex);
            }
    }


    @FXML
    private void pressTABLE(MouseEvent event) {
        b = tableview.getSelectionModel().getSelectedItem();

        budget.setText("" + b.getBudget());
        date.setValue(b.getDate());
        budgetMateriel.setText("" + b.getBudget_materiel());
        prime.setText("" + b.getPrime());
        budgetService.setText("" + b.getBudget_salaire());
        budgetSalaire.setText("" + b.getBudget_service());
        id.setText("" + b.getId());

    }

}
