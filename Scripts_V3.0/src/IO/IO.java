package IO;

import java.util.Scanner;

/**
 * 输入输出
 */
public abstract class IO {
    private Scanner input = new Scanner(System.in);

    /**输出 */
    public void _Speak(String str) {
        System.out.println(str);
    }

    /**输入Int */
    public int _ReadInt() {
        try {
            return input.nextInt();
        } catch (Exception e) {
            _Speak("输入错误！请重新输入：");
            input.nextLine();
            return _ReadInt();
        }        
    }

    /**输入String */
    public String _ReadStr() {
        try {
            return input.next();
        } catch (Exception e) {
            _Speak("输入错误！请重新输入：");
            input.nextLine();
            return _ReadStr();
        }
    }

    /**输入Float */
    public float _ReadFloat() {
        try {
            return input.nextFloat();
        } catch (Exception e) {
            _Speak("输入错误！请重新输入：");
            input.nextLine();
            return _ReadFloat();
        }
    }
}
