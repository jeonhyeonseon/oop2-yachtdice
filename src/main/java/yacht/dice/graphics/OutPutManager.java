package yacht.dice.graphics;

import java.io.IOException;
import java.util.Arrays;

public class OutPutManager {
    private final int SECTION_LENGTH = 16;
    private final int DICE_SIZE = 5;
    private final int PRINT_OFFSET = 40;
    private int[] dice;
    private String[] scoreBoard;
    private DrawDice drawDice;
    private boolean[] keep;
    DrawScoreBoard drawScoreBoard;
    DrawPredictedResult drawPredictedResult;

    public boolean[] getKeep() {
        return keep;
    }

    public void setKeep(boolean[] keep) {
        this.keep = keep;
    }

    public int[] getDice() {
        return dice;
    }

    public void setDice(int[] dice) {
        this.dice = dice;
    }

    //constructor
    public OutPutManager() {
        this.drawDice = new DrawDice();
        this.dice = new int[DICE_SIZE];
        this.keep = new boolean[DICE_SIZE];
        this.scoreBoard = new String[SECTION_LENGTH];
        Arrays.fill(scoreBoard, "+0");
        this.drawScoreBoard = new DrawScoreBoard();
        this.drawPredictedResult = new DrawPredictedResult();
        Arrays.fill(this.dice, 6);
        Arrays.fill(this.keep, false);
    }

    //타이틀 출력
    public void printTitle(){
        boolean[] tempKeep = new boolean[DICE_SIZE];
        Arrays.fill(tempKeep, true);
        Title title = new Title();

        for(int j = 0 ; j < DICE_SIZE ; j++) {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                clearConsole();
                System.out.print(addOffset(drawDice.drawAnime(dice, tempKeep), PRINT_OFFSET));
            }
            tempKeep[j] = false;
        }
        clearConsole();
        System.out.println(addOffset(drawDice.draw(dice), PRINT_OFFSET));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        clearConsole();
        System.out.print(addOffset(title.getTitle(), PRINT_OFFSET+5));
    }

    /**
     * 매개변수를 지정하지 않으면 현재 객체에 저장된 데이터를 참조하여 출력함
     */
    public void printScreen(){
        Arrays.fill(keep,false);
        printDice(this.dice, keep);
    }

    /**
     * 주사위 입력을 받아 출력함
     * 주사위를 굴릴때 사용
     * @param dice
     * @param keep
     */
    public void printScreen(int[] dice, boolean[] keep){
        this.dice = dice;
        this.keep = keep;
        rollAnime(dice, keep);
        clearConsole();
        System.out.println(drawScoreBoard.printScoreBoard());
        System.out.println(addOffset(drawDice.draw(dice), PRINT_OFFSET));
        System.out.println(addOffset(drawPredictedResult.draw(scoreBoard), 10));
    }

    //점수판만 입력 받아 화면을 갱신하는 메소드
    //주사위는 기존 입력을 유지함
    //점수판 갱신시 사용
    public void printScreen(String[] scoreBoard){
        this.scoreBoard = scoreBoard;
        clearConsole();
        System.out.println(drawScoreBoard.printScoreBoard());
        System.out.println(drawDice.draw(dice));
        System.out.println(addOffset(drawPredictedResult.draw(scoreBoard), 10));
    }



/*
    private void printScoreBoard(ScoreBoard scoreBoard){

    }
*/

    private void printDice(int[] dice, boolean[] keep){
        System.out.println(drawDice.draw(dice));
    }

    private void rollAnime(int[] dice, boolean[] keep){
        for (int i = 0; i< 10; i++){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            clearConsole();
            System.out.println(drawScoreBoard.printScoreBoard());
            System.out.print(addOffset(drawDice.drawAnime(dice, keep), PRINT_OFFSET));

        }
    }

    private static void clearConsole(){
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private String addOffset(String diceStr, int offset){
        StringBuilder result = new StringBuilder();
        String[] diceSubStr = diceStr.split("\n");
        for(String subStr : diceSubStr){
            result.append(" ".repeat(offset));
            result.append(subStr);
            result.append("\n");
        }
        return result.toString();
    }
}
