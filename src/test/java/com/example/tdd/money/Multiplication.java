package com.example.tdd.money;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.rmi.MarshalledObject;

import static org.junit.jupiter.api.Assertions.*;

public class Multiplication {
    @Nested
    @DisplayName("Dollar 테스트")
    class testDollar {
        @Test
        void testMultiplication() {
            Money five = Money.dollar(5);
            assertEquals(Money.dollar(10), five.times(2));
            assertEquals(Money.dollar(15), five.times(3));
        }

        @Test
        void testEquality() {
            assertTrue(Money.dollar(5).equals(Money.dollar(5)));
            assertFalse(Money.dollar(5).equals(Money.dollar(6)));
        }
    }

    @Nested
    @DisplayName("Franc 테스트")
    class testFranc {
        @Test
        void testMultiplication() {
            Money five = Money.franc(5);
            assertEquals(Money.franc(10), five.times(2));
            assertEquals(Money.franc(15), five.times(3));
        }

        @Test
        void testEquality() {
            assertTrue(Money.dollar(5).equals(Money.dollar(5)));
            assertFalse(Money.dollar(5).equals(Money.dollar(6)));
            assertFalse(Money.franc(5).equals(Money.franc(6)));
        }
    }

    @Nested
    @DisplayName("통화 개념 도입")
    class testCurrency {
        @Test
        void testCurrency() {
            assertEquals("USD", Money.dollar(1).currency());
            assertEquals("CHF", Money.franc(1).currency());
        }
    }

    @Nested
    @DisplayName("더하기")
    class testSum {
        @Test
        void testSimpleAddition() {
            Money five = Money.dollar(5);
            Expression sum = five.plus(five);
            Bank bank = new Bank();
            Money reduced = bank.reduce(sum, "USD");
            assertEquals(Money.dollar(10), reduced);
        }

        @Test
        void testPlusReturnSum() {
            Money five = Money.dollar(5);
            Expression result = five.plus(five);

            Sum sum = (Sum) result;

            assertEquals(five, sum.augend);
            assertEquals(five, sum.addend);
        }

        @Test
        void testReduceSum() {
            Expression sum = new Sum(Money.dollar(3), Money.dollar(4));

            Bank bank = new Bank();
            Money result = bank.reduce(sum, "USD");
            assertEquals(Money.dollar(7), result);
        }

        @Test
        void testReduceMoney() {
            Bank bank = new Bank();
            Money result = bank.reduce(Money.dollar(1), "USD");
            assertEquals(Money.dollar(1), result);
        }

        @Test
        @DisplayName("Money에 대한 통화 변환을 수행하는 Reduce")
        void testReduceMoneyDifferentCurrency() {
            Bank bank = new Bank();
            bank.addRate("CHF", "USD", 2);
            Money result = bank.reduce(Money.franc(2), "USD");
            assertEquals(Money.dollar(1), result);
        }

        @Test
        void testIdentityRate() {
            assertEquals(1, new Bank().rate("USD", "USD"));
        }
    }

    @Nested
    @DisplayName("서로 다른 통화 더하기")
    class testDifferentCurrency {
        @Test
        void testMixedAddition() {
            Expression fiveBucks = Money.dollar(5);
            Expression tenFrancs = Money.franc(10);

            Bank bank = new Bank();
            bank.addRate("CHF", "USD", 2);

            Money result = bank.reduce(fiveBucks.plus(tenFrancs), "USD");

            assertEquals(Money.dollar(10), result);
        }
    }

    @Nested
    @Disabled
    @DisplayName("드디어 더하기")
    class testExpressionPlus {
        @Test
        void testSumPlusMoney() {
            Expression fiveBucks = Money.dollar(5);
            Expression tenFrancs = Money.franc(10);

            Bank bank = new Bank();
            Expression sum = new Sum(fiveBucks, tenFrancs).plus(fiveBucks);
            Money result = bank.reduce(sum, "USD");
            assertEquals(Money.dollar(15), result);
        }

        @Test
        void testSumTimes() {
            Expression fiveBucks = Money.dollar(5);
            Expression tenFrancs = Money.franc(10);

            Bank bank = new Bank();
            bank.addRate("CHF", "USD", 2);

            Expression sum = new Sum(fiveBucks, tenFrancs).times(2);
            Money result = bank.reduce(sum, "USD");
            assertEquals(Money.dollar(20), result);
        }
    }
}
