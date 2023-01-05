package connection;

import com.example.quizz5.Flight;
import com.sun.javafx.collections.MappingChange;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FlightUtilTest{

    @BeforeEach
    void setUp() {
        System.out.println(" before each");
    }

    @AfterEach
    void tearDown() {
        System.out.println(" after each");
    }

    @Test
    public  void createTableTest() throws SQLException {
        FlightUtil.createTable();
        DatabaseMetaData dbm = JDBCUtil.getConnection().getMetaData();
// check if "FLIGHTS_TB" table is there
        ResultSet tables = dbm.getTables(null, null, "FLIGHTS_TB", null);
        Assertions.assertEquals(tables.next(), true, "not correct");

    }

    @Test
    public void insertTest() throws SQLException {
        String selectTable = "SELECT COUNT(*) FROM FLIGHTS_TB";
        ResultSet resultSet = JDBCUtil.getStatement().executeQuery(selectTable);
        resultSet.next();
        int count1 = resultSet.getInt(1);
        System.out.println();

        FlightUtil.insertItem(new Flight("testDirectionCity", LocalDate.now(), 5, 10));
        resultSet = JDBCUtil.getStatement().executeQuery(selectTable);
        resultSet.next();
        int count2 = resultSet.getInt(1);
        Assertions.assertEquals(count2-count1, 1, "insert error :(");
        System.out.println(count2+" "+count1);
        System.out.println("insert test was successfully completed!");
    }

    @Test
    public void getMapDataTest() throws SQLException {

        String selectData = "SELECT Direction,COUNT(*) FROM FLIGHTS_TB GROUP BY Direction";

        Map<String, Long> map = new HashMap<>();
        ResultSet resultSet = JDBCUtil.getStatement().executeQuery(selectData);
        while (resultSet.next()){
            map.put(resultSet.getString("Direction"),
                    resultSet.getLong("COUNT(*)"));
        }

        Map<String, Long> map2 = FlightUtil.getMapData();

        System.out.println(map);
        System.out.println(map2);
        Assertions.assertEquals(map, map2, "getMapData nw");
        System.out.println("GetMapData test was successfully completed");
    }

    @Test
    public void getDataTest() throws SQLException {
        Map<String, Long> map = FlightUtil.getMapData();
        ObservableList<PieChart.Data> myList = FXCollections.observableArrayList();

        for (String direction : map.keySet()) {
            myList.add(new PieChart.Data(direction, map.get(direction)));
        }

        ObservableList<PieChart.Data> list = FlightUtil.getData();

        for (int i=0; i<list.size(); i++) {
        Assertions.assertEquals(list.get(i).toString(), myList.get(i).toString(), "getData not working "+i);
        }
        System.out.println("GetData test was successfully completed");
    }

}