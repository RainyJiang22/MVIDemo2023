package com.volcengine.service.visual;

import com.volcengine.model.request.VisualAllAgeGenerationRequest;
import com.volcengine.model.request.VisualHairStyleRequest;
import com.volcengine.model.request.VisualJPCartoonRequest;
import com.volcengine.model.response.VisualAllAgeGenerationResponse;
import com.volcengine.model.response.VisualHairStyleResponse;
import com.volcengine.model.response.VisualJPCartoonResponse;
import com.volcengine.service.IBaseService;



/**
 * @author jiangshiyu
 * @date 2023/10/30
 */
public interface IVisualService extends IBaseService {


    /**
     * 人像漫画风
     * @param request
     * @return
     * @throws Exception
     */
    VisualJPCartoonResponse jpCartoon(VisualJPCartoonRequest request) throws Exception;

    /**
     * 年龄变化
     * @param request
     * @return
     * @throws Exception
     */
    VisualAllAgeGenerationResponse allAgeGeneration(VisualAllAgeGenerationRequest request) throws Exception;


    /**
     * 发型编辑
     * @return
     * @throws Exception
     */
    VisualHairStyleResponse hairStyle(VisualHairStyleRequest request) throws Exception;


}