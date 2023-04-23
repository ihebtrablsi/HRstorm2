/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

/**
 *
 * @author ihebt
 */

import java.util.List;
import entity.budget;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tools.MaConnection;
public class BudgetService implements BudgetInterface <budget> {
Connection cnx;
    public BudgetService ()
    {
        cnx = MaConnection.getInstance().getCnx();
    }
    @Override
    public void ajouter(budget t) {
     try { 
            String sql = "insert into budget(id,budget,date,prime,budget_materiel,budget_salaire,budget_service)"
                    + "values (?,?,?,?,?,?,?)";
            PreparedStatement ste = cnx.prepareStatement(sql);
           ste.setInt(1,t.getId());
            ste.setFloat(2,t.getBudget());
            ste.setDate(3, Date.valueOf(t.getDate()));
            ste.setFloat(4,t.getPrime() );
            ste.setFloat(5, t.getBudget_materiel());
            ste.setFloat(6, t.getBudget_salaire());
            ste.setFloat(7,t.getBudget_service());
            
            
            
            
           
            ste.executeUpdate();
            System.out.println("budget ajoutée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<budget> getAll() {
    
        List<budget> budgets= new ArrayList<>();
    try {
        String sql = "select * from budget ";
        Statement ste = cnx.createStatement();
        ResultSet s = ste.executeQuery(sql);
        
        while (s.next()) {
          budget m =new budget(s.getInt("id"),s.getFloat("budget"),s.getDate("date").toLocalDate(),s.getFloat("prime"),s.getFloat("budget_materiel"),s.getFloat("budget_salaire"),s.getFloat("budget_service"));
            budgets.add(m);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return budgets;
        
    }

    @Override
    public void supprimer_budget(budget t) {
         String sql = "delete from budget where id=?";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, t.getId());
            ste.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    
    }

    @Override
    public void modifier_budget(float budget, LocalDate d, float prime, float budget_materiel, float budget_Service, float budget_salaire, budget t) {
        String sql = "update budget set budget=?,date=?,prime=?,budget_materiel=?,budget_salaire=?,budget_service=? where id=?";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setFloat(1, budget);
            ste.setDate(2, Date.valueOf(d));
            ste.setFloat(3,prime);
            ste.setFloat(4,budget_materiel );
            ste.setFloat(5,budget_salaire);
            ste.setFloat(6,budget_Service);
            ste.setInt(7, t.getId());
            
            
            ste.executeUpdate();
            System.out.println("modification effectuée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
public List<budget> RechercheBudget(float budget) {
    List<budget> bud = new ArrayList<>();
    try {
        String req ="select * from budget WHERE budget = '"+budget+"'";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while(rs.next()) {
           budget b = new budget();
           b.setId(rs.getInt("id"));
           b.setBudget(rs.getFloat("budget"));
           b.setDate(rs.getDate("date").toLocalDate());
           b.setPrime(rs.getFloat("prime"));
           b.setBudget_materiel(rs.getFloat("budget_materiel"));
           b.setBudget_salaire(rs.getFloat("Budget_salaire"));
           b.setBudget_service(rs.getFloat("Budget_service"));
           bud.add(b);
        }
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }
    return bud;
}
     public ObservableList<budget> chercherVoyage(String chaine){
          String sql="SELECT * FROM budget WHERE (budget LIKE ? or date LIKE ? or budget_materiel LIKE ? or budget_salaire LIKE ? or budget_service = ? )";
            
             Connection cnx= MaConnection.getInstance().getCnx();
            String ch=""+chaine+"%";
         System.out.println(sql);
            ObservableList<budget> myList= FXCollections.observableArrayList();
        try {
           
            Statement ste= cnx.createStatement();
           // PreparedStatement pst = myCNX.getCnx().prepareStatement(requete6);
            PreparedStatement stee =cnx.prepareStatement(sql);  
            stee.setString(1, ch);
            stee.setString(2, ch);
            stee.setString(3, ch);
            stee.setString(4, ch);
            stee.setString(5, ch);
         System.out.println(stee);

            ResultSet rs = stee.executeQuery();
            while (rs.next()){
                budget b = new budget();
           b.setId(rs.getInt("id"));
           b.setBudget(rs.getFloat("budget"));
           b.setDate(rs.getDate("date").toLocalDate());
           b.setPrime(rs.getFloat("prime"));
           b.setBudget_materiel(rs.getFloat("budget_materiel"));
           b.setBudget_salaire(rs.getFloat("Budget_salaire"));
           b.setBudget_service(rs.getFloat("Budget_service"));

                myList.add(b);
                System.out.println("titre trouvé! ");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
      }


    @Override
    public List<Float>  getBudget(){
              List<Float> budgets= new ArrayList<>();
    try {
        String sql = "select budget from budget ";
        Statement ste = cnx.createStatement();
        ResultSet s = ste.executeQuery(sql);
        
        while (s.next()) {
         budgets.add(s.getFloat("budget"));
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return budgets;
    }
    
}
