package com.hulu.order.service;

import com.hulu.diagnoseTime.service.DiagnoseTimeService;
import com.hulu.order.bean.OrderBean;
import com.hulu.order.bean.OrderPayStateEnum;
import com.hulu.order.dao.OrderDao;
import com.hulu.order.exception.CannotUpdateOrderPayState;
import com.hulu.subject.bean.SubjectBean;
import com.hulu.subject.dao.SubjectDao;
import com.hulu.system.payment.unionpay.service.UnionpayQueryService;
import com.hulu.util.ConfigUtil;
import com.hulu.util.RedisUtil;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private UnionpayQueryService unionpayQueryService;

	@Autowired
	private DiagnoseTimeService diagnoseTimeService;

	@Transactional(propagation= Propagation.REQUIRED)
	public int delete(int id){
		return orderDao.delete(id);
	}

	@Transactional(propagation= Propagation.REQUIRED, readOnly = true)
	public List<OrderBean> list(){
		return orderDao.list();
	}

	@Transactional(propagation= Propagation.REQUIRED, readOnly = true)
	public OrderBean get(int id){
		return orderDao.get(id);
	}

	@Transactional(propagation= Propagation.REQUIRED, readOnly = true)
	public OrderBean findFirstOrderByTimeId(int timeId){
		return orderDao.findFirstOrderByTimeId(timeId);
	}

	@Transactional(propagation= Propagation.REQUIRED, readOnly = true)
	public List<OrderBean> findOrderByTimeIdForPage(int timeId){
		return orderDao.findOrderByTimeIdForPage(timeId);
	}

	@Transactional(propagation= Propagation.REQUIRED, readOnly = true)
	public List<OrderBean> findOrderByParam(Map<String,Object> param){
		return orderDao.findOrderByParam(param);
	}

	@Transactional(propagation= Propagation.REQUIRED)
	public void updateOrderPayState(int orderId) {
		OrderBean orderBean = orderDao.get(orderId);
		Map<String,String> queryOrderResult = null;
		if(orderBean != null){
			try {
				queryOrderResult = unionpayQueryService.queryPayState(orderBean.getOrderSequence(),orderBean.getCreateTime());
			} catch (InterruptedException e) {
				throw new CannotUpdateOrderPayState("查询银联订单支付结果发生错误！", e);
			}
		}
		Integer currentOrderState = queryOrderResult != null ? Integer.parseInt(queryOrderResult.get("respCode")) : null;
		if(currentOrderState != null){
			if(currentOrderState.equals("00") || currentOrderState.equals("A6")){
				currentOrderState = OrderPayStateEnum.SUCCESS.val();
			}else if(currentOrderState.equals("03") || currentOrderState.equals("04") ||
					currentOrderState.equals("05") || currentOrderState.equals("01") ||
					currentOrderState.equals("12") || currentOrderState.equals("34") ||
					currentOrderState.equals("60")){
				currentOrderState = OrderPayStateEnum.PAYING.val();
			}else{
				currentOrderState = OrderPayStateEnum.FAIL.val();
			}
		}else{
			logger.error("查询银联订单支付结果没有获取到任何信息！订单ID：{}创建时间：{}接口返回消息：{}", new Object[]{
					orderBean.getOrderSequence(),orderBean.getCreateTime(),queryOrderResult.toString()
			});
			throw new CannotUpdateOrderPayState("查询银联订单支付结果没有获取到任何信息！");
		}

		//更新支付状态
		orderDao.updateOrderPayState(orderBean.getId(),currentOrderState);
		if(currentOrderState.equals(OrderPayStateEnum.FAIL.val())){
			diagnoseTimeService.updateDiagnoseNumbalance(orderBean.getTimeId());
		}
	}

}
