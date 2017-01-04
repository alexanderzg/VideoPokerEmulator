
package videopoker;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Application; import javafx.geometry.Pos;

/**
 * Class VideoPoker creates the GUI for the Video Poker game
 * It extends the Apllication class.
 **/
public class VideoPoker extends Application {
    
    //Object instantiation
    TextField dispGameRes = new TextField();     
    TextField dispBot1 = new TextField();
    TextField dispBot2 = new TextField();
    TextField dispBot3 = new TextField();
    TextArea dispTop1  = new TextArea();
    TextArea dispTop2  = new TextArea();
    TextArea dispTop3  = new TextArea();
    TextArea dispTop4  = new TextArea();
    TextArea dispTop5  = new TextArea();
    TextArea dispTop6  = new TextArea();
    Button   cardBtn;
    Poker    engine    = new Poker();
    Button   btnDeal;
    Button   cardBack;
    Button   btnCheck;
    
    //Variable declaration
    int[]    currHand  = new int[5];
    int[]    currHand2 = new int[5];
    String[] setId = {"0","1","2","3","4"};
    String[] getId = new String[3];
    int k = 0;
    int mouseCount;
    int p;
    int temp;
    
    /**
     * @param primaryStage initial view
     **/
    @Override
    public void start(Stage primaryStage) {
        
        //Object instantiation
        HBox        hBox1          = new HBox();
        HBox        hBox2          = new HBox();
        HBox        hBox3          = new HBox();
        HBox        model          = new HBox();
        VBox        mainVert       = new VBox();
        
        //Aligning position of dipGameRes
        dispGameRes.setAlignment(Pos.CENTER);
        
        //URL and styling information
        String cardURL = "http://cim.saddleback.edu/casino/cardMedium/";
        String cardBackUrl ="http://cim.saddleback.edu/casino/card/b2fv.png";
        
        //Set style of top list of winning hands
        String dispTopStyle = "-fx-font: 15 arial; -fx-background-color: #00BFFF; -fx-background-radius: 0; -fx-border-color: black; -fx-border-width: 3; -fx-text-fill: #B22222;";
        String backColor = "-fx-background-color: #00BFFF;";
        
        //Sizing primary stage and card model
        model.setPrefHeight(150);
        model.setPrefWidth(700);
        primaryStage.setResizable(false);

        //Adds nodes to hBox1
        hBox1.getChildren().add(dispTop1);
        hBox1.getChildren().add(dispTop2);
        hBox1.getChildren().add(dispTop3);
        hBox1.getChildren().add(dispTop4);
        hBox1.getChildren().add(dispTop5);
        hBox1.getChildren().add(dispTop6);
        
        //Styles hBox1 hBox2
        hBox1.setStyle(backColor);
        hBox2.setStyle(backColor);
        
        //Set style of top list content of winning hands
        //Left most top panel
        dispTop1.setStyle(dispTopStyle);
        dispTop1.setEditable(false);
        dispTop1.setPrefSize(300, 180);  
        dispTop1.appendText("ROYAL FLUSH\nSTRAIGHT FLUSH\nFOUR OF A KIND\nFULL HOUSE\nFLUSH\nSTRAIGHT\nTHREE OF A KIND\nTWO PAIR\nJACK OR BETTER");
        
        //Left second most top panel
        dispTop2.setStyle(dispTopStyle);
        dispTop2.setEditable(false);
        dispTop2.setPrefSize(200, 180);
        dispTop2.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        dispTop2.appendText("250\n50\n25\n9\n6\n4\n3\n2\n1");

        //Left third most top panel
        dispTop3.setStyle(dispTopStyle);
        dispTop3.setEditable(false);
        dispTop3.setPrefSize(200, 180);
        dispTop3.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        dispTop3.appendText("500\n100\n50\n18\n12\n8\n6\n4\n2");
        
        //Right third most top panel
        dispTop4.setStyle(dispTopStyle);
        dispTop4.setEditable(false);
        dispTop4.setPrefSize(200, 180);
        dispTop4.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        dispTop4.appendText("750\n150\n75\n27\n18\n12\n9\n6\n3");
        
        //Right second most top panel
        dispTop5.setStyle(dispTopStyle);
        dispTop5.setEditable(false);
        dispTop5.setPrefSize(200, 180); 
        dispTop5.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        dispTop5.appendText("1000\n200\n100\n36\n24\n16\n12\n8\n4");
        
        //Right most top panel
        dispTop6.setStyle(dispTopStyle);
        dispTop6.setEditable(false);
        dispTop6.setPrefSize(200, 180);
        dispTop6.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        dispTop6.appendText("4000\n250\n125\n45\n30\n20\n15\n10\n5");
        
        //Set style of game hand result
        dispGameRes.setStyle("-fx-font: 20 arial; -fx-background-color: #1E90FF; -fx-text-fill: yellow;");
        dispGameRes.setPrefHeight(60);
        dispGameRes.setEditable(false);
       
        //Adds nodes to hBox2
        hBox2.getChildren().add(dispBot1);
        hBox2.getChildren().add(dispBot2);
        hBox2.getChildren().add(dispBot3);
        
        //Set style of hBox3
        hBox3.setPrefHeight(66);
        hBox3.setStyle("-fx-background-color: #1E90FF;");
        hBox3.setPadding(new Insets(5, 5, 5, 5));
        hBox3.setSpacing(20);
        
        //Add back card images in beginning
        for(int k = 0; k < 5; k++){
            cardBack = new Button("", new ImageView(new Image(cardBackUrl,108 ,144, false, false)));
            model.getChildren().add(cardBack);
        }
            //Create an styles deal button
            btnDeal = new Button("DEAL");            
            hBox3.getChildren().add(btnDeal);
            btnDeal.setStyle("-fx-background-color: #FFD700;");
            btnDeal.setMinSize(86, 40);
            
            //Handles events when deal button is pressed
            btnDeal.setOnAction(e->{
                model.getChildren().clear();
                dispGameRes.clear();
                mouseCount = 0;
                dispGameRes.appendText("Click the cards you want to swap - Up to three cards");
                currHand = engine.shuffle();
                
                //Creates 5 cards
                for(int i =0; i <5; i++){
                    cardBtn = new Button("", new ImageView( new Image(cardURL + currHand[i] + ".png")));
                    model.getChildren().add(cardBtn);
                    cardBtn.setId(setId[i]);
                    
                    //Handles button connection with the event
                    cardBtn.setOnAction(new EventHandler<ActionEvent>(){
                        
                         //Handles the cards been selected to replace   
                        @Override
                        public void handle(ActionEvent event){
                            if(mouseCount <= 2){
                                p = engine.pickCard();
                                Button b =(Button)event.getSource();
                                temp = Integer.parseInt(b.getId());
                                b.setGraphic(
                                      new ImageView( new Image( cardURL + p +".png"))                           
                                );
                                b.setStyle("-fx-background-color: yellow;");
                                b.setOnMouseClicked(eventMouse ->{
                                   mouseCount += 1;
                                });
                            }
                            currHand[temp] = p;   
                        }
                    });
                }
                btnDeal.setVisible(false);
                btnCheck.setVisible(true);
            });
            
            //Creation and styling of btnCheck
            btnCheck = new Button("CHECK PLAY");
            btnCheck.setVisible(false);
            btnCheck.setStyle("-fx-background-color: #FFD700;");
            hBox3.getChildren().add(btnCheck);
            btnCheck.setMinSize(86, 40);
        
            //Handles the btnCheck event
            btnCheck.setOnAction(ex->{
                btnCheck.setVisible(false);
                btnDeal.setVisible(true);
                dispGameRes.clear();
                dispGameRes.appendText(engine.checkWinnerHand(currHand));
            });

        //DispBot styling
        dispBot1.setPrefSize(210, 50);
        dispBot1.setStyle(backColor);
        dispBot2.setPrefSize(210, 50);
        dispBot2.setStyle(backColor);
        dispBot3.setPrefSize(210, 50);
        dispBot3.setStyle(backColor);
        
        //Adds nodes into the mainVert
        mainVert.getChildren().add(hBox1);      
        mainVert.getChildren().add(dispGameRes);
        mainVert.getChildren().add(model);
        mainVert.getChildren().add(hBox2);
        mainVert.getChildren().add(hBox3);
        
        //Adds mainVert into Scene
        Scene scene = new Scene(mainVert, 630, 510);
        
        //Adds scene into primaryStage
        primaryStage.setTitle("Video Poker");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        //Handles inner content styling in textarea disptop
        Region region = ( Region ) dispTop1.lookup( ".content" );
        region.setStyle(backColor);       
        region = ( Region ) dispTop2.lookup( ".content" );
        region.setStyle(backColor);       
        region = ( Region ) dispTop3.lookup( ".content" );
        region.setStyle(backColor);       
        region = ( Region ) dispTop4.lookup( ".content" );
        region.setStyle(backColor);       
        region = ( Region ) dispTop5.lookup( ".content" );
        region.setStyle(backColor);       
        region = ( Region ) dispTop6.lookup( ".content" );
        region.setStyle(backColor);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
