package ro.msg.edu.jbugs.bugmanagement.business.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import ro.msg.edu.jbugs.bugmanagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.bugmanagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.bugmanagement.business.exceptions.ExceptionCode;

import javax.ejb.Stateless;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


@Stateless
public class PdfExportService {


    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);

    /**
     * Add all the fields of a bug in a document
     * @param bugDTO
     * @param document
     * @throws BusinessException
     */
    public void createPdf(BugDTO bugDTO, Document document) throws BusinessException {

        document.open();
        addContent(document, bugDTO);
        document.close();

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
//        table.addCell(getCell(bugDTO.getCreatedByUser().toString(), PdfPCell.ALIGN_LEFT, BaseColor.WHITE));
        table.addCell(getCell("Assigned to", PdfPCell.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
//        table.addCell(getCell(bugDTO.getAssignedTo().toString(), PdfPCell.ALIGN_LEFT, BaseColor.WHITE));
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


