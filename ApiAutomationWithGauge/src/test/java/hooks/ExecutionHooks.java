package hooks;

import com.thoughtworks.gauge.AfterSpec;
import com.thoughtworks.gauge.BeforeSpec;

public class ExecutionHooks {

    @BeforeSpec
    public void beforeSpec() {
        System.out.println("Starting spec execution...");
    }

    @AfterSpec
    public void afterSpec() {
        System.out.println("Spec execution completed.");
    }
}