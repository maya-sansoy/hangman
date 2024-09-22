import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


/*
    hangman plan:
    - random word generator/word bank √
    - user input √
    - check if the input(letter guessed) appears in the word √
    - if input is longer than 1 letter √
        - if input = generated word; user wins √
            - replaces all the chars in the userArray to the correct ones √
            - for i in range length √
                - userArray[i] = correctword[i]
        - else: "wrong word, guess again" √
    - different array for what's been guessed in relation to the correct word (userArray) √
        - same length as old array, but in each 'char' variable it will just be '_' √
        - as the user starts to guess the words, the '_'s get replaced with the actual letters √
        - when userArray = word; the user wins (or if they guess the correct word) √
    - print out the user array each time √


    todo:
     store all the letters guessed in a new array,
     if the letter they guessed is part of that array, tell them to use a unique letter (+1 to remaining guesses)

 */

//Make it show the letters you've guessed already (ArrayList) √
//Learn how to read a text file into Java and randomly pick the hangman word from this list of words √

public class Main {
    public static void main(String[] args)   throws IOException {

        Scanner keyboard = new Scanner(System.in);
        Random rnd = new Random();

        // creating random word bank and generator

        //
        File wordlist = new File("/Users/maya/IdeaProjects/ReadFromFile/src/text.txt");
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(wordlist));
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        }

        List<String> wordBank = new ArrayList<String>();

        //adding all words from file to new list array
        while (true) {

            String words = reader.readLine();

            if (words == null) {
                break;
            }
            wordBank.add(words);
        }

//        String[] wordBank = {"maya", "hangman", "tamagotchi", "blablabla"};

        String chosenword = wordBank.get(rnd.nextInt(0, wordBank.size()));

        char[] word = chosenword.toCharArray();
//
        int length = word.length;

        char[] userArray = new char[length];

        for (int i = 0; i <= length -1; i++) {
            userArray[i] = '_';
        }

        // user input/innitialising variables


        int guessesRemaining = 6;
        int counter = 0;
        int numberOfCorrectTerms = 0;
        int numberOfCorrectTermsInnitial = 0;

        //letters the user has used already
        List<String> alreadyUsed = new ArrayList<String>();



        // printing instructions
        System.out.println("H A N G  M A N");
        System.out.println("rules:");
        System.out.println("- i will think of a word, and you have 6 guesses to figure it out");
        System.out.println("- if you think you know the word, you can also try to type it in instead of a letter");
        System.out.println("good luck!");
        System.out.println("");
        System.out.println("» press any key to start « ");
        String empty = keyboard.nextLine();
        System.out.println("");
        System.out.println("the word has " + length + " letters");
        System.out.println("guess a letter or word");

        String guess = "guess";
        int count = 0;

        do {
            // printing the hangman graphic'
            count ++;

            System.out.println("already guessed: ");


            for (int i = 0; i < alreadyUsed.size(); i++) {
                System.out.print(alreadyUsed.get(i));
                System.out.print(", ");

            }

            System.out.println("");

            if (guessesRemaining == 6){
                System.out.println("____ ");
                System.out.println("|   ");
                System.out.println("|    ");
            }
            else if (guessesRemaining == 5) {
                System.out.println("____ o");
                System.out.println("|   ");
                System.out.println("|    ");
            }
            else if (guessesRemaining == 4) {
                System.out.println("____ o");
                System.out.println("|    |");
                System.out.println("|    ");
            }
            else if (guessesRemaining == 3) {
                System.out.println("____ o");
                System.out.println("|   -|");
                System.out.println("|    ");
            }
            else if (guessesRemaining == 2) {
                System.out.println("____ o");
                System.out.println("|   -|-");
                System.out.println("|    ");
            }
            else if (guessesRemaining == 1) {
                System.out.println("____ o");
                System.out.println("|   -|-");
                System.out.println("|    /");
            }

            //getting user input
            guessesRemaining = guessesRemaining - 1;



            System.out.println(userArray);
            System.out.println("");

            guess = keyboard.nextLine();
            System.out.println("");

            guess = guess.toLowerCase();

            boolean alreadyguessed = false;

            if (count != 1) {
                for (int i = 0; i < alreadyUsed.size(); i++) {
                    if (guess.equals(alreadyUsed.get(i))) {
                        System.out.println("you've already guessed that. try again");
                        alreadyguessed = true;
                        guessesRemaining++;
                        break;
                    }
                }
                if (alreadyguessed) {
                    continue;
                }
                else {
                    alreadyUsed.add(guess);
                }
            }
            else {
                alreadyUsed.add(guess);
            }


            //checking for singular characters/letters
            if (guess.length() == 1) {


                numberOfCorrectTermsInnitial = numberOfCorrectTerms;

                // checks if the guessed letter is part of the word
                for (int i = 0; i <= word.length - 1; i++) {

                    if (guess.charAt(0) == word[i]) {
                        userArray[i] = word[i];
                        numberOfCorrectTerms = numberOfCorrectTerms + 1;
                    }

                }
                if (Arrays.equals(userArray, word)) {
                    break;
                }

                // figuring out if the user got it right (and giving them another guess if they did)
                if (numberOfCorrectTermsInnitial != numberOfCorrectTerms) {
                    guessesRemaining = guessesRemaining + 1;
                    System.out.println("correct! guess again");
                    System.out.println("you have " + guessesRemaining + " left");

                }
                else {
                    System.out.println("wrong, guess again");
                    System.out.println("you have " + guessesRemaining + " left");
                }

            } // checking for when the user inputs actual words
            else {
                // checks if word is equal, then sets the userArray to the correct word too
                if (Arrays.equals(guess.toCharArray(), word)) {
                    for (int i = 0; i <= word.length - 1; i++) {
                        userArray[i] = word[i];
                        numberOfCorrectTerms = length;
                        guessesRemaining = guessesRemaining + 1;
                    }

                } else {
                    System.out.println("that's" +
                            " the wrong word, guess again");
                    guessesRemaining --;

                }
            }

            //prints hangman graphic for 0 guesses remaining (i had to add this to the end, because i lower the guesses remaining at the beginning, and if you had no more guesses you wouldn't be able to loop back up and see the last grqphic, which are printed before removing a guess)
            if (guessesRemaining == 0) {
                System.out.println("____ o");
                System.out.println("|   -|-");
                System.out.println("|    /\\");

                System.out.println("no more guesses left");
                break;
            }
        }
        while (!Arrays.equals(userArray, word));




        // printing final winning/losing statements
        if (Arrays.equals(userArray, word)) {
            System.out.println("correct, the word was "+ chosenword);
            System.out.println("you win!");
        }
        else {
            System.out.println("you lose :(");
            System.out.println("the word was");
            System.out.println(word);
        }

    }
}