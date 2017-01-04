package videopoker;

import java.util.Arrays;
import java.util.Scanner;

/**
 * This program emulates a poker card game. It will receiv five
 * integers representing five cards dealt. It will then determine
 * the type of hand the user has in order to win the game.
 **/
public class Poker {
    
    //Variable declaration
    int[]deck;
    boolean newDeckFlag = true;
    int currDeckSize = 52; 
    
    /**
     * Poker default constructor
     **/
    public Poker(){
    }        

    /**
     * main method
     * tests the poker class
     * @param args the command line arguments
     **/
    public static void main(String[]args){
        Poker newGame = new Poker();
        int[]temp = new int[5];
        Scanner input = new Scanner(System.in);
        
        while(input.hasNext()&& input.nextInt()!= 1){
        temp = newGame.shuffle();
            for(int d = 0; d < temp.length; d++){
                System.out.println("Temp d: " + temp[d]);
            }
        }
        
    }
    
    /**
     * shuffle method
     * It shuffles available cards for the next play
     * @return next hand to play
     **/
    public int[] shuffle() {
        //Variables
        int[] deckTemp = new int[currDeckSize];
        int[] nextHand = new int[5];
        boolean repeatCardFlag = false;
        int c = 0;
        int g = 0;
        
        //Creates new deck and update current deck
        if(newDeckFlag){
            deck = new int[currDeckSize];
            newDeckFlag = false;
            for(int a = 1; a < 53; a++){
                deck[a-1] = a;           
            }
        }else{
            for(int f = 0; f < deck.length; f++){
                if(deck[f] != 0){
                    deckTemp[g] = deck[f];
                    g++;
                }
            }
            deck = new int[currDeckSize];
            System.arraycopy(deckTemp, 0, deck , 0, deckTemp.length);
        }
        
        //Deals 5 cards without repeating the cards and from only available cards
        while(c < 5){
            int card = (int)Math.ceil(Math.random()*52.0);
            for(int h = 0; h < deck.length; h++){
                if(card == deck[h]){
                    for(int b = 0; b < nextHand.length; b++){
                        if(card == nextHand[b]){
                            repeatCardFlag = true;
                        }
                    }           
                    if(!repeatCardFlag){
                        nextHand[c] = card;
                        c++;
                    } 
                    repeatCardFlag = false;
                }
            }
        }       
        //Mark the cards that were used for deletion
        for(int d = 0; d < deck.length; d++){
            for(int e = 0; e < 5; e++){
                if(nextHand[e] == deck[d]) {
                    deck[d] = 0;
                }
            }
        }       
        //updates deck for new deck when needed
        currDeckSize -=5;
        if(currDeckSize < 5){
            currDeckSize = 52;
            newDeckFlag = true;
        }

        return nextHand;
    }
    /**
     * pickCard method
     * It deals one unused card from the deck
     * @return the card picked
     **/
    public int pickCard() {
        //Variables
        int cardPicked = 0;
        int c = 0;
        while(c < 1){
            int card = (int)Math.ceil(Math.random()*52.0);
            for(int h = 0; h < deck.length; h++){
                if(card == deck[h]){
                    cardPicked = card;
                    c++;
                } 
            }
            
        //Mark the cards that were used for deletion
        for(int d = 0; d < deck.length; d++){
            if(cardPicked == deck[d]) {
                deck[d] = 0;
            }
        }
         
        }
        currDeckSize--;
        if(currDeckSize < 5){
            currDeckSize = 52;
            newDeckFlag = true;
        }
   
        return cardPicked;
    }
    
    /**
     * getSuit method
     * Assigns the appropriate suit to a card
     * @param a card number
     * @return the card suit
     **/
    public static String getSuit(int n){
        int temp = (n-1)/13;
        String[] suits = {"Spades","Hearts","Diamonds","Clubs"};
        return suits[temp];       
    }
    
    /**
     * getRank method
     * Assigns the appropriate rank to a card
     * @param a card number
     * @return the card rank
     **/
    public static String getRank(int n){
        int temp = (n-1)%13;
        String[] ranks = {"Ace", "Deuce", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"}; 
        return ranks[temp];
    }
    
    /**
     * isFlush method
     * Determines if the given hand matches a flush hand
     * @param a hand of cards
     * @return a boolean value
     **/
    public static boolean isFlush(int[]n){
        int current =0;
        int prev = (n[0]-1)/13;
        //Checks for cards with same suit
        for(int l=0; l<n.length;l++){
            current = (n[l]-1)/13;
            if(current != prev){
                return false;
            }
            prev = current;
        }       
        return true;
    }
    
    /**
     * isStraight method
     * Determines if the given hand matches a straight hand
     * @param a hand of cards
     * @return a boolean value
     **/
    public static boolean isStraight(int[]n){
        
        int currOut=0;
        int currInner=0;
        int[]temp = new int[n.length];
        //Arrange input to card numbers
        for(int l=0; l<n.length;l++)
            temp[l] = ((n[l]-1)%13)+1;
        //Sorts card numbers      
        Arrays.sort(temp);
        int prev = temp[0]-1;
        //Check consecutive numbers
        for(int l=0; l<temp.length;l++){
            currOut = temp[l];
            if(currOut != prev+1){
                int prevInner = temp[1]-1;
                //When fails checks for Ace exception
                if(temp[0]!= 1){
                    return false;
                }else if(temp[4]!= 13){
                    return false;
                }
                //Check again for consecutive numbers for Ace exception
                for(int k=1; k<temp.length;k++){
                    currInner = temp[k];
                    if(currInner != prevInner+1){
                        return false;
                    }
                    prevInner = currInner;
                }
                return true;
            }
            prev = currOut;
        }       
        return true;
    }
    
    /**
     * pairsAndFullHouse method
     * Determines if the given hand matches a pair or a full house
     * @param a hand of cards
     * @return an integer value representing a pair or a full house play
     **/
    public static int pairsAndFullHouse(int[]n){
        int[]temp = new int[5];
        int current = 0;
        int counter = 0;
        //Arrange input to card numbers
        for(int l=0; l<n.length;l++)
            temp[l] = ((n[l]-1)%13)+1;
        //Sorts card numbers      
        Arrays.sort(temp);
        
        int prev = temp[0];
        
        for(int l=0; l<temp.length;l++){
            current = temp[l];
            if(current == prev){
                counter++;
            }
            prev = current;
        }                     
        return (counter<=1||counter>4? 0: counter-1);
    }
    /**
     * threeFourOfKind method
     * Determines if the given hand matches a pair or a full house
     * @param a hand of cards
     * @return an integer value representing a three of a kind play
     **/
    public static int threeFourOfKind(int[]n){
        int[]temp = new int[5];
        int counter = 2;
        int current = 0;
        int prev = 0;
        //Arrange input to card numbers
        for(int l=0; l<n.length;l++)
            temp[l] = ((n[l]-1)%13)+1;
        //Sorts card numbers      
        Arrays.sort(temp);
        int onePrev = temp[0];
        prev = temp[1];
        //Checks for matching cards
        for(int l=2; l<temp.length;l++){
            current = temp[l];            
            if(current == prev && current == onePrev){
                counter++;
            }
            onePrev = prev;
            prev = current;           
        }
        return (counter<3||counter==5? 0:counter);
    }
    
    /**
     * getHighCard method
     * Determines the highest card
     * @param a hand of cards
     * @return a string representation of the highest cards' rank and suit
     **/
    public static String getHighCard(int[]n){
        String str = "";
        int[]temp = new int[5];
        int biggest = 0;
        int challenger = 0;
        int index = 0;
        for(int l=0; l<n.length;l++)
            temp[l] = ((n[l]-1)%13)+1;
        //Sorts card numbers      
        biggest = temp[0];
        
        if(biggest == 1){
            return getRank(biggest) + " of " + getSuit(n[index]) + " High";
        }
        //Checks for the highest card
        for(int l=1; l<temp.length;l++){
            challenger = temp[l];
            if(challenger > biggest){
                biggest = challenger;
                index = l;
            }
            if(challenger == 1){
                return getRank(challenger) + " of " + getSuit(n[l]) + " High";
            }
        }
        return getRank(biggest) + " of " + getSuit(n[index]) + " High";
    }
    
    /**
     * isRoyalFlush method
     * Determines if the given hand matches a royal flush hand
     * @param a hand of cards
     * @return a boolean value
     **/
    public static boolean isRoyalFlush(int[]n){
        int[]temp = new int[5];
        for(int l=0; l<n.length;l++)
            temp[l] = ((n[l]-1)%13)+1;
        //Sorts card numbers      
        Arrays.sort(temp);
        if(isFlush(n) && temp[0]== 1 
        && temp[1]== 10 && temp[2]== 11 && temp[3]== 12 && temp[4]== 13){
            return true;
        }
        return false;
    }
    
    /**
     * checkWinnerHand method
     * Determines what type of winner hand the user has
     * @param a hand of cards
     * @return a string representation of his winner hand
     **/
    public static String checkWinnerHand(int[]temp){        
        String str = "";
        //Determines what kinf od winner hand the user has
        if(isRoyalFlush(temp)){
            str = "Royal Flush";
        }else if(isFlush(temp) && isStraight(temp)){
            str = "Straight Flush";
        }else if(threeFourOfKind(temp)==4){
            str = "Four of a Kind";
        }else if(pairsAndFullHouse(temp)==3){
            str = "Full House";            
        }else if(isFlush(temp)){
            str = "Flush";           
        }else if(isStraight(temp)){
            str = "Straight";
        }else if(threeFourOfKind(temp)==3){
            str = "Three of a Kind";
        }else if(pairsAndFullHouse(temp)==2){
            str = "Two Pair";
        }else if(pairsAndFullHouse(temp)==1){
            str = "One Pair";
        }else{
            str = getHighCard(temp);
        }                  
        return str;
    }
}
