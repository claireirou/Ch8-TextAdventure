/**
 * This class is part of the "Mansion Detective" application. 
 * "Mansion Detective" is a text based murder myster adventure game.  
 *
 * This class holds information about a command that was issued by the user.
 * A command currently consists of up to four parts: a CommandWord and a series of strings
 * (for example, if the command was "take old map", then the three parts
 * are TAKE and "old" and "map").
 * 
 * The way this is used is: Commands are already checked for being valid
 * command words. If the user entered an invalid command (a word that is not
 * known) then the CommandWord is UNKNOWN.
 *
 * If the command had only one word, then the second, third and fourth
 * words are <null>.
 * 
 * @author Claire Iroudayassamy
 * @version 2019.04.15
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.08.10
 */

public class Command
{
    private CommandWord commandWord;
    private String secondWord;
    private String thirdWord;
    private String fourthWord;

    /**
     * Create a command object. First, second, third, and fourth 
     * words must be supplied, but the second, third and fouth may be null.
     * @param commandWord The CommandWord. UNKNOWN if the command word
     *                  was not recognised.
     * @param secondWord The second word of the command. May be null.
     * @param thirdWord The third word of the command. May be null.
     * @param fourthWord The fourth word of the command. May be null.
     */
    public Command(CommandWord commandWord, String secondWord, String thirdWord, String fourthWord)
    {
        this.commandWord = commandWord;
        this.secondWord = secondWord;
        this.thirdWord = thirdWord;
        this.fourthWord = fourthWord;
    }

    /**
     * Return the command word (the first word) of this command.
     * @return The command word.
     */
    public CommandWord getCommandWord()
    {
        return commandWord;
    }

    /**
     * @return The second word of this command. Returns null if there was no
     * second word.
     */
    public String getSecondWord()
    {
        return secondWord;
    }
    
    /**
     * @return The third word of this command. Returns null if there was no
     * third word
     */
    public String getThirdWord()
    {
        return thirdWord;
    }
    
    /**
     * @return The fourth word of this command. Returns null if there was no
     * fourth word
     */
    public String getFourthWord()
    {
        return fourthWord;
    }

    /**
     * @return true if this command was not understood.
     */
    public boolean isUnknown()
    {
        return (commandWord == CommandWord.UNKNOWN);
    }

    /**
     * @return true if the command has a second word.
     */
    public boolean hasSecondWord()
    {
        return (secondWord != null);
    }
    
    /**
     * @return true if the command has a thrid word.
     */
    public boolean hasThirdWord()
    {
        return (thirdWord != null);
    }
    
    /**
     * @return true if the command has a fourth word.
     */
    public boolean hasFourthWord()
    {
        return (fourthWord != null);
    }
}

