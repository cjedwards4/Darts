

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.*;
import javafx.scene.text.TextAlignment;

public class Darts extends Application {
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Darts");
		DartsPlayer player1=new DartsPlayer();
		DartsPlayer player2=new DartsPlayer();
		player1.switchTurn();
		//next section for getting input for player names
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		Text scenetitle = new Text("Enter names for players");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);
		Label name1 = new Label("Player1:");
		grid.add(name1, 0, 1);
		TextField userTextField = new TextField();
		grid.add(userTextField, 1, 1);
		Label name2 = new Label("Player2:");
		grid.add(name2, 0, 2);
		TextField userTextField2 = new TextField();
		grid.add(userTextField2, 1, 2);
		Button submit = new Button("Submit");
		GridPane.setConstraints(submit, 3, 4);
		grid.getChildren().add(submit);
		Scene scene=new Scene(grid,400,200);
		Stage stage=new Stage();
		stage.setScene(scene);
		submit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				player1.setName(userTextField.getText());
				player2.setName(userTextField2.getText());
				stage.close();
				;}
		});
		stage.showAndWait();
		//if no names are entered set names to player1 and player2
		if(player1.getName().equals(""))
			player1.setName("player1");
		if(player2.getName().equals(""))
			player2.setName("player2");
		//construct dart board and create events
		Group root = new Group();
		scene=new Scene(root,500,500);
		scene.setFill(Color.PERU);
		//construct canvas for scoreboard graphics
		Canvas scorebrd=new Canvas(140,110);
		scorebrd.relocate(330, 10);
		GraphicsContext playerNames = scorebrd.getGraphicsContext2D();
		playerNames.setTextBaseline(VPos.TOP);
		playerNames.setFill(Color.BLACK);
		playerNames.fillRect(-10,0,170,100);
		playerNames.setTextAlign(TextAlignment.CENTER);
		playerNames.setFill(Color.WHITE);
		playerNames.setFont(Font.font("Verdana",FontWeight.BOLD,14));
		playerNames.fillText(player1.getName()+"  "+player2.getName(), Math.round(scorebrd.getWidth()/2), 10);
		GraphicsContext Score = scorebrd.getGraphicsContext2D();
		Score.setTextBaseline(VPos.CENTER);
		Score.setTextAlign(TextAlignment.CENTER);
		Score.setFill(Color.BLACK);
		Score.fillRect(10,40,170,20);
		Score.setFill(Color.WHITE);
		Score.setFont(Font.font("Tahoma",FontWeight.BOLD,20));
		Score.fillText(player1.getScore()+"  -  "+player2.getScore(), Math.round(scorebrd.getWidth()/2), Math.round(scorebrd.getHeight()/2));
		Text turnsTxt=new Text(335,100,"turns:");
		turnsTxt.setFill(Color.WHITE);
		turnsTxt.setTextAlignment(TextAlignment.LEFT);
		Text turnsLeft=new Text(377,100,11-player1.getTurnNumber()+"             "+(11-player2.getTurnNumber()));
		turnsLeft.setFill(Color.WHITE);
		root.getChildren().addAll(scorebrd,turnsTxt,turnsLeft);
		//transparent rectangle around board to record misses 
		Rectangle miss_board=new Rectangle(100,100,300,300);
		miss_board.setFill(Color.TRANSPARENT);
		handleEvent(miss_board, player1, player2,0,scorebrd,Score, turnsLeft, root);
		itsYourTurn(player1,root);    //Display that it's player1's turn
		Circle c=new Circle(250,250,120);
		Circle d=new Circle(250,250,90);
		//outer layer
		Shape boundary=Shape.subtract(c, d);
		boundary.setEffect(new GaussianBlur());
		boundary.setEffect(new Lighting());
		handleEvent(boundary, player1, player2,0,scorebrd, Score, turnsLeft,root);
		//double layer
		Shape double6=CreateLayer(90,90,342,36);
		double6.setFill(Color.GREEN);
		handleEvent(double6, player1, player2,12,scorebrd,Score, turnsLeft,root);
		Shape double10=CreateLayer(90,90,18,36);
		double10.setFill(Color.RED);
		handleEvent(double10, player1, player2,20, scorebrd,Score, turnsLeft,root);
		Shape double4=CreateLayer(90,90,54,36);
		double4.setFill(Color.GREEN);
		handleEvent(double4, player1, player2,8,scorebrd,Score, turnsLeft, root);
		Shape double20=CreateLayer(90,90,90,36);
		double20.setFill(Color.RED);
		handleEvent(double20, player1, player2,40,scorebrd,Score, turnsLeft, root);
		Shape double14=CreateLayer(90,90,126,36);
		double14.setFill(Color.GREEN);
		handleEvent(double14, player1, player2,28,scorebrd,Score, turnsLeft,root);
		Shape double8=CreateLayer(90,90,162,36);
		double8.setFill(Color.RED);
		handleEvent(double8, player1, player2,16,scorebrd,Score, turnsLeft, root);
		Shape double16=CreateLayer(90,90,198,36);
		double16.setFill(Color.GREEN);
		handleEvent(double16, player1, player2,32,scorebrd,Score, turnsLeft, root);
		Shape double2=CreateLayer(90,90,234,36);
		double2.setFill(Color.RED);
		handleEvent(double2, player1, player2,4,scorebrd,Score,  turnsLeft,root);
		Shape double18=CreateLayer(90,90,270,36);
		double18.setFill(Color.GREEN);
		handleEvent(double18, player1, player2,36,scorebrd,Score,turnsLeft, root);
		Shape double12=CreateLayer(90,90,306,36);
		double12.setFill(Color.RED);
		handleEvent(double12, player1, player2,24,scorebrd,Score, turnsLeft, root);
		root.getChildren().addAll(miss_board,boundary,double2,double4,double6,double8,double10,double12,double14,double16,double18,double20);
		//Normal layer a
		Shape six_a=CreateSecondLayer(80,80,342,36);
		six_a.setFill(Color.WHITE);
		handleEvent(six_a, player1, player2,6,scorebrd,Score, turnsLeft, root);
		Shape ten_a=CreateSecondLayer(80,80,18,36);
		ten_a.setFill(Color.BLACK);
		handleEvent(ten_a, player1, player2,10,scorebrd,Score, turnsLeft, root);
		Shape four_a=CreateSecondLayer(80,80,54,36);
		four_a.setFill(Color.WHITE);
		handleEvent(four_a, player1, player2,4,scorebrd,Score, turnsLeft, root);
		Shape twenty_a=CreateSecondLayer(80,80,90,36);
		twenty_a.setFill(Color.BLACK);
		handleEvent(twenty_a, player1, player2,20,scorebrd,Score, turnsLeft,root);
		Shape fourteen_a=CreateSecondLayer(80,80,126,36);
		fourteen_a.setFill(Color.WHITE);
		handleEvent(fourteen_a, player1, player2,14,scorebrd,Score, turnsLeft, root);
		Shape eight_a=CreateSecondLayer(80,80,162,36);
		eight_a.setFill(Color.BLACK);
		handleEvent(eight_a, player1, player2,8,scorebrd,Score, turnsLeft, root);
		Shape sixteen_a=CreateSecondLayer(80,80,198,36);
		sixteen_a.setFill(Color.WHITE);
		handleEvent(sixteen_a, player1, player2,16,scorebrd,Score, turnsLeft, root);
		Shape two_a=CreateSecondLayer(80,80,234,36);
		two_a.setFill(Color.BLACK);
		handleEvent(two_a, player1, player2,2,scorebrd,Score, turnsLeft, root);
		Shape eighteen_a=CreateSecondLayer(80,80,270,36);
		eighteen_a.setFill(Color.WHITE);
		handleEvent(eighteen_a, player1, player2,18,scorebrd,Score, turnsLeft, root);
		Shape twelve_a=CreateSecondLayer(80,80,306,36);
		twelve_a.setFill(Color.BLACK);
		handleEvent(twelve_a, player1, player2,12,scorebrd,Score, turnsLeft, root);
		root.getChildren().addAll(two_a,four_a,six_a,eight_a,ten_a,twelve_a,fourteen_a,sixteen_a,eighteen_a,twenty_a);
		//triple layer
		Shape triple6=CreateLayer(50,50,342,36);
		triple6.setFill(Color.GREEN);
		handleEvent(triple6, player1, player2,18,scorebrd,Score, turnsLeft, root);
		Shape triple10=CreateLayer(50,50,18,36);
		triple10.setFill(Color.RED);
		handleEvent(triple10, player1, player2,30,scorebrd,Score, turnsLeft,root);
		Shape triple4=CreateLayer(50,50,54,36);
		triple4.setFill(Color.GREEN);
		handleEvent(triple4, player1, player2,12,scorebrd,Score, turnsLeft, root);
		Shape triple20=CreateLayer(50,50,90,36);
		triple20.setFill(Color.RED);
		handleEvent(triple20, player1, player2,60, scorebrd,Score, turnsLeft,root);
		Shape triple14=CreateLayer(50,50,126,36);
		triple14.setFill(Color.GREEN);
		handleEvent(triple14, player1, player2,42,scorebrd,Score, turnsLeft, root);
		Shape triple8=CreateLayer(50,50,162,36);
		triple8.setFill(Color.RED);
		handleEvent(triple8, player1, player2,24,scorebrd,Score, turnsLeft, root);
		Shape triple16=CreateLayer(50,50,198,36);
		triple16.setFill(Color.GREEN);
		handleEvent(triple16, player1, player2,48,scorebrd,Score, turnsLeft, root);
		Shape triple2=CreateLayer(50,50,234,36);
		triple2.setFill(Color.RED);
		handleEvent(triple2, player1, player2,6,scorebrd,Score, turnsLeft, root);
		Shape triple18=CreateLayer(50,50,270,36);
		triple18.setFill(Color.GREEN);
		handleEvent(triple18, player1, player2,54,scorebrd,Score, turnsLeft, root);
		Shape triple12=CreateLayer(50,50,306,36);
		triple12.setFill(Color.RED);
		handleEvent(triple12, player1, player2,36,scorebrd,Score, turnsLeft, root);
		root.getChildren().addAll(triple2,triple4,triple6,triple8,triple10,triple12,triple14,triple16,triple18,triple20);
		//normal layer b
		Shape six_b=CreateSecondLayer(40,40,342,36);
		six_b.setFill(Color.WHITE);
		handleEvent(six_b, player1, player2,6,scorebrd,Score, turnsLeft, root);
		Shape ten_b=CreateSecondLayer(40,40,18,36);
		ten_b.setFill(Color.BLACK);
		handleEvent(ten_b, player1, player2,10,scorebrd,Score, turnsLeft, root);
		Shape four_b=CreateSecondLayer(40,40,54,36);
		four_b.setFill(Color.WHITE);
		handleEvent(four_b, player1, player2,4,scorebrd,Score, turnsLeft, root);
		Shape twenty_b=CreateSecondLayer(40,40,90,36);
		twenty_b.setFill(Color.BLACK);
		handleEvent(twenty_b, player1, player2,20,scorebrd,Score, turnsLeft, root);
		Shape fourteen_b=CreateSecondLayer(40,40,126,36);
		fourteen_b.setFill(Color.WHITE);
		handleEvent(fourteen_b, player1, player2,14,scorebrd,Score, turnsLeft, root);
		Shape eight_b=CreateSecondLayer(40,40,162,36);
		eight_b.setFill(Color.BLACK);
		handleEvent(eight_b, player1, player2,8,scorebrd,Score, turnsLeft, root);
		Shape sixteen_b=CreateSecondLayer(40,40,198,36);
		sixteen_b.setFill(Color.WHITE);
		handleEvent(sixteen_b, player1, player2,16,scorebrd,Score, turnsLeft, root);
		Shape two_b=CreateSecondLayer(40,40,234,36);
		two_b.setFill(Color.BLACK);
		handleEvent(two_b, player1, player2,2,scorebrd,Score, turnsLeft, root);
		Shape eighteen_b=CreateSecondLayer(40,40,270,36);
		eighteen_b.setFill(Color.WHITE);
		handleEvent(eighteen_b, player1, player2,18,scorebrd,Score, turnsLeft, root);
		Shape twelve_b=CreateSecondLayer(40,40,306,36);
		twelve_b.setFill(Color.BLACK);
		handleEvent(twelve_b, player1, player2,12,scorebrd,Score, turnsLeft, root);
		root.getChildren().addAll(two_b,four_b,six_b,eight_b,ten_b,twelve_b,fourteen_b,sixteen_b,eighteen_b,twenty_b);
		//inner ring and bullseye
		Circle e=new Circle(250,250,10);
		Circle bullseye=new Circle(250,250,3);
		bullseye.setFill(Color.RED);
		handleEvent(bullseye, player1, player2,50,scorebrd,Score, turnsLeft, root);
		Shape inner_ring=Shape.subtract(e, bullseye);
		inner_ring.setFill(Color.GREEN);
		handleEvent(inner_ring, player1, player2,25,scorebrd,Score, turnsLeft, root);
		//numbers along outer ring
		Text t6=BuildTextRing(350,255,"6");
		handleEvent(t6,player1, player2, 0,scorebrd,Score, turnsLeft,root);
		Text t10=BuildTextRing(326,195,"10");
		handleEvent(t10,player1, player2, 0,scorebrd,Score, turnsLeft,root);
		Text t4=BuildTextRing(275,158,"4");
		handleEvent(t4,player1, player2, 0,scorebrd,Score, turnsLeft,root);
		Text t20=BuildTextRing(210,158,"20");
		handleEvent(t20,player1, player2, 0,scorebrd,Score, turnsLeft,root);
		Text t14=BuildTextRing(153,195,"14");
		handleEvent(t14,player1, player2, 0,scorebrd,Score, turnsLeft,root);
		Text t8=BuildTextRing(140,255,"8");
		handleEvent(t8,player1, player2, 0,scorebrd,Score, turnsLeft,root);
		Text t16=BuildTextRing(153,315,"16");
		handleEvent(t16,player1, player2, 0,scorebrd,Score, turnsLeft,root);
		Text t2=BuildTextRing(210,355,"2");
		handleEvent(t2,player1, player2, 0,scorebrd,Score, turnsLeft,root);
		Text t18=BuildTextRing(275,354,"18");
		handleEvent(t18,player1, player2, 0,scorebrd,Score, turnsLeft,root);
		Text t12=BuildTextRing(325,315,"12");
		handleEvent(t12,player1, player2, 0,scorebrd,Score, turnsLeft,root);
		root.getChildren().addAll(inner_ring,bullseye,t2,t4,t6,t8,t10,t12,t14,t16,t18,t20);
		// animated targets
		//key for scores of animated targets
		Circle key=new Circle(8,12,5); 
		key.setFill(Color.GOLD);
		Text k1=new Text(15,15," 100 points");
		Circle key2=new Circle(8,33,5);	
		key2.setFill(Color.DEEPPINK);
		Text k2=new Text(15,35," 90 points");
		Rectangle key3=new Rectangle(3, 46, 10, 10);
		key3.setFill(Color.DARKTURQUOISE);
		Text k3=new Text(15,55," 80 points");
		Rectangle key4=new Rectangle(3,66,10,10); 	
		key4.setFill(Color.ORANGERED);
		Text k4=new Text(15,75," 70 points");
		root.getChildren().addAll(key,key2,key3,key4,k1,k2,k3,k4);
		//first animated target
		Rectangle r=new Rectangle(310, 250, 10, 10);    //target worth 80
		r.setFill(Color.DARKTURQUOISE);
		handleEvent(r, player1, player2,80,scorebrd,Score, turnsLeft, root);
		root.getChildren().add(r);
		PathTransition p1=CreateCircleTrans(310,250,309.9999,250.1047,60,4500,r);
		p1.play();
		//second animated target
		Rectangle r2=new Rectangle(186,186,10,10); 	//target worth 70
		r2.setFill(Color.ORANGERED);
		handleEvent(r2, player1, player2,70,scorebrd,Score, turnsLeft, root);
		root.getChildren().add(r2);
		Path path = new Path();
		path.getElements().add(new MoveTo(186,186));
		path.getElements().add(new LineTo(314,186));
		path.getElements().add(new LineTo(186,314));
		path.getElements().add(new LineTo(314,314));
		path.getElements().add(new LineTo(186,186));
		PathTransition p2= new PathTransition();
		p2.setPath(path);
		p2.setNode(r2);
		p2.setInterpolator(Interpolator.LINEAR);
		p2.setDuration(Duration.millis(8000));
		p2.setCycleCount(Timeline.INDEFINITE);
		p2.play();
		//third animated target
		Circle c1=new Circle(185,325,5);	//target worth 90
		c1.setFill(Color.DEEPPINK);
		handleEvent(c1, player1, player2,90,scorebrd,Score, turnsLeft, root);
		root.getChildren().add(c1);
		Path path2 = new Path();
		path2.getElements().add(new MoveTo(175,250));
		path2.getElements().add(new QuadCurveTo( 217, 125, 250, 250));
		path2.getElements().add(new QuadCurveTo( 288, 325, 325, 250));
		path2.getElements().add(new QuadCurveTo( 288, 125, 250, 250));
		path2.getElements().add(new QuadCurveTo( 217, 325, 175, 250));
		PathTransition p3= new PathTransition();
		p3.setPath(path2);
		p3.setNode(c1);
		p3.setInterpolator(Interpolator.LINEAR);
		p3.setDuration(Duration.millis(7500));
		p3.setCycleCount(Timeline.INDEFINITE);
		p3.play();
		//fourth target
		Circle c2=new Circle(250,120,5); //target worth 100
		c2.setFill(Color.GOLD);
		handleEvent(c2, player1, player2,100,scorebrd,Score, turnsLeft, root);
		root.getChildren().add(c2);
		PathTransition p4=CreateCircleTrans(250,120,250.2269,120.0002,130,5000,c2);
		p4.play();
		// quit and restart buttons
		Button quit=new Button("Quit");
		quit.setLayoutX(scene.getWidth()-38);
		quit.setLayoutY(scene.getHeight()-25);
		root.getChildren().add(quit);
		quit.addEventHandler(MouseEvent.MOUSE_PRESSED, 
			    new EventHandler<MouseEvent>() {
			        @Override
			        public void handle(MouseEvent e) {  
			            System.exit(0);}});
		Button restart=new Button("Restart");
		restart.setLayoutX(scene.getWidth()-91);
		restart.setLayoutY(scene.getHeight()-25);
		root.getChildren().add(restart);
		restart.addEventHandler(MouseEvent.MOUSE_PRESSED, 
			    new EventHandler<MouseEvent>() {
			        @Override
			        public void handle(MouseEvent e) {  
			            start(primaryStage);}});
		primaryStage.setScene(scene);
		primaryStage.show();

	}
	public PathTransition CreateCircleTrans(int startx,int starty,double endx,double endy,int radius,int time,Node n){
		Path path=new Path();
		MoveTo moveTo = new MoveTo(startx,starty);
		ArcTo arcTo = new ArcTo();
		arcTo.setX(endx);
		arcTo.setY(endy);
		arcTo.setRadiusX(radius);
		arcTo.setRadiusY(radius);
		arcTo.setLargeArcFlag(true);
		path.getElements().addAll(moveTo,arcTo);
		PathTransition pathTransition = new PathTransition();
		pathTransition.setDuration(Duration.millis(time));
		pathTransition.setPath(path);
		pathTransition.setCycleCount(Timeline.INDEFINITE);
		pathTransition.setInterpolator(Interpolator.LINEAR);
		pathTransition.setNode(n);
		pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
		return pathTransition;
	}
	public Shape CreateLayer(double x, double y, double a, double b){
		Arc a1=new Arc(250,250,x,y,a,b);
		a1.setType(ArcType.ROUND);
		Arc a2=new Arc(250,250,x-10,y-10,a,b);
		a2.setType(ArcType.ROUND);
		Shape s=Shape.subtract(a1, a2);
		return s;		
	}
	public Shape CreateSecondLayer(double x,double y,double a,double b){
		Arc a1=new Arc(250,250,x,y,a,b);
		a1.setType(ArcType.ROUND);
		Arc a2=new Arc(250,250,x-30,y-30,a,b);
		a2.setType(ArcType.ROUND);
		Shape s=Shape.subtract(a1, a2);
		return s;	
	}
	public Text BuildTextRing(double c, double d, String DartboardNumber){
		Text t = new Text(c,d,DartboardNumber);
		t.setFont(new Font(20));
		t.setFill(Color.WHITE);
		return t;	
	}
	public void showThrow(DartsPlayer a,int num, Group root){
		Text str=new Text(140,400,a.getName()+" scored "+num+" on throw "+(a.getTurnNumber()));
		str.setFill(Color.NAVY);
		str.setFont(Font.font("Tahoma",FontWeight.BOLD,16));
		root.getChildren().add(str);
		Timeline timeline = new Timeline();
		EventHandler onFinished = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	root.getChildren().remove(str);
            }};
        KeyFrame keyFrame = new KeyFrame(Duration.millis(1500), onFinished);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();

	}
	public void itsYourTurn(DartsPlayer a,Group root){
		Text str=new Text(175,420,"It's "+a.getName()+"'s turn.");
		str.setFill(Color.NAVY);
		str.setFont(Font.font("Tahoma",FontWeight.BOLD,16));
		root.getChildren().add(str);
		Timeline timeline = new Timeline();
		EventHandler onFinished = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	root.getChildren().remove(str);
            }};
        KeyFrame keyFrame = new KeyFrame(Duration.millis(2000), onFinished);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
	}
	public void handleEvent(Node s, DartsPlayer a, DartsPlayer b,int num,Canvas scorebrd,GraphicsContext t1, Text t2, Group root){
		s.addEventHandler(MouseEvent.MOUSE_PRESSED, 
			    new EventHandler<MouseEvent>() {
			        @Override
			        public void handle(MouseEvent e) {  
			            ThrowDart(a,b,num,root);
			            t1.setFill(Color.BLACK);
			            t1.fillRect(10,40,140,30);
			            t1.setFill(Color.WHITE);
			            t1.setFont(Font.font("Tahoma",FontWeight.BOLD,20));
			            t1.fillText(a.getScore()+" - "+b.getScore(),Math.round(scorebrd.getWidth()/2), Math.round(scorebrd.getHeight()/2));
			            t2.setText(11-a.getTurnNumber()+"             "+(11-b.getTurnNumber()));}});
	}
	public void ThrowDart(DartsPlayer a,DartsPlayer b,int num,Group root){
		if(!a.isOver()){
			if(a.isTurn()){
				a.addScore(num);
				showThrow(a,num,root);
				a.incrementTurn();
				if(!a.isTurn()){
					b.switchTurn();
					itsYourTurn(b,root);}
			}
			else{
				b.addScore(num);
				showThrow(b,num,root);
				b.incrementTurn();
				if(!b.isTurn()){
					a.switchTurn();
					itsYourTurn(a,root);}
			}
		}
		else{
			if(!b.isOver()){
				b.addScore(num);
				showThrow(b,num,root);
				b.incrementTurn();
				gameOver(a,b,root);
			}
		}
			
	}
	public void gameOver(DartsPlayer a, DartsPlayer b,Group root){
		if(a.getScore()>b.getScore()){
			Text aWins=new Text(110,440,a.getName()+" wins! The score is "+a.getScore()+" to "+b.getScore());
			aWins.setFill(Color.NAVY);
			aWins.setFont(Font.font("Tahoma",FontWeight.BOLD,16));
			root.getChildren().add(aWins);
		}
		else if(a.getScore()==b.getScore()){
			Text tieGame=new Text(200,440,"Tie game!!");
			tieGame.setFill(Color.NAVY);
			tieGame.setFont(Font.font("Tahoma",FontWeight.BOLD,20));
			root.getChildren().add(tieGame);
		}
		else{
			Text bWins=new Text(110,440,b.getName()+" wins! The score is "+b.getScore()+" to "+a.getScore());
			bWins.setFill(Color.NAVY);
			bWins.setFont(Font.font("Tahoma",FontWeight.BOLD,16));
			root.getChildren().add(bWins);
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}