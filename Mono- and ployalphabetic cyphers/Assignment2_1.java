import java.util.Scanner;

public class Assignment2_1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the string");
        String cyphertext = sc.nextLine();

        System.out.println("a=");
        int a = sc.nextInt();
        sc.nextLine();

        System.out.println("b=");
        int b = sc.nextInt();
        sc.nextLine();

        if (gcd(a, 26) != 1) {
            System.out.println("a and 26 are not coprime");
            sc.close();
            return;
        }

        System.out.println("The cyphertext is " + encrypt(a,b, cyphertext) );
        System.out.println("Press enter to continue");
        sc.nextLine();

        sc.close();

}

    public static String encrypt(int a, int b, String cyphertext) {
        byte[] x = new byte[100];
        cyphertext = cyphertext.toLowerCase();
        x = cyphertext.getBytes();
        for (int i = 0; i < x.length; i++) {
            x[i]-=97;
            
            x[i] *= a;
            x[i] += b;
            if (x[i] < 0) {
                x[i] += 26;
            }
            x[i] %= 26;
            x[i] += 97;
        }
        return new String(x);
    }

    public static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        } else {
            return gcd(b, a % b);
        }
    }
}
