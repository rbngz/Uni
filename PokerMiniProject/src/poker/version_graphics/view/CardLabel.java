package poker.version_graphics.view;

import javafx.animation.ScaleTransition;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import poker.version_graphics.controller.PokerGameController;
import poker.version_graphics.model.Card;

public class CardLabel extends Label {
	private static String color = "card_back_red.png";
	public CardLabel() {
		super();
		this.getStyleClass().add("card");
	}

	public void setCard(Card card) {
		if (card != null) {
			String fileName = cardToFileName(card);
			Image image = new Image(this.getClass().getClassLoader().getResourceAsStream("poker/images/" + fileName));
			ImageView imv = new ImageView(image);
			imv.fitWidthProperty().bind(this.widthProperty());
			imv.fitHeightProperty().bind(this.heightProperty());
			imv.setPreserveRatio(true);
			//Animation
			ScaleTransition st = new ScaleTransition(Duration.millis(1000),this);
			st.setFromX(0);
			st.setToX(imv.getScaleX());
			st.play();
			this.setGraphic(imv);
		} else {
			Image image = new Image(this.getClass().getClassLoader().getResourceAsStream("poker/images/"+color));
			ImageView imv = new ImageView(image);
			imv.fitWidthProperty().bind(this.widthProperty().subtract(2));
			imv.fitHeightProperty().bind(this.heightProperty().subtract(2));
			imv.setPreserveRatio(true);
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
