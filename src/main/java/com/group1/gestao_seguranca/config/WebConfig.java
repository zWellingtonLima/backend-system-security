package com.group1.gestao_seguranca.config;

import com.group1.gestao_seguranca.interceptors.SessaoInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final SessaoInterceptor sessaoInterceptor;

    public WebConfig(SessaoInterceptor sessaoInterceptor) {
        this.sessaoInterceptor = sessaoInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessaoInterceptor)
                .addPathPatterns("/api/**") // vai proteger tudo com ssa rota
                .excludePathPatterns(       // menos essas rotas aqui
                        "/api/auth/login",
                        "/api/auth/register",
                        "/api/lists/**"
                );
    }
}
