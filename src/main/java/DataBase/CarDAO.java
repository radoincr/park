package DataBase;

import Parking.Car;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;


public class CarDAO {
    private Connection cnx;
    private Statement statement;
    private PreparedStatement preparedStatement;

    private String url = "jdbc:mysql://127.0.0.1:3306/cars";
    private String user = "root";
    private String password = "";
    private ResultSet resultSet;

    public CarDAO() throws SQLException {
        this.cnx = DriverManager.getConnection(url, user, password);
    }

    public boolean saveEnter(Car object) throws SQLException {
        try {
            String rq = "INSERT INTO `cars`(`car_id`, `situation`, `time_entrer`, `time_sortie`) VALUES (?,?,?,?)";

            this.preparedStatement = this.cnx.prepareStatement(rq);

            this.preparedStatement.setInt(1, object.getId());
            this.preparedStatement.setBoolean(2, true);
            this.preparedStatement.setTime(3, Time.valueOf(LocalTime.now()));
            this.preparedStatement.setTime(4, Time.valueOf(LocalTime.now()));
            this.preparedStatement.execute();
        } catch (SQLException e) {
            // Handle SQLException specifically
            e.printStackTrace();
            return false; // Indicate failure
        }
        return true;
    }

    public boolean saveSortie(Integer car_id) throws SQLException {
        try {
            String re = "UPDATE `cars` SET `situation`=?,`time_sortie`=? WHERE car_id=?";
            this.preparedStatement = this.cnx.prepareStatement(re);

            this.preparedStatement.setBoolean(1, false);
            this.preparedStatement.setTime(2, Time.valueOf(LocalTime.now()));
            this.preparedStatement.setInt(3, car_id);

            this.preparedStatement.execute();
        } catch (SQLException e) {
            // Handle SQLException specifically
            e.printStackTrace();
            return false; // Indicate failure
        }
        return true;
    }



    public ArrayList<CarDB> getAll() throws SQLException {
        ArrayList<CarDB> lis = new ArrayList<>();
        String re = "SELECT * FROM cars";

        try {
            this.statement = this.cnx.createStatement();
            this.resultSet = this.statement.executeQuery(re);

            while (this.resultSet.next()) {
                lis.add(new CarDB(
                        this.resultSet.getInt(1),
                        this.resultSet.getInt(2),
                        this.resultSet.getBoolean(3),
                        this.resultSet.getTime(4),
                        this.resultSet.getTime(5)
                ));
            }
        } catch (SQLException e) {
            // Handle SQLException specifically
            e.printStackTrace();
        } finally {
            // Close resources (ResultSet, Statement) in the finally block
            if (this.resultSet != null) {
                this.resultSet.close();
            }
            if (this.statement != null) {
                this.statement.close();
            }
        }
        return lis;
    }

    public void drawCombinedChart() {
        try {
            ArrayList<CarDB> dataList = getAll();

            JFrame frame = new JFrame("Combined Chart");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Create dataset for the curve
            XYSeries curveSeries = new XYSeries("Curve");
            for (CarDB car : dataList) {
                curveSeries.add(car.getId(), car.getTime_entrer().getMinutes());
            }
            XYSeriesCollection curveDataset = new XYSeriesCollection(curveSeries);

            // Create dataset for the bar graph
            XYSeries barSeries = new XYSeries("Bar");
            for (CarDB car : dataList) {
                barSeries.add(car.getId(), car.getTime_sortie().getMinutes());
            }
            XYSeriesCollection barDataset = new XYSeriesCollection(barSeries);

            // Create the combined chart
            JFreeChart combinedChart = ChartFactory.createXYLineChart(
                    "Combined Chart",
                    "Car ID",
                    "Minutes",
                    curveDataset
            );

            // Add the bar graph to the combined chart
            XYPlot plot = combinedChart.getXYPlot();
            plot.setDataset(1, barDataset);
            plot.setRenderer(1, new XYLineAndShapeRenderer(false, true));

            // Create chart panel and set it to the frame
            ChartPanel chartPanel = new ChartPanel(combinedChart);
            frame.getContentPane().add(chartPanel);

            frame.setSize(600, 400);
            frame.setVisible(true);
        } catch (SQLException e) {
            // Handle SQLException specifically or log the error
            e.printStackTrace();
        }
    }


    public void drawBarChart() {
        try {
            ArrayList<CarDB> dataList = getAll();

            JFrame frame = new JFrame("Bar Chart");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Create dataset for the bar chart
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            for (CarDB car : dataList) {
                dataset.addValue(car.getTime_sortie().getMinutes(), "Car ID", String.valueOf(car.getId()));
            }

            // Create the bar chart
            JFreeChart barChart = ChartFactory.createBarChart(
                    "Bar Chart",
                    "Car ID",
                    "Minutes",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true, true, false
            );

            // Customize the chart if needed
            CategoryPlot plot = barChart.getCategoryPlot();
            BarRenderer renderer = new BarRenderer();
            plot.setRenderer(renderer);

            // Create chart panel and set it to the frame
            ChartPanel chartPanel = new ChartPanel(barChart);
            frame.getContentPane().add(chartPanel);

            frame.setSize(600, 400);
            frame.setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private CategoryDataset createDataset(ArrayList<CarDB> dataList) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (CarDB car : dataList) {
            dataset.addValue(car.getTime_entrer().getMinutes(), "Entrance Time", String.valueOf(car.getId()));
            dataset.addValue(car.getTime_sortie().getMinutes(), "Exit Time", String.valueOf(car.getId()));
        }

        return dataset;
    }
    public void drawCurveChart() {
        try {
            ArrayList<CarDB> dataList = getAll();

            JFrame frame = new JFrame("Curve Chart");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Creating a custom JPanel for drawing the curve
            JPanel chartPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    drawCurve(g, dataList);
                }
            };

            frame.getContentPane().add(chartPanel);
            frame.setSize(600, 400);
            frame.setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Existing code...

    private void drawCurve(Graphics g, ArrayList<CarDB> dataList) {
        // Custom drawing logic for the curve
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLUE);

        for (CarDB car : dataList) {
            int x = car.getId() * 30;  // Adjust the scaling factor as needed
            int y = car.getTime_entrer().getMinutes();  // Adjust the scaling factor as needed

            g2d.fillOval(x, y, 5, 5);  // Adjust the size of the points as needed
        }
    }
// Import statements...


        // Existing code...

        public void drawColumnChart() {
            try {
                ArrayList<CarDB> dataList = getAll();

                JFrame frame = new JFrame("Column Chart");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                // Create dataset for the column chart
                DefaultCategoryDataset dataset = new DefaultCategoryDataset();

                for (CarDB car : dataList) {
                    dataset.addValue(car.getTime_sortie().getMinutes(), "Car ID", String.valueOf(car.getId()));
                }

                // Create the column chart
                JFreeChart columnChart = ChartFactory.createBarChart(
                        "Column Chart",
                        "Car ID",
                        "Minutes",
                        dataset,
                        PlotOrientation.VERTICAL,
                        true, true, false
                );

                // Customize the chart if needed
                CategoryPlot plot = columnChart.getCategoryPlot();
                BarRenderer renderer = new BarRenderer();
                plot.setRenderer(renderer);

                // Create chart panel and set it to the frame
                ChartPanel chartPanel = new ChartPanel(columnChart);
                frame.getContentPane().add(chartPanel);

                frame.setSize(600, 400);
                frame.setVisible(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Existing code...
    }


