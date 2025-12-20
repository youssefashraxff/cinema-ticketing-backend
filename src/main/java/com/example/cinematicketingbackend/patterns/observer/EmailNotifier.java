package com.example.cinematicketingbackend.patterns.observer;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.example.cinematicketingbackend.model.Booking;
import com.example.cinematicketingbackend.model.Hall;
import com.example.cinematicketingbackend.model.Movie;
import com.example.cinematicketingbackend.model.Seat;
import com.example.cinematicketingbackend.model.Show;
import com.example.cinematicketingbackend.model.User;
import com.example.cinematicketingbackend.repository.FacadeRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailNotifier implements BookingObserver {

    private final JavaMailSender mailSender;
    private final FacadeRepository facade;

    @Autowired
    public EmailNotifier(JavaMailSender mailSender, FacadeRepository facade) {
        this.mailSender = mailSender;
        this.facade = facade;
    }

    @Override
    public void update(Booking booking) {

        // User
        Optional<User> userOpt = facade.users().findById(booking.getCustomerId());
        if (userOpt.isEmpty()) return;
        User user = userOpt.get();

        if (user.getEmail() == null || user.getEmail().isBlank()) return;

        // Movie
        Movie movie = facade.movies()
                .findById(booking.getMovieId())
                .orElse(null);

        // Show
        Show show = facade.shows()
                .findById(booking.getShowId())
                .orElse(null);

        // Hall
        Hall hall = (show != null)
                ? facade.halls().findById(show.getHallId()).orElse(null)
                : null;

        try {
            sendHtmlEmail(user, booking, movie, show, hall);
        } catch (MessagingException e) {
            System.err.println("❌ Email sending failed: " + e.getMessage());
        }
    }

    private void sendHtmlEmail(
            User user,
            Booking booking,
            Movie movie,
            Show show,
            Hall hall
    ) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper =
                new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(user.getEmail());
        helper.setSubject("🎬 Booking Confirmed – Prestige Cinema");

        String html = buildHtmlTemplate(user, booking, movie, show, hall);
        helper.setText(html, true);

        mailSender.send(message);
    }

    private String buildHtmlTemplate(
            User user,
            Booking booking,
            Movie movie,
            Show show,
            Hall hall
    ) {

        String seats = booking.getSeats()
                .stream()
                .map(Seat::toString)   // relies on Seat.toString()
                .collect(Collectors.joining(", "));

        return """
        <html>
          <body style="background:#f4f6f8;font-family:Arial;margin:0;padding:0;">
            <div style="max-width:600px;margin:30px auto;background:#fff;
                        border-radius:12px;box-shadow:0 6px 18px rgba(0,0,0,.1);">

              <div style="background:#111827;color:#fff;padding:24px;text-align:center;">
                <h1 style="margin:0;">🎬 Prestige Cinema</h1>
                <p style="margin-top:6px;color:#d1d5db;">Booking Confirmation</p>
              </div>

              <div style="padding:30px;color:#111827;">
                <h2>Hello %s 👋</h2>

                <p>Your booking has been
                <strong style="color:#16a34a;">successfully confirmed</strong>.</p>

                <table style="width:100%%;margin-top:20px;">
                  <tr><td>Movie</td><td style="text-align:right;"><strong>%s</strong></td></tr>
                  <tr><td>Hall</td><td style="text-align:right;">%s</td></tr>
                  <tr><td>Show Time</td><td style="text-align:right;">%s</td></tr>
                  <tr><td>Seats</td><td style="text-align:right;">%s</td></tr>
                  <tr>
                    <td>Total Paid</td>
                    <td style="text-align:right;">
                      <strong style="color:#dc2626;">%.2f EGP</strong>
                    </td>
                  </tr>
                </table>

                <p style="margin-top:30px;">
                  Enjoy your movie 🍿<br/>
                  See you soon at <strong>Prestige Cinema</strong>.
                </p>

                <p>
                  Best regards,<br/>
                  <strong>Prestige Cinema Team</strong>
                </p>
              </div>

              <div style="background:#f9fafb;padding:15px;text-align:center;
                          font-size:12px;color:#9ca3af;">
                © 2024 Prestige Cinema
              </div>

            </div>
          </body>
        </html>
        """.formatted(
                user.getUsername(),   // or getFullName()
                movie != null ? movie.getName() : "N/A",
                hall != null ? hall.getHallId() : "N/A",
                show != null ? show.getStartTime() : "N/A",
                seats,
                booking.getTotalPrice()
        );
    }
}