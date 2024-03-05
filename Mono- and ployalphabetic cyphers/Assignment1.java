import java.util.Scanner;

class Assignment1 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the string");
        String cyphertext = sc.nextLine();

        System.out.println("Enter the key");
        int key = sc.nextInt();
        sc.nextLine();
        System.out.println("The text is " + decrypt(key, cyphertext) );
        System.out.println("Press enter to continue");
        sc.nextLine();

        sc.close();

}

    public static String decrypt(int key, String cyphertext) {
        byte[] x = new byte[100];
        cyphertext = cyphertext.toLowerCase();
        x = cyphertext.getBytes();
        for (int i = 0; i < x.length; i++) {
            x[i]-=97;
            
            x[i] -= key;
            if (x[i] < 0) {
                x[i] += 26;
            }

            x[i] %= 26;

            x[i] += 97;
        }
        return new String(x);
    }
}