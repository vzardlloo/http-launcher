package com.launch.kit;


import com.launch.HttpTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ConvertKit {

    private static final Logger logger = LoggerFactory.getLogger(ConvertKit.class);

    public static Map convertBeanToMap(Object bean) {
        try {
            Class type = bean.getClass();
            Map returnMap = new HashMap();
            BeanInfo beanInfo = Introspector.getBeanInfo(type);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor descriptor : propertyDescriptors) {
                String propertyName = descriptor.getName();
                //获取bean的class描述
                if (!propertyName.equals("class")) {
                    Method reacMethod = descriptor.getReadMethod();
                    Object result = reacMethod.invoke(bean);
                    if (result != null) {
                        returnMap.put(propertyName, result.toString());
                    } else {
                        returnMap.put(propertyName, "");
                    }
                }
            }
            return returnMap;
        } catch (IntrospectionException e) {
            logger.error(e.getLocalizedMessage());
        } catch (IllegalAccessException e) {
            logger.error(e.getLocalizedMessage());
        } catch (InvocationTargetException e) {
            logger.error(e.getLocalizedMessage());
        }

        return null;
    }

}
