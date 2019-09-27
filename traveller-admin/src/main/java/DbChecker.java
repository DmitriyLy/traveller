import org.dmly.traveller.app.service.UserService;

import java.sql.*;

public class DbChecker {
    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection connection = DriverManager.getConnection("jdbc:mysql://traveller_db:3306/traveller", "traveller", "traveller");
            //Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/traveller", "traveller", "traveller");
            System.out.println("\n >>> Connection is successful <<< \n");

            Statement statement = connection.createStatement();
            //Retrieving the data
            ResultSet rs = statement.executeQuery("Show tables");
            System.out.println("Tables in the current database: ");
            while(rs.next()) {
                System.out.print(rs.getString(1));
                System.out.println();
            }

            System.out.println("\n-----------------------------------\n");

            rs = statement.executeQuery("SELECT * FROM USER");
            System.out.println("SELECT * FROM USER >>>");
            while(rs.next()) {
                System.out.print(rs.getString(1)
                        + " " + rs.getString(2)
                        + " " + rs.getString(5));
                System.out.println();
            }
            System.out.println("\n-----------------------------------\n");

            connection.close();

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

       /* Weld weld = new Weld();
        WeldContainer weldContainer = weld.initialize();
        weldContainer.select(UserService.class).get();*/

    }
}
