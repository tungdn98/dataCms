package vn.com.datamanager.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Permission Interceptor
 *
 * @author tungdn <tungdn@pvcombank.com.vn>
 */
@Component
public class PermissionInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(PermissionInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            String controllerName = handlerMethod.getBeanType().getSimpleName();
            String actionName = handlerMethod.getMethod().getName();

            if (controllerName != null) {
                controllerName =
                    StringUtils.replaceEach(
                        controllerName.toLowerCase(),
                        new String[] { "controller", "resource" },
                        new String[] { "", "" }
                    );
            }
            if (actionName != null) {
                actionName = actionName.toLowerCase();
            }

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null) {
                String resource = controllerName + "/" + actionName;

                if (!SecurityUtils.isCurrentUserInRole(resource)) {
                    logger.error("-------------------------------------------------------");
                    logger.error("[ERROR] USER '{}' KHONG CO QUYEN TRUY CAP RESOURCE = {}", SecurityUtils.getLoggedInUsername(), resource);
                    logger.error("-------------------------------------------------------");

                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Bạn không có quyền truy cập chức năng này");
                    return false;
                }
            }
        }

        return true;
    }
}
