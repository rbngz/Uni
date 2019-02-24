import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class TextWindow extends Application {
    public static  void main(String[] args){
        launch(args);
    }

    public void start(Stage primaryStage){
        primaryStage.setTitle("Textfield");



        BorderPane pane = new BorderPane();

        Region r = new Region();
        r.setPrefSize(1,27);


        VBox leftColumn = new VBox(new Button("alpha"),new Button("beta"),r, new Button("delta"),new Button("gamma"));
        pane.setLeft(leftColumn);

        //textArea
        TextArea lorem = new TextArea("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n");
        lorem.setWrapText(true);
        lorem.setPrefSize(500,700);



        HBox bottom = new HBox(new Button("left"),new Button("right"));
        bottom.setLayoutX(lorem.getLayoutX());

        pane.setCenter(lorem);
        pane.setBottom(bottom);

        Scene myScene = new Scene(pane);


        primaryStage.setScene(myScene);
        primaryStage.show();

    }
}
