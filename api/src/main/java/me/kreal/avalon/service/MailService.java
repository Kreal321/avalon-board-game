package me.kreal.avalon.service;

import me.kreal.avalon.domain.User;
import me.kreal.avalon.dto.MailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
@PropertySource("classpath:application.properties")
public class MailService {

    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender javaMailSender;

    @Autowired
    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(MailDTO mailDTO) {

        this.javaMailSender.send(mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            message.setFrom(this.from, "PlayLobby.Club");
            message.setTo(mailDTO.getTo());
            message.setSubject(mailDTO.getSubject());
            message.setText(mailDTO.getText(), true);
        });

    }

    public void sendWelcomeMail(User user) {
        String subject = "Welcome to Play Lobby Club!";

        String text = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "\n" +
                "<head>\n" +
                "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "  <title>Welcome to Play Lobby Club</title>\n" +
                "  <!--[if mso]><style type=\"text/css\">body, table, td, a { font-family: Arial, Helvetica, sans-serif !important; }</style><![endif]-->\n" +
                "</head>\n" +
                "\n" +
                "<body style=\"font-family: Helvetica, Arial, sans-serif; margin: 0px; padding: 0px; background-color: #ffffff;\">\n" +
                "  <table role=\"presentation\"\n" +
                "    style=\"width: 100%; border-collapse: collapse; border: 0px; border-spacing: 0px; font-family: Arial, Helvetica, sans-serif; background-color: rgb(239, 239, 239);\">\n" +
                "    <tbody>\n" +
                "      <tr>\n" +
                "        <td style=\"padding: 1rem 2rem; vertical-align: top; width: 100%;\">\n" +
                "          <table role=\"presentation\" style=\"max-width: 600px; border-collapse: collapse; border: 0px; border-spacing: 0px; text-align: left;\">\n" +
                "            <tbody>\n" +
                "              <tr>\n" +
                "                <td style=\"padding: 40px 0px 0px;\">\n" +
                "                  <div style=\"text-align: left;\">\n" +
                "                    <!---->\n" +
                "                  </div>\n" +
                "                  <div style=\"padding: 20px; background-color: rgb(255, 255, 255);\">\n" +
                "                    <div style=\"color: rgb(0, 0, 0); text-align: left;\">\n" +
                "                      <h2>Welcome to Play Lobby Club</h2>\n" +
                "                      <h4>Hi " + user.getPreferredName() + ",</h4>\n" +
                "                      <p style=\"padding-bottom: 16px\">We're thrilled to have you as a member of Play Lobby Club! As a board game enthusiast,\n" +
                "                        you've joined a community of passionate players who love to play online and in person, and also participate in other fun\n" +
                "                        activities.</p>\n" +
                "                      <p style=\"padding-bottom: 16px\">Username: <strong style=\"font-size: 120%\"> " + user.getUsername() + " </strong><br>One-Time Password (OTP): <strong\n" +
                "                          style=\"font-size: 120%\">" + user.getOneTimePassword() + "</strong></p>\n" +
                "                      <p style=\"padding-bottom: 16px\"><a href=\"#\" target=\"_blank\"\n" +
                "                          style=\"padding: 12px 24px; border-radius: 4px; color: #FFF; background: #6200EE;display: inline-block;margin: 0.5rem 0; text-decoration: none;\">Login\n" +
                "                          to your account</a></p>\n" +
                "                      <p style=\"padding-bottom: 16px\">We're excited to have you on board and can't wait to play games and participate in other\n" +
                "                        activities with you. If you have any questions, feel free to contact us at <a href=\"mailto:support@playlobby.club\"\n" +
                "                          target=\"_blank\" style=\"text-decoration: none;\">support@playlobby.club</a></p>\n" +
                "                      <p style=\"padding-bottom: 16px\">Best regards,<br>Play Lobby Team</p>\n" +
                "                    </div>\n" +
                "                  </div>\n" +
                "                  <div style=\"padding-top: 20px; color: rgb(153, 153, 153); text-align: center;\">\n" +
                "                    <p style=\"padding-bottom: 16px\">Copyright © 2023 Play Lobby Club</p>\n" +
                "                  </div>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "            </tbody>\n" +
                "          </table>\n" +
                "        </td>\n" +
                "      </tr>\n" +
                "    </tbody>\n" +
                "  </table>\n" +
                "</body>\n" +
                "\n" +
                "</html>";

        MailDTO mailDTO = MailDTO.builder()
                .to(user.getEmail())
                .subject(subject)
                .text(text)
                .build();
        this.sendMail(mailDTO);
    }

    public void sendRetrievalMail(User user) {
        String subject = "Account Retrieval - Play Lobby Club!";

        String text = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "\n" +
                "<head>\n" +
                "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "  <title>Account Retrieval</title>\n" +
                "  <!--[if mso]><style type=\"text/css\">body, table, td, a { font-family: Arial, Helvetica, sans-serif !important; }</style><![endif]-->\n" +
                "</head>\n" +
                "\n" +
                "<body style=\"font-family: Helvetica, Arial, sans-serif; margin: 0px; padding: 0px; background-color: #ffffff;\">\n" +
                "  <table role=\"presentation\"\n" +
                "    style=\"width: 100%; border-collapse: collapse; border: 0px; border-spacing: 0px; font-family: Arial, Helvetica, sans-serif; background-color: rgb(239, 239, 239);\">\n" +
                "    <tbody>\n" +
                "      <tr>\n" +
                "        <td style=\"padding: 1rem 2rem; vertical-align: top; width: 100%;\">\n" +
                "          <table role=\"presentation\" style=\"max-width: 600px; border-collapse: collapse; border: 0px; border-spacing: 0px; text-align: left;\">\n" +
                "            <tbody>\n" +
                "              <tr>\n" +
                "                <td style=\"padding: 40px 0px 0px;\">\n" +
                "                  <div style=\"text-align: left;\">\n" +
                "                    <!---->\n" +
                "                  </div>\n" +
                "                  <div style=\"padding: 20px; background-color: rgb(255, 255, 255);\">\n" +
                "                    <div style=\"color: rgb(0, 0, 0); text-align: left;\">\n" +
                "                      <h2>Account Retrieval</h2>\n" +
                "                      <h4>Dear " + user.getPreferredName() + ",</h4>\n" +
                "                      <p style=\"padding-bottom: 16px\">We received a request to retrieve your account information. Below are your username and a\n" +
                "                        one-time password (OTP) to access your account:</p>\n" +
                "                      <p style=\"padding-bottom: 16px\">Username: <strong style=\"font-size: 120%\">" + user.getUsername() + "</strong><br>One-Time Password (OTP):\n" +
                "                        <strong style=\"font-size: 120%\">" + user.getOneTimePassword() + "</strong></p>\n" +
                "                      <p style=\"padding-bottom: 16px\"><a href=\"#\" target=\"_blank\"\n" +
                "                          style=\"padding: 12px 24px; border-radius: 4px; color: #FFF; background: #6200EE;display: inline-block;margin: 0.5rem 0; text-decoration: none;\">Login\n" +
                "                          to your account</a></p>\n" +
                "                      <p style=\"padding-bottom: 16px\">If you did not initiate this request, please disregard this email and take the necessary\n" +
                "                        steps to secure your account.</p>\n" +
                "                      <p style=\"padding-bottom: 16px\">If you need any further assistance or have any questions, please don't hesitate to contact\n" +
                "                        our support team at <a href=\"mailto:support@playlobby.club\" target=\"_blank\"\n" +
                "                          style=\"text-decoration: none;\">support@playlobby.club</a></p>\n" +
                "                      <p style=\"padding-bottom: 16px\">Thank you for using our services!</p>\n" +
                "                      <p style=\"padding-bottom: 16px\">Best regards,<br>Play Lobby Team</p>\n" +
                "                    </div>\n" +
                "                  </div>\n" +
                "                  <div style=\"padding-top: 20px; color: rgb(153, 153, 153); text-align: center;\">\n" +
                "                    <p style=\"padding-bottom: 16px\">Copyright © 2023 Play Lobby Club</p>\n" +
                "                  </div>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "            </tbody>\n" +
                "          </table>\n" +
                "        </td>\n" +
                "      </tr>\n" +
                "    </tbody>\n" +
                "  </table>\n" +
                "</body>\n" +
                "\n" +
                "</html>";
        MailDTO mailDTO = MailDTO.builder()
                .to(user.getEmail())
                .subject(subject)
                .text(text)
                .build();
        this.sendMail(mailDTO);
    }

}
