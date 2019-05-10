import ru.otus.TestExample;
import ru.otus.TestValidation;

import java.lang.reflect.InvocationTargetException;

public class TestRunner {




    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        run(TestExample.class);
    }

    private static void run(Class<?> testClass) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        TestValidation junit = new TestValidation();
        junit.run(TestExample.class);
    }
}
