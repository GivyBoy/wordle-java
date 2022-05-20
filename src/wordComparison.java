import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;
import java.util.StringJoiner;

public class wordComparison {

    private word[] words; // array of all the words
    private String[] user_guess; // holds the guesses of the user
    private word[] results; // stores the hits or misses of the user
    private word[] used_words; // stores the words that have already been used
    private String secret; // stores the generated guess
    private  int hits; // stores the number of hits
    private String[] progress_history; // stores the history of the hits and misses


    public wordComparison(){
        /* constructor */

        words = new word[0];
        used_words = new word[0];
        user_guess = new String[0];
        results = new word[0];
        progress_history = new String[0];
        hits = 0;

    }


    public int size_words() {
        return words.length;
    }

    public int size_history(){
        return progress_history.length;
    }

    public int getHits() {
        return hits;
    }

    public String getSecret(){

        return secret;

    }

    public boolean valid_guess(String user){

        /* Checks if the user entered a one letter command, or if the word has 6 letters and is present in the word list*/

        if (user.equals("s") || user.equals("g") || user.equals("h")){

            return false;

        }

        if (user.length() == 6){

            for (int i = 0; i < size_words(); i++){

                if (user.equals(words[i].getWord())){

                    return false;

                }

            }

        }

        System.out.println("Invalid input.");

        return true;

    }

    public void addFromFile(File f){

        /* adds all the words from the text file to an array, using the word class */

        Scanner scanner;

        try{

            scanner = new Scanner(f);

        } catch (FileNotFoundException e) {

            System.out.println("***File Not Found***");
            return;
        }

        int count = Integer.parseInt(scanner.nextLine());

        word[] temp = Arrays.copyOf(words, size_words() + count); // creates a temporary array to hold all the words in the text file

        for (int i = size_words(); i < size_words() + count; i++) {

            temp[i] = new word(scanner.nextLine());

        }

        scanner.close();

        words = Arrays.copyOf(temp, size_words() + count); // updates the array to hold all the words from the text file

    }

    public void generateSecret(){

        /* generates the secret by indexing the word array, using a random number*/
        progress_history = new String[0]; // ensures that the history 'refreshes' every time a new secret word is generated

        Random random = new Random();

        int rand = random.nextInt(0, size_words()); // generates a random integer between 0 and the number of words in the word array

        secret = words[rand].getWord();

    }

    public void compare(String user){

        hits = 0;
        results = new word[0];

        String[] temp_user = Arrays.copyOf(user_guess, size_history() + 1); // creates a temporary array to store the guesses of the user
        temp_user[size_history()] = user; // sets the last element in the array to the value of the current guess

        user_guess = Arrays.copyOf(temp_user, size_history() + 1); // copies the updated values into the designated array


        word[] temp_result = Arrays.copyOf(results, user.length()+1); // initializes an array to hold the results (hits, misses, or dashes


        char[] ar_secret = secret.toCharArray(); // converts the secret word to a char array
        char[] ar_user = user.toCharArray(); // converts the user's guess to a char array

        boolean[] ar_test = new boolean[6]; // initializes a boolean array

        for(int i = 0; i < ar_secret.length; i++){

            ar_test[i]= true;
        } // set all the values of the boolean array to true

        for(int i = 0; i < ar_secret.length; i++){

            boolean temp = (ar_secret[i] == ar_user[i]); // checks if the current letter is a hit

            for (int j = 0; j < ar_secret.length; j++){

                if (ar_user[i] == ar_user[j] && temp){ // Checks to see if the letter at i is a hit and is equal to the letter at j. If this is the case, it updates the value in ar_test to false. This will ensure that hit and miss are not registered simultaneously

                    ar_test[j] = false;

                }

            }

        }

        word tester = new word("H");

        for (int i = 0; i < ar_secret.length; i++) {

            for (int j = 0; j < ar_secret.length; j++) {

                if (i==j && ar_secret[i]==ar_user[j]){ // checks for a hit

                    temp_result[j] = new word("H");
                    hits++;

                } else if(i!=j && ar_secret[i] == ar_user[j] && (temp_result[j] != tester) && ar_test[j]){ // checks for miss, but also ensures that a hit is not already registered for this letter

                    temp_result[j] = new word("m");

                } else if (temp_result[j] == null && ar_secret[i]!=ar_user[j]) { // records a miss otherwise, ensuring that it doesn't overwrite the preciously recorded results

                    temp_result[j] = new word("-");

                }

            }

        }

        results = Arrays.copyOf(temp_result, user.length()); //copies the result to the results array

//        int count = 0;

        // prints the results of the comparison (all hits, misses, or dashes
        for (int i = 0; i < results.length; i++){

            if (results[i].getWord() != null){

                System.out.print(results[i].getWord());

            }

        }

        System.out.print("\n");

        String[] temp_history = Arrays.copyOf(progress_history, size_history() + 1); // creates a temporary array to store the history of the user

        StringJoiner s = new StringJoiner("");

        for(word word: results){

            s.add(word.getWord()); // adds the results to the StringJoiner array

        }

        temp_history[size_history()] = s.toString(); // converts the StringJoiner object to a String

        progress_history = Arrays.copyOf(temp_history, size_history() + 1); // copies the history into the dedicated array

    }

    public void history(){

        /* prints the history of each game */

        System.out.println("History: ");

        for (int i = 0; i < size_history(); i++){

            System.out.printf(" %d:%s:%s\n", i+1,  user_guess[i], progress_history[i]);

        }
    }

    public void secret(){

        /* prints the secret word*/

        System.out.printf("Secret = %s\n", getSecret());

    }

}

