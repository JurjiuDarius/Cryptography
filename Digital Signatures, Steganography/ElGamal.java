import java.util.Scanner;

class ElGamal {
    
    public static void main(String[] args) {
        int p = 107;
        int alhpa = 122503;
        int a = 10;
        int k = 45;
        String m = "B";
        int[] message = stringToIntArray(m);
        int[] encryption = signElGamal(alhpa, a, p, k, message);
        System.out.println("Encrypted message: ");
        for (int i = 0; i < encryption.length;i++){
            System.out.println( encryption[i]);
        }
        int beta = verifyBeta(alhpa, a, p);
        System.out.println("Beta: " + beta);
        Scanner sc = new Scanner(System.in);
        System.out.println("Press any key to continue...");
        sc.nextLine();
        sc.close();

    }
    
    public static int[] stringToIntArray(String s) {
        int [] encryption = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            encryption[i] = s.charAt(i);
        }
        return encryption;
    }
    public static int[] signElGamal(int alpha, int a, int p, int k, int[] message) {
        int gamma = rapidExp(a, k, p);
        int [] encryptionArray = new int[message.length];
        for (int i = 0; i < message.length; i++) {
            int encryption = ((message[i] - a * gamma) * modInverse(k, p - 1)) % (p - 1);
            while (encryption < 0) {
                encryption += (p - 1);
            }
            encryptionArray[i] = encryption;
        }
        //System.out.println(gamma);
        return encryptionArray;
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