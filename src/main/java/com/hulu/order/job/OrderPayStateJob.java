package com.hulu.order.job;

import com.hulu.order.bean.OrderBean;
import com.hulu.order.bean.OrderPayStateEnum;
import com.hulu.order.service.OrderService;
import com.hulu.system.payment.unionpay.service.UnionpayQueryService;
import com.hulu.util.ConfigUtil;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangyiwei on 15/11/2.
 */
@Service
public class OrderPayStateJob {

    private Logger logger = LoggerFactory.getLogger(OrderPayStateJob.class);

    @Value("${order.pay.timeout}")
    private static int orderPayTimeOut;

    @Autowired
    private OrderService orderService;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void updateOrderPayStateJob(){
        logger.info("开始更新订单状态。。。");
        Map<String,Object> queryOrderParam = new HashMap<String,Object>();
        queryOrderParam.put("paystat" + OrderPayStateEnum.UN_PAY.val(),OrderPayStateEnum.UN_PAY.val());
        queryOrderParam.put("paystat" + OrderPayStateEnum.PAYING.val(),OrderPayStateEnum.PAYING.val());
        List<OrderBean> orderBeanList = orderService.findOrderByParam(queryOrderParam);
        if(orderBeanList != null){
            logger.info("有{}个订单状态需要更新。", orderBeanList.size());
            for(OrderBean orderBean : orderBeanList){
                try {
                    if(System.currentTimeMillis() - orderBean.getCreateTime().getTime() > (orderPayTimeOut + 3) * 1000 * 60){
                        orderService.updateOrderPayState(orderBean.getId());
                    }
                } catch (Exception e) {
                    logger.error("更新订单支付状态定时任务发生错误！订单ID：{}",
                            new Object[]{orderBean.getId(), e});
                }
            }
        }
        logger.info("更新订单状态结束。。。");
    }

}
