import java.util.Scanner;

/**
 * This class is part of the "Mansion Detective" application. 
 * "Mansion Detective" is a text based murder mystery adventure game.  
 * 
 * This parser reads user input and tries to interpret it as an "Adventure"
 * command. Every time it is called it reads a line from the terminal and
 * tries to interpret the line as a four-word command. It returns the command
 * as an object of class Command.
 *
 * The parser has a set of known command words. It checks user input against
 * the known commands, and if the input is not one of the known commands, it
 * returns a command object that is marked as an unknown command.
 * 
 * @author Claire Iroudayassamy
 * @verson 2019.04.15
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.08.10
 */
public class Parser 
{
    private CommandWords commands;  // holds all valid command words
    private Scanner reader;         // source of command input

    /**
     * Create a parser to read from the terminal window.
     */
    public Parser() 
    {
        commands = new CommandWords();
        reader = new Scanner(System.in);
    }

    /**
     * @return The next command from the user.
     */
    public Command getCommand() 
    {
        String inputLine;   // will hold the full input line
        String word1 = null;
        String word2 = null;
        String word3 = null;
        String word4 = null;

        System.out.print("> ");     // print prompt

        inputLine = reader.nextLine().toLowerCase();

        // Find up to four words on the line.
        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next();      // get first word
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next();      // get second word
                if(tokenizer.hasNext()) {
                    word3 = tokenizer.next();       //get third word
                    if(tokenizer.hasNext()) {
                        word4 = tokenizer.next();       //get fourth word
                        // note: we just ignore the rest of the input line.
                    }
                }
            }
        }

        return new Command(commands.getCommandWord(word1), word2, word3, word4);
    }

    /**
     * Print out a list of valid command words.
     */
    public void showCommands()
    {
        commands.showAll();
    }
}
