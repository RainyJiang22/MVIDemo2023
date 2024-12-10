package com.volcengine.service.visual;

import com.volcengine.helper.Const;
import com.volcengine.model.ApiInfo;
import com.volcengine.model.Credentials;
import com.volcengine.model.Header;
import com.volcengine.model.NameValuePair;
import com.volcengine.model.ServiceInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VisualConfig {

    public static Map<String, ServiceInfo> serviceInfoMap = new HashMap<String, ServiceInfo>() {
        {
            put(Const.REGION_CN_NORTH_1, new ServiceInfo(
                    new HashMap<String, Object>() {
                        {
                            put(Const.CONNECTION_TIMEOUT, 5000);
                            put(Const.SOCKET_TIMEOUT, 5000);
                            put(Const.Scheme, "https");
                            put(Const.Host, "visual.volcengineapi.com");
                            put(Const.Header, new ArrayList<Header>() {
                                {
                                    add(new Header("Accept", "application/json"));
                                }
                            });
                            put(Const.Credentials, new Credentials(Const.REGION_CN_NORTH_1, "cv"));
                        }
                    }
            ));
            put(Const.REGION_AP_SINGAPORE_1, new ServiceInfo(
                    new HashMap<String, Object>() {
                        {
                            put(Const.CONNECTION_TIMEOUT, 5000);
                            put(Const.SOCKET_TIMEOUT, 5000);
                            put(Const.Scheme, "https");
                            put(Const.Host, "open-ap-singapore-1.volcengineapi.com");
                            put(Const.Header, new ArrayList<Header>() {
                                {
                                    add(new Header("Accept", "application/json"));
                                }
                            });
                            put(Const.Credentials, new Credentials(Const.REGION_AP_SINGAPORE_1, "cv"));
                        }
                    }
            ));
        }
    };

    public static Map<String, ApiInfo> apiInfoList = new HashMap<String, ApiInfo>() {
        {
            put(Const.JPCartoon, new ApiInfo(
                    new HashMap<String, Object>() {
                        {
                            put(Const.Method, "POST");
                            put(Const.Path, "/");
                            put(Const.Query, new ArrayList<NameValuePair>() {
                                {
                                    add(new NameValuePair("Action", Const.JPCartoon));
                                    add(new NameValuePair("Version", "2020-08-26"));
                                }
                            });
                        }
                    }
            ));

            put(Const.AllAgeGeneration, new ApiInfo(
                    new HashMap<String, Object>() {
                        {
                            put(Const.Method, "POST");
                            put(Const.Path, "/");
                            put(Const.Query, new ArrayList<NameValuePair>() {
                                {
                                    add(new NameValuePair("Action", Const.AllAgeGeneration));
                                    add(new NameValuePair("Version", "2022-08-31"));
                                }
                            });
                        }
                    }
            ));

            put(Const.HairStyle, new ApiInfo(
                    new HashMap<String, Object>() {
                        {
                            put(Const.Method, "POST");
                            put(Const.Path, "/");
                            put(Const.Query, new ArrayList<NameValuePair>() {
                                {
                                    add(new NameValuePair("Action", Const.HairStyle));
                                    add(new NameValuePair("Version", "2022-08-31"));
                                }
                            });
                        }
                    }
            ));

            put(Const.HumanSegment, new ApiInfo(
                    new HashMap<String,Object>() {
                        {
                            put(Const.Method,"POST");
                            put(Const.Path,"/");
                            put(Const.Query,new ArrayList<NameValuePair>(){
                                {
                                    add(new NameValuePair("Action", Const.HumanSegment));
                                    add(new NameValuePair("Version", "2022-08-26"));
                                }
                            });
                        }
                    }
            ));

            put(Const.CVProcess, new ApiInfo(
                    new HashMap<String,Object>() {
                        {
                            put(Const.Method,"POST");
                            put(Const.Path,"/");
                            put(Const.Query,new ArrayList<NameValuePair>(){
                                {
                                    add(new NameValuePair("Action", Const.CVProcess));
                                    add(new NameValuePair("Version", "2022-08-31"));
                                }
                            });
                        }
                    }
            ));

        }
    };
}