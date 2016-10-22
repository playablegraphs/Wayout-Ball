package com.awayout.game.b2d;

import static com.awayout.game.GameVars.PPM;

import com.awayout.game.FrameWork;
import com.awayout.game.GameVars;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.utils.Array;



public class Ball {

	public static Body bodyBall;
	private static BodyDef bDefBall;
	private FixtureDef fDefBall;
	private CircleShape cShape;
	private Array<Fixture> bodies = new Array<Fixture>();
	private Sprite ballsprite;
	private TextureRegion[] deadframes;
	private TextureRegion currentframe;
	private Animation animation;
	private Texture explodesheet;
	public static float statetime;
	
	public void update(float dt){
		/* if BannedWall Collides Reduces HP */
		if(GameVars.touched > 0 && GameVars.state == GameVars.condition.PLAYING){ GameVars.hpAmount -= dt * (1+GameVars.currentLevel);
		;}
	}
	public void draw(){

		/* Drawing Player */ 
		FrameWork.sb.setProjectionMatrix(FrameWork.gameCam.combined);
		FrameWork.sb.begin();
		B2dWorld.world.getFixtures(bodies);
		for(Fixture body : bodies){	
			if(body.getUserData() != null && body.getUserData().equals(B2dvars.BIT_BALL) && !GameVars.dead){
			//	FrameWork.sb.draw(balltex,body.getBody().getPosition().x*PPM-10, body.getBody().getPosition().y*PPM-10,20,20);
				ballsprite.setRotation((float) Math.toDegrees(body.getBody().getAngle()));
				FrameWork.sb.draw(ballsprite, body.getBody().getPosition().x*PPM-30, body.getBody().getPosition().y*PPM-30
						,ballsprite.getOriginX(),
	                       ballsprite.getOriginY(),
	                       60,60
	                       ,ballsprite.getScaleX(),ballsprite.
	                                       getScaleY(),ballsprite.getRotation());
				
			}
			else if (body.getUserData() != null && body.getUserData().equals(B2dvars.BIT_BALL) && GameVars.dead && statetime < 1f){
				  statetime += Gdx.graphics.getDeltaTime();   
			      currentframe = animation.getKeyFrame(statetime, true);
			      FrameWork.sb.draw(currentframe,body.getBody().getPosition().x*PPM-30,body.getBody().getPosition().y*PPM-30,60,60);
			}
		}
		FrameWork.sb.end();
	}
	public void defineBall(){
		ballsprite = new Sprite(new Texture(Gdx.files.internal("gfx/sprites/body.png")));
		explodesheet = new Texture(Gdx.files.internal("gfx/explosion.png"));
		TextureRegion[][] tr = TextureRegion.split(explodesheet, 60, 60);
		deadframes = new TextureRegion[12];
		 int index = 0;
	        for (int i = 0; i < 3; i++) {
	            for (int j = 0; j < 4; j++) {
	                deadframes[index++] = tr[i][j];
	            }
	        }
	        animation = new Animation(1f/11f, deadframes);
	        statetime = 0f;     
		
		float x = 0;
		float y = 0;
		for(MapObject object : B2dWorld.map.getLayers().get("startpoint").getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			x = (rect.getX() + rect.getWidth()  / 2)/ PPM;
			y = (rect.getY() + rect.getHeight() / 2)/ PPM;
		}

		// body definition
		bDefBall = new BodyDef();
		bDefBall.type = BodyType.DynamicBody;
		bDefBall.position.set(x, y);

		// creating body
		bodyBall = B2dWorld.world.createBody(bDefBall);

		// fixture definition
		fDefBall = new FixtureDef();
		fDefBall.filter.categoryBits = B2dvars.BIT_BALL;
		fDefBall.filter.maskBits = B2dvars.BIT_BWALLS | B2dvars.BIT_WALLS | B2dvars.BIT_FINISHPOINT | B2dvars.BIT_ONEHIT
				| B2dvars.BIT_KEY | B2dvars.BIT_GEM | B2dvars.BIT_SWITCH | B2dvars.BIT_TELEPORT | B2dvars.BIT_ROPE | B2dvars.BIT_AXE;
		fDefBall.friction = 0f;
		fDefBall.density = 0.25f;
		fDefBall.restitution = 0.25f;


		// setting the shape
		cShape = new CircleShape();  fDefBall.shape = cShape; cShape.setRadius(28f / PPM);
		//cShape.setPosition(new Vector2(pShapex,pShapey)); // bDefBall.position.set or this one should use once

		// creating fixture
		bodyBall.createFixture(fDefBall).setUserData(B2dvars.BIT_BALL);
		;
		MassData md = bodyBall.getMassData();
		md.mass = 1f;
		bodyBall.setMassData(md);
		bodyBall.setLinearVelocity(0, 0);
	}
	public static Body getBodyBall() {
		return bodyBall;
	}
	public static BodyDef getbDefBall() {
		return bDefBall;
	}
	public FixtureDef getfDefBall() {
		return fDefBall;
	}
	public Vector2 getPosition(){ 
		return bodyBall.getPosition();
	}
	public void dispose() {
		// TODO Auto-generated method stub
		ballsprite.getTexture().dispose();
		cShape.dispose();
		
	}
}
