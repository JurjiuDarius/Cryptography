import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Assignment3 {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the string");
        String plaintext = scanner.nextLine();
        System.out.println("Enter the key");
        int c11 = scanner.nextInt();
        int c12 = scanner.nextInt();
        int c21 = scanner.nextInt();
        int c22 = scanner.nextInt();
        scanner.nextLine();
        System.out.println("The cyphertext is " + encrypt(c11, c12, c21, c22, plaintext) );
        System.out.println("Press enter to continue");
        scanner.nextLine();
        scanner.close();
    }

    public static String encrypt(int c11, int c12, int c21, int c22, String s){
        // Check if determinant and 26 are coprime
        if (gcd(c11*c22 - c12*c21,26) != 1){
            System.out.println("The determinant and 26 are not coprime");
            return "";
        }
        String encryption = "";
        int i=0;
        if (s.length()%2 != 0){
            s += "x";
        }
        while(i < s.length()){
            List<Integer> plaintext = new ArrayList<Integer>();

            plaintext.add((int)s.charAt(i) - 97);
            plaintext.add((int)s.charAt(i+1) - 97);
            List<Integer> ciphertext = matmul(c11, c12, c21, c22, plaintext);
            encryption += (char)(ciphertext.get(0) + 97);
            encryption += (char)(ciphertext.get(1) + 97);
            i+=2;
        }
        return encryption;

    }

    public static List<Integer> matmul(int c11, int c12, int c21, int c22, List<Integer> plaintext){
        List<Integer> ciphertext = new ArrayList<Integer>();
        ciphertext.add((c11*plaintext.get(0) + c12*plaintext.get(1))%26);
        ciphertext.add((c21*plaintext.get(0) + c22*plaintext.get(1))%26);
        return ciphertext;
    }

    public static Integer gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }
}
