package ru.chemarev.andrey;

public class Main {
    public static void main(String... args) {
        try {
            ATM atm = new ATM(Denomination.FIVE_THOUSAND, Denomination.ONE_THOUSAND,
                    Denomination.FIVE_HUNDRED, Denomination.ONE_HUNDRED);

            atm.putMoney(Bill.oneThousand());
            atm.putMoney(Bill.getBills(Denomination.ONE_HUNDRED, 8));
            atm.putMoney(Bill.fiveHundred());

            System.out.println(String.format("Balance: %d", atm.getBalance()));
            int amount = 1600;

            System.out.println(String.format("Get bills for amount = %d:", amount));
            for ( Bill bill : atm.getMoney(amount) )
                System.out.println(bill.getDenomination());

        } catch (ATMException e) {
            e.printStackTrace();
        }
    }
}
