package com.kajti.auth.unit;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.rules.ExternalResource;
import org.slf4j.LoggerFactory;

import java.util.List;

public final class LogSpy extends ExternalResource {

    private Logger logger;
    private ListAppender<ILoggingEvent> appender;

    @Override
    protected void before() {
        appender = new ListAppender<>();
        logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.addAppender(appender);
        appender.start();
    }

    @Override
    protected void after() {
        logger.detachAppender(appender);
    }

    public List<ILoggingEvent> getEvents() throws Exception {
        if (appender == null) {
            throw new Exception("LogSpy needs to be annotated with @Rule");
        }
        return appender.list;
    }

    public boolean findEvent(String eventMessage) throws Exception {
        return getEvents()
                .stream()
                .filter(log -> log.getFormattedMessage().equals(eventMessage))
                .map(ILoggingEvent::getMessage).count() > 0;
    }
}