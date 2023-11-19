package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;
//import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static it.unibo.bank.impl.SimpleBankAccount.*;
import static it.unibo.bank.impl.StrictBankAccount.TRANSACTION_FEE;
import static org.junit.jupiter.api.Assertions.*;

public class TestStrictBankAccount {

    private final static int INITIAL_AMOUNT = 100;

    // 1. Create a new AccountHolder and a StrictBankAccount for it each time tests are executed.
    private AccountHolder mRossi;
    private BankAccount bankAccount;

    @BeforeEach
    public void setUp() {
        this.mRossi = new AccountHolder("mario", "rossi", 1000);
        this.bankAccount = new StrictBankAccount(this.mRossi, INITIAL_AMOUNT);
    }

    // 2. Test the initial state of the StrictBankAccount
    @Test
    public void testInitialization() {
        assertEquals(100, bankAccount.getBalance());
        assertEquals(0, bankAccount.getTransactionsCount());
        assertEquals(mRossi, bankAccount.getAccountHolder());
    }


    // 3. Perform a deposit of 100€, compute the management fees, and check that the balance is correctly reduced.
    @Test
    public void testManagementFees() {
        this.bankAccount.deposit(1000, INITIAL_AMOUNT);
        double  deposit = this.bankAccount.getBalance();
        this.bankAccount.chargeManagementFees(1000);
        assertEquals(deposit - TRANSACTION_FEE - MANAGEMENT_FEE, this.bankAccount.getBalance());
        
    }
    // 4. Test the withdraw of a negative value
    @Test
    public void testNegativeWithdraw() {
        try {
                this.bankAccount.withdraw(this.mRossi.getUserID(), -INITIAL_AMOUNT);
        }
        catch(IllegalArgumentException e){
            assertNotNull(e.getMessage());
        }
    }

    // 5. Test withdrawing more money than it is in the account
    @Test
    public void testWithdrawingTooMuch() {
        try {
            bankAccount.withdraw(mRossi.getUserID(), INITIAL_AMOUNT);
        } catch (IllegalArgumentException e) {
            assertNotNull(e.getMessage());
            assertTrue(e.getMessage().length() > 1);
        }
    }
}
