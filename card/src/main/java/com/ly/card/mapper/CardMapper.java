package com.ly.card.mapper;


import com.ly.card.domain.Card;
import com.ly.card.domain.vo.BaseCard;
import com.ly.card.utils.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Tarry
 * @create 2019/12/2 8:40
 */
@Repository
public interface CardMapper extends BaseMapper<Card,Long> {

    @Select("select count(*) from card where user_id = #{id}")
    int queryCountByUserId(Long userId);

    @Insert("insert into card (user_id,file,file_type) values (#{userId},#{file},#{fileType})")
    void addFileUrl(@Param("userId") Long userId, @Param("file") String fileUrl,@Param("fileType") String type);

    @Select("select name,phone,company,position,head_image from card where user_id = #{id}")
    Card getMyCard(Long userId);

    //查询收藏列表
    @Select("select card.name,card.phone,card.company,card.position,card.head_image from collect,card where collect.user_id =#{id} and card.id = collect.card_id")
    List<Card> queryCardListByUserId(Long userId);

    //添加收藏
    @Insert("insert into collect(user_id,card_id) values (#{userId},#{cardId})")
    void addCollect(@Param("userId") Long userId,@Param("cardId") Long cardId);


    //移除收藏
    @Delete("delete from collect where user_id=#{userId} and card_id = #{cardId}")
    void removeCollect(@Param("userId") Long userId,@Param("cardId") Long cardId);

    //校验当前名片是否已收藏
    @Select("select * from collect where user_id=#{userId} and card_id = #{cardId}")
    int verifyCollect(@Param("userId") Long userId,@Param("cardId") Long cardId);

//    //修改基本信息
//    @Update("update card set name = #{baseCard.name},phone = #{baseCard.phone},company = #{baseCard.company},position = #{baseCard.position},address = #{baseCard.address},mail = #{baseCard.mail}")
//    void updateBaseInfo(@Param("userId") Long userId, @Param("baseCard") BaseCard baseCard);

    //修改信息
    @Update("update card set  name=#{card.name}, phone=#{card.phone}, company=#{card.company}, position=#{card.position}, address=#{card.address}, mail=#{card.mail}, head_image=#{card.headImage}, wx_code=#{card.wxCode}, image=#{card.image}, video=#{card.video}, update_time=#{card.updateTime}  where user_id = #{userId}")
    void updateInfo(@Param("userId") Long userId, @Param("card") Card card);
}
