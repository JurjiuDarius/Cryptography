import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class DES {

    public static String keyString = "3b3898371520f75e";
    public static List<Integer> initialPermutation = List.of(58, 50, 42, 34, 26, 18, 10, 2, 60, 52, 44, 36,
            28, 20, 12, 4, 62, 54, 46, 38, 30, 22, 14, 6, 64, 56, 48, 40, 32, 24, 16, 8, 57, 49, 41,
            33, 25, 17, 9, 1, 59, 51, 43, 35, 27, 19, 11, 3, 61, 53, 45, 37, 29, 21, 13, 5, 63, 55,
            47, 39, 31, 23, 15, 7);
    // Inverse of initial permutation
    public static List<Integer> inverseInitialPermutation = List.of(40, 8, 48, 16, 56, 24, 64, 32, 39, 7, 47, 15, 55, 23,
            63, 31, 38, 6, 46, 14, 54, 22, 62, 30, 37, 5, 45, 13, 53, 21, 61, 29, 36,
            4, 44, 12, 52, 20, 60, 28, 35, 3, 43, 11, 51, 19, 59, 27, 34, 2, 42, 10, 50, 18,
            58, 26, 33, 1, 41, 9, 49, 17, 57, 25);

    public static List<Integer> PC1 = List.of(57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18, 10, 2,
            59, 51, 43, 35, 27, 19, 11, 3, 60, 52, 44, 36, 63, 55, 47, 39, 31, 23, 15, 7, 62, 54,
            46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 29, 21, 13, 5, 28, 20, 12, 4);

    public static List<Integer> PC2 = List.of(14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10, 23, 19, 12, 4,
            26, 8, 16, 7, 27, 20, 13, 2, 41, 52, 31, 37, 47, 55, 30, 40, 51, 45, 33, 48, 44, 49, 39,
            56, 34, 53, 46, 42, 50, 36, 29, 32);

    public static List<Integer> leftShifts = List.of(0, 1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1);

    public static List<Integer> E = List.of(32, 1, 2, 3, 4, 5, 4, 5, 6, 7, 8, 9, 8, 9, 10, 11, 12, 13, 12,
            13, 14, 15, 16, 17, 16, 17, 18, 19, 20, 21, 20, 21, 22, 23, 24, 25, 24, 25, 26, 27, 28,
            29, 28, 29, 30, 31, 32, 1);

    public static List<Integer> P = List.of(16, 7, 20, 21, 29, 12, 28, 17, 1, 15, 23, 26, 5, 18, 31, 10, 2,
            8, 24, 14, 32, 27, 3, 9, 19, 13, 30, 6, 22, 11, 4, 25);
    
    public static List<List<List<Integer>>> sboxList = List.of(
        List.of( //S1
            List.of(14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7),
            List.of(0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8),
            List.of(4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0),
            List.of(15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13)
        ),
        List.of( //S2
            List.of(15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10),
            List.of(3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5),
            List.of(0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15),
            List.of(13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9)
        ),
        List.of( //S3
            List.of(10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8),
            List.of(13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1),
            List.of(13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7),
            List.of(1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12)
        ),
        List.of( //S4
            List.of(7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15),
            List.of(13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9),
            List.of(10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4),
            List.of(3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14)
        ),
        List.of( //S5
            List.of(2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9),
            List.of(14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6),
            List.of(4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14),
            List.of(11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3)
        ),
        List.of( //S6
            List.of(12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11),
            List.of(10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8),
            List.of(9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6),
            List.of(4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13)
        ),
        List.of( //S7
            List.of(4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1),
            List.of(13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6),
            List.of(1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2),
            List.of(6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12)
        ),
        List.of( //S8 finally
                    List.of(13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7),
                    List.of(1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2),
                    List.of(7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8),
                    List.of(2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11)
            )
   
    );
        

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter plaintext: ");
        String plaintext = scanner.nextLine();
        
        DESencrypt(plaintext, keyString, 16);
        System.out.println("Press any key to continue...");
        scanner.nextLine();
        scanner.close();
        
    }
     
    public static List<boolean[]> DESencrypt(String plaintext, String key, Integer rounds) {
        System.out.println("Encrypting plaintext: " + plaintext + " with key: " + key);
        List<boolean[]> partitionedPlaintext = partitionPlaintext(plaintext);
        List<boolean[]> partitionedCypherText = new ArrayList<>();
        for (int k = 0; k < partitionedPlaintext.size(); k++) {
            System.out.println("For partition " + k);
            boolean[] booleanPlaintext = partitionedPlaintext.get(k);
            for (int i = 0; i < rounds; i++) {
                System.out.println("\n\nRound " + i + ": ");
                boolean[] keyBits = hexStringToBits(key);
                boolean[] roundKey = getKeyForRound(keyBits, i);


                boolean[] newPlaintext = encryptPlaintextWithKey(booleanPlaintext, roundKey, i);
                System.out.println("\nFor round " + i + " the plaintext is: ");
                for (int j = 0; j < newPlaintext.length; j++) {
                    System.out.print(newPlaintext[j] ? "1" : "0");
                }
                System.out.println("\nThe round key was: ");
                for (int j = 0; j < roundKey.length; j++) {
                    System.out.print(roundKey[j] ? "1" : "0");
                }
                booleanPlaintext = newPlaintext;

            }
            booleanPlaintext = applyPermutation(booleanPlaintext, inverseInitialPermutation);
            partitionedCypherText.add(booleanPlaintext);
        }
        return partitionedCypherText;
    }

    public static List<boolean[]> partitionPlaintext(String plaintext) {
        // Partition the plaintext into 64-bit parts. If the last one is not exactly 64, pad it with zeros
        int partitionCount = (int) Math.ceil((double) plaintext.length() / 16);
        List<boolean[]> partitions = new ArrayList<boolean[]>();
        for (int i = 0; i < partitionCount; i++) {
            String partitionString = plaintext.substring(i*8, Math.min((i+1)*16, plaintext.length()));
            boolean[] partition = hexStringToBits(partitionString);
            
            partitions.add(partition);
        }
        return partitions;
    }

    public static boolean[] encryptPlaintextWithKey(boolean[] plaintext, boolean[] key, Integer round) {
        boolean[] leftSide = new boolean[plaintext.length / 2];
        boolean[] rightSide = new boolean[plaintext.length / 2];
        for (int i = 0; i < plaintext.length / 2; i++) {
            leftSide[i] = plaintext[i];
            rightSide[i] = plaintext[i + plaintext.length / 2];
        }
        boolean[] rightSideAfterFeistel = applyFeistel(rightSide, key, round);
        boolean[] newLeftSide = rightSide;
        boolean[] newRightSide = xor(leftSide, rightSideAfterFeistel);
        boolean[] newPlaintext = new boolean[plaintext.length];
        for (int i = 0; i < plaintext.length / 2; i++) {
            newPlaintext[i] = newLeftSide[i];
            newPlaintext[i + plaintext.length / 2] = newRightSide[i];
        }
        return newPlaintext;
    }

    public static boolean[] applyFeistel(boolean[] plaintext, boolean[] key, Integer round) {
        boolean[] expanded = applyPermutation(plaintext, E);
        boolean[] xorResult = xor(expanded, key);
        boolean[] sboxResult = applySbox(xorResult, round);
        boolean[] permuted = applyPermutation(sboxResult, P);
        return permuted;
    }

    public static boolean[] xor(boolean[] a, boolean[] b) {
        boolean[] result = new boolean[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = a[i] ^ b[i];
        }
        return result;
    }

    public static boolean[] applySbox(boolean[] plaintext, Integer round) {
        boolean[] newPlaintext = new boolean[4 * sboxList.size()];
        for (int i = 0; i < sboxList.size(); i++) {
            List<List<Integer>> sbox = sboxList.get(i);

            boolean[] firstAndLast = {plaintext[i*6+0], plaintext[i*6+5]};
            int firstAndLastInt = convertToInteger(firstAndLast);

            boolean[] middle = {plaintext[i*6+1], plaintext[i*6+2], plaintext[i*6+3], plaintext[i*6+4]};
            int middleInt = convertToInteger(middle);

            Integer sboxValue = sbox.get(firstAndLastInt).get(middleInt);
            boolean[] bitArray = integerToBits(sboxValue, 4);
            for (int j = 0; j < bitArray.length; j++) {
                newPlaintext[i*4+j] = bitArray[j];
            }
        }
    return newPlaintext;
    }
    
    public static boolean[] integerToBits(Integer input, Integer size) {
        boolean[] bits = new boolean[size];
        for (int i = 0; i < size; i++) {
            bits[i] = (input & (1 << (size - 1 - i))) != 0;
        }
        return bits;
    }
    public static int convertToInteger(boolean[] bits) {
    int value = 0;
    for (int i = 0; i < bits.length; i++) {
        if (bits[i]) {
            value += 1 << (bits.length - i - 1);
        }
    }
    return value;
}
    public static boolean[] applyPermutation(boolean[] bits, List<Integer> permutation) {
        boolean[] permutedBits = new boolean[permutation.size()];
        for (int i = 0; i < permutation.size(); i++) {
            permutedBits[i] = bits[permutation.get(i) - 1];
        }
        return permutedBits;
    }

    public static boolean[] getKeyForRound(boolean[] key, Integer round) {
        boolean permutedKey[] = applyPermutation(key, PC1);

        boolean[] leftKey = new boolean[permutedKey.length / 2];
        boolean[] rightKey = new boolean[permutedKey.length / 2];
        for (int i = 0; i < permutedKey.length / 2; i++) {
            leftKey[i] = permutedKey[i];
            rightKey[i] = permutedKey[i + permutedKey.length / 2];
        }
        Integer shifInteger = leftShifts.get(round);
        leftKey = leftCircularShift(leftKey, shifInteger);
        rightKey = leftCircularShift(rightKey, shifInteger);

        boolean[] combinedKey = new boolean[leftKey.length + rightKey.length];
        for (int i = 0; i < leftKey.length; i++) {
            combinedKey[i] = leftKey[i];
            combinedKey[i + leftKey.length] = rightKey[i];
        }
        boolean[] secondPermutedKey = applyPermutation(combinedKey, PC2);
        return secondPermutedKey;
    }

    public static boolean[] leftCircularShift(boolean[] input, Integer shift) {
        boolean[] shifted = new boolean[input.length];
        for (int i = 0; i < input.length; i++) {
            shifted[i] = input[(i + shift) % input.length];
        }
        return shifted;
    }

    public static boolean[] hexStringToBits(String hexString) {
        StringBuilder binaryStringBuilder = new StringBuilder();

        // Convert each hexadecimal character to binary
        for (int i = 0; i < hexString.length(); i++) {
            char hexChar = hexString.charAt(i);
            String binary = Integer.toBinaryString(Integer.parseInt(String.valueOf(hexChar), 16));

            // Add leading zeros to ensure each character is represented by 4 bits
            while (binary.length() < 4) {
                binary = "0" + binary;
            }

            binaryStringBuilder.append(binary);
        }

        // Convert binary string to array of bits
        boolean[] bits = new boolean[binaryStringBuilder.length()];
        for (int i = 0; i < binaryStringBuilder.length(); i++) {
            bits[i] = binaryStringBuilder.charAt(i) == '1';
        }

        return bits;
    }
    

}