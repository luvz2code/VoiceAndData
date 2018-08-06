package com.etrade.eeo;

import com.etrade.eeo.model.Principal;
import com.etrade.eeo.util.PrincipalHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by rayyar on 3/16/18.
 */
@Component
public class RequestInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("In preHandle we are intercepting the Request");
        String userId = request.getHeader("USER_ID");

        System.out.println("User Id found in header is " + userId);

        Principal principal = new Principal();
        principal.setUserId(userId);

        // Set Entitlements into Principal

        PrincipalHolder.getInstance().setPrincipal(principal);

        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);

        PrincipalHolder.getInstance().unsetPrincipal();
        System.out.println("In postHandle after clearing principal");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);

        PrincipalHolder.getInstance().unsetPrincipal();
        System.out.println("In afterCompletion after clearing principal");
    }
}
