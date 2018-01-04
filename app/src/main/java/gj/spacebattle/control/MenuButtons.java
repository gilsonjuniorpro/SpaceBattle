package gj.spacebattle.control;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;

import gj.spacebattle.config.Assets;
import gj.spacebattle.GameScene;
import gj.spacebattle.delegate.ButtonDelegate;

import static gj.spacebattle.config.DeviceSettings.screenHeight;
import static gj.spacebattle.config.DeviceSettings.screenResolution;
import static gj.spacebattle.config.DeviceSettings.screenWidth;

/**
 * Created by gilsonsantos on 03/01/18.
 */

public class MenuButtons extends CCLayer implements ButtonDelegate {

    private Button playButton;
    //private Button highscoredButton;
    //private Button helpButton;
    //private Button soundButton;

    public MenuButtons() {

        // Enable Touch
        this.setIsTouchEnabled(true);

        // Create Buttons
        this.playButton = new Button(Assets.PLAY);
        //this.highscoredButton = new Button(Assets.HIGHSCORE);
        //this.helpButton = new Button(Assets.HELP);
        //this.soundButton = new Button(Assets.SOUND);

        // set position
        setButtonspPosition();

        // Add Buttons to Screen
        addChild(playButton);
        //addChild(highscoredButton);

        // Set Buttons Delegates
        this.playButton.setDelegate(this);
        //this.highscoredButton.setDelegate(this);
        //this.helpButton.setDelegate(this);
        //this.soundButton.setDelegate(this);
    }

    private void setButtonspPosition() {
        // Buttons Positions
        playButton.setPosition(screenResolution(CGPoint.ccp( screenWidth() /2,
                screenHeight() - 250 ))) ;

        /*
        highscoredButton.setPosition(screenResolution(CGPoint.ccp( screenWidth() /2,
                screenHeight() - 300 ))) ;

        helpButton.setPosition(
                screenResolution(
                        CGPoint.ccp( screenWidth() /2 , screenHeight() - 350 )));

        soundButton.setPosition(screenResolution(
                CGPoint.ccp( screenWidth() /2 - 100,
                        screenHeight() - 420 )));
                        */
    }

    @Override
    public void buttonClicked(Button sender) {
        if (sender.equals(this.playButton)) {
            System.out.println("Button clicked: Play");
            CCDirector.sharedDirector().replaceScene(
                    CCFadeTransition.transition(1.0f,
                            GameScene.createGame()));
        }
/*
        if (sender.equals(this.highscoredButton)) {
            System.out.println("Button clicked: Highscore");
        }

        if (sender.equals(this.helpButton)) {
            System.out.println("Button clicked: Help");
        }
        if (sender.equals(this.soundButton)) {
            System.out.println("Button clicked: Sound");
        }
        */
    }
}
