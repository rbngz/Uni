import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CalculatorView {
    private CalculatorModel model;
    private Stage primaryStage;

    protected Label textBar;
    protected Button[] numbers = new Button[9];
    protected Button plus;
    protected Button equals;
    protected Button delete;
    protected Button zero;

    protected CalculatorView(Stage primaryStage, CalculatorModel model){
        this.primaryStage = primaryStage;
        this.model = model;

        primaryStage.setTitle("Calculator");

        BorderPane gui = new BorderPane();

        //create Buttons
        for(int i = 0; i<9;i++){
            numbers[i] = new Button(Integer.toString(i+1));
        }

        zero = new Button("0");


        // distribute buttons to grid
        GridPane buttons = new GridPane();
        int counter = 0;
        for(int i = 0;i<3;i++){
            for (int j = 0;j<3;j++) {
                buttons.add(numbers[counter], j, i);
                counter++;
            }
        }

        //Create top label
        textBar = new Label();

        //Create operators
        plus = new Button("+");
        plus.setId("plus-button");
        equals = new Button("=");
        equals.setId("equals-button");
        VBox operators = new VBox(plus,equals,zero);

        delete = new Button("Delete");
        delete.setPrefWidth(360);
        delete.setId("red-button");


        //add elements to borderpane
        gui.setCenter(buttons);
        gui.setTop(textBar);
        gui.setRight(operators);
        gui.setBottom(delete);

        Scene scene = new Scene(gui);
        scene.getStylesheets().add(
                getClass().getResource("Calculator.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setWidth(360);
        primaryStage.setHeight(520);
    }

    public void start(){
        primaryStage.show();
    }
}
