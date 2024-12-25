package iys.customer.employee.interactions;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AppRunnerTest {

    public static final String APP_PACKAGE = "iys.customer.employee.interactions";

    @Test
    void contextLoads() {
        AppRunner.main(new String[]{});
        assertThat(this.getClass().getClassLoader()
                .getDefinedPackage(APP_PACKAGE)).isNotNull();
    }

}