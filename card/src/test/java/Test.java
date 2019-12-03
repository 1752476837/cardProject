
import com.ly.card.CardApplication;
import com.ly.card.domain.Card;
import com.ly.card.service.CardService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author Tarry
 * @create 2019/12/3 14:18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CardApplication.class)
public class Test {
    @Autowired
    private CardService cardService;

    //创建名片
    @org.junit.Test
    public void testService1(){
        Card card = new Card();
        card.setName("张铁瑞");
        card.setPhone("15127812872");
        card.setCompany("大公司");
        card.setPosition("总经理");
        card.setAddress("河北科技师范学院");
        card.setMail("1111@qq.com");
        card.setHeadImage("https://www.baidu.com/img/bd_logo1.png");
        card.setWxCode("https://www.baidu.com/img/bd_logo1.png");
        card.setImage("[\"https://www.baidu.com/img/bd_logo1.png\"]");
        card.setVideo("https://www.baidu.com/img/bd_logo1.png");
        cardService.addCard(20L,card);


    }

    //浏览自己名片
    @org.junit.Test
    public void testService2(){
        Card myCard = cardService.getMyCard(20L);
        System.out.println(myCard);
    }

    //根据id 获取详细资料
    @org.junit.Test
    public void test3(){
        Card cardDetail = cardService.getCardDetail(20L);
        System.out.println(cardDetail);

    }

    //获取收藏列表
    @org.junit.Test
    public void test4(){
        List<Card> cardList = cardService.queryCardListByUserId(20L);
        System.out.println(cardList);
    }
    //添加收藏
    @org.junit.Test
    public void test5(){
        cardService.addCollect(20L,4L);
    }

    //移除收藏
    @org.junit.Test
    public void test06(){
        cardService.removeCollect(20L,3L);
    }


    //检验是否收藏的该物品
    @org.junit.Test
    public void test07(){
        Boolean aBoolean = cardService.verifyCollect(20L, 2L);
        System.out.println(aBoolean);
    }
}
