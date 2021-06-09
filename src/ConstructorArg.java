/**
 * 构造者模式demo: ConstructorArg
 */
public class ConstructorArg {
    private boolean isRef;
    private Class type;
    private Object arg;

    private ConstructorArg(Builder builder) {
        this.isRef = builder.isRef;
        this.type = builder.type;
        this.arg = builder.arg;
    }

    public static class Builder {
        private boolean isRef;
        private Class type;
        private Object arg;

        public ConstructorArg build() throws Exception {
            if (isValidParams()) {
                return new ConstructorArg(isRef, type, arg);
            }
            throw new Exception("params invalid");
        }

        public Builder setIsRef(boolean isRef) {
            this.isRef = isRef;
            return this;
        }

        public Builder setType(Class type) {
            this.type = type;
            return this;
        }

        public Builder setObject(Object arg) {
            this.arg = arg;
            return this;
        }

        private boolean isValidParams() {
            if (isRef && arg instanceof String) {
                return true;
            }
            if (!isRef && arg != null && type != null) {
                return true;
            }
            return false;
        }
    }
}