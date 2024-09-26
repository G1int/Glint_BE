package com.swyp.glint.core.system.logtrace;

import org.aspectj.lang.annotation.Pointcut;


public class PointCuts {

    @Pointcut("execution(* com.swyp.glint.*(..))")
    public void all(){}


}
