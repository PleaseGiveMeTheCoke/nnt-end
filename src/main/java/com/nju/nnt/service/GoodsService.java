package com.nju.nnt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nju.nnt.entity.Goods;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface GoodsService {
    void publishGoods(Goods goods);

    List<Goods> getGoodsList(int page,int size,Map<String,Object> conditionMap);

    IPage<Goods> listGoodsPerPage(int page, int size);

    Goods getGoodsDetail(Long goodsId);
}
