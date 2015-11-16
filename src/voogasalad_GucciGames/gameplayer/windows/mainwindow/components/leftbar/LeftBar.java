package voogasalad_GucciGames.gameplayer.windows.mainwindow.components.leftbar;

import java.util.ResourceBundle;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import voogasalad_GucciGames.gameplayer.controller.GameEngineToGamePlayerInterface;
import voogasalad_GucciGames.gameplayer.windows.GameScene;
import voogasalad_GucciGames.gameplayer.windows.WindowComponent;
import voogasalad_GucciGames.gameplayer.windows.mainwindow.map.MapInterface;
import voogasalad_GucciGames.gameplayer.windows.mainwindow.components.DisplayComponent;

public class LeftBar extends WindowComponent implements DisplayComponent{
    private VBox container;
    private double spacing = 5;
    private MapInterface myMap;
    private DisplayMapObjectDetails objectDetails;
    ResourceBundle myBundle;
    public LeftBar (GameScene scene, GameEngineToGamePlayerInterface game, MapInterface map, ResourceBundle bundle) {
        super(scene, game);
        myMap=map;
        container = new VBox(spacing);
        myBundle=bundle;
        initializeData();
    }
    private void initializeData() {
        objectDetails = new DisplayMapObjectDetails(myMap);
        container.getChildren().add(objectDetails.getNodeToDraw());
        container.getStyleClass().add(myBundle.getString("LeftVBox"));
    }

    @Override
    public Parent getParent () {
        return container;
    }
    public Node getNodeToDraw() {
        return container;
    }
    public ListChangeListener requestListener() {
        return objectDetails;
    }

}
