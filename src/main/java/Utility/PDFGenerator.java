package Utility;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import Model.Bill;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Utility class responsible for generating PDF bills using iText.
 * Given a {@link Model.Bill} and a filename, creates a formatted PDF in a specific folder.
 *
 * The PDF includes:
 * <ul>
 *     <li>Order ID</li>
 *     <li>Client and product information</li>
 *     <li>Quantity and total price</li>
 * </ul>
 */

public class PDFGenerator
{
    public static void generateBillPDF(Bill bill, String fileName) {
        Document document = new Document();

        try {
            Path billPath = Paths.get("bill");
            File dir = billPath.toFile();

            if(!dir.exists())
            {
                dir.mkdirs();
            }

            Path fullPath = billPath.resolve(fileName);

            PdfWriter.getInstance(document, new FileOutputStream(fullPath.toFile()));
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Paragraph title = new Paragraph("Factura / Bill", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Order ID: " + bill.orderId()));
            document.add(new Paragraph("Client: " + bill.clientName()));
            document.add(new Paragraph("Product: " + bill.productName()));
            document.add(new Paragraph("Quantity: " + bill.quantity()));
            document.add(new Paragraph("Total Price: " + bill.totalPrice() + " RON"));

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}