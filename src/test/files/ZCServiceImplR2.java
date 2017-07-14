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
	 * 账单推送
	 * 
	 * @param message
	 * @return
	 */
	public DispatchTypeFirefly queryDate(List<Map> temp) {
		DispatchTypeFirefly disQzjI = new DispatchTypeFirefly();
		JMSLog.instance.log("temp-=====" + temp);
		disQzjI.setDataType(nullToString(temp.get(0).get("dataType")));
		disQzjI.setTargetId(nullToString(temp.get(0).get("targetId")));
		disQzjI.setTaskId(nullToString(temp.get(0).get("msgId")));
		disQzjI.setAuto(false);
		disQzjI.setPriority(4);
		disQzjI.setTimeout(40);// 秒
		disQzjI.setStipulationName("pushMess");
		disQzjI.setToken(nullToString(temp.get(0).get("msgId")));
		disQzjI.setMsgId(disQzjI.getToken());
		disQzjI.setObj(temp);
		disQzjI.setBusiProc(nullToString(temp.get(0).get("busiProc")));
		disQzjI.setSource(nullToString(temp.get(0).get("source")));
		disQzjI.setDesIfno(nullToString(temp.get(0).get("desIfno")));
		disQzjI.setClientID("clientId");
		disQzjI.setAgreementId(nullToString(temp.get(0).get("agreementId")));
		disQzjI.setAppId(nullToString(temp.get(0).get("appId")));
		disQzjI.setUserId(nullToString(temp.get(0).get("userId")));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		disQzjI.setMsgTime(sdf.format(new Date()));
		DispatchTypeFirefly r = this.connWebHost(disQzjI);
		return r;

	}

	public DispatchTypeFirefly queryDateF(List<Map> temp) {
		DispatchTypeFirefly disQzjI = new DispatchTypeFirefly();
		JMSLog.instance.log("temp-=====" + temp);
		disQzjI.setDataType(nullToString(temp.get(0).get("dataType")));
		disQzjI.setTargetId(nullToString(temp.get(0).get("targetId")));
		disQzjI.setTaskId(nullToString(temp.get(0).get("msgId")));
		disQzjI.setAuto(false);
		disQzjI.setPriority(4);
		disQzjI.setTimeout(50);// 秒
		disQzjI.setStipulationName("pushMess");
		disQzjI.setToken(nullToString(temp.get(0).get("msgId")));
		disQzjI.setMsgId(disQzjI.getToken());

		disQzjI.setBusiProc(nullToString(temp.get(0).get("busiProc")));
		disQzjI.setSource(nullToString(temp.get(0).get("source")));
		disQzjI.setDesIfno(nullToString(temp.get(0).get("desIfno")));
		disQzjI.setClientID("clientId");
		disQzjI.setAgreementId(nullToString(temp.get(0).get("agreementId")));
		disQzjI.setAppId(nullToString(temp.get(0).get("appId")));
		disQzjI.setUserId(nullToString(temp.get(0).get("userId")));
		disQzjI.setVersion(nullToString(temp.get(0).get("version")));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		disQzjI.setMsgTime(sdf.format(new Date()));
		temp.get(0).remove("dataType");
		temp.get(0).remove("targetId");
		temp.get(0).remove("busiProc");
		temp.get(0).remove("source");
		temp.get(0).remove("desIfno");
		temp.get(0).remove("msgId");
		disQzjI.setObj(temp);
		DispatchTypeFirefly r = this.connWebHost(disQzjI);
		if (r != null) {
			JMSLog.instance.log("tempret-=====" + r.getObj());
		}
		return r;

	}

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

	public String nullToString(Object src) {
		if (src == null)
			return "";
		else
			return src.toString();
	}
}
