package com.xeiam.xchange.dto.marketdata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by root on 09/08/14.
 */
public class Transactions {


    private final List<Transaction> transactions;
    private final long lastID;
    private final TransactionSortType transactionSortType;

    /**
     * Constructor
     *
     * @param transactions
     * @param transactionSortType
     */
    public Transactions(List<Transaction> transactions, TransactionSortType transactionSortType) {

        this(transactions, 0L, transactionSortType);
    }

    /**
     * Constructor
     *
     * @param transactions The list of transactions
     * @param lastID
     */
    public Transactions(List<Transaction> transactions, long lastID, TransactionSortType transactionSortType) {

        this.transactions = new ArrayList<Transaction>(transactions);
        this.lastID = lastID;
        this.transactionSortType = transactionSortType;

        switch (this.transactionSortType) {
            case SortByTimestamp:
                Collections.sort(this.transactions, new TradeTimestampComparator());
                break;
            case SortByID:
                Collections.sort(this.transactions, new TradeIDComparator());
                break;

            default:
                break;
        }
    }

    /**
     * @return A list of trades ordered by id
     */
    public List<Transaction> getTransactions() {

        return transactions;
    }

    /**
     * @return a Unique ID for the fetched trades
     */
    public long getlastID() {

        return lastID;
    }

    public TransactionSortType getTransactionSortType() {

        return transactionSortType;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("Trades\n");
        for (Transaction transaction : getTransactions()) {
            sb.append("[trade=");
            sb.append(transaction.toString());
            sb.append("]\n");
        }
        return sb.toString();
    }

    public enum TransactionSortType {
        SortByTimestamp, SortByID
    }

    public class TradeTimestampComparator implements Comparator<Transaction> {

        @Override
        public int compare(Transaction transaction1, Transaction transaction2) {

            return transaction1.getTimestamp().compareTo(transaction2.getTimestamp());
        }
    }

    public class TradeIDComparator implements Comparator<Transaction> {

        @Override
        public int compare(Transaction transaction1, Transaction transaction2) {

            return transaction1.getId().compareTo(transaction2.getId());
        }
    }
}
