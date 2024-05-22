import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RSA_Hack {
    public static int[] cyphertext = {365, 0, 4845, 14930, 2608, 2608, 0};
    public static int n = 11413;
    public static int b = 3533;
    public static char[] englishAlphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    public static Map<Integer, String> characterEncryptionMap;

    public static void main(String[] args) {
        characterEncryptionMap = getCharMap(b, n);
        System.out.println("Print the hasmap");
       
        Scanner scanner = new Scanner(System.in);
        System.out.println("How long is the cyphertext?");
        int length = scanner.nextInt();
        int[] cyphertext = new int[length];
        for (int i = 0; i < length; i++) {
            System.out.println("Enter the cyphertext");
            cyphertext[i] = scanner.nextInt();
        }
        System.out.println("The plaintext is");
        for (int i = 0; i < length; i++) {
            if (characterEncryptionMap.containsKey(cyphertext[i])) {
                System.out.println("?");
            }
            System.err.print(characterEncryptionMap.get(cyphertext[i]));
        }

        // String plaintext = RSAHack(cyphertext, length);
        // System.out.println("The plaintext is " + plaintext);
        System.out.println("Press enter to continue");
        scanner.nextLine();
        scanner.nextLine();
        scanner.close();
    }

   public static HashMap<Integer, String> getCharMap(int b, int n){
        HashMap<Integer, String> map = new HashMap<>();
        for (char c : englishAlphabet) {
            int ascii = (int) c - 97;
            int encrypted = rapidExpModN(ascii, b, n);
            map.put(encrypted, String.valueOf(c));
        }
        
        return map;
    } 
    public static String RSAHack(int[] cyphertext, int length) {
    
        int[] plaintext = new int[length];
        for (int i = 0; i < length; i++) {
            int m = cyphertext[i];
            plaintext[i] = rapidExpModN(m, b, n);
        }
        String result = listToString(plaintext);
        return result;
    }

    public static int[] factorN(int n) {
        // Obviously unfeasable for large numbers
        int[] factors = new int[2];
        for (int i = 2; i < n; i++) {
            if (n % i == 0 && isPrime(i) && isPrime(n / i)){
                factors[0] = i;
                factors[1] = n / i;
                return factors;
            }
        }
        return factors;
    }

    public static boolean isPrime(int n) {
        for (int i = 2; i < n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
    public static int inverseOfBModuloN(int b, int n) {
        int[] ext = extendedEuclidean(b, n);
        if (ext[0] > 1) {
            return -1; 
        }
        int inv = ext[1] % n;
        if (inv < 0) {
            inv += n; 
        }
        return inv;
    }

    public static int[] extendedEuclidean(int a, int b) {
        if (a == 0) {
            return new int[] {b, 0, 1};
        } else {
            int[] ext = extendedEuclidean(b % a, a);
            int x = ext[2] - (b / a) * ext[1];
            int y = ext[1];
            return new int[] {ext[0], x, y};
        }
    }
    public static String listToString(int[] list) {
        String result = "";
        for (int i = 0; i < list.length; i++) {
            result += (char) (list[i] + 97);
        }
        return result;
    }
    public static int rapidExpModN(int a, int b, int n) {
        int result = 1;
        while (b > 0) {
            if (b % 2 == 1) {
                result = (result * a) % n;
            }
            a = (a * a) % n;
            b /= 2;
        }
        return result;
    }
}
