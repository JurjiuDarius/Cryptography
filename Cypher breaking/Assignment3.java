import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Assignment3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the string");
        String plaintext = scanner.nextLine();
        List<Double> signals = plaintextToSignal(plaintext);
        System.out.println("The analog signal is");
        for (int i = 0; i < signals.size(); i++) {
            System.out.println(signals.get(i));
        }
        System.out.println("Press enter to continue");
        scanner.nextLine();
        scanner.close();
    }
    
    public static List<Double> plaintextToSignal(String plaintext) {
        String cypherText = caesarEncrypt(plaintext, 3);
        List<Integer> charIndex = getCharIndex(cypherText);
        return adConverter(charIndex);
    }
    public static List<Double> adConverter(List<Integer> signals) {
        List<Double> modulatedSignals = new ArrayList<Double>();
        for (int i = 0; i < signals.size(); i++) {
            modulatedSignals.add(signalFunction(signals.get(i)));
        }
        return modulatedSignals;
    }
    
    public static String caesarEncrypt(String plaintext, int shift) {
        String cypherText = "";
        for (int i = 0; i < plaintext.length(); i++) {
            char c = plaintext.charAt(i);
            if (c >= 'a' && c <= 'z') {
                c = (char) (c + shift);
                if (c > 'z') {
                    c -= 26;
                }
            }
            cypherText += c;
        }
        return cypherText;
    }

    public static List<Integer> getCharIndex(String plaintext) {
        List<Integer> charIndex = new ArrayList<Integer>();
        for (int i = 0; i < plaintext.length(); i++) {
            charIndex.add((int) plaintext.charAt(i) - 97);
        }
        return charIndex;
    }

    public static Double signalFunction(Integer value) {
        return (Math.sin(value) * 100);
    }
}
