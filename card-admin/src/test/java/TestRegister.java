import com.ly.cardadmin.utils.CodeUtil;

/**
 * @author Tarry
 * @create 2019/11/29 14:31
 */
public class TestRegister {
    public static void main(String[] args) {

        //对密码加密【md5】
        String salt= CodeUtil.generateSalt();
        String password = CodeUtil.md5Hex("111111", salt);
        System.out.println("密码："+password);
        System.out.println("盐："+salt);
    }
}
