package com.hulu.system.payment.unionpay.service;

import com.hulu.system.payment.unionpay.core.UnionpayUtil;
import com.unionpay.acp.sdk.SDKConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 名称： 第一卷 商户卷 第1\5\6部分 跳转网关支付产品\手机控件支付产品\手机网页支付产品<br>
 * 功能：6.5　交易状态查询交易<br>
 * 版本： 5.0<br>
 * 日期： 2014-07<br>
 * 作者： 中国银联ACP团队<br>
 * 版权： 中国银联<br>
 * 说明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己需要，按照技术文档编写。该代码仅供参考。<br>
 */
@Service
public class UnionpayQueryService {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UnionpayUtil unionpayUtil;

	public Map<String, String> queryPayState(String orderId,Date txnDate) throws InterruptedException {
		/**
		 * 组装请求报文
		 */
		Map<String, String> data = new HashMap<String, String>();
		// 版本号
		data.put("version", UnionpayUtil.version);
		// 字符集编码 默认"UTF-8"
		data.put("encoding", UnionpayUtil.encoding);
		// 签名方法 01 RSA
		data.put("signMethod", "01");
		// 交易类型 
		data.put("txnType", "00");
		// 交易子类型 
		data.put("txnSubType", "00");
		// 业务类型
		data.put("bizType", "000000");
		// 渠道类型，07-PC，08-手机
		data.put("channelType", "08");
		// 接入类型，商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
		data.put("accessType", "0");
		// 商户号码，请改成自己的商户号
		data.put("merId", unionpayUtil.getUnionpayMerId());
		// 商户订单号，请修改被查询的交易的订单号
		data.put("orderId", orderId);
		// 订单发送时间，请修改被查询的交易的订单发送时间
		data.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(txnDate));

		data = unionpayUtil.signData(data);

		// 交易请求url 从配置文件读取
		String url = SDKConfig.getConfig().getSingleQueryUrl();

		Map<String, String> resmap = unionpayUtil.submitUrl(data, url);

		log.info("请求报文=[" + data.toString() + "]");
		log.info("应答报文=[" + resmap.toString() + "]");

		return resmap;

	}

}
