package bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * <p>
 * bean
 * </p>
 *
 * @author StormBirds Email：xbaojun@gmail.com
 * @since 2019/8/28 15:05
 */


public class CitConTransactionsBean {
    /**
     * id : fdc4817992f87408a30e5a2327a350da
     * type : charge or refund Type of transaction. Possible values are charge (payment) and refund (return of money)
     * amount : 2 Amount of money charged or returned. This is an integer without decimal places or thousand separators. For example, 9.99 is returned as 999
     * currency : USD 3-character ISO country code, for example, USD.
     * time : 2016-12-27 12:10:58
     * reference : jkh25jh1348fd89sg
     * status : success
     * note : null
     */

    private String id;
    private String type;
    private int amount;
    private String currency;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;
    private String reference;
    private String status;
    private String note;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
