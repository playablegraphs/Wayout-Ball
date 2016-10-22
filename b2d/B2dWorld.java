package com.awayout.game.b2d;

import static com.awayout.game.GameVars.PPM;

import com.awayout.game.FrameWork;
import com.awayout.game.GameVars;
import com.awayout.game.managers.ContactManager;
import com.awayout.game.managers.ResourceManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

import box2dLight.ChainLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;

public class B2dWorld {

	/* Map Camera*/
	public static OrthographicCamera textureCam;

	/* Ground Body*/
	private Body groundbody;
	private BodyDef groundbdef;
	private FixtureDef groundfdef;

	/* Map & World Stuff*/
	public static TiledMap map;
	public static World world;
	private Vector2 gravity;
	private Box2DDebugRenderer renderer = new Box2DDebugRenderer();
	private OrthogonalTiledMapRenderer tmRenderer;

	/* World Simulation */
	private final int velocityIterations = 6;
	private final int positionIterations = 2;
	private final float timeStep = 1f / 60f;

	/* Rope Variables */
	private Vector2[] ropeworldvertices;
	private float[] ropevertices;

	/* Texture Variables*/
	private Texture gem,key,hrope,vrope,butdown,butup,lock,circle;
	private Sprite axe;
	private Texture left,right,up,down;

	/* Fixtures Array*/
	public static Array<Fixture> fixtures = new Array<Fixture>();

	/* Box2d Lights */
	private RayHandler rayHandler;
	private ChainLight light;
	public static PointLight point;

	/* WorldWalls variables */
	private ChainShape cShape;
	private ShapeRenderer shaperender;
	private Vector2[] worldvertices; 

	
	private float rotate = 0;
	public B2dWorld(String path) {

		/* Map Camera*/
		textureCam = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		FrameWork.thePort = new FitViewport(GameVars.WIDTH, GameVars.HEIGHT+GameVars.padding,textureCam);
		textureCam.setToOrtho(false,FrameWork.thePort.getWorldWidth()/100,FrameWork.thePort.getWorldHeight()/100);

		/* initiliaze world */
		gravity = new Vector2(0f, 0f); 
		world = new World(gravity, false);
		world.setContactListener(new ContactManager());

		/* Loads Map */
		map = new TmxMapLoader().load("gfx/levels/b"+GameVars.currentLevel+".tmx");
		tmRenderer = new OrthogonalTiledMapRenderer(map,1f/100f);


		/* Texture Initiliaze */
		key = ResourceManager.asset.get(("gfx/sprites/key.png"),Texture.class);
		vrope = ResourceManager.asset.get(("gfx/sprites/vrope.png"),Texture.class);
		hrope = ResourceManager.asset.get(("gfx/sprites/hrope.png"),Texture.class);
		butdown = ResourceManager.asset.get(("gfx/sprites/butdown.png"),Texture.class);
		butup = ResourceManager.asset.get(("gfx/sprites/butup.png"),Texture.class);
		lock = ResourceManager.asset.get(("gfx/sprites/locked.png"),Texture.class);
		circle = ResourceManager.asset.get(("gfx/sprites/circle.png"),Texture.class);
		left = ResourceManager.asset.get(("gfx/sprites/leftarrow.png"),Texture.class);
		right = ResourceManager.asset.get(("gfx/sprites/rightarrow.png"),Texture.class);
		up = ResourceManager.asset.get(("gfx/sprites/uparrow.png"),Texture.class);
		down = ResourceManager.asset.get(("gfx/sprites/downarrow.png"),Texture.class);
		axe = new Sprite(new Texture("gfx/sprites/saw.png"));
		/* Defines Rectangle Bodys*/
		defineRect();
		
		/* ShapeRenderer For WorldWall */
		shaperender = new ShapeRenderer();
		
		/* B2dLights Variables*/
		if(GameVars.tutorials && GameVars.currentLevel == 0){
		RayHandler.setGammaCorrection(false);
		rayHandler = new RayHandler(B2dWorld.world,GameVars.WIDTH,GameVars.HEIGHT);

		rayHandler.setAmbientLight(0.5f);
		light = new ChainLight(
				rayHandler, 8, Color.RED, 1.5f, -1,
				new float[]{
						0,
						(GameVars.HEIGHT+GameVars.padding)/2/GameVars.PPM+0.85f,
						GameVars.WIDTH/2/GameVars.PPM,
						(GameVars.HEIGHT+GameVars.padding)/2/GameVars.PPM+0.85f,
						GameVars.WIDTH/GameVars.PPM+0.9f,
						(GameVars.HEIGHT+GameVars.padding)/2/GameVars.PPM+0.85f
				});



		point = new PointLight(rayHandler, 8, null, 1f, 0, 0);
		point.setSoft(true);
		point.setSoftnessLength(1f);
		point.setActive(false);
		point.setColor(Color.RED);
		}
	}
	public void update(float dt){
		/* Simulates World and Destroys Bodys */
		world.step(timeStep, velocityIterations, positionIterations);
		if(GameVars.b2ddeletetrigger){
			world.getFixtures(fixtures);
			for(Fixture body : fixtures){
				if(body.getBody().getUserData() == "DEAD"){
					world.destroyBody(body.getBody());
					body.getBody().setUserData(null);
					GameVars.b2ddeletetrigger = false;
				}
			}
		}


		/* Updating TextureCam */
		textureCam.update();
		
	}
	public void draw(){

		/* Draw TileMap */
		tmRenderer.setView(textureCam);
		tmRenderer.render();
		//renderer.render(world, textureCam.combined);
		/* Settings SpriteBatch */
		FrameWork.sb.setProjectionMatrix(FrameWork.gameCam.combined);
		FrameWork.sb.begin();

		/* Drawing B2d Object */
		world.getFixtures(fixtures);
		for(Fixture body : fixtures){
			/*if(body.getFilterData().categoryBits == B2dvars.BIT_GEM ){
				FrameWork.sb.draw(gem,body.getBody().getPosition().x*PPM-10, body.getBody().getPosition().y*PPM-10,20,20);
				if(GameVars.tuts == GameVars.tutorial.GEMS && GameVars.tutorials && GameVars.currentLevel == 0){
					FrameWork.sb.draw(circle,body.getBody().getPosition().x*PPM-35, body.getBody().getPosition().y*PPM-35,70,70);
					FrameWork.sb.draw(down,body.getBody().getPosition().x*PPM-10, body.getBody().getPosition().y*PPM-10+35,20,20);
					point.setPosition(body.getBody().getPosition());
				}
			}*/
			if(body.getFilterData().categoryBits == B2dvars.BIT_AXE ){
				//axe.setRotation(60f);
				/*FrameWork.sb.draw(
						axe,body.getBody().getPosition().x*PPM-axe.getWidth()/2,
						body.getBody().getPosition().y*PPM-axe.getHeight()/2
						
						);*/
				rotate += Gdx.graphics.getDeltaTime()*360;
				if(rotate > 360) rotate = 0;
				axe.setRotation(rotate);
				FrameWork.sb.draw(axe, body.getBody().getPosition().x*PPM-axe.getWidth()/2,
						body.getBody().getPosition().y*PPM-axe.getHeight()/2,
						axe.getOriginX(), axe.getOriginY(), axe.getWidth(), axe.getHeight(),
						axe.getScaleX(), axe.getScaleY(),axe.getRotation());
				
				/*if(GameVars.tuts == GameVars.tutorial.GEMS && GameVars.tutorials && GameVars.currentLevel == 0){
					FrameWork.sb.draw(circle,body.getBody().getPosition().x*PPM-35, body.getBody().getPosition().y*PPM-35,70,70);
					FrameWork.sb.draw(down,body.getBody().getPosition().x*PPM-10, body.getBody().getPosition().y*PPM-10+35,20,20);
					point.setPosition(body.getBody().getPosition());
				}*/
			}
			if(body.getFilterData().categoryBits == B2dvars.BIT_KEY ){
				FrameWork.sb.draw(key,body.getBody().getPosition().x*PPM-30, body.getBody().getPosition().y*PPM-30,60,60);
				if(GameVars.tuts == GameVars.tutorial.KEY && GameVars.tutorials && GameVars.currentLevel == 0){
					FrameWork.sb.draw(circle,body.getBody().getPosition().x*PPM-35, body.getBody().getPosition().y*PPM-35,70,70);
					FrameWork.sb.draw(right,body.getBody().getPosition().x*PPM-90, body.getBody().getPosition().y*PPM-30,60,60);
					point.setPosition(body.getBody().getPosition());
				}
			}
			if(body.getFilterData().categoryBits == B2dvars.BIT_SWITCH){
				if(GameVars.tuts == GameVars.tutorial.SWITCH && GameVars.tutorials && GameVars.currentLevel == 0){
					FrameWork.sb.draw(right,body.getBody().getPosition().x*PPM-90, body.getBody().getPosition().y*PPM-30,60,60);
					FrameWork.sb.draw(circle,body.getBody().getPosition().x*PPM-35, body.getBody().getPosition().y*PPM-35,70,70);
					point.setPosition(body.getBody().getPosition());
				}
				if(GameVars.button == GameVars.buttonswitch.UP){
					FrameWork.sb.draw(butup,body.getBody().getPosition().x*PPM-60/2, body.getBody().getPosition().y*PPM-60/2,60,60);
				}
				else if(GameVars.button == GameVars.buttonswitch.DOWN){
					FrameWork.sb.draw(butdown,body.getBody().getPosition().x*PPM-60/2, body.getBody().getPosition().y*PPM-60/2,60,60);
				}
			}
			if(body.getFilterData().categoryBits == B2dvars.BIT_BWALLS){
				if(GameVars.tuts == GameVars.tutorial.BANNEDWALLS && GameVars.tutorials && GameVars.currentLevel == 0){
					FrameWork.sb.draw(left,body.getBody().getPosition().x*PPM+30, body.getBody().getPosition().y*PPM-30,60,60);
					point.setPosition(body.getBody().getPosition());
				}
			}
			if(body.getFilterData().categoryBits == B2dvars.BIT_ONEHIT){
				if(GameVars.tuts == GameVars.tutorial.ONEHIT && GameVars.tutorials && GameVars.currentLevel == 0){
					FrameWork.sb.draw(right,body.getBody().getPosition().x*PPM-90, body.getBody().getPosition().y*PPM-30,60,60);
					FrameWork.sb.draw(circle,body.getBody().getPosition().x*PPM-35, body.getBody().getPosition().y*PPM-35,70,70);
					point.setPosition(body.getBody().getPosition());
				}
			}
			if(body.getFilterData().categoryBits == B2dvars.BIT_ROPE){
				if(body.getBody() != null){
					if(GameVars.button == GameVars.buttonswitch.UP){
						if(ropevertices[0]-ropevertices[2] < ropevertices[1]-ropevertices[3]){
							FrameWork.sb.draw(hrope,ropevertices[0]-10/2,ropevertices[1]-(ropevertices[1]-ropevertices[3])
									,10,ropevertices[1]-ropevertices[3]
									);
						}
						else {
							FrameWork.sb.draw(vrope,ropevertices[0]-(ropevertices[0]-ropevertices[2]),ropevertices[1]-10/2
									,ropevertices[0]-ropevertices[2]
											,10
									);
						}
					}
					else if(GameVars.button == GameVars.buttonswitch.DOWN){
						body.getBody().setUserData("DEAD");
					}
				}
			}
			if(body.getUserData() == "tpcoor" && GameVars.point == GameVars.teleportpoint.point2){
				GameVars.point = GameVars.teleportpoint.none;
				Ball.bodyBall.setTransform(body.getBody().getPosition().x, body.getBody().getPosition().y - (30 / 100), 0);
			}
			if(body.getFilterData().categoryBits == B2dvars.BIT_FINISHPOINT && !GameVars.unlocked){
				FrameWork.sb.draw(lock,body.getBody().getPosition().x*PPM-50/2, body.getBody().getPosition().y*PPM-50/2,50,50);
			}
		}
		FrameWork.sb.end();	

		/* Drawing WorldWall */
		FrameWork.sb.setProjectionMatrix(textureCam.combined);
		FrameWork.sb.begin();
		for(MapObject object : map.getLayers().get("worldwall").getObjects().getByType(PolylineMapObject.class)){
			float[] vertices = ((PolylineMapObject) object).getPolyline().getTransformedVertices();
			shaperender.setProjectionMatrix(textureCam.combined);
			shaperender.begin(ShapeType.Line);
			worldvertices = new Vector2[vertices.length/2];
			for (int i = 0; i < worldvertices.length; i++) {
				worldvertices[i] = new Vector2(vertices[i*2]/PPM ,vertices[i*2+1]/PPM);
			}

			shaperender.line(worldvertices[0], worldvertices[1]);
			shaperender.setColor(Color.BLACK);
			shaperender.end();
		}
		FrameWork.sb.end();	
		/* b2dlight renderer */
		if(GameVars.tutorials && GameVars.currentLevel == 0){
			rayHandler.setCombinedMatrix(textureCam);
			rayHandler.updateAndRender();
		}

	}
	public void defineRect(){

		// ************ defines walls rectangle
		for(MapObject object : map.getLayers().get("walls").getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) object).getRectangle();

			// body definition
			groundbdef = new BodyDef();
			groundbdef.type = BodyType.StaticBody;
			groundbdef.position.set(((rect.getX() + rect.getWidth() / 2)) / PPM ,((rect.getY() + rect.getHeight() / 2))/PPM);

			// creating body 
			groundbody = world.createBody(groundbdef);

			// fixture definition
			groundfdef = new FixtureDef();	
			groundfdef.filter.categoryBits = B2dvars.BIT_WALLS;
			groundfdef.filter.maskBits = B2dvars.BIT_BALL;

			// setting the shape
			PolygonShape pShape = new PolygonShape();
			groundfdef.shape = pShape;	pShape.setAsBox(rect.getWidth() / 2 / PPM, rect.getHeight() / 2 / PPM);

			// creating fixture and setting userdata
			groundbody.createFixture(groundfdef).setUserData(B2dvars.BIT_WALLS);

		}
		// *********** defines bannedwalls rectangle
		for(MapObject object : map.getLayers().get("bannedwalls").getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) object).getRectangle();

			// body definition
			groundbdef = new BodyDef();
			groundbdef.type = BodyType.StaticBody;
			groundbdef.position.set(((rect.getX() + rect.getWidth() / 2)) / PPM ,((rect.getY() + rect.getHeight() / 2))/PPM);

			// creating body 
			groundbody = world.createBody(groundbdef);

			// fixture definition
			groundfdef = new FixtureDef();	
			groundfdef.filter.categoryBits = B2dvars.BIT_BWALLS;
			groundfdef.filter.maskBits = B2dvars.BIT_BALL;

			// setting the shape
			PolygonShape pShape = new PolygonShape();
			groundfdef.shape = pShape;	pShape.setAsBox(rect.getWidth() / 2 / PPM, rect.getHeight() / 2 / PPM);

			// creating the fixture and setting user data
			groundbody.createFixture(groundfdef).setUserData(B2dvars.BIT_BWALLS);
		}
		// *********** defines worldwalls rectangle
		for(MapObject object : map.getLayers().get("worldwall").getObjects().getByType(PolylineMapObject.class)){
			float[] vertices = ((PolylineMapObject) object).getPolyline().getTransformedVertices();

			worldvertices = new Vector2[vertices.length/2];
			for (int i = 0; i < worldvertices.length; i++) {
				worldvertices[i] = new Vector2(vertices[i*2] / PPM,vertices[i*2+1] / PPM);

			}
			// body definition
			groundbdef = new BodyDef();
			groundbdef.type = BodyType.StaticBody;

			// creating body 
			groundbody = world.createBody(groundbdef);

			// fixture definition
			groundfdef = new FixtureDef();	
			groundfdef.filter.categoryBits = B2dvars.BIT_WALLS;
			groundfdef.filter.maskBits = B2dvars.BIT_BALL;

			// setting the shape
			cShape = new ChainShape();
			cShape.createChain(worldvertices);

			// creating the fixture and setting user data
			groundbody.setUserData(B2dvars.BIT_WALLS);
			groundbody.createFixture(cShape,0f).setFilterData(groundfdef.filter);
		}
		// *********** define one hit points
		if(map.getLayers().get("onehit") != null){
			for(MapObject object : map.getLayers().get("onehit").getObjects().getByType(RectangleMapObject.class)){
				if(object == null) break;
				Rectangle rect = ((RectangleMapObject) object).getRectangle();

				// body definition
				groundbdef = new BodyDef();
				groundbdef.type = BodyType.StaticBody;
				groundbdef.position.set(((rect.getX() + rect.getWidth() / 2)) / PPM ,((rect.getY() + rect.getHeight() / 2))/PPM);

				// creating body 
				groundbody = world.createBody(groundbdef);

				// fixture definition
				groundfdef = new FixtureDef();	
				groundfdef.filter.categoryBits = B2dvars.BIT_ONEHIT;
				groundfdef.filter.maskBits = B2dvars.BIT_BALL;

				// setting the shape
				PolygonShape pShape = new PolygonShape();
				groundfdef.shape = pShape;	pShape.setAsBox(rect.getWidth() / 2 / PPM, rect.getHeight() / 2 / PPM);

				// creating the fixture and setting user data
				groundbody.createFixture(groundfdef).setUserData(B2dvars.BIT_ONEHIT);
			}
		}
		// *********** define one hit points
				if(map.getLayers().get("axe") != null){
					for(MapObject object : map.getLayers().get("axe").getObjects().getByType(EllipseMapObject.class)){
						if(object == null) break;
						Ellipse rect = ((EllipseMapObject) object).getEllipse();

						// body definition
						groundbdef = new BodyDef();
						groundbdef.type = BodyType.StaticBody;
						groundbdef.position.set(((rect.x + rect.width / 2)) / PPM ,((rect.y) + rect.width / 2)/PPM);

						// creating body 
						groundbody = world.createBody(groundbdef);

						// fixture definition
						groundfdef = new FixtureDef();	
						groundfdef.filter.categoryBits = B2dvars.BIT_AXE;//B2dvars.BIT_ONEHIT | B2dvars.BIT_AXE;
						groundfdef.filter.maskBits = B2dvars.BIT_BALL;
						// setting the shape
						CircleShape pShape = new CircleShape();
						groundfdef.shape = pShape;	pShape.setRadius(20f / PPM);

						// creating the fixture and setting user data
						groundbody.createFixture(groundfdef).setUserData(B2dvars.BIT_ONEHIT);
					}
				}
		// *********** define finish point rectangle
		for(MapObject object : map.getLayers().get("finishpoint").getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) object).getRectangle();

			// body definition
			groundbdef = new BodyDef();
			groundbdef.type = BodyType.StaticBody;
			groundbdef.position.set(((rect.getX() + rect.getWidth() / 2)) / PPM ,((rect.getY() + rect.getHeight() / 2))/PPM);

			// creating body 
			groundbody = world.createBody(groundbdef);

			// fixture definition
			groundfdef = new FixtureDef();	
			groundfdef.filter.categoryBits = B2dvars.BIT_FINISHPOINT;
			groundfdef.filter.maskBits = B2dvars.BIT_BALL;

			// setting the shape
			PolygonShape pShape = new PolygonShape();
			groundfdef.shape = pShape;	pShape.setAsBox(rect.getWidth() / 2 / PPM, rect.getHeight() / 2 / PPM);
			groundfdef.isSensor = true;
			// creating the fixture and setting user data
			groundbody.createFixture(groundfdef).setUserData(B2dvars.BIT_FINISHPOINT);
		}
		// *********** defines lines 
		for(MapObject object : map.getLayers().get("lines").getObjects().getByType(PolylineMapObject.class)){
			float[] vertices = ((PolylineMapObject) object).getPolyline().getTransformedVertices();

			Vector2[] worldvertices = new Vector2[vertices.length/2];
			for (int i = 0; i < worldvertices.length; i++) {
				worldvertices[i] = new Vector2(vertices[i*2] / PPM,vertices[i*2+1] / PPM);

			}
			// body definition
			groundbdef = new BodyDef();
			groundbdef.type = BodyType.StaticBody;

			// creating body 
			groundbody = world.createBody(groundbdef);

			// fixture definition
			groundfdef = new FixtureDef();	
			groundfdef.filter.categoryBits = B2dvars.BIT_WALLS;
			groundfdef.filter.maskBits = B2dvars.BIT_BALL;

			// setting the shape
			cShape = new ChainShape();
			cShape.createChain(worldvertices);

			// creating the fixture and setting user data
			groundbody.setUserData(B2dvars.BIT_WALLS);
			groundbody.createFixture(cShape,0f).setFilterData(groundfdef.filter);
		}
		// *********** defines rope 
		for(MapObject object : map.getLayers().get("rope").getObjects().getByType(PolylineMapObject.class)){
			ropevertices = ((PolylineMapObject) object).getPolyline().getTransformedVertices();

			ropeworldvertices = new Vector2[ropevertices.length/2];
			for (int i = 0; i < worldvertices.length; i++) {
				ropeworldvertices[i] = new Vector2(ropevertices[i*2] / PPM,ropevertices[i*2+1] / PPM);

			}
			// body definition
			groundbdef = new BodyDef();
			groundbdef.type = BodyType.StaticBody;

			// creating body 
			groundbody = world.createBody(groundbdef);

			// fixture definition
			groundfdef = new FixtureDef();	
			groundfdef.filter.categoryBits = B2dvars.BIT_ROPE;
			groundfdef.filter.maskBits = B2dvars.BIT_BALL;

			// setting the shape
			cShape = new ChainShape();
			cShape.createChain(ropeworldvertices);

			// creating the fixture and setting user data
			groundbody.setUserData(B2dvars.BIT_WALLS);
			groundbody.createFixture(cShape,0f).setFilterData(groundfdef.filter);
		}
		// *********** defines gems 
		for(MapObject object : map.getLayers().get("gems").getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) object).getRectangle();

			// body definition
			groundbdef = new BodyDef();
			groundbdef.type = BodyType.StaticBody;
			groundbdef.position.set(((rect.getX() + rect.getWidth() / 2)) / PPM ,((rect.getY() + rect.getHeight() / 2))/PPM);

			// creating body 
			groundbody = world.createBody(groundbdef);

			// fixture definition
			groundfdef = new FixtureDef();	
			groundfdef.filter.categoryBits = B2dvars.BIT_GEM;
			groundfdef.filter.maskBits = B2dvars.BIT_BALL;

			// setting the shape
			PolygonShape pShape = new PolygonShape();
			groundfdef.shape = pShape;	pShape.setAsBox(rect.getWidth() / 4 / PPM, rect.getHeight() / 4 / PPM);
			groundfdef.isSensor = true;
			// creating the fixture and setting user data

			groundbody.createFixture(groundfdef).setUserData(B2dvars.BIT_GEM);
		}
		// *********** defines key 
		for(MapObject object : map.getLayers().get("key").getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) object).getRectangle();

			// body definition
			groundbdef = new BodyDef();
			groundbdef.type = BodyType.StaticBody;
			groundbdef.position.set(((rect.getX() + rect.getWidth() / 2)) / PPM ,((rect.getY() + rect.getHeight() / 2))/PPM);

			// creating body 
			groundbody = world.createBody(groundbdef);

			// fixture definition
			groundfdef = new FixtureDef();	
			groundfdef.filter.categoryBits = B2dvars.BIT_KEY;
			groundfdef.filter.maskBits = B2dvars.BIT_BALL;

			// setting the shape
			PolygonShape pShape = new PolygonShape();
			groundfdef.shape = pShape;	pShape.setAsBox(rect.getWidth() / 4 / PPM, rect.getHeight() / 4 / PPM);
			groundfdef.isSensor = true;
			// creating the fixture and setting user data
			groundbody.createFixture(groundfdef).setUserData(B2dvars.BIT_KEY);
		}
		/* TOTELEPORT*/
		if(map.getLayers().get("teleportpoint") != null){
			for(MapObject object : map.getLayers().get("teleportpoint").getObjects().getByType(RectangleMapObject.class)){
				Rectangle rect = ((RectangleMapObject) object).getRectangle();

				// body definition
				groundbdef = new BodyDef();
				groundbdef.type = BodyType.StaticBody;
				groundbdef.position.set(((rect.getX() + rect.getWidth() / 2)) / PPM ,((rect.getY() + rect.getHeight() / 2))/PPM);

				// creating body 
				groundbody = world.createBody(groundbdef);

				// fixture definition
				groundfdef = new FixtureDef();	
				groundfdef.filter.categoryBits = B2dvars.BIT_TELEPORT;
				groundfdef.filter.maskBits = B2dvars.BIT_BALL;

				// setting the shape
				PolygonShape pShape = new PolygonShape();
				groundfdef.shape = pShape;	pShape.setAsBox(rect.getWidth() / 2 / PPM, rect.getHeight() / 2 / PPM);
				groundfdef.isSensor = true;
				// creating the fixture and setting user data
				groundbody.createFixture(groundfdef).setUserData(B2dvars.BIT_TELEPORT);
			}}
		/* TELEPORT */
		if(map.getLayers().get("toteleport") != null){
			for(MapObject object : map.getLayers().get("toteleport").getObjects().getByType(RectangleMapObject.class)){
				Rectangle rect = ((RectangleMapObject) object).getRectangle();

				// body definition
				groundbdef = new BodyDef();
				groundbdef.type = BodyType.StaticBody;
				groundbdef.position.set(((rect.getX() + rect.getWidth() / 2)) / PPM ,((rect.getY() + rect.getHeight() / 2))/PPM);

				// creating body 
				groundbody = world.createBody(groundbdef);

				// fixture definition
				groundfdef = new FixtureDef();	

				// setting the shape
				PolygonShape pShape = new PolygonShape();
				groundfdef.shape = pShape;	pShape.setAsBox(rect.getWidth() / 2 / PPM, rect.getHeight() / 2 / PPM);
				groundfdef.isSensor = true;
				// creating the fixture and setting user data
				groundbody.createFixture(groundfdef).setUserData("tpcoor");
			}
		}
		// *********** defines switch button 
		for(MapObject object : map.getLayers().get("switch").getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) object).getRectangle();

			// body definition
			groundbdef = new BodyDef();
			groundbdef.type = BodyType.StaticBody;
			groundbdef.position.set(((rect.getX() + rect.getWidth() / 2)) / PPM ,((rect.getY() + rect.getHeight() / 2))/PPM);
			//groundbdef.fixedRotation = true;


			// creating body 
			groundbody = world.createBody(groundbdef);

			// fixture definition
			groundfdef = new FixtureDef();	
			groundfdef.filter.categoryBits = B2dvars.BIT_SWITCH;
			groundfdef.filter.maskBits = B2dvars.BIT_BALL;

			// setting the shape
			PolygonShape pShape = new PolygonShape();
			pShape.setAsBox(rect.getWidth() / 2 / PPM, rect.getHeight() / 8 / PPM);
			groundfdef.shape = pShape;
			// creating the fixture and setting user data

			groundbody.createFixture(groundfdef).setUserData("SWITCHER");;
			pShape.dispose();



			// left sensor 
			groundfdef = new FixtureDef();
			pShape = new PolygonShape();
			pShape.setAsBox(rect.getWidth() / 3 / PPM, rect.getHeight() / 8 / PPM,new Vector2(0,1 / PPM),0);
			groundfdef.shape = pShape;	
			groundfdef.filter.categoryBits = B2dvars.BIT_SWITCH;
			groundfdef.filter.maskBits = B2dvars.BIT_BALL;
			groundfdef.isSensor = true;
			groundbody.createFixture(groundfdef).setUserData("down");
			pShape.dispose();

			//groundbody.setUserData(B2dvars.BIT_SWITCH);

		}
	}
	public void dispose() {
		butdown.dispose();
		butup.dispose();
		circle.dispose();
		cShape.dispose();
		down.dispose();
		hrope.dispose();
		key.dispose();
		left.dispose();
		
		if(light != null)
		light.dispose();
		
		lock.dispose();
		if(point != null)
			point.dispose();

		right.dispose();
		up.dispose();
		vrope.dispose();
		world.dispose();
	}
}
