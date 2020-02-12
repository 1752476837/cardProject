import com.ly.card.CardApplication;
import com.ly.card.controller.CardController;
import com.ly.card.controller.UserController;
import com.ly.card.domain.Card;
import com.ly.card.mapper.CardMapper;
import com.ly.card.service.CardService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author Tarry
 * @create 2019/12/7 15:13
 */
@SpringBootTest(classes = CardApplication.class)
@RunWith(SpringRunner.class)
public class test {

    @Autowired
    CardService cardService;
    @Autowired
    CardController cardController;
    @Test
    public void get(){
        List<Card> cardList = cardService.queryCardListByUserId(7L);
        System.out.println(cardList);
    }
    @Test
    public void test2(){

        cardController.msgSecCheck("我爱你");

    }
}
