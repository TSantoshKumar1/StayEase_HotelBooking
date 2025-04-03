package com.airbnb.service;

import com.airbnb.entity.Booking;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class PdfService {


    public boolean createPdf(String fileName, Booking booking) {


        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fileName));

            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            Chunk bookingConfirmation = new Chunk("Booking-Confirmation", font);
            Chunk guestName = new Chunk("Guest Name: " + booking.getGuestName(), font);
            Chunk totalNights = new Chunk("No of Nights: " + booking.getTotalNights(), font);
            Chunk totalPrice = new Chunk("Total Price: " + booking.getTotalPrice(), font);

            document.add(bookingConfirmation);
            document.add(new Paragraph("\n"));
            document.add(guestName);
            document.add(new Paragraph("\n"));
            document.add(totalNights);
            document.add(new Paragraph("\n"));
            document.add(totalPrice);
            document.close();

            return true;

        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    }

