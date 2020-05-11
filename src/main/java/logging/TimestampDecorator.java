package logging;


import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;
import java.util.Date;

@Decorator
public class TimestampDecorator implements ILogger {
	@Delegate
	@Inject
	private ILogger logger;

	@Override
	public void log(LogMessage message) {
		message.addProperty("Timestamp", new Date().toString());
		logger.log(message);
	}
}
