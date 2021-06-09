/**
 * Demo
 */
public class Demo {
    public static void main(String[] args) throws Exception {
        ConstructorArg arg = new ConstructorArg.Builder().setIsRef(false).setObject(String.valueOf(1))
                .setType(String.class).build();
        System.out.println(arg);
    }
}