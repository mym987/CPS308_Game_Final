package voogasalad_GucciGames.gameEngine;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import voogasalad_GucciGames.gameEngine.CommunicationParameters.ChangedParameters;
import voogasalad_GucciGames.gameEngine.CommunicationParameters.BasicParameters;
import voogasalad_GucciGames.gameEngine.CommunicationParameters.GameParameters;
import voogasalad_GucciGames.gameEngine.CommunicationParameters.GameResult;
import voogasalad_GucciGames.gameEngine.CommunicationParameters.GridCoordinateParameters;
import voogasalad_GucciGames.gameEngine.gameConditions.ConditionHandler;
import voogasalad_GucciGames.gameEngine.gameConditions.ConditionParams;
import voogasalad_GucciGames.gameEngine.gameConditions.Conditions;
import voogasalad_GucciGames.gameEngine.gameConditions.ConditionsFactory;
import voogasalad_GucciGames.gameEngine.gameConditions.EndGameConditions;
import voogasalad_GucciGames.gameEngine.gameConditions.defaultConditions.CheckPlayerObjects;
import voogasalad_GucciGames.gameEngine.gamePlayer.ATurnDecider;
import voogasalad_GucciGames.gameEngine.gamePlayer.AllPlayers;
import voogasalad_GucciGames.gameEngine.gamePlayer.DefaultTurnDecider;
import voogasalad_GucciGames.gameEngine.gamePlayer.TurnCounter;
import voogasalad_GucciGames.gameEngine.gameRules.RuleFactory;
import voogasalad_GucciGames.gameEngine.gameRules.RuleParams;
import voogasalad_GucciGames.gameEngine.mapObject.MapObject;
import voogasalad_GucciGames.gameEngine.targetCoordinate.ATargetCoordinate;
import voogasalad_GucciGames.gameEngine.targetCoordinate.TargetCoordinateMultiple;
import voogasalad_GucciGames.gameplayer.controller.GameParametersInterface;

public class MainGameEngine implements GameEngineToGamePlayerInterface {

	private AllPlayers myGamePlayers;
	private TurnCounter myCurrentTurnCounter;
	private ATurnDecider myTurnDecider;
	private ConditionHandler myConditionHandler;
	private int mapDimensions;
	private int myMapWidth;
	private int myMapHeight;
	private String myName;
	
	public String getName() {
		return myName;
	}
	
	public MainGameEngine(AllPlayers gamePlayers) {
		myGamePlayers = gamePlayers;
		myCurrentTurnCounter = new TurnCounter();
		myTurnDecider = new DefaultTurnDecider(myGamePlayers, myCurrentTurnCounter);
		myConditionHandler = new ConditionHandler();

		myConditionHandler.addCondition("PlayerUnitCondition", new CheckPlayerObjects(new ConditionParams(this)));
		
		myName = "Game " + Math.round((Math.random()*10000));
	}
	@Override
	public String getGameName() {
		return myName;
	}
	@Override
	public GameParametersInterface endTurn() {
		//check game conditions
		myCurrentTurnCounter.update();
		myTurnDecider.updateActivePlayer();
		myGamePlayers.reset();
		return getGameParameters();
	}

	public int getActivePlayer() {
		return myTurnDecider.decideTurn();
	}

	public int getTurn() {
		return myCurrentTurnCounter.getCurrentTurn();
	}

	@Override
	public List<PlayerMapObjectInterface> getInitialState() {
		return myGamePlayers.getInitialState();
	}

	@Override
	public int getTurnPlayerID() {
		return myTurnDecider.decideTurn();
	}

	@Override
	public GridCoordinateParameters getPossibleCoordinates(String action, PlayerMapObjectInterface myMapObject) {
		if(((MapObject) myMapObject).getPlayerID() == myTurnDecider.getActivePlayer().getMyPlayerId()){
			return ((MapObject) myMapObject).performRequest(action);
			//return ((MapObject) myMapObject).performRequest(action, new BasicParameters(this, ((MapObject) myMapObject)));
		}
		else{
			return new GridCoordinateParameters(new TargetCoordinateMultiple());
		}

	}
	////////
	public void createTestCondition() {
		List<Integer> pl = new ArrayList<Integer>();
		pl.add(0);
		ConditionParams condParams = new ConditionParams("PlayerUnitCondition", "player", pl, null);
		ConditionsFactory factory = new ConditionsFactory();
		//BasicParameters comParams = new BasicParameters(myGamePlayers, null, null);
		BasicParameters comParams = new BasicParameters(null,this);
		try {
			Conditions condition = factory.createCondition(condParams, comParams);
			myConditionHandler.addCondition("PlayerUnitCondition", condition);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		endTurn();
		endTurn();
	}
	public void testRules() {
		System.out.println("create rules");
		RuleFactory factory = new RuleFactory();
		RuleParams params = new RuleParams("move", null, null);
		//BasicParameters comParams = new BasicParameters(myGamePlayers, null, manager);
		BasicParameters comParams = new BasicParameters(null,this);
		try {
			factory.createRule(params, comParams);
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}

	}
	public AllPlayers getPlayers() {
		// TODO Auto-generated method stub
		return myGamePlayers;
	}

	/*
	 * Deprecated this because we need to support more than square maps. -- Ted :)
	 */
	@Deprecated
	public int getMapDimensions() {
		return mapDimensions;
	}
	
	@Override
	public ChangedParameters performAction(String action, PlayerMapObjectInterface mapObject,
			ATargetCoordinate target) {
		// TODO Auto-generated method stub
		if(((MapObject) mapObject).getPlayerID() == myTurnDecider.getActivePlayer().getMyPlayerId()){
			return ((MapObject) mapObject).performAction(action, target.getListOfCoordinates().get(0));
			/*
			return ((MapObject) mapObject).performAction(action,
					new LocationParams(new BasicParameters(this, ((MapObject) mapObject)),
							target.getListOfCoordinates().get(0),
							this.getPlayers().getPlayerById(((MapObject) mapObject).getPlayerID()).getMovable()));
			 */
		}
		else{
			return new ChangedParameters();
		}

	}
	@Override
	public int getMapWidth() {
		// TODO Auto-generated method stub
		return myMapWidth;
	}
	@Override
	public int getMapHeight() {
		// TODO Auto-generated method stub
		return myMapHeight;
	}
	
	public void setMapWidth(int width){
		myMapWidth = width;
	}
	
	public void setMapHeight(int height){
		myMapHeight = height;
	}
	
	@Override
	public GameParametersInterface getGameParameters() {
		// TODO Auto-generated method stub
		GameParameters pp= new GameParameters();
		if(myGamePlayers.getNumberOfPlayers() != 2){
			pp.setGameWon(false);
		}
		else{
			pp.setGameWon(true);
		}

		pp.setCurrentTurnPlayer(getTurnPlayerID());
		pp.setGameName(myName);

		//change it before demo
		pp.setMapHeight(8);
		pp.setMapWidth(8);

		Map<String, Double> score = new HashMap<String, Double>();
		Map<String, Integer> scoreID = new HashMap<String, Integer>();

		List<Integer> myIDs = myGamePlayers.getAllIds();
		Collections.sort(myIDs);

		for(int i = 1; i < myIDs.size(); i++){
			score.put("Player" + myIDs.get(i), myGamePlayers.getActivePlayer(myIDs.get(i)).getMapObjects().size() * 500.0);
			scoreID.put("Player" + myIDs.get(i), myGamePlayers.getActivePlayer(myIDs.get(i)).getMapObjects().size() * 500);		}


		pp.setScore(score);

		GameResult game = new GameResult();


		Map<Integer, EndGameConditions> myConds = new HashMap<Integer, EndGameConditions>();
		for(int i = 1; i < myIDs.size(); i++){
			myConds.put(myIDs.get(i), myGamePlayers.getActivePlayer(myIDs.get(i)).getStatusCondition());
		}


		game.setPlayerConditions(myConds);
		game.setFinalScores(scoreID);

		for(int i = 1; i < myIDs.size(); i++){
			if(myConds.get(myIDs.get(i)).equals(EndGameConditions.WIN)){
				game.setWinner(myIDs.get(i));

			}
		}


		pp.setGameResult(game);

		return pp;
	}
	public void setName(String name) {
		myName = name;
	}
}
