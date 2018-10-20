package pl.edu.agh.iisg.to.javafx.cw3.command;

import pl.edu.agh.iisg.to.javafx.cw3.model.Account;
import pl.edu.agh.iisg.to.javafx.cw3.model.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RemoveTransactionsCommand implements Command {
    private List<Transaction> transactionsToRemove = new ArrayList<>();
    private Account account;

    public RemoveTransactionsCommand(List<Transaction> transactionsToRemove, Account account){
        this.transactionsToRemove = transactionsToRemove;
        this.account = account;
    }

    @Override
    public void execute() {
        account.getTransactions().removeAll(transactionsToRemove);
    }

    @Override
    public void undo() {
        for(Transaction transaction : this.transactionsToRemove){
            account.addTransaction(transaction);
        }
    }

    @Override
    public void redo() {
        account.getTransactions().removeAll(transactionsToRemove);
    }

    @Override
    public String getName() {
        return "Removed transactions: \n" +
                this.transactionsToRemove.stream().map(transaction -> transaction.toString() + "\n")
                        .collect(Collectors.joining());
    }
}
