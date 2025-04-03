package com.airbnb.controller;

import com.airbnb.dto.BookingDto;
import com.airbnb.entity.Booking;
import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.service.*;
import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {

    private BookingService bookingService;
    private PropertyRepository propertyRepository;
    private PdfService pdfService;
    private BucketService bucketService;
    private SmsService smsService;
    private EmailService emailService;

    @Autowired
    private AmazonS3 amazonS3;


    public BookingController(BookingService bookingService, PropertyRepository propertyRepository, PdfService pdfService, BucketService bucketService, SmsService smsService, EmailService emailService) {
        this.bookingService = bookingService;
        this.propertyRepository = propertyRepository;
        this.pdfService = pdfService;
        this.bucketService = bucketService;
        this.smsService = smsService;
        this.emailService = emailService;
    }


    @PostMapping("/addBooking/{propertyId}")
    public ResponseEntity<?> createBooking(@PathVariable long propertyId,
                                           @RequestBody BookingDto bookingDto,
                                           @AuthenticationPrincipal PropertyUser propertyUser) throws IOException {


        Optional<Property> byId = propertyRepository.findById(propertyId);

        if (byId.isPresent()) {

            Property property = byId.get();

            Booking booking = bookingService.createBooking(property, bookingDto, propertyUser);

            boolean pdf = pdfService.createPdf("E://feb//" + "booking-confirmation-id" + booking.getId() + ".pdf", booking);

            if (pdf) {

                MultipartFile file = BookingController.convertFileToMultipartFile("E://feb//" + "booking-confirmation-id" + booking.getId() + ".pdf");


                // file upload in AWS S3 service .......
                String uploadFileUrl = bucketService.uploadFile(file, "myairbnb35");
                smsService.sendSms("+916264241485","Your Booking confirmation details click here." + uploadFileUrl);
                emailService.sendSimpleEmail("emailsenderservice121@gmail.com","booking status",uploadFileUrl);
                System.out.println(uploadFileUrl);

            } else {

                return new ResponseEntity<>("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return new ResponseEntity<>(booking, HttpStatus.CREATED);
        }


        return new ResponseEntity<>("property Id is not found ", HttpStatus.CONFLICT);
    }





    @DeleteMapping("/deleteBooking/{propertyId}")
    public ResponseEntity<String> deleteBooking(@PathVariable long propertyId , @RequestParam("bucket") String bucketName , @RequestParam("fileName") String fileName) {


        Optional<Property> byId = propertyRepository.findById(propertyId);
        if (byId.isPresent()) {
            Property property = byId.get();

            bookingService.deleteBooking(property);

            if (amazonS3.doesObjectExist(bucketName, fileName)) {
                // deleting file from AWS S3
                bucketService.deleteFile(bucketName, fileName);
                System.out.println("----------------------------------");
                System.out.println("BucketName:" + bucketName);
                System.out.println("fileName:" + fileName);

                return new ResponseEntity<>("Booking deleted succesfully", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("property id not found", HttpStatus.CONFLICT);


    }







    public static MultipartFile convertFileToMultipartFile(String filePath) throws IOException {
        // File to be converted
        File file = new File(filePath);

        // Convert file content into a byte array
        byte[] fileContent = readFileToByteArray(file);

        // Return a custom MultipartFile object
        return createMultipartFile(file, fileContent);
    }

    private static byte[] readFileToByteArray(File file) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(file)) {
            return inputStream.readAllBytes();
        }
    }

    private static MultipartFile createMultipartFile(File file, byte[] fileContent) {
        return new MultipartFile() {
            @Override
            public String getName() {
                return file.getName();
            }

            @Override
            public String getOriginalFilename() {
                return file.getName();
            }

            @Override
            public String getContentType() {
                return "application/pdf"; // Adjust content type if necessary
            }

            @Override
            public boolean isEmpty() {
                return fileContent.length == 0;
            }

            @Override
            public long getSize() {
                return fileContent.length;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return fileContent;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return new ByteArrayInputStream(fileContent);
            }

            @Override
            public void transferTo(File dest) throws IOException {
                try (OutputStream outputStream = new FileOutputStream(dest)) {
                    outputStream.write(fileContent);
                }
            }
        };


    }

}
