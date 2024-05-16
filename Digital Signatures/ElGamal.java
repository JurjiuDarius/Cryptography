import java.util.Scanner;

class ElGamal {
    
    public static void main(String[] args) {
        int p = 107;
        int alhpa = 122503;
        int a = 10;
        int k = 45;
        int message = (int) 'B';
        int encryption = signElGamal(alhpa, a, p, k, message);
        System.out.println("Encrypted message: " + encryption);
        int beta = verifyBeta(alhpa, a, p);
        System.out.println("Beta: " + beta);
        Scanner sc = new Scanner(System.in);
        System.out.println("Press any key to continue...");
        sc.nextLine();
        sc.close();

    }
    

    public static int signElGamal(int alpha, int a, int p, int k, int m) {
        int gamma = rapidExp(alpha, k, p);
        int encryption = (m - a * gamma) * modInverse(k, p - 1) % (p - 1);
        while (encryption < 0) {
            encryption += (p - 1);
        }
        return encryption;
    }

    public static int modInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        return 1;
    }
    public static int verifyBeta(int alpha, int a, int p) {
        return rapidExp(alpha, a, p);
    }
    
    public static int rapidExp(int number, int pow, int modulo){
        int result = 1;
        while (pow > 0) {
            if (pow % 2 == 1) {
                result = (result * number) % modulo;
            }
            number = (number * number) % modulo;
            pow = pow / 2;
        }
        return result;
    }
}