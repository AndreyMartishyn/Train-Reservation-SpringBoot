package ua.martishyn.app.utils.pdf;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import ua.martishyn.app.entities.Ticket;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PdfGenerator {
    private final Ticket ticket;

    public PdfGenerator(Ticket boughtTicket) {
        ticket = boughtTicket;
    }


    public void export(HttpServletResponse response) throws IOException {
        try (Document document = new Document(PageSize.A4)) {
            PdfWriter.getInstance(document, response.getOutputStream());
            BaseFont font = BaseFont.createFont("Courier" , "CP1251", BaseFont.EMBEDDED);
            Font newFont = new Font(font,12,Font.BOLDITALIC);
            document.open();
            document.add(new Paragraph("Train ticket"));
            Image image = Image.getInstance("src/main/resources/static/images/local_railway.png");
            document.add(image);

            //specify column widths
            float[] columnWidths = {2f, 2f, 2f, 2f, 2f, 2f};
            //create PDF table with the given widths
            PdfPTable table = new PdfPTable(columnWidths);


            PdfPCell cell1 = new PdfPCell(new Paragraph("Ticket#", newFont));
            PdfPCell cell2 = new PdfPCell(new Paragraph("Train#", newFont));
            PdfPCell cell3 = new PdfPCell(new Paragraph("From", newFont));
            PdfPCell cell4 = new PdfPCell(new Paragraph("To", newFont));
            PdfPCell cell5 = new PdfPCell(new Paragraph("Name", newFont));
            PdfPCell cell6 = new PdfPCell(new Paragraph("Surname",newFont));

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            table.addCell(cell5);
            table.addCell(cell6);

            table.addCell(String.valueOf(ticket.getId()));
            table.addCell(String.valueOf(ticket.getDeparture().getRoute().getTrain().getId()));
            table.addCell(String.valueOf(ticket.getDeparture().getStation().getName()));
            table.addCell(String.valueOf(ticket.getArrival().getStation().getName()));
            table.addCell(String.valueOf(ticket.getPassengerDetails().getFirstName()));
            table.addCell(String.valueOf(ticket.getPassengerDetails().getLastName()));

            document.add(table);
            Image image1 = Image.getInstance("src/main/resources/static/images/already_paid.png");
            document.add(image1);
        }
    }
}
