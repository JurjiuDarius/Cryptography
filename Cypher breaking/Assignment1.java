import java.util.Scanner;

public class Assignment1 {
    public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the string");
            String cyphertext = scanner.nextLine();
            System.out.println("The plaintext is " + HackShift(cyphertext));
            System.out.println("Press enter to continue");
            scanner.nextLine();
            scanner.close();
        
    }

    public static String HackShift(String cyphertext){
        cyphertext = cyphertext.toLowerCase();
        
        for (int i = 0; i < 26; i++){
            String potentialPlaintext = shift(i, cyphertext);
            if (potentialPlaintext.contains("the") || potentialPlaintext.contains("and") || potentialPlaintext.contains("you") || potentialPlaintext.contains("that") || potentialPlaintext.contains("have")){
                return potentialPlaintext;
            }
        }
        return "The plaintext could not be found";
    }

    public static String shift(int shift, String cyphertext){
        String plaintext = "";
        for (int i = 0; i < cyphertext.length(); i++){
            char c = cyphertext.charAt(i);
            if (c >= 'a' && c <= 'z'){
                c = (char)(c - shift);
                if (c < 'a'){
                    c += 26;
                }
            }
            plaintext += c;
        }
        return plaintext;
    }
}
