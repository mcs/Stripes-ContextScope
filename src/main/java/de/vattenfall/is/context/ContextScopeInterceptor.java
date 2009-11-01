package de.vattenfall.is.context;

import javax.servlet.http.HttpSession;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.ExecutionContext;
import net.sourceforge.stripes.controller.Interceptor;
import net.sourceforge.stripes.controller.Intercepts;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.util.Log;

/**
 * Interceptor that enables the @ContextScope and @Context annotations.
 * 
 * @author Marcus Krassmann
 */
@Intercepts({LifecycleStage.BindingAndValidation, LifecycleStage.ResolutionExecution})
public class ContextScopeInterceptor implements Interceptor {

    private static final Log log = Log.getInstance(ContextScopeInterceptor.class);

    @Override
    public Resolution intercept(ExecutionContext ctx) throws Exception {
        HttpSession session = ctx.getActionBeanContext().getRequest().getSession();
        ContextScopeSessionStrategy strategy = new ContextScopeSessionStrategy(session);
        Resolution target;
        switch (ctx.getLifecycleStage()) {
            case BindingAndValidation:
                log.debug("Injecting values for bean " + ctx.getActionBean().getClass().getSimpleName());
                target = handleInjectionStage(ctx, strategy);
                break;
            case ResolutionExecution:
                log.debug("Storing values for bean " + ctx.getActionBean().getClass().getSimpleName());
                target = handleStoringStage(ctx, strategy);
                break;
            default:
                target = ctx.proceed();
        }
        return target;
    }

    private Resolution handleInjectionStage(ExecutionContext ctx, ContextScopeSessionStrategy strategy) throws Exception {
        ActionBean bean = ctx.getActionBean();
        ContextScope scope = bean.getClass().getAnnotation(ContextScope.class);
        if (scope != null && strategy.isInScope(scope)) {
            strategy.injectContextValues(bean);
        }
        return ctx.proceed();
    }

    private Resolution handleStoringStage(ExecutionContext ctx, ContextScopeSessionStrategy strategy) throws Exception {
        ActionBean bean = ctx.getActionBean();
        ContextScope scope = bean.getClass().getAnnotation(ContextScope.class);
        if (scope != null) {
            strategy.setSessionScopes(scope.scopes());
            strategy.storeValuesInContext(bean);
        }
        return ctx.proceed();
    }
}
