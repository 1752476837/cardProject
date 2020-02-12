package com.ly.cardadmin.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ly.cardadmin.domain.Card;
import com.ly.cardadmin.mapper.CardMapper;
import com.ly.cardadmin.utils.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author Tarry
 * @create 2019/12/2 8:40
 */
@Service
public class CardService {
    @Autowired
    private CardMapper cardMapper;
    public PageResult<Card> queryList(String name, String phone, Date startDate,Date endDate, Integer currentPage, Integer pageSize, Integer pageTotal) {
        //分页
        PageHelper.startPage(currentPage,pageSize);
        //过滤条件
        Example example = new Example(Card.class);
        Example.Criteria criteria = example.createCriteria();
        //根据名字过滤
        if (StringUtils.isNoneBlank(name)){
            criteria.andLike("name","%"+name+"%");
        }
        //根据类型过滤
        if (StringUtils.isNotBlank(phone)){
            criteria.andLike("phone","%"+phone+"%");
        }
        //根据时间过滤
        if (startDate != null && endDate != null){
            criteria.andBetween("createTime",startDate,endDate);
        }
        //默认按时间排序
        example.setOrderByClause("create_time DESC");

        List<Card> cardList = cardMapper.selectByExample(example);

        //解析分页结果
        PageInfo<Card> cardPageInfo = new PageInfo<Card>(cardList);

        return new PageResult<Card>(cardPageInfo.getTotal(), cardList);
    }


    //根据id查看名片详情
    public Card queryCardById(Long id) {
       return cardMapper.selectByPrimaryKey(id);
    }


    /**
     * 导出全部名片信息
     * @return
     */
    public List<Card> queryAllCard() {
        List<Card> cards = cardMapper.selectAll();
        return cards;
    }

    /**
     * 导出选中的名片信息
     * @param ids
     * @return
     */
    public List<Card> querySelectedCard(List<Long> ids) {
        List<Card> cards = cardMapper.selectByIdList(ids);
        return cards;
    }

    /**
     * 删除指定的名片信息
     * @param ids
     */
    public void deleteCard(List<Long> ids) {
        cardMapper.deleteByIdList(ids);
    }

    /**
     * 修改名片基本信息
     * @param card
     */
    public void updateCard(Card card) {
        card.setUpdateTime(new Date());
        cardMapper.updateCard(card);
    }
}
