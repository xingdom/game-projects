import java.util.*;
import java.lang.Math;

public class Wordle {

    static boolean endGame = false; //Ends program when true
    static boolean userWon = false;
    static int round = 1;
    static String solution;
    static HashMap<Character,Integer> solutionFreqMap;
    private static final String[] solutionsList = {"abhor","abort","about","above","abuse","abyss","actor", "adept"};

    public static void main(String[] args) {
        System.out.println("Welcome to Jordle, Jane's mini slightly scuffed version of Wordle!");
        while(!endGame) {
            newRound();
        }
        System.out.println("Thanks for playing! Come back soon.");
    }

    //Starts a new round, runs through guesses until user wins or game over
    public static void newRound() {
        Scanner s = new Scanner(System.in);
        solution = solutionsList[(int)(Math.random()*(solutionsList.length)-1)];
        solutionFreqMap = letterFreqMap(solution);

        while(round <= 6 && userWon == false) {
            String guess = "";
            System.out.println("\nEnter 5 letter guess:");
            guess = s.nextLine();
            //Makes sure guess is actually 5 letters
            while (guess.length() != 5) {
                System.out.println("Girl, be so for real. 5 letters. Try again:");
                guess = s.nextLine();
            }

            checkGuess(guess);
        }

        //Post match; user either won or lost
        if(userWon == false) {
            System.out.println("Game over.");
        } else {
            System.out.println("You win!");
        }

        System.out.println("The correct word was "+solution+". Play again? Y or N");
        String playAgain = s.next();
        while(true) {
            if(playAgain.equals("N") || playAgain.equals("n")) {
                endGame = true;
                break;
            } else if(!(playAgain.equals("Y") || playAgain.equals("y"))) {
                System.out.println("Didn't quite catch that. Y or N?");
                playAgain = s.next();
            } else { //Selected Y, wants to replay
                userWon = false;
                round = 1;
                break;
            }
        }

    }

    //Given a guess, will print out the corresponding emojis to the solution
    public static void checkGuess(String guess) {
        guess = guess.toLowerCase();
        if(guess.equals(solution)) {
            System.out.println("EEEEE");
            userWon = true;
            return;
        } else {
            HashMap<Character,Integer> guessFreqMap = letterFreqMap(guess);
            //Iterating through every letter in the guess and printing an emoji
            for(int i=0; i<5; i++) {
                //Letter doesn't exist in the word
                if(solution.indexOf(guess.substring(i,i+1))==-1) {
                    System.out.print("A");
                } else if(guess.charAt(i) == solution.charAt(i)) { //Right position
                    System.out.print("E");
                } else { //Letter exists at diff position
                    if(guessFreqMap.get(guess.charAt(i)) <= solutionFreqMap.get(guess.charAt(i))) {
                        System.out.print("Y");
                    } else {
                        //It occurs in guess more than solution.
                        //First, count how many greens exist for this letter
                        int greenOccurrences = 0;
                        for(int k=0; k<5; k++) {
                            if((guess.charAt(k) == guess.charAt(i)) && (guess.charAt(k) == solution.charAt(k))) {
                                greenOccurrences++;
                            }
                        }

                        //If there are same number of greens in guess as solutionFreq, print gray
                        if(greenOccurrences == solutionFreqMap.get(guess.charAt(i))) {
                            System.out.print("A");
                        } else {
                            //Which occurrence of this letter are we on?
                            int occurrenceNum = 1;
                            for(int j=i-1; j>=0; j--) {
                                if(guess.charAt(j)==guess.charAt(i)) {
                                    occurrenceNum++;
                                }
                            }
                            if(occurrenceNum <= solutionFreqMap.get(guess.charAt(i)) - greenOccurrences) {
                                System.out.print("Y");
                            } else {
                                System.out.print("A");
                            }
                        }

                    }
                }
            }
            round++;
        }
    }

    //Given a word, returns a HashMap of each letter and its frequency
    public static HashMap<Character,Integer> letterFreqMap(String word) {
        HashMap<Character,Integer> freqMap = new HashMap<>();
        for(int i=0; i<5; i++) {
            Character c = word.charAt(i);
            if(freqMap.containsKey(c)) {
                freqMap.put(c,freqMap.get(c)+1);
            } else {
                freqMap.put(c,1);
            }
        }

        return freqMap;
    }

}
