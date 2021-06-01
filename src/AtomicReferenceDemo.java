import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceDemo {
    public static AtomicReference<User> atomicReference = new AtomicReference<>();

    public static void main(String[] args) {
        User user = new User("conan", 15);
        atomicReference.set(user);
        User updateUser = new User("JYZ", 27);
        atomicReference.compareAndSet(user, updateUser);
        
        System.out.println(atomicReference.get().getName());
        System.out.println(atomicReference.get().getAge());
    }

    static class User {
        private String name;
        private int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }
}
