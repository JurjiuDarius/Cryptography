import java.util.Scanner;

public class RSA_Encryption {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int q, p, b;
        q = 101;
        p = 113;
        b = 3533;
        System.out.println("Enter the plaintext");
        String plaintext = scanner.nextLine();
        plaintext = plaintext.toLowerCase();
        int[] encryption = RSAEncrypt(plaintext, q, p, b);
        System.out.println("The cyphertext is ");
        for (int i = 0; i < encryption.length; i++) {
            System.out.print(encryption[i] + " ");
        }
        String decryption = RSADecrypt(encryption, q, p, b);
        System.out.println("The decrypted plaintext is " + decryption);
        System.out.println("Press enter to continue");
        scanner.nextLine();
        scanner.close();
    }
    public static String RSADecrypt(int[] cypher, int q, int p, int b) {
        int phi = (q - 1) * (p - 1);
        int n = q * p;
        int a = inverseOfBModuloN(b, phi);
        int[] cyphertext = new int[cypher.length];
        for (int i = 0; i < cypher.length; i++) {
            int m = cypher[i];
            cyphertext[i] = rapidExpModN(m, a, n);
        }
        String result = listToString(cyphertext);
        return result;
    }
    public static int[] RSAEncrypt(String plaintext, int q, int p, int b) {
        int n = q * p;
        int[] cypher = new int[plaintext.length()];
        for (int i = 0; i < plaintext.length(); i++) {
            int m = charToInt(plaintext.charAt(i));
            cypher[i] = rapidExpModN(m, b, n);
        }
        return cypher;
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

    public static int charToInt(char c) {
        return (int) c - 97;
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
