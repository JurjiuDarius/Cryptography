import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;
public class Assignment2 {

    public static SortedMap<Float, String> englishFrequencyPerLetterMap = new TreeMap<>(); 
    static {
            englishFrequencyPerLetterMap.put(0.08167f, "a");
            englishFrequencyPerLetterMap.put(0.01492f, "b");
            englishFrequencyPerLetterMap.put(0.02782f, "c");
            englishFrequencyPerLetterMap.put(0.04253f, "d");
            englishFrequencyPerLetterMap.put(0.12702f, "e");
            englishFrequencyPerLetterMap.put(0.02228f, "f");
            englishFrequencyPerLetterMap.put(0.02015f, "g");
            englishFrequencyPerLetterMap.put(0.06094f, "h");
            englishFrequencyPerLetterMap.put(0.06966f, "i");
            englishFrequencyPerLetterMap.put(0.00153f, "j");
            englishFrequencyPerLetterMap.put(0.00772f, "k");
            englishFrequencyPerLetterMap.put(0.04025f, "l");
            englishFrequencyPerLetterMap.put(0.02406f, "m");
            englishFrequencyPerLetterMap.put(0.06749f, "n");
            englishFrequencyPerLetterMap.put(0.07507f, "o");
            englishFrequencyPerLetterMap.put(0.01929f, "p");
            englishFrequencyPerLetterMap.put(0.00095f, "q");
            englishFrequencyPerLetterMap.put(0.05987f, "r");
            englishFrequencyPerLetterMap.put(0.06327f, "s");
            englishFrequencyPerLetterMap.put(0.09056f, "t");
            englishFrequencyPerLetterMap.put(0.02758f, "u");
            englishFrequencyPerLetterMap.put(0.00978f, "v");
            englishFrequencyPerLetterMap.put(0.02360f, "w");
            englishFrequencyPerLetterMap.put(0.00150f, "x");
            englishFrequencyPerLetterMap.put(0.01974f, "y");
            englishFrequencyPerLetterMap.put(0.00074f, "z");
        }

    public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the string");
            String cyphertext = scanner.nextLine();
            System.out.println("The plaintext is " + HackAffine(cyphertext, 1000));
            System.out.println("Press enter to continue");
            scanner.nextLine();
            scanner.close();
        
    }

    public static String HackAffine(String cyphertext, int tries){
        cyphertext = cyphertext.toLowerCase();
        SortedMap<String, Float> cypherFrequencyPerLetterMap = new TreeMap<>();
        for (int i = 0; i < cyphertext.length(); i++){
            String c = String.valueOf(cyphertext.charAt(i));
            if (cypherFrequencyPerLetterMap.containsKey(c)){
                cypherFrequencyPerLetterMap.put(c, cypherFrequencyPerLetterMap.get(c) + 1);
            } else {
                cypherFrequencyPerLetterMap.put(c, 1f);
            }
        }

        String highest1 = cypherFrequencyPerLetterMap.entrySet().stream().max((e1, e2) -> e1.getValue().compareTo(e2.getValue())).get().getKey();
        cypherFrequencyPerLetterMap.remove(highest1);
        String highest2 = cypherFrequencyPerLetterMap.entrySet().stream().max((e1, e2) -> e1.getValue().compareTo(e2.getValue())).get().getKey();
        SortedMap<Float, List<String>> combinedFrequenciesSorted = combineFrequencies(englishFrequencyPerLetterMap);
        for (int i = 0; i < tries; i++){
            Map.Entry<Float, List<String>> entry = getEntryAt(combinedFrequenciesSorted, i);
            List <String> letters = entry.getValue();
            String initialLetter1 = letters.get(0);
            String initialLetter2 = letters.get(1);
            String plaintext = tryLetters(cyphertext, highest1, highest2, initialLetter1, initialLetter2);
            if (!plaintext.equals("")){
                return plaintext;
            }
            plaintext = tryLetters(cyphertext, highest1, highest2, initialLetter2, initialLetter1);
            if (!plaintext.equals("")){
                return plaintext;
            }

        }
        return "";
    }

    public static String tryLetters(String cyphertext, String highest1, String highest2, String initialLetter1, String initialLetter2){
        List<Integer> solution = solveEquation(highest1.charAt(0) - 97, highest2.charAt(0) - 97, initialLetter1.charAt(0) - 97, initialLetter2.charAt(0) - 97);
            int a = solution.get(0);
            int b = solution.get(1);
            if (a == -1 || b == -1){
                return "";
            }
            String plaintext = decrypt(a, b, cyphertext);
            if (plaintext.contains("the") || plaintext.contains("and") || plaintext.contains("you") || plaintext.contains("that") || plaintext.contains("have")){
                return plaintext;
            }
            return "";
        }

    public static List<Integer> solveEquation(int highest1, int highest2, int english1, int english2){
        List<Integer> solution = new ArrayList<>();
        int diff1 = highest1 - highest2;
        while (diff1 < 0){
            diff1 += 26;
        }
        int diff2 = english1 - english2;
        while (diff2 < 0){
            diff2 += 26;
        }
        int a = findModuloDivisor(diff2, diff1, 26);
        int b = (highest1 - (a * english1));
        while (b < 0){
            b += 26;
        }
        b = b%26;
        
        solution.add(a);
        solution.add(b);
        return solution;
    }
    public static SortedMap<Float, List<String>> combineFrequencies(SortedMap<Float, String> frequencyPerLetterMap){
        SortedMap<Float, List<String>> combinedFrequencies = new TreeMap<>(new CompareFloat());
        for (Entry<Float, String> entry1 : frequencyPerLetterMap.entrySet()){
            for (Entry<Float, String> entry2 : frequencyPerLetterMap.entrySet()){
                if(entry1.getKey() == entry2.getKey()){
                    continue;
                }
                float combinedFrequency = entry1.getKey() + entry2.getKey();
                List<String> letters = new ArrayList<>();
                letters.add(entry1.getValue());
                letters.add(entry2.getValue());
                List<String> lettersReversed = new ArrayList<>();
                lettersReversed.add(entry2.getValue());
                lettersReversed.add(entry1.getValue());
                combinedFrequencies.put(combinedFrequency, letters);
            }
        }
        return combinedFrequencies;
    }

    public static String decrypt(int a, int b, String cyphertext) {
        byte[] x = new byte[100];
        cyphertext = cyphertext.toLowerCase();
        x = cyphertext.getBytes();
        int inverseA = findModuloInverse(a, 26);
        for (int i = 0; i < x.length; i++) {
            x[i] -= 97;
            x[i] -= b;
            if (x[i] < 0) {
                x[i] += 26;
            }
            x[i] = (byte) ((x[i] * inverseA) % 26);
            x[i] += 97;
        }
        return new String(x);
    }

    public static int findModuloInverse(int x, int m) {
        for (int i = 1; i < m; i++) {
            if ((x * i) % m == 1) {
                return i;
            }
        }
        return -1;

        
    }
    public static int findModuloDivisor(int x, int y, int m){
        for (int i = 1; i < m; i++){
            if ((x * i) % m == y){
                return i;
            }
        }
        return -1;
    }

    public static <K, V> Map.Entry<K, V> getEntryAt(SortedMap<K, V> map, int index) {
        int i = 0;
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (i++ == index) {
                return entry;
            }
        }
        return null;
    }

    static class CompareFloat implements Comparator<Float> {
        public int compare(Float f1, Float f2){
            return f2.compareTo(f1);
        }
    }

    
}
