import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.ArrayList;

public class CoursePane extends HBox {
	// GUI components
	private ArrayList<Course> courseList; 
	private VBox checkboxContainer; 

	//instance variables 
	private String subject;
	private int numCourse;
	private String instructors;

	// Course object
	Course course;

	// Instantiating Labels
	Label courseSubject;
	Label courseNum;
	Label instructor;
	Label error;
	Label numCourseLabel;

	// create TextField and comboBox for the labels
	TextField courseNumField;
	TextField instructorField;
	ComboBox<String> comb;

	// instantiating add and drop button
	Button addButton;
	Button dropButton;

	// border styling
	String perimeter = " -fx-border-color: black;\r\n" + "    -fx-border-insets: 1;\r\n"
			+ "    -fx-border-width: 2;\r\n";

	// list arrayList used to store CheckBoxes and update accordingly when drop/add
	ArrayList<CheckBox> list = new ArrayList<CheckBox>();


	// constructor
	public CoursePane() {
		//initialize instance variables
		this.subject = "";
		this.numCourse = 0;
		this.instructors = "";
		this.courseList = new ArrayList<Course>();


		// left and right labels
		Label labelLeft = new Label("Add Course(s)");
		labelLeft.setTextFill(Color.BLUE);
		labelLeft.setFont(Font.font(null, 14));
		Label labelRight = new Label("Course(s) Enrolled");
		labelRight.setTextFill(Color.BLUE);
		labelRight.setFont(Font.font(null, 14));
		// set up the layout.
		VBox leftPane = new VBox();
		GridPane left = new GridPane();

		// **LEFT PANE**//
		// spacing and styling of left pane
		left.setAlignment(Pos.CENTER);
		left.setVgap(1);
		left.setHgap(1);
		leftPane.setSpacing(100);
		leftPane.setStyle(perimeter);

		// label objects
		courseSubject = new Label();
		courseNum = new Label();
		instructor = new Label();

		// comboBox object
		comb = new ComboBox<String>();
		comb.getItems().addAll("ACC", "AME", "BME", "CHM", "CSE", "DAT", "EEE");

		// adding labels and TextField to GridPane
		// subject label and textField

		courseSubject = new Label("Subject");
		comb.setValue("CSE");
		left.add(this.courseSubject, 0, 0);
		left.add(this.comb, 10, 0);
		// courseNum label and textField

		courseNum = new Label("Course Num");
		courseNumField = new TextField();
		error = new Label("No course added");
		left.add(this.courseNum, 0, 15);
		left.add(this.courseNumField, 10, 15);
		// instructor label and textField

		instructor = new Label("Instructor");
		instructorField = new TextField();
		left.add(this.instructor, 0, 30);
		left.add(this.instructorField, 10, 30);

		// setting the preferred width for the textFields above
		courseNumField.setMaxWidth(160);
		instructorField.setMaxWidth(160);

		// adding the top label, gridPane, and the bottom label to the left pane
		leftPane.getChildren().addAll(labelLeft, left, error);
		// ----
		//create and set up the layout of the centerPane which holds the two buttons
		VBox centerPane = new VBox();
		centerPane.setAlignment(Pos.CENTER);
		centerPane.setSpacing(50);

		// making the add and drop button object
		addButton = new Button("Add =>");
		dropButton = new Button("Drop <=");

		// adding buttons to the center pane
		centerPane.getChildren().addAll(addButton, dropButton);
		
		BorderPane rightPane = new BorderPane();
		// setting spacing and styling of right pane
		rightPane.setPadding(new Insets(10, 0, 0, 0));
		rightPane.setStyle(perimeter);
		rightPane.setPrefSize(225, 200);
		labelRight.setAlignment(Pos.TOP_LEFT);

		// "Total course enrolled" label in bottom left of right pane
		numCourseLabel = new Label("Total course enrolled: " + courseList.size());
		// making checkBoxCotainer object
		checkboxContainer = new VBox();
		checkboxContainer.setAlignment(Pos.TOP_LEFT); // alignment of the checkboxContainer

		// adding the top label, checkBoxContainer, and bottom label to right pane
		rightPane.setTop(labelRight);// getChildren().addAll(labelRight, checkboxContainer, numCourseLabel);
		rightPane.setCenter(checkboxContainer);
		rightPane.setBottom(numCourseLabel);

		// ----

		// CoursePane is a HBox. Add leftPane, centerPane and rightPane inside
		this.setPadding(new Insets(10, 10, 10, 10));
		this.getChildren().addAll(leftPane, centerPane, rightPane);
		// ----

		// connecting in the ButtonHandler class
		addButton.setOnAction(new ButtonHandler());
		dropButton.setOnAction(new ButtonHandler());
		// ----
	} // end of constructor

	//  Whenever a new Course is added or one or several courses are
	// dropped/removed, this method will

	// Whenever drop button is pressed this method is called
	public void updateCheckBoxContainer() {
		// clears all the checkBoxes and toString
		checkboxContainer.getChildren().clear();

		for (int j = 0; j < list.size(); j++) {
			if (!(list.get(j).isSelected())) { // when the checkBox is not selected
				checkboxContainer.getChildren().addAll(list.get(j));
			} else {
				// else statement is used to update the arrayLists
				courseList.remove(j);
				list.remove(j);
				j--;
			}
		}
		// updates the number of courses enrolled after the drop button is pressed
		numCourseLabel.setText("Total course enrolled: " + courseList.size());
	}

	// Create a ButtonHandler class
	// this class handles all the buttons of the program mainly the add and drop
	// button
	private class ButtonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) { // instance variables in buttonHandler class
			String courseNum = courseNumField.getText();
			String instructor = instructorField.getText();
			String comBox = comb.getValue();
			Boolean sameCourse = false;
			Button add = (Button) e.getSource();
			Button drop = (Button) e.getSource();
			Course course;

			try {
				if (!(courseNum.equals("")) && !(instructor.equals("")) && add == addButton) {

					course = new Course(comBox, Integer.parseInt(courseNum), instructor);

					// need to check whether the course already exist inside the courseList or not
					int courseNumber = Integer.parseInt(courseNum);
					sameCourse = false; // no duplicate course object variables input

					for (int i = 0; i < courseList.size(); i++) {
						Course cou = courseList.get(i); // new course object

						if (cou.getCourseNum() == courseNumber && cou.getInstructor().equals(instructor)) {
							sameCourse = true; // there is a duplicate course object variables input
						}
					}
					if (!sameCourse) { // when sameCourse is false
						courseList.add(course);
						error.setTextFill(Color.BLACK);
						error.setText("Course added successfully");

						numCourseLabel.setText("Total course enrolled: " + courseList.size());
						CheckBox checkBox = new CheckBox(course.toString());
						list.add(checkBox);

						checkboxContainer.getChildren().addAll(checkBox);
					} else if (sameCourse) // a duplicated one
					{
						error.setTextFill(Color.RED);
						error.setText("Duplicate course - Not added");

					}

					// Clear all the text fields and prepare for the next entry;
					courseNumField.setText("");
					instructorField.setText("");
				} // ----
				else if ((courseNum.equals("") || instructor.equals("")) && add == addButton) {
					error.setTextFill(Color.RED);
					error.setText("At least one field is empty. Fill all fields");
				} else if (drop == dropButton) // when drop button is pressed
				{
					updateCheckBoxContainer(); // calls updateCheckBoxContainer method

				} else // for all other invalid actions, thrown an exception and it will be caught
				{
					if (add == addButton && (courseNum.equals("")) || !(instructor.equals(""))) {
						error.setTextFill(Color.RED);
						error.setText("At least one field is empty. Fill all fields");
					}
				}
			} // end of try
			catch (NumberFormatException ex) { // when the courseNum is not an integer
				error.setTextFill(Color.RED);
				error.setText("Error! Course number must be an integer");
			} catch (Exception exception) { // when one of the textField is empty
				error.setTextFill(Color.RED);
				error.setText("At least one field is empty. Fill all fields");
			}
		} // end of handle() method
	} // end of ButtonHandler class

}