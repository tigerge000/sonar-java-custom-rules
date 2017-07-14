package com.finger.web.api;

import com.finger.web.model.HTTPRequestInfo;
import com.finger.web.pojo.TResultInfo;
import net.sf.json.JSONObject;

/**
 * 执行测试用例
 * Created by huqingen on 2017/3/10.
 */
public interface ActionTestCase {

    /**
     * 执行http测试请求
     * @param httpRequestInfo
     * @return
     */
    TResultInfo runTestCase(JSONObject HttpRequestInfo, String assertValue);// Noncompliant
}