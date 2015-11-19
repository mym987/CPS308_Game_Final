package voogasalad_GucciGames.gameEngine.CommunicationParams;

import java.util.List;

import voogasalad_GucciGames.gameEngine.gamePlayer.AllPlayers;
import voogasalad_GucciGames.gameEngine.gameRules.ActionToRuleManager;
import voogasalad_GucciGames.gameEngine.mapObject.MapObject;

public class BasicParameters extends CommunicationParameters{

	// Classes which extend this will be used to share information between the front and back end
	private AllPlayers myPlayers;
	private MapObject myCalledMe;
	private ActionToRuleManager myActionToRuleMap;

	public BasicParameters(AllPlayers players,
		MapObject calledMe, ActionToRuleManager actionToRuleMap){
		this.myPlayers = players;
		this.myCalledMe = calledMe;
		this.myActionToRuleMap = actionToRuleMap;

	}

	public BasicParameters(BasicParameters params){
		this(params.getPlayers(), params.getCalledMe(), params.getActionToRuleMap());
	}

	public AllPlayers getPlayers() {
		return myPlayers;
	}

	public MapObject getCalledMe() {
		return myCalledMe;
	}

	public ActionToRuleManager getActionToRuleMap() {
		return myActionToRuleMap;
	}
}