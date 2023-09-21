package github.serliunx.varytalk;

import org.aspectj.apache.bcel.classfile.InnerClass;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

class VaryTalkApplicationTests {
    public static void main(String[] args){

    }
}

class OuterClass{

    private InnerClass innerClass;

    public static InnerClass getInstance(){
        return new InnerClass();
    }

    private static class InnerClass{

    }
}
