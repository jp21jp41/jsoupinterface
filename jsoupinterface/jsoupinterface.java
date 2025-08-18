//Jsoup Interface
package jsoupinterface;

//Import packages (Configured in module-info.java)
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;


// Class with extend for JavaFX
public class jsoupinterface extends Application {
	// Function to change the html into one editable string
    public void textScene(Stage primaryStage) throws IOException {
    	// Default: gets jsoup.org
    	Document doc = Jsoup.connect("http://jsoup.org").get();
    	// Assign HTML variable
    	String html = doc.html();
    	// Constructor functions and variable assignment
    	BorderPane border = new BorderPane();
    	TextArea textpiece = new TextArea(html);
    	// Set Stage Items
    	border.setCenter(textpiece);
    	Scene scene2 = new Scene(border,800,800);
    	primaryStage.setScene(scene2);
    	primaryStage.show();
    };
	// HTMLElement class: Creates a class to be implemented with observableArrayList
	public static class HTMLElement {
		// Class Attributes
		private final String row;
		private final String html;
		private final String deleteString;
		// HTMLElement Constructor
		public HTMLElement(String row, String html, String deleteString) {
			this.row = row;
			this.html = html;
			this.deleteString = deleteString;
		}
		// Functions to return the row as well as the HTML each
		public String getrow() {return row;}
		public String gethtml() {return html;}
		public String getdeletestring() {return deleteString;}
	}
	    // Function to change the html into element-by-element format
	    public void elementScene(Stage primaryStage) throws IOException {
	    	// Listview object with HTMLElement data type
	    	ListView<HTMLElement> listView = new ListView<>();
	   		// Set Cell Factory to allow proper observableArrayList compatibility
	   		listView.setCellFactory(new Callback<ListView<HTMLElement>, ListCell<HTMLElement>>() {
	    		// Call function with element adjustments
	   			public ListCell<HTMLElement> call(ListView<HTMLElement> listView) {
	    			return new ListCell<HTMLElement>() {
	    				// HBox graphic
	    				private final HBox graphic = new HBox(10);
	    				StackPane graphicstack = new StackPane();
	    				// Labels
						private final Label label1 = new Label();
	    				private final Label label2 = new Label();
	    				ScrollPane htmlscroll = new ScrollPane(label2);
	    				private final Button deleteButton = new Button();
	    				{
							// Add the labels to the HBox and set width and height
	    					graphic.getChildren().addAll(label1, htmlscroll);
	    					graphicstack.getChildren().addAll(graphic, deleteButton);
	    					StackPane.setAlignment(deleteButton, Pos.TOP_RIGHT);
	   						label1.setPrefWidth(100);
	  						label1.setPrefHeight(150);
	    				}
	    				// Item Update override
	    				@Override
	    				protected void updateItem(HTMLElement item, boolean empty) {
	    					// call super class
	    					super.updateItem(item, empty);
	    					// set empty text as null or set the text and HTML
	    					if (empty || item == null) {
	    						setGraphic(null);
	    						setText(null);
	    					} else {
	    						label1.setText(item.getrow());
	    						label2.setText(item.gethtml());
	    						deleteButton.setText(item.deleteString);
	    						setGraphic(graphicstack);
	    					}
	    				}
	    			};
	   			}
	    	});
	    	// Default: gets jsoup.org
			Document doc = Jsoup.connect("http://jsoup.org").get();
			// Select elements
			Elements elements = doc.select("*");
			// Add Border and a scene with the border, instantiate pieces with
			// Constructor functions and variable assignment
			BorderPane border = new BorderPane();
			Scene scene2 = new Scene(border,800,800);
			TextArea textpiece = new TextArea();
			ContextMenu contextMenu = new ContextMenu();
			ObservableList<HTMLElement> items = FXCollections.observableArrayList();
			int row = 1;
			// ForEach loop: Add rows and HTML one after another
			for (Element element : elements){
				String elementString = element.html();
				items.add(new HTMLElement(String.valueOf(row), elementString, "Delete Row " + String.valueOf(row)));
				row ++;
			}
			// Set stage items
			listView.setItems(items);
			textpiece.setContextMenu(contextMenu);
			TableView<HTMLElement> tableView = new TableView<>();
			tableView.setItems(items);
			ListView<Button> sideButtons = new ListView<Button>();
			Button toggleButton = new Button("Toggle");
			Button returnButton = new Button("Return");
			sideButtons.getItems().addAll(toggleButton, returnButton);
			border.setCenter(listView);
			border.setRight(sideButtons);
			primaryStage.setScene(scene2);
			primaryStage.show();
	    }
	    // Function override to set stage
		@Override
		public void start(Stage primaryStage) {
			// Try-except: Primary Stage setup
			try {
				Button button1 = new Button("Scrape Data");
				Button button2 = new Button("Add HTML Manually");
				HBox buttonBox = new HBox(10);
				buttonBox.getChildren().addAll(button1, button2);
		        Scene scene1 = new Scene(buttonBox, 400, 400);
				// Title set
				primaryStage.setTitle("HTML data checker");
				// Add CSS file to scene, set scene, show
				scene1.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				button1.setOnAction(e -> {
					try {
						elementScene(primaryStage);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				});
				button2.setOnAction(e -> {
					try {
						textScene(primaryStage);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				});
				primaryStage.setScene(scene1);
				primaryStage.show();
			} catch(Exception e) {
				e.printStackTrace(); //Exception
			}
		}
		
		public static void main(String[] args) throws IOException {
			launch(args); //Runs whole JavaFX
		}
}

