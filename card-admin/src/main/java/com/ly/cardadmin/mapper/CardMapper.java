package com.ly.cardadmin.mapper;

        import com.ly.cardadmin.domain.Card;
        import com.ly.cardadmin.utils.BaseMapper;
        import org.apache.ibatis.annotations.Param;
        import org.apache.ibatis.annotations.Update;
        import org.springframework.stereotype.Repository;

/**
 * @author Tarry
 * @create 2019/12/2 8:40
 */
@Repository
public interface CardMapper extends BaseMapper<Card,Long> {

    @Update("update card set name = #{card.name},phone = #{card.phone},company = #{card.company},position = #{card.position},address = #{card.address},mail  = #{card.mail}, update_time = #{card.updateTime} where id = #{card.id}")
    void updateCard(@Param("card") Card card);
}
