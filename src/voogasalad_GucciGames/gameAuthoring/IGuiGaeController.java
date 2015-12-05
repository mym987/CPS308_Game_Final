package voogasalad_GucciGames.gameAuthoring;

import voogasalad_GucciGames.gameAuthoring.gui.levels.LevelTabPane;
import voogasalad_GucciGames.gameAuthoring.gui.map.GridPoint;
import voogasalad_GucciGames.gameAuthoring.model.DisplayMapObject;
import voogasalad_GucciGames.gameAuthoring.model.MapObjectType;
import voogasalad_GucciGames.gameAuthoring.properties.ObjectProperty;
import voogasalad_GucciGames.gameAuthoring.properties.Property;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import voogasalad_GucciGames.gameEngine.mapObject.MapObject;

public interface IGuiGaeController {
	/**
	 * Add Map Object
	 * 
	 * @param mapObj
	 */

	public void initGrid(int width, int height);

	public void deleteComponent(DisplayMapObject mapObj);

	public DisplayMapObject addObject(GridPoint gridpoint, MapObjectType mapObjType);

	public List<DisplayMapObject> getMapObjects();

	// public int getMapObjectListPosAtPoint(ObservableList<MapObject>
	// mapObjectList, GridPoint gridPoint);

	public void clearMap();

	public void createCustomTileType(Map<String, String> m);

	public void createCustomUnitType(Map<String, String> m);

	public ObservableList<MapObjectType> getImmutableTileTypes();

	public ObservableList<MapObjectType> getImmutableUnitTypes();

	public ObservableList<MapObjectType> getImmutableStructureTypes();

	public void saveToXML(File file);

	public void setMapObjectTypeToMap(MapObjectType mapType);

	public MapObjectType getMapObjectTypeToMap();

	public Image getCurrSelectedImage();

	public void setCurrDraggedImage(Image draggedImage);

	public DisplayMapObject addObject(GridPoint gridpoint, MapObjectType mapObjType, int ownerID);

	public void changeOwner(MapObject mapObject, int playerID);

	public Stage getStage();

	public Image requestImage(String path);

	public ImageView getMapObjectImage(MapObjectType object);

	public ImageView getMapObjectImage(DisplayMapObject object);

	public List<String> getCustomGamePlayerLeftComponents();

	public void setCustomGamePlayerLeftComponents(List<String> allComponents);    

	public List<String> getCustomGamePlayerRightComponents();

	public void setCustomGamePlayerRightComponents(List<String> allComponents);    

	public List<String> getCustomGamePlayerBottomComponents();

	public void setCustomGamePlayerBottomComponents(List<String> allComponents);
	
	public LevelTabPane getLevelTabPane();
}
