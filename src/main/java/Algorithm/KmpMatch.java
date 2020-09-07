package Algorithm;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangyang on 4/15/2017.
 */
public class KmpMatch {
    String pattern;
    int[] subStringLength;

    public KmpMatch(String pattern) {
        this.pattern = pattern;
        this.subStringLength = new int[pattern.length()];

        searchInPattern();
    }

    public List<Integer> match(String text) {
        ArrayList<Integer> matchedIndex = new ArrayList<Integer>();

        int patternIdx = 0;

        for (int textIdx = 0; textIdx < text.length(); textIdx++) {
            if (text.charAt(textIdx) == pattern.charAt(patternIdx)) {
                patternIdx++;
            }
            else {
                while (patternIdx != 0 && text.charAt(textIdx) != pattern.charAt(patternIdx)) {
                    patternIdx = subStringLength[patternIdx - 1];
                }
                patternIdx = patternIdx == 0 ? 0 : subStringLength[patternIdx - 1];
            }

            if (patternIdx == pattern.length()) {
                matchedIndex.add(textIdx - pattern.length() + 1);
                patternIdx = subStringLength[patternIdx - 1];
                System.out.println("After matching, the patternIdx goes to " + patternIdx);
            }
        }

        return matchedIndex;
    }

    public void searchInPattern() {
        int j = 0;

        subStringLength[0] = 0;

        for (int i = 1; i < subStringLength.length; i++) {
            if (pattern.charAt(i) == pattern.charAt(j)) {
                subStringLength[i] = j + 1;
                j++;
            }
            else {
                while (j != 0 && pattern.charAt(j) != pattern.charAt(i)) {
                    j = subStringLength[j - 1];
                }

                subStringLength[i] = pattern.charAt(i) == pattern.charAt(j) ? j + 1 : 0;
            }
        }
    }

    public void testSubstringMatch() {
        System.out.println(Arrays.toString(subStringLength));
    }

    public static void main(String[] args) {
        KmpMatch match = new KmpMatch("acaca");

        match.testSubstringMatch();

        List<Integer> result = match.match("acacabacacabacacac");


        for (int idx : result) {
            System.out.println(idx);
        }
    }
}
