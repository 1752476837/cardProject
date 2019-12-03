package com.ly.cardadmin.mapper;

        import com.ly.cardadmin.domain.Card;
        import com.ly.cardadmin.utils.BaseMapper;
        import org.springframework.stereotype.Repository;

/**
 * @author Tarry
 * @create 2019/12/2 8:40
 */
@Repository
public interface CardMapper extends BaseMapper<Card,Long> {
}
