package voogasalad_GucciGames.gameAuthoring.gui.sidebar;

import javafx.event.ActionEvent;
import voogasalad_GucciGames.gameAuthoring.gui.gaedialog.maindialogs.NewObMakerDialog;

public class UnitTab extends ATab {
	private static final String TYPE = "unit";
	
	public UnitTab(SideBar bar) {
		super(bar);
		setText("Units");
		init(myController.getImmutableUnitTypes());
	}

	@Override
	protected void addNewTypeDialog(ActionEvent e) {
		NewObMakerDialog unitMaker = new NewObMakerDialog( myController, TYPE);
		unitMaker.showAndWait();
	}

}
