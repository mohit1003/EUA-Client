package in.gov.abdm.uhi.common.dto;

//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * ACK or NACK of message received
 */
//@JsonInclude(Include.NON_NULL)
public class AcknowledementTO {

	private MessageTO message;

	private ErrorTO error;

    public AcknowledementTO(MessageTO message, ErrorTO error) {
        this.message = message;
        this.error = error;
    }

    public AcknowledementTO() {
    }

    public MessageTO getMessage() {
        return this.message;
    }

    public ErrorTO getError() {
        return this.error;
    }

    public void setMessage(MessageTO message) {
        this.message = message;
    }

    public void setError(ErrorTO error) {
        this.error = error;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof AcknowledementTO)) return false;
        final AcknowledementTO other = (AcknowledementTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$message = this.getMessage();
        final Object other$message = other.getMessage();
        if (this$message == null ? other$message != null : !this$message.equals(other$message)) return false;
        final Object this$error = this.getError();
        final Object other$error = other.getError();
        if (this$error == null ? other$error != null : !this$error.equals(other$error)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof AcknowledementTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $message = this.getMessage();
        result = result * PRIME + ($message == null ? 43 : $message.hashCode());
        final Object $error = this.getError();
        result = result * PRIME + ($error == null ? 43 : $error.hashCode());
        return result;
    }

    public String toString() {
        return "AcknowledementTO(message=" + this.getMessage() + ", error=" + this.getError() + ")";
    }
}
