package github.serliunx.varytalk.common.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PointCutDefinition {

    @Pointcut("@annotation(github.serliunx.varytalk.common.annotation.GetOperator)")
    public void operatorPoint(){}
}
