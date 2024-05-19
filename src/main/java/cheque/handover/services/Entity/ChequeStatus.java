package cheque.handover.services.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Table(name = "cheque_status")
@Data
public class ChequeStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "application_number")
    private String applicationNo;
    @Column(name = "consumer_type")
    private String consumerType;
    @Column(name = "handover_date")
    private Date handoverDate;
    @Column(name = "ddfs_flag")
    private String ddfsFlag;
}
