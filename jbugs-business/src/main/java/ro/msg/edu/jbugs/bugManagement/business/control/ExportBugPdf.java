package ro.msg.edu.jbugs.bugManagement.business.control;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.scene.layout.Background;
import ro.msg.edu.jbugs.bugManagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.bugManagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.bugManagement.business.exceptions.ExceptionCode;


import javax.ejb.Stateless;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


@Stateless
public class ExportBugPdf {

    private static String FILE = "t:/BugPdf.pdf";
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font fFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.NORMAL);


    public void createPdf(BugDTO bugDTO) throws BusinessException {
        try {
            Document document = new Document();
            PdfWriter pdfWriter =PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();
            addContent(document, bugDTO);
            document.close();

        } catch (FileNotFoundException e) {
            throw new BusinessException(ExceptionCode.BUG_NOT_EXPORTED);
        } catch (DocumentException e) {
            throw new BusinessException(ExceptionCode.BUG_NOT_EXPORTED);
        }

    }

    private void createTable(Document document, BugDTO bugDTO)
            throws BusinessException {
        PdfPTable table = new PdfPTable(new float[]{25, 45});
        table.setWidthPercentage(60);

        DateFormat formatterDate = new SimpleDateFormat("yyyy-MM-dd");
        String targetDate = formatterDate.format(bugDTO.getTargetDate());
        table.addCell(getCell("Description", PdfPCell.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(getCell(bugDTO.getDescription(), PdfPCell.ALIGN_LEFT, BaseColor.WHITE));
        table.addCell(getCell("Version", PdfPCell.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(getCell(bugDTO.getVersion(), PdfPCell.ALIGN_LEFT, BaseColor.WHITE));
        table.addCell(getCell("Target date", PdfPCell.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(getCell(targetDate, PdfPCell.ALIGN_LEFT, BaseColor.WHITE));
        table.addCell(getCell("Status", PdfPCell.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(getCell(bugDTO.getStatus().toString(), PdfPCell.ALIGN_LEFT, BaseColor.WHITE));
        table.addCell(getCell("Fixed version", PdfPCell.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(getCell(bugDTO.getFixedVersion(), PdfPCell.ALIGN_LEFT, BaseColor.WHITE));
        table.addCell(getCell("Severity", PdfPCell.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(getCell(bugDTO.getSeverity().toString(), PdfPCell.ALIGN_LEFT, BaseColor.WHITE));
        table.addCell(getCell("Created by", PdfPCell.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(getCell(bugDTO.getCreatedByUser().getUsername(), PdfPCell.ALIGN_LEFT, BaseColor.WHITE));
        table.addCell(getCell("Assigned to", PdfPCell.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(getCell(bugDTO.getAssignedTo().getUsername(), PdfPCell.ALIGN_LEFT, BaseColor.WHITE));
        try {
            document.add(table);
        } catch (DocumentException e) {
            throw new BusinessException(ExceptionCode.BUG_NOT_EXPORTED);
        }

    }

    private void addContent(Document document, BugDTO bugDTO) throws BusinessException {
        document.addTitle("Bug");
        Paragraph preface = new Paragraph();
        addEmptyLine(preface, 1);
        preface.add(new Paragraph("Bug " + bugDTO.getTitle(), catFont));
        preface.setAlignment(Element.ALIGN_CENTER);
        addEmptyLine(preface, 2);
        try {
            document.add(preface);
        } catch (DocumentException e) {
            throw new BusinessException(ExceptionCode.BUG_NOT_EXPORTED);
        }
        createTable(document, bugDTO);


    }

    public PdfPCell getCell(String text, int alignment, BaseColor color) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setFixedHeight(26f);
        cell.setPaddingBottom(11f);
        cell.setBackgroundColor(color);
        cell.setHorizontalAlignment(alignment);
        return cell;
    }


    private void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}


