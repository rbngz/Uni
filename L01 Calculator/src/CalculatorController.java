public class CalculatorController {
    final private CalculatorModel model;
    final private CalculatorView view;

    protected CalculatorController(CalculatorModel model, CalculatorView view){
        this.model = model;
        this.view = view;



        //Label displays button clicked
        for(int i =0; i<9; i++) {
            final String num = Integer.toString(i+1);

            view.numbers[i].setOnAction((event -> {
                String text = view.textBar.getText();
                view.textBar.setText(text+ num);

            }));
        }

        view.zero.setOnAction(event -> {
            String text = view.textBar.getText();
            view.textBar.setText(text + 0);
        });

        //plus operator
        view.plus.setOnAction(event -> {
            String text = view.textBar.getText();
            view.textBar.setText(text + "+");
        });

        //equals operator
        view.equals.setOnAction(event -> {
            String text = view.textBar.getText();
            view.textBar.setText(Integer.toString(model.calculate(text)));
        });

        //Delete button
        view.delete.setOnAction(event -> {
            view.textBar.setText("");
        });





    }
}
