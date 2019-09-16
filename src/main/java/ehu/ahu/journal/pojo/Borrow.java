package ehu.ahu.journal.pojo;

import java.util.Date;

/**
 * @Author:Keyu
 */
public class Borrow {
    int id;
    int userId;
    int registerId;
    Date borrowDate;
    Date returnDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRegisterId() {
        return registerId;
    }

    public void setRegisterId(int journalId) {
        this.registerId = journalId;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
