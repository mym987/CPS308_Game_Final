package voogasalad_GucciGames.gameplayer.windows.mainwindow.components.bar;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import voogasalad_GucciGames.gameplayer.config.PlayerConfig;
import voogasalad_GucciGames.gameplayer.controller.GameControllerInterface;
import voogasalad_GucciGames.gameplayer.windows.GameScene;
import voogasalad_GucciGames.gameplayer.windows.mainwindow.components.DisplayComponent;

public class GameStatsDisplay extends DisplayComponent {
    private ListView<String> listView;
    private List<String> stats;
    private ObservableList<String> observeStats;
    private ResourceBundle myBundle=PlayerConfig.load("components.Bar");

    public GameStatsDisplay(GameScene scene, GameControllerInterface controller) {
        super(scene,controller);
        stats = new ArrayList<String>();
        observeStats = FXCollections.observableList(stats);
        listView=new ListView<String>(observeStats);
        updateDisplay();
    }

    @Override
    public void updateDisplay() {
        observeStats.clear();
        observeStats.add(myBundle.getString("statswin")+getController().getEngine().getGameParameters().gameWon());
        observeStats.add(myBundle.getString("statsturn")+getController().getEngine().getGameParameters().whoseTurn());
    }

    @Override
    public Parent getParent() {
        return listView;
    }

}