import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*  I used a multiple class strategy. One class 'word' accepts and stores the words from the text file. Another class, 'wordComparison', does all the heavy lifting.
    It reads the file and stores all the words in a variable initialized using the 'word' class. From there, a secret word is generated, which is then compared to the user's guess (after it is validated).
    The user's guess and comparison results are stored for later use (for the history). These are then implemented in the main, wordle class. The user input is accepted and then the necessary methods are called.

*/


public class wordle {

    public static Scanner input = new Scanner(System.in);

    public static void rules() {
        /* This method prints the rules of the game */
        System.out.print("#########################################################\n" +
                "# Let’s play Wordle.                                    #\n" +
                "# Your goal is to guess a secret word.                  #\n" +
                "# The word may have duplicated letters.                 #\n" +
                "# For each guess, you receive a feedback.               #\n" +
                "# ’H’ for hits, ’m’ for miss, and ’-’ for others.       #\n" +
                "# Your commands are as follows:                         #\n" +
                "#   s for showing the secret,                           #\n" +
                "#   h for show the history, and                         #\n" +
                "#   g for giving up and terminating the present puzzle. #\n" +
                "#########################################################\n");

        System.out.print("Choose the length for secret, here: ");
        int holder = input.nextInt();

        System.out.println("Enter your guess below:");
    }


    public static void main(String arg []){

        wordComparison play = new wordComparison(); // instantiates the wordComparison class

        File f = new File("words.txt"); // reads the words from the text file

        play.addFromFile(f); // creates an array of words, using the words in the file

        boolean runner  = true; // boolean variable that is used to support the while loop

        String tester = "y"; // helper variable to ensure that the intro method only run when a new game is being played

        while (runner){

            if (tester.equals("y")){ //

                rules();

                play.generateSecret();

            }

            tester = "n"; // ensures that the above loop is not run, until a new game is being played

            boolean valid = true;

            String user; // instantiates the variable that will be used to store the user's input

            do{

                System.out.print(">");

                user = input.next();

                valid = play.valid_guess(user); // calls the valid_guess method to ensure that it follows the rules of the game: 1) length = 6 and 2) the word appears on the list. If so, it returns false, which breaks out of the loop


            } while(valid);

            if (user.equals("g")){ // checks to see if command was invoked

                    play.history();
                    play.secret();
                    System.out.println("Thanks for playing!");
                    break;

            } else if (user.equals("s")){ // checks to see if command was invoked

                    play.secret();
                    continue;

            } else if (user.equals("h")){ // checks to see if command was invoked

                    play.history();
                    continue;

            }

            play.compare(user); // invokes the compare method, that tests for hits, misses, or neither and prints the result

            if (play.getHits() == 6){ // checks to see if the user won the game

                System.out.println("You've got it!");
                play.history();
                System.out.print("Another game (y for yes and n for no)? ");
                String response = input.next();

                if (response.equals("y")){

                    tester = "y"; // ensures that when the loop reruns, the intro and generate_secret methods are invoked


                } else {

                        runner = false; // breaks out of the loop and ends the game if the user enters "n"

                }
            }

        }

    }

}



