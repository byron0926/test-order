package com.bjmu.order.mapper;

import com.bjmu.order.domain.union.param.SelectOrderParam;
import com.bjmu.order.domain.union.persistence.PerOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnionOrderMapper {

    int insertOrder(PerOrder order);

    List<PerOrder> queryOrders(SelectOrderParam param);

    void updatePayerId(@Param("orderId") String orderId,@Param("payerId") String payerId);

    String getTxnTimeByOrderId(String orderId);

    void updateQueryId(@Param("queryId") String queryId,@Param("orderId") String orderId);

    PerOrder queryOrdersByOrderId(String orderId);
    List<PerOrder> queryOrdersByUserId(@Param("userId") String userId,@Param("flag") int flag);

    void updateStatusByOrderId(@Param("orderId") String orderId,@Param("queryId") String queryId,@Param("payType") String payType);

    void updateSubMerOrderId(@Param("orderId") String orderId,@Param("subMerOrderId") String subMerOrderId);
}
