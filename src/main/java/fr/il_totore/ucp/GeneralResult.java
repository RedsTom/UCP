package fr.il_totore.ucp;

import java.util.Optional;

public abstract class GeneralResult {

    public abstract ResultType getResultType();

    public abstract Optional<String> getMessage();

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GeneralResult)) return false;
        return ((GeneralResult) obj).getMessage().equals(getMessage()) && ((GeneralResult) obj).getResultType() == getResultType();
    }

    public enum ResultType {

        SUCCESS(0, true),
        FAILURE(-1, false);

        private final int code;
        private final boolean successful;

        ResultType(int code, boolean successful) {
            this.code = code;
            this.successful = successful;
        }

        public int getCode() {
            return code;
        }

        public boolean isSuccessful() {
            return successful;
        }
    }

    public class ImplicitResult extends GeneralResult {

        private final ResultType resultType;
        private Optional<String> message = Optional.empty();

        public ImplicitResult(ResultType resultType) {
            this.resultType = resultType;
        }

        public ImplicitResult whilst(String message) {
            this.message = Optional.of(message);
            return this;
        }

        @Override
        public ResultType getResultType() {
            return this.resultType;
        }

        @Override
        public Optional<String> getMessage() {
            return Optional.empty();
        }
    }
}
