package com.bjmu.order.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class TempUtil {

    final private static String msgSrcId = "3194";

    public static String getFormatTimeWithNull(){
        UUID uuid = UUID.randomUUID();
        log.info("uuid="+uuid.toString());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return msgSrcId+sdf.format(new Date());
    }
    public static String getFormatTime(){
        SimpleDateFormat  sdf = new SimpleDateFormat("YYYYMMddHHmmss");
        return sdf.format(new Date());
    }
    public static Map<String, String> objectToMap(Object obj) throws Exception {
        if(obj == null)
            return null;

        Map<String, String> map = new HashMap<String, String>();

        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if (key.compareToIgnoreCase("class") == 0) {
                continue;
            }
            Method getter = property.getReadMethod();
            Object value = getter!=null ? getter.invoke(obj) : null;
            if(value!=null){
                map.put(key, JSON.toJSONString(value));
            }
        }
        return map;
    }
}
