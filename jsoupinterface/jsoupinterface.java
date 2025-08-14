package jsoupinterface;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class jsoupinterface extends Application {
	public static class HTMLElement {
		private final String row;
		private final String html;
		
		public HTMLElement(String row, String html) {
			this.row = row;
			this.html = html;
		}
		
		public String getrow() {return row;}
		public String gethtml() {return html;}
	}
		@Override
		public void start(Stage primaryStage) {
			ListView<HTMLElement> listView = new ListView<>();
			listView.setCellFactory(new Callback<ListView<HTMLElement>, ListCell<HTMLElement>>() {
				public ListCell<HTMLElement> call(ListView<HTMLElement> listView) {
					return new ListCell<HTMLElement>() {
						private final HBox graphic = new HBox(10);
						private final Label label1 = new Label();
						private final Label label2 = new Label();
						{
							graphic.getChildren().addAll(label1, label2);
							label1.setPrefWidth(100);
							label2.setPrefHeight(150);
						}
						
						@Override
						protected void updateItem(HTMLElement item, boolean empty) {
							super.updateItem(item, empty);
							if (empty || item == null) {
								setGraphic(null);
								setText(null);
							} else {
								label1.setText(item.getrow());
								label2.setText(item.gethtml());
								setGraphic(graphic);
							}
						}
					};
				}
			});
			try {
				primaryStage.setTitle("HTML data checker");
				Document doc = Jsoup.connect("http://jsoup.org").get();
				Elements elements = doc.select("*");
				int totalElements = elements.size();
				System.out.println(totalElements);
				//Element link = doc.select("a").first();
				//String relHref = link.attr("href"); // == "/"
				//String absHref = link.attr("abs:href"); // "http://jsoup.org/"
				BorderPane border = new BorderPane();
				Scene scene = new Scene(border,800,800);
				TextArea textpiece = new TextArea();
				ContextMenu contextMenu = new ContextMenu();
				ObservableList<HTMLElement> items = FXCollections.observableArrayList();
				int row = 1;
				for (Element element : elements){
					String elementString = element.html();
					items.add(new HTMLElement(String.valueOf(row), elementString));
					row ++;
				}
				listView.setItems(items);
				textpiece.setContextMenu(contextMenu);
				TableView<HTMLElement> tableView = new TableView<>();
				tableView.setItems(items);
				border.setCenter(listView);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		public static void main(String[] args) throws IOException {
			launch(args);
		}
}
