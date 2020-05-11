package logging;

import lombok.SneakyThrows;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;
import java.net.InetAddress;
import java.util.Date;

@Decorator
public class MachineNameDecorator implements ILogger {
	@Delegate
	@Inject
	private ILogger logger;

	@SneakyThrows
	@Override
	public void log(LogMessage message) {
		message.addProperty("MachineName", InetAddress.getLocalHost().getHostName());
		logger.log(message);
	}
}
