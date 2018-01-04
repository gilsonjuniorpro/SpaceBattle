package gj.spacebattle.entity;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCSpawn;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import java.util.Random;

import gj.spacebattle.R;
import gj.spacebattle.config.Runner;
import gj.spacebattle.delegate.MeteorsEngineDelegate;

import static gj.spacebattle.config.DeviceSettings.screenHeight;
import static gj.spacebattle.config.DeviceSettings.screenResolution;
import static gj.spacebattle.config.DeviceSettings.screenWidth;

/**
 * Created by gilsonsantos on 03/01/18.
 */

public class Meteor extends CCSprite {

    private MeteorsEngineDelegate delegate;
    private float x, y;

    public Meteor(String image) {
        super(image);
        x = new Random().nextInt(Math.round(screenWidth()));
        y = screenHeight();
    }

    public void start() {
        this.schedule("update");
    }


    public void update(float dt) {
        // pause
        if (Runner.check().isGamePlaying() && !Runner.check().isGamePaused()) {
            y -= 1;
            this.setPosition(screenResolution(CGPoint.ccp(x, y)));
        }
    }


    public void setDelegate(MeteorsEngineDelegate delegate) {
        this.delegate = delegate;
    }

    public void shooted() {
        // PLay explosion
        SoundEngine.sharedEngine().playEffect(
                CCDirector.sharedDirector().getActivity(), R.raw.bang);

        // Remove from Game Array
        this.delegate.removeMeteor(this);

        // Stop Shoot
        this.unschedule("update");

        // Pop Actions
        float dt = 0.2f;
        CCScaleBy a1 = CCScaleBy.action(dt, 0.5f);
        CCFadeOut a2 = CCFadeOut.action(dt);
        CCSpawn s1 = CCSpawn.actions(a1, a2);

        // Call RemoveMe
        CCCallFunc c1 = CCCallFunc.action(this, "removeMe");

        // Run actions!
        this.runAction(CCSequence.actions(s1, c1));
    }

    public void removeMe() {
        this.removeFromParentAndCleanup(true);
    }

    public CGRect getBoarders(CCSprite object){
        CGRect rect = object.getBoundingBox();
        CGPoint GLpoint = rect.origin;
        CGRect GLrect = CGRect.make(GLpoint.x, GLpoint.y, rect.size.width,
                rect.size.height);
        return GLrect;
    }
}
