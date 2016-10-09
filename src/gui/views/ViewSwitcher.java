package gui.views;

import java.util.LinkedList;
import java.util.List;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewSwitcher {

	private Stage stage;
	private int viewCounter = 0;
	private List<View> views;

	public ViewSwitcher(Stage stage) {
		this.stage = stage;
		views = new LinkedList<View>();
	}

	public void setViewOrder(View ... views) {
		for (View view : views) {
			this.views.add(view);
		}
	}

	private void chooseView(View view) {
		stage.setScene(new Scene(view.getUI()));
	}

	public void setView(int i){
		if (i > views.size() - 1)
			chooseView(views.get(views.size()-1));
		else if(i < 0)
			chooseView(views.get(0));
		else
			chooseView(views.get(i));
	}

	public void nextView() {
		if(views.isEmpty()){
			throw new IndexOutOfBoundsException("ViewSwitcher contains no views. Have you set views using setViewOrder()?");
		}
		
		if (viewCounter >= views.size() - 1)
			viewCounter = 0; // reset viewCounter
		else
			viewCounter++;
		
		chooseView(views.get(viewCounter));
	}
}
