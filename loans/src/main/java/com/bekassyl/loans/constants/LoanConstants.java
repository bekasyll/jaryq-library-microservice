package com.bekassyl.loans.constants;

public final class LoanConstants {
    private LoanConstants() {
        // restrict initialization
    }

    public static final String STATUS_201 = "201";
    public static final String MESSAGE_201 = "Loan created successfully";
    public static final String STATUS_200 = "200";
    public static final String MESSAGE_200 = "Loan processed successfully";
    public static final String STATUS_417 = "417";
    public static final String MESSAGE_417_CREATE = "Create operation failed. Please try again or contact Dev team";
    public static final String MESSAGE_417_RETURN = "Book return operation failed. Please try again or contact Dev team";
    public static final String MESSAGE_417_EXTEND = "Loan extend operation failed. Please try again or contact Dev team";
    public static final String MESSAGE_417_DELETE = "Delete operation failed. Please try again or contact Dev team";
}
