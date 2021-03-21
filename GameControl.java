package simple21;
// import simple21.HumanPlayer;
// import simple21.ComputerPlayer;

import java.util.Scanner;
import java.util.Random;

/**
 * This is a simplified version of a common card game, "21". 
 */
public class GameControl {
    
	/**
	 * Human player.
	 */
    HumanPlayer human;
    
    /**
     * Computer player.
     */
    ComputerPlayer player1;
    
    /**
     * Computer player.
     */
    ComputerPlayer player2;
    
    /**
     * Computer player.
     */
    ComputerPlayer player3;
    
    /** 
     * A random number generator to be used for returning random "cards" in a card deck.
     * */
    Random random = new Random();
      
    /**
     * The main method just creates a GameControl object and calls its run method.
     * @param args Not used.
     */
    public static void main(String args[]) {    
        new GameControl().run();
    }
    
    /**
     * Prints a welcome method, then calls methods to perform each of the following actions:
     * - Create the players (one of them a Human)
     * - Deal the initial two cards to each player
     * - Control the play of the game
     * - Print the final results
     */
    public void run() {
    	
        Scanner scanner = new Scanner(System.in);
        
        // Students: your code goes here.
        System.out.println("Welcome to simple 21!");
        System.out.println("You will play with 3 other players (computers)");
    	System.out.println("Try to get as close to 21 as possible, without going over.");
        
        String humansName = scanner.next();

        this.createPlayers(humansName);
        this.deal();
        this.controlPlay(scanner);
        this.printResults();

        scanner.close();
    }
    
    /**
     * Creates one human player with the given humansName, and three computer players with hard-coded names.
     * @param humansName for human player
     */
    public void createPlayers(String humansName) {
       // Students: your code goes here.
       // Create human player
       this.human = new HumanPlayer(humansName);

       // Create human player1, player 2, player 3
       this.player1 = new ComputerPlayer("Player 1");
       this.player2 = new ComputerPlayer("Player 2");
       this.player3 = new ComputerPlayer("Player 3");
      
    }
    
    /**
     * Deals two "cards" to each player, one hidden, so that only the player who gets it knows what it is, 
     * and one face up, so that everyone can see it. (Actually, what the other players see is the total 
     * of each other player's cards, not the individual cards.)
     */
    public void deal() { 
        // Students: your code goes here.
    	human.takeVisibleCard(nextCard());
        human.takeHiddenCard(nextCard());

        player1.takeVisibleCard(nextCard());
        player1.takeHiddenCard(nextCard());
        
        player2.takeVisibleCard(nextCard());
        player2.takeHiddenCard(nextCard());

        player3.takeVisibleCard(nextCard());
        player3.takeHiddenCard(nextCard());

    }
    
    /**
     * Returns a random "card", represented by an integer between 1 and 10, inclusive. 
     * The odds of returning a 10 are four times as likely as any other value (because in an actual
     * deck of cards, 10, Jack, Queen, and King all count as 10).
     * 
     * Note: The java.util package contains a Random class, which is perfect for generating random numbers.
     * @return a random integer in the range 1 - 10.
     */
    public int nextCard() { 
    	// Students: your code goes here.
    	int randomInt = random.nextInt(13) + 1;
        if(randomInt >= 11){
            randomInt = 10;
        }
        return randomInt;
    }

    /**
     * Gives each player in turn a chance to take a card, until all players have passed. Prints a message when 
     * a player passes. Once a player has passed, that player is not given another chance to take a card.
     * @param scanner to use for user input
     */
    public void controlPlay(Scanner scanner) { 
        // Students: your code goes here.

        while(!checkAllPlayersHavePassed()){
            if(!human.passed){
                if(human.offerCard(human, player1, player2, player3, scanner)){
                    human.takeVisibleCard(nextCard());
                }else{
                    System.out.println(human.name + " passes." );
                }
            }

            if(!player1.passed){
                if(player1.offerCard(human, player1, player2, player3)){
                    player1.takeVisibleCard(nextCard());
                }else{
                    System.out.println(player1.name + " passes." );
                }
            }

            if(!player2.passed){
                if(player2.offerCard(human, player1, player2, player3)){
                    player2.takeVisibleCard(nextCard());
                }else{
                    System.out.println(player2.name + " passes." );
                }
            }

            if(!player3.passed){
                if(player3.offerCard(human, player1, player2, player3)){
                    player3.takeVisibleCard(nextCard());
                }else{
                    System.out.println(player3.name + " passes." );
                }
            }
        }
    	
    }
     
    /**
     * Checks if all players have passed.
     * @return true if all players have passed
     */
    public boolean checkAllPlayersHavePassed() {
    	// Students: your code goes here.
        // use && operator to show only all the players pass, will it return true;
    	return human.passed && player1.passed && player2.passed && player3.passed;
    }
    
    /**
     * Prints a summary at the end of the game.
     * Displays how many points each player had, and if applicable, who won.
     */
    public void printResults() { 
        // Students: your code goes here.
    	System.out.println();
        System.out.println("Game Over.");

        System.out.println(human.name + " has " + human.getScore() + " total points");
        System.out.println(player1.name + " has " + player1.getScore() + " total points");
        System.out.println(player2.name + " has " + player2.getScore() + " total points");
        System.out.println(player3.name + " has " + player3.getScore() + " total points");

        printWinner();
    }

    /**
     * Determines who won the game, and prints the results.
     */
    public void printWinner() { 
        // Students: your code goes here.
    	boolean tie = false;
        int maxScore = 0;
        int winner = -1;

        int humanScore = human.getScore();
        int player1Score = player1.getScore();
        int player2Score = player2.getScore();
        int player3Score = player3.getScore();

        if(humanScore <= 21){
            maxScore = humanScore;
            winner = 0;
        }

        if(player1Score <= 21 && player1Score >= humanScore){
            if( player1Score == humanScore){
                tie = true;
            }
            else{
                maxScore = player1Score;
                winner = 1;
            }
        }

        if(player2Score <= 21 && player2Score >= maxScore){
            if( player2Score == maxScore){
                tie = true;        
            }
            else{
                maxScore = player2Score;
                winner = 2;
            }
        }

        if(player3Score <= 21 && player3Score >= maxScore){
            if( player3Score == maxScore){
                tie = true;        
            }
            else{
                maxScore = player3Score;
                winner = 3;
            }
        }

        if(tie) System.out.println("It's a tie. Nobody wins");
        else if (winner == -1) System.out.println("Nobody wins");
        else{
            switch(winner){
                case 0:
                    System.out.println(human.name + " wins with " + human.getScore() + " points");
                    break;

                case 1:
                    System.out.println(player1.name + " wins with " + player1.getScore() + " points");
                    break;

                case 2:
                    System.out.println(player2.name + " wins with " + player2.getScore() + " points");
                    break;
                
                case 3:
                    System.out.println(player3.name + " wins with " + player3.getScore() + " points");
                    break;
            }
        }
    }
}
