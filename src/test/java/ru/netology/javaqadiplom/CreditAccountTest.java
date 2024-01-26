package ru.netology.javaqadiplom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CreditAccountTest {
    final int initialBalance = 1000;
    final int creditLimit = 5_000;
    final int rate = 15;
    CreditAccount account = new CreditAccount(initialBalance, creditLimit, rate);

    @Test
    public void shouldCreateAccount() {
        Assertions.assertEquals(initialBalance, account.getBalance());
        Assertions.assertEquals(creditLimit, account.getCreditLimit());
        Assertions.assertEquals(rate, account.getRate());
    }

    @Test
    public void shouldGenerateExceptionByRate() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new CreditAccount(initialBalance, creditLimit, -rate));
    }

    @Test
    public void shouldGenerateExceptionByLimit() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new CreditAccount(initialBalance, -creditLimit, rate));
    }

    @Test
    public void shouldGenerateExceptionByInitBalance() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new CreditAccount(-initialBalance, creditLimit, rate));
    }

    @Test
    public void shouldPayUnderLimit() {
        int amount = creditLimit + initialBalance - 1;

        boolean expectedPay = true;
        boolean actualPay = account.pay(amount);
        int expectedBalance = initialBalance - amount;
        int actualBalance = account.getBalance();

        Assertions.assertEquals(expectedPay, actualPay);
        Assertions.assertEquals(expectedBalance, actualBalance);
    }

    @Test
    public void shouldPayEqualLimit() {
        int amount = creditLimit + initialBalance;

        boolean expectedPay = true;
        boolean actualPay = account.pay(amount);
        int expectedBalance = -creditLimit;
        int actualBalance = account.getBalance();

        Assertions.assertEquals(expectedPay, actualPay);
        Assertions.assertEquals(expectedBalance, actualBalance);
    }

    @Test
    public void shouldNotPayOverLimit() {
        int amount = creditLimit + initialBalance + 1;

        boolean expectedPay = false;
        boolean actualPay = account.pay(amount);
        int expectedBalance = initialBalance;
        int actualBalance = account.getBalance();

        Assertions.assertEquals(expectedPay, actualPay);
        Assertions.assertEquals(expectedBalance, actualBalance);

    }

    @Test
    public void shouldNotPayAmountNegative() {
        int amount = -1;

        boolean expectedPay = false;
        boolean actualPay = account.pay(amount);
        int expectedBalance = initialBalance;
        int actualBalance = account.getBalance();

        Assertions.assertEquals(expectedPay, actualPay);
        Assertions.assertEquals(expectedBalance, actualBalance);

    }

    @Test
    public void shouldAddToPositiveBalance() {
        int amount = 1;

        boolean expectedAdd = true;
        boolean actualAdd = account.add(amount);
        int expectedBalance = initialBalance + amount;
        int actualBalance = account.getBalance();

        Assertions.assertEquals(expectedBalance, actualBalance);
        Assertions.assertEquals(expectedAdd, actualAdd);
    }

    @Test
    public void shouldNotAddNegativeBalance() {
        int amount = -1;

        boolean expectedAdd = false;
        boolean actualAdd = account.add(amount);
        int expectedBalance = initialBalance;
        int actualBalance = account.getBalance();

        Assertions.assertEquals(expectedBalance, actualBalance);
        Assertions.assertEquals(expectedAdd, actualAdd);
    }

    @Test
    public void shouldCalculateChange() {
        account.pay(creditLimit);

        int expected = (initialBalance - creditLimit) / 100 * rate;
        int actual = account.yearChange();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldNotCalculateChangeWithPositiveBalance() {
        int expected = 0;
        int actual = account.yearChange();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldNotChangeFieldsDirectly() {
        account.balance = 0;
        account.rate = 1;
        account.creditLimit = 1000000;
        int expectedBalance = initialBalance;
        int expectedRate = rate;
        int expectedLimit = creditLimit;
        Assertions.assertEquals(expectedBalance, account.getBalance());
        Assertions.assertEquals(expectedRate, account.getRate());
        Assertions.assertEquals(expectedLimit, account.getCreditLimit());
    }

}
