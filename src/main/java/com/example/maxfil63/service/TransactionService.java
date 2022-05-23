package com.example.maxfil63.service;

import com.example.maxfil63.entities.Bank;
import com.example.maxfil63.entities.Bill;
import com.example.maxfil63.entities.Client;
import com.example.maxfil63.entities.Status;
import com.example.maxfil63.entities.Transaction;
import com.example.maxfil63.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Data
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);
    private final TransactionRepository transactionRepo;
    private final BankService bankService;
    private final BillService billService;
    private final ClientService clientService;

    @Transactional
    public void createTransaction(@NonNull final String clientSenderName, @NonNull final String bankSenderName,
                                  @NonNull final String clientRecipientName, @NonNull final String bankRecipientName,
                                  long amount) throws Exception {
        if (amountIsCorrect(amount)) {
            Bill senderBill = getBill(clientSenderName, bankSenderName);
            Bill recipientBill = getBill(clientRecipientName, bankRecipientName);
            billService.updateBill(decrementSenderBalance(senderBill, amount));
            Transaction createdTransaction = createTransaction(senderBill, recipientBill, amount);
            logger.info("Со счета: {}, была списана сумма: {], в соответствии с транзакцией: {}",
                    senderBill.getId(), createdTransaction.getAmount(), createdTransaction.getId());
        }
    }

    private boolean amountIsCorrect(final long amount) {
        return amount > 0;
    }

    private Bill decrementSenderBalance(Bill senderBill, final long amount) throws Exception {
        long senderBalanceAfterTransaction = senderBill.getBalance().longValue() - amount;
        if (senderBalanceAfterTransaction < 0) {
            logger.info("На счете {}, недостаточно средств для перевода", senderBill.getId());
            throw new Exception("На счету недостаточно средств");
        };
        senderBill.setBalance(senderBalanceAfterTransaction);
        return senderBill;
    }

    private Transaction createTransaction(final Bill senderBill, final Bill recipientBill, final long amount) {
        Transaction transaction = Transaction.builder()
                .amount(amount)
                .date(LocalDateTime.now(Clock.systemUTC()))
                .senderBill(senderBill)
                .recepientBill(recipientBill)
                .status(Status.WAIT_FOR_APPROVE)
                .build();
        return transactionRepo.save(transaction);
    }

    public void continueTransaction(@NonNull final Transaction transaction) throws Exception {
        if (transaction.getStatus().equals(Status.APPROVE)) {
            Bill recipientBill = transaction.getRecepientBill();
            commitTransaction(transaction, recipientBill);
            logger.info("Transaction {} was successful", transaction.getId());
        } else if (transaction.getStatus().equals(Status.REJECT)) {
            Bill senderBill = transaction.getSenderBill();
            commitTransaction(transaction, senderBill);
            logger.info("Транзакция: {} отменена. На счет: {} была возвращена сумма: {}",
                    transaction.getId(), senderBill.getId(), transaction.getAmount());
            logger.info("Transaction {} was rejected by Monitor service", transaction.getId());
        }
    }

    @Transactional
    private void commitTransaction(@NonNull final Transaction transaction, @NonNull Bill bill) throws Exception {
        billService.updateBill(incrementBalance(bill, transaction.getAmount()));
        transactionRepo.save(transaction);
    }

    public List getTransactions(@NonNull final String clientSenderName, @NonNull final String bankSenderName,
                                @NonNull final String clientRecipientName, @NonNull final String bankRecipientName)
                                throws Exception {
        Bill senderBill = getBill(clientSenderName, bankSenderName);
        Bill recipientBill = getBill(clientRecipientName, bankRecipientName);
        return getTransactions(senderBill, recipientBill);
    }

    private List getTransactions(final Bill senderBill, final Bill recipientBill) {
        return transactionRepo.findBySenderBillAndRecepientBill(senderBill, recipientBill);
    }

    private Bill getBill(final String clientName, final String bankName) throws Exception {
        Client client = clientService.getClient(clientName);
        Bank bank = bankService.getBank(bankName);
        return billService.getBill(client, bank);
    }

    private Bill incrementBalance(Bill bill, final long amount) {
        long recepientBalanceAfterTransaction = bill.getBalance().longValue() + amount;
        bill.setBalance(recepientBalanceAfterTransaction);
        return bill;
    }

}
