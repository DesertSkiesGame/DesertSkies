package org.dersertskies;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.ScreenUtils;

public class DesertSkies extends ApplicationAdapter {
	//CONSTANTS
	private final int FOV = 67;

	//Camera
	private PerspectiveCamera camera;
	private CameraInputController cameraController;

	//Models
	private ModelBatch modelBatch;
	private ModelInstance modelInstance;
	private ModelBuilder modelBuilder;
	private ModelLoader modelLoader;
	private Model testModel;

	//Environment
	private Environment environment;
	
	@Override
	public void create () {
		modelBatch = new ModelBatch();

		camera = new PerspectiveCamera(FOV, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(10f,10f,10f);
		camera.lookAt(0f,0f,0f);
		camera.near = 1f;
		camera.far = 300f;
		camera.update();

		/*modelBuilder = new ModelBuilder();
		testModel = modelBuilder.createBox(5f,5f,5f,
				new Material(ColorAttribute.createDiffuse(Color.GREEN)),
				VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);*/
		modelLoader = new ObjLoader();
		testModel = modelLoader.loadModel(Gdx.files.internal("data/testHouse.obj"));
		modelInstance = new ModelInstance(testModel);

		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

		cameraController = new CameraInputController(camera);
		Gdx.input.setInputProcessor(cameraController);
	}

	@Override
	public void render () {
		cameraController.update();
		Gdx.gl.glViewport(0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		modelBatch.begin(camera);
		modelBatch.render(modelInstance, environment);
		modelBatch.end();
	}
	
	@Override
	public void dispose () {
		testModel.dispose();
	}
}
