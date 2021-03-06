package gj.spacebattle.screen;

import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor4B;

import gj.spacebattle.config.Assets;
import gj.spacebattle.control.Button;
import gj.spacebattle.delegate.ButtonDelegate;
import gj.spacebattle.delegate.PauseDelegate;

import static gj.spacebattle.config.DeviceSettings.screenHeight;
import static gj.spacebattle.config.DeviceSettings.screenResolution;
import static gj.spacebattle.config.DeviceSettings.screenWidth;


public class PauseScreen extends CCLayer implements ButtonDelegate {

	private Button resumeButton;
	private Button quitButton;

	private PauseDelegate delegate;
	
	private CCColorLayer background;

	public PauseScreen() {
		// Enable Touch
		this.setIsTouchEnabled(true);

		// Adds background
		this.background = CCColorLayer.node(ccColor4B.ccc4(0, 0, 0, 175),
				screenWidth(),
				screenHeight());
		this.addChild(this.background);

		// logo
		CCSprite title = CCSprite.sprite(Assets.LOGO);
		title.setPosition(screenResolution(CGPoint.ccp( screenWidth() /2 , screenHeight() - 130 ))) ;
		this.addChild(title);

		// Add Buttons
		this.resumeButton = new Button(Assets.PLAY);
		this.quitButton = new Button(Assets.EXIT);
		this.resumeButton.setDelegate(this);
		this.quitButton.setDelegate(this);
		this.addChild(this.resumeButton);
		this.addChild(this.quitButton);

		// Position Buttons
		this.resumeButton.setPosition(screenResolution(CGPoint.ccp( screenWidth() /2 , screenHeight() - 250 ))) ;
		this.quitButton.setPosition(screenResolution(CGPoint.ccp( screenWidth() /2 , screenHeight() - 300 ))) ;

	}

	public void setDelegate(PauseDelegate delegate) {
		this.delegate = delegate;
	}
	
	
	@Override
	public void buttonClicked(Button sender) {
		
		// Check Resume Button touched
		if (sender == this.resumeButton) {
			this.delegate.resumeGame();
			this.removeFromParentAndCleanup(true);
		}

		// Check Quit Button touched
		if (sender == this.quitButton) {
			this.delegate.quitGame();
		}
	}
}
