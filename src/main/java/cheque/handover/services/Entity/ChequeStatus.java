package cheque.handover.services.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "issued_cheque")
@Data
public class ChequeStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "issued_id")
    private Long issuedId;
    @Column(name = "cheque_Id")
    private Long chequeId;
    @Column(name = "consumer_type")
    private String consumerType;

    @Column(name = "handover_date")
    private Date handoverDate;
    @Column(name = "ddfs_flag")
    private String ddfsFlag;

    @Column(name = "updated_date")
    private Timestamp updatedDate;
    @Column(name = "updated_by")
    private String updatedBy;
}
