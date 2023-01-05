package connection;


import com.example.quizz5.Flight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FlightUtil {
    public FlightUtil() {
    }

    //String direction, Date date, int number, float price
    public static void createTable() throws SQLException, RuntimeException {
        String createSql = "CREATE TABLE IF NOT EXISTS FLIGHTS_TB" +
                "(ID INTEGER not NULL AUTO_INCREMENT, " +
                " Direction VARCHAR(255), " +
                " Date1 DATE, " +
                " Number1 INTEGER NOT NULL, " +
                " Price FLOAT NOT NULL, " +
                //" Quantity INTEGER NOT NULL, " +
                " PRIMARY KEY ( ID ))";

  //      String createSql = "DROP TABLE FLIGHTS_TB";

        JDBCUtil.getStatement().executeUpdate(createSql);

        System.out.println("Created Table in given database if it is not exists");
    }

    public static void insertItem(Flight flight) throws SQLException, RuntimeException {

      //  LocalDate myDate =LocalDate.parse("2014-02-14");
        String insertSql = "INSERT INTO FLIGHTS_TB(Direction, Date1, Number1, Price) VALUES( "
                +"'"+flight.getDirection()+"', "
                +"'"+flight.getDate() +"', "
                +"'"+flight.getNumber()+"', "
                +"'"+flight.getPrice()+"' )";

        JDBCUtil.getStatement().executeUpdate(insertSql);

        System.out.println("Inserted flight "+flight.getDirection()+" into table Successfully!");
    }

    public static Map<String, Long> getMapData() throws SQLException {
        String selectAll = "SELECT * FROM FLIGHTS_TB";
        List<Flight> flights = new ArrayList<>();

        ResultSet resultSet = JDBCUtil.getStatement().executeQuery(selectAll);
        while(resultSet.next()){
            flights.add(new Flight(resultSet.getString("Direction"),
                    resultSet.getDate("Date1").toLocalDate(),
                    resultSet.getInt("Number1"),
                    resultSet.getFloat("Price")));
        }
    //    System.out.println(flights);

        Map<String, Long> myMap = flights.stream().
                collect(Collectors.groupingBy(flight -> flight.getDirection(), Collectors.counting()));
    //    System.out.println(myMap);
        return  myMap;
    }



    public static ObservableList<PieChart.Data> getData() throws SQLException {
        Map<String, Long> map = getMapData();
        ObservableList<PieChart.Data> myList = FXCollections.observableArrayList(
                );

        for (Map.Entry<String, Long> entry : map.entrySet()) {
          //  System.out.println(entry.getKey() + ":" + entry.getValue());
            myList.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        return myList;
    }

}
