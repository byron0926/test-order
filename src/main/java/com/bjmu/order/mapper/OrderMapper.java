package com.bjmu.order.mapper;

import com.bjmu.order.domain.Card;
import com.bjmu.order.domain.PayFlow;
import com.bjmu.order.domain.ReqOrder;
import com.bjmu.order.param.PayInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMapper {

    void initOrder(ReqOrder order);

    int queryByOrderId(String merOrderId);

    ReqOrder queryOrderByMerOrderId(String merOrderId);

    void updateOrder(@Param("merOrderId") String merOrderId, @Param("status") int status);

    List<ReqOrder> queryHisOrdersByUserId(String userId);

    List<ReqOrder> queryHisOrdersByUserIdWithStatus(String userId);

    void save(@Param("card") String card, @Param("userId") String userId);

    List<Card> queryCardByUserId(String userId);

    List<PayFlow> queryPayRecord(String userId);

    int savePayRecord(PayInfo info);
}
