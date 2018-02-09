package io.ark.core.requests;

import org.bitcoinj.core.ECKey;

import io.ark.core.crypto.Crypto;
import io.ark.core.model.Account;
import io.ark.core.model.Transaction;
import io.ark.core.network.NetworkConfig;
import io.ark.core.network.NetworkInfo;
import io.ark.core.requests.dto.TransactionTransport;
import io.ark.core.responses.TransactionResponse;

public class TransactionManager extends Manager {

  private static final String SEND_TX = "/peer/transactions";

  public TransactionManager(NetworkConfig config, NetworkInfo info) {
    super(config, info);
  }

  public Transaction createTransaction(String address, double amount, ECKey keyPair) {
    Transaction tx = new Transaction(address, amount, keyPair);
    return tx;
  }

  public String sendTransaction(String address, double amount, Account sender) {
    Transaction tx = createTransaction(address, amount, sender.getKeyPair());

    Crypto.sign(tx, sender.getKeyPair());

    if (sender.getSecondKeyPair() != null) {
      Crypto.secondSign(tx, sender.getSecondKeyPair());
    }

    Crypto.setId(tx);

    TransactionTransport txTransport = new TransactionTransport(tx);
    TransactionResponse res = http.postFuture(SEND_TX, TransactionResponse.class, txTransport);

    return res.getTransactionIds().get(0);
  }

}
