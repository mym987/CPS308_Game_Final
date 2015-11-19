package voogasalad_GucciGames.gameAuthoring.gui.sidebar;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import voogasalad_GucciGames.gameAuthoring.IGuiGaeController;
import voogasalad_GucciGames.gameEngine.mapObject.MapObjectType;

public class SideBar{
	private MapObjectType myCurrSelection;
	private IGuiGaeController myController;
	private TabPane myTabPane;
	private ATab mySelectSource;
	
	public SideBar(IGuiGaeController controller){
		myController = controller;
		Tab tileTab = new TileTab(this, null);
    	Tab unitTab = new UnitTab(this, null);
    	Tab strucTab = new StructureTab(this, null);
    	myTabPane = new TabPane(tileTab,unitTab,strucTab);
	}
	
	public TabPane getPane(){
		return myTabPane;
	}
	
	protected IGuiGaeController getController(){
		return myController;
	}
	
	protected MapObjectType getCurrSelection(){
		return myCurrSelection;
	}
	
	protected void setCurrSelection(ATab source, MapObjectType type){
		if(mySelectSource!=null){
			mySelectSource.deselect();
		}
		myCurrSelection = type;
		mySelectSource = source;
	}
}