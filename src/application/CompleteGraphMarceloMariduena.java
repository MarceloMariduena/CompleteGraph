package application;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import java.util.Scanner;
/* @autor: Marcelo Mariduena */

public class CompleteGraphMarceloMariduena extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			/* User variables */
			int width = 400, height = 400, n;
			Scanner input = new Scanner(System.in);
			
			/* Prompt user to n vertices */
			System.out.print("Enter the number of vertices: ");
			n = input.nextInt();
			input.close();
			
			/* Output graphical drawing */
			Scene scene = new Scene(showKn(n, width, height), width, height);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {e.printStackTrace();}
	}
	public Pane showKn(int n, int width, int height) {
		Pane root = new Pane();
		Text legend = new Text(0, 20, "Complete graph for n = " + n);
		
		/* Default variables */
		double radian = 2.0 * Math.PI/n; // 2*Pi = 6.28. 6.28/n = the radian of each section
		int hypotenuse = (int) (Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2)) / 4);
		
		int x0, y0, x1, y1, x2, y2; // (x0,y0)center, (x1,y1)endline1, (x2,y2)endline2
		/* 1st For-Loop: Draws lines from the center to the end of the radius of a given angle */
		for(int i = 0; i < n; i++) {
			int distanceX = (int) (hypotenuse * Math.cos(i * radian)); // cos(0) = ADJACENT(width) / hypotenuse
			int distanceY = (int) (hypotenuse * Math.sin(i * radian)); // sin(0) = OPPOSITE(height) / hypotenuse
			x0 = width/2; // half the scene's default width
			y0 = height/2; // half the scene's default height
			x1 = x0 + distanceX;
			y1 = y0 + distanceY;
			Line line1 = new Line(x0, y0, x1, y1);
			
			/* Binds the start of each line to the center of the pane */
			line1.startXProperty().bind(root.widthProperty().divide(2));
			line1.startYProperty().bind(root.heightProperty().divide(2));
			
			/* Binds the end of each line */
			line1.endXProperty().bind(line1.startXProperty().add(distanceX));
			line1.endYProperty().bind(line1.startYProperty().add(distanceY));

			/* 2nd For-Loop: Draws line from the end of the previous line to the end of the current line */
			for(int j = 0; j < n -1; j++) {
				int distanceX2 = (int) (hypotenuse * Math.cos(j * radian)); 
				int distanceY2 = (int) (hypotenuse * Math.sin(j * radian));
				x2 = x0 + distanceX2;
				y2 = y0 + distanceY2;
				Line line2 = new Line(x1, y1, x2, y2);
				
				/* Bind the start of line2 to the end of line1 */
				line2.startXProperty().bind(line1.endXProperty());
				line2.startYProperty().bind(line1.endYProperty());
				/* Bind the end of line2 the end to the end of line 1 plus the distance between them */
				line2.endXProperty().bind(root.widthProperty().divide(2).add(distanceX2));
				line2.endYProperty().bind(root.heightProperty().divide(2).add(distanceY2));
				root.getChildren().add(line2); 
			}
			root.getChildren().add(line1);
		}
		root.getChildren().add(legend);
		return root;
	}
	public static void main(String[] args) {
		launch(args);
	}
}