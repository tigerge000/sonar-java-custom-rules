package com.finger.web.api.impl;

import com.finger.web.api.ActionTestCase;
import com.finger.web.common.DateUtils;
import com.finger.web.common.HTTPClientMethod;
import com.finger.web.pojo.TResultInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
/**
 * Created by huqingen on 2017/3/10.
 */
@Component
public class ActionTestCaseImpl implements ActionTestCase{

    private static final Log LOGGER= LogFactory.getLog(ActionTestCaseImpl.class);

    private static final String FINGERSSLHOST = "https://stable.fingerapp.cn";

    private DateUtils dateUtils;

    /**
     *
     * @param httpRequestInfo
     * @param assertValue
     * @return
     */
    public TResultInfo runTestCase(JSONObject httpRequestInfo,String assertValue){
        TResultInfo tResultInfo = new TResultInfo();
        try {
            tResultInfo = HTTPTestRun(httpRequestInfo,assertValue);
        } catch (ParseException e) {
            LOGGER.info(e);
        }

        return tResultInfo;
    }

    /**
     * http请求
     * @param httpRequestInfo
     * @param assertValue
     * @return
     */
    public TResultInfo HTTPTestRun(JSONObject httpRequestInfo,String assertValue) throws ParseException {
        HTTPClientMethod httpClientMethod = new HTTPClientMethod();
        TResultInfo resultInfo = new TResultInfo();
        String model = httpRequestInfo.get("model").toString();
        String method = httpRequestInfo.get("method").toString();

        if("delete".equalsIgnoreCase(method) || "get".equalsIgnoreCase(method)) {
            resultInfo = deleteOrGet(httpClientMethod, resultInfo, httpRequestInfo, method, assertValue,model);
            return resultInfo;
        }

        if("post".equalsIgnoreCase(method) || "put".equalsIgnoreCase(method)){
            resultInfo = postOrPut(httpClientMethod,resultInfo,httpRequestInfo,method,assertValue,model);
            return resultInfo;
        }
        resultInfo.setActionresult(0);
        return resultInfo;
    }

    /**
     * 处理header
     * @param headerContent
     * @return
     */

    public Map<String, Object> getHeaders(String headerContent){
        Map<String, Object> headers = new HashMap<String, Object>();
        if (headerContent.length() != 0) {
            JSONObject headerJSON = JSONObject.fromObject(headerContent);//转换成JSON
            Iterator keys = headerJSON.keys();
            while (keys.hasNext()) {
                String key = String.valueOf(keys.next());
                String value = (String) headerJSON.get(key);
                headers.put(key, value);
            }
        }
        return headers;
    }

    /**
     * 处理post或put请求
     * @param httpClientMethod
     * @param resultInfo
     * @param httpRequestInfo
     * @param method
     * @param assertValue
     * @return
     */
    public TResultInfo postOrPut(HTTPClientMethod httpClientMethod,TResultInfo resultInfo,JSONObject httpRequestInfo,String method,String assertValue,String model) throws ParseException {
        //处理Headers
        String headerContent = httpRequestInfo.get("header").toString();
        String paramsContent = httpRequestInfo.get("params").toString();
        String uriContent = httpRequestInfo.get("uri").toString();
        Map<String, Object> headers = getHeaders(headerContent);
        String url = FINGERSSLHOST + uriContent;

        LOGGER.info("URL信息:" + url);
        LOGGER.info("Header信息:" + headerContent);
        JSONObject result = new JSONObject();
        //判断参数是JSONOjcet还是JSONArray
        if ("{".equals(paramsContent.substring(0, 1))) {
            JSONObject paramsJSON = JSONObject.fromObject(paramsContent);//生成JSON格式的参数
            LOGGER.info("Params信息: " + paramsJSON.toString());
            //发送HTTP请求
            try {
                if("HTTPS".equalsIgnoreCase(model)) {
                    if ("post".equalsIgnoreCase(method)) {
                        result = httpClientMethod.postSSLJSONMethod(url, paramsJSON, headers);
                    } else {
                        result = httpClientMethod.putSSLJSONMethod(url, paramsJSON, headers);
                    }
                }else {
                    if ("post".equalsIgnoreCase(method)) {
                        result = httpClientMethod.postJSONMethod(url, paramsJSON, headers);
                    } else {
                        result = httpClientMethod.putJSONMethod(url, paramsJSON, headers);
                    }
                }
                resultInfo.setTestresultinfo(result.toString());
            } catch (Exception e) {
                LOGGER.info(e);
            }

        } else {
            JSONArray paramsJSON = JSONArray.fromObject(paramsContent);//生成JSONArray格式
            LOGGER.info("Params信息: " + paramsJSON.toString());
            //发送HTTP请求
            try {
                if("HTTPS".equalsIgnoreCase(model)) {
                    if ("post".equalsIgnoreCase(method)) {
                        result = httpClientMethod.postSSLJSONArrayMethod(url, paramsJSON, headers);
                    } else {
                        result = httpClientMethod.putSSLJSONArrayMethod(url, paramsJSON, headers);
                    }
                }else {
                    if ("post".equalsIgnoreCase(method)) {
                        result = httpClientMethod.postJSONArrayMethod(url, paramsJSON, headers);
                    } else {
                        result = httpClientMethod.putJSONArrayMethod(url, paramsJSON, headers);
                    }
                }
                resultInfo.setTestresultinfo(result.toString());
            } catch (Exception e) {
                LOGGER.info(e);
            }

        }

        //断言结果
        boolean actionResult = resultAssert(assertValue,result.toString());

        if(actionResult == true){
            resultInfo.setActionresult(1);
        }else {
            resultInfo.setActionresult(2);
        }
        return resultInfo;
    }

    /**
     * 处理delete或get请求
     * @param httpClientMethod
     * @param resultInfo
     * @param httpRequestInfo
     * @param method
     * @param assertValue
     * @param model
     * @return
     */
    public TResultInfo deleteOrGet(HTTPClientMethod httpClientMethod,TResultInfo resultInfo,JSONObject httpRequestInfo,String method,String assertValue,String model) throws ParseException {
        //处理Headers
        String headerContent = httpRequestInfo.get("header").toString();
//        String paramsContent = httpRequestInfo.get("params").toString();
        String uriContent = httpRequestInfo.get("uri").toString();
        Map<String, Object> headers = getHeaders(headerContent);
        String url = FINGERSSLHOST + uriContent;

        LOGGER.info("Header信息:" + headerContent);
        LOGGER.info("URL信息:" + url);

        JSONObject result = new JSONObject();


        //发送HTTP请求
        try {

            if("HTTPS".equalsIgnoreCase(model)) {
                if("get".equalsIgnoreCase(method)) {
                    result = httpClientMethod.getSSLMethod(url, headers);
                }else {
                    result = httpClientMethod.deleteSSLMethod(url,new HashMap<String, Object>(), headers);
                }
            }else {
                if("get".equalsIgnoreCase(method)) {
                    result = httpClientMethod.getMethod(url, headers);
                }else {
                    result = httpClientMethod.deleteMethod(url,new HashMap<String, Object>(), headers);
                }
            }
            resultInfo.setTestresultinfo(result.toString());
        } catch (Exception e) {
            LOGGER.info(e);
        }

        //断言结果
        boolean actionResult = resultAssert(assertValue,result.toString());

        if(actionResult == true){
            resultInfo.setActionresult(1);
        }else {
            resultInfo.setActionresult(2);
        }
        return resultInfo;
    }



    /**
     * 断言处理
     * @param excepContent
     * @param resultContent
     * @return
     */
    public boolean resultAssert(String excepContent,String resultContent) {

        //判断预期结果中是否要判断多种类型,以,进行分割
        if(excepContent.contains(",") && !",".equals(excepContent.substring(excepContent.length()-1))) {
            String[] excepContentList = excepContent.split(",");
            //增加不等于,以 !表示不等于
            ArrayList<String> equalContentList = new ArrayList<String>(); //等于
            ArrayList<String> notEqualContentList = new ArrayList<String>(); //不等于
            for(String str : excepContentList){
                if(str.substring(0,1).contains("!")){
                    notEqualContentList.add(str.substring(1));
                }else {
                    equalContentList.add(str);
                }
            }
            ArrayList<String> failList = new ArrayList<String>();//专门存储失败项
            //遍历等于
            for(String tempStr : equalContentList){
                if(!resultContent.contains(tempStr)){
                    failList.add("FAIL");
                }
            }
            //遍历不等于
            for(String tempStr : notEqualContentList){
                if(resultContent.contains(tempStr)){
                    failList.add("FAIL");
                }
            }
            //判断FailList是否为空
            if(failList.isEmpty()){
                LOGGER.info("测试结果: PASS");
                return true;
            }
            LOGGER.info("测试结果: FAIL");
            return false;
        }else {
            if(!"".equals(excepContent)) {
                if (resultContent.contains(excepContent)) {
                    LOGGER.info("测试结果: PASS ");
                    return true;
                }
                LOGGER.info("测试结果: FAIL");
                return false;
            }
            LOGGER.info("测试结果: FAIL");
            return false;
        }
    }


}
