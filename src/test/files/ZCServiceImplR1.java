package com.ls.lstrade.firefly.pdeductpay.service.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ls.athena.firefly.impl.DispatchTypeFirefly;
import com.ls.athena.framework.callmessage.spi.CallMessage;
import com.ls.athena.tradding.logrec.service.JMSLog;
import com.ls.lstrade.firefly.pdeductpay.service.ZCServiceR;
import com.ls.pf.base.api.framework.Service;
import com.ls.pf.base.api.framework.ServiceType;

@Service(target = { ServiceType.APPLICATION })
public class ZCServiceImplR implements ZCServiceR {

	/**
	 * 召测方式推送
	 * 
	 * @param message
	 * @return
	 */
	public DispatchTypeFirefly connWebHost(DispatchTypeFirefly disQzjI) {

		DispatchTypeFirefly disQzjRtnI = new DispatchTypeFirefly();
		try {
			// 调用方法发送请求

			String token = CallMessage.send(disQzjI);
			int msgStateI = CallMessage.getState(token);

			while (msgStateI == CallMessage.DISPOSING) {
				Thread.sleep(100);
				msgStateI = CallMessage.getState(token);
			}
			disQzjRtnI = (DispatchTypeFirefly) CallMessage.get(token);

			disQzjRtnI.getObj();
		} catch (Exception e) {
			// e.printStackTrace();
			List<Map> obj = new ArrayList<Map>();
			Map temp = new HashMap();
			temp.put("rtnCode", "1111");
			temp.put("msgId", disQzjI.getToken());
			temp.put("rtnMsg", e.getMessage());
			obj.add(temp);
			disQzjRtnI = new DispatchTypeFirefly();
			disQzjRtnI.setObj(obj);

		}
		return disQzjRtnI;

	}
}
