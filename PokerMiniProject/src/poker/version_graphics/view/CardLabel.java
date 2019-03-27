package poker.version_graphics.view;

import javafx.animation.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import poker.version_graphics.controller.PokerGameController;
import poker.version_graphics.model.Card;

public class CardLabel extends Label {
	private static String color = "card_back_red.png";
	private static int cardNum = 0;
	public CardLabel() {
		super();
		this.getStyleClass().add("card");
	}

	public void setCard(Card card) {
		if (card != null) {
			//TODO first animation to turn around back
			ScaleTransition st1 = new ScaleTransition(Duration.millis(500),this);
			st1.setFromX(this.getScaleX());
			st1.setToX(0);



			String fileName = cardToFileName(card);
			Image image = new Image(this.getClass().getClassLoader().getResourceAsStream("poker/images/" + fileName));
			ImageView imv = new ImageView(image);
			imv.fitWidthProperty().bind(this.widthProperty());
			imv.fitHeightProperty().bind(this.heightProperty());
			imv.setPreserveRatio(true);
			st1.setOnFinished(event -> {
				this.setGraphic(imv);
			});

			//second Animation to turn around front
			ScaleTransition st = new ScaleTransition(Duration.millis(500),this);
			st.setFromX(0);
			st.setToX(imv.getScaleX());
			SequentialTransition sqt = new SequentialTransition(new PauseTransition(Duration.millis(100*cardNum)),st1,st);
			sqt.play();
			cardNum++;
			if(cardNum>4){
				cardNum = 0;
			}

		} else {
			Image image = new Image(this.getClass().getClassLoader().getResourceAsStream("poker/images/"+color));
			ImageView imv = new ImageView(image);
			imv.fitWidthProperty().bind(this.widthProperty().subtract(2));
			imv.fitHeightProperty().bind(this.heightProperty().subtract(2));
			imv.setPreserveRatio(true);
			TranslateTransition tt = new TranslateTransition(Duration.millis(1000),this);
			tt.setFromX(500);
			tt.setToX(0);
			SequentialTransition sqt = new SequentialTransition(
					new PauseTransition(Duration.millis(100*cardNum)),
					tt
			);
			sqt.play();
			cardNum++;
			if(cardNum>4){
				cardNum=0;
			}

			this.setGraphic(imv);
		}
	}

	private String cardToFileName(Card card) {
		String rank = card.getRank().toString();
		String suit = card.getSuit().toString();
		return rank + "_of_" + suit + ".png";
	}
	public static void setColor(String colorChoice){
		color = colorChoice;
	}

}
