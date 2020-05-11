package interceptors;

import logging.ILogger;
import logging.LogMessage;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@LoggerInterceptor
public class Logger {
	@Inject
	ILogger logger;

	@AroundInvoke
	public Object logMethodInvocation(InvocationContext context) throws Exception {
		logger.log(new LogMessage("Called method: " + context.getMethod().getName()));
		return context.proceed();
	}
}
