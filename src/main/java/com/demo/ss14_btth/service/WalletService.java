package com.demo.ss14_btth.service;

import com.demo.ss14_btth.model.entity.TransactionHistory;
import com.demo.ss14_btth.model.entity.Wallet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WalletService {

    @Autowired
    private SessionFactory sessionFactory;

    public void rechargeMoney(Long walletId, Double amount) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Wallet wallet = session.find(Wallet.class, walletId);
            wallet.setBalance(wallet.getBalance() + amount);
            TransactionHistory history = new TransactionHistory(amount, "Nap tien", wallet);
            session.persist(history);
            tx.commit();
            System.out.println("Nap tien thanh cong");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Loi: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    public void testCache(Long walletId) {
        Session session = sessionFactory.openSession();
        session.find(Wallet.class, walletId);
        session.find(Wallet.class, walletId);
        session.find(Wallet.class, walletId);
        session.close();
    }

    public void transferMoney(Long fromId, Long toId, Double amount) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.createNativeQuery("SET TRANSACTION ISOLATION LEVEL REPEATABLE READ", Object.class).executeUpdate();
            Wallet from = session.find(Wallet.class, fromId);
            Wallet to = session.find(Wallet.class, toId);
            if (from.getBalance() < amount) throw new RuntimeException("Khong du tien");
            from.setBalance(from.getBalance() - amount);
            int x = 1 / 0;
            to.setBalance(to.getBalance() + amount);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Transfer fail -> rollback");
        } finally {
            session.close();
        }
    }

    public void showWalletLazy(Long id) {
        Session session = sessionFactory.openSession();
        Wallet wallet = session.find(Wallet.class, id);
        session.close();
        try {
            System.out.println(wallet.getHistories().size());
        } catch (Exception e) {
            System.out.println("Loi Lazy: " + e.getMessage());
        }
    }

    public void showWalletJoinFetch(Long id) {
        Session session = sessionFactory.openSession();
        Wallet wallet = session.createQuery(
                        "SELECT w FROM Wallet w LEFT JOIN FETCH w.histories WHERE w.id = :id",
                        Wallet.class)
                .setParameter("id", id)
                .uniqueResult();
        session.close();
        System.out.println("Size histories: " + wallet.getHistories().size());
    }
}