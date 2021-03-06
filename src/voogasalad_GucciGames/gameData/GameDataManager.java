package voogasalad_GucciGames.gameData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import voogasalad_GucciGames.gameData.wrapper.GameInfo;
import voogasalad_GucciGames.gameData.wrapper.GamePlayerSave;

public class GameDataManager implements GameDataInterface {

	private XStreamGameEngine myXStream;
	private GameListManager myGameList;
	private GameFileHelper myFileHelper;

	List<String> myAccessedResources;

	private final ResourceBundle myConfig = ResourceBundle.getBundle("voogasalad_GucciGames.gameData.config.GameData");

	private String myBasePath;

	public GameDataManager() {
		myXStream = new XStreamGameEngine();
		myGameList = new GameListManager();
		myFileHelper = new GameFileHelper();
		myAccessedResources = new ArrayList<>();
		myBasePath = myConfig.getString("BaseResourcePath");
	}

	@Override
	public GameInfo loadGame(String name) {
		return myXStream.loadGameByName(name);
	}

	@Override
	public void saveGame(GamePlayerSave game) {
		myXStream.saveGameState(game);
	}

	@Override
	public List<String> getAvailableGames() {
		return myGameList.listGames();
	}

	@Override
	public GameInfo loadGameFromFile(String path) throws GameDataException {
		if (!path.endsWith(myConfig.getString("GameExtension")))
			throw new GameDataException("Please select a valid game data file.)");
		return myXStream.loadGameInfo(path);
	}

	@Override
	public GameInfo loadDefault() {
		return myXStream.loadGameByName(myGameList.listGames().get(0));
	}

	public String getGamePath(String gameName) {
		return myXStream.gameNameToPathName(gameName);
	}

	/**
	 * Gets resources from the root folder (i.e. GAE database)
	 * 
	 * @param extensions
	 * @return
	 */
	public List<String> getResources(List<String> extensions) {
		List<String> result = myFileHelper.getMatchingFiles(myBasePath, extensions);
		return result.stream().map(s -> s.substring(myBasePath.length())).collect(Collectors.toList());
	}

	/**
	 * Gets resources from the subdirectory folder of the main database (i.e.
	 * GAE database)
	 * 
	 * @param extensions
	 * @return
	 */
	public List<String> getResources(List<String> extensions, String path) {
		List<String> result = myFileHelper.getMatchingFiles(myBasePath + path, extensions);
		return result.stream().map(s -> s.substring(myBasePath.length())).collect(Collectors.toList());
	}

	/**
	 * Copies an image to the specified game folder.
	 * 
	 * @param URI
	 * @param gameName
	 */
	public void copyResourceToGame(String URI, String gameName) {
		if (!myAccessedResources.contains(URI) && copyResource(URI, gameName, myBasePath)) {
			myAccessedResources.add(URI);
		}
	}

	/**
	 * 
	 * @param source
	 * @param gameName
	 * @param basePath
	 *            - whether this path is relative to base resource path or if
	 *            it's a specific game
	 */
	private boolean copyResource(String URI, String gameName, String basePath) {
		try {
			myFileHelper.copyResource(basePath + URI, getGamePath(gameName) + myConfig.getString("ResourcePath") + URI);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return false;
		}
		return true;
	}

	// NOTE: I could make this actually search the folder
	// but in the interests of time it'll just return preconfigured directories
	public List<String> listDirectories(String root) {
		return myConfig.keySet().stream().filter(s -> s.startsWith(root + "Directory")).map(s -> myConfig.getString(s))
				.collect(Collectors.toList());
	}

	// Dear professor duvall
	// if you see this function
	// please don't put it on the screen
	// I'm sorry.
	public void buildGameDirectories(String gameName) {
		myFileHelper.makeDirectory(getGamePath(gameName));
		// can't create child directories until roots have been created
		for (int i = 0; i < Integer.parseInt(myConfig.getString("DirectoryDepth")); i++) {
			myConfig.keySet().stream().filter(s -> s.startsWith("GameDirectory")).map(s -> myConfig.getString(s))
					.forEach(s -> myFileHelper.makeDirectory(getGamePath(gameName) + s));
		}
	}

	public void renameGameDirectory(String oldName, String newName) {
		// System.out.println("RENAMING " + oldName + " TO " + newName);
		buildGameDirectories(newName);
		recopyResources(oldName, newName);
	}

	private void recopyResources(String oldName, String newName) {
		for (String URI : myAccessedResources) {
			copyResource(URI, newName, getGamePath(oldName) + myConfig.getString("ResourcePath"));
		}
	}

	@Override
	public List<String> getAvailableSaves(String gameName) {
		List<String> result = filterURIs(
				myFileHelper.getMatchingFiles(getGamePath(gameName) + myConfig.getString("SaveDirectory"),
						Arrays.asList(myConfig.getString("SaveExtension"))),
				getGamePath(gameName) + myConfig.getString("SaveDirectory"));
		return result;
	}

	private List<String> filterURIs(List<String> resources, String base) {
		return resources.stream().map(s -> s.substring(base.length())).collect(Collectors.toList());
	}

	@Override
	public GamePlayerSave loadSave(String saveName, String gameName) {
		return myXStream.loadGameState(saveName, gameName);
	}

}
