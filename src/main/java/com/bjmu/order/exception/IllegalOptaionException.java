package com.bjmu.order.exception;

import com.bjmu.order.enmu.SystemCodeEnum;

import java.security.PrivilegedActionException;

/**
 * @title : IllegalOptaionException
 * @describle :
 * <p>
 * Create By byron
 * @date 2017/5/22 11:44 星期一
 */
public class IllegalOptaionException extends RuntimeException {
    private SystemCodeEnum s_;

    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public IllegalOptaionException(SystemCodeEnum s_) {
        this.s_ = s_;
    }

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public IllegalOptaionException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified cause and a detail
     * message of <tt>(cause==null ? null : cause.toString())</tt> (which
     * typically contains the class and detail message of <tt>cause</tt>).
     * This constructor is useful for exceptions that are little more than
     * wrappers for other throwables (for example, {@link
     * PrivilegedActionException}).
     *
     * @param cause the cause (which is saved for later retrieval by the
     *              {@link #getCause()} method).  (A <tt>null</tt> value is
     *              permitted, and indicates that the cause is nonexistent or
     *              unknown.)
     * @since 1.4
     */
    public IllegalOptaionException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A <tt>null</tt> value is
     *                permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     * @since 1.4
     */
    public IllegalOptaionException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalOptaionException(SystemCodeEnum s_, String message, Throwable cause) {
        super(message, cause);
        this.s_ = s_;
    }

    public SystemCodeEnum getS_() {
        return s_;
    }

    public void setS_(SystemCodeEnum s_) {
        this.s_ = s_;
    }
}
