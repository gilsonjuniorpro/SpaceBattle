package gj.spacebattle.entity;

import static gj.spacebattle.config.DeviceSettings.screenWidth;

import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCSpawn;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import gj.spacebattle.R;
import gj.spacebattle.config.Assets;
import gj.spacebattle.config.Runner;
import gj.spacebattle.delegate.AccelerometerDelegate;
import gj.spacebattle.delegate.ShootEngineDelegate;
import gj.spacebattle.sensor.Accelerometer;


public class Player extends CCSprite implements AccelerometerDelegate {

	private static final double NOISE = 1;

	private ShootEngineDelegate delegate;

	float positionX = screenWidth() / 2;
	float positionY = 110;

	private Accelerometer accelerometer;

	private float currentAccelX;

	private float currentAccelY;
	
	public Player() {
		super(Assets.NAVE);
		setPosition(positionX, positionY);
		this.schedule("update");
	}

	public void shoot() {
		if (Runner.check().isGamePlaying() && !Runner.check().isGamePaused()) {
			delegate.createShoot(new Shoot(positionX, positionY));
		}
	}

	public CGRect getBoarders(CCSprite object){
		CGRect rect = object.getBoundingBox();
		CGPoint GLpoint = rect.origin;
		CGRect GLrect = CGRect.make(GLpoint.x, GLpoint.y, rect.size.width,
				rect.size.height);
		return GLrect;
	}


	public void setDelegate(ShootEngineDelegate delegate) {
		this.delegate = delegate;
	}

	public void moveLeft() {
		if (Runner.check().isGamePlaying() && !Runner.check().isGamePaused()) {

			if (positionX > 30) {
				positionX -= 10;
			}
			setPosition(positionX, positionY);
		}
	}

	public void moveRight() {
		if (Runner.check().isGamePlaying() && !Runner.check().isGamePaused()) {

			if (positionX < screenWidth() - 30) {
				positionX += 10;
			}
			setPosition(positionX, positionY);
		}
	}

	public void explode() {

		SoundEngine.sharedEngine().playEffect(
				CCDirector.sharedDirector().getActivity(), R.raw.over);
		SoundEngine.sharedEngine().pauseSound();

		// Stop Shoot
		this.unschedule("update");

		// Pop Actions
		float dt = 0.2f;
		CCScaleBy a1 = CCScaleBy.action(dt, 2f);
		CCFadeOut a2 = CCFadeOut.action(dt);
		CCSpawn s1 = CCSpawn.actions(a1, a2);

		// Run actions!
		this.runAction(CCSequence.actions(s1));

	}

	public void catchAccelerometer() {
		Accelerometer.sharedAccelerometer().catchAccelerometer();
		this.accelerometer = Accelerometer.sharedAccelerometer();
		this.accelerometer.setDelegate(this);
	}

	@Override
	public void accelerometerDidAccelerate(float x, float y) {
		if (Runner.check().isGamePlaying() && !Runner.check().isGamePaused() ) {

			System.out.println("X: " + x);
			System.out.println("Y: " + y);
			
			// Read acceleration
			this.currentAccelX = x;
			this.currentAccelY = y;
						
		}
		
	}

	public void update(float dt) {
		if (Runner.check().isGamePlaying() && !Runner.check().isGamePaused()) {

			//fazer primeiro com tudo zero depois colocar essa constant
			if(this.currentAccelX< -NOISE){
				this.positionX++;
			}
			
			if(this.currentAccelX> NOISE){
				this.positionX--;
			}
			
			if(this.currentAccelY< -NOISE){
				this.positionY++;
			}
			
			if(this.currentAccelY> NOISE){
				this.positionY--;
			}
			
			// Update Player Position
			this.setPosition(CGPoint.ccp(this.positionX, this.positionY));

		}
	}
	
}