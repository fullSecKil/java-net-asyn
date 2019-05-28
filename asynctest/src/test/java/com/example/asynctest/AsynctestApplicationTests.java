package com.example.asynctest;

import com.example.asynctest.config.AsyncTask;
import com.example.asynctest.config.ConfigPropertiesTest;
import com.example.asynctest.config.TaskThreadPoolConfig;
import com.example.asynctest.pojo.MockQueue;
import com.example.asynctest.pojo.TestReflectionPojo;
import lombok.extern.jbosslog.JBossLog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest
@JBossLog
public class AsynctestApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    TaskThreadPoolConfig taskThreadPoolConfig;

    @Test
    public void testYamlConfig() {
        log.info(taskThreadPoolConfig.getCorePoolSize());
    }

    @Autowired
    ConfigPropertiesTest configPropertiesTest;

    @Test
    public void testConfigProperties() {
        log.info(configPropertiesTest.getFrom());
        log.info(configPropertiesTest.getPets());
        List<String> stringList = Arrays.asList("123", "456", "789");

        // configPropertiesTest.getPets().stream().forEach(System.out::print);
    }

    @Autowired
    private AsyncTask asyncTask;

    @Test
    public void AsyncTaskTest() {
        IntStream.rangeClosed(0, 100).forEach(asyncTask::doTask1);
        System.out.println("All tasks finished.");
    }

    /**
     * ‘
     * java反射 https://www.jianshu.com/p/647f2debbf2c
     *
     * @throws ClassNotFoundException
     */
    @Test
    public void testReflection1() throws ClassNotFoundException {
        Class<MockQueue> testMockQueue = MockQueue.class;
        Class testDeferredResultHolder = Class.forName("com.example.asynctest.pojo.DeferredResultHolder");
        Class testMockQueue2 = new MockQueue().getClass();

        // 获取class对象名
        // 完全限定的类名（包括包名）
        log.info(testMockQueue.getName());
        // 没有包名称的类名，可以用getSimpleName方法获取
        log.info(testMockQueue.getSimpleName());
        // 通过class对象访问修饰符, 修饰符被打包成一个int
        int modifiers = testMockQueue.getModifiers();
        System.out.println(Modifier.isPublic(modifiers));
        // 可以从class对象中获得关于这个包的信息
        Package package1 = testMockQueue.getPackage();

        // class对象可以访问父类
        Class superClass = testMockQueue.getSuperclass();

        // 可以胡德给定类实现的接口列表
        Class[] interfaces = testMockQueue.getInterfaces();

        // methods and fields 方法和字段
        Method[] methods = testMockQueue.getMethods();
        Field[] fields = testMockQueue.getFields();

        // Constructors
        // 可以像这样访问一个类的构造函数
        Constructor[] constructors = testMockQueue.getConstructors();

        // Methods
        // 可以这样访问一个类的方法
        Method[] methods1 = testMockQueue.getMethods();

        // Fields
        // 访问字段
        Field[] method = testMockQueue.getFields();

        // Annotations 注解
        // 可以这样访问类的类注解
        Annotation[] annotations = testMockQueue.getAnnotations();
    }

    /**
     * 获取对象的构造函数
     */
    @Test
    public void testReflectionConstructors() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<TestReflectionPojo> TestReflectionPojoClass = TestReflectionPojo.class;
        // Constructor []数组对于在类中什么的每个公共构造函数都将有一个Constructor实例
        Constructor[] constructors = TestReflectionPojoClass.getConstructors();
        // 要访问的构造函数的确切参数类型，则可以这样做，而不是获取数组所有构造函数
        // 如果没有构造函数匹配给定的构造函数参数（在这种情况下是String.class），则抛出NoSuchMethodException
        Constructor<TestReflectionPojo> constructor = TestReflectionPojoClass.getConstructor(String.class);
        System.out.println(Arrays.toString(constructor.getParameterTypes()));
        // 使用构造器实例化对象
        TestReflectionPojo testReflectionPojoClass = constructor.newInstance("薛瑞");
        System.out.println(testReflectionPojoClass.getName());
    }

    /**
     * java反射-字段
     */
    @Test
    public void testReflectionField() throws NoSuchFieldException, IllegalAccessException {
        Class testReflectionPojoClass = TestReflectionPojo.class;
        // 获取类中声明的公共字段field实例
        Field[] fields = testReflectionPojoClass.getFields();
        // 也可以通过字段名称访问
        Field field = testReflectionPojoClass.getField("name");
        //                                                           ^
        // 如果没有字段以getField（）方法的参数形式存在，则抛出NoSuchFieldException

        // 字段名称
        String fieldName = field.getName();
        log.info(fieldName);

        // 通过getType方法确定字段的字段类型
        Object fieldType = field.getType();
        log.info(((Class) fieldType).getSimpleName());

        // 获取和设置字段值 Field.get() 和 Field.set() 方法来获取和设置值
        TestReflectionPojo testReflectionPojo = new TestReflectionPojo("xr");
        Object value = field.get(testReflectionPojo);
        field.set(testReflectionPojo, "最帅");
        System.out.println(testReflectionPojo.getName());
    }

    /**
     * 使用java反射可以检查类方法，并在运行时调用他们
     */
    @Test
    public void testReflectionMethod() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class testReflectionPojoClass = TestReflectionPojo.class;
        // 获得每个公用方法的实例
        Method[] methods = testReflectionPojoClass.getMethods();
        log.info(methods.length);
        Method method = testReflectionPojoClass.getMethod("doSomething", String.class);

        // 如果访问不带参数的方法，可以将null作为参数类型数组, 或者不填
        Method method2 = testReflectionPojoClass.getMethod("doSomething2");

        // 得到给定方法参数类型
        Class[] paramenterTypes = method.getParameterTypes();

        // 得到方法的返回类型
        Class returnType = method.getReturnType();

        TestReflectionPojo testReflectionPojo = new TestReflectionPojo();

        method2.invoke(testReflectionPojo);
        // 通过invoke调用方法, Method.invoke（Object target，Object ... parameters）方法接受可选数量的参数, 但必须为要调用的方法中提供实例
        Object value = method.invoke(testReflectionPojo, "薛瑞最帅");
        System.out.println(testReflectionPojo.getName());

    }
}
