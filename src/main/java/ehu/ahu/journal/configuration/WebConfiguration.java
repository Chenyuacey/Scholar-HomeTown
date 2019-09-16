package ehu.ahu.journal.configuration;

import ehu.ahu.journal.interceptor.NeedAdminInterceptor;
import ehu.ahu.journal.interceptor.NeedLoginInterceptor;
import ehu.ahu.journal.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * @Author:Keyu
 * 配置拦截器
 */
@Component
public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    PassportInterceptor passportInterceptor;

    @Autowired
    NeedLoginInterceptor needLoginInterceptor;

    @Autowired
    NeedAdminInterceptor needAdminInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);
        registry.addInterceptor(needLoginInterceptor).addPathPatterns("/user/*");
        registry.addInterceptor(needAdminInterceptor).addPathPatterns("/admin/*");
        super.addInterceptors(registry);
    }
}
