package cheque.handover.services.Utility;

import cheque.handover.services.Controller.User;
import cheque.handover.services.Entity.UserDetail;
import cheque.handover.services.Repository.OtpRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OtpUtility {
    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private OtpRepository otpRepository;
    private final Logger logger = LoggerFactory.getLogger(User.class);

    public int generateOtp(UserDetail userDetailData) {
        String emailId = userDetailData.getEmailId();
        int count = otpRepository.countEmailId(emailId);
        int randomNo = 0;
        if (count > 0) {
            otpRepository.deletePrevOtp(emailId);
            logger.info("previous otp deleted");
        }
        randomNo = (int) (Math.random() * 900000) + 100000;
        return randomNo;
    }
    @Async
    public void sendOtpOnMail(String sentTo, String otpCode) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            String msgBody = "Dear Sir/Mam \n\n\n\n Your OTP for reset password  is: " + otpCode + ".Please enter this OTP to proceed. \n Regards,\nIT Support;";

//            System.out.println(msgBody);

            mailMessage.setFrom(sender);
            mailMessage.setTo(sentTo);
            mailMessage.setText(msgBody);
            mailMessage.setSubject("One-Time Password");

            javaMailSender.send(mailMessage);
            logger.info("otp sent on mail.");


        } catch (Exception e) {
            System.out.println(e);
        }
    }

}