import java.util.Scanner;

// This class represents a graded sports card and allows these cards to be compared with each other
// through various characteristics
public class GradedSportsCard implements Comparable<GradedSportsCard>{
    private int grade;
    private String sport;
    private String player;

    // Behavior: 
    //   - This method constructs a GradedSportsCard with characteristics of the clients choosing
    // Parameters:
    //   - grade: the grade of the sports card
    //   - sport: the sport the player plays
    //   - player: the name of the player
    public GradedSportsCard(int grade, String sport, String player){
        this.grade = grade;
        this.sport = sport;
        this.player = player;
    }

    // Behavior: 
    //   - This method returns the grade a given sport card has
    // Returns:
    //   - int: the grade of the GradedSportsCard
    public int getGrade(){
        return grade;
    }
    
    // Behavior: 
    //   - This method takes in user input to decide the characteristics of a card.
    // Parameters:
    //   - input: the user's input
    // Returns:
    //   - GradedSportsCard: the card the user gave the traits for
    // Exceptions:
    //   - if the given input is null, an IllegalArgumentException is thrown.
    public static GradedSportsCard parse(Scanner input){
        if(input == null){
            throw new IllegalArgumentException();
        }
        System.out.println("What grade is this card?");
        int grade = input.nextInt();
        input.nextLine();

        System.out.println("What sport is the player from?");
        String sport = input.nextLine();
        
        System.out.println("What's his/her name?");
        String player = input.nextLine();

        return new GradedSportsCard(grade, sport, player);
    }

    // Behavior: 
    //   - This method displays the characteristics of a card in a nicely formatted manner
    // Returns:
    //   - String: important information about a card
    public String toString(){
        return "This " + sport + " card is a card of " + player + ", grading a " + grade + ".";
    }

    // Behavior: 
    //   - This method determines if two cards are the same based on their key characteristics
    // Parameters:
    //   - o: the other card being checked
    // Returns:
    //   - boolean: true if they are the exact same, false otherwise
    public boolean equals(GradedSportsCard o){
        if(this.grade == o.grade && this.sport.compareTo(o.sport) == 0 && 
                this.player.compareTo(o.player) == 0){
            return true;
        }
        return false;
    } 
    
    // Behavior: 
    //   - This method gives a unique numerical identification to every card. If two cards are the
    //   - exact same, they will have the same identification.
    // Returns:
    //   - int: the unique identification of every card
    public int hashCode(){
        int h = 0;
        for(int i = 0; i < sport.length(); i++){
            h = 31 * h + sport.charAt(i);
        }
        for(int i = 0; i < player.length(); i++){
            h = 31 * h + player.charAt(i);
        }
        h += grade;
        return h;
    }

    // Behavior: 
    //   - This method compares two cards to determine their relative order, first by grade, with
    //   - higher grade coming first, then by sport in a lexicographic manner, and then by the name
    //   - of the player, again in a lexographic manner.
    // Parameters:
    //   - other: the card being compared to
    // Returns:
    //   - int: a positive number if this card comes before the other card, negative if the
    //   - opposite is true, and 0 if they're both the exact same card
    public int compareTo(GradedSportsCard other){
        if(!this.equals(other)){
            if(this.grade != other.grade){
                return other.grade - this.grade;
            }
            if(!this.sport.equals(other.sport)){
                if(this.sport.compareTo(other.sport) > 0){
                    return 1;
                }
                return -1;
            }
            if(!this.player.equals(other.player)){
                if(this.player.compareTo(other.player) > 0){
                    return 1;
                }
                return -1;
            }
        }
        return 0;
    }
}
