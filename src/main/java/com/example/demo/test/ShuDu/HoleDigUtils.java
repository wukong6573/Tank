package com.example.demo.test.ShuDu;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class HoleDigUtils {

    private Random random = new Random();

    public void digHolesByGameDifficulty(DifficultyLevel level) {
        int[][] array = ShuDu.generateShuDu();
        for (int i = 0; i < array.length; i++) {
            for (int n = 0; n < array[i].length; n++) {//一行中有多少个元素（即多少列）
                System.out.print(array[i][n] + " ");
            }
            System.out.println();
        }
        System.out.println("============================================================");
        int randomInt = 0;
        randomInt = getRandomNumberByLevel(level);
        int[][] arrayQuestion = getQuestionArray(randomInt, array);
        for (int i = 0; i < arrayQuestion.length; i++) {
            for (int j = 0; j < arrayQuestion.length; j++) {
                System.out.print(arrayQuestion[i][j] + " ");
            }
            System.out.println();
        }

        JSONArray jsonArray = JSONArray.fromObject(arrayQuestion);
        System.out.println(jsonArray);
    }

    private int[][] getQuestionArray(int randomInt, int[][] array) {
        int[][] arrayQuestion = new int[9][9];
        for (int i = 0; i < array.length; i++) {
        Map<Integer, Integer> map = new HashMap<>();
            //每一行，生成 randomInt 个0-8随机数，每个数代表这一行中的位置，并把这个位置的数变成0，来实现“挖空”的效果
            while (map.size() < randomInt) {
                Random random = new Random();
                int nextInt = random.nextInt(9);
                map.put(nextInt, nextInt);
            }
            for (int n = 0; n < array[i].length; n++) {//一行中有多少个元素（即多少列）
                if (map.size() > 0) {
                    Set<Integer> keys = map.keySet();
                    for (Integer key : keys) {
                        if (key == n) {
                            arrayQuestion[i][n] = 0;
                            break;
                        } else {
                            arrayQuestion[i][n] = array[i][n];
                        }
                    }
                }
            }
        }
        return arrayQuestion;
    }


    /**
     * 根据不同的游戏难度，获取随机数
     */
    public int getRandomNumberByLevel(DifficultyLevel level) {
        int randomValue = 5;

//        switch (level) {
//            case EASY:
//                /**
//                 * 产生随机数[4,5]
//                 */
//                randomValue = random.nextInt(2) + 4;
//                break;
//            case MEDIUM:
//                /**
//                 * 产生随机数[5,7]
//                 */
//                randomValue = random.nextInt(3) + 5;
//                break;
//            case DIFFICULT:
//                /**
//                 * 产生随机数[5,8]
//                 */
//                randomValue = random.nextInt(4) + 5;
//                break;
//            case EVIL:
//                /**
//                 * 产生随机数[6,9]
//                 */
//                randomValue = random.nextInt(4) + 6;
//                break;
//            default:
//                break;
//
//        }

        switch (level) {
            case EASY:
                /**
                 * 产生随机数[4,5]
                 */
                randomValue = random.nextInt(2) + 4;
                break;
            case MEDIUM:
                /**
                 * 产生随机数[2,3]
                 */
                randomValue = random.nextInt(2) + 2;
                break;
            case DIFFICULT:
                /**
                 * 产生随机数[4,5]
                 */
                randomValue = random.nextInt(2) + 4;
                break;
            case EVIL:
                /**
                 * 产生随机数[6,7]
                 */
                randomValue = random.nextInt(2) +6;
                break;
            default:
                break;

        }
        return randomValue;
    }

    private int[] populateRandomArray(int numOfRandoms) {
        int[] array = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        int randomInt = 0;
        for (int i = 0; i < 20; i++) {
            randomInt = random.nextInt(8) + 1;
            int temp = array[0];
            array[0] = array[randomInt];
            array[randomInt] = temp;
        }

        int[] result = new int[numOfRandoms];

        System.arraycopy(array, 0, result, 0, numOfRandoms);

        return result;
    }

    public static void main(String[] args) throws IOException {
        HoleDigUtils digger = new HoleDigUtils();

        digger.digHolesByGameDifficulty(DifficultyLevel.EVIL);
    }
}


