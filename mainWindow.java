//Imports
	import javafx.application.Application;
	import javafx.scene.image.Image;
	import javafx.scene.image.ImageView;
	import javafx.scene.image.*;
	import javafx.scene.paint.Color;
	import javafx.scene.paint.CycleMethod;
	import javafx.scene.paint.LinearGradient;
	import javafx.scene.paint.Stop;
	import javafx.scene.shape.Rectangle;
	import javafx.scene.text.Font;
	import javafx.scene.text.FontWeight;
	import javafx.scene.text.Text;
	import javafx.beans.value.ChangeListener;
	import javafx.beans.value.ObservableValue;
	import javafx.event.ActionEvent;
	import javafx.event.EventHandler;
	import javafx.scene.control.*;
	import javafx.scene.Node;
	import javafx.stage.*;
	import javafx.scene.*;
	import javafx.scene.input.*;
	import javafx.scene.layout.*;
	import javafx.scene.control.*;
	import javafx.geometry.*;
	import javafx.scene.shape.*;
	import javafx.scene.effect.*;
	import javafx.scene.paint.*;
	import javafx.scene.text.*;
	import javafx.scene.canvas.*;
	import javafx.animation.*;

	import java.util.*;
	import java.io.*;
	import javafx.collections.FXCollections;
	import javafx.collections.ListChangeListener;
	import javafx.collections.ObservableList;

	import javafx.scene.media.Media;
	import javafx.scene.media.MediaPlayer;

/**
* GUI and animation for the core game
* Available online at jmerkl.github.io
* @author Jackson M.
* @version 1.0
* Memes are king
*/
public class mainWindow extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void start(Stage stage) {

		int windowHeight = 400;
		int windowWidth = 750;

		BorderPane mainWindow = new BorderPane();
		BorderPane gameWindow = new BorderPane();
		mainWindow.setPrefSize(windowWidth, windowHeight);
		mainWindow.setStyle("-fx-background-color: #000000;");

		Scene scene = new Scene(mainWindow);

		Group root = new Group();
			Canvas canvas = new Canvas(windowWidth, windowHeight);

			Label scoreLabel = new Label("Score: ");
			scoreLabel.setFont(new Font("Verdana", 20));
			scoreLabel.setFont(Font.font(null, FontWeight.BOLD, 20));
			scoreLabel.setPadding(new Insets(0,5,10,5));
		root.getChildren().addAll(canvas,scoreLabel);
		GraphicsContext gc = canvas.getGraphicsContext2D();

		// Start Pane UI
			Button startButton = new Button("Start");
			startButton.setFont(new Font("Verdana", 18));

		// Reset & Hiscores UI
			BorderPane resetPane = new BorderPane();
			resetPane.setStyle("-fx-background-color: #FFFFFF;");
				Button resetButton = new Button("Reset");
				resetButton.setFont(new Font("Verdana", 18));

				HBox resetButtonBox = new HBox();
				resetButtonBox.getChildren().addAll(resetButton);
				resetButtonBox.setAlignment(Pos.CENTER);
				resetButtonBox.setPadding(new Insets(5,0,0,0));

				Button nameButton = new Button("Enter Score");
				nameButton.setFont(new Font("Verdana", 18));

				VBox resetBox = new VBox(10);
				resetBox.setAlignment(Pos.CENTER);

				TextField nameField = new TextField();
				Label finalScoreLabel = new Label(scoreLabel.getText());
				finalScoreLabel.setFont(new Font("Verdana", 20));
				finalScoreLabel.setFont(Font.font(null, FontWeight.BOLD, 20));
				nameField.setMaxWidth(200);
				resetBox.getChildren().addAll(finalScoreLabel, nameField, nameButton);

				Records records = new Records("memory files/hiscores.txt");

				ObservableList<String> hiscoreList = FXCollections.observableArrayList(records.getScoreList());
				ListView<String> hiscoreView = new ListView<String>(hiscoreList);
				Label hiscoreLabel = new Label("Hiscores");
				hiscoreLabel.setFont(new Font("Verdana", 18));

				VBox hsBox = new VBox(10);
				hsBox.getChildren().addAll(hiscoreLabel, hiscoreView);
				hsBox.setAlignment(Pos.CENTER);
				hsBox.setPadding(new Insets(0,0,50,0));
			resetPane.setLeft(resetBox);
			resetPane.setRight(hsBox);
			resetPane.setBottom(resetButtonBox);
			resetPane.setPadding(new Insets(10,100,10,100));

			mainWindow.setCenter(startButton);

		// Loading music
			File musicFiles = new File("music");
			ArrayList<String> songNames = new ArrayList<String>(Arrays.asList(musicFiles.list()));
			ArrayList<MediaPlayer> songs = new ArrayList<MediaPlayer>();
			for (int i = 0; i < songNames.size(); i++) {
				songs.add(new MediaPlayer(new Media(getClass().getClassLoader().getResource("music/" + songNames.get(i)).toString())));
			}

		// Game Settings
			Image gameBackground = new Image("images/background.jpg");
			Image mainCharacter = new Image("images/sombrero.PNG", 50.0, 40.0, true, true);
			Image singleSpike = new Image("images/trumpWall2.PNG", 50.0, 80.0, true, true);
			Image doubleSpike = new Image("images/predator.PNG", 80.0, 100.0, true, true);

			final GameTimer timer = new GameTimer();

			final double dxdt = 0.0; //Speed
			final double scrollSpeed = -1600.0; //speed of obstacles & background
			final double dydt = 150.0; //vertical speed
			final double g = 3000.0; //Restoring gravity
			final double groundLevel = 325.0; //Ground level position
			final ArrayList<Double> timeList = new ArrayList<Double>();
			final double[] spikeLocations = {100.0, 200.0, 300.0, 400.0, 500.0, 600.0, 700.0, 800.0, 900.0, 1000.0};

			MainCharacter mc = new MainCharacter(mainCharacter, dxdt, dydt, 100.0, groundLevel);
			ScrollingBackground scrollBg = new ScrollingBackground(gameBackground, scrollSpeed, 0.0, 0.0);
			Spike spike = new Spike(singleSpike, scrollSpeed, (double)windowWidth + 50.0, groundLevel-35.0);
			Spike spikes = new Spike(doubleSpike, scrollSpeed, (double)windowWidth + 150.0, 100.0);

		// Game Loop
			AnimationTimer gameLoop = new AnimationTimer() {
				@Override
				public void handle(long currentNanoTime) {
					
					if (timer.isReset()) {
						timeList.clear();
						timer.reset();

						scrollBg.setX(0.0);

						spike.setX((double)windowWidth + 50.0);
						spikes.setX((double)windowWidth + 150.0);

						spike.pass();
						spikes.pass();

						timer.setSongInt(0);
						songs.get(timer.getSongInt()).play();
					}

					double t = timer.incrementTime();
					
					timeList.add(t);
					int endLoc = timeList.size();
					double dt = 0.004;
					if (endLoc > 2) {
						dt = timeList.get(endLoc-1) - timeList.get(endLoc-2);
					}

					double x = dxdt*t;
					double xx = -scrollSpeed*t;
					double y = groundLevel;

					// Jump-Key Sequence
						//Spacebar
						scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
						      if(key.getCode()==KeyCode.SPACE) {
						          if (!mc.isFlying(groundLevel)) {
						          	mc.setJump(true);
						          } else if (mc.getJump() & !mc.getDoubleJump()) {
						          	mc.setDoubleJump(true);
						          }
						      }
						});
						//Mouse
						scene.addEventHandler(MouseEvent.MOUSE_CLICKED, (mouse) -> {
							if (!mc.isFlying(groundLevel)) {
					        	mc.setJump(true);
					        } else if (mc.getJump() & !mc.getDoubleJump()) {
					        	mc.setDoubleJump(true);
					        }
						});

					// Jump Physics Sequence
						if (mc.getJump()) {
							mc.jump(dydt, g, dt, groundLevel);
						} else if (mc.getDoubleJump()) {
							mc.doubleJump(dydt, g, dt, groundLevel);
						} else if (mc.isFlying(groundLevel)) {
							mc.jump(dydt, g, dt, groundLevel);
						} else {
							mc.setTime(0.0);
						}

					// Check for Collision
						double singleSpikeLeft = spike.getX() - (spike.getImage().getWidth()/2.0) - mc.getX();
						double singleSpikeRight = spike.getX() + (spike.getImage().getWidth()/2.0) - mc.getX();
						double doubleSpikeLeft = spikes.getX() - (spikes.getImage().getWidth()/2.0) - mc.getX();
						double doubleSpikeRight = spikes.getX() + (spikes.getImage().getWidth()/2.0) - mc.getX();

						if (xx > singleSpikeLeft && xx < singleSpikeRight) {
							if (mc.getY() > (spike.getY() - spike.getImage().getHeight())) {
								mc.death();
							}
						}

						if (xx > doubleSpikeLeft && xx < doubleSpikeRight) {
							if ((mc.getY()-mc.getImage().getHeight()) < (spikes.getY() + spikes.getImage().getHeight())) {
								mc.death();
							}
						}

						if (xx > singleSpikeLeft*1.2) {
							spike.setX(xx+750.0);
						}
						if (xx > doubleSpikeLeft*1.2) {
							spikes.setX(xx+900.0);
						}

					//Continue or terminate the game
						if (mc.isAlive()) {
							double score = t * 24;
							scoreLabel.setText("Score: " + Integer.toString((int)score));

							gc.drawImage(scrollBg.getImage(),scrollBg.scroll(x,dt),0);
							gc.drawImage(spike.getImage(),spike.scroll(spike.getX(),dt),spike.getY());
							gc.drawImage(spikes.getImage(),spikes.scroll(spike.getX(),dt),spikes.getY());
							gc.drawImage(mc.getImage(), mc.getX(), mc.getY());
						} else {
							stop();
							double score = t * 24;
							finalScoreLabel.setText("Score: " + Integer.toString((int)score));
							songs.get(timer.getSongInt()).stop();
							mainWindow.setCenter(resetPane);
						}

					//Shuffle Playlist or Reset Music
						songs.get(timer.getSongInt()).setOnEndOfMedia(new Runnable() { //Anonymous inner class runnable
						    @Override
						    public void run()
						    {
						        timer.setSongInt(timer.getSongInt() + 1);
								if (timer.getSongInt() >= songs.size()) {
									timer.setSongInt(0);
								}
								songs.get(timer.getSongInt()).play();
						    }
						});					
				}
			};

		// UI Element Functionality
			startButton.setOnAction(e -> {
				mainWindow.setCenter(gameWindow);
				mainWindow.setStyle("-fx-background-color: #000000;");

				timer.reset();
				gameLoop.start();
			});

			resetButton.setOnAction(e -> {
				scoreLabel.setText("Score: 0");
				mc.revive();

				timer.reset();
				gameLoop.start();
				nameButton.setDisable(false);

				mainWindow.setCenter(gameWindow);
				mainWindow.setStyle("-fx-background-color: #000000;");
			});

			nameButton.setOnAction(e -> {
				String name = nameField.getText();
				String[] halves = finalScoreLabel.getText().split(": ");
				int plyScore = Integer.parseInt(halves[1]);
				records.updateScores(new Scores(name, plyScore));

				nameField.clear();

				hsBox.getChildren().clear();
				hsBox.getChildren().addAll(hiscoreLabel, new ListView<String>(FXCollections.observableArrayList(records.getScoreList())));
				hsBox.setAlignment(Pos.CENTER);

				nameButton.setDisable(true);
			});


		gameWindow.setCenter(root);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Run & Jump Man");
		stage.show();
	}
}