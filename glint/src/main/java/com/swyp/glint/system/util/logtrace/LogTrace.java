package com.swyp.glint.system.util.logtrace;


public interface LogTrace {

    TraceStatus begin(String message);

    void end(TraceStatus status);

    void exception(TraceStatus status, Exception e);


}
