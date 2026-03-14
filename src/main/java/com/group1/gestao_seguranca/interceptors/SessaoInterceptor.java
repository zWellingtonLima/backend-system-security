package com.group1.gestao_seguranca.interceptors;

import com.group1.gestao_seguranca.entities.Sessao;
import com.group1.gestao_seguranca.repositories.SessaoRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SessaoInterceptor implements HandlerInterceptor {
    private final SessaoRepository sessaoRepo;

    public SessaoInterceptor(SessaoRepository sessaoRepo) {
        this.sessaoRepo = sessaoRepo;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String header = request.getHeader("X-Sessao-Id");

        if (header == null) {
            response.setStatus(401);
            response.getWriter().write("Sessão não fornecida");
            return false;
        }

        Sessao sessao = sessaoRepo.findByToken(header).orElse(null);

        if (sessao == null || sessao.getHoraSaida() != null) {
            response.setStatus(401);
            response.getWriter().write("Sessão inválida ou expirada.");
            return false;
        }

        request.setAttribute("usuarioAutenticado", sessao.getUser());
        return true;
    }
}
