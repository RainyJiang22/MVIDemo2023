package com.volcengine.service.visual.impl;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.volcengine.error.SdkError;
import com.volcengine.helper.Const;
import com.volcengine.model.NameValuePair;
import com.volcengine.model.ServiceInfo;
import com.volcengine.model.request.VisualAllAgeGenerationRequest;
import com.volcengine.model.request.VisualHairStyleRequest;
import com.volcengine.model.request.VisualHumanSegmentRequest;
import com.volcengine.model.request.VisualImageStylizationRequest;
import com.volcengine.model.request.VisualJPCartoonRequest;
import com.volcengine.model.response.RawResponse;
import com.volcengine.model.response.VisualAllAgeGenerationResponse;
import com.volcengine.model.response.VisualHairStyleResponse;
import com.volcengine.model.response.VisualHumanSegmentResponse;
import com.volcengine.model.response.VisualImageStylizationResponse;
import com.volcengine.model.response.VisualJPCartoonResponse;
import com.volcengine.service.BaseServiceImpl;
import com.volcengine.service.visual.IVisualService;
import com.volcengine.service.visual.VisualConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jiangshiyu
 * @date 2023/10/30
 */
public class VisualServiceImpl extends BaseServiceImpl implements IVisualService {

    private VisualServiceImpl() {
        super(VisualConfig.serviceInfoMap.get(Const.REGION_CN_NORTH_1), VisualConfig.apiInfoList);
    }

    private VisualServiceImpl(ServiceInfo serviceInfo) {
        super(serviceInfo, VisualConfig.apiInfoList);
    }

    public static IVisualService getInstance() {
        return new VisualServiceImpl();
    }

    public static IVisualService getInstance(String region) throws Exception {
        ServiceInfo serviceInfo = VisualConfig.serviceInfoMap.get(region);
        if (serviceInfo == null) {
            throw new Exception("Edit not support region " + region);
        }
        return new VisualServiceImpl(serviceInfo);
    }
    @NonNull
    @Override
    public VisualJPCartoonResponse jpCartoon(@NonNull VisualJPCartoonRequest request) throws Exception {
        RawResponse response = post(Const.JPCartoon, null, convertNameValuePair(request));
        if (response.getCode() != SdkError.SUCCESS.getNumber()) {
            throw response.getException();
        }
        return JSON.parseObject(new String(response.getData(), "UTF-8"), VisualJPCartoonResponse.class);
    }

    @NonNull
    @Override
    public VisualAllAgeGenerationResponse allAgeGeneration(@NonNull VisualAllAgeGenerationRequest request) throws Exception {
        RawResponse response = json(Const.AllAgeGeneration,null,JSON.toJSONString(request));
        if (response.getCode() != SdkError.SUCCESS.getNumber()) {
            throw response.getException();
        }
        return JSON.parseObject(new String(response.getData(), "UTF-8"), VisualAllAgeGenerationResponse.class);
    }

    @Override
    public VisualHairStyleResponse hairStyle(VisualHairStyleRequest request) throws Exception {
        RawResponse response = json(Const.HairStyle,null,JSON.toJSONString(request));
        if (response.getCode() != SdkError.SUCCESS.getNumber()) {
            throw response.getException();
        }
        return JSON.parseObject(new String(response.getData(),"UTF-8"),VisualHairStyleResponse.class);
    }

    @Override
    public VisualHumanSegmentResponse humanSegment(VisualHumanSegmentRequest request) throws Exception {
        RawResponse response = post(Const.HumanSegment,null,convertNameValuePair(request));
        if (response.getCode() != SdkError.SUCCESS.getNumber()) {
            throw response.getException();
        }
        return JSON.parseObject(new String(response.getData(),"UTF-8"),VisualHumanSegmentResponse.class);
    }

    @Override
    public VisualImageStylizationResponse imageStyle(VisualImageStylizationRequest request) throws Exception {
        RawResponse response = post(Const.HumanSegment,null,convertNameValuePair(request));
        if (response.getCode() != SdkError.SUCCESS.getNumber()) {
            throw response.getException();
        }
        return JSON.parseObject(new String(response.getData(),"UTF-8"),VisualImageStylizationResponse.class);
    }

    private List<NameValuePair> convertNameValuePair(Object obj)
            throws IllegalArgumentException {
        JSONObject jsonObject = (JSONObject) JSON.toJSON(obj);
        List<NameValuePair> list = new ArrayList<>();
        for (String key : jsonObject.keySet()) {
            NameValuePair nameValuePair = new NameValuePair(key, jsonObject.get(key).toString());
            list.add(nameValuePair);
        }
        return list;
    }
}
