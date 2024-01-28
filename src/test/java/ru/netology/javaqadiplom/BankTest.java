package ru.netology.javaqadiplom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BankTest {

    //перевод с дебетового аккаунта на дебетовый в предедах мин., макс. балансов
    @Test
    public void shouldTransferBetweenSavingAccounts() {
        Bank bank = new Bank();

        int initialBalanceFrom = 10_000;
        int minBalanceFrom = 0;
        int maxBalanceFrom = 50_000;
        int rateFrom = 15;
        Account from = new SavingAccount(initialBalanceFrom, minBalanceFrom, maxBalanceFrom, rateFrom);

        int initialBalanceTo = 1_000;
        int minBalanceTo = 0;
        int maxBalanceTo = 50_000;
        int rateTo = 15;
        Account to = new SavingAccount(initialBalanceTo, minBalanceTo, maxBalanceTo, rateTo);

        boolean expectedResult = true;
        boolean actualResult = bank.transfer(from, to, initialBalanceFrom-1);
        int expectedBalanceTo = initialBalanceTo + initialBalanceFrom-1;
        int actualBalanceTo = to.getBalance();
        int expectedBalanceFrom = 1;
        int actualBalanceFrom = from.getBalance();

        Assertions.assertEquals(expectedBalanceFrom, actualBalanceFrom);
        Assertions.assertEquals(expectedBalanceTo, actualBalanceTo);
        Assertions.assertEquals(expectedResult, actualResult);
    }

    //перевод с дебетового аккаунта на дебетовый больше чем на счету переводящего
    @Test
    public void shouldNotTransferOverBalanceFrom() {
        Bank bank = new Bank();

        int initialBalanceFrom = 10_000;
        int minBalanceFrom = 0;
        int maxBalanceFrom = 50_000;
        int rateFrom = 15;
        Account from = new SavingAccount(initialBalanceFrom, minBalanceFrom, maxBalanceFrom, rateFrom);

        int initialBalanceTo = 1_000;
        int minBalanceTo = 0;
        int maxBalanceTo = 50_000;
        int rateTo = 15;
        Account to = new SavingAccount(initialBalanceTo, minBalanceTo, maxBalanceTo, rateTo);

        boolean expectedResult = false;
        boolean actualResult = bank.transfer(from, to, 11000);
        int expectedBalanceTo = initialBalanceTo;
        int actualBalanceTo = to.getBalance();
        int expectedBalanceFrom = initialBalanceFrom;
        int actualBalanceFrom = from.getBalance();

        Assertions.assertEquals(expectedBalanceFrom, actualBalanceFrom);
        Assertions.assertEquals(expectedBalanceTo, actualBalanceTo);
        Assertions.assertEquals(expectedResult, actualResult);
    }

    //перевод с дебетового аккаунта на дебетовый больше макс. баланса принимающего
    @Test
    public void shouldNotTransferOverMaxBalanceTo() {
        Bank bank = new Bank();

        int initialBalanceFrom = 100_000;
        int minBalanceFrom = 0;
        int maxBalanceFrom = 150_000;
        int rateFrom = 15;
        Account from = new SavingAccount(initialBalanceFrom, minBalanceFrom, maxBalanceFrom, rateFrom);

        int initialBalanceTo = 1_000;
        int minBalanceTo = 0;
        int maxBalanceTo = 10_000;
        int rateTo = 15;
        Account to = new SavingAccount(initialBalanceTo, minBalanceTo, maxBalanceTo, rateTo);

        boolean expectedResult = false;
        boolean actualResult = bank.transfer(from, to, 10_000);
        int expectedBalanceTo = initialBalanceTo;
        int actualBalanceTo = to.getBalance();
        int expectedBalanceFrom = initialBalanceFrom;
        int actualBalanceFrom = from.getBalance();

        Assertions.assertEquals(expectedBalanceFrom, actualBalanceFrom);
        Assertions.assertEquals(expectedBalanceTo, actualBalanceTo);
        Assertions.assertEquals(expectedResult, actualResult);
    }

    //перевод с дебетового аккаунта на дебетовый меньше мин. баланса переводящего
    @Test
    public void shouldNotTransferOverMinBalanceFrom() {
        Bank bank = new Bank();

        int initialBalanceFrom = 100_000;
        int minBalanceFrom = 90_000;
        int maxBalanceFrom = 150_000;
        int rateFrom = 15;
        Account from = new SavingAccount(initialBalanceFrom, minBalanceFrom, maxBalanceFrom, rateFrom);

        int initialBalanceTo = 1_000;
        int minBalanceTo = 0;
        int maxBalanceTo = 100_000;
        int rateTo = 15;
        Account to = new SavingAccount(initialBalanceTo, minBalanceTo, maxBalanceTo, rateTo);

        boolean expectedResult = false;
        boolean actualResult = bank.transfer(from, to, 15_000);
        int expectedBalanceTo = initialBalanceTo;
        int actualBalanceTo = to.getBalance();
        int expectedBalanceFrom = initialBalanceFrom;
        int actualBalanceFrom = from.getBalance();

        Assertions.assertEquals(expectedBalanceFrom, actualBalanceFrom);
        Assertions.assertEquals(expectedBalanceTo, actualBalanceTo);
        Assertions.assertEquals(expectedResult, actualResult);
    }

    //перевод с дебетового аккаунта на дебетовый отрицательную сумму или ноль
    @Test
    public void shouldNotTransferNegativeAmount() {
        Bank bank = new Bank();

        int initialBalanceFrom = 10_000;
        int minBalanceFrom = 0;
        int maxBalanceFrom = 150_000;
        int rateFrom = 15;
        Account from = new SavingAccount(initialBalanceFrom, minBalanceFrom, maxBalanceFrom, rateFrom);

        int initialBalanceTo = 1_000;
        int minBalanceTo = 0;
        int maxBalanceTo = 100_000;
        int rateTo = 15;
        Account to = new SavingAccount(initialBalanceTo, minBalanceTo, maxBalanceTo, rateTo);

        boolean expectedResult = false;
        boolean actualResult = bank.transfer(from, to, 0);
        int expectedBalanceTo = initialBalanceTo;
        int actualBalanceTo = to.getBalance();
        int expectedBalanceFrom = initialBalanceFrom;
        int actualBalanceFrom = from.getBalance();

        Assertions.assertEquals(expectedBalanceFrom, actualBalanceFrom);
        Assertions.assertEquals(expectedBalanceTo, actualBalanceTo);
        Assertions.assertEquals(expectedResult, actualResult);
    }

    //перевод с кредитного аккаунта на кредитный в пределах кредитного лимита переводящего
    @Test
    public void shouldTransferBetweenCreditAccounts() {
        Bank bank = new Bank();

        int initialBalanceFrom = 0;
        int creditLimitFrom = 10_000;
        int rateFrom = 15;
        Account from = new CreditAccount(initialBalanceFrom, creditLimitFrom, rateFrom);

        int initialBalanceTo = 0;
        int creditLimitTo = 10_000;
        int rateTo = 15;
        Account to = new CreditAccount(initialBalanceTo, creditLimitTo, rateTo);

        boolean expectedResult = true;
        boolean actualResult = bank.transfer(from, to, creditLimitFrom + initialBalanceFrom);
        int expectedBalanceTo = initialBalanceTo + initialBalanceFrom + creditLimitFrom;
        int actualBalanceTo = to.getBalance();
        int expectedBalanceFrom = 0;
        int actualBalanceFrom = from.getBalance();

        Assertions.assertEquals(expectedBalanceFrom, actualBalanceFrom);
        Assertions.assertEquals(expectedBalanceTo, actualBalanceTo);
        Assertions.assertEquals(expectedResult, actualResult);
    }

    //перевод с кредитного аккаунта на кредитный больше лимита переводящего
    @Test
    public void shouldNotTransferOverCreditLimitFrom() {
        Bank bank = new Bank();

        int initialBalanceFrom = 0;
        int creditLimitFrom = 10_000;
        int rateFrom = 15;
        Account from = new CreditAccount(initialBalanceFrom, creditLimitFrom, rateFrom);

        int initialBalanceTo = 0;
        int creditLimitTo = 10_000;
        int rateTo = 15;
        Account to = new CreditAccount(initialBalanceTo, creditLimitTo, rateTo);

        boolean expectedResult = false;
        boolean actualResult = bank.transfer(from, to, creditLimitFrom + initialBalanceFrom + 1);
        int expectedBalanceTo = initialBalanceTo;
        int actualBalanceTo = to.getBalance();
        int expectedBalanceFrom = initialBalanceFrom;
        int actualBalanceFrom = from.getBalance();

        Assertions.assertEquals(expectedBalanceFrom, actualBalanceFrom);
        Assertions.assertEquals(expectedBalanceTo, actualBalanceTo);
        Assertions.assertEquals(expectedResult, actualResult);
    }
}
