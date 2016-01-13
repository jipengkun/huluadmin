package com.hulu.order.web;

import com.hulu.base.core.web.BaseAction;
import com.hulu.base.eazyui.bean.EasyUIDateGrid;
import com.hulu.order.service.OrderService;
import com.hulu.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("order")
public class OrderAction extends BaseAction {
	@Autowired
	private OrderService orderService;

	@RequestMapping("/findFirstOrderByTimeId")
	@ResponseBody
	public String findFirstOrderByTimeId(int timeId) {
		return JsonUtils.ObjtoJson(orderService.findFirstOrderByTimeId(timeId));
	}

	@RequestMapping("/findOrderByTimeId")
	@ResponseBody
	public String findOrderByTimeId(int timeId) {
		EasyUIDateGrid easyUIDateGrid = new EasyUIDateGrid(orderService.findOrderByTimeIdForPage(timeId));
		return JsonUtils.ObjtoJson(easyUIDateGrid);
	}

}
