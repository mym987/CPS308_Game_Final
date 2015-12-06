package voogasalad_GucciGames.helpers;

import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import voogasalad_GucciGames.gameData.wrapper.GameInfo;

public interface GameResourceManagerToGAE {

	public List<String> getImages();
	
	public List<String> getImages(String directory);
	
	public List<String> listImageDirectories();
	
	public Image getImage(String URI);
	
	
	
	/**
	 * Returns the average color of the request image.
	 * @param URI
	 * @return
	 */
	public Color getImageColor(String URI);
	
	/**
	 * If set to true, copies all resources to the Game Directory on access.
	 * MUST BE SET IN GAE.
	 * @param copy
	 */
	public void toggleCopyOnAccess(boolean copy);
	
	public void changeGameName(String newName);	
}