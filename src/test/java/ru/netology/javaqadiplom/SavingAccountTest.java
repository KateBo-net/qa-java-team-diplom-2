package ru.netology.javaqadiplom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SavingAccountTest {

   // создание сберегательного счета
    @Test
    public void shouldCreateSavingAccount() {
        SavingAccount account = new SavingAccount(
                10_000,
                0,
                10_000,
                11
        );

        int initialBalance = account.balance;
        int minBalance = account.minBalance;
        int maxBalance = account.maxBalance;
        int rate = account.rate;

        Assertions.assertEquals(initialBalance, account.getBalance());
        Assertions.assertEquals(minBalance, account.getMinBalance());
        Assertions.assertEquals(maxBalance, account.getMaxBalance());
        Assertions.assertEquals(rate, account.getRate());
    }

    // initialBalance меньше minBalance, должно выкидываться исключения вида IllegalArgumentException
    @Test
    public void shouldGenerateExceptionWhenInitialBalanceLessThenMinBalance() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new SavingAccount(1, 100, 5_000, 7));
    }

    // initialBalance больше maxBalance, должно выкидываться исключения вида IllegalArgumentException
    @Test
    public void shouldGenerateExceptionWhenInitialBalanceIsGreaterMaxBalance() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new SavingAccount(10_001, 100, 10_000, 7));
    }

    // minBalance меньше нуля, должно выкидываться исключения вида IllegalArgumentException
    @Test
    public void shouldGenerateExceptionWhenMinBalanceIsNegative() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new SavingAccount(100, -100, 10_000, 7));
    }

    // maxBalance меньше нуля, должно выкидываться исключения вида IllegalArgumentException
    @Test
    public void shouldGenerateExceptionWhenMaxBalanceIsNegative() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new SavingAccount(0,0, -1, 5));
    }

    // minBalance больше maxBalance, должно выкидываться исключения вида IllegalArgumentException
    @Test
    public void shouldGenerateExceptionWhenMinBalanceIsGreaterMax() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new SavingAccount(500, 10_000, 1_000, 10));
    }

    // rate - отрицательное число, должно выкидываться исключения вида IllegalArgumentException
    @Test
    public void shouldGenerateExceptionWhenRateIsNegative() {
       Assertions.assertThrows(IllegalArgumentException.class,() -> new SavingAccount(0, 0, 10_000, -10));
    }

    // успешная оплата с карты на сумму в пределах допустимого баланса
    @Test
    public void shouldPayIfAmountLessThanBalance() {
        SavingAccount account = new SavingAccount(
                1000,
                0,
                1000,
                5
        );

        int initialBalance = account.balance;
        int amount = 500;

        boolean expectedPay = true;
        boolean actualPay = account.pay(amount);
        int expectedBalance = initialBalance - amount;
        int actualBalance = account.getBalance();

        Assertions.assertEquals(expectedPay, actualPay);
        Assertions.assertEquals(expectedBalance, actualBalance);
    }

    // оплата с карты на сумму равную нулю не должна пройти
    @Test
    public void shouldNotPayIfAmountIsZero() {
        SavingAccount account = new SavingAccount(
                1000,
                0,
                1000,
                5
        );

        int initialBalance = account.balance;
        int amount = 0;

        boolean expectedPay = false;
        boolean actualPay = account.pay(amount);

        int actualBalance = account.getBalance();

        Assertions.assertEquals(expectedPay, actualPay);
        Assertions.assertEquals(initialBalance, actualBalance);
    }

    // оплата с карты на сумму меньше нуля не должна пройти
    @Test
    public void shouldNotPayIfAmountIsNegative() {
        SavingAccount account = new SavingAccount(
                1000,
                0,
                1000,
                5
        );

        int initialBalance = account.balance;
        int amount = -1;

        boolean expectedPay = false;
        boolean actualPay = account.pay(amount);

        int actualBalance = account.getBalance();

        Assertions.assertEquals(expectedPay, actualPay);
        Assertions.assertEquals(initialBalance, actualBalance);
    }

    // оплата с карты на сумму превышающую баланс не должна пройти
    @Test
    public void shouldNotPayIfAmountIsMoreThenBalance() {
        SavingAccount account = new SavingAccount(
                1000,
                0,
                1000,
                5
        );

        int initialBalance = account.balance;
        int amount = 20_000;

        boolean expectedPay = false;
        boolean actualPay = account.pay(amount);

        int actualBalance = account.getBalance();

        Assertions.assertEquals(expectedPay, actualPay);
        Assertions.assertEquals(initialBalance, actualBalance);
    }

    // успешное пополнение карты на максиммальную сумму равную максимальному балансу
    @Test
    public void shouldAddIfAmountEqualToMaxBalance() {
        SavingAccount account = new SavingAccount(
                0,
                0,
                5_000,
                5
        );

        int initialBalance = account.balance;
        int amount = 5_000;

        boolean expectedAdd = true;
        boolean actualAdd = account.add(amount);

        int expectedBalance = initialBalance + amount;
        int actualBalance = account.getBalance();

        Assertions.assertEquals(expectedAdd, actualAdd);
        Assertions.assertEquals(expectedBalance, actualBalance);
    }

    // успешное пополнение карты в пределах допустимого максимального баланса
    @Test
    public void shouldAddLessThanMaxBalance() {
        SavingAccount account = new SavingAccount(
                2_000,
                1_000,
                10_000,
                5
        );

        int initialBalance = account.balance;
        int amount = 3_000;

        boolean expectedAdd = true;
        boolean actualAdd = account.add(amount);

        int expectedBalance = initialBalance + amount;
        int actualBalance = account.getBalance();

        Assertions.assertEquals(expectedAdd, actualAdd);
        Assertions.assertEquals(expectedBalance, actualBalance);
    }

    // пополнение карты на сумму превышающую максимальный баланс не должно пройти
    @Test
    public void shouldNotAddMoreThanMaxBalance() {
        SavingAccount account = new SavingAccount(
                5_000,
                100,
                15_000,
                5
        );

        int initialBalance = account.balance;
        int amount = 11_000;

        boolean expectedAdd = false;
        boolean actualAdd = account.add(amount);

        int expectedBalance = initialBalance;
        int actualBalance = account.getBalance();

        Assertions.assertEquals(expectedAdd, actualAdd);
        Assertions.assertEquals(expectedBalance, actualBalance);
    }

    // пополнение карты на нулевую сумму не должно пройти
    @Test
    public void shouldNotAddIfAmountIsZero() {
        SavingAccount account = new SavingAccount(
                0,
                0,
                10_000,
                5
        );

        int initialBalance = account.balance;
        int amount = 0;

        boolean expectedAdd = false;
        boolean actualAdd = account.add(amount);

        int expectedBalance = initialBalance;
        int actualBalance = account.getBalance();

        Assertions.assertEquals(expectedAdd, actualAdd);
        Assertions.assertEquals(expectedBalance, actualBalance);
    }

    // пополнение карты на отрицательную сумму не должно пройти
    @Test
    public void shouldNotAddIfAmountIsNegative() {
        SavingAccount account = new SavingAccount(
                0,
                0,
                10_000,
                5
        );

        int initialBalance = account.balance;
        int amount = -1_000;

        boolean expectedAdd = false;
        boolean actualAdd = account.add(amount);

        int expectedBalance = initialBalance;
        int actualBalance = account.getBalance();

        Assertions.assertEquals(expectedAdd, actualAdd);
        Assertions.assertEquals(expectedBalance, actualBalance);
    }

    // расчет годовых процентов на остаток
    @Test
    public void shouldCalculateYearChange() {
        SavingAccount account = new SavingAccount(
                200,
                0,
                1_000,
                15
        );

        int initialBalance = account.balance;
        int rate = account.rate;

        int expectedYearChange = initialBalance / 100 * rate;
        int actualYearChange = account.yearChange();

        Assertions.assertEquals(expectedYearChange, actualYearChange);
    }
}
